/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.command.SegnalazioneCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio.DettaglioDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.flussorendicontazione.DettaglioFlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.ImportExportRendicontazioneTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.DettaglioUtils;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * @author Riccardo Cannas
 */
@Controller
@RequestMapping("/protected")
public class DettaglioController {

	private static Log log = LogFactory.getLog(DettaglioController.class);

	@Autowired
	private ImportExportRendicontazioneTesoreriaService importExportRendicontazioneTesoreriaService;

	@Autowired
	private SegnalazioneService segnalazioneService;

	@Autowired
	private FlussoRendicontazioneService flussoRendicontazioneService;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;
	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;
	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@RequestMapping(value = { "/visualizzaDettaglio.html" }, method = RequestMethod.GET)
	public ModelAndView visualizzaDettaglio(HttpServletRequest request,
			@RequestParam(required = false) String classificazioneCompletezza,
			@RequestParam(required = false) String codiceIuv,
			@RequestParam(required = false) String identificativoFlussoRendicontazione,
			@RequestParam(required = false) String codiceIud, @RequestParam(required = true) String tipoVisualizzazione)
			throws Exception {
		ModelAndView mav;
		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		} else {
			mav = commonVisualizzaDettaglio(request, enteTO, classificazioneCompletezza, codiceIuv,
					identificativoFlussoRendicontazione, codiceIud, tipoVisualizzazione);
		}
		return mav;

	}

	private ModelAndView commonVisualizzaDettaglio(HttpServletRequest request, EnteTO enteTO,
			String classificazioneCompletezza, String codiceIuv, String codiceIuf, String codiceIud,
			String tipoVisualizzazione) {
		ModelAndView mav = new ModelAndView();

		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}

		if (!SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");

			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		ImportExportRendicontazioneTesoreriaTO recordVista = importExportRendicontazioneTesoreriaService
				.getByEnteAndClassificazioneCompletezzaAndCodIuvAndCodIufAndCodIud(enteTO.getCodIpaEnte(),
						classificazioneCompletezza, codiceIuv, codiceIuf, codiceIud);

		List<EnteTipoDovuto> listaEnteTipoDovuto = enteTipoDovutoService.getByCodIpaEnte(enteTO.getCodIpaEnte());
		List<EnteTipoDovutoTO> listaEnteTipoDovutoTO = mapListaEnteTipoDovutoInListaEnteTipoDovutoTO(
				listaEnteTipoDovuto);

		DettaglioDto dettaglioDto = DettaglioUtils.createDettaglioDtoFromImportExportRendicontazioneTesoreriaTO(
				recordVista, enteTO, listaEnteTipoDovutoTO);
		mav.addObject("dettaglio", dettaglioDto);

		SegnalazioneKeyDto key = new SegnalazioneKeyDto(enteTO.getCodIpaEnte(), classificazioneCompletezza, codiceIuf,
				codiceIud, codiceIuv);
		SegnalazioneTO segnalazione = segnalazioneService.findAttiveByKey(key);

		SegnalazioneCommand command = new SegnalazioneCommand();
		command.setClassificazioneCompletezza(classificazioneCompletezza);
		command.setCodIuv(codiceIuv);
		command.setCodIuf(codiceIuf);
		command.setCodIud(codiceIud);
		command.setTipoVisualizzazione(tipoVisualizzazione);

		if (segnalazione != null && segnalazione.getId() != null) {
			command.setIdSegnalazione(segnalazione.getId());
			command.setDeNota(segnalazione.getDeNota());
			command.setFlgNascosto(segnalazione.getFlgNascosto());
		} else {
			command.setDeNota("");
			command.setFlgNascosto(Boolean.FALSE);
		}
		command.setRendicontazione(Boolean.FALSE);

		mav.addObject("segnalazioneCommand", command);

		mav.addObject("isAmministratore",
				Boolean.valueOf((SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))));

		if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue())) {
			mav.addObject("isRiconciliazione", true);
			mav.addObject("isAnomalie", false);
		}

		if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue())) {
			mav.addObject("isRiconciliazione", false);
			mav.addObject("isAnomalie", true);
		}

		mav.setViewName("visualizzaDettaglio");
		return mav;
	}

	@RequestMapping(value = { "/aggiungiSegnalazione.html" }, method = RequestMethod.POST)
	public ModelAndView aggiungiSegnalazione(
			@ModelAttribute("segnalazioneCommand") SegnalazioneCommand segnalazioneCommand,
			HttpServletRequest request) {
		ModelAndView mav;

		EnteTO enteTO = SecurityContext.getEnte();
		UtenteTO utenteTO = SecurityContext.getUtente();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		} else if (utenteTO == null || !SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE)) {
			mav = new ModelAndView();
			// ERRORE, tentativo di accedere ad ente non autorizzato
			log.error("Tentativo di aggiunta di una segnalazione non autorizzata");
			mav.setViewName("message");
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);
		} else {
			MessagesDto messagesDto = new MessagesDto();
			if (segnalazioneCommand == null || StringUtils.isBlank(segnalazioneCommand.getClassificazioneCompletezza())//
					|| StringUtils.isBlank(segnalazioneCommand.getDeNota())//
			// && segnalazioneCommand.getFlgNascosto() != null//
			) {
				String errMsg = "Mancano dati obbligatori per aggiungere la segnalazione classificazioneCompletezza["
						+ segnalazioneCommand.getClassificazioneCompletezza() + "], nota["
						+ segnalazioneCommand.getDeNota() + "], nascosto[" + segnalazioneCommand.getFlgNascosto() + "]";
				log.error(errMsg);
				messagesDto.getErrorMessages()
						.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.nocommand"));
			} else {
				AggiornaSegnalazioneDto segnalazione = new AggiornaSegnalazioneDto();
				if (segnalazioneCommand.getIdSegnalazione() != null) {
					log.debug("Aggionramento sengalazione id[" + segnalazioneCommand.getIdSegnalazione() + "]");
					segnalazione.setIdSegnalazione(segnalazioneCommand.getIdSegnalazione());
				} else {
					log.debug("Creazione di una nuova segnalazione");
					segnalazione.setEnte(enteTO);
					segnalazione.setClassificazioneCompletezza(segnalazioneCommand.getClassificazioneCompletezza());
					String codIud = StringUtils.isNotBlank(segnalazioneCommand.getCodIud())
							? segnalazioneCommand.getCodIud() : null;
					String codIuv = StringUtils.isNotBlank(segnalazioneCommand.getCodIuv())
							? segnalazioneCommand.getCodIuv() : null;
					String codIuf = StringUtils.isNotBlank(segnalazioneCommand.getCodIuf())
							? segnalazioneCommand.getCodIuf() : null;

					segnalazione.setCodIud(codIud);
					segnalazione.setCodIuv(codIuv);
					segnalazione.setCodIuf(codIuf);
				}

				segnalazione.setUtente(utenteTO);
				segnalazione.setDeNota(segnalazioneCommand.getDeNota());
				segnalazione.setFlgNascosto(Boolean.TRUE.equals(segnalazioneCommand.getFlgNascosto()));

				AggiornaSegnalazioneResultDto esitoAggiornamento = segnalazioneService
						.aggiornaSegnalazione(segnalazione);
				ESITO esito = esitoAggiornamento.getEsito();
				switch (esito) {
				case INSERITA:
				case AGGIORNATA:
					log.debug("Segnalazione aggiornata correttamente [" + esito + "]. [" + esitoAggiornamento.getMsg()
							+ "]");
					messagesDto.getInformationMessages()
							.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.ok"));
					break;
				case NO_NEED_TO_UPDATE:
					log.debug("Segnalazione gia aggiornata. [" + esitoAggiornamento.getMsg() + "]");
					messagesDto.getInformationMessages()
							.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.noneedtoupdate"));
					break;
				case ERROR:
					log.error("Errore nell'aggiornamento della segnalazione. [" + esitoAggiornamento.getMsg() + "]");

					messagesDto.getErrorMessages().add(new DynamicMessageDto(
							"mypivot.dettaglio.info.addsegnalazione.ko", esitoAggiornamento.getMsg()));
					break;
				default:
					log.error(
							"Esito non previsto [" + esito + "], all'utente viene comunque segnalato messaggio di ok");
					messagesDto.getInformationMessages()
							.add(new DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.ok"));
					break;
				}
			}
			if (Boolean.TRUE.equals(segnalazioneCommand.getRendicontazione())) {
				mav = visualizzaDettaglioRendicontazione(request, enteTO,
						segnalazioneCommand.getClassificazioneCompletezza(), segnalazioneCommand.getCodIuf(), null,
						null, segnalazioneCommand.getTipoVisualizzazione());
			} else {
				mav = commonVisualizzaDettaglio(request, enteTO, segnalazioneCommand.getClassificazioneCompletezza(),
						segnalazioneCommand.getCodIuv(), segnalazioneCommand.getCodIuf(),
						segnalazioneCommand.getCodIud(), segnalazioneCommand.getTipoVisualizzazione());
			}
			mav.addObject("messagesDto", messagesDto);
			// } else {
			// String errMsg = "Mancano dati obbligatori per aggiungere la
			// segnalazione classificazioneCompletezza["
			// + segnalazioneCommand.getClassificazioneCompletezza() + "],
			// nota["
			// + segnalazioneCommand.getDeNota() + "], nascosto[" +
			// segnalazioneCommand.getFlgNascosto() + "]";
			// log.error(errMsg);
			// MessagesDto errMsgDto = new MessagesDto();
			// errMsgDto.getErrorMessages()
			// .add(new
			// DynamicMessageDto("mypivot.dettaglio.info.addsegnalazione.nocommand"));
			//
			// mav = new ModelAndView();
			// mav.addObject("messagesDto", errMsgDto);
			// mav.setViewName("message");
			// }
		}
		return mav;
	}

	@RequestMapping(value = { "/visualizzaDettaglioRendicontazione.html" }, method = RequestMethod.GET)
	public ModelAndView visualizzaDettaglio(HttpServletRequest request,
			@RequestParam(required = false) String classificazioneCompletezza,
			@RequestParam(required = false) String codiceIuf, @RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer pageSize,
			@RequestParam(required = true) String tipoVisualizzazione) {

		ModelAndView mav;
		EnteTO enteTO = SecurityContext.getEnte();
		if (enteTO == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		} else {
			mav = visualizzaDettaglioRendicontazione(request, enteTO, classificazioneCompletezza, codiceIuf, page,
					pageSize, tipoVisualizzazione);
		}
		return mav;
	}

	private ModelAndView visualizzaDettaglioRendicontazione(HttpServletRequest request, EnteTO enteTO,
			String classificazioneCompletezza, String codiceIuf, Integer page, Integer pageSize,
			String tipoVisualizzazione) {
		UtenteTO utenteTO = SecurityContext.getUtente();
		ModelAndView mav = new ModelAndView();
		if (SecurityContext.getRoles() == null) {
			return new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
		}
		if (utenteTO == null || !SecurityContext.getRoles().contains(Constants.RUOLO_VISUALIZZATORE)) {
			mav.setViewName("message");
			MessagesDto messagesDto = new MessagesDto();
			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);
			return mav;
		}
		try {
			page = (page != null) ? page : 0; // valori di dft
			pageSize = (pageSize != null) ? pageSize : 5; // valori di dft

			Collection<String> codTipoDovutoValidi = new HashSet<>();
			// TODO DA RECUPERARE DA PARAMETRO, SE "TUTTI" ALLORA RECUPERARE LA
			// LISTA
			List<EnteTipoDovutoTO> enteTipoDovutoTOList = operatoreEnteTipoDovutoService
					.getListaEnteTipoDovutoForOperatoreAndCodIpaEnte(utenteTO.getCodFedUserId(),
							enteTO.getCodIpaEnte());
			if (CollectionUtils.isNotEmpty(enteTipoDovutoTOList)) {
				for (EnteTipoDovutoTO enteTipoDovutoTO : enteTipoDovutoTOList) {
					codTipoDovutoValidi.add(enteTipoDovutoTO.getCodTipo());
				}
			}

			ImportExportRendicontazioneTesoreriaTO recordVistaTesoreria = importExportRendicontazioneTesoreriaService
					.getByEnteAndClassificazioneCompletezzaAndCodIuf(enteTO.getCodIpaEnte(), classificazioneCompletezza,
							codiceIuf);

			PageDto<DettaglioFlussoRendicontazioneDto> dettaglioFlussoRendicontazionePaginato = flussoRendicontazioneService
					.findDettagliFlussoRendicontazione(enteTO.getCodIpaEnte(), codiceIuf, codTipoDovutoValidi, page,
							pageSize);

			DettaglioDto dettaglioDto = DettaglioUtils.createDettaglioDtoRendicontazione(classificazioneCompletezza,
					codiceIuf, dettaglioFlussoRendicontazionePaginato, enteTO, recordVistaTesoreria);
			mav.addObject("dettaglio", dettaglioDto);

			SegnalazioneKeyDto key = new SegnalazioneKeyDto(enteTO.getCodIpaEnte(), classificazioneCompletezza,
					codiceIuf, null, null);
			SegnalazioneTO segnalazione = segnalazioneService.findAttiveByKey(key);

			SegnalazioneCommand command = new SegnalazioneCommand();
			command.setClassificazioneCompletezza(classificazioneCompletezza);
			command.setCodIuv(null);
			command.setCodIuf(codiceIuf);
			command.setCodIud(null);
			command.setTipoVisualizzazione(tipoVisualizzazione);

			if (segnalazione != null && segnalazione.getId() != null) {
				command.setIdSegnalazione(segnalazione.getId());
				command.setDeNota(segnalazione.getDeNota());
				command.setFlgNascosto(segnalazione.getFlgNascosto());
			} else {
				command.setDeNota("");
				command.setFlgNascosto(Boolean.FALSE);
			}
			command.setRendicontazione(Boolean.TRUE);
			mav.addObject("segnalazioneCommand", command);

			mav.addObject("isAmministratore",
					Boolean.valueOf((SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))));

			mav.setViewName("visualizzaDettaglio");
		} catch (MyPivotServiceException e) {
			log.error("Impossibile visualizzare il dettaglio", e);
			mav = new ModelAndView();
			mav.setViewName("message");
			MessagesDto errMsgDto = new MessagesDto();
			errMsgDto.getErrorMessages().add(new DynamicMessageDto("mypivot.dettaglio.info.error"));
			mav.addObject("messagesDto", errMsgDto);
		}
		
		if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.RICONCILIAZIONE.getValue())) {
			mav.addObject("isRiconciliazione", true);
			mav.addObject("isAnomalie", false);
		}

		if (tipoVisualizzazione.equals(Constants.TIPO_VISUALIZZAZIONE.ANOMALIE.getValue())) {
			mav.addObject("isRiconciliazione", false);
			mav.addObject("isAnomalie", true);
		}
		
		return mav;

	}

	private List<EnteTipoDovutoTO> mapListaEnteTipoDovutoInListaEnteTipoDovutoTO(
			List<EnteTipoDovuto> listaEnteTipoDovuto) {
		Assert.notEmpty(listaEnteTipoDovuto);
		List<EnteTipoDovutoTO> listaEnteTipoDovutoTO = new ArrayList<EnteTipoDovutoTO>();
		for (EnteTipoDovuto etd : listaEnteTipoDovuto) {
			EnteTipoDovutoTO etdTO = modelMapperUtil.map(etd, EnteTipoDovutoTO.class);
			listaEnteTipoDovutoTO.add(etdTO);
		}
		return listaEnteTipoDovutoTO;
	}
}
