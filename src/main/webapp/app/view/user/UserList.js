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

Ext.define('AUDB.view.user.UserList', {
    extend: 'Ext.grid.Panel',
    xtype: 'userlist',
    store: 'User',
    title: 'User List',
    viewConfig: {
        markDirty: false,
        stripeRows: false
    },
    columnLines: true,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            tools: [{
                type: 'refresh',
                tooltip: 'Refresh user list'
            }],
            columns: [{
                xtype: 'gridcolumn',
                dataIndex: 'username',
                flex: 1,
                text: 'Username'
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'nome',
                flex: 1,
                text: 'Nome'
            }, {
                xtype: 'gridcolumn',
                flex: 1,
                dataIndex: 'cognome',
                text: 'Cognome'
            }, {
                xtype: 'gridcolumn',
                flex: 2,
                dataIndex: 'email',
                text: 'Email'
            }]
        });
        me.callParent(arguments);
    }
});