package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.EnteTipoDovutoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.FlussoExportDao;
import it.regioneveneto.mygov.payment.mypivot.dao.FlussoRendicontazioneDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.EnteTipoDovuto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExportId;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoRendicontazione;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.RtNonInRendicontazioneException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Service
public class FlussoExportServiceImpl implements FlussoExportService {
	private static final Logger LOG = Logger.getLogger(FlussoExportServiceImpl.class);

	@Autowired
	private FlussoExportDao flussoExportDao;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private FlussoRendicontazioneDao flussoRendicontazioneDao;

	@Autowired
	private EnteTipoDovutoDao enteTipoDovutoDao;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Override
	public List<FlussoExportTO> findAllByIdSet(Iterable<FlussoExportId> flussoExportIds) {
		LOG.debug("finding all flussoExport by id set - begin");
		List<FlussoExport> flussoExportList = flussoExportDao.findAllById(flussoExportIds);
		List<FlussoExportTO> flussoExportToList = null;
		if (CollectionUtils.isNotEmpty(flussoExportList)) {
			LOG.debug("flussoExport trovati [" + flussoExportList.size() + "]");
			flussoExportToList = new ArrayList<>();
			for (FlussoExport flussoExport : flussoExportList) {
				FlussoExportTO flussoExportTO = modelMapperUtil.map(flussoExport, FlussoExportTO.class);
				flussoExportToList.add(flussoExportTO);
			}
		}
		LOG.debug("finding all flussoExport by id set - end");
		return flussoExportToList;
	}

	@Override
	public FlussoExportTO findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(final String codIpaEnte, final String iuv,
			final int indiceDatiSingoloPagamento) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuv, "Parametro [ iuv ] vuoto");
		if (indiceDatiSingoloPagamento < 1)
			throw new IllegalArgumentException(
					"Parametro indiceDatiSingoloPagamento [ " + indiceDatiSingoloPagamento + " ] non valido");

//		LOG.debug("Chiamata al servizio findByCodIpaEnteAndIUF con i parametri codIpaEnte [ " + codIpaEnte
//				+ " ], iuv [ " + iuv + " ] e indiceDatiSingoloPagamento [ " + indiceDatiSingoloPagamento + " ]");

