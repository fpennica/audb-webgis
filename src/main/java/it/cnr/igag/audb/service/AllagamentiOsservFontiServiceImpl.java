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

import it.cnr.igag.audb.dao.AllagamentiOsservFontiDao;
import it.cnr.igag.audb.domain.AllagamentiOsservFonti;
import it.cnr.igag.audb.domain.Utente;
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
@Service("allagamentiOsservFontiService")
public class AllagamentiOsservFontiServiceImpl extends AbstractService implements AllagamentiOsservFontiService {
    
    @Autowired
    protected AllagamentiOsservFontiDao allagamentiOsservFontiDao;

    public AllagamentiOsservFontiServiceImpl() {
        super();
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<AllagamentiOsservFonti> store(
            Integer idFonte,
            String nomeFonte,
            String noteFonte,
            Integer actionUserId) {
        
        Utente actionUser = utenteDao.find(actionUserId);

        if (!actionUser.isEditor()) {

            return ResultFactory.getFailResult(USER_NOT_EDITOR);

        }
        
        //ETC ETC
        
        return null;
        
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<AllagamentiOsservFonti> remove(Integer idFonte, Integer actionUserId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<AllagamentiOsservFonti> find(Integer idFonte, Integer actionUserId) {
        if (isValidUser(actionUserId)) {

            AllagamentiOsservFonti fonte = allagamentiOsservFontiDao.find(idFonte);
            return ResultFactory.getSuccessResult(fonte);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<AllagamentiOsservFonti>> findAll(Integer actionUserId) {
        if (isValidUser(actionUserId)) {
            return ResultFactory.getSuccessResult(allagamentiOsservFontiDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
    
}
