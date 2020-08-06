package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.RendicontazioneSubsetDao;
import it.regioneveneto.mygov.payment.mypivot.dao.RendicontazioneSubsetFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Segnalazione;
import it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.RendicontazioneSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Service
public class RendicontazioneSubsetServiceImpl implements RendicontazioneSubsetService {
	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	private RendicontazioneSubsetDao rendicontazioneSubsetDao;
	@Autowired
	private RendicontazioneSubsetFunctionDao rendicontazioneSubsetFunctionDao;
	@Autowired
	private SegnalazioneService segnalazioneService;
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Resource
	private Environment env;

	private static final Logger LOG = Logger.getLogger(RendicontazioneSubsetServiceImpl.class);

	static final Map<String, List<String>> filtersMap = new HashMap<String, List<String>>() {
		{
			put(Constants.COD_ERRORE_IUF_NO_TES,
					Arrays.asList("identificativo_univoco_regolamento", "data_regolamento_check", "data_regolamento_da", "data_regolamento_a",
							"data_ultimo_aggiornamento_check", "data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a",
							"identificativo_flusso_rendicontazione", "tipoDovuto"));

			put(Constants.COD_ERRORE_IUV_NO_RT,
					Arrays.asList("identificativo_univoco_regolamento", "data_regolamento_check", "data_regolamento_da", "data_regolamento_a",
							"data_ultimo_aggiornamento_check", "data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a",
							"identificativo_flusso_rendicontazione"));
		}
	};

	private static final Specification<RendicontazioneSubset> getSegnalazioneFilterSpecification(final String classificazioneCompletezza, final Boolean nascosto) {
		return new Specification<RendicontazioneSubset>() {
			private Predicate getSegnalazioneAttivaByKey(final CriteriaBuilder cb, final Root<Segnalazione> segnalazioneRoot,
					final Root<RendicontazioneSubset> impExpRoot) {
				Predicate classCompletezzaPred = cb.equal(segnalazioneRoot.get("classificazioneCompletezza"),
						classificazioneCompletezza);
				Predicate entePred = cb.equal(segnalazioneRoot.get("ente").get("codIpaEnte"),
						impExpRoot.get("id").get("codiceIpaEnte"));
				
				// PROVENIENDO DAL SUBSET DELLA RENDICONTAZIONE AVRO' massimo una segnalazione per classificazioneCompletezza
				// e iuf
				Predicate iufPred = cb.or( //
						cb.equal(segnalazioneRoot.get("codIuf"), impExpRoot.get("codIufKey")), //
						cb.and(cb.isNull(segnalazioneRoot.get("codIuf")), cb.isNull(impExpRoot.get("codIufKey")))//
				);

				Predicate attivoPred = cb.equal(segnalazioneRoot.get("flgAttivo"), Boolean.TRUE);
				Predicate keyPred = cb.and(attivoPred, iufPred, entePred, classCompletezzaPred);
				return keyPred;
			}

			@Override
			public Predicate toPredicate(Root<RendicontazioneSubset> impExpRoot, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Subquery<Long> segnalazioneAttivaSubquery = query.subquery(Long.class);
				Root<Segnalazione> segnalazioneAttivaRoot = segnalazioneAttivaSubquery.from(Segnalazione.class);
				Predicate keySegnalazioneAttivaPred = getSegnalazioneAttivaByKey(cb, segnalazioneAttivaRoot,
						impExpRoot);

				Predicate fullPredicate;
				if (nascosto == null) { // default... mostro sia i record senza segnalazioni che quelli con segnalazione in stato non nascosto
					Predicate nonNascostoPred = cb.equal(segnalazioneAttivaRoot.get("flgNascosto"), Boolean.FALSE);
					segnalazioneAttivaSubquery.select(cb.literal(1L)).where(keySegnalazioneAttivaPred, nonNascostoPred);
					Predicate existSegnalazioneAttivaNonNascostaSpecificationPredicate = cb
							.exists(segnalazioneAttivaSubquery);
					
					Subquery<Long> segnalazioneAssenteSubquery = query.subquery(Long.class);
					Root<Segnalazione> segnalazioneAssenteRoot = segnalazioneAssenteSubquery.from(Segnalazione.class);
					Predicate keySegnalazioneAssentePred = getSegnalazioneAttivaByKey(cb, segnalazioneAssenteRoot,
							impExpRoot);
					segnalazioneAssenteSubquery.select(cb.literal(1L)).where(keySegnalazioneAssentePred);
					Predicate existSegnalazioneAssentePredicate = cb.not(cb.exists(segnalazioneAssenteSubquery));

					fullPredicate = cb.or(existSegnalazioneAttivaNonNascostaSpecificationPredicate,
							existSegnalazioneAssentePredicate);
				} else if (nascosto) {
					// mostro solo i record con segnalazione in stato nascosto
					Predicate nascostoPred = cb.equal(segnalazioneAttivaRoot.get("flgNascosto"), Boolean.TRUE);

					segnalazioneAttivaSubquery.select(cb.literal(1L)).where(keySegnalazioneAttivaPred, nascostoPred);
					fullPredicate = cb
							.exists(segnalazioneAttivaSubquery);
				} else { //not nascosto
								// solo quelli che hanno una segnalazione
					Predicate nonNascostoPred = cb.equal(segnalazioneAttivaRoot.get("flgNascosto"), Boolean.FALSE);
					segnalazioneAttivaSubquery.select(cb.literal(1L)).where(keySegnalazioneAttivaPred, nonNascostoPred);
					fullPredicate = cb
							.exists(segnalazioneAttivaSubquery);
				}
				return fullPredicate;
			}
		};
	}
	
	@Override
	public PageDto<RendicontazioneSubsetDto> getRendicontazioneSubsetPage(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id, Boolean visualizzaNascostiBool, int page, int size, Sort sort) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, size, sort);

		Page<RendicontazioneSubset> rendicontazioneSubsetPage = getRendicontazioneSubsetPageList(codice_ipa_ente, identificativo_flusso_rendicontazione,
				identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, data_ultimo_aggiornamento_da, data_ultimo_aggiornamento_a,
				errorCode, cod_tipo_dovuto, cod_fed_user_id, visualizzaNascostiBool, pageable);
	
		List<RendicontazioneSubset> rendicontazioneSubsetList = rendicontazioneSubsetPage
				.getContent();
		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(rendicontazioneSubsetList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (RendicontazioneSubset rendicontazioneSubset : rendicontazioneSubsetList) {
				String codIufKey = rendicontazioneSubset.getCodIufKey();
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(rendicontazioneSubset.getId().getCodiceIpaEnte(),
						rendicontazioneSubset.getClassificazioneCompletezza(), codIufKey, null,
						null);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}
		
		List<RendicontazioneSubsetDto> visualizzaSubsetDtos = mapEntitiesListToDtosList(rendicontazioneSubsetPage.getContent(), segnalazioni);

		PageDto<RendicontazioneSubsetDto> visualizzaSubsetDtoPage = new PageDto<RendicontazioneSubsetDto>();
		visualizzaSubsetDtoPage.setList(visualizzaSubsetDtos);

		visualizzaSubsetDtoPage.setNextPage(rendicontazioneSubsetPage.hasNextPage());
		visualizzaSubsetDtoPage.setPage(rendicontazioneSubsetPage.getNumber() + 1);
		visualizzaSubsetDtoPage.setPageSize(rendicontazioneSubsetPage.getSize());
		visualizzaSubsetDtoPage.setPreviousPage(rendicontazioneSubsetPage.hasPreviousPage());
		visualizzaSubsetDtoPage.setTotalPages(rendicontazioneSubsetPage.getTotalPages());
		visualizzaSubsetDtoPage.setTotalRecords(rendicontazioneSubsetPage.getTotalElements());

		return visualizzaSubsetDtoPage;
	}
	
	/**
	 * Il metodo getRendicontazioneSubsetPageFunction richiama una function per recuperare le rendicontazioni
	 */
	@Override
	public PageDto<RendicontazioneSubsetDto> getRendicontazioneSubsetPageFunction(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id, Boolean visualizzaNascostiBool, int page, int size, Sort sort) {

		/** Recupero le rendicontazioni per pagina **/
		List<RendicontazioneSubset> resultList = 
				rendicontazioneSubsetFunctionDao.getRendicontazioneSubsetFunction(codice_ipa_ente, identificativo_flusso_rendicontazione,identificativo_univoco_regolamento, data_regolamento_da,data_regolamento_a,data_ultimo_aggiornamento_da,data_ultimo_aggiornamento_a,errorCode,cod_tipo_dovuto,cod_fed_user_id,visualizzaNascostiBool,page,size,sort);
		/** Recupero il numero di rendicontazioni **/
		int numberResult =  
				rendicontazioneSubsetFunctionDao.getCountRendicontazioneSubsetFunction(codice_ipa_ente, identificativo_flusso_rendicontazione,identificativo_univoco_regolamento, data_regolamento_da,data_regolamento_a,data_ultimo_aggiornamento_da,data_ultimo_aggiornamento_a,errorCode,cod_tipo_dovuto,cod_fed_user_id,visualizzaNascostiBool);
		
		List<RendicontazioneSubset> rendicontazioneSubsetList = resultList;
		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(rendicontazioneSubsetList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (RendicontazioneSubset rendicontazioneSubset : rendicontazioneSubsetList) {
				String codIufKey = rendicontazioneSubset.getCodIufKey();
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(rendicontazioneSubset.getId().getCodiceIpaEnte(),
						rendicontazioneSubset.getClassificazioneCompletezza(), codIufKey, null,
						null);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}
		
		List<RendicontazioneSubsetDto> visualizzaSubsetDtos = mapEntitiesListToDtosList(resultList, segnalazioni);
		
		PageDto<RendicontazioneSubsetDto> visualizzaSubsetDtoPage = new PageDto<RendicontazioneSubsetDto>();
		visualizzaSubsetDtoPage.setList(visualizzaSubsetDtos);

		boolean hasNextPage = numberResult-(size*page)>0 ? true : false;
		visualizzaSubsetDtoPage.setNextPage(hasNextPage);
		visualizzaSubsetDtoPage.setPage(page);
		visualizzaSubsetDtoPage.setPageSize(size);
		boolean hasPreviousPage = page>1 ? true : false;
		visualizzaSubsetDtoPage.setPreviousPage(hasPreviousPage);
		Double totalPages = new Double( (numberResult/size) ) + (numberResult%size == 0 ? 0 : 1);
		visualizzaSubsetDtoPage.setTotalPages(totalPages.intValue());
		visualizzaSubsetDtoPage.setTotalRecords( numberResult );		

		return visualizzaSubsetDtoPage;
	}

	private Page<RendicontazioneSubset> getRendicontazioneSubsetPageList(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id, Boolean visualizzaNascosti, Pageable pageable) {

		Specifications<RendicontazioneSubset> specification = getSpecifications(codice_ipa_ente, identificativo_flusso_rendicontazione,
				identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, data_ultimo_aggiornamento_da, data_ultimo_aggiornamento_a,
				errorCode, cod_tipo_dovuto, cod_fed_user_id);
		
		Specification<RendicontazioneSubset> segnalazioneFilterSpecification = getSegnalazioneFilterSpecification(errorCode, 
				visualizzaNascosti);
		specification = specification == null ? Specifications.where(segnalazioneFilterSpecification)
				: specification.and(segnalazioneFilterSpecification);
		
		Page<RendicontazioneSubset> rendicontazioneSubsetPage = null;
		if (specification != null) {
			rendicontazioneSubsetPage = rendicontazioneSubsetDao.findAll(specification, pageable);
		}
		else {
			rendicontazioneSubsetPage = rendicontazioneSubsetDao.findAll(pageable);
		}
		return rendicontazioneSubsetPage;
	}

	private Specifications<RendicontazioneSubset> getSpecifications(String codice_ipa_ente, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a, Date data_ultimo_aggiornamento_da,
			Date data_ultimo_aggiornamento_a, String errorCode, String cod_tipo_dovuto, String cod_fed_user_id) {

		Specifications<RendicontazioneSubset> specifications = null;

		if (StringUtils.isNotBlank(codice_ipa_ente)) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("codiceIpaEnte", "=", codice_ipa_ente.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione) && isValidWhereCondition(errorCode, "identificativo_flusso_rendicontazione")) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("identificativoFlussoRendicontazione", "=", identificativo_flusso_rendicontazione.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(identificativo_univoco_regolamento) && isValidWhereCondition(errorCode, "identificativo_univoco_regolamento")) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("identificativoUnivocoRegolamento", "=", identificativo_univoco_regolamento.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (data_regolamento_da != null && isValidWhereCondition(errorCode, "data_regolamento_da")) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("dtDataRegolamento", ">=", data_regolamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (data_regolamento_a != null && isValidWhereCondition(errorCode, "data_regolamento_a")) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("dtDataRegolamento", "<=", data_regolamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (data_ultimo_aggiornamento_da != null && isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da")) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("dtDataUltimoAggiornamento", ">=", data_ultimo_aggiornamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (data_ultimo_aggiornamento_a != null && isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a")) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("dtDataUltimoAggiornamento", "<", data_ultimo_aggiornamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(errorCode)) {
			MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
					new SearchCriteria("classificazioneCompletezza", "=", errorCode.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (isValidWhereCondition(errorCode, "tipoDovuto")) {
			if (StringUtils.isEmpty(cod_tipo_dovuto)) {
				List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(cod_fed_user_id, codice_ipa_ente);
				MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
						new SearchCriteria("tipoDovuto", "in", listaCodTipoDovuto, false));
				specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
			}else{
				MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
						new SearchCriteria("tipoDovuto", "=", cod_tipo_dovuto.trim(), false));
				specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
			}

		}
		
		if(cod_tipo_dovuto != null && "".equals(cod_tipo_dovuto)) {
		}


		MyJpaSpecification<RendicontazioneSubset> myJpaSpecification = new MyJpaSpecification<RendicontazioneSubset>(
				new SearchCriteria("identificativoFlussoRendicontazione", "distinct", null, true));
		specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);

		return specifications;
	}

	/**
	 * @param entities
	 * @return
	 */
	private List<RendicontazioneSubsetDto> mapEntitiesListToDtosList(List<RendicontazioneSubset> entities,
			Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni) {
		List<RendicontazioneSubsetDto> dtos = new ArrayList<RendicontazioneSubsetDto>();
		for (RendicontazioneSubset rendicontazioneSubset : entities) {
			RendicontazioneSubsetDto visualizzaTesoreriaSubsetDto = new RendicontazioneSubsetDto();

			String codIufKey = rendicontazioneSubset.getCodIufKey();
			String codIpaEnte = rendicontazioneSubset.getId() != null ? rendicontazioneSubset.getId().getCodiceIpaEnte()
					: null;
			SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
					rendicontazioneSubset.getClassificazioneCompletezza(), codIufKey, null, null);

			if (segnalazioni.containsKey(key)) {
				visualizzaTesoreriaSubsetDto.setSegnalazione(segnalazioni.get(key));
			}

			RendicontazioneSubsetTO rendicontazioneSubsetTO = modelMapperUtil.map(rendicontazioneSubset,
					RendicontazioneSubsetTO.class);

			if (StringUtils.isBlank(rendicontazioneSubset.getImportoTotalePagamenti())) {
				rendicontazioneSubsetTO.setImportoTotalePagamenti("n/a");
			} else if (!rendicontazioneSubset.getImportoTotalePagamenti().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String importoTotalePagamenti = format
						.format(new BigDecimal(rendicontazioneSubsetTO.getImportoTotalePagamenti()));
				rendicontazioneSubsetTO.setImportoTotalePagamenti("€ " + importoTotalePagamenti);
			}

			visualizzaTesoreriaSubsetDto.setIdentificativoFlussoRendicontazione(
					rendicontazioneSubset.getId().getIdentificativoFlussoRendicontazione());
			//			visualizzaTesoreriaSubsetDto.setDataOraFlussoRendicontazione(rendicontazioneSubset.getId().getDataOraFlussoRendicontazione());

			if (StringUtils.isBlank(rendicontazioneSubset.getDeDataUltimoAggiornamento())) {
				rendicontazioneSubsetTO.setDeDataUltimoAggiornamento("n/a");
			}

			visualizzaTesoreriaSubsetDto.setRendicontazioneSubsetTO(rendicontazioneSubsetTO);
			dtos.add(visualizzaTesoreriaSubsetDto);
		}
		return dtos;
	}
	
	/**
	 * @param entities
	 * @return
	 */
	private List<RendicontazioneSubsetDto> mapEntitiesListToDtosList(List<RendicontazioneSubset> entities) {
		List<RendicontazioneSubsetDto> dtos = new ArrayList<RendicontazioneSubsetDto>();
		for (RendicontazioneSubset rendicontazioneSubset : entities) {
			RendicontazioneSubsetDto visualizzaTesoreriaSubsetDto = new RendicontazioneSubsetDto();

			String codIufKey = rendicontazioneSubset.getCodIufKey();
			String codIpaEnte = rendicontazioneSubset.getId() != null ? rendicontazioneSubset.getId().getCodiceIpaEnte()
					: null;
			SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
					rendicontazioneSubset.getClassificazioneCompletezza(), codIufKey, null, null);

//			if (segnalazioni.containsKey(key)) {
//				visualizzaTesoreriaSubsetDto.setSegnalazione(segnalazioni.get(key));
//			}

			RendicontazioneSubsetTO rendicontazioneSubsetTO = modelMapperUtil.map(rendicontazioneSubset,
					RendicontazioneSubsetTO.class);

			if (StringUtils.isBlank(rendicontazioneSubset.getImportoTotalePagamenti())) {
				rendicontazioneSubsetTO.setImportoTotalePagamenti("n/a");
			} else if (!rendicontazioneSubset.getImportoTotalePagamenti().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String importoTotalePagamenti = format
						.format(new BigDecimal(rendicontazioneSubsetTO.getImportoTotalePagamenti()));
				rendicontazioneSubsetTO.setImportoTotalePagamenti("€ " + importoTotalePagamenti);
			}

			visualizzaTesoreriaSubsetDto.setIdentificativoFlussoRendicontazione(
					rendicontazioneSubset.getId().getIdentificativoFlussoRendicontazione());
			//			visualizzaTesoreriaSubsetDto.setDataOraFlussoRendicontazione(rendicontazioneSubset.getId().getDataOraFlussoRendicontazione());

			if (StringUtils.isBlank(rendicontazioneSubset.getDeDataUltimoAggiornamento())) {
				rendicontazioneSubsetTO.setDeDataUltimoAggiornamento("n/a");
			}

			visualizzaTesoreriaSubsetDto.setRendicontazioneSubsetTO(rendicontazioneSubsetTO);
			dtos.add(visualizzaTesoreriaSubsetDto);
		}
		return dtos;
	}

	private boolean isValidWhereCondition(String errorCode, String fieldName) {
		if (errorCode.isEmpty()) {
			return true;
		}
		return filtersMap.get(errorCode).contains(fieldName);
	}

	public void setRendicontazioneSubsetFunctionDao(RendicontazioneSubsetFunctionDao rendicontazioneSubsetFunctionDao) {
		this.rendicontazioneSubsetFunctionDao = rendicontazioneSubsetFunctionDao;
	}

}
