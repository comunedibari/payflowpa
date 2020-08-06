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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.RendicontazioneTesoreriaSubsetFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.RendicontazioneTesoreriaSubsetDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.RendicontazioneTesoreriaSubset;
import it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.RendicontazioneTesoreriaSubsetService;
import it.regioneveneto.mygov.payment.mypivot.service.SegnalazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

@Service("rendicontazioneTesoreriaSubsetService")
public class RendicontazioneTesoreriaSubsetServiceImpl implements RendicontazioneTesoreriaSubsetService {

	@Autowired
	private RendicontazioneTesoreriaSubsetFunctionDao rendicontazioneTesoreriaSubsetFunctionDao;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Resource
	private Environment env;

	@Autowired
	private SegnalazioneService segnalazioneService;

	private static final Logger LOG = Logger.getLogger(RendicontazioneTesoreriaSubsetServiceImpl.class);

	private static final Map<String, List<String>> filtersMap = new HashMap<String, List<String>>() {
		{
			put(Constants.COD_ERRORE_IUF_TES_DIV_IMP,
					Arrays.asList("identificativo_univoco_regolamento", "data_regolamento_check",
							"dt_data_regolamento_da", "dt_data_regolamento_a", "identificativo_flusso_rendicontazione",
							"codOr1", "importo", "data_contabile_check", "dt_data_contabile_da", "dt_data_contabile_a",
							"data_valuta_check", "dt_data_valuta_da", "dt_data_valuta_a", "data_ultimo_aggiornamento_check",
							"dt_data_ultimo_aggiornamento_da", "dt_data_ultimo_aggiornamento_a", "codConto",
							"causale_versamento"));

		}
	};

	@Override
	public PageDto<RendicontazioneTesoreriaSubsetDto> getRendicontazioneTesoreriaSubsetPageFunction(String codIpaEnte,
			String identificativoUnivocoFlusso, String identificativoUnivocoRegolamento, Date dt_data_regolamento_da,
			Date dt_data_regolamento_a, Date dt_data_contabile_da, Date dt_data_contabile_a, Date dt_data_valuta_da,
			Date dt_data_valuta_a, Date dt_data_ultimo_aggiornamento_da, Date dt_data_ultimo_aggiornamento_a,
			String causaleTesoreria, String importo, String conto, String codOr1, String codTipoDovuto,
			String codFedUserId, Boolean visualizzaNascosti, String classificazioneCompletezza, int page,
			int pageSize) {

		LOG.info("Chiamata al metodo service getRendicontazioneTesoreriaSubsetPageFunction");

		//Boolean isCodTipoDovutoPresent = (StringUtils.isNotBlank(codTipoDovuto)) ? true : false;
		Boolean isCodTipoDovutoPresent = false;

		List<RendicontazioneTesoreriaSubset> rendicontazioneTesoreriaList = rendicontazioneTesoreriaSubsetFunctionDao
				.getRendicontazioneTesoreriaPageListFunction(codIpaEnte,
						isValidWhereCondition(classificazioneCompletezza, "identificativo_flusso_rendicontazione")
								? identificativoUnivocoFlusso : null,
						isValidWhereCondition(classificazioneCompletezza, "identificativo_univoco_regolamento")
								? identificativoUnivocoRegolamento : null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_regolamento_da")
								? dt_data_regolamento_da : null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_regolamento_a")
								? dt_data_regolamento_a : null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_contabile_da") ? dt_data_contabile_da
								: null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_contabile_a") ? dt_data_contabile_a
								: null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_valuta_da") ? dt_data_valuta_da
								: null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_valuta_a") ? dt_data_valuta_a : null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_ultimo_aggiornamento_da")
								? dt_data_ultimo_aggiornamento_da : null,
						isValidWhereCondition(classificazioneCompletezza, "dt_data_ultimo_aggiornamento_a")
								? dt_data_ultimo_aggiornamento_a : null,
						isValidWhereCondition(classificazioneCompletezza, "causale_versamento") ? causaleTesoreria
								: null,
						isValidWhereCondition(classificazioneCompletezza, "importo") ? importo : null,
						isValidWhereCondition(classificazioneCompletezza, "codConto") ? conto : null,
						isValidWhereCondition(classificazioneCompletezza, "codOr1") ? codOr1 : null,
						null,
						isCodTipoDovutoPresent, codFedUserId, visualizzaNascosti, classificazioneCompletezza, page,
						pageSize);

		int numberResult = rendicontazioneTesoreriaSubsetFunctionDao.getCountRendicontazioneTesoreriaPageListFunction(
				codIpaEnte,
				isValidWhereCondition(classificazioneCompletezza, "identificativo_flusso_rendicontazione")
						? identificativoUnivocoFlusso : null,
				isValidWhereCondition(classificazioneCompletezza, "identificativo_univoco_regolamento")
						? identificativoUnivocoRegolamento : null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_regolamento_da") ? dt_data_regolamento_da
						: null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_regolamento_a") ? dt_data_regolamento_a
						: null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_contabile_da") ? dt_data_contabile_da : null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_contabile_a") ? dt_data_contabile_a : null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_valuta_da") ? dt_data_valuta_da : null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_valuta_a") ? dt_data_valuta_a : null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_ultimo_aggiornamento_da")
						? dt_data_ultimo_aggiornamento_da : null,
				isValidWhereCondition(classificazioneCompletezza, "dt_data_ultimo_aggiornamento_a")
						? dt_data_ultimo_aggiornamento_a : null,
				isValidWhereCondition(classificazioneCompletezza, "causale_versamento") ? causaleTesoreria : null,
				isValidWhereCondition(classificazioneCompletezza, "importo") ? importo : null,
				isValidWhereCondition(classificazioneCompletezza, "codConto") ? conto : null,
				isValidWhereCondition(classificazioneCompletezza, "codOr1") ? codOr1 : null,
				null,
				isCodTipoDovutoPresent, codFedUserId, visualizzaNascosti, classificazioneCompletezza);

		Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni = null;
		if (CollectionUtils.isNotEmpty(rendicontazioneTesoreriaList)) {
			List<SegnalazioneKeyDto> keyList = new ArrayList<>();
			for (RendicontazioneTesoreriaSubset rendicontazioneTesoreriaSubset : rendicontazioneTesoreriaList) {
				String codIufKey = rendicontazioneTesoreriaSubset.getCodIufKey();
				String codIpaEnteSubset = rendicontazioneTesoreriaSubset.getId() != null
						? rendicontazioneTesoreriaSubset.getId().getCodiceIpaEnte() : null;
				SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnteSubset,
						rendicontazioneTesoreriaSubset.getClassificazioneCompletezza(), codIufKey, null, null);

				keyList.add(key);
			}
			segnalazioni = segnalazioneService.findAttiveByKeys(keyList);
		}

		List<RendicontazioneTesoreriaSubsetDto> rendicontazioneTesoreriaSubsetDtos = mapEntitiesListToDtosList(
				rendicontazioneTesoreriaList, segnalazioni);

		PageDto<RendicontazioneTesoreriaSubsetDto> rendicontazioneTesoreriaSubsetDtoPage = new PageDto<RendicontazioneTesoreriaSubsetDto>();
		rendicontazioneTesoreriaSubsetDtoPage.setList(rendicontazioneTesoreriaSubsetDtos);

		boolean hasNextPage = numberResult - (pageSize * page) > 0 ? true : false;
		rendicontazioneTesoreriaSubsetDtoPage.setNextPage(hasNextPage);
		rendicontazioneTesoreriaSubsetDtoPage.setPage(page);
		rendicontazioneTesoreriaSubsetDtoPage.setPageSize(pageSize);
		boolean hasPreviousPage = page > 1 ? true : false;
		rendicontazioneTesoreriaSubsetDtoPage.setPreviousPage(hasPreviousPage);
		Double totalPages = new Double((numberResult / pageSize)) + (numberResult % pageSize == 0 ? 0 : 1);
		rendicontazioneTesoreriaSubsetDtoPage.setTotalPages(totalPages.intValue());
		rendicontazioneTesoreriaSubsetDtoPage.setTotalRecords(numberResult);

		return rendicontazioneTesoreriaSubsetDtoPage;
	}

	private List<RendicontazioneTesoreriaSubsetDto> mapEntitiesListToDtosList(
			List<RendicontazioneTesoreriaSubset> entities, Map<SegnalazioneKeyDto, SegnalazioneTO> segnalazioni) {

		List<RendicontazioneTesoreriaSubsetDto> dtos = new ArrayList<RendicontazioneTesoreriaSubsetDto>();
		for (RendicontazioneTesoreriaSubset rendicontazioneTesoreriaSubset : entities) {

			RendicontazioneTesoreriaSubsetDto rendicontazioneTesoreriaSubsetDto = new RendicontazioneTesoreriaSubsetDto();

			String codIuvKey = null;
			String codIufKey = rendicontazioneTesoreriaSubset.getCodIufKey();
			String codIudKey = null;
			String codIpaEnte = rendicontazioneTesoreriaSubset.getId() != null
					? rendicontazioneTesoreriaSubset.getId().getCodiceIpaEnte() : null;
			SegnalazioneKeyDto key = new SegnalazioneKeyDto(codIpaEnte,
					rendicontazioneTesoreriaSubset.getClassificazioneCompletezza(), codIufKey, codIudKey, codIuvKey);

			if (segnalazioni.containsKey(key)) {
				rendicontazioneTesoreriaSubsetDto.setSegnalazione(segnalazioni.get(key));
			}

			RendicontazioneTesoreriaSubsetTO rendicontazioneTesoreriaSubsetTO = modelMapperUtil
					.map(rendicontazioneTesoreriaSubset, RendicontazioneTesoreriaSubsetTO.class);

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeImporto())) {
				rendicontazioneTesoreriaSubset.setDeImporto("n/a");
			} else if (!rendicontazioneTesoreriaSubset.getDeImporto().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String deImporto = format.format(new BigDecimal(rendicontazioneTesoreriaSubsetTO.getDeImporto()));
				rendicontazioneTesoreriaSubsetTO.setDeImporto("€ " + deImporto);
			}

			if (StringUtils.isNotBlank(rendicontazioneTesoreriaSubset.getId().getIdentificativoFlussoRendicontazione())
					&& !rendicontazioneTesoreriaSubset.getId().getIdentificativoFlussoRendicontazione().equals("n/a")) {
				rendicontazioneTesoreriaSubsetDto.setIdentificativoFlussoRendicontazione(
						rendicontazioneTesoreriaSubset.getId().getIdentificativoFlussoRendicontazione());
			}

			rendicontazioneTesoreriaSubsetDto
					.setCodiceIpaEnte(rendicontazioneTesoreriaSubset.getId().getCodiceIpaEnte());

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getClassificazioneCompletezza())) {
				rendicontazioneTesoreriaSubsetTO.setClassificazioneCompletezza("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodBolletta())) {
				rendicontazioneTesoreriaSubsetTO.setCodBolletta("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodConto())) {
				rendicontazioneTesoreriaSubsetTO.setCodConto("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodDocumento())) {
				rendicontazioneTesoreriaSubsetTO.setCodDocumento("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodIdDominio())) {
				rendicontazioneTesoreriaSubsetTO.setCodIdDominio("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodIufKey())) {
				rendicontazioneTesoreriaSubsetTO.setCodIufKey("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodOr1())) {
				rendicontazioneTesoreriaSubsetTO.setCodOr1("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getCodProvvisorio())) {
				rendicontazioneTesoreriaSubsetTO.setCodProvvisorio("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDataOraFlussoRendicontazione())) {
				rendicontazioneTesoreriaSubsetTO.setDataOraFlussoRendicontazione("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeAnnoBolletta())) {
				rendicontazioneTesoreriaSubsetTO.setDeAnnoBolletta("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeAnnoDocumento())) {
				rendicontazioneTesoreriaSubsetTO.setDeAnnoDocumento("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeAnnoProvvisorio())) {
				rendicontazioneTesoreriaSubsetTO.setDeAnnoProvvisorio("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeCausaleT())) {
				rendicontazioneTesoreriaSubsetTO.setDeCausaleT("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeDataContabile())) {
				rendicontazioneTesoreriaSubsetTO.setDeDataContabile("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeDataEsecuzionePagamento())) {
				rendicontazioneTesoreriaSubsetTO.setDeDataEsecuzionePagamento("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeDataRegolamento())) {
				rendicontazioneTesoreriaSubsetTO.setDeDataRegolamento("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeDataRicezione())) {
				rendicontazioneTesoreriaSubsetTO.setDeDataRicezione("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeDataUltimoAggiornamento())) {
				rendicontazioneTesoreriaSubsetTO.setDeDataUltimoAggiornamento("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getDeDataValuta())) {
				rendicontazioneTesoreriaSubsetTO.setDeDataValuta("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getIdentificativoUnivocoRegolamento())) {
				rendicontazioneTesoreriaSubsetTO.setIdentificativoUnivocoRegolamento("n/a");
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getImportoTotalePagamenti())) {
				rendicontazioneTesoreriaSubset.setImportoTotalePagamenti("n/a");
			} else if (!rendicontazioneTesoreriaSubset.getImportoTotalePagamenti().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String importoTotalePagamenti = format
						.format(new BigDecimal(rendicontazioneTesoreriaSubsetTO.getImportoTotalePagamenti()));
				rendicontazioneTesoreriaSubsetTO.setImportoTotalePagamenti("€ " + importoTotalePagamenti);
			}

			if (StringUtils.isBlank(rendicontazioneTesoreriaSubset.getSingoloImportoCommissioneCaricoPa())) {
				rendicontazioneTesoreriaSubsetTO.setSingoloImportoCommissioneCaricoPa("n/a");
			}

			rendicontazioneTesoreriaSubsetDto.setRendicontazioneTesoreriaSubsetTO(rendicontazioneTesoreriaSubsetTO);
			dtos.add(rendicontazioneTesoreriaSubsetDto);
		}
		return dtos;
	}

	private boolean isValidWhereCondition(String errorCode, String fieldName) {
		if (errorCode.equals(Constants.COD_ERRORE_IUD_RT_IUF_TES)) {
			return true;
		}
		return filtersMap.get(errorCode).contains(fieldName);
	}
}
