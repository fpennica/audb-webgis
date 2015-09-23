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

import it.cnr.igag.audb.domain.CodiceIstat;
import it.cnr.igag.audb.vo.Result;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author francesco
 */
public class CodiceIstatServiceTest extends AbstractServiceForTesting {
    
    protected final int TEST_USER_ID = 3;
    protected final String TEST_REGIONE = "PUGLIA";
    protected final String TEST_PROVINCIA = "Brindisi";
    protected final String TEST_COMUNE = "Brindisi";
    
    @Autowired(required = true)
    protected CodiceIstatService codiceIstatService;    
    
    @Test
    public void testFindAllRegioni() throws Exception {
        
        logger.debug("\nSTARTED testFindAllRegioni()\n");
        Result<List<String>> allItems = codiceIstatService.findAllRegioni(TEST_USER_ID);
        
        assertTrue(allItems.getData().size() > 0);

        for(String regione : allItems.getData()) {
            logger.debug(regione);
        }
        
        logger.debug("\nFINISHED testFindAllRegioni()\n");
    }
    
    @Test
    public void testFindAllProvinceByRegione() throws Exception {
        
        logger.debug("\nSTARTED testFindAllProvinceByRegione()\n");
        Result<List<String>> allItems = codiceIstatService.findAllProvinceByRegione(TEST_REGIONE, TEST_USER_ID);
        
        assertTrue(allItems.getData().size() > 0);

        for(String provincia : allItems.getData()) {
            logger.debug(provincia);
        }
        
        logger.debug("\nFINISHED testFindAllProvinceByRegione()\n");
    }
    
    @Test
    public void testFindAllComuniByProvincia() throws Exception {
        
        logger.debug("\nSTARTED testFindAllComuniByProvincia()\n");
        Result<List<String>> allItems = codiceIstatService.findAllComuniByProvincia(TEST_PROVINCIA, TEST_USER_ID);
        
        assertTrue(allItems.getData().size() > 0);

        for(String comune : allItems.getData()) {
            logger.debug(comune);
        }
        
        logger.debug("\nFINISHED testFindAllComuniByProvincia()\n");
    }
    
    @Test
    public void testFindByRegioneProvinciaComune() throws Exception {
        
        logger.debug("\nSTARTED testFindByRegioneProvinciaComune()\n");
        Result<CodiceIstat> codiceIstat = codiceIstatService.findByRegioneProvinciaComune(TEST_REGIONE, TEST_PROVINCIA, TEST_COMUNE, TEST_USER_ID);
        
        assertTrue(codiceIstat.getData().getComune().equals(TEST_COMUNE));

        logger.debug("\nFINISHED testFindByRegioneProvinciaComune()\n");
    }
    
}
