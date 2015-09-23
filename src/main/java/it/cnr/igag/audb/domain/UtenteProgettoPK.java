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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author francesco
 */
@Embeddable
public class UtenteProgettoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "idutente")
    private int idutente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idprogetto")
    private int idprogetto;

    public UtenteProgettoPK() {
    }

    public UtenteProgettoPK(int idutente, int idprogetto) {
        this.idutente = idutente;
        this.idprogetto = idprogetto;
    }

    public int getIdutente() {
        return idutente;
    }

    public void setIdutente(int idutente) {
        this.idutente = idutente;
    }

    public int getIdprogetto() {
        return idprogetto;
    }

    public void setIdprogetto(int idprogetto) {
        this.idprogetto = idprogetto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idutente;
        hash += (int) idprogetto;
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
        final UtenteProgettoPK other = (UtenteProgettoPK) obj;
        if (this.idutente != other.idutente) {
            return false;
        }
        return this.idprogetto == other.idprogetto;
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.UtenteProgettoPK[ idutente=" + idutente + ", idprogetto=" + idprogetto + " ]";
    }
    
}
