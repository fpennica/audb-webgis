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

Ext.define('AUDB.controller.UserController', {
    extend: 'Ext.app.Controller',
    
    requires: [
        'AUDB.store.Progetti'
    ],
    
    views: ['user.ManageUsers'],
    
    stores: [
        'Progetti'
    ],
    
    refs: [{
        ref: 'userList',
        selector: 'manageusers userlist'
    }, {
        ref: 'userForm',
        selector: 'manageusers userform'
    }, {
        ref: 'addUserButton',
        selector: 'manageusers #addUserBtn'
    }, {
        ref: 'saveUserButton',
        selector: 'manageusers userform #saveBtn'
    }, {
        ref: 'deleteUserButton',
        selector: 'manageusers userform #deleteBtn'
    }, {
        ref: 'userFormFieldset',
        selector: 'manageusers userform fieldset'
    }, {
        ref: 'usernameField',
        selector: 'manageusers userform textfield[name=username]'
    }, {
        ref: 'pwdField',
        selector: 'manageusers userform textfield[name=pwd]'
    }],

    init: function(application) {
        this.control({
            'manageusers #addUserBtn': {
                click: this.doAddUser
            },
            'userlist': {
                itemclick: this.doSelectUser,
                viewready: this.doInitStore
            },
            'manageusers userform #saveBtn': {
                click: this.doSaveUser
            },
            'manageusers userform #deleteBtn': {
                click: this.doDeleteUser
            },
            'manageusers userform': {
                afterrender: this.doAddUser
            },
            'userlist header tool[type="refresh"]': {
                click: this.doRefreshUserList
            }
        });
    },
    
    doInitStore: function() {
        this.getUserList().getStore().load();
        this.getProgettiStore().load();
    },
    doAddUser: function() {
        var me = this;
        me.getUserFormFieldset().setTitle('Add New User');
        me.getUsernameField().enable();
        var newUserRec = Ext.create('AUDB.model.Utente', {
            amministratore: false
        });
        me.getUserForm().loadRecord(newUserRec);
        me.getDeleteUserButton().disable();
    },
    doSelectUser: function(grid, record) {
        var me = this;
        me.getUserForm().loadRecord(record);
        me.getUserFormFieldset().setTitle('Edit User ' + record.get('username'));
        me.getUsernameField().disable();
        
//        console.log(record.get('idprogettoaudb'));
//        me.getProgettiAudbCombo().setValue(record.get('idprogettoaudb'));
        
        me.getDeleteUserButton().enable();
    },
    doSaveUser: function() {
        var me = this;
        var rec = me.getUserForm().getRecord();
        if (rec !== null) {
            me.getUserForm().updateRecord();
            var errs = rec.validate();
            if (errs.isValid()) {
                
                if(!rec.get('idutente') && !rec.get('pwd')) {
                    Ext.Msg.alert('Password required', 'Password required for a new user!');
                    return;
                }
                
                if(rec.get('pwd')) {
                    rec.encodePwd();
                }
                
                rec.save({
                    success: function(record, operation) {
                        if(rec.get('pwd')) {
                            rec.set('pwd', '');
                        }
                        if (typeof record.store === 'undefined') {
                            // the record is not yet in a store 
                            me.getUserList().getStore().add(record);
                            // select the user in the grid
                            me.getUserList().getSelectionModel().select(record,true);
                        }
                        me.getUserFormFieldset().setTitle('Edit User ' + record.get('username'));
                        me.getUsernameField().disable();
                        me.getPwdField().setValue('');
                        me.getDeleteUserButton().enable();
                    },
                    failure: function(rec, operation) {
                        Ext.Msg.alert('Save Failure', operation.request.scope.reader.jsonData.msg);
                    }
                });
            } else {
                me.getUserForm().getForm().markInvalid(errs);
                Ext.Msg.alert('Invalid Fields', 'Please fix the invalid entries!');
            }
        }
    },
    doDeleteUser: function() {
        var me = this;
        var rec = me.getUserForm().getRecord();
        Ext.Msg.confirm('Confirm Delete User', 'Are you sure you want to delete user ' + rec.get('username') + '?', function(btn) {
            if (btn === 'yes') {
                rec.destroy({
                    failure: function(rec, operation) {
                        Ext.Msg.alert('Delete Failure', operation.request.scope.reader.jsonData.msg);
                    }
                });
                Ext.Msg.alert('User deactivated', 'User has been deativated!');
                me.doRefreshUserList();
                me.doAddUser();
            }
        });
    },
    doRefreshUserList: function() {
        this.getUserList().getStore().load();
    }
});