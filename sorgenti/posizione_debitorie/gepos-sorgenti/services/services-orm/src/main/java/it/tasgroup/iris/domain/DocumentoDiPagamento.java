package it.tasgroup.iris.domain;

import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="DOCUMENTI_PAGAMENTO")
@NamedQueries(
{	
	@NamedQuery(
		name="countByIdAndStatus",
		query="select count(docs) from DocumentoDiPagamento docs " +
				"where docs.id=:codiceAutorizzazione and docs.stato=:statoDocumento ")
	}
)
//getDocumentoDiPagamentoByIdDocumento inserita per problemi con i pagamenti 
public class DocumentoDiPagamento extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/*** Persistent Properties ***/
	private String id;
	private String opAggiornamento;
	private String opAnnullamento;
	private String opInserimento;
	private String intestatario;

	private String stato;	
	
	private Timestamp tsAggiornamento;
	private Timestamp tsAnnullamento;
	private Timestamp tsEmissione;
	private Timestamp tsInserimento;
	
	private BigDecimal importo;
	
	private BigDecimal importoCommissioni;
	
	private GestioneFlussi distinta;
	
	private CfgGatewayPagamento cfgGatewayPagamento;
	
	/*** Persistent Collections ***/
	private Set<CondizioneDocumento> condizioni;
	
	private Long idDocumentoRepository;
	
	private Date dtScadenzaDoc;
	
	private String codFiscalePagante;
	
	private String emailVersante;

	@Id
	@Column(unique=true, nullable=false, length=82)
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}

	@Column(name="OP_AGGIORNAMENTO", length=160)
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}
	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	@Column(name="OP_ANNULLAMENTO", length=160)
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}
	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}


	@Column(name="OP_INSERIMENTO", nullable=false, length=40)
	public String getOpInserimento() {
		return this.opInserimento;
	}
	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	@Column(nullable=false, length=2)
	public String getStato() {
		return this.stato;
	}
	
	@Transient
	public EnumStatoDDP getStatoEnum() {
		return EnumStatoDDP.getByKey(this.stato);
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	@Transient
	public void setStatoEnum(EnumStatoDDP statoEnum) {
		this.stato = statoEnum.getChiave();
	}
	
	@Transient
	public EnumTipoDDP getTipoDocumentoEnum() {
		
//		String chiaveBundleTipoDoc = getCfgGatewayPagamento().getCfgDocumentoPagamento().getBundleKey();
//		return EnumTipoDDP.getByChiaveBundle(chiaveBundleTipoDoc);
		Long chiaveTipoDoc = getCfgGatewayPagamento().getCfgDocumentoPagamento().getId();
		return EnumTipoDDP.getByKey(Long.toString(chiaveTipoDoc));
		
	}
	
	@Transient
	public CfgDocumentoPagamento getCfgTipoDocumentoPagamento() {
		return getCfgGatewayPagamento().getCfgDocumentoPagamento();
	}
	
	@Transient
	public Set<CfgCommissionePagamento> getCfgCommissioniPagamenti() {
		return getCfgGatewayPagamento().getCfgCommissionePagamenti();
	}

	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}
	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	@Column(name="TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}
	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}

	@Column(name="TS_EMISSIONE", nullable=false)
	public Timestamp getTsEmissione() {
		return this.tsEmissione;
	}
	public void setTsEmissione(Timestamp tsEmissione) {
		this.tsEmissione = tsEmissione;
	}

	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}
	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	//bi-directional many-to-one association to CondizioneDocumento
	@OneToMany(mappedBy="documento", cascade={CascadeType.ALL})
	public Set<CondizioneDocumento> getCondizioni() {
		return this.condizioni;
	}
	public void setCondizioni(Set<CondizioneDocumento> condizioni) {
		this.condizioni = condizioni;
	}
	
	public String getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	
	@ManyToOne(targetEntity=CfgGatewayPagamento.class,fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_GATEWAY_PAGAMENTO")
	public CfgGatewayPagamento getCfgGatewayPagamento() {
		return cfgGatewayPagamento;
	}
	public void setCfgGatewayPagamento(CfgGatewayPagamento cfgGatewayPagamento) {
		this.cfgGatewayPagamento = cfgGatewayPagamento;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentoDiPagamento other = (DocumentoDiPagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DocumentoDiPagamento [id=");
		builder.append(id);
		builder.append(", opAggiornamento=");
		builder.append(opAggiornamento);
		builder.append(", opAnnullamento=");
		builder.append(opAnnullamento);
		builder.append(", opInserimento=");
		builder.append(opInserimento);
		builder.append(", stato=");
		builder.append(stato);
		builder.append(", tipo=");
		builder.append(", intestatario=");
		builder.append(intestatario);
		builder.append(", tsAggiornamento=");
		builder.append(tsAggiornamento);
		builder.append(", tsAnnullamento=");
		builder.append(tsAnnullamento);
		builder.append(", tsEmissione=");
		builder.append(tsEmissione);
		builder.append(", tsInserimento=");
		builder.append(tsInserimento);
		builder.append(", condizioni=");
		builder.append(condizioni);
		builder.append(", getVersion()=");
		builder.append(getVersion());
		builder.append("]");
		return builder.toString();
	}

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_DISTINTA_PAGAMENTO")
	public GestioneFlussi getDistinta() {
		return distinta;
	}
	public void setDistinta(GestioneFlussi distinta) {
		this.distinta = distinta;
	}
	
	@Transient
	public Integer getNumCondizioni(){
		
		return condizioni.size();
		
	}
	
	@Column(name="IMPORTO")
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
	
	@Column(name="ID_DOCUMENTO_REPOSITORY")
	public Long getIdDocumentoRepository() {
		return idDocumentoRepository;
	}
	public void setIdDocumentoRepository(Long idDocumentoRepository) {
		this.idDocumentoRepository = idDocumentoRepository;
	}
	
	@Temporal( TemporalType.DATE)
	@Column(name="DT_SCADENZA_DOC")
	public Date getDtScadenzaDoc() {
		return dtScadenzaDoc;
	}
	
	public void setDtScadenzaDoc(Date dtScadenzaDoc) {
		this.dtScadenzaDoc = dtScadenzaDoc;
	}
	
	@Column(name="CO_PAGANTE")
	public String getCodFiscalePagante() {
		return codFiscalePagante;
	}
	
	public void setCodFiscalePagante(String codFiscalePagante) {
		this.codFiscalePagante = codFiscalePagante;
	}
	
	@Column(name="EMAIL_VERSANTE", nullable=false)	
	public String getEmailVersante() {
		return emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}
	
	public static class TsEmissioneComparatorAsc implements Comparator<DocumentoDiPagamento>{

		@Override
		public int compare(DocumentoDiPagamento doc0, DocumentoDiPagamento doc1) {
			
			return doc0.tsEmissione.compareTo(doc1.tsEmissione);
		}
		
	}
	
	public static class TsEmissioneComparatorDesc implements Comparator<DocumentoDiPagamento>{

		@Override
		public int compare(DocumentoDiPagamento doc0, DocumentoDiPagamento doc1) {
			
			return doc1.tsEmissione.compareTo(doc0.tsEmissione);
		}
		
	}
}