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

Ext.define('AUDB.Application', {
    name: 'AUDB',

    extend: 'Ext.app.Application',
    
    requires: [
        'AUDB.view.login.LoginWindow',
        'AUDB.view.Viewport',
        'AUDB.view.Main'
    ],

    views: [
        'AUDB.view.Main',
        'AUDB.view.login.LoginWindow'
    ],

    controllers: [
        'MainController',
        'LoginController',
        'MapController',
        'UserController',
        'AllagamentiOsservController',
        'ToolsController'
    ],
    
    models: [
        'Utente',
        'AllagamentiOsserv'
    ],

    stores: [
        'User',
        'AllagamentiOsserv'
    ],
    
    init: function() {
        Ext.log("init");
        
        /*
         * si può usare AUDB.app invece di AUDB.getApplication() per 
         * accedere a variabili/metodi globali
         */
        AUDB.app = this;
        
        AUDB.app.BUILD_DATE = '$BUILD_DATE$';
        AUDB.app.BUILD_VERSION = '$BUILD_VERSION$';

    },
    
    launch: function() {
        var me = this;
        Ext.log("launch");
        /*
         * effettuo un check sul server per vedere se esiste già una sessione
         * attiva (tab del browser chiuso accidentalmente, ricaricata la pagina)
         */
        Ext.Ajax.request({
            url: 'audb/security/checkSession.json',
            //scope: this,
            success: function (conn, response, options, eOpts) {
                var result = AUDB.util.Util.decodeJson(conn.responseText);

                if (result.success) {
                    /*
                     * la sessione e l'utente associato esistono, per cui è come
                     * se fosse avvenuto il login
                     */
                    me.doAfterLogon(result.data);
                } else {
                    /*
                     * non c'è una sessione attiva per cui attivo la procedura 
                     * di login
                     */
                    Ext.widget('loginwindow');
                }
            },
            failure: function (conn, response, options, eOpts) {
                AUDB.util.Util.showErrorMsg(conn.responseText);
            }
        });
        
        
        
        //me.loginWindow = Ext.create('AUDB.view.login.LoginWindow');
        //me.loginWindow.show();
    },
    
    doAfterLogon: function (userObj) {
        Ext.log({msg: userObj});
        
        var currentUser = Ext.create('AUDB.model.Utente', {
            idutente: userObj.idutente,
            nome: userObj.nome,
            cognome: userObj.cognome,
            ente: userObj.ente,
            email: userObj.email,
            username: userObj.username,
            //pwd: userObj.pwd,
            amministratore: userObj.amministratore,
            downloadPdf: userObj.downloadPdf,
            editor: userObj.editor,
            attivo: userObj.attivo,
            webbdgt: userObj.webbdgt,
            audb: userObj.audb,
            idprogettoaudb: userObj.idprogettoaudb
        });
        AUDB.app.getCurrentUserModel = function () {
            return currentUser;
        };
        
        /*
         * creo un metodo globale per accedere all'utente corrente
         * AUDB.getApplication().getCurrentUser()
         * L'utente corrente non è accessibile in altro modo e non è modificabile
         * direttamente
         */
        AUDB.app.getCurrentUser = function () {
            return userObj;
        };

        this.startMainUI();
    },
    
    startMainUI: function() {
        
        Ext.widget('mainviewport');
        
        // start the session monitor
        //AUDB.util.SessionMonitor.start();
    },
    
    /*
     * qui si potrebbero definire variabili globali (tipo Registry)
     * AUDB.getApplication().globals.myVar
     */
    globals: {
        
    }
});
