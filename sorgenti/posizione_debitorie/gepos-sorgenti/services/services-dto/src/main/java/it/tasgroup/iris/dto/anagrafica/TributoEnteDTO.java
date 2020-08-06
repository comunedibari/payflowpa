package it.tasgroup.iris.dto.anagrafica;

import it.tasgroup.services.util.enumeration.EnumTipoAttivo;

import java.io.Serializable;

public class TributoEnteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String IDX_SEPARATOR = "###$$$###";
	public final static String IDX_SEPARATOR_REGEX = "###\\$\\$\\$###";

	private String idEnte;
	private String cdTrbEnte;
	private String cdPlugin;
	private byte[] dati;
	
	private String idTributo;
	private String deTrb;
	private String flIniziativa;
	private String tipoGestioneRichiestaRevoca;
	private String flPredeterm;
	private String flNotificaPagamento;
	private String soggEsclusi;
	private String stato;
	private EnteDTO ente = new EnteDTO();
	private CategoriaTributoDTO categoria = new CategoriaTributoDTO();
	private boolean isNew = true;
	private SistemaEnteDTO sistemiEnte = new SistemaEnteDTO();
	private String SIL;
	private String IBAN;
	private String IBANContoTecnico;
	private String IBAN_CCP;
	private String IBAN_MYBANK;
	private Long numPendenze;
	private String desCategoriaTributo;
	private String infoTributo;

	private String flRicevutaAnonimo;
	private String urlInfoService;
	private String flNascostoFe;
	private String flVerificaPagamento;
	private String urlUpdService;
	private String istruzioniPagamento;
	
	private String ndpCodStazPa; 
	private String ndpAuxDigit;  
	private String flNdpIuvGenerato; 
	private	Long   ndpIuvStartNum; 
	private String ndpCodSegr;
	
	private String autorizzStampaBP;
	private String intestazioneCCP;
	private String uoaCompetente; 
 
	
	private String opInserimento;
	private String opAggiornamento;
	
	private String flNdp;
	private String flNdpModello3;
	private String flBancaTesoriera;
	private String flBlf;

	
	private CfgNotificaPagamentoDTO cfgNotificaEseguito = new CfgNotificaPagamentoDTO();
	
	private CfgNotificaPagamentoDTO cfgNotificaIncassato = new CfgNotificaPagamentoDTO();
	
	private CfgNotificaPagamentoDTO cfgNotificaRegolato = new CfgNotificaPagamentoDTO();
	
	//
	// configurazione tesoreria
	//
	private String cfgTsrCodiceEnte;
	private String cfgTsrContoEnte;
	
	
	public String getDesCategoriaTributo() {
		return desCategoriaTributo;
	}
	
	public void setDesCategoriaTributo(String desCategoriaTributo) {
		this.desCategoriaTributo = desCategoriaTributo;
	}


	public String getIBAN() {
		return IBAN;
	}
	public void setIBAN(String iban) {
		IBAN = iban;
	}
	
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	public String getIdTributo() {
		return idTributo;
	}
	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}
	public String getDeTrb() {
		return deTrb;
	}
	public void setDeTrb(String deTrb) {
		this.deTrb = deTrb;
	}
	public String getFlIniziativa() {
		return flIniziativa;
	}
	public void setFlIniziativa(String flIniziativa) {
		this.flIniziativa = flIniziativa;
	}
	public String getTipoGestioneRichiestaRevoca() {
		return tipoGestioneRichiestaRevoca;
	}
	public void setTipoGestioneRichiestaRevoca(String tipoGestioneRichiestaRevoca) {
		this.tipoGestioneRichiestaRevoca = tipoGestioneRichiestaRevoca;
	}
	public String getSoggEsclusi() {
		return soggEsclusi;
	}
	public void setSoggEsclusi(String soggEsclusi) {
		this.soggEsclusi = soggEsclusi;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	
	public EnumTipoAttivo getStatoEnum() {
		
		return EnumTipoAttivo.getByKey(stato);
		
	}
	
	public EnteDTO getEnte() {
		return ente;
	}
	public void setEntiobj(EnteDTO entiobj) {
		this.ente = entiobj;
	}
	public CategoriaTributoDTO getCategoriaobj() {
		return categoria;
	}
	public void setCategoria(CategoriaTributoDTO categoria) {
		this.categoria = categoria;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	
	public String getIndexSeparator(){
		return IDX_SEPARATOR;
	}
	
	
	public SistemaEnteDTO getSistemaEnte() {
		return this.sistemiEnte;
	}
	
	public void setSistemaEnte(SistemaEnteDTO sistemaEnte) {
		this.sistemiEnte = sistemaEnte;
	}
	
	public String getSIL() {
		return SIL;
	}
	
	public void setSIL(String sil) {
		SIL = sil;
	}
	
	public String getFlPredeterm() {
		return flPredeterm;
	}
	
	public void setFlPredeterm(String flPredeterm) {
		this.flPredeterm = flPredeterm;
	}
	
	public String getFlNotificaPagamento() {
		return flNotificaPagamento;
	}
	
	public void setFlNotificaPagamento(String flNotificaPagamento) {
		this.flNotificaPagamento = flNotificaPagamento;
	}
	
	public String getInfoTributo() {
		return infoTributo;
	}
	public void setInfoTributo(String infoTributo) {
		this.infoTributo = infoTributo;
	}
	public String getFlRicevutaAnonimo() {
		return flRicevutaAnonimo;
	}
	public void setFlRicevutaAnonimo(String flRicevutaAnonimo) {
		this.flRicevutaAnonimo = flRicevutaAnonimo;
	}
	public String getUrlInfoService() {
		return urlInfoService;
	}
	public void setUrlInfoService(String urlInfoService) {
		this.urlInfoService = urlInfoService;
	}
	public String getFlNascostoFe() {
		return flNascostoFe;
	}
	public void setFlNascostoFe(String flNascostoFe) {
		this.flNascostoFe = flNascostoFe;
	}
	public String getFlVerificaPagamento() {
		return flVerificaPagamento;
	}
	public void setFlVerificaPagamento(String flVerificaPagamento) {
		this.flVerificaPagamento = flVerificaPagamento;
	}
	public String getUrlUpdService() {
		return urlUpdService;
	}
	public void setUrlUpdService(String urlUpdService) {
		this.urlUpdService = urlUpdService;
	}
	public String getIstruzioniPagamento() {
		return istruzioniPagamento;
	}
	public void setIstruzioniPagamento(String istruzioniPagamento) {
		this.istruzioniPagamento = istruzioniPagamento;
	}
	public String getIBANContoTecnico() {
		return IBANContoTecnico;
	}
	public void setIBANContoTecnico(String iBANContoTecnico) {
		IBANContoTecnico = iBANContoTecnico;
	}
	
	public CfgNotificaPagamentoDTO getCfgNotificaPagamento() {
		return cfgNotificaEseguito;
	}
	public void setCfgNotificaPagamento(CfgNotificaPagamentoDTO cfgNotificaPagamento) {
		this.cfgNotificaEseguito = cfgNotificaPagamento;
	}
	
	public String getLabel(){
		
		return IBAN + " (" + deTrb + ")";
	}
	
	public String getLabelContoTecnico(){
		
		return deTrb + " (" + IBANContoTecnico + ")";
	}
	
	public String getIBANRiversamento(){
		
		return cdTrbEnte + ":" + IBAN;
	}
	
	public String getCodice() {
		
		return idEnte + "|" + cdTrbEnte;
		
	}
	
	public String getDescrizione() {
		
		return getEnte().getDenominazione() + " - " + deTrb;
		
	}

	public CfgNotificaPagamentoDTO getCfgNotificaEseguito() {
		return cfgNotificaEseguito;
	}

	public void setCfgNotificaEseguito(CfgNotificaPagamentoDTO cfgNotificaEseguito) {
		this.cfgNotificaEseguito = cfgNotificaEseguito;
	}

	public CfgNotificaPagamentoDTO getCfgNotificaIncassato() {
		return cfgNotificaIncassato;
	}

	public void setCfgNotificaIncassato(CfgNotificaPagamentoDTO cfgNotificaIncassato) {
		this.cfgNotificaIncassato = cfgNotificaIncassato;
	}

	public CfgNotificaPagamentoDTO getCfgNotificaRegolato() {
		return cfgNotificaRegolato;
	}

	public void setCfgNotificaRegolato(CfgNotificaPagamentoDTO cfgNotificaRegolato) {
		this.cfgNotificaRegolato = cfgNotificaRegolato;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	public String getIBAN_CCP() {
		return IBAN_CCP;
	}

	public void setIBAN_CCP(String iBAN_CCP) {
		IBAN_CCP = iBAN_CCP;
	}

	public Long getNumPendenze() {
		return numPendenze;
	}

	public void setNumPendenze(Long numPendenze) {
		this.numPendenze = numPendenze;
	}

	public String getNdpCodStazPa() {
		return ndpCodStazPa;
	}

	public void setNdpCodStazPa(String ndpCodStazPa) {
		this.ndpCodStazPa = ndpCodStazPa;
	}

	public String getNdpAuxDigit() {
		return ndpAuxDigit;
	}

	public void setNdpAuxDigit(String ndpAuxDigit) {
		this.ndpAuxDigit = ndpAuxDigit;
	}

	public String getFlNdpIuvGenerato() {
		return flNdpIuvGenerato;
	}

	public void setFlNdpIuvGenerato(String flNdpIuvGenerato) {
		this.flNdpIuvGenerato = flNdpIuvGenerato;
	}

	public Long getNdpIuvStartNum() {
		return ndpIuvStartNum;
	}

	public void setNdpIuvStartNum(Long ndpIuvStartNum) {
		this.ndpIuvStartNum = ndpIuvStartNum;
	}

	public String getIBAN_MYBANK() {
		return IBAN_MYBANK;
	}

	public void setIBAN_MYBANK(String iBAN_MYBANK) {
		IBAN_MYBANK = iBAN_MYBANK;
	}

	public String getFlNdp() {
		return flNdp;
	}

	public void setFlNdp(String flNdp) {
		this.flNdp = flNdp;
	}

	public String getFlNdpModello3() {
		return flNdpModello3;
	}

	public void setFlNdpModello3(String flNdpModello3) {
		this.flNdpModello3 = flNdpModello3;
	}

	public String getFlBancaTesoriera() {
		return flBancaTesoriera;
	}

	public void setFlBancaTesoriera(String flBancaTesoriera) {
		this.flBancaTesoriera = flBancaTesoriera;
	}

	public String getFlBlf() {
		return flBlf;
	}

	public void setFlBlf(String flBlf) {
		this.flBlf = flBlf;
	}

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}

	public byte[] getDati() {
		return dati;
	}

	public void setDati(byte[] dati) {
		this.dati = dati;
	}

	public String getAutorizzStampaBP() {
		return autorizzStampaBP;
	}

	public void setAutorizzStampaBP(String autorizzStampaBP) {
		this.autorizzStampaBP = autorizzStampaBP;
	}

	public String getIntestazioneCCP() {
		return intestazioneCCP;
	}

	public void setIntestazioneCCP(String intestazioneCCP) {
		this.intestazioneCCP = intestazioneCCP;
	}
	
	public String getUoaCompetente() {
		return uoaCompetente;
	}

	public void setUoaCompetente(String uoaCompetente) {
		this.uoaCompetente = uoaCompetente;
	}

	public String getNdpCodSegr() {
		return ndpCodSegr;
	}

	public void setNdpCodSegr(String ndpCodSegr) {
		this.ndpCodSegr = ndpCodSegr;
	}

	public String getCfgTsrCodiceEnte() {
		return cfgTsrCodiceEnte;
	}

	public void setCfgTsrCodiceEnte(String cfgTsrCodiceEnte) {
		this.cfgTsrCodiceEnte = cfgTsrCodiceEnte;
	}

	public String getCfgTsrContoEnte() {
		return cfgTsrContoEnte;
	}

	public void setCfgTsrContoEnte(String cfgTsrContoEnte) {
		this.cfgTsrContoEnte = cfgTsrContoEnte;
	}


	
}
