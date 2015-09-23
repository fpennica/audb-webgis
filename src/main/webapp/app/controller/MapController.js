/* 
 * Copyright (C) 2014 Francesco Pennica <francesco.pennica at igag.cnr.it>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

Ext.define('AUDB.controller.MapController', {
    extend: 'Ext.app.Controller',
    
    requires: [
        'AUDB.store.AllagamentiOsserv',
        'AUDB.view.allagamenti.AllagamentiOsservIdentForm',
        'AUDB.view.map.SVPanoramaPanel',
        'GeoExt.data.proxy.Protocol',
        'GeoExt.data.reader.WmsCapabilities',
        'GeoExt.data.WmsCapabilitiesLayerStore'
    ],
    
    views: [
        'map.MapPanel',
        'map.LayerTreePanel',
        'map.LegendPanel'
    ],
    
    models: ['AllagamentiOsserv'],
    
    stores: ['AllagamentiOsserv'],
    
    refs: [
        {ref: 'westPanel', selector: 'main-westpanel'},
        {ref: 'mainTabPanel', selector: 'main-tabpanel'},
        {ref: 'eastPanel', selector: 'main-eastpanel'},
        {ref: 'mapPanel', selector: 'audb-mappanel'},
        {
            ref: 'allagamentiOsservNumDispField',
            selector: 'main-tabpanel field[name=allagamentiOsservNum]'
        }, {
            ref: 'lowerDateField',
            selector: 'main-tabpanel field[name=lowerDate]'
        }, {
            ref: 'upperDateField',
            selector: 'main-tabpanel field[name=upperDate]'
        }
        
    ],
    
    init: function(application) {
        
        var me = this;

//        me.getAllagamentiOsservStore().on({
//            scope: me,
//            load : me.onAllagamentiOsservStoreLoad
//        });
        
        this.control({
            'audb-mappanel': {
                'beforerender': this.onMapPanelBeforeRender,
                'afterrender': this.onMapPanelAfterRender,
                'added': this.onMapPanelAdded,
                'activate': this.onMapPanelActivate,
                'deactivate': this.onMapPanelDeactivate
            },
            'layertreepanel': {
                'added': this.onLayerTreePanelAdded
            },
            'svpanoramapanel': {
                svdatanotfound: this.onSVDataNotFound
            }
        });
        
        this.listen({
             controller: {
                 '#MainController': {
                    'mainready': this.onMainReady,
                    'daterangechanged': this.onDateRangeChanged
                },
                '#AllagamentiOsservController': {
                    insertallagamento: this.onInsertAllagamento,
                    allagamentoeditstart: this.onAllagamentoEditStart,
                    allagamentoinsertcomplete: this.onAllagamentoInsertComplete,
                    zoomTo: this.onMapZoomTo,
                    currentprojectchanged: this.onCurrentProjectChanged
                },
                '#ToolsController': {
                    moveallagamento: this.onMoveAllagamento
                }
             }
         });
        
    },
    
    onLaunch: function() {
        var me = this;
        AUDB.app.allagamentiOsservStore = me.getAllagamentiOsservStore();
    },
    
    onMainReady: function() {
        var me = this;
        
        //TODO: carico la config della mappa dal server (ad es. impostazione utente srs mappa)
        
        OpenLayers.ProxyHost = 'gwtOpenLayersProxy?targetURL=';
        
        //creo la mappa OpenLayers in base alla config
        var map = me.createMap();
        
        //crea il MapPanel e lo inserisce nel TabPanel
        var mapPanel = me.createMapPanel(map);
        me.getMainTabPanel().add(mapPanel);
        me.getMainTabPanel().setActiveTab(mapPanel);
        
        AUDB.app.mapPanel = mapPanel;
        
        //lancio un evento 'mapready', che permette al MainController di creare il ManageAllagamenti
        me.fireEvent('mapready');
        
        //creo LayerTreePanel, LegendPanel, ecc.
        //creo layertree, legend ecc. e li inserisco nel west panel
        var westPanel = this.getWestPanel();
        //AUDB.app.wp = westPanel;
        var layerTreePanel = Ext.create('AUDB.view.map.LayerTreePanel');
        westPanel.add(layerTreePanel);
        
        var legendPanel = Ext.create('AUDB.view.map.LegendPanel');
        westPanel.add(legendPanel);
        
    },
    
    onMapPanelBeforeRender: function(mapPanel, options) {
        var me = this;
        
        Ext.log('onMapPanelBeforeRender');
        
        //this.mapPanel = mapPanel;

    },
    
    onMapPanelAfterRender: function() {
        Ext.log('onMapPanelAfterRender');
    },
    
    onMapPanelAdded: function() {
        Ext.log('onMapPanelAdded');
    },
    
    onMapPanelActivate: function() {
        var me = this;
        Ext.log('onMapPanelActivate');
        if(me.featurePopup) {
            me.featurePopup.show();
        }
    },
    
    onMapPanelDeactivate: function() {
        var me = this;
        Ext.log('onMapPanelDeactivate');
        if(me.featurePopup)
            me.featurePopup.hide();
    },
    
    onLayerTreePanelAdded: function() {
        Ext.log('onLayerTreePanelAdded');
    },
    
    onAllagamentiOsservStoreLoad: function(store, records) {
        // do custom stuff on summits load if you want, for example here we
        // zoom to summits extent
//        var dataExtent = store.layer.getDataExtent();
//        if (dataExtent) {
//            store.layer.map.zoomToExtent(dataExtent);
//        }
        Ext.log('onAllagamentiOsservStoreLoad');
//        var dataExtent = store.layer.getDataExtent();
//        if (dataExtent) {
//            store.layer.map.zoomToExtent(dataExtent);
//        }
    },
    
    onInsertAllagamento: function() {
        var me = this;
        Ext.log("Attivazione controllo Drawfeature");
        
        this.allagamentiDrawFeatureControl.activate();

//        insertLayer.events.register("featureadded", insertLayer.events.object, function (evt) {
//                var point = evt.feature.geometry.clone();
//                var destProj = new $wnd.OpenLayers.Projection('EPSG:4326');
//                var srcProj = new $wnd.OpenLayers.Projection('EPSG:900913');
//                point.transform(srcProj, destProj);
//        });
    },
    
    onAllagamentoEditStart: function() {
        var me = this;
        Ext.log('MapController onAllagamentoEditStart');
        
        if(me.featurePopup)
            me.featurePopup.close();
    },
    
    onAllagamentoInsertComplete: function() {
        Ext.log('MapController onAllagamentoInsertComplete');
        
        //this.insertPointLayer.removeAllFeatures();
        
        this.refreshAllagamentiOsserv();
        
        // ripristino i controlli
        this.allagamentiDrawFeatureControl.deactivate();
    },
    
    createMap: function() {
        var me = this;
        
        Ext.log("Initializing OpenLayers map...");
        
        //TODO: creare mappa in base a config salvata
        
        var ghyb, gsat;
        
        var map = new OpenLayers.Map({
            projection: 'EPSG:3857',
            displayProjection: 'EPSG:4326',
            transitionEffect: null,
            zoomMethod: null,
            
            // risolve il problema del resize delle finestre ExtJS sulla mappa
            // http://stackoverflow.com/questions/24960296/popup-on-a-mappanel-is-difficult-to-manually-resize
            fallThrough: true,
            
            controls: [
                new OpenLayers.Control.Navigation(),
                new OpenLayers.Control.Zoom(),
                new OpenLayers.Control.MousePosition({
                    numDigits: 0
                }),
                new OpenLayers.Control.ScaleLine({
                    bottomOutUnits: "",
                    maxWidth: 200
                }),
                new OpenLayers.Control.KeyboardDefaults()
                //new OpenLayers.Control.LayerSwitcher()
                //new OpenLayers.Control.LoadingPanel()
            ],
            layers: [
                new OpenLayers.Layer.Google(
                    "Google Streets",
                    {numZoomLevels: 21}
                ),
                ghyb = new OpenLayers.Layer.Google(
                    "Google Hybrid",
                    {type: google.maps.MapTypeId.HYBRID, numZoomLevels: 20}
                ),
                new OpenLayers.Layer.Google(
                    "Google Physical",
                    {type: google.maps.MapTypeId.TERRAIN}
                ),
                gsat = new OpenLayers.Layer.Google(
                    "Google Satellite",
                    {type: google.maps.MapTypeId.SATELLITE, numZoomLevels: 20}
                )
            ],
            center: new OpenLayers.LonLat(12.486458,41.889017)
                // Google.v3 uses web mercator as projection, so we have to
                // transform our coordinates
                .transform('EPSG:4326', 'EPSG:3857'),
            zoom: 10
        });
        
        ghyb.mapObject.setTilt(0);
        gsat.mapObject.setTilt(0);
        
        var locationLayer = new OpenLayers.Layer.Vector("Location", {
            displayInLayerSwitcher: false,
            styleMap: new OpenLayers.Style({
                externalGraphic: "http://openlayers.org/api/img/marker.png",
                graphicYOffset: -25,
                graphicHeight: 25,
                graphicTitle: "${name}"
            })
        });
        map.addLayers([locationLayer]);
        
        
        /*
         * layer WMS
         */
        // create a new WMS capabilities store
        var audbLayerStore = Ext.create('GeoExt.data.WmsCapabilitiesStore', {
            storeId: 'wmsCapsStore',
            url: "gwtOpenLayersProxy?targetURL=" + encodeURIComponent("http://webgis.urbisit.it/geoserver/it.urbisit.audb/ows?SERVICE=WMS&REQUEST=GetCapabilities"),
//            proxy: new GeoExt.data.proxy.Protocol({
//                protocol: new OpenLayers.Protocol.HTTP({
//                    url: 'http://webgis.urbisit.it/geoserver/it.urbisit.audb/ows?SERVICE=WMS&REQUEST=GetCapabilities',
//                    format: new OpenLayers.Format.WMSCapabilities()
//                })
//                
//            }),
            autoLoad: true
        });
        audbLayerStore.on({
            scope: me,
            load : me.onAudbLayerStoreLoad
        });
        
