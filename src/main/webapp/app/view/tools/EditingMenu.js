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

Ext.define("AUDB.view.tools.EditingMenu", {
    extend: 'Ext.tree.Panel',
    
    xtype: 'editingmenu',
    
    requires: [
        'Ext.data.TreeStore'
    ],
    
    title: 'Editing',
    border: 0,
    autoScroll: true,
    rootVisible: false,
    
    initComponent: function() {
        var me = this;
        
        var store = Ext.create('Ext.data.TreeStore', {
            root: {
                expanded: true,
                children: [{
                    text: 'Inserimento nuovo dato',
                    leaf: true,
                    iconCls: 'addnew',
                    id: 'addnew'
                }, {
                    text: 'Modifica dato',
                    leaf: true,
                    iconCls: 'task',
                    id: 'edit'
                }, {
                    text: 'Elimina dato',
                    leaf: true,
                    iconCls: 'delete',
                    id: 'delete'
                }, {
                    text: 'Sposta dato',
                    leaf: true,
                    iconCls: 'pan-norm',
                    id: 'move'
                }
//                    { text: "detention", leaf: true },
//                    { text: "homework", expanded: true, children: [
//                        { text: "book report", leaf: true },
//                        { text: "algebra", leaf: true}
//                    ] },
//                    { text: "buy lottery tickets", leaf: true }
                ]
            }
        });
        
        Ext.applyIf(me, {
            store: store
        });
        
        me.callParent(arguments);
    }
});