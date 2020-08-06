package it.tasgroup.iris2.pagamenti;

import it.tasgroup.cbill.domain.EsitiCbill;
import it.tasgroup.idp.gateway.EsitiNdp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
@NamedQuery(
	name="RendicontazioniOByTsInserimento", 
	query="SELECT rendicontazioni FROM Rendicontazioni rendicontazioni " +
		  "order by rendicontazioni.tsInserimento desc"),
@NamedQuery(
	name="findRendicontazioniByIdFlusso",
	query="SELECT rendicontazioni FROM Rendicontazioni rendicontazioni " +
		  "WHERE rendicontazioni.idFlusso = :idFlusso"),
@NamedQuery(
	name="findRendicontazioniByIdFlussoAndIdRegolamento",
	query="SELECT rendicontazioni FROM Rendicontazioni rendicontazioni " +
		  "WHERE rendicontazioni.idFlusso = :idFlusso " +
		  "AND rendicontazioni.idRegolamento = :idRegolamento"),
@NamedQuery(
	name="findRendicontazioniFRToProcess",
	query="SELECT rendicontazioni FROM Rendicontazioni rendicontazioni " +
		  "WHERE rendicontazioni.codRendicontazione = 'FR' " +
		  "AND rendicontazioni.stato = 'DA RICONCILIARE' and rendicontazioni.casellarioInfo.ricevemteVero NOT IN (:riceventeExcludeList) "),
@NamedQuery(
	name="findRendicontazioniFRToProcessByEnteRiceventeAndPSPMittente",
	query="SELECT rendicontazioni FROM Rendicontazioni rendicontazioni " +
		  "WHERE rendicontazioni.codRendicontazione = 'FR' " +
		  "AND rendicontazioni.stato = 'DA RICONCILIARE' " +
		  "AND rendicontazioni.casellarioInfo.ricevemteVero = :enteRicevente " +
		  "AND rendicontazioni.casellarioInfo.mittenteVero IN (:pspMittenti) "),
@NamedQuery(
		name="getMittenteRendicontazioni",
		query="SELECT rendicontazioni.casellarioInfo.mittenteVero FROM Rendicontazioni rendicontazioni " +
			  "WHERE rendicontazioni.id = :idRendicontazioni ")

})




/**
 * The persistent class for the RENDICONTAZIONI database table.
 * 
 */
@Entity
@Table(name="RENDICONTAZIONI")
public class Rendicontazioni extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String codRendicontazione;
	private Timestamp dataCreazione;
	private Timestamp dataRicezione;
	private String divisa;
	private short flagElaborazione;
	private BigDecimal importo;
	private int numEsitiInsoluto;
	private int numEsitiPagato;
	private int numeroEsiti;
	private String stato;
	private String utenteCreatore;
	private String idFlusso;
	private String idRegolamento;
	private Date dataRegolamento;
	private String bicBancaRiversamento;
	
	/*** Persistent Associations ***/
	private Set<EsitiBb> esitiBbs;
	private Set<EsitiBonificiRiaccredito> esitiBonificiRiaccreditos;
	private Set<EsitiRct> esitiRcts;
	private Set<IncassiBonificiRh> incassiBonificiRhs;
	private Set<EsitiCbill> esitiCbills;
	private Set<EsitiNdp> esitiNdps;
	private CasellarioInfo casellarioInfo;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="rendicontazioni_pk_gen")	
	@SequenceGenerator(
	    name="rendicontazioni_pk_gen",
	    sequenceName="RENDICONTAZIONI_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}		

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="COD_RENDICONTAZIONE")
	public String getCodRendicontazione() {
		return this.codRendicontazione;
	}

	public void setCodRendicontazione(String codRendicontazione) {
		this.codRendicontazione = codRendicontazione;
	}


	@Column(name="DATA_CREAZIONE")
	public Timestamp getDataCreazione() {
		return this.dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}


	@Column(name="DATA_RICEZIONE")
	public Timestamp getDataRicezione() {
		return this.dataRicezione;
	}

	public void setDataRicezione(Timestamp dataRicezione) {
		this.dataRicezione = dataRicezione;
	}


	public String getDivisa() {
		return this.divisa;
	}

	public void setDivisa(String divisa) {
		this.divisa = divisa;
	}


	@Column(name="FLAG_ELABORAZIONE")
	public short getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(short flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="NUM_ESITI_INSOLUTO")
	public int getNumEsitiInsoluto() {
		return this.numEsitiInsoluto;
	}

	public void setNumEsitiInsoluto(int numEsitiInsoluto) {
		this.numEsitiInsoluto = numEsitiInsoluto;
	}


	@Column(name="NUM_ESITI_PAGATO")
	public int getNumEsitiPagato() {
		return this.numEsitiPagato;
	}

	public void setNumEsitiPagato(int numEsitiPagato) {
		this.numEsitiPagato = numEsitiPagato;
	}


	@Column(name="NUMERO_ESITI")
	public int getNumeroEsiti() {
		return this.numeroEsiti;
	}

	public void setNumeroEsiti(int numeroEsiti) {
		this.numeroEsiti = numeroEsiti;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="UTENTE_CREATORE")
	public String getUtenteCreatore() {
		return this.utenteCreatore;
	}

	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	
	@Column(name="ID_FLUSSO")
	public String getIdFlusso() {
		return idFlusso;
	}
	
	public void setIdFlusso(String idFlusso) {
		this.idFlusso = idFlusso;
	}
	
	
	@Column(name="ID_REGOLAMENTO")
	public String getIdRegolamento() {
		return idRegolamento;
	}
	
	public void setIdRegolamento(String idRegolamento) {
		this.idRegolamento = idRegolamento;
	}
	
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_REGOLAMENTO")
	public Date getDataRegolamento() {
		return dataRegolamento;
	}
	
	public void setDataRegolamento(Date dataRegolamento) {
		this.dataRegolamento = dataRegolamento;
	}
	

	//bi-directional many-to-one association to EsitiBb
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiBb> getEsitiBbs() {
		return this.esitiBbs;
	}

	public void setEsitiBbs(Set<EsitiBb> esitiBbs) {
		this.esitiBbs = esitiBbs;
	}
	

	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiBonificiRiaccredito> getEsitiBonificiRiaccreditos() {
		return this.esitiBonificiRiaccreditos;
	}

	public void setEsitiBonificiRiaccreditos(Set<EsitiBonificiRiaccredito> esitiBonificiRiaccreditos) {
		this.esitiBonificiRiaccreditos = esitiBonificiRiaccreditos;
	}
	

	//bi-directional many-to-one association to EsitiRct
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiRct> getEsitiRcts() {
		return this.esitiRcts;
	}

	public void setEsitiRcts(Set<EsitiRct> esitiRcts) {
		this.esitiRcts = esitiRcts;
	}
	

	//bi-directional many-to-one association to IncassiBonificiRh
	@OneToMany(mappedBy="rendicontazioni")
	public Set<IncassiBonificiRh> getIncassiBonificiRhs() {
		return this.incassiBonificiRhs;
	}

	public void setIncassiBonificiRhs(Set<IncassiBonificiRh> incassiBonificiRhs) {
		this.incassiBonificiRhs = incassiBonificiRhs;
	}
	
	//bi-directional many-to-one association to EsitiCbill
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiCbill> getEsitiCbills() {
		return this.esitiCbills;
	}
	public void setEsitiCbills(Set<EsitiCbill> esitiCbills) {
		this.esitiCbills = esitiCbills;
	}	
	

	//bi-directional many-to-one association to EsitiNdp
	@OneToMany(mappedBy="rendicontazioni")
	public Set<EsitiNdp> getEsitiNdps() {
		return this.esitiNdps;
	}
	public void setEsitiNdps(Set<EsitiNdp> esitiNdps) {
		this.esitiNdps = esitiNdps;
	}	
	
	
	//bi-directional one-to-one association to CasellarioInfo
	@OneToOne
    //@ManyToOne
	@JoinColumn(name="ID_CASELLARIO_INFO")
	public CasellarioInfo getCasellarioInfo() {
		return this.casellarioInfo;
	}

	public void setCasellarioInfo(CasellarioInfo casellarioInfo) {
		this.casellarioInfo = casellarioInfo;
	}

	@Column(name="BIC_BANCA_RIVERSAMENTO")
	public String getBicBancaRiversamento() {
		return bicBancaRiversamento;
	}

	public void setBicBancaRiversamento(String bicBancaRiversamento) {
		this.bicBancaRiversamento = bicBancaRiversamento;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Rendicontazioni [codRendicontazione=");
		builder.append(codRendicontazione);
		builder.append(", dataCreazione=");
		builder.append(dataCreazione);
		builder.append(", dataRicezione=");
		builder.append(dataRicezione);
		builder.append(", divisa=");
		builder.append(divisa);
		builder.append(", flagElaborazione=");
		builder.append(flagElaborazione);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", numEsitiInsoluto=");
		builder.append(numEsitiInsoluto);
		builder.append(", numEsitiPagato=");
		builder.append(numEsitiPagato);
		builder.append(", numeroEsiti=");
		builder.append(numeroEsiti);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", utenteCreatore=");
		builder.append(utenteCreatore);
		builder.append(", idFlusso=");
		builder.append(idFlusso);
		builder.append(", idRegolamento=");
		builder.append(idRegolamento);
		builder.append(", dataRegolamento=");
		builder.append(dataRegolamento);
		builder.append(", bicBancaRiversamento=");
		builder.append(bicBancaRiversamento);
		//builder.append(", esitiBbs=");
		//builder.append(esitiBbs);
		//builder.append(", esitiBonificiRiaccreditos=");
		//builder.append(esitiBonificiRiaccreditos);
		//builder.append(", esitiRcts=");
		//builder.append(esitiRcts);
		//builder.append(", incassiBonificiRhs=");
		//builder.append(incassiBonificiRhs);
		//builder.append(", casellarioInfo=");
		//builder.append(casellarioInfo);
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
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((casellarioInfo == null) ? 0 : casellarioInfo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Rendicontazioni other = (Rendicontazioni) obj;
		if (casellarioInfo == null) {
			if (other.casellarioInfo != null) {
				return false;
			}
		} else if (!casellarioInfo.equals(other.casellarioInfo)) {
			return false;
		}
		return true;
	}

	
	
}