/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import it.regioneveneto.mygov.payment.mypivot.controller.exception.MyPivotControllerException;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.DynamicMessageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.MessagesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.SegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.SegnalazioniFilterDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.StoricoSegnalazioniDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.UtenteService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.SecurityContext;

/**
 * @author Riccardo Cannas
 */
@Controller
@RequestMapping("/protected")
public class StoricoSegnalazioniController {

	private static Log log = LogFactory.getLog(StoricoSegnalazioniController.class);

	
	public static final String CALL_FROM_VISUALIZZA = "visualizza";
	public static final String CALL_FROM_DETTAGLIO = "dettaglio";
	
	
	@Autowired
	private SegnalazioneService segnalazioneService;
	
	@Autowired
	private UtenteService utenteService;
	
	@RequestMapping(value = { "/visualizzaStoricoSegnalazioni.html" }, method = RequestMethod.GET)
	public ModelAndView visualizzaStoricoSegnalazioni(HttpServletRequest request,
			@RequestParam(required = false) String classificazioneCompletezza, //
			@RequestParam(required = false) String codiceIuv, //
			@RequestParam(required = false) Boolean codiceIuvEnabled, //
			@RequestParam(required = false) String codiceIuf, //
			@RequestParam(required = false) Boolean codiceIufEnabled, //
			@RequestParam(required = false) String codiceIud, //
			@RequestParam(required = false) Boolean codiceIudEnabled, //
			@RequestParam(required = false) String codFedUserId, //
			@RequestParam(required = false) String dataDa, //
			@RequestParam(required = false) String dataA, //
			@RequestParam(required = false) String attivi, //
			@RequestParam(required = false) String nascosti, //
			@RequestParam(required = false) Integer pageNum, //
			@RequestParam(required = false) Integer pageSize,//
			@RequestParam(required = false) Integer prevPageSize
	) {
		ModelAndView mav;
		try {
			EnteTO enteTO = SecurityContext.getEnte();
			if (enteTO == null) {
				mav = new ModelAndView(new RedirectView(request.getContextPath() + "/protected/sceltaEnte.html"));
			} else {
				mav = new ModelAndView();

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

				SegnalazioniFilterDto filter = initFilter(classificazioneCompletezza, codiceIuv, codiceIuvEnabled,
						codiceIuf, codiceIufEnabled, codiceIud, codiceIudEnabled, codFedUserId, dataDa, dataA, attivi,
						nascosti, pageNum, prevPageSize, pageSize);

				mav.addObject("filter", filter);

				PageDto<SegnalazioneTO> segnalazioniPaginate = segnalazioneService
						.findPageByFilter(enteTO.getCodIpaEnte(), filter);

				StoricoSegnalazioniDto storicoSegnalazioniDto = createStoricoSegnalazioniDto(segnalazioniPaginate);

				mav.addObject("storicoSegnalazioni", storicoSegnalazioniDto);
				
				
				List<String> listaClassificazioni = getClassificazioniList(enteTO.getFlgPagati(), enteTO.getFlgTesoreria());
				mav.addObject("listaClassificazioni", listaClassificazioni);

				
				List<UtenteTO> utenti = utenteService.findByCodIpaEnte(enteTO.getCodIpaEnte());
				mav.addObject("utenti", utenti);
				mav.setViewName("visualizzaStoricoSegnalazioni");
			}
		} catch (MyPivotServiceException e) {
			String errMsg = "Errore di servizio nel recupero dello storico segnalazioni";
			mav = manageException(e, errMsg);
		} catch (MyPivotControllerException e) {
			String errMsg = "Errore di controllo nel recupero dello storico segnalazioni";
			mav = manageException(e, errMsg);
		} catch (RuntimeException e) {
			String errMsg = "Errore";
			mav = manageException(e, errMsg);
		}
		return mav;
	}

	private List<String> getClassificazioniList(boolean flgPagati, boolean flgTesoreria) {
		List<String> codClassificazioni = new ArrayList<>();
		if (flgPagati && flgTesoreria) {
			codClassificazioni.add(Constants.COD_ERRORE_IUD_RT_IUF_TES);
		}
		if (flgTesoreria) {
			codClassificazioni.add(Constants.COD_ERRORE_RT_IUF_TES);
		}
		codClassificazioni.add(Constants.COD_ERRORE_RT_IUF);
		if (flgPagati) {
			codClassificazioni.add(Constants.COD_ERRORE_IUD_NO_RT);
			codClassificazioni.add(Constants.COD_ERRORE_RT_NO_IUD);
			codClassificazioni.add(Constants.COD_ERRORE_IUD_RT_IUF);
		}
		codClassificazioni.add(Constants.COD_ERRORE_RT_NO_IUF);
		codClassificazioni.add(Constants.COD_ERRORE_IUV_NO_RT);
		if (flgTesoreria) {
			codClassificazioni.add(Constants.COD_ERRORE_IUF_NO_TES);
			codClassificazioni.add(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV);
		}

		return codClassificazioni;
	}
	
	private SegnalazioniFilterDto initFilter(String classificazioneCompletezza, String codiceIuv, Boolean codiceIuvEnabled, String codiceIuf, Boolean codiceIufEnabled,
			String codiceIud, Boolean codiceIudEnabled, String codFedUserId, String dataDa, String dataA, String attivi, String nascosti,
			Integer pageNum, Integer prevPageSize, Integer pageSize) throws MyPivotControllerException {

		log.debug("inizializzando il filtro per il recupero dello storico segnalazioni classificazioneCompletezza["
				+ classificazioneCompletezza + "], codiceIuv[" + codiceIuv + "], codiceIuf[" + codiceIuf
				+ "], codiceIud[" + codiceIud + "], codFedUserId[" + codFedUserId + "], dataDa[" + dataDa + "], dataA["
				+ dataA + "], attivi[" + attivi + "], nascosti[" + nascosti + "], pageNum[" + pageNum + "], pageSize["
				+ pageSize + "]");
		validaParametri(classificazioneCompletezza, codiceIuv, codiceIuf, codiceIud, codFedUserId, dataDa, dataA,
				attivi, nascosti, pageNum, prevPageSize, pageSize);

		int pageNumInt = (pageNum != null && (pageSize == null || pageSize.equals(prevPageSize))) ? pageNum : 1;
		int pageSizeInt = (pageSize != null) ? pageSize : 5;

		Boolean nascostiBool = (StringUtils.isNotBlank(nascosti)) ? Boolean.valueOf(nascosti) : null;
		Boolean attiviBool = (StringUtils.isNotBlank(attivi)) ? Boolean.valueOf(attivi) : null;

		SegnalazioniFilterDto filter = new SegnalazioniFilterDto(pageNumInt, pageSizeInt);
		filter.setClassificazioneCompletezza(classificazioneCompletezza);
		filter.setCodiceIuv(codiceIuv);
		filter.setCodiceIuf(codiceIuf);
		filter.setCodiceIud(codiceIud);
		filter.setCodFedUserId(codFedUserId);
		filter.setDataDa(dataDa);
		filter.setDataA(dataA);
		filter.setAttivi(attiviBool);
		filter.setNascosti(nascostiBool);
		filter.setCodiceIuvEnabled(Boolean.TRUE.equals(codiceIuvEnabled));
		filter.setCodiceIufEnabled(Boolean.TRUE.equals(codiceIufEnabled));
		filter.setCodiceIudEnabled(Boolean.TRUE.equals(codiceIudEnabled));
		return filter;
	}

	private static StoricoSegnalazioniDto createStoricoSegnalazioniDto(
			final PageDto<SegnalazioneTO> segnalazioniPaginate) {
		List<SegnalazioneTO> listaSegnalazioni = segnalazioniPaginate.getList();

		List<SegnalazioneDto> storicoSegnalazioni = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(listaSegnalazioni)) {
			for (SegnalazioneTO segnalazioneTO : listaSegnalazioni) {
				SegnalazioneDto segnalazione = new SegnalazioneDto();
				if (segnalazioneTO.getUtente() != null) {
					segnalazione.setDeFirstname(segnalazioneTO.getUtente().getDeFirstname());
					segnalazione.setDeLastname(segnalazioneTO.getUtente().getDeLastname());
				} else {
					log.warn("la segnalazione id[" + segnalazioneTO.getId() + "] non ha l'utente associato");
				}
				segnalazione.setCodIuv(segnalazioneTO.getCodIuv());
				segnalazione.setDtUltimaModifica(segnalazioneTO.getDtUltimaModifica());
				segnalazione.setFlgAttivo(segnalazioneTO.getFlgAttivo());
				segnalazione.setClassificazioneCompletezza(segnalazioneTO.getClassificazioneCompletezza());
				segnalazione.setFlgNascosto(segnalazioneTO.getFlgNascosto());
				segnalazione.setCodIuf(segnalazioneTO.getCodIuf());
				segnalazione.setCodIud(segnalazioneTO.getCodIud());
				segnalazione.setDtCreazione(segnalazioneTO.getDtCreazione());
				segnalazione.setDeNota(segnalazioneTO.getDeNota());
				storicoSegnalazioni.add(segnalazione);
			}
		}

		int currPage = segnalazioniPaginate.getPage();
		int totPage = segnalazioniPaginate.getTotalPages();
		int maxElmPerPagina = segnalazioniPaginate.getPageSize();
		StoricoSegnalazioniDto storicoSegnalazioniDto = StoricoSegnalazioniDto
				.getInstanceByCurrPageAndTotPageAndsegnalazioni(currPage, totPage, maxElmPerPagina,
						storicoSegnalazioni);
		return storicoSegnalazioniDto;
	}

	private static ModelAndView manageException(Exception e, String errMsg) {
		log.error(errMsg, e);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("message");
		MessagesDto messagesDto = new MessagesDto();
		messagesDto.getErrorMessages().add(new DynamicMessageDto("mypivot.messages.errordetail", errMsg));
		mav.addObject("messagesDto", messagesDto);
		return mav;
	}

	private void validaParametri(String classificazioneCompletezza, String codiceIuv, String codiceIuf,
			String codiceIud, String codFedUserId, String dataDa, String dataA, String attivi, String nascosti,
			Integer pageNum, Integer prevPageSize, Integer pageSize) throws MyPivotControllerException {
		String errMsg = null;
		if (StringUtils.isNotBlank(dataA)) {
			try {
				Constants.DDMMYY.parse(dataA);
			} catch (ParseException e) {
				errMsg = "La Data A deve essere una data nel formato DD/MM/YY";
			}
		} else if (StringUtils.isNotBlank(dataDa)) {
			try {
				Constants.DDMMYY.parse(dataDa);
			} catch (ParseException e) {
				errMsg = "La Data Da deve essere una data nel formato DD/MM/YY";
			}
		} else if (StringUtils.isNotBlank(nascosti) && !(nascosti.equalsIgnoreCase(Boolean.TRUE.toString())
				|| nascosti.equalsIgnoreCase(Boolean.FALSE.toString()))) {
			errMsg = "Il flag nascosti deve essere un valore booleano";
		} else if (StringUtils.isNotBlank(attivi) && !(attivi.equalsIgnoreCase(Boolean.TRUE.toString())
				|| attivi.equalsIgnoreCase(Boolean.FALSE.toString()))) {
			errMsg = "Il flag attivi deve essere un valore booleano";
		}

		if (errMsg != null) {
			throw new MyPivotControllerException(errMsg);
		}
	}
}
