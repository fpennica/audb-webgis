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
package it.cnr.igag.audb.web;

import it.cnr.igag.audb.domain.AllagamentiOsserv;
import it.cnr.igag.audb.domain.JsonItem;
import it.cnr.igag.audb.domain.Utente;
import it.cnr.igag.audb.service.AllagamentiOsservService;
import it.cnr.igag.audb.vo.Result;
import static it.cnr.igag.audb.web.AbstractHandler.getJsonErrorMsg;
import static it.cnr.igag.audb.web.AbstractHandler.getJsonSuccessData;
import static it.cnr.igag.audb.web.AbstractHandler.getJsonSuccessMsg;
import static it.cnr.igag.audb.web.AbstractHandler.toJsonString;
import static it.cnr.igag.audb.web.SecurityHelper.getSessionUser;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author francesco
 */
@Controller
@RequestMapping("/allagamentiosserv")
public class AllagamentiOsservHandler extends AbstractHandler {
    
    //static final SimpleDateFormat DATE_FORMAT_yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    protected AllagamentiOsservService allagamentiOsservService;
    
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "idAllagamentoOsserv", required = true) Integer idAllagamentoOsserv,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);
        
        Result<AllagamentiOsserv> ar = allagamentiOsservService.find(idAllagamentoOsserv, sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/store", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String store(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) throws ParseException {

        Utente sessionUser = getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);
        
        String dataOsservString = jsonObj.getString("dataOsserv", "");
        Date dataOsserv = null;
        if(dataOsservString != null && !dataOsservString.isEmpty())
            dataOsserv = DATE_FORMAT.parse(dataOsservString);
        String oraOsservString = jsonObj.getString("oraOsserv", "");
        Date oraOsserv = null;
        if(oraOsservString != null && !oraOsservString.isEmpty())
            oraOsserv = TIME_FORMAT.parse(oraOsservString);
        
        String noteAllagamento = jsonObj.getString("noteAllagamento");
        if(noteAllagamento.trim().isEmpty())
            noteAllagamento = null;
        
//        String dataIns = jsonObj.getString("dataIns");
//        String dataAgg = jsonObj.getString("dataAgg");

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
        Result<AllagamentiOsserv> ar = allagamentiOsservService.store(
                getIntegerValue(jsonObj.get("idAllagamentoOsserv")),
                jsonObj.getString("indirizzo"),
                dataOsserv,
                oraOsserv,
                getIntegerValue(jsonObj.get("idcodistat")),
                getIntegerValue(jsonObj.get("idEstensione")),
                getIntegerValue(jsonObj.get("idProfondita")),
                getIntegerValue(jsonObj.get("idDurata")),
                noteAllagamento,
                //getIntegerValue(jsonObj.get("utenteIns")),
                //TIMESTAMP_FORMAT.parse(jsonObj.getString("dataIns")),
                //getIntegerValue(jsonObj.get("utenteAgg")),
                //TIMESTAMP_FORMAT.parse(jsonObj.getString("dataAgg")),
                getIntegerValue(jsonObj.get("idFonte")),
                jsonObj.getJsonNumber("coordX4326").doubleValue(),
                jsonObj.getJsonNumber("coordY4326").doubleValue(),
                sessionUser.getIdutente());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<AllagamentiOsserv> ar = allagamentiOsservService.remove(getIntegerValue(jsonObj.get("idAllagamentoOsserv")), sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessMsg(ar.getMsg());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findAll(HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        Result<List<AllagamentiOsserv>> ar = allagamentiOsservService.findAll(sessionUser.getId());

        if (ar.isSuccess()) {

            return getGeoJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/findByProject", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String findByProject(HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);
        
        logger.debug("Progetto associato all'utente corrente ({}): {}", sessionUser.getUsername(), sessionUser.getProgettoDefaultAudb().getId());

        Result<List<AllagamentiOsserv>> ar = allagamentiOsservService.findByProject(sessionUser.getProgettoDefaultAudb().getId(), sessionUser.getId());

        if (ar.isSuccess()) {

            return getGeoJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    private String getGeoJsonSuccessData(List<? extends JsonItem> results) {

        final JsonObjectBuilder featureCollectionBuilder = Json.createObjectBuilder();
        featureCollectionBuilder.add("success", true);
        
        featureCollectionBuilder.add("type", "FeatureCollection");

        JsonArrayBuilder featureArrayBuilder = Json.createArrayBuilder();

        for (JsonItem ji : results) {
            
            JsonObjectBuilder featureBuilder = Json.createObjectBuilder();
            featureBuilder.add("type", "Feature");
            
            JsonArrayBuilder coordinatesArrayBuilder = Json.createArrayBuilder();
            JsonObjectBuilder geometryBuilder = Json.createObjectBuilder();

            AllagamentiOsserv a = (AllagamentiOsserv) ji;
            Double lon = a.getCoordX4326();
            Double lat = a.getCoordY4326();
            
            coordinatesArrayBuilder.add(lon);
            coordinatesArrayBuilder.add(lat);
            
            geometryBuilder.add("type", "Point");
            geometryBuilder.add("coordinates", coordinatesArrayBuilder);
            
            featureBuilder.add("geometry", geometryBuilder);
            
            featureBuilder.add("properties", ji.toJson());
            
            featureArrayBuilder.add(featureBuilder);
        }
        
        featureCollectionBuilder.add("features", featureArrayBuilder);

        return toJsonString(featureCollectionBuilder.build());
    }
    
}
