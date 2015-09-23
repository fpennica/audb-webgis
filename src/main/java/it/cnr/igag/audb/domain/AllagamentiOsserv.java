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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.json.JsonObjectBuilder;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author francesco
 */
@Entity
@Table(name = "allagamenti_osserv", schema = "audb")
@NamedQueries({
    @NamedQuery(name = "AllagamentiOsserv.findAll", query = "SELECT a FROM AllagamentiOsserv a ORDER BY a.dataOsserv ASC"),
    @NamedQuery(name = "AllagamentiOsserv.findByIdAllagamentoOsserv", query = "SELECT a FROM AllagamentiOsserv a WHERE a.idAllagamentoOsserv = :idAllagamentoOsserv"),
    //@NamedQuery(name = "AllagamentiOsserv.findByDataOra", query = "SELECT a FROM AllagamentiOsserv a WHERE a.dataOra = :dataOra"),
    @NamedQuery(name = "AllagamentiOsserv.findByIndirizzo", query = "SELECT a FROM AllagamentiOsserv a WHERE a.indirizzo = :indirizzo"),
    @NamedQuery(name = "AllagamentiOsserv.findByCodiceIstat", query = "SELECT a FROM AllagamentiOsserv a WHERE a.codiceIstat = :codiceIstat"),
    @NamedQuery(name = "AllagamentiOsserv.findByNoteAllagamento", query = "SELECT a FROM AllagamentiOsserv a WHERE a.noteAllagamento = :noteAllagamento"),
    @NamedQuery(name = "AllagamentiOsserv.findByUtenteIns", query = "SELECT a FROM AllagamentiOsserv a WHERE a.utenteIns = :utenteIns"),
    @NamedQuery(name = "AllagamentiOsserv.findByDataIns", query = "SELECT a FROM AllagamentiOsserv a WHERE a.dataIns = :dataIns"),
    @NamedQuery(name = "AllagamentiOsserv.findByUtenteAgg", query = "SELECT a FROM AllagamentiOsserv a WHERE a.utenteAgg = :utenteAgg"),
    @NamedQuery(name = "AllagamentiOsserv.findByDataAgg", query = "SELECT a FROM AllagamentiOsserv a WHERE a.dataAgg = :dataAgg"),
    @NamedQuery(name = "AllagamentiOsserv.findByCoordX4326", query = "SELECT a FROM AllagamentiOsserv a WHERE a.coordX4326 = :coordX4326"),
    @NamedQuery(name = "AllagamentiOsserv.findByCoordY4326", query = "SELECT a FROM AllagamentiOsserv a WHERE a.coordY4326 = :coordY4326"),
    @NamedQuery(name = "AllagamentiOsserv.findByFonte", query = "SELECT a FROM AllagamentiOsserv a WHERE a.fonte = :fonte"),
    @NamedQuery(name = "AllagamentiOsserv.findByDateRange", query = "SELECT a FROM AllagamentiOsserv a WHERE a.dataOsserv BETWEEN :startDate AND :endDate ORDER BY a.dataOsserv ASC")
})
@NamedNativeQueries({
    @NamedNativeQuery(name = "AllagamentiOsserv.findByProject", query = 
        "SELECT allagamenti_osserv.* " +
        "FROM audb.allagamenti_osserv,urbisit.progetto " +
        "WHERE progetto.idprogetto = ? AND ST_Intersects(allagamenti_osserv.geom,progetto.the_geom) " +
        "ORDER BY allagamenti_osserv.data_osserv,allagamenti_osserv.ora_osserv;",
            resultClass = AllagamentiOsserv.class),
    @NamedNativeQuery(name = "AllagamentiOsserv.getWktFromGeom", query = "SELECT ST_AsText(geom) FROM audb.allagamenti_osserv WHERE id_allagamento_osserv = ?")
})
public class AllagamentiOsserv extends AbstractEntity implements EntityItem<Integer>, Serializable {

    private static final long serialVersionUID = 1L;

    static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    static final SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Id
//    @SequenceGenerator(name = "id_allagamento_osserv_seq",
//            sequenceName = "allagamenti_osserv_id_allagamento_osserv_seq",
//            schema = "audb",
//            allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE,
//            generator = "id_allagamento_osserv_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_allagamento_osserv")
    private Integer idAllagamentoOsserv;
    
//    @Column(name = "data_ora")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dataOra;
    
    @Column(name = "data_osserv")
    @Temporal(TemporalType.DATE)
    private Date dataOsserv;
    
    @Column(name = "ora_osserv")
    @Temporal(TemporalType.TIME)
    private Date oraOsserv;
    
    @Size(max = 2147483647)
    @Column(name = "indirizzo")
    private String indirizzo;
    
    @JoinColumn(name = "codice_istat", referencedColumnName = "idcodistat")
    @ManyToOne
    private CodiceIstat codiceIstat;
    
    @Size(max = 2147483647)
    @Column(name = "note_allagamento")
    private String noteAllagamento;
    
    @JoinColumn(name = "utente_ins", referencedColumnName = "idutente")
    @ManyToOne
    private Utente utenteIns;
    
    @Column(name = "data_ins")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataIns;
    
    @JoinColumn(name = "utente_agg", referencedColumnName = "idutente")
    @ManyToOne
    private Utente utenteAgg;
    
    @Column(name = "data_agg")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAgg;
    
//    @Lob
//    @Column(name = "geom")
//    private Object geom;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "coord_x_4326")
    private Double coordX4326;
    
    @Column(name = "coord_y_4326")
    private Double coordY4326;
    
    @JoinColumn(name = "fonte", referencedColumnName = "id_fonte")
    @ManyToOne
    private AllagamentiOsservFonti fonte;
    
    @JoinColumn(name = "durata_approx", referencedColumnName = "id_durata")
    @ManyToOne
    private ValoriDurata durataApprox;
    
    @JoinColumn(name = "estensione_approx", referencedColumnName = "id_estensione")
    @ManyToOne
    private ValoriEstensione estensioneApprox;
    
    @JoinColumn(name = "profondita_approx", referencedColumnName = "id_profondita")
    @ManyToOne
    private ValoriProfondita profonditaApprox;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "allagamentoOsserv")
    private List<AllagamentiFoto> allagamentiFotoList;

    public AllagamentiOsserv() {
    }

    public AllagamentiOsserv(Integer idAllagamentoOsserv) {
        this.idAllagamentoOsserv = idAllagamentoOsserv;
    }

    public Integer getIdAllagamentoOsserv() {
        return idAllagamentoOsserv;
    }

    public void setIdAllagamentoOsserv(Integer idAllagamentoOsserv) {
        this.idAllagamentoOsserv = idAllagamentoOsserv;
    }

