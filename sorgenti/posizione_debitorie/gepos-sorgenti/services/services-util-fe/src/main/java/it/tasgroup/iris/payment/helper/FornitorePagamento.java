package it.tasgroup.iris.payment.helper;

import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.creditcard.DisposizioneCartaCreditoVO;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;

public abstract class FornitorePagamento {

	protected static final String BUSINESS_SERVICE = "POSIZIONEDEBITORIA";

	protected static final String className = FornitorePagamento.class.getName();

	/** configurazione del pagamento */
	protected CfgGatewayPagamentoDTO cfgPagamento;

	protected IProfileManager profileManager;

	protected OperatoriPojo operatorePojo;

	protected IntestatarioDTO intestatarioDebitore;

	protected IntestatarioDTO intestatarioVersante;

	protected OperatoreDTO operatoreVersante;

	private boolean anonymous;

	protected BigDecimal importoTotalePagamenti;

	protected BigDecimal importoCommissioni;

	protected BigDecimal importoConCommissioni;

	protected List<SessionShoppingCartItemDTO> pagamentiWIP;

	FornitorePagamento() {

	}


	protected void inizializzaImporti() {
		//
		// inizializzazione importi
		//
		importoTotalePagamenti = new BigDecimal(0);

		for (SessionShoppingCartItemDTO cartItem : pagamentiWIP) {

			importoTotalePagamenti = importoTotalePagamenti.add(cartItem.getImporto());

		}

		importoTotalePagamenti = importoTotalePagamenti.setScale(2);
		//
		importoConCommissioni = cfgPagamento.getCfgModalitaPagamento().getImportoTotale();
		importoConCommissioni = importoConCommissioni.setScale(2, RoundingMode.HALF_UP);
		//
		importoCommissioni = importoConCommissioni.subtract(importoTotalePagamenti);
		importoCommissioni = importoCommissioni.setScale(2, RoundingMode.HALF_UP);

	}

	protected abstract String preparaRichiesta(HttpServletRequest request, HttpServletResponse response) throws PagamentoException, IncompleteRegistrationException;

	protected DistintaCartaCreditoVO creaDistinta(String codTransazione, HttpServletRequest request) {
		
		
		Locale locale = (Locale)request.getSession().getAttribute(Globals.LOCALE_KEY);
		

		DistintaCartaCreditoVO distintaCC = new DistintaCartaCreditoVO();
		distintaCC.setCodTransazione(codTransazione);
		distintaCC.setCodPagamento(codTransazione);
		// recupero id fiscale ente
        String idFiscaleEnte = null;
		for (SessionShoppingCartItemDTO pagamentoWIP : pagamentiWIP) {

			DisposizioneCartaCreditoVO disposizioneCC = new DisposizioneCartaCreditoVO();
			disposizioneCC.setBusinessService(BUSINESS_SERVICE);
			disposizioneCC.setPagamentoVO(pagamentoWIP);
			disposizioneCC.setIdPagamento(pagamentoWIP.getIdPagamento());
			disposizioneCC.setImportoPagato(pagamentoWIP.getImporto());
			disposizioneCC.setDescrizione(pagamentoWIP.getEnte() + ", " + pagamentoWIP.getCausale() + ", ID=" + pagamentoWIP.getIdPagamento());
			disposizioneCC.setOperatore(profileManager.getUsername());
			idFiscaleEnte = pagamentoWIP.getIdFiscaleEnte();

			distintaCC.addDisposizione(disposizioneCC);
			
			
			
			distintaCC.setTipoSoggettoVers(pagamentoWIP.getTipoSoggettoVers()); 
			distintaCC.setCodFiscaleVers(pagamentoWIP.getCodFiscaleVers()); 
			distintaCC.setAnagraficaVers(pagamentoWIP.getAnagraficaVers());
			distintaCC.setIndirizzoVers(pagamentoWIP.getIndirizzoVers()); 
			distintaCC.setNumeroCivicoVers(pagamentoWIP.getNumeroCivicoVers()); 
			distintaCC.setCapVers(pagamentoWIP.getCapVers()); 
			distintaCC.setLocalitaVers(pagamentoWIP.getLocalitaVers()); 
			distintaCC.setProvinciaVers(pagamentoWIP.getProvinciaVers()); 
			distintaCC.setNazioneVers(pagamentoWIP.getNazioneVers()); 
			//distintaCC.setEmailVersante(emailVersante);Vers(pagamentoWIP.getEmailVers()); 
			

		}

		
		distintaCC.setIdFiscaleCreditore(idFiscaleEnte);
		distintaCC.setCausaleDistinta("PAGONLINERTIRIS");

		distintaCC.setImportoCommissioni(importoCommissioni);
		distintaCC.setTotImportiPositivi(importoConCommissioni);
		distintaCC.setMezzoPagamento(cfgPagamento.getCfgModalitaPagamento().getId());
		distintaCC.setIdCfgGateway(Long.toString(cfgPagamento.getId()));

		if(locale != null)
			distintaCC.setLocale(locale.getLanguage() + "_" + locale.getCountry());

		return distintaCC;

	}

	/**
	 * Aggiorna (almeno ci prova) lo stato di una distinta in corso e restituisce true o false a seconda
	 * che il pagamento della distinta può continuare o meno 
	 * @param dettDistinta
	 * @return
	 * @throws Exception
	 */
	protected abstract boolean aggiornaStatoDistintaInCorso(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) throws Exception;

	public boolean isAnonymous() {
		return anonymous || profileManager == null;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	protected void aggiornaStatoDistintaEseguitoSBF(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
		// implementazione di default per tutti i Fornitori che non dovrebbero mai utilizzare questo metodo
	}

	static protected void aggiornaEsito(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) throws it.tasgroup.iris.shared.util.locator.ServiceLocatorException {
		DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator.getSLSBProxy("distintePagamentoFacadeBean");
		dpBean.aggiornaEsito(dettDistinta.getIdFlusso().longValue(), StatiPagamentiIris.getStatiPagamentiIrisFromFlud(dettDistinta.getStato()), dettDistinta.getTranId(), dettDistinta.getDeOperazione(),dettDistinta.getTipoIdentificativoAttestante(),dettDistinta.getIdentificativoAttestante(),dettDistinta.getDescrizioneAttestante());
	}

}
