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
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@Repository("allagamentiOsservDao")
@Transactional
public class AllagamentiOsservDaoImpl extends GenericDaoImpl<AllagamentiOsserv, Integer> implements AllagamentiOsservDao {

    public AllagamentiOsservDaoImpl() {
        super(AllagamentiOsserv.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllagamentiOsserv> findAll() {
        return em.createNamedQuery("AllagamentiOsserv.findAll")
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllagamentiOsserv> findByFonte(AllagamentiOsservFonti fonte) {
        List<AllagamentiOsserv> allagamenti = 
                em.createNamedQuery("AllagamentiOsserv.findByFonte")
                .setParameter("fonte", fonte)
                .getResultList();
        
        return allagamenti;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllagamentiOsserv> findByDateRange(Date startDate, Date endDate) {
        List<AllagamentiOsserv> allagamenti = 
                em.createNamedQuery("AllagamentiOsserv.findByDateRange")
                .setParameter("startDate", startDate, TemporalType.TIMESTAMP)
                .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
                .getResultList();
        
        return allagamenti;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public String getWktFromGeom(int id) {
        return (String) em.createNamedQuery("AllagamentiOsserv.getWktFromGeom")
                .setParameter(1, id).getSingleResult();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllagamentiOsserv> findByProject(Integer idprogettoaudb) {
        List<AllagamentiOsserv> allagamenti = 
                em.createNamedQuery("AllagamentiOsserv.findByProject")
                .setParameter(1, idprogettoaudb)
                .getResultList();
        
        return allagamenti;
    }

}
