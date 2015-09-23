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

Ext.define('AUDB.model.Utente', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'idutente', type: 'int', useNull:true},
        {name: 'nome', type: 'string'},
        {name: 'cognome', type: 'string'},
        {name: 'ente', type: 'string'},
        {name: 'email', type: 'string'},
        {name: 'username', type: 'string'},
        {name: 'pwd', type: 'string'},
        {name: 'amministratore', type: 'boolean'},
        {name: 'downloadPdf', type: 'boolean'},
        {name: 'editor', type: 'boolean'},
        {name: 'attivo', type: 'boolean', defaultValue: true},
        {name: 'webbdgt', type: 'boolean'},
        {name: 'audb', type: 'boolean', defaultValue: true},
        {name: 'idprogetto', type: 'int', useNull:true} //sarà inserito in idprogettoaudb
        //{name: 'idprogettoaudb', type: 'int', useNull:true}

    ],
    idProperty: 'idutente',
    proxy: {
        type: 'ajax',
        idParam: 'idutente',
        api: {
            create: 'audb/utente/store.json',
            read: 'audb/utente/find.json',
            update: 'audb/utente/store.json',
            destroy: 'audb/utente/remove.json'
        },
        reader: {
            type: 'json',
            root: 'data'
        },
        writer: {
            type: 'json',
            allowSingle: true,
            encode: true,
            root: 'data',
            writeAllFields: true
        }
    },
    validations: [
        {type: 'presence', field: 'username'},
        {type: 'length', field: 'username', min: 4},
        {type: 'presence', field: 'nome'},
        {type: 'length', field: 'nome', min: 2},
        {type: 'presence', field: 'cognome'},
        {type: 'length', field: 'cognome', min: 2},
        {type: 'presence', field: 'email'},
        {type: 'email', field: 'email'},
        {type: 'presence', field: 'ente'},
        //{type: 'presence', field: 'pwd'},
        //{type: 'length', field: 'pwd', min: 6},
        {type: 'presence', field: 'amministratore'},
        {type: 'presence', field: 'downloadPdf'},
        {type: 'presence', field: 'editor'},
        {type: 'presence', field: 'attivo'},
        {type: 'presence', field: 'webbdgt'},
        {type: 'presence', field: 'audb'},
        {type: 'presence', field: 'idprogetto'}
    ],
    
    encodePwd: function() {
        var plainPwd = this.get('pwd'),
            encodedPwd = AUDB.util.MD5.encode(plainPwd);

        this.set('pwd', encodedPwd);
    }



});
