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
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@Repository("codiceIstatDao")
@Transactional
public class CodiceIstatDaoImpl extends GenericDaoImpl<CodiceIstat, Integer> implements CodiceIstatDao {

    public CodiceIstatDaoImpl() {
        super(CodiceIstat.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<CodiceIstat> findAll() {
        
        return em.createNamedQuery("CodiceIstat.findAll").getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CodiceIstat findReferenceByCodice(Integer codicestat) {
        
        return em.getReference(CodiceIstat.class, codicestat);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> findAllRegioni() {
        //return em.createQuery("SELECT DISTINCT c.regione FROM CodiceIstat c ORDER BY c.regione").getResultList();
        return em.createNamedQuery("CodiceIstat.findAllRegioni").getResultList();
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> findAllProvinceByRegione(String regione) {
        return em.createNamedQuery("CodiceIstat.findAllProvinceByRegione")
                .setParameter("regione", regione)
                .getResultList();
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<String> findAllComuniByProvincia(String provincia) {
        return em.createNamedQuery("CodiceIstat.findAllComuniByProvincia")
                .setParameter("provincia", provincia)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public CodiceIstat findByRegioneProvinciaComune(String regione, String provincia, String comune) {
        List<CodiceIstat> codiceIstat = em.createNamedQuery("CodiceIstat.findByRegioneProvinciaComune")
                .setParameter("regione", regione)
                .setParameter("provincia", provincia)
                .setParameter("comune", comune)
                .getResultList();
        return (codiceIstat.size() == 1 ? codiceIstat.get(0) : null);
    }

}
