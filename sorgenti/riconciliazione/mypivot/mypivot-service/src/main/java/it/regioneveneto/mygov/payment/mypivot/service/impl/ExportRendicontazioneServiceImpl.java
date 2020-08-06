package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.EnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.ExportRendicontazioneDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.ExportCSVDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.VisualizzaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ExportRendicontazione;
import it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.ExportRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Service
public class ExportRendicontazioneServiceImpl implements ExportRendicontazioneService {

	@Autowired
	private ExportRendicontazioneDao exportRendicontazioneDao;

	@Autowired
	private EnteTipoDovutoDao enteTipoDovutoDao;

	@Resource
	private Environment env;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Override
	public PageDto<VisualizzaDto> getExportRendicontazionePage(String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date data_regolamento_da, Date data_regolamento_a, String cod_tipo_dovuto, int page, int size, Sort sort) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, size, sort);

		Specifications<ExportRendicontazione> specification = getSpecifications(cod_ipa_ente, codice_iud, codice_iuv, denominazione_attestante,
				identificativo_univoco_riscossione, codice_identificativo_univoco_versante, anagrafica_versante, codice_identificativo_univoco_pagatore,
				anagrafica_pagatore, causale_versamento, data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a,
				identificativo_flusso_rendicontazione, identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, cod_tipo_dovuto);

		Page<ExportRendicontazione> exportRendicontazionePage = null;
		if (specification != null) {
			exportRendicontazionePage = exportRendicontazioneDao.findAll(specification, pageable);
		} else {
			exportRendicontazionePage = exportRendicontazioneDao.findAll(pageable);
		}

		List<VisualizzaDto> visualizzaDtos = mapEntitiesListToDtosList(exportRendicontazionePage.getContent());

		PageDto<VisualizzaDto> visualizzaDtoPage = new PageDto<VisualizzaDto>();
		visualizzaDtoPage.setList(visualizzaDtos);

		visualizzaDtoPage.setNextPage(exportRendicontazionePage.hasNextPage());
		visualizzaDtoPage.setPage(exportRendicontazionePage.getNumber() + 1);
		visualizzaDtoPage.setPageSize(exportRendicontazionePage.getSize());
		visualizzaDtoPage.setPreviousPage(exportRendicontazionePage.hasPreviousPage());
		visualizzaDtoPage.setTotalPages(exportRendicontazionePage.getTotalPages());
		visualizzaDtoPage.setTotalRecords(exportRendicontazionePage.getTotalElements());

		return visualizzaDtoPage;

	}

	@Override
	public List<ExportCSVDto> getExportRendicontazione(String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date data_regolamento_da, Date data_regolamento_a, String cod_tipo_dovuto, Sort sort) {

		Specifications<ExportRendicontazione> specification = getSpecifications(cod_ipa_ente, codice_iud, codice_iuv, denominazione_attestante,
				identificativo_univoco_riscossione, codice_identificativo_univoco_versante, anagrafica_versante, codice_identificativo_univoco_pagatore,
				anagrafica_pagatore, causale_versamento, data_esito_singolo_pagamento_da, data_esito_singolo_pagamento_a,
				identificativo_flusso_rendicontazione, identificativo_univoco_regolamento, data_regolamento_da, data_regolamento_a, cod_tipo_dovuto);

		List<ExportRendicontazione> exportRendicontazioneList = new ArrayList<ExportRendicontazione>();
		if (specification != null) {
			exportRendicontazioneList = exportRendicontazioneDao.findAll(specification, sort);
		} else {
			exportRendicontazioneList = exportRendicontazioneDao.findAll(sort);
		}

		List<ExportCSVDto> exportCSVDtoDtos = mapEntitiesListToExportCSVDtosList(exportRendicontazioneList);

		return exportCSVDtoDtos;
	}

	private Specifications<ExportRendicontazione> getSpecifications(String cod_ipa_ente, String codice_iud, String codice_iuv, String denominazione_attestante,
			String identificativo_univoco_riscossione, String codice_identificativo_univoco_versante, String anagrafica_versante,
			String codice_identificativo_univoco_pagatore, String anagrafica_pagatore, String causale_versamento, Date data_esito_singolo_pagamento_da,
			Date data_esito_singolo_pagamento_a, String identificativo_flusso_rendicontazione, String identificativo_univoco_regolamento,
			Date data_regolamento_da, Date data_regolamento_a, String cod_tipo_dovuto) {

		Specifications<ExportRendicontazione> specifications = null;

		if (StringUtils.isNotBlank(cod_ipa_ente)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria("codiceIpaEnte",
					"=", cod_ipa_ente.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codice_iud)) {

			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria("codiceIud", "=",
					codice_iud.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codice_iuv)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria("codiceIuv", "=",
					codice_iuv.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(denominazione_attestante)) {
			MyJpaSpecification<ExportRendicontazione> denominazioneAttestanteSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"denominazioneAttestante", "like", denominazione_attestante.trim(), false));
			MyJpaSpecification<ExportRendicontazione> codiceIdentificativoUnivocoAttestanteSpecification = new MyJpaSpecification<ExportRendicontazione>(
					new SearchCriteria("codiceIdentificativoUnivocoAttestante", "like", denominazione_attestante.trim(), false));

			Specifications<ExportRendicontazione> denominazioneAttestante_OR_identificativoAttestanteSpecifications = Specifications.where(
					denominazioneAttestanteSpecification).or(codiceIdentificativoUnivocoAttestanteSpecification);

			specifications = specifications == null ? denominazioneAttestante_OR_identificativoAttestanteSpecifications : specifications
					.and(denominazioneAttestante_OR_identificativoAttestanteSpecifications);
		}

		if (StringUtils.isNotBlank(identificativo_univoco_riscossione)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"identificativoUnivocoRiscossione", "=", identificativo_univoco_riscossione.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codice_identificativo_univoco_versante)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"codiceIdentificativoUnivocoVersante", "=", codice_identificativo_univoco_versante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(anagrafica_versante)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"anagraficaVersante", "like", anagrafica_versante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codice_identificativo_univoco_pagatore)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"codiceIdentificativoUnivocoPagatore", "=", codice_identificativo_univoco_pagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(anagrafica_pagatore)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"anagraficaPagatore", "like", anagrafica_pagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(causale_versamento)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"causaleVersamento", "like", causale_versamento.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (data_esito_singolo_pagamento_da != null) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"dtDataEsitoSingoloPagamento", ">", data_esito_singolo_pagamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (data_esito_singolo_pagamento_a != null) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"dtDataEsitoSingoloPagamento", "<", data_esito_singolo_pagamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(identificativo_flusso_rendicontazione)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"identificativoFlussoRendicontazione", "=", identificativo_flusso_rendicontazione.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(identificativo_univoco_regolamento)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"identificativoUnivocoRegolamento", "=", identificativo_univoco_regolamento.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (data_regolamento_da != null) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"dtDataRegolamento", ">", data_regolamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}
		if (data_regolamento_a != null) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria(
					"dtDataRegolamento", "<", data_regolamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(cod_tipo_dovuto)) {
			MyJpaSpecification<ExportRendicontazione> myJpaSpecification = new MyJpaSpecification<ExportRendicontazione>(new SearchCriteria("tipoDovuto", "=",
					cod_tipo_dovuto.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification) : specifications.and(myJpaSpecification);
		}

		return specifications;

	}

	/**
	 * @param entities
	 * @return
	 */
	private List<VisualizzaDto> mapEntitiesListToDtosList(List<ExportRendicontazione> entities) {
		List<VisualizzaDto> dtos = new ArrayList<VisualizzaDto>();
		for (ExportRendicontazione exportRendicontazione : entities) {
			VisualizzaDto visualizzaDto = new VisualizzaDto();

			ExportRendicontazioneTO exportRendicontazioneTO = modelMapperUtil.map(exportRendicontazione,
					ExportRendicontazioneTO.class);

			if (StringUtils.isBlank(exportRendicontazione.getSingoloImportoPagato())) {
				exportRendicontazioneTO.setSingoloImportoPagato("n/a");
			} else if (!exportRendicontazione.getSingoloImportoPagato().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String singoloImportoPagato = format.format(new BigDecimal(exportRendicontazioneTO.getSingoloImportoPagato()));
				exportRendicontazioneTO.setSingoloImportoPagato("€ " + singoloImportoPagato);
			}
			if (StringUtils.isBlank(exportRendicontazione.getImportoTotalePagamenti())) {
				exportRendicontazioneTO.setImportoTotalePagamenti("n/a");
			} else if (!exportRendicontazione.getImportoTotalePagamenti().equals("n/a")) {
				NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
				String importoTotalePagamenti = format.format(new BigDecimal(exportRendicontazioneTO.getImportoTotalePagamenti()));
				exportRendicontazioneTO.setImportoTotalePagamenti("€ " + importoTotalePagamenti);
			}

			visualizzaDto.setExportRendicontazioneTO(exportRendicontazioneTO);
			if (StringUtils.isNotBlank(exportRendicontazioneTO.getEsitoSingoloPagamento())) {
				visualizzaDto.setEsitoSingoloPagamento(exportRendicontazioneTO.getEsitoSingoloPagamento());
			} else {
				visualizzaDto.setEsitoSingoloPagamento("-");
			}

			visualizzaDto.setCodiceIpaEnte(exportRendicontazione.getId().getCodiceIpaEnte());
			visualizzaDto.setCodiceIuv(exportRendicontazione.getId().getCodiceIuv());
			visualizzaDto.setIdentificativoUnivocoRiscossione(exportRendicontazione.getId().getIdentificativoUnivocoRiscossione());

			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(exportRendicontazione.getId().getCodiceIpaEnte(),
					exportRendicontazione.getTipoDovuto());
			if (enteTipoDovuto == null) {
				exportRendicontazioneTO.setTipoDovuto(exportRendicontazione.getTipoDovuto());
			} else {
				exportRendicontazioneTO.setTipoDovuto(enteTipoDovuto.getDeTipo());
			}

			dtos.add(visualizzaDto);
		}
		return dtos;
	}

	private List<ExportCSVDto> mapEntitiesListToExportCSVDtosList(List<ExportRendicontazione> entities) {
		List<ExportCSVDto> dtos = new ArrayList<ExportCSVDto>();
		for (ExportRendicontazione exportRendicontazione : entities) {
			ExportCSVDto exportCSVDto = getExportCSVDTO(exportRendicontazione);

			if (exportRendicontazione.getDataOraFlussoRendicontazione() == null) {
				exportCSVDto.setDataOraFlussoRendicontazione("n/a");
			} else {
				exportCSVDto.setDataOraFlussoRendicontazione(exportRendicontazione.getDataOraFlussoRendicontazione());
			}

			if (exportRendicontazione.getDtDataEsitoSingoloPagamento() == null) {
				exportCSVDto.setDataEsitoSingoloPagamento("n/a");
			} else {
				exportCSVDto.setDataEsitoSingoloPagamento(Utilities.formatDate(exportRendicontazione.getDtDataEsitoSingoloPagamento(),
						Constants.DDMMYYYY_PATTERN));
			}

			if (exportRendicontazione.getDtDataRegolamento() == null) {
				exportCSVDto.setDataRegolamento("n/a");
			} else {
				exportCSVDto.setDataRegolamento(Utilities.formatDate(exportRendicontazione.getDtDataRegolamento(), Constants.DDMMYYYY_PATTERN));
			}

			if (StringUtils.isNotBlank(exportRendicontazione.getEsitoSingoloPagamento())) {
				exportCSVDto.setEsitoSingoloPagamento(exportRendicontazione.getEsitoSingoloPagamento());
			} else {
				exportCSVDto.setEsitoSingoloPagamento("-");
			}

			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(exportRendicontazione.getId().getCodiceIpaEnte(),
					exportRendicontazione.getTipoDovuto());
			if (enteTipoDovuto == null) {
				exportCSVDto.setTipoDovuto(exportRendicontazione.getTipoDovuto());
			} else {
				exportCSVDto.setTipoDovuto(enteTipoDovuto.getDeTipo());
			}
			dtos.add(exportCSVDto);
		}
		return dtos;
	}

	private ExportCSVDto getExportCSVDTO(ExportRendicontazione exportRendicontazione) {

		ExportCSVDto exportCSVDto = new ExportCSVDto();

		exportCSVDto.setCodiceIdentificativoUnivocoAttestante(exportRendicontazione.getCodiceIdentificativoUnivocoAttestante());
		exportCSVDto.setIdentificativoUnivocoRiscossione(exportRendicontazione.getId().getIdentificativoUnivocoRiscossione());
		exportCSVDto.setCodiceIpaEnte(exportRendicontazione.getId().getCodiceIpaEnte());
		exportCSVDto.setNomeFlussoImportEnte(exportRendicontazione.getNomeFlussoImportEnte());
		exportCSVDto.setRigaFlussoImportEnte(exportRendicontazione.getRigaFlussoImportEnte());
		exportCSVDto.setCodiceIud(exportRendicontazione.getCodiceIud());
		exportCSVDto.setCodiceIuv(exportRendicontazione.getId().getCodiceIuv());
		exportCSVDto.setVersioneOggetto(exportRendicontazione.getVersioneOggetto());
		exportCSVDto.setIdentificativoDominio(exportRendicontazione.getIdentificativoDominio());
		exportCSVDto.setIdentificativoStazioneRichiedente(exportRendicontazione.getIdentificativoStazioneRichiedente());
		exportCSVDto.setIdentificativoMessaggioRicevuta(exportRendicontazione.getIdentificativoMessaggioRicevuta());
		exportCSVDto.setDataOraMessaggioRicevuta(exportRendicontazione.getDataOraMessaggioRicevuta());
		exportCSVDto.setRiferimentoMessaggioRichiesta(exportRendicontazione.getRiferimentoMessaggioRichiesta());
		exportCSVDto.setRiferimentoDataRichiesta(exportRendicontazione.getRiferimentoDataRichiesta());
		exportCSVDto.setTipoIdentificativoUnivocoAttestante(exportRendicontazione.getTipoIdentificativoUnivocoAttestante());
		exportCSVDto.setDenominazioneAttestante(exportRendicontazione.getDenominazioneAttestante());
		exportCSVDto.setCodiceUnitOperAttestante(exportRendicontazione.getCodiceUnitOperAttestante());
		exportCSVDto.setDenomUnitOperAttestante(exportRendicontazione.getDenomUnitOperAttestante());
		exportCSVDto.setIndirizzoAttestante(exportRendicontazione.getIndirizzoAttestante());
		exportCSVDto.setCivicoAttestante(exportRendicontazione.getCivicoAttestante());
		exportCSVDto.setCapAttestante(exportRendicontazione.getCapAttestante());
		exportCSVDto.setLocalitaAttestante(exportRendicontazione.getLocalitaAttestante());
		exportCSVDto.setProvinciaAttestante(exportRendicontazione.getProvinciaAttestante());
		exportCSVDto.setNazioneAttestante(exportRendicontazione.getNazioneAttestante());
		exportCSVDto.setTipoIdentificativoUnivocoBeneficiario(exportRendicontazione.getTipoIdentificativoUnivocoBeneficiario());
		exportCSVDto.setCodiceIdentificativoUnivocoBeneficiario(exportRendicontazione.getCodiceIdentificativoUnivocoBeneficiario());
		exportCSVDto.setDenominazioneBeneficiario(exportRendicontazione.getDenominazioneBeneficiario());
		exportCSVDto.setCodiceUnitOperBeneficiario(exportRendicontazione.getCodiceUnitOperBeneficiario());
		exportCSVDto.setDenomUnitOperBeneficiario(exportRendicontazione.getDenomUnitOperBeneficiario());
		exportCSVDto.setIndirizzoBeneficiario(exportRendicontazione.getIndirizzoBeneficiario());
		exportCSVDto.setCivicoBeneficiario(exportRendicontazione.getCivicoBeneficiario());
		exportCSVDto.setCapBeneficiario(exportRendicontazione.getCapBeneficiario());
		exportCSVDto.setLocalitaBeneficiario(exportRendicontazione.getLocalitaBeneficiario());
		exportCSVDto.setProvinciaBeneficiario(exportRendicontazione.getProvinciaBeneficiario());
		exportCSVDto.setNazioneBeneficiario(exportRendicontazione.getNazioneBeneficiario());
		exportCSVDto.setTipoIdentificativoUnivocoVersante(exportRendicontazione.getTipoIdentificativoUnivocoVersante());
		exportCSVDto.setCodiceIdentificativoUnivocoVersante(exportRendicontazione.getCodiceIdentificativoUnivocoVersante());
		exportCSVDto.setAnagraficaVersante(exportRendicontazione.getAnagraficaVersante());
		exportCSVDto.setIndirizzoVersante(exportRendicontazione.getIndirizzoVersante());
		exportCSVDto.setCivicoVersante(exportRendicontazione.getCivicoVersante());
		exportCSVDto.setCapVersante(exportRendicontazione.getCapVersante());
		exportCSVDto.setLocalitaVersante(exportRendicontazione.getLocalitaVersante());
		exportCSVDto.setProvinciaVersante(exportRendicontazione.getProvinciaVersante());
		exportCSVDto.setNazioneVersante(exportRendicontazione.getNazioneVersante());
		exportCSVDto.setEmailVersante(exportRendicontazione.getEmailVersante());
		exportCSVDto.setTipoIdentificativoUnivocoPagatore(exportRendicontazione.getTipoIdentificativoUnivocoPagatore());
		exportCSVDto.setCodiceIdentificativoUnivocoPagatore(exportRendicontazione.getCodiceIdentificativoUnivocoPagatore());
		exportCSVDto.setAnagraficaPagatore(exportRendicontazione.getAnagraficaPagatore());
		exportCSVDto.setIndirizzoPagatore(exportRendicontazione.getIndirizzoPagatore());
		exportCSVDto.setCivicoPagatore(exportRendicontazione.getCivicoPagatore());
		exportCSVDto.setCapPagatore(exportRendicontazione.getCapPagatore());
		exportCSVDto.setLocalitaPagatore(exportRendicontazione.getLocalitaPagatore());
		exportCSVDto.setProvinciaPagatore(exportRendicontazione.getProvinciaPagatore());
		exportCSVDto.setNazionePagatore(exportRendicontazione.getNazionePagatore());
		exportCSVDto.setEmailPagatore(exportRendicontazione.getEmailPagatore());
		exportCSVDto.setCodiceEsitoPagamento(exportRendicontazione.getCodiceEsitoPagamento());
		exportCSVDto.setImportoTotalePagato(exportRendicontazione.getImportoTotalePagato());
		exportCSVDto.setIdentificativoUnivocoVersamento(exportRendicontazione.getIdentificativoUnivocoVersamento());
		exportCSVDto.setCodiceContestoPagamento(exportRendicontazione.getCodiceContestoPagamento());
		exportCSVDto.setSingoloImportoPagato(exportRendicontazione.getSingoloImportoPagato());
		exportCSVDto.setEsitoSingoloPagamento(exportRendicontazione.getEsitoSingoloPagamento());
		exportCSVDto.setDataEsitoSingoloPagamento(exportRendicontazione.getDeDataEsitoSingoloPagamento());
		exportCSVDto.setCausaleVersamento(exportRendicontazione.getCausaleVersamento());
		exportCSVDto.setDatiSpecificiRiscossione(exportRendicontazione.getDatiSpecificiRiscossione());
		exportCSVDto.setTipoDovuto(exportRendicontazione.getTipoDovuto());
		exportCSVDto.setIdentificativoFlussoRendicontazione(exportRendicontazione.getIdentificativoFlussoRendicontazione());
		exportCSVDto.setDataOraFlussoRendicontazione(exportRendicontazione.getDataOraFlussoRendicontazione());
		exportCSVDto.setIdentificativoUnivocoRegolamento(exportRendicontazione.getIdentificativoUnivocoRegolamento());
		exportCSVDto.setDataRegolamento(exportRendicontazione.getDeDataRegolamento());
		exportCSVDto.setNumeroTotalePagamenti(exportRendicontazione.getNumeroTotalePagamenti());
		exportCSVDto.setImportoTotalePagamenti(exportRendicontazione.getImportoTotalePagamenti());
		exportCSVDto.setDataAcquisizione(exportRendicontazione.getDataAcquisizione());

		return exportCSVDto;
	}

}
