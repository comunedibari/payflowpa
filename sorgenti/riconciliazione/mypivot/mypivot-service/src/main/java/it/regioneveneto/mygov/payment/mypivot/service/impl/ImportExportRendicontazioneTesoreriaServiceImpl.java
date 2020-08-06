package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.lang.reflect.InvocationTargetException;
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
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.EnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.ImportExportRendicontazioneTesoreriaDao;
import it.regioneveneto.mygov.payment.mypivot.dao.ImportExportRendicontazioneTesoreriaFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVDtoIFace;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVIudRtIufTesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVRtIufDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVRtIufTesDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaCompletaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ImportExportRendicontazioneTesoreria;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Segnalazione;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.ImportExportRendicontazioneTesoreriaService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.MyPivotServiceException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants.VISUALIZZA_NASCOSTI;

@Service
public class ImportExportRendicontazioneTesoreriaServiceImpl implements ImportExportRendicontazioneTesoreriaService {

	@Autowired
	private ImportExportRendicontazioneTesoreriaFunctionDao importExportRendicontazioneTesoreriaFunctionDao;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	private EnteTipoDovutoDao enteTipoDovutoDao;

	@Autowired
	private ImportExportRendicontazioneTesoreriaDao importExportRendicontazioneTesoreriaDao;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Resource
	private Environment env;

	@Autowired
	private SegnalazioneService segnalazioneService;

	private static final Logger LOG = Logger.getLogger(ImportExportRendicontazioneTesoreriaServiceImpl.class);

	static final Map<String, List<String>> filtersMap = new HashMap<String, List<String>>() {
		{
			put(Constants.COD_ERRORE_RT_IUF_TES,
					Arrays.asList("codice_iud", "codice_iuv", "identificativo_univoco_riscossione",
							"denominazione_attestante", "codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
							"codice_identificativo_univoco_versante", "anagrafica_versante", "tipoDovuto",
							"data_esito_check", "data_esito_singolo_pagamento_da", "data_esito_singolo_pagamento_a",
							"identificativo_univoco_regolamento", "data_regolamento_check", "data_regolamento_da",
							"data_regolamento_a", "identificativo_flusso_rendicontazione", "codOr1", "importo",
							"data_contabile_check", "data_contabile_da", "data_contabile_a", "data_valuta_check",
							"data_valuta_da", "data_valuta_a", "data_ultimo_aggiornamento_check",
							"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a", "codConto"));

			put(Constants.COD_ERRORE_IUD_RT_IUF, Arrays.asList("codice_iud", "codice_iuv",
					"identificativo_univoco_riscossione", "denominazione_attestante",
					"codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
					"codice_identificativo_univoco_versante", "anagrafica_versante", "tipoDovuto",
					"data_esecuzione_check", "data_esecuzione_singolo_pagamento_da",
					"data_esecuzione_singolo_pagamento_a", "data_esito_check", "data_esito_singolo_pagamento_da",
					"data_esito_singolo_pagamento_a", "identificativo_univoco_regolamento", "data_regolamento_check",
					"data_regolamento_da", "data_regolamento_a", "data_ultimo_aggiornamento_check",
					"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a",
					"identificativo_flusso_rendicontazione",
					"causale_versamento" /*
											 * "codOr1", "importo"
											 * "data_contabile_check",
											 * "data_contabile_da",
											 * "data_contabile_a",
											 * "data_valuta_check",
											 * "data_valuta_da",
											 * "data_valuta_a", "codConto"
											 */));

			put(Constants.COD_ERRORE_RT_IUF,
					Arrays.asList("codice_iud", "codice_iuv", "identificativo_univoco_riscossione",
							"denominazione_attestante", "codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
							"codice_identificativo_univoco_versante", "anagrafica_versante", "tipoDovuto",
							"data_esito_check", "data_esito_singolo_pagamento_da", "data_esito_singolo_pagamento_a",
							"identificativo_univoco_regolamento", "data_regolamento_check", "data_regolamento_da",
							"data_regolamento_a", "data_ultimo_aggiornamento_check", "data_ultimo_aggiornamento_da",
							"data_ultimo_aggiornamento_a", "identificativo_flusso_rendicontazione"));

			put(Constants.COD_ERRORE_RT_NO_IUD,
					Arrays.asList("codice_iud", "codice_iuv", "identificativo_univoco_riscossione",
							"denominazione_attestante", "codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
							"codice_identificativo_univoco_versante", "anagrafica_versante", "tipoDovuto",
							"data_esito_check", "data_esito_singolo_pagamento_da", "data_esito_singolo_pagamento_a",
							"identificativo_univoco_regolamento", "data_regolamento_check", "data_regolamento_da",
							"data_regolamento_a", "identificativo_flusso_rendicontazione", "codOr1", "importo",
							"data_contabile_check", "data_contabile_da", "data_contabile_a", "data_valuta_check",
							"data_valuta_da", "data_valuta_a", "data_ultimo_aggiornamento_check",
							"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a", "codConto"));

			put(Constants.COD_ERRORE_RT_NO_IUF,
					Arrays.asList("codice_iud", "codice_iuv", "identificativo_univoco_riscossione",
							"denominazione_attestante", "codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
							"codice_identificativo_univoco_versante", "anagrafica_versante", "tipoDovuto",
							"data_esito_check", "data_esito_singolo_pagamento_da", "data_esito_singolo_pagamento_a",
							"data_ultimo_aggiornamento_check", "data_ultimo_aggiornamento_da",
							"data_ultimo_aggiornamento_a"));

			put(Constants.COD_ERRORE_IUD_NO_RT,
					Arrays.asList("codice_iud", "codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
							"tipoDovuto", "data_esecuzione_check", "data_esecuzione_singolo_pagamento_da",
							"data_esecuzione_singolo_pagamento_a", "data_ultimo_aggiornamento_check",
							"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a", "causale_versamento"));

			put(Constants.COD_ERRORE_RT_TES,
					Arrays.asList("codice_iud", "codice_iuv", "identificativo_univoco_riscossione",
							"denominazione_attestante", "codice_identificativo_univoco_pagatore", "anagrafica_pagatore",
							"codice_identificativo_univoco_versante", "anagrafica_versante", "tipoDovuto",
							"data_esito_check", "data_esito_singolo_pagamento_da", "data_esito_singolo_pagamento_a",
							"codOr1", "importo", "data_contabile_check", "data_contabile_da", "data_contabile_a",
							"data_valuta_check", "data_valuta_da", "data_valuta_a", "data_ultimo_aggiornamento_check",
							"data_ultimo_aggiornamento_da", "data_ultimo_aggiornamento_a", "codConto"));
		}
	};

	private static final Specification<ImportExportRendicontazioneTesoreria> getSegnalazioneFilterSpecification(
			final Boolean nascosto) {
		return new Specification<ImportExportRendicontazioneTesoreria>() {
			private Predicate getSegnalazioneAttivaByKey(final CriteriaBuilder cb,
					final Root<Segnalazione> segnalazioneRoot,
					final Root<ImportExportRendicontazioneTesoreria> impExpRoot) {
				Predicate classCompletezzaPred = cb.equal(segnalazioneRoot.get("classificazioneCompletezza"),
						impExpRoot.get("id").get("classificazioneCompletezza"));
				Predicate entePred = cb.equal(segnalazioneRoot.get("ente").get("codIpaEnte"),
						impExpRoot.get("id").get("codiceIpaEnte"));
				// i codici IUV, IUF e IUD potrebbero essere diversi in quanto
				// quelli nella tabella materializzata

				Predicate iuvPred = cb.or( //
						cb.equal(segnalazioneRoot.get("codIuv"), impExpRoot.get("codIuvKey")), //
						cb.and(cb.isNull(segnalazioneRoot.get("codIuv")), cb.isNull(impExpRoot.get("codIuvKey")))//
				);
				Predicate iufPred = cb.or( //
						cb.equal(segnalazioneRoot.get("codIuf"), impExpRoot.get("codIufKey")), //
						cb.and(cb.isNull(segnalazioneRoot.get("codIuf")), cb.isNull(impExpRoot.get("codIufKey")))//
				);
				Predicate iudPred = cb.or(//
						cb.equal(segnalazioneRoot.get("codIud"), impExpRoot.get("codIudKey")), //
						cb.and(cb.isNull(segnalazioneRoot.get("codIud")), cb.isNull(impExpRoot.get("codIudKey")))//
				);

				Predicate attivoPred = cb.equal(segnalazioneRoot.get("flgAttivo"), Boolean.TRUE);

				Predicate keyPred = cb.and(attivoPred, iudPred, iufPred, iuvPred, entePred, classCompletezzaPred);
				return keyPred;
			}

			@Override
			public Predicate toPredicate(Root<ImportExportRendicontazioneTesoreria> impExpRoot, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Subquery<Long> segnalazioneAttivaSubquery = query.subquery(Long.class);
				Root<Segnalazione> segnalazioneAttivaRoot = segnalazioneAttivaSubquery.from(Segnalazione.class);
				Predicate keySegnalazioneAttivaPred = getSegnalazioneAttivaByKey(cb, segnalazioneAttivaRoot,
						impExpRoot);

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
							impExpRoot);
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
	public PageDto<VisualizzaCompletaDto> getImportExportRendicontazioneTesoreriaPageFunction(String cod_fed_user_id,
			String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date data_esecuzione_singolo_pagamento_da,
			Date data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date data_valuta_da, Date data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String cod_tipo_dovuto,
			String importo, String conto, String codOr1, Boolean visualizzaNascosti, String errorCode, int page,
			int size) {

		List<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaList = importExportRendicontazioneTesoreriaFunctionDao
				.getImportExportRendicontazioneTesoreriaPageListFunction(cod_fed_user_id, cod_ipa_ente,
						isValidWhereCondition(errorCode, "codice_iud") ? codice_iud : null,
						isValidWhereCondition(errorCode, "codice_iuv") ? codice_iuv : null,
						isValidWhereCondition(errorCode, "denominazione_attestante") ? denominazione_attestante : null,
						isValidWhereCondition(errorCode, "identificativo_univoco_riscossione")
								? identificativo_univoco_riscossione : null,
						isValidWhereCondition(errorCode, "codice_identificativo_univoco_versante")
								? codice_identificativo_univoco_versante : null,
						isValidWhereCondition(errorCode, "anagrafica_versante") ? anagrafica_versante : null,
						isValidWhereCondition(errorCode, "codice_identificativo_univoco_pagatore")
								? codice_identificativo_univoco_pagatore : null,
						isValidWhereCondition(errorCode, "anagrafica_pagatore") ? anagrafica_pagatore : null,
						isValidWhereCondition(errorCode, "causale_versamento") ? causale_versamento : null,
						isValidWhereCondition(errorCode, "data_esecuzione_singolo_pagamento_da")
								? data_esecuzione_singolo_pagamento_da : null,
						isValidWhereCondition(errorCode, "data_esecuzione_singolo_pagamento_a")
								? data_esecuzione_singolo_pagamento_a : null,
						isValidWhereCondition(errorCode, "data_esito_singolo_pagamento_da")
								? data_esito_singolo_pagamento_da : null,
						isValidWhereCondition(errorCode, "data_esito_singolo_pagamento_a")
								? data_esito_singolo_pagamento_a : null,
						isValidWhereCondition(errorCode, "identificativo_flusso_rendicontazione")
								? identificativo_flusso_rendicontazione : null,
						isValidWhereCondition(errorCode, "identificativo_univoco_regolamento")
								? identificativo_univoco_regolamento : null,
						isValidWhereCondition(errorCode, "data_regolamento_da") ? data_regolamento_da : null,
						isValidWhereCondition(errorCode, "data_regolamento_a") ? data_regolamento_a : null,
						isValidWhereCondition(errorCode, "data_contabile_da") ? dt_data_contabile_da : null,
						isValidWhereCondition(errorCode, "data_contabile_a") ? dt_data_contabile_a : null,
						isValidWhereCondition(errorCode, "data_valuta_da") ? data_valuta_da : null,
						isValidWhereCondition(errorCode, "data_valuta_a") ? data_valuta_a : null,
						isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da")
								? dt_data_ultimo_aggiornamento_da : null,
						isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a") ? dt_data_ultimo_aggiornamento_a
								: null,
						cod_tipo_dovuto, isValidWhereCondition(errorCode, "tipoDovuto"),
						isValidWhereCondition(errorCode, "importo") ? importo : null,
						isValidWhereCondition(errorCode, "codConto") ? conto : null,
						isValidWhereCondition(errorCode, "codOr1") ? codOr1 : null, visualizzaNascosti, errorCode, page,
						size);

		int numberResult = importExportRendicontazioneTesoreriaFunctionDao
				.getCountImportExportRendicontazioneTesoreriaPageListFunction(cod_fed_user_id, cod_ipa_ente,
						isValidWhereCondition(errorCode, "codice_iud") ? codice_iud : null,
						isValidWhereCondition(errorCode, "codice_iuv") ? codice_iuv : null,
						isValidWhereCondition(errorCode, "denominazione_attestante") ? denominazione_attestante : null,
						isValidWhereCondition(errorCode, "identificativo_univoco_riscossione")
								? identificativo_univoco_riscossione : null,
						isValidWhereCondition(errorCode, "codice_identificativo_univoco_versante")
								? codice_identificativo_univoco_versante : null,
						isValidWhereCondition(errorCode, "anagrafica_versante") ? anagrafica_versante : null,
						isValidWhereCondition(errorCode, "codice_identificativo_univoco_pagatore")
								? codice_identificativo_univoco_pagatore : null,
						isValidWhereCondition(errorCode, "anagrafica_pagatore") ? anagrafica_pagatore : null,
						isValidWhereCondition(errorCode, "causale_versamento") ? causale_versamento : null,
						isValidWhereCondition(errorCode, "data_esecuzione_singolo_pagamento_da")
								? data_esecuzione_singolo_pagamento_da : null,
						isValidWhereCondition(errorCode, "data_esecuzione_singolo_pagamento_a")
								? data_esecuzione_singolo_pagamento_a : null,
						isValidWhereCondition(errorCode, "data_esito_singolo_pagamento_da")
								? data_esito_singolo_pagamento_da : null,
						isValidWhereCondition(errorCode, "data_esito_singolo_pagamento_a")
								? data_esito_singolo_pagamento_a : null,
						isValidWhereCondition(errorCode, "identificativo_flusso_rendicontazione")
								? identificativo_flusso_rendicontazione : null,
						isValidWhereCondition(errorCode, "identificativo_univoco_regolamento")
								? identificativo_univoco_regolamento : null,
						isValidWhereCondition(errorCode, "data_regolamento_da") ? data_regolamento_da : null,
						isValidWhereCondition(errorCode, "data_regolamento_a") ? data_regolamento_a : null,
						isValidWhereCondition(errorCode, "data_contabile_da") ? dt_data_contabile_da : null,
						isValidWhereCondition(errorCode, "data_contabile_a") ? dt_data_contabile_a : null,
						isValidWhereCondition(errorCode, "data_valuta_da") ? data_valuta_da : null,
						isValidWhereCondition(errorCode, "data_valuta_a") ? data_valuta_a : null,
						isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da")
								? dt_data_ultimo_aggiornamento_da : null,
						isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a") ? dt_data_ultimo_aggiornamento_a
								: null,
						cod_tipo_dovuto, isValidWhereCondition(errorCode, "tipoDovuto"),
						isValidWhereCondition(errorCode, "importo") ? importo : null,
						isValidWhereCondition(errorCode, "codConto") ? conto : null,
						isValidWhereCondition(errorCode, "codOr1") ? codOr1 : null, visualizzaNascosti, errorCode);

		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(importExportRendicontazioneTesoreriaList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria : importExportRendicontazioneTesoreriaList) {
				String codIuvKey = importExportRendicontazioneTesoreria.getCodIuvKey();
				String codIufKey = importExportRendicontazioneTesoreria.getCodIufKey();
				String codIudKey = importExportRendicontazioneTesoreria.getCodIudKey();
				String codIpaEnte = importExportRendicontazioneTesoreria.getId() != null
						? importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte() : null;
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
						importExportRendicontazioneTesoreria.getId().getClassificazioneCompletezza(), codIufKey,
						codIudKey, codIuvKey);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}

		List<VisualizzaCompletaDto> visualizzaDtos = mapEntitiesListToDtosList(importExportRendicontazioneTesoreriaList,
				segnalazioni);

		PageDto<VisualizzaCompletaDto> visualizzaDtoPage = new PageDto<VisualizzaCompletaDto>();
		visualizzaDtoPage.setList(visualizzaDtos);

		boolean hasNextPage = numberResult - (size * page) > 0 ? true : false;
		visualizzaDtoPage.setNextPage(hasNextPage);
		visualizzaDtoPage.setPage(page);
		visualizzaDtoPage.setPageSize(size);
		boolean hasPreviousPage = page > 1 ? true : false;
		visualizzaDtoPage.setPreviousPage(hasPreviousPage);
		Double totalPages = new Double((numberResult / size)) + (numberResult % size == 0 ? 0 : 1);
		visualizzaDtoPage.setTotalPages(totalPages.intValue());
		visualizzaDtoPage.setTotalRecords(numberResult);

		return visualizzaDtoPage;
	}

	@Override
	public PageDto<VisualizzaCompletaDto> getImportExportRendicontazioneTesoreriaPage(String cod_fed_user_id,
			String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date dt_data_esecuzione_singolo_pagamento_da,
			Date dt_data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String cod_tipo_dovuto,
			String importo, String conto, String codOr1, String visualizzaNascosti, String errorCode, int page,
			int size, Sort sort) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, size, sort);

		Page<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaPage = getImportExportRendicontazioneTesoreriaPageList(
				cod_fed_user_id, cod_ipa_ente, codice_iud, codice_iuv, denominazione_attestante,
				identificativo_univoco_riscossione, codice_identificativo_univoco_versante, anagrafica_versante,
				codice_identificativo_univoco_pagatore, anagrafica_pagatore, causale_versamento,
				dt_data_esecuzione_singolo_pagamento_da, dt_data_esecuzione_singolo_pagamento_a,
				data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a, identificativo_flusso_rendicontazione,
				identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, dt_data_contabile_da,
				dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a, dt_data_ultimo_aggiornamento_da,
				dt_data_ultimo_aggiornamento_a, cod_tipo_dovuto, importo, conto, codOr1, visualizzaNascosti, errorCode,
				pageable);

		List<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaList = importExportRendicontazioneTesoreriaPage
				.getContent();
		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(importExportRendicontazioneTesoreriaList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria : importExportRendicontazioneTesoreriaList) {
				String codIuvKey = importExportRendicontazioneTesoreria.getCodIuvKey();
				String codIufKey = importExportRendicontazioneTesoreria.getCodIufKey();
				String codIudKey = importExportRendicontazioneTesoreria.getCodIudKey();
				String codIpaEnte = importExportRendicontazioneTesoreria.getId() != null
						? importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte() : null;
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
						importExportRendicontazioneTesoreria.getId().getClassificazioneCompletezza(), codIufKey,
						codIudKey, codIuvKey);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}

		List<VisualizzaCompletaDto> visualizzaDtos = mapEntitiesListToDtosList(importExportRendicontazioneTesoreriaList,
				segnalazioni);

		PageDto<VisualizzaCompletaDto> visualizzaDtoPage = new PageDto<VisualizzaCompletaDto>();
		visualizzaDtoPage.setList(visualizzaDtos);

		visualizzaDtoPage.setNextPage(importExportRendicontazioneTesoreriaPage.hasNextPage());
		visualizzaDtoPage.setPage(importExportRendicontazioneTesoreriaPage.getNumber() + 1);
		visualizzaDtoPage.setPageSize(importExportRendicontazioneTesoreriaPage.getSize());
		visualizzaDtoPage.setPreviousPage(importExportRendicontazioneTesoreriaPage.hasPreviousPage());
		visualizzaDtoPage.setTotalPages(importExportRendicontazioneTesoreriaPage.getTotalPages());
		visualizzaDtoPage.setTotalRecords(importExportRendicontazioneTesoreriaPage.getTotalElements());

		return visualizzaDtoPage;

	}

	@Override
	public List<ExportCSVDtoIFace> getImportExportRendicontazioneTesoreria(String cod_fed_user_id, String cod_ipa_ente,
			String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date dt_data_esecuzione_singolo_pagamento_da,
			Date dt_data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			String cod_tipo_dovuto, String importo, String conto, String codOr1, String visualizzaNascosti,
			String errorCode, Sort sort) throws IllegalAccessException, InvocationTargetException {

		Specifications<ImportExportRendicontazioneTesoreria> specification = getSpecifications(cod_fed_user_id,
				cod_ipa_ente, codice_iud, codice_iuv, denominazione_attestante, identificativo_univoco_riscossione,
				codice_identificativo_univoco_versante, anagrafica_versante, codice_identificativo_univoco_pagatore,
				anagrafica_pagatore, causale_versamento, dt_data_esecuzione_singolo_pagamento_da,
				dt_data_esecuzione_singolo_pagamento_a, data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a,
				identificativo_flusso_rendicontazione, identificativo_univoco_regolamento, data_regolamento_da,
				data_regolamento_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a,
				null, null, cod_tipo_dovuto, importo, conto, codOr1, errorCode);

		Boolean visualizzaNascostiBool = null;
		if (VISUALIZZA_NASCOSTI.TRUE.getValue().equalsIgnoreCase(visualizzaNascosti)) {
			visualizzaNascostiBool = true;
		} else if (VISUALIZZA_NASCOSTI.FALSE.getValue().equalsIgnoreCase(visualizzaNascosti)) {
			visualizzaNascostiBool = false;
		}

		Specification<ImportExportRendicontazioneTesoreria> segnalazioneFilterSpecification = getSegnalazioneFilterSpecification(
				visualizzaNascostiBool);
		specification = specification == null ? Specifications.where(segnalazioneFilterSpecification)
				: specification.and(segnalazioneFilterSpecification);

		List<ImportExportRendicontazioneTesoreria> exportRendicontazioneTesoreria = new ArrayList<ImportExportRendicontazioneTesoreria>();
		if (specification != null) {
			exportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaDao.findAll(specification, sort);
		} else {
			exportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaDao.findAll(sort);
		}
		return mapEntitiesListToExportCSVTesoreriaDtosList(exportRendicontazioneTesoreria, errorCode);
	}

	public Page<ImportExportRendicontazioneTesoreria> getImportExportRendicontazioneTesoreriaPageList(
			String cod_fed_user_id, String cod_ipa_ente, String codice_iud, String codice_iuv,
			String denominazione_attestante, String identificativo_univoco_riscossione,
			String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento,
			Date dt_data_esecuzione_singolo_pagamento_da, Date dt_data_esecuzione_singolo_pagamento_a,
			Date data_esito_singolo_pagamento_da, Date data_esito_singolo_pagamento_a,
			String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date data_regolamento_da, Date data_regolamento_a, Date dt_data_contabile_da, Date dt_data_contabile_a,
			Date dt_data_valuta_da, Date dt_data_valuta_a, Date dt_data_ultimo_aggiornamento_da,
			Date dt_data_ultimo_aggiornamento_a, String cod_tipo_dovuto, String importo, String conto, String codOr1,
			String visualizzaNascosti, String errorCode, Pageable pageable) {

		Specifications<ImportExportRendicontazioneTesoreria> specification = getSpecifications(cod_fed_user_id,
				cod_ipa_ente, codice_iud, codice_iuv, denominazione_attestante, identificativo_univoco_riscossione,
				codice_identificativo_univoco_versante, anagrafica_versante, codice_identificativo_univoco_pagatore,
				anagrafica_pagatore, causale_versamento, dt_data_esecuzione_singolo_pagamento_da,
				dt_data_esecuzione_singolo_pagamento_a, data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a,
				identificativo_flusso_rendicontazione, identificativo_univoco_regolamento, data_regolamento_da,
				data_regolamento_a, dt_data_contabile_da, dt_data_contabile_a, dt_data_valuta_da, dt_data_valuta_a,
				dt_data_ultimo_aggiornamento_da, dt_data_ultimo_aggiornamento_a, cod_tipo_dovuto, importo, conto,
				codOr1, errorCode);

		Boolean visualizzaNascostiBool = null;
		if (VISUALIZZA_NASCOSTI.TRUE.getValue().equalsIgnoreCase(visualizzaNascosti)) {
			visualizzaNascostiBool = true;
		} else if (VISUALIZZA_NASCOSTI.FALSE.getValue().equalsIgnoreCase(visualizzaNascosti)) {
			visualizzaNascostiBool = false;
		}

		Specification<ImportExportRendicontazioneTesoreria> segnalazioniSpecification = getSegnalazioneFilterSpecification(
				visualizzaNascostiBool);
		specification = specification == null ? Specifications.where(segnalazioniSpecification)
				: specification.and(segnalazioniSpecification);

		Page<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreria = null;
		if (specification != null) {
			importExportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaDao.findAll(specification,
					pageable);
		} else {
			importExportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaDao.findAll(pageable);
		}
		return importExportRendicontazioneTesoreria;
	}

	private Specifications<ImportExportRendicontazioneTesoreria> getSpecifications(String cod_fed_user_id,
			String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante,
			String anagrafica_versante, String codice_identificativo_univoco_pagatore, String anagrafica_pagatore,
			String causale_versamento, Date data_esecuzione_singolo_pagamento_da,
			Date data_esecuzione_singolo_pagamento_a, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione,
			String identificativo_univoco_regolamento, Date data_regolamento_da, Date data_regolamento_a,
			Date dt_data_contabile_da, Date dt_data_contabile_a, Date dt_data_valuta_da, Date dt_data_valuta_a,
			Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a, String cod_tipo_dovuto,
			String importo, String conto, String codOr1, String errorCode) {

		Specifications<ImportExportRendicontazioneTesoreria> specifications = null;

		if (StringUtils.isNotBlank(cod_ipa_ente)) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codiceIpaEnte", "=", cod_ipa_ente.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codice_iud) && isValidWhereCondition(errorCode, "codice_iud")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codiceIud", "=", codice_iud.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codice_iuv) && isValidWhereCondition(errorCode, "codice_iuv")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codiceIuv", "=", codice_iuv.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(denominazione_attestante)
				&& isValidWhereCondition(errorCode, "denominazione_attestante")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> denominazioneAttestanteSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("denominazioneAttestante", "like", denominazione_attestante.trim(), false));
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> codiceIdentificativoUnivocoAttestanteSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codiceIdentificativoUnivocoAttestante", "like", denominazione_attestante.trim(),
							false));

			Specifications<ImportExportRendicontazioneTesoreria> denominazioneAttestante_OR_identificativoAttestanteSpecifications = Specifications
					.where(denominazioneAttestanteSpecification).or(codiceIdentificativoUnivocoAttestanteSpecification);

			specifications = specifications == null ? denominazioneAttestante_OR_identificativoAttestanteSpecifications
					: specifications.and(denominazioneAttestante_OR_identificativoAttestanteSpecifications);
		}
		if (StringUtils.isNotBlank(identificativo_univoco_riscossione)
				&& isValidWhereCondition(errorCode, "identificativo_univoco_riscossione")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("identificativoUnivocoRiscossione", "=",
							identificativo_univoco_riscossione.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codice_identificativo_univoco_versante)
				&& isValidWhereCondition(errorCode, "codice_identificativo_univoco_versante")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codiceIdentificativoUnivocoVersante", "=",
							codice_identificativo_univoco_versante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(anagrafica_versante) && isValidWhereCondition(errorCode, "anagrafica_versante")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("anagraficaVersante", "like", anagrafica_versante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codice_identificativo_univoco_pagatore)
				&& isValidWhereCondition(errorCode, "codice_identificativo_univoco_pagatore")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codiceIdentificativoUnivocoPagatore", "=",
							codice_identificativo_univoco_pagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(anagrafica_pagatore) && isValidWhereCondition(errorCode, "anagrafica_pagatore")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("anagraficaPagatore", "like", anagrafica_pagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(causale_versamento) && isValidWhereCondition(errorCode, "causale_versamento")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("causaleVersamento", "like", causale_versamento.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (data_esecuzione_singolo_pagamento_da != null
				&& isValidWhereCondition(errorCode, "data_esecuzione_singolo_pagamento_da")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataEsecuzionePagamento", ">=", data_esecuzione_singolo_pagamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (data_esecuzione_singolo_pagamento_a != null
				&& isValidWhereCondition(errorCode, "data_esecuzione_singolo_pagamento_a")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataEsecuzionePagamento", "<=", data_esecuzione_singolo_pagamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (data_esito_singolo_pagamento_da != null
				&& isValidWhereCondition(errorCode, "data_esito_singolo_pagamento_da")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataEsitoSingoloPagamento", ">=", data_esito_singolo_pagamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (data_esito_singolo_pagamento_a != null
				&& isValidWhereCondition(errorCode, "data_esito_singolo_pagamento_a")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataEsitoSingoloPagamento", "<=", data_esito_singolo_pagamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione)
				&& isValidWhereCondition(errorCode, "identificativo_flusso_rendicontazione")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("identificativoFlussoRendicontazione", "=",
							identificativo_flusso_rendicontazione.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(identificativo_univoco_regolamento)
				&& isValidWhereCondition(errorCode, "identificativo_univoco_regolamento")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("identificativoUnivocoRegolamento", "=",
							identificativo_univoco_regolamento.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (data_regolamento_da != null && isValidWhereCondition(errorCode, "data_regolamento_da")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataRegolamento", ">=", data_regolamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (data_regolamento_a != null && isValidWhereCondition(errorCode, "data_regolamento_a")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataRegolamento", "<=", data_regolamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_contabile_da != null && isValidWhereCondition(errorCode, "data_contabile_da")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataContabile", ">=", dt_data_contabile_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_contabile_a != null && isValidWhereCondition(errorCode, "data_contabile_a")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataContabile", "<=", dt_data_contabile_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_valuta_da != null && isValidWhereCondition(errorCode, "data_valuta_da")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataValuta", ">=", dt_data_valuta_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_valuta_a != null && isValidWhereCondition(errorCode, "data_valuta_a")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataValuta", "<=", dt_data_valuta_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (dt_data_ultimo_aggiornamento_da != null
				&& isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_da")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataUltimoAggiornamento", ">=", dt_data_ultimo_aggiornamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_ultimo_aggiornamento_a != null && isValidWhereCondition(errorCode, "data_ultimo_aggiornamento_a")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("dtDataUltimoAggiornamento", "<", dt_data_ultimo_aggiornamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (isValidWhereCondition(errorCode, "tipoDovuto")) {
			if (StringUtils.isEmpty(cod_tipo_dovuto)) {
				List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService
						.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(cod_fed_user_id, cod_ipa_ente);
				MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("tipoDovuto", "in", listaCodTipoDovuto, false));
				specifications = specifications == null ? Specifications.where(myJpaSpecification)
						: specifications.and(myJpaSpecification);
			} else {
				MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("tipoDovuto", "=", cod_tipo_dovuto.trim(), false));
				specifications = specifications == null ? Specifications.where(myJpaSpecification)
						: specifications.and(myJpaSpecification);
			}
		}

		if (StringUtils.isNotBlank(importo) && isValidWhereCondition(errorCode, "importo")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("deImporto", "=", importo.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(conto) && isValidWhereCondition(errorCode, "codConto")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codConto", "=", conto.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(codOr1) && isValidWhereCondition(errorCode, "codOr1")) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("codOr1", "like", codOr1.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(errorCode)) {
			MyJpaSpecification<ImportExportRendicontazioneTesoreria> myJpaSpecification = new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
					new SearchCriteria("classificazioneCompletezza", "=", errorCode.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		return specifications;
	}

	/**
	 * @param entities
	 * @return
	 */
	private List<VisualizzaCompletaDto> mapEntitiesListToDtosList(List<ImportExportRendicontazioneTesoreria> entities,
			Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni) {
		List<VisualizzaCompletaDto> dtos = new ArrayList<VisualizzaCompletaDto>();
		for (ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria : entities) {

			VisualizzaCompletaDto visualizzaCompletaDto = new VisualizzaCompletaDto();

			String codIuvKey = importExportRendicontazioneTesoreria.getCodIuvKey();
			String codIufKey = importExportRendicontazioneTesoreria.getCodIufKey();
			String codIudKey = importExportRendicontazioneTesoreria.getCodIudKey();
			String codIpaEnte = importExportRendicontazioneTesoreria.getId() != null
					? importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte() : null;
			SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
					importExportRendicontazioneTesoreria.getId().getClassificazioneCompletezza(), codIufKey, codIudKey,
					codIuvKey);

			if (segnalazioni.containsKey(key)) {
				visualizzaCompletaDto.setSegnalazione(segnalazioni.get(key));
			}

			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO = modelMapperUtil
					.map(importExportRendicontazioneTesoreria, ImportExportRendicontazioneTesoreriaTO.class);

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getSingoloImportoPagato())) {
				importExportRendicontazioneTesoreriaTO.setSingoloImportoPagato("n/a");
			} else if (!importExportRendicontazioneTesoreria.getSingoloImportoPagato().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String singoloImportoPagato = format
						.format(new BigDecimal(importExportRendicontazioneTesoreriaTO.getSingoloImportoPagato()));
				importExportRendicontazioneTesoreriaTO.setSingoloImportoPagato("€ " + singoloImportoPagato);
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getImportoTotalePagamenti())) {
				importExportRendicontazioneTesoreriaTO.setImportoTotalePagamenti("n/a");
			} else if (!importExportRendicontazioneTesoreria.getImportoTotalePagamenti().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String importoTotalePagamenti = format
						.format(new BigDecimal(importExportRendicontazioneTesoreriaTO.getImportoTotalePagamenti()));
				importExportRendicontazioneTesoreriaTO.setImportoTotalePagamenti("€ " + importoTotalePagamenti);
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getDeImporto())) {
				importExportRendicontazioneTesoreriaTO.setDeImporto("n/a");
			} else if (!importExportRendicontazioneTesoreria.getDeImporto().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String deImporto = format.format(new BigDecimal(importExportRendicontazioneTesoreriaTO.getDeImporto()));
				importExportRendicontazioneTesoreriaTO.setDeImporto("€ " + deImporto);
			}

			if (StringUtils.isNotBlank(importExportRendicontazioneTesoreriaTO.getEsitoSingoloPagamento())) {
				visualizzaCompletaDto
						.setEsitoSingoloPagamento(importExportRendicontazioneTesoreriaTO.getEsitoSingoloPagamento());
			} else {
				visualizzaCompletaDto.setEsitoSingoloPagamento("-");
			}

			if (StringUtils
					.isNotBlank(importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione())
					&& !importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione()
							.equals("n/a")) {
				visualizzaCompletaDto.setIdentificativoRiversamento(
						importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione());
			} else {
				visualizzaCompletaDto
						.setIdentificativoRiversamento(importExportRendicontazioneTesoreria.getId().getCodiceIuv());
			}

			visualizzaCompletaDto.setCodiceIpaEnte(importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte());
			visualizzaCompletaDto.setCodiceIuv(importExportRendicontazioneTesoreria.getId().getCodiceIuv());
			visualizzaCompletaDto.setIdentificativoUnivocoRiscossione(
					importExportRendicontazioneTesoreria.getId().getIdentificativoUnivocoRiscossione());
			visualizzaCompletaDto.setIdentificativoFlussoRendicontazione(
					importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione());

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getCodConto())) {
				importExportRendicontazioneTesoreriaTO.setCodConto("n/a");
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getDeDataValuta())) {
				importExportRendicontazioneTesoreriaTO.setDeDataValuta("n/a");
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getDeDataContabile())) {
				importExportRendicontazioneTesoreriaTO.setDeDataContabile("n/a");
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getDeDataUltimoAggiornamento())) {
				importExportRendicontazioneTesoreriaTO.setDeDataUltimoAggiornamento("n/a");
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getCodOr1())) {
				importExportRendicontazioneTesoreriaTO.setCodOr1("n/a");
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getCausaleVersamento())) {
				importExportRendicontazioneTesoreriaTO.setCausaleVersamento("n/a");
			}

			if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getDatiSpecificiRiscossione())) {
				importExportRendicontazioneTesoreriaTO.setDatiSpecificiRiscossione("n/a");
			}
			
			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(
					importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte(),
					importExportRendicontazioneTesoreria.getTipoDovuto());
			if (enteTipoDovuto == null) {
				if (StringUtils.isBlank(importExportRendicontazioneTesoreria.getTipoDovuto())) {
					importExportRendicontazioneTesoreriaTO.setTipoDovuto("n/a");
				} else {
					importExportRendicontazioneTesoreriaTO
							.setTipoDovuto(importExportRendicontazioneTesoreria.getTipoDovuto());
				}

			} else {
				importExportRendicontazioneTesoreriaTO.setTipoDovuto(enteTipoDovuto.getDeTipo());
			}
			String rowClass = null;
			switch (importExportRendicontazioneTesoreriaTO.getVerificaTotale()) {
			case "OK":
				rowClass = "visualizza-lista-row-ok";
				break;
			case "KO":
				rowClass = "visualizza-lista-row-ko";
				break;
			case "n/a":
				rowClass = "visualizza-lista-row-na";
				break;
			default:
				break;
			}
			visualizzaCompletaDto.setRowClass(rowClass);
			visualizzaCompletaDto.setImportExportRendicontazioneTesoreriaTO(importExportRendicontazioneTesoreriaTO);

			dtos.add(visualizzaCompletaDto);
		}
		return dtos;
	}

	private List<ExportCSVDtoIFace> mapEntitiesListToExportCSVTesoreriaDtosList(
			List<ImportExportRendicontazioneTesoreria> entities, String errorCode)
			throws IllegalAccessException, InvocationTargetException {
		List<ExportCSVDtoIFace> dtos = new ArrayList<ExportCSVDtoIFace>();
		for (ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria : entities) {
			ExportCSVDtoIFace exportCSVCompletaDto = null;
			switch (errorCode) {
			case Constants.COD_ERRORE_IUD_RT_IUF_TES:
				exportCSVCompletaDto = convertImportExportRendicontazioneToExportCSVIudRtIufTes(
						importExportRendicontazioneTesoreria);
				break;
			case Constants.COD_ERRORE_RT_IUF_TES:
				exportCSVCompletaDto = convertImportExportRendicontazioneToExportCSVRtIufTes(
						importExportRendicontazioneTesoreria);
				break;
			case Constants.COD_ERRORE_RT_IUF:
				exportCSVCompletaDto = convertImportExportRendicontazioneToExportCSVRtIuf(
						importExportRendicontazioneTesoreria);
				break;
			}

			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(
					importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte(),
					importExportRendicontazioneTesoreria.getTipoDovuto());
			if (enteTipoDovuto == null) {
				exportCSVCompletaDto.setTipoDovuto(importExportRendicontazioneTesoreria.getTipoDovuto());
			} else {
				exportCSVCompletaDto.setTipoDovuto(enteTipoDovuto.getDeTipo());
			}
			dtos.add(exportCSVCompletaDto);
		}
		return dtos;
	}

	private ExportCSVRtIufDto convertImportExportRendicontazioneToExportCSVRtIuf(
			ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria) {
		ExportCSVRtIufDto exportCSVTCompletaDto = new ExportCSVRtIufDto();

		// TODO (solo se class TUTTI, in base ad ente non tutti campi sono
		// necessari (pagati, tesoreria)

		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoAttestante(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoAttestante());
		exportCSVTCompletaDto.setIdentificativoUnivocoRiscossione(
				importExportRendicontazioneTesoreria.getId().getIdentificativoUnivocoRiscossione());
		exportCSVTCompletaDto.setCodiceIpaEnte(importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte());
		exportCSVTCompletaDto.setNomeFlussoImportEnte(importExportRendicontazioneTesoreria.getNomeFlussoImportEnte());
		exportCSVTCompletaDto.setRigaFlussoImportEnte(importExportRendicontazioneTesoreria.getRigaFlussoImportEnte());
		exportCSVTCompletaDto.setCodiceIud(importExportRendicontazioneTesoreria.getCodiceIud());
		exportCSVTCompletaDto.setCodiceIuv(importExportRendicontazioneTesoreria.getId().getCodiceIuv());
		exportCSVTCompletaDto.setVersioneOggetto(importExportRendicontazioneTesoreria.getVersioneOggetto());
		exportCSVTCompletaDto.setIdentificativoDominio(importExportRendicontazioneTesoreria.getIdentificativoDominio());
		exportCSVTCompletaDto.setIdentificativoStazioneRichiedente(
				importExportRendicontazioneTesoreria.getIdentificativoStazioneRichiedente());
		exportCSVTCompletaDto.setIdentificativoMessaggioRicevuta(
				importExportRendicontazioneTesoreria.getIdentificativoMessaggioRicevuta());
		exportCSVTCompletaDto
				.setDataOraMessaggioRicevuta(importExportRendicontazioneTesoreria.getDataOraMessaggioRicevuta());
		exportCSVTCompletaDto.setRiferimentoMessaggioRichiesta(
				importExportRendicontazioneTesoreria.getRiferimentoMessaggioRichiesta());
		exportCSVTCompletaDto
				.setRiferimentoDataRichiesta(importExportRendicontazioneTesoreria.getRiferimentoDataRichiesta());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoAttestante(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoAttestante());
		exportCSVTCompletaDto
				.setDenominazioneAttestante(importExportRendicontazioneTesoreria.getDenominazioneAttestante());
		exportCSVTCompletaDto
				.setCodiceUnitOperAttestante(importExportRendicontazioneTesoreria.getCodiceUnitOperAttestante());
		exportCSVTCompletaDto
				.setDenomUnitOperAttestante(importExportRendicontazioneTesoreria.getDenomUnitOperAttestante());
		exportCSVTCompletaDto.setIndirizzoAttestante(importExportRendicontazioneTesoreria.getIndirizzoAttestante());
		exportCSVTCompletaDto.setCivicoAttestante(importExportRendicontazioneTesoreria.getCivicoAttestante());
		exportCSVTCompletaDto.setCapAttestante(importExportRendicontazioneTesoreria.getCapAttestante());
		exportCSVTCompletaDto.setLocalitaAttestante(importExportRendicontazioneTesoreria.getLocalitaAttestante());
		exportCSVTCompletaDto.setProvinciaAttestante(importExportRendicontazioneTesoreria.getProvinciaAttestante());
		exportCSVTCompletaDto.setNazioneAttestante(importExportRendicontazioneTesoreria.getNazioneAttestante());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoBeneficiario(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoBeneficiario());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoBeneficiario(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoBeneficiario());
		exportCSVTCompletaDto
				.setDenominazioneBeneficiario(importExportRendicontazioneTesoreria.getDenominazioneBeneficiario());
		exportCSVTCompletaDto
				.setCodiceUnitOperBeneficiario(importExportRendicontazioneTesoreria.getCodiceUnitOperBeneficiario());
		exportCSVTCompletaDto
				.setDenomUnitOperBeneficiario(importExportRendicontazioneTesoreria.getDenomUnitOperBeneficiario());
		exportCSVTCompletaDto.setIndirizzoBeneficiario(importExportRendicontazioneTesoreria.getIndirizzoBeneficiario());
		exportCSVTCompletaDto.setCivicoBeneficiario(importExportRendicontazioneTesoreria.getCivicoBeneficiario());
		exportCSVTCompletaDto.setCapBeneficiario(importExportRendicontazioneTesoreria.getCapBeneficiario());
		exportCSVTCompletaDto.setLocalitaBeneficiario(importExportRendicontazioneTesoreria.getLocalitaBeneficiario());
		exportCSVTCompletaDto.setProvinciaBeneficiario(importExportRendicontazioneTesoreria.getProvinciaBeneficiario());
		exportCSVTCompletaDto.setNazioneBeneficiario(importExportRendicontazioneTesoreria.getNazioneBeneficiario());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoVersante(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoVersante());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoVersante(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoVersante());
		exportCSVTCompletaDto.setAnagraficaVersante(importExportRendicontazioneTesoreria.getAnagraficaVersante());
		exportCSVTCompletaDto.setIndirizzoVersante(importExportRendicontazioneTesoreria.getIndirizzoVersante());
		exportCSVTCompletaDto.setCivicoVersante(importExportRendicontazioneTesoreria.getCivicoVersante());
		exportCSVTCompletaDto.setCapVersante(importExportRendicontazioneTesoreria.getCapVersante());
		exportCSVTCompletaDto.setLocalitaVersante(importExportRendicontazioneTesoreria.getLocalitaVersante());
		exportCSVTCompletaDto.setProvinciaVersante(importExportRendicontazioneTesoreria.getProvinciaVersante());
		exportCSVTCompletaDto.setNazioneVersante(importExportRendicontazioneTesoreria.getNazioneVersante());
		exportCSVTCompletaDto.setEmailVersante(importExportRendicontazioneTesoreria.getEmailVersante());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoPagatore(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoPagatore());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoPagatore(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoPagatore());
		exportCSVTCompletaDto.setAnagraficaPagatore(importExportRendicontazioneTesoreria.getAnagraficaPagatore());
		exportCSVTCompletaDto.setIndirizzoPagatore(importExportRendicontazioneTesoreria.getIndirizzoPagatore());
		exportCSVTCompletaDto.setCivicoPagatore(importExportRendicontazioneTesoreria.getCivicoPagatore());
		exportCSVTCompletaDto.setCapPagatore(importExportRendicontazioneTesoreria.getCapPagatore());
		exportCSVTCompletaDto.setLocalitaPagatore(importExportRendicontazioneTesoreria.getLocalitaPagatore());
		exportCSVTCompletaDto.setProvinciaPagatore(importExportRendicontazioneTesoreria.getProvinciaPagatore());
		exportCSVTCompletaDto.setNazionePagatore(importExportRendicontazioneTesoreria.getNazionePagatore());
		exportCSVTCompletaDto.setEmailPagatore(importExportRendicontazioneTesoreria.getEmailPagatore());
		exportCSVTCompletaDto.setCodiceEsitoPagamento(importExportRendicontazioneTesoreria.getCodiceEsitoPagamento());
		exportCSVTCompletaDto.setImportoTotalePagato(importExportRendicontazioneTesoreria.getImportoTotalePagato());
		exportCSVTCompletaDto.setIdentificativoUnivocoVersamento(
				importExportRendicontazioneTesoreria.getIdentificativoUnivocoVersamento());
		exportCSVTCompletaDto
				.setCodiceContestoPagamento(importExportRendicontazioneTesoreria.getCodiceContestoPagamento());
		exportCSVTCompletaDto.setSingoloImportoPagato(importExportRendicontazioneTesoreria.getSingoloImportoPagato());
		exportCSVTCompletaDto.setEsitoSingoloPagamento(importExportRendicontazioneTesoreria.getEsitoSingoloPagamento());
		exportCSVTCompletaDto
				.setDataEsitoSingoloPagamento(importExportRendicontazioneTesoreria.getDeDataEsitoSingoloPagamento());
		exportCSVTCompletaDto.setCausaleVersamento(importExportRendicontazioneTesoreria.getCausaleVersamento());
		exportCSVTCompletaDto
				.setDatiSpecificiRiscossione(importExportRendicontazioneTesoreria.getDatiSpecificiRiscossione());
		exportCSVTCompletaDto.setTipoDovuto(importExportRendicontazioneTesoreria.getTipoDovuto());
		exportCSVTCompletaDto.setIdentificativoFlussoRendicontazione(
				importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione());
		exportCSVTCompletaDto.setDataOraFlussoRendicontazione(
				importExportRendicontazioneTesoreria.getDataOraFlussoRendicontazione());
		exportCSVTCompletaDto.setIdentificativoUnivocoRegolamento(
				importExportRendicontazioneTesoreria.getIdentificativoUnivocoRegolamento());
		exportCSVTCompletaDto.setDataRegolamento(importExportRendicontazioneTesoreria.getDeDataRegolamento());
		exportCSVTCompletaDto.setNumeroTotalePagamenti(importExportRendicontazioneTesoreria.getNumeroTotalePagamenti());
		exportCSVTCompletaDto
				.setImportoTotalePagamenti(importExportRendicontazioneTesoreria.getImportoTotalePagamenti());
		exportCSVTCompletaDto.setDataAcquisizione(importExportRendicontazioneTesoreria.getDataAcquisizione());
		exportCSVTCompletaDto.setClassificazioneCompletezza(
				importExportRendicontazioneTesoreria.getId().getClassificazioneCompletezza());

		return exportCSVTCompletaDto;
	}

	private ExportCSVRtIufTesDto convertImportExportRendicontazioneToExportCSVRtIufTes(
			ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria) {
		ExportCSVRtIufTesDto exportCSVTCompletaDto = new ExportCSVRtIufTesDto();

		// TODO (solo se class TUTTI, in base ad ente non tutti campi sono
		// necessari (pagati, tesoreria)

		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoAttestante(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoAttestante());
		exportCSVTCompletaDto.setIdentificativoUnivocoRiscossione(
				importExportRendicontazioneTesoreria.getId().getIdentificativoUnivocoRiscossione());
		exportCSVTCompletaDto.setCodiceIpaEnte(importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte());
		exportCSVTCompletaDto.setNomeFlussoImportEnte(importExportRendicontazioneTesoreria.getNomeFlussoImportEnte());
		exportCSVTCompletaDto.setRigaFlussoImportEnte(importExportRendicontazioneTesoreria.getRigaFlussoImportEnte());
		exportCSVTCompletaDto.setCodiceIud(importExportRendicontazioneTesoreria.getCodiceIud());
		exportCSVTCompletaDto.setCodiceIuv(importExportRendicontazioneTesoreria.getId().getCodiceIuv());
		exportCSVTCompletaDto.setVersioneOggetto(importExportRendicontazioneTesoreria.getVersioneOggetto());
		exportCSVTCompletaDto.setIdentificativoDominio(importExportRendicontazioneTesoreria.getIdentificativoDominio());
		exportCSVTCompletaDto.setIdentificativoStazioneRichiedente(
				importExportRendicontazioneTesoreria.getIdentificativoStazioneRichiedente());
		exportCSVTCompletaDto.setIdentificativoMessaggioRicevuta(
				importExportRendicontazioneTesoreria.getIdentificativoMessaggioRicevuta());
		exportCSVTCompletaDto
				.setDataOraMessaggioRicevuta(importExportRendicontazioneTesoreria.getDataOraMessaggioRicevuta());
		exportCSVTCompletaDto.setRiferimentoMessaggioRichiesta(
				importExportRendicontazioneTesoreria.getRiferimentoMessaggioRichiesta());
		exportCSVTCompletaDto
				.setRiferimentoDataRichiesta(importExportRendicontazioneTesoreria.getRiferimentoDataRichiesta());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoAttestante(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoAttestante());
		exportCSVTCompletaDto
				.setDenominazioneAttestante(importExportRendicontazioneTesoreria.getDenominazioneAttestante());
		exportCSVTCompletaDto
				.setCodiceUnitOperAttestante(importExportRendicontazioneTesoreria.getCodiceUnitOperAttestante());
		exportCSVTCompletaDto
				.setDenomUnitOperAttestante(importExportRendicontazioneTesoreria.getDenomUnitOperAttestante());
		exportCSVTCompletaDto.setIndirizzoAttestante(importExportRendicontazioneTesoreria.getIndirizzoAttestante());
		exportCSVTCompletaDto.setCivicoAttestante(importExportRendicontazioneTesoreria.getCivicoAttestante());
		exportCSVTCompletaDto.setCapAttestante(importExportRendicontazioneTesoreria.getCapAttestante());
		exportCSVTCompletaDto.setLocalitaAttestante(importExportRendicontazioneTesoreria.getLocalitaAttestante());
		exportCSVTCompletaDto.setProvinciaAttestante(importExportRendicontazioneTesoreria.getProvinciaAttestante());
		exportCSVTCompletaDto.setNazioneAttestante(importExportRendicontazioneTesoreria.getNazioneAttestante());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoBeneficiario(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoBeneficiario());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoBeneficiario(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoBeneficiario());
		exportCSVTCompletaDto
				.setDenominazioneBeneficiario(importExportRendicontazioneTesoreria.getDenominazioneBeneficiario());
		exportCSVTCompletaDto
				.setCodiceUnitOperBeneficiario(importExportRendicontazioneTesoreria.getCodiceUnitOperBeneficiario());
		exportCSVTCompletaDto
				.setDenomUnitOperBeneficiario(importExportRendicontazioneTesoreria.getDenomUnitOperBeneficiario());
		exportCSVTCompletaDto.setIndirizzoBeneficiario(importExportRendicontazioneTesoreria.getIndirizzoBeneficiario());
		exportCSVTCompletaDto.setCivicoBeneficiario(importExportRendicontazioneTesoreria.getCivicoBeneficiario());
		exportCSVTCompletaDto.setCapBeneficiario(importExportRendicontazioneTesoreria.getCapBeneficiario());
		exportCSVTCompletaDto.setLocalitaBeneficiario(importExportRendicontazioneTesoreria.getLocalitaBeneficiario());
		exportCSVTCompletaDto.setProvinciaBeneficiario(importExportRendicontazioneTesoreria.getProvinciaBeneficiario());
		exportCSVTCompletaDto.setNazioneBeneficiario(importExportRendicontazioneTesoreria.getNazioneBeneficiario());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoVersante(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoVersante());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoVersante(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoVersante());
		exportCSVTCompletaDto.setAnagraficaVersante(importExportRendicontazioneTesoreria.getAnagraficaVersante());
		exportCSVTCompletaDto.setIndirizzoVersante(importExportRendicontazioneTesoreria.getIndirizzoVersante());
		exportCSVTCompletaDto.setCivicoVersante(importExportRendicontazioneTesoreria.getCivicoVersante());
		exportCSVTCompletaDto.setCapVersante(importExportRendicontazioneTesoreria.getCapVersante());
		exportCSVTCompletaDto.setLocalitaVersante(importExportRendicontazioneTesoreria.getLocalitaVersante());
		exportCSVTCompletaDto.setProvinciaVersante(importExportRendicontazioneTesoreria.getProvinciaVersante());
		exportCSVTCompletaDto.setNazioneVersante(importExportRendicontazioneTesoreria.getNazioneVersante());
		exportCSVTCompletaDto.setEmailVersante(importExportRendicontazioneTesoreria.getEmailVersante());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoPagatore(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoPagatore());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoPagatore(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoPagatore());
		exportCSVTCompletaDto.setAnagraficaPagatore(importExportRendicontazioneTesoreria.getAnagraficaPagatore());
		exportCSVTCompletaDto.setIndirizzoPagatore(importExportRendicontazioneTesoreria.getIndirizzoPagatore());
		exportCSVTCompletaDto.setCivicoPagatore(importExportRendicontazioneTesoreria.getCivicoPagatore());
		exportCSVTCompletaDto.setCapPagatore(importExportRendicontazioneTesoreria.getCapPagatore());
		exportCSVTCompletaDto.setLocalitaPagatore(importExportRendicontazioneTesoreria.getLocalitaPagatore());
		exportCSVTCompletaDto.setProvinciaPagatore(importExportRendicontazioneTesoreria.getProvinciaPagatore());
		exportCSVTCompletaDto.setNazionePagatore(importExportRendicontazioneTesoreria.getNazionePagatore());
		exportCSVTCompletaDto.setEmailPagatore(importExportRendicontazioneTesoreria.getEmailPagatore());
		exportCSVTCompletaDto.setCodiceEsitoPagamento(importExportRendicontazioneTesoreria.getCodiceEsitoPagamento());
		exportCSVTCompletaDto.setImportoTotalePagato(importExportRendicontazioneTesoreria.getImportoTotalePagato());
		exportCSVTCompletaDto.setIdentificativoUnivocoVersamento(
				importExportRendicontazioneTesoreria.getIdentificativoUnivocoVersamento());
		exportCSVTCompletaDto
				.setCodiceContestoPagamento(importExportRendicontazioneTesoreria.getCodiceContestoPagamento());
		exportCSVTCompletaDto.setSingoloImportoPagato(importExportRendicontazioneTesoreria.getSingoloImportoPagato());
		exportCSVTCompletaDto.setEsitoSingoloPagamento(importExportRendicontazioneTesoreria.getEsitoSingoloPagamento());
		exportCSVTCompletaDto
				.setDataEsitoSingoloPagamento(importExportRendicontazioneTesoreria.getDeDataEsitoSingoloPagamento());
		exportCSVTCompletaDto.setCausaleVersamento(importExportRendicontazioneTesoreria.getCausaleVersamento());
		exportCSVTCompletaDto
				.setDatiSpecificiRiscossione(importExportRendicontazioneTesoreria.getDatiSpecificiRiscossione());
		exportCSVTCompletaDto.setTipoDovuto(importExportRendicontazioneTesoreria.getTipoDovuto());
		exportCSVTCompletaDto.setIdentificativoFlussoRendicontazione(
				importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione());
		exportCSVTCompletaDto.setDataOraFlussoRendicontazione(
				importExportRendicontazioneTesoreria.getDataOraFlussoRendicontazione());
		exportCSVTCompletaDto.setIdentificativoUnivocoRegolamento(
				importExportRendicontazioneTesoreria.getIdentificativoUnivocoRegolamento());
		exportCSVTCompletaDto.setDataRegolamento(importExportRendicontazioneTesoreria.getDeDataRegolamento());
		exportCSVTCompletaDto.setNumeroTotalePagamenti(importExportRendicontazioneTesoreria.getNumeroTotalePagamenti());
		exportCSVTCompletaDto
				.setImportoTotalePagamenti(importExportRendicontazioneTesoreria.getImportoTotalePagamenti());
		exportCSVTCompletaDto.setDataAcquisizione(importExportRendicontazioneTesoreria.getDataAcquisizione());
		exportCSVTCompletaDto.setDataValuta(importExportRendicontazioneTesoreria.getDeDataValuta());
		exportCSVTCompletaDto.setDataContabile(importExportRendicontazioneTesoreria.getDeDataContabile());
		exportCSVTCompletaDto.setNumeroImporto(importExportRendicontazioneTesoreria.getDeImporto());
		exportCSVTCompletaDto.setCodOr1(importExportRendicontazioneTesoreria.getCodOr1());
		exportCSVTCompletaDto.setCodConto(importExportRendicontazioneTesoreria.getCodConto());
		exportCSVTCompletaDto.setVerifica(importExportRendicontazioneTesoreria.getVerificaTotale());
		exportCSVTCompletaDto.setClassificazioneCompletezza(
				importExportRendicontazioneTesoreria.getId().getClassificazioneCompletezza());

		return exportCSVTCompletaDto;
	}

	private ExportCSVIudRtIufTesDto convertImportExportRendicontazioneToExportCSVIudRtIufTes(
			ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria) {
		ExportCSVIudRtIufTesDto exportCSVTCompletaDto = new ExportCSVIudRtIufTesDto();

		// TODO (solo se class TUTTI, in base ad ente non tutti campi sono
		// necessari (pagati, tesoreria)

		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoAttestante(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoAttestante());
		exportCSVTCompletaDto.setIdentificativoUnivocoRiscossione(
				importExportRendicontazioneTesoreria.getId().getIdentificativoUnivocoRiscossione());
		exportCSVTCompletaDto.setCodiceIpaEnte(importExportRendicontazioneTesoreria.getId().getCodiceIpaEnte());
		exportCSVTCompletaDto.setNomeFlussoImportEnte(importExportRendicontazioneTesoreria.getNomeFlussoImportEnte());
		exportCSVTCompletaDto.setRigaFlussoImportEnte(importExportRendicontazioneTesoreria.getRigaFlussoImportEnte());
		exportCSVTCompletaDto.setCodiceIud(importExportRendicontazioneTesoreria.getCodiceIud());
		exportCSVTCompletaDto.setCodiceIuv(importExportRendicontazioneTesoreria.getId().getCodiceIuv());
		exportCSVTCompletaDto.setVersioneOggetto(importExportRendicontazioneTesoreria.getVersioneOggetto());
		exportCSVTCompletaDto.setIdentificativoDominio(importExportRendicontazioneTesoreria.getIdentificativoDominio());
		exportCSVTCompletaDto.setIdentificativoStazioneRichiedente(
				importExportRendicontazioneTesoreria.getIdentificativoStazioneRichiedente());
		exportCSVTCompletaDto.setIdentificativoMessaggioRicevuta(
				importExportRendicontazioneTesoreria.getIdentificativoMessaggioRicevuta());
		exportCSVTCompletaDto
				.setDataOraMessaggioRicevuta(importExportRendicontazioneTesoreria.getDataOraMessaggioRicevuta());
		exportCSVTCompletaDto.setRiferimentoMessaggioRichiesta(
				importExportRendicontazioneTesoreria.getRiferimentoMessaggioRichiesta());
		exportCSVTCompletaDto
				.setRiferimentoDataRichiesta(importExportRendicontazioneTesoreria.getRiferimentoDataRichiesta());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoAttestante(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoAttestante());
		exportCSVTCompletaDto
				.setDenominazioneAttestante(importExportRendicontazioneTesoreria.getDenominazioneAttestante());
		exportCSVTCompletaDto
				.setCodiceUnitOperAttestante(importExportRendicontazioneTesoreria.getCodiceUnitOperAttestante());
		exportCSVTCompletaDto
				.setDenomUnitOperAttestante(importExportRendicontazioneTesoreria.getDenomUnitOperAttestante());
		exportCSVTCompletaDto.setIndirizzoAttestante(importExportRendicontazioneTesoreria.getIndirizzoAttestante());
		exportCSVTCompletaDto.setCivicoAttestante(importExportRendicontazioneTesoreria.getCivicoAttestante());
		exportCSVTCompletaDto.setCapAttestante(importExportRendicontazioneTesoreria.getCapAttestante());
		exportCSVTCompletaDto.setLocalitaAttestante(importExportRendicontazioneTesoreria.getLocalitaAttestante());
		exportCSVTCompletaDto.setProvinciaAttestante(importExportRendicontazioneTesoreria.getProvinciaAttestante());
		exportCSVTCompletaDto.setNazioneAttestante(importExportRendicontazioneTesoreria.getNazioneAttestante());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoBeneficiario(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoBeneficiario());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoBeneficiario(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoBeneficiario());
		exportCSVTCompletaDto
				.setDenominazioneBeneficiario(importExportRendicontazioneTesoreria.getDenominazioneBeneficiario());
		exportCSVTCompletaDto
				.setCodiceUnitOperBeneficiario(importExportRendicontazioneTesoreria.getCodiceUnitOperBeneficiario());
		exportCSVTCompletaDto
				.setDenomUnitOperBeneficiario(importExportRendicontazioneTesoreria.getDenomUnitOperBeneficiario());
		exportCSVTCompletaDto.setIndirizzoBeneficiario(importExportRendicontazioneTesoreria.getIndirizzoBeneficiario());
		exportCSVTCompletaDto.setCivicoBeneficiario(importExportRendicontazioneTesoreria.getCivicoBeneficiario());
		exportCSVTCompletaDto.setCapBeneficiario(importExportRendicontazioneTesoreria.getCapBeneficiario());
		exportCSVTCompletaDto.setLocalitaBeneficiario(importExportRendicontazioneTesoreria.getLocalitaBeneficiario());
		exportCSVTCompletaDto.setProvinciaBeneficiario(importExportRendicontazioneTesoreria.getProvinciaBeneficiario());
		exportCSVTCompletaDto.setNazioneBeneficiario(importExportRendicontazioneTesoreria.getNazioneBeneficiario());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoVersante(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoVersante());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoVersante(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoVersante());
		exportCSVTCompletaDto.setAnagraficaVersante(importExportRendicontazioneTesoreria.getAnagraficaVersante());
		exportCSVTCompletaDto.setIndirizzoVersante(importExportRendicontazioneTesoreria.getIndirizzoVersante());
		exportCSVTCompletaDto.setCivicoVersante(importExportRendicontazioneTesoreria.getCivicoVersante());
		exportCSVTCompletaDto.setCapVersante(importExportRendicontazioneTesoreria.getCapVersante());
		exportCSVTCompletaDto.setLocalitaVersante(importExportRendicontazioneTesoreria.getLocalitaVersante());
		exportCSVTCompletaDto.setProvinciaVersante(importExportRendicontazioneTesoreria.getProvinciaVersante());
		exportCSVTCompletaDto.setNazioneVersante(importExportRendicontazioneTesoreria.getNazioneVersante());
		exportCSVTCompletaDto.setEmailVersante(importExportRendicontazioneTesoreria.getEmailVersante());
		exportCSVTCompletaDto.setTipoIdentificativoUnivocoPagatore(
				importExportRendicontazioneTesoreria.getTipoIdentificativoUnivocoPagatore());
		exportCSVTCompletaDto.setCodiceIdentificativoUnivocoPagatore(
				importExportRendicontazioneTesoreria.getCodiceIdentificativoUnivocoPagatore());
		exportCSVTCompletaDto.setAnagraficaPagatore(importExportRendicontazioneTesoreria.getAnagraficaPagatore());
		exportCSVTCompletaDto.setIndirizzoPagatore(importExportRendicontazioneTesoreria.getIndirizzoPagatore());
		exportCSVTCompletaDto.setCivicoPagatore(importExportRendicontazioneTesoreria.getCivicoPagatore());
		exportCSVTCompletaDto.setCapPagatore(importExportRendicontazioneTesoreria.getCapPagatore());
		exportCSVTCompletaDto.setLocalitaPagatore(importExportRendicontazioneTesoreria.getLocalitaPagatore());
		exportCSVTCompletaDto.setProvinciaPagatore(importExportRendicontazioneTesoreria.getProvinciaPagatore());
		exportCSVTCompletaDto.setNazionePagatore(importExportRendicontazioneTesoreria.getNazionePagatore());
		exportCSVTCompletaDto.setEmailPagatore(importExportRendicontazioneTesoreria.getEmailPagatore());
		exportCSVTCompletaDto.setCodiceEsitoPagamento(importExportRendicontazioneTesoreria.getCodiceEsitoPagamento());
		exportCSVTCompletaDto.setImportoTotalePagato(importExportRendicontazioneTesoreria.getImportoTotalePagato());
		exportCSVTCompletaDto.setIdentificativoUnivocoVersamento(
				importExportRendicontazioneTesoreria.getIdentificativoUnivocoVersamento());
		exportCSVTCompletaDto
				.setCodiceContestoPagamento(importExportRendicontazioneTesoreria.getCodiceContestoPagamento());
		exportCSVTCompletaDto.setSingoloImportoPagato(importExportRendicontazioneTesoreria.getSingoloImportoPagato());
		exportCSVTCompletaDto.setEsitoSingoloPagamento(importExportRendicontazioneTesoreria.getEsitoSingoloPagamento());
		exportCSVTCompletaDto
				.setDataEsitoSingoloPagamento(importExportRendicontazioneTesoreria.getDeDataEsitoSingoloPagamento());
		exportCSVTCompletaDto.setCausaleVersamento(importExportRendicontazioneTesoreria.getCausaleVersamento());
		exportCSVTCompletaDto
				.setDatiSpecificiRiscossione(importExportRendicontazioneTesoreria.getDatiSpecificiRiscossione());
		exportCSVTCompletaDto.setTipoDovuto(importExportRendicontazioneTesoreria.getTipoDovuto());
		exportCSVTCompletaDto.setIdentificativoFlussoRendicontazione(
				importExportRendicontazioneTesoreria.getId().getIdentificativoFlussoRendicontazione());
		exportCSVTCompletaDto.setDataOraFlussoRendicontazione(
				importExportRendicontazioneTesoreria.getDataOraFlussoRendicontazione());
		exportCSVTCompletaDto.setIdentificativoUnivocoRegolamento(
				importExportRendicontazioneTesoreria.getIdentificativoUnivocoRegolamento());
		exportCSVTCompletaDto.setDataRegolamento(importExportRendicontazioneTesoreria.getDeDataRegolamento());
		exportCSVTCompletaDto.setNumeroTotalePagamenti(importExportRendicontazioneTesoreria.getNumeroTotalePagamenti());
		exportCSVTCompletaDto
				.setImportoTotalePagamenti(importExportRendicontazioneTesoreria.getImportoTotalePagamenti());
		exportCSVTCompletaDto.setDataAcquisizione(importExportRendicontazioneTesoreria.getDataAcquisizione());
		exportCSVTCompletaDto.setDataValuta(importExportRendicontazioneTesoreria.getDeDataValuta());
		exportCSVTCompletaDto.setDataContabile(importExportRendicontazioneTesoreria.getDeDataContabile());
		exportCSVTCompletaDto.setNumeroImporto(importExportRendicontazioneTesoreria.getDeImporto());
		exportCSVTCompletaDto.setCodOr1(importExportRendicontazioneTesoreria.getCodOr1());
		exportCSVTCompletaDto.setCodConto(importExportRendicontazioneTesoreria.getCodConto());
		exportCSVTCompletaDto.setVerifica(importExportRendicontazioneTesoreria.getVerificaTotale());
		exportCSVTCompletaDto.setClassificazioneCompletezza(
				importExportRendicontazioneTesoreria.getId().getClassificazioneCompletezza());
		exportCSVTCompletaDto
				.setDataEsecuzionePagamento(importExportRendicontazioneTesoreria.getDeDataEsecuzionePagamento());

		return exportCSVTCompletaDto;
	}

	@Override
	public PageDto<ImportExportRendicontazioneTesoreriaTO> findByEnteAndClassificazioneCompletezzaAndCodIufAndPagination(
			String codiceIpaEnte, String classificazioneCompletezza, String codIuf, int page, int pageSize)
			throws MyPivotServiceException {

		// devo avere almeno iuf, la classificazione
		// e il cod dell'ente
		if (StringUtils.isBlank(codiceIpaEnte) || //
				StringUtils.isBlank(classificazioneCompletezza) || //
				StringUtils.isBlank(codIuf)//
		) {
			String errMsg = "Impossibile recuperare correttamente un record. Parametri obbligatori assenti: codiceIpaEnte["
					+ codiceIpaEnte + "], classificazioneCompletezza[" + classificazioneCompletezza + "], codIuf["
					+ codIuf + "]";
			LOG.error(errMsg);
			throw new MyPivotServiceException(errMsg);
		}

		Specifications<ImportExportRendicontazioneTesoreria> specifications = Specifications//
				.where(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("classificazioneCompletezza", "=", classificazioneCompletezza.trim(), true)))//
				// campo presente nell'id del po
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codiceIpaEnte", "=", codiceIpaEnte.trim(), true)))//
				// campo presente nell'id del po
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codIufKey", "eqOrNull", codIuf, false)))//
		;

		// ORDINO PER IUV
		Sort sort = new Sort(new Order(Direction.DESC, "codIuvKey"));

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}
		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaPage = importExportRendicontazioneTesoreriaDao
				.findAll(specifications, pageable);

		List<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaList = importExportRendicontazioneTesoreriaPage
				.getContent();
		List<ImportExportRendicontazioneTesoreriaTO> importExportRendicontazioneTesoreriaTOList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(importExportRendicontazioneTesoreriaList)) {
			for (ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria : importExportRendicontazioneTesoreriaList) {
				ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO = modelMapperUtil
						.map(importExportRendicontazioneTesoreria, ImportExportRendicontazioneTesoreriaTO.class);
				importExportRendicontazioneTesoreriaTOList.add(importExportRendicontazioneTesoreriaTO);
			}
		}

		int currPage = importExportRendicontazioneTesoreriaPage.getNumber() + 1;
		int totPage = importExportRendicontazioneTesoreriaPage.getTotalPages();
		// long totElm =
		// importExportRendicontazioneTesoreriaPage.getTotalElements();
		int maxElmPerPagina = importExportRendicontazioneTesoreriaPage.getSize();
		boolean previousPage = currPage > 1;
		boolean nextPage = currPage < totPage;

		PageDto<ImportExportRendicontazioneTesoreriaTO> pageDto = new PageDto<>(totPage, maxElmPerPagina, previousPage,
				nextPage, importExportRendicontazioneTesoreriaTOList, totPage,
				importExportRendicontazioneTesoreriaPage.getTotalElements());
		return pageDto;
	}

	@Override
	public ImportExportRendicontazioneTesoreriaTO getByEnteAndClassificazioneCompletezzaAndCodIuvAndCodIufAndCodIud(
			String codiceIpaEnte, String classificazioneCompletezza, String codIuv, String codIuf, String codIud)
			throws MyPivotServiceException {

		// devo avere almeno uno dei codici (iuv, iuf o iud), la classificazione
		// e il cod dell'ente
		if (StringUtils.isBlank(codiceIpaEnte) || //
				StringUtils.isBlank(classificazioneCompletezza) || //
				(StringUtils.isBlank(codIuv) && //
						StringUtils.isBlank(codIuf) && //
						StringUtils.isBlank(codIud))//
		) {
			String errMsg = "Impossibile recuperare correttamente un record. Parametri obbligatori assenti: codiceIpaEnte["
					+ codiceIpaEnte + "], classificazioneCompletezza[" + classificazioneCompletezza + "], codIuv["
					+ codIuv + "], codIuf[" + codIuf + "], codIud[" + codIud + "]";
			LOG.error(errMsg);
			throw new MyPivotServiceException(errMsg);
		}

		Specifications<ImportExportRendicontazioneTesoreria> specifications = Specifications//
				.where(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("classificazioneCompletezza", "=", classificazioneCompletezza.trim(), true)))//
				// campo presente nell'id del po
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codiceIpaEnte", "=", codiceIpaEnte.trim(), true)))//
				// campo presente nell'id del po
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codIuvKey", "eqOrNull", codIuv, false)))//
				// campo presente nell'id del po
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codIufKey", "eqOrNull", codIuf, false)))//
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codIudKey", "eqOrNull", codIud, false)))//
		;

//		ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaDao
//				.findAll(specifications).get(0);

//		ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO = modelMapperUtil
//		.map(importExportRendicontazioneTesoreria, ImportExportRendicontazioneTesoreriaTO.class);
//
//		return importExportRendicontazioneTesoreriaTO;

		
		
		List<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaList = importExportRendicontazioneTesoreriaDao
				.findAll(specifications);

		if(importExportRendicontazioneTesoreriaList != null && CollectionUtils.isNotEmpty(importExportRendicontazioneTesoreriaList)) {
			ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaList.get(0);
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO = modelMapperUtil
					.map(importExportRendicontazioneTesoreria, ImportExportRendicontazioneTesoreriaTO.class);

			return importExportRendicontazioneTesoreriaTO;
		}
		return null;
				
	}

	@Override
	public ImportExportRendicontazioneTesoreriaTO getByEnteAndClassificazioneCompletezzaAndCodIuf(String codiceIpaEnte,
			String classificazioneCompletezza, String codIuf) throws MyPivotServiceException {

		// devo avere almeno uno dei codici (iuv, iuf o iud), la classificazione
		// e il cod dell'ente
		if (StringUtils.isBlank(codiceIpaEnte) || //
				StringUtils.isBlank(classificazioneCompletezza) || //
				StringUtils.isBlank(codIuf)) {
			String errMsg = "Impossibile recuperare correttamente un record. Parametri obbligatori assenti: codiceIpaEnte["
					+ codiceIpaEnte + "], classificazioneCompletezza[" + classificazioneCompletezza + "], codIuf["
					+ codIuf + "]";
			LOG.error(errMsg);
			throw new MyPivotServiceException(errMsg);
		}

		Specifications<ImportExportRendicontazioneTesoreria> specifications = Specifications//
				.where(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("classificazioneCompletezza", "=", classificazioneCompletezza.trim(), true)))//
				// campo presente nell'id del po
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codiceIpaEnte", "=", codiceIpaEnte.trim(), true)))
				.and(new MyJpaSpecification<ImportExportRendicontazioneTesoreria>(
						new SearchCriteria("codIufKey", "eqOrNull", codIuf, false)));

		List<ImportExportRendicontazioneTesoreria> importExportRendicontazioneTesoreriaList = importExportRendicontazioneTesoreriaDao
				.findAll(specifications);

		if(importExportRendicontazioneTesoreriaList != null && CollectionUtils.isNotEmpty(importExportRendicontazioneTesoreriaList)) {
			ImportExportRendicontazioneTesoreria importExportRendicontazioneTesoreria = importExportRendicontazioneTesoreriaList.get(0);
			ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO = modelMapperUtil
					.map(importExportRendicontazioneTesoreria, ImportExportRendicontazioneTesoreriaTO.class);

			return importExportRendicontazioneTesoreriaTO;
		}
		return null;
	}

	private boolean isValidWhereCondition(String errorCode, String fieldName) {
		if (errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF_TES)) {
			return true;
		}
		return filtersMap.get(errorCode).contains(fieldName);
	}
}
