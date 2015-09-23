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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public class SecurityHelper {

    static final String SESSION_ATTRIB_USER = "sessionuser";

    public static Utente getSessionUser(HttpServletRequest request) {

        Utente user = null;

        HttpSession session = request.getSession(true);

        Object obj = session.getAttribute(SESSION_ATTRIB_USER);

        if (obj != null && obj instanceof Utente) {
            user = (Utente) obj;
        }

        return user;

    }
    
    public static void setSessionUser(HttpServletRequest request, Utente user) {

        HttpSession session = request.getSession(true);
        session.setAttribute(SESSION_ATTRIB_USER, user);      
        
    }
}
