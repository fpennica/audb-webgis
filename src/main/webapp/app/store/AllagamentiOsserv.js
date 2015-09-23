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

Ext.define('AUDB.store.AllagamentiOsserv', {
    extend: 'GeoExt.data.FeatureStore',
    
    requires: ['AUDB.model.AllagamentiOsserv'],
    
    model: 'AUDB.model.AllagamentiOsserv',
    
    autoLoad: false
    
    // GeoExt gestisce internamente un memory proxy
    
//    proxy: {
//        type: 'ajax',
//        url: 'audb/allagamentiosserv/findAll.json',
//        reader: {
//            type: 'json',
//            root: 'data'
//        }
//    }
    
//    doFindByUser: function(username, startDate, endDate) {
//        this.load({
//            url: 'taskLog/findByUser.json',
//            params: {
//                username: username,
//                startDate: Ext.Date.format(startDate, 'Ymd'),
//                endDate: Ext.Date.format(endDate, 'Ymd')
//            }
//        });
//    }

});