//		modifica dovuta alla possibile presenza di diversi flussi di export
//		(con codEDatiPagCodiceEsitoPagamento 9 o 0)
		List<FlussoExport> flussoExportList = flussoExportDao.findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(codIpaEnte, iuv,
				indiceDatiSingoloPagamento);
		if (flussoExportList != null) {
			if (flussoExportList.size() == 1) {
				FlussoExportTO flussoExportTO = modelMapperUtil.map(flussoExportList.get(0), FlussoExportTO.class);
				return flussoExportTO;
			} else if (flussoExportList.size() > 1){
				for (FlussoExport flussoExport : flussoExportList){
					if (!flussoExport.getCodEDatiPagCodiceEsitoPagamento().toString().equals("9")){
						FlussoExportTO flussoExportTO = modelMapperUtil.map(flussoExport, FlussoExportTO.class);
						return flussoExportTO;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<FlussoExportTO> findByCodIpaEnteIUV(final String codIpaEnte, final String iuv) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuv, "Parametro [ iuv ] vuoto");

		List<FlussoExport> listaRT = flussoExportDao.findByCodIpaEnteIUV(codIpaEnte, iuv);
		if (listaRT != null && !listaRT.isEmpty()) {
			List<FlussoExportTO> listaFlussiExportTO = new ArrayList<FlussoExportTO>();
			for (FlussoExport rt : listaRT) {
				FlussoExportTO flussoExportTO = modelMapperUtil.map(rt, FlussoExportTO.class);
				listaFlussiExportTO.add(flussoExportTO);
			}
			return listaFlussiExportTO;
		}
		return null;
	}

	
	@Override
	public PageDto<FlussoRicevutaDto> getFlussoExportPage(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, int page,
			int pageSize, Sort sort) {
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoExport> flussoExportPage = getExportPageList(codIpaEnte, codFedUserId,
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a, iud, iuv,
				denominazioneAttestante, identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore,
				anagraficaPagatore, codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, pageable);

		List<FlussoExport> flussoExportList = flussoExportPage.getContent();

		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, flussoExportList);

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(flussoExportPage.hasNextPage());
		flussoRicevutaDtoPage.setPage(flussoExportPage.getNumber() + 1);
		flussoRicevutaDtoPage.setPageSize(flussoExportPage.getSize());
		flussoRicevutaDtoPage.setPreviousPage(flussoExportPage.hasPreviousPage());
		flussoRicevutaDtoPage.setTotalPages(flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(flussoExportPage.getTotalElements());

		return flussoRicevutaDtoPage;
	}
	
	
	@Override
	public PageDto<FlussoRicevutaDto> getFlussoExportPage(Pageable pageable, String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto) {

		Page<FlussoExport> flussoExportPage = getExportPageList(codIpaEnte, codFedUserId,
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a, iud, iuv,
				denominazioneAttestante, identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore,
				anagraficaPagatore, codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, pageable);

		List<FlussoExport> flussoExportList = flussoExportPage.getContent();

		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, flussoExportList);

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(flussoExportPage.getNumber() + 1 < flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setPage(flussoExportPage.getNumber() + 1);
		flussoRicevutaDtoPage.setPageSize(flussoExportPage.getSize());
		flussoRicevutaDtoPage.setPreviousPage(flussoExportPage.getNumber() > 0);		
		flussoRicevutaDtoPage.setTotalPages(flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(flussoExportPage.getTotalElements());

		return flussoRicevutaDtoPage;
	}
	
	/**
	 * @author Alessandro Paolillo
	 */
	@Override
	public PageDto<FlussoRicevutaDto> getFlussoExportPage(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, List<String> iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, int page,
			int pageSize, Sort sort) {
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoExport> flussoExportPage = getExportPageList(codIpaEnte, codFedUserId,
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a, iud, iuv,
				denominazioneAttestante, identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore,
				anagraficaPagatore, codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, pageable);

		List<FlussoExport> flussoExportList = flussoExportPage.getContent();

		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, flussoExportList);

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(flussoExportPage.hasNextPage());
		flussoRicevutaDtoPage.setPage(flussoExportPage.getNumber() + 1);
		flussoRicevutaDtoPage.setPageSize(flussoExportPage.getSize());
		flussoRicevutaDtoPage.setPreviousPage(flussoExportPage.hasPreviousPage());
		flussoRicevutaDtoPage.setTotalPages(flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(flussoExportPage.getTotalElements());

		return flussoRicevutaDtoPage;
	}

	
	@Override
	public PageDto<FlussoRicevutaDto> getFlussoExportPage(Pageable pageable, String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, List<String> iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto) {
		
		Page<FlussoExport> flussoExportPage = getExportPageList(codIpaEnte, codFedUserId,
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a, iud, iuv,
				denominazioneAttestante, identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore,
				anagraficaPagatore, codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, pageable);

		List<FlussoExport> flussoExportList = flussoExportPage.getContent();

		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, flussoExportList);

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(flussoExportPage.getNumber() + 1 < flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setPage(flussoExportPage.getNumber() + 1);
		flussoRicevutaDtoPage.setPageSize(flussoExportPage.getSize());
		flussoRicevutaDtoPage.setPreviousPage(flussoExportPage.getNumber() > 0);		
		flussoRicevutaDtoPage.setTotalPages(flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(flussoExportPage.getTotalElements());

		return flussoRicevutaDtoPage;
	}

	
	private Page<FlussoExport> getExportPageList(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto,
			Pageable pageable) {

		Specifications<FlussoExport> specification = getSpecifications(codIpaEnte, codFedUserId,
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a, iud, iuv,
				denominazioneAttestante, identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore,
				anagraficaPagatore, codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto);

		Page<FlussoExport> exportPage = null;
		if (specification != null) {
			exportPage = flussoExportDao.findAll(specification, pageable);
		} else {
			exportPage = flussoExportDao.findAll(pageable);
		}
		return exportPage;
	}

	private Specifications<FlussoExport> getSpecifications(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto) {

		Specifications<FlussoExport> specifications = null;

		if (StringUtils.isNotBlank(codIpaEnte)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codIpaEnte", "=", codIpaEnte.trim(), "ente"));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_esito_singolo_pagamento_da != null) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("dtEDatiPagDatiSingPagDataEsitoSingoloPagamento", ">=",
							dt_data_esito_singolo_pagamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_esito_singolo_pagamento_a != null) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("dtEDatiPagDatiSingPagDataEsitoSingoloPagamento", "<=",
							dt_data_esito_singolo_pagamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(iud)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codIud", "=", iud.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(iuv)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codRpSilinviarpIdUnivocoVersamento", "=", iuv.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(denominazioneAttestante)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("deEIstitAttDenominazioneAttestante", "like", denominazioneAttestante.trim(),
							false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(identificativoUnivocoRiscossione)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codEDatiPagDatiSingPagIdUnivocoRiscoss", "=",
							identificativoUnivocoRiscossione.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codiceIdentificativoUnivocoPagatore)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggPagIdUnivPagCodiceIdUnivoco", "=",
							codiceIdentificativoUnivocoPagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(anagraficaPagatore)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggPagAnagraficaPagatore", "like", anagraficaPagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codiceIdentificativoUnivocoVersante)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggVersIdUnivVersCodiceIdUnivoco", "=",
							codiceIdentificativoUnivocoVersante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(anagraficaVersante)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggVersAnagraficaVersante", "like", anagraficaVersante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isEmpty(codTipoDovuto)) {
			List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService
					.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codTipoDovuto", "in", listaCodTipoDovuto, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		} else {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codTipoDovuto", "=", codTipoDovuto.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		return specifications;
	}

	public List<FlussoRicevutaDto> mapPoInDto(String codIpaEnte, List<FlussoExport> flussoExportList) {
		List<FlussoRicevutaDto> flussoRicevutaDtoList = new ArrayList<FlussoRicevutaDto>();
		for (FlussoExport flussoExport : flussoExportList) {
			FlussoExportTO flussoExportTO = modelMapperUtil.map(flussoExport, FlussoExportTO.class);
			FlussoRicevutaDto dto = new FlussoRicevutaDto();

			dto.setCodiceIpaEnte(flussoExportTO.getEnte().getCodIpaEnte());
			dto.setDeImportoTotale(Utilities.parseImportoString(flussoExportTO.getNumEDatiPagImportoTotalePagato()));
			dto.setDeImportoSingolo(
					Utilities.parseImportoString(flussoExportTO.getNumEDatiPagDatiSingPagSingoloImportoPagato()));
			if (flussoExportTO.getCodESoggPagIdUnivPagTipoIdUnivoco() != null) {
				dto.setCodESoggPagIdUnivPagTipoIdUnivoco(
						flussoExportTO.getCodESoggPagIdUnivPagTipoIdUnivoco().toString());
			}

			if (flussoExportTO.getCodESoggVersIdUnivVersTipoIdUnivoco() != null) {
				dto.setCodESoggVersIdUnivVersTipoIdUnivoco(
						flussoExportTO.getCodESoggVersIdUnivVersTipoIdUnivoco().toString());
			}

			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(codIpaEnte,
					flussoExportTO.getCodTipoDovuto());
			if (enteTipoDovuto == null) {
				if (StringUtils.isBlank(flussoExportTO.getCodTipoDovuto())) {
					dto.setDeTipoDovuto("n/a"); 
				} else {
					dto.setDeTipoDovuto(flussoExportTO.getCodTipoDovuto());
				}

			} else {
				dto.setDeTipoDovuto(enteTipoDovuto.getDeTipo());
			}

			dto.setFlussoExportTO(flussoExportTO);
			flussoRicevutaDtoList.add(dto);
		}
		return flussoRicevutaDtoList;
	}

	public FlussoRicevutaDto mapPoInDto(String codIpaEnte, FlussoExport flussoExport) {
		FlussoRicevutaDto flussoRicevutaDto = new FlussoRicevutaDto();
			FlussoExportTO flussoExportTO = modelMapperUtil.map(flussoExport, FlussoExportTO.class);
			FlussoRicevutaDto dto = new FlussoRicevutaDto();

			dto.setCodiceIpaEnte(flussoExportTO.getEnte().getCodIpaEnte());
			dto.setDeImportoTotale(Utilities.parseImportoString(flussoExportTO.getNumEDatiPagImportoTotalePagato()));
			dto.setDeImportoSingolo(
					Utilities.parseImportoString(flussoExportTO.getNumEDatiPagDatiSingPagSingoloImportoPagato()));
			if (flussoExportTO.getCodESoggPagIdUnivPagTipoIdUnivoco() != null) {
				dto.setCodESoggPagIdUnivPagTipoIdUnivoco(
						flussoExportTO.getCodESoggPagIdUnivPagTipoIdUnivoco().toString());
			}

			if (flussoExportTO.getCodESoggVersIdUnivVersTipoIdUnivoco() != null) {
				dto.setCodESoggVersIdUnivVersTipoIdUnivoco(
						flussoExportTO.getCodESoggVersIdUnivVersTipoIdUnivoco().toString());
			}

			EnteTipoDovuto enteTipoDovuto = enteTipoDovutoDao.findByCodIpaEnteAndCodTipo(codIpaEnte,
					flussoExportTO.getCodTipoDovuto());
			if (enteTipoDovuto == null) {
				if (StringUtils.isBlank(flussoExportTO.getCodTipoDovuto())) {
					dto.setDeTipoDovuto("n/a"); 
				} else {
					dto.setDeTipoDovuto(flussoExportTO.getCodTipoDovuto());
				}

			} else {
				dto.setDeTipoDovuto(enteTipoDovuto.getDeTipo());
			}

			dto.setFlussoExportTO(flussoExportTO);
		return flussoRicevutaDto;
	}
	
	@Override
	public PageDto<FlussoRicevutaDto> getFlussoExportPageByIUF(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			int page, int pageSize, Sort sort) throws RtNonInRendicontazioneException {

		List<String> listaIud = getListaIUDFromListaExportByCodIpaEnteAndIUF(codIpaEnte, iuf);

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoExport> flussoExportPage;

		if (StringUtils.isNotBlank(iud)) {
			if (listaIud.contains(iud)) {
				flussoExportPage = getExportPageList(codIpaEnte, codFedUserId, dt_data_esito_singolo_pagamento_da,
						dt_data_esito_singolo_pagamento_a, iud, iuv, denominazioneAttestante,
						identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore, anagraficaPagatore,
						codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, pageable);
			} else {
				throw new RtNonInRendicontazioneException(
						"Errore durante il recupero delle ricevute, lo iud inserito non è presente nel flusso di rendicontazione selezionato");
			}
		} else {
			flussoExportPage = getExportPageList(codIpaEnte, codFedUserId, dt_data_esito_singolo_pagamento_da,
					dt_data_esito_singolo_pagamento_a, listaIud, iuv, denominazioneAttestante,
					identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore, anagraficaPagatore,
					codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto, pageable);
		}

		List<FlussoExport> flussoExportList = flussoExportPage.getContent();

		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, flussoExportList);

		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(flussoExportPage.hasNextPage());
		flussoRicevutaDtoPage.setPage(flussoExportPage.getNumber() + 1);
		flussoRicevutaDtoPage.setPageSize(flussoExportPage.getSize());
		flussoRicevutaDtoPage.setPreviousPage(flussoExportPage.hasPreviousPage());
		flussoRicevutaDtoPage.setTotalPages(flussoExportPage.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(flussoExportPage.getTotalElements());

		return flussoRicevutaDtoPage;
	}

	private Page<FlussoExport> getExportPageList(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, List<String> listaIud,
			String iuv, String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto,
			Pageable pageable) {

		Specifications<FlussoExport> specification = getSpecifications(codIpaEnte, codFedUserId,
				dt_data_esito_singolo_pagamento_da, dt_data_esito_singolo_pagamento_a, listaIud, iuv,
				denominazioneAttestante, identificativoUnivocoRiscossione, codiceIdentificativoUnivocoPagatore,
				anagraficaPagatore, codiceIdentificativoUnivocoVersante, anagraficaVersante, codTipoDovuto);

		Page<FlussoExport> exportPage = null;
		if (specification != null) {
			exportPage = flussoExportDao.findAll(specification, pageable);
		} else {
			exportPage = flussoExportDao.findAll(pageable);
		}
		return exportPage;
	}

	private Specifications<FlussoExport> getSpecifications(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, List<String> listaIud,
			String iuv, String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto) {

		Specifications<FlussoExport> specifications = null;

		if (StringUtils.isNotBlank(codIpaEnte)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codIpaEnte", "=", codIpaEnte.trim(), "ente"));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_esito_singolo_pagamento_da != null) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("dtEDatiPagDatiSingPagDataEsitoSingoloPagamento", ">=",
							dt_data_esito_singolo_pagamento_da, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (dt_data_esito_singolo_pagamento_a != null) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("dtEDatiPagDatiSingPagDataEsitoSingoloPagamento", "<=",
							dt_data_esito_singolo_pagamento_a, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (CollectionUtils.isNotEmpty(listaIud)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codIud", "in", listaIud, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(iuv)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codRpSilinviarpIdUnivocoVersamento", "=", iuv.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(denominazioneAttestante)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("deEIstitAttDenominazioneAttestante", "like", denominazioneAttestante.trim(),
							false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(identificativoUnivocoRiscossione)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codEDatiPagDatiSingPagIdUnivocoRiscoss", "=",
							identificativoUnivocoRiscossione.trim(), true));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codiceIdentificativoUnivocoPagatore)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggPagIdUnivPagCodiceIdUnivoco", "=",
							codiceIdentificativoUnivocoPagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(anagraficaPagatore)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggPagAnagraficaPagatore", "like", anagraficaPagatore.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isNotBlank(codiceIdentificativoUnivocoVersante)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggVersIdUnivVersCodiceIdUnivoco", "=",
							codiceIdentificativoUnivocoVersante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}
		if (StringUtils.isNotBlank(anagraficaVersante)) {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codESoggVersAnagraficaVersante", "like", anagraficaVersante.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		if (StringUtils.isEmpty(codTipoDovuto)) {
			List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService
					.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codTipoDovuto", "in", listaCodTipoDovuto, false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		} else {
			MyJpaSpecification<FlussoExport> myJpaSpecification = new MyJpaSpecification<FlussoExport>(
					new SearchCriteria("codTipoDovuto", "=", codTipoDovuto.trim(), false));
			specifications = specifications == null ? Specifications.where(myJpaSpecification)
					: specifications.and(myJpaSpecification);
		}

		return specifications;
	}

	private List<String> getListaIUDFromListaExportByCodIpaEnteAndIUF(String codIpaEnte, String iuf) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuf, "Parametro [ iuf ] vuoto");

		List<String> listaIud = new ArrayList<String>();

		List<FlussoRendicontazione> listaFlussiRendicontazione = flussoRendicontazioneDao
				.findByCodIpaEnteAndIUF(codIpaEnte, iuf);
		if (listaFlussiRendicontazione != null && !listaFlussiRendicontazione.isEmpty()) {
			List<FlussoRendicontazioneTO> listaFlussiRendicontazioneTO = new ArrayList<FlussoRendicontazioneTO>();
			for (FlussoRendicontazione rend : listaFlussiRendicontazione) {
				FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(rend,
						FlussoRendicontazioneTO.class);
				listaFlussiRendicontazioneTO.add(flussoRendicontazioneTO);
			}
			if (listaFlussiRendicontazioneTO != null && !listaFlussiRendicontazioneTO.isEmpty()) {
				for (FlussoRendicontazioneTO rend : listaFlussiRendicontazioneTO) {
					String iuv = rend.getId().getCodDatiSingPagamIdentificativoUnivocoVersamento();
					int indiceDatiSingoloPagamento = rend.getId().getIndiceDatiSingoloPagamento();
					FlussoExportTO flussoExportTO = findByCodIpaEnteIUVAndIndiceDatiSingoloPagamento(codIpaEnte, iuv,
							indiceDatiSingoloPagamento);
					if (flussoExportTO != null) {
						listaIud.add(flussoExportTO.getCodIud());
					}
				}
				return listaIud;
			}
		}
		return null;
	}	
	
}
