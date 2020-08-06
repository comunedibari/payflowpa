package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="BozzeBonificiRiaccreditoByFlagAndId", 
	query=
	" SELECT bozzeBonificiRiaccredito  " +
	" from BozzeBonificiRiaccredito bozzeBonificiRiaccredito " +
	" where bozzeBonificiRiaccredito.flagElaborazione = :flagElaborazione " +
	" and bozzeBonificiRiaccredito.idBonificiRiaccredito = :idBonificiRiaccredito"),
	@NamedQuery(
			name="BozzeBonificiRiaccreditoById", 
			query=
			" SELECT bozzeBonificiRiaccredito  " +
			" from BozzeBonificiRiaccredito bozzeBonificiRiaccredito " +
			" where bozzeBonificiRiaccredito.idBonificiRiaccredito = :idBonificiRiaccredito ")


})
	

/**
 * The persistent class for the BOZZE_BONIFICI_RIACCREDITO database table.
 * 
 */
@Entity
@Table(name="BOZZE_BONIFICI_RIACCREDITO")
public class BozzeBonificiRiaccredito extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/	
	private short flagElaborazione;
	private String ibanBeneficiario;
	private int idBonificiRiaccredito;
	private String idCondizione;
	private String idEnte;
	private int idEsitoOrigine;
	private BigDecimal importo;
	private String tipoEsito;
	private String ragSocialeBeneficiario;
	private String codFiscDebitore;

	private String idPagamentoCondizioneEnte;
	
	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="bozze_bonifici_riaccredito_pk_gen")	
	@SequenceGenerator(
	    name="bozze_bonifici_riaccredito_pk_gen",
	    sequenceName="BOZZE_BONIFICI_RIACCRE_ID_SEQ",
	    allocationSize=1)	
	public Long getId() {
		return super.id; 
	}	

	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/	
	@Column(name="FLAG_ELABORAZIONE")
	public short getFlagElaborazione() {
		return this.flagElaborazione;
	}

	public void setFlagElaborazione(short flagElaborazione) {
		this.flagElaborazione = flagElaborazione;
	}
	
	@Column(name="TIPO_ESITO")
	public String getTipoEsito() {
		return this.tipoEsito;
	}

	public void setTipoEsito(String tipoEsito) {
		this.tipoEsito = tipoEsito;
	}	


	@Column(name="IBAN_BENEFICIARIO")
	public String getIbanBeneficiario() {
		return this.ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}


	@Column(name="ID_BONIFICI_RIACCREDITO")
	public int getIdBonificiRiaccredito() {
		return this.idBonificiRiaccredito;
	}

	public void setIdBonificiRiaccredito(int idBonificiRiaccredito) {
		this.idBonificiRiaccredito = idBonificiRiaccredito;
	}


	@Column(name="ID_CONDIZIONE")
	public String getIdCondizione() {
		return this.idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}


	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}


	@Column(name="ID_ESITO_ORIGINE")
	public int getIdEsitoOrigine() {
		return this.idEsitoOrigine;
	}

	public void setIdEsitoOrigine(int idEsitoOrigine) {
		this.idEsitoOrigine = idEsitoOrigine;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}


	@Column(name="RAG_SOCIALE_BENEFICIARIO")
	public String getRagSocialeBeneficiario() {
		return this.ragSocialeBeneficiario;
	}

	public void setRagSocialeBeneficiario(String ragSocialeBeneficiario) {
		this.ragSocialeBeneficiario = ragSocialeBeneficiario;
	}
	
	

	@Column(name="ID_PAG_COND_ENTE")
	public String getIdPagamentoCondizioneEnte() {
		return idPagamentoCondizioneEnte;
	}

	public void setIdPagamentoCondizioneEnte(String idPagamentoCondizioneEnte) {
		this.idPagamentoCondizioneEnte = idPagamentoCondizioneEnte;
	}
	
	@Column(name="CODFISC_DEBITORE")
	public String getCodFiscDebitore() {
		return codFiscDebitore;
	}

	public void setCodFiscDebitore(String codFiscDebitore) {
		this.codFiscDebitore = codFiscDebitore;
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BozzeBonificiRiaccredito [flagElaborazione=");
		builder.append(flagElaborazione);
		builder.append(", ibanBeneficiario=");
		builder.append(ibanBeneficiario);
		builder.append(", idBonificiRiaccredito=");
		builder.append(idBonificiRiaccredito);
		builder.append(", idCondizione=");
		builder.append(idCondizione);
		builder.append(", idEnte=");
		builder.append(idEnte);
		builder.append(", idEsitoOrigine=");
		builder.append(idEsitoOrigine);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", tipoEsito=");
		builder.append(tipoEsito);
		builder.append(", ragSocialeBeneficiario=");
		builder.append(ragSocialeBeneficiario);
		builder.append(", idPagamentoCondizioneEnte=");
		builder.append(idPagamentoCondizioneEnte);
		builder.append(", codfiscDebitore=");
		builder.append(codFiscDebitore);		
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
		builder.append(", getIdPagamentoCondizioneEnte()=");
		builder.append(getIdPagamentoCondizioneEnte());
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idCondizione == null) ? 0 : idCondizione.hashCode());
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
		BozzeBonificiRiaccredito other = (BozzeBonificiRiaccredito) obj;
		if (idCondizione == null) {
			if (other.idCondizione != null) {
				return false;
			}
		} else if (!idCondizione.equals(other.idCondizione)) {
			return false;
		}
		return true;
	}

	
}