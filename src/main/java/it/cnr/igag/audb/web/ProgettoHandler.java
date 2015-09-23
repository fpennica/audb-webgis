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
import it.cnr.igag.audb.service.ProgettoService;
import it.cnr.igag.audb.vo.Result;
import static it.cnr.igag.audb.web.SecurityHelper.getSessionUser;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/progetto")
public class ProgettoHandler extends AbstractHandler {
    
    @Autowired
    protected ProgettoService progettoService;
    
    @RequestMapping(value = "/findByModulo", method = RequestMethod.GET, produces = {"application/json; charset=utf8"})
    @ResponseBody
    public String findByModulo(
            @RequestParam(value = "modulo", required = true) String modulo,
            HttpServletRequest request) {

        Utente sessionUser = getSessionUser(request);

        Result<List<Progetto>> ar = progettoService.findByModulo(modulo, sessionUser.getId());

        if (ar.isSuccess()) {

            return getJsonSuccessData(ar.getData());

        } else {

            return getJsonErrorMsg(ar.getMsg());

        }
    }
    
}
