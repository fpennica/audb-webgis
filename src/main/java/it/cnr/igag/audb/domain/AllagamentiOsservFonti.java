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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author francesco
 */
@Entity
@Table(name = "allagamenti_osserv_fonti", schema="audb")
@NamedQueries({
    @NamedQuery(name = "AllagamentiOsservFonti.findAll", query = "SELECT a FROM AllagamentiOsservFonti a"),
    @NamedQuery(name = "AllagamentiOsservFonti.findByIdFonte", query = "SELECT a FROM AllagamentiOsservFonti a WHERE a.idFonte = :idFonte"),
    @NamedQuery(name = "AllagamentiOsservFonti.findByNomeFonte", query = "SELECT a FROM AllagamentiOsservFonti a WHERE a.nomeFonte = :nomeFonte"),
    @NamedQuery(name = "AllagamentiOsservFonti.findByNoteFonte", query = "SELECT a FROM AllagamentiOsservFonti a WHERE a.noteFonte = :noteFonte")})
public class AllagamentiOsservFonti extends AbstractEntity implements EntityItem<Integer>, Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fonte")
    private Integer idFonte;
    
    @Size(max = 2147483647)
    @Column(name = "nome_fonte")
    private String nomeFonte;
    
    @Size(max = 2147483647)
    @Column(name = "note_fonte")
    private String noteFonte;
    
//    @OneToMany(mappedBy = "fonte")
//    private List<AllagamentiOsserv> allagamentiOsservList;

    public AllagamentiOsservFonti() {
    }

    public AllagamentiOsservFonti(Integer idFonte) {
        this.idFonte = idFonte;
    }

    public Integer getIdFonte() {
        return idFonte;
    }

    public void setIdFonte(Integer idFonte) {
        this.idFonte = idFonte;
    }

    public String getNomeFonte() {
        return nomeFonte;
    }

    public void setNomeFonte(String nomeFonte) {
        this.nomeFonte = nomeFonte;
    }

    public String getNoteFonte() {
        return noteFonte;
    }

    public void setNoteFonte(String noteFonte) {
        this.noteFonte = noteFonte;
    }

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
        hash += (idFonte != null ? idFonte.hashCode() : 0);
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
        final AllagamentiOsservFonti other = (AllagamentiOsservFonti) obj;
        return Objects.equals(this.idFonte, other.idFonte);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.AllagamentiOsservFonti[ idFonte=" + idFonte + " ]";
    }

    @Override
    public Integer getId() {
        return idFonte;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idFonte", idFonte)
                .add("nomeFonte", nomeFonte)
                .add("noteFonte", noteFonte);
    }
    
}
