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
package it.cnr.igag.audb.service;

import it.cnr.igag.audb.dao.ProgettoDao;
import it.cnr.igag.audb.domain.Progetto;
import it.cnr.igag.audb.vo.Result;
import it.cnr.igag.audb.vo.ResultFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@Transactional
@Service("progettoService")
public class ProgettoServiceImpl extends AbstractService implements ProgettoService {
    
    @Autowired
    protected ProgettoDao progettoDao;

    public ProgettoServiceImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<Progetto>> findByModulo(String modulo, Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {

            List<Progetto> progetti = progettoDao.findByModulo(modulo);
            return ResultFactory.getSuccessResult(progetti);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Progetto> find(Integer idprogetto, Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {

            Progetto p = progettoDao.find(idprogetto);
            return ResultFactory.getSuccessResult(p);

        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
}
