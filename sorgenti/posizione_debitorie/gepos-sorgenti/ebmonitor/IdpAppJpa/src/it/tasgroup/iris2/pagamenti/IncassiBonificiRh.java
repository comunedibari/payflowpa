package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="IncassiBonificiRhByIdRiconciliazione", 
	query=
	" SELECT RH FROM IncassiBonificiRh RH " +
	" WHERE RH.idRiconciliazione = :idRiconciliazione ")})

/**
 * The persistent class for the INCASSI_BONIFICI_RH database table.
 * 
 */
@Entity
@Table(name="INCASSI_BONIFICI_RH")
public class IncassiBonificiRh extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private String causale;
	private Timestamp dataContabile;
	private Timestamp dataValuta;
	private short flagRiconciliazione;
	private int idBozzeBonificiRiaccredito;
	private String idRiconciliazione;
	private BigDecimal importo;
	private int progressivo61;
	private int progressivo62;
	private String riferimentoBanca;
	private String segno;
	private String anomalia;
	private String trn;
	private String idRiconciliazioneOriginale;
	private String iban;
	
	
	/*** Persistent Associations ***/
	private Rendicontazioni rendicontazioni;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="incassi_bonifici_pk_gen")	
	@SequenceGenerator(
	    name="incassi_bonifici_pk_gen",
	    sequenceName="INCASSI_BONIFICI_RH_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}
	
	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	public String getCausale() {
		return this.causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}


	@Column(name="DATA_CONTABILE")
	public Timestamp getDataContabile() {
		return this.dataContabile;
	}

	public void setDataContabile(Timestamp dataContabile) {
		this.dataContabile = dataContabile;
	}


	@Column(name="DATA_VALUTA")
	public Timestamp getDataValuta() {
		return this.dataValuta;
	}

	public void setDataValuta(Timestamp dataValuta) {
		this.dataValuta = dataValuta;
	}


	@Column(name="FLAG_RICONCILIAZIONE")
	public short getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(short flagRiconciliazione) {
		this.flagRiconciliazione = flagRiconciliazione;
	}


	@Column(name="ID_BOZZE_BONIFICI_RIACCREDITO")
	public int getIdBozzeBonificiRiaccredito() {
		return this.idBozzeBonificiRiaccredito;
	}

	public void setIdBozzeBonificiRiaccredito(int idBozzeBonificiRiaccredito) {
		this.idBozzeBonificiRiaccredito = idBozzeBonificiRiaccredito;
	}


	@Column(name="ID_RICONCILIAZIONE")
	public String getIdRiconciliazione() {
		return this.idRiconciliazione;
	}

	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	public int getProgressivo61() {
		return this.progressivo61;
	}

	public void setProgressivo61(int progressivo61) {
		this.progressivo61 = progressivo61;
	}


	public int getProgressivo62() {
		return this.progressivo62;
	}

	public void setProgressivo62(int progressivo62) {
		this.progressivo62 = progressivo62;
	}


	@Column(name="RIFERIMENTO_BANCA")
	public String getRiferimentoBanca() {
		return this.riferimentoBanca;
	}

	public void setRiferimentoBanca(String riferimentoBanca) {
		this.riferimentoBanca = riferimentoBanca;
	}


	public String getSegno() {
		return this.segno;
	}

	public void setSegno(String segno) {
		this.segno = segno;
	}
	
	@Column(name="ANOMALIA")
	public String getAnomalia() {
		return anomalia;
	}

	public void setAnomalia(String anomalia) {
		this.anomalia = anomalia;
	}

	@Column(name="TRN")
	public String getTrn() {
		return trn;
	}

	public void setTrn(String trn) {
		this.trn = trn;
	}	

	@Column(name="ID_RICONCILIAZIONE_ORIG")
	public String getIdRiconciliazioneOriginale() {
		return idRiconciliazioneOriginale;
	}

	public void setIdRiconciliazioneOriginale(String idRiconciliazioneOriginale) {
		this.idRiconciliazioneOriginale = idRiconciliazioneOriginale;
	}
	
	@Column(name="IBAN_ACCREDITO")
	public String getIban() {
		return iban;
	}
	
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	//bi-directional many-to-one association to Rendicontazioni
    @ManyToOne
	@JoinColumn(name="ID_RENDICONTAZIONE")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IncassiBonificiRh [causale=");
		builder.append(causale);
		builder.append(", dataContabile=");
		builder.append(dataContabile);
		builder.append(", dataValuta=");
		builder.append(dataValuta);
		builder.append(", flagRiconciliazione=");
		builder.append(flagRiconciliazione);
		builder.append(", idBozzeBonificiRiaccredito=");
		builder.append(idBozzeBonificiRiaccredito);
		builder.append(", idRiconciliazione=");
		builder.append(idRiconciliazione);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", progressivo61=");
		builder.append(progressivo61);
		builder.append(", progressivo62=");
		builder.append(progressivo62);
		builder.append(", riferimentoBanca=");
		builder.append(riferimentoBanca);
		builder.append(", segno=");
		builder.append(segno);
		//builder.append(", rendicontazioni=");
		//builder.append(rendicontazioni);
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
		builder.append(", getIban()=");
		builder.append(getIban());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((idRiconciliazione == null) ? 0 : idRiconciliazione
						.hashCode());
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
		IncassiBonificiRh other = (IncassiBonificiRh) obj;
		if (idRiconciliazione == null) {
			if (other.idRiconciliazione != null) {
				return false;
			}
		} else if (!idRiconciliazione.equals(other.idRiconciliazione)) {
			return false;
		}
		return true;
	}

	
	
}