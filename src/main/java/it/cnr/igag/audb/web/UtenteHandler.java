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

package it.cnr.igag.audb.web;

import it.cnr.igag.audb.domain.Progetto;
import it.cnr.igag.audb.domain.Utente;
import it.cnr.igag.audb.service.UtenteService;
import it.cnr.igag.audb.vo.Result;
import static it.cnr.igag.audb.web.SecurityHelper.SESSION_ATTRIB_USER;
import static it.cnr.igag.audb.web.SecurityHelper.getSessionUser;
import static it.cnr.igag.audb.web.SecurityHelper.setSessionUser;
import java.util.ArrayList;
import java.util.List;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/utente")
public class UtenteHandler extends AbstractHandler {

    @Autowired
    protected UtenteService utenteService;
    
    @RequestMapping(value = "/find", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String find(
            @RequestParam(value = "username", required = true) String username,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        Result<Utente> ar = utenteService.find(username, sessionUser.getId());

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
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);
        
        /*
            Integer idutente,
            String nome,
            String cognome,
            String ente,
            String email,
            String username,
            String pwd,
            boolean amministratore,
            boolean downloadPdf,
            boolean editor,
            boolean attivo,
            boolean webbdgt,
            boolean audb,
            Integer idprogetto,
            Integer idprogettoaudb,
            Integer actionUserId
        */
        Result<Utente> ar = utenteService.store(
                getIntegerValue(jsonObj.get("idutente")),
                jsonObj.getString("nome"),
                jsonObj.getString("cognome"),
                jsonObj.getString("ente"),
                jsonObj.getString("email"),
                jsonObj.getString("username"),
                jsonObj.getString("pwd"),
                jsonObj.getBoolean("amministratore"),
                jsonObj.getBoolean("downloadPdf"),
                jsonObj.getBoolean("editor"),
                jsonObj.getBoolean("attivo"),
                jsonObj.getBoolean("webbdgt"),
                jsonObj.getBoolean("audb"),
                getIntegerValue(jsonObj.get("idprogetto")), //sarà inserito in idprogettoaudb
                //getIntegerValue(jsonObj.get("idprogettoaudb")),
                sessionUser.getIdutente());

        if (ar.isSuccess()) {
            Utente user = ar.getData();
            if(user.equals(sessionUser)) {
                
                setSessionUser(request, user);
            }
            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
    @RequestMapping(value = "/updateProgettoDefaultAudb", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String updateProgettoDefaultAudb(
            @RequestParam(value = "idprogetto", required = true) Integer idprogetto,
            HttpServletRequest request) {
        
        Utente sessionUser = getSessionUser(request);
        
        Result<Utente> ar = utenteService.updateProgettoDefaultAudb(idprogetto, sessionUser.getId());
        
        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
        
    }
    
    @RequestMapping(value = "/getProgettiUtente", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String getProgettiUtente(HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        List<Progetto> progetti = sessionUser.getProgetti();
        List<Progetto> progettiAudb = new ArrayList();
        for(Progetto p : progetti) {
            if(p.getModulo() != null && p.getModulo().equals("audb"))
                progettiAudb.add(p);
        }       
        
        return getJsonSuccessData(progettiAudb);

    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST, produces = {"application/json"})
    @ResponseBody
    public String remove(
            @RequestParam(value = "data", required = true) String jsonData,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        JsonObject jsonObj = parseJsonObject(jsonData);

        Result<Utente> ar = utenteService.remove(getIntegerValue(jsonObj.get("idutente")), sessionUser.getId());

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

        Result<List<Utente>> ar = utenteService.findAll(sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
}
