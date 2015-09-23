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

Ext.define("AUDB.view.map.MapPanel", {
    extend: "GeoExt.panel.Map",
    
    xtype: "audb-mappanel",

    requires: [
        'GeoExt.Action',
        'GeoExt.form.field.GeocoderComboBox'
    ],
    
    title: 'Mappa',
    iconCls: 'layers',
    
    constructor: function() {
//        this.addEvents(
//            'someevent'
//        );

        this.callParent( arguments );
    },
    
    initComponent: function() {
        var me = this;

        Ext.log("Initializing MapPanel...");
        
        //var map = AUDB.app.olMap;
        
        //creazione toolbar
        var items = [];
        
        items.push(Ext.create('Ext.button.Button', Ext.create('GeoExt.Action', {
            control: new OpenLayers.Control.ZoomToMaxExtent(),
            map: me.map,
            //text: "max extent",
            iconCls: 'maxExtent',
            tooltip: "Zoom to max extent"
        })));
        
        items.push("-");

        // Navigation control
        items.push(Ext.create('Ext.button.Button',Ext.create('GeoExt.Action', {
            //text: "nav",
            control: new OpenLayers.Control.Navigation(),
            map: me.map,
            // button options
            toggleGroup: "draw",
            allowDepress: false,
            pressed: true,
            tooltip: "navigate",
            // check item options
            group: "draw",
            checked: true,
            iconCls: 'pan'
        })));

        items.push("-");

        // Navigation history - two "button" controls
        var ctrl = new OpenLayers.Control.NavigationHistory();
        me.map.addControl(ctrl);
        
        items.push(Ext.create('Ext.button.Button', Ext.create('GeoExt.Action', {
            //text: "previous",
            iconCls: 'zoomPrevious',
            control: ctrl.previous,
            disabled: true,
            tooltip: "previous in history"
        })));
        
        items.push(Ext.create('Ext.button.Button', Ext.create('GeoExt.Action', {
            //text: "next",
            iconCls: 'zoomNext',
            control: ctrl.next,
            disabled: true,
            tooltip: "next in history"
        })));
        
        items.push("-");
        
        items.push(
            {
                xtype: "gx_geocodercombo",
                layer: me.map.getLayersByName('Location')[0],
                // To restrict the search to a bounding box, uncomment the following
                // line and change the viewboxlbrt parameter to a left,bottom,right,top
                // bounds in EPSG:4326:
                //url: "http://nominatim.openstreetmap.org/search?format=json&viewboxlbrt=15,47,17,49",
                width: 280
            }
        );
        items.push("->");
        
        // Help action
//        items.push(
//            Ext.create('Ext.button.Button', {
//                text: "help"
//            })
//        );

        Ext.applyIf(me, {
            //map: map,
//            center: new OpenLayers.LonLat(12.486458,41.889017)
//                // Google.v3 uses web mercator as projection, so we have to
//                // transform our coordinates
//                .transform('EPSG:4326', 'EPSG:3857'),
//            zoom: 6,
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui: 'footer',
                items: items,
                style: {
                    //border: 0
                    //padding: 1
                }
            }]
        });
        
        me.callParent(arguments);
    }
});