package it.tasgroup.idp.gateway;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the CFG_GATEWAY_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name = "CFG_GATEWAY_PAGAMENTO")
@NamedQueries({
		@NamedQuery(name = "getcfByBundleKey", query = "select cf from CfgGatewayPagamento cf "
				+ "where cf.bundleKey =:inBundleKey"),
				
		@NamedQuery(name = "getCfgBySystemIdAndApplicationId", query = "select cf from CfgGatewayPagamento cf "
				+ "where cf.applicationId =:inApplicationId " + "and cf.systemId =:inSystemId ", hints = { @QueryHint(name = "org.hibernate.cacheable", value = "true") }),

		@NamedQuery(name = "getCfgGatewayPagamentoAll", query = "select cf from CfgGatewayPagamento cf "
				+ "where cf.stRiga =:stRiga " + "order by cf.cfgFornitoreGateway.descrizione desc  "),

		@NamedQuery(name = "getCfgGatewayPagamentoById", query = "select cf from CfgGatewayPagamento cf "
				+ "where cf.id =:id"),
				
		@NamedQuery(name = "getCfGatewayPagamentoListByStato", query = "select cgp from CfgGatewayPagamento cgp "
				+ "where cgp.stato = :stato " + "and cgp.dataInizioValidita <= :dtInizioValidita "
				+ "and cgp.dataFineValidita >= :dtFineValidita " + "and cgp.stRiga = 'V' "
				+ "and cgp.cfgModalitaPagamento is not null"),
				
		@NamedQuery(name = "getCfGatewayPagamentoListByStatoAndSysIdAndAppId", query = "select cgp from CfgGatewayPagamento cgp "
				+ "where cgp.stato = :stato "
				+ "and cgp.dataInizioValidita <= :dtInizioValidita "
				+ "and cgp.dataFineValidita >= :dtFineValidita "
				+ "and cgp.systemId = :sysId "
				+ "and cgp.applicationId = :appId "
				+ "and cgp.stRiga = 'V' "
				+ "and cgp.cfgModalitaPagamento is not null"),
				
		@NamedQuery(name = "getCfGatewayPagamentoListByFornitoreGateway", query = "select cgp from CfgGatewayPagamento cgp "
				+ "where cgp.cfgFornitoreGateway.id =:idFornitoreGateway "),
				
		@NamedQuery(name = "getCfGatewayPagamentoListByFornitoreGatewayExcludeMyAvvisoPagoPA", query = "select cgp from CfgGatewayPagamento cgp "
				+ "where cgp.cfgFornitoreGateway.id =:idFornitoreGateway "
				+ "and cgp.bundleKey <> 'AVVISO-PAGOPA' ")
})
// Disabilitare durante l'esecuzione dei test JUnit
// @Cache(usage=CacheConcurrencyStrategy.TRANSACTIONAL)
// Condizionare la cache in funzione dell'ambiente
public class CfgGatewayPagamento extends it.tasgroup.idp.domain.BaseIdEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String applicationIp;
	private String bundleKey;
	private Timestamp dataFineValidita;
	private Timestamp dataInizioValidita;
	private Timestamp dataPubblicazione;
	private String descGateway;
	private String descrizione;
	private String disponibilitaServizio;
	private BigDecimal maxImporto;
	private String molteplicita;
	private String opAnnullamento;
	private String priorita;
	private String stRiga;
	private String stato;
	private String subsystemId;
	private String systemId;
	private String systemName;
	private Timestamp tsAnnullamento;

	private List<CfgCommissionePagamento> cfgCommissionePagamentos;
	private CfgCanalePagamento cfgCanalePagamento;
	private CfgDocumentoPagamento cfgDocumentoPagamento;
	private CfgFornitoreGateway cfgFornitoreGateway;
	private CfgModalitaPagamento cfgModalitaPagamento;
	private CfgStrumentoPagamento cfgStrumentoPagamento;

	private String flPagabileIris = "Y"; // DEFAULT
	private String urlInfoPsp;
	private String urlInfoCanale;

	private String flagModRiversamento = "D";
	private Long idContotecnico;
	private String flagRendVersamento;
	private String modelloVersamento;
	private String flStornoGestito = "N"; // DEFAULT

	private String tipoVersamento;
	private String flMdbGestito;
	
	public CfgGatewayPagamento() {
	}

	/*** Id Mapping ***/
	/*** AUTO, works on DB2 with Identity ***/
	/*** AUTO + seqGenerator, works on Oracle with Sequences ***/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "cfg_gateway_pagamento_pk_gen")
	@SequenceGenerator(name = "cfg_gateway_pagamento_pk_gen", sequenceName = "CFG_GATEWAY_PAGAMENTO_ID_SEQ", allocationSize = 1)
	public Long getId() {
		return super.id;
	}

	@Column(name = "APPLICATION_ID")
	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	@Column(name = "APPLICATION_IP")
	public String getApplicationIp() {
		return this.applicationIp;
	}

	public void setApplicationIp(String applicationIp) {
		this.applicationIp = applicationIp;
	}

	@Column(name = "BUNDLE_KEY")
	public String getBundleKey() {
		return this.bundleKey;
	}

	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}

	@Column(name = "DATA_FINE_VALIDITA")
	public Timestamp getDataFineValidita() {
		return this.dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	@Column(name = "DATA_INIZIO_VALIDITA")
	public Timestamp getDataInizioValidita() {
		return this.dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	@Column(name = "DATA_PUBBLICAZIONE")
	public Timestamp getDataPubblicazione() {
		return this.dataPubblicazione;
	}

	public void setDataPubblicazione(Timestamp dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	@Column(name = "DESC_GATEWAY")
	public String getDescGateway() {
		return this.descGateway;
	}

	public void setDescGateway(String descGateway) {
		this.descGateway = descGateway;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "DISPONIBILITA_SERVIZIO")
	public String getDisponibilitaServizio() {
		return this.disponibilitaServizio;
	}

	public void setDisponibilitaServizio(String disponibilitaServizio) {
		this.disponibilitaServizio = disponibilitaServizio;
	}

	@Column(name = "MAX_IMPORTO")
	public BigDecimal getMaxImporto() {
		return this.maxImporto;
	}

	public void setMaxImporto(BigDecimal maxImporto) {
		this.maxImporto = maxImporto;
	}

	public String getMolteplicita() {
		return this.molteplicita;
	}

	public void setMolteplicita(String molteplicita) {
		this.molteplicita = molteplicita;
	}

	@Column(name = "OP_ANNULLAMENTO")
	public String getOpAnnullamento() {
		return this.opAnnullamento;
	}

	public void setOpAnnullamento(String opAnnullamento) {
		this.opAnnullamento = opAnnullamento;
	}

	public String getPriorita() {
		return this.priorita;
	}

	public void setPriorita(String priorita) {
		this.priorita = priorita;
	}

	@Column(name = "ST_RIGA")
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	@Column(name = "SUBSYSTEM_ID")
	public String getSubsystemId() {
		return this.subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}

	@Column(name = "SYSTEM_ID")
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Column(name = "SYSTEM_NAME")
	public String getSystemName() {
		return this.systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	@Column(name = "TS_ANNULLAMENTO")
	public Timestamp getTsAnnullamento() {
		return this.tsAnnullamento;
	}

	public void setTsAnnullamento(Timestamp tsAnnullamento) {
		this.tsAnnullamento = tsAnnullamento;
	}

	@Column(name = "FL_PAGABILE_IRIS", nullable = false)
	public String getFlPagabileIris() {
		return flPagabileIris;
	}

	public void setFlPagabileIris(String flPagabileIris) {
		this.flPagabileIris = flPagabileIris;
	}

	@Column(name = "URL_INFO_PSP")
	public String getUrlInfoPsp() {
		return urlInfoPsp;
	}

	public void setUrlInfoPsp(String urlInfoPsp) {
		this.urlInfoPsp = urlInfoPsp;
	}

	@Column(name = "URL_INFO_CANALE")
	public String getUrlInfoCanale() {
		return urlInfoCanale;
	}

	public void setUrlInfoCanale(String urlInfoCanale) {
		this.urlInfoCanale = urlInfoCanale;
	}

	@Column(name = "FLAG_MOD_RIVERSAMENTO")
	public String getFlagModRiversamento() {
		return flagModRiversamento;
	}

	public void setFlagModRiversamento(String flagModRiversamento) {
		this.flagModRiversamento = flagModRiversamento;
	}

	@Column(name = "ID_CONTOTECNICO")
	public Long getIdContotecnico() {
		return idContotecnico;
	}

	public void setIdContotecnico(Long idContotecnico) {
		this.idContotecnico = idContotecnico;
	}

	@Column(name = "FLAG_REND_RIVERSAMENTO")
	public String getFlagRendVersamento() {
		return flagRendVersamento;
	}

	public void setFlagRendVersamento(String flagRendVersamento) {
		this.flagRendVersamento = flagRendVersamento;
	}

	@Column(name = "MODELLO_VERSAMENTO")
	public String getModelloVersamento() {
		return modelloVersamento;
	}

	public void setModelloVersamento(String modelloVersamento) {
		this.modelloVersamento = modelloVersamento;
	}

	@Column(name = "FL_STORNO_GESTITO")
	public String getFlStornoGestito() {
		return flStornoGestito;
	}

	public void setFlStornoGestito(String flStornoGestito) {
		this.flStornoGestito = flStornoGestito;
	}

	
	// bi-directional many-to-one association to CfgCommissionePagamento
	@OneToMany(mappedBy = "cfgGatewayPagamento")
	public List<CfgCommissionePagamento> getCfgCommissionePagamentos() {
		return this.cfgCommissionePagamentos;
	}

	public void setCfgCommissionePagamentos(List<CfgCommissionePagamento> cfgCommissionePagamentos) {
		this.cfgCommissionePagamentos = cfgCommissionePagamentos;
	}

	public CfgCommissionePagamento addCfgCommissionePagamento(CfgCommissionePagamento cfgCommissionePagamento) {
		getCfgCommissionePagamentos().add(cfgCommissionePagamento);
		cfgCommissionePagamento.setCfgGatewayPagamento(this);

		return cfgCommissionePagamento;
	}

	public CfgCommissionePagamento removeCfgCommissionePagamento(CfgCommissionePagamento cfgCommissionePagamento) {
		getCfgCommissionePagamentos().remove(cfgCommissionePagamento);
		cfgCommissionePagamento.setCfgGatewayPagamento(null);

		return cfgCommissionePagamento;
	}

	// bi-directional many-to-one association to CfgCanalePagamento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CFG_CANALE_PAGAMENTO")
	public CfgCanalePagamento getCfgCanalePagamento() {
		return this.cfgCanalePagamento;
	}

	public void setCfgCanalePagamento(CfgCanalePagamento cfgCanalePagamento) {
		this.cfgCanalePagamento = cfgCanalePagamento;
	}

	// bi-directional many-to-one association to CfgDocumentoPagamento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CFG_DOCUMENTO_PAGAMENTO")
	public CfgDocumentoPagamento getCfgDocumentoPagamento() {
		return this.cfgDocumentoPagamento;
	}

	public void setCfgDocumentoPagamento(CfgDocumentoPagamento cfgDocumentoPagamento) {
		this.cfgDocumentoPagamento = cfgDocumentoPagamento;
	}

	// bi-directional many-to-one association to CfgFornitoreGateway
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CFG_FORNITORE_GATEWAY")
	public CfgFornitoreGateway getCfgFornitoreGateway() {
		return this.cfgFornitoreGateway;
	}

	public void setCfgFornitoreGateway(CfgFornitoreGateway cfgFornitoreGateway) {
		this.cfgFornitoreGateway = cfgFornitoreGateway;
	}

	// bi-directional many-to-one association to CfgModalitaPagamento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CFG_MODALITA_PAGAMENTO")
	public CfgModalitaPagamento getCfgModalitaPagamento() {
		return this.cfgModalitaPagamento;
	}

	public void setCfgModalitaPagamento(CfgModalitaPagamento cfgModalitaPagamento) {
		this.cfgModalitaPagamento = cfgModalitaPagamento;
	}

	// bi-directional many-to-one association to CfgStrumentoPagamento
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CFG_STRUMENTO_PAGAMENTO")
	public CfgStrumentoPagamento getCfgStrumentoPagamento() {
		return this.cfgStrumentoPagamento;
	}

	public void setCfgStrumentoPagamento(CfgStrumentoPagamento cfgStrumentoPagamento) {
		this.cfgStrumentoPagamento = cfgStrumentoPagamento;
	}


	@Column(name = "TIPO_VERSAMENTO")
	public String getTipoVersamento() {
		return tipoVersamento;
	}

	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}

	@Column(name = "FL_MDB_GESTITO")
	public String getFlMdbGestito() {
		return flMdbGestito;
	}

	public void setFlMdbGestito(String flMdbGestito) {
		this.flMdbGestito = flMdbGestito;
	}

}