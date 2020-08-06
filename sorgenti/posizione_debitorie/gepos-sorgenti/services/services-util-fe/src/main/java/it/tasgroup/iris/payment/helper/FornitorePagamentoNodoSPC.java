package it.tasgroup.iris.payment.helper;

import gov.telematici.pagamenti.ws.FaultBean;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRT;
import gov.telematici.pagamenti.ws.NodoChiediCopiaRTRisposta;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPT;
import gov.telematici.pagamenti.ws.NodoChiediStatoRPTRisposta;
import gov.telematici.pagamenti.ws.NodoInviaRPT;
import gov.telematici.pagamenti.ws.NodoInviaRPTRisposta;
import gov.telematici.pagamenti.ws.ppthead.IntestazionePPT;
import it.gov.agenziaentrate.x2014.marcaDaBollo.MarcaDaBolloDocument;
import it.gov.digitpa.schemas.x2011.pagamenti.CtDatiSingoloPagamentoRT;
import it.gov.digitpa.schemas.x2011.pagamenti.CtDatiVersamentoRT;
import it.gov.digitpa.schemas.x2011.pagamenti.CtRicevutaTelematica;
import it.gov.digitpa.schemas.x2011.pagamenti.RTDocument;
import it.nch.erbweb.client.ServiceSessionConstants;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.web.util.WebUtil;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.web.FrontEndConstant;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.pagamenti.nodopagamentispc.DatiRicevutaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiSingoloPagamentoVO;
import it.nch.utility.iban.IbanHelper;
import it.tasgroup.avvisi.NumeroAvvisoUtils;
import it.tasgroup.iris.cache.IrisCacheSingleton;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.anagrafica.ContoTecnicoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.anagrafica.OperatoreDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.IncompleteRegistrationException;
import it.tasgroup.iris.facade.ejb.client.anagrafica.AnagraficaEntiFacade;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacade;
import it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacade;
import it.tasgroup.iris.nodopagamentispc.util.RPTUtil;
import it.tasgroup.iris.sessioncart.ShoppingCartSessionHelper;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.iris.util.WrapperRPTInvoker;
import it.tasgroup.services.util.enumeration.EnumStatoRPT;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;
import it.tasgroup.services.util.idgenerator.IDGenerator;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.xmlbeans.XmlOptions;

@SuppressWarnings("rawtypes")
public class FornitorePagamentoNodoSPC extends FornitorePagamento {
		
		protected static final int N_MAX_PAGAMENTI = 5;
		
	private static final ContoTecnicoDTO CONTO_TECNICO = IrisCacheSingleton
			.getContoTecnico();
	private static final IntestatarioDTO ANAGRAFICA_RT = IrisCacheSingleton
			.getAnagraficaRT();
		
	protected static final String IBAN_CONTO_TECNICO = CONTO_TECNICO != null ? CONTO_TECNICO
			.getIban() : null;
	protected static final String ID_FISCALE_RT = ANAGRAFICA_RT != null ? ANAGRAFICA_RT
			.getIdFiscale() : null;
	protected static final String DENOMINAZIONE_RT = ANAGRAFICA_RT != null ? ANAGRAFICA_RT
			.getRagioneSociale() : null;
		
	protected static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader
			.getInstance("nodopagamenti.properties");
		
	protected static final String irisCustomer = ConfigurationPropertyLoader
			.getInstance("iris-fe.properties").getProperty("iris.customer");
		
	private static final ConfigurationPropertyLoader confIrisFe = ConfigurationPropertyLoader
			.getInstance("iris-fe.properties");

		
		FornitorePagamentoNodoSPC() {
				
		}
		
	protected String gestisciIbanAddebito(HttpServletRequest request)
			throws PagamentoException {
				
				String ibanAddebito = request.getParameter("ibanAddebito");
				if (ibanAddebito != null) {
						ibanAddebito = ibanAddebito.toUpperCase();
				}
				//		String mezzoDiPagamento = cfgPagamento.getCfgModalitaPagamento()
				//				.getId();
				//		EnumTipoModalitaPagamento mezzoPagamentoEnum = EnumTipoModalitaPagamento
				//				.getByChiave(mezzoDiPagamento);
				//
				//		if (!(EnumTipoModalitaPagamento.RIDONLINE.equals(mezzoPagamentoEnum)
				//				|| EnumTipoModalitaPagamento.BBT.equals(mezzoPagamentoEnum) || EnumTipoModalitaPagamento.PSP
				//					.equals(mezzoPagamentoEnum))) {
				//
				//			return null;
				//
				//		}
				
				String modelloVersamento = cfgPagamento.getModelloVersamento();
				

		if (    ("0".equals(modelloVersamento)) ||
				("1".equals(modelloVersamento)) ) {
						
						return null;
						
				}
				

						
		if (("2".equals(modelloVersamento))
				&& (StringUtils.isEmpty(ibanAddebito) || ibanAddebito.length() != 27)) {

			request.setAttribute("codFiscale",
					request.getParameter("codFiscale"));
			request.setAttribute("mezzoDiPagamento",
					request.getParameter("mezzoDiPagamento"));
						request.setAttribute("email", profileManager.getEmailPagante());
						request.setAttribute("errorIban", "");
						// esco dal metodo e rimando alla pagina di selezione del conto di
						// addebito da utilizzare
						return "selezionaContoPagamentoNodoSPC";
						
				} else if ("4".equals(modelloVersamento)) {
						// Generazione ddp
						
						DDPInputDTO dto = new DDPInputDTO();
						
						for (SessionShoppingCartItemDTO cartItem : pagamentiWIP) {
								checkIdPagamento(cartItem.getIdPagamentoEnte());
								dto.addCondizioneCarrello(cartItem.getIdCondizione());
						}
						
			String idTipoDDP = cfgPagamento.getCfgDocumentoPagamento() != null ? cfgPagamento
					.getCfgDocumentoPagamento().getId() : "null";
						EnumTipoDDP tipoDDP = EnumTipoDDP.getByKey(idTipoDDP);
						dto.setTipo(tipoDDP);
						dto.setIdGateway(cfgPagamento.getId());
						
						try {
								
				DDPFacade ddpBean = (DDPFacade) ServiceLocator
						.getSLSBProxy("ddpFacadeBean");
								
				Locale locale = (Locale) request.getSession().getAttribute(
						Globals.LOCALE_KEY);
								
				List<DocumentoDiPagamentoDTO> creati = ddpBean.createDDP(
						profileManager, dto, locale);
				request.setAttribute(FrontEndConstant.ID_DDP_CREATO, creati
						.get(0).getId());
				request.setAttribute(FrontEndConstant.BACK_TO_PAGE_ATTRIBUTE,
						FrontEndConstant.BACK_FROM_SAME_PAGE);
								
								// esco
								return "successPrePagamentoDDP";
								
						} catch (BusinessConstraintException bce) {
				throw new PagamentoException(bce.getI18NMessageKey(),
						bce.getI18NMessageParameters(),
						"Eccezione di business", bce);
						} catch (Exception e) {
								
								Tracer.error(className, "preparaRichiesta", "EXCEPTION", e);
								throw new RuntimeException("ERRORE", e);
						}
						
				} else {
						// check if iban is ok
						IbanHelper ibanHelper = new IbanHelper();
						String result = ibanHelper.ibanCheck(ibanAddebito);
						if (result == null || !result.equalsIgnoreCase("ok")) {
				request.setAttribute("codFiscale",
						request.getParameter("codFiscale"));
				request.setAttribute("mezzoDiPagamento",
						request.getParameter("mezzoDiPagamento"));
								request.setAttribute("email", profileManager.getEmailPagante());
								request.setAttribute("ibanAddebito", ibanAddebito);
								request.setAttribute("errorIban", result);
								// esco dal metodo e rimando alla pagina di selezione del conto
								// di addebito da utilizzare
								return "selezionaContoPagamentoNodoSPC";
						}
						
				}
				return null;
				
		}
		
		protected void inizializzaAnagraficaVersante(HttpServletRequest request) {
				
		}
		
		protected void inizializzaAnagraficaDebitore() {
				
		}
		
	@Override
	protected String preparaRichiesta(HttpServletRequest request,
			HttpServletResponse response) throws PagamentoException,
			IncompleteRegistrationException {
				
				Tracer.debug(className, "inner preparaRichiesta", "START");
				
				String forward;
				
				inizializzaAnagraficaVersante(request);
				
				verificaSoggettoVersante();
				
				// TODO PAZZIK: VERIFICARE SE SERVE VERIFICA ANAGRAFICA PAGATORE
				
				//
				// Controllo composizione del carrello
				//
				this.verificaCarrello();
				
				//
				// per pagamento con RID devo scegliere l'IBAN di Addebito
				//
				forward = gestisciIbanAddebito(request);
				
				if (forward != null)
						
						return forward;
				
				//
				// creazione distinta - inserimento su DB di DISTINTA, PAGAMENTO e
				// PAGAMENTO_ONLINE (SEMPRE in stato IN_CORSO)
				//
				String codTransazione = IDGenerator.Generate_TRANSACTION_ID();
				
				DistintaCartaCreditoVO distinta = creaDistinta(request, codTransazione, "n/a");
				
				//
				// creazione richiesta (solo xml RPT) - la creo prima di salvare la
				// distinta in modo da poter ritentare il pagamento in caso di errori
				//
				String rptString = creaRichiestaPagamentoTelematico(request, distinta);
				
				Tracer.debug(className, "preparaRichiesta", "RPT da inviare");
				Tracer.debug(className, "preparaRichiesta", rptString);
				
				//
				// invio richiesta al Nodo
				//
				
		NodoInviaRPT bodyrichiesta = RPTUtil.buildBodyInviaRPT(
				cfgPagamento.getSystemId(), cfgPagamento.getSubsystemId(),
				cfgPagamento.getApplicationId(), rptString.getBytes());
				
				String idDominio = distinta.getIdFiscaleCreditore();
				if ("RegioneToscana".equals(confIrisFe.getProperty("iris.customer"))) {
						idDominio = FornitorePagamentoNodoSPC.ID_FISCALE_RT;
						distinta.setIdFiscaleCreditore(FornitorePagamentoNodoSPC.ID_FISCALE_RT);
				}
				
		IntestazionePPT header = RPTUtil.buildIntestazioneRichiesta(
				codTransazione,
				conf.getProperty("nodopagamentispc.codiceContestoPagamento"),
				idDominio);
				
				StatiPagamentiIris statoIris;
				
				try {
			Tracer.debug(className, "preparaRichiesta",
					"calling nodoInviaRPT con RT:");
						Tracer.debug(className, "preparaRichiesta", rptString);
			/*NodoInviaRPTRisposta risposta = RPTUtil
					.getPagamentiTelematiciRPTPort().nodoInviaRPT(
							bodyrichiesta, header); */
						
						NodoInviaRPTRisposta risposta = new WrapperRPTInvoker().nodoInviaRPT(bodyrichiesta, header);
						
			Tracer.debug(className, "preparaRichiesta", "esito risposta: "
					+ risposta.getEsito());
						//
						// elaborazione risposta del nodo
						//
						if (risposta.getFault() != null) {
								
				Tracer.debug(className, "preparaRichiesta",
						"FAULT nella risposta");
								// TODO: gestire il mapping dei codici di fault??
								statoIris = StatiPagamentiIris.IN_ERRORE;
				request.setAttribute("FAULT_NODO_SPC", risposta.getFault()
						.getFaultCode()
						+ " - "
						+ risposta.getFault().getFaultString()
						+ " [descrizione: "
						+ risposta.getFault().getDescription() + "]");
				request.setAttribute("MSG_NODO_SPC",
						"flusso.esitoPagamento.nodopagamenti.msg.esitoKo");
								//-------
								
								if (!"RegioneToscana".equals(irisCustomer)) {
										
										ShoppingCartSessionHelper.removeCartFromSession(request);
								}
								//------
								forward = "esitoRichiestaNodoSPC";
								
						} else {
								
								if (risposta.getRedirect().intValue() == 1) {
					Tracer.debug(className, "preparaRichiesta",
							"pagamento IMMEDIATO");
										// pagamento immediato - redirect alla pagina del nodo
										statoIris = StatiPagamentiIris.IN_CORSO;
										try {
												URL url = new URL(risposta.getUrl());
						Map<String, String> queryMap = getQueryMap(url
								.getQuery());
												String idSessioneNDP = queryMap.get("idSession");
						request.getSession().setAttribute("idSessioneNDP",
								idSessioneNDP);
												
										} catch (MalformedURLException e) {
												
												request.getSession().removeAttribute("idSessioneNDP");
												// TODO: cosa faccio se mi arriva un URL non ben
												// formato?
						Tracer.error(className, "preparaRichiesta",
								"ERRORE ho ricevuto un URL non ben formato", e);
										}
					Tracer.debug(className, "preparaRichiesta",
							"redirezione a: " + risposta.getUrl());
										System.out.println(risposta.getUrl());
										request.setAttribute("URL_TO_REDIRECT", risposta.getUrl());
										forward = "redirectionNodoSPC";
										
								} else {
					Tracer.debug(className, "preparaRichiesta",
							"Pagamento DIFFERITO terminato");
										// pagamento differito - richiesta ok
										statoIris = StatiPagamentiIris.ESEGUITO_SBF;
					request.setAttribute("MSG_NODO_SPC",
							"flusso.esitoPagamento.nodopagamenti.msg.esitoOkSbf");
										ShoppingCartSessionHelper.removeCartFromSession(request);
										forward = "esitoRichiestaNodoSPC";
								}
						}
						
				} catch (RemoteException e) {
						// lascio la distinta IN CORSO
						statoIris = StatiPagamentiIris.IN_CORSO;
			Tracer.error(
					className,
					"preparaRichiesta",
					"Non riesco a determinare l'esito della richiesta pagamento a causa di un errore.",
					e);
			request.setAttribute("MSG_NODO_SPC",
					"flusso.esitoPagamento.nodopagamenti.msg.esitoNonDeterminato");
						request.setAttribute("MSG_ESITO_PAGAMENTO_TYPE", "WARNING");
						forward = "esitoRichiestaNodoSPC";
						
				}
				
				//
				// update stato dalla risposta del servizio
				//
				try {
						// aggiorno l'oggetto in sessione (mi serve per portare le info
						// sulla pagina)
						distinta.setStatoPagamento(statoIris);
			request.getSession().setAttribute(
					ServiceSessionConstants.distintaPagamento, distinta);
						
						//
						// aggiorno lo stato
						//
						// DTO dto = new DTOImpl();
						
						DistintaPosizioneDebitoriaDettaglioVO dettDistinta = new DistintaPosizioneDebitoriaDettaglioVO();
						dettDistinta.setIdFlusso(Long.parseLong(distinta.getFlusso()));
						dettDistinta.setStato(statoIris.getFludMapping());
						dettDistinta.setStPagamento(statoIris.getPagaMapping());
			dettDistinta
					.setDeOperazione("NDP_RICHIESTA_RPT: richiesta inviata - update stato distinta a: "
							+ dettDistinta.getStato());
						//dettDistinta.setTranId("n/a");
						// dto.setVO(dettDistinta);
						
						// Tracer.debug(className, "preparaRichiesta",
						// "calling AGGIORNA_STATO_DISTINTA ...");
						// DTO dtoOut =
						// bxbd.execute(PosizioneDebitoriaService.AGGIORNA_STATO_DISTINTA,
						// profileManager, dto);
						//
						// if (dtoOut.getInfoList() != null || dtoOut.hasError())
						// // non riesco ad aggiornare la distinta
						// throw new
						// RuntimeException("Errore. Non riesco ad aggiornare lo stato della distinta: "
						// + distinta.getIdDistinta() + " a " + statoIris.getFludMapping());
						
						aggiornaEsito(dettDistinta);
						
				} catch (Throwable e) {
						Tracer.error(className, "preparaRichiesta", "ERRORE", e);
						throw new RuntimeException("ERRORE GRAVE", e);
				}
				
		Tracer.debug(className, "preparaRichiesta", "END - forwarding to: "
				+ forward);
				
				return forward;
		}
		
	protected void verificaSoggettoVersante()
			throws IncompleteRegistrationException {
				
		}
		
	protected DistintaCartaCreditoVO creaDistinta(HttpServletRequest request,
			String codTransazione,String codTransazionePsp) {
				
				Properties beProperties = ConfigurationPropertyLoader.getProperties("iris-fe.properties");
				
    	boolean nodoPagamenti1_7 =beProperties.getProperty("iris.pagamenti.nodoPagamenti1_7").equals("true");
				
		
				DistintaCartaCreditoVO distinta = creaDistinta(codTransazione, request);
				distinta.setCodTransazionePSP(codTransazionePsp);
				
				if (nodoPagamenti1_7) {
						distinta.setCodTransazione(codTransazionePsp);
						distinta.setIUV(codTransazione);
						distinta.setCodPagamento(codTransazionePsp);
				}
				
				try {
						
			DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator
					.getSLSBProxy("distintePagamentoFacadeBean");
						
						distinta.setDataTransazione(distinta.getDataOrdine());
						distinta.setTotale(importoConCommissioni);
						distinta.setImportoCommissioni(importoCommissioni);
						distinta.setTotImportiPositivi(importoTotalePagamenti);
						
			
						distinta = dpBean.preparaPagamento(profileManager, distinta);
						
				} catch (Throwable e) {
			Tracer.error(className, "preparaRichiesta",
					"ERRORE nella creazione distinta", e);
						throw new RuntimeException("ERRORE", e);
				}
				
				return distinta;
		}
		
		/**
		 * Creazione della richiesta di pagamento RPT a partire dagli elementi del
		 * carrello
		 *
		 * @param request
		 * @param distinta
		 * @return
		 */
	protected String creaRichiestaPagamentoTelematico(
			HttpServletRequest request, DistintaCartaCreditoVO distinta) {
				
				// TODO su ogni elemento del carrello viene settato il conti di appoggio
				// con il conto tecnico centrale: necessario
				// ricalcolare tutto... qui viene utilizzato l'IBAN di appoggio
				for (SessionShoppingCartItemDTO cartItem : pagamentiWIP)
						cartItem.setIbanAppoggio(IBAN_CONTO_TECNICO);
				
				String ibanAddebito = request.getParameter("ibanAddebito");
				if (ibanAddebito != null) {
						ibanAddebito = ibanAddebito.toUpperCase();
				}
				
				FrontEndContext fec = WebUtil.getLocatedFrontEndContext(request);
		@SuppressWarnings("deprecation")
		OperatoriPojo operatorePojo = (OperatoriPojo) fec.getOperatore()
				.getPojo();
				
				OperatoreDTO operatore = new OperatoreDTO();
				operatore.setNome(operatorePojo.getName());
				operatore.setCognome(operatorePojo.getSurname());
				
				IntestatarioDTO profilo = new IntestatarioDTO();
				profilo.setIdFiscale(profileManager.getCodFiscalePagante());
				profilo.setEmail(profileManager.getEmailPagante());
				
		String richiestaPagamentoTelematico = RPTBuilder.buildRPTXmlString(
				profilo, isAnonymous(), operatore, pagamentiWIP, cfgPagamento.getTipoVersamento(),
				importoTotalePagamenti, distinta.getCodPagamento(), distinta
						.getCodTransazione(), distinta.getCodTransazionePSP(),
				distinta.getDataOrdine(), ibanAddebito);
				
				return richiestaPagamentoTelematico;
		}
		
		protected void verificaCarrello() throws PagamentoException {
				//
				// controllo numero pagamenti del carrello
				//
				if (pagamentiWIP.size() > N_MAX_PAGAMENTI)
			throw new PagamentoException(
					"posizionedebitoria.nodospc.error.numeromaxpagamenti",
					"Superato numero massimo pagamenti consentiti dal Nodo "
							+ pagamentiWIP.size() + " su " + N_MAX_PAGAMENTI);
				
				//
				// controllo omogeneita per Debitore e Beneficiario
				//
				String debitore = null, beneficiario = null;
				
				for (SessionShoppingCartItemDTO cartItem : pagamentiWIP) {
						
						if (debitore == null) {
								debitore = cartItem.getDebtor();
						} else if (!debitore.equals(cartItem.getDebtor())) {
								// Per poter utilizzare questa modalit� di pagamento �
								// necessario che i pagamenti siano riferiti ad un unico
								// debitore e a favore di un unico Ente
				throw new PagamentoException(
						"posizionedebitoria.nodospc.error.stessodebitore",
						"DEBITORE NON OMOGENEO");
						}
						
						if (beneficiario == null) {
								beneficiario = cartItem.getIdEnte(); // ID ente IRIS
						} else if (!beneficiario.equals(cartItem.getIdEnte())) {
								// Per poter utilizzare questa modalit� di pagamento �
								// necessario che i pagamenti siano riferiti ad un unico
								// debitore e a favore di un unico Ente
				throw new PagamentoException(
						"posizionedebitoria.nodospc.error.stessoente",
						"ENTE NON OMOGENEO");
						}
				}
				
		}
		
	@Override
	protected boolean aggiornaStatoDistintaInCorso(
			DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
				
				Tracer.debug(getClass().getName(), "aggiornaStatoDistinta", " - BEGIN");
				return aggiornaStatoDistintaDaStatoRPToDaCopiaRT(dettDistinta);
				
		}
		
		protected boolean aggiornaStatoDistintaDaStatoRPToDaCopiaRT(DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
				
				//
				// calcolo se il pagamento � diretto o differito in base alla modalit�
				// di pagamento
				// TODO: in futuro questa informazione sar� sulla tabella di
				// configurazione CFG_GATEWAY_PAGAMENTO (modello versamento)
				//
				EnumTipoModalitaPagamento enumModalitaPagamento = EnumTipoModalitaPagamento.getByDescrizione(dettDistinta.getModalitaPagamento());
				boolean isPagamentoDiretto =  ("0".equals(dettDistinta.getModelloVersamento()) || "1".equals(dettDistinta.getModelloVersamento())); 
				

		Tracer.debug(getClass().getName(),
				"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
				" aggiorno lo stato di un pagamento "
						+ (isPagamentoDiretto ? "DIRETTO" : "DIFFERITO"));
				
				NodoChiediStatoRPT richiestaStatoRPT = new NodoChiediStatoRPT();
				richiestaStatoRPT.setIdentificativoIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
				richiestaStatoRPT.setIdentificativoStazioneIntermediarioPA(conf.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
				richiestaStatoRPT.setPassword(conf.getProperty("nodopagamentispc.password"));
				
				String idDominio = dettDistinta.getIdFiscaleEnte();
				if (conf.getBooleanProperty("nodopagamentispc.modalitaRT") == true) { // AL: secondo me non serve nulla e per me potrebbe essere rimosso
						idDominio = FornitorePagamentoNodoSPC.ID_FISCALE_RT;
				}
				
				richiestaStatoRPT.setIdentificativoDominio(idDominio);
				richiestaStatoRPT.setIdentificativoUnivocoVersamento(dettDistinta.getIuv());
				richiestaStatoRPT.setCodiceContestoPagamento(dettDistinta.getCodiceContestoPagamento());
				
				NodoChiediStatoRPTRisposta rispostaStatoRPT;
				
				try {
						rispostaStatoRPT = new WrapperRPTInvoker().nodoChiediStatoRPT(richiestaStatoRPT);
						
				} catch (RemoteException e) {
						// non riesco a contattare il servizio
			Tracer.error(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
					"non riesco a contattare il servizio", e);
						return false;
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
				Tracer.error(className,
						"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
						"fault nella risposta al nodoChiediStatoRPT: "
								+ rispostaStatoRPT.getFault().getFaultString());
								return false;
						}
				}
				
				if (stato == null) {
						stato = rispostaStatoRPT.getEsito().getStato();
			Tracer.debug(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
					"nodoChiediStatoRPT ha restituito un esito: " + stato);
						
						EnumStatoRPT enumStato = EnumStatoRPT.valueOf(stato);
			Tracer.debug(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
					"descrizione esito: " + enumStato.getDescrizione());
						
			statoIris = isPagamentoDiretto ? enumStato
					.getStatoPagamentoDiretto() : enumStato
					.getStatoPagamentoDifferito();
			Tracer.debug(className,
					"aggiornaStatoDistintaDaStatoRPToDaCopiaRT", "statoIris: "
							+ statoIris);
						
			if (enumStato.equals(EnumStatoRPT.RT_ACCETTATA_PA) ||
					enumStato.equals(EnumStatoRPT.RT_ACCETTATA_NODO) ||
					enumStato.equals(EnumStatoRPT.RT_ESITO_SCONOSCIUTO_PA)) {
								// esiste sicuramente una ricevuta
								return aggiornaStatoDistintaDaCopiaRT(dettDistinta);
						}
						
				}
				
				String descOp = null;
				//
				// se la distinta � in corso da un tempo superiore al timeout allora
				// forzo l'annullamento
				// processo disabilitabile by properties
				// "nodopagamentispc.abilita.annullamento.incorso"
				//
				ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");
				if (cpl.getBooleanProperty("nodopagamentispc.abilita.annullamento.incorso")) {
						int minutiTimeout = cpl.getIntProperty("nodopagamentispc.timeout.annullamento.incorso");
			if (StatiPagamentiIris.IN_CORSO.equals(statoIris)
					&& new Date().getTime()	- dettDistinta.getDataTransazione().getTime() > minutiTimeout * 60 * 1000) {
				Tracer.info(
						className,
						"aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
						"TIMEOUT IN_CORSO della distinta ID: "
								+ dettDistinta.getIdFlusso());
								statoIris = StatiPagamentiIris.ANNULLATO;
				descOp = "NDP_STATO_RPT [TIMEOUT] - stato esito nodo: " + stato
						+ " --> update stato distinta a: "
						+ statoIris.getFludMapping();
						}
				}
				
		boolean continuaPagamento = StatiPagamentiIris.IN_ERRORE
				.equals(statoIris)
				|| StatiPagamentiIris.ANNULLATO.equals(statoIris)
				|| StatiPagamentiIris.ANNULLATO_DA_OPERATORE.equals(statoIris)
				|| StatiPagamentiIris.NON_ESEGUITO.equals(statoIris);
				
		Tracer.debug(className, "aggiornaStatoDistintaDaStatoRPToDaCopiaRT",
				"continuaPagamento: " + continuaPagamento);
				
		return salvaDistinta(dettDistinta, statoIris, stato, continuaPagamento,
				descOp);
				
		}
		
		/**
		 * aggiorna lo stato della distinta + pagamenti + tracciamento su
		 * pagamenti_online facendo la richiesta al nodo di una copia della RT
		 * (ricevuta telematica)
		 *
		 * @param dettDistinta
		 * @return true o false a seconda che sia stato possibile fare
		 * l'aggiornamento o no
		 */
	private boolean aggiornaStatoDistintaDaCopiaRT(
			DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
				
				try {
						
						NodoChiediCopiaRT richiestaCopiaRT = new NodoChiediCopiaRT();
			richiestaCopiaRT
					.setIdentificativoIntermediarioPA(conf
							.getProperty("nodopagamentispc.identificativoIntermediarioPA"));
			richiestaCopiaRT
					.setIdentificativoStazioneIntermediarioPA(conf
							.getProperty("nodopagamentispc.identificativoStazioneIntermediarioPA"));
			richiestaCopiaRT.setPassword(conf
					.getProperty("nodopagamentispc.password"));
						
						String idDominio = dettDistinta.getIdFiscaleEnte();
						//			if ("RegioneToscana".equals(confIrisFe.getProperty("iris.customer"))) {
						//				idDominio = FornitorePagamentoNodoSPC.ID_FISCALE_RT;
						//			}
						richiestaCopiaRT.setIdentificativoDominio(idDominio);
						
			richiestaCopiaRT.setIdentificativoUnivocoVersamento(dettDistinta
					.getIuv());
						
						// TODO:MINO da eliminare. Solo per evitare problemi di
						// compatibilit� con le prime distinte create senza codTransazione
						// PSP
			String ccp = dettDistinta.getCodiceContestoPagamento() == null
					|| dettDistinta.getCodiceContestoPagamento().isEmpty() ? conf
					.getProperty("nodopagamentispc.codiceContestoPagamento")
					: dettDistinta.getCodiceContestoPagamento();
						richiestaCopiaRT.setCodiceContestoPagamento(ccp);

			/*NodoChiediCopiaRTRisposta rispostaCopiaRT = RPTUtil
					.getPagamentiTelematiciRPTPort().nodoChiediCopiaRT(
							richiestaCopiaRT); */
						NodoChiediCopiaRTRisposta rispostaCopiaRT = new WrapperRPTInvoker().nodoChiediCopiaRT(richiestaCopiaRT);
						
						FaultBean faultRDP = rispostaCopiaRT.getFault();
						
						if (faultRDP != null) {
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Fault nella risposta al NodoChiediCopiaRPT: "
								+ rispostaCopiaRT.getFault().getFaultCode()
								+ " - "
								+ rispostaCopiaRT.getFault().getFaultString());
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Descrizione: "
								+ rispostaCopiaRT.getFault().getDescription());
								// cos� traccio l'operazione sulla PAGAMENTI_ONLINE mantenendo
								// lo stesso stato
				return salvaDistinta(dettDistinta,
						StatiPagamentiIris
								.getStatiPagamentiIrisFromFlud(dettDistinta
										.getStato()), "Fault", false);
								
						} else {
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Successo nella risposta al NodoChiediCopiaRPT: "
								+ rispostaCopiaRT.getRt());
								
								String xmlString = new String(rispostaCopiaRT.getRt());
								// unmarshall di RT e aggiornamento dei pagamenti dall'esito
								// ricevuto
				String nuovoStato = FornitorePagamentoNodoSPC
						.aggiornaPagamentiDallaRT(xmlString);
				Tracer.debug(className, "aggiornaStatoDistintaDaCopiaRT",
						"Aggiornamento dati DISTINTA terminato con successo - nuovo stato: "
								+ nuovoStato);
								if (nuovoStato == null) {
					Tracer.error(
							className,
							"aggiornaStatoDistintaDaCopiaRT",
							"NON RIESCO AD AGGIORNARE DALLA RT PERCHE' POTREBBE ESSERE RELATIVA AD UNA DISTINTA CHE NON CONOSCO");
										return false;
										// throw new
										// Exception("NON RIESCO AD AGGIORNARE DALLA RT PERCHE' POTREBBE ESSERE RELATIVA AD UNA DISTINTA CHE NON CONOSCO ...");
								}
								return true;
								
						}
						
				} catch (Exception e) {
			Tracer.error(className, "aggiornaStatoDistintaDaCopiaRT", "ERRORE",
					e);
						return false;
				}
				
		}
		
		protected static Map<String, String> getQueryMap(String query) {
				String[] params = query.split("&");
				Map<String, String> map = new HashMap<String, String>();
				for (String param : params) {
						String[] nameAndValue = param.split("=");
						map.put(nameAndValue[0], nameAndValue[1]);
				}
				return map;
		}
		
	static protected boolean salvaDistinta(
			DistintaPosizioneDebitoriaDettaglioVO dettDistinta,
			StatiPagamentiIris statoIris, String esitoNodo,
			boolean continuaPagamento, String descOperazione) {
				
		Tracer.debug(className, "salvaDistinta", " - START per distinta "
				+ dettDistinta.getExtToken());
				
				try {
						
						// BusinessService bxbd = new
						// ServiceBusinessDelegate(CommonConstants.CODICE_SERVIZIO_PDC);
						
						// DTO dto = new DTOImpl();
						
						dettDistinta.setStato(statoIris.getFludMapping());
						dettDistinta.setStPagamento(statoIris.getPagaMapping());
						if (descOperazione == null)
				dettDistinta
						.setDeOperazione("NDP_STATO_RPT - stato esito nodo: "
								+ esitoNodo + " --> update stato distinta a: "
								+ dettDistinta.getStato());
						else
								dettDistinta.setDeOperazione(descOperazione);
						

						aggiornaEsito(dettDistinta);
						
						// dto.setVO(dettDistinta);
						//
						// DTO dtoOut =
						// bxbd.execute(PosizioneDebitoriaService.AGGIORNA_STATO_DISTINTA,
						// profileManager, dto);
						
						// if (dtoOut.getInfoList() != null || dtoOut.hasError()) {
						//
						// Tracer.error(className, "salvaDistinta",
						// "Non riesco ad aggiornare la distinta");
						//
						// // non riesco ad aggiornare la distinta
						// return false;
						//
						// } else {
						
			Tracer.debug(className, "salvaDistinta",
					"Salvata distinta " + dettDistinta.getExtToken()
							+ " con stato " + statoIris.getFludMapping());
						return continuaPagamento;
						// }
						
				} catch (Throwable e) {
						
						// non riesco ad aggiornare la distinta
						Tracer.error(className, "salvaDistinta", "ERRORE", e);
						
						return false;
						
				} finally {
						
			Tracer.debug(className, "salvaDistinta",
					" - END con stato finale: " + statoIris);
						
				}
		}
		
	static protected boolean salvaDistinta(
			DistintaPosizioneDebitoriaDettaglioVO dettDistinta,
			StatiPagamentiIris statoIris, String esitoNodo,
			boolean continuaPagamento) {
		return salvaDistinta(dettDistinta, statoIris, esitoNodo,
				continuaPagamento, null);
		}
		
		/**
		 * Come da specifiche del Nodo dei Pagamenti, se CONDIZIONI.ID_PAGAMENTO NON
		 * e' numerico massimo 15 cifre, il DDP non puo' essere emesso.
		 *
		 * @param cartItem
		 * @throws it.tasgroup.iris.payment.helper.IdPagamentoException
		 */
	private static void checkIdPagamento(String idCondizione)
			throws PagamentoException {
				
		if (idCondizione == null || !StringUtils.isNumeric(idCondizione) || (idCondizione.length() != NumeroAvvisoUtils.NUM_AVVISO_LEN_15 && idCondizione.length() != NumeroAvvisoUtils.NUM_AVVISO_LEN_17)) {
						String msgKey = "posizionedebitoria.nodospc.error.idpagamentononvalido";
			throw new PagamentoException(
					msgKey,
					"Non e' possibile emettere un Documento di Pagamento (identificativo non conforme alle specifiche AGID).");
				}
		}
		
	public static String aggiornaPagamentiDallaRT(String xmlString)
			throws Exception {
				
				RTDocument rtDoc = parseXmlRT(xmlString);
				CtRicevutaTelematica rt = rtDoc.getRT();
				CtDatiVersamentoRT datiVersamento = rt.getDatiPagamento();
				
				//
				// creazione VO per richiesta di aggiornamento
				//
				DatiRicevutaPagamentoVO datiRicevuta = new DatiRicevutaPagamentoVO();
				datiRicevuta.setRiferimentoMessaggioRichiesta(rt.getRiferimentoMessaggioRichiesta());
				datiRicevuta.setRiferimentoDataRichiesta(rt.getRiferimentoDataRichiesta().getTime());
				datiRicevuta.setCodiceEsitoPagamento(datiVersamento.getCodiceEsitoPagamento().toString());
				datiRicevuta.setIdentificativoUnivocoVersamento(datiVersamento.getIdentificativoUnivocoVersamento());
				datiRicevuta.setCodiceContestoPagamento(datiVersamento.getCodiceContestoPagamento());
				datiRicevuta.setIdentificativoFiscaleCreditore(rt.getDominio().getIdentificativoDominio());
				
				String codiceIdentificativoUnivocoPSP = rt.getIstitutoAttestante().getIdentificativoUnivocoAttestante().getCodiceIdentificativoUnivoco();
				String tipoIdentificativoPSP = rt.getIstitutoAttestante().getIdentificativoUnivocoAttestante().getTipoIdentificativoUnivoco().toString();
				String descrizionePSPattestante = rt.getIstitutoAttestante().getDenominazioneAttestante();
				
				Tracer.info(className, "aggiornaPagamentiDallaRT","[PagamentiTelematiciRTImpl::paaInviaRT] RT ricevuta codiceIdentificativoUnivocoPSP = '"+ codiceIdentificativoUnivocoPSP+"' , tipoIdentificativoPSP = '"+tipoIdentificativoPSP+"' "); 
				
				datiRicevuta.setCodiceIdentificativoUnivocoPSP(codiceIdentificativoUnivocoPSP);
				datiRicevuta.setTipoIdentificativoPSP(tipoIdentificativoPSP);
				datiRicevuta.setDescrizionePSP(descrizionePSPattestante.toUpperCase());


				
		for (CtDatiSingoloPagamentoRT ctDatiSingoloPagamentoRT : datiVersamento
				.getDatiSingoloPagamentoArray()) {
						
						DatiSingoloPagamentoVO datiSingoloPagamento = new DatiSingoloPagamentoVO();
						datiSingoloPagamento.setIdentificativoUnivocoRiscossione(ctDatiSingoloPagamentoRT.getIdentificativoUnivocoRiscossione());
						datiSingoloPagamento.setEsitoSingoloPagamento(ctDatiSingoloPagamentoRT.getEsitoSingoloPagamento());
						datiSingoloPagamento.setSingoloImportoPagato(ctDatiSingoloPagamentoRT.getSingoloImportoPagato());
						datiSingoloPagamento.setDataEsitoSingoloPagamento(ctDatiSingoloPagamentoRT.getDataEsitoSingoloPagamento().getTime());
						if (ctDatiSingoloPagamentoRT.getAllegatoRicevuta() != null) {
								datiSingoloPagamento.setTipoAllegatoRicevuta(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTipoAllegatoRicevuta().toString());
								datiSingoloPagamento.setAllegatoRicevuta(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTestoAllegato());
								if ("BD".equals(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTipoAllegatoRicevuta().toString())) {
										String alleg = new String(ctDatiSingoloPagamentoRT.getAllegatoRicevuta().getTestoAllegato());
										datiSingoloPagamento.setDatiricevutaMBD(parseAllegatoMBD(alleg));
								}
						}
						datiRicevuta.addDatiSingoloVersamento(datiSingoloPagamento);
				}
				
				//
				// aggiornamento dati distinta e pagamenti
				//
				DistintePagamentoFacade dpBean = (DistintePagamentoFacade) ServiceLocator.getSLSBProxy("distintePagamentoFacadeBean");
				
				String stato = dpBean.aggiornaEsitoDaRT(datiRicevuta);
				return stato;
				
		}
		
		public static String parseAllegatoMBD(String allegatotestoMBD) throws Exception {
				Collection validationErrors = new ArrayList();
				XmlOptions validationOptions = new XmlOptions();
				validationOptions.setErrorListener(validationErrors);
				
				MarcaDaBolloDocument xmlObj = MarcaDaBolloDocument.Factory.parse(allegatotestoMBD);
				
				if (!xmlObj.validate(validationOptions)) {
			StringBuffer errors = new StringBuffer(
					"Errori di validazione xml MBD: \n");
						Iterator iter = validationErrors.iterator();
						while (iter.hasNext()) {
								errors.append(iter.next()).append("\n");
						}
			Tracer.error(FornitorePagamentoNodoSPC.class.getClass().getName(),
					"MBD", errors.toString());
						throw new Exception(errors.toString());
				}
				String out =
		"IUBD = " +xmlObj.getMarcaDaBollo().getIUBD() +";"+
		"DATA_ORA_ACQUISTO = " +xmlObj.getMarcaDaBollo().getOraAcquisto()+";"+
		"PSP_EMITTENTE = " +xmlObj.getMarcaDaBollo().getPSP().getDenominazione()+
		"(" +xmlObj.getMarcaDaBollo().getPSP().getCodiceFiscale()+")";
				return out;
				
		}
		
		private static RTDocument parseXmlRT(String xmlString) throws Exception {
				
				Collection validationErrors = new ArrayList();
				XmlOptions validationOptions = new XmlOptions();
				validationOptions.setErrorListener(validationErrors);
				
				RTDocument xmlObj = RTDocument.Factory.parse(xmlString);
				
				if (!xmlObj.validate(validationOptions)) {
			StringBuffer errors = new StringBuffer(
					"Errori di validazione xml RT: \n");
						Iterator iter = validationErrors.iterator();
						while (iter.hasNext()) {
								errors.append(iter.next()).append("\n");
						}
			Tracer.error(FornitorePagamentoNodoSPC.class.getName(),
					"parseXmlRT", errors.toString());
						throw new Exception(errors.toString());
				}
				return xmlObj;
		}
		
	@Override
	protected void aggiornaStatoDistintaEseguitoSBF(
			DistintaPosizioneDebitoriaDettaglioVO dettDistinta) {
				
				// [SR 18/11] E' inutile provare ad aggiornare le eseguito SBF del nodo.
				// il risultato non verr� mai sbloccato.
				
				// Tracer.debug(getClass().getName(),
				// "aggiornaStatoDistintaEseguitoSBF", "BEGIN");
				// boolean esito =
				// aggiornaStatoDistintaDaStatoRPToDaCopiaRT(dettDistinta);
				// Tracer.debug(getClass().getName(),
				// "aggiornaStatoDistintaEseguitoSBF", "END - aggiornamento riuscito? "
				// + esito);
				
		}
		
	
		/*
		 *
		 */
		protected String calcolaIbanBeneficiario(String ente, String tributo, String idCondizione, CfgGatewayPagamentoDTO cfgPagamento) {
				
				try {
					

						AnagraficaEntiFacade anagBean = (AnagraficaEntiFacade) ServiceLocator.getSLSBProxy("anagraficaEntiFacadeBean");
						
						TributoEnteDTO tribEnte = anagBean.getTributoEnteByKey(ente, tributo);
						

						if (tribEnte.getIBANContoTecnico() != null && !tribEnte.getIBANContoTecnico().trim().equals(""))
								return tribEnte.getIBANContoTecnico();
						
						return tribEnte.getIBAN();
				} catch (Throwable t) {
						t.printStackTrace();
				}
				
				return null;
		}
		
		protected String calcolaIbanAppoggio(String ente, String tributo) {
			
			try {

					AnagraficaEntiFacade anagBean = (AnagraficaEntiFacade) ServiceLocator.getSLSBProxy("anagraficaEntiFacadeBean");
					
					TributoEnteDTO tribEnte = anagBean.getTributoEnteByKey(ente, tributo);

					if (tribEnte.getIBAN_CCP()!=null && !"".equals(tribEnte.getIBAN_CCP())) {		
							return tribEnte.getIBAN_CCP();
					}
					
					return null;
					
			} catch (Throwable t) {
					t.printStackTrace();
			}
			
			return null;
	}
		
}
