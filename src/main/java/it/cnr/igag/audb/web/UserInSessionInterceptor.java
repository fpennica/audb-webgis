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
import static it.cnr.igag.audb.web.SecurityHelper.getSessionUser;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public class UserInSessionInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        logger.debug("calling preHandle with url=" + request.getRequestURI());

        Utente sessionUser = getSessionUser(request);

        if (sessionUser == null) {
            String json = "{\"success\":false,\"msg\":\"A valid user is not logged on!\"}";
            response.getOutputStream().write(json.getBytes());
            return false;
        } else {
            return true;
        }
    }
}
