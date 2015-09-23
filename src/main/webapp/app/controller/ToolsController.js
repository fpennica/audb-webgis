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

Ext.define('AUDB.controller.ToolsController', {
    extend: 'Ext.app.Controller',
    
    id: 'toolsController',
    
    requires: [
        
    ],
    
    views: [
        'tools.EditingMenu',
        'tools.AdminMenu'
    ],
    
    stores: [
        
    ],
    
    refs: [
        
    ],

    init: function(application) {
        this.control({
            'editingmenu': {
                //select: this.onEditingMenuSelect,
                itemclick: this.onEditingMenuItemClick
            },
            'adminmenu': {
                //select: this.onEditingMenuSelect,
                itemclick: this.onAdminMenuItemClick
            }
        });
        
        this.listen({
             controller: {
                
             }
         });
    },
    
//    onEditingMenuSelect: function(selModel, record, index, options) {
//        Ext.log('onEditingMenuSelect');
//        console.log(record);
//    },
    
    onEditingMenuItemClick: function(view, record, item, index, event, options) {
        var me = this;
        
        Ext.log('onEditingMenuItemClick');
        Ext.log(record.data.id);
        
        switch(record.data.id) {
            case 'addnew':
                me.fireEvent('addnewallagamento');
                break;
            case 'edit':
                this.fireEvent('editallagamento');
                break;
            case 'delete':
                this.fireEvent('deleteallagamento');
                break;
            case 'move':
                this.fireEvent('moveallagamento');
                break;
            default:
                
        } 
    },
    
    onAdminMenuItemClick: function(view, record, item, index, event, options) {
        var me = this;
        
        Ext.log('onAdminMenuItemClick');
        Ext.log(record.data.id);
        
        switch(record.data.id) {
            case 'manageusers':
                me.fireEvent('manageusers');
                break;
            default:
                
        } 
    }
    
});
