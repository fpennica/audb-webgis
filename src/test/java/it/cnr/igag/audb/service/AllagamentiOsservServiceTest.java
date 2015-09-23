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
import it.cnr.igag.audb.dao.AllagamentiOsservFontiDao;
import it.cnr.igag.audb.dao.CodiceIstatDao;
import it.cnr.igag.audb.dao.UtenteDao;
import it.cnr.igag.audb.dao.ValoriDurataDao;
import it.cnr.igag.audb.dao.ValoriEstensioneDao;
import it.cnr.igag.audb.dao.ValoriProfonditaDao;
import it.cnr.igag.audb.domain.AllagamentiOsserv;
import it.cnr.igag.audb.vo.Result;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public class AllagamentiOsservServiceTest extends AbstractServiceForTesting {
    
    protected final int TEST_USER_ID = 3;
    @Autowired(required = true)
    protected AllagamentiOsservService allagamentiOsservService;    
    
    @Autowired(required = true)
    protected AllagamentiFotoDao allagamentiFotoDao;
    
    @Autowired(required = true)
    protected UtenteDao utenteDao;
    
    @Autowired(required = true)
    protected AllagamentiOsservFontiDao allagamentiOsservFontiDao;
    
    @Autowired(required = true)
    protected ValoriEstensioneDao valoriEstensioneDao;
    @Autowired(required = true)
    protected ValoriProfonditaDao valoriProfonditaDao;
    @Autowired(required = true)
    protected ValoriDurataDao valoriDurataDao;
    
    @Autowired(required = true)
    protected CodiceIstatDao codiceIstatDao;
    
    @Test
    public void testFind() throws Exception {
        
        logger.debug("\nSTARTED testFind()\n");
        Result<List<AllagamentiOsserv>> allItems = allagamentiOsservService.findAll(TEST_USER_ID);
        
        logger.debug(allItems.getMsg());

        assertTrue(allItems.getData().size() > 0);

        // get the first item in the list
        AllagamentiOsserv a1 = allItems.getData().get(0);

        int id = a1.getId();

        Result<AllagamentiOsserv> c2= allagamentiOsservService.find(id, TEST_USER_ID);

        assertTrue(a1.equals(c2.getData()));
        logger.debug("\nFINISHED testFind()\n");
    }
    
    @Test
    public void testFindAll() throws Exception {
        
        logger.debug("\nSTARTED testFindAll()\n");
        int rowCount = countRowsInTable("audb.allagamenti_osserv");
        
        if(rowCount > 0) {                       
            
            Result<List<AllagamentiOsserv>> allItems = allagamentiOsservService.findAll(TEST_USER_ID);
            assertTrue("AllagamentiOsserv.findAll list not equal to row count of table audb.allagamenti_osserv", rowCount == allItems.getData().size());
            
        } else {
            throw new IllegalStateException("INVALID TESTING SCENARIO: AllagamentiOsserv table is empty");
        }
        logger.debug("\nFINISHED testFindAll()\n");
    }
    
    @Test
    public void testAddNew() throws Exception {
        
        logger.debug("\nSTARTED testAddNew()\n");
        
        //AllagamentiOsserv a = new AllagamentiOsserv();
        
        /*
            Integer idAllagamentoOsserv,
            String indirizzo,
            Date dataOsserv,
            Date oraOsserv,
            Integer idcodistat,
            Integer idEstensioneApprox,
            Integer idProfonditaApprox,
            Integer idDurataApprox,
            String note,
            Integer idUtenteIns,
            Date dataIns,
            Integer idUtenteAgg,
            Date dataAgg,
            Integer idFonte,
            Double coordX4326,
            Double coordY4326,
            Integer actionUserId
        */
        
        final String indirizzo = "Via Pippo Baudo";
        final String NOTE = "Allagamento di test";
        
//        a.setIndirizzo(indirizzo);
//        a.setDataOsserv(Date.from(Instant.now()));
//        a.setOraOsserv(Date.from(Instant.now()));
//        a.setCodiceIstat(codiceIstatDao.findReferenceByCodice(74001));
//        a.setEstensioneApprox(valoriEstensioneDao.findReferenceById(1));
//        a.setProfonditaApprox(valoriProfonditaDao.findReferenceById(1));
//        a.setDurataApprox(valoriDurataDao.findReferenceById(1));
//        a.setNote(NOTE);
//        a.setUtenteIns(utenteDao.find(TEST_USER_ID));
//        a.setDataIns(Date.from(Instant.now()));
//        a.setUtenteAgg(utenteDao.find(TEST_USER_ID));
//        a.setDataAgg(Date.from(Instant.now()));
//        a.setFonte(allagamentiOsservFontiDao.find(1));
//        a.setCoordX4326(12.457256);
//        a.setCoordY4326(41.902232);

        Result<AllagamentiOsserv> a2 = allagamentiOsservService.store(
                null,
                indirizzo,
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                74001,
                1,
                1,
                1,
                NOTE,
//                TEST_USER_ID,
//                Date.from(Instant.now()),
//                TEST_USER_ID,
//                Date.from(Instant.now()),
                1,
                12.457256,
                41.902232,
                TEST_USER_ID);
        
        assertTrue(a2.getData().getId() != null);
        assertTrue(a2.getData().getIndirizzo().equals(indirizzo));
        
        logger.debug("\nFINISHED testAddNew()\n");
    }
    
    @Test
    public void testUpdate() throws Exception {
        
        logger.debug("\nSTARTED testUpdate()\n");
        final String NEW_NOTE = "Update AllagamentiOsserv notes test";
        
        Result<List<AllagamentiOsserv>> list = allagamentiOsservService.findAll(TEST_USER_ID);
        AllagamentiOsserv a = list.getData().get(0);
        //c.setCompanyName(NEW_NAME);
        
        allagamentiOsservService.store(
                a.getIdAllagamentoOsserv(),
                a.getIndirizzo(),
                Date.from(Instant.now()),
                Date.from(Instant.now()),
                74001,
                1,
                1,
                1,
                NEW_NOTE,
//                TEST_USER_ID,
//                Date.from(Instant.now()),
//                TEST_USER_ID,
//                Date.from(Instant.now()),
                1,
                12.457256,
                41.902232,
                TEST_USER_ID);
        
        Result<AllagamentiOsserv> list2 = allagamentiOsservService.find(a.getIdAllagamentoOsserv(), TEST_USER_ID);
        
        assertTrue(list2.getData().getNoteAllagamento().equals(NEW_NOTE));
        
        logger.debug("\nFINISHED testUpdate()\n");
        
    }
    
}
