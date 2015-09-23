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

Ext.define("AUDB.view.CenterPanel", {
    extend: 'Ext.tab.Panel',
    xtype: 'main-tabpanel',
    requires: [
        'Ext.toolbar.Toolbar',
        'Ext.layout.container.Border',
        'Ext.form.field.Date',
        'AUDB.view.allagamenti.AllagamentiOsservList', 
        'AUDB.view.allagamenti.AllagamentiOsservForm'],
    layout: {
        type: 'fit'
    },
    deferredRender: false,
    initComponent: function() {
        var me = this;
        var now = new Date();
        Ext.applyIf(me, {
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                ui: 'footer',
                items: [{
                    xtype: 'displayfield',
                    name: 'allagamentiOsservNum',
                    value: '0'
                }, {
                    xtype: 'displayfield',
                    //name: 'latlonupdate',
                    value: 'Allagamenti -'
                }, {
                    xtype: 'datefield',
                    //labelAlign: 'right',
                    name: 'lowerDate',
                    format: 'd-M-Y',
                    fieldLabel: 'Da',
                    //value: Ext.Date.getFirstDateOfMonth(now),
                    width: 160,
                    labelWidth: 25
                }, {
                    xtype: 'datefield',
                    labelAlign: 'right',
                    name: 'upperDate',
                    format: 'd-M-Y',
                    fieldLabel: 'A',
                    //value: Ext.Date.getLastDateOfMonth(now),
                    width: 160,
                    labelWidth: 25
                }, {
                    xtype: 'button',
                    iconCls: 'search',
                    itemId: 'dateFilterBtn',
                    text: 'Aggiorna',
                    tooltip: 'Aggiorna i dati in base alle date selezionate'
                }, { 
                    xtype: 'tbseparator'
                }, {
                    
                    xtype: 'combobox',
                    name: 'idprogetto',
                    labelAlign: 'right',
                    fieldLabel: 'Ambito',
                    labelWidth: 60,
                    //anchor:'95%',
                    //queryMode: 'local',
                    editable: false,
                    store: 'ProgettiUtente',
                    displayField: 'nomeProgetto',
                    valueField: 'idprogetto',
                    listConfig: {
                        minWidth: 300
                    }
                },{
                    xtype: 'tbfill'
                },{
                    xtype: 'button',
                    text: 'Logout',
                    itemId: 'logoutBtn',
                    iconCls: 'logoff'
                }]
            }]
        });
        me.callParent(arguments);
    }
});
