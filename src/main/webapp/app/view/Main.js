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

Ext.define('AUDB.view.Main', {
    extend: 'Ext.container.Container',
    
    requires:[
        'Ext.tab.Panel',
        'Ext.layout.container.Border',
        //'AUDB.view.Header',
        'AUDB.view.CenterPanel',
        'AUDB.view.WestPanel',
        'AUDB.view.EastPanel'
    ],
    
    xtype: 'app-main',

    layout: {
        type: 'border'
    },

    items: [
//        {
//        xtype: 'main-header',
//        region: 'north'
//        //border: true
//        //split: true,
//        //resizable: false,
//        //collapsible: false
//    }
    , {
        xtype: 'panel',
        cls: 'banner',
//        style: {
//            //background:'#ffffff',
//            backgroundImage: 'url(../resources/images/logo/banner.png)',
//            //backgroundSize: '100% 100%',
//            backgroundRepeat: 'no-repeat',
//            backgroundPosition: 'bottom left'
//        },
        ui: 'footer',
        id: 'main-header',
        region: 'north',
        height: 50,
        //border: true
        split: true,
        resizable: false,
        collapsible: true,
        //hideCollapseTool: true,
        header: false,
        collapseMode: 'mini'
    }
    , {
        xtype: 'main-westpanel',
        region: 'west',
        collapsible: true,
        width: 250,
        split: true
    }, {
        region: 'center',
        xtype: 'main-tabpanel'
    }, {
        region: 'east',
        xtype: 'main-eastpanel',
        collapsible: true,
        collapsed: true,
        //hideCollapseTool: true,
        width: 250,
        split: true
    }]
});