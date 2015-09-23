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

Ext.define("AUDB.view.allagamenti.AllagamentiOsservIdentForm", {
    extend: 'Ext.form.Panel',
    
    xtype: 'allagamentiosservidentform',
    
    requires: [
        'Ext.form.FieldSet',
        'Ext.form.field.Display',
        'Ext.toolbar.Toolbar',
        'AUDB.ux.form.field.DateDisplayField'
    ],
    
    layout: {
        type: 'anchor'
    },
    
    bodyPadding: 10,
    border: false,
    autoScroll: true,
    
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            fieldDefaults: {
                anchor: '100%'
                //labelWidth: 180,
                //labelAlign: 'top',
                //msgTarget: 'side'
            },
            items: [{
                xtype: 'fieldset',
                //padding: 10,
                anchor: '100%',
                //layout: 'hbox',
                title: 'Luogo dell\'osservazione',
                items: [{
                    xtype: 'displayfield',
                    name: 'coordY4326',
                    fieldLabel: 'Latitudine'
                }, {
                    xtype: 'displayfield',
                    name: 'coordX4326',
                    fieldLabel: 'Longitudine'
                },{
                    xtype: 'displayfield',
                    name: 'indirizzo',
                    fieldLabel: 'Indirizzo'
                }, {
                    xtype: 'displayfield',
                    name: 'idcodistat',
                    fieldLabel: 'Codice ISTAT'
                }, {
                    xtype: 'displayfield',
                    name: 'regione',
                    fieldLabel: 'Regione'
                },{
                    xtype: 'displayfield',
                    name: 'provincia',
                    fieldLabel: 'Provincia'
                }, {
                    xtype: 'displayfield',
                    name: 'comuni',
                    fieldLabel: 'Comune'
                }]
            }, {
                xtype: 'fieldset',
                //padding: 10,
                anchor: '100%',
                //layout: 'hbox',
                title: 'Informazioni di dettaglio',
                items: [{
                    xtype: 'datedisplayfield',
                    name: 'dataOsserv',
                    fieldLabel: 'Data osservazione',
                    datePattern: 'd-M-Y'
                }, {
                    xtype: 'datedisplayfield',
                    name: 'oraOsserv',
                    fieldLabel: 'Ora osservazione',
                    datePattern: 'H:i'
                }, {
                    xtype: 'displayfield',
                    name: 'nomeFonte',
                    fieldLabel: 'Fonte'
                },{
                    xtype: 'displayfield',
                    name: 'estensioneValoreHtml',
                    fieldLabel: 'Estensione Approssimativa'
                }, {
                    xtype: 'displayfield',
                    name: 'profonditaValoreHtml',
                    fieldLabel: 'Profondità Approssimativa'
                }, {
                    xtype: 'displayfield',
                    name: 'durataValoreHtml',
                    fieldLabel: 'Durata Approssimativa'
                }, {
                    xtype: 'displayfield',
                    height: 50,
                    name: 'noteAllagamento',
                    fieldLabel: 'Note'
                }, {
                    xtype: 'displayfield',
                    name: 'utenteIns',
                    fieldLabel: 'Inserito da'
                }, {
                    xtype: 'displayfield',
                    name: 'utenteAgg',
                    fieldLabel: 'Ultimo aggiornamento di'
                }]
            }]
//            buttons: [{
//                text: 'Chiudi',
//                iconCls: 'close',
//                itemId: 'closeBtn'
//            }]
        });
        me.callParent(arguments);
    }
});