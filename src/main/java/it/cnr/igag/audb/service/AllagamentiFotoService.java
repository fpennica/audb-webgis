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

import it.cnr.igag.audb.domain.AllagamentiFoto;
import it.cnr.igag.audb.vo.Result;
import java.util.List;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public interface AllagamentiFotoService {
    
    public Result<AllagamentiFoto> find(Integer idFoto, Integer actionUserId);

    public Result<List<AllagamentiFoto>> findAll(Integer actionUserId);
    
    public Result<List<AllagamentiFoto>> findByIdAllagamentoOsserv(Integer idAllagamentoOsserv, Integer actionUserId);
    
    public Result<AllagamentiFoto> store(
        Integer idFoto,
        //String percorso, // il percorso viene assegnato lato server
        Integer idAllagamentoOsserv,
        Integer actionUserId);

    public Result<AllagamentiFoto> remove(Integer idFoto, Integer actionUserId);
    
}
