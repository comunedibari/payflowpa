/**
 * 
 */
package it.tasgroup.iris.payment.helper;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRT;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPT;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta;
import gov.telematici.pagamenti.ws.NodoInviaRPT;
import gov.telematici.pagamenti.ws.NodoInviaRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.facade.ejb.client.confpagamenti.ConfPagamentiFacade;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.nodopagamentispc.util.RPTUtil;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.locator.ServiceLocatorException;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.iris.util.WrapperRPTInvoker;
import it.tasgroup.services.util.enumeration.EnumStatoRPT;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author pazzik
 *
 */
public class FornitorePagamentoNodoSPC_RT_PSP extends FornitorePagamentoNodoSPC {
	
	FornitorePagamentoNodoSPC_RT_PSP(){

	}

	@Override
	protected String preparaRichiesta(HttpServletRequest request, HttpServletResponse response) throws PagamentoException {
		// In questo caso la scelta della modalit� di pagamento PSP non porta
		// alla creazione di una richiesta ma solo alla generazione del DDP 
		// quindi non dovrei mai arrivare in questo punto.
		return null;
	}


	/**
	 * Nel caso dei pagamenti da PSP l'aggiornamento delle distinte IN CORSO deve inviare la RPT
	 * relativa alla distinta creata dal "paaAttivaRPT"
	 * @see it.tasgroup.iris.payment.helper.FornitorePagamento#aggiornaStatoDistinta(it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO)
	 */
	@Override
	protected boolean aggiornaStatoDistintaInCorso(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {

		Tracer.debug(className, "aggiornaStatoDistintaInCorso", " - BEGIN");

		String rptString = creaRichiestaPagamentoTelematico(dettDistinta);
		String idPsp = dettDistinta.getSystemId();
		String idIntermediarioPsp = dettDistinta.getSubsystemId();
		String idCanalePsp = dettDistinta.getApplicationId();
		if (dettDistinta.getIdPspModello3()!=null && !"".equals(dettDistinta.getIdPspModello3())) {
			idPsp = dettDistinta.getIdPspModello3();
			idIntermediarioPsp = dettDistinta.getIdIntermediarioModello3();
			idCanalePsp =  dettDistinta.getIdCanaleModello3();
		}
		
		NodoInviaRPT bodyInviaRPT = RPTUtil.buildBodyInviaRPT(idPsp, idIntermediarioPsp, idCanalePsp, rptString.getBytes());
		IntestazionePPT headerInviaRPT = RPTUtil.buildIntestazioneRichiesta(dettDistinta.getIuv() , dettDistinta.getCodiceContestoPagamento(),dettDistinta.getIdFiscaleEnte());

		try {

			Tracer.debug(className, "aggiornaStatoDistintaInCorso", "chiamata a nodoInviaRPT con RT:");
			Tracer.debug(className, "aggiornaStatoDistintaInCorso", rptString);			
			NodoInviaRPTRisposta risposta = new WrapperRPTInvoker().nodoInviaRPT(bodyInviaRPT, headerInviaRPT);
			Tracer.debug(className, "aggiornaStatoDistintaInCorso", "esito risposta: " + risposta.getEsito());

			FaultBean faultRDP = risposta.getFault();

			if (faultRDP != null) {
				Tracer.debug(className, "aggiornaStatoDistintaInCorso", "fault nella risposta al NodoInviaRPT: " + risposta.getFault().getFaultCode() + " - " + risposta.getFault().getFaultString());
				Tracer.debug(className, "aggiornaStatoDistintaInCorso", "descrizione: " + risposta.getFault().getDescription());

				StatiPagamentiIris st = null;
				if (faultRDP.getFaultCode().equals("PPT_RPT_DUPLICATA")) {
					st = resolveStatoDistintaDuplicated(dettDistinta);
				} else {
					if (isErroreTemporaneo(faultRDP.getFaultCode())) {
						st = StatiPagamentiIris.IN_CORSO;
					}
					else {
						st = StatiPagamentiIris.IN_ERRORE;
					}
				}
				return salvaDistinta(dettDistinta, st, risposta.getEsito(), false);

			} else {
				String stato = risposta.getEsito();
				StatiPagamentiIris statoIris = StatiPagamentiIris.ESEGUITO_SBF;
				return salvaDistinta(dettDistinta, statoIris, stato, true);
			}
			
		} catch (RemoteException e) {
			Tracer.error(className, "aggiornaStatoDistintaInCorso", "ERRORE: ", e);
		} finally {
			Tracer.debug(className, "aggiornaStatoDistintaInCorso", " - END ");
		}
		return false;		
	}
	
	/**
	 * 
	 * creazione richiesta (solo xml RPT) - la creo prima di salvare la distinta in modo da poter ritentare il pagamento in caso di errori
	 * 
	 * @param dettDistinta
	 * @return
	 * @throws ServiceLocatorException 
	 */
	private String creaRichiestaPagamentoTelematico(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
		
		String richiestaPagamentoTelematico = null;	

		try {
			
			DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator.getSLSBProxy("distintePagamentoFacadeBean");
		
			ConfPagamentiFacade  cfgPagBean = (ConfPagamentiFacade) ServiceLocator.getSLSBProxy("confPagamentiFacadeBean");
			
			CfgGatewayPagamentoDTO cfgGatewayPagDTO= cfgPagBean.readCfgGatewayPagamentoDettaglio(dettDistinta.getIdCfgGatewayPagamento());
			
			List<String> debitoriPendenza = dpBean.getDebitorePendenza(dettDistinta.getIdPendenza());
			
			IntestatarioDTO pagante = new IntestatarioDTO();
			pagante.setIdFiscale(dettDistinta.getCoPagante());
			
			boolean isAnonymous = true; // cos� costruisce i dati versante dal "pagante" e non dall'"operatore"

			OperatoreDTO operatore = new OperatoreDTO();

			SessionShoppingCartItemDTO cartItem = new SessionShoppingCartItemDTO();
			cartItem.setEnte(dettDistinta.getEnte());
			cartItem.setDebtor(debitoriPendenza.get(0));
			cartItem.setIdFiscaleEnte(dettDistinta.getIdFiscaleEnte());
			cartItem.setImporto(dettDistinta.getImportoNetto());			
			////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////////// ATTENZIONE: questa classe viene utilizzata sia per regione toscana 
			////////////////// che per caso generico, quindi si devono considerare tutti e 2 i casi 
			////////////////////////////////////////////////////////////////////////////////////////////////////
			cartItem.setIbanBeneficiario(calcolaIbanBeneficiario( dettDistinta.getIdEnte(),   dettDistinta.getCodTrbEnte() ,  dettDistinta.getIdCondizione(), cfgGatewayPagDTO));
			cartItem.setIdPagamentoEnte(dettDistinta.getCodPagamento());
			cartItem.setIbanAppoggio(calcolaIbanAppoggio(dettDistinta.getIdEnte(),   dettDistinta.getCodTrbEnte()));

			List<SessionShoppingCartItemDTO> cart = new ArrayList<SessionShoppingCartItemDTO>();
			cart.add(cartItem);

			String ibanAddebito = null;			
			
			richiestaPagamentoTelematico = RPTBuilder.buildRPTXmlString(pagante, isAnonymous, operatore, cart, cfgGatewayPagDTO.getTipoVersamento(),
					dettDistinta.getImportoNetto(), dettDistinta.getIuv(), dettDistinta.getExtToken(), dettDistinta.getCodiceContestoPagamento(), dettDistinta.getDataTransazione(), ibanAddebito);

		} catch (ServiceLocatorException e) {
			
			Tracer.error(getClass().getName(), "creaRichiestaPagamentoTelematico", "ERRORE", e);
			
		}			
		
		return richiestaPagamentoTelematico;
		
	}
    private boolean isErroreTemporaneo(String errCode){
    	if ("PPT_CANALE_IRRAGGIUNGIBILE".equals(errCode) ||
    	   "PPT_CANALE_TIMEOUT".equals(errCode) ||
    	   "PPT_CANALE_SERVIZIO_NONATTIVO".equals(errCode) ||
    	   "PPT_CANALE_INDISPONIBILE".equals(errCode) ||
    	   "PPT_CANALE_ERRORE_RESPONSE".equals(errCode)) return true;
    	else 
    		return false;
    	
    }
    /*
     * 
     */
    private StatiPagamentiIris resolveStatoDistintaDuplicated(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
		Tracer.debug(getClass().getName(),"resolveStatoDistintaDuplicated","BEGIN  ");

		NodoChiediStatoRPT richiestaStatoRPT = new NodoChiediStatoRPT();
		richiestaStatoRPT.setIdentificativoIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
		richiestaStatoRPT.setIdentificativoStazioneIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
		richiestaStatoRPT.setPassword(conf.getProperty("nodopagamentispc.password"));

		String idDominio = dettDistinta.getIdFiscaleEnte();
		
		richiestaStatoRPT.setIdentificativoDominio(idDominio);
		richiestaStatoRPT.setIdentificativoUnivocoVersamento(dettDistinta.getIuv());
		richiestaStatoRPT.setCodiceContestoPagamento(dettDistinta.getCodiceContestoPagamento());

		NodoChiediStatoRPTRisposta rispostaStatoRPT;
		try {
			rispostaStatoRPT = new WrapperRPTInvoker().nodoChiediStatoRPT(richiestaStatoRPT);
		} catch (RemoteException e) {
			// non riesco a contattare il servizio
			Tracer.error(className,	"resolveStatoDistintaDuplicated","non riesco a contattare il servizio per nodoChiediStatoRPT lascio la distinta IN CORSO", e);
			return StatiPagamentiIris.IN_CORSO;
		}

		String stato = null;
		StatiPagamentiIris statoIris = null;
		if (rispostaStatoRPT.getFault() != null) {
			if ("PPT_RPT_SCONOSCIUTA".equals(rispostaStatoRPT.getFault().getFaultCode())||
				"PPT_DOMINIO_SCONOSCIUTO".equals(rispostaStatoRPT.getFault().getFaultCode())) {
				stato = rispostaStatoRPT.getFault().getFaultCode();
				statoIris = StatiPagamentiIris.ANNULLATO;
			} else {
				// problema gestito dal nodo
				Tracer.error(className,	"resolveStatoDistintaDuplicated", "fault nella risposta al nodoChiediStatoRPT: "+ rispostaStatoRPT.getFault().getFaultString());
				statoIris = StatiPagamentiIris.IN_CORSO;				
			}
		}

		if (stato == null) {
			stato = rispostaStatoRPT.getEsito().getStato();
			Tracer.debug(className,	"resolveStatoDistintaDuplicated", "nodoChiediStatoRPT ha restituito un esito: " + stato);

			EnumStatoRPT enumStato = EnumStatoRPT.valueOf(stato);
			Tracer.debug(className,	"resolveStatoDistintaDuplicated","descrizione esito: " + enumStato.getDescrizione());

			statoIris = enumStato.getStatoPagamentoDifferito();
			Tracer.debug(className,	"resolveStatoDistintaDuplicated", "statoIris: " + statoIris);
		}
		return statoIris;
	}
}
