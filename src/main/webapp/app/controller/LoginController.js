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

Ext.define('AUDB.controller.LoginController', {
    extend: 'Ext.app.Controller',
    
    requires: [
        'AUDB.util.MD5',
        'AUDB.util.Util',
        'AUDB.util.SessionMonitor',
        'Ext.MessageBox'
    ],
    
    views: [
        'login.LoginWindow'
    ],
    
    init: function(application) {
        this.control({
            "loginwindow form textfield": {
                specialkey: this.onTextfieldSpecialKey
            },
            'loginwindow form button': {
                click: this.onLoginButtonClick
            }
//            "main-header button#logout": {
//                click: this.onLogoutButtonClick
//            }

        });
        
        this.listen({
             controller: {
                '#MainController': {
                    logout: this.onLogout
                }
             }
         });
    },
    
    onTextfieldSpecialKey: function(field, e, options) {
        if (e.getKey() === e.ENTER) {
            var submitBtn = field.up('form').down('button#submit');
            submitBtn.fireEvent('click', submitBtn, e, options);
        }
    },
    
    onLoginButtonClick: function (button, e, options) {
        //Ext.getBody().mask(this.loginText);
        
        var formPanel = button.up('form'),
                loginWindow = button.up('loginwindow');
        var md5pwd = AUDB.util.MD5.encode(formPanel.form.findField('password').getValue());
        
        Ext.Ajax.request({
            scope: this,
            url: 'audb/security/logon.json',
            params: {
                username: formPanel.form.findField('username').getValue(),
                pwd: md5pwd
            },
            success: function (conn, response, options, eOpts) {
                var result = AUDB.util.Util.decodeJson(conn.responseText);
                
                if (result.success) {
                    // Remove Login Window
                    //this.getView().destroy();
                    loginWindow.destroy();
                    
                    AUDB.app.doAfterLogon(result.data);
                    
                } else {
                    AUDB.util.Util.showErrorMsg(result.msg);
                }
                //Ext.getBody().unmask();
            },
            failure: function (conn, response, options, eOpts) {
                AUDB.util.Util.showErrorMsg(conn.responseText);
            }
        });
    },
    
    onLogout: function (button) {
        Ext.Msg.confirm('Confirm Logout', 'Are you sure you want to log out of AUDB?', function (btn) {
            if (btn === 'yes') {
                Ext.Ajax.request({
                    url: 'audb/security/logout.json',
                    //scope: this,
                    success: function (conn, response, options, eOpts) {
                        var result = AUDB.util.Util.decodeJson(conn.responseText);

                        if (result.success) {
                            // Remove Main view
                            button.up('mainviewport').destroy();

                            /*
                             * Non so se è meglio usare Ext.widget o fare il reload, che
                             * fa una chiamata in più (checkSession)
                             */
                            // Ext.widget('login');
                            window.location.reload();
                        } else {
                            AUDB.util.Util.showErrorMsg(result.msg);
                        }
                    },
                    failure: function (conn, response, options, eOpts) {
                        AUDB.util.Util.showErrorMsg(conn.responseText);
                    }
                });
            }
        }, this);
    }
    
});
