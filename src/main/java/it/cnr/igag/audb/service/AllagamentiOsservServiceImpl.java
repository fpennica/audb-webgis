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

import it.cnr.igag.audb.dao.AllagamentiOsservDao;
import it.cnr.igag.audb.dao.AllagamentiOsservFontiDao;
import it.cnr.igag.audb.dao.CodiceIstatDao;
import it.cnr.igag.audb.dao.ValoriDurataDao;
import it.cnr.igag.audb.dao.ValoriEstensioneDao;
import it.cnr.igag.audb.dao.ValoriProfonditaDao;
import it.cnr.igag.audb.domain.AllagamentiOsserv;
import it.cnr.igag.audb.domain.Utente;
import it.cnr.igag.audb.vo.Result;
import it.cnr.igag.audb.vo.ResultFactory;
import java.time.Instant;
import java.util.Date;
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
@Service("allagamentiOsservService")
public class AllagamentiOsservServiceImpl extends AbstractService implements AllagamentiOsservService {

    @Autowired
    protected AllagamentiOsservDao allagamentiOsservDao;

    @Autowired
    protected CodiceIstatDao codiceIstatDao;

    @Autowired
    protected ValoriDurataDao valoriDurataDao;

    @Autowired
    protected ValoriEstensioneDao valoriEstensioneDao;

    @Autowired
    protected ValoriProfonditaDao valoriProfonditaDao;
    
    @Autowired
    protected AllagamentiOsservFontiDao fontiDao;

    public AllagamentiOsservServiceImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<AllagamentiOsserv> find(Integer idAllagamentoOsserv, Integer actionUserId) {

        if (isValidUser(actionUserId)) {

            AllagamentiOsserv allagamento = allagamentiOsservDao.find(idAllagamentoOsserv);
            return ResultFactory.getSuccessResult(allagamento);

        } else {

            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<AllagamentiOsserv> store(
            Integer idAllagamentoOsserv,
            String indirizzo,
            Date dataOsserv,
            Date oraOsserv,
            Integer idcodistat,
            Integer idEstensioneApprox,
            Integer idProfonditaApprox,
            Integer idDurataApprox,
            String noteAllagamento,
//            Integer idUtenteIns,
//            Date dataIns,
//            Integer idUtenteAgg,
//            Date dataAgg,
            Integer idFonte,
            Double coordX4326,
            Double coordY4326,
            Integer actionUserId) {

        Utente actionUser = utenteDao.find(actionUserId);

        logger.debug("Coordinate del punto da inserire: {}, {}", coordX4326, coordY4326);
        
        if (!actionUser.isEditor()) {

            return ResultFactory.getFailResult(USER_NOT_EDITOR);

        }

        AllagamentiOsserv allagamento;

        if (idAllagamentoOsserv == null) {
            logger.debug("adding new allagamento");
            allagamento = new AllagamentiOsserv();
            allagamento.setUtenteIns(actionUser);
            allagamento.setDataIns(Date.from(Instant.now()));

        } else {
            logger.debug("updating existing allagamento");
            allagamento = allagamentiOsservDao.find(idAllagamentoOsserv);

            if (allagamento == null) {

                return ResultFactory.getFailResult("Unable to find allagamento instance with ID=" + idAllagamentoOsserv);

            }
        }

        allagamento.setDataOsserv(dataOsserv);
        allagamento.setOraOsserv(oraOsserv);
        allagamento.setIndirizzo(indirizzo);
        if(idcodistat != null)
            allagamento.setCodiceIstat(codiceIstatDao.findReferenceByCodice(idcodistat));
        allagamento.setNoteAllagamento(noteAllagamento);
        allagamento.setUtenteAgg(actionUser);
        allagamento.setDataAgg(Date.from(Instant.now()));
        if(idFonte != null)
            allagamento.setFonte(fontiDao.findReferenceById(idFonte));
        allagamento.setCoordX4326(coordX4326);
        allagamento.setCoordY4326(coordY4326);
        if(idDurataApprox != null)
            allagamento.setDurataApprox(valoriDurataDao.findReferenceById(idDurataApprox));
        if(idEstensioneApprox != null)
            allagamento.setEstensioneApprox(valoriEstensioneDao.findReferenceById(idEstensioneApprox));
        if(idProfonditaApprox != null)
            allagamento.setProfonditaApprox(valoriProfonditaDao.findReferenceById(idProfonditaApprox));

        if (allagamento.getId() == null) {

            allagamentiOsservDao.persist(allagamento);

        } else {

            allagamento = allagamentiOsservDao.merge(allagamento);

        }

        return ResultFactory.getSuccessResult(allagamento);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<AllagamentiOsserv> remove(Integer idAllagamentoOsserv, Integer actionUserId) {
        
        Utente actionUser = utenteDao.find(actionUserId);
        
        if (!actionUser.isEditor()) {

            return ResultFactory.getFailResult(USER_NOT_EDITOR);

        }
        
        if(idAllagamentoOsserv == null){

            return ResultFactory.getFailResult("Unable to remove null AllagamentoOsserv");

        } 

        AllagamentiOsserv a = allagamentiOsservDao.find(idAllagamentoOsserv);
        
        if(a == null) {

            return ResultFactory.getFailResult("Unable to load AllagamentiOsserv for removal with id=" + idAllagamentoOsserv);

        } else {
            allagamentiOsservDao.remove(a);
            String msg = "AllagamentiOsserv with id " + a.getId() + " was deleted by " + actionUser.getUsername();
            logger.info(msg);
            return ResultFactory.getSuccessResultMsg(msg);
            
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<AllagamentiOsserv>> findAll(Integer actionUserId) {
        
        if (isValidUser(actionUserId)) {
            return ResultFactory.getSuccessResult(allagamentiOsservDao.findAll());
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<AllagamentiOsserv>> findByProject(Integer idprogetto, Integer actionUserId) {
    
        if (isValidUser(actionUserId)) {
            return ResultFactory.getSuccessResult(allagamentiOsservDao.findByProject(idprogetto));
        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }
}
