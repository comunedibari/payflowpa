package it.tasgroup.iris2.pagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the BONIFICI_RIACCREDITO database table.
 *
 */
@Entity
@Table(name="BONIFICI_RIACCREDITO")
public class BonificiRiaccredito extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/	
	private String causale;
	private String codiceUnivoco;
	private Timestamp dataEsecuzione;
	private Timestamp dataValuta;
	private String flagRichiestaEsito;
	private String ibanBeneficiario;
	private String ibanOrdinante;
	private String idFiscaleOrdinante;
	private BigDecimal importo;
	private int progressivo;
	private String ragioneSocialeBeneficiario;
	private String ragioneSocialeOrdinante;
	private String riferimentoOperazione;
	private String stato;
	
	/*** Persistent Associations ***/
	private DistinteRiaccredito distinteRiaccredito;
	private Set<EsitiBonificiRiaccredito> esitiBonificiRiaccreditos;

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(
			strategy=GenerationType.AUTO,
			generator="bonifici_riaccredito_pk_gen")	
	@SequenceGenerator(
	    name="bonifici_riaccredito_pk_gen",
	    sequenceName="BONIFICI_RIACCREDITO_ID_SEQ",
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


	@Column(name="CODICE_UNIVOCO")
	public String getCodiceUnivoco() {
		return this.codiceUnivoco;
	}

	public void setCodiceUnivoco(String codiceUnivoco) {
		this.codiceUnivoco = codiceUnivoco;
	}


	@Column(name="DATA_ESECUZIONE")
	public Timestamp getDataEsecuzione() {
		return this.dataEsecuzione;
	}

	public void setDataEsecuzione(Timestamp dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}


	@Column(name="DATA_VALUTA")
	public Timestamp getDataValuta() {
		return this.dataValuta;
	}

	public void setDataValuta(Timestamp dataValuta) {
		this.dataValuta = dataValuta;
	}


	@Column(name="FLAG_RICHIESTA_ESITO")
	public String getFlagRichiestaEsito() {
		return this.flagRichiestaEsito;
	}

	public void setFlagRichiestaEsito(String flagRichiestaEsito) {
		this.flagRichiestaEsito = flagRichiestaEsito;
	}


	@Column(name="IBAN_BENEFICIARIO")
	public String getIbanBeneficiario() {
		return this.ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}


	@Column(name="IBAN_ORDINANTE")
	public String getIbanOrdinante() {
		return this.ibanOrdinante;
	}

	public void setIbanOrdinante(String ibanOrdinante) {
		this.ibanOrdinante = ibanOrdinante;
	}


	@Column(name="ID_FISCALE_ORDINANTE")
	public String getIdFiscaleOrdinante() {
		return this.idFiscaleOrdinante;
	}

	public void setIdFiscaleOrdinante(String idFiscaleOrdinante) {
		this.idFiscaleOrdinante = idFiscaleOrdinante;
	}


	public BigDecimal getImporto() {
		return this.importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public int getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(int progressivo) {
		this.progressivo = progressivo;
	}


	@Column(name="RAGIONE_SOCIALE_BENEFICIARIO")
	public String getRagioneSocialeBeneficiario() {
		return this.ragioneSocialeBeneficiario;
	}

	public void setRagioneSocialeBeneficiario(String ragioneSocialeBeneficiario) {
		this.ragioneSocialeBeneficiario = ragioneSocialeBeneficiario;
	}


	@Column(name="RAGIONE_SOCIALE_ORDINANTE")
	public String getRagioneSocialeOrdinante() {
		return this.ragioneSocialeOrdinante;
	}

	public void setRagioneSocialeOrdinante(String ragioneSocialeOrdinante) {
		this.ragioneSocialeOrdinante = ragioneSocialeOrdinante;
	}


	@Column(name="RIFERIMENTO_OPERAZIONE")
	public String getRiferimentoOperazione() {
		return this.riferimentoOperazione;
	}

	public void setRiferimentoOperazione(String riferimentoOperazione) {
		this.riferimentoOperazione = riferimentoOperazione;
	}


	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	//bi-directional many-to-one association to DistinteRiaccredito
    @ManyToOne(cascade=CascadeType.PERSIST)
	@JoinColumn(name="ID_DISTINTE_RIACCREDITO")
	public DistinteRiaccredito getDistinteRiaccredito() {
		return this.distinteRiaccredito;
	}

	public void setDistinteRiaccredito(DistinteRiaccredito distinteRiaccredito) {
		this.distinteRiaccredito = distinteRiaccredito;
	}


	//bi-directional many-to-one association to EsitiBonificiRiaccredito
	@OneToMany(mappedBy="bonificiRiaccredito")
	public Set<EsitiBonificiRiaccredito> getEsitiBonificiRiaccreditos() {
		return this.esitiBonificiRiaccreditos;
	}

	public void setEsitiBonificiRiaccreditos(Set<EsitiBonificiRiaccredito> esitiBonificiRiaccreditos) {
		this.esitiBonificiRiaccreditos = esitiBonificiRiaccreditos;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BonificiRiaccredito [causale=");
		builder.append(causale);
		builder.append(", codiceUnivoco=");
		builder.append(codiceUnivoco);
		builder.append(", dataEsecuzione=");
		builder.append(dataEsecuzione);
		builder.append(", dataValuta=");
		builder.append(dataValuta);
		builder.append(", flagRichiestaEsito=");
		builder.append(flagRichiestaEsito);
		builder.append(", ibanBeneficiario=");
		builder.append(ibanBeneficiario);
		builder.append(", ibanOrdinante=");
		builder.append(ibanOrdinante);
		builder.append(", idFiscaleOrdinante=");
		builder.append(idFiscaleOrdinante);
		builder.append(", importo=");
		builder.append(importo);
		builder.append(", progressivo=");
		builder.append(progressivo);
		builder.append(", ragioneSocialeBeneficiario=");
		builder.append(ragioneSocialeBeneficiario);
		builder.append(", ragioneSocialeOrdinante=");
		builder.append(ragioneSocialeOrdinante);
		builder.append(", riferimentoOperazione=");
		builder.append(riferimentoOperazione);
		builder.append(", stato=");
		builder.append(stato);
		//builder.append(", distinteRiaccredito=");
		//builder.append(distinteRiaccredito);
		//builder.append(", esitiBonificiRiaccreditos=");
		//builder.append(esitiBonificiRiaccreditos);
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
				+ ((codiceUnivoco == null) ? 0 : codiceUnivoco.hashCode());
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
		BonificiRiaccredito other = (BonificiRiaccredito) obj;
		if (codiceUnivoco == null) {
			if (other.codiceUnivoco != null) {
				return false;
			}
		} else if (!codiceUnivoco.equals(other.codiceUnivoco)) {
			return false;
		}
		return true;
	}

	
}