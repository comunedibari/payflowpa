package it.tasgroup.iris.facade.ejb.flussi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.erbweb.profilo.ProfiloInputVO;
import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoForHomePageVO;
import it.nch.idp.posizionedebitoria.CondizionePagamentoVO;
import it.nch.idp.posizionedebitoria.DistintaPosizioneDebitoriaDettaglioVO;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.pagamenti.nodopagamentispc.DataRichiestaRevocaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiRicevutaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DomainNotFoundException;
import it.nch.pagamenti.nodopagamentispc.DuplicatedRptException;
import it.nch.pagamenti.nodopagamentispc.RTNotFoundException;
import it.nch.pagamenti.nodopagamentispc.RptNotFoundException;
import it.nch.pagamenti.nodopagamentispc.SemanticException;
import it.nch.profile.IProfileManager;
import it.nch.utility.exception.WrongProfileException;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.creditore.StatistichePagamento;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.OperatoriBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.TributoEnteBusinessLocal;
import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.ddp.DDPBusinessLocal;
import it.tasgroup.iris.business.ejb.client.esportazioni.ExportBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.Esportazioni;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.CruscottoHomePageDTO;
import it.tasgroup.iris.dto.PagamentiInScadenzaDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.TributoEnteDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.dto.flussi.PagamentoDTOLightForReceipt;
import it.tasgroup.iris.dto.flussi.PrenotazioniDTO;
import it.tasgroup.iris.dto.flussi.RevocaPagamentoDTO;
import it.tasgroup.iris.dto.giornaleeventi.GiornaleEventiExtDTO;
import it.tasgroup.iris.dto.rest.filters.StatistichePagamentoFilter;
import it.tasgroup.iris.facade.ejb.anonymous.AnonymousPaymentFacadeDTOBuilder;
import it.tasgroup.iris.facade.ejb.authorization.VisibilityChecker;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.flussi.DistintePagamentoFacadeRemote;
import it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacadeLocal;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DDPDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DistintePagamentoDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.EntiDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.PagamentoDTOBuilder;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.services.util.enumeration.EnumCategoriaEvento;
import it.tasgroup.services.util.enumeration.EnumComponente;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumSottoTipoEvento;
import it.tasgroup.services.util.enumeration.EnumStatoExport;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoEventiNDP;
import it.tasgroup.services.util.enumeration.EnumTipoExport;
import it.tasgroup.services.util.enumeration.EnumTipoVersamento;
import it.tasgroup.services.util.enumeration.EnumTypeExportMassivo;

@Stateless(name = "DistintePagamentoFacade")
public class DistintePagamentoFacadeBean implements DistintePagamentoFacadeLocal, DistintePagamentoFacadeRemote {

	private static final Logger LOGGER = LogManager.getLogger(DistintePagamentoFacadeBean.class);
	private static final ConfigurationPropertyLoader cpl  = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
	
	private static final String TIPAGAMENTO_S = "S";

	@EJB(name = "DistintePagamentoBusiness")
	private DistintePagamentoBusinessLocal distintePagamentoBusinessBean;

	@EJB(name = "ExportBusiness")
	private ExportBusinessLocal exportBusinessBean;

	@EJB
	private DDPBusinessLocal ddpBusinessBean;

	@EJB
	private DDPFacadeLocal ddpFacadeLocal;

	@EJB
	private IntestatariBusinessLocal intestatariBusinessBean;

    @EJB
	private IrisCacheSingletonLocal irisCache;

    @EJB
    private DistintePagamentoFacadeLocal distintePagamentBean;

    @EJB
	private ConfPagamentiBusinessLocal cfgBusinessBean;

    @EJB(name = "TributoEnteBusiness")
	private TributoEnteBusinessLocal tributoEnteBusinessBean;
    
    @EJB(name = "GiornaleEventiBusiness")
    private GiornaleEventiBusinessLocal giornaleEventiBusinessBean;
	
	@EJB(name = "OperatoriBusiness")
	private OperatoriBusinessLocal operatoriBusinessBean;
    
    
    @Override
	public ContainerDTO getPagamentoEseguito(String idCondizione) {
		List<it.tasgroup.idp.rs.model.creditore.Pagamento> listaCondizioni = distintePagamentoBusinessBean.getPagamentoEseguito(idCondizione);
		ContainerDTO inputDTO = new ContainerDTO();
		
		inputDTO.setOutputDTOList(listaCondizioni);
		return inputDTO;
	}


	@Override
	public ContainerDTO readGestioneFlussiListLight(ContainerDTO inputDTO) {

		List<GestioneFlussi> listaGestioneFlussi = distintePagamentoBusinessBean.getGestioneFlussiAll(inputDTO);
		List<DistintePagamentoDTOLight> dtos = DistintePagamentoDTOBuilder.populateListDistintePagamentoDTOLight(listaGestioneFlussi);
		inputDTO.setOutputDTOList(dtos);
		return inputDTO;
	}

	@Override
	public ContainerDTO readDistinteNDP(ContainerDTO inputDTO) {

		List<GestioneFlussi> listaGestioneFlussi = distintePagamentoBusinessBean.getDistinteNDP(inputDTO);
		List<DistintePagamentoDTOLight> dtos = DistintePagamentoDTOBuilder.populateListDistintePagamentoDTOLight(listaGestioneFlussi);
		inputDTO.setOutputDTOList(dtos);
		return inputDTO;
	}

	@Override
	public List<DistintePagamentoDTOLight> readGestioneFlussiListLightByCodTransazione(String codTransazione) {

		List<GestioneFlussi> listaGestioneFlussi = distintePagamentoBusinessBean.getDistintaByCodTransazione(codTransazione);
		List<DistintePagamentoDTOLight> dtos = DistintePagamentoDTOBuilder.populateListDistintePagamentoDTOLight(listaGestioneFlussi);

		return dtos;
	}

	@Override
	public ContainerDTO getPagamentiCreditore(ContainerDTO inputDTO, Map<String, String> mapEnti, HashMap<String, HashMap<String, String>> mapTipiTributo) {
		List<Pagamenti> listaPagamenti = distintePagamentoBusinessBean.getPagamenti(inputDTO);
		List<DistintePagamentoDTOLight> dtos = PagamentoDTOBuilder.populateListPagamentoDTOLight(listaPagamenti, mapEnti, mapTipiTributo);

		inputDTO.setOutputDTOList(dtos);
		return inputDTO;
	}
	
	@Override
	public ContainerDTO getPagamentiEseguiti(ContainerDTO inputDTO, String flagInCorso, String idCondizione) {
		List<it.tasgroup.idp.rs.model.creditore.Pagamento> listaCondizioni = distintePagamentoBusinessBean.getPagamentiEseguiti(inputDTO, flagInCorso, idCondizione);

		inputDTO.setOutputDTOList(listaCondizioni);
		return inputDTO;
	}


	@Override
	public ContainerDTO getPagamentiCreditore(ContainerDTO inputDTO) {
		List<it.tasgroup.idp.rs.model.creditore.Pagamento> listaCondizioni = distintePagamentoBusinessBean.getPagamentiCreditore(inputDTO);


		inputDTO.setOutputDTOList(listaCondizioni);
		return inputDTO;
	}
		
		@Override
		public ContainerDTO getCondizioniCreditore(ContainerDTO inputDTO) {
				List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> listaCondizioni = distintePagamentoBusinessBean.getCondizioniCreditore(inputDTO);
				inputDTO.setOutputDTOList(listaCondizioni);
				return inputDTO;
		}


	@Override
	public ContainerDTO getQuietanzePagamenti(ContainerDTO inputDTO) {
		List<DocumentiRepository> docs = new ArrayList<DocumentiRepository>();
		String vo = (String) inputDTO.getInputDTO();
		if (vo != null && !vo.trim().equals("")) {
			Long idDoc = new Long(vo);
			DocumentiRepository doc = ddpBusinessBean.readDocumentiRepositoryById(idDoc);
			docs.add(doc);
		}

		inputDTO.setOutputDTOList(docs);
		return inputDTO;
	}

	@Override
	public ContainerDTO contaQuietanzePagamenti(ContainerDTO inputDTO) {
//		DistintePagamentoRicercaVO vo = (DistintePagamentoRicercaVO) inputDTO.getInputDTO();
//		List<DocumentiRepository> docs = new ArrayList<DocumentiRepository>();
		inputDTO.addOutputDTO(distintePagamentoBusinessBean.contaPagamentiQuietanzati(inputDTO));

		return inputDTO;
	}
	
	@Override
	public ContainerDTO contaRicevuteTelematiche(ContainerDTO inputDTO) {
		inputDTO.addOutputDTO(distintePagamentoBusinessBean.contaRicevuteTelematiche(inputDTO));
		return inputDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public ContainerDTO creaPrenotazioneEsporta(IProfileManager profileManager, ContainerDTO inputDTO, String cfOperatore) throws Exception {
		Prenotazioni pren = exportBusinessBean.creaPrenotazione(inputDTO, profileManager, cfOperatore);
		LOGGER.debug("creaPrenotazioneEsporta - salvata la prenotazione");
		ContainerDTO out = new ContainerDTO();
		out.setOutputDTO(pren);
		return out;
	}

	@Override
	public void esportaPagamenti(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception {

		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamenti - salvata la prenotazione");

		distintePagamentoBusinessBean.esportaPagamentiFull(inputDTO, pren, mapEnti, mapTipiTiributo, cfOperatore, locale);
		LOGGER.debug("esportaPagamenti - lanciato l'exporter asincrono");
	}
	
	@Override
	public void esportaCondizioni (IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
								 Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception {
		
		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamenti - salvata la prenotazione");
		
		distintePagamentoBusinessBean.esportaCondizioniCreditoreFull(inputDTO, pren, mapEnti, mapTipiTiributo, cfOperatore, locale);
		LOGGER.debug("esportaPagamenti - lanciato l'exporter asincrono");
	}
	
	
	@Override
	public void esportaPagamentiCSVStandard(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, EnumExportSTDFormat stdFormat, Locale locale) throws Exception {

		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamentiCSVStandard - salvata la prenotazione");

		distintePagamentoBusinessBean.esportaPagamentiCSVStandard(inputDTO, pren, mapEnti, mapTipiTiributo, cfOperatore, stdFormat, locale);
		LOGGER.debug("esportaPagamentiCSVStandard - lanciato l'exporter asincrono");
	}
	
	@Override
	public void esportaCondizioniCSVStandard(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapEnti,
											Map<String, String> mapTipiTiributo, String cfOperatore, EnumExportSTDFormat stdFormat, Locale locale) throws Exception {
		
		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamentiCSVStandard - salvata la prenotazione");
		
		distintePagamentoBusinessBean.esportaCondizioniCSVStandard(inputDTO, pren, mapEnti, mapTipiTiributo, cfOperatore, stdFormat, locale);
		LOGGER.debug("esportaPagamentiCSVStandard - lanciato l'exporter asincrono");
	}
	
	@Override
	public void esportaRiaccrediti(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, String cfOperatore, Locale locale)
			throws Exception {

		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaRiaccrediti - salvata la prenotazione");

		distintePagamentoBusinessBean.esportaRiaccreditiFull(inputDTO, pren, mapListaCampi, cfOperatore, locale);
		LOGGER.debug("esportaRiaccrediti - lanciato l'exporter asincrono");
	}

	@Override
	public void esportaAvvisi(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception {

		// distintePagamentoBusinessBean.creaPrenotazione(inputDTO,profileManager);
		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamenti - salvata la prenotazione");

		distintePagamentoBusinessBean.esportaAvvisiFull(inputDTO, pren, mapListaCampi, mapEnti, mapTipiTiributo, cfOperatore, locale);
		LOGGER.debug("esportaPagamenti - lanciato l'exporter asincrono");
	}

	@Override
	public void esportaAvvisiCSVStandard(IProfileManager profileManager, ContainerDTO inputDTO, Map<String, String> mapListaCampi, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore, Locale locale) throws Exception {

		// distintePagamentoBusinessBean.creaPrenotazione(inputDTO,profileManager);
		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamenti - salvata la prenotazione");

		distintePagamentoBusinessBean.esportaAvvisiCSVStandard(inputDTO, pren, mapListaCampi, mapEnti, mapTipiTiributo, cfOperatore, locale);
		LOGGER.debug("esportaPagamenti - lanciato l'exporter asincrono");
	}

	@Override
	public DTO listaPreferenze(IProfileManager profilo, DTO dto) {
		Tracer.info(this.getClass().getName(), "Start...", "");
		DTOImpl dtoReturn = new DTOImpl();
		try {
			Tracer.debug(this.getClass().getName(), "listaPreferenze", "inizio", null);

			ProfiloInputVO vo = (ProfiloInputVO) dto.getVO();

			List list = exportBusinessBean.listaPreferenze(profilo, vo);


			HashMap ret = exportBusinessBean.listaPreferenzeExport(list, vo);

			dtoReturn.setVO(ret);

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "errore!!!!!!!!!!!!!!", e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BO_0012);
		}

		return dtoReturn;
	}

	@Override
	public DTO aggiornaPreferenze(IProfileManager profilo, DTO dto) {
		DTOImpl dtoReturn = new DTOImpl();
		Boolean allright = Boolean.TRUE;
		try {
			Tracer.info(this.getClass().getName(), "aggiornaPreferenze", "inizio", null);

			exportBusinessBean.aggiornaPreferenze(profilo, (PreferenzaEsportazioneVO) dto.getVO());

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "errore aggiornaPreferenze", e.getMessage(), e);
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BO_0012);
			allright = Boolean.FALSE;
		}
		dtoReturn.setVO(allright);
		return dtoReturn;
	}

	@Override
	@Asynchronous
	public void esportaQuietanzePagamenti(Locale locale, IProfileManager profileManager, ContainerDTO inputDTO, String cfOperatore, EnumTypeExportMassivo typo) throws BusinessConstraintException {
		Prenotazioni pren = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaQuietanzePagamenti - salvata la prenotazione");
		List<Pagamenti> listaPagamenti= null;
		if ((typo.equals(EnumTypeExportMassivo.RT))) {
			listaPagamenti = distintePagamentoBusinessBean.getPagamentiFullRicevuteTelematiche(inputDTO);
		} else {
			listaPagamenti = distintePagamentoBusinessBean.getPagamentiFull(inputDTO);
		}
		List<DocumentoRepositoryDTO> lDoc = new ArrayList<DocumentoRepositoryDTO>();
		try {
			if (typo.equals(EnumTypeExportMassivo.RT)) {
				lDoc = getRicevuteTelematiche(listaPagamenti);
				if (lDoc.isEmpty()) {
					DocumentoRepositoryDTO doc = new DocumentoRepositoryDTO();
					ResourceBundle bundle = ResourceBundle.getBundle("messages.services.NewMessageBundle", locale);
					String fileName = bundle.getString("esportaRT.is.empty.filename") + new SimpleDateFormat("_yyyyMMddHHmmss").format(new Date());
					doc.setFileName(fileName);
					doc.setId(new Long(0));
					doc.setFileExtension("txt");
					String gls = bundle.getString("esportaRT.is.empty");
					doc.setContent(gls.getBytes());
		            lDoc.add(doc); 
				}
			} else {
				for (Pagamenti p : listaPagamenti) {
			    	//ora l'attesatazione del pagamento viene rilasciata 
		        	//a fronte del pagamento in stato ES
					if (p.getStPagamento() != null && (p.getStPagamento().equals("ES"))) { // && p.getIdDocumentoRepository() == null) {
						DocumentoRepositoryDTO dataCollection = ddpFacadeLocal.getRicevutaPagamento(locale, p.getId().toString(), p.getFlussoDistinta().getCodPagamento(),
								p.getFlussoDistinta().getUtentecreatore());
						lDoc.add(dataCollection);
					}
				}
			}
		} finally {
			// spostato nella finally perche' se scoppia il ciclo sulla listaDeiPagamenti non viene aggiunto e nel metodo
			// sottostante scoppia perche' lo cerca anche se vuoto empty
			inputDTO.addInputDTO(lDoc);
			try {
				distintePagamentoBusinessBean.esportaQuietanzeGestioneFlussiFull(inputDTO, pren, cfOperatore, typo);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error("errore durante la esportaQuietanzeGestioneFlussi: " + e.getMessage());
			}
		}
		LOGGER.debug("esportaQuietanzeGestioneFlussi - lanciato l'exporter asincrono");
	}
	
	
	private List<DocumentoRepositoryDTO> getRicevuteTelematiche(List<Pagamenti> pagList) {
		List<DocumentoRepositoryDTO> lDoc = new ArrayList<DocumentoRepositoryDTO>();
		try {
			for (Pagamenti p : pagList) {
				DocumentoRepositoryDTO repoDTO = distintePagamentoBusinessBean.getRicevuteTelematicheContent(p.getFlussoDistinta().getIuv(),p.getFlussoDistinta().getCodTransazionePSP(),p.getFlussoDistinta().getIdentificativoFiscaleCreditore());
				lDoc.add(repoDTO);
			}
		} catch( Exception e) {
			LOGGER.error("Exception per ricevute telematiche");
			e.printStackTrace();
			LOGGER.error("errore durante la getRicevuteTelematiche in " + this.getClass().getName() + "::getRicevuteTelematiche: " + e.getMessage());
		}
		return lDoc;
	}

	
	private List<DocumentoRepositoryDTO> getRicevuteQuietanzePagamenti(Locale locale, List<Pagamenti> listaPagamenti) {
		List<DocumentoRepositoryDTO> lDoc = new ArrayList<DocumentoRepositoryDTO>();
		try {
			for (Pagamenti p : listaPagamenti) {
		    	//ora l'attesatazione del pagamento viene rilasciata 
	        	//a fronte del pagamento in stato ES
				if (p.getStPagamento() != null && (p.getStPagamento().equals("ES"))) { // && p.getIdDocumentoRepository() == null) {
					DocumentoRepositoryDTO dataCollection = ddpFacadeLocal.getRicevutaPagamento(locale, p.getId().toString(), p.getFlussoDistinta().getCodPagamento(),
							p.getFlussoDistinta().getUtentecreatore());
					lDoc.add(dataCollection);
				}
			}
		} catch (IllegalStateException e) {
			LOGGER.error("IllegalStateException per quietanza del pagamento");
			e.printStackTrace();
			LOGGER.error("errore durante la getQuietanzaPagamento in getRicevuteQuietanzePagamenti: " + e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Errore generico per quietanza");
			e.printStackTrace();
			LOGGER.error("errore generico  durante la getQuietanzaPagamento in getRicevuteQuietanzePagamenti: " + e.getMessage());
		}
		return lDoc;
	}

	@Override
	public ContainerDTO listaPrenotazioni(ContainerDTO inputDTO) {

		List<Prenotazioni> listaPrenotazioni = exportBusinessBean.listaPrenotazioni(inputDTO);
		List<PrenotazioniDTO> dtos = DistintePagamentoDTOBuilder.populateListPrenotazioniDTOLight(listaPrenotazioni);
		inputDTO.setOutputDTOList(dtos);
		return inputDTO;
	}
	
	@Override
	public ContainerDTO listaPrenotazioniStorico(ContainerDTO inputDTO) {

		List<Prenotazioni> listaPrenotazioni = exportBusinessBean.listaPrenotazioniStorico(inputDTO);
		List<PrenotazioniDTO> dtos = DistintePagamentoDTOBuilder.populateListPrenotazioniDTOLight(listaPrenotazioni);
		inputDTO.setOutputDTOList(dtos);
		return inputDTO;
	}
	
	@Override
	public ContainerDTO getDocEsportazionePreferenza(IProfileManager profileManager, ContainerDTO inputDTO) throws Exception {
		List<Esportazioni> docs = new ArrayList<Esportazioni>();
		String vo = (String) inputDTO.getInputDTO();
		if (vo != null && !vo.trim().equals("")) {
			Long idPren = new Long(vo);
			Esportazioni doc = exportBusinessBean.getEsportazioni(idPren);
			docs.add(doc);
		}
		
		
		inputDTO.setOutputDTOList(docs);
		
		final List<Esportazioni> outputDTOList = inputDTO.getOutputDTOList();
		
		// check permissions
		for (Esportazioni e : outputDTOList) {
			final String operatoreInserimento = e.getPrenotazione().getOpInserimento();
			final OperatoriPojo operatore = operatoriBusinessBean.getOperatoreById(profileManager.getUsername());
			if (!"IDP".equals(operatoreInserimento) && !operatore.getUsername().equals(operatoreInserimento)) {
				throw new WrongProfileException();
			}
		}
		
		
		return inputDTO;
	}


	@Override
	public void aggiornaEsito(long idFlusso, StatiPagamentiIris stato, String tranId, String deOperazione , String tipoIdentifAttestante, String identifAttestante, String descrizAttestante) {
		distintePagamentoBusinessBean.aggiornaEsito(idFlusso,stato, tranId, deOperazione , tipoIdentifAttestante, identifAttestante, descrizAttestante);
	}

	@Override
	public String aggiornaEsitoDaRT(DatiRicevutaPagamentoVO datiRicevutaPagamento) throws DomainNotFoundException, RptNotFoundException, DuplicatedRptException {
		GestioneFlussi gf = distintePagamentoBusinessBean.aggiornaEsitoDaRT(datiRicevutaPagamento);
		return gf != null ? gf.getStato() : null;
	}

	public void manageRichiestaRevoca(DataRichiestaRevocaPagamentoVO richiestaRevoca) throws SemanticException, RTNotFoundException {
		distintePagamentoBusinessBean.manageRichiestaRevoca(richiestaRevoca);
	}
	// Metodi nuova HP BO e Ente - START
	
	@Override
	public List<CruscottoHomePageDTO> riepilogoIncassi(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk) {
		List<Object[]> riepilogoList = distintePagamentoBusinessBean.riepilogoIncassi(idEnte, annoRiferimento, cdTrbEntePk);
		List<CruscottoHomePageDTO> list = getRiepilogoList(riepilogoList);

		return list;
	}

	@Override
	public List<CruscottoHomePageDTO> riepilogoIncassi(String idEnte, String annoRiferimento) {
		return riepilogoIncassi(idEnte, annoRiferimento, null);
	}
	
	@Override
	public List<CruscottoHomePageDTO> riepilogoIncassi(String idEnte) {
		return riepilogoIncassi(idEnte, null);
	}

	@Override
	public List<CruscottoHomePageDTO> riepilogoPagamenti(String idEnte, String annoRiferimento) {
		List<Object[]> riepilogoList = distintePagamentoBusinessBean.riepilogoPagamenti(idEnte, annoRiferimento);
		List<CruscottoHomePageDTO> list = getRiepilogoList(riepilogoList);

		return list;
	}
	
	@Override
	public List<CruscottoHomePageDTO> riepilogoPagamenti(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk) {
		List<Object[]> riepilogoList = distintePagamentoBusinessBean.riepilogoPagamenti(idEnte, annoRiferimento, cdTrbEntePk);
		List<CruscottoHomePageDTO> list = getRiepilogoList(riepilogoList);

		return list;
	}
	
	@Override
	public List<CruscottoHomePageDTO> riepilogoPagamenti(String idEnte) {
		return riepilogoPagamenti(idEnte, null);
	}

	@Override
	public List<CruscottoHomePageDTO> riepilogoPosizioniAttese(String idEnte, Integer nrGiorni) {
		List<Object[]> riepilogoList = distintePagamentoBusinessBean.riepilogoPosizioniAttese(idEnte, nrGiorni);
		List<CruscottoHomePageDTO> list = getRiepilogoList(riepilogoList);

		return list;
	}

	@Override
	public List<CruscottoHomePageDTO> riepilogoPosizioniNonAttese(String idEnte, Integer nrGiorni) {
		List<Object[]> riepilogoList = distintePagamentoBusinessBean.riepilogoPosizioniNonAttese(idEnte, nrGiorni);
		List<CruscottoHomePageDTO> list = getRiepilogoList(riepilogoList);

		return list;
	}

	private CruscottoHomePageDTO populateCruscottoHomePageDTO(Object[] row) {
		CruscottoHomePageDTO item = new CruscottoHomePageDTO();

		item.setTipoDebito((String) row[0]);
		item.setNumero(((Number) row[1]).intValue());
		item.setImporto(((BigDecimal) row[2]));

		return item;
	}

	private List<CruscottoHomePageDTO> getRiepilogoList(List<Object[]> riepilogoList) {
		List<CruscottoHomePageDTO> list = new ArrayList<CruscottoHomePageDTO>();

		Integer totNumero = new Integer(0);
		BigDecimal totImporto = BigDecimal.ZERO;

		for (Object[] row : riepilogoList) {
			CruscottoHomePageDTO item = populateCruscottoHomePageDTO(row);
			list.add(item);
			totNumero += item.getNumero();
			totImporto = totImporto.add(item.getImporto());
		}

		if (!riepilogoList.isEmpty()) {
			CruscottoHomePageDTO tot = new CruscottoHomePageDTO();
			tot.setTipoDebito("Totali");
			tot.setNumero(totNumero);
			tot.setImporto(totImporto);
			list.add(tot);
		}

		return list;
	}

	public List<it.tasgroup.idp.rs.model.CondizionePagamento> pagamentiInScadenzaHP(IProfileManager profilo, String catTributo) {
		List<it.tasgroup.idp.rs.model.CondizionePagamento> condizionePagamentoList = new ArrayList<it.tasgroup.idp.rs.model.CondizionePagamento>();
		List<Object[]> objects = distintePagamentoBusinessBean.pagamentiInScadenzaHP(profilo, catTributo);
		for (Object[] object : objects) {
			CondizionePagamento condizionePagamento = (CondizionePagamento) object[0];
			it.tasgroup.idp.rs.model.CondizionePagamento cp = AnonymousPaymentFacadeDTOBuilder.populateCondizioneRestApi(condizionePagamento);
			condizionePagamentoList.add(cp);
		}

		return condizionePagamentoList;
	}

	// Metodi nuova HP BO e Ente - END

	@Override
	public PagamentiInScadenzaDTO pagamentiInScadenzaHP(IProfileManager profilo, List<String> carrello) {
		PagamentiInScadenzaDTO dto = new PagamentiInScadenzaDTO();

		List<String> idPendenzeGiaInserite = new ArrayList<String>();

		List<CondizionePagamentoForHomePageVO> pagamentiInScadenzaHP = new ArrayList<CondizionePagamentoForHomePageVO>();
		List<CondizionePagamentoForHomePageVO> pagamentiInDelegaHP = new ArrayList<CondizionePagamentoForHomePageVO>();

		try {
			Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", "inizio");

			List<Object[]> scadenzeList = distintePagamentoBusinessBean.pagamentiInScadenzaHP(profilo, null);

			String previousIdPend = null;

			Map<Date, List<CondizionePagamentoForHomePageVO>> mapCondizioniTipoS = new HashMap<Date, List<CondizionePagamentoForHomePageVO>>();
			Map<Date, List<CondizionePagamentoForHomePageVO>> mapCondizioniTipoR = new HashMap<Date, List<CondizionePagamentoForHomePageVO>>();

			for (Object[] row : scadenzeList) {

				CondizionePagamentoForHomePageVO item = populateCondizionePagamentoForHomePageVO(row, carrello);

				if (item.getFlagPagamentoInDelega() != 0 || "BOLLO_AUTO".equals(item.getCodiceTributoEnte())) {

					List<String> debitoriPendenza = distintePagamentoBusinessBean.getDebitorePendenza(item.getIdPendenza());

					item.setCfDestinatario(debitoriPendenza.get(0));
					item.setListaCfDestinatari(debitoriPendenza);
				}

				if (previousIdPend == null)
					previousIdPend = item.getIdPendenza();

				// Cambio di pendenza
				if (!item.getIdPendenza().equalsIgnoreCase(previousIdPend)) {
					previousIdPend = item.getIdPendenza();

					insertPendenza(idPendenzeGiaInserite, pagamentiInScadenzaHP, pagamentiInDelegaHP, mapCondizioniTipoS, mapCondizioniTipoR);

					mapCondizioniTipoR = new HashMap<Date, List<CondizionePagamentoForHomePageVO>>();
					mapCondizioniTipoS = new HashMap<Date, List<CondizionePagamentoForHomePageVO>>();
				}

				// aggiungo alla lista del tipo giusto
				if (TIPAGAMENTO_S.equals(item.getTipoPagamento()))
					addToMap(mapCondizioniTipoS, item);
				else
					addToMap(mapCondizioniTipoR, item);

			}

			insertPendenza(idPendenzeGiaInserite, pagamentiInScadenzaHP, pagamentiInDelegaHP, mapCondizioniTipoS, mapCondizioniTipoR);

			Collections.sort(pagamentiInScadenzaHP);
			Collections.sort(pagamentiInDelegaHP);

//			dto.setPagamentiInScadenza(patch2013(pagamentiInScadenzaHP));
//			dto.setPagamentiInDelega(patch2013(pagamentiInDelegaHP));
			dto.setPagamentiInScadenza(pagamentiInScadenzaHP);
			dto.setPagamentiInDelega(pagamentiInDelegaHP);

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "errore pagamentiInScadenzaHP", e.getMessage(), e);
			e.printStackTrace();
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", "fine");

		return dto;
	}


	private void insertPendenza(List<String> idPendenzeGiaInserite, List<CondizionePagamentoForHomePageVO> pagamentiInScadenzaHP,
			List<CondizionePagamentoForHomePageVO> pagamentiInDelegaHP, Map<Date, List<CondizionePagamentoForHomePageVO>> mapCondizioniTipoS,
			Map<Date, List<CondizionePagamentoForHomePageVO>> mapCondizioniTipoR) {
		if (!mapCondizioniTipoS.isEmpty() && mapCondizioniTipoR.isEmpty()) {
			// solo condizioni di tipo S
			addToPagamenti(pagamentiInScadenzaHP, pagamentiInDelegaHP, mapCondizioniTipoS, idPendenzeGiaInserite);
		} else if (!mapCondizioniTipoR.isEmpty() && mapCondizioniTipoS.isEmpty()) {
			// solo condizioni di tipo R
			addToPagamenti(pagamentiInScadenzaHP, pagamentiInDelegaHP, mapCondizioniTipoR, idPendenzeGiaInserite);
		} else if (!mapCondizioniTipoR.isEmpty() && !mapCondizioniTipoS.isEmpty()) {
			// 1 condizione di tipo S e n di tipo R
			CondizionePagamentoForHomePageVO condTipoS = null;
			for (Date dataScadenza : mapCondizioniTipoS.keySet()) {
				condTipoS = mapCondizioniTipoS.get(dataScadenza).get(0);
			}

			BigDecimal importo = distintePagamentoBusinessBean.importoTotalePagatoByPendenza(condTipoS.getIdPendenza());

			// se importo == 0, si mostra la condizione di pagamento di tipo S
			if (importo == null || BigDecimal.ZERO.compareTo(importo) == 0) {
				addToPagamenti(pagamentiInScadenzaHP, pagamentiInDelegaHP, mapCondizioniTipoS, idPendenzeGiaInserite);
			} else { // se importo > 0, si mostra la condizione di pagamento di
						// tipo
						// R pi� vecchia: a parit� di data si sceglie quella
						// con importo max
				addToPagamenti(pagamentiInScadenzaHP, pagamentiInDelegaHP, mapCondizioniTipoR, idPendenzeGiaInserite);
			}
		}
	}

	private void addToHPLists(List<String> idPendenzeGiaInserite, List<CondizionePagamentoForHomePageVO> pagamentiInScadenzaHP,
			List<CondizionePagamentoForHomePageVO> pagamentiInDelegaHP, CondizionePagamentoForHomePageVO cond) {
		String idPend = cond.getIdPendenza();

		if (!idPendenzeGiaInserite.contains(idPend)) {
			if (cond.getFlagPagamentoInDelega().equals(1)) {
				pagamentiInDelegaHP.add(cond);
			} else {
				pagamentiInScadenzaHP.add(cond);
			}
			idPendenzeGiaInserite.add(idPend);
		}
	}

	private void addToMap(Map<Date, List<CondizionePagamentoForHomePageVO>> mapCondizioni, CondizionePagamentoForHomePageVO item) {
		if (mapCondizioni.get(item.getDataScandenza()) != null) {
			mapCondizioni.get(item.getDataScandenza()).add(item);
		} else {
			List<CondizionePagamentoForHomePageVO> list = new ArrayList<CondizionePagamentoForHomePageVO>();
			list.add(item);
			mapCondizioni.put(item.getDataScandenza(), list);
		}
	}

	private void addToPagamenti(List<CondizionePagamentoForHomePageVO> pagamentiInScadenzaHP, List<CondizionePagamentoForHomePageVO> pagamentiInDelegaHP,
			Map<Date, List<CondizionePagamentoForHomePageVO>> mapCondizioniTipo, List<String> idPendenzaGiaInserite) {
		// se la map ha una sola chiave, due soli casi possibili:
		// 1. N condizioni dello stesso tipo, ma tutte alla stessa data -> si
		// estrae quella di
		// importo max
		// 2. 1 sola condizione
		CondizionePagamentoForHomePageVO cpToAdd = null;

		// una o pi� pendenze, ma tutte nella stessa data
		if (mapCondizioniTipo.size() == 1) {
			Date date = mapCondizioniTipo.keySet().iterator().next();
			List<CondizionePagamentoForHomePageVO> cond = mapCondizioniTipo.get(date);

			// N condizioni dello stesso tipo
			if (cond.size() > 1) {
				// cpToAdd = searchMaxImporto(cond);

				if (cond.get(0).isCartellaPagamento()) {
					cpToAdd = searchImportoTotaleCartella(cond);

				} else {
					cpToAdd = searchMaxImporto(cond);

				}

			} else {
				// 1 sola condizione
				cpToAdd = cond.get(0);
			}
		} else { // N condizioni dello stesso tipo, ma in N date diverse
			// prendo la lista di pendenze con dataScadenza pi� vecchia
			Date minDate = searchMinData(mapCondizioniTipo.keySet());
			cpToAdd = searchMaxImporto(mapCondizioniTipo.get(minDate));
		}

		addToHPLists(idPendenzaGiaInserite, pagamentiInScadenzaHP, pagamentiInDelegaHP, cpToAdd);
	}

	private CondizionePagamentoForHomePageVO populateCondizionePagamentoForHomePageVO(Object[] row, List<String> carrello) {

		CondizionePagamentoForHomePageVO cpVO = new CondizionePagamentoForHomePageVO();

		CondizionePagamento cp = (CondizionePagamento) row[0];
		String idPendenza = (String) row[1];
		
		
		Integer idTributoStrutturato =(row[2]!=null?new Integer( ((Number) row[2]).intValue()):null);
		String causalePendenza = (String) row[3];
		String idDocumento = (String) row[4];
		Integer flagErrore = new Integer( ((Number) row[5]).intValue());
		Integer flagPagamentoInDelega = new Integer( ((Number) row[6]).intValue());
//				(Integer) row[6];
		String denominazioneEnte = (String) row[7];
		String denominazioneTributo = (String) row[8];

		Integer cartellaPagamento = null;
		if (row[9]!=null) cartellaPagamento= new Integer( ((Number) row[9]).intValue());
//				(Integer) row[9];

		String urlUpdService = (String) row[10];

		String cfPaganteDDP = (String) row[11];

		String intestatarioDDP = (String) row[12];
		
		String riscossore = (String) row[13];
		String riferimento = (String) row[14];

		String cdPlugin = (String) row[15];

		//	16	" pend.id_pendenzaente, " +
		//	17	" pend.note, " +
		//	18	" pend.ts_emissione_ente, " +
		//	19	" pend.ts_prescrizione, " +
		//	20	" pend.anno_rif " +

	    String idDebitoEnte = (String) row[16];
	    String note = (String) row[17];
	    Date dataEmissione = (Date) row[18];
	    Date dataPrescrizione = (Date) row[19];
	    Integer annoRiferimento = (Integer) row[20];
		
	    cpVO.setIdDebitoEnte(idDebitoEnte);
	    cpVO.setNote(note);
		cpVO.setDataEmissione(dataEmissione);
		cpVO.setDataPrescrizione(dataPrescrizione);
		cpVO.setAnnoRiferimento(annoRiferimento);
		cpVO.setIdPagamentoEnte(cp.getIdPagamento());
		
		Boolean flagInCarrello = carrello.contains(cp.getIdCondizione());

		cpVO.setDataScandenza(cp.getDtScadenza());
		cpVO.setDataFineValidita(cp.getDtFinevalidita());

		// StringBuffer causale = new StringBuffer();

		// causale.append(denominazioneTributo).append(":").append(causalePendenza).append(" (")
		// .append(denominazioneEnte).append(")");

		cpVO.setIdPendenza(idPendenza);
		cpVO.setTipoPagamento(cp.getTiPagamento());
		cpVO.setImportoTotale(cp.getImTotale());
		cpVO.setIdDocumento(idDocumento);
		cpVO.setFlagErrore(flagErrore != null && flagErrore.equals(1) ? Boolean.TRUE : Boolean.FALSE);
		cpVO.setFlagInCarrello(flagInCarrello);
		cpVO.setFlagPagamentoInDelega(flagPagamentoInDelega);
		cpVO.setFlagIsSpontaneo(idTributoStrutturato != null);
		cpVO.setCodiceTributoEnte(cp.getCdTrbEnte());
		cpVO.setDenominazioneEnte(denominazioneEnte);
		cpVO.setDescrizioneTributo(denominazioneTributo);
		cpVO.setCausale(causalePendenza);

		cpVO.setCartellaPagamento(new Integer(1).equals(cartellaPagamento));
		cpVO.setUrlUpdService(urlUpdService);
		cpVO.setCfPaganteDDP(cfPaganteDDP);
		cpVO.setIntestatarioDDP(intestatarioDDP);
		
		cpVO.setRiscossore(riscossore);
		cpVO.setRiferimento(riferimento);

		cpVO.setCdPlugin(cdPlugin);
		
		cpVO.setIdEnte(cp.getEnte().getIdEnte());
		
		
		
		
		
		
		return cpVO;
	}

	private CondizionePagamentoForHomePageVO searchMaxImporto(List<CondizionePagamentoForHomePageVO> cpList) {
		int i = 0;
		CondizionePagamentoForHomePageVO max = cpList.get(i);

		for (; i < cpList.size(); i++) {
			CondizionePagamentoForHomePageVO current = cpList.get(i);
			if (max.getImportoTotale().compareTo(current.getImportoTotale()) == 1) {
				max = current;
			}
		}

		return max;
	}

	private CondizionePagamentoForHomePageVO searchImportoTotaleCartella(List<CondizionePagamentoForHomePageVO> cpList) {
		BigDecimal importoTotaleCartella = new BigDecimal(0);
		for (CondizionePagamentoForHomePageVO condizionePagamentoForHomePageVO : cpList) {
			importoTotaleCartella = importoTotaleCartella.add(condizionePagamentoForHomePageVO.getImportoTotale());
		}
		CondizionePagamentoForHomePageVO tot = cpList.get(0);
		tot.setImportoTotale(importoTotaleCartella);
		return tot;
	}

	private Date searchMinData(Set<Date> dateList) {
		Date min = null;
		for (Iterator<Date> iterator = dateList.iterator(); iterator.hasNext();) {
			Date current = iterator.next();
			if (min == null || min.after(current)) {
				min = current;
			}
		}
		return min;
	}

	@Override
	public List<PagamentoPosizioneDebitoriaForHomePageVO> ultimiPagamentieffettuatiHP(IProfileManager profilo) {
		List<PagamentoPosizioneDebitoriaForHomePageVO> dto = new ArrayList<PagamentoPosizioneDebitoriaForHomePageVO>();

		try {
			Tracer.debug(this.getClass().getName(), "ultimiPagamentieffettuatiHP", "inizio");
			dto = distintePagamentoBusinessBean.ultimiPagamentiEffettuatiHP(profilo);

		} catch (Exception e) {
			Tracer.error(this.getClass().getName(), "errore ultimiPagamentieffettuatiHP", e.getMessage(), e);
			e.printStackTrace();
			new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
		}
		Tracer.debug(this.getClass().getName(), "ultimiPagamentieffettuatiHP", "fine");

		return dto;

	}

	@Override
	public ContainerDTO storicoPagamenti(IProfileManager profilo, ContainerDTO inputDTO) throws BusinessConstraintException {
		return distintePagamentoBusinessBean.storicoPagamenti(profilo, inputDTO);
	}

	@Override
	public boolean cancellaPrenotazione(Long idPrenotazione) throws Exception {

		return exportBusinessBean.deletePrenotazione(idPrenotazione);

	}

	@Override
	public List<TributoEnteDTO> listTributoEntePDB(String lapl) {
		return listTributoEntePDB(lapl, false);
	}
	
	@Override
	public List<TributoEnteDTO> listTributoEntePDB(String lapl, boolean isBackOffice) {

		List<TributoEnte> tributiEnte = distintePagamentoBusinessBean.listTributoEntePDB(lapl);

		List<TributoEnteDTO> tributiEnteDTO = EntiDTOBuilder.fillListEntiTributiDTO(tributiEnte, isBackOffice);

		return tributiEnteDTO;
	}

	@Override
	public List<String> getDebitorePendenza(String idPendenza) {

		List<String> debitoriPendenza = distintePagamentoBusinessBean.getDebitorePendenza(idPendenza);

		return debitoriPendenza;
	}

	@Override
	public void esporta(IProfileManager profileManager, ContainerDTO inputDTO,
			Map<String, String> mapListaCampi, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore,
			EnumTipoExport exportType, EnumDynaReportFormat dynaFormat)
			throws Exception {
		// TODO Auto-generated method stub

	}

    @Asynchronous
	@Override
	public void esportaBollettiniAvvisiZipPdf(IProfileManager pfm,
			ContainerDTO inputDTO, Map<String, String> mapEnti,
			Map<String, String> mapTipiTiributo, String cfOperatore,
			Locale locale) throws Exception {


		Prenotazioni prenotazione = (Prenotazioni) inputDTO.getInputDTOList().get(1);
		LOGGER.debug("esportaPagamenti - salvata la prenotazione");

		EnumTipoExport tipo;
    	tipo = EnumTipoExport.PAGAMENTI;

        String stato;

        try {

            Esportazioni exp = new Esportazioni();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(baos);
            //FornitorePagamentoNodoSPC cfgPagamento = new FornitorePagamentoNodoSPC();
            ArrayList array = null;
            if (inputDTO.getInputDTOList()!=null) {
            	Iterator iterator = inputDTO.getInputDTOList().iterator();
            	while (iterator.hasNext()){
            		Object o = iterator.next();
            		if (o instanceof ArrayList) {
            			array = (ArrayList) o;
            		}
            	}
            }
            if (array!=null) {

            	Iterator iterator = array.iterator();

            	List<String> listaCondizioni = new ArrayList<String>();

            	while (iterator.hasNext()){
            		AvvisoPosizioneDebitoriaVO avvDeb=(AvvisoPosizioneDebitoriaVO)iterator.next();
            		//  ciclo per recuperare  tutte le condizioni associate ad un singolo documento di pagamento
            		if (avvDeb.getCondizioni()!=null) {
            			Iterator<CondizionePagamentoVO> iterPagam = avvDeb.getCondizioni().iterator();
            			while (iterPagam.hasNext()) {
            				listaCondizioni.add(iterPagam.next().getIdCondizione());
            			}
            		}
            	}

            	CfgGatewayPagamento cfg = distintePagamentoBusinessBean.getCfgGatewayPagamentoBySystemIdAndApplicationId("NDP-PSP-PO", "NDP-PSP-PO");
            	Long idCfgGatewayPagamento = cfg.getId();

            	Iterator<String> iteratorCondizioni = listaCondizioni.iterator();
            	while (iteratorCondizioni.hasNext()) {

            		zos = distintePagamentBean.esportaBollettiniPdfChunck(iteratorCondizioni, zos, pfm, idCfgGatewayPagamento, locale);


            	}

            }

            zos.close();

            Timestamp now = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd-HH_mm_ss");

            StringBuilder filename = new StringBuilder("export-"+ tipo.getDescrizione()+"-");
            filename.append(dateFormatter.format(now));

            if(cfOperatore != null)
                filename.append("-").append(cfOperatore);

            stato = EnumStatoExport.DISPONIBILE.getDescrizione();

            exp.setDati(baos.toByteArray());
            exp.setCompressione("S");
            exp.setFormato("zip");
            exp.setLenDati(new Long(baos.size()));
            exp.setNomeFile(filename.toString());
            exp.setOpInserimento("esportaGestioneFlussiFull");
            exp.setTsInserimento(now);
            exp.setPrenotazione(prenotazione);

            Set<Esportazioni> esportazioni = new LinkedHashSet();
            esportazioni.add(exp);
            prenotazione.setEsportazioni(esportazioni);

        } catch (Exception e) {

            e.printStackTrace();
            stato = EnumStatoExport.ERRORE.getDescrizione();

        }

        prenotazione.setStato(stato);
        prenotazione.setTipoEsportazione(tipo.getDescrizione());
        prenotazione.setOpAggiornamento("esportaGestioneFlussiFull");
        prenotazione.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
        ContainerDTO cDto = new ContainerDTO();
        cDto.setInputDTO(prenotazione);
        try {
           exportBusinessBean.updatePrenotazione(cDto);
        } catch (Exception e) {

           e.printStackTrace();
           stato = EnumStatoExport.ERRORE.getDescrizione();

        }
        LOGGER.debug("esportaPagamenti - esecuzione terminata");
    }
    /**
     * Esporta i bolletti con chunk di 10 elementi
     * @param iteratorCondizioni
     * @param zos
     * @param pfm
     * @param intestatarioRT
     * @param intestatarioDDP
     * @param contoTecnico
     * @param locale
     * @return
     * @throws Exception
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public ZipOutputStream esportaBollettiniPdfChunck(Iterator<String> iteratorCondizioni, ZipOutputStream zos, IProfileManager pfm, Long idCfgGatewayPagamento, Locale locale) throws Exception {
    	int index = 0;

		Intestatari intestatarioDDP = null;
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		LOGGER.debug("esportaBollettiniPdfChunck - inizio esecuzione - ");

		boolean error = false;
		while (iteratorCondizioni.hasNext() && index < 5 && !error) {

			String idCondizione = iteratorCondizioni.next();
			index++;
			// controllo se il documento di pagamento � gi� stato creato per la
			// condizione in esame
			try {
				List<DocumentoDiPagamento> docPagList = ddpBusinessBean.readDDPListByIdCondizione(idCondizione);
				DocumentoDiPagamento ddp = null;
				byte[] bytesReport;
				if (docPagList.isEmpty()) {
					// il documento di pagamento non e' stato creato: lo creo e
					// creo
					// anche il document nel repository
					// TODO: begin patch pazzesca per evitare errore su insert
					// documento violazione constraint NOT NULL
					DDPInputDTO dto = new DDPInputDTO();
					dto.addCondizioneCarrello(idCondizione);
					dto.setTipo(EnumTipoDDP.NDP);

					dto.setIdGateway(idCfgGatewayPagamento);
					// end
					ddp = ddpBusinessBean.createDDP(pfm, dto, intestatarioDDP);

				} else {
					// il documento di pagamento esiste gia'
					ddp = docPagList.get(0);
				}
				if (ddp.getIdDocumentoRepository() == null) {
					// creo document repository
					List<DocumentoDiPagamento> ddpListEntityOut = new ArrayList<DocumentoDiPagamento>();

					ddpListEntityOut.add(ddp);
                    //************* recupero l intestatario del documento di pagamento dalla condizione ******************************
					if (ddp.getCondizioni()!=null && !ddp.getCondizioni().isEmpty()) {
						CondizioneDocumento cond=ddp.getCondizioni().iterator().next();
						if(cond.getCondizionePagamento()!=null &&
						   cond.getCondizionePagamento().getEnte()!=null &&
						   cond.getCondizionePagamento().getEnte().getIntestatarioIForm()!=null) {

							intestatarioDDP = intestatariBusinessBean.readIntestatarioById(cond.getCondizionePagamento().getEnte().getIntestatarioIForm().getCorporateIForm());
						} else {
							intestatarioDDP = null;
							LOGGER.warn("esportaBollettiniPdfChunck - non riesco a recuperare intestatarioDDP per la CondizionePagamento.idCondizione = " + idCondizione);
						}
					}
					//****************************************************************************************************************
					// Per estrarre il logo del DDP si suppone che il carrello sia monoente.
					String idEnte = ddp.getCondizioni().iterator().next().getCondizionePagamento().getEnte().getIdEnte();

					CfgEntiLogo logoEnte = cfgBusinessBean.getCfgEntiLogoById(idEnte);

					List<DocumentoDiPagamentoDTO> dtoList = DDPDTOBuilder
							.populateDDPDTOList(intestatarioDDP,
									intestatarioDDP, ddpListEntityOut, logoEnte,
									contoTecnico, locale);

					bytesReport = ddpBusinessBean.creaPdfDocumentoDiPagamento(
							dtoList, locale);

					DocumentiRepository ricevutaDocRepo = DDPDTOBuilder
							.makeDocumentiRepository(bytesReport, dtoList
									.get(0).getNomeFile(), intestatarioDDP
									.getLapl());
					String fileName = dtoList.get(0).getNomeFile();
					if (!fileName.endsWith(".pdf")) {
						fileName = fileName + ".pdf";
					}
					zipBytes(zos, fileName, bytesReport);
					boolean saveDDP = cpl.getBooleanProperty("save.ddp.ndp.on.documenti_repository");
					if(saveDDP) {
						ddpBusinessBean.createDocumentiRepository(ricevutaDocRepo, dtoList.get(0).getId());
					}
				} else {
					// leggo document repository
					DocumentiRepository docRepo = ddpBusinessBean
							.readDocumentiRepositoryById(ddp
									.getIdDocumentoRepository());

					String fileName = docRepo.getNomeFile();
					if (!fileName.endsWith(".pdf")) {
						fileName = fileName + ".pdf";
					}
					zipBytes(zos, fileName, docRepo.getDocumento());
				}
			} catch (Throwable t) {
				error=true;
				LOGGER.error("esportaBollettiniPdfChunck - errore elaborazione idCondizione = " + idCondizione);
			}
		}
		LOGGER.debug("esportaBollettiniPdfChunck - fine esecuzione - ");
    	return zos;
    }
    /**
     *
     * @param zos
     * @param filename
     * @param input
     * @throws IOException
     */
    public static void zipBytes(ZipOutputStream zos, String filename, byte[] input) throws IOException {
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
    }
    /**
     *
     * @param filename
     * @param input
     * @return
     * @throws IOException
     */
    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zipBytes(zos, filename, input);
        zos.close();
        return baos.toByteArray();
    }

    @Override
    public void updateRevocaPagamento(RevocaPagamentoDTO revocaDTO){
    	distintePagamentoBusinessBean.updateRevocaPagamento(revocaDTO);
    }

    
    @Override
    public DistintePagamentoDTOLight retrieveFlowById(IProfileManager profileManager, Long flowId){

		GestioneFlussi flow = distintePagamentoBusinessBean.getDistintaById(flowId);
		
		//Check visibilità del dato
		//--------------------------------------
		for (Pagamenti p:flow.getPagamenti()) {
			Enti ente=intestatariBusinessBean.readEnteByIdEnte(p.getIdEnte());
			VisibilityChecker.checkVisibilitaDatiEnte(profileManager, ente);
			//---------------------------------------
			//Fine Check visibilit� del dato
			//--------------------------------------
			List<RevochePagamento> rp = distintePagamentoBusinessBean.getRevocaPagamentoByIdPagamento(p.getId());
			p.setRevochePagamento(rp);
		}
		//---------------------------------------
		//Fine Check visibilità del dato
		//--------------------------------------
		byte[] outRT = giornaleEventiBusinessBean.getGiornaleEventiDocumentiNDP(flow.getCodTransazionePSP(), flow.getIdentificativoFiscaleCreditore(), flow.getIuv() , "RT", null);
		byte[] outRPT = giornaleEventiBusinessBean.getGiornaleEventiDocumentiNDP(flow.getCodTransazionePSP(), flow.getIdentificativoFiscaleCreditore(), flow.getIuv() , "RP", null);
 
		byte[] outRR = giornaleEventiBusinessBean.getGiornaleEventiDocumentiNDP(flow.getCodTransazionePSP(), flow.getIdentificativoFiscaleCreditore(), flow.getIuv() , "RR", null);
		byte[] outER = giornaleEventiBusinessBean.getGiornaleEventiDocumentiNDP(flow.getCodTransazionePSP(), flow.getIdentificativoFiscaleCreditore(), flow.getIuv() , "ER", null);
		DistintePagamentoDTOLight output = DistintePagamentoDTOBuilder.populateDistintePagamentoDTOLight(flow);
		
		if (outRT==null){
			output.setRTAvailable(false); 
		} else {
			output.setRTAvailable(true);
		}
		
		if (outRPT==null){
			output.setRPTAvailable(false);
		} else {
			output.setRPTAvailable(true);
		}
		output.setDocRRAvailable(outRR != null);
		output.setDocERAvailable(outER != null);
		return output;
    }

    @Override
    public List<DistintaPosizioneDebitoriaDettaglioVO> getDistintaPagamento(ContainerDTO dtoIn){

		List<GestioneFlussi> flows = distintePagamentoBusinessBean.getDistintaPagamento(dtoIn);

		return DistintePagamentoDTOBuilder.fillDistintePosizioneDebitoriaDettaglioVO(flows);
    }

    @Override
    public List<DistintaPosizioneDebitoriaDettaglioVO> getDistinteByFilterParameters(ContainerDTO inputDTO){

		List<GestioneFlussi> flows = distintePagamentoBusinessBean.getDistinteByFilterParameters(inputDTO);

		return DistintePagamentoDTOBuilder.fillDistintePosizioneDebitoriaDettaglioVO(flows);
    }


    @Override
    public List<DistintaPosizioneDebitoriaDettaglioVO> getDistinteByIdCondizionePagamento(String idCondizionePagamento){

		List<GestioneFlussi> flows = distintePagamentoBusinessBean.getDistinteByIdCondizionePagamento(idCondizionePagamento);

		return DistintePagamentoDTOBuilder.fillDistintePosizioneDebitoriaDettaglioVO(flows);
    }
    
    @Override
    public List<DistintaPosizioneDebitoriaDettaglioVO> getDistinteByIdCondizionePagamentoStorico(String idCondizionePagamento){

		List<GestioneFlussi> flows = distintePagamentoBusinessBean.getDistinteByIdCondizionePagamentoStorico(idCondizionePagamento);

		return DistintePagamentoDTOBuilder.fillDistintePosizioneDebitoriaDettaglioVO(flows);
    }


	@Override
	public DistintaPosizioneDebitoriaDettaglioVO getDistintaById(
			Long idDistinta) {

		GestioneFlussi flow = distintePagamentoBusinessBean.getDistintaById(idDistinta);

		return DistintePagamentoDTOBuilder.fillDistintaPosizioneDebitoriaDettaglioVO(flow, true /*includePendenzeList*/);
	}
	
	@Override
	public DistintaPosizioneDebitoriaDettaglioVO getDistintaByIdStorico(Long idDistinta) {
		GestioneFlussi flow = distintePagamentoBusinessBean.getDistintaByIdStorico(idDistinta);
		return DistintePagamentoDTOBuilder.fillDistintaPosizioneDebitoriaDettaglioVO(flow, true /*includePendenzeList*/);
	}


    @Override
    public CondizionePagamentoDTO readCondizionePagamento(String idCondizione) {

    	CondizionePagamento condizione = ddpBusinessBean.readCondizionePagamento(idCondizione);

    	CondizionePagamentoDTO condDTO = new  CondizionePagamentoDTO();

		EnteDTO enteDTO = new EnteDTO();

		IndirizzoDTO indirizzoDTO = new IndirizzoDTO();

		condDTO.setCausalePagamento(condizione.getCausalePagamento());
		condDTO.setCdTrbEnte(condizione.getCdTrbEnte());
		condDTO.setCoCip(condizione.getCoCip());
		condDTO.setDeCanalepag(condizione.getDeCanalepag());
		condDTO.setDeMezzoPagamento(condizione.getDeMezzoPagamento());
		condDTO.setDeNotePagamento(condizione.getDeNotePagamento());

		condDTO.setIdPagamento(condizione.getIdPagamento());
		condDTO.setImTotale(condizione.getImTotale());
		condDTO.setStPagamento(condizione.getStPagamento());
		condDTO.setStatoPagamentoCalcolato(condizione.getStatoPagamentoCalcolato());
		condDTO.setCodiceFiscaleDebitore(condizione.getPendenza().getDestinatari().iterator().next().getCoDestinatario());
		condDTO.setDescrizioneCreditore(condizione.getEnte().getIntestatarioobj().getRagionesocialeIForm());
		condDTO.setDescrizioneDebito(condizione.getPendenza().getTributoEnte().getDeTrb());
		condDTO.setCausalePendenza(condizione.getPendenza().getDeCausale());
		condDTO.setIdPendenza(condizione.getPendenza().getIdPendenza());

		Enti ente = condizione.getPendenza().getTributoEnte().getEntiobj();
		enteDTO.setCodice(ente.getCodiceEnte());

		enteDTO.setDenominazione(ente.getDenominazione());

		IndirizzipostaliCommon indirizzo = ente.getIntestatarioobj().getIndirizzipostaliobjIForm();

		indirizzoDTO.setCap(indirizzo.getCapCodeIForm());

		indirizzoDTO.setNazione(indirizzo.getCountryIForm());

		indirizzoDTO.setProvincia(indirizzo.getProvinceIForm());

		indirizzoDTO.setVia(indirizzo.getAddressIForm());

		indirizzoDTO.setNumeroCivico(indirizzo.getNumeroCivicoIForm());

		enteDTO.getIntestatario().setIndirizzo(indirizzoDTO);

		condDTO.setEnte(enteDTO);

    	return condDTO;
    }

	@Override
	public DistintaCartaCreditoVO getDistintaCreditCardByCodTransazione(
			String codTransazione) {

		List<GestioneFlussi> distinte = distintePagamentoBusinessBean.getDistintaByCodTransazione(codTransazione);
		if (distinte == null || distinte.size()==0) {
			return null;
		} else if (distinte.size()>1) {
			throw new IllegalArgumentException("Trovate piu' distinte con codTransazione="+codTransazione);
		} else {
			GestioneFlussi distinta=distinte.get(0);

			// VecchiaQuery:
			//
			//		SELECT F.ID as idFlusso,
			//               F.STATO as statoPagamentoString,
			//               (F.IMPORTO-F.IMPORTO_COMMISSIONI) as totale,
			//               F.COD_TRANSAZIONE as token,
			//			     M .COD_AUTORIZZAZIONE as idAutorizzazione,
			//               M.TS_OPERAZIONE as dataTransazione,
			//			     F.COD_PAGAMENTO as codPagamento,
			//			     F.IMPORTO_COMMISSIONI as importoCommissioni,
			//               G.ID_CFG_MODALITA_PAGAMENTO as mezzoPagamentoKey
			//		FROM  ${databaseSchema}.DISTINTE_PAGAMENTO F
			//			inner join ${databaseSchema}.PAGAMENTI_ONLINE M on F.ID=M.ID_DISTINTE_PAGAMENTO
			//			inner join ${databaseSchema}.CFG_GATEWAY_PAGAMENTO G on G.ID=F.ID_CFG_GATEWAY_PAGAMENTO
			//		WHERE 1=1
			//		[if IDFLUSSO :  AND  F.ID = :IDFLUSSO ]
			//		[if COD_TRANSAZIONE :  AND  F.COD_TRANSAZIONE = :COD_TRANSAZIONE ]


			DistintaCartaCreditoVO result = new DistintaCartaCreditoVO();
			result.setIdFlusso(distinta.getId());
			result.setStatoPagamentoString(distinta.getStato());
			if (distinta.getImportoCommissioni()!=null) {
				result.setTotale(distinta.getTotimportipositivi().subtract(distinta.getImportoCommissioni()));
				result.setTotImportiPositivi(distinta.getTotimportipositivi());
			} else {
				result.setTotale(distinta.getTotimportipositivi());
			}
			result.setIdAutorizzazione(null);  // Non veniva mai usato
			result.setDataTransazione(distinta.getTsInserimento());
			result.setCodPagamento(distinta.getCodPagamento());
			result.setMezzoPagamentoKey(distinta.getCfgGatewayPagamento().getCfgModalitaPagamento().getId());
			result.setImportoCommissioni(distinta.getImportoCommissioni());

			Iterator<Pagamenti> pagIter=distinta.getPagamenti().iterator();
			while (pagIter.hasNext()) {
				Pagamenti p = pagIter.next();
				result.setAssociatedDocAvailable(p.isAssociatedDocumentAvailable());
			    break;
		    }
			return result;

		}

	}

	@Override
	public DistintaCartaCreditoVO preparaPagamento(IProfileManager profilo,	DistintaCartaCreditoVO distinta) {
		return distintePagamentoBusinessBean.preparaPagamento(profilo,distinta);
	}

	@Override
	public List<DistintePagamentoDTOLight> getDistinteByCodPagamento(
			String codPagamento) {
		List<GestioneFlussi> listaDistinte = distintePagamentoBusinessBean.getDistinteByCodPagamento(codPagamento);

		List<DistintePagamentoDTOLight> outputList = new ArrayList<DistintePagamentoDTOLight>();

		if (listaDistinte!=null) {
			for (GestioneFlussi distinta:listaDistinte) {
				DistintePagamentoDTOLight distintaDTO = DistintePagamentoDTOBuilder.populateDistintePagamentoDTOLight(distinta);
				outputList.add(distintaDTO);
			}
		}

		return outputList;

	}
	
		

	@Override
	public PagamentoDTOLightForReceipt getPagamentoById(Long idPagamento) {
		Pagamenti p = distintePagamentoBusinessBean.getPagamentoById(idPagamento);
		return DistintePagamentoDTOBuilder.populatePagamentoDTOLightForReceipt(p);

	}

	@Override
	public List<PagamentoDTOLightForReceipt> getPagamentiByIdDistinta(Long idDistinta) {

		List<PagamentoDTOLightForReceipt> outputList = new ArrayList<PagamentoDTOLightForReceipt>();
		List<Pagamenti> plist = distintePagamentoBusinessBean.getPagamentiByIdDistinta(idDistinta);

		for (Pagamenti p:plist ) {
			PagamentoDTOLightForReceipt pdto = DistintePagamentoDTOBuilder.populatePagamentoDTOLightForReceipt(p);
			outputList.add(pdto);
		}

		return outputList;

	}

	@Override
	public StatistichePagamento getStatisticheCreditore(StatistichePagamentoFilter statistichePagamentoFilter) {
		return distintePagamentoBusinessBean.getStatisticheCreditore(statistichePagamentoFilter);
	}

	@Override
	public Long aggiornaInformazioniTransazioneIncasso(Long idFisico, String iuvPagamento, String codiceContestoPagamento, Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso) {
		return distintePagamentoBusinessBean.aggiornaInformazioniTransazioneIncasso (idFisico,iuvPagamento,codiceContestoPagamento, informazioniTransazioneIncasso );
	}
	
	@Override
	public  void annullaOperatoreByDistinta(DistintePagamentoDTOLight distintaPag) {
		boolean updated=distintePagamentoBusinessBean.annullaOperatoreByIdDistinta(distintaPag.getId().longValue());
		if (updated){
		   GiornaleEventiExtDTO g = createGiornaleEvDTO(distintaPag);
		   giornaleEventiBusinessBean.save(g);
		}
	}

	private GiornaleEventiExtDTO createGiornaleEvDTO(DistintePagamentoDTOLight distintaPag){
		GiornaleEventiExtDTO g = new GiornaleEventiExtDTO();
		g.setDataOraEvento(new Timestamp(System.currentTimeMillis()));


		g.setIdDominio(distintaPag.getIdentificativoFiscaleCreditore());
		g.setIdUnivocoVersamento(distintaPag.getIuv());
		g.setCodiceContestoPagamento(distintaPag.getCodTransazionePSP());
		g.setTipoVersamento(EnumTipoVersamento.CP);
		g.setComponente(EnumComponente.WISP);
		g.setCategoriaEvento(EnumCategoriaEvento.INTERFACCIA);
		g.setTipoEvento(EnumTipoEventiNDP.KOOnline);
		g.setSottoTipoEvento(EnumSottoTipoEvento.req);
		g.setIdFruitore("NodoDeiPagamentiSPC");
		g.setIdErogatore("n/a");
		g.setIdStazioneIntermediarioPA("n/a");
		g.setIdPSP("n/a");
		g.setCanalePagamento(null);
		g.setParametriSpecificiInterfaccia(null);
		g.setEsito(null);
		g.setIdEGov(null);
		g.setStoreDocumentoNDP(false);
		g.setOpInserimento("IDP");
		g.setTsInserimento(new Timestamp(System.currentTimeMillis()));

		return g;
	}
	@Override
	public EsitoNDP notificaPagamento(RichiestaNotificaPagamento request){
		return distintePagamentoBusinessBean.notificaPagamento(request);
	}
	

}