//        var wmsAllagamentiPlg = new OpenLayers.Layer.WMS(
//            "Allagamenti Roma 2004-2007",
//            "http://webgis.urbisit.it/geoserver/it.allagamentiurbani/ows?",
//        {
//            layers: 'it.allagamentiurbani:allagamenti_2004_2007_dpc_comune_roma', 
//            format: 'image/png',
//            transparent: true
//        },{
//            isBaseLayer: false,
//            visibility: true,
//            opacity: 0.7
//        });
//        map.addLayers([wmsAllagamentiPlg]);
        
        
        
        /*
         * Preparazione layer vettoriale allagamenti
         **********************************************************************/
        //esempio filter
        //var now = new Date();
        //var lowerDate = Ext.Date.format(new Date(new Date().getFullYear(), 0, 1), 'Y-m-d');
        //var upperDate = Ext.Date.format(new Date(2014,03,01), 'Y-m-d');
        //var firstOfMonth = Ext.Date.getFirstDateOfMonth(now);
        
        var lowerDate = Ext.Date.format(me.getLowerDateField().getValue(), 'Y-m-d');
        var upperDate = Ext.Date.format(me.getUpperDateField().getValue(), 'Y-m-d');
        
        Ext.log(lowerDate + " - " + upperDate);
        
        this.dateFilter = new OpenLayers.Filter.Comparison({
            type: OpenLayers.Filter.Comparison.BETWEEN,
            property: "dataOsserv",
            //value: d //firstOfMonth //"2000-01-01"
            lowerBoundary: lowerDate,
            upperBoundary: upperDate
        });

        this.dateFilterStrategy = new OpenLayers.Strategy.Filter({filter: this.dateFilter});
        //filterStrategy.setFilter(filter);
        
        var protocol = new OpenLayers.Protocol.HTTP({
            url: "audb/allagamentiosserv/findByProject.json", // filtrare sul poligono del progetto corrente: findByProject.json
            format: new OpenLayers.Format.GeoJSON()
            /* ??? {
                internalProjection: new OpenLayers.Projection("EPSG:4326"),
                externalProjection: new OpenLayers.Projection("EPSG:3857")
            }*/
        });
        
        var defaultStyle = new OpenLayers.Style({
            externalGraphic: "resources/images/flood_default.png",
            graphicWidth: 32,
            graphicHeight: 37,
            graphicYOffset: -35,
            cursor: 'pointer'
        });
        
        /*
         * mistero
         */
        defaultStyle.title = "Osservazione Allagamento";
        
        var selectedStyle = new OpenLayers.Style({
            externalGraphic: "resources/images/flood_selected.png"
        });
        
        this.refreshStrategy = new OpenLayers.Strategy.Refresh();
        
        this.allagamentiVectorLayer = new OpenLayers.Layer.Vector("Allagamenti AUDB", {
            styleMap: new OpenLayers.StyleMap({
                'default': defaultStyle,
                'select': selectedStyle
            }),
            rendererOptions: {yOrdering: true},
            projection: 'EPSG:4326',
            protocol: protocol,
//            protocol: new OpenLayers.Protocol.HTTP({
//                url: "audb/allagamentiosserv/findAll.json", // filtrare sul poligono del progetto corrente: findByProject.json
//                format: new OpenLayers.Format.GeoJSON()
//            }),
            strategies: [new OpenLayers.Strategy.Fixed(), this.dateFilterStrategy, this.refreshStrategy]
        });
        //layers.push(vecLayer);
        
        this.allagamentiVectorLayer.events.on({
            'loadend': function(e) {
                Ext.log('dati caricati: ' + e.object.features.length);
                
                me.getAllagamentiOsservNumDispField().setValue(e.object.features.length);
                
                me.zoomToAllagamentiOsservBounds();
            }
        });
        
//        this.allagamentiVectorLayer.events.register("featuresadded",this.allagamentiVectorLayer,function(){
//            var bounds = me.allagamentiVectorLayer.getDataExtent();
//            if(bounds) {
//                //map.panTo(bounds.getCenterLonLat());
//                me.mapPanel.map.zoomToExtent(bounds);
//            }
//        });
        
        // create popup on "featureselected"
        this.allagamentiVectorLayer.events.on({
            featureselected: function(e) {
                //il controllo selectfeature dovrebbe essere creato automaticamente
                //da GeoExt quando si fa il bind con il FeatureStore
                me.createPopup(e.feature);
            },
            featureunselected: function() {
                if(me.featurePopup) {
                    me.featurePopup.close();
                }
            }
        });
        
        //AUDB.app.allagamentiVectorLayer = this.allagamentiVectorLayer;

        // manually bind store to layer
        me.getAllagamentiOsservStore().bind(this.allagamentiVectorLayer);

        map.addLayers([this.allagamentiVectorLayer]);
        
        /*
         * Preparazione controlli mappa
         **********************************************************************/
        this.allagamentiDragFeatureControl = new OpenLayers.Control.DragFeature(this.allagamentiVectorLayer, {
            //autoActivate: true,
            onComplete: function(feature, px) {
                me.onDragComplete(feature, px);
            }
        });
        
//        this.insertPointLayer = new OpenLayers.Layer.Vector("Insert Point Layer", {
//            projection: 'EPSG:4326',
//            displayInLayerSwitcher: false
//        });
//        map.addLayers([this.insertPointLayer]);
        
        this.allagamentiDrawFeatureControl = new OpenLayers.Control.DrawFeature(
            //this.insertPointLayer,
            this.allagamentiVectorLayer,
            OpenLayers.Handler.Point
        );

        this.allagamentiDrawFeatureControl.events.register('featureadded', this.allagamentiDrawFeatureControl, function(evt) {
            //var point = evt.feature.geometry.clone();
//            var destProj = new OpenLayers.Projection('EPSG:4326');
//            var srcProj = new OpenLayers.Projection('EPSG:3857');
            evt.feature.geometry.transform('EPSG:3857', 'EPSG:4326');
            
            me.fireEvent('allagamentofeatureinserted', evt);
        });
        
        map.addControls([
            this.allagamentiDrawFeatureControl,
            this.allagamentiDragFeatureControl
        ]);

        return map;
    },
    
    onAudbLayerStoreLoad: function(store, records, successful, eOpts) {
        var me = this;
        
        var layers = [];
        
        if(successful) {
            Ext.each(records, function(record, index) {
                var layer = record.getLayer().clone();
                layer.visibility = false;
                layer.opacity = 0.8;
                layer.params.TILED = true;
                layers.push(layer);
            });
        }
        
        me.getMapPanel().map.addLayers(layers);

    },
    
//    loadAudbWmsLayers: function() {
//        var me = this;
//        
//        var layers = [];
//        
//        Ext.Ajax.request({
//            scope: this,
//            url: 'http://webgis.urbisit.it/geoserver/rest/workspaces/it.urbisit.audb/featuretypes.json',
//            success: function (conn, response, options, eOpts) {
//                var result = AUDB.util.Util.decodeJson(conn.responseText);
//                
//                var result = Ext.JSON.decode(conn.responseText, true);
//                
//                if (result.featureTypes) {
//                    Ext.each(result.featureTypes, function(featureType, index) {
//                        layers.push(new OpenLayers.Layer.WMS(
//                            featureType.name,
//                            featureType.href,
//                        {
//                            layers: 'it.allagamentiurbani:allagamenti_2004_2007_dpc_comune_roma', 
//                            format: 'image/png',
//                            transparent: true
//                        },{
//                            isBaseLayer: false,
//                            visibility: true,
//                            opacity: 0.7
//                        }));
//                    });
//                    
//                } else {
//                    AUDB.util.Util.showErrorMsg("errore");
//                }
//                
//            },
//            failure: function (conn, response, options, eOpts) {
//                AUDB.util.Util.showErrorMsg(conn.responseText);
//            }
//        });
//        
//        return layers;
//    },
    
    createMapPanel: function(map) {
        var me = this;

        var mapPanel = Ext.create('AUDB.view.map.MapPanel', {
            map: map
        });
        
        return mapPanel;
    },
    
    onMapZoomTo: function(lonlat) {
        var me = this;
        me.getMapPanel().map.setCenter(lonlat,18);
    },
    
    createPopup: function(feature) {
        var me = this;
        Ext.log('MapController createPopup');
        
        if(!AUDB.app.mapPanel.isVisible()) {
            Ext.log('Map is not visible, I\'m not creating popup');
            return;
        }
        
        var store = me.getAllagamentiOsservStore();
        var record = store.getByFeature(feature);
        
        var form = Ext.create('AUDB.view.allagamenti.AllagamentiOsservIdentForm');
        
        me.featurePopup = Ext.create('Ext.window.Window', {
            title: 'Osservazione Allagamento',
            width:360,
            height: 460,
            layout: 'fit',
            items: [form],
            listeners: {
                close: {
                    fn: function() {
                        //Ext.log('close');
                        me.featurePopup = null;
                    }
                }
//                hide: {
//                    fn: function(){ Ext.log('hide'); }
//                }
            },
            tools: [{
                type: 'search',
                tooltip: 'zoom',
                callback: function() {
                    var x = record.get('coordX4326');
                    var y = record.get('coordY4326');

                    var lonlat = new OpenLayers.LonLat(x,y).transform('EPSG:4326', 'EPSG:3857');
                    me.getMapPanel().map.setCenter(lonlat,18);
                },
                scope: this
            },{
                type: 'gear',
                tooltip: 'streetview',
                callback: function() {
                    var x = record.get('coordX4326');
                    var y = record.get('coordY4326');

                    var location = new google.maps.LatLng(y, x);
                    
                    me.createStreetViewPanorama(location);
                    
                },
                scope: this
            }]
        });
        
        form.loadRecord(record);
        
        me.featurePopup.show();
        me.featurePopup.alignTo(me.getMapPanel(), 'bl-bl');
        
//        me.featurePopup = Ext.create('GeoExt.window.Popup', {
//            title: 'Osservazione Allagamento',
//            location: feature,
//            width:200,
//            html: '<p>bogusMarkup</p><p>asdf</p>',
//            //maximizable: true,
//            //collapsible: true,
//            anchorPosition: 'top-right'
//        });
//        // unselect feature when the popup
//        // is closed
//        me.featurePopup.on({
//            close: function() {
////                if(OpenLayers.Util.indexOf(vectorLayer.selectedFeatures,
////                                           this.feature) > -1) {
////                    selectCtrl.unselect(this.feature);
////                }
//            }
//        });
//        me.featurePopup.show();
    },
    
    onMoveAllagamento: function() {
        var me = this;
        
        Ext.Msg.alert("Spostamento dato", "Cliccare e trascinare un allagamento sulla mappa per cambiarne la posizione geografica.");
        
        me.allagamentiDragFeatureControl.activate();
    },
    
    onDragComplete: function(feature, px) {
        var me = this;
        Ext.log("onDragComplete");
        
        Ext.Msg.confirm('Conferma spostamento', 'Feature spostata nella nuova posizione. Cliccare su "No" per annullare le modifiche.', function(btn) {
            if (btn === 'yes') {
                var store = me.getAllagamentiOsservStore();
                var record = store.getByFeature(feature);
                store.fireEvent('update', store, record);
                me.fireEvent('featuredragged', record);
            } else {
                //TODO: undo the drag
            }
        });
        
        me.allagamentiDragFeatureControl.deactivate();
        
    },
    
    onCurrentProjectChanged: function() {
        var me = this;
        if(me.featurePopup)
            me.featurePopup.close();
        this.refreshAllagamentiOsserv();
    },
    
    onDateRangeChanged: function() {
        var me = this;
        
        var lowerDate = Ext.Date.format(me.getLowerDateField().getValue(), 'Y-m-d');
        var upperDate = Ext.Date.format(me.getUpperDateField().getValue(), 'Y-m-d');
        
        Ext.log(lowerDate + " - " + upperDate);
        
        this.dateFilter.lowerBoundary = lowerDate;
        this.dateFilter.upperBoundary = upperDate;
        
        this.getAllagamentiOsservStore().removeAll();
        
        this.dateFilterStrategy.setFilter(this.dateFilter);
        
        this.refreshStrategy.refresh();
    },
    
    refreshAllagamentiOsserv: function() {
        //sembra migliorare le performance del successivo refresh()
        this.getAllagamentiOsservStore().removeAll();
        
        //this.allagamentiVectorLayer.refresh();
        this.refreshStrategy.refresh();
    },
    
    zoomToAllagamentiOsservBounds: function() {
        var bounds = this.allagamentiVectorLayer.getDataExtent();
        if(bounds) {
            //map.panTo(bounds.getCenterLonLat());
            AUDB.app.mapPanel.map.zoomToExtent(bounds);
        }
    },
    
    createStreetViewPanorama: function(location) {
        var me = this;
//        if(!me.streetViewService)
//            me.streetViewService = new google.maps.StreetViewService();
//
//        var panoramaPanel = Ext.create('Ext.panel.Panel', {
//            id: 'panoramaPanel',
//            listeners: {
//                
//            }
//        });
        
//        afterlayout: function() {
//                    //this.callParent(arguments);
//                    Ext.log('beforetrigger');
//                    
//                    if(me.panorama)
//                        google.maps.event.trigger(me.panorama, 'resize');
//                    Ext.log('aftertrigger');
//                }
        
        var svPanel = Ext.create('AUDB.view.map.SVPanoramaPanel', {
            location: location
        });
        
        me.panoramaWin = Ext.create('Ext.window.Window', {
            title: 'StreetView Panorama',
            width:600,
            height: 400,
            resizable: true,
            maximizable: true,
            layout: 'fit',
            autoShow: true,
            //items: [panoramaPanel], 
            items: [svPanel]
//            listeners: {
//                close: {
//                    fn: function() {
//                        //Ext.log('close');
//                        me.panoramaWin = null;
//                        me.panorama = null;
//                    }
//                }
//            },
//            tools: [{
//                type: 'gear',
//                tooltip: 'refresh',
//                callback: function() {
//                    Ext.log('beforetrigger');
//                    
//                    if(me.panorama)
//                        google.maps.event.trigger(me.panorama, 'resize');
//                    Ext.log('aftertrigger');
//                    
//                },
//                scope: this
//            }]
            
        });
        
        //me.panoramaWin.show();
        
        //svPanel.createStreetViewPanorama(location);
        
        //me.streetViewService.getPanoramaByLocation(location, 100, me.processSVData);
        
    },
    
    onSVDataNotFound: function() {
        var me = this;
        Ext.log('onSVDataNotFound');
        if(me.panoramaWin)
            me.panoramaWin.close();
    }
    
//    processSVData: function (data, status) {
//        var me = this;
//        me.panorama = new google.maps.StreetViewPanorama(document.getElementById('panoramaPanel'));
//        
//        pan = me.panorama;
//        
//        if (status == google.maps.StreetViewStatus.OK) {
////            var marker = new google.maps.Marker({
////              position: data.location.latLng,
////              map: map,
////              title: data.location.description
////            });
//
//            me.panorama.setPano(data.location.pano);
//            me.panorama.setPov({
//                heading: 270,
//                pitch: 0
//            });
//            me.panorama.setVisible(true);
//
////            google.maps.event.addListener(marker, 'click', function() {
////
////              var markerPanoID = data.location.pano;
////              // Set the Pano to use the passed panoID
////              panorama.setPano(markerPanoID);
////              panorama.setPov({
////                heading: 270,
////                pitch: 0
////              });
////              panorama.setVisible(true);
////            });
//        } else {
//            Ext.Msg.alert('Street View','Dati Street View non trovati nel raggio di 100 metri.');
//        }
//
//    }
    
});
