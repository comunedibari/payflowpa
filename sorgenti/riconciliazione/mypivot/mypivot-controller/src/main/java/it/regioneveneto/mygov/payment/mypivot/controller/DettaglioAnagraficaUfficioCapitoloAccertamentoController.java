package it.regioneveneto.mygov.payment.mypivot.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.AnagraficaUfficioCapitoloAccertamentoCommand;
import it.regioneveneto.mygov.payment.mypivot.controller.utils.UtilitiesCtrl;
import it.regioneveneto.mygov.payment.mypivot.controller.validator.AnagraficaUfficioCapitoloAccertamentoValidator;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AnagraficaUfficioCapitoloAccertamentoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.AnagraficaUfficioCapitoloAccertamentoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * 
 * @author Alessandro Paolillo
 *
 */
@Controller
@RequestMapping("/protected/accertamentiAnagrafiche")
public class DettaglioAnagraficaUfficioCapitoloAccertamentoController {
	
	private static Log logger = LogFactory.getLog(DettaglioAnagraficaUfficioCapitoloAccertamentoController.class);
	
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	
	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoService anagraficaService;
	
	@Autowired
	private AnagraficaUfficioCapitoloAccertamentoValidator validator;
	
	/**
	 * Ritorna la view per la visualizzazione del dettaglio dell'anagrafica
	 * 
	 * @param request
	 * @param id
	 * @param codAccertamento
	 * @param codCapitolo
	 * @param codTipoDovuto
	 * @param deTipoDovuto
	 * @param codUfficio
	 * @param deAccertamento
	 * @param deAnnoEsercizio
	 * @param deCapitolo
	 * @param deUfficio
	 * @param flgAttivo
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/dettaglioAnagrafica.html" }, method = RequestMethod.GET)
	public ModelAndView visualizzaDettaglio(HttpServletRequest request,
			@RequestParam(required = false) String id
			) throws Exception{
		ModelAndView mav;
		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		} else {
			mav = commonVisualizzaDettaglio(request, enteTO, id);
		}
		return mav;
	}

	/**
	 * Ritorna la view per la visualizzazione del dettaglio dell'anagrafica
	 * 
	 * @param request
	 * @param enteTO
	 * @param id
	 * @param codAccertamento
	 * @param codCapitolo
	 * @param codTipoDovuto
	 * @param deTipoDovuto
	 * @param codUfficio
	 * @param deAccertamento
	 * @param deAnnoEsercizio
	 * @param deCapitolo
	 * @param deUfficio
	 * @param flgAttivo
	 * @return
	 * @author Alessandro Paolillo
	 */
	private ModelAndView commonVisualizzaDettaglio(HttpServletRequest request, EnteTO enteTO, String id) {
		
		ModelAndView mav = new ModelAndView();
		
		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}
		
		try{
			
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto = anagraficaService.getAnagraficaById(Long.parseLong(id));
			
			mav.addObject("isAmministratore",Boolean.valueOf((SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))));
			mav.addObject("anagUffCapAccDto", anagraficaUfficioCapitoloAccertamentoDto);
			mav.setViewName("visualizzaDettaglioAnagrafica");
		
		}catch (Exception e) {
			logger.error("Impossibile visualizzare il dettaglio dell'anagrafica Ufficio Capitolo Accertamento", e);
			mav = new ModelAndView();
			mav.setViewName("message");
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.dettaglio.info.error"));
			mav.addObject("messagesDto", errMsgDto);
		}
		return mav;
	}
	
	
	/**
	 * Ritorna la view per la modifica del dettaglio dell'anagrafica
	 * 
	 * @param request
	 * @param id
	 * @param codAccertamento
	 * @param codCapitolo
	 * @param codTipoDovuto
	 * @param deTipoDovuto
	 * @param codUfficio
	 * @param deAccertamento
	 * @param deAnnoEsercizio
	 * @param deCapitolo
	 * @param deUfficio
	 * @param flgAttivo
	 * @return
	 * @throws Exception
	 * @author Alessandro Paolillo
	 */
	@RequestMapping(value = { "/modificaAnagrafica.html" }, method = RequestMethod.GET)
	public ModelAndView modificaDettaglio(HttpServletRequest request,
			@RequestParam(required = false) String id
			) throws Exception{
		
		ModelAndView mav;
		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		} else {
			mav = commonModificaDettaglio(request, enteTO, id);
		}
		return mav;
	}

	/**
	 * Ritorna la view per la modifica del dettaglio dell'anagrafica
	 * 
	 * @param request
	 * @param enteTO
	 * @param id
	 * @param codAccertamento
	 * @param codCapitolo
	 * @param codTipoDovuto
	 * @param deTipoDovuto
	 * @param codUfficio
	 * @param deAccertamento
	 * @param deAnnoEsercizio
	 * @param deCapitolo
	 * @param deUfficio
	 * @param flgAttivo
	 * @return
	 * @author Alessandro Paolillo
	 */
	private ModelAndView commonModificaDettaglio(HttpServletRequest request, EnteTO enteTO, String id) {
		
		ModelAndView mav = new ModelAndView();
		
		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}
		
		try{
			
			AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto = anagraficaService.getAnagraficaById(Long.parseLong(id));
			
			AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand = new AnagraficaUfficioCapitoloAccertamentoCommand();
			
			valorizzaCommand(anagraficaUfficioCapitoloAccertamentoDto, anagraficaUfficioCapitoloAccertamentoCommand);
			
			////////////INIZIO RECUPERO LISTA DEI TIPIDOVUTO PER ENTE SELEZIONATO/////////////////		
			/**
			 * Recupero la lista dei tipi dovuto dell'ente per cui l'utente è un operatore attivo. 
			 * Se la lista è vuota, mostro un messaggio di errore perchè vuol dire che l'utente non può ne consultare ne creare accertamenti.
			 */
			List<EnteTipoDovutoTO> activeOperatoreEnteTdAsObj = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
			
			if (activeOperatoreEnteTdAsObj == null || activeOperatoreEnteTdAsObj.isEmpty()) {
				logger.warn("RICERCA :: GESTIONE ANAGRAFICA UFFICIO CAPITOLO ACCERTAMENTO :: Utente[codFedUserId: " + SecurityContext.getUtente().getCodFedUserId() + "] :: NON risulta OPERATORE per nessun tipo dovuto.");
				/* 
				 * Redirect alla pagina di "Messaggio" per segnalare l'errore.
				 */
				return UtilitiesCtrl.getViewErrorMessage("mypivot.messages.error.nessunTipoDovutoAssegnato");
			}
			
			////////////////FINE RECUPERO LISTA DEI TIPIDOVUTO/////////////////////////////
	
			mav.addObject("listaTipiDovuti", activeOperatoreEnteTdAsObj);
			mav.addObject("isAmministratore",Boolean.valueOf((SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))));
			mav.addObject("anagUffCapAccDto", anagraficaUfficioCapitoloAccertamentoDto);
			mav.addObject(anagraficaUfficioCapitoloAccertamentoCommand);
			mav.setViewName("modificaDettaglioAnagrafica");
		
		}catch (Exception e) {
			logger.error("Impossibile modificare il dettaglio dell'anagrafica Ufficio Capitolo Accertamento", e);
			mav = new ModelAndView();
			mav.setViewName("message");
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.dettaglio.info.error"));
			mav.addObject("messagesDto", errMsgDto);
		}
		return mav;
	}

	/**
	 * Prevalorizzo il command da passare alla pagina della modifica del dettaglio anagrafica
	 * 
	 * @param anagraficaUfficioCapitoloAccertamentoDto
	 * @param anagraficaUfficioCapitoloAccertamentoCommand
	 * @author Alessandro Paolillo
	 */
	private void valorizzaCommand(AnagraficaUfficioCapitoloAccertamentoDto anagUffCapAccDto,
			AnagraficaUfficioCapitoloAccertamentoCommand anagUffCapAccCommand) {
		
		anagUffCapAccCommand.setAnagraficaUffCapAccId(anagUffCapAccDto.getId());
		anagUffCapAccCommand.setCodiceAccertamento(anagUffCapAccDto.getCodAccertamento());
		anagUffCapAccCommand.setCodiceCapitolo(anagUffCapAccDto.getCodCapitolo());
		anagUffCapAccCommand.setCodiceUfficio(anagUffCapAccDto.getCodUfficio());
		anagUffCapAccCommand.setDenominazioneAccertamento(anagUffCapAccDto.getDeAccertamento());
		anagUffCapAccCommand.setDenominazioneCapitolo(anagUffCapAccDto.getDeCapitolo());
		anagUffCapAccCommand.setDenominazioneUfficio(anagUffCapAccDto.getDeUfficio());
		anagUffCapAccCommand.setAnnoEsercizio(anagUffCapAccDto.getDeAnnoEsercizio());
		anagUffCapAccCommand.setCodTipoDovuto(anagUffCapAccDto.getCodTipoDovuto());
		anagUffCapAccCommand.setFlgAttivo(anagUffCapAccDto.getFlgAttivo());
		
		anagUffCapAccCommand.setIdDto(anagUffCapAccDto.getId());
		anagUffCapAccCommand.setCodAccertamentoDto(anagUffCapAccDto.getCodAccertamento());
		anagUffCapAccCommand.setCodCapitoloDto(anagUffCapAccDto.getCodCapitolo());
		anagUffCapAccCommand.setCodUfficioDto(anagUffCapAccDto.getCodUfficio());
		anagUffCapAccCommand.setDeAccertamentoDto(anagUffCapAccDto.getDeAccertamento());
		anagUffCapAccCommand.setDeCapitoloDto(anagUffCapAccDto.getDeCapitolo());
		anagUffCapAccCommand.setDeUfficioDto(anagUffCapAccDto.getDeUfficio());
		anagUffCapAccCommand.setDeAnnoEsercizioDto(anagUffCapAccDto.getDeAnnoEsercizio());
		anagUffCapAccCommand.setCodTipoDovutoDto(anagUffCapAccDto.getCodTipoDovuto());
		anagUffCapAccCommand.setDeTipoDovutoDto(anagUffCapAccDto.getDeTipoDovuto());
		anagUffCapAccCommand.setFlgAttivoDto(anagUffCapAccDto.getFlgAttivo());
	}
	
	
	@RequestMapping(value = { "/modificaAnagrafica.html" }, method = RequestMethod.POST)
	public ModelAndView modificaAnagrafica(
			@ModelAttribute("anagraficaUfficioCapitoloAccertamentoCommand") AnagraficaUfficioCapitoloAccertamentoCommand anagraficaUfficioCapitoloAccertamentoCommand,
			HttpServletRequest request, BindingResult result) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		
		EnteTO enteTO = SecurityContext.getEnte();
		UtenteTO utenteTO = SecurityContext.getUtente();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		} else if (utenteTO == null || !SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
			mav = new ModelAndView();
			// ERRORE, tentativo di accedere ad ente non autorizzato
			logger.error("Tentativo di modifica anagrafica non autorizzata");
			mav.setViewName("message");
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);
			} else {
				
				/**
				 * Validazione form
				 */
				validator.validate(anagraficaUfficioCapitoloAccertamentoCommand, result);
				
				MessagesDto messagesDto = new MessagesDto();
				if (anagraficaUfficioCapitoloAccertamentoCommand == null 
						|| anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo() == null
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto())
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo())
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo())
						|| StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio())) {
					
					String errMsg = "Mancano dati obbligatori la modifica dell'anagrafica "+ anagraficaUfficioCapitoloAccertamentoCommand.stampaObbligatori();
					logger.error(errMsg);
					messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.dettaglioAnagrafica.modAnagrafica.nocommand"));
					
					if (result.hasErrors()){
						List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
						
						AnagraficaUfficioCapitoloAccertamentoDto anagDto = new AnagraficaUfficioCapitoloAccertamentoDto();
						//POPOLA DTO DAL COMMAND
						anagDto = popolaOldDto(anagraficaUfficioCapitoloAccertamentoCommand);
						mav.addObject("anagUffCapAccDto", anagDto);
						
						mav.addObject("listaTipiDovuti", activeOperatoreEnteTd);
						mav.addObject(anagraficaUfficioCapitoloAccertamentoCommand);
						mav.setViewName("modificaDettaglioAnagrafica");
						return mav;
					}
					
					}else{
						
						if (result.hasErrors()){
							List<EnteTipoDovutoTO> activeOperatoreEnteTd = operatoreEnteTipoDovutoService.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(SecurityContext.getUtente().getCodFedUserId(), SecurityContext.getEnte().getCodIpaEnte());
							
							AnagraficaUfficioCapitoloAccertamentoDto anagDto = new AnagraficaUfficioCapitoloAccertamentoDto();
							//POPOLA DTO DAL COMMAND
							anagDto = popolaOldDto(anagraficaUfficioCapitoloAccertamentoCommand);
							mav.addObject("anagUffCapAccDto", anagDto);
							
							mav.addObject("listaTipiDovuti", activeOperatoreEnteTd);
							mav.addObject(anagraficaUfficioCapitoloAccertamentoCommand);
							mav.setViewName("modificaDettaglioAnagrafica");
							return mav;
						}
						String esitoMsg="";
						
						AnagraficaUfficioCapitoloAccertamentoDto anagraficaUfficioCapitoloAccertamentoDto = new AnagraficaUfficioCapitoloAccertamentoDto();
						anagraficaUfficioCapitoloAccertamentoDto.setId(anagraficaUfficioCapitoloAccertamentoCommand.getAnagraficaUffCapAccId());
						
						anagraficaUfficioCapitoloAccertamentoDto.setDtCreazione(new Date());
						anagraficaUfficioCapitoloAccertamentoDto.setDtUltimaModifica(new Date());
						anagraficaUfficioCapitoloAccertamentoDto.setDeAnnoEsercizio(anagraficaUfficioCapitoloAccertamentoCommand.getAnnoEsercizio());
						anagraficaUfficioCapitoloAccertamentoDto.setCodCapitolo(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceCapitolo());
						anagraficaUfficioCapitoloAccertamentoDto.setDeCapitolo(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneCapitolo());
						anagraficaUfficioCapitoloAccertamentoDto.setFlgAttivo(anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivo());
						if (StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio())
								|| anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio().equals("n/a")){
							anagraficaUfficioCapitoloAccertamentoDto.setFlgAttivo(true);
							anagraficaUfficioCapitoloAccertamentoDto.setCodUfficio("n/a");
							anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio("n/a");
						} else {
							anagraficaUfficioCapitoloAccertamentoDto.setCodUfficio(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceUfficio());
							if (StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio()))
								anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio("n/a");
							else
								anagraficaUfficioCapitoloAccertamentoDto.setDeUfficio(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneUfficio());
						}
						/**
						 * Controllo se il flag è stato cambiato, se si riporto il cambiamento per applicare l'update a tutti i record con tale ufficio
						 */
						if(anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivoCambiato() != null && anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivoCambiato()){
							anagraficaUfficioCapitoloAccertamentoDto.setFlgAttivoCambiato(anagraficaUfficioCapitoloAccertamentoCommand.getFlgAttivoCambiato());
						}
						
						anagraficaUfficioCapitoloAccertamentoDto.setEnte(enteTO);
						
						if(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento()))
							anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento("n/a");
						else
							anagraficaUfficioCapitoloAccertamentoDto.setDeAccertamento(anagraficaUfficioCapitoloAccertamentoCommand.getDenominazioneAccertamento());
						
						if(StringUtils.isBlank(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento()))
							anagraficaUfficioCapitoloAccertamentoDto.setCodAccertamento("n/a");
						else
							anagraficaUfficioCapitoloAccertamentoDto.setCodAccertamento(anagraficaUfficioCapitoloAccertamentoCommand.getCodiceAccertamento());
						
						String codTipoDovuto = anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto();
						

						AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto esitoAggiornamento = null;
						//se non contiene , vuol dire che ho selezionato solo un valore, procedo all'aggiornamento
						if (!codTipoDovuto.contains(",")){
							anagraficaUfficioCapitoloAccertamentoDto.setCodTipoDovuto(anagraficaUfficioCapitoloAccertamentoCommand.getCodTipoDovuto());
							esitoAggiornamento = anagraficaService.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto);
						}
						//altrimenti devo effettuare un update e gli inserimenti per gli altri codici tipo dovuto
						else{
							String listCodTipoDovuto[] = codTipoDovuto.split(",");
							
							
							try {
								
									//il primo sarà sempre una modifica
									anagraficaUfficioCapitoloAccertamentoDto.setCodTipoDovuto(listCodTipoDovuto[0]);
									esitoAggiornamento = anagraficaService.aggiornaAnagrafica(anagraficaUfficioCapitoloAccertamentoDto);
									ESITO esito = esitoAggiornamento.getEsito();
									if (esito.toString() == "AGGIORNATA"){
										anagraficaService.salvaAnagrafiche(anagraficaUfficioCapitoloAccertamentoDto, listCodTipoDovuto, false);
									}
								
							} catch (Exception e){
								logger.error("Errore nell'aggiornamento dell'anagrafica");
								messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko"));
								esitoMsg="ERROR";
							}
						}
						
						
						
						
						AnagraficaUfficioCapitoloAccertamentoDto aggDto = esitoAggiornamento.getAnagraficaAggiornata();
						
						ESITO esito = esitoAggiornamento.getEsito();
						
						switch (esito) {
							case AGGIORNATA:
								mav = commonVisualizzaDettaglio(request, enteTO, String.valueOf(aggDto.getId()));
								logger.debug("Anagrafica aggiornata correttamente [" + esito + "]");
								messagesDto.getInformationMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ok"));
								esitoMsg="AGGIORNATA";
								break;
							case EXIST:
								mav = commonModificaDettaglio(request, enteTO, String.valueOf(aggDto.getId()));
								logger.debug("Anagrafica già esistente [" + esito + "]");
								messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko.exist"));
								esitoMsg="EXIST";
								break;
							case ERROR:
								mav = commonVisualizzaDettaglio(request, enteTO, String.valueOf(aggDto.getId()));
								logger.error("Errore nell'aggiornamento dell'anagrafica");
								messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko"));
								esitoMsg="ERROR";
								break;
							default:
								mav = commonVisualizzaDettaglio(request, enteTO, String.valueOf(aggDto.getId()));
								logger.error("Esito non previsto nell'aggiornamento dell'anagrafica");
								messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.anagrafica.info.updateAnagrafica.ko"));
								break;
						}
						
						mav.addObject("esitoMsg",esitoMsg);
						mav.addObject("messagesDto", messagesDto);
					}
				}
		return mav;
	}

	/**
	 * 
	 * @param anagraficaComm
	 * @return
	 * @author Alessandro Paolillo
	 */
	private AnagraficaUfficioCapitoloAccertamentoDto popolaOldDto(
			AnagraficaUfficioCapitoloAccertamentoCommand anagraficaComm) {
		
		AnagraficaUfficioCapitoloAccertamentoDto oldDto = new AnagraficaUfficioCapitoloAccertamentoDto();
		oldDto.setId(anagraficaComm.getIdDto());
		oldDto.setCodAccertamento(anagraficaComm.getCodAccertamentoDto());
		oldDto.setCodCapitolo(anagraficaComm.getCodCapitoloDto());
		oldDto.setCodTipoDovuto(anagraficaComm.getCodTipoDovutoDto());
		oldDto.setDeTipoDovuto(anagraficaComm.getDeTipoDovutoDto());
		oldDto.setCodUfficio(anagraficaComm.getCodUfficioDto());
		oldDto.setDeAccertamento(anagraficaComm.getDeAccertamentoDto());
		oldDto.setDeAnnoEsercizio(anagraficaComm.getDeAnnoEsercizioDto());
		oldDto.setDeCapitolo(anagraficaComm.getDeCapitoloDto());
		oldDto.setDeUfficio(anagraficaComm.getDeUfficioDto());
		oldDto.setFlgAttivo(anagraficaComm.isFlgAttivoDto());
		
		return oldDto;
	}
	
	

}
