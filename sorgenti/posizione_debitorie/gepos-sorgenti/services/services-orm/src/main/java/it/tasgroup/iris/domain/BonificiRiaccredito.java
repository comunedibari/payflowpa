package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the BONIFICI_RIACCREDITO database table.
 * 
 */
@Entity
@Table(name="BONIFICI_RIACCREDITO")

public class BonificiRiaccredito extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String causale;
	private String codiceUnivoco;
	private Timestamp dataEsecuzione;
	private Timestamp dataValuta;
	private String flagRichiestaEsito;
	private String ibanBeneficiario;
	private String ibanOrdinante;
	private String idFiscaleOrdinante;
	private BigDecimal importo;
	private String opAggiornamento;
	private String opInserimento;
	private Integer progressivo;
	private String ragioneSocialeBeneficiario;
	private String ragioneSocialeOrdinante;
	private String riferimentoOperazione;
	private String stato;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	
	private DistinteRiaccredito distinteRiaccredito;
	
	private Set<BozzeBonificiRiaccredito> bozzeBonificiRiaccredito;
	
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="bonifici_riaccredito_pk_gen")	
	@SequenceGenerator(
		    name="bonifici_riaccredito_pk_gen",
		    sequenceName="BONIFICI_RIACCREDITO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;
	 
	}	
	
	public void setId(Long id) {		
		this.id = id;	 
	} 	

	public BonificiRiaccredito() {
    }

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


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	public Integer getProgressivo() {
		return this.progressivo;
	}

	public void setProgressivo(Integer progressivo) {
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


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	//bi-directional many-to-one association to DistinteRiaccredito
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISTINTE_RIACCREDITO")
	public DistinteRiaccredito getDistinteRiaccredito() {
		return this.distinteRiaccredito;
	}

	public void setDistinteRiaccredito(DistinteRiaccredito distinteRiaccredito) {
		this.distinteRiaccredito = distinteRiaccredito;
	}
	
	@OneToMany(mappedBy="bonificoRiaccredito",targetEntity=BozzeBonificiRiaccredito.class,fetch=FetchType.LAZY,
			cascade={CascadeType.ALL})
	public Set<BozzeBonificiRiaccredito> getBozzeBonificiRiaccredito() {
		return bozzeBonificiRiaccredito;
	}

	public void setBozzeBonificiRiaccredito(
			Set<BozzeBonificiRiaccredito> bozzeBonificiRiaccredito) {
		this.bozzeBonificiRiaccredito = bozzeBonificiRiaccredito;
	}
	
	@Transient	
	public Integer getNumBozze() {
		return bozzeBonificiRiaccredito.size();
	}
	
}