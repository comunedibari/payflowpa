package it.tasgroup.iris.dto.confpagamenti;


import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.services.util.enumeration.EnumModRiversamento;

public class CfgGatewayPagamentoDTO implements Serializable{
	
	private final static String EMPTY_STRING = "";
	
	private Long id;
	private String applicationId;
	private String applicationIp;
	private String bundleKey;
	private Timestamp dataFineValidita;
	private Timestamp dataInizioValidita;
	private String descGateway;
	private String descrizione;
	private BigDecimal maxImporto;
	private String molteplicita;
	private String stato;
	private String systemId;
	private String subsystemId;
	private String systemName;
	private Integer timeout;
	private Integer timeoutAup;
	private Integer timeoutNp;
	private List<CfgCommissionePagamentoDTO> cfgCommissionePagamenti;
	private CfgCanalePagamentoDTO cfgCanalePagamento;
	private CfgDocumentoPagamentoDTO cfgDocumentoPagamento;
	private CfgFornitoreGatewayDTO cfgFornitoreGateway;
	private CfgModalitaPagamentoDTO cfgModalitaPagamento;
	private CfgStrumentoPagamentoDTO cfgStrumentoPagamento;
	private ContoTecnicoDTO contoTecnico;
	
	private String flPagabileIris;
	private String urlInfoPsp;
	private String urlInfoCanale;
	private String disponibilitaServizio;
	
	private String flRendRiversamento;
	private EnumModRiversamento flModRiversamento;
	
	private String cfgDocumentoPagamentoVideo = EMPTY_STRING;
	private String cfgModalitaPagamentoVideo = EMPTY_STRING;
	private String cfgStrumentoPagamentoVideo = EMPTY_STRING;
	
    private String tipoVersamento;
    private String modelloVersamento;
	
    // contengono le informazioni relative alla disponibilita/indisponibilita del mezzo pagamento
    // per l'insieme di pagamenti inseriti in carrello (valori calcolati non hanno corrispettivo su db)
    private boolean disponibilePerPagamentoCorrente = true;
    private String  descrizioneIndisponibilitaPerPagamentoCorrente="";
	private String  acl;
	private String flMdbGestito;
    
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getApplicationIp() {
		return applicationIp;
	}
	public void setApplicationIp(String applicationIp) {
		this.applicationIp = applicationIp;
	}
	public String getBundleKey() {
		return bundleKey;
	}
	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}
	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}
	public String getDescGateway() {
		return descGateway;
	}
	public void setDescGateway(String descGateway) {
		this.descGateway = descGateway;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public BigDecimal getMaxImporto() {
		return maxImporto;
	}
	public void setMaxImporto(BigDecimal maxImporto) {
		this.maxImporto = maxImporto;
	}
	public String getMolteplicita() {
		return molteplicita;
	}
	public void setMolteplicita(String molteplicita) {
		this.molteplicita = molteplicita;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	public String getSubsystemId() {
		return subsystemId;
	}
	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}
	public Integer getTimeout() {
		return timeout;
	}
	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}
	public Integer getTimeoutAup() {
		return timeoutAup;
	}
	public void setTimeoutAup(Integer timeoutAup) {
		this.timeoutAup = timeoutAup;
	}
	public Integer getTimeoutNp() {
		return timeoutNp;
	}
	public void setTimeoutNp(Integer timeoutNp) {
		this.timeoutNp = timeoutNp;
	}
	public List<CfgCommissionePagamentoDTO> getCfgCommissionePagamenti() {
		return cfgCommissionePagamenti;
	}
	public void setCfgCommissionePagamenti(List<CfgCommissionePagamentoDTO> cfgCommissionePagamenti) {
		this.cfgCommissionePagamenti = cfgCommissionePagamenti;
	}
	public CfgCanalePagamentoDTO getCfgCanalePagamento() {
		return cfgCanalePagamento;
	}
	public void setCfgCanalePagamento(CfgCanalePagamentoDTO cfgCanalePagamento) {
		this.cfgCanalePagamento = cfgCanalePagamento;
	}
	public CfgDocumentoPagamentoDTO getCfgDocumentoPagamento() {
		return cfgDocumentoPagamento;
	}
	public void setCfgDocumentoPagamento(CfgDocumentoPagamentoDTO cfgDocumentoPagamento) {
		this.cfgDocumentoPagamento = cfgDocumentoPagamento;
	}
	public CfgFornitoreGatewayDTO getCfgFornitoreGateway() {
		return cfgFornitoreGateway;
	}
	public void setCfgFornitoreGateway(CfgFornitoreGatewayDTO cfgFornitoreGateway) {
		this.cfgFornitoreGateway = cfgFornitoreGateway;
	}
	public CfgModalitaPagamentoDTO getCfgModalitaPagamento() {
		return cfgModalitaPagamento;
	}
	public void setCfgModalitaPagamento(CfgModalitaPagamentoDTO cfgModalitaPagamento) {
		this.cfgModalitaPagamento = cfgModalitaPagamento;
	}
	public CfgStrumentoPagamentoDTO getCfgStrumentoPagamento() {
		return cfgStrumentoPagamento;
	}
	public void setCfgStrumentoPagamento(CfgStrumentoPagamentoDTO cfgStrumentoPagamento) {
		this.cfgStrumentoPagamento = cfgStrumentoPagamento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CfgGatewayPagamentoDTO other = (CfgGatewayPagamentoDTO) obj;
		if (applicationId == null) {
			if (other.applicationId != null)
				return false;
		} else if (!applicationId.equals(other.applicationId))
			return false;
		if (systemId == null) {
			if (other.systemId != null)
				return false;
		} else if (!systemId.equals(other.systemId))
			return false;
		return true;
	}
	public String getCfgDocumentoPagamentoVideo() {
		if(getCfgDocumentoPagamento() != null)
			cfgDocumentoPagamentoVideo =  getCfgDocumentoPagamento().getDescrizione();
					
		return cfgDocumentoPagamentoVideo;
	}

	public String getCfgModalitaPagamentoVideo() {
		if(getCfgModalitaPagamento() != null)
			cfgModalitaPagamentoVideo =  getCfgModalitaPagamento().getDescrizione();

		return cfgModalitaPagamentoVideo;
	}
	
	public void setCfgModalitaPagamentoVideo(String value) {
		if(getCfgModalitaPagamento() != null)
			getCfgModalitaPagamento().setDescrizione(value);
	}	

	public String getCfgStrumentoPagamentoVideo() {
		if(getCfgStrumentoPagamento() != null)
			cfgStrumentoPagamentoVideo =  getCfgStrumentoPagamento().getDescrizione();
		
		return cfgStrumentoPagamentoVideo;
	}
	
	public String getCfgFornitoreGatewayVideo() {
		return getCfgFornitoreGateway().getDescrizione();
	}

	public String getCfgCanalePagamentoVideo() {
		return getCfgCanalePagamento().getDescrizione();
	}
	public String getFlPagabileIris() {
		return flPagabileIris;
	}
	public void setFlPagabileIris(String flPagabileIris) {
		this.flPagabileIris = flPagabileIris;
	}
	public String getUrlInfoPsp() {
		return urlInfoPsp;
	}
	public void setUrlInfoPsp(String urlInfoPsp) {
		this.urlInfoPsp = urlInfoPsp;
	}
	public String getUrlInfoCanale() {
		return urlInfoCanale;
	}
	public void setUrlInfoCanale(String urlInfoCanale) {
		this.urlInfoCanale = urlInfoCanale;
	}
	public boolean getUrlInformativiPresenti() {
		return StringUtils.isNotEmpty(urlInfoPsp) || StringUtils.isNotEmpty(urlInfoCanale);
	}
	
	public String getFlRendRiversamento() {
		return flRendRiversamento;
	}
	public void setFlRendRiversamento(String flRendRiversamento) {
		this.flRendRiversamento = flRendRiversamento;
	}
	public ContoTecnicoDTO getContoTecnico() {
		return contoTecnico;
	}
	public void setContoTecnico(ContoTecnicoDTO contoTecnico) {
		this.contoTecnico = contoTecnico;
	}
	
	public EnumModRiversamento getFlModRiversamento() {
		
		return flModRiversamento;
	}

	public void setFlModRiversamento(EnumModRiversamento flag) {
		
		this.flModRiversamento = flag;
		
	}
	public String getDisponibilitaServizio() {
		return disponibilitaServizio;
	}
	public void setDisponibilitaServizio(String disponibilitaServizio) {
		this.disponibilitaServizio = disponibilitaServizio;
	}
	public String getTipoVersamento() {
		return tipoVersamento;
	}
	public void setTipoVersamento(String tipoVersamento) {
		this.tipoVersamento = tipoVersamento;
	}
	public String getModelloVersamento() {
		return modelloVersamento;
	}
	public void setModelloVersamento(String modelloVersamento) {
		this.modelloVersamento = modelloVersamento;
	}
	public boolean isDisponibilePerPagamentoCorrente() {
		return disponibilePerPagamentoCorrente;
	}
	public void setDisponibilePerPagamentoCorrente(
			boolean disponibilePerPagamentoCorrente) {
		this.disponibilePerPagamentoCorrente = disponibilePerPagamentoCorrente;
	}
	public String getDescrizioneIndisponibilitaPerPagamentoCorrente() {
		return descrizioneIndisponibilitaPerPagamentoCorrente;
	}
	public void setDescrizioneIndisponibilitaPerPagamentoCorrente(
			String descrizioneIndisponibilitaPerPagamentoCorrente) {
		this.descrizioneIndisponibilitaPerPagamentoCorrente = descrizioneIndisponibilitaPerPagamentoCorrente;
	}
	public String getAcl() {
		return acl;
	}
	public void setAcl(String acl) {
		this.acl = acl;
	}
	public String getFlMdbGestito() {
		return flMdbGestito;
	}
	public void setFlMdbGestito(String flMdbGestito) {
		this.flMdbGestito = flMdbGestito;
	}
    public boolean isDisponibilePerCodiceFiscaleUtente(String cf,boolean isAnonymous){
		if (acl == null || "".equals(acl)) {
			return true;
		} else {
			if (isAnonymous) {
				return false;
			} else {
				if (acl.indexOf(cf) >= 0) {
					return true;
				} else {
					return false;
				}
			}
		}
    }
	
}
