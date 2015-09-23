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

Ext.define('AUDB.model.AllagamentiFoto', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'idFoto', type: 'int', useNull: true},
        {name: 'percorso', type: 'string'}
    ],
    idProperty: 'idFoto',
    proxy: {
        type: 'ajax',
        idParam: 'idFoto',
        api: {
            create: 'audb/allagamentifoto/store.json',
            read: 'audb/allagamentifoto/find.json',
            update: 'audb/allagamentifoto/store.json',
            destroy: 'audb/allagamentifoto/remove.json'
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
        {type: 'presence', field: 'percorso'}
    ]

});
