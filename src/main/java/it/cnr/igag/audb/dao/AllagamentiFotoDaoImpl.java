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

import it.cnr.igag.audb.domain.AllagamentiFoto;
import it.cnr.igag.audb.domain.AllagamentiOsserv;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Francesco Pennica <francesco.pennica at igag.cnr.it>
 */
@Repository("allagamentiFotoDao")
@Transactional
public class AllagamentiFotoDaoImpl extends GenericDaoImpl<AllagamentiFoto, Integer> implements AllagamentiFotoDao {

    public AllagamentiFotoDaoImpl() {
        super(AllagamentiFoto.class);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllagamentiFoto> findAll() {
        return em.createNamedQuery("AllagamentiFoto.findAll")
                .getResultList();
    }

//    @Override
//    @Transactional(readOnly = true, propagation
//            = Propagation.SUPPORTS)
//    public List<AllagamentiFoto> findByAllagamento(AllagamentiOsserv allagamento) {
//        List<AllagamentiFoto> fotoList = 
//                em.createNamedQuery("AllagamentiFoto.findByAllagamentoOsserv")
//                .setParameter("allagamentoOsserv", allagamento)
//                .getResultList();
//        
//        return fotoList;
//    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<AllagamentiFoto> findByIdAllagamentoOsserv(Integer idAllagamentoOsserv) {
        List<AllagamentiFoto> fotoList = 
                em.createNamedQuery("AllagamentiFoto.findByIdAllagamentoOsserv")
                .setParameter("idAllagamentoOsserv", idAllagamentoOsserv)
                .getResultList();
        
        return fotoList;
    }
    
}
