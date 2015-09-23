/* 
 * Copyright (C) 2014 francesco
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

Ext.define('AUDB.store.Province', {
    extend: 'Ext.data.Store',
    requires: ['AUDB.model.GenericComboModel'],
    model: 'AUDB.model.GenericComboModel',
    autoLoad: false,
    proxy: {
        type: 'ajax',
        url: 'audb/codiceistat/findAllProvinceByRegione.json',
        reader: {
            type: 'json',
            root: 'data'
        }
    },
    extraParams: {
        regione: ''
    }
});
