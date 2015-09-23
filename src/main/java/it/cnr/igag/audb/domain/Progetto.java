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
import java.util.List;
import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "progetto", schema="urbisit")
@NamedQueries({
    @NamedQuery(name = "Progetto.findAll", query = "SELECT p FROM Progetto p"),
    @NamedQuery(name = "Progetto.findByIdprogetto", query = "SELECT p FROM Progetto p WHERE p.idprogetto = :idprogetto"),
    @NamedQuery(name = "Progetto.findByNome", query = "SELECT p FROM Progetto p WHERE p.nomeProgetto = :nomeProgetto"),
    @NamedQuery(name = "Progetto.findByNote", query = "SELECT p FROM Progetto p WHERE p.noteProgetto = :noteProgetto"),
    @NamedQuery(name = "Progetto.findByFileDtm", query = "SELECT p FROM Progetto p WHERE p.fileDtm = :fileDtm"),
    @NamedQuery(name = "Progetto.findByDescrizioneDtm", query = "SELECT p FROM Progetto p WHERE p.descrizioneDtm = :descrizioneDtm"),
    @NamedQuery(name = "Progetto.findByModulo", query = "SELECT p FROM Progetto p WHERE p.modulo = :modulo")})
public class Progetto extends AbstractEntity implements EntityItem<Integer>, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprogetto")
    private Integer idprogetto;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome")
    private String nomeProgetto;
    
    @Size(max = 2147483647)
    @Column(name = "note")
    private String noteProgetto;
    
//    @Basic(optional = false)
//    @NotNull
//    @Lob
//    @Column(name = "the_geom")
//    private Object theGeom;
    @Size(max = 2147483647)
    @Column(name = "file_dtm")
    private String fileDtm;
    @Size(max = 2147483647)
    @Column(name = "descrizione_dtm")
    private String descrizioneDtm;
    @Size(max = 2147483647)
    @Column(name = "modulo")
    private String modulo;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "progettoDefault")
//    private List<Utente> utenteList;
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "progetto")
//    private List<UtenteProgetto> utenteProgettoList;
    @JoinTable(name = "urbisit.utenteprogetto",
            joinColumns = {
                @JoinColumn(name = "idprogetto", referencedColumnName = "idprogetto")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "idutente", referencedColumnName = "idutente")
            })
    @ManyToMany
    private List<Utente> utenti;

    public Progetto() {
    }

    public Progetto(Integer idprogetto) {
        this.idprogetto = idprogetto;
    }

    public Progetto(Integer idprogetto, String nome) {
        this.idprogetto = idprogetto;
        this.nomeProgetto = nome;
//        this.theGeom = theGeom;
    }

    public List<Utente> getUtenti() {
        return utenti;
    }

    public void setUtenti(List<Utente> utenti) {
        this.utenti = utenti;
    }

    public Integer getIdprogetto() {
        return idprogetto;
    }

    public void setIdprogetto(Integer idprogetto) {
        this.idprogetto = idprogetto;
    }

    public String getNomeProgetto() {
        return nomeProgetto;
    }

    public void setNomeProgetto(String nomeProgetto) {
        this.nomeProgetto = nomeProgetto;
    }

    public String getNoteProgetto() {
        return noteProgetto;
    }

    public void setNoteProgetto(String noteProgetto) {
        this.noteProgetto = noteProgetto;
    }

//    public Object getTheGeom() {
//        return theGeom;
//    }
//
//    public void setTheGeom(Object theGeom) {
//        this.theGeom = theGeom;
//    }
    public String getFileDtm() {
        return fileDtm;
    }

    public void setFileDtm(String fileDtm) {
        this.fileDtm = fileDtm;
    }

    public String getDescrizioneDtm() {
        return descrizioneDtm;
    }

    public void setDescrizioneDtm(String descrizioneDtm) {
        this.descrizioneDtm = descrizioneDtm;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

//    public List<Utente> getUtenteList() {
//        return utenteList;
//    }
//
//    public void setUtenteList(List<Utente> utenteList) {
//        this.utenteList = utenteList;
//    }
//    public List<UtenteProgetto> getUtenteProgettoList() {
//        return utenteProgettoList;
//    }
//
//    public void setUtenteProgettoList(List<UtenteProgetto> utenteProgettoList) {
//        this.utenteProgettoList = utenteProgettoList;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprogetto != null ? idprogetto.hashCode() : 0);
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
        final Progetto other = (Progetto) obj;
        return Objects.equals(this.idprogetto, other.idprogetto);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.Progetto[ idprogetto=" + idprogetto + " ]";
    }

    @Override
    public Integer getId() {
        return idprogetto;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        builder.add("idprogetto", idprogetto)
                .add("nomeProgetto", nomeProgetto)
                .add("noteProgetto", noteProgetto != null ? noteProgetto : "")
                //TODO: DTM
                .add("modulo", modulo != null ? modulo : "");
    }

}