//    public Date getDataOra() {
//        return dataOra;
//    }
//
//    public void setDataOra(Date dataOra) {
//        this.dataOra = dataOra;
//    }
    
    public Date getDataOsserv() {
        return dataOsserv;
    }

    public void setDataOsserv(Date dataOsserv) {
        this.dataOsserv = dataOsserv;
    }

    public Date getOraOsserv() {
        return oraOsserv;
    }

    public void setOraOsserv(Date oraOsserv) {
        this.oraOsserv = oraOsserv;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public CodiceIstat getCodiceIstat() {
        return codiceIstat;
    }

    public void setCodiceIstat(CodiceIstat codiceIstat) {
        this.codiceIstat = codiceIstat;
    }

    public String getNoteAllagamento() {
        return noteAllagamento;
    }

    public void setNoteAllagamento(String noteAllagamento) {
        this.noteAllagamento = noteAllagamento;
    }

    public Utente getUtenteIns() {
        return utenteIns;
    }

    public void setUtenteIns(Utente utenteIns) {
        this.utenteIns = utenteIns;
    }

    public Date getDataIns() {
        return dataIns;
    }

    public void setDataIns(Date dataIns) {
        this.dataIns = dataIns;
    }

    public Utente getUtenteAgg() {
        return utenteAgg;
    }

    public void setUtenteAgg(Utente utenteAgg) {
        this.utenteAgg = utenteAgg;
    }

    public Date getDataAgg() {
        return dataAgg;
    }

    public void setDataAgg(Date dataAgg) {
        this.dataAgg = dataAgg;
    }

//    public Object getGeom() {
//        return geom;
//    }
//
//    public void setGeom(Object geom) {
//        this.geom = geom;
//    }
    public Double getCoordX4326() {
        return coordX4326;
    }

    public void setCoordX4326(Double coordX4326) {
        this.coordX4326 = coordX4326;
    }

    public Double getCoordY4326() {
        return coordY4326;
    }

    public void setCoordY4326(Double coordY4326) {
        this.coordY4326 = coordY4326;
    }

    public AllagamentiOsservFonti getFonte() {
        return fonte;
    }

    public void setFonte(AllagamentiOsservFonti fonte) {
        this.fonte = fonte;
    }

    public ValoriDurata getDurataApprox() {
        return durataApprox;
    }

    public void setDurataApprox(ValoriDurata durataApprox) {
        this.durataApprox = durataApprox;
    }

    public ValoriEstensione getEstensioneApprox() {
        return estensioneApprox;
    }

    public void setEstensioneApprox(ValoriEstensione estensioneApprox) {
        this.estensioneApprox = estensioneApprox;
    }

    public ValoriProfondita getProfonditaApprox() {
        return profonditaApprox;
    }

    public void setProfonditaApprox(ValoriProfondita profonditaApprox) {
        this.profonditaApprox = profonditaApprox;
    }

    public List<AllagamentiFoto> getAllagamentiFotoList() {
        return allagamentiFotoList;
    }

    public void setAllagamentiFotoList(List<AllagamentiFoto> allagamentiFotoList) {
        this.allagamentiFotoList = allagamentiFotoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAllagamentoOsserv != null ? idAllagamentoOsserv.hashCode() : 0);
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
        final AllagamentiOsserv other = (AllagamentiOsserv) obj;
        return Objects.equals(this.idAllagamentoOsserv, other.idAllagamentoOsserv);
    }

    @Override
    public String toString() {
        return "it.cnr.igag.audb.domain.AllagamentiOsserv[ idAllagamentoOsserv=" + idAllagamentoOsserv + " ]";
    }

    @Override
    public Integer getId() {
        return idAllagamentoOsserv;
    }

    @Override
    public void addJson(JsonObjectBuilder builder) {
        
        builder.add("idAllagamentoOsserv", idAllagamentoOsserv)
                .add("dataOsserv", dataOsserv == null ? "" : DATE_FORMAT.format(dataOsserv))
                .add("oraOsserv", oraOsserv == null ? "" : TIME_FORMAT.format(oraOsserv))
                .add("indirizzo", indirizzo == null ? "" : indirizzo)
                .add("noteAllagamento", noteAllagamento == null ? "" : noteAllagamento)
                .add("utenteIns", utenteIns == null ? "" : utenteIns.getUsername())
                .add("dataIns", dataIns == null ? "" : TIMESTAMP_FORMAT.format(dataIns))
                .add("utenteAgg", utenteAgg == null ? "" : utenteAgg.getUsername())
                .add("dataAgg", dataAgg == null ? "" : TIMESTAMP_FORMAT.format(dataAgg))
                .add("coordX4326", coordX4326)
                .add("coordY4326", coordY4326);
        
        if(codiceIstat != null) {
            codiceIstat.addJson(builder);
        }
        
        if(estensioneApprox != null) {
            estensioneApprox.addJson(builder);
        }
        
        if(profonditaApprox != null) {
            profonditaApprox.addJson(builder);
        }
        
        if(durataApprox != null) {
            durataApprox.addJson(builder);
        }
        
        if(fonte != null) {
            fonte.addJson(builder);
        }
        
//        if(utenteAgg != null) {
//            utenteAgg.addJson(builder);
//        }
//        
//        if(utenteIns != null) {
//            utenteIns.addJson(builder);
//        }
    }

}
