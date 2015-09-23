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

Ext.define("AUDB.view.allagamenti.AllagamentiOsservForm", {
    extend: 'Ext.form.Panel',
    xtype: 'allagamentiosservform',
    requires: [
        'Ext.form.FieldSet',
        'AUDB.view.allagamenti.ReverseGeocodingTrigger',
        'Ext.form.field.ComboBox',
        'Ext.form.field.Date',
        'Ext.form.field.Time',
        'Ext.form.field.Number',
        //'Ext.form.field.TextArea',
        'Ext.form.field.HtmlEditor',
        'Ext.form.field.Display',
        'Ext.toolbar.Toolbar'
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
                anchor: '100%',
                //labelWidth: 180,
                labelAlign: 'top',
                msgTarget: 'side'
            },
            items: [{
                xtype: 'displayfield',
                name: 'latlonupdate',
                value: 'Le coordinate del punto sulla mappa e nel database corrispondono.'
            }, {
                xtype: 'fieldset',
                //padding: 10,
                anchor: '100%',
                //layout: 'hbox',
                title: 'Luogo dell\'osservazione',
                items: [{
                    xtype: 'container',
                    layout: 'hbox',
                    items: [{
                        xtype: 'container',
                        flex: 1,
                        layout: 'anchor',
                        items: [{
                            xtype: 'displayfield',
                            name: 'lat',
                            fieldLabel: 'Latitudine precedente'
                        }, {
                            xtype: 'displayfield',
                            name: 'lon',
                            fieldLabel: 'Longitudine precedente'
                        }]
                    }, {
                        xtype: 'container',
                        flex: 1,
                        layout: 'anchor',
                        items: [{
                            xtype: 'numberfield',
                            name: 'coordY4326',
                            decimalPrecision: 6,
                            hideTrigger: true,
                            keyNavEnabled: false,
                            mouseWheelEnabled: false,
                            fieldLabel: 'Nuova latitudine',
                            afterLabelTextTpl: AUDB.util.Util.required
                        }, {
                            xtype: 'numberfield',
                            name: 'coordX4326',
                            decimalPrecision: 6,
                            hideTrigger: true,
                            keyNavEnabled: false,
                            mouseWheelEnabled: false,
                            fieldLabel: 'Nuova longitudine',
                            afterLabelTextTpl: AUDB.util.Util.required
                        }]
                    }]
                }, {
                    xtype: 'reversegeocodingtrigger',
                    name: 'indirizzo',
                    fieldLabel: 'Indirizzo',
                    afterLabelTextTpl: AUDB.util.Util.required,
                    queryMode: 'local',
                    displayField: 'nome',
                    valueField: 'nome',
                    store: 'Indirizzi'
                }, {
                    xtype: 'container',
                    layout: 'hbox',
                    items: [{
                        xtype: 'container',
                        flex: 1,
                        layout: 'anchor',
                        items: [{
                            xtype: 'numberfield',
                            name: 'idcodistat',
                            fieldLabel: 'Codice ISTAT',
                            anchor:'95%',
                            editable: false,
                            hideTrigger: true,
                            enableKeyEvents: true,
                            keyNavEnabled: false,
                            mouseWheelEnabled: false,
                            listeners: {
                                keypress: function(field, value) {
                                    Ext.Msg.alert('Codice ISTAT', 'Utilizzare i campi Regione, Provincia e Comune per inserire il codice ISTAT.');
                                }
                            }
                        }, {
                            xtype: 'combobox',
                            name: 'regioni',
                            fieldLabel: 'Regione',
                            anchor:'95%',
                            //queryMode: 'local',
                            editable: false,
                            store: 'Regioni',
                            displayField: 'nome',
                            valueField: 'nome',
                            listConfig: {
                                minWidth: 300
                            }
                        }]
                    }, {
                        xtype: 'container',
                        flex: 1,
                        layout: 'anchor',
                        items: [{
                            xtype: 'combobox',
                            name: 'province',
                            fieldLabel: 'Provincia',
                            //queryMode: 'local',
                            editable: false,
                            disabled: true,
                            store: 'Province',
                            displayField: 'nome',
                            valueField: 'nome',
                            listConfig: {
                                minWidth: 300
                            }
                        }, {
                            xtype: 'combobox',
                            name: 'comuni',
                            fieldLabel: 'Comune',
                            //queryMode: 'local',
                            editable: false,
                            disabled: true,
                            store: 'Comuni',
                            displayField: 'nome',
                            valueField: 'nome',
                            listConfig: {
                                minWidth: 300
                            }
                        }]
                    }]
                }]
            }, {
                xtype: 'fieldset',
                //padding: 10,
                anchor: '100%',
                //layout: 'hbox',
                title: 'Informazioni di dettaglio',
                items: [{
                    xtype: 'container',
                    layout: 'hbox',
                    items: [{
                        xtype: 'container',
                        flex: 1,
                        layout: 'anchor',
                        items: [{
                            xtype: 'datefield',
                            name: 'dataOsserv',
                            anchor:'95%',
                            format: 'd-M-Y',
                            fieldLabel: 'Data osservazione',
                            afterLabelTextTpl: AUDB.util.Util.required
                        }, {
                            xtype: 'timefield',
                            name: 'oraOsserv',
                            anchor:'95%',
                            format: 'H:i',
                            increment: 10,
                            fieldLabel: 'Ora osservazione',
                            afterLabelTextTpl: AUDB.util.Util.required
                        }, {
                            xtype: 'combobox',
                            name: 'idFonte',
                            fieldLabel: 'Fonte',
                            anchor:'95%',
                            //queryMode: 'local',
                            editable: false,
                            store: 'AllagamentiOsservFonti',
                            valueField: 'idFonte',
                            listConfig: {
                                minWidth: 300
                            },
                            tpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '<div class="x-boundlist-item"><b>{nomeFonte}</b></div>', '</tpl>'),
                            displayTpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '{nomeFonte}', '</tpl>')
                        }]
                    }, {
                        xtype: 'container',
                        flex: 1,
                        layout: 'anchor',
                        items: [{
                            xtype: 'combobox',
                            name: 'idEstensione',
                            fieldLabel: 'Estensione Approssimativa',
                            queryMode: 'local',
                            editable: false,
                            store: 'ValoriEstensione',
                            valueField: 'idEstensione',
                            listConfig: {
                                minWidth: 300
                            },
                            tpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '<div class="x-boundlist-item"><b>{estensioneValoreHtml}</b></div>', '</tpl>'),
                            displayTpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '{estensioneValore}', '</tpl>')
                        }, {
                            xtype: 'combobox',
                            name: 'idProfondita',
                            fieldLabel: 'Profondità Approssimativa',
                            queryMode: 'local',
                            editable: false,
                            store: 'ValoriProfondita',
                            valueField: 'idProfondita',
                            listConfig: {
                                minWidth: 300
                            },
                            tpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '<div class="x-boundlist-item"><b>{profonditaValoreHtml}</b></div>', '</tpl>'),
                            displayTpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '{profonditaValore}', '</tpl>')
                        }, {
                            xtype: 'combobox',
                            name: 'idDurata',
                            fieldLabel: 'Durata Approssimativa',
                            queryMode: 'local',
                            editable: false,
                            store: 'ValoriDurata',
                            valueField: 'idDurata',
                            listConfig: {
                                minWidth: 300
                            },
                            tpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '<div class="x-boundlist-item"><b>{durataValoreHtml}</b></div>', '</tpl>'),
                            displayTpl: Ext.create('Ext.XTemplate', '<tpl for=".">', '{durataValore}', '</tpl>')
                        }]
                    }]
                }]
            }, {
                xtype: 'htmleditor',
                height: 160,
                name: 'noteAllagamento',
                fieldLabel: 'Note',
                emptyText: 'Altre informazioni utili...'
            }, {
                xtype: 'displayfield',
                name: 'utenteIns',
                fieldLabel: 'Inserito da'
            }, {
                xtype: 'displayfield',
                name: 'utenteAgg',
                fieldLabel: 'Ultimo aggiornamento di'
            }
