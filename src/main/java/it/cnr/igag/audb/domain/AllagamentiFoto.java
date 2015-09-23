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

package it.cnr.igag.audb.domain;

import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author francesco
 */
@Entity
@Table(name = "allagamenti_foto", schema="audb")
@NamedQueries({
    @NamedQuery(name = "AllagamentiFoto.findAll", query = "SELECT a FROM AllagamentiFoto a"),
    @NamedQuery(name = "AllagamentiFoto.findByIdFoto", query = "SELECT a FROM AllagamentiFoto a WHERE a.idFoto = :idFoto"),
    @NamedQuery(name = "AllagamentiFoto.findByPercorso", query = "SELECT a FROM AllagamentiFoto a WHERE a.percorso = :percorso"),
    @NamedQuery(name = "AllagamentiFoto.findByIdAllagamentoOsserv", query = "SELECT a FROM AllagamentiFoto a WHERE a.allagamentoOsserv.idAllagamentoOsserv = :idAllagamentoOsserv")
    //@NamedQuery(name = "AllagamentiFoto.findByAllagamentoOsserv", query = "SELECT a FROM AllagamentiFoto a WHERE a.allagamentoOsserv = :allagamentoOsserv")
})
public class AllagamentiFoto extends AbstractEntity implements EntityItem<Integer> {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_foto")
    private Integer idFoto;
    
    @Size(max = 2147483647)
    @Column(name = "percorso")
    private String percorso;
    
    @JoinColumn(name = "id_allagamento_osserv", referencedColumnName = "id_allagamento_osserv")
    @ManyToOne
    private AllagamentiOsserv allagamentoOsserv;

    public AllagamentiFoto() {
    }

    public AllagamentiFoto(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public Integer getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Integer idFoto) {
        this.idFoto = idFoto;
    }

    public String getPercorso() {
        return percorso;
    }

    public void setPercorso(String percorso) {
        this.percorso = percorso;
    }

    public AllagamentiOsserv getAllagamentoOsserv() {
        return allagamentoOsserv;
    }

    public void setAllagamentoOsserv(AllagamentiOsserv idAllagamentoOsserv) {
        this.allagamentoOsserv = idAllagamentoOsserv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFoto != null ? idFoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AllagamentiFoto other = (AllagamentiFoto) obj;
        return Objects.equals(this.idFoto, other.idFoto);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.AllagamentiFoto[ idFoto=" + idFoto + " ]";
    }

    @Override
    public Integer getId() {
        return idFoto;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idFoto", idFoto)
           .add("percorso", percorso);
                
        if(allagamentoOsserv != null){
           allagamentoOsserv.addJson(builder);
        }
    }
    
}
