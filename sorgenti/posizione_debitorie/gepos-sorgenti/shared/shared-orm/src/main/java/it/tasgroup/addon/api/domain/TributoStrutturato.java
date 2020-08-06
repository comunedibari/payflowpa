package it.tasgroup.addon.api.domain;


import it.tasgroup.addon.api.validation.CodiceFiscale;
import it.tasgroup.addon.api.validation.EmailForm;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="TRIBUTO_STRUTTURATO")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TIPO_TRIBUTO",discriminatorType=DiscriminatorType.STRING)
@SequenceGenerator(name = "tributoDettaglioSeq", sequenceName = "TRIBUTO_DETTAGLIO_SEQ", allocationSize = 1)
public abstract class TributoStrutturato implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long version;	
	
	private long id;
	private String idPendenza;	
	private String noteVersante;	
	private String cfSoggettoVersante;
	private String email;

	private String opInserimento;
	private Timestamp tsInserimento;
	private DettaglioStrutturato dettaglioStrutturato;
	
	// Campi transient usati per comunicare questa informazione dall'esterno in fase di inserimento.
	private Date dataScadenza;     
	private Date dataFineValidita; 
    private String cdTrbEnte;      
    private String denominazioneEnte;  
    private String descTrbEnte;
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tributoDettaglioSeq")
	@Column(name="ID")	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
		
	@Transient
	public DettaglioStrutturato getDettaglioStrutturato() {
		return dettaglioStrutturato;
	}
	
	protected void setDettaglioStrutturato(DettaglioStrutturato dettaglioStrutturato) {
		this.dettaglioStrutturato = dettaglioStrutturato;
	}
	@Column(name="ID_PENDENZA")	
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	@Column(name="NOTE_VERSANTE", nullable=true, length=1024)
	public String getNoteVersante() {
		return noteVersante;
	}
	public void setNoteVersante(String noteVersante) {
		this.noteVersante = noteVersante;
	}
	
	
	@CodiceFiscale()
	@Column(name="CF_SOGG_VERSANTE", nullable=true, length=16)
	public String getCfSoggettoVersante() {
		return cfSoggettoVersante;
	}
	public void setCfSoggettoVersante(String cfSoggettoVersante) {
		if (cfSoggettoVersante != null) {
			this.cfSoggettoVersante =  cfSoggettoVersante.toUpperCase();
		}
	}
	
    @Transient
	abstract public String getTipoTributo();

    public void setTipoTributo(String tipoTributo) {
       // aggiunto per JPA
    }
    
    
	@Version
	@Column(name="VERSION")
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	@Column(name="OP_INSERIMENTO", nullable=false, length=40)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}
	
	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}
	
	@Column(name="EMAIL_VERSANTE")
	@EmailForm
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	
	@Transient
	public Date getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Date dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	
    @Transient
    public String getCdTrbEnte() {
    	return cdTrbEnte;
    }
    public void setCdTrbEnte(String cdTrbEnte) {
    	this.cdTrbEnte = cdTrbEnte;
    }
    
    @Transient
    public String getDenominazionEnte() {
    	return denominazioneEnte;
    }
    public void setDenominazioneEnte(String denominazioneEnte) {
    	this.denominazioneEnte = denominazioneEnte;
    }
    
    @Transient
	public String getDescTrbEnte() {
		return descTrbEnte;
	}
	public void setDescTrbEnte(String descTrbEnte) {
		this.descTrbEnte = descTrbEnte;
	}


	
	@Override
	public String toString() {
		return "TributoStrutturato [version=" + version + ", id=" + id
				+ ", cdTrbEnte=" + cdTrbEnte 
				+ ", idPendenza=" + idPendenza + ", noteVersante="
				+ noteVersante + ", cfSoggettoVersante=" + cfSoggettoVersante
				+ ", tipoTributo=" + getTipoTributo()
				+ ", opInserimento=" + opInserimento + ", tsInserimento="
				+ tsInserimento + "]";
	}
	
}
