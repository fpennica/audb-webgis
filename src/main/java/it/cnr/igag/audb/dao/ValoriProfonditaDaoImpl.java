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

import it.cnr.igag.audb.domain.ValoriProfondita;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@Repository("valoriProfonditaDaoImpl")
@Transactional
public class ValoriProfonditaDaoImpl extends GenericDaoImpl<ValoriProfondita, Integer> implements ValoriProfonditaDao {

    public ValoriProfonditaDaoImpl() {
        super(ValoriProfondita.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ValoriProfondita> findAll() {
        return em.createNamedQuery("ValoriProfondita.findAll")
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ValoriProfondita findReferenceById(Integer id) {
        /*
        http://stackoverflow.com/questions/7447149/hibernate-lookup-tables-as-an-object-relation-or-as-a-code-key
        http://stackoverflow.com/questions/3613641/jpa-best-practice-static-lookup-entities
        http://stackoverflow.com/questions/5482141/what-is-the-difference-between-entitymanager-find-and-entitymanger-getreferenc
        */
        return em.getReference(ValoriProfondita.class, id);
    }
    
}
