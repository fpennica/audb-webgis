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

Ext.define("AUDB.view.allagamenti.AllagamentiOsservList", {
    //extend: 'Ext.grid.Panel',
    extend: 'Ext.ux.LiveSearchGridPanel',
    
    xtype: 'allagamentiosservlist',
    viewConfig: {
        markDirty: false,
        emptyText: 'There are no records to display...',
        
        /*
         * Occhio al bug della ver 4.2.1 di Ext JS!!!
         * http://www.sencha.com/forum/showthread.php?268135-Grid-error-on-delete-selected-row
         */
        stripeRows: false,
        
        loadMask: true
    },
    title: 'Lista Allagamenti',
    store: 'AllagamentiOsserv',
    
    requires: [
        'Ext.grid.feature.Summary',
        'Ext.grid.column.Date',
        'Ext.grid.column.Action',
        'Ext.ux.grid.FiltersFeature',
        'Ext.util.Point',
        'GeoExt.selection.FeatureModel'
    ],
    
    selType: 'featuremodel',
    
    
    columnLines: true,
    
    initComponent: function() {
        var me = this;
        
        me.features = [
            Ext.create('Ext.ux.grid.FiltersFeature', { 
                local: true
            })
        ];
        
        Ext.applyIf(me, {
            columns: [{
                xtype: 'actioncolumn',
                width: 30,
                sortable: false,
                menuDisabled: true,
                items: [{
                    handler: function(view, rowIndex, colIndex, item, e) {
                        this.fireEvent('itemclick', this, 'zoomTo', view,
                        rowIndex, colIndex, item, e);
                    },
                    iconCls: 'zoomTo',
                    tooltip: 'Zoom To'
                }]
            }, {
                xtype: 'datecolumn',
                dataIndex: 'dataOsserv',
                format: 'd-M-Y',
                width: 100,
                text: 'Data'
            }, {
                xtype: 'datecolumn',
                dataIndex: 'oraOsserv',
                format: 'H:i',
                width: 60,
                text: 'Ora'
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'comune',
                text: 'Comune',
                filter: true
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'indirizzo',
                text: 'Indirizzo',
                width: 300,
                filter: true
            }
//            , {
//                xtype: 'gridcolumn',
//                dataIndex: 'provincia',
//                text: 'Provincia',
//                filter: true
//            }, {
//                xtype: 'gridcolumn',
//                dataIndex: 'regione',
//                text: 'Regione',
//                filter: true
//            }
            , {
                xtype: 'gridcolumn',
                dataIndex: 'nomeFonte',
                text: 'Fonte',
                filter: true
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'estensioneValoreHtml',
                text: 'Estensione',
                filter: true
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'profonditaValoreHtml',
                text: 'Profondità',
                filter: true
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'durataValoreHtml',
                text: 'Durata',
                filter: true
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'noteAllagamento',
                flex: 1,
                text: 'Note'
//                summaryType: 'count',
//                summaryRenderer: function(value, summaryData, dataIndex) {
//                    return Ext.String.format('<div style="font-weight:bold;text-align:right;">{0} Records, Total Hours:</div>', value);
//                }
            }
//            , {
//                xtype: 'gridcolumn',
//                dataIndex: 'coordY4326',
//                text: 'Latitudine',
//                filter: true
//            }, {
//                xtype: 'gridcolumn',
//                dataIndex: 'coordX4326',
//                text: 'Longitudine',
//                filter: true
//            }
            
//            , {
//                xtype: 'gridcolumn',
//                dataIndex: 'taskMinutes',
//                width: 50,
//                align: 'center',
//                text: 'Hours',
//                summaryType: 'sum',
//                renderer: function(value, metaData, record) {
//                    return record.get('hours');
//                },
//                summaryRenderer: function(value, summaryData, dataIndex) {
//                    var valHours = value / 60;
//                    return Ext.String.format('<b>{0}</b>', valHours);
//                }
//            }
        ]
        });
        me.callParent(arguments);
    }
});