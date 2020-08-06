package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@NamedQueries({
@NamedQuery(
	name="CasellarioInfoAll", 
	query=
	" SELECT casellarioInfo FROM CasellarioInfo casellarioInfo " +
	" order by casellarioInfo.tsInserimento desc "),	
@NamedQuery(
	name="CasellarioInfoByFlagElaborazione", 
	query=
	" SELECT casellarioInfo FROM CasellarioInfo casellarioInfo " +
	" WHERE casellarioInfo.flagElaborazione = :flagElaborazione "  +
	" order by casellarioInfo.tsInserimento desc "),
@NamedQuery(
		name="CasellarioInfoByNomeSupporto", 
		query = " SELECT casellarioInfo FROM CasellarioInfo casellarioInfo" +
				" WHERE casellarioInfo.nomeSupporto = :nomeSupporto")
})

/**
 * The persistent class for the CASELLARIO_INFO database table.
 * 
 */
@Entity
@Table(name="CASELLARIO_INFO")
public class CasellarioInfo extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String idSupporto;	
	private Timestamp dataElaborazione;
	private String descErrore;
	private int dimensione;
	private short flagElaborazione;
	private byte[] flussoCbi;
	private String nomeSupporto;
	private int numeroRecord;
	private short tipoErrore;
	private String tipoFlusso;
	private String mittenteVero;
	private String ricevemteVero;
	private String nomeFile;

	/*** Persistent Associations ***/
	private Rendicontazioni rendicontazioni;

	/*** Transient Properties ***/
	private String mittente;
	private String ricevente;
	private String dataCreazione;
	private String IUR;
	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="casellario_info_pk_gen")	
	@SequenceGenerator(
	    name="casellario_info_pk_gen",
	    sequenceName="CASELLARIO_INFO_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ID_SUPPORTO", nullable=false, insertable=true, updatable=false, unique=true) 
	public String getIdSupporto() {
		return this.idSupporto;
	}
	
	public void setIdSupporto(String idSupporto) {
		this.idSupporto = idSupporto;
	}

	@Column(name="DATA_ELABORAZIONE")
	public Timestamp getDataElaborazione() {
		return this.dataElaborazione;
	}

	public void setDataElaborazione(Timestamp dataElaborazione) {
		this.dataElaborazione = dataElaborazione;
	}


	@Column(name="DESC_ERRORE")
	public String getDescErrore() {
		return this.descErrore;
	}

	public void setDescErrore(String descErrore) {
		this.descErrore = descErrore;
	}


	public int getDimensione() {
		return this.dimensione;
	}

	public void setDimensione(int dimensione) {
		this.dimensione = dimensione;
	}


	@Column(name="FLAG_ELABORAZIONE")
	public short getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(short flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}


    @Lob()
	@Column(name="FLUSSO_CBI")
	public byte[] getFlussoCbi() {
		return this.flussoCbi;
	}

	public void setFlussoCbi(byte[] flussoCbi) {
		this.flussoCbi = flussoCbi;
	}


	@Column(name="NOME_SUPPORTO")
	public String getNomeSupporto() {
		return this.nomeSupporto;
	}

	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}


	@Column(name="NUMERO_RECORD")
	public int getNumeroRecord() {
		return this.numeroRecord;
	}

	public void setNumeroRecord(int numeroRecord) {
		this.numeroRecord = numeroRecord;
	}
	

	@Column(name="TIPO_ERRORE")
	public short getTipoErrore() {
		return this.tipoErrore;
	}

	public void setTipoErrore(short tipoErrore) {
		this.tipoErrore = tipoErrore;
	}


	@Column(name="TIPO_FLUSSO")
	public String getTipoFlusso() {
		return this.tipoFlusso;
	}

	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}
	
	
	@OneToOne(mappedBy="casellarioInfo")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}

	/*** Transient Getters/Setters ***/
	@Transient
	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	@Transient
	public String getRicevente() {
		return ricevente;
	}

	public void setRicevente(String ricevente) {
		this.ricevente = ricevente;
	}

	@Transient
	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}
		
	@Transient
	public String getIUR() {
		return IUR;
	}

	public void setIUR(String iUR) {
		IUR = iUR;
	}	
	
	/*** LifeCycle CallBacks ***/
	/*** Composite property to ensure the unicity of CBI record ***/
	@PrePersist
	public void ensureIdSupporto() {
//		//se idSupporto non � valorizzato
//		//allora idSupporto lo creo con la concatenzione dei campi 
//		if (getIUR()!=null) {
//			//ok allora utilizzo lo IUR
//			//proviene da gestione nodo pagamenti
//			this.setIdSupporto(this.getIUR());			
//		} 
//
//		//se dopo il set di IUR � ancora vuoto
//		//allora metto valore standard
//		if (getIdSupporto()==null) {
//			StringBuilder sb = new StringBuilder();
//			sb.append(this.getTipoFlusso()).
//				append(this.getMittente()).
//				append(this.getRicevente()).
//				append(this.getDataCreazione()).
//				append(this.getNomeSupporto());
//			this.setIdSupporto(sb.toString());					
//		}
		
		if (this.getIdSupporto()==null) {
			StringBuilder sb = new StringBuilder();
			sb.append(this.getTipoFlusso()).
				append(this.getMittente()).
				append(this.getRicevente()).
				append(this.getDataCreazione()).
				append(this.getNomeSupporto());
			this.setIdSupporto(sb.toString());
		}
		
	}
	

	/*** Overriding Methods ***/
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CasellarioInfo [idSupporto=");
		builder.append(idSupporto);
		builder.append(", dataElaborazione=");
		builder.append(dataElaborazione);
		builder.append(", descErrore=");
		builder.append(descErrore);
		builder.append(", dimensione=");
		builder.append(dimensione);
		builder.append(", flagElaborazione=");
		builder.append(flagElaborazione);
		builder.append(", flussoCbi=");
		builder.append(Arrays.toString(flussoCbi));
		builder.append(", nomeSupporto=");
		builder.append(nomeSupporto);
		builder.append(", numeroRecord=");
		builder.append(numeroRecord);
		builder.append(", tipoErrore=");
		builder.append(tipoErrore);
		builder.append(", tipoFlusso=");
		builder.append(tipoFlusso);
		//builder.append(", rendicontazioni=");
		//builder.append(rendicontazioni);
		builder.append(", mittente=");
		builder.append(mittente);
		builder.append(", ricevente=");
		builder.append(ricevente);
		builder.append(", mittenteVero=");
		builder.append(mittenteVero);
		builder.append(", ricevemteVero=");
		builder.append(ricevemteVero);
		builder.append(", dataCreazione=");
		builder.append(dataCreazione);
		builder.append(", getId()=");
		builder.append(getId());
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append(", getOpInserimento()=");
		builder.append(getOpInserimento());
		builder.append(", getOpAggiornamento()=");
		builder.append(getOpAggiornamento());
		builder.append(", getTsInserimento()=");
		builder.append(getTsInserimento());
		builder.append(", getTsAggiornamento()=");
		builder.append(getTsAggiornamento());
		builder.append(", nomeFile=");
		builder.append(nomeFile);
		builder.append("]");
		return builder.toString();
	}

	@Column(name="MITTENTE")
	public String getMittenteVero() {
		return mittenteVero;
	}

	public void setMittenteVero(String mittenteVero) {
		this.mittenteVero = mittenteVero;
	}

	@Column(name="RICEVENTE")
	public String getRicevemteVero() {
		return ricevemteVero;
	}
 
	public void setRicevemteVero(String ricevemteVero) {
		this.ricevemteVero = ricevemteVero;
	}
	
	@Column(name="NOME_FILE")
	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}



}