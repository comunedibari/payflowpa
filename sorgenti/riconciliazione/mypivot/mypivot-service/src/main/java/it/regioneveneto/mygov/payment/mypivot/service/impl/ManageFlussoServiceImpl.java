package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaStatoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.EnteDao;
import it.regioneveneto.mygov.payment.mypivot.dao.InfoFlussoPosteWebDao;
import it.regioneveneto.mygov.payment.mypivot.dao.InfoMappingTesoreriaDao;
import it.regioneveneto.mygov.payment.mypivot.dao.ManageFlussoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.TipoFlussoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.UploadEsitoDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.InfoFlussoPosteWeb;
import it.regioneveneto.mygov.payment.mypivot.domain.po.InfoMappingTesoreria;
import it.regioneveneto.mygov.payment.mypivot.domain.po.ManageFlusso;
import it.regioneveneto.mygov.payment.mypivot.domain.po.TipoFlusso;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.service.ManageFlussoService;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Service
public class ManageFlussoServiceImpl implements ManageFlussoService {

	@Autowired
	private ManageFlussoDao manageFlussoDao;

	@Autowired
	private InfoFlussoPosteWebDao infoFlussoPosteWebDao;

	@Autowired
	private AnagraficaStatoDao anagraficaStatoDao;

	@Autowired
	private TipoFlussoDao tipoFlussoDao;

	@Autowired
	private UtenteDao utenteDao;

	@Autowired
	private EnteDao enteDao;

	@Resource
	private Environment env;
	
	@Autowired
	private InfoMappingTesoreriaDao infoMappingTesoreriaDao;

	@Override
	public PageDto<FlussoDto> getFlussoPage(Character codTipoFlusso, String codFedUserId, String codIpaEnte,
			String fullTextSearch, Date dateFrom, Date dateTo, int page, int size, Direction direction,
			String orderBy) {

		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		TipoFlusso tipoFlusso = tipoFlussoDao.findByCodTipo(codTipoFlusso);

		Pageable pageable = new PageRequest(pageToGet, size, direction, orderBy);

		List<AnagraficaStato> states = new ArrayList<AnagraficaStato>();

		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FLUSSO_OBSOLETO,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_ERROR_LOAD,
				Constants.DE_TIPO_STATO_MANAGE));

		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_SCARICATO,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_IN_CARICAMENTO,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_CARICATO,
				Constants.DE_TIPO_STATO_MANAGE));

		dateTo = DateUtils.addDays(dateTo, 1);

		Page<ManageFlusso> flussiPage = manageFlussoDao.getManageFlussoPage(ente.getId(), tipoFlusso.getId(),
				Constants.COD_PROVENIENZA_FILE_WEB, states, fullTextSearch, dateFrom, dateTo, pageable);

		List<FlussoDto> flussiDtos = mapEntitiesListToDtosList(flussiPage.getContent());

		PageDto<FlussoDto> flussiDtosPage = new PageDto<FlussoDto>();
		flussiDtosPage.setList(flussiDtos);
		flussiDtosPage.setNextPage(flussiPage.hasNextPage());
		flussiDtosPage.setPage(flussiPage.getNumber() + 1);
		flussiDtosPage.setPageSize(flussiPage.getSize());
		flussiDtosPage.setPreviousPage(flussiPage.hasPreviousPage());
		flussiDtosPage.setTotalPages(flussiPage.getTotalPages());
		flussiDtosPage.setTotalRecords(flussiPage.getTotalElements());

		return flussiDtosPage;
	}

	
	@Override
	public PageDto<FlussoDto> getFlussoPage(Character codTipoFlusso, String codFedUserId, String codIpaEnte,
			String fullTextSearch, Date dateFrom, Date dateTo, 
			Pageable pageable) {

//		int pageToGet = 0;
//		if (page > 0) {
//			pageToGet = page - 1;
//		}

		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		TipoFlusso tipoFlusso = tipoFlussoDao.findByCodTipo(codTipoFlusso);

//		Pageable pageable = new PageRequest(pageToGet, size, direction, orderBy);

		List<AnagraficaStato> states = new ArrayList<AnagraficaStato>();

		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FLUSSO_OBSOLETO,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_ERROR_LOAD,
				Constants.DE_TIPO_STATO_MANAGE));

		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_SCARICATO,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_IN_CARICAMENTO,
				Constants.DE_TIPO_STATO_MANAGE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_MANAGE_FILE_CARICATO,
				Constants.DE_TIPO_STATO_MANAGE));

		dateTo = DateUtils.addDays(dateTo, 1);

		Page<ManageFlusso> flussiPage = manageFlussoDao.getManageFlussoPage(ente.getId(), tipoFlusso.getId(),
				Constants.COD_PROVENIENZA_FILE_WEB, states, fullTextSearch, dateFrom, dateTo, pageable);

		List<FlussoDto> flussiDtos = mapEntitiesListToDtosList(flussiPage.getContent());

		PageDto<FlussoDto> flussiDtosPage = new PageDto<FlussoDto>();
		flussiDtosPage.setList(flussiDtos);
		flussiDtosPage.setNextPage(flussiPage.getNumber() + 1 < flussiPage.getTotalPages());
		flussiDtosPage.setPage(flussiPage.getNumber() + 1);
		flussiDtosPage.setPageSize(flussiPage.getSize());
		flussiDtosPage.setPreviousPage(flussiPage.getNumber() > 0);
		flussiDtosPage.setTotalPages(flussiPage.getTotalPages());
		flussiDtosPage.setTotalRecords(flussiPage.getTotalElements());

		return flussiDtosPage;
	}

	
	/**
	 * @param entities
	 * @return
	 */
	private List<FlussoDto> mapEntitiesListToDtosList(List<ManageFlusso> entities) {
		List<FlussoDto> dtos = new ArrayList<FlussoDto>();
		for (ManageFlusso flusso : entities) {
			FlussoDto flussoDto = new FlussoDto();

			flussoDto.setIdFlusso((flusso.getId().toString()));

			if (flusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_EXPORT_PAGATI
					|| flusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_TESORERIA
					|| flusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA
					|| flusso.getTipoFlusso().getCodTipo() == Constants.COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI)
				flussoDto.setNome(flusso.getDeNomeFile());
			else
				flussoDto.setNome(flusso.getCodIdentificativoFlusso());

			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			flussoDto.setDataCaricamento(fmt.print(new DateTime(flusso.getDtCreazione())));
			flussoDto.setOperatore(flusso.getUtente().getDeFirstname() + " " + flusso.getUtente().getDeLastname());

			flussoDto.setCodStato(flusso.getAnagraficaStato().getCodStato());
			flussoDto.setStato(flusso.getAnagraficaStato().getDeStato());

			dtos.add(flussoDto);
		}
		return dtos;
	}

	@Override
	public ManageFlusso insertFlusso(Character codTipoFlusso, String codIpaEnte, String codFedUserId,
			String requestToken, String deTipoStato, String codTipoStato) {

		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		TipoFlusso tipoFlusso = tipoFlussoDao.findByCodTipo(codTipoFlusso);
		Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);
		AnagraficaStato anagraficaStato = anagraficaStatoDao.findByCodStatoAndDeTipoStato(codTipoStato, deTipoStato);

		ManageFlusso manageFlusso = new ManageFlusso();

		manageFlusso.setEnte(ente);
		manageFlusso.setTipoFlusso(tipoFlusso);
		manageFlusso.setUtente(utente);
		manageFlusso.setAnagraficaStato(anagraficaStato);

		manageFlusso.setCodRequestToken(requestToken);

		manageFlusso.setDtCreazione(new Date());
		manageFlusso.setDtUltimaModifica(new Date());

		manageFlusso.setCodProvenienzaFile(Constants.COD_PROVENIENZA_FILE_WEB);

		return manageFlussoDao.saveAndFlush(manageFlusso);
	}

	@Override
	public ManageFlusso cambiaStatoFlusso(String codRequestToken, String deTipoStato, String precCodTipoStato,
			String codTipoStato) {
		AnagraficaStato precAnagraficaStato = anagraficaStatoDao.findByCodStatoAndDeTipoStato(precCodTipoStato,
				deTipoStato);

		ManageFlusso manageFlusso = manageFlussoDao.findByCodRequestTokenAndAnagraficaStato(codRequestToken,
				precAnagraficaStato);

		if (manageFlusso == null)
			return null;

		AnagraficaStato nuovoAnagraficaStato = anagraficaStatoDao.findByCodStatoAndDeTipoStato(codTipoStato,
				deTipoStato);
		manageFlusso.setAnagraficaStato(nuovoAnagraficaStato);

		return manageFlussoDao.saveAndFlush(manageFlusso);
	}

	@Override
	public void completaFlusso(String codRequestToken, String uploadPath, String nomeFile, Long dimensioneFile) {

		ManageFlusso manageFlusso = manageFlussoDao.findByCodRequestToken(codRequestToken);
		if (manageFlusso == null)
			return;

		manageFlusso.setDeNomeFile(nomeFile);
		manageFlusso.setDePercorsoFile(uploadPath);
		manageFlusso.setNumDimensioneFileScaricato(dimensioneFile);

		manageFlussoDao.saveAndFlush(manageFlusso);
	}

	@Override
	public void completaFlussoPoste(UploadEsitoDto uploadEsitoDto, String uploadPath) throws ParseException {
		ManageFlusso manageFlusso = manageFlussoDao.findByCodRequestToken(uploadEsitoDto.getCodRequestToken());
		if (manageFlusso == null)
			return;

		manageFlusso.setDeNomeFile(uploadEsitoDto.getNomeFile());
		manageFlusso.setDePercorsoFile(uploadPath);
		manageFlusso.setNumDimensioneFileScaricato(uploadEsitoDto.getDimensioneFile());

		manageFlusso.setIdentificativoPsp(env.getProperty("mypivot.psp.postecom"));
		manageFlusso.setCodIdentificativoFlusso(uploadEsitoDto.getCod_identificativo_flusso());
		manageFlusso.setDtDataOraFlusso(Constants.DDMMYYYY_HHMMSS.parse(uploadEsitoDto.getDt_data_ora_flusso()));

		manageFlussoDao.saveAndFlush(manageFlusso);

		InfoFlussoPosteWeb infoFlussoPosteWeb = new InfoFlussoPosteWeb();
		infoFlussoPosteWeb.setManageFlusso(manageFlusso);
		infoFlussoPosteWeb
				.setCodIdentificativoUnivocoRegolamento(uploadEsitoDto.getCod_identificativo_univoco_regolamento());
		infoFlussoPosteWeb.setDtDataRegolamento(Constants.DDMMYYYY.parse(uploadEsitoDto.getDt_data_regolamento()));
		infoFlussoPosteWeb
				.setNumImportoTotalePagamenti(new BigDecimal(uploadEsitoDto.getNum_importo_totale_pagamenti()));
		infoFlussoPosteWeb.setDtCreazione(new Date());
		infoFlussoPosteWeb.setDtUltimaModifica(new Date());

		infoFlussoPosteWebDao.saveAndFlush(infoFlussoPosteWeb);
	}

	@Override
	public ManageFlusso getByRequestToken(String requestToken) {
		return manageFlussoDao.findByCodRequestToken(requestToken);
	}

	@Override
	public void completaFlussoGiornaleDiCassaCsv(UploadEsitoDto uploadEsitoDto, String uploadPath) throws Exception {
		ManageFlusso manageFlusso = manageFlussoDao.findByCodRequestToken(uploadEsitoDto.getCodRequestToken());
		if (manageFlusso == null)
			throw new Exception("Errore durante il recupero del flusso di upload. Il flusso non è stato trovato");

		this.completaFlusso(uploadEsitoDto.getCodRequestToken(), uploadPath, uploadEsitoDto.getNomeFile(),
				uploadEsitoDto.getDimensioneFile());

		InfoMappingTesoreria infoMappingTesoreria = new InfoMappingTesoreria();
		infoMappingTesoreria.setManageFlusso(manageFlusso);
		infoMappingTesoreria.setPosDeAnnoBolletta(Integer.parseInt(uploadEsitoDto.getDe_anno_bolletta()));
		infoMappingTesoreria.setPosCodBolletta(Integer.parseInt(uploadEsitoDto.getCod_bolletta()));
		infoMappingTesoreria.setPosDtContabile(Integer.parseInt(uploadEsitoDto.getDt_contabile()));
		infoMappingTesoreria.setPosDeDenominazione(Integer.parseInt(uploadEsitoDto.getDe_denominazione()));
		infoMappingTesoreria.setPosDeCausale(Integer.parseInt(uploadEsitoDto.getDe_causale()));
		infoMappingTesoreria.setPosNumImporto(Integer.parseInt(uploadEsitoDto.getNum_importo()));
		infoMappingTesoreria.setPosDtValuta(Integer.parseInt(uploadEsitoDto.getDt_valuta()));
		
		Date now = new Date();
		infoMappingTesoreria.setDtCreazione(now);
		infoMappingTesoreria.setDtUltimaModifica(now);
		
		infoMappingTesoreriaDao.saveAndFlush(infoMappingTesoreria);
	}

}
