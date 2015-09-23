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

Ext.define('AUDB.controller.AllagamentiOsservController', {
    extend: 'Ext.app.Controller',
    
    id: 'allagamentiOsservController',
    
    requires: [
        'AUDB.store.AllagamentiFoto'
    ],
    
    views: [
        'CenterPanel',
        'allagamenti.AllagamentiOsservEditWin',
        'allagamenti.AllagamentiOsservList'
    ],
    
    stores: [
        'AllagamentiOsserv',
        'AllagamentiOsservFonti',
        'ValoriDurata',
        'ValoriEstensione',
        'ValoriProfondita',
        'Regioni',
        'Province',
        'Comuni',
        'Indirizzi',
        'ProgettiUtente'
    ],
    
    refs: [{
        ref: 'allagamentiOsservList',
        selector: 'allagamentiosservlist'
    }, {
        ref: 'allagamentiOsservForm',
        selector: 'allagamentiosservform'
    }
//    , {
//        ref: 'addAllagamentoOsservBtn',
//        selector: 'main-tabpanel #addAllagamentoOsservBtn'
//    }
    , {
        ref: 'saveAllagamentiOsservButton',
        selector: 'allagamentiosservform #saveBtn'
    }
//    , {
//        ref: 'searchTaskLogsButton',
//        selector: 'managetasklogs #searchBtn'
    , {
        ref: 'deleteAllagamentiOsservButton',
        selector: 'allagamentiosservform #deleteBtn'
    }
//    , {
//        ref: 'taskLogFormFieldset',
//        selector: 'managetasklogs tasklogform fieldset'
//    }, {
//        ref: 'startDateField',
//        selector: 'managetasklogs datefield[name=startDate]'
//    }, {
//        ref: 'endDateField',
//        selector: 'managetasklogs datefield[name=endDate]'
//    }
    , {
        ref: 'allagamentiOsservCoordDisplayField',
        selector: 'allagamentiosservform field[name=latlonupdate]'
    }, {
        ref: 'allagamentiOsservCoordXDisplayField',
        selector: 'allagamentiosservform field[name=lon]'
    }, {
        ref: 'allagamentiOsservCoordYDisplayField',
        selector: 'allagamentiosservform field[name=lat]'
    }, {
        ref: 'allagamentiOsservCoordXField',
        selector: 'allagamentiosservform field[name=coordX4326]'
    }, {
        ref: 'allagamentiOsservCoordYField',
        selector: 'allagamentiosservform field[name=coordY4326]'
    }, {
        ref: 'allagamentiOsservIndirizzo',
        selector: 'allagamentiosservform field[name=indirizzo]'
    }, {
        ref: 'allagamentiOsservCodiceIstatField',
        selector: 'allagamentiosservform field[name=idcodistat]'
    }, {
        ref: 'regioniCombo',
        selector: 'allagamentiosservform combo[name=regioni]'
    }, {
        ref: 'provinceCombo',
        selector: 'allagamentiosservform combo[name=province]'
    }, {
        ref: 'comuniCombo',
        selector: 'allagamentiosservform combo[name=comuni]'
    }, {
        ref: 'allagamentiOsservFontiCombo',
        selector: 'allagamentiosservform combo[name=idFonte]'
    }, {
        ref: 'estensioneApproxCombo',
        selector: 'allagamentiosservform combo[name=idEstensione]'
    }, {
        ref: 'profonditaApproxCombo',
        selector: 'allagamentiosservform combo[name=idProfondita]'
    }, {
        ref: 'durataApproxCombo',
        selector: 'allagamentiosservform combo[name=idDurata]'
    }, {
        ref: 'progettiCombo',
        selector: 'main-tabpanel combo[name=idprogetto]'
    }
//    , {
//        ref: 'taskLogDateField',
//        selector: 'managetasklogs tasklogform datefield[name=taskLogDate]'
//    }, {
//        ref: 'taskHoursField',
//        selector: 'managetasklogs tasklogform #taskHours'
//    }
        
    ],

    init: function(application) {
        
        this.control({
            'allagamentiosservlist': {
                render: this.onAllagamentiOsservListAfterRender,
                activate: this.onAllagamentiOsservListAfterActivate
            },
            'allagamentiosservlist actioncolumn': {
                itemclick: this.onActionColumnClick
            },
//            'main-tabpanel #addAllagamentoOsservBtn': {
//                click: this.onAddAllagamentoOsservBtnClick
//            },
//            'main-tabpanel #editAllagamentoOsservBtn': {
//                click: this.onEditAllagamentoOsservBtnClick
//            },
//            'manageallagamentiosserv > #deleteAllagamentoOsservBtn': {
//                click: this.doDeleteAllagamentoOsserv
//            },
            'allagamentiosservform #saveBtn': {
                click: this.doSaveAllagamentoOsserv
            },
            'allagamentiosservform #deleteBtn': {
                click: this.onDeleteAllagamentoOsservBtnClick
            },
//            'manageallagamentiosserv #searchBtn': {
//                click: this.doSearch
//            },
            'allagamentiosservform combobox[name=regioni]': {
                select: this.onRegioniSelect
            },
            'allagamentiosservform combobox[name=province]': {
                select: this.onProvinceSelect
            },
            'allagamentiosservform combobox[name=comuni]': {
                select: this.onComuniSelect
            },
//            'allagamentiosservform combobox[name=fonte]': {
//                select: this.doSelectFonte
//            },
            'allagamentiosservform reversegeocodingtrigger': {
                triggerclick: this.doReverseGeocoding
            },
            'main-tabpanel combo[name=idprogetto]': {
                beforeselect: this.onProgettiComboBeforeSelect
                //select: this.onProgettiComboSelect
            }
        });
        
        this.listen({
             controller: {
                '#MapController': {
                    allagamentofeatureinserted: this.onAllagamentoFeatureInserted,
                    featuredragged: this.onFeatureDragged
                },
                '#ToolsController': {
                    'addnewallagamento': this.onAddNewAllagamento,
                    'editallagamento': this.onEditAllagamento,
                    'deleteallagamento': this.onDelteAllagamento
                    //'moveallagamento': this.onMoveAllagamento
                }
             }
         });
    },
    
    onLaunch: function() {
        
    },
    
    onAllagamentiOsservListAfterRender: function() {
        var me = this;
        
        var userObj = AUDB.app.getCurrentUser();
        
        // carico lo store dei progetti
        me.getProgettiUtenteStore().getProxy().setExtraParam("idUtente", userObj.idutente);
        me.getProgettiUtenteStore().load();
        
        me.getProgettiCombo().setValue(userObj.idprogetto);
        
        // non c'è bisogno di caricare questi store se l'utente non è editor
        if(userObj.editor) {
            me.getAllagamentiOsservFontiStore().load();
            me.getValoriDurataStore().load();
            me.getValoriEstensioneStore().load();
            me.getValoriProfonditaStore().load();
        }
    },
    
    onAllagamentiOsservListAfterActivate: function() {
        var me = this;
        
        var rec = me.getAllagamentiOsservList().getSelectionModel().getSelection()[0];
        
        if(rec) {
            var rowIndex = me.getAllagamentiOsservList().store.indexOf(rec);
            Ext.log('rowIndex: ' + rowIndex);
            me.getAllagamentiOsservList().getView().focusRow(rowIndex);
        }
    },
    
    onProgettiComboBeforeSelect: function(combo, record, index, eOpts) {
        var me = this;
        Ext.log('onProgettiComboBeforeSelect');
        
        //Ext.log('combo selected: ' + combo.getValue());
        
        Ext.Msg.confirm('Cambia Ambito', 'Sei sicuro di voler cambiare l\'ambito corrente?', function(btn) {
            if (btn === 'yes') {
                combo.setValue(record.get('idprogetto'));
                me.changeCurrentProject(combo.getValue());
            }
        });
        
        return false;
    },
    
//    onProgettiComboSelect: function(combo, records, eOpts) {
//        var me = this;
//        
//        Ext.log('combo selected: ' + combo.getValue());
//        
//        Ext.Msg.confirm('Cambia Ambito', 'Sei sicuro di voler cambiare l\'ambito corrente?', function(btn) {
//            if (btn === 'yes') {
//                me.changeCurrentProject(combo.getValue());
//            }
//        });
//    },
    
    changeCurrentProject: function(idprogetto) {
        var me = this;
        
        var userModel = AUDB.app.getCurrentUserModel();
        
        userModel.set('idprogetto', idprogetto);
        
        userModel.save({
            success: function(record, operation) {
                Ext.log('savesuccess');
                me.fireEvent('currentprojectchanged', idprogetto);
            },
            failure: function(rec, operation) {
                Ext.Msg.alert('Save Failure', operation.request.scope.reader.jsonData.msg);
            }
        });
        
//        Ext.Ajax.request({
//            scope: this,
//            url: 'audb/utente/updateProgettoDefaultAudb.json',
//            method: 'GET',
//            params: {
//                idprogetto: idprogetto
//            },
//            success: function (conn, response, options, eOpts) {
//                var result = AUDB.util.Util.decodeJson(conn.responseText);
//                
//                if (result.success) {

//                    
//                } else {
//                    AUDB.util.Util.showErrorMsg(result.msg);
//                }
//            },
//            failure: function (conn, response, options, eOpts) {
//                AUDB.util.Util.showErrorMsg(conn.responseText);
//            }
//        });
    },
    
    onRegioniSelect: function(combo, records, eOpts) {
        var me = this;
        me.getProvinceCombo().clearValue();
        me.getComuniCombo().clearValue();
        me.getComuniCombo().disable();
        var regioneSel = records[0].get('nome');
        me.getProvinceCombo().getStore().getProxy().setExtraParam("regione", regioneSel);
        me.getProvinceCombo().getStore().load();
        me.getProvinceCombo().enable();
    },
    
    onProvinceSelect: function(combo, records, eOpts) {
        var me = this;
        me.getComuniCombo().clearValue();
        var provinciaSel = records[0].get('nome');
        me.getComuniCombo().getStore().getProxy().setExtraParam("provincia", provinciaSel);
        me.getComuniCombo().getStore().load();
        me.getComuniCombo().enable();
    },
    
    onComuniSelect: function(combo, records, eOpts) {
        var me = this;
        var regione = me.getRegioniCombo().getValue();
        var provincia = me.getProvinceCombo().getValue();
        var comune = me.getComuniCombo().getValue();
        // chiamata ajax per ricavare il codice istat
        Ext.Ajax.request({
            scope: this,
            url: 'audb/codiceistat/findByRegioneProvinciaComune.json',
            method: 'GET',
            params: {
                regione: regione,
                provincia: provincia,
                comune: comune
            },
            success: function (conn, response, options, eOpts) {
                var result = AUDB.util.Util.decodeJson(conn.responseText);
                
                if (result.success) {
                    data = result.data;
                    me.getAllagamentiOsservCodiceIstatField().setValue(result.data.idcodistat);
                    
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
    
//    doSelectFonte: function(combo, records) {
//        var me = this;
//        var rec = records[0];
//        if (!Ext.isEmpty(rec)) {
//            me.getAllagamentiOsservFontiCombo().getStore().clearFilter();
//            me.getAllagamentiOsservFontiCombo().getStore().filter({
//                property: 'idFonte',
//                value: rec.get('idFonte'),
//                exactMatch: true
//            });
//            me.getAllagamentiOsservFontiCombo().setValue('');
//            if (me.getTaskCombo().getStore().getCount() === 0) {
//                Ext.Msg.alert('No Tasks Available', 'There are no tasks assigned to this project!');
//            }
//        }
//    },
    
    onAddNewAllagamento: function() {
        var me = this;
        
        Ext.Msg.confirm('Inserisci nuova osservazione', 'Inserire su mappa il punto in cui si è verificato l\'allagamento.', function(btn) {
            if (btn === 'yes') {
                // lancio evento per attivazione controllo DrawFeature in MapController
                me.fireEvent('insertallagamento');
            }
        });
    },
    
    onAllagamentoFeatureInserted: function(evt) {
        var me = this;
        
        Ext.log('Feature inserted: ' + evt.feature.geometry);
        
        //me.getAllagamentiOsservStore()
        var rec = Ext.create('AUDB.model.AllagamentiOsserv');
        
        //AUDB.app.allagamentiVectorLayer.addFeatures([evt.feature.geometry]);
        //AUDB.app.allagamentiOsservStore.addFeatures([evt.feature.geometry]);
        
        me.doEditAllagamentoOsserv(rec, evt.feature);
    },
    
    onEditAllagamento: function() {
        Ext.log("onEditAllagamento");
        var me = this;
        
        var grid = me.getAllagamentiOsservList();
        var record = grid.getSelectionModel().getSelection();
        
        if(record[0]) {
            me.doEditAllagamentoOsserv(record[0]);
        } else {
            Ext.Msg.alert('Modifica allagamento', 'Selezionare un allagamento nella lista.');
        }
    },
    
    doEditAllagamentoOsserv: function(rec, newFeature) {
        Ext.log("doEditAllagamentoOsserv");
        var me = this;
        
        //lancio un evento per far sapere alla mappa che è cominciato l'editing di un dato
        me.fireEvent('allagamentoeditstart');
        
        /*
         * TEST FOTO
         */
        var fotoStore = Ext.create('AUDB.store.AllagamentiFoto');
        fotoStore.getProxy().setExtraParam("idAllagamentoOsserv", rec.get('idAllagamentoOsserv'));
        fotoStore.load();
        
        
        var editWin = Ext.create('AUDB.view.allagamenti.AllagamentiOsservEditWin');
        
        me.getAllagamentiOsservFontiCombo().setValue(rec.get('idFonte'));
        me.getEstensioneApproxCombo().setValue(rec.get('idEstensione'));
        me.getProfonditaApproxCombo().setValue(rec.get('idProfondita'));
        me.getDurataApproxCombo().setValue(rec.get('idDurata'));
        if (!Ext.isEmpty(rec.get('idcodistat'))) {
            me.getRegioniCombo().setValue(rec.get('regione'));
            me.getProvinceCombo().enable();
            me.getProvinceCombo().getStore().getProxy().setExtraParam("regione", rec.get('regione'));
            me.getProvinceCombo().setValue(rec.get('provincia'));
            me.getComuniCombo().enable();
            me.getComuniCombo().getStore().getProxy().setExtraParam("provincia", rec.get('provincia'));
            me.getComuniCombo().setValue(rec.get('comune'));
        }

        me.getAllagamentiOsservForm().loadRecord(rec);

        if(!newFeature) {
            var oldX = rec.get('coordX4326');
            var oldY = rec.get('coordY4326');
        
            if(rec.get('lat') !== oldY.toString() || 
                    rec.get('lon') !== oldX.toString()) {
                me.getAllagamentiOsservCoordDisplayField().setValue("I valori delle coordinate verranno aggiornati");
                me.getAllagamentiOsservCoordXField().setValue(parseFloat(rec.get('lon')));
                me.getAllagamentiOsservCoordYField().setValue(parseFloat(rec.get('lat')));
                me.getAllagamentiOsservCoordXDisplayField().setValue(oldX);
                me.getAllagamentiOsservCoordYDisplayField().setValue(oldY);
            }
        } else {
            me.getAllagamentiOsservCoordXField().setValue(newFeature.geometry.x);
            me.getAllagamentiOsservCoordYField().setValue(newFeature.geometry.y);
        }

        me.getDeleteAllagamentiOsservButton().enable();

        //win.add(form);
        //win.setTitle('Add new User');
        editWin.show();

    },

    onDelteAllagamento: function() {
        Ext.log("onDeleteAllagamento");
        var me = this;
        
        var grid = me.getAllagamentiOsservList();
        var record = grid.getSelectionModel().getSelection();
        
        if(record[0]) {
            me.doDeleteAllagamentoOsserv(record[0]);
        } else {
            Ext.Msg.alert('Eliminazione allagamento', 'Selezionare un allagamento nella lista.');
        }
    },
    
    onDeleteAllagamentoOsservBtnClick: function() {
        var me = this;
        var rec = me.getAllagamentiOsservForm().getRecord();
        
        if(rec) {
            me.doDeleteAllagamentoOsserv(rec);
        }
    },
    
    doDeleteAllagamentoOsserv: function(rec) {
        var me = this;
        //var rec = me.getAllagamentiOsservForm().getRecord();
        Ext.Msg.confirm('Conferma cancellazione', 'Sei sicuro di voler eliminare l\'allagamento selezionato?', function(btn) {
            if (btn === 'yes') {
                //me.getAllagamentiList().getSelectionModel().deselectAll();
                rec.destroy({
                    success: function(rec, operation) {
                        if(me.getAllagamentiOsservForm())
                            me.getAllagamentiOsservForm().up('window').close();
                        Ext.Msg.show({
                            title: 'Eliminazione effettuata',
                            msg: 'L\'eliminazione è stata completata con successo.',
                            //width: 300,
                            buttons: Ext.Msg.OK,
                            //multiline: true,
                            icon: Ext.window.MessageBox.INFO
                        });
                        me.fireEvent('allagamentoinsertcomplete');
                    },
                    failure: function(rec, operation) {
                        Ext.Msg.alert('Delete Failure', operation.request.scope.reader.jsonData.msg);
                    }
                });
            }
        });
    },
    
    doSaveAllagamentoOsserv: function() {
        var me = this;
        Ext.log("doSaveAllagamentoOsserv");
        
        var rec = me.getAllagamentiOsservForm().getRecord();
        if (!Ext.isEmpty(rec)) {
            me.getAllagamentiOsservForm().updateRecord(); 
            // update the minutes field of the record
//            var hours = me.getTaskHoursField().getValue();
//            rec.set('taskMinutes', hours * 60);
            var errs = rec.validate();
            if (errs.isValid() && me.getAllagamentiOsservForm().isValid()) {
                rec.save({
                    success: function(record, operation) {
                        if (typeof record.store === 'undefined') {
                            //me.getAllagamentiOsservStore().add(record);
                            //Ext.log("doSaveAllagamentoOsserv");
                            Ext.Msg.alert('Inserimento effettuato', 'L\'inserimento è stato effettuato con successo.');
                            me.fireEvent('allagamentoinsertcomplete');
                        } else {
                            Ext.Msg.alert('Modifica effettuata', 'I dati sono stati modificati con successo.');
                        }
                        me.getAllagamentiOsservForm().up('window').close();
                        
                        //me.getTaskLogFormFieldset().setTitle('Edit Task Log For ' + record.get('taskName'));
                        //me.getDeleteTaskLogButton().enable();
                    },
                    failure: function(rec, operation) {
                        Ext.Msg.alert('Save Failure', operation.request.scope.reader.jsonData.msg);
                    }
                });
            } else {
                me.getAllagamentiOsservForm().getForm().markInvalid(errs);
                Ext.Msg.alert('Campi richiesti', 'Inserire le informazioni mancanti!');
            }
        }
    },
    
    doReverseGeocoding: function(combo) {
        var me = this;
        Ext.log("doReverseGeocoding");
        
        var geocoder = new google.maps.Geocoder();
        
        combo.getStore().removeAll();

        var lng = me.getAllagamentiOsservCoordXField().getValue();
        var lat = me.getAllagamentiOsservCoordYField().getValue();
        
        var latlng = new google.maps.LatLng(lat, lng);
        geocoder.geocode({'latLng': latlng}, function(results, status) {
            
            if (status === google.maps.GeocoderStatus.OK) {

                Ext.each(results, function(address, index) {
                    var record = Ext.create(AUDB.model.GenericComboModel);
                    record.set('nome', address.formatted_address);
                    combo.getStore().add(record);
                });
                
                combo.setValue(results[0].formatted_address);
                
            } else {
                Ext.Msg.alert("Nessun indirizzo trovato: ", status);
            }
        });

    },
    
    onActionColumnClick: function(column, action, view, rowIndex, colIndex, item, e) {
        var me = this;
        
        Ext.log('onActionColumnClick');
        
        var store = me.getAllagamentiOsservStore();
        var rec = store.getAt(rowIndex);
        me.getAllagamentiOsservList().getSelectionModel().select(rec);
        
        if (action === 'zoomTo') {
            var x = rec.get('coordX4326');
            var y = rec.get('coordY4326');
            
            var lonlat = new OpenLayers.LonLat(x,y).transform('EPSG:4326', 'EPSG:3857');
            
            me.fireEvent('zoomTo', lonlat);
        }
    },

    onFeatureDragged: function(record) {
        Ext.log("onFeatureDragged");
        var me = this;
        
        var grid = me.getAllagamentiOsservList();
        grid.getSelectionModel().select(record);
        
        if(record) {
            me.doEditAllagamentoOsserv(record);
        }
    }
    
});