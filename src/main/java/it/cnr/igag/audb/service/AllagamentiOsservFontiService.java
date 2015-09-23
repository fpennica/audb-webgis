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

import it.cnr.igag.audb.domain.AllagamentiOsservFonti;
import it.cnr.igag.audb.vo.Result;
import java.util.List;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public interface AllagamentiOsservFontiService {
    public Result<AllagamentiOsservFonti> store(
            Integer idFonte,
            String nomeFonte,
            String noteFonte,
            Integer actionUserId
    );

    public Result<AllagamentiOsservFonti> remove(Integer idFonte, Integer actionUserId);

    public Result<AllagamentiOsservFonti> find(Integer idFonte, Integer actionUserId);

    public Result<List<AllagamentiOsservFonti>> findAll(Integer actionUserId);
}
