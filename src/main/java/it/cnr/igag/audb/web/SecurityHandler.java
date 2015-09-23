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

import it.cnr.igag.audb.domain.Utente;
import it.cnr.igag.audb.service.UtenteService;
import it.cnr.igag.audb.vo.Result;
import static it.cnr.igag.audb.web.SecurityHelper.SESSION_ATTRIB_USER;
import static it.cnr.igag.audb.web.SecurityHelper.setSessionUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@Controller
@RequestMapping("/security")
public class SecurityHandler extends AbstractHandler {
    
    @Autowired
    protected UtenteService utenteService;

    @RequestMapping(value = "/logon", method = RequestMethod.POST, produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String logon(
            @RequestParam(value = "username", required = true) String username,
            @RequestParam(value = "pwd", required = true) String pwd,
            HttpServletRequest request) {

        Result<Utente> ar = utenteService.findByUsernamePassword(username, pwd);

        if (ar.isSuccess()) {

            Utente user = ar.getData();
            
            if(!user.getAttivo()) {
                return getJsonErrorMsg("L'utente non è attivo.");
            }
            
            if(!user.getAudb()) {
                return getJsonErrorMsg("L'utente non possiede i diritti di accesso al modulo AUDB.");
            }
            
//            HttpSession session = request.getSession(true);
//            session.setAttribute(SESSION_ATTRIB_USER, user);        
            setSessionUser(request, user);

            return getJsonSuccessData(user);
            
        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }

    @RequestMapping(value = "/logout", produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        session.removeAttribute(SESSION_ATTRIB_USER);
        return getJsonSuccessMsg("User logged out...");
    }
    
    @RequestMapping(value = "/checkSession", produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String checkSession(HttpServletRequest request) {

        HttpSession session = request.getSession();
        if(session == null || session.getAttribute(SESSION_ATTRIB_USER) == null) {
            return getJsonErrorMsg("No active session");
        }
        Utente currentUser = (Utente) session.getAttribute(SESSION_ATTRIB_USER);            
        return getJsonSuccessData(currentUser);
        //return getJsonSuccessMsg("Session is active");
    }
}
