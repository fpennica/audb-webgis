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

import it.cnr.igag.audb.domain.AllagamentiOsserv;
import it.cnr.igag.audb.domain.AllagamentiOsservFonti;
import it.cnr.igag.audb.domain.Progetto;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public class AllagamentiOsservDaoTest extends AbstractDaoForTesting {

    public AllagamentiOsservDaoTest() {
    }

    @Test
    public void testFind() throws Exception {

        logger.debug("\nSTARTED testFind()\n");
        List<AllagamentiOsserv> allItems = allagamentiOsservDao.findAll();

        assertTrue(allItems.size() > 0);

        // get the first item in the list
        AllagamentiOsserv c1 = allItems.get(0);
        
        //assertTrue(c1.getAllagamentiFotoList().size() > 0);
        //assertTrue(!c1.getAllagamentiFotoList().get(0).getPercorso().isEmpty());

        int id = c1.getId();

        AllagamentiOsserv c2 = allagamentiOsservDao.find(id);

        assertTrue(c1.equals(c2));
        logger.debug("\nFINISHED testFind()\n");
    }

    @Test
    public void testFindAll() throws Exception {

        logger.debug("\nSTARTED testFindAll()\n");
        int rowCount = countRowsInTable("audb.allagamenti_osserv");

        if (rowCount > 0) {

            List<AllagamentiOsserv> allItems = allagamentiOsservDao.findAll();
            assertTrue("AllagamentiOsserv.findAll list not equal to row count of table audb.allagamenti_osserv", rowCount == allItems.size());

        } else {
            throw new IllegalStateException("INVALID TESTING SCENARIO: AllagamentiOsserv table is empty");
        }
        logger.debug("\nFINISHED testFindAll()\n");
    }
    
    @Test
    public void testFindByFonte() throws Exception {
        logger.debug("\nSTARTED testFindByFonte()\n");
        
        AllagamentiOsservFonti f = allagamentiOsservFontiDao.findAll().get(0);
        List<AllagamentiOsserv> list = allagamentiOsservDao.findByFonte(f);
        
        assertTrue(list.size() > 0);
        
        logger.debug("\nFINISHED testFindByFonte()\n");
    }
    
    @Test
    public void testFindByProject() throws Exception {
        logger.debug("\nSTARTED testFindByProject()\n");
        
        Progetto p = progettoDao.find(9);
        List<AllagamentiOsserv> list = allagamentiOsservDao.findByProject(p.getId());
        
        assertTrue(list.size() > 0);
        logger.debug("" + list.size());
        
        logger.debug("\nFINISHED testFindByProject()\n");
    }

    @Test
    //@Rollback(false)
    public void testPersist() throws Exception {

        logger.debug("\nSTARTED testPersist()\n");
        AllagamentiOsserv a = new AllagamentiOsserv();

        final String NOTE = "Allagamento di test";
        a.setNoteAllagamento(NOTE);

        a.setCoordX4326(12.457256);
        a.setCoordY4326(41.902232);

        a.setCodiceIstat(codiceIstatDao.findReferenceByCodice(74001));
        a.setDataOsserv(Date.from(Instant.now()));
        a.setOraOsserv(Date.from(Instant.now()));
        a.setIndirizzo("Vaticano");
        a.setEstensioneApprox(valoriEstensioneDao.findReferenceById(1));
        a.setProfonditaApprox(valoriProfonditaDao.findReferenceById(1));
        a.setDurataApprox(valoriDurataDao.findReferenceById(1));

        //a.setUtenteIns(utenteDao.find(3));
        allagamentiOsservDao.persist(a);

        assertTrue(a.getId() != null);
        assertTrue(a.getCodiceIstat().getComune().equals("Brindisi"));
        assertTrue(a.getEstensioneApprox() == valoriEstensioneDao.findReferenceById(1));
        assertTrue(a.getProfonditaApprox() == valoriProfonditaDao.findReferenceById(1));
        assertTrue(a.getDurataApprox() == valoriDurataDao.findReferenceById(1));

        // controlla che il trigger per il popolamento della geometria abbia funzionato 
        String wkt = allagamentiOsservDao.getWktFromGeom(a.getId());
        assertTrue(wkt != null && !wkt.isEmpty());
        logger.debug("Punto inserito: {}", wkt);

        assertTrue(a.getNoteAllagamento().equals(NOTE));

        logger.debug("\nFINISHED testPersist()\n");
    }

    @Test
    //@Rollback(false)
    public void testMerge() throws Exception {

        logger.debug("\nSTARTED testMerge()\n");
        final String NEW_INDIRIZZO = "Via Pinco Pallino";

        AllagamentiOsserv a = allagamentiOsservDao.findAll().get(0);
        a.setIndirizzo(NEW_INDIRIZZO);
        a = allagamentiOsservDao.merge(a);

        assertTrue(a.getIndirizzo().equals(NEW_INDIRIZZO));

        logger.debug("\nFINISHED testMerge()\n");
    }

    @Test
    public void testRemove() throws Exception {
        
        logger.debug("\nSTARTED testRemove()\n");
        AllagamentiOsserv a = allagamentiOsservDao.findAll().get(0);
        
        allagamentiOsservDao.remove(a);
        
        List<AllagamentiOsserv> allItems = allagamentiOsservDao.findAll();
        assertTrue("Deleted company may not be in findAll List",
                !allItems.contains(a));
        
        logger.debug("\nFINISHED testRemove()\n");
    }
}
