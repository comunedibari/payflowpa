package it.nch.is.fo.tributi;

import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.base.BaseForm;
import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.profilo.EntiCommon;
import it.nch.is.fo.sistemienti.ISistemaEnte;
import it.tasgroup.services.util.enumeration.EnumCfgNotificaPagamento;

public class TributoEnteFormImpl extends BaseForm implements ITributoEnteForm {

	private static final String DEFAULT_FL_NDP = "S";
	private static final String DEFAULT_FL_NDP_MODELLO3 = "N";
	private static final String DEFAULT_FL_BANCA_TESORIERA = "N";
	private static final String DEFAULT_FL_BFL = "N";

	/**
	 * 
	 */
	private static final long serialVersionUID = 3547539259647395054L;
	private String idEnte;
	private String codEnte;
	private String abiEnte;
	private String cdTrbEnte;
	private String idTributo;
	private String desCatTributo;
	private String deTrb;
	private String flIniziativa;
	private String flPredeterm;
	
	private String codPlugin;
	private String dati;
	
	private String flNotificaPagamento;
	private String soggEsclusi;
	private String stato;
	private String[] selectedItems;
	private String selectedItem;
	private String isNew = "true";
	private String isEnte;
	private String ragSociale;
	private String SIL;
	private String IBAN;
	private String IBANContoTecnico;
	private String IBAN_CCP;
	private String IBAN_MYBANK;
	private String tipoGestioneRichiestaRevoca;

	private String deSil;
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
	private	String ndpIuvStartNum; 
	private String ndpCodSegr;
	
	private String autorizzStampaBP;
	private String intestazioneCCP;
    private String uoaCompetente;
 
	private String formatoNotificheEseguito;
	private String formatoNotificheIncassato;
	private String formatoNotificheRegolato;
	
	private String frequenzaNotificheEseguito;
	private String frequenzaNotificheIncassato;
	private String frequenzaNotificheRegolato;
	
	private String cfgTsrCodiceEnte;
	private String cfgTsrContoEnte;
	
	private String consegnaNotificheEseguito;
	private String consegnaNotificheIncassato;
	private String consegnaNotificheRegolato;
	
	private String flNdp = DEFAULT_FL_NDP;
	private String flNdpModello3 = DEFAULT_FL_NDP_MODELLO3;
	private String flBancaTesoriera = DEFAULT_FL_BANCA_TESORIERA;
	private String flBlf = DEFAULT_FL_BFL;
	
	private boolean disableNdpModello3 = false; 
	
	private transient BeanFactoryLocator bfl;
	private transient BeanFactoryReference bfr;
	private transient BeanFactory bf;

	public String getDeSil() {
		return deSil;
	}

	public void setDeSil(String deSil) {
		this.deSil = deSil;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iban) {
		IBAN = iban;
	}

	public String getSIL() {
		return SIL;
	}

	public void setSIL(String sil) {
		SIL = sil;
	}

	public TributoEnteFormImpl() {
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

	public String[] getSelectedItems() {
		return selectedItems;
	}

	public void setSelectedItems(String[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	@Override
	public void setNativePojo(Object nativePojo) {
		// TODO Auto-generated method stub

	}

	public CommonBusinessObject copy() {

		ITributoEnteForm _form = this;
		ITributoEnte _pojo = (ITributoEnte) this.nativePojo;

		if (_pojo == null) {

			if (Tracer.isDebugEnabled(this.getClass().getName())) {
				Tracer.debug(this.getClass().getName(), "", "", null);
				Tracer.debug(this.getClass().getName(), "copy()", "---------------------------------------------------------------------", null);
				Tracer.debug(this.getClass().getName(), "copy()", "Attenzione nativeObject dentro FORM vuoto provvedo ad istanziare POJO", null);
				Tracer.debug(this.getClass().getName(), "copy()", "---------------------------------------------------------------------", null);
				Tracer.debug(this.getClass().getName(), "", "", null);
			}

			bfl = SingletonBeanFactoryLocator.getInstance("client-beanRefFactory.xml");
			bfr = bfl.useBeanFactory("it.nch.orm");
			bf = bfr.getFactory();
			_pojo = (ITributoEnte) bf.getBean("TributoEnte");

		}

		_pojo.setCdTrbEnte(_form.getCdTrbEnte());
		_pojo.setDeTrb(_form.getDeTrb());
		_pojo.setFlIniziativa(_form.getFlIniziativa());
		_pojo.setSIL(_form.getSIL());
		_pojo.setFlPredeterm(_form.getFlPredeterm());
		_pojo.setFlNotificaPagamento(_form.getFlNotificaPagamento());
		_pojo.setFlVerificaPagamento(_form.getFlVerificaPagamento());
		_pojo.setIBAN(_form.getIBAN());
		_pojo.setIdEnte(_form.getIdEnte());
		_pojo.setIdTributo(_form.getIdTributo());
		_pojo.setSoggEsclusi(_form.getSoggEsclusi());
		_pojo.setStato(_form.getStato());

		_pojo.setFlRicevutaAnonimo(_form.getFlRicevutaAnonimo());
		_pojo.setUrlInfoService(_form.getUrlInfoService());
		_pojo.setFlNascostoFe(_form.getFlNascostoFe());
		_pojo.setUrlUpdService(_form.getUrlUpdService());
		_pojo.setIstruzioniPagamento(_form.getIstruzioniPagamento());

		_pojo.setNdpCodStazPa(_form.getNdpCodStazPa()); 
		_pojo.setNdpCodSegr(_form.getNdpCodSegr()); 
		_pojo.setNdpAuxDigit(_form.getNdpAuxDigit());  
		_pojo.setFlNdpIuvGenerato(_form.getFlNdpIuvGenerato()); 
		//_pojo.setNdpIuvStartNum(_form.getNdpIuvStartNum());
		
		_pojo.setFlNdp(_form.getFlNdp());
        _pojo.setFlNdpModello3(_form.getFlNdpModello3());
        _pojo.setFlBancaTesoriera(_form.getFlBancaTesoriera());
        _pojo.setFlBlf(_form.getFlBlf());
        
        _pojo.setUoaCompetente(_form.getUoaCompetente());
        _pojo.setIntestazioneCCP(_form.getIntestazioneCCP());
        _pojo.setAutorizzStampaBP(_form.getAutorizzStampaBP());
        
        _pojo.setTipoGestioneRichiestaRevoca(_form.getTipoGestioneRichiestaRevoca());
		return _pojo;
	}

	@Override
	public DTO<?, ?, ?> incapsulateBO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	public String getIsEnte() {
		return isEnte;
	}

	public void setIsEnte(String b) {
		this.isEnte = b;
	}

	public String getRagSociale() {
		return ragSociale;
	}

	public void setRagSociale(String ragSociale) {
		this.ragSociale = ragSociale;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
	}

	@Override
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors mess = super.validate(mapping, request);

		ActionMessages messaggi = new ActionMessages();

		for (Iterator<?> iterator = mess.get(); iterator.hasNext();) {
			ActionMessage m = (ActionMessage) iterator.next();

			messaggi.add("ERRORI", m);
		}

		request.setAttribute(Globals.MESSAGE_KEY, messaggi);

		return mess;

	}
	

	public String getTipoGestioneRichiestaRevoca() {
		return tipoGestioneRichiestaRevoca;
	}

	public void setTipoGestioneRichiestaRevoca(String tipoGestioneRichiestaRevoca) {
		this.tipoGestioneRichiestaRevoca = tipoGestioneRichiestaRevoca;
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


	@Override
	public ICategoriaTributo getCategoriaobj() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDesCategoriaTributo() {
		
		return desCatTributo;
	}

	@Override
	public EntiCommon getEntiobj() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISistemaEnte getSistemaEnteobj() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCategoriaobj(ICategoriaTributo categoriaobj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDesCategoriaTributo(String desCategoriaTributo) {
		this.desCatTributo = desCategoriaTributo;
		
	}

	@Override
	public void setEntiobj(EntiCommon entiobj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSistemaEnteobj(ISistemaEnte sistemaEnteobj) {
		// TODO Auto-generated method stub
		
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
	
	public String getFlVerificaPagamento() {
		return flVerificaPagamento;
	}

	public void setFlNascostoFe(String flNascostoFe) {
		this.flNascostoFe = flNascostoFe;
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

	public String getAbiEnte() {
		return abiEnte;
	}

	public void setAbiEnte(String abiEnte) {
		this.abiEnte = abiEnte;
	}

	public String getIBANContoTecnico() {
		return IBANContoTecnico;
	}

	public void setIBANContoTecnico(String iBANContoTecnico) {
		IBANContoTecnico = iBANContoTecnico;
	}

	public String getFormatoNotificheEseguito() {
		return formatoNotificheEseguito;
	}

	public void setFormatoNotificheEseguito(String formatoNotificheEseguito) {
		this.formatoNotificheEseguito = formatoNotificheEseguito;
	}

	public String getFormatoNotificheIncassato() {
		return formatoNotificheIncassato;
	}

	public void setFormatoNotificheIncassato(String formatoNotificheIncassato) {
		this.formatoNotificheIncassato = formatoNotificheIncassato;
	}

	public String getFormatoNotificheRegolato() {
		return formatoNotificheRegolato;
	}

	public void setFormatoNotificheRegolato(String formatoNotificheRegolato) {
		this.formatoNotificheRegolato = formatoNotificheRegolato;
	}

	public String getFrequenzaNotificheEseguito() {
		return frequenzaNotificheEseguito;
	}

	public void setFrequenzaNotificheEseguito(String frequenzaNotificheEseguito) {
		this.frequenzaNotificheEseguito = frequenzaNotificheEseguito;
	}

	public String getFrequenzaNotificheIncassato() {
		return frequenzaNotificheIncassato;
	}

	public void setFrequenzaNotificheIncassato(String frequenzaNotificheIncassato) {
		this.frequenzaNotificheIncassato = frequenzaNotificheIncassato;
	}

	public String getFrequenzaNotificheRegolato() {
		return frequenzaNotificheRegolato;
	}

	public void setFrequenzaNotificheRegolato(String frequenzaNotificheRegolato) {
		this.frequenzaNotificheRegolato = frequenzaNotificheRegolato;
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

	public String getConsegnaNotificheEseguito() {
		return consegnaNotificheEseguito;
	}

	public void setConsegnaNotificheEseguito(String consegnaNotificheEseguito) {
		this.consegnaNotificheEseguito = consegnaNotificheEseguito;
	}

	public String getConsegnaNotificheIncassato() {
		return consegnaNotificheIncassato;
	}

	public void setConsegnaNotificheIncassato(String consegnaNotificheIncassato) {
		this.consegnaNotificheIncassato = consegnaNotificheIncassato;
	}

	public String getConsegnaNotificheRegolato() {
		return consegnaNotificheRegolato;
	}

	public void setConsegnaNotificheRegolato(String consegnaNotificheRegolato) {
		this.consegnaNotificheRegolato = consegnaNotificheRegolato;
	}
	
	public boolean isCfgNotificheDisabled(){
		
		return EnumCfgNotificaPagamento.NON_NOTIFICATO.getChiave().equals(flNotificaPagamento);
	}

	public String getCodEnte() {
		return codEnte;
	}

	public void setCodEnte(String codEnte) {
		this.codEnte = codEnte;
	}
	
	public String getIBAN_CCP() {
		return IBAN_CCP;
	}

	public void setIBAN_CCP(String iBAN_CCP) {
		this.IBAN_CCP = iBAN_CCP;
	}

	public String getNdpCodStazPa() {
		return ndpCodStazPa;
	}
    @Override
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
	public String getFlNdpIuvGeneratoKeyLbl() {
		return "backoffice.tributi.searchCategoriaTributo.attribuzione.iuv."+flNdpIuvGenerato;
	}

	public void setFlNdpIuvGenerato(String flNdpIuvGenerato) {
		this.flNdpIuvGenerato = flNdpIuvGenerato;
	}

	public String getNdpIuvStartNum() {
		return ndpIuvStartNum;
	}

	public void setNdpIuvStartNum(String ndpIuvStartNum) {
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
	
	public boolean getDisableNdpModello3() {
		return disableNdpModello3;
	}

	public void setDisableNdpModello3(boolean disableNdpModello3) {
		this.disableNdpModello3 = disableNdpModello3;
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

	public String getCodPlugin() {
		return codPlugin;
	}

	public void setCodPlugin(String codPlugin) {
		this.codPlugin = codPlugin;
	}


	public String getDati() {
		return dati;
	}

	public void setDati(String dati) {
		this.dati = dati;
	}
	
	public String getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}

	@Override
	public String getAutorizzStampaBP() {
		return autorizzStampaBP;
	}

	@Override
	public void setAutorizzStampaBP(String autorizzStampaBP) {
		this.autorizzStampaBP = autorizzStampaBP;
	}

	@Override
	public String getIntestazioneCCP() {		
		return intestazioneCCP;
	}

	@Override
	public void setIntestazioneCCP(String myIntestazioneCCP) {
		intestazioneCCP=myIntestazioneCCP;		
	}

	@Override
	public String getUoaCompetente() {		
		return uoaCompetente;
	}

	@Override
	public void setUoaCompetente(String myUoaCompetente) {
		uoaCompetente=myUoaCompetente;
		
	}
	
	@Override
	public String getNdpCodSegr() {		
		return ndpCodSegr;
	}

	@Override
	public void setNdpCodSegr(String ndpCodSegr) {
		this.ndpCodSegr=ndpCodSegr;		
	}


}