//                , {
//                    xtype: 'combobox',
//                    name: 'idTask',
//                    fieldLabel: 'Task',
//                    displayField: 'taskName',
//                    queryMode: 'local',
//                    store: 'Task',
//                    valueField: 'idTask'
//                }, {
//                    xtype: 'datefield',
//                    name: 'taskLogDate',
//                    format: 'd-M-Y',
//                    fieldLabel: 'Date'
//                }, {
//                    xtype: 'numberfield',
//                    name: 'hours',
//                    minValue: 0,
//                    decimalPrecision: 2,
//                    itemId: 'taskHours',
//                    fieldLabel: 'Hours'
//                }, {
//                    xtype: 'textareafield',
//                    height: 100,
//                    name: 'taskDescription',
//                    fieldLabel: 'Description',
//                    emptyText: 'Enter task log description here...'
//                }
//                , {
//                    xtype: 'toolbar',
//                    ui: 'footer',
//                    layout: {
//                        pack: 'end',
//                        type: 'hbox'
//                    },
//                    items: [{
//                        xtype: 'button',
//                        iconCls: 'delete',
//                        itemId: 'deleteBtn',
//                        disabled: true,
//                        text: 'Delete'
//                    }, {
//                        xtype: 'button',
//                        iconCls: 'save',
//                        itemId: 'saveBtn',
//                        text: 'Save'
//                    }]
//                }
            ],
            //}],
            buttons: [{
                text: 'Delete',
                iconCls: 'delete',
                itemId: 'deleteBtn',
                disabled: true
            }, {
                text: 'Save',
                iconCls: 'save',
                itemId: 'saveBtn',
                formBind: true
            }]
        });
        me.callParent(arguments);
    }
});