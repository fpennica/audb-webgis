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

Ext.define('AUDB.view.map.SVPanoramaPanel', {
    extend: 'Ext.panel.Panel',
    
    xtype: 'svpanoramapanel',
    
    requires: [],
    
    id: 'panoramaPanel',
    
    panorama: null,
    
    streetViewService: new google.maps.StreetViewService(),
    
    initComponent: function() {
        var me = this;
        
        Ext.applyIf(me, {
            location: null,
            radius: 100,
            heading: 0,
            pitch: 0,
            zoom: 0,
            border: false
        });
        
        me.callParent(arguments);
        
    },
    
    afterFirstLayout: function() {
        this.callParent();
        
        var me = this;
        Ext.log('SVPanoramaPanel afterFirstLayout');
        
        me.panorama = new google.maps.StreetViewPanorama(this.getEl().dom);
        
        me.createStreetViewPanorama();
    },
    
    createStreetViewPanorama: function() {
        var me = this;
        Ext.log('createStreetViewPanorama');
        
        //var streetViewService = new google.maps.StreetViewService();
        
        me.streetViewService.getPanoramaByLocation(me.location, me.radius, function(data, status) {
            
            Ext.log('processSVData');
            if (status === google.maps.StreetViewStatus.OK) {

                me.panorama.setPano(data.location.pano);
                me.panorama.setPov({
                    heading: me.heading,
                    pitch: me.pitch,
                    zoom: me.zoom
                });
                me.panorama.setVisible(true);

            } else {
                Ext.Msg.alert('Street View','Dati Street View non trovati nel raggio di ' + me.radius + ' metri.');
                me.fireEvent('svdatanotfound');
            }
        });
    },
    
    afterLayout: function () {
        var me = this;
        if (me.panorama) {
            google.maps.event.trigger(me.panorama, "resize");
        }
    },
    
    beforeDestroy: function() {
        Ext.log('SVPanoramaPanel beforeDestroy');
        delete this.panorama;
        delete this.streetViewService;
    }
 
});
