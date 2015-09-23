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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author francesco
 */
@Entity
@Table(name = "utente", schema="urbisit")
@NamedQueries({
    @NamedQuery(name = "Utente.findAll", query = "SELECT u FROM Utente u"),
    @NamedQuery(name = "Utente.findByIdutente", query = "SELECT u FROM Utente u WHERE u.idutente = :idutente"),
    @NamedQuery(name = "Utente.findByNome", query = "SELECT u FROM Utente u WHERE u.nome = :nome"),
    @NamedQuery(name = "Utente.findByCognome", query = "SELECT u FROM Utente u WHERE u.cognome = :cognome"),
    @NamedQuery(name = "Utente.findByEnte", query = "SELECT u FROM Utente u WHERE u.ente = :ente"),
    @NamedQuery(name = "Utente.findByEmail", query = "SELECT u FROM Utente u WHERE u.email = :email"),
    @NamedQuery(name = "Utente.findByUsername", query = "SELECT u FROM Utente u WHERE u.username = :username"),
    @NamedQuery(name = "Utente.findByPwd", query = "SELECT u FROM Utente u WHERE u.pwd = :pwd"),
    @NamedQuery(name = "Utente.findByAmministratore", query = "SELECT u FROM Utente u WHERE u.amministratore = :amministratore"),
    @NamedQuery(name = "Utente.findByDownloadPdf", query = "SELECT u FROM Utente u WHERE u.downloadPdf = :downloadPdf"),
    @NamedQuery(name = "Utente.findByEditor", query = "SELECT u FROM Utente u WHERE u.editor = :editor"),
    @NamedQuery(name = "Utente.findByAttivo", query = "SELECT u FROM Utente u WHERE u.attivo = :attivo"),
    @NamedQuery(name = "Utente.findByWebbdgt", query = "SELECT u FROM Utente u WHERE u.webbdgt = :webbdgt"),
    @NamedQuery(name = "Utente.findByAudb", query = "SELECT u FROM Utente u WHERE u.audb = :audb"),
    @NamedQuery(name = "Utente.findByUsernamePwd", query = "SELECT u FROM Utente u WHERE u.username = :username AND u.pwd = :pwd")})
public class Utente extends AbstractEntity implements EntityItem<Integer>, Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idutente")
    private Integer idutente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "cognome")
    private String cognome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "ente")
    private String ente;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "pwd")
    private String pwd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "amministratore")
    private boolean amministratore;
    @Basic(optional = false)
    @NotNull
    @Column(name = "download_pdf")
    private boolean downloadPdf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "editor")
    private boolean editor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "attivo")
    private boolean attivo;
    @Column(name = "webbdgt")
    private boolean webbdgt;
    @Column(name = "audb")
    private boolean audb;
    
    @JoinColumn(name = "idprogetto", referencedColumnName = "idprogetto")
    @ManyToOne(optional = false)
    private Progetto progettoDefault;
    
    @JoinColumn(name = "idprogettoaudb", referencedColumnName = "idprogetto")
    @ManyToOne(optional = false)
    private Progetto progettoDefaultAudb;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utente")
//    private List<UtenteProgetto> utenteProgettoList;
    @ManyToMany(mappedBy = "utenti")
    private List<Progetto> progetti;

    public Utente() {
    }

    public Utente(Integer idutente) {
        this.idutente = idutente;
    }

    public Utente(Integer idutente, String nome, String cognome, String ente, String username, String pwd, boolean amministratore, boolean downloadPdf, boolean editor, boolean attivo) {
        this.idutente = idutente;
        this.nome = nome;
        this.cognome = cognome;
        this.ente = ente;
        this.username = username;
        this.pwd = pwd;
        this.amministratore = amministratore;
        this.downloadPdf = downloadPdf;
        this.editor = editor;
        this.attivo = attivo;
    }

    public List<Progetto> getProgetti() {
        return progetti;
    }

    public void setProgetti(List<Progetto> progetti) {
        this.progetti = progetti;
    }

    public Integer getIdutente() {
        return idutente;
    }

    public void setIdutente(Integer idutente) {
        this.idutente = idutente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean getAmministratore() {
        return amministratore;
    }

    public void setAmministratore(boolean amministratore) {
        this.amministratore = amministratore;
    }

    public boolean getDownloadPdf() {
        return downloadPdf;
    }

    public void setDownloadPdf(boolean downloadPdf) {
        this.downloadPdf = downloadPdf;
    }

    public boolean getEditor() {
        return editor;
    }

    public void setEditor(boolean editor) {
        this.editor = editor;
    }

    public boolean getAttivo() {
        return attivo;
    }

    public void setAttivo(boolean attivo) {
        this.attivo = attivo;
    }

    public boolean getWebbdgt() {
        return webbdgt;
    }

    public void setWebbdgt(boolean webbdgt) {
        this.webbdgt = webbdgt;
    }

    public Boolean getAudb() {
        return audb;
    }

    public void setAudb(Boolean audb) {
        this.audb = audb;
    }

    public Progetto getProgettoDefault() {
        return progettoDefault;
    }

    public void setProgettoDefault(Progetto progettoDefault) {
        this.progettoDefault = progettoDefault;
    }
    
    public Progetto getProgettoDefaultAudb() {
        return progettoDefaultAudb;
    }

    public void setProgettoDefaultAudb(Progetto progettoDefaultAudb) {
        this.progettoDefaultAudb = progettoDefaultAudb;
    }

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
        hash += (idutente != null ? idutente.hashCode() : 0);
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
        final Utente other = (Utente) obj;
        return Objects.equals(this.idutente, other.idutente);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.Utente[ idutente=" + idutente + " ]";
    }

    @Override
    public Integer getId() {
        return idutente;
    }
    
    /**
     * Utility method to check if user is admin
     * @return True if user is admin
     */
    public boolean isAdmin() {
        return amministratore;
    }
    
    /**
     * Utility method to check if user is editor
     * @return True if user is editor
     */
    public boolean isEditor() {
        return editor;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idutente", idutente)
                .add("nome", nome)
                .add("cognome", cognome)
                .add("ente", ente)
                .add("email", email != null ? email : "")
                .add("username", username)
                
                //inutile inserire la pdw nel json
                //.add("pwd", pwd)
                
                .add("amministratore", amministratore)
                .add("downloadPdf", downloadPdf)
                .add("editor", editor)
                .add("attivo", attivo)
                .add("webbdgt", webbdgt)
                .add("audb", audb);
                
                // temp
//                .add("idprogetto", progettoDefault.getId())
//                .add("idprogettoaudb", progettoDefaultAudb.getId());
        
        if(progettoDefaultAudb != null) {
            progettoDefaultAudb.addJson(builder);
        }
    }
    
}
