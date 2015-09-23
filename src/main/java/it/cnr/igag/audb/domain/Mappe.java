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
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "mappe", schema="audb")
@NamedQueries({
    @NamedQuery(name = "Mappe.findAll", query = "SELECT m FROM Mappe m"),
    @NamedQuery(name = "Mappe.findByIdMappa", query = "SELECT m FROM Mappe m WHERE m.idMappa = :idMappa"),
//    @NamedQuery(name = "Mappe.findByIdutente", query = "SELECT m FROM Mappe m WHERE m.idutente = :idutente"),
//    @NamedQuery(name = "Mappe.findByIdprogetto", query = "SELECT m FROM Mappe m WHERE m.idprogetto = :idprogetto"),
    @NamedQuery(name = "Mappe.findByLayerTree", query = "SELECT m FROM Mappe m WHERE m.layerTree = :layerTree"),
    @NamedQuery(name = "Mappe.findByCenterZoom", query = "SELECT m FROM Mappe m WHERE m.centerZoom = :centerZoom"),
    @NamedQuery(name = "Mappe.findByUtenteProgetto", query = "SELECT m FROM Mappe m WHERE m.utenteProgetto = :utenteProgetto")})
public class Mappe extends AbstractEntity implements EntityItem<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mappa")
    private Integer idMappa;
//    @Column(name = "idutente")
//    private Integer idutente;
//    @Column(name = "idprogetto")
//    private Integer idprogetto;
    @Size(max = 2147483647)
    @Column(name = "nome")
    private String nome;
    @JoinColumns({
        @JoinColumn(name = "idutente", referencedColumnName = "idutente"),
        @JoinColumn(name = "idprogetto", referencedColumnName = "idprogetto")})
    @ManyToOne
    private UtenteProgetto utenteProgetto;
    @Size(max = 2147483647)
    @Column(name = "layer_tree")
    private String layerTree;
    @Size(max = 2147483647)
    @Column(name = "center_zoom")
    private String centerZoom;
    

    public Mappe() {
    }

    public Mappe(Integer idMappa) {
        this.idMappa = idMappa;
    }

    public Integer getIdMappa() {
        return idMappa;
    }

    public void setIdMappa(Integer idMappa) {
        this.idMappa = idMappa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
//    public Integer getIdutente() {
//        return idutente;
//    }
//
//    public void setIdutente(Integer idutente) {
//        this.idutente = idutente;
//    }
//
//    public Integer getIdprogetto() {
//        return idprogetto;
//    }
//
//    public void setIdprogetto(Integer idprogetto) {
//        this.idprogetto = idprogetto;
//    }

    public String getLayerTree() {
        return layerTree;
    }

    public void setLayerTree(String layerTree) {
        this.layerTree = layerTree;
    }

    public String getCenterZoom() {
        return centerZoom;
    }

    public UtenteProgetto getUtenteProgetto() {
        return utenteProgetto;
    }

    public void setUtenteProgetto(UtenteProgetto utenteProgetto) {
        this.utenteProgetto = utenteProgetto;
    }
    
    public void setCenterZoom(String centerZoom) {
        this.centerZoom = centerZoom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMappa != null ? idMappa.hashCode() : 0);
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
        final Mappe other = (Mappe) obj;
        return Objects.equals(this.idMappa, other.idMappa);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.Mappe[ idMappa=" + idMappa + " ]";
    }

    @Override
    public Integer getId() {
        return idMappa;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
