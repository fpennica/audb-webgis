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

import it.cnr.igag.audb.domain.CodiceIstat;
import it.cnr.igag.audb.domain.Utente;
import it.cnr.igag.audb.service.CodiceIstatService;
import it.cnr.igag.audb.vo.Result;
import static it.cnr.igag.audb.web.AbstractHandler.toJsonString;
import static it.cnr.igag.audb.web.SecurityHelper.getSessionUser;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
@RequestMapping("/codiceistat")
public class CodiceIstatHandler extends AbstractHandler {
    
    @Autowired
    protected CodiceIstatService codiceIstatService;
    
    @RequestMapping(value = "/findByRegioneProvinciaComune", method = RequestMethod.GET, produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String findByRegioneProvinciaComune(
            @RequestParam(value = "regione", required = true) String regione,
            @RequestParam(value = "provincia", required = true) String provincia,
            @RequestParam(value = "comune", required = true) String comune,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        Result<CodiceIstat> ar = codiceIstatService.findByRegioneProvinciaComune(regione, provincia, comune, sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/findAllRegioni", method = RequestMethod.GET, produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String findAllRegioni(HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        Result<List<String>> ar = codiceIstatService.findAllRegioni(sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessDataList(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/findAllProvinceByRegione", method = RequestMethod.GET, produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String findAllProvinceByRegione(
            @RequestParam(value = "regione", required = true) String regione,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        Result<List<String>> ar = codiceIstatService.findAllProvinceByRegione(regione, sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessDataList(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/findAllComuniByProvincia", method = RequestMethod.GET, produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String findAllComuniByProvincia(
            @RequestParam(value = "provincia", required = true) String provincia,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        logger.debug(provincia);
        
        Result<List<String>> ar = codiceIstatService.findAllComuniByProvincia(provincia, sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessDataList(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    private String getJsonSuccessDataList(List<String> list) {

        final JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("success", true);
        
        JsonArrayBuilder listBuilder = Json.createArrayBuilder();
        for(String l : list) {
            JsonObjectBuilder itemBuilder = Json.createObjectBuilder();
            itemBuilder.add("nome", l);
            listBuilder.add(itemBuilder);
        }
        
        builder.add("data", listBuilder);

        return toJsonString(builder.build());

    }
    
}
