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
package it.cnr.igag.audb.service;

import it.cnr.igag.audb.dao.CodiceIstatDao;
import it.cnr.igag.audb.domain.CodiceIstat;
import it.cnr.igag.audb.vo.Result;
import it.cnr.igag.audb.vo.ResultFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author francesco
 */
@Transactional
@Service("codiceIstatService")
public class CodiceIstatServiceImpl extends AbstractService implements CodiceIstatService {

    @Autowired
    protected CodiceIstatDao codiceIstatDao;

    public CodiceIstatServiceImpl() {
        super();
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<CodiceIstat> find(Integer idcodistat, Integer actionUserId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<String>> findAllRegioni(Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {

            List<String> regioni = codiceIstatDao.findAllRegioni();
            return ResultFactory.getSuccessResult(regioni);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<String>> findAllProvinceByRegione(String regione, Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {

            List<String> province = codiceIstatDao.findAllProvinceByRegione(regione);
            return ResultFactory.getSuccessResult(province);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<String>> findAllComuniByProvincia(String provincia, Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {

            List<String> comuni = codiceIstatDao.findAllComuniByProvincia(provincia);
            return ResultFactory.getSuccessResult(comuni);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<CodiceIstat> findByRegioneProvinciaComune(String regione, String provincia, String comune, Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {

            CodiceIstat codiceIstat = codiceIstatDao.findByRegioneProvinciaComune(regione, provincia, comune);
            return ResultFactory.getSuccessResult(codiceIstat);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
    
}
