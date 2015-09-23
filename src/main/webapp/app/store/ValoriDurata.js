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

Ext.define('AUDB.store.ValoriDurata', {
    extend: 'Ext.data.Store',
    requires: ['AUDB.model.ValoriDurata'],
    model: 'AUDB.model.ValoriDurata',
    
//    sorters: [{
//            property: 'companyName',
//            direction: 'ASC'
//        }, {
//            property: 'projectName',
//            direction: 'ASC'
//        }],
    
    proxy: {
        type: 'ajax',
        url: 'audb/valoridurata/findAll.json',
        reader: {
            type: 'json',
            root: 'data'
        }
    }
});
