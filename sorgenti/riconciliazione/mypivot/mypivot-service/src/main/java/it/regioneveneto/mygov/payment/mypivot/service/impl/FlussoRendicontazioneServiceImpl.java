package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.FlussoRendicontazioneDao;
import it.regioneveneto.mygov.payment.mypivot.dao.FlussoRendicontazioneFunctionDao;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.MyJpaSpecification;
import it.regioneveneto.mygov.payment.mypivot.dao.specifications.SearchCriteria;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDistinctDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoRicevutaDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.flussorendicontazione.DettaglioFlussoRendicontazioneDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExport;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoExportId;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoRendicontazione;
import it.regioneveneto.mygov.payment.mypivot.domain.po.FlussoRendicontazioneId;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportIdTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.EnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoExportService;
import it.regioneveneto.mygov.payment.mypivot.service.FlussoRendicontazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.RtNonInRendicontazioneException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Service("flussoRendicontazioneService")
public class FlussoRendicontazioneServiceImpl implements FlussoRendicontazioneService {
	private static final Logger LOG = Logger.getLogger(FlussoRendicontazioneServiceImpl.class);

	@Autowired
	private FlussoRendicontazioneDao flussoRendicontazioneDao;

	@Autowired
	private FlussoExportService flussoExportService;

	@Autowired
	private EnteTipoDovutoService enteTipoDovutoService;

	@Autowired
	private FlussoRendicontazioneFunctionDao flussoRendicontazioneFunctionDao;
	
	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private ModelMapperUtil modelMapperUtil;

	private static final Specification<FlussoRendicontazione> getFlussiRendicontazioneByTipoDovuto(
			final Collection<String> tipoDovuto) {
		return new Specification<FlussoRendicontazione>() {

			private Predicate getFlussoExport(final CriteriaBuilder cb, final Root<FlussoExport> flussoExportRoot,
					final Root<FlussoRendicontazione> flussoRendicontazioneRoot) {

				Predicate entePred = cb.equal(flussoExportRoot.get("ente"), flussoRendicontazioneRoot.get("ente"));

				Predicate indiceDatiSingoloPagamentoPred = cb.equal(
						flussoExportRoot.get("id").get("indiceDatiSingoloPagamento"),
						flussoRendicontazioneRoot.get("id").get("indiceDatiSingoloPagamento"));

				Predicate iuvPred = cb.equal(flussoExportRoot.get("id").get("codRpSilinviarpIdUnivocoVersamento"),
						flussoRendicontazioneRoot.get("id").get("codDatiSingPagamIdentificativoUnivocoVersamento"));

				Predicate keyPred = cb.and(entePred, indiceDatiSingoloPagamentoPred, iuvPred);
				return keyPred;
			}

			@Override
			public Predicate toPredicate(Root<FlussoRendicontazione> flussoRendicontazioneRoot, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Subquery<Long> flussoExportPresenteSubquery = query.subquery(Long.class);
				Root<FlussoExport> flussoExportRoot = flussoExportPresenteSubquery.from(FlussoExport.class);
				Predicate flussoExportPred = getFlussoExport(cb, flussoExportRoot, flussoRendicontazioneRoot);

				
				
				Predicate tipoDovutoValidoPred = flussoExportRoot.get("codTipoDovuto").in(tipoDovuto);
				
				Predicate codiceEsitoPagamento0 = cb.equal(flussoExportRoot.get("codEDatiPagCodiceEsitoPagamento"), new Character('0'));
				
				Predicate codiceEsitoPagamento9 = cb.equal(flussoExportRoot.get("codEDatiPagCodiceEsitoPagamento"), new Character('9'));
				
				Predicate tipoDovutoValidoPredAndCod0 = cb.and(tipoDovutoValidoPred,codiceEsitoPagamento0);
				
				Predicate tipoDovutoValidoPredAndCod0orCod9 =cb.or(tipoDovutoValidoPredAndCod0,codiceEsitoPagamento9);
				
				

				flussoExportPresenteSubquery.select(cb.literal(1L)).where(flussoExportPred, tipoDovutoValidoPredAndCod0orCod9);
				Predicate existExportTipoDovutoValidoPredicate = cb.exists(flussoExportPresenteSubquery);

				// -------------------------------------------- //
				Subquery<Long> flussoExportAssenteSubquery = query.subquery(Long.class);
				Root<FlussoExport> flussoExportAssenteRoot = flussoExportAssenteSubquery.from(FlussoExport.class);
				Predicate keyFlussoExportAssentePred = getFlussoExport(cb, flussoExportAssenteRoot,
						flussoRendicontazioneRoot);
				flussoExportAssenteSubquery.select(cb.literal(1L)).where(keyFlussoExportAssentePred);
				Predicate flussoExportAssentePredicate = cb.not(cb.exists(flussoExportAssenteSubquery));

				Predicate fullPredicate = cb.or(flussoExportAssentePredicate, existExportTipoDovutoValidoPredicate);

				return fullPredicate;
			}
		};
	}

	@Override
	public PageDto<DettaglioFlussoRendicontazioneDto> findDettagliFlussoRendicontazione(final String codiceIpaEnte,
			final String codIuf, final Collection<String> codTipoDovutoValidi, int page, int pageSize) {
		LOG.debug("recupero lista dettaglio flusso rendicontazione paginata - inizio");
		Specification<FlussoRendicontazione> tipoDovutoExportSpecification = getFlussiRendicontazioneByTipoDovuto(
				codTipoDovutoValidi);

		MyJpaSpecification<FlussoRendicontazione> codIufSpecification = new MyJpaSpecification<FlussoRendicontazione>(
				new SearchCriteria("codIdentificativoFlusso", "=", codIuf.trim(), false));

		MyJpaSpecification<FlussoRendicontazione> codiceIpaEnteSpecification = new MyJpaSpecification<FlussoRendicontazione>(
				new SearchCriteria("codIpaEnte", "=", codiceIpaEnte.trim(), "ente"));

		Specifications<FlussoRendicontazione> specifications = Specifications//
				.where(tipoDovutoExportSpecification)//
				.and(codIufSpecification)//
				.and(codiceIpaEnteSpecification);

		Sort sort = new Sort(new Order(Direction.DESC, "id.codDatiSingPagamIdentificativoUnivocoVersamento"),
				new Order(Direction.ASC, "id.indiceDatiSingoloPagamento"));

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}
		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoRendicontazione> dettagliFlussoRendicontazionePage = flussoRendicontazioneDao.findAll(specifications,
				pageable);

