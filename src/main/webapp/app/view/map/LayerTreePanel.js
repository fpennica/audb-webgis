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

Ext.define("AUDB.view.map.LayerTreePanel", {
    extend: 'GeoExt.tree.Panel',
    
    xtype: 'layertreepanel',
    
    requires: [
        'GeoExt.data.LayerTreeModel',
        'GeoExt.tree.BaseLayerContainer',
        'GeoExt.tree.OverlayLayerContainer'
    ],
    
    //layout: 'fit',
    title: 'Layers',
    rootVisible: false,
    
    initComponent: function() {
        var me = this;
        
        Ext.log("Initializing LayerTreePanel...");
        
        var store = Ext.create('Ext.data.TreeStore', {
            model: 'GeoExt.data.LayerTreeModel',
            root: {
                expanded: true,
                children: [
//                    {
//                        plugins: [{
//                            ptype: 'gx_layercontainer',
//                            store: AUDB.app.mapPanel.layers
//                        }],
//                        expanded: true
//                    },
                    {
                        plugins: ['gx_overlaylayercontainer'],
                        expanded: true,
                        text: "Overlays"
                    }, {
                        plugins: ['gx_baselayercontainer'],
                        expanded: true,
                        text: "Base Maps"
                    }
                ]
            }
        });
        
        Ext.applyIf(me, {
            store: store
        });
        
        me.callParent(arguments);
    }

});