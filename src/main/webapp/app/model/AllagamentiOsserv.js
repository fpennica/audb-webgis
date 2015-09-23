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

Ext.define('AUDB.model.AllagamentiOsserv', {
    
    extend: 'Ext.data.Model',
    
    /*
            Integer idAllagamentoOsserv,
            String indirizzo,
            Date dataOsserv,
            Date oraOsserv,
            Integer idcodistat,
            Integer idEstensioneApprox,
            Integer idProfonditaApprox,
            Integer idDurataApprox,
            String note,
            Integer idUtenteIns,
            Date dataIns,
            Integer idUtenteAgg,
            Date dataAgg,
            Integer idFonte,
            Double coordX4326,
            Double coordY4326,
            Integer actionUserId
     */
    
    fields: [
        {name: 'idAllagamentoOsserv', type: 'int', useNull:true},
        {name: 'indirizzo', type: 'string'},
        {name: 'dataOsserv', type: 'date', dateFormat:'Y-m-d' },
        {name: 'oraOsserv', type: 'date', dateFormat:'H:i:s' },
        {name: 'idcodistat', type: 'int', useNull:true},
        {name: 'comune', type: 'string', persist:false},
        {name: 'provincia', type: 'string', persist:false},
        {name: 'regione', type: 'string', persist:false},
        {name: 'idEstensione', type: 'int', useNull:true},
        {name: 'estensioneValoreHtml', type: 'string', persist:false},
        {name: 'idProfondita', type: 'int', useNull:true},
        {name: 'profonditaValoreHtml', type: 'string', persist:false},
        {name: 'idDurata', type: 'int', useNull:true},
        {name: 'durataValoreHtml', type: 'string', persist:false},
        {name: 'noteAllagamento', type: 'string'},
        {name: 'utenteIns', type: 'string', persist:false},
        {name: 'utenteAgg', type: 'string', persist:false},
//        {name: 'idUtenteIns', type: 'int', useNull:true},
//        {name: 'dataIns', type: 'date', dateFormat:'Y-m-d H:i:s' },
//        {name: 'idUtenteAgg', type: 'int', useNull:true},
//        {name: 'dataAgg', type: 'date', dateFormat:'Y-m-d H:i:s' },
        {name: 'idFonte', type: 'int', useNull:true},
        {name: 'nomeFonte', type: 'string', persist:false},
        {name: 'noteFonte', type: 'string', persist:false},
        {name: 'coordX4326', type: 'float', useNull:true},
        {name: 'coordY4326', type: 'float', useNull:true},
        {
            name: 'lat',
            convert: function(value, record) {
                // record.raw a OpenLayers.Feature.Vector instance
                if (record.raw instanceof OpenLayers.Feature.Vector &&
                    record.raw.geometry instanceof OpenLayers.Geometry.Point) {
                    var lonlat = new OpenLayers.LonLat(record.raw.geometry.x,record.raw.geometry.y).transform('EPSG:3857', 'EPSG:4326');
                    return lonlat.lat.toFixed(6);
                    //return record.raw.geometry.y;
                } else {
                    return "This is not a Feature or geometry is wrong type";
                }
            },
            persist:false
        },
        {
            name: 'lon',
            convert: function(value, record) {
                // record.raw a OpenLayers.Feature.Vector instance
                if (record.raw instanceof OpenLayers.Feature.Vector &&
                    record.raw.geometry instanceof OpenLayers.Geometry.Point) {
                    var lonlat = new OpenLayers.LonLat(record.raw.geometry.x,record.raw.geometry.y).transform('EPSG:3857', 'EPSG:4326');
                    return lonlat.lon.toFixed(6);
                    //return record.raw.geometry.x;
                } else {
                    return "This is not a Feature or geometry is wrong type";
                }
            },
            persist:false
        }
    ],
    idProperty: 'idAllagamentoOsserv',
    
    proxy: {
        type: 'ajax',
        idParam: 'idAllagamentoOsserv',
        api: {
            create: 'audb/allagamentiosserv/store.json',
            read: 'audb/allagamentiosserv/find.json',
            update: 'audb/allagamentiosserv/store.json',
            destroy: 'audb/allagamentiosserv/remove.json'
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
        {type: 'presence', field: 'coordX4326'},
        {type: 'presence', field: 'coordY4326'},
        {type: 'presence', field: 'indirizzo'},
        {type: 'presence', field: 'dataOsserv'},
        {type: 'presence', field: 'oraOsserv'}
//        {type: 'length', field: 'username', min: 4},
//        {type: 'presence', field: 'nome'},
//        {type: 'length', field: 'nome', min: 2},
//        {type: 'presence', field: 'cognome'},
//        {type: 'length', field: 'cognome', min: 2},
//        {type: 'presence', field: 'email'},
//        {type: 'email', field: 'email'},
//        {type: 'presence', field: 'ente'},
//        //{type: 'presence', field: 'pwd'},
//        //{type: 'length', field: 'pwd', min: 6},
//        {type: 'presence', field: 'amministratore'},
//        {type: 'presence', field: 'downloadPdf'},
//        {type: 'presence', field: 'editor'},
//        {type: 'presence', field: 'attivo'},
//        {type: 'presence', field: 'webbdgt'},
//        {type: 'presence', field: 'audb'},
//        {type: 'presence', field: 'idprogetto'}
    ]

});

