package it.regioneveneto.mygov.payment.mypivot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import it.regioneveneto.mygov.payment.mypivot.dao.AnagraficaStatoDao;
import it.regioneveneto.mygov.payment.mypivot.dao.EnteDao;
import it.regioneveneto.mygov.payment.mypivot.dao.PrenotazioneFlussoRiconciliazioneDao;
import it.regioneveneto.mygov.payment.mypivot.dao.UtenteDao;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.FlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;
import it.regioneveneto.mygov.payment.mypivot.domain.po.AnagraficaStato;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Ente;
import it.regioneveneto.mygov.payment.mypivot.domain.po.PrenotazioneFlussoRiconciliazione;
import it.regioneveneto.mygov.payment.mypivot.domain.po.Utente;
import it.regioneveneto.mygov.payment.mypivot.domain.to.PrenotazioneFlussoRiconciliazioneTO;
import it.regioneveneto.mygov.payment.mypivot.service.OperatoreEnteTipoDovutoService;
import it.regioneveneto.mygov.payment.mypivot.service.PrenotazioneFlussoRiconciliazioneService;
import it.regioneveneto.mygov.payment.mypivot.service.exception.NessunTipoDovutoAttivoException;
import it.regioneveneto.mygov.payment.mypivot.service.exception.TipoDovutoNonValidoPerUtenteException;
import it.regioneveneto.mygov.payment.mypivot.service.utils.ModelMapperUtil;
import it.regioneveneto.mygov.payment.mypivot.utils.Constants;
import it.regioneveneto.mygov.payment.mypivot.utils.Utilities;

@Service
public class PrenotazioneFlussoRiconciliazioneServiceImpl implements PrenotazioneFlussoRiconciliazioneService {
	@Autowired
	private ModelMapperUtil modelMapperUtil;

	@Autowired
	private PrenotazioneFlussoRiconciliazioneDao prenotazioneFlussoRiconciliazioneDao;

	@Autowired
	private UtenteDao utenteDao;

	@Autowired
	private EnteDao enteDao;

	@Autowired
	private AnagraficaStatoDao anagraficaStatoDao;

	@Autowired
	private OperatoreEnteTipoDovutoService operatoreEnteTipoDovutoService;

	@Autowired
	private MessageSource messageSource;

	private static final Logger LOG = Logger.getLogger(PrenotazioneFlussoRiconciliazioneServiceImpl.class);

	@Override
	public PrenotazioneFlussoRiconciliazioneTO insertPrenotazioneFlussoRiconciliazione(String codIpaEnte,
			String codFedUserId, String codTipoStato, String deTipoStato, String codCodiceClassificazione,
			String deTipoDovuto, String codIdUnivocoVersamento, String codIdUnivocoRendicontazione,
			Date dtDataUltimoAggiornamentoDa, Date dtDataUltimoAggiornamentoA, Date dtDataEsecuzioneDa,
			Date dtDataEsecuzioneA, Date dtDataEsitoDa, Date dtDataEsitoA, Date dtDataRegolamentoDa,
			Date dtDataRegolamentoA, Date dtDataContabileDa, Date dtDataContabileA, Date dtDataValutaDa,
			Date dtDataValutaA, String codIdUnivocoDovuto, String codIdUnivocoRiscossione, String codIdUnivocoPagatore,
			String deAnagraficaPagatore, String codIdUnivocoVersante, String deAnagraficaVersante,
			String deDenominazioneAttestante, String deOrdinante, String codIdRegolamento, String codContoTesoreria,
			String deImportoTesoreria, String deCausale, String versioneTracciato) {

		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);

		AnagraficaStato anagraficaStato = anagraficaStatoDao.findByCodStatoAndDeTipoStato(codTipoStato, deTipoStato);

		PrenotazioneFlussoRiconciliazione prenotazioneFlussoRiconciliazione = new PrenotazioneFlussoRiconciliazione();

		prenotazioneFlussoRiconciliazione.setEnte(ente);
		prenotazioneFlussoRiconciliazione.setUtente(utente);
		prenotazioneFlussoRiconciliazione.setAnagraficaStato(anagraficaStato);
		prenotazioneFlussoRiconciliazione.setCodCodiceClassificazione(codCodiceClassificazione);

		String codRequestToken = UUID.randomUUID().toString();
		prenotazioneFlussoRiconciliazione.setCodRequestToken(codRequestToken);

		if (Utilities.isWSUSer(codFedUserId)) {
			if (StringUtils.isNotBlank(deTipoDovuto)) {
				prenotazioneFlussoRiconciliazione.setDeTipoDovuto(deTipoDovuto);
			}
		} else {
			if (Utilities.isTipoDovutoAbilitatoPerClassificazione(codCodiceClassificazione)) {
				if (StringUtils.isNotBlank(deTipoDovuto)) {
					if (operatoreEnteTipoDovutoService.isCodTipoDovutoValidoPerOperatoreAndCodIpaEnte(codFedUserId,
							codIpaEnte, deTipoDovuto)) {
						prenotazioneFlussoRiconciliazione.setDeTipoDovuto(deTipoDovuto);
					} else {
						String message = "Tipo dovuto non valido per l'operatore [ " + utente.getCodFedUserId()
								+ " ] per l'ente [ " + ente.getCodIpaEnte() + " ]";
						LOG.error(message);
						throw new TipoDovutoNonValidoPerUtenteException(message);
					}
				} else {
					List<String> listaCodTipoDovuto = operatoreEnteTipoDovutoService
							.getListaCodTipoDovutoForOperatoreAndCodIpaEnte(codFedUserId, codIpaEnte);
					if (CollectionUtils.isNotEmpty(listaCodTipoDovuto)) {
						String finalCodTd = "";
						for (String codTipo : listaCodTipoDovuto) {
							finalCodTd = finalCodTd + codTipo + "|";
						}
						if (finalCodTd.endsWith("|"))
							finalCodTd = finalCodTd.substring(0, finalCodTd.length() - 1);
						prenotazioneFlussoRiconciliazione.setDeTipoDovuto(finalCodTd);
					} else {
						String message = "Nessun tipo dovuto attivo associato all'operatore [ "
								+ utente.getCodFedUserId() + " ] per l'ente [ " + ente.getCodIpaEnte() + " ]";
						LOG.error(message);
						throw new NessunTipoDovutoAttivoException(message);
					}
				}
			}
		}

		if (StringUtils.isNotBlank(codIdUnivocoVersamento)) {
			prenotazioneFlussoRiconciliazione.setCodIdUnivocoVersamento(codIdUnivocoVersamento);
		}

		if (StringUtils.isNotBlank(codIdUnivocoRendicontazione)) {
			prenotazioneFlussoRiconciliazione.setCodIdUnivocoRendicontazione(codIdUnivocoRendicontazione);
		}

		if (dtDataUltimoAggiornamentoDa != null) {
			prenotazioneFlussoRiconciliazione.setDtDataUltimoAggiornamentoDa(dtDataUltimoAggiornamentoDa);
		}

		if (dtDataUltimoAggiornamentoA != null) {
			prenotazioneFlussoRiconciliazione.setDtDataUltimoAggiornamentoA(dtDataUltimoAggiornamentoA);
		}

		if (dtDataEsecuzioneDa != null) {
			prenotazioneFlussoRiconciliazione.setDtDataEsecuzioneDa(dtDataEsecuzioneDa);
		}

		if (dtDataEsecuzioneA != null) {
			prenotazioneFlussoRiconciliazione.setDtDataEsecuzioneA(dtDataEsecuzioneA);
		}

		if (dtDataEsitoDa != null) {
			prenotazioneFlussoRiconciliazione.setDtDataEsitoDa(dtDataEsitoDa);
		}

		if (dtDataEsitoA != null) {
			prenotazioneFlussoRiconciliazione.setDtDataEsitoA(dtDataEsitoA);
		}

		if (dtDataRegolamentoDa != null) {
			prenotazioneFlussoRiconciliazione.setDtDataRegolamentoDa(dtDataRegolamentoDa);
		}

		if (dtDataRegolamentoA != null) {
			prenotazioneFlussoRiconciliazione.setDtDataRegolamentoA(dtDataRegolamentoA);
		}

		if (dtDataContabileDa != null) {
			prenotazioneFlussoRiconciliazione.setDtDataContabileDa(dtDataContabileDa);
		}

		if (dtDataContabileA != null) {
			prenotazioneFlussoRiconciliazione.setDtDataContabileA(dtDataContabileA);
		}

		if (dtDataValutaDa != null) {
			prenotazioneFlussoRiconciliazione.setDtDataValutaDa(dtDataValutaDa);
		}

		if (dtDataValutaA != null) {
			prenotazioneFlussoRiconciliazione.setDtDataValutaA(dtDataValutaA);
		}

		if (StringUtils.isNotBlank(codIdUnivocoDovuto)) {
			prenotazioneFlussoRiconciliazione.setCodIdUnivocoDovuto(codIdUnivocoDovuto);
		}

		if (StringUtils.isNotBlank(codIdUnivocoRiscossione)) {
			prenotazioneFlussoRiconciliazione.setCodIdUnivocoRiscossione(codIdUnivocoRiscossione);
		}

		if (StringUtils.isNotBlank(codIdUnivocoPagatore)) {
			prenotazioneFlussoRiconciliazione.setCodIdUnivocoPagatore(codIdUnivocoPagatore);
		}

		if (StringUtils.isNotBlank(deAnagraficaPagatore)) {
			prenotazioneFlussoRiconciliazione.setDeAnagraficaPagatore(deAnagraficaPagatore);
		}

		if (StringUtils.isNotBlank(codIdUnivocoVersante)) {
			prenotazioneFlussoRiconciliazione.setCodIdUnivocoVersante(codIdUnivocoVersante);
		}

		if (StringUtils.isNotBlank(deAnagraficaVersante)) {
			prenotazioneFlussoRiconciliazione.setDeAnagraficaVersante(deAnagraficaVersante);
		}

		if (StringUtils.isNotBlank(deDenominazioneAttestante)) {
			prenotazioneFlussoRiconciliazione.setDeDenominazioneAttestante(deDenominazioneAttestante);
		}

		if (StringUtils.isNotBlank(deOrdinante)) {
			prenotazioneFlussoRiconciliazione.setDeOrdinante(deOrdinante);
		}

		if (StringUtils.isNotBlank(codIdRegolamento)) {
			prenotazioneFlussoRiconciliazione.setCodIdRegolamento(codIdRegolamento);
		}

		if (StringUtils.isNotBlank(codContoTesoreria)) {
			prenotazioneFlussoRiconciliazione.setCodContoTesoreria(codContoTesoreria);
		}

		if (StringUtils.isNotBlank(deImportoTesoreria)) {
			prenotazioneFlussoRiconciliazione.setDeImportoTesoreria(deImportoTesoreria);
		}

		if (StringUtils.isNotBlank(deCausale)) {
			prenotazioneFlussoRiconciliazione.setDeCausale(deCausale);
		}

		Date data = new Date();
		prenotazioneFlussoRiconciliazione.setDtCreazione(data);
		prenotazioneFlussoRiconciliazione.setDtUltimaModifica(data);

		if (dtDataUltimoAggiornamentoDa != null && dtDataUltimoAggiornamentoA == null) {
			Date dataSenzaTime = Utilities.removeTime(data);
			prenotazioneFlussoRiconciliazione.setDtDataUltimoAggiornamentoA(dataSenzaTime);
		}

		prenotazioneFlussoRiconciliazione.setVersioneTracciato(versioneTracciato);

		prenotazioneFlussoRiconciliazione = prenotazioneFlussoRiconciliazioneDao
				.saveAndFlush(prenotazioneFlussoRiconciliazione);

		PrenotazioneFlussoRiconciliazioneTO prenotazioneFlussoRiconciliazioneTO = modelMapperUtil
				.map(prenotazioneFlussoRiconciliazione, PrenotazioneFlussoRiconciliazioneTO.class);

		return prenotazioneFlussoRiconciliazioneTO;
	}

	public PrenotazioneFlussoRiconciliazioneTO getByCodRequestToken(final String codRequestToken) {
		Assert.notNull(codRequestToken, "CodRequestToken nullo");

		PrenotazioneFlussoRiconciliazione prenotazioneFlussoRiconciliazione = prenotazioneFlussoRiconciliazioneDao
				.findByCodRequestToken(codRequestToken);
		if (prenotazioneFlussoRiconciliazione != null) {
			PrenotazioneFlussoRiconciliazioneTO prenotazioneFlussoRiconciliazioneTO = modelMapperUtil
					.map(prenotazioneFlussoRiconciliazione, PrenotazioneFlussoRiconciliazioneTO.class);
			return prenotazioneFlussoRiconciliazioneTO;
		}
		return null;
	}

	public PageDto<FlussoExportDto> getPrenotazioneByCodRequestToken(final String codRequestToken) {
		Assert.notNull(codRequestToken, "CodRequestToken nullo");

		PrenotazioneFlussoRiconciliazione prenotazioneFlussoRiconciliazione = prenotazioneFlussoRiconciliazioneDao
				.findByCodRequestToken(codRequestToken);
		if (prenotazioneFlussoRiconciliazione != null) {
			//return prenotazioneFlussoRiconciliazione;
			
			List<PrenotazioneFlussoRiconciliazione> flussiList = new ArrayList<PrenotazioneFlussoRiconciliazione>();
			flussiList.add(prenotazioneFlussoRiconciliazione);
			Page<PrenotazioneFlussoRiconciliazione> flussiPage = null;
			List<FlussoExportDto> flussiDtos = mapEntitiesListToDtosListConstant(flussiList);

			PageDto<FlussoExportDto> flussiDtosPage = new PageDto<FlussoExportDto>();
			flussiDtosPage.setList(flussiDtos);
			flussiDtosPage.setNextPage(false);
			flussiDtosPage.setPage(1);
			flussiDtosPage.setPageSize(1);
			flussiDtosPage.setPreviousPage(false);
			flussiDtosPage.setTotalPages(1);
			flussiDtosPage.setTotalRecords(1);

			return flussiDtosPage;
			
		}
		return null;
	}
	
	public PrenotazioneFlussoRiconciliazioneTO getByCodIdFlusso(Long idFlusso) {
		Assert.notNull(idFlusso, "idFlusso nullo");
		
		PrenotazioneFlussoRiconciliazione prenotazioneFlussoRiconciliazione = prenotazioneFlussoRiconciliazioneDao
				.findById(idFlusso);
		if (prenotazioneFlussoRiconciliazione != null) {
			PrenotazioneFlussoRiconciliazioneTO prenotazioneFlussoRiconciliazioneTO = modelMapperUtil
					.map(prenotazioneFlussoRiconciliazione, PrenotazioneFlussoRiconciliazioneTO.class);
			return prenotazioneFlussoRiconciliazioneTO;
		}
		return null;

	}
	
	public PageDto<FlussoExportDto> getFlussoPage(String codFedUserId, String codIpaEnte, String fullTextSearch,
			Date dateFrom, Date dateTo, int page, int pageSize, Direction direction, String orderBy) {
		int pageToGet = 0;
		if (page > 0) {
			pageToGet = page - 1;
		}

		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);

		Pageable pageable = new PageRequest(pageToGet, pageSize, direction, orderBy);

		List<AnagraficaStato> states = new ArrayList<AnagraficaStato>();

		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_PRENOTATO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_ERRORE_EXPORT_FLUSSO_RICONCILIAZIONE,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_NUMERO_MASSIMO_EXPORT_RIGHE_CONSENTITO_SUPERATO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO_NESSUN_RECORD_TROVATO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_VERSIONE_TRACCIATO_ERRATA,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_IN_CARICO,
				Constants.DE_TIPO_STATO_ALL));

		dateTo = DateUtils.addDays(dateTo, 1);

		Page<PrenotazioneFlussoRiconciliazione> flussiPage = null;
		if (StringUtils.isBlank(fullTextSearch)) {
			flussiPage = prenotazioneFlussoRiconciliazioneDao.getPrenotazioneFlussoRiconciliazionePage(ente.getId(),
					utente.getId(), states, dateFrom, dateTo, pageable);
		} else {
			flussiPage = prenotazioneFlussoRiconciliazioneDao.getPrenotazioneFlussoRiconciliazionePage(ente.getId(),
					utente.getId(), states, fullTextSearch, dateFrom, dateTo, pageable);
		}

		List<FlussoExportDto> flussiDtos = mapEntitiesListToDtosList(flussiPage.getContent());

		PageDto<FlussoExportDto> flussiDtosPage = new PageDto<FlussoExportDto>();
		flussiDtosPage.setList(flussiDtos);
		flussiDtosPage.setNextPage(flussiPage.hasNextPage());
		flussiDtosPage.setPage(flussiPage.getNumber() + 1);
		flussiDtosPage.setPageSize(flussiPage.getSize());
		flussiDtosPage.setPreviousPage(flussiPage.hasPreviousPage());
		flussiDtosPage.setTotalPages(flussiPage.getTotalPages());
		flussiDtosPage.setTotalRecords(flussiPage.getTotalElements());

		return flussiDtosPage;
	}

	
	public PageDto<FlussoExportDto> getFlussoPage(String codFedUserId, String codIpaEnte, String fullTextSearch,
			Date dateFrom, Date dateTo, Pageable pageable) {

//		int pageToGet = 0;
//		if (page > 0) {
//			pageToGet = page - 1;
//		}

		Ente ente = enteDao.findByCodIpaEnte(codIpaEnte);
		Utente utente = utenteDao.findByCodFedUserIdIgnoreCase(codFedUserId);

//		Pageable pageable = new PageRequest(pageToGet, pageSize, direction, orderBy);

		List<AnagraficaStato> states = new ArrayList<AnagraficaStato>();

		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_PRENOTATO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_ERRORE_EXPORT_FLUSSO_RICONCILIAZIONE,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_NUMERO_MASSIMO_EXPORT_RIGHE_CONSENTITO_SUPERATO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO_NESSUN_RECORD_TROVATO,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(
				Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_VERSIONE_TRACCIATO_ERRATA,
				Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE));
		states.add(anagraficaStatoDao.findByCodStatoAndDeTipoStato(Constants.COD_TIPO_STATO_IN_CARICO,
				Constants.DE_TIPO_STATO_ALL));

		dateTo = DateUtils.addDays(dateTo, 1);

		Page<PrenotazioneFlussoRiconciliazione> flussiPage = null;
		if (StringUtils.isBlank(fullTextSearch)) {
			flussiPage = prenotazioneFlussoRiconciliazioneDao.getPrenotazioneFlussoRiconciliazionePage(ente.getId(),
					utente.getId(), states, dateFrom, dateTo, pageable);
		} else {
			flussiPage = prenotazioneFlussoRiconciliazioneDao.getPrenotazioneFlussoRiconciliazionePage(ente.getId(),
					utente.getId(), states, fullTextSearch, dateFrom, dateTo, pageable);
		}

		List<FlussoExportDto> flussiDtos = mapEntitiesListToDtosListConstant(flussiPage.getContent());

		PageDto<FlussoExportDto> flussiDtosPage = new PageDto<FlussoExportDto>();
		flussiDtosPage.setList(flussiDtos);
		flussiDtosPage.setNextPage(flussiPage.getNumber() + 1 < flussiPage.getTotalPages());
		flussiDtosPage.setPage(flussiPage.getNumber() + 1);
		flussiDtosPage.setPageSize(flussiPage.getSize());
		flussiDtosPage.setPreviousPage(flussiPage.getNumber() > 0);
		flussiDtosPage.setTotalPages(flussiPage.getTotalPages());
		flussiDtosPage.setTotalRecords(flussiPage.getTotalElements());

		return flussiDtosPage;
	}

	private List<FlussoExportDto> mapEntitiesListToDtosList(List<PrenotazioneFlussoRiconciliazione> entities) {
		List<FlussoExportDto> dtos = new ArrayList<FlussoExportDto>();
		for (PrenotazioneFlussoRiconciliazione flusso : entities) {
			FlussoExportDto flussoDto = new FlussoExportDto();

			flussoDto.setIdFlusso((flusso.getId().toString()));
			flussoDto.setNome(getNameFromPath(flusso.getDeNomeFileGenerato()));
			flussoDto.setPath(flusso.getDeNomeFileGenerato());

			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			flussoDto.setDataPrenotazione(fmt.print(new DateTime(flusso.getDtCreazione())));
			flussoDto.setOperatore(flusso.getUtente().getDeFirstname() + " " + flusso.getUtente().getDeLastname());

			flussoDto.setCodStato(flusso.getAnagraficaStato().getCodStato());
			flussoDto.setStato(flusso.getAnagraficaStato().getDeStato());

			if (StringUtils.isNotBlank(flusso.getDeNomeFileGenerato())) {
				Double fileSize_bytes = flusso.getNumDimensioneFileGenerato().doubleValue();
				Double fileSize_Kbytes = Math.round((fileSize_bytes / (double) 1024) * 100.0) / 100.0;
				Double fileSize_Mbytes = Math.round((fileSize_Kbytes / (double) 1024) * 100.0) / 100.0;
				Double fileSize_Gbytes = Math.round((fileSize_Mbytes / (double) 1024) * 100.0) / 100.0;

				if (fileSize_Kbytes < 1) {
					flussoDto.setDimensione(fileSize_bytes + " B");
				} else if (fileSize_Mbytes < 1) {
					flussoDto.setDimensione(fileSize_Kbytes + " KB");
				} else if (fileSize_Gbytes < 1) {
					flussoDto.setDimensione(fileSize_Mbytes + " MB");
				} else {
					flussoDto.setDimensione(fileSize_Gbytes + " GB");
				}
			}

			if (flusso.getAnagraficaStato().getDeTipoStato()
					.equals(Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE)
					&& flusso.getAnagraficaStato().getCodStato()
							.equals(Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO)) {
				flussoDto.setShowDownloadButton(true);
			} else {
				flussoDto.setShowDownloadButton(false);
			}
			flussoDto.setClassificazione(mapClassificazione(flusso.getCodCodiceClassificazione()));
			flussoDto.setVersioneTracciato(flusso.getVersioneTracciato());
			dtos.add(flussoDto);
		}
		return dtos;
	}

	private String getNameFromPath(String path) {
		if (StringUtils.isNotBlank(path)) {
			String[] parts = path.split("/");
			int n = parts.length;
			if (n != 0) {
				return parts[n - 1];
			}
		}
		return null;
	}

	private String mapClassificazione(String classificazioni) {
		if (StringUtils.isNotBlank(classificazioni)) {
			if (classificazioni.contains("|")) {
				String[] parts = classificazioni.split("|");
				if (StringUtils.isNotBlank(parts[0])) {
					return getDescrizioneFromCodiceClassificazione(parts[0]);
				}
			} else {
				return getDescrizioneFromCodiceClassificazione(classificazioni);
			}
		}
		return null;
	}

	private String getDescrizioneFromCodiceClassificazione(String cc) {
		switch (cc) {
		case Constants.COD_ERRORE_RT_NO_IUF:
			return messageSource.getMessage("mypivot.classificazioni.RT_NO_IUF", null, Locale.ITALY);
		case Constants.COD_ERRORE_IUF_NO_TES:
			return messageSource.getMessage("mypivot.classificazioni.IUF_NO_TES", null, Locale.ITALY);
		case Constants.COD_ERRORE_TES_NO_IUF_OR_IUV:
			return messageSource.getMessage("mypivot.classificazioni.TES_NO_IUF_OR_IUV", null, Locale.ITALY);
		case Constants.COD_ERRORE_IUV_NO_RT:
			return messageSource.getMessage("mypivot.classificazioni.IUV_NO_RT", null, Locale.ITALY);
		case Constants.COD_ERRORE_IUD_NO_RT:
			return messageSource.getMessage("mypivot.classificazioni.IUD_NO_RT", null, Locale.ITALY);
		case Constants.COD_ERRORE_RT_NO_IUD:
			return messageSource.getMessage("mypivot.classificazioni.RT_NO_IUD", null, Locale.ITALY);
		case Constants.COD_ERRORE_IUD_RT_IUF_TES:
			return messageSource.getMessage("mypivot.classificazioni.IUD_RT_IUF_TES", null, Locale.ITALY);
		case Constants.COD_ERRORE_RT_IUF:
			return messageSource.getMessage("mypivot.classificazioni.RT_IUF", null, Locale.ITALY);
		case Constants.COD_ERRORE_RT_IUF_TES:
			return messageSource.getMessage("mypivot.classificazioni.RT_IUF_TES", null, Locale.ITALY);
		case Constants.COD_ERRORE_IUD_RT_IUF:
			return messageSource.getMessage("mypivot.classificazioni.IUD_RT_IUF", null, Locale.ITALY);
		case Constants.COD_ERRORE_TES_NO_MATCH:
			return messageSource.getMessage("mypivot.classificazioni.TES_NO_MATCH", null, Locale.ITALY);
		case Constants.COD_ERRORE_IUF_TES_DIV_IMP:
			return messageSource.getMessage("mypivot.classificazioni.IUF_TES_DIV_IMP", null, Locale.ITALY);
		case Constants.COD_ERRORE_RT_TES:
			return messageSource.getMessage("mypivot.classificazioni.RT_TES", null, Locale.ITALY);
		default:
			return null;
		}
	}

	
	
	private List<FlussoExportDto> mapEntitiesListToDtosListConstant(List<PrenotazioneFlussoRiconciliazione> entities) {
		List<FlussoExportDto> dtos = new ArrayList<FlussoExportDto>();
		for (PrenotazioneFlussoRiconciliazione flusso : entities) {
			FlussoExportDto flussoDto = new FlussoExportDto();

			flussoDto.setIdFlusso((flusso.getId().toString()));
			flussoDto.setNome(getNameFromPath(flusso.getDeNomeFileGenerato()));
			flussoDto.setPath(flusso.getDeNomeFileGenerato());

			DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
			flussoDto.setDataPrenotazione(fmt.print(new DateTime(flusso.getDtCreazione())));
			flussoDto.setOperatore(flusso.getUtente().getDeFirstname() + " " + flusso.getUtente().getDeLastname());

			flussoDto.setCodStato(flusso.getAnagraficaStato().getCodStato());
			flussoDto.setStato(flusso.getAnagraficaStato().getDeStato());

			if (StringUtils.isNotBlank(flusso.getDeNomeFileGenerato())) {
				Double fileSize_bytes = flusso.getNumDimensioneFileGenerato().doubleValue();
				Double fileSize_Kbytes = Math.round((fileSize_bytes / (double) 1024) * 100.0) / 100.0;
				Double fileSize_Mbytes = Math.round((fileSize_Kbytes / (double) 1024) * 100.0) / 100.0;
				Double fileSize_Gbytes = Math.round((fileSize_Mbytes / (double) 1024) * 100.0) / 100.0;

				if (fileSize_Kbytes < 1) {
					flussoDto.setDimensione(fileSize_bytes + " B");
				} else if (fileSize_Mbytes < 1) {
					flussoDto.setDimensione(fileSize_Kbytes + " KB");
				} else if (fileSize_Gbytes < 1) {
					flussoDto.setDimensione(fileSize_Mbytes + " MB");
				} else {
					flussoDto.setDimensione(fileSize_Gbytes + " GB");
				}
			}

			if (flusso.getAnagraficaStato().getDeTipoStato()
					.equals(Constants.DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE)
					&& flusso.getAnagraficaStato().getCodStato()
							.equals(Constants.COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO)) {
				flussoDto.setShowDownloadButton(true);
			} else {
				flussoDto.setShowDownloadButton(false);
			}
			flussoDto.setClassificazione(mapClassificazioneConstant(flusso.getCodCodiceClassificazione()));
			flussoDto.setVersioneTracciato(flusso.getVersioneTracciato());
			dtos.add(flussoDto);
		}
		return dtos;
	}

	
	private String mapClassificazioneConstant(String classificazioni) {
		if (StringUtils.isNotBlank(classificazioni)) {
			if (classificazioni.contains("|")) {
				String[] parts = classificazioni.split("|");
				if (StringUtils.isNotBlank(parts[0])) {
					return getDescrizioneFromCodiceClassificazioneConstant(parts[0]);
				}
			} else {
				return getDescrizioneFromCodiceClassificazioneConstant(classificazioni);
			}
		}
		return null;
	}


	private String getDescrizioneFromCodiceClassificazioneConstant(String cc) {
		switch (cc) {
		case Constants.COD_ERRORE_RT_NO_IUF:
			return Constants.CLASSIFICAZIONI_RT_NO_IUF;
		case Constants.COD_ERRORE_IUF_NO_TES:
			return Constants.CLASSIFICAZIONI_IUF_NO_TES;
		case Constants.COD_ERRORE_TES_NO_IUF_OR_IUV:
			return Constants.CLASSIFICAZIONI_TES_NO_IUF_OR_IUV;
		case Constants.COD_ERRORE_IUV_NO_RT:
			return Constants.CLASSIFICAZIONI_IUV_NO_RT;
		case Constants.COD_ERRORE_IUD_NO_RT:
			return Constants.CLASSIFICAZIONI_IUD_NO_RT;
		case Constants.COD_ERRORE_RT_NO_IUD:
			return Constants.CLASSIFICAZIONI_RT_NO_IUD;
		case Constants.COD_ERRORE_IUD_RT_IUF_TES:
			return Constants.CLASSIFICAZIONI_IUD_RT_IUF_TES;
		case Constants.COD_ERRORE_RT_IUF:
			return Constants.CLASSIFICAZIONI_RT_IUF;
		case Constants.COD_ERRORE_RT_IUF_TES:
			return Constants.CLASSIFICAZIONI_RT_IUF_TES;
		case Constants.COD_ERRORE_IUD_RT_IUF:
			return Constants.CLASSIFICAZIONI_IUD_RT_IUF;
		case Constants.COD_ERRORE_TES_NO_MATCH:
			return Constants.CLASSIFICAZIONI_TES_NO_MATCH;
		case Constants.COD_ERRORE_IUF_TES_DIV_IMP:
			return Constants.CLASSIFICAZIONI_IUF_TES_DIV_IMP;
		case Constants.COD_ERRORE_RT_TES:
			return Constants.CLASSIFICAZIONI_RT_TES;
		default:
			return null;
		}
	}

}
