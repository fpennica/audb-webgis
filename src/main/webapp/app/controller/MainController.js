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

Ext.define('AUDB.controller.MainController', {
    extend: 'Ext.app.Controller',
    
    views: [
        'Main'
    ],
    
    refs: [
        {ref: 'mainHeader', selector: 'app-main #main-header'},
        {ref: 'westPanel', selector: 'main-westpanel'},
        {ref: 'mainTabPanel', selector: 'main-tabpanel'},
        {ref: 'eastPanel', selector: 'main-eastpanel'},
        {
            ref: 'dateFilterBtn',
            selector: 'main-tabpanel #dateFilterBtn'
        }, {
            ref: 'lowerDateField',
            selector: 'main-tabpanel field[name=lowerDate]'
        }, {
            ref: 'upperDateField',
            selector: 'main-tabpanel field[name=upperDate]'
        }
    ],
    
    init: function () {
        this.control({
            'app-main': {
                'afterrender': this.onAfterRender
            },
            'main-tabpanel #dateFilterBtn': {
                click: this.onDateFilterBtnClick
            },
            'main-tabpanel #logoutBtn': {
                click: this.onLogoutBtnClick
            }
//            'main-header menu > menuitem': {
//                'click': this.onAdminItemClick
//            }
        });
        
        this.listen({
             controller: {
                '#AllagamentiOsservController': {
                    insertallagamento: this.onInsertAllagamento,
                    allagamentoinsertcomplete: this.onAllagamentoInsertComplete,
                    zoomTo: this.onMapZoomTo
                },
                '#MapController': {
                    mapready: this.onMapReady
                },
                '#ToolsController': {
                    manageusers: this.onManageUsers
                }
             }
         });
    },
    
    onAfterRender: function() {
        var me = this;
        
        /*
         * setto il range di date iniziali
         */
        var lowerDate = Ext.Date.format(new Date(new Date().getFullYear() - 1, 0, 1), 'Y-m-d');
        var upperDate = Ext.Date.format(new Date(), 'Y-m-d');
        
        me.getLowerDateField().setValue(lowerDate);
        me.getUpperDateField().setValue(upperDate);
        
        /*
          lancio un evento 'mainready' che verrà intercettato dal MapController,
          il quale:
            - carica la config della mappa dal server
            - crea la mappa OpenLayers in base alla config
            - crea il MapPanel e lo inserisce nel TabPanel
            - lancia un evento 'mapready', che permette al MainController di creare il ManageAllagamenti
            - crea LayerTreePanel, LegendPanel, ecc.
        */
        me.fireEvent('mainready');
        
    },
    
    onMapReady: function() {
        var me = this;
        
        var tabs = me.getMainTabPanel();
        
        // creo il tab dati
        //var manageAllagamentiOsservPanel = Ext.create('AUDB.view.allagamenti.ManageAllagamentiOsserv');
        //tabs.add(manageAllagamentiOsservPanel);
        var manageAllagamentiOsservPanel = tabs.add({
            xtype: 'allagamentiosservlist',
            closable: false,
            iconCls: 'table'
        });
        
//        var eastPanel = this.getEastPanel();
//        var manageAllagamentiOsservPanel = Ext.create('AUDB.view.allagamenti.AllagamentiOsservList');
//        eastPanel.add(manageAllagamentiOsservPanel);
    },
    
    onDateFilterBtnClick: function(btn, e, eOpts) {
        var me = this;
        
        if((!me.getLowerDateField().isValid() || !me.getUpperDateField().isValid()) ||
                (me.getLowerDateField().getValue() > me.getUpperDateField().getValue())) {
            Ext.Msg.alert('Date non corrette', 'Le date selezionate non sono corrette.');
            return;
        }
        
        me.fireEvent('daterangechanged');
        
//        Ext.log(me.getLowerDateField().getValue());
//        Ext.log(me.getUpperDateField().getValue());
    },
    
    onManageUsers: function(menuitem, e, opt) {
        var mainTabPanel = this.getMainTabPanel();
        
        var newTab = mainTabPanel.items.findBy(
            function(tab) {
                return tab.title === 'Utenti';
            });
        if(!newTab) {
            newTab = mainTabPanel.add({
                xtype: 'manageusers',
                closable: true,
                //layout: 'fit',
                iconCls: 'users',
                title: 'Utenti'
            });
        }
        mainTabPanel.setActiveTab(newTab);

    },
    
    onInsertAllagamento: function() {
        var me = this;
        
        this.activateMapPanel();
        
        me.getMainHeader().mask();
        me.getWestPanel().mask();
        me.getEastPanel().mask();
    },
    
    onAllagamentoInsertComplete: function() {
        var me = this;
        me.getMainHeader().unmask();
        me.getWestPanel().unmask();
        me.getEastPanel().unmask();
    },
    
    onMapZoomTo: function() {
        this.activateMapPanel();
    },
    
    onLogoutBtnClick: function(button, e, options) {
        this.fireEvent('logout', button);
    },
    
    activateMapPanel: function() {
        var tabs = this.getMainTabPanel();
        var mapTab = tabs.items.findBy(
            function(tab) {
                return tab.title === 'Mappa';
            });
        tabs.setActiveTab(mapTab);
    }
    
});
