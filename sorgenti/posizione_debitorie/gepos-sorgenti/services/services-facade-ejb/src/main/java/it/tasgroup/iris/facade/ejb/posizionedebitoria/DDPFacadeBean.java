package it.tasgroup.iris.facade.ejb.posizionedebitoria;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.erbweb.constants.CommonConstants;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.is.fo.util.ZipUnzip;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.business.ejb.client.IrisCacheSingletonLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.TributoEnteBusinessLocal;
import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.ddp.DDPBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.giornaleeventi.GiornaleEventiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneLocal;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgEntiLogo;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgUtenteModalitaPagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.ContoTecnico;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.helper.BillInspector;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.domain.helper.CommissioniCalculator;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.ListaDocumentiInputDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgEntiLogoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgGatewayPagamentoDTO;
import it.tasgroup.iris.dto.confpagamenti.CfgUtenteModalitaPagamentoDTO;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTOLight;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.ConfigurationException;
import it.tasgroup.iris.dto.exception.InvalidInputException;
import it.tasgroup.iris.dto.exception.SecurityException;
import it.tasgroup.iris.dto.flussi.PagamentoDTOLightForReceipt;
import it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacadeRemote;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DDPDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.DRPDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.builder.EntiDTOBuilder;
import it.tasgroup.iris.facade.ejb.posizionedebitoria.dto.cfgpagamenti.builder.CfgPagamentiDTOBuilder;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.report.utility.PDFAUtils;
import it.tasgroup.report.utility.PdfUtils;
import it.tasgroup.services.util.enumeration.EnumAttachmentRendering;
import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

@Stateless(name = "DDPFacade")
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DDPFacadeBean implements DDPFacadeLocal, DDPFacadeRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(DDPFacadeBean.class);	
	
	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");
	private static final ConfigurationPropertyLoader cpl  = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
	
	@EJB
	private TributoEnteBusinessLocal tributoEnteBusinessLocal;

	@EJB
	private DDPBusinessLocal ddpBusinessBean;
	 
	@EJB
	private IntestatariBusinessLocal intestatariBusinessBean;
	
	@EJB
	private ConfPagamentiBusinessLocal cfgBusinessBean;
	
	@EJB
	private DistintePagamentoBusinessLocal distintePagamentoBusinessBean;
	
	@EJB
	private IrisCacheSingletonLocal irisCache;
	
	@EJB
	private AutorizzazioneLocal autorizzazioneBean;
	
	@EJB
	private GiornaleEventiBusinessLocal giornaleEventiBean;
	
	@EJB(name = "TributoEnteBusiness")
    private TributoEnteBusinessLocal tributoEnteBusinessBean;
		
	
	private static Boolean externalReceiptCreation = null;
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.bmi.posizionedebitoria.DDPFacade#readDDPs(java.lang.String)
	 */
	@Override
	public List<DocumentoDiPagamentoDTO> readDDPList(String codFiscale, String azienda, ContainerDTO containerDTO, Locale locale) throws BusinessConstraintException {
		
		DocumentiRepository docRepo = null;
		
		List<DocumentoDiPagamento> ddpListEntityOut = ddpBusinessBean.readDDPs(codFiscale, azienda, containerDTO);
		
		DocumentoDiPagamento ddp = ddpListEntityOut.get(0);
		
		Intestatari intestatarioRT = irisCache.getAnagraficaRT();
		
		Intestatari intestatarioDDP = intestatariBusinessBean.readIntestatarioById(azienda);
		
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		/*
		 * Con l'eliminazione del conto tecnico, Regione Toscana ha
		 * richiesto di impostare l'IBAN relativo al tributo pagato
		 */
		// contoTecnicoNoCache is Casalino approved
		// qui ci si passa allo "scarica documento"
		ContoTecnico contoTecnicoNoCache = new ContoTecnico(); // shallow copy minimale per evitare problemi di concorrenza dull'iban
		contoTecnicoNoCache.setIban(contoTecnico.getIban());
		contoTecnicoNoCache.setIntestatario(contoTecnico.getIntestatario()); // l'intestatario e' ancora quello della cache ma non ci da problemi a valle
		Set<CondizioneDocumento> condizioni = ddp.getCondizioni();
		for (CondizioneDocumento condizioneDocumento: condizioni) {
			String idTributo = condizioneDocumento.getCondizionePagamento().getCdTrbEnte();
			String idEnte = condizioneDocumento.getCondizionePagamento().getEnte().getIdEnte();
			TributoEnte te = tributoEnteBusinessLocal.getTributiEntiByKey(idEnte, idTributo);
			contoTecnicoNoCache.setIban(te.getIBAN());
		}
		
		// Per estrarre il logo del DDP si suppone che il carrello sia monoente.
		String idEnte = ddp.getCondizioni().iterator().next().getCondizionePagamento().getEnte().getIdEnte();
		
		CfgEntiLogo logoEnte = cfgBusinessBean.getCfgEntiLogoById(idEnte);
		
		List<DocumentoDiPagamentoDTO> dtoList = DDPDTOBuilder.populateDDPDTOList(intestatarioDDP, intestatarioRT, ddpListEntityOut, logoEnte, contoTecnicoNoCache, locale);
		DocumentoRepositoryDTO docRepoDTO = null;
		if (ddp.getIdDocumentoRepository() == null){	
			if (!ddp.getTipoDocumentoEnum().equals(EnumTipoDDP.FRECCIA)){
				byte [] reportFlow = ddpBusinessBean.creaPdfDocumentoDiPagamento(dtoList, locale);
				boolean saveDdpNdp = cpl.getBooleanProperty("save.ddp.ndp.on.documenti_repository");
				if( !saveDdpNdp && ddp.getTipoDocumentoEnum().getChiave().equals(EnumTipoDDP.NDP.getChiave())) {
					docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(dtoList.get(0), reportFlow, dtoList.get(0).getNomeFile());
				} else {
					docRepo = DDPDTOBuilder.makeDocumentiRepository(reportFlow, dtoList.get(0).getNomeFile(), intestatarioDDP.getLapl());
					ddpBusinessBean.createDocumentiRepository(docRepo, ddp);
					docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(dtoList.get(0), docRepo);
				}
			}
		} else {
			docRepo = ddpBusinessBean.readDocumentiRepositoryById(ddp.getIdDocumentoRepository());
			docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(dtoList.get(0), docRepo);
		}
		dtoList.get(0).setDocumentoRepository(docRepoDTO);
		return dtoList;		
	}
	
	@Override
	public DocumentoDiPagamentoDTO readSingleDDP_BKOF(String docId, Locale locale) throws BusinessConstraintException {
		
		DocumentiRepository docRepo = null;
		
		DocumentoDiPagamentoDTO ddpDTO = null;
		
		DocumentoDiPagamento ddp = ddpBusinessBean.readSingleDDP_BKOF(docId);
		
		if (ddp != null) {
			
			Intestatari intestatarioRT = irisCache.getAnagraficaRT();
			
			Intestatari intestatarioDDP = intestatariBusinessBean.readIntestatarioById(ddp.getIntestatario());
			
			ContoTecnico contoTecnico = irisCache.getContoTecnico();
			
			String idEnte = ddp.getCondizioni().iterator().next().getCondizionePagamento().getEnte().getIdEnte();
			
			CfgEntiLogo logoEnte = cfgBusinessBean.getCfgEntiLogoById(idEnte);
			
			ddpDTO = DDPDTOBuilder.populateDDPDTO(intestatarioDDP, intestatarioRT, ddp, logoEnte ,contoTecnico, locale);
			
			if (ddp.getIdDocumentoRepository() == null){	
				
				if (!ddp.getTipoDocumentoEnum().equals(EnumTipoDDP.FRECCIA)){
					
					List<DocumentoDiPagamentoDTO> dtoList = new ArrayList<DocumentoDiPagamentoDTO>();
					
					dtoList.add(ddpDTO);
					
					byte [] reportFlow = ddpBusinessBean.creaPdfDocumentoDiPagamento(dtoList, locale);
				
					docRepo = DDPDTOBuilder.makeDocumentiRepository(reportFlow, ddpDTO.getNomeFile(), intestatarioDDP.getLapl());
					
					ddpBusinessBean.createDocumentiRepository(docRepo, ddp);
				}
				
			} else 
				docRepo = ddpBusinessBean.readDocumentiRepositoryById(ddp.getIdDocumentoRepository());
			
			DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(ddpDTO, docRepo);
			
			ddpDTO.setDocumentoRepository(docRepoDTO);
			
		}
		
		
		return ddpDTO;		
	}
	
	@Override
	public ContainerDTO readDDPLightList(String codFiscale, String azienda, ContainerDTO containerDTO, Locale locale) throws SecurityException{
		
		List<DocumentoDiPagamento> ddpListEntityOut = ddpBusinessBean.readDDPs(codFiscale, azienda, containerDTO);
		
		String selectedIdTreeTable = ((ListaDocumentiInputDTO)containerDTO.getInputDTO()).getDocIdSelected();
		
		List<DocumentoDiPagamentoDTOLight> resultList = DDPDTOBuilder.populateDDPDTOLightList(ddpListEntityOut, selectedIdTreeTable, locale);
		
		containerDTO.setOutputDTOList(resultList);
		
		return containerDTO;
	}
	
	@Override
	public ContainerDTO readDDPLightListForBackOffice(ContainerDTO containerDTO, Locale locale) throws SecurityException{
		
		List<DocumentoDiPagamento> ddpListEntityOut = ddpBusinessBean.readDDPsForBackOffice(containerDTO);
		
		String selectedIdTreeTable = ((ListaDocumentiInputDTO)containerDTO.getInputDTO()).getDocIdSelected();
		
		List<DocumentoDiPagamentoDTOLight> resultList = DDPDTOBuilder.populateDDPDTOLightList(ddpListEntityOut, selectedIdTreeTable, locale);
		
		containerDTO.setOutputDTOList(resultList);
		
		return containerDTO;
	}
	
	private void checkCfgGatewayPagamento(CfgGatewayPagamento cfgGateway, String tipoDocumento) throws ConfigurationException {
		
		if (cfgGateway == null)
			throw new ConfigurationException(EnumBusinessErrorCodes.APPEXC_NOGTW, null, "Gateway conf not found with ID=" + tipoDocumento);
		
		
		if (!cfgGateway.isDocumentEnabled())
			throw new ConfigurationException(EnumBusinessErrorCodes.APPEXC_NODDPGTW, null, "Gateway with ID=" + tipoDocumento + " is not enabled for payment by document.");
			
	}
		
	@Override
	public List<DocumentoDiPagamentoDTO> createDDP(IProfileManager profile, DDPInputDTO dto, Locale locale) throws BusinessConstraintException {
			
		Intestatari intestatarioDDP = intestatariBusinessBean.readIntestatarioById(profile.getAzienda());
		
		DocumentoDiPagamento createdDDP = ddpBusinessBean.createDDP(profile, dto, intestatarioDDP);
		
		// Tributo Spontaneo
//		for (CondizioneDocumento condizioneDocumento: condizioni) {
//		if(condizioneDocumento.getCondizionePagamento().getDtScadenza().compareTo(SharedConstants.NO_EXPIRE) == 0 &&
//				condizioneDocumento.getDocumento().getDtScadenzaDoc() == null) {
//			condizioneDocumento.getDocumento().setDtScadenzaDoc(null);
//		}
//		String idTributo = condizioneDocumento.getCondizionePaganto().getCdTrbEnte();
//		String idEnte = condizioneDocumento.getCondizionePagamento().getEnte().getIdEnte();
//		TributoEnte te = tributoEnteBusinessLocal.getTributiEntiByKey(idEnte, idTributo);
//		if (te.getFlIniziativa().equals("Y") && te.getFlPredeterm().equals("N")) {
//			condizioneDocumento.getDocumento().setDtScadenzaDoc(null);
//		}
//	}
		
		Intestatari intestatarioRT = irisCache.getAnagraficaRT();

		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		
		/*
		 * Con l'eliminazione del conto tecnico, Regione Toscana ha
		 * richiesto di impostare l'IBAN relativo al tributo pagato
		 */
		// contoTecnicoNoCache is Casalino approved
		// qui ci si passa allo "stampa documento"
		ContoTecnico contoTecnicoNoCache = new ContoTecnico(); // shallow copy minimale per evitare problemi di concorrenza dull'iban
		contoTecnicoNoCache.setIban(contoTecnico.getIban());
		contoTecnicoNoCache.setIntestatario(contoTecnico.getIntestatario()); // l'intestatario e' ancora quello della cache ma non ci da problemi a valle
		Set<CondizioneDocumento> condizioni = createdDDP.getCondizioni();
		for (CondizioneDocumento condizioneDocumento: condizioni) {
			String idTributo = condizioneDocumento.getCondizionePagamento().getCdTrbEnte();
			String idEnte = condizioneDocumento.getCondizionePagamento().getEnte().getIdEnte();
			TributoEnte te = tributoEnteBusinessLocal.getTributiEntiByKey(idEnte, idTributo);
			contoTecnicoNoCache.setIban(te.getIBAN());
		}
		
		List<DocumentoDiPagamento> ddpListEntityOut =  new ArrayList<DocumentoDiPagamento>();
	
		ddpListEntityOut.add(createdDDP);
		
		return  DDPDTOBuilder.populateDDPDTOList(intestatarioDDP, intestatarioRT, ddpListEntityOut, null, contoTecnicoNoCache, locale);
		
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.facade.ejb.client.posizionedebitoria.DDPFacade#createDDPMultipleBiller(it.tasgroup.iris.dto.ddp.DDPInputDTO, java.lang.String, java.util.Locale)
	 */
	@Override
	public List<DocumentoDiPagamentoDTO> createDDPMultiBiller(DDPInputDTO inputDTO, Locale locale) throws BusinessConstraintException {
		
		// nel Facade concentro tutti i metodi di controllo e popolamento del DTO  specifici del DocumentWS
		// nella speranza di poter usare lo stesso BusinessBean per creare i DDP sia di DocumentWS, che di Iris Webapp che di IrisWS
		CfgGatewayPagamento cfgGateway = cfgBusinessBean.getCfgGatewayPagamento(inputDTO.getTipoDocumento());
		
		checkCfgGatewayPagamento(cfgGateway, inputDTO.getTipoDocumento());
		
		Enti ente = intestatariBusinessBean.readEnte(inputDTO.getCodEnte());
		
		checkEnte(ente, inputDTO.getCodEnte());
			
		String idEnte = ente.getIdEnte();
		
		String idPagamento = inputDTO.getCondizioniCarrello().get(0);
		
		List<CondizionePagamento> condizioni = ddpBusinessBean.getListCondizioniByIdPagamento(idPagamento, null, idEnte);
		
		checkCondizioniPagamento(condizioni, idPagamento, idEnte);
		
		checkCondizioniPagamentoPagabili(condizioni);
		
		CondizionePagamento condizione = condizioni.get(0);

		DestinatariPendenza destinatario = BillInspector.getDestinatario(condizione.getPendenza());
		
		String cfPagante = destinatario.getCoDestinatario();
		
		Intestatari intestatarioDDP = intestatariBusinessBean.readIntestatarioByLapl(cfPagante, true);
				
		inputDTO.getCondizioniCarrello().clear();
		
		inputDTO.getCondizioniCarrello().add(condizione.getIdCondizione());
		
		inputDTO.setLoggedIntestatario(intestatarioDDP.getCorporate());
		
		inputDTO.setOperatorUsername(cfPagante);
		
		inputDTO.setCfPagante(cfPagante);
		
		// TODO PAZZIK, estrarre da COMMUNICATION
		inputDTO.setEmailPagante("k.p@ciao.it");
		
		DocumentoDiPagamento createdDDP = ddpBusinessBean.createDDP(inputDTO, intestatarioDDP, cfgGateway);
		
		Intestatari intestatarioEnte = (Intestatari) ente.getIntestatarioobj();
		
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		
		List<DocumentoDiPagamento> ddpListEntityOut =  new ArrayList<DocumentoDiPagamento>();
	
		ddpListEntityOut.add(createdDDP);
		
		CfgEntiLogo logoEnte = cfgBusinessBean.getCfgEntiLogoById(idEnte);
		
		List<DocumentoDiPagamentoDTO> dtoList = DDPDTOBuilder.populateDDPDTOList(intestatarioDDP, intestatarioEnte, ddpListEntityOut, logoEnte, contoTecnico, locale);

		byte[] bytesReport = ddpBusinessBean.creaPdfDocumentoDiPagamento(dtoList, locale);

		DocumentiRepository docRepo = DDPDTOBuilder.makeDocumentiRepository(bytesReport, dtoList.get(0).getNomeFile(), intestatarioDDP.getLapl());
		
		ddpBusinessBean.createDocumentiRepository(docRepo,dtoList.get(0).getId());
		
		DocumentoDiPagamentoDTO ddpDTO = dtoList.get(0);
		
		DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(ddpDTO, docRepo);
		
		ddpDTO.setDocumentoRepository(docRepoDTO);
		
		return  dtoList;
		
	}
	
	private void checkCondizioniPagamento(List<CondizionePagamento> condizioni, String idPagamento, String idEnte) throws InvalidInputException {
		
		if (condizioni == null || condizioni.size() != 1)
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and ID_ENTE=" + idEnte + " is not enabled for DOCUMENTS_WS.");
				
	}
	
//	private  CondizionePagamento checkCondizionePagata(List<CondizionePagamento> condizioni, String idPagamento, String idEnte) throws InvalidInputException {
//		
//		List<CondizionePagamento> pagate = new ArrayList<CondizionePagamento>();
//		
//		if (condizioni == null )
//			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and ID_ENTE=" + idEnte + " not found.");
//		
//		// Rimuovo dalla lista quelle non pagate!
//		for (CondizionePagamento c:condizioni) {
//			c.updateStatoPagamentoCalcolato();
//			if (c.getStatoPagamentoCalcolato()==EnumStatoPagamentoCondizione.PAGATA) {
//				pagate.add(c);
//			}
//		}
//		
//		if (pagate.size()>1)
//			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and ID_ENTE=" + idEnte + " has more than 1 payment");
//		
//		return pagate.get(0);
//		
//				
//	}

	private void checkEnte(Enti ente, String codEnte) throws InvalidInputException {
		
		if (ente == null)
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILLER, null, "Biller with COD_ENTE=" + codEnte + " is not enabled for DOCUMENTS_WS.");
		
		
	}


	@Override
	public DocumentoRepositoryDTO getRicevutaPagamento(Locale locale, String idPagamento,String codPagamento,String codPagante) throws BusinessConstraintException {
		Long id = Long.parseLong(idPagamento);
		// recupero il pagamento byId
		Pagamenti pagamento = ddpBusinessBean.readPagamentoById(id);
		//Secure check...
		if (!(pagamento.getFlussoDistinta().getUtentecreatore().equals(codPagante) && pagamento.getFlussoDistinta().getCodPagamento().equals(codPagamento)) ) {
			System.out.println("codPagante="+codPagante+" atteso="+pagamento.getFlussoDistinta().getUtentecreatore());
			System.out.println("codPagamento="+codPagamento+" atteso="+pagamento.getFlussoDistinta().getCodPagamento());
			throw new RuntimeException("Secure Check Failed");
		}
		List<DocumentoRepositoryDTO> ricevuteDocRepo = new ArrayList();
		DocumentoRepositoryDTO docRepoDTO = null;
		GestioneFlussi distinta = pagamento.getFlussoDistinta(); 
		boolean saveReceipt = cpl.getBooleanProperty("save.receipt.on.documenti_repository");
		if (pagamento.getIdDocumentoRepository() == null) {
			if(saveReceipt) {
				// genero tutti i documenti della distinta
				ricevuteDocRepo = generaRicevuteDelCarrello(distinta.getCodPagamento(), distinta.getId().toString(), distinta.getUtentecreatore() , locale, false, EnumAttachmentRendering.ASIS);
				// leggo di nuovo il pagamento che ora DEVE avere IdDocumentoRepository() != null
				pagamento = ddpBusinessBean.readPagamentoById(id);
			} else {
				// genero solo un documento
				PagamentoDTOLightForReceipt pagamentoDTO = new PagamentoDTOLightForReceipt();
				pagamentoDTO.setId(id);
				pagamentoDTO.setCodPagamento(codPagamento);
				ricevuteDocRepo = generaRicevutaSingolaDelCarrello(pagamentoDTO, distinta.getId().toString(), distinta.getUtentecreatore() , locale, false, EnumAttachmentRendering.ASIS);
				docRepoDTO = ricevuteDocRepo.get(0);
			}
		}
		// infine recupero il PDF dal repository byIdRepo 
		if(saveReceipt) {
	    	DocumentiRepository ricevutaDocRepo = ddpBusinessBean.readDocumentiRepositoryById(pagamento.getIdDocumentoRepository());
	    	docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(null, ricevutaDocRepo);
	    }
		// informazioni per la filigrana
//		final Pagamenti p = pagamento;
//		PrintableDocument ricevutaPrintInfo = new PrintableDocument() {
//			@Override
//			public Boolean needWatermark() {
//				return "1".equals(p.getFlagIncasso()) || "2".equals(p.getFlagIncasso());
//			}
//			@Override
//			public String getWatermarkText(ResourceBundle bundle) {
//				return bundle.getString("iris.drp.stato.incassato.watermark");
//			}
//		};
//		
//		// popolo docRepoDTO e se necessario appongo la filigrana al volo
//		DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(ricevutaPrintInfo, ricevutaDocRepo);
		return docRepoDTO;
	}

	@Override
	public List<DocumentoRepositoryDTO> generaRicevuteDelCarrello(String codPagamento, String idFlusso, String codPagante, Locale locale, boolean createResult, EnumAttachmentRendering attachmentRendering) throws BusinessConstraintException {
		List<DocumentoRepositoryDTO> result = new ArrayList<DocumentoRepositoryDTO>();
		GestioneFlussi distinta = distintePagamentoBusinessBean.getDistintaPagamentoForRicevuta(codPagamento, idFlusso, codPagante);
		Intestatari intestatarioRT = irisCache.getAnagraficaRT();
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		Set<Pagamenti> pagamenti = distinta.getPagamenti();
		Pagamenti pag1 = pagamenti.iterator().next();
		TributoEnte tribEnte = tributoEnteBusinessBean.getTributiEntiByKey(pag1.getIdEnte(), pag1.getCdTrbEnte());
		List<DocumentoDiPagamentoDTO> dtoList = DRPDTOBuilder.populateDRPDTOList(distinta, intestatarioRT, pagamenti, contoTecnico, locale, tribEnte);
		DocumentoDiPagamentoDTO drpDTO = dtoList.get(0);
		// parcheggio qui il carrello e il nome file
		List<CondizioneDDPDTO> carrello = drpDTO.getCarrello();
		for (Pagamenti pagamento : distinta.getPagamenti()) { //qui ne devo generare solo uno se non lo salvo
			getRicevutaPagamento(locale, createResult, attachmentRendering, result, distinta, dtoList, drpDTO, carrello, pagamento);
		}
		return result;
	}
	
	@Override
	public List<DocumentoRepositoryDTO> generaRicevutaSingolaDelCarrello(PagamentoDTOLightForReceipt pagamentoDTO, String idFlusso, String codPagante, Locale locale, 
			boolean createResult, EnumAttachmentRendering attachmentRendering) throws BusinessConstraintException {
		List<DocumentoRepositoryDTO> result = new ArrayList<DocumentoRepositoryDTO>();
		GestioneFlussi distinta = distintePagamentoBusinessBean.getDistintaPagamentoForRicevuta(pagamentoDTO.getCodPagamento(), idFlusso, codPagante);
		Intestatari intestatarioRT = irisCache.getAnagraficaRT();
		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		Pagamenti pagamento = ddpBusinessBean.readPagamentoById(pagamentoDTO.getId());
		Set<Pagamenti> pagamenti = new HashSet<Pagamenti>();
		pagamenti.add(pagamento); 

		Pagamenti pag1 = pagamenti.iterator().next();
		
		TributoEnte tribEnte = tributoEnteBusinessBean.getTributiEntiByKey(pag1.getIdEnte(), pag1.getCdTrbEnte());

		List<DocumentoDiPagamentoDTO> dtoList = DRPDTOBuilder.populateDRPDTOList(distinta, intestatarioRT, pagamenti, contoTecnico, locale,tribEnte);
		DocumentoDiPagamentoDTO drpDTO = dtoList.get(0);
		List<CondizioneDDPDTO> carrello = drpDTO.getCarrello();
		getRicevutaPagamento(locale, createResult, attachmentRendering, result, distinta, dtoList, drpDTO, carrello, pagamento);
		return result;
	}

	private void getRicevutaPagamento(Locale locale, boolean createResult, EnumAttachmentRendering attachmentRendering,
			List<DocumentoRepositoryDTO> result, GestioneFlussi distinta, List<DocumentoDiPagamentoDTO> dtoList,
			DocumentoDiPagamentoDTO drpDTO, List<CondizioneDDPDTO> carrello, Pagamenti pagamento) {
		
		DocumentiRepository ricevutaDocRepo = null;
		boolean saveReceipt = cpl.getBooleanProperty("save.receipt.on.documenti_repository");
		if(pagamento.getIdDocumentoRepository() == null) { //&& !getExternalReceiptCreation()
			//
			// in questo caso la ricevuta non e' mai stata generata
			//
			AllegatiPendenza allegato = ddpBusinessBean.getRicevutaAllegatoCondizione(pagamento.getIdPendenza(), pagamento.getCondPagamento().getIdCondizione(), locale, EnumCodificaAllegato.PDF);
			// N.B. il carrello nella dtoList per la Ricevuta deve contenere solo il pagamento corrente ...
			List<CondizioneDDPDTO> carrelloRicevuta = creaCarrelloMonoCondizione(carrello, pagamento, allegato != null);
			String receiptCustomPrefix = conf.getProperty("configuration.ddp.prefix");
			String prefix = "";
			if(receiptCustomPrefix.length() > 0)
				prefix = receiptCustomPrefix + "_";
			String nomeFileRicevuta = prefix + CommonConstant.RICEVUTA_FILE_NAME_PREFIX + pagamento.getCondPagamento().getIdPagamento();
			drpDTO.setCarrello(carrelloRicevuta);
			drpDTO.setNomeFile(nomeFileRicevuta);
			String cfAnonimo = ConfigurationPropertyLoader.getInstance( "iris-fe.properties").getProperty("iris.codice.fiscale.anonymous");
			if(drpDTO.getCfPagante().equals(cfAnonimo))
				drpDTO.setCfPagante("ANONIMO");
			if(drpDTO.getCfIntestatarioPendenza().equals(cfAnonimo))
				drpDTO.setCfIntestatarioPendenza("ANONIMO");
			
			if(drpDTO.getCfPagante().equals("ANONYMOUS"))
				drpDTO.setCfPagante("NON SPECIFICATO");
			// genero la ricevuta
			byte[] pdfRicevuta = ddpBusinessBean.creaPdfRicevuta(dtoList, locale);
			pdfRicevuta = PDFAUtils.fixPrintFlag(pdfRicevuta);
			// gestisco eventuali allegati di tipo RICEVUTA PDF
			if (allegato != null) {
				try {
					byte[] decodedAttachment = Base64.decodeBase64(allegato.getDatiBody());
//						if (EnumTipoDRP.PSTSPA.getIdCfgModalitaPagamento().equals(distinta.getCfgGatewayPagamento().getId()))
//							isImageAttachment = true;
					pdfRicevuta = PdfUtils.manipulatePdf(pdfRicevuta, decodedAttachment, attachmentRendering);
				} catch (Exception e) {
					LOGGER.error("Error on generaRicevuteDelCarrello", e);
					throw new RuntimeException("Errore durante elaborazione allegato per ricevute", e);
				}
			}
			
		    if (saveReceipt) {
				ricevutaDocRepo = DDPDTOBuilder.makeDocumentiRepository(pdfRicevuta, drpDTO.getNomeFile(), distinta.getUtentecreatore());
				if (allegato != null && allegato.getAllegatoXML() != null)
					allegato = allegato.getAllegatoXML();
				ddpBusinessBean.createDocumentiRepository(ricevutaDocRepo, pagamento.getId(), allegato,null);
			} else {
				DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(null, pdfRicevuta, drpDTO.getNomeFile());
				result.add(docRepoDTO);
			}
		}
		if  (createResult && saveReceipt) {
			if (ricevutaDocRepo == null ) 
				// il documento e' gia' stato creato in precedenza - lo recupero dal db
				ricevutaDocRepo = ddpBusinessBean.readDocumentiRepositoryById(pagamento.getIdDocumentoRepository());
			
			// informazioni per la filigrana
//				final Pagamenti p = pagamento;
//				PrintableDocument printInfo = new PrintableDocument() {
//					@Override
//					public Boolean needWatermark() {
//						return "1".equals(p.getFlagIncasso()) || "2".equals(p.getFlagIncasso());
//					}
//					@Override
//					public String getWatermarkText(ResourceBundle bundle) {
//						return bundle.getString("iris.drp.stato.incassato.watermark");
//					}
//				};
//
//				DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(printInfo, ricevutaDocRepo);
			DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(null, ricevutaDocRepo);
			result.add(docRepoDTO);
		}
	}

	
	/**
	 * Restituisce tutte le ricevute relative al carrello (distinta) con idFlusso dato
	 * Se il carrello (distinta) contiene un solo elemento viene restituito l'unico file PDF della ricevuta.
	 * Se invece ci sono piu elementi viene restituito un archivio ZIP contenente i singoli file PDF delle ricevute. 
	 */
	@Override
	public DocumentoRepositoryDTO getRicevutaCarrello(String codPagamento, String idFlusso, String codPagante, Locale locale) throws BusinessConstraintException {

		List<DocumentoRepositoryDTO> ricevuteCarrello = generaRicevuteDelCarrello(codPagamento, idFlusso, codPagante, locale, true, EnumAttachmentRendering.ASIS);
		
			if (ricevuteCarrello.size() == 1) {
				// restituisco il pdf dell'unica ricevuta
				return ricevuteCarrello.get(0);
	
			} else {
				
				// preparo lo zip
				Map<String, byte[]> fileMapToZip = new HashMap<String, byte[]>();
				for (DocumentoRepositoryDTO ricevuta : ricevuteCarrello) {
					fileMapToZip.put(ricevuta.getFileName() + "." + ricevuta.getFileExtension(), ricevuta.getContent());
			}
			
			try {
				
				DocumentoRepositoryDTO docRepoDTO = new DocumentoRepositoryDTO();
				
				docRepoDTO.setContent(ZipUnzip.zipFileMap(fileMapToZip));
				
				docRepoDTO.setSize(docRepoDTO.getContent().length);
				
				String receiptCustomPrefix = conf.getProperty("configuration.ddp.prefix");
				
				String prefix = "";
				if(receiptCustomPrefix.length() > 0)
					prefix = receiptCustomPrefix + "_";
				
				docRepoDTO.setFileName(prefix + CommonConstant.RICEVUTA_FILE_NAME_PREFIX + codPagamento);
				
				docRepoDTO.setFileExtension("zip");
	
				return docRepoDTO;
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}	
	
	private List<CondizioneDDPDTO> creaCarrelloMonoCondizione(List<CondizioneDDPDTO> carrello, Pagamenti pagamento, boolean allegatoRicevuta) {
		List<CondizioneDDPDTO> carrelloMono = new ArrayList<CondizioneDDPDTO>();
		for (CondizioneDDPDTO condizioneDDPDTO : carrello) {
			if(condizioneDDPDTO.getIdCondizione().equals(pagamento.getCondPagamento().getIdCondizione())) {
				
				CfgEntiLogo logoEnte = cfgBusinessBean.getCfgEntiLogoById(condizioneDDPDTO.getEnte().getId());
				
				if (logoEnte!=null){
					
					CfgEntiLogoDTO dto = EntiDTOBuilder.fillCfgEntiLogoDTO(logoEnte);
					
					condizioneDDPDTO.getEnte().setLogo(dto);
				}
				condizioneDDPDTO.setImporto(pagamento.getImPagato());
				condizioneDDPDTO.setAllegatoRicevuta(allegatoRicevuta);
				carrelloMono.add(condizioneDDPDTO);
				break;
			}
		}
		return carrelloMono;
	}


	@Override
	public List<DocumentoDiPagamentoDTO> readDDPListByIdCondizione(String idCondizione, Locale locale) throws BusinessConstraintException {
		
		DocumentiRepository docRepo = null;
		
		List<DocumentoDiPagamento> ddpListEntityOut = ddpBusinessBean.readDDPListByIdCondizione(idCondizione);
		
		DocumentoDiPagamento ddp = ddpListEntityOut.get(0);
		
		Intestatari intestatarioRT = irisCache.getAnagraficaRT();
		
		Intestatari intestatarioDDP = intestatariBusinessBean.readIntestatarioById(((DocumentoDiPagamento) ddpListEntityOut.get(0)).getIntestatario());

		ContoTecnico contoTecnico = irisCache.getContoTecnico();
		
		// Per estrarre il logo del DDP si suppone che il carrello sia monoente.
		String idEnte = ddp.getCondizioni().iterator().next().getCondizionePagamento().getEnte().getIdEnte();
				
		CfgEntiLogo logoEnte = cfgBusinessBean.getCfgEntiLogoById(idEnte);
				
		List<DocumentoDiPagamentoDTO> dtos = DDPDTOBuilder.populateDDPDTOList(intestatarioDDP, intestatarioRT, ddpListEntityOut, logoEnte, contoTecnico, locale);
		
		if (ddp.getIdDocumentoRepository() == null) {			
		
			if (!ddp.getTipoDocumentoEnum().equals(EnumTipoDDP.FRECCIA)){
				
				byte[] reportFlow = ddpBusinessBean.creaPdfDocumentoDiPagamento(dtos, locale);
				
//				docRepo = DDPDTOBuilder.populateDocumentiRepository(reportFlow, ddp, intestatarioDDP);
				docRepo = DDPDTOBuilder.makeDocumentiRepository(reportFlow, dtos.get(0).getNomeFile(), intestatarioDDP.getLapl());
				
				ddpBusinessBean.createDocumentiRepository(docRepo, ddp);
			}
			
		} else 
			docRepo = ddpBusinessBean.readDocumentiRepositoryById(ddp.getIdDocumentoRepository());
		
		DocumentoRepositoryDTO docRepoDTO = DDPDTOBuilder.populateDocumentoRepositoryDTO(dtos.get(0), docRepo);
		
		dtos.get(0).setDocumentoRepository(docRepoDTO);
		
		return dtos;
	}
	
	@Override
	public void nullifyDDPList(String codFiscale, String[] ddpIds) throws SecurityException{
		
		ddpBusinessBean.nullifyDDPList(codFiscale, ddpIds);
		
	}

	
	public static String getIDAnagraficaRegioneToscana() { 
		
		return CommonConstants.ANAGRAFICA_RT;
		
	}

	@Override
	public void checkDoubleDocument(List<String> condizioniCarrello) throws BusinessConstraintException {
		
		ddpBusinessBean.checkDoubleDocument(condizioniCarrello);
		
	}

	@Override
	public DocumentoRepositoryDTO createNewDocumentiRepository(IProfileManager profile, DocumentoRepositoryDTO docRepoDTO, String idDDP) {
		
		DocumentiRepository docRepo = DDPDTOBuilder.populateDocumentoRepository(profile, docRepoDTO);
		
		DocumentiRepository docRepoCreated = ddpBusinessBean.createDocumentiRepository(docRepo, idDDP);
		
		return DDPDTOBuilder.populateDocumentoRepositoryDTO(null, docRepoCreated);
	}
	
	
	@Override
	public DocumentoRepositoryDTO createNewDocumentiRepository(String intestatarioDDP, DocumentoRepositoryDTO docRepoDTO, String idDDP) {
		
		Intestatari intestatario = intestatariBusinessBean.readIntestatarioByLapl(intestatarioDDP, false);
		
		DocumentiRepository docRepo = DDPDTOBuilder.populateDocumentoRepository(intestatario, docRepoDTO);
		
		DocumentiRepository docRepoCreated = ddpBusinessBean.createDocumentiRepository(docRepo, idDDP);
		
		return DDPDTOBuilder.populateDocumentoRepositoryDTO(null, docRepoCreated);
	}


	@Override
	public List<CfgGatewayPagamentoDTO> getLstCfgUtenteModalitaPagamentoUtilizzate(
			String codFiscale, BigDecimal importoTotalePagamenti) throws BusinessConstraintException {
		List<CfgUtenteModalitaPagamento> lst = ddpBusinessBean.readUtenteModalitaPagamentoUtilizzate(codFiscale);
		List<CfgGatewayPagamentoDTO> lstCfgGatewayPagamentoDTO = new ArrayList<CfgGatewayPagamentoDTO>();
		for (Iterator<CfgUtenteModalitaPagamento> iterator = lst.iterator(); iterator.hasNext();) {
			CfgUtenteModalitaPagamento cfgUtenteModalitaPagamento = (CfgUtenteModalitaPagamento) iterator.next();
			CfgGatewayPagamentoDTO cfgDTO = CfgPagamentiDTOBuilder.populateCfgGatewayPagamentoDTO(cfgUtenteModalitaPagamento.getGatewayPagamento());
			BigDecimal importoCommissioni = CommissioniCalculator.calcolaTotaleCommissioni(cfgUtenteModalitaPagamento.getGatewayPagamento().getCfgCommissionePagamenti(), importoTotalePagamenti);
			cfgDTO.getCfgModalitaPagamento().setImportoCommissioni(importoCommissioni);
			cfgDTO.getCfgModalitaPagamento().setImportoTotale(importoTotalePagamenti.add(importoCommissioni).setScale(2, RoundingMode.HALF_UP));
			lstCfgGatewayPagamentoDTO.add(cfgDTO);
		}
		return lstCfgGatewayPagamentoDTO;
	}
	@Override
	public List<CfgGatewayPagamentoDTO> getUtenteModalitaPagamentoUtilizzate(
			String codFiscale) throws BusinessConstraintException {
		List<CfgUtenteModalitaPagamento> lst = ddpBusinessBean.readUtenteModalitaPagamentoUtilizzate(codFiscale);
		List<CfgGatewayPagamentoDTO> lstCfgGatewayPagamentoDTO = new ArrayList<CfgGatewayPagamentoDTO>();
		for (Iterator<CfgUtenteModalitaPagamento> iterator = lst.iterator(); iterator.hasNext();) {
			CfgUtenteModalitaPagamento cfgUtenteModalitaPagamento = (CfgUtenteModalitaPagamento) iterator.next();
			CfgGatewayPagamentoDTO cfgDTO = CfgPagamentiDTOBuilder.populateCfgGatewayPagamentoDTO(cfgUtenteModalitaPagamento.getGatewayPagamento());
			
			lstCfgGatewayPagamentoDTO.add(cfgDTO);
		}
		return lstCfgGatewayPagamentoDTO;
	}
	@Override
	public List<CfgUtenteModalitaPagamentoDTO> readLstCfgUtenteModalitaPagamento(
			String codFiscale) throws BusinessConstraintException {
		List<CfgUtenteModalitaPagamento> lst = ddpBusinessBean.readUtenteModalitaPagamentoUtilizzate(codFiscale);
		
		List<CfgUtenteModalitaPagamentoDTO> lstCfgUtenteModalitaPagamentoDTO = CfgPagamentiDTOBuilder.populateListUtenteModalitaPagamentoDTO(lst);
		return lstCfgUtenteModalitaPagamentoDTO;
	}
	
	@Override
	public DocumentoRepositoryDTO createReceiptMultiBiller(DDPInputDTO inputDTO, Locale locale) throws BusinessConstraintException {
		
		Enti ente = intestatariBusinessBean.readEnte(inputDTO.getCodEnte());
		
		checkEnte(ente, inputDTO.getCodEnte());
		
		String idEnte = ente.getIdEnte();
		
		String idPagamento = inputDTO.getCondizioniCarrello().get(0);
		
		List<CondizionePagamento> condizioni = ddpBusinessBean.getListCondizioniByIdPagamento(idPagamento, null, idEnte);
		
		Pagamenti pagamento = getPagamento(condizioni,idPagamento,ente.getCodiceEnte());
		
		String cfPagante = pagamento.getCoPagante();
		
		GestioneFlussi distinta = pagamento.getFlussoDistinta();
		
		String idFlusso = distinta.getId().toString();
		
		String codPagamento = distinta.getCodPagamento();
		
		CfgGatewayPagamento cfgGateway = distinta.getCfgGatewayPagamento();
		
		DocumentoRepositoryDTO docRepo = getRicevutaCarrello(codPagamento, idFlusso, cfPagante, locale);
		
		if (cfgGateway.getCfgFornitoreGateway().getBundleKey().equals("NDP") && inputDTO.isIncludiRTAgid()){
			
			String iuv = distinta.getIuv();
			
			String idDominio = distinta.getIdentificativoFiscaleCreditore();
			
			String codContesto = distinta.getCodTransazionePSP();
			
			
			byte[] rtAGID = giornaleEventiBean.getGiornaleEventiDocumentiNDP(codContesto, idDominio, iuv, "RT",null);
			
			docRepo.setRtAGID(rtAGID);
					
		}
			
		return docRepo;	
	
	}
	
	
	private void checkCondizioniPagamentoPagabili(List<CondizionePagamento> condizioni) throws InvalidInputException {
		
		EsitoOperazionePagamentoDTO esitoInputDTO = new EsitoOperazionePagamentoDTO();
		
		EsitoOperazionePagamentoDTO esitoOutputDTO = autorizzazioneBean.checkCondizioniPagabili(esitoInputDTO, condizioni);
		
		if (esitoOutputDTO.isError())
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, esitoOutputDTO.getReturnCode().getChiave()+":"+esitoOutputDTO.getReturnCode().getDescrizione());
		
	}

	private Pagamenti getPagamento(List<CondizionePagamento> condizioni,String idPagamento, String cdEnte) throws InvalidInputException {
		
		List<CondizionePagamento> pagate = new ArrayList<CondizionePagamento>();
		
		if (condizioni == null || condizioni.size()==0)
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and SenderId=" + cdEnte + " not found.");
		
		// Rimuovo dalla lista quelle non pagate!
		for (CondizionePagamento c:condizioni) {
			c.updateStatoPagamentoCalcolato();
			if (c.getStatoPagamentoCalcolato()==EnumStatoPagamentoCondizione.PAGATA) {
				pagate.add(c);
			}
		}
		
		if (pagate.size()>1)
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and SenderId=" + cdEnte + " has more than 1 payment");
		
		if (pagate.size() == 0)
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and SenderId=" + cdEnte + " has not been payed.");

		
		CondizionePagamento cond = pagate.get(0);
		
		Pagamenti pagamento = BillItemInspector.getMainPayment(cond);
		
		if (pagamento == null)
			throw new InvalidInputException(EnumBusinessErrorCodes.APPEXC_NOBILL, null, "Bill with ID_PAGAMENTO=" + idPagamento+ " and SenderId=" + cdEnte + " has not been payed.");
		
		return pagamento;
		
	}

	/*
	public static Boolean getExternalReceiptCreation() {
		
		if (externalReceiptCreation==null) {
			
			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
			
			externalReceiptCreation= new Boolean(props.getBooleanProperty("generate.external.receipt"));
		
		}
		
		return externalReceiptCreation;
	}
	*/
	
	@Override
	public void ackRicevuta(String senderId, String idPagamento, boolean includiRTAgid) throws InvalidInputException {
		
		Enti ente = intestatariBusinessBean.readEnte(senderId);
		
		checkEnte(ente, senderId);
		
		String idEnte = ente.getIdEnte();
		
		List<CondizionePagamento> condizioni = ddpBusinessBean.getListCondizioniByIdPagamento(idPagamento, null, idEnte);
		
		Pagamenti pagamento = getPagamento(condizioni,idPagamento,ente.getCodiceEnte());
		
		GestioneFlussi distinta = pagamento.getFlussoDistinta();
		
		CfgGatewayPagamento cfgGateway = distinta.getCfgGatewayPagamento();
		
		Long idDocRepo = pagamento.getIdDocumentoRepository();
		
		if (idDocRepo != null)
		
			ddpBusinessBean.ackRicevuta(idDocRepo);
		
		if (cfgGateway.getCfgFornitoreGateway().getBundleKey().equals("NDP") && includiRTAgid){
			
			String iuv = distinta.getIuv();
			
			String idDominio = distinta.getIdentificativoFiscaleCreditore();
			
			String codContesto = distinta.getCodTransazionePSP();
			
			giornaleEventiBean.ackRTAgid(codContesto, idDominio, iuv, "RT");
		}
														
		
	}

	
}
