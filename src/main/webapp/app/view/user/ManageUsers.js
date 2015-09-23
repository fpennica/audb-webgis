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

Ext.define('AUDB.view.user.ManageUsers', {
    extend: 'Ext.panel.Panel',
    xtype: 'manageusers',

    requires: [
        'Ext.toolbar.Toolbar',
        'AUDB.view.user.UserList',
        'AUDB.view.user.UserForm'
    ],

    layout: {
        type: 'hbox',
        align:'stretch'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    ui: 'footer',
                    items: [
                        {
                            xtype: 'button',
                            itemId:'addUserBtn',
                            iconCls: 'addnew',
                            text: 'Add user'
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'userlist',
                    //width:400,
                    flex: 1,
                    margin:1
                },
                {
                    xtype: 'userform',
                    flex: 1,
                    margin:1
                }
            ]
        });

        me.callParent(arguments);
    }

});