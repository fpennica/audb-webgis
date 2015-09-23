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

import it.cnr.igag.audb.domain.CodiceIstat;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public class CodiceIstatDaoTest extends AbstractDaoForTesting {

    public CodiceIstatDaoTest() {
    }
    
    @Test
    public void testFindAllRegioni() throws Exception {

        logger.debug("\nSTARTED testFindAllRegioni()\n");
        List<String> allItems = codiceIstatDao.findAllRegioni();

        assertTrue(allItems.size() > 0);
        
        for(String regione : allItems) {
            logger.debug(regione);
        }
        
        logger.debug("\nFINISHED testFindAllRegioni()\n");
    }
    
    @Test
    public void testFindAllProvinceByRegione() throws Exception {

        logger.debug("\nSTARTED testFindAllProvinceByRegione()\n");
        List<String> allItems = codiceIstatDao.findAllProvinceByRegione("PUGLIA");

        assertTrue(allItems.size() > 0);
        
        for(String provincia : allItems) {
            logger.debug(provincia);
        }
        
        logger.debug("\nFINISHED testFindAllProvinceByRegione()\n");
    }
    
    @Test
    public void testFindAllComuniByProvincia() throws Exception {

        logger.debug("\nSTARTED testFindAllComuniByProvincia()\n");
        List<String> allItems = codiceIstatDao.findAllComuniByProvincia("Brindisi");

        assertTrue(allItems.size() > 0);
        
        for(String comune : allItems) {
            logger.debug(comune);
        }
        
        logger.debug("\nFINISHED testFindAllComuniByProvincia()\n");
    }
    
    @Test
    public void testFindByRegioneProvinciaComune() throws Exception {

        logger.debug("\nSTARTED testFindByRegioneProvinciaComune()\n");
        CodiceIstat codiceIstat = codiceIstatDao.findByRegioneProvinciaComune("LAZIO", "Roma", "Ariccia");

        assertTrue(codiceIstat != null);
        
        logger.debug("Codice istat di {}: {}", codiceIstat.getComune(), codiceIstat.getIdcodistat());
        
        logger.debug("\nFINISHED testFindByRegioneProvinciaComune()\n");
    }
    
}
