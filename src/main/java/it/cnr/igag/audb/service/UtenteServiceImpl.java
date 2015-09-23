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
@Service("utenteService")
public class UtenteServiceImpl extends AbstractService implements UtenteService {

//    @Autowired
//    protected UtenteDao utenteDao;
    @Autowired
    protected ProgettoDao progettoDao;

    public UtenteServiceImpl() {
        super();
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Utente> store(
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
            Integer idprogetto, //sarà inserito in idprogettoaudb
            Integer actionUserId) {

        Utente actionUser = utenteDao.find(actionUserId);
        
        if (!actionUser.getId().equals(idutente) && !actionUser.isAdmin()) {

            return ResultFactory.getFailResult(USER_NOT_ADMIN);

        }

        if (username == null || username.trim().isEmpty()) {

            return ResultFactory.getFailResult("Unable to store a user without a valid non empty username");

        }

        //preparo i dati forniti e faccio i dovuti test
        Utente utente;
        boolean doInsert = false;
        username = username.trim();
        email = email.trim();
        
        if (idutente == null) {
            if(pwd == null || pwd.length() == 0) {
                return ResultFactory.getFailResult("Unable to store a user without a valid non empty password");
            }
            
            logger.debug("adding new user");
            // we are adding a NEW utente
            doInsert = true;
            utente = new Utente();
            
            // test if the username is already in the system
            Utente testByUsername = utenteDao.findByUsername(username);

            if(testByUsername != null) {
                return ResultFactory.getFailResult("Unable to add new user: the username is already in use");
            }
            
        } else {
            // user must be valid if idutente is supplied
            utente = utenteDao.find(idutente);
            
            if(utente == null) {
                return ResultFactory.getFailResult("Unable to find utente instance with ID=" + idutente);
            }
        }
        
        //controllo che l'idprogetto default corrisponda ad un progetto valido
        //l'id verrà inserito in idprogettoaudb
        Progetto progettoDefaultAudb;
        if(idprogetto != null) {
            // the project must be valid if idProject is supplied
            progettoDefaultAudb = progettoDao.find(idprogetto);
            utente.setProgettoDefaultAudb(progettoDefaultAudb);
        } else {
            return ResultFactory.getFailResult("Unable to add new utente without a valid progetto [idprogettoaudb=" + idprogetto + "]");
        }
        
        // a questo punto setto l'entity da inserire o aggiornare
        logger.debug("updating existing user");
        utente.setUsername(username);
        if(pwd != null && pwd.length() > 0) {
            utente.setPwd(pwd);
        }
        utente.setNome(nome);
        utente.setCognome(cognome);
        utente.setEnte(ente);
        utente.setEmail(email);
        utente.setAmministratore(amministratore);
        utente.setDownloadPdf(downloadPdf);
        utente.setEditor(editor);
        utente.setAttivo(attivo);
        utente.setWebbdgt(webbdgt);
        utente.setAudb(audb);

        if(doInsert) {

            utenteDao.persist(utente);

        } else {

            utente = utenteDao.merge(utente);
            
        }

        return ResultFactory.getSuccessResult(utente);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Utente> remove(Integer idutente, Integer actionUserId) {
        Utente actionUser = utenteDao.find(actionUserId);
        
        if (!actionUser.isAdmin()) {

            return ResultFactory.getFailResult(USER_NOT_ADMIN);

        }
        
        if(actionUserId.equals(idutente)){

            return ResultFactory.getFailResult("It is not allowed to delete yourself!");

        }
        
        if(idutente == null){

            return ResultFactory.getFailResult("Unable to remove null User");

        } 

        Utente user = utenteDao.find(idutente);
        
        if(user == null) {

            return ResultFactory.getFailResult("Unable to load User for removal with id=" + idutente);

        } else {
//            utenteDao.remove(user);
//            String msg = "User " + user.getUsername() + " was deleted by " + actionUser.getUsername();
//            logger.info(msg);
//            return ResultFactory.getSuccessResultMsg(msg);
            
            // per come è strutturato webbdgt non si possono cancellare del tutto gli utenti!!!
            user.setAttivo(false);
            user = utenteDao.merge(user);
            
            return ResultFactory.getSuccessResultMsg("User " + user.getUsername() + " has been deactivated");

        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Utente> find(String username, Integer actionUserId) {

        if (isValidUser(actionUserId)) {

            Utente user = utenteDao.findByUsername(username);
            return ResultFactory.getSuccessResult(user);

        } else {
            return ResultFactory.getFailResult(USER_INVALID);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<List<Utente>> findAll(Integer actionUserId) {
        if(isValidUser(actionUserId)){

            return ResultFactory.getSuccessResult(utenteDao.findAll());

        } else {

            return ResultFactory.getFailResult(USER_INVALID);

        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Result<Utente> findByUsernamePassword(String username, String password) {
        Utente utente = utenteDao.findByUsernamePwd(username, password);

        if (utente == null) {

            return ResultFactory.getFailResult("Unable to verify user/password combination!");

        } else {

            return ResultFactory.getSuccessResult(utente);
        }

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Result<Utente> updateProgettoDefaultAudb(Integer idprogetto, Integer actionUserId) {
        Utente actionUser = utenteDao.find(actionUserId);
        
        Progetto p = progettoDao.find(idprogetto);
        
        if(p == null) {

            return ResultFactory.getFailResult("Unable to load Progetto with id=" + idprogetto);

        } else {

            actionUser.setProgettoDefaultAudb(p);
            actionUser = utenteDao.merge(actionUser);
            
            return ResultFactory.getSuccessResult(actionUser);

        }
        
    }

}
