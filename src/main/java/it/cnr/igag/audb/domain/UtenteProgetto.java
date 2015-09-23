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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author francesco
 */
@Entity
@Table(name = "utenteprogetto", schema="urbisit")
@NamedQueries({
    @NamedQuery(name = "UtenteProgetto.findAll", query = "SELECT u FROM UtenteProgetto u"),
    @NamedQuery(name = "UtenteProgetto.findByIdutente", query = "SELECT u FROM UtenteProgetto u WHERE u.utenteProgettoPK.idutente = :idutente"),
    @NamedQuery(name = "UtenteProgetto.findByIdprogetto", query = "SELECT u FROM UtenteProgetto u WHERE u.utenteProgettoPK.idprogetto = :idprogetto"),
    @NamedQuery(name = "UtenteProgetto.findByIdUtenteIdProgetto", query = "SELECT u FROM UtenteProgetto u WHERE u.utenteProgettoPK.idutente = :idUtente AND u.utenteProgettoPK.idprogetto = :idProgetto")
})
public class UtenteProgetto implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UtenteProgettoPK utenteProgettoPK;
    @JoinColumn(name = "idprogetto", referencedColumnName = "idprogetto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Progetto progetto;
    @JoinColumn(name = "idutente", referencedColumnName = "idutente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Utente utente;
//    @OneToMany(mappedBy = "utenteProgetto")
//    private List<Mappe> mappeList;

    public UtenteProgetto() {
    }

    public UtenteProgetto(UtenteProgettoPK utenteProgettoPK) {
        this.utenteProgettoPK = utenteProgettoPK;
    }

    public UtenteProgetto(int idutente, int idprogetto) {
        this.utenteProgettoPK = new UtenteProgettoPK(idutente, idprogetto);
    }

    public UtenteProgettoPK getUtenteProgettoPK() {
        return utenteProgettoPK;
    }

    public void setUtenteProgettoPK(UtenteProgettoPK utenteProgettoPK) {
        this.utenteProgettoPK = utenteProgettoPK;
    }

    public Progetto getProgetto() {
        return progetto;
    }

    public void setProgetto(Progetto progetto) {
        this.progetto = progetto;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }
    
//    public List<Mappe> getMappeList() {
//        return mappeList;
//    }
//
//    public void setMappeList(List<Mappe> mappeList) {
//        this.mappeList = mappeList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (utenteProgettoPK != null ? utenteProgettoPK.hashCode() : 0);
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
        final UtenteProgetto other = (UtenteProgetto) obj;
        return Objects.equals(this.utenteProgettoPK, other.utenteProgettoPK);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.UtenteProgetto[ utenteProgettoPK=" + utenteProgettoPK + " ]";
    }
    
}
