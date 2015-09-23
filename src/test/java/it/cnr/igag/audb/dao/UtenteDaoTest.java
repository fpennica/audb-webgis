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

package it.cnr.igag.audb.dao;

import it.cnr.igag.audb.domain.Mappe;
import it.cnr.igag.audb.domain.Progetto;
import it.cnr.igag.audb.domain.Utente;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

/**
 *
 * @author francesco
 */
public class UtenteDaoTest extends AbstractDaoForTesting {

    public UtenteDaoTest() {
    }
    
    @Test
    public void testFind() throws Exception {

        logger.debug("\nSTARTED testFind()\n");
        List<Utente> allItems = utenteDao.findAll();

        assertTrue(allItems.size() > 0);

        // get the first item in the list
        Utente c1 = allItems.get(0);
        
        int id = c1.getId();

        Utente c2 = utenteDao.find(id);

        assertTrue(c1.equals(c2));
        logger.debug("\nFINISHED testFind()\n");
    }
    
    @Test
    public void testFindMe() throws Exception {

        logger.debug("\nSTARTED testFindMe()\n");
        Utente me = utenteDao.findByUsername("franz");

        assertTrue(me != null && me.getAmministratore());
        
        logger.debug("Progetto AUDB di default: {}", me.getProgettoDefaultAudb().getNomeProgetto());
        
        List<Mappe> mappe = mappeDao.findByUtenteProgetto(
                utenteProgettoDao.findByIdUtenteIdProgetto(me.getId(), me.getProgettoDefaultAudb().getId())
        );
        logger.debug("Mappe associate al mio progetto di default:");
        for(Mappe m : mappe) {
            logger.debug(m.getNome());
        }
        
        List<Utente> utenti = me.getProgettoDefaultAudb().getUtenti();
        logger.debug("Utenti associati al progetto di default:");
        for(Utente u : utenti) {
            logger.debug(u.getUsername());
        }
        
        List<Progetto> progetti = me.getProgetti();
        logger.debug("Progetti AUDB associati a me:");
        for(Progetto p : progetti) {
            if(p.getModulo() != null && p.getModulo().equals("audb"))
                logger.debug(p.getNomeProgetto());
        }

        logger.debug("\nFINISHED testFindMe()\n");
    }
    
    @Test
    public void testFindAll() throws Exception {
        logger.debug("\nSTARTED testFindAll()\n");
        int rowCount = countRowsInTable("urbisit.utente");

        if(rowCount > 0) {

            List<Utente> allItems = utenteDao.findAll();
            assertTrue("Utente.findAll list not equal to row count of table bdgt.utente", rowCount == allItems.size());

        } else {
            throw new IllegalStateException("INVALID TESTING SCENARIO: utente table is empty");
        }
        logger.debug("\nFINISHED testFindAll()\n");
    }
    
    @Test
    //@Rollback(false)
    public void testPersist() throws Exception {
        logger.debug("\nSTARTED testPersist()\n");
        
        Utente utente = new Utente();
        Progetto progettoDefault = progettoDao.find(4);
        String username = "pippo";
        
        // test if the username is already in the system
        Utente testByUsername = utenteDao.findByUsername("pippo");
        assertTrue(testByUsername == null);
        
        // the project must be valid if idProject is supplied
        assertTrue(progettoDefault != null);
        
        utente.setUsername(username);
        utente.setPwd("STRINGMD5");
        utente.setNome("Pippo Romeo");
        utente.setCognome("Baudo");
        utente.setEmail("pippo@pippo.it");
        utente.setEnte("RAI");
        utente.setAmministratore(true);
        utente.setDownloadPdf(true);
        utente.setEditor(true);
        utente.setAttivo(true);
        utente.setWebbdgt(true);
        utente.setAudb(true);
        utente.setProgettoDefault(progettoDefault);
        
        utenteDao.persist(utente);

        assertTrue(utente.getId() != null); // only if flush() is called in GenericDaoImpl
        //assertTrue(utente.getUsername().equals(username));

        logger.debug("\nFINISHED testPersist()\n");
    }
    
    @Test
    //@Rollback(false)
    public void testMerge() throws Exception {
        logger.debug("\nSTARTED testMerge()\n");
        
        Utente me = utenteDao.findByUsername("franz");
        
        me.setUsername("francesco");
        
        me = utenteDao.merge(me);
        
        assertTrue(me.getUsername().equals("francesco"));

        logger.debug("\nFINISHED testMerge()\n");
    }
}
