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

Ext.define("AUDB.view.login.LoginWindow", {
    extend: 'Ext.window.Window',
    
    xtype: 'loginwindow',
    
    requires: [
        'Ext.form.Panel',
        'Ext.form.Label',
        'Ext.form.field.Text',
        'Ext.form.field.Display'
    ],

    title: 'AUDB Login',
    closable: false,
    resizable: false,
    autoShow: true,
    
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [{
                xtype: 'form',
                //reference: 'loginFormPanel',
                bodyPadding: 10,
                activeItem: 0,
                items: [{
                    xtype: 'textfield',
                    name: 'username',
                    fieldLabel: 'Username',
                    value: 'franz',
                    allowBlank: false,
                    enableKeyEvents: true,
                    listeners: {
    //                    specialKey: 'onSpecialKey'
                    }
                    //minLength: 3
                }, {
                    xtype: 'textfield',
                    name: 'password',
                    inputType: 'password',
                    fieldLabel: 'Password',
                    value: 'Fr4nc3sc0',
                    allowBlank: false,
                    enableKeyEvents: true,
                    listeners: {
    //                    specialKey: 'onSpecialKey'
                    }
                },{
                    xtype:'displayfield',
                    fieldStyle:{
                        textAlign:'center',
                        fontSize: 'x-small'
                    },
                    value:'Versione ' + AUDB.app.BUILD_VERSION + ' compilata il ' + AUDB.app.BUILD_DATE
                }],
    //            , {
    //                xtype: 'displayfield',
    //                hideEmptyLabel: false,
    //                value: 'Enter any non-blank password'
    //            }],
                buttons: [{
                    text: 'Login',
                    itemId: 'submit',
                    formBind: true,
                    listeners: {
        //                click: 'onLoginClick'
                    }
                }]
            }]
        });
        
        me.callParent(arguments);
    }
    
//    initComponent: function() {
//        var me = this;
//        Ext.applyIf(me, {
//            items: [{
//                xtype: 'textfield',
//                fieldLabel: 'User Name',
//                name: 'username',
//                value: 'franz',
//                allowBlank: false,
//                validateOnBlur: true,
//                emptyText: 'Enter a Username',
//                enableKeyEvents: true,
//                listeners: {
////                    specialKey: 'onSpecialKey'
//                }
//            }, {
//                xtype: 'textfield',
//                name: 'password',
//                value: '',
//                fieldLabel: 'Password',
//                inputType: 'password',
//                validateOnBlur: true,
//                allowBlank: false,
//                enableKeyEvents: true,
//                listeners: {
////                    specialKey: 'onSpecialKey'
//                }
//            }, {
//                xtype:'container',   
//                style:{
//                    textAlign:'center'
//                },
//                html: 'temp' //' Version ' + TTT.BUILD_VERSION + ' built on ' + TTT.BUILD_DATE
//            }],
//        buttons: [{
//            text: 'Login',
//            formBind: true
////            listeners: {
////                click: 'onLoginClick'
////            }
//        }]
//        });
//        me.callParent(arguments);
//    }
});