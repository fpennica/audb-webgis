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
@Table(name = "valori_durata", schema="audb")
@NamedQueries({
    @NamedQuery(name = "ValoriDurata.findAll", query = "SELECT v FROM ValoriDurata v ORDER BY v.idDurata ASC"),
    @NamedQuery(name = "ValoriDurata.findByIdDurata", query = "SELECT v FROM ValoriDurata v WHERE v.idDurata = :idDurata"),
    @NamedQuery(name = "ValoriDurata.findByDurataValore", query = "SELECT v FROM ValoriDurata v WHERE v.durataValore = :durataValore"),
    @NamedQuery(name = "ValoriDurata.findByDurataValoreHtml", query = "SELECT v FROM ValoriDurata v WHERE v.durataValoreHtml = :durataValoreHtml")})
public class ValoriDurata extends AbstractEntity implements EntityItem<Integer>, Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_durata")
    private Integer idDurata;
    
    @Size(max = 2147483647)
    @Column(name = "durata_valore")
    private String durataValore;
    
    @Size(max = 2147483647)
    @Column(name = "durata_valore_html")
    private String durataValoreHtml;
    
//    @OneToMany(mappedBy = "durataApprox")
//    private List<AllagamentiOsserv> allagamentiOsservList;

    public ValoriDurata() {
    }

    public ValoriDurata(Integer idDurata) {
        this.idDurata = idDurata;
    }

    public Integer getIdDurata() {
        return idDurata;
    }

//    public void setIdDurata(Integer idDurata) {
//        this.idDurata = idDurata;
//    }

    public String getDurataValore() {
        return durataValore;
    }

//    public void setDurataValore(String durataValore) {
//        this.durataValore = durataValore;
//    }

    public String getDurataValoreHtml() {
        return durataValoreHtml;
    }

//    public void setDurataValoreHtml(String durataValoreHtml) {
//        this.durataValoreHtml = durataValoreHtml;
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
        hash += (idDurata != null ? idDurata.hashCode() : 0);
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
        final ValoriDurata other = (ValoriDurata) obj;
        return Objects.equals(this.idDurata, other.idDurata);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.ValoriDurata[ idDurata=" + idDurata + " ]";
    }

    @Override
    public Integer getId() {
        return idDurata;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idDurata", idDurata)
                .add("durataValore", durataValore)
                .add("durataValoreHtml", durataValoreHtml);
    }
    
}
