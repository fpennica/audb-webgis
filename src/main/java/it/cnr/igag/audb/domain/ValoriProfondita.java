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
@Table(name = "valori_profondita", schema="audb")
@NamedQueries({
    @NamedQuery(name = "ValoriProfondita.findAll", query = "SELECT v FROM ValoriProfondita v ORDER BY v.idProfondita ASC"),
    @NamedQuery(name = "ValoriProfondita.findByIdProfondita", query = "SELECT v FROM ValoriProfondita v WHERE v.idProfondita = :idProfondita"),
    @NamedQuery(name = "ValoriProfondita.findByProfonditaValore", query = "SELECT v FROM ValoriProfondita v WHERE v.profonditaValore = :profonditaValore"),
    @NamedQuery(name = "ValoriProfondita.findByProfonditaValoreHtml", query = "SELECT v FROM ValoriProfondita v WHERE v.profonditaValoreHtml = :profonditaValoreHtml")})
public class ValoriProfondita extends AbstractEntity implements EntityItem<Integer>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_profondita")
    private Integer idProfondita;
    
    @Size(max = 2147483647)
    @Column(name = "profondita_valore")
    private String profonditaValore;
    
    @Size(max = 2147483647)
    @Column(name = "profondita_valore_html")
    private String profonditaValoreHtml;
    
//    @OneToMany(mappedBy = "profonditaApprox")
//    private List<AllagamentiOsserv> allagamentiOsservList;

    public ValoriProfondita() {
    }

    public ValoriProfondita(Integer idProfondita) {
        this.idProfondita = idProfondita;
    }

    public Integer getIdProfondita() {
        return idProfondita;
    }

//    public void setIdProfondita(Integer idProfondita) {
//        this.idProfondita = idProfondita;
//    }

    public String getProfonditaValore() {
        return profonditaValore;
    }

//    public void setProfonditaValore(String profonditaValore) {
//        this.profonditaValore = profonditaValore;
//    }

    public String getProfonditaValoreHtml() {
        return profonditaValoreHtml;
    }

//    public void setProfonditaValoreHtml(String profonditaValoreHtml) {
//        this.profonditaValoreHtml = profonditaValoreHtml;
//    }

//    public List<AllagamentiOsserv> getAllagamentiOsservList() {
//        return allagamentiOsservList;
//    }
//
//    public void setAllagamentiOsservList(List<AllagamentiOsserv> allagamentiOsservList) {
//        this.allagamentiOsservList = allagamentiOsservList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProfondita != null ? idProfondita.hashCode() : 0);
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
        final ValoriProfondita other = (ValoriProfondita) obj;
        return Objects.equals(this.idProfondita, other.idProfondita);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.ValoriProfondita[ idProfondita=" + idProfondita + " ]";
    }

    @Override
    public Integer getId() {
        return idProfondita;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idProfondita", idProfondita)
                .add("profonditaValore", profonditaValore)
                .add("profonditaValoreHtml", profonditaValoreHtml == null ? "" : profonditaValoreHtml);
    }
    
}
