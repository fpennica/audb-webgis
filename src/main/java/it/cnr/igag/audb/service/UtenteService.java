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

import it.cnr.igag.audb.domain.Utente;
import it.cnr.igag.audb.vo.Result;
import java.util.List;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public interface UtenteService {
    
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
        Integer actionUserId);

    public Result<Utente> remove(Integer idutente, Integer actionUserId);
    public Result<Utente> find(String username, Integer actionUserId);
    public Result<List<Utente>> findAll(Integer actionUserId);
    public Result<Utente> findByUsernamePassword(String username, String password);

    public Result<Utente> updateProgettoDefaultAudb(Integer idprogetto, Integer actionUserId);
}