		List<FlussoRendicontazione> dettagliPO = dettagliFlussoRendicontazionePage.getContent();
		List<DettaglioFlussoRendicontazioneDto> dettaglioDtoList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(dettagliPO)) {
			Set<FlussoExportId> flussoExportIds = new HashSet<>();
			for (FlussoRendicontazione flussoRendicontazione : dettagliPO) {
				FlussoExportId exportId = getFlussoExportIdByFlussoRendicontazione(flussoRendicontazione);
				flussoExportIds.add(exportId);
			}

			// Devo recuperare anche eventuali dettagli sul flusso presenti
			// nella tabella flusso Export
			List<FlussoExportTO> flussoExportList = flussoExportService.findAllByIdSet(flussoExportIds);
			// costruisco una mappa con questi valori per facilitare la
			// valorizzazione del dto che contiene
			// la coppia di dettalgi flussoExport e flussoRendicontazioni
			Map<FlussoExportMapKey, FlussoExportTO> flussoExportMap = new HashMap<>();
			Set<String> listaCodiciTipoDovuto = new HashSet<String>();
			if (CollectionUtils.isNotEmpty(flussoExportList)) {
				for (FlussoExportTO flussoExportTO : flussoExportList) {
					FlussoExportMapKey key = new FlussoExportMapKey(flussoExportTO.getId());
					flussoExportMap.put(key, flussoExportTO);
					if(StringUtils.isNotBlank(flussoExportTO.getCodTipoDovuto()))
						listaCodiciTipoDovuto.add(flussoExportTO.getCodTipoDovuto());
				}
			}

			List<EnteTipoDovutoTO> listaEnteTipoDovuto;
			if (CollectionUtils.isNotEmpty(listaCodiciTipoDovuto)) {
				listaEnteTipoDovuto = enteTipoDovutoService.getByCodIpaEnteAndCodTipoDovutoList(codiceIpaEnte,
						listaCodiciTipoDovuto);
			} else {
				listaEnteTipoDovuto = new ArrayList<>();
			}
			for (FlussoRendicontazione flussoRendicontazione : dettagliPO) {
				FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(flussoRendicontazione,
						FlussoRendicontazioneTO.class);
				DettaglioFlussoRendicontazioneDto dettaglioDto = new DettaglioFlussoRendicontazioneDto();
				dettaglioDtoList.add(dettaglioDto);
				dettaglioDto.setFlussoRendicontazioneTO(flussoRendicontazioneTO);

				FlussoExportIdTO flussoExportIdTO = getFlussoExportIdTOByFlussoRendicontazione(flussoRendicontazione);
				FlussoExportMapKey key = new FlussoExportMapKey(flussoExportIdTO);
				if (flussoExportMap.containsKey(key)) {
					FlussoExportTO exportTO = flussoExportMap.get(key);
					dettaglioDto.setFlussoExportTO(exportTO);
					if (CollectionUtils.isNotEmpty(listaEnteTipoDovuto)) {
						for (EnteTipoDovutoTO etd : listaEnteTipoDovuto) {
							if (etd.getCodTipo().equals(exportTO.getCodTipoDovuto())) {
								dettaglioDto.setDeTipoDovuto(etd.getDeTipo());
							}
						}
					}
				}
			}
		}

		PageDto<DettaglioFlussoRendicontazioneDto> pag = Utilities.<DettaglioFlussoRendicontazioneDto>getPageDtoInstance(dettaglioDtoList, pageToGet + 1, pageSize,
						dettagliFlussoRendicontazionePage.getTotalPages(),
						dettagliFlussoRendicontazionePage.getTotalElements());
		LOG.debug("recupero lista dettaglio flusso rendicontazione paginata - fine");
		return pag;
	}

	
	
	@Override
	public PageDto<DettaglioFlussoRendicontazioneDto> findDettagliFlussoRendicontazione(final String codiceIpaEnte,
			final String codIuf, final Collection<String> codTipoDovutoValidi, Pageable pageable , Sort sort) {
		LOG.debug("recupero lista dettaglio flusso rendicontazione paginata - inizio");
		Specification<FlussoRendicontazione> tipoDovutoExportSpecification = getFlussiRendicontazioneByTipoDovuto(
				codTipoDovutoValidi);

		MyJpaSpecification<FlussoRendicontazione> codIufSpecification = new MyJpaSpecification<FlussoRendicontazione>(
				new SearchCriteria("codIdentificativoFlusso", "=", codIuf.trim(), false));

		MyJpaSpecification<FlussoRendicontazione> codiceIpaEnteSpecification = new MyJpaSpecification<FlussoRendicontazione>(
				new SearchCriteria("codIpaEnte", "=", codiceIpaEnte.trim(), "ente"));

		Specifications<FlussoRendicontazione> specifications = Specifications//
				.where(tipoDovutoExportSpecification)//
				.and(codIufSpecification)//
				.and(codiceIpaEnteSpecification);

//		Sort sort = new Sort(new Order(Direction.DESC, "id.codDatiSingPagamIdentificativoUnivocoVersamento"),
//				new Order(Direction.ASC, "id.indiceDatiSingoloPagamento"));

//		int pageToGet = 0;
//		if (page > 0) {
//			pageToGet = page - 1;
//		}
//		Pageable pageable = new PageRequest(pageToGet, pageSize, sort);

		Page<FlussoRendicontazione> dettagliFlussoRendicontazionePage = flussoRendicontazioneDao.findAll(specifications,
				pageable);

		List<FlussoRendicontazione> dettagliPO = dettagliFlussoRendicontazionePage.getContent();
		List<DettaglioFlussoRendicontazioneDto> dettaglioDtoList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(dettagliPO)) {
			Set<FlussoExportId> flussoExportIds = new HashSet<>();
			for (FlussoRendicontazione flussoRendicontazione : dettagliPO) {
				FlussoExportId exportId = getFlussoExportIdByFlussoRendicontazione(flussoRendicontazione);
				flussoExportIds.add(exportId);
			}

			// Devo recuperare anche eventuali dettagli sul flusso presenti
			// nella tabella flusso Export
			List<FlussoExportTO> flussoExportList = flussoExportService.findAllByIdSet(flussoExportIds);
			// costruisco una mappa con questi valori per facilitare la
			// valorizzazione del dto che contiene
			// la coppia di dettalgi flussoExport e flussoRendicontazioni
			Map<FlussoExportMapKey, FlussoExportTO> flussoExportMap = new HashMap<>();
			Set<String> listaCodiciTipoDovuto = new HashSet<String>();
			if (CollectionUtils.isNotEmpty(flussoExportList)) {
				for (FlussoExportTO flussoExportTO : flussoExportList) {
					FlussoExportMapKey key = new FlussoExportMapKey(flussoExportTO.getId());
					flussoExportMap.put(key, flussoExportTO);
					if(StringUtils.isNotBlank(flussoExportTO.getCodTipoDovuto()))
						listaCodiciTipoDovuto.add(flussoExportTO.getCodTipoDovuto());
				}
			}

			List<EnteTipoDovutoTO> listaEnteTipoDovuto;
			if (CollectionUtils.isNotEmpty(listaCodiciTipoDovuto)) {
				listaEnteTipoDovuto = enteTipoDovutoService.getByCodIpaEnteAndCodTipoDovutoList(codiceIpaEnte,
						listaCodiciTipoDovuto);
			} else {
				listaEnteTipoDovuto = new ArrayList<>();
			}
			for (FlussoRendicontazione flussoRendicontazione : dettagliPO) {
				FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(flussoRendicontazione,
						FlussoRendicontazioneTO.class);
				DettaglioFlussoRendicontazioneDto dettaglioDto = new DettaglioFlussoRendicontazioneDto();
				dettaglioDtoList.add(dettaglioDto);
				dettaglioDto.setFlussoRendicontazioneTO(flussoRendicontazioneTO);

				FlussoExportIdTO flussoExportIdTO = getFlussoExportIdTOByFlussoRendicontazione(flussoRendicontazione);
				FlussoExportMapKey key = new FlussoExportMapKey(flussoExportIdTO);
				if (flussoExportMap.containsKey(key)) {
					FlussoExportTO exportTO = flussoExportMap.get(key);
					dettaglioDto.setFlussoExportTO(exportTO);
					if (CollectionUtils.isNotEmpty(listaEnteTipoDovuto)) {
						for (EnteTipoDovutoTO etd : listaEnteTipoDovuto) {
							if (etd.getCodTipo().equals(exportTO.getCodTipoDovuto())) {
								dettaglioDto.setDeTipoDovuto(etd.getDeTipo());
							}
						}
					}
				}
			}
		}

		Integer pageToGet = pageable.getPageNumber();
		Integer pageSize = pageable.getPageSize();
		PageDto<DettaglioFlussoRendicontazioneDto> pag = Utilities.<DettaglioFlussoRendicontazioneDto>getPageDtoInstance(dettaglioDtoList, pageToGet + 1, pageSize,
						dettagliFlussoRendicontazionePage.getTotalPages(),
						dettagliFlussoRendicontazionePage.getTotalElements());
		LOG.debug("recupero lista dettaglio flusso rendicontazione paginata - fine");
		return pag;
	}

	
	private class FlussoExportMapKey {
		private String codEDatiPagDatiSingPagIdUnivocoRiscoss;
		private String codRpSilinviarpIdUnivocoVersamento;
		private int indiceDatiSingoloPagamento;
		private long mygovEnteId;

		public FlussoExportMapKey(FlussoExportIdTO flussoExportIdTO) {
			super();
			this.codEDatiPagDatiSingPagIdUnivocoRiscoss = flussoExportIdTO.codEDatiPagDatiSingPagIdUnivocoRiscoss;
			this.codRpSilinviarpIdUnivocoVersamento = flussoExportIdTO.codRpSilinviarpIdUnivocoVersamento;
			this.indiceDatiSingoloPagamento = flussoExportIdTO.indiceDatiSingoloPagamento;
			this.mygovEnteId = flussoExportIdTO.mygovEnteId;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((codEDatiPagDatiSingPagIdUnivocoRiscoss == null) ? 0
					: codEDatiPagDatiSingPagIdUnivocoRiscoss.hashCode());
			result = prime * result + ((codRpSilinviarpIdUnivocoVersamento == null) ? 0
					: codRpSilinviarpIdUnivocoVersamento.hashCode());
			result = prime * result + indiceDatiSingoloPagamento;
			result = prime * result + (int) (mygovEnteId ^ (mygovEnteId >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			FlussoExportMapKey other = (FlussoExportMapKey) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (codEDatiPagDatiSingPagIdUnivocoRiscoss == null) {
				if (other.codEDatiPagDatiSingPagIdUnivocoRiscoss != null)
					return false;
			} else if (!codEDatiPagDatiSingPagIdUnivocoRiscoss.equals(other.codEDatiPagDatiSingPagIdUnivocoRiscoss))
				return false;
			if (codRpSilinviarpIdUnivocoVersamento == null) {
				if (other.codRpSilinviarpIdUnivocoVersamento != null)
					return false;
			} else if (!codRpSilinviarpIdUnivocoVersamento.equals(other.codRpSilinviarpIdUnivocoVersamento))
				return false;
			if (indiceDatiSingoloPagamento != other.indiceDatiSingoloPagamento)
				return false;
			if (mygovEnteId != other.mygovEnteId)
				return false;
			return true;
		}

		private FlussoRendicontazioneServiceImpl getOuterType() {
			return FlussoRendicontazioneServiceImpl.this;
		}

	}

	private FlussoExportIdTO getFlussoExportIdTOByFlussoRendicontazione(FlussoRendicontazione flussoRendicontazione) {
		FlussoExportId flussoExportId = getFlussoExportIdByFlussoRendicontazione(flussoRendicontazione);
		FlussoExportIdTO flussoExportIdTO = modelMapperUtil.map(flussoExportId, FlussoExportIdTO.class);
		return flussoExportIdTO;
	}

	private static final FlussoExportId getFlussoExportIdByFlussoRendicontazione(
			FlussoRendicontazione flussoRendicontazione) {

		FlussoRendicontazioneId frid = flussoRendicontazione.getId();
		String codRpSilinviarpIdUnivocoVersamento = frid.getCodDatiSingPagamIdentificativoUnivocoVersamento();
		String codEDatiPagDatiSingPagIdUnivocoRiscoss = frid.getCodDatiSingPagamIdentificativoUnivocoRiscossione();
		int indiceDatiSingoloPagamento = frid.getIndiceDatiSingoloPagamento();

		FlussoExportId exportId = new FlussoExportId(frid.getMygovEnteId(), codRpSilinviarpIdUnivocoVersamento,
				codEDatiPagDatiSingPagIdUnivocoRiscoss, indiceDatiSingoloPagamento);
		return exportId;
	}

	@Override
	public List<FlussoRendicontazioneTO> findByCodIpaEnteAndIUF(final String codIpaEnte, final String iuf) {
		Assert.hasText(codIpaEnte, "Parametro [ codIpaEnte ] vuoto");
		Assert.hasText(iuf, "Parametro [ iuf ] vuoto");

		LOG.debug("Chiamata al servizio findByCodIpaEnteAndIUF con i parametri codIpaEnte [ " + codIpaEnte
				+ " ] e iuf [ " + iuf + " ]");

		List<FlussoRendicontazione> listaFlussiRendicontazione = flussoRendicontazioneDao
				.findByCodIpaEnteAndIUF(codIpaEnte, iuf);
		if (listaFlussiRendicontazione != null && !listaFlussiRendicontazione.isEmpty()) {
			List<FlussoRendicontazioneTO> listaFlussiRendicontazioneTO = new ArrayList<FlussoRendicontazioneTO>();
			for (FlussoRendicontazione rend : listaFlussiRendicontazione) {
				FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(rend,
						FlussoRendicontazioneTO.class);
				listaFlussiRendicontazioneTO.add(flussoRendicontazioneTO);
			}
			return listaFlussiRendicontazioneTO;
		}
		return null;
	}

	@Override
	public PageDto<FlussoRendicontazioneDistinctDto> getFlussoRendicontazionePage(Long mygovEnteId,
			Date dt_data_regolamento_da, Date dt_data_regolamento_a, String iuf,
			String identificativoUnivocoRegolamento, int page, int pageSize) {

		PageDto<FlussoRendicontazioneDistinctDto> pageDto = flussoRendicontazioneFunctionDao
				.callFlussoRendicontazioneFunction(mygovEnteId, dt_data_regolamento_da, dt_data_regolamento_a, iuf,
						identificativoUnivocoRegolamento, page, pageSize);
		return pageDto;
	}

	public List<FlussoRendicontazioneDto> mapPoInDto(List<FlussoRendicontazione> flussoRendicontazioneList) {
		List<FlussoRendicontazioneDto> flussoRendicontazioneDtoList = new ArrayList<FlussoRendicontazioneDto>();
		for (FlussoRendicontazione flussoRendicontazione : flussoRendicontazioneList) {
			FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(flussoRendicontazione,
					FlussoRendicontazioneTO.class);
			FlussoRendicontazioneDto dto = new FlussoRendicontazioneDto();

			dto.setCodiceIpaEnte(flussoRendicontazioneTO.getEnte().getCodIpaEnte());
			dto.setDeImporto(Utilities.parseImportoString(flussoRendicontazioneTO.getNumImportoTotalePagamenti()));
			dto.setFlussoRendicontazioneTO(flussoRendicontazioneTO);
			flussoRendicontazioneDtoList.add(dto);
		}
		return flussoRendicontazioneDtoList;
	}

	public FlussoRendicontazioneDto mapPoInDto(FlussoRendicontazione flussoRendicontazione) {
		FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(flussoRendicontazione,
				FlussoRendicontazioneTO.class);
		FlussoRendicontazioneDto dto = new FlussoRendicontazioneDto();

		dto.setCodiceIpaEnte(flussoRendicontazioneTO.getEnte().getCodIpaEnte());
		dto.setDeImporto(Utilities.parseImportoString(flussoRendicontazioneTO.getNumImportoTotalePagamenti()));
		dto.setFlussoRendicontazioneTO(flussoRendicontazioneTO);
		return dto;
	}

	@Override
	public FlussoRendicontazioneDto getFlussoRendicontazioneDto(String codIpaEnte, String iuf) {
		List<FlussoRendicontazione> listaFlussiRendicontazione = flussoRendicontazioneDao
				.findByCodIpaEnteAndIUF(codIpaEnte, iuf);
		if (CollectionUtils.isNotEmpty(listaFlussiRendicontazione)) {
			FlussoRendicontazioneDto flussoRendicontazioneDto = mapPoInDto(listaFlussiRendicontazione.get(0));
			return flussoRendicontazioneDto;
		}
		return null;
	}

	@Override
	public PageDto<FlussoRicevutaDto> getFlussoRendicontazionePageByIUF(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			int page, int pageSize, Sort sort) throws RtNonInRendicontazioneException {
		
		List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
		
		PageDto<DettaglioFlussoRendicontazioneDto>  dettaglioFRdto= findDettagliFlussoRendicontazione(codIpaEnte, iuf, listaCodTipoDovuto, page, pageSize);
	
		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, dettaglioFRdto.getList());
		
		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(dettaglioFRdto.isNextPage());
		flussoRicevutaDtoPage.setPage(dettaglioFRdto.getPage());
		flussoRicevutaDtoPage.setPageSize(dettaglioFRdto.getPageSize());
		flussoRicevutaDtoPage.setPreviousPage(dettaglioFRdto.isPreviousPage());
		flussoRicevutaDtoPage.setTotalPages(dettaglioFRdto.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(dettaglioFRdto.getTotalRecords());
		
		
		return flussoRicevutaDtoPage;
	}
	
	@Override
	public PageDto<FlussoRicevutaDto> getFlussoRendicontazionePageByIUF(String codIpaEnte, String codFedUserId,
			Date dt_data_esito_singolo_pagamento_da, Date dt_data_esito_singolo_pagamento_a, String iud, String iuv,
			String denominazioneAttestante, String identificativoUnivocoRiscossione,
			String codiceIdentificativoUnivocoPagatore, String anagraficaPagatore,
			String codiceIdentificativoUnivocoVersante, String anagraficaVersante, String codTipoDovuto, String iuf,
			Pageable pageable) throws RtNonInRendicontazioneException {
		
		List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
		
		PageDto<DettaglioFlussoRendicontazioneDto>  dettaglioFRdto= findDettagliFlussoRendicontazione(codIpaEnte, iuf, listaCodTipoDovuto, pageable);
	
		List<FlussoRicevutaDto> flussoRicevutaDtoList = mapPoInDto(codIpaEnte, dettaglioFRdto.getList());
		
		PageDto<FlussoRicevutaDto> flussoRicevutaDtoPage = new PageDto<FlussoRicevutaDto>();
		flussoRicevutaDtoPage.setList(flussoRicevutaDtoList);

		flussoRicevutaDtoPage.setNextPage(dettaglioFRdto.isNextPage());
		flussoRicevutaDtoPage.setPage(dettaglioFRdto.getPage());
		flussoRicevutaDtoPage.setPageSize(dettaglioFRdto.getPageSize());
		flussoRicevutaDtoPage.setPreviousPage(dettaglioFRdto.isPreviousPage());
		flussoRicevutaDtoPage.setTotalPages(dettaglioFRdto.getTotalPages());
		flussoRicevutaDtoPage.setTotalRecords(dettaglioFRdto.getTotalRecords());
		
		
		return flussoRicevutaDtoPage;
	}
	
	private PageDto<DettaglioFlussoRendicontazioneDto> findDettagliFlussoRendicontazione(String codiceIpaEnte, String codIuf,
			List<String> codTipoDovutoValidi, Pageable pageable) {
		LOG.debug("recupero lista dettaglio flusso rendicontazione paginata - inizio");
		Specification<FlussoRendicontazione> tipoDovutoExportSpecification = getFlussiRendicontazioneByTipoDovuto(
				codTipoDovutoValidi);

		MyJpaSpecification<FlussoRendicontazione> codIufSpecification = new MyJpaSpecification<FlussoRendicontazione>(
				new SearchCriteria("codIdentificativoFlusso", "=", codIuf.trim(), false));

		MyJpaSpecification<FlussoRendicontazione> codiceIpaEnteSpecification = new MyJpaSpecification<FlussoRendicontazione>(
				new SearchCriteria("codIpaEnte", "=", codiceIpaEnte.trim(), "ente"));

		Specifications<FlussoRendicontazione> specifications = Specifications//
				.where(tipoDovutoExportSpecification)//
				.and(codIufSpecification)//
				.and(codiceIpaEnteSpecification);

		Page<FlussoRendicontazione> dettagliFlussoRendicontazionePage = flussoRendicontazioneDao.findAll(specifications,
				pageable);

		List<FlussoRendicontazione> dettagliPO = dettagliFlussoRendicontazionePage.getContent();
		List<DettaglioFlussoRendicontazioneDto> dettaglioDtoList = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(dettagliPO)) {
			Set<FlussoExportId> flussoExportIds = new HashSet<>();
			for (FlussoRendicontazione flussoRendicontazione : dettagliPO) {
				FlussoExportId exportId = getFlussoExportIdByFlussoRendicontazione(flussoRendicontazione);
				flussoExportIds.add(exportId);
			}

			// Devo recuperare anche eventuali dettagli sul flusso presenti
			// nella tabella flusso Export
			List<FlussoExportTO> flussoExportList = flussoExportService.findAllByIdSet(flussoExportIds);
			// costruisco una mappa con questi valori per facilitare la
			// valorizzazione del dto che contiene
			// la coppia di dettalgi flussoExport e flussoRendicontazioni
			Map<FlussoExportMapKey, FlussoExportTO> flussoExportMap = new HashMap<>();
			Set<String> listaCodiciTipoDovuto = new HashSet<String>();
			if (CollectionUtils.isNotEmpty(flussoExportList)) {
				for (FlussoExportTO flussoExportTO : flussoExportList) {
					FlussoExportMapKey key = new FlussoExportMapKey(flussoExportTO.getId());
					flussoExportMap.put(key, flussoExportTO);
					if(StringUtils.isNotBlank(flussoExportTO.getCodTipoDovuto()))
						listaCodiciTipoDovuto.add(flussoExportTO.getCodTipoDovuto());
				}
			}

			List<EnteTipoDovutoTO> listaEnteTipoDovuto;
			if (CollectionUtils.isNotEmpty(listaCodiciTipoDovuto)) {
				listaEnteTipoDovuto = enteTipoDovutoService.getByCodIpaEnteAndCodTipoDovutoList(codiceIpaEnte,
						listaCodiciTipoDovuto);
			} else {
				listaEnteTipoDovuto = new ArrayList<>();
			}
			for (FlussoRendicontazione flussoRendicontazione : dettagliPO) {
				FlussoRendicontazioneTO flussoRendicontazioneTO = modelMapperUtil.map(flussoRendicontazione,
						FlussoRendicontazioneTO.class);
				DettaglioFlussoRendicontazioneDto dettaglioDto = new DettaglioFlussoRendicontazioneDto();
				dettaglioDtoList.add(dettaglioDto);
				dettaglioDto.setFlussoRendicontazioneTO(flussoRendicontazioneTO);

				FlussoExportIdTO flussoExportIdTO = getFlussoExportIdTOByFlussoRendicontazione(flussoRendicontazione);
				FlussoExportMapKey key = new FlussoExportMapKey(flussoExportIdTO);
				if (flussoExportMap.containsKey(key)) {
					FlussoExportTO exportTO = flussoExportMap.get(key);
					dettaglioDto.setFlussoExportTO(exportTO);
					if (CollectionUtils.isNotEmpty(listaEnteTipoDovuto)) {
						for (EnteTipoDovutoTO etd : listaEnteTipoDovuto) {
							if (etd.getCodTipo().equals(exportTO.getCodTipoDovuto())) {
								dettaglioDto.setDeTipoDovuto(etd.getDeTipo());
							}
						}
					}
				}
			}
		}

		PageDto<DettaglioFlussoRendicontazioneDto> pag = Utilities.<DettaglioFlussoRendicontazioneDto>getPageDtoInstance(dettaglioDtoList, pageable.getPageNumber() + 1, pageable.getPageSize(),
						dettagliFlussoRendicontazionePage.getTotalPages(),
						dettagliFlussoRendicontazionePage.getTotalElements());
		LOG.debug("recupero lista dettaglio flusso rendicontazione paginata - fine");
		return pag;
	}

	private List<FlussoRicevutaDto> mapPoInDto(String codIpaEnte, List<DettaglioFlussoRendicontazioneDto> flussoList){
		List<FlussoRicevutaDto> flussoRicevutaDtoList = new ArrayList<FlussoRicevutaDto>();
		for (DettaglioFlussoRendicontazioneDto flussoSingle : flussoList) {
			FlussoRendicontazioneTO flussoRendicontazioneTO = flussoSingle.getFlussoRendicontazioneTO();
			FlussoExportTO flussoExportTO = flussoSingle.getFlussoExportTO();;
			FlussoRicevutaDto dto = new FlussoRicevutaDto();
			if(flussoExportTO != null) {
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
				String tipoDovuto = flussoSingle.getDeTipoDovuto();
				if (StringUtils.isNotBlank(tipoDovuto)) 
					dto.setDeTipoDovuto(tipoDovuto);
				else 
					dto.setDeTipoDovuto("n/a");
				
				if(StringUtils.isBlank(flussoExportTO.getCodIud()))
					flussoExportTO.setCodIud("n/a");
				
				if(StringUtils.isBlank(flussoExportTO.getDeEDatiPagDatiSingPagCausaleVersamento()))
					flussoExportTO.setDeEDatiPagDatiSingPagCausaleVersamento("n/a");
				
				dto.setFlussoExportTO(flussoExportTO);
				flussoRicevutaDtoList.add(dto);
			}
			else {
				dto.setCodiceIpaEnte(flussoRendicontazioneTO.getEnte().getCodIpaEnte());
				dto.setDeImportoTotale("n/a");
				dto.setDeImportoSingolo(Utilities.parseImportoString(flussoRendicontazioneTO.getNumDatiSingPagamSingoloImportoPagato()));
				dto.setCodESoggPagIdUnivPagTipoIdUnivoco("n/a");
				dto.setCodESoggVersIdUnivVersTipoIdUnivoco("n/a");
				String tipoDovuto = flussoSingle.getDeTipoDovuto();
				if (StringUtils.isNotBlank(tipoDovuto)) 
					dto.setDeTipoDovuto(tipoDovuto);
				else 
					dto.setDeTipoDovuto("n/a");
				FlussoExportTO flussoExportTOtemp = new FlussoExportTO();
				FlussoExportIdTO idTemp = new FlussoExportIdTO();
				idTemp.setCodRpSilinviarpIdUnivocoVersamento(flussoRendicontazioneTO.getId().getCodDatiSingPagamIdentificativoUnivocoVersamento());
				idTemp.setCodEDatiPagDatiSingPagIdUnivocoRiscoss(flussoRendicontazioneTO.getId().getCodDatiSingPagamIdentificativoUnivocoRiscossione());
				flussoExportTOtemp.setId(idTemp);
				flussoExportTOtemp.setCodIud("n/a");
				flussoExportTOtemp.setDeEDatiPagDatiSingPagCausaleVersamento("n/a");
				flussoExportTOtemp.setDtEDatiPagDatiSingPagDataEsitoSingoloPagamento(flussoRendicontazioneTO.getDtDatiSingPagamDataEsitoSingoloPagamento());
				flussoExportTOtemp.setNumEDatiPagDatiSingPagSingoloImportoPagato(flussoRendicontazioneTO.getNumDatiSingPagamSingoloImportoPagato());
				dto.setFlussoExportTO(flussoExportTOtemp);
				flussoRicevutaDtoList.add(dto);
			}
		}
		return flussoRicevutaDtoList;
	}
}
