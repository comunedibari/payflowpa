package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.EnteDao;
import it.regioneveneto.mygov.payment.mypivot.dao.SegnalazioneDao;
import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AggiornaSegnalazioneResultDto.ESITO;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni.SegnalazioniFilterDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Segnalazione;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Service
public class SegnalazioneServiceImpl implements SegnalazioneService {

	@Autowired
	ModelMapperUtil modelMapperUtil;
	@Autowired
	private SegnalazioneDao segnalazioneDao;
	@Autowired
	private UtenteDao utenteDao;
	@Autowired
	private EnteDao enteDao;

	private static final Logger log = Logger.getLogger(SegnalazioneServiceImpl.class);

	@Override
	public SegnalazioneTO findAttiveByKey(final SegnalazioneKeyDto segnalazioneKey) throws MyPivotServiceException {
		List<SegnalazioneKeyDto> segnKeyList = new ArrayList<>();
		segnKeyList.add(segnalazioneKey);
		Specification<Segnalazione> specifications = getSegnalazioniSpecification(true, segnKeyList);
		Segnalazione segnalazione = segnalazioneDao.findOne(specifications);
		return modelMapperUtil.map(segnalazione, SegnalazioneTO.class);
	}

	@Override
	public List<SegnalazioneTO> findByKey(final SegnalazioneKeyDto segnalazioneKey) throws MyPivotServiceException {
		List<SegnalazioneKeyDto> segnKeyList = new ArrayList<>();
		segnKeyList.add(segnalazioneKey);
		Specification<Segnalazione> specifications = getSegnalazioniSpecification(null, segnKeyList);
		List<Segnalazione> segnalazioni = segnalazioneDao.findAll(specifications);
		List<SegnalazioneTO> segnalazioniTO = convertToTO(segnalazioni);
		return segnalazioniTO;
	}

	private static final Specification<Segnalazione> getSegnalazioniSpecification(
			final Boolean flgAttivo, final List<SegnalazioneKeyDto> segnalazioniKeyList) {
		return new Specification<Segnalazione>() {
			@Override
			public Predicate toPredicate(Root<Segnalazione> segnalazioneRoot, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate fullPredicate;
				if (CollectionUtils.isNotEmpty(segnalazioniKeyList)) {

					List<Predicate> keyPredList = new ArrayList<>();
					for (SegnalazioneKeyDto segnalazioneKeyDto : segnalazioniKeyList) {
						Predicate classCompletezzaPred = cb.equal(segnalazioneRoot.get("classificazioneCompletezza"),
								segnalazioneKeyDto.getClassificazioneCompletezza());
						Predicate entePred = cb.equal(segnalazioneRoot.get("ente").get("codIpaEnte"),
								segnalazioneKeyDto.getCodIpaEnte());
						Predicate iuvPred;
						if (StringUtils.isEmpty(segnalazioneKeyDto.getCodIuv())) {
							iuvPred = cb.isNull(segnalazioneRoot.get("codIuv"));
						} else {
							iuvPred = cb.equal(segnalazioneRoot.get("codIuv"), segnalazioneKeyDto.getCodIuv());
						}

						Predicate iufPred;
						if (StringUtils.isEmpty(segnalazioneKeyDto.getCodIuf())) {
							iufPred = cb.isNull(segnalazioneRoot.get("codIuf"));
						} else {
							iufPred = cb.equal(segnalazioneRoot.get("codIuf"), segnalazioneKeyDto.getCodIuf());
						}

						Predicate iudPred;
						if (StringUtils.isEmpty(segnalazioneKeyDto.getCodIud())) {
							iudPred = cb.isNull(segnalazioneRoot.get("codIud"));
						} else {
							iudPred = cb.equal(segnalazioneRoot.get("codIud"), segnalazioneKeyDto.getCodIud());
						}
						Predicate keyPred;
						if(flgAttivo != null){
							Predicate attivoPred = cb.equal(segnalazioneRoot.get("flgAttivo"), flgAttivo);
							keyPred = cb.and(iudPred, iufPred, iuvPred, entePred, classCompletezzaPred,
									attivoPred);
						}else{
							keyPred = cb.and(iudPred, iufPred, iuvPred, entePred, classCompletezzaPred);
						}
						keyPredList.add(keyPred);
					}
					Predicate[] a = {};
					fullPredicate = cb.or(keyPredList.toArray(a));
				} else {
					String errMsg = "Parametro non valorizzato correttamente. Lista chiavi segnalazioni vuota";
					log.error(errMsg);
					throw new RuntimeException(errMsg);
				}
				return fullPredicate;
			}
		};
	}

	@Override
	public Map<SegnalazioneKeyDto, SegnalazioneTO> findAttiveByKeys(final List<SegnalazioneKeyDto> segnalazioniKeyList)
			throws MyPivotServiceException {
		Map<SegnalazioneKeyDto, SegnalazioneTO> res = new HashMap<>();
		if (CollectionUtils.isNotEmpty(segnalazioniKeyList)) {
			Specification<Segnalazione> specifications = getSegnalazioniSpecification(true, segnalazioniKeyList);
			List<Segnalazione> segnalazioni = segnalazioneDao.findAll(specifications);
			if(CollectionUtils.isNotEmpty(segnalazioni)){
				List<SegnalazioneTO> segnalazioniTO = convertToTO(segnalazioni);
				for (SegnalazioneTO segnalazioneTO : segnalazioniTO) {
					String codIuvKey = Utilities.translateDescToCodNotAvaliable(segnalazioneTO.getCodIuv());
					String codIufKey = Utilities.translateDescToCodNotAvaliable(segnalazioneTO.getCodIuf());
					String codIudKey = Utilities.translateDescToCodNotAvaliable(segnalazioneTO.getCodIud());

					String codIpaEnte = segnalazioneTO.getEnte() != null ? segnalazioneTO.getEnte().getCodIpaEnte()
							: null;
					SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
							segnalazioneTO.getClassificazioneCompletezza(), codIufKey, codIudKey, codIuvKey);
					res.put(key, segnalazioneTO);
				}
			}
		}
		return res;
	}

	@Override
	public PageDto<SegnalazioneTO> findPageByFilter(String codIpaEnte, SegnalazioniFilterDto filter) throws MyPivotServiceException {
		log.debug("recupero elenco completo segnalazioni");

		if (filter == null || StringUtils.isBlank(codIpaEnte) || filter.getPageSize() <= 0
				|| filter.getPageNum() < 0) {
			String errMsg = "Impossibile recuperare la lista delle segnalazioni, parametri non validi"
					+ ReflectionToStringBuilder.toString(filter);
			log.error(errMsg);
			throw new MyPivotServiceException(errMsg);
		} else {

			// aggiungo anche l'id, quando si pagina è sempre necessario mettere un'ordinamento univoco per evitare che ricaricando
			// la stessa pagina si vedano dati diversi
			Sort sort = new Sort(new Order(Direction.DESC, "dtCreazione"), new Order(Direction.DESC, "id"));

			int pageToGet = 0;
			if (filter.getPageNum() > 0) {
				pageToGet = filter.getPageNum() - 1;
			}
			Pageable pageable = new PageRequest(pageToGet, filter.getPageSize(), sort);

			// devo filtrare sempre almeno per ente
			Specifications<Segnalazione> specifications = Specifications.where(new MyJpaSpecification<Segnalazione>(
					new SearchCriteria("codIpaEnte", "=", codIpaEnte.trim(), "ente")));

			if (StringUtils.isNotBlank(filter.getClassificazioneCompletezza())) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(new SearchCriteria("classificazioneCompletezza",
						"=", filter.getClassificazioneCompletezza().trim(), false)));
			}
			
			if (filter.isCodiceIuvEnabled()) {
				String cod = StringUtils.isNotBlank(filter.getCodiceIuv()) ? filter.getCodiceIuv().trim() : null;
				specifications = specifications.and(
						new MyJpaSpecification<Segnalazione>(new SearchCriteria("codIuv", "eqOrNull", cod, false)));
			}
			if (filter.isCodiceIufEnabled()) {
				String cod = StringUtils.isNotBlank(filter.getCodiceIuf()) ? filter.getCodiceIuf().trim() : null;
				specifications = specifications.and(
						new MyJpaSpecification<Segnalazione>(new SearchCriteria("codIuf", "eqOrNull", cod, false)));
			}
			if (filter.isCodiceIudEnabled()) {
				String cod = StringUtils.isNotBlank(filter.getCodiceIud()) ? filter.getCodiceIud().trim() : null;
				specifications = specifications.and(
						new MyJpaSpecification<Segnalazione>(new SearchCriteria("codIud", "eqOrNull", cod, false)));
			}
			if (StringUtils.isNotBlank(filter.getCodFedUserId())) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
						new SearchCriteria("codFedUserId", "=", filter.getCodFedUserId(), "utente")));
			}
			if (filter.getAttivi() != null) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
						new SearchCriteria("flgAttivo", "=", filter.getAttivi(), false)));
			}

			if (filter.getNascosti() != null) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
						new SearchCriteria("flgNascosto", "=", filter.getNascosti(), false)));
			}

			if (StringUtils.isNotBlank(filter.getDataDa())) {
				try {
					Date data = Constants.DDMMYY.parse(filter.getDataDa());
					specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
							new SearchCriteria("dtCreazione", ">=", data, false)));
				} catch (ParseException e) {
					throw new MyPivotServiceException("Data di filtro non formattata correttamente ["
							+ filter.getDataDa() + "]. Il formato previsto è [" + Constants.DDMMYY_PATTERN + "]", e);
				}
			}
			if (StringUtils.isNotBlank(filter.getDataA())) {
				try {
					Date data = Constants.DDMMYY.parse(filter.getDataA());
					Calendar c = Calendar.getInstance();
					c.setTime(data);
					c.add(Calendar.DATE, 1);  // number of days to add
					data = c.getTime();  // dt is now the new date
					specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
							new SearchCriteria("dtCreazione", "<", data, false)));
				} catch (ParseException e) {
					throw new MyPivotServiceException("Data di filtro non formattata correttamente ["
							+ filter.getDataDa() + "]. Il formato previsto è [" + Constants.DDMMYY_PATTERN + "]", e);
				}
			}

			//TODO aggiungere tutti gli altri filtri
			Page<Segnalazione> paginaSegnalazioni = segnalazioneDao.findAll(specifications, pageable);

			List<Segnalazione> segnalazioni = paginaSegnalazioni.getContent();

			List<SegnalazioneTO> segnalazioniTO = convertToTO(segnalazioni);

			int currPage = paginaSegnalazioni.getNumber() + 1;
			int totPage = paginaSegnalazioni.getTotalPages();
			long totElm = paginaSegnalazioni.getTotalElements();
			int maxElmPerPagina = paginaSegnalazioni.getSize();
			
			PageDto<SegnalazioneTO> segnalazioniPaginate = Utilities.<SegnalazioneTO>getPageDtoInstance(segnalazioniTO, currPage, maxElmPerPagina, totPage, totElm);
			return segnalazioniPaginate;
		}
	}

	@Override
	public PageDto<SegnalazioneTO> findPageByFilter(String codIpaEnte, SegnalazioniFilterDto filter, Pageable pageable) throws MyPivotServiceException {
		log.debug("recupero elenco completo segnalazioni");

		if (filter == null || StringUtils.isBlank(codIpaEnte) || filter.getPageSize() <= 0
				|| filter.getPageNum() < 0) {
			String errMsg = "Impossibile recuperare la lista delle segnalazioni, parametri non validi"
					+ ReflectionToStringBuilder.toString(filter);
			log.error(errMsg);
			throw new MyPivotServiceException(errMsg);
		} else {

			// aggiungo anche l'id, quando si pagina è sempre necessario mettere un'ordinamento univoco per evitare che ricaricando
			// la stessa pagina si vedano dati diversi
			//Sort sort = new Sort(new Order(Direction.DESC, "dtCreazione"), new Order(Direction.DESC, "id"));

			int pageToGet = 0;
			if (filter.getPageNum() > 0) {
				pageToGet = filter.getPageNum() - 1;
			}
			//Pageable pageable = new PageRequest(pageToGet, filter.getPageSize(), sort);

			// devo filtrare sempre almeno per ente
			Specifications<Segnalazione> specifications = Specifications.where(new MyJpaSpecification<Segnalazione>(
					new SearchCriteria("codIpaEnte", "=", codIpaEnte.trim(), "ente")));

			if (StringUtils.isNotBlank(filter.getClassificazioneCompletezza())) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(new SearchCriteria("classificazioneCompletezza",
						"=", filter.getClassificazioneCompletezza().trim(), false)));
			}
			
			if (filter.isCodiceIuvEnabled()) {
				String cod = StringUtils.isNotBlank(filter.getCodiceIuv()) ? filter.getCodiceIuv().trim() : null;
				specifications = specifications.and(
						new MyJpaSpecification<Segnalazione>(new SearchCriteria("codIuv", "eqOrNull", cod, false)));
			}
			if (filter.isCodiceIufEnabled()) {
				String cod = StringUtils.isNotBlank(filter.getCodiceIuf()) ? filter.getCodiceIuf().trim() : null;
				specifications = specifications.and(
						new MyJpaSpecification<Segnalazione>(new SearchCriteria("codIuf", "eqOrNull", cod, false)));
			}
			if (filter.isCodiceIudEnabled()) {
				String cod = StringUtils.isNotBlank(filter.getCodiceIud()) ? filter.getCodiceIud().trim() : null;
				specifications = specifications.and(
						new MyJpaSpecification<Segnalazione>(new SearchCriteria("codIud", "eqOrNull", cod, false)));
			}
			if (StringUtils.isNotBlank(filter.getCodFedUserId())) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
						new SearchCriteria("codFedUserId", "=", filter.getCodFedUserId(), "utente")));
			}
			if (filter.getAttivi() != null) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
						new SearchCriteria("flgAttivo", "=", filter.getAttivi(), false)));
			}

			if (filter.getNascosti() != null) {
				specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
						new SearchCriteria("flgNascosto", "=", filter.getNascosti(), false)));
			}

			if (StringUtils.isNotBlank(filter.getDataDa())) {
				try {
					Date data = Constants.DDMMYY.parse(filter.getDataDa());
					specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
							new SearchCriteria("dtCreazione", ">=", data, false)));
				} catch (ParseException e) {
					throw new MyPivotServiceException("Data di filtro non formattata correttamente ["
							+ filter.getDataDa() + "]. Il formato previsto è [" + Constants.DDMMYY_PATTERN + "]", e);
				}
			}
			if (StringUtils.isNotBlank(filter.getDataA())) {
				try {
					Date data = Constants.DDMMYY.parse(filter.getDataA());
					Calendar c = Calendar.getInstance();
					c.setTime(data);
					c.add(Calendar.DATE, 1);  // number of days to add
					data = c.getTime();  // dt is now the new date
					specifications = specifications.and(new MyJpaSpecification<Segnalazione>(
							new SearchCriteria("dtCreazione", "<", data, false)));
				} catch (ParseException e) {
					throw new MyPivotServiceException("Data di filtro non formattata correttamente ["
							+ filter.getDataDa() + "]. Il formato previsto è [" + Constants.DDMMYY_PATTERN + "]", e);
				}
			}

			//TODO aggiungere tutti gli altri filtri
			Page<Segnalazione> paginaSegnalazioni = segnalazioneDao.findAll(specifications, pageable);

			List<Segnalazione> segnalazioni = paginaSegnalazioni.getContent();

			List<SegnalazioneTO> segnalazioniTO = convertToTO(segnalazioni);

			int currPage = paginaSegnalazioni.getNumber() + 1;
			int totPage = paginaSegnalazioni.getTotalPages();
			long totElm = paginaSegnalazioni.getTotalElements();
			int maxElmPerPagina = paginaSegnalazioni.getSize();
			
			PageDto<SegnalazioneTO> segnalazioniPaginate = Utilities.<SegnalazioneTO>getPageDtoInstance(segnalazioniTO, currPage, maxElmPerPagina, totPage, totElm);
			return segnalazioniPaginate;
		}
	}

	private List<SegnalazioneTO> convertToTO(List<Segnalazione> segnalazioni) {
		List<SegnalazioneTO> segnalazioniTO = null;
		if (CollectionUtils.isNotEmpty(segnalazioni)) {
			segnalazioniTO = new ArrayList<>();
			for (Segnalazione segnalazione : segnalazioni) {
				SegnalazioneTO segnalazioneTO = modelMapperUtil.map(segnalazione, SegnalazioneTO.class);
				segnalazioniTO.add(segnalazioneTO);
			}
			log.debug("recuperate [" + segnalazioni.size() + "] segnalazioni");
		}
		return segnalazioniTO;
	}

	@Override
	public AggiornaSegnalazioneResultDto aggiornaSegnalazione(AggiornaSegnalazioneDto segnalazioneDto)
			throws MyPivotServiceException {
		log.debug("Aggiunta della segnalazione ");
		if (segnalazioneDto.getUtente() == null) {
			throw new MyPivotServiceException(
					"Lo user è obbligatorio per effettuare l'aggiornamento della segnalazione");
		}

//		Utente utente = utenteDao.findOne(segnalazioneDto.getUtente().getId());
		Utente utente = utenteDao.findById(segnalazioneDto.getUtente().getId());

		AggiornaSegnalazioneResultDto result;

		Date dataModifica = new Date();
		if (segnalazioneDto.getIdSegnalazione() != null) {
//			Segnalazione segnalazioneOld = segnalazioneDao.findOne(segnalazioneDto.getIdSegnalazione());
			Segnalazione segnalazioneOld = segnalazioneDao.findById(segnalazioneDto.getIdSegnalazione());
			if (segnalazioneOld == null) {
				String errMsg = "L'id segnalazione passato non e' valido [" + segnalazioneDto.getIdSegnalazione() + "]";
				result = new AggiornaSegnalazioneResultDto(ESITO.ERROR, errMsg);
				//				throw new MyPivotServiceException(
				//						"L'id segnalazione passato non e' valido [" + segnalazioneDto.getIdSegnalazione() + "]");
			} else if (segnalazioneOld.isFlgNascosto() == segnalazioneDto.isFlgNascosto()
					&& StringUtils.equals(segnalazioneOld.getDeNota(), segnalazioneDto.getDeNota())) {
				// non serve fare nulla, non sono cambiati i dati
				SegnalazioneTO segnalazioneAggiornataTO = modelMapperUtil.map(segnalazioneOld, SegnalazioneTO.class);
				result = new AggiornaSegnalazioneResultDto(ESITO.NO_NEED_TO_UPDATE, segnalazioneAggiornataTO);
			} else {
				segnalazioneOld.setFlgAttivo(false);
				segnalazioneOld.setDtUltimaModifica(dataModifica);
				segnalazioneDao.saveAndFlush(segnalazioneOld);

				Segnalazione segnalazioneNew = new Segnalazione(//
						segnalazioneOld.getEnte(), // 
						utente, //
						segnalazioneOld.getClassificazioneCompletezza(), //
						segnalazioneOld.getCodIud(), segnalazioneOld.getCodIuv(), segnalazioneOld.getCodIuf(), //
						segnalazioneDto.getDeNota(), //
						segnalazioneDto.isFlgNascosto(), //
						Boolean.TRUE, dataModifica, //
						dataModifica //
				);

				Segnalazione segnalazioneAggiornata = segnalazioneDao.saveAndFlush(segnalazioneNew);
				SegnalazioneTO segnalazioneAggiornataTO = modelMapperUtil.map(segnalazioneAggiornata,
						SegnalazioneTO.class);
				result = new AggiornaSegnalazioneResultDto(ESITO.AGGIORNATA, segnalazioneAggiornataTO);
			}
		} else if (segnalazioneDto.getEnte() == null) {
			String errMsg = "L'ente è obbligatorio per effettuare l'inserimento di una nuova segnalazione";
			result = new AggiornaSegnalazioneResultDto(ESITO.ERROR, errMsg);
		} else {
//			Ente ente = enteDao.getOne(segnalazioneDto.getEnte().getId());
			Ente ente = enteDao.findById(segnalazioneDto.getEnte().getId());
			String codIud = StringUtils.isNotBlank(segnalazioneDto.getCodIud()) ? segnalazioneDto.getCodIud() : null;
			String codIuv = StringUtils.isNotBlank(segnalazioneDto.getCodIuv()) ? segnalazioneDto.getCodIuv() : null;
			String codIuf = StringUtils.isNotBlank(segnalazioneDto.getCodIuf()) ? segnalazioneDto.getCodIuf() : null;

			// controllo che per qualche errore (refresh dopo inserimento ad esempio) 
			// non esista già una segnalazione con le stesse chiavi logiche
			SegnalazioneKeyDto segnalazioneKey = new SegnalazioneKeyDto(segnalazioneDto.getEnte().getCodIpaEnte(),
					segnalazioneDto.getClassificazioneCompletezza(), codIuf, codIud, codIuv);

			if (CollectionUtils.isNotEmpty(findByKey(segnalazioneKey))) {
				String errMsg = "Non si possono inserire nuove segnalazioni con le stesse chiavi di segnalazioni precedentemente inserite ed in stato attivo";
				result = new AggiornaSegnalazioneResultDto(ESITO.ERROR, errMsg);

			} else {
				Segnalazione segnalazioneNew = new Segnalazione(//
						ente, // 
						utente, //
						segnalazioneDto.getClassificazioneCompletezza(), //
						codIud, codIuv, codIuf, //
						segnalazioneDto.getDeNota(), //
						segnalazioneDto.isFlgNascosto(), //
						Boolean.TRUE, dataModifica, //
						dataModifica //
				);
				Segnalazione segnalazioneAggiornata = segnalazioneDao.saveAndFlush(segnalazioneNew);
				SegnalazioneTO segnalazioneAggiornataTO = modelMapperUtil.map(segnalazioneAggiornata,
						SegnalazioneTO.class);
				result = new AggiornaSegnalazioneResultDto(ESITO.INSERITA, segnalazioneAggiornataTO);
			}
		}
		return result;
	}
}
