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

import java.io.Serializable;
import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author francesco
 */
@Entity
@Table(name = "codiceistat", schema="urbisit")
@NamedQueries({
    @NamedQuery(name = "CodiceIstat.findAll", query = "SELECT c FROM CodiceIstat c"),
    @NamedQuery(name = "CodiceIstat.findByIdcodistat", query = "SELECT c FROM CodiceIstat c WHERE c.idcodistat = :idcodistat"),
    @NamedQuery(name = "CodiceIstat.findByComune", query = "SELECT c FROM CodiceIstat c WHERE c.comune = :comune"),
    @NamedQuery(name = "CodiceIstat.findByProvincia", query = "SELECT c FROM CodiceIstat c WHERE c.provincia = :provincia"),
    @NamedQuery(name = "CodiceIstat.findByRegione", query = "SELECT c FROM CodiceIstat c WHERE c.regione = :regione"),
    @NamedQuery(name = "CodiceIstat.findAllRegioni", query = "SELECT DISTINCT c.regione FROM CodiceIstat c ORDER BY c.regione"),
    @NamedQuery(name = "CodiceIstat.findAllProvinceByRegione", query = "SELECT DISTINCT c.provincia FROM CodiceIstat c WHERE c.regione = :regione ORDER BY c.provincia"),
    @NamedQuery(name = "CodiceIstat.findAllComuniByProvincia", query = "SELECT DISTINCT c.comune FROM CodiceIstat c WHERE c.provincia = :provincia ORDER BY c.comune"),
    @NamedQuery(name = "CodiceIstat.findByRegioneProvinciaComune", query = "SELECT c FROM CodiceIstat c WHERE c.regione = :regione AND c.provincia = :provincia AND c.comune = :comune")
})
public class CodiceIstat extends AbstractEntity implements EntityItem<Integer>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idcodistat")
    private Integer idcodistat;
    
    @Size(max = 100)
    @Column(name = "comune")
    private String comune;
    
    @Size(max = 100)
    @Column(name = "provincia")
    private String provincia;
    
    @Size(max = 100)
    @Column(name = "regione")
    private String regione;

    public CodiceIstat() {
    }

    public CodiceIstat(Integer idcodistat) {
        this.idcodistat = idcodistat;
    }

    public Integer getIdcodistat() {
        return idcodistat;
    }

    public void setIdcodistat(Integer idcodistat) {
        this.idcodistat = idcodistat;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getRegione() {
        return regione;
    }

    public void setRegione(String regione) {
        this.regione = regione;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcodistat != null ? idcodistat.hashCode() : 0);
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
        final CodiceIstat other = (CodiceIstat) obj;
        return Objects.equals(this.idcodistat, other.idcodistat);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.CodiceIstat[ idcodistat=" + idcodistat + " ]";
    }

    @Override
    public Integer getId() {
        return idcodistat;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idcodistat", idcodistat)
                .add("comune", comune)
                .add("provincia", provincia)
                .add("regione", regione);
    }
    
}
