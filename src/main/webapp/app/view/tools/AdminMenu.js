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

Ext.define("AUDB.view.tools.AdminMenu", {
    extend: 'Ext.tree.Panel',
    
    xtype: 'adminmenu',
    
    requires: [
        'Ext.data.TreeStore'
    ],
    
    title: 'Amministrazione',
    border: 0,
    autoScroll: true,
    rootVisible: false,
    
    initComponent: function() {
        var me = this;
        
        var store = Ext.create('Ext.data.TreeStore', {
            root: {
                expanded: true,
                children: [{
                    text: 'Gestione Utenti',
                    leaf: true,
                    iconCls: 'users',
                    id: 'manageusers'
                }]
            }
        });
        
        Ext.applyIf(me, {
            store: store
        });
        
        me.callParent(arguments);
    }
});