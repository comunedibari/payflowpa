/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mybox.client.MyBoxClient;
import it.regioneveneto.mybox.domain.Errore;
import it.regioneveneto.mybox.domain.IntestazioneRisposta;
import it.regioneveneto.mybox.domain.MyBoxAuthorize;
import it.regioneveneto.mybox.domain.MyBoxAuthorizeRisposta;
import it.regioneveneto.mygov.payment.mypivot.controller.command.FlussiUploadCommand;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ResponseDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.UploadEsitoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ManageFlusso;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.ManageFlussoService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;
import it.regioneveneto.mygov.payment.mypivot.utils.SessionVariables;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

/**
 * @author Giorgio Vallini
 * @author Cristiano Perin
 */
@Controller
@RequestMapping("/protected/carica")
public class FlussiUploadController {

	private static Log log = LogFactory.getLog(FlussiUploadController.class);

	@Autowired
	private MyBoxClient myBoxClient;

	@Autowired
	private ManageFlussoService manageFlussoService;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private Environment environment;

	public FlussiUploadController() {
		super();
	}

	@RequestMapping(value = { "/flussiUpload.html" }, method = RequestMethod.GET)
	public ModelAndView getFlussiUpload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(required = true) String tipoFlusso, @RequestParam(required = false) Boolean forceClear,
			@RequestParam(required = false) String pg, @RequestParam(required = false) String pgSize,
			@RequestParam(required = false) String dtFrom, @RequestParam(required = false) String dtTo,
			@RequestParam(required = false) String fSearch, @RequestParam(required = false) String command,
			@RequestParam(required = false) String cod, ModelAndView mav) {

		/*
		 * //CONTROLLO ROBUSTEZZA: CHECK SE L'UTENTE PUO' ESSERE OPERATORE
		 * DELL'ENTE CORRENTE if (!SecurityContext.isOperatoreOnCurrentEnte()) {
		 * mav = new ModelAndView(); MessagesDto messagesDto = new
		 * MessagesDto(); messagesDto.getErrorMessages().add(new
		 * DynamicMessageDto("pa.messages.notOperatorAccess",
		 * SecurityContext.getEnte().getDeNomeEnte().toUpperCase()));
		 * mav.addObject("messagesDto", messagesDto);
		 * mav.addObject("altMessageImg", "Accesso vietato");
		 * mav.addObject("pageTitle", "Accesso vietato");
		 * mav.addObject("imgPath", "/pa/resources/img/accessoVietato.jpg");
		 * mav.setViewName("errorComunication"); return mav; }
		 */

		EnteTO ente = SecurityContext.getEnte();
		if (ente == null) {
			mav.setView(new RedirectView(request.getContextPath()
					+ "/protected/sceltaEnte.html?redirectUrl=protected/carica/flussiUpload.html?tipoFlusso="
					+ tipoFlusso));
			return mav;
		}

		// CHECK TIPO FLUSSO VALIDO
		if (!tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_EXPORT_PAGATI.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_STANDARD.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_TESORERIA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI.toString())) {
			mav.setView(
					new RedirectView(request.getContextPath() + "/protected/carica/flussiUpload.html?tipoFlusso=E"));
			return mav;
		}

		// Check se tesoreria abilitata per l'ente selezionato
		if (!ente.getFlgTesoreria() && tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())) {
			mav.setView(
					new RedirectView(request.getContextPath() + "/protected/carica/flussiUpload.html?tipoFlusso=E"));
			return mav;
		}

		UtenteTO utente = SecurityContext.getUtente();
		MessagesDto messagesDto = new MessagesDto();

		if ((SecurityContext.getRoles() == null) || (SecurityContext.getRoles() != null
				&& !SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))) {
			mav.setViewName("message");

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utente.getCodFedUserId(), ente.getCodIpaEnte()))) {
			mav.setViewName("message");

			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		FlussiUploadCommand flussiUploadCommand = new FlussiUploadCommand();

		if (forceClear != null && forceClear) {
			request.getSession().removeAttribute(SessionVariables.FLUSSI_UPLOAD_PG);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_UPLOAD_PG_SIZE);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_UPLOAD_DT_FROM);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_UPLOAD_DT_TO);
			request.getSession().removeAttribute(SessionVariables.FLUSSI_UPLOAD_F_SEARCH);
		}

		setFilters(request, pg, pgSize, dtFrom, dtTo, fSearch, flussiUploadCommand);

		initialize(flussiUploadCommand);

		String dataFromStr = flussiUploadCommand.getDateFrom();
		String dataToStr = flussiUploadCommand.getDateTo();
		Date dateFrom = null;
		Date dateTo = null;

		// DATE FROM
		if (StringUtils.isNotBlank(dataFromStr)) {
			try {
				dateFrom = Constants.DDMMYYYY.parse(dataFromStr);
			} catch (ParseException e) {
				log.error("Errore nel parsing della data inizio nella ricerca dei debiti. DateFrom = [" + dataFromStr
						+ "]", e);
				dateFrom = Constants.MINIMUM_DATE;
			}
		} else {
			// dateFrom = Utilities.getMinimunDate();
		}

		// DATE TO
		if (StringUtils.isNotBlank(dataToStr)) {
			try {
				dateTo = Constants.DDMMYYYY.parse(dataToStr);
			} catch (ParseException e) {
				log.error("Errore nel parsing della data fine nella ricerca dei debiti. DateTo = [" + dataToStr + "]",
						e);
				dateTo = Constants.MAXIMUM_DATE;
			}
		} else {
			// dateTo = Utilities.getMaximumDate();
		}

		// if (command != null) {
		// if (command.equals("remove") && cod != null) {
		// annullaFlusso(cod, messagesDto);
		// }
		// }

		PageDto<FlussoDto> flussiDtoPage = manageFlussoService.getFlussoPage(tipoFlusso.charAt(0),
				utente.getCodFedUserId(), ente.getCodIpaEnte(), flussiUploadCommand.getFullTextSearch(), dateFrom,
				dateTo, flussiUploadCommand.getPage(), flussiUploadCommand.getPageSize(), Direction.DESC,
				"dtCreazione");

		flussiUploadCommand.setEnte(SecurityContext.getEnte().getDeNomeEnte());

		// get authorization token from myBox required for upload

		try {
			requestAuthToken(flussiUploadCommand, messagesDto, ente, mav);

			String requestToken = UUID.randomUUID().toString();
			ManageFlusso manageFlusso = manageFlussoService.insertFlusso(tipoFlusso.charAt(0), ente.getCodIpaEnte(),
					utente.getCodFedUserId(), requestToken, Constants.DE_TIPO_STATO_MANAGE,
					Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD);

			flussiUploadCommand.setRequestToken(requestToken);

			String pathEnvProperty = "mybox.flussi." + tipoFlusso + ".uploadPath";

			flussiUploadCommand.setUploadPath(
					environment.getProperty(pathEnvProperty) + Constants.YYYY_MM.format(manageFlusso.getDtCreazione()));

		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			flussiUploadCommand.setShowUploadForm(false);
		}

		// response.addHeader("Access-Control-Allow-Origin",
		// "http://192.168.44.8");

		mav.setViewName("flussiUpload");
		mav.addObject("messagesDto2", messagesDto);
		mav.addObject("tipoFlusso", tipoFlusso);
		mav.addObject(flussiUploadCommand);
		mav.addObject("flussiDtoPage", flussiDtoPage);
		mav.addObject("isTesoreriaActive", ente.getFlgTesoreria());

		return mav;
	}

	@RequestMapping(value = { "/flussiUpload.html" }, method = RequestMethod.POST)
	public ModelAndView postFlussiUpload(@ModelAttribute("flussiUploadCommand") FlussiUploadCommand flussiUploadCommand,
			HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		MessagesDto messagesDto = new MessagesDto();

		UtenteTO utente = SecurityContext.getUtente();

		if ((SecurityContext.getRoles() == null) || (SecurityContext.getRoles() != null
				&& !SecurityContext.getRoles().contains(Constants.RUOLO_AMMINISTRATORE))) {
			mav.setViewName("message");

			messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.error.notAuthorized"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		// CHECK TIPO FLUSSO VALIDO
		String tipoFlusso = flussiUploadCommand.gettFlusso();
		if (!tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_EXPORT_PAGATI.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_STANDARD.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_TESORERIA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())
				&& !tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI.toString())) {
			mav.setView(
					new RedirectView(request.getContextPath() + "/protected/carica/flussiUpload.html?tipoFlusso=E"));
			return mav;
		}

		EnteTO ente = SecurityContext.getEnte();
		if (ente == null) {
			mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			return mav;
		}

		// Check se tesoreria abilitata per l'ente selezionato
		if (!ente.getFlgTesoreria() && tipoFlusso.equals(Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA.toString())) {
			mav.setView(
					new RedirectView(request.getContextPath() + "/protected/carica/flussiUpload.html?tipoFlusso=E"));
			return mav;
		}

		if (SecurityContext.getAllOperatoreEnteTipoDovuto() == null
				|| SecurityContext.getAllOperatoreEnteTipoDovuto().isEmpty()
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(SecurityContext.getAllOperatoreEnteTipoDovuto())
				|| Utilities.isAllOperatoreEnteTipoDovutoDisabilitati(operatoreEnteTipoDovutoService
						.findActiveByCodFedUserIdAndCodIpaEnte(utente.getCodFedUserId(), ente.getCodIpaEnte()))) {
			mav.setViewName("message");

			messagesDto = new MessagesDto();
			messagesDto.getErrorMessages()
					.add(new DynamicMessageDto("mypivot.messages.error.nessunTipoDovutoAssegnato"));
			mav.addObject("messagesDto", messagesDto);

			return mav;
		}

		initialize(flussiUploadCommand);

		Date dt_data_da = null;

		if (StringUtils.isNotBlank(flussiUploadCommand.getDateFrom())) {
			try {
				dt_data_da = Constants.DDMMYYYY.parse(flussiUploadCommand.getDateFrom());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date dt_data_a = null;

		if (StringUtils.isNotBlank(flussiUploadCommand.getDateTo())) {
			try {
				dt_data_a = Constants.DDMMYYYY.parse(flussiUploadCommand.getDateTo());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		flussiUploadCommand.setPage(1);

		PageDto<FlussoDto> flussiDtoPage = manageFlussoService.getFlussoPage(tipoFlusso.charAt(0),
				utente.getCodFedUserId(), ente.getCodIpaEnte(), flussiUploadCommand.getFullTextSearch(), dt_data_da,
				dt_data_a, flussiUploadCommand.getPage(), flussiUploadCommand.getPageSize(), Direction.DESC,
				"dtCreazione");

		try {
			requestAuthToken(flussiUploadCommand, messagesDto, ente, mav);

			String requestToken = UUID.randomUUID().toString();
			ManageFlusso manageFlusso = manageFlussoService.insertFlusso(tipoFlusso.charAt(0), ente.getCodIpaEnte(),
					utente.getCodFedUserId(), requestToken, Constants.DE_TIPO_STATO_MANAGE,
					Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD);

			flussiUploadCommand.setRequestToken(requestToken);

			String pathEnvProperty = "mybox.flussi." + tipoFlusso + ".uploadPath";

			flussiUploadCommand.setUploadPath(
					environment.getProperty(pathEnvProperty) + Constants.YYYY_MM.format(manageFlusso.getDtCreazione()));

		} catch (Exception e) {
			log.error("Errore di comunicazione con myBox o inserimento in importDovuti: " + e.getMessage());
			flussiUploadCommand.setShowUploadForm(false);
		}

		mav.setViewName("flussiUpload");

		mav.addObject("messagesDto2", messagesDto);
		mav.addObject(flussiUploadCommand);
		mav.addObject("flussiDtoPage", flussiDtoPage);
		mav.addObject("tipoFlusso", tipoFlusso);
		mav.addObject("isTesoreriaActive", ente.getFlgTesoreria());

		// save session filter data
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_PG, flussiUploadCommand.getPage());
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_PG_SIZE, flussiUploadCommand.getPageSize());
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_DT_FROM, flussiUploadCommand.getDateFrom());
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_DT_TO, flussiUploadCommand.getDateTo());
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_F_SEARCH,
				flussiUploadCommand.getFullTextSearch());

		return mav;
	}

	@RequestMapping(value = {
			"/keepAlive.json" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> keepAlive(@RequestParam(required = true) Long _) {
		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("OK");
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}

	@RequestMapping(value = {
			"/flussiUploadEsito.json" }, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseDto> flussiUploadEsito(@RequestBody UploadEsitoDto uploadEsitoDto) {
		log.debug("ESITO UPLOAD [" + uploadEsitoDto.getCodRequestToken() + "] " + uploadEsitoDto.getEsito());

		if (uploadEsitoDto.getEsito().equals("OK")) {
			ManageFlusso manageFlusso = manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
					Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
					Constants.COD_TIPO_STATO_MANAGE_FILE_SCARICATO);

			String pathEnvProperty = "mybox.flussi." + String.valueOf(manageFlusso.getTipoFlusso().getCodTipo())
					+ ".uploadPath";
			String uploadPath = environment.getProperty(pathEnvProperty)
					+ Constants.YYYY_MM.format(manageFlusso.getDtCreazione());

			if (manageFlusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE) {
				try {
					manageFlussoService.completaFlussoPoste(uploadEsitoDto, uploadPath);
				} catch (ParseException e) {
					log.error(e);
					manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
							Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
							Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
					ResponseDto responseDto = new ResponseDto();
					responseDto.setStatus("KO");
					return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
				}
			} else if (manageFlusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA) {
				try {
					boolean valid = validateGiornaleDiCassaCsv(uploadEsitoDto);
					if (!valid) {
						log.error(
								"I dati ricevuti non sono tutti valorizzati, non sono tutti numeri oppure sono duplicati");
						manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
								Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
								Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
						ResponseDto responseDto = new ResponseDto();
						responseDto.setStatus("KO");
						return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
					}
					manageFlussoService.completaFlussoGiornaleDiCassaCsv(uploadEsitoDto, uploadPath);
				} catch (Exception e) {
					log.error(e);
					manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(),
							Constants.DE_TIPO_STATO_MANAGE, Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
							Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
					ResponseDto responseDto = new ResponseDto();
					responseDto.setStatus("KO");
					return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
				}
			} else {
				manageFlussoService.completaFlusso(uploadEsitoDto.getCodRequestToken(), uploadPath,
						uploadEsitoDto.getNomeFile(), uploadEsitoDto.getDimensioneFile());
			}
		} else {
			manageFlussoService.cambiaStatoFlusso(uploadEsitoDto.getCodRequestToken(), Constants.DE_TIPO_STATO_MANAGE,
					Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD, Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD);
		}

		ResponseDto responseDto = new ResponseDto();
		responseDto.setStatus("OK");
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}

	// FILTRI TABBELLA FLUSSI
	private void setFilters(HttpServletRequest request, String pg, String pgSize, String dtFrom, String dtTo,
			String fSearch, FlussiUploadCommand flussiUploadCommand) {
		Object session_pg = request.getSession().getAttribute(SessionVariables.FLUSSI_UPLOAD_PG);
		Object session_pg_size = request.getSession().getAttribute(SessionVariables.FLUSSI_UPLOAD_PG_SIZE);
		Object session_dt_from = request.getSession().getAttribute(SessionVariables.FLUSSI_UPLOAD_DT_FROM);
		Object session_dt_to = request.getSession().getAttribute(SessionVariables.FLUSSI_UPLOAD_DT_TO);
		Object session_f_search = request.getSession().getAttribute(SessionVariables.FLUSSI_UPLOAD_F_SEARCH);

		// PAGE
		setFilter_page(request, pg, session_pg, flussiUploadCommand);

		// PAGE SIZE
		setFilter_pageSize(request, pgSize, session_pg_size, flussiUploadCommand);

		// DATE FROM
		setFilter_dateFrom(request, dtFrom, session_dt_from, flussiUploadCommand);

		// DATE TO
		setFilter_dateTo(request, dtTo, session_dt_to, flussiUploadCommand);

		// FULL TEXT SEARCH
		setFilter_fSearch(request, fSearch, session_f_search, flussiUploadCommand);
	}

	private void setFilter_page(HttpServletRequest request, String request_pg, Object session_pg,
			FlussiUploadCommand flussiUploadCommand) {
		try {
			if (request_pg == null)
				flussiUploadCommand.setPage(Integer.parseInt(session_pg.toString()));
			else {
				flussiUploadCommand.setPage(Integer.parseInt(request_pg));
			}
		} catch (NullPointerException e) {
			flussiUploadCommand.setPage(1);
		} catch (NumberFormatException e) {
			flussiUploadCommand.setPage(1);
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_PG, flussiUploadCommand.getPage());
	}

	private void setFilter_pageSize(HttpServletRequest request, String request_pgSize, Object session_pg_size,
			FlussiUploadCommand flussiUploadCommand) {
		try {
			if (request_pgSize == null)
				flussiUploadCommand.setPageSize(Integer.parseInt(session_pg_size.toString()));
			else
				flussiUploadCommand.setPageSize(Integer.parseInt(request_pgSize));
		} catch (NullPointerException e) {
			flussiUploadCommand.setPageSize(5);
		} catch (NumberFormatException e) {
			flussiUploadCommand.setPageSize(5);
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_PG_SIZE, flussiUploadCommand.getPageSize());
	}

	private void setFilter_dateFrom(HttpServletRequest request, String request_dt_from, Object session_dt_from,
			FlussiUploadCommand flussiUploadCommand) {
		try {
			if (request_dt_from == null)
				flussiUploadCommand.setDateFrom(session_dt_from.toString());
			else
				flussiUploadCommand.setDateFrom(request_dt_from);
		} catch (NullPointerException e) {
			flussiUploadCommand.setDateFrom("");
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_DT_FROM, flussiUploadCommand.getDateFrom());
	}

	private void setFilter_dateTo(HttpServletRequest request, String request_dt_to, Object session_dt_to,
			FlussiUploadCommand flussiUploadCommand) {
		try {
			if (request_dt_to == null)
				flussiUploadCommand.setDateTo(session_dt_to.toString());
			else
				flussiUploadCommand.setDateTo(request_dt_to);
		} catch (NullPointerException e) {
			flussiUploadCommand.setDateTo("");
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_DT_TO, flussiUploadCommand.getDateTo());
	}

	private void setFilter_fSearch(HttpServletRequest request, String request_fSearch, Object session_f_search,
			FlussiUploadCommand flussiUploadCommand) {
		try {
			if (request_fSearch == null)
				flussiUploadCommand.setFullTextSearch(session_f_search.toString());
			else
				flussiUploadCommand.setFullTextSearch(request_fSearch);
		} catch (NullPointerException e) {
			flussiUploadCommand.setFullTextSearch("");
		}
		request.getSession().setAttribute(SessionVariables.FLUSSI_UPLOAD_F_SEARCH,
				flussiUploadCommand.getFullTextSearch());
	}

	private void requestAuthToken(FlussiUploadCommand flussiUploadCommand, MessagesDto messagesDto, EnteTO ente,
			ModelAndView mav) {
		MyBoxAuthorize authorize = new MyBoxAuthorize();
		authorize.setClientKey(ente.getMyboxClientKey());
		authorize.setClientSecret(ente.getMyboxClientSecret());
		MyBoxAuthorizeRisposta risposta = myBoxClient.myBoxAuthorize(authorize);

		IntestazioneRisposta intestazioneRisposta = risposta.getIntestazioneRisposta();

		List<Errore> errori = intestazioneRisposta.getErrori();
		if (!errori.isEmpty()) {
			flussiUploadCommand.setShowUploadForm(false);
			messagesDto.getErrorMessages().add(new DynamicMessageDto("pa.messages.enteNonAutorizzatoPerUploadFlusso"));
		} else {
			String authorizationToken = risposta.getTokenRisposta();

			// UPLOAD STUFF

			flussiUploadCommand.setShowUploadForm(true);
			flussiUploadCommand.setMyBoxContextURL(environment.getProperty("myBox.contextURL"));
			flussiUploadCommand.setAuthorizationToken(authorizationToken);

		}
	}

	private void initialize(FlussiUploadCommand flussiUploadCommand) {

		Date data_da = null;

		if (StringUtils.isNotBlank(flussiUploadCommand.getDateFrom())) {
			try {
				data_da = Constants.DDMMYYYY.parse(flussiUploadCommand.getDateFrom());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		Date data_a = null;

		if (StringUtils.isNotBlank(flussiUploadCommand.getDateTo())) {
			try {
				data_a = Constants.DDMMYYYY.parse(flussiUploadCommand.getDateTo());
			} catch (ParseException e) {
				// Nothing to do
			}
		}

		if ((data_da == null) && (data_a == null)) {
			data_a = new Date();
			data_a = DateUtils.setHours(data_a, 0);
			data_a = DateUtils.setMinutes(data_a, 0);
			data_a = DateUtils.setSeconds(data_a, 0);
			data_a = DateUtils.setMilliseconds(data_a, 0);

			data_da = DateUtils.addMonths(data_a, -1);
		} else if ((data_da != null) && (data_a == null)) {
			data_da = DateUtils.setHours(data_da, 0);
			data_da = DateUtils.setMinutes(data_da, 0);
			data_da = DateUtils.setSeconds(data_da, 0);
			data_da = DateUtils.setMilliseconds(data_da, 0);

			data_a = DateUtils.addMonths(data_da, -1);
		} else if ((data_da == null) && (data_a != null)) {
			data_a = DateUtils.setHours(data_a, 0);
			data_a = DateUtils.setMinutes(data_a, 0);
			data_a = DateUtils.setSeconds(data_a, 0);
			data_a = DateUtils.setMilliseconds(data_a, 0);

			data_da = DateUtils.addMonths(data_a, -1);
		}

		flussiUploadCommand.setDateFrom(Constants.DDMMYYYY.format(data_da));
		flussiUploadCommand.setDateTo(Constants.DDMMYYYY.format(data_a));

	}

	private boolean validateGiornaleDiCassaCsv(UploadEsitoDto uploadEsitoDto) {
		if (uploadEsitoDto == null)
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDe_anno_bolletta()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getCod_bolletta()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDt_contabile()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDe_denominazione()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDe_causale()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getNum_importo()))
			return false;
		if (StringUtils.isBlank(uploadEsitoDto.getDt_valuta()))
			return false;

		if (!NumberUtils.isNumber(uploadEsitoDto.getDe_anno_bolletta()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getCod_bolletta()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDt_contabile()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDe_denominazione()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDe_causale()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getNum_importo()))
			return false;
		if (!NumberUtils.isNumber(uploadEsitoDto.getDt_valuta()))
			return false;

		List<String> list = new ArrayList<String>();

		list.add(uploadEsitoDto.getDe_anno_bolletta());
		list.add(uploadEsitoDto.getCod_bolletta());
		list.add(uploadEsitoDto.getDt_contabile());
		list.add(uploadEsitoDto.getDe_denominazione());
		list.add(uploadEsitoDto.getDe_causale());
		list.add(uploadEsitoDto.getNum_importo());
		list.add(uploadEsitoDto.getDt_valuta());

		if (hasDuplicates(list))
			return false;
		return true;
	}

	private boolean hasDuplicates(List<String> list) {
		Set<String> appeared = new HashSet<String>();
		for (String item : list) {
			if (!appeared.add(item)) {
				return true;
			}
		}
		return false;
	}

}
