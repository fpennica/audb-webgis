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

Ext.define("AUDB.view.Header",{
    "extend": "Ext.toolbar.Toolbar",
    
    xtype: 'main-header',
    
    requires: [
        
    ],
    
    //height: 30,
    //split: true,
    //resizable: false,
    //collapsible: true,
    ui: 'footer',
    
    items: [{
        xtype: 'label',
        html: '<strong>AUDB</strong> - Database degli Allagamenti Urbani'
    },{
        xtype: 'tbfill'
    },{
        text: 'Admin',
        menu: {
            xtype: 'menu',
            items: [{
                text: "Gestione Utenti",
                itemId: 'userAdminItem'
            }]
        }
    },{
        xtype: 'tbseparator'
    },{
        xtype: 'button',
        text: 'Logout',
        itemId: 'logout',
        iconCls: 'logoff'
        //handler: 'onLogOutClick'
    }]
});
