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

Ext.define('AUDB.model.Progetto', {
    extend: 'Ext.data.Model',
    
    fields: [
        {name: 'idprogetto', type: 'int', useNull:true},
        {name: 'nomeProgetto', type: 'string'},
        {name: 'noteProgetto', type: 'string', useNull:true},
        {name: 'modulo', type: 'string', defaultValue: 'audb'}
    ],
    
    idProperty: 'idprogetto',
    
    proxy: {
        type: 'ajax',
        idParam: 'idprogetto',
        api: {
            create: 'audb/progetto/store.json',
            read: 'audb/progetto/find.json',
            update: 'audb/progetto/store.json',
            destroy: 'audb/progetto/remove.json'
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
        {type: 'presence', field: 'nomeProgetto'}
    ]

});

