package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the ESITI_BONIFICI_RIACCREDITO database table.
 * 
 */
@Entity
@Table(name="ESITI_BONIFICI_RIACCREDITO")
public class EsitiBonificiRiaccredito extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Values ***/
	private String causale;
	private String codiceRiferimento;
	private Timestamp dataContabileAddebito;
	private Timestamp dataEsecuzione;
	private Timestamp dataOrdine;
	private Timestamp dataValutaBeneficiario;
	private Timestamp dataValutaOrdinante;
	private short flagRiconciliazione;
	private String idRiconciliazione;
	private BigDecimal importo;
	private String modalitaPagamento;
	private String siaOrdinante;
	private String tipoAnomalia;
	private String tipoCodiceRiferimento;
	
	/*** Persistent Associations ***/
	private BonificiRiaccredito bonificiRiaccredito;
	private Rendicontazioni rendicontazioni;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="esiti_bonifici_pk_gen")	
	@SequenceGenerator(
	    name="esiti_bonifici_pk_gen",
	    sequenceName="ESITI_BONIFICI_RIACCRE_ID_SEQ",
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


	@Column(name="CODICE_RIFERIMENTO")
	public String getCodiceRiferimento() {
		return this.codiceRiferimento;
	}

	public void setCodiceRiferimento(String codiceRiferimento) {
		this.codiceRiferimento = codiceRiferimento;
	}


	@Column(name="DATA_CONTABILE_ADDEBITO")
	public Timestamp getDataContabileAddebito() {
		return this.dataContabileAddebito;
	}

	public void setDataContabileAddebito(Timestamp dataContabileAddebito) {
		this.dataContabileAddebito = dataContabileAddebito;
	}


	@Column(name="DATA_ESECUZIONE")
	public Timestamp getDataEsecuzione() {
		return this.dataEsecuzione;
	}

	public void setDataEsecuzione(Timestamp dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}


	@Column(name="DATA_ORDINE")
	public Timestamp getDataOrdine() {
		return this.dataOrdine;
	}

	public void setDataOrdine(Timestamp dataOrdine) {
		this.dataOrdine = dataOrdine;
	}


	@Column(name="DATA_VALUTA_BENEFICIARIO")
	public Timestamp getDataValutaBeneficiario() {
		return this.dataValutaBeneficiario;
	}

	public void setDataValutaBeneficiario(Timestamp dataValutaBeneficiario) {
		this.dataValutaBeneficiario = dataValutaBeneficiario;
	}


	@Column(name="DATA_VALUTA_ORDINANTE")
	public Timestamp getDataValutaOrdinante() {
		return this.dataValutaOrdinante;
	}

	public void setDataValutaOrdinante(Timestamp dataValutaOrdinante) {
		this.dataValutaOrdinante = dataValutaOrdinante;
	}


	@Column(name="FLAG_RICONCILIAZIONE")
	public short getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(short flagRiconciliazione) {
		this.flagRiconciliazione = flagRiconciliazione;
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


	@Column(name="MODALITA_PAGAMENTO")
	public String getModalitaPagamento() {
		return this.modalitaPagamento;
	}

	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}


	@Column(name="SIA_ORDINANTE")
	public String getSiaOrdinante() {
		return this.siaOrdinante;
	}

	public void setSiaOrdinante(String siaOrdinante) {
		this.siaOrdinante = siaOrdinante;
	}


	@Column(name="TIPO_ANOMALIA")
	public String getTipoAnomalia() {
		return this.tipoAnomalia;
	}

	public void setTipoAnomalia(String tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
	}


	@Column(name="TIPO_CODICE_RIFERIMENTO")
	public String getTipoCodiceRiferimento() {
		return this.tipoCodiceRiferimento;
	}

	public void setTipoCodiceRiferimento(String tipoCodiceRiferimento) {
		this.tipoCodiceRiferimento = tipoCodiceRiferimento;
	}


	//bi-directional many-to-one association to BonificiRiaccredito
    @ManyToOne
	@JoinColumn(name="ID_BONIFICI_RIACCREDITO")
	public BonificiRiaccredito getBonificiRiaccredito() {
		return this.bonificiRiaccredito;
	}

	public void setBonificiRiaccredito(BonificiRiaccredito bonificiRiaccredito) {
		this.bonificiRiaccredito = bonificiRiaccredito;
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
		builder.append("EsitiBonificiRiaccredito [causale=");
		builder.append(causale);
		builder.append(", codiceRiferimento=");
		builder.append(codiceRiferimento);
		builder.append(", dataContabileAddebito=");
		builder.append(dataContabileAddebito);
		builder.append(", dataEsecuzione=");
		builder.append(dataEsecuzione);
		builder.append(", dataOrdine=");
		builder.append(dataOrdine);
		builder.append(", dataValutaBeneficiario=");
		builder.append(dataValutaBeneficiario);
		builder.append(", dataValutaOrdinante=");
		builder.append(dataValutaOrdinante);
		builder.append(", flagRiconciliazione=");
		builder.append(flagRiconciliazione);
		builder.append(", idRiconciliazione=");
		builder.append(idRiconciliazione);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", modalitaPagamento=");
		builder.append(modalitaPagamento);
		builder.append(", siaOrdinante=");
		builder.append(siaOrdinante);
		builder.append(", tipoAnomalia=");
		builder.append(tipoAnomalia);
		builder.append(", tipoCodiceRiferimento=");
		builder.append(tipoCodiceRiferimento);
		//builder.append(", bonificiRiaccredito=");
		//builder.append(bonificiRiaccredito);
		builder.append(", rendicontazioni=");
		builder.append(rendicontazioni);
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
		EsitiBonificiRiaccredito other = (EsitiBonificiRiaccredito) obj;
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