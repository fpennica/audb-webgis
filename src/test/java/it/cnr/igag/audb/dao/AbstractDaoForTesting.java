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
package it.cnr.igag.audb.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@ContextConfiguration("/testingContext.xml")
public abstract class AbstractDaoForTesting extends AbstractTransactionalJUnit4SpringContextTests {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired(required = true)
    protected AllagamentiFotoDao allagamentiFotoDao;
    @Autowired(required = true)
    protected AllagamentiOsservDao allagamentiOsservDao;
    @Autowired(required = true)
    protected AllagamentiOsservFontiDao allagamentiOsservFontiDao;
    @Autowired(required = true)
    protected MappeDao mappeDao;
    @Autowired(required = true)
    protected ProgettoDao progettoDao;
    @Autowired(required = true)
    protected UtenteDao utenteDao;
    @Autowired(required = true)
    protected ValoriEstensioneDao valoriEstensioneDao;
    @Autowired(required = true)
    protected ValoriProfonditaDao valoriProfonditaDao;
    @Autowired(required = true)
    protected ValoriDurataDao valoriDurataDao;
    @Autowired(required = true)
    protected UtenteProgettoDao utenteProgettoDao;
    @Autowired(required = true)
    protected CodiceIstatDao codiceIstatDao;
}
