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

import it.cnr.igag.audb.dao.AllagamentiFotoDao;
import it.cnr.igag.audb.domain.AllagamentiFoto;
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
@Service("allagamentiFotoService")
public class AllagamentiFotoServiceImpl extends AbstractService implements AllagamentiFotoService {

    @Autowired
    protected AllagamentiFotoDao allagamentiFotoDao;
    
    public AllagamentiFotoServiceImpl() {
        super();
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<AllagamentiFoto> find(Integer idFoto, Integer actionUserId) {
        if (isValidUser(actionUserId)) {

            AllagamentiFoto durata = allagamentiFotoDao.find(idFoto);
            return ResultFactory.getSuccessResult(durata);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<AllagamentiFoto>> findAll(Integer actionUserId) {
        if (isValidUser(actionUserId)) {
            return ResultFactory.getSuccessResult(allagamentiFotoDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<AllagamentiFoto>> findByIdAllagamentoOsserv(Integer idAllagamentoOsserv, Integer actionUserId) {
        if (isValidUser(actionUserId)) {
            return ResultFactory.getSuccessResult(allagamentiFotoDao.findByIdAllagamentoOsserv(idAllagamentoOsserv));
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<AllagamentiFoto> store(Integer idFoto, Integer idAllagamentoOsserv, Integer actionUserId) {
        
        Utente actionUser = utenteDao.find(actionUserId);

        if (!actionUser.isEditor()) {

            return ResultFactory.getFailResult(USER_NOT_EDITOR);

        }
        
        if (idAllagamentoOsserv == null) {

            return ResultFactory.getFailResult("Unable to store a foto without an associated AllagamentoOsserv");

        }
        
        //TODO ETCETC
        
        return null;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<AllagamentiFoto> remove(Integer idFoto, Integer actionUserId) {
        Utente actionUser = utenteDao.find(actionUserId);
        
        if (!actionUser.isEditor()) {

            return ResultFactory.getFailResult(USER_NOT_EDITOR);

        }
        
        if(idFoto == null){

            return ResultFactory.getFailResult("Unable to remove null foto");

        } 

        AllagamentiFoto foto = allagamentiFotoDao.find(idFoto);
        
        if(foto == null) {

            return ResultFactory.getFailResult("Unable to load AllagamentiFoto for removal with id=" + idFoto);

        } else {
            allagamentiFotoDao.remove(foto);
            String msg = "AllagamentiFoto with id " + foto.getId() + " was deleted by " + actionUser.getUsername();
            logger.info(msg);
            return ResultFactory.getSuccessResultMsg(msg);
            
        }
    }
    
}
