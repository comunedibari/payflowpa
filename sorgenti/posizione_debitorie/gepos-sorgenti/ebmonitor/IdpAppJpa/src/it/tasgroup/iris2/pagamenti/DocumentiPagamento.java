package it.tasgroup.iris2.pagamenti;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NamedQueries({
@NamedQuery(
	name="DocumentiPagamentoById", 
	query=
	"SELECT doc FROM DocumentiPagamento doc " +
	" WHERE doc.id = :id "),
@NamedQuery(
		name="DocumentiPagamentoByIdAndStato", 
		query=
		"SELECT doc FROM DocumentiPagamento doc " +
		" WHERE doc.id = :id "
		+ "		AND doc.stato = :stato")
//Stato, tabella documenti pagamento
//A = annullato
//P= pagato
//S = annullato sistema
//E= emesso
//Stato, tabella condizioni
//E	(?) documento emesso forse ?
//N	non pagato
//P	pagato
//X	invisibile (?)
})
/**
 * The persistent class for the DOCUMENTI_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="DOCUMENTI_PAGAMENTO")
public class DocumentiPagamento extends it.tasgroup.idp.domain.BaseStringIdEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*** Persistent Values ***/
	private Long idCfgGatewayPagamento;
	private String intestatario;
	private String opAnnullamento;
	private String stato;
	private Timestamp tsAnnullamento;
	private Timestamp tsEmissione;
	private BigDecimal importo;
	private BigDecimal importoCommissioni;
	private String emailVersante;	
	private String coPagante;	

	/*** Persistent Associations ***/
	private List<CondizioniDocumento> condizioniDocumentos;
	private DistintePagamento distintePagamento;
	
	//bi-directional many-to-one association to DocumentiRepository
	private DocumentiRepository documentiRepository;	


	/*******************************************/
	/****** Persistent Properties Mapping ******/
	/*******************************************/
	@Column(name="ID_CFG_GATEWAY_PAGAMENTO")
	public Long getIdCfgGatewayPagamento() {
		return this.idCfgGatewayPagamento;
	}

	public void setIdCfgGatewayPagamento(Long idCfgGatewayPagamento) {
		this.idCfgGatewayPagamento = idCfgGatewayPagamento;
	}


	public String getIntestatario() {
		return this.intestatario;
	}

	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}


	@Column(name="OP_ANNULLAMENTO")
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}

	
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}


	@Column(name="TS_EMISSIONE")
	public Timestamp getTsEmissione() {
		return this.tsEmissione;
	}

	public void setTsEmissione(Timestamp tsEmissione) {
		this.tsEmissione = tsEmissione;
	}


	//bi-directional many-to-one association to CondizioniDocumento
	@OneToMany(mappedBy="documentiPagamento")
	public List<CondizioniDocumento> getCondizioniDocumentos() {
		return this.condizioniDocumentos;
	}

	public void setCondizioniDocumentos(List<CondizioniDocumento> condizioniDocumentos) {
		this.condizioniDocumentos = condizioniDocumentos;
	}
	

	//bi-directional many-to-one association to DistintePagamento
    @ManyToOne
	@JoinColumn(name="ID_DISTINTA_PAGAMENTO")
	public DistintePagamento getDistintePagamento() {
		return this.distintePagamento;
	}

	public void setDistintePagamento(DistintePagamento distintePagamento) {
		this.distintePagamento = distintePagamento;
	}

	
	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	@Column(name="IMPORTO_COMMISSIONI")
	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}
	
	@Column(name="EMAIL_VERSANTE")
	public String getEmailVersante() {
		return this.emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}	
	
	@Column(name="CO_PAGANTE")
	public String getCoPagante() {
		return coPagante;
	}

	public void setCoPagante(String coPagante) {
		this.coPagante = coPagante;
	}

	@ManyToOne
	@JoinColumn(name="ID_DOCUMENTO_REPOSITORY")	
	public DocumentiRepository getDocumentiRepository() {
		return this.documentiRepository;
	}

	public void setDocumentiRepository(DocumentiRepository documentiRepository) {
		this.documentiRepository = documentiRepository;
	}	

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentiPagamento [idCfgGatewayPagamento=");
		builder.append(idCfgGatewayPagamento);
		builder.append(", intestatario=");
		builder.append(intestatario);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsEmissione=");
		builder.append(tsEmissione);
		//builder.append(", condizioniDocumentos=");
		//builder.append(condizioniDocumentos);
		//builder.append(", distintePagamento=");
		//builder.append(distintePagamento);
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

	
	
}