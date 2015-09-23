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

Ext.define("AUDB.view.EastPanel",{
    extend: "Ext.panel.Panel",

    xtype: 'main-eastpanel',
    
    requires: [
        'Ext.layout.container.Accordion',
        'AUDB.view.tools.EditingMenu',
        'AUDB.view.tools.AdminMenu'
    ],

    title: 'Gestione Dati',
    
    bodyStyle: 'padding:0',
    
    layout: {
        type: 'accordion'
    },
    
//    items: [{
//        xtype: 'editingmenu'
//    }],

    initComponent: function() {
        var me = this;
        
        var isUserEditor = AUDB.app.getCurrentUserModel().get('editor');
        var isUserAdmin = AUDB.app.getCurrentUserModel().get('amministratore');
        
        var items = [];
        
        if(isUserEditor) {
            items.push({xtype: 'editingmenu'});
        }
        
        if(isUserAdmin) {
            items.push({xtype: 'adminmenu'});
        }
        
        Ext.applyIf(me, {
            items: items
        });
        
        me.callParent(arguments);
    }
    
    
});
