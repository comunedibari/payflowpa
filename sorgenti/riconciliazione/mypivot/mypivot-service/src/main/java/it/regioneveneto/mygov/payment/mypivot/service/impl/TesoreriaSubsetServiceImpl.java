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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.TesoreriaFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.dao.TesoreriaSubsetDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaNoMatchSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.TesoreriaSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Segnalazione;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaNoMatchSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TesoreriaSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetTO;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.TesoreriaSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Service
public class TesoreriaSubsetServiceImpl implements TesoreriaSubsetService {

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	private TesoreriaSubsetDao tesoreriaSubsetDao;

	@Autowired
	private TesoreriaFunctionDao tesoreriaFunctionDao;
	@Autowired
	private SegnalazioneService segnalazioneService;

	@Resource
	private Environment env;

	static final Map<String, List<String>> filtersMap = new HashMap<String, List<String>>() {
		{
			put(Constants.COD_ERRORE_TES_NO_IUF_OR_IUV,
					Arrays.asList("codOr1", "importo", "data_contabile_check", "data_contabile_da", "data_contabile_a",
							"data_valuta_check", "data_valuta_da", "data_valuta_a", "data_ultimo_aggiornamento_check",
							"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a", "codConto", "codIuv", "codIuf"));
			put(Constants.COD_ERRORE_TES_NO_MATCH,
					Arrays.asList("codOr1", "importo", "data_contabile_check", "data_contabile_da", "data_contabile_a",
							"data_valuta_check", "data_valuta_da", "data_valuta_a", "data_ultimo_aggiornamento_check",
							"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a", "codConto", "deCausaleT"));
		}
	};

	private static final Specification<TesoreriaSubset> getSegnalazioneFilterSpecification(final Boolean nascosto) {
		return new Specification<TesoreriaSubset>() {
			private Predicate getSegnalazioneAttivaByKey(final CriteriaBuilder cb,
					final Root<Segnalazione> segnalazioneRoot, final Root<TesoreriaSubset> tesoreriaRoot) {
				Predicate classCompletezzaPred = cb.equal(segnalazioneRoot.get("classificazioneCompletezza"),
						tesoreriaRoot.get("classificazioneCompletezza"));
				Predicate entePred = cb.equal(segnalazioneRoot.get("ente").get("codIpaEnte"),
						tesoreriaRoot.get("id").get("codiceIpaEnte"));
				// i codici IUV, IUF e IUD potrebbero essere diversi in quanto
				// quelli nella tabella materializzata

				Predicate iuvPred = cb.or( //
						cb.equal(segnalazioneRoot.get("codIuv"), tesoreriaRoot.get("codIuvKey")), //
						cb.and(cb.isNull(segnalazioneRoot.get("codIuv")), cb.isNull(tesoreriaRoot.get("codIuvKey")))//
				);
				Predicate iufPred = cb.or( //
						cb.equal(segnalazioneRoot.get("codIuf"), tesoreriaRoot.get("codIufKey")), //
						cb.and(cb.isNull(segnalazioneRoot.get("codIuf")), cb.isNull(tesoreriaRoot.get("codIufKey")))//
				);

				Predicate attivoPred = cb.equal(segnalazioneRoot.get("flgAttivo"), Boolean.TRUE);

				Predicate keyPred = cb.and(attivoPred, iufPred, iuvPred, entePred, classCompletezzaPred);
				return keyPred;
			}

			@Override
			public Predicate toPredicate(Root<TesoreriaSubset> tesoreriaRoot, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Subquery<Long> segnalazioneAttivaSubquery = query.subquery(Long.class);
				Root<Segnalazione> segnalazioneAttivaRoot = segnalazioneAttivaSubquery.from(Segnalazione.class);
				Predicate keySegnalazioneAttivaPred = getSegnalazioneAttivaByKey(cb, segnalazioneAttivaRoot,
						tesoreriaRoot);

				Predicate fullPredicate;
				if (nascosto == null) { // default... mostro sia i record senza
										// segnalazioni che quelli con
										// segnalazione in stato non nascosto
					Predicate nonNascostoPred = cb.equal(segnalazioneAttivaRoot.get("flgNascosto"), Boolean.FALSE);
					segnalazioneAttivaSubquery.select(cb.literal(1L)).where(keySegnalazioneAttivaPred, nonNascostoPred);
					Predicate existSegnalazioneAttivaNonNascostaSpecificationPredicate = cb
							.exists(segnalazioneAttivaSubquery);

					Subquery<Long> segnalazioneAssenteSubquery = query.subquery(Long.class);
					Root<Segnalazione> segnalazioneAssenteRoot = segnalazioneAssenteSubquery.from(Segnalazione.class);
					Predicate keySegnalazioneAssentePred = getSegnalazioneAttivaByKey(cb, segnalazioneAssenteRoot,
							tesoreriaRoot);
					segnalazioneAssenteSubquery.select(cb.literal(1L)).where(keySegnalazioneAssentePred);
					Predicate existSegnalazioneAssentePredicate = cb.not(cb.exists(segnalazioneAssenteSubquery));

					fullPredicate = cb.or(existSegnalazioneAttivaNonNascostaSpecificationPredicate,
							existSegnalazioneAssentePredicate);
				} else if (nascosto) {
					// mostro solo i record con segnalazione in stato nascosto
					Predicate nascostoPred = cb.equal(segnalazioneAttivaRoot.get("flgNascosto"), Boolean.TRUE);

					segnalazioneAttivaSubquery.select(cb.literal(1L)).where(keySegnalazioneAttivaPred, nascostoPred);
					fullPredicate = cb.exists(segnalazioneAttivaSubquery);
				} else { // not nascosto
							// solo quelli che hanno una segnalazione
					Predicate nonNascostoPred = cb.equal(segnalazioneAttivaRoot.get("flgNascosto"), Boolean.FALSE);
					segnalazioneAttivaSubquery.select(cb.literal(1L)).where(keySegnalazioneAttivaPred, nonNascostoPred);
					fullPredicate = cb.exists(segnalazioneAttivaSubquery);
				}
				return fullPredicate;
			}
		};
	}

	@Override
	public PageDto<TesoreriaSubsetDto> getTesoreriaSubsetPageFunction(String cod_ipa_ente, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a, Date dt_data_ultimo_aggiornamento_da,
			Date dt_data_ultimo_aggiornamento_a, String importo, String conto, String codOr1, String errorCode,
			Boolean visualizzaNascosti, String codIuv, String codIuf, int page, int size) {

		List<TesoreriaSubset> tesoreriaList = tesoreriaFunctionDao.getTesoreriaPageListFunction(cod_ipa_ente,
				isValidWhereCondition(errorCode, "data_contabile_da") ? dt_data_contabile_da : null,
				isValidWhereCondition(errorCode, "data_contabile_a") ? dt_data_contabile_a : null,
				isValidWhereCondition(errorCode, "data_valuta_da") ? data_valuta_da : null,
				isValidWhereCondition(errorCode, "data_valuta_a") ? data_valuta_a : null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da") ? dt_data_ultimo_aggiornamento_da
						: null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a") ? dt_data_ultimo_aggiornamento_a : null,
				null, isValidWhereCondition(errorCode, "importo") ? importo : null,
				isValidWhereCondition(errorCode, "codConto") ? conto : null,
				isValidWhereCondition(errorCode, "codOr1") ? codOr1 : null, visualizzaNascosti, errorCode,
				isValidWhereCondition(errorCode, "codIuv") ? codIuv : null,
				isValidWhereCondition(errorCode, "codIuf") ? codIuf : null,
				page, size);

		int numberResult = tesoreriaFunctionDao.getCountTesoreriaPageListFunction(cod_ipa_ente,
				isValidWhereCondition(errorCode, "data_contabile_da") ? dt_data_contabile_da : null,
				isValidWhereCondition(errorCode, "data_contabile_a") ? dt_data_contabile_a : null,
				isValidWhereCondition(errorCode, "data_valuta_da") ? data_valuta_da : null,
				isValidWhereCondition(errorCode, "data_valuta_a") ? data_valuta_a : null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da") ? dt_data_ultimo_aggiornamento_da
						: null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a") ? dt_data_ultimo_aggiornamento_a : null,
				null, isValidWhereCondition(errorCode, "importo") ? importo : null,
				isValidWhereCondition(errorCode, "codConto") ? conto : null,
				isValidWhereCondition(errorCode, "codOr1") ? codOr1 : null, visualizzaNascosti, errorCode,
				isValidWhereCondition(errorCode, "codIuv") ? codIuv : null,
				isValidWhereCondition(errorCode, "codIuf") ? codIuf : null);

		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(tesoreriaList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (TesoreriaSubset tesoreriaSubset : tesoreriaList) {
				String codIuvKey = tesoreriaSubset.getCodIuvKey();
				String codIufKey = tesoreriaSubset.getCodIufKey();
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(tesoreriaSubset.getId().getCodiceIpaEnte(),
						tesoreriaSubset.getClassificazioneCompletezza(), codIufKey, null, codIuvKey);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}

		List<TesoreriaSubsetDto> tesoreriaSubsetDtos = mapEntitiesListToDtosList(tesoreriaList, segnalazioni);

		PageDto<TesoreriaSubsetDto> tesoreriaSubsetDtoPage = new PageDto<TesoreriaSubsetDto>();
		tesoreriaSubsetDtoPage.setList(tesoreriaSubsetDtos);

		boolean hasNextPage = numberResult - (size * page) > 0 ? true : false;
		tesoreriaSubsetDtoPage.setNextPage(hasNextPage);
		tesoreriaSubsetDtoPage.setPage(page);
		tesoreriaSubsetDtoPage.setPageSize(size);
		boolean hasPreviousPage = page > 1 ? true : false;
		tesoreriaSubsetDtoPage.setPreviousPage(hasPreviousPage);
		Double totalPages = new Double((numberResult / size)) + (numberResult % size == 0 ? 0 : 1);
		tesoreriaSubsetDtoPage.setTotalPages(totalPages.intValue());
		tesoreriaSubsetDtoPage.setTotalRecords(numberResult);

		return tesoreriaSubsetDtoPage;
	}

	@Override
	public PageDto<TesoreriaNoMatchSubsetDto> getTesoreriaNoMatchSubsetPageFunction(String cod_ipa_ente,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String deCausaleT,
			String importo, String conto, String codOr1, String errorCode, Boolean visualizzaNascosti, int page,
			int size) {

		List<TesoreriaNoMatchSubset> tesoreriaList = tesoreriaFunctionDao.getTesoreriaNoMatchPageListFunction(
				cod_ipa_ente, isValidWhereCondition(errorCode, "data_contabile_da") ? dt_data_contabile_da : null,
				isValidWhereCondition(errorCode, "data_contabile_a") ? dt_data_contabile_a : null,
				isValidWhereCondition(errorCode, "data_valuta_da") ? data_valuta_da : null,
				isValidWhereCondition(errorCode, "data_valuta_a") ? data_valuta_a : null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da") ? dt_data_ultimo_aggiornamento_da
						: null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a") ? dt_data_ultimo_aggiornamento_a : null,
				isValidWhereCondition(errorCode, "deCausaleT") ? deCausaleT : null,
				isValidWhereCondition(errorCode, "importo") ? importo : null,
				isValidWhereCondition(errorCode, "codConto") ? conto : null,
				isValidWhereCondition(errorCode, "codOr1") ? codOr1 : null, visualizzaNascosti, errorCode, null, null, page, size);

		int numberResult = tesoreriaFunctionDao.getCountTesoreriaNoMatchPageListFunction(cod_ipa_ente,
				isValidWhereCondition(errorCode, "data_contabile_da") ? dt_data_contabile_da : null,
				isValidWhereCondition(errorCode, "data_contabile_a") ? dt_data_contabile_a : null,
				isValidWhereCondition(errorCode, "data_valuta_da") ? data_valuta_da : null,
				isValidWhereCondition(errorCode, "data_valuta_a") ? data_valuta_a : null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da") ? dt_data_ultimo_aggiornamento_da
						: null,
				isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a") ? dt_data_ultimo_aggiornamento_a : null,
				isValidWhereCondition(errorCode, "deCausaleT") ? deCausaleT : null,
				isValidWhereCondition(errorCode, "importo") ? importo : null,
				isValidWhereCondition(errorCode, "codConto") ? conto : null,
				isValidWhereCondition(errorCode, "codOr1") ? codOr1 : null, visualizzaNascosti, errorCode);

		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(tesoreriaList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (TesoreriaNoMatchSubset tesoreriaSubset : tesoreriaList) {
				String codIuvKey = tesoreriaSubset.getCodIuvKey();
				String codIufKey = tesoreriaSubset.getCodIufKey();
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(tesoreriaSubset.getId().getCodiceIpaEnte(),
						tesoreriaSubset.getClassificazioneCompletezza(), codIufKey, null, codIuvKey);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}

		List<TesoreriaNoMatchSubsetDto> tesoreriaSubsetDtos = mapNoMatchEntitiesListToDtosList(tesoreriaList,
				segnalazioni);

		PageDto<TesoreriaNoMatchSubsetDto> tesoreriaSubsetDtoPage = new PageDto<TesoreriaNoMatchSubsetDto>();
		tesoreriaSubsetDtoPage.setList(tesoreriaSubsetDtos);

		boolean hasNextPage = numberResult - (size * page) > 0 ? true : false;
		tesoreriaSubsetDtoPage.setNextPage(hasNextPage);
		tesoreriaSubsetDtoPage.setPage(page);
		tesoreriaSubsetDtoPage.setPageSize(size);
		boolean hasPreviousPage = page > 1 ? true : false;
		tesoreriaSubsetDtoPage.setPreviousPage(hasPreviousPage);
		Double totalPages = new Double((numberResult / size)) + (numberResult % size == 0 ? 0 : 1);
		tesoreriaSubsetDtoPage.setTotalPages(totalPages.intValue());
		tesoreriaSubsetDtoPage.setTotalRecords(numberResult);

		return tesoreriaSubsetDtoPage;
	}

	@Override
	public PageDto<TesoreriaSubsetDto> getTesoreriaSubsetPage(String codIpaEnte, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String importo, String conto,
			String codOr1, String classificazione, Boolean visualizzaNascosti, int page, int pageSize, Sort sort) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<TesoreriaSubset> tesoreriaSubsetPage = getTesoreriaSubsetPageList(codIpaEnte, dt_data_valuta_da,
				dt_data_valuta_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_ultimo_aggiornamento_da,
				dt_data_ultimo_aggiornamento_a, importo, conto, codOr1, classificazione, visualizzaNascosti, pageable);

		List<TesoreriaSubset> tesoreriaSubsetList = tesoreriaSubsetPage.getContent();
		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(tesoreriaSubsetList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (TesoreriaSubset tesoreriaSubset : tesoreriaSubsetList) {
				String codIuvKey = tesoreriaSubset.getCodIuvKey();
				String codIufKey = tesoreriaSubset.getCodIufKey();
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(tesoreriaSubset.getId().getCodiceIpaEnte(),
						tesoreriaSubset.getClassificazioneCompletezza(), codIufKey, null, codIuvKey);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}

		List<TesoreriaSubsetDto> tesoreriaSubsetDtos = mapEntitiesListToDtosList(tesoreriaSubsetPage.getContent(),
				segnalazioni);

		PageDto<TesoreriaSubsetDto> tesoreriaSubsetDtoPage = new PageDto<TesoreriaSubsetDto>();
		tesoreriaSubsetDtoPage.setList(tesoreriaSubsetDtos);

		tesoreriaSubsetDtoPage.setNextPage(tesoreriaSubsetPage.hasNextPage());
		tesoreriaSubsetDtoPage.setPage(tesoreriaSubsetPage.getNumber() + 1);
		tesoreriaSubsetDtoPage.setPageSize(tesoreriaSubsetPage.getSize());
		tesoreriaSubsetDtoPage.setPreviousPage(tesoreriaSubsetPage.hasPreviousPage());
		tesoreriaSubsetDtoPage.setTotalPages(tesoreriaSubsetPage.getTotalPages());
		tesoreriaSubsetDtoPage.setTotalRecords(tesoreriaSubsetPage.getTotalElements());

		return tesoreriaSubsetDtoPage;

	}

	private Page<TesoreriaSubset> getTesoreriaSubsetPageList(String codIpaEnte, Date dt_data_valuta_da,
			Date dt_data_valuta_a, Date dt_data_contabile_da, Date dt_data_contabile_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String importo, String conto,
			String codOr1, String classificazione, Boolean visualizzaNascosti, Pageable pageable) {

		Specifications<TesoreriaSubset> specification = getSpecifications(codIpaEnte, dt_data_contabile_da,
				dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a, dt_data_ultimo_aggiornamento_da,
				dt_data_ultimo_aggiornamento_a, importo, conto, codOr1, classificazione);

		Specification<TesoreriaSubset> segnalazioniSpecification = getSegnalazioneFilterSpecification(
				visualizzaNascosti);
		specification = specification == null ? Specifications.where(segnalazioniSpecification)
				: specification.and(segnalazioniSpecification);

		Page<TesoreriaSubset> tesoreriaSubsetPage = null;
		if (specification != null) {
			tesoreriaSubsetPage = tesoreriaSubsetDao.findAll(specification, pageable);
		} else {
			tesoreriaSubsetPage = tesoreriaSubsetDao.findAll(pageable);
		}
		return tesoreriaSubsetPage;
	}

	private Specifications<TesoreriaSubset> getSpecifications(String codIpaEnte, Date dt_data_contabile_da,
			Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String importo, String conto,
			String codOr1, String classificazione) {

		Specifications<TesoreriaSubset> specifications = null;

		if (StringUtils.isNotBlank(codIpaEnte)) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("codiceIpaEnte", "=", codIpaEnte.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_contabile_da != null && isValidWhereCondition(classificazione, "data_contabile_da")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("dtDataContabile", ">=", dt_data_contabile_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_contabile_a != null && isValidWhereCondition(classificazione, "data_contabile_a")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("dtDataContabile", "<=", dt_data_contabile_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_valuta_da != null && isValidWhereCondition(classificazione, "data_valuta_da")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("dtDataValuta", ">=", dt_data_valuta_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_valuta_a != null && isValidWhereCondition(classificazione, "data_valuta_a")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("dtDataValuta", "<=", dt_data_valuta_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (dt_data_ultimo_aggiornamento_da != null
				&& isValidWhereCondition(classificazione, "data_ultimo_aggiornamento_da")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("dtDataUltimoAggiornamento", ">=", dt_data_ultimo_aggiornamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_ultimo_aggiornamento_a != null
				&& isValidWhereCondition(classificazione, "data_ultimo_aggiornamento_a")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("dtDataUltimoAggiornamento", "<", dt_data_ultimo_aggiornamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(importo) && isValidWhereCondition(classificazione, "importo")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("deImporto", "=", importo.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(conto) && isValidWhereCondition(classificazione, "codConto")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("codConto", "=", conto.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codOr1) && isValidWhereCondition(classificazione, "codOr1")) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("codOr1", "like", codOr1.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(classificazione)) {
			MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
					new SearchCriteria("classificazioneCompletezza", "=", classificazione.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		MyJpaSpecification<TesoreriaSubset> myJpaSpecification = new MyJpaSpecification<TesoreriaSubset>(
				new SearchCriteria("codiceIpaEnte", "distinct", null, true));
		specifications = specifications == null ? Specifications.where(myJpaSpecification)
				: specifications.and(myJpaSpecification);

		return specifications;
	}

	private List<TesoreriaSubsetDto> mapEntitiesListToDtosList(List<TesoreriaSubset> entities,
			Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni) {

		List<TesoreriaSubsetDto> dtos = new ArrayList<TesoreriaSubsetDto>();
		for (TesoreriaSubset tesoreriaSubset : entities) {

			TesoreriaSubsetDto tesoreriaSubsetDto = new TesoreriaSubsetDto();

			String codIuvKey = tesoreriaSubset.getCodIuvKey();
			String codIufKey = tesoreriaSubset.getCodIufKey();
			String codIudKey = null;
			String codIpaEnte = tesoreriaSubset.getId() != null ? tesoreriaSubset.getId().getCodiceIpaEnte() : null;
			SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte, tesoreriaSubset.getClassificazioneCompletezza(),
					codIufKey, codIudKey, codIuvKey);

			if (segnalazioni.containsKey(key)) {
				tesoreriaSubsetDto.setSegnalazione(segnalazioni.get(key));
			}

			TesoreriaSubsetTO tesoreriaSubsetTO = modelMapperUtil.map(tesoreriaSubset, TesoreriaSubsetTO.class);

			if (StringUtils.isBlank(tesoreriaSubset.getDeImporto())) {
				tesoreriaSubsetTO.setDeImporto("n/a");
			} else if (!tesoreriaSubset.getDeImporto().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String deImporto = format.format(new BigDecimal(tesoreriaSubsetTO.getDeImporto()));
				tesoreriaSubsetTO.setDeImporto("€ " + deImporto);
			}

			if (StringUtils.isNotBlank(tesoreriaSubset.getId().getIdentificativoFlussoRendicontazione())
					&& !tesoreriaSubset.getId().getIdentificativoFlussoRendicontazione().equals("n/a")) {
				tesoreriaSubsetDto.setIdentificativoRiversamento(
						tesoreriaSubset.getId().getIdentificativoFlussoRendicontazione());
			} else {
				tesoreriaSubsetDto.setIdentificativoRiversamento(tesoreriaSubset.getId().getCodiceIuv());
			}

			tesoreriaSubsetDto.setCodiceIpaEnte(tesoreriaSubset.getId().getCodiceIpaEnte());
			tesoreriaSubsetDto.setCodiceIuv(tesoreriaSubset.getId().getCodiceIuv());
			tesoreriaSubsetDto.setIdentificativoFlussoRendicontazione(
					tesoreriaSubset.getId().getIdentificativoFlussoRendicontazione());

			if (StringUtils.isBlank(tesoreriaSubset.getCodConto())) {
				tesoreriaSubsetTO.setCodConto("n/a");
			}

			if (StringUtils.isBlank(tesoreriaSubset.getDeDataValuta())) {
				tesoreriaSubsetTO.setDeDataValuta("n/a");
			}

			if (StringUtils.isBlank(tesoreriaSubset.getDeDataContabile())) {
				tesoreriaSubsetTO.setDeDataContabile("n/a");
			}

			if (StringUtils.isBlank(tesoreriaSubset.getCodOr1())) {
				tesoreriaSubsetTO.setCodOr1("n/a");
			}

			if (StringUtils.isBlank(tesoreriaSubset.getDeDataUltimoAggiornamento())) {
				tesoreriaSubsetTO.setDeDataUltimoAggiornamento("n/a");
			}

			// String rowClass = null;
			// switch (tesoreriaSubsetTO.getVerificaTotale()) {
			// case "OK":
			// rowClass = "visualizza-lista-row-ok";
			// break;
			// case "KO":
			// rowClass = "visualizza-lista-row-ko";
			// break;
			// case "n/a":
			// rowClass = "visualizza-lista-row-na";
			// break;
			// default:
			// break;
			// }
			// tesoreriaSubsetDto.setRowClass(rowClass);
			tesoreriaSubsetDto.setTesoreriaSubsetTO(tesoreriaSubsetTO);
			dtos.add(tesoreriaSubsetDto);
		}
		return dtos;
	}

	private List<TesoreriaNoMatchSubsetDto> mapNoMatchEntitiesListToDtosList(List<TesoreriaNoMatchSubset> entities,
			Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni) {

		List<TesoreriaNoMatchSubsetDto> dtos = new ArrayList<TesoreriaNoMatchSubsetDto>();
		for (TesoreriaNoMatchSubset tesoreriaNoMatchSubset : entities) {

			TesoreriaNoMatchSubsetDto tesoreriaNoMatchSubsetDto = new TesoreriaNoMatchSubsetDto();

			String codIuvKey = tesoreriaNoMatchSubset.getCodIuvKey();
			String codIufKey = tesoreriaNoMatchSubset.getCodIufKey();
			String codIudKey = null;
			String codIpaEnte = tesoreriaNoMatchSubset.getId() != null
					? tesoreriaNoMatchSubset.getId().getCodiceIpaEnte() : null;
			SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
					tesoreriaNoMatchSubset.getClassificazioneCompletezza(), codIufKey, codIudKey, codIuvKey);

			if (segnalazioni.containsKey(key)) {
				tesoreriaNoMatchSubsetDto.setSegnalazione(segnalazioni.get(key));
			}

			TesoreriaNoMatchSubsetTO tesoreriaNoMatchSubsetTO = modelMapperUtil.map(tesoreriaNoMatchSubset,
					TesoreriaNoMatchSubsetTO.class);

			if (StringUtils.isBlank(tesoreriaNoMatchSubset.getDeImporto())) {
				tesoreriaNoMatchSubsetTO.setDeImporto("n/a");
			} else if (!tesoreriaNoMatchSubset.getDeImporto().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String deImporto = format.format(new BigDecimal(tesoreriaNoMatchSubsetTO.getDeImporto()));
				tesoreriaNoMatchSubsetTO.setDeImporto("€ " + deImporto);
			}

			// if
			// (StringUtils.isNotBlank(tesoreriaNoMatchSubset.getId().getIdentificativoFlussoRendicontazione())
			// &&
			// !tesoreriaNoMatchSubset.getId().getIdentificativoFlussoRendicontazione().equals("n/a"))
			// {
			// tesoreriaNoMatchSubsetDto.setIdentificativoRiversamento(tesoreriaNoMatchSubset.getId().getIdentificativoFlussoRendicontazione());
			// }
			// else {
			// tesoreriaNoMatchSubsetDto.setIdentificativoRiversamento(tesoreriaNoMatchSubset.getId().getCodiceIuv());
			// }

			tesoreriaNoMatchSubsetDto.setCodiceIpaEnte(tesoreriaNoMatchSubset.getId().getCodiceIpaEnte());
			tesoreriaNoMatchSubsetDto.setDeAnnoBolletta(tesoreriaNoMatchSubset.getId().getDeAnnoBolletta());
			tesoreriaNoMatchSubsetDto.setCodBolletta(tesoreriaNoMatchSubset.getId().getCodBolletta());

			if (StringUtils.isBlank(tesoreriaNoMatchSubset.getCodConto())) {
				tesoreriaNoMatchSubsetTO.setCodConto("n/a");
			}

			if (StringUtils.isBlank(tesoreriaNoMatchSubset.getDeDataValuta())) {
				tesoreriaNoMatchSubsetTO.setDeDataValuta("n/a");
			}

			if (StringUtils.isBlank(tesoreriaNoMatchSubset.getDeDataContabile())) {
				tesoreriaNoMatchSubsetTO.setDeDataContabile("n/a");
			}

			if (StringUtils.isBlank(tesoreriaNoMatchSubset.getCodOr1())) {
				tesoreriaNoMatchSubsetTO.setCodOr1("n/a");
			}

			if (StringUtils.isBlank(tesoreriaNoMatchSubset.getDeDataUltimoAggiornamento())) {
				tesoreriaNoMatchSubsetTO.setDeDataUltimoAggiornamento("n/a");
			}

			// String rowClass = null;
			// switch (tesoreriaSubsetTO.getVerificaTotale()) {
			// case "OK":
			// rowClass = "visualizza-lista-row-ok";
			// break;
			// case "KO":
			// rowClass = "visualizza-lista-row-ko";
			// break;
			// case "n/a":
			// rowClass = "visualizza-lista-row-na";
			// break;
			// default:
			// break;
			// }
			// tesoreriaSubsetDto.setRowClass(rowClass);
			tesoreriaNoMatchSubsetDto.setTesoreriaNoMatchSubsetTO(tesoreriaNoMatchSubsetTO);
			dtos.add(tesoreriaNoMatchSubsetDto);
		}
		return dtos;
	}

	private boolean isValidWhereCondition(String errorCode, String fieldName) {
		if (errorCode.isEmpty()) {
			return true;
		}
		return filtersMap.get(errorCode).contains(fieldName);
	}
}
