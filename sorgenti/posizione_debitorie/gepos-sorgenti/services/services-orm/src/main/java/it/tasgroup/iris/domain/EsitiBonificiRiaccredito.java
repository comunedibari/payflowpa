package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * The persistent class for the ESITI_BONIFICI_RIACCREDITO database table.
 * 
 */
@Entity
@Table(name="ESITI_BONIFICI_RIACCREDITO")
@NamedQueries(
{
	
	@NamedQuery(
			name="getEsitiBonificiRiaccreditoById",
			query="select esiti from EsitiBonificiRiaccredito esiti "+
				  "where esiti.id =:id"),
	@NamedQuery(
		name="listEsitiBonificiRiaccreditoByIdRend",
		query="select esiti from EsitiBonificiRiaccredito esiti " +
				"where esiti.rendicontazioni.id=:idRendicontazione "),
	@NamedQuery(
		name="getEsitiBonificiRiaccreditoByIdBonifico",
		query="select esiti from EsitiBonificiRiaccredito esiti " +
				"where esiti.bonificiRiaccredito.id=:idBonificiRiaccredito ")
		}

)
public class EsitiBonificiRiaccredito extends BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String causale;
	private String codiceRiferimento;
	private Timestamp dataContabileAddebito;
	private Timestamp dataEsecuzione;
	private Timestamp dataOrdine;
	private Timestamp dataValutaBeneficiario;
	private Timestamp dataValutaOrdinante;
	private Integer flagRiconciliazione;
	private String idRiconciliazione;
	private BigDecimal importo;
	private String modalitaPagamento;
	private String opAggiornamento;
	private String opInserimento;
	private String siaOrdinante;
	private String tipoAnomalia;
	private String tipoCodiceRiferimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

	private BonificiRiaccredito bonificiRiaccredito;
	private Rendicontazioni rendicontazioni;

    public EsitiBonificiRiaccredito() {
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="esiti_bonifici_riaccredito_pk_gen")	
	@SequenceGenerator(
		    name="esiti_bonifici_riaccredito_pk_gen",
		    sequenceName="ESITI_BONIFICI_RIACCRE_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	      
	public void setId(Long id) {		
		this.id = id;	 
	} 		    

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
	public Integer getFlagRiconciliazione() {
		return this.flagRiconciliazione;
	}

	public void setFlagRiconciliazione(Integer flagRiconciliazione) {
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

	//bi-directional many-to-one association to BonificiRiaccredito
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_BONIFICI_RIACCREDITO")
	public BonificiRiaccredito getBonificiRiaccredito() {
		return this.bonificiRiaccredito;
	}

	public void setBonificiRiaccredito(BonificiRiaccredito bonificiRiaccredito) {
		this.bonificiRiaccredito = bonificiRiaccredito;
	}
	

	//bi-directional many-to-one association to Rendicontazioni
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_RENDICONTAZIONE")
	public Rendicontazioni getRendicontazioni() {
		return this.rendicontazioni;
	}

	public void setRendicontazioni(Rendicontazioni rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}
	
}