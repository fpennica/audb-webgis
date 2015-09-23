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

Ext.define('AUDB.view.user.UserForm', {
    extend: 'Ext.form.Panel',
    xtype: 'userform',
    requires: [
        'Ext.form.FieldSet',
        'Ext.form.field.Radio',
        'Ext.form.RadioGroup',
        'Ext.toolbar.Toolbar',
        'Ext.form.field.Number'
    ],
    title: 'User Form',
//    layout: {
//        type: 'fit'
//    },
    bodyPadding: 10,
    border: false,
    autoScroll: true,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [{
                xtype: 'fieldset',
                padding: 10,
                //width: 350,
                fieldDefaults: {
                    anchor: '100%'
                },
                title: 'User',
                items: [{
                    xtype: 'textfield',
                    name: 'username',
                    fieldLabel: 'Username'
                }, {
                    xtype: 'textfield',
                    name: 'nome',
                    fieldLabel: 'Nome'
                }, {
                    xtype: 'textfield',
                    name: 'cognome',
                    fieldLabel: 'Cognome'
                }, {
                    xtype: 'textfield',
                    name: 'email',
                    fieldLabel: 'Email'
                }, {
                    xtype: 'textfield',
                    name: 'ente',
                    fieldLabel: 'Ente'
                }, {
                    xtype: 'textfield',
                    name: 'pwd',
                    inputType: 'password',
                    fieldLabel: 'Password'
                }, {
                    xtype: 'checkboxfield',
                    name: 'amministratore',
                    fieldLabel: 'Administrator'
                }, {
                    xtype: 'checkboxfield',
                    name: 'editor',
                    fieldLabel: 'Editor'
                }, {
                    xtype: 'checkboxfield',
                    name: 'downloadPdf',
                    fieldLabel: 'Download'
                }, {
                    xtype: 'checkboxfield',
                    name: 'webbdgt',
                    fieldLabel: 'Accesso WebBDGT'
                }, {
                    xtype: 'checkboxfield',
                    name: 'audb',
                    fieldLabel: 'Accesso AUDB'
                }, {
                    xtype: 'checkboxfield',
                    name: 'attivo',
                    fieldLabel: 'Utente attivo'
                }, {
                    
                    xtype: 'combobox',
                    name: 'idprogetto',
                    fieldLabel: 'Progetto di default',
                    //anchor:'95%',
                    queryMode: 'local',
                    editable: false,
                    store: 'Progetti',
                    displayField: 'nomeProgetto',
                    valueField: 'idprogetto',
                    listConfig: {
                        minWidth: 300
                    }
                    
                }, {
                    xtype: 'toolbar',
                    ui: 'footer',
                    layout: {
                        pack: 'end',
                        type: 'hbox'
                    },
                    items: [{
                        xtype: 'button',
                        itemId: 'deleteBtn',
                        iconCls: 'delete',
                        text: 'Delete'
                    }, {
                        xtype: 'button',
                        itemId: 'saveBtn',
                        iconCls: 'save',
                        text: 'Save'
                    }]
                }]
            }]
        });
        me.callParent(arguments);
    }
});