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

import it.cnr.igag.audb.domain.EntityItem;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID> {

    final protected Logger logger
            = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext(unitName = "audbPU")
    protected EntityManager em;

    private Class<T> type;

    public GenericDaoImpl(Class<T> type1) {
        this.type = type1;
    }

    @Override
    @Transactional(readOnly = true, propagation
            = Propagation.SUPPORTS)
    public T find(ID id) {
        return (T) em.find(type, id);
    }

    @Override
    @Transactional(readOnly = false, propagation
            = Propagation.REQUIRED)
    public void persist(T o) {
        em.persist(o);
        
        // insert to the DB now so that the ID is populated
        // If not flushed, subsequent actions that require a ID
        // may fail (depends on when transaction is committed)
        em.flush();
        
        if (o instanceof EntityItem) {
            EntityItem<ID> item = (EntityItem<ID>) o;
            ID id = item.getId();
            logger.info("The " + o.getClass().getName() + " record with ID=" + id + " has been inserted");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation
            = Propagation.REQUIRED)
    public T merge(T o) {
        o = em.merge(o);
        return o;
    }

    @Override
    @Transactional(readOnly = false, propagation
            = Propagation.REQUIRED)
    public void remove(T o) {
        // associate object with persistence context
        o = merge(o);
        em.remove(o);

        if (o instanceof EntityItem) {
            EntityItem<ID> item = (EntityItem<ID>) o;
            ID id = item.getId();
            logger.warn("The " + o.getClass().getName() + " record with ID=" + id + " has been deleted");
        }
    }

}
