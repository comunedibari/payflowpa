package it.tasgroup.iris.domain;

import it.tasgroup.services.util.enumeration.EnumModRiversamento;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the CFG_GATEWAY_PAGAMENTO database table.
 * 
 */
@Entity
@Table(name="CFG_GATEWAY_PAGAMENTO")
@NamedQueries(
{
@NamedQuery(
		name="getcfByBundleKey",
		query="select cf from CfgGatewayPagamento cf "+
			  "where cf.bundleKey =:inBundleKey"),
			  
@NamedQuery(
		name="getCfgBySystemIdAndApplicationId",
		query="select cf from CfgGatewayPagamento cf "+
			  "where cf.systemId =:inSystemId "+
			  "and cf.applicationId =:inApplicationId "),
			  
@NamedQuery(
		name="getCfgBySystemIdAndModelloVersamento",
		query="select cf from CfgGatewayPagamento cf "+
			  "where cf.systemId =:inSystemId "+
			  "and cf.modelloVersamento =:inModelloVersamento "),
			  
@NamedQuery(
		name="getCfgGatewayPagamentoAll",
		query="select cf from CfgGatewayPagamento cf "+
              "where cf.stRiga =:stRiga "+
			  "order by cf.cfgFornitoreGateway.descrizione desc  "),
		
@NamedQuery(
		name="getCfgGatewayPagamentoById",
		query="select cf from CfgGatewayPagamento cf "+
			  "where cf.id =:id"),
@NamedQuery(
		name="getCfGatewayPagamentoListByStato",
		query="select cgp from CfgGatewayPagamento cgp "+
			  "where cgp.stato = :stato " +
			  "and cgp.dataInizioValidita <= :dtInizioValidita " +
			  "and cgp.dataFineValidita >= :dtFineValidita " +
			  "and cgp.stRiga = 'V' " +
			  "and cgp.cfgModalitaPagamento is not null"),
@NamedQuery(
		name="getCfGatewayPagamentoListByStatoAndSysIdAndAppId",
		query="select cgp from CfgGatewayPagamento cgp "+
			  "where cgp.stato = :stato " +
			  "and cgp.dataInizioValidita <= :dtInizioValidita " +
			  "and cgp.dataFineValidita >= :dtFineValidita " +
			  "and cgp.systemId = :sysId " +
			  "and cgp.applicationId = :appId " +
			  "and cgp.stRiga = 'V' " +
			  "and cgp.cfgModalitaPagamento is not null"),
@NamedQuery(
		name="getCfGatewayPagamentoDistinctSysId",
		query="select distinct cgp.systemId from CfgGatewayPagamento cgp "),			  

@NamedQuery(
		name="getCfGatewayPagamentoDistinctApplicationId",
		query="select distinct cgp.applicationId from CfgGatewayPagamento cgp "),			  
					 
		
@NamedQuery(
		name="getCfgGatewayPagamentoDistinctSystemName",
		query="select cgp from CfgGatewayPagamento cgp where cgp.systemName IN (select distinct(cf.systemName) from CfgGatewayPagamento cf "+
              "where cf.stRiga =:stRiga "+
			  "order by cf.systemName )"),

@NamedQuery(
		name="getCfgGatewayPagamentoTributiEnte",
		query="select cgp from CfgGatewayPagamento cgp where cgp.cfgFornitoreGateway.id IN (select cf.cfgFornitoreGateway.id from CfgTributiEntiGateway cf where cf.tributoEnte.idEnte =:idEnte and cf.tributoEnte.cdTrbEnte =:cdTrbEnte  and cf.cfgGatewayPagamento is null )"),


@NamedQuery(
		name="getIdCfgGatewayPagamentoTributiEnte",
		query="select cgp.id from CfgGatewayPagamento cgp where cgp.cfgFornitoreGateway.id IN (select cf.cfgFornitoreGateway.id "
				+ "from CfgTributiEntiGateway cf where cf.tributoEnte.idEnte =:idEnte and cf.tributoEnte.cdTrbEnte =:cdTrbEnte "
				+ "and (cf.modelloVersamento is null  or cf.modelloVersamento=:modelloVersamento) and (cf.tipoVersamento is null or cf.tipoVersamento=:tipoVersamento) and cf.cfgGatewayPagamento is null )"),

@NamedQuery(
		name="getIdCfgGatewayPagamentoPSP",
		query="select distinct(cgp.systemName),cgp.systemId, cgp.subsystemId, cgp.cfgFornitoreGateway.id "
				+ "from CfgGatewayPagamento cgp where cgp.stato = 'ATTIVO' and cgp.stRiga = 'V' "
				+ " and cgp.id IN (select distinct (dp.cfgGatewayPagamento) from GestioneFlussi dp where dp.stato = 'ESEGUITO' "
				+ 					"and dp.id IN (select distinct(flussoDistinta) from Pagamenti p where p.stPagamento = 'ES' and p.idEnte =:idEnte )) "
				+ "order by cgp.systemName ASC "),

@NamedQuery(
		name="getIdCfgGatewayPagamentoTipoVersamento",
		query="select distinct(cgp.tipoVersamento), mp.descrizione, f.descrizione "
				+ " from CfgGatewayPagamento cgp, CfgModalitaPagamento mp, CfgFornitoreGateway f "
				+ " where mp.id = cgp.cfgModalitaPagamento.id and f.id = cgp.cfgFornitoreGateway.id "
				+ " and cgp.stato = 'ATTIVO' and cgp.stRiga = 'V' "
				+ " and cgp.systemId =:sysId and cgp.subsystemId =:subSysId "
				+ "order by mp.descrizione ASC "),
@NamedQuery(
		name="getCfgNdpByModelloVersamento",
		query="select cf from CfgGatewayPagamento cf "+
              " where cf.modelloVersamento =:inModelloVersamento " +
  			  " and cf.stato = 'ATTIVO' " +
  			  " and cf.stRiga = 'V' " +
  			  " and cf.cfgFornitoreGateway.bundleKey='NDP' "),
@NamedQuery(
		name="getCfgNdpByBic",
		query="select cf from CfgGatewayPagamento cf "+
              " where cf.systemId =:inSystemId " +
  			  " and cf.stato = 'ATTIVO' " +
  			  " and cf.stRiga = 'V' " +
  			  " and cf.cfgFornitoreGateway.bundleKey='NDP' "),
@NamedQuery(
		name="getCfgNdpByCodFiscale",
		query="select cf from CfgGatewayPagamento cf "+
              " where cf.subsystemId =:subsystemId " +
  			  " and cf.stato = 'ATTIVO' " +
  			  " and cf.stRiga = 'V' " +
  			  " and cf.cfgFornitoreGateway.bundleKey='NDP' ")

}

)
public class CfgGatewayPagamento extends BaseIdEntity {
	private static final long serialVersionUID = 1L;
	private String applicationId;
	private String applicationIp;
	private String bundleKey;
	private Timestamp dataFineValidita;
	private Timestamp dataInizioValidita;
	private String descGateway;
	private String descrizione;
	private BigDecimal maxImporto;
	private String molteplicita;
	private String opAggiornamento;
	private String opInserimento;
	private String stRiga;
	private String stato;
	private String systemId;
	private String subsystemId;
	private String systemName;
	private String flagModRiversamento;
	private String flagRendRiversamento;
	private ContoTecnico contoTecnico;
	private String disponibilitaServizio;
	private String flagConsegnaRicevutaIdp;
	
	private Integer timeout;
	private Integer timeoutAup;
	private Integer timeoutNp;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private Set<CfgCommissionePagamento> cfgCommissionePagamenti;
	private CfgCanalePagamento cfgCanalePagamento;
	private CfgDocumentoPagamento cfgDocumentoPagamento;
	private CfgFornitoreGateway cfgFornitoreGateway;
	private CfgModalitaPagamento cfgModalitaPagamento;
	private CfgStrumentoPagamento cfgStrumentoPagamento;
    private String modelloVersamento;
    private String tipoVersamento;
    private String acl;
	
	private String flPagabileIris = "Y"; // DEFAULT
	private String urlInfoPsp;
	private String urlInfoCanale;
	private String flMdbGestito;
	
	private String sslCn;
	private String sslIp;
	
	public final static String CBILL = "CBILL";
	
	public final static String PUNTOSI = "PUNTOSI";
	
	private final static String ABI_TODO= "09110";

    public CfgGatewayPagamento() {    
    }
    
	private Long id;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="cfg_gateway_pagamento_pk_gen")	
	@SequenceGenerator(
		    name="cfg_gateway_pagamento_pk_gen",
		    sequenceName="CFG_GATEWAY_PAGAMENTO_ID_SEQ",
		    allocationSize=1
		)	
	public Long getId() {
		return this.id;	 
	}	        
	public void setId(Long id) {		
		this.id = id;	 
	} 		

	@Column(name="APPLICATION_ID", nullable=false)
	public String getApplicationId() {
		return this.applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}


	@Column(name="APPLICATION_IP")
	public String getApplicationIp() {
		return this.applicationIp;
	}

	public void setApplicationIp(String applicationIp) {
		this.applicationIp = applicationIp;
	}


	@Column(name="BUNDLE_KEY", nullable=false)
	public String getBundleKey() {
		return this.bundleKey;
	}

	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}


	@Column(name="DATA_FINE_VALIDITA", nullable=false)
	public Timestamp getDataFineValidita() {
		return this.dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}


	@Column(name="DATA_INIZIO_VALIDITA", nullable=false)
	public Timestamp getDataInizioValidita() {
		return this.dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}


	@Column(name="DESC_GATEWAY", nullable=false)
	public String getDescGateway() {
		return this.descGateway;
	}

	public void setDescGateway(String descGateway) {
		this.descGateway = descGateway;
	}


	@Column(nullable=false, length=510)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	@Column(name="MAX_IMPORTO")
	public BigDecimal getMaxImporto() {
		return this.maxImporto;
	}

	public void setMaxImporto(BigDecimal maxImporto) {
		this.maxImporto = maxImporto;
	}


	@Column(length=2)
	public String getMolteplicita() {
		return this.molteplicita;
	}

	public void setMolteplicita(String molteplicita) {
		this.molteplicita = molteplicita;
	}


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO", nullable=false)
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="ST_RIGA", nullable=false)
	public String getStRiga() {
		return this.stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}


	@Column(nullable=false)
	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}


	@Column(name="SYSTEM_ID", nullable=false)
	public String getSystemId() {
		return this.systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	@Column(name="SUBSYSTEM_ID")
	public String getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}

	public Integer getTimeout() {
		return this.timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}


	@Column(name="TIMEOUT_AUP")
	public Integer getTimeoutAup() {
		return this.timeoutAup;
	}

	public void setTimeoutAup(Integer timeoutAup) {
		this.timeoutAup = timeoutAup;
	}


	@Column(name="TIMEOUT_NP")
	public Integer getTimeoutNp() {
		return this.timeoutNp;
	}

	public void setTimeoutNp(Integer timeoutNp) {
		this.timeoutNp = timeoutNp;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO", nullable=false)
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}


	//bi-directional many-to-one association to CfgCommissionePagamento
	@OneToMany(mappedBy="cfgGatewayPagamento")
	@OrderBy("id")
	public Set<CfgCommissionePagamento> getCfgCommissionePagamenti() {
		return this.cfgCommissionePagamenti;
	}

	public void setCfgCommissionePagamenti(Set<CfgCommissionePagamento> cfgCommissionePagamenti) {
		this.cfgCommissionePagamenti = cfgCommissionePagamenti;
	}
	

	//bi-directional many-to-one association to CfgCanalePagamento
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_CANALE_PAGAMENTO", nullable=false)
	public CfgCanalePagamento getCfgCanalePagamento() {
		return this.cfgCanalePagamento;
	}

	public void setCfgCanalePagamento(CfgCanalePagamento cfgCanalePagamento) {
		this.cfgCanalePagamento = cfgCanalePagamento;
	}
	

	//bi-directional many-to-one association to CfgDocumentoPagamento
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_DOCUMENTO_PAGAMENTO")
	public CfgDocumentoPagamento getCfgDocumentoPagamento() {
		return this.cfgDocumentoPagamento;
	}

	public void setCfgDocumentoPagamento(CfgDocumentoPagamento cfgDocumentoPagamento) {
		this.cfgDocumentoPagamento = cfgDocumentoPagamento;
	}
	

	//bi-directional many-to-one association to CfgFornitoreGateway
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_FORNITORE_GATEWAY", nullable=false)
	public CfgFornitoreGateway getCfgFornitoreGateway() {
		return this.cfgFornitoreGateway;
	}

	public void setCfgFornitoreGateway(CfgFornitoreGateway cfgFornitoreGateway) {
		this.cfgFornitoreGateway = cfgFornitoreGateway;
	}
	

	//bi-directional many-to-one association to CfgModalitaPagamento
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_MODALITA_PAGAMENTO")
	public CfgModalitaPagamento getCfgModalitaPagamento() {
		return this.cfgModalitaPagamento;
	}

	public void setCfgModalitaPagamento(CfgModalitaPagamento cfgModalitaPagamento) {
		this.cfgModalitaPagamento = cfgModalitaPagamento;
	}
	

	//bi-directional many-to-one association to CfgStrumentoPagamento
    @ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ID_CFG_STRUMENTO_PAGAMENTO")
	public CfgStrumentoPagamento getCfgStrumentoPagamento() {
		return this.cfgStrumentoPagamento;
	}

	public void setCfgStrumentoPagamento(CfgStrumentoPagamento cfgStrumentoPagamento) {
		this.cfgStrumentoPagamento = cfgStrumentoPagamento;
	}

	@Column(name="SYSTEM_NAME", nullable=false)
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	
	@Column(name="FL_PAGABILE_IRIS", nullable=false)
	public String getFlPagabileIris() {
		return flPagabileIris;
	}
	public void setFlPagabileIris(String flPagabileIris) {
		this.flPagabileIris = flPagabileIris;
	}

	@Column(name="URL_INFO_PSP")
	public String getUrlInfoPsp() {
		return urlInfoPsp;
	}
	public void setUrlInfoPsp(String urlInfoPsp) {
		this.urlInfoPsp = urlInfoPsp;
	}

	@Column(name="URL_INFO_CANALE")
	public String getUrlInfoCanale() {
		return urlInfoCanale;
	}
	public void setUrlInfoCanale(String urlInfoCanale) {
		this.urlInfoCanale = urlInfoCanale;
	}

	@Transient
	public boolean isCBILL(){
		
		return this.getApplicationId().equals(CBILL);
	}
	
	@Transient
	public boolean isPUNTOSI(){
		
		return this.getApplicationId().equals(PUNTOSI) && this.getSystemId().equals(ABI_TODO);
	}

	@Column(name="FLAG_MOD_RIVERSAMENTO")
	public String getFlagModRiversamento() {
		return flagModRiversamento;
	}
	
	@Transient
	public EnumModRiversamento getEnumModRiversamento() {
		return EnumModRiversamento.getByKey(flagModRiversamento);
	}

	public void setFlagModRiversamento(String flagModRiversamento) {
		this.flagModRiversamento = flagModRiversamento;
	}
	
	@Column(name="FLAG_REND_RIVERSAMENTO")
	public String getFlagRendRiversamento() {
		return flagRendRiversamento;
	}

	public void setFlagRendRiversamento(String flagRendRiversamento) {
		this.flagRendRiversamento = flagRendRiversamento;
	}
	
	@ManyToOne( fetch=FetchType.LAZY)
    @JoinColumn(name="ID_CONTOTECNICO")
	public ContoTecnico getContoTecnico() {
		return contoTecnico;
	}

	public void setContoTecnico(ContoTecnico contoTecnico) {
		this.contoTecnico = contoTecnico;
	}
	
	@Column(name="DISPONIBILITA_SERVIZIO")
	public String getDisponibilitaServizio() {
		return disponibilitaServizio;
	}

	public void setDisponibilitaServizio(String disponibilitaServizio) {
		this.disponibilitaServizio = disponibilitaServizio;
	}
	
	@Column(name="MODELLO_VERSAMENTO")
	public String getModelloVersamento() {
		return modelloVersamento;
	}
	
	public void setModelloVersamento(String modelloVersamento) {
		this.modelloVersamento = modelloVersamento;
	}
	
	@Column(name="TIPO_VERSAMENTO")
	public String getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	@Transient
	public boolean isDocumentEnabled(){		
		return this.getCfgDocumentoPagamento() != null && this.getCfgDocumentoPagamento().getStRiga().equals("V");		
	}
	
	@Column(name="ACL")
	public String getAcl() {
		return acl;
	}
	public void setAcl(String acl) {
		this.acl = acl;
	}
	
	@Column(name="SSL_CN")
	public String getSslCn() {
		return sslCn;
	}
	public void setSslCn(String sslCn) {
		this.sslCn = sslCn;
	}
	
	@Column(name="SSL_IP")
	public String getSslIp() {
		return sslIp;
	}
	public void setSslIp(String sslIp) {
		this.sslIp = sslIp;
	}
	
	@Column(name = "FL_MDB_GESTITO")
	public String getFlMdbGestito() {
		return flMdbGestito;
	}

	public void setFlMdbGestito(String flMdbGestito) {
		this.flMdbGestito = flMdbGestito;
	}
	
	@Column(name = "FL_CONSEGNA_RICEVUTA_IDP")
	public String getFlagConsegnaRicevutaIdp() {
		return flagConsegnaRicevutaIdp;
	}
	public void setFlagConsegnaRicevutaIdp(String flagConsegnaRicevutaIdp) {
		this.flagConsegnaRicevutaIdp = flagConsegnaRicevutaIdp;
	}
	
}