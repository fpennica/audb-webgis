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
@Table(name = "valori_estensione", schema="audb")
@NamedQueries({
    @NamedQuery(name = "ValoriEstensione.findAll", query = "SELECT v FROM ValoriEstensione v ORDER BY v.idEstensione ASC"),
    @NamedQuery(name = "ValoriEstensione.findByIdEstensione", query = "SELECT v FROM ValoriEstensione v WHERE v.idEstensione = :idEstensione"),
    @NamedQuery(name = "ValoriEstensione.findByEstensioneValore", query = "SELECT v FROM ValoriEstensione v WHERE v.estensioneValore = :estensioneValore"),
    @NamedQuery(name = "ValoriEstensione.findByEstensioneValoreHtml", query = "SELECT v FROM ValoriEstensione v WHERE v.estensioneValoreHtml = :estensioneValoreHtml")})
public class ValoriEstensione extends AbstractEntity implements EntityItem<Integer>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_estensione")
    private Integer idEstensione;
    
    @Size(max = 2147483647)
    @Column(name = "estensione_valore")
    private String estensioneValore;
    
    @Size(max = 2147483647)
    @Column(name = "estensione_valore_html")
    private String estensioneValoreHtml;
    
//    @OneToMany(mappedBy = "estensioneApprox")
//    private List<AllagamentiOsserv> allagamentiOsservList;

    public ValoriEstensione() {
    }

    public ValoriEstensione(Integer idEstensione) {
        this.idEstensione = idEstensione;
    }

    public Integer getIdEstensione() {
        return idEstensione;
    }

//    public void setIdEstensione(Integer idEstensione) {
//        this.idEstensione = idEstensione;
//    }

    public String getEstensioneValore() {
        return estensioneValore;
    }

//    public void setEstensioneValore(String estensioneValore) {
//        this.estensioneValore = estensioneValore;
//    }

    public String getEstensioneValoreHtml() {
        return estensioneValoreHtml;
    }

//    public void setEstensioneValoreHtml(String estensioneValoreHtml) {
//        this.estensioneValoreHtml = estensioneValoreHtml;
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
        hash += (idEstensione != null ? idEstensione.hashCode() : 0);
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
        final ValoriEstensione other = (ValoriEstensione) obj;
        return Objects.equals(this.idEstensione, other.idEstensione);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.ValoriEstensione[ idEstensione=" + idEstensione + " ]";
    }

    @Override
    public Integer getId() {
        return idEstensione;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idEstensione", idEstensione)
                .add("estensioneValore", estensioneValore)
                .add("estensioneValoreHtml", estensioneValoreHtml == null ? "" : estensioneValoreHtml);
    }
    
}
