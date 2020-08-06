package it.tasgroup.iris.business.ejb.ddp;

import java.net.URL;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.CommonConstant;
import it.nch.is.fo.profilo.IndirizzipostaliCommon;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.tributi.JltentrId;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.profile.IProfileManager;
import it.tasgroup.iris.business.ejb.client.ddp.DDPBusinessLocal;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgUtenteModalitaPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.PrenotazioneAvvisiDigitali;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ListaDocumentiInputDTO;
import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.dto.anagrafica.IndirizzoDTO;
import it.tasgroup.iris.dto.anagrafica.IntestatarioDTO;
import it.tasgroup.iris.dto.ddp.CondizioneDDPDTO;
import it.tasgroup.iris.dto.ddp.DDPInputDTO;
import it.tasgroup.iris.dto.ddp.DettaglioNDPDTO;
import it.tasgroup.iris.dto.ddp.DocumentoDiPagamentoDTO;
import it.tasgroup.iris.dto.exception.BollettinoFrecciaMissingAddressException;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.DDPMultiplicityViolationException;
import it.tasgroup.iris.dto.exception.DuplicatedDocumentException;
import it.tasgroup.iris.dto.exception.PaymentBusinessException;
import it.tasgroup.iris.dto.exception.SecurityException;
import it.tasgroup.iris.dto.exception.UnavailableDDPBonException;
import it.tasgroup.iris.persistence.dao.interfaces.AllegatiPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgUtenteModalitaPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.DDPDAO;
import it.tasgroup.iris.persistence.dao.interfaces.DocumentiRepositoryDAO;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PrenotazioneAvvisiDigitaliDao;
import it.tasgroup.iris.persistence.dao.interfaces.TributoEnteDao;
import it.tasgroup.iris.persistence.dao.util.DDP_IDGenerator;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.report.ReportManager;
import it.tasgroup.report.exporter.ReportExporterFactory;
import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;
import it.tasgroup.services.util.enumeration.EnumStatoRiga;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;
import net.sf.jasperreports.engine.JRParameter;

@Stateless(name = "DDPBusiness")
public class DDPBusinessBean implements DDPBusinessLocal {
	
	private static final Logger LOGGER = LogManager.getLogger(DDPBusinessBean.class);
	
	@EJB(name = "DocumentoDiPagamentoDaoImpl")
	private DDPDAO ddpDAO;
	
	@EJB(name = "DocumentiRepositoryDaoImpl")
	private DocumentiRepositoryDAO docRepoDAO;
	
	@EJB(name = "CondizioniPagamentoDao")
	private CondizioniPagamentoDao condizioniPagamentoDAO;
	
	@EJB(name = "CfgGatewayPagamentoDao")
	private CfgGatewayPagamentoDao cfgGatewayPagamentoDao;	
	
	@EJB(name = "GestioneFlussiDaoService")	
	private GestioneFlussiDao gestioneFlussiDao;
	
	@EJB(name = "PagamentiDaoService")
	private PagamentiDao pagamentiDao;

	@EJB(name = "CfgUtenteModalitaPagamentoDao")
	private CfgUtenteModalitaPagamentoDao cfgUtenteModalitaPagamentoDao;	
	
	@EJB(name = "AllegatiPendenzaDao")
	private AllegatiPendenzaDao allegatiPendenzaDao;
	
	@EJB(name = "EntiDaoService")
	private EntiDao entiDao;
	
	@EJB(name = "PrenotazioneAvvisiDigitaliDao")
	private PrenotazioneAvvisiDigitaliDao prenotazioneAvvisiDigitaliDao;

	@EJB(name = "TributoEnteDaoService")
	private TributoEnteDao tributiEntiDao;
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.bmi.posizionedebitoria.DDPService#readDDPs(java.util.Map)
	 */
	@Override
	public List<DocumentoDiPagamento> readDDPs(String codFiscale, String azienda, ContainerDTO dto) throws SecurityException {
		
		String docId = ((ListaDocumentiInputDTO)dto.getInputDTO()).getDocIdFilter();

		if (!StringUtils.isEmpty(docId))
			DDP_IDGenerator.checkCRC(docId);
		
		List<DocumentoDiPagamento> list = ddpDAO.listDDPByFilterParameters(null, null, dto);

		return list;
	}
	
	@Override
	public List<DocumentoDiPagamento> readDDPsForBackOffice(ContainerDTO dto) throws SecurityException {
		
		ListaDocumentiInputDTO inDTO = (ListaDocumentiInputDTO)dto.getInputDTO();
		
		String codPagante = inDTO.getCodPagante();
		
		List<DocumentoDiPagamento> list = ddpDAO.listDDPByFilterParameters(null, codPagante, dto);

		return list;
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.bmi.posizionedebitoria.DDPService#readDDPs(java.util.Map)
	 */
	@Override
	public DocumentoDiPagamento readSingleDDP_BKOF(String docId){
		
		DDP_IDGenerator.checkCRC(docId);
		
		DocumentoDiPagamento ddp = ddpDAO.retrieveDDPById(docId);

		return ddp;
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.posizionedebitoria.DDPBusiness#createDDP(it.nch.profile.IProfileManager, it.tasgroup.iris.dto.ddp.DDPInputDTO)
	 */
	@Override
	public DocumentoDiPagamento createDDP(IProfileManager profile, DDPInputDTO inputDTO, Intestatari intestatarioDDP)throws BusinessConstraintException {
		
		inputDTO.setEmailPagante(profile.getEmailPagante());
		inputDTO.setCfPagante(profile.getCodFiscalePagante());
		inputDTO.setLoggedIntestatario(profile.getAzienda());
		
		String operatorUsername = profile.getCodiceFiscale()!=null?profile.getCodiceFiscale():profile.getUsername();  
        //
        //  patch per gestire errore nella generazione massiva documenti da backoffice (getCodiceFiscale == NULL)
        //

		inputDTO.setOperatorUsername(operatorUsername);
		
		CfgGatewayPagamento cfgGateway = cfgGatewayPagamentoDao.getCfgGatewayPagamentoById(inputDTO.getIdGateway());
				
		DocumentoDiPagamento ddpCreated = createDDP(inputDTO, intestatarioDDP, cfgGateway);
		
		return ddpCreated;
		
	}
	
	private void checkBollettinoFreccia(Intestatari intestatarioDDP,DDPInputDTO dto, String mezzoDiPagamento) throws BollettinoFrecciaMissingAddressException {
		
		if (EnumTipoModalitaPagamento.BOLLETTINOFRECCIA.getChiave().equals(mezzoDiPagamento)) {
			IndirizzipostaliCommon indirizzoPostale = intestatarioDDP.getIndirizzipostaliobj();
			if (StringUtils.isEmpty(indirizzoPostale.getCapCodeIForm())
					|| StringUtils.isEmpty(indirizzoPostale.getCityIForm())
					|| StringUtils.isEmpty(indirizzoPostale.getAddressIForm())) {
				
				throw new BollettinoFrecciaMissingAddressException();
				
			}
		}
		
	}

	public void checkEnteTributo(DDPInputDTO dto) throws BusinessConstraintException {
		
		String selectedEnte = null;
		
		String selectedTributo = null;
		
		List<CondizionePagamento> condizioni = condizioniPagamentoDAO.getCondizioniByIdList(dto.getCondizioniCarrello());
		
		for(CondizionePagamento cond: condizioni){
			
			if (selectedEnte == null)
				selectedEnte = cond.getEnte().getCodiceEnte();
			else if(!selectedEnte.equals(cond.getEnte().getCodiceEnte()))
				throw new UnavailableDDPBonException();
			
			if (selectedTributo == null)
				selectedTributo = cond.getCdTrbEnte();
			else if(!selectedTributo.equals(cond.getCdTrbEnte()))
					throw new UnavailableDDPBonException();	
				
		}
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.posizionedebitoria.DDPBusiness#createDDP(it.tasgroup.iris.dto.ddp.DDPInputDTO)
	 */
	@Override
	public DocumentoDiPagamento createDDP(DDPInputDTO inputDTO, Intestatari intestatarioDDP, CfgGatewayPagamento cfgGateway) throws BusinessConstraintException {
		checkMultiplicity(inputDTO, cfgGateway.getMolteplicita());
		DocumentoDiPagamento documentoDiPagamento = checkExistingDocument(inputDTO.getCondizioniCarrello()); 
        if (documentoDiPagamento == null) { 
            String mezzoDiPagamento = cfgGateway.getCfgModalitaPagamento().getId().toString(); 
            checkBollettinoFreccia(intestatarioDDP, inputDTO, mezzoDiPagamento); 
            checkEnteTributo(inputDTO, mezzoDiPagamento); 
            documentoDiPagamento = ddpDAO.createDDP(inputDTO, cfgGateway); 
        } else {
        	documentoDiPagamento.setEmailVersante(inputDTO.getEmailPagante());
        	documentoDiPagamento.setCodFiscalePagante(inputDTO.getCfPagante());
        }
        return documentoDiPagamento; 
	}
	
	private void checkMultiplicity(DDPInputDTO inputDTO, String molteplicita) throws DDPMultiplicityViolationException {
		
		Integer numCondizioniSelezionate = inputDTO.getCondizioniCarrello().size();
		
		EnumTipoDDP tipoDocSelezionato = inputDTO.getTipo();
		
		Integer molteplicitaConfigurata = Integer.parseInt(molteplicita);
				
		if (molteplicitaConfigurata < numCondizioniSelezionate)
			throw new DDPMultiplicityViolationException(tipoDocSelezionato,numCondizioniSelezionate,molteplicitaConfigurata);
			
	}
	
	@Override
	public void checkDoubleDocument(List<String> condizioniCarrello) throws BusinessConstraintException {
		try {
			List<DocumentoDiPagamento> documentiPagamento = ddpDAO.listDDPBylistIdCondizioni(condizioniCarrello);
			ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		    String controllaEsistenzaBollettini = cpl.getProperty("checkExists.bollettino.associato.enabled");
			if(!documentiPagamento.isEmpty()) {
			    if (controllaEsistenzaBollettini.equalsIgnoreCase("true")) {
			    	throw new DuplicatedDocumentException(condizioniCarrello);
			    } 
			}
		} catch (Throwable t) {
			throw new PaymentBusinessException(condizioniCarrello);
		}
	}
	
	@Override
    public DocumentoDiPagamento checkExistingDocument(List<String> condizioniCarrello) throws BusinessConstraintException {
        try {
            List<DocumentoDiPagamento> documentiPagamento = ddpDAO.listDDPBylistIdCondizioni(condizioniCarrello);
            if (!documentiPagamento.isEmpty()) {
                return documentiPagamento.get(0);
            }
        } catch (Throwable t) {
            throw new PaymentBusinessException(condizioniCarrello);
        }
        return null;
    } 


	private void checkEnteTributo(DDPInputDTO dto, String mezzoDiPagamento) throws BusinessConstraintException {
		
		if (EnumTipoModalitaPagamento.BONIFICOCODPRED.getChiave().equals(mezzoDiPagamento))
			
			checkEnteTributo(dto);
	
	}


	@Override
	public void nullifyDDPList(String codFiscale, String[] ddpIds) throws SecurityException{
		
		DDP_IDGenerator.checkCRC(ddpIds);
					
		ddpDAO.nullifyDDPList(codFiscale, ddpIds);
		
	}

	@Override
	public List<DocumentoDiPagamento> readDDPListByIdCondizione(String idCondizione){
		
		List<DocumentoDiPagamento> ddpList = ddpDAO.listDDPByIdCondizione(idCondizione);
		
		return ddpList;
	}
	
	
	@Override
	public CondizionePagamento readCondizionePagamento(String idCondizione) {
		
		CondizionePagamento condizionePagamento = condizioniPagamentoDAO.getSingleCondizioneById(idCondizione);
		
		return condizionePagamento;
	}

	@Override
	public DocumentiRepository readDocumentiRepositoryById(Long idDocRepository) {
		
		DocumentiRepository docRepository = docRepoDAO.retrieveById(idDocRepository);
		
		return docRepository;
	}
	
	@Override
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, DocumentoDiPagamento ddp) {		
		DocumentiRepository created = docRepoDAO.createDocumentiRepository(docRepo);
		ddp.setIdDocumentoRepository(created.getId());
		ddpDAO.updateDDP(ddp);
		return created;
	}
	
	@Override
	public DocumentiRepository updateDocumentiRepository(DocumentiRepository docRepo) {
		DocumentiRepository docRepository = docRepoDAO.updateDocumentiRepository(docRepo);
		return docRepository;
	}
	
	@Override
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, GestioneFlussi distinta) {		
		DocumentiRepository created = docRepoDAO.createDocumentiRepository(docRepo);
		distinta.setIdDocumentoRepository(created.getId());
		gestioneFlussiDao.saveFlusso(distinta);
		return created;
	}

	@Override
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, Long idPagamento, AllegatiPendenza allegato, String idDocumentoExt) {		
		
		DocumentiRepository created = docRepoDAO.createDocumentiRepository(docRepo);
			
		pagamentiDao.updatePagamentoIdRepository(idPagamento, created.getId(),idDocumentoExt);
				
		if (allegato != null) {
			
			try {
				
				ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("iris-be.properties");
				
				boolean cancellaAllegato = cpl.getBooleanProperty("iris.svecchiamento.allegati.cancellazione");
				
				if(cancellaAllegato) {
					// cancellazione fisica
					allegatiPendenzaDao.delete(allegato);
				} else {
					// cancellazione logica
					allegato.setStRiga(EnumStatoRiga.INVALIDATED.getChiave());
					allegatiPendenzaDao.save(allegato);
				}
				
			} catch (Exception e) {
				LOGGER.error("error saving allegato, ID_ALLEGATO = " + allegato.getIdAllegato(), e);
				throw new DAORuntimeException(e);
			}
		}
		return created;
	}
	
	@Override
	public DocumentiRepository createDocumentiRepository(DocumentiRepository docRepo, String idDDP) {

		DocumentiRepository created = docRepoDAO.createDocumentiRepository(docRepo);
		DocumentoDiPagamento ddp = ddpDAO.retrieveDDPById(idDDP);
		ddp.setIdDocumentoRepository(created.getId());
		ddpDAO.updateDDP(ddp);
			
		return created;
	}
	

	@Override
	public byte[] creaPdfDocumentoDiPagamento(List<DocumentoDiPagamentoDTO> dtoList, Locale locale) {
		//BollettinoPagamentoBindingStub loaderService;
		byte[] reportFlow = null;
		//BollettinoPagamentoResponse response = null;
		ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		boolean useExternalBollettinoService = props.getBooleanProperty("iris.use.external.bollettino.service");
		DocumentoDiPagamentoDTO dto = dtoList.get(0);
		if(!useExternalBollettinoService || (dto.getTipoDocumento().compareTo(EnumTipoDDP.NDP) != 0)){
			Map<String, Object> reportParametersMap = new HashMap<String, Object>();

			// TODO PAZZIK verificare quali locale posso trovare su BE
			// per ora lo prendo dal FE

			reportParametersMap.put("REPORT_LOCALE", locale == null ? Locale.ITALIAN : locale); // DEFAULT ITALIANO

			String reportName = CommonConstant.DDP_REPORT_NAME;
			if (dtoList.get(0).getTipoDocumento()== EnumTipoDDP.NDP) {
				reportName = CommonConstant.DDP_REPORT_NDP_NAME;
			}
			ReportManager reportGenerator = new ReportManager(
					reportName, 
					ReportExporterFactory.PDF_EXT, 
					reportParametersMap,
					CommonConstant.DDP_FOLDER_NAME);

			reportFlow = reportGenerator.generateReport(dtoList);
		}
		else {
			/*String url = props.getProperty("iris.external.bollettino.service.url");
			//String url = "http://localhost:8080/bollettino-services/BollettinoService";
			try {
				loaderService = new BollettinoPagamentoBindingStub(new URL(url), new org.apache.axis.client.Service());
				BollettinoPagamentoRequest request = createBollettino(dtoList.get(0));
				response = loaderService.getBollettino(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
			reportFlow = response.getFileBollettino();*/
		}
        //---- BEGIN si inserisce riga in tabella PRENOTA_AVVISI_DIGITALI
		String idCondizione= dtoList.get(0).getCarrello().get(0).getIdCondizione();
		CondizionePagamento cond = condizioniPagamentoDAO.getSingleCondizioneById(idCondizione);
		if ("H".equals(cond.getStRiga())) {
			try {
			   PrenotazioneAvvisiDigitali pren = buildPrenotazAvvDigitFromCond(cond, "C");
		       prenotazioneAvvisiDigitaliDao.create(pren);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//---- END si inserisce riga in tabella PRENOTA_AVVISI_DIGITALI
		return reportFlow;
	}
	
    private PrenotazioneAvvisiDigitali buildPrenotazAvvDigitFromCond( CondizionePagamento c, String tipoOperaz) throws Exception {
		
    	JltentrId tePK = new JltentrId();
		tePK.setIdEntePk(c.getEnte().getIdEnte());
		tePK.setCdTrbEntePk(c.getCdTrbEnte());
		
		TributoEnte te = tributiEntiDao.getById(TributoEnte.class, tePK);
		
		PrenotazioneAvvisiDigitali p = new PrenotazioneAvvisiDigitali();
		p.setIdCondizione(c.getIdCondizione());              //- ID_CONDIZIONE
		p.setIdEnte(c.getEnte().getIdEnte());                          //- ID_ENTE
		p.setIdPagamento(c.getIdPagamento());                //- ID_PAGAMENTO (IUV)
		p.setCodiceAvviso(calculateNumeroAvviso(te, c.getIdPagamento(), false)); //- CODICE_AVVISO
		p.setTipoOperazioneOriginale(tipoOperaz);;           // - TIPO_OPERAZIONE_ORIG ('C','U','D')
		p.setIdFileAvvisatura(null);                            //- ID_FILE_NODOSPC
		p.setIdRichiestaAvviso(null);                       //- ID_RICHIESTA_NODOSPC
		p.setStatoAvviso("I");
		p.setTipoProcesso("RT");
		p.setNumTentativiAvviso(new Long(0));
		p.setOpInserimento("IDP"); 
		p.setTsInserimento(new Timestamp(System.currentTimeMillis()));
		p.setVersion(0L);
		return p;
	}
    
    /**
     *
     * @param tributo
     * @param IUV
     * @param formatted
     * @return
     */
    private static String calculateNumeroAvviso(TributoEnte tributo, String IUV,boolean formatted) {
    	String auxDigit = tributo.getNdpAuxDigit();
    	String codStazPa = tributo.getNdpCodStazPa();
    	String separator="";
    	if (formatted) separator=" ";

    	String numeroAvviso = null;
    	if ("0".equals(auxDigit)){
    		numeroAvviso = auxDigit+
    				separator+
    				codStazPa+
    				separator+
    				IUV;
    	} else
    		if ("1".equals(auxDigit)){
    			numeroAvviso = auxDigit+
    					separator+
    					IUV;
    		} else
    			if ("2".equals(auxDigit)){
    				numeroAvviso = auxDigit+
    						separator+
    						IUV;
    			} else
    				if ("3".equals(auxDigit)){
    					numeroAvviso = auxDigit+
    							separator+
    							IUV;
    				}   
    	return numeroAvviso;
    }
	/*
	private BollettinoPagamentoRequest createBollettino(DocumentoDiPagamentoDTO dto) {
		BollettinoPagamentoRequest bollettino = new BollettinoPagamentoRequest();
		EnteCreditore creditore = new EnteCreditore();
		InformazioniDebitore debitore = new InformazioniDebitore();
		InformazioniPagamento info = new InformazioniPagamento();
		
		try {
			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
			
			String bollettinoVersion = props.getProperty("bollettino.version"); 
			String language = props.getProperty("report.locale.language");
			String country = props.getProperty("report.locale.country");
			String variant = props.getProperty("report.locale.variant");
			LocaleConfig locale = new LocaleConfig();
			locale.setLanguage(language);
			locale.setCountry(country);
			locale.setVariant(variant);
			
			bollettino.setVersione(bollettinoVersion);
			bollettino.setLocale(locale);
			
			EnteDTO ente = dto.getCarrello().iterator().next().getEnte();
			creditore.setAnagrafica(ente.getDenominazione());
			creditore.setCap(ente.getCap());
			creditore.setCodEnte(ente.getCodice());
			creditore.setCodiceFiscale(ente.getCodiceFiscale());
			creditore.setIndirizzo(ente.getIndirizzo() + " " + ente.getNumeroCivico());
			creditore.setLocalita(ente.getCitta());
			creditore.setProvincia(ente.getProvincia());
			if(StringUtils.isBlank(ente.getSiaFormatted())) creditore.setSiaFormatted("N.D.");
			else creditore.setSiaFormatted(ente.getSiaFormatted());
			creditore.setIdEnte(ente.getId());
			// creditore.setAutorizzStampaDDP(ente.getAutorizzStampaDDP()); // ora e' sul tributo - vedi sotto 
			
			CondizioneDDPDTO condizione = dto.getCarrello().get(0);
			String causaleFinal = "";
			if ("2.1".equals(bollettinoVersion)) {
				causaleFinal= condizione.getCausaleNDPAvviso21();
			} else {
				causaleFinal=condizione.getCausaleNDP();
			}
			DettaglioNDPDTO dettagli = (DettaglioNDPDTO) dto.getSpecificDetail();
			IntestatarioDTO intestatario = dto.getCarrello().iterator().next().getDebitoriDTO().get(0);			
			String anagraficaDebitore = dto.getIntestatario();
			
			if (dto.getTipoDocumento() == EnumTipoDDP.NDP) {
			
				CondizionePagamento c = readCondizionePagamento(condizione.getIdCondizione());
				Pendenza p = c.getPendenza();
				DestinatariPendenza d = p.getDestinatari().iterator().next(); // TODO gestire pendenze cointestate
				
				debitore.setCodiceFiscale(d.getCoDestinatario());
				if (StringUtils.isBlank(d.getAnagraficaDestinatario()))
					debitore.setAnagrafica(d.getDeDestinatario());
				else
					debitore.setAnagrafica(d.getAnagraficaDestinatario());
				if (StringUtils.isBlank(d.getIndirizzoDestinatario()))
					debitore.setIndirizzo("");
				else
					debitore.setIndirizzo(d.getIndirizzoDestinatario() + (d.getNumCivicoDestinatario() != null ? (" " + d.getNumCivicoDestinatario()) : ""));
				if (StringUtils.isBlank(d.getCapDestinatario()))
					debitore.setCap("");
				else
					debitore.setCap(d.getCapDestinatario());
				if (StringUtils.isBlank(d.getLocalitaDestinatario()))
					debitore.setLocalita("");
				else
					debitore.setLocalita(d.getLocalitaDestinatario());
				if (StringUtils.isBlank(d.getProvinciaDestinatario()))
					debitore.setProvincia("");
				else
					debitore.setProvincia(d.getProvinciaDestinatario());

			} else {
			
				debitore.setCodiceFiscale(dettagli.getCfIntestatarioPendenza());
				if ("ANONYMOUS".equals(anagraficaDebitore) || "ANONIMO".equals(anagraficaDebitore)) {
					debitore.setAnagrafica("");
					debitore.setCap("");
					debitore.setIndirizzo("");
					debitore.setLocalita("");
					debitore.setProvincia("");
				} else {
					if (intestatario != null) {
						debitore.setAnagrafica(intestatario.getRagioneSociale());
						try {
							IndirizzoDTO indirizzi = intestatario.getIndirizzo();
							if (StringUtils.isEmpty(indirizzi.getCap()))
								debitore.setCap("");
							else
								debitore.setCap(indirizzi.getCap());
							if (StringUtils.isEmpty(indirizzi.getVia()))
								debitore.setIndirizzo("");
							else
								debitore.setIndirizzo(indirizzi.getVia() + (indirizzi.getNumeroCivico() != null ? (" " + indirizzi.getNumeroCivico()) : ""));
							if (StringUtils.isEmpty(indirizzi.getCitta()))
								debitore.setLocalita("");
							else
								debitore.setLocalita(indirizzi.getCitta());
							if (StringUtils.isEmpty(indirizzi.getCitta()))
								debitore.setProvincia("");
							else
								debitore.setProvincia(indirizzi.getProvincia());
						} catch (Exception ex) {
							LOGGER.error(ex.getMessage());
						}
					} else {
						debitore.setAnagrafica("");
						debitore.setCap("");
						debitore.setIndirizzo("");
						debitore.setLocalita("");
						debitore.setProvincia("");
					}
				}
			}
			info.setBarCode(dettagli.getBarCode());
			info.setStringaBarCode(dettagli.getFormattedBarCode());
			info.setQrCode(dettagli.getXmlQRcode());
			info.setCodiceAvviso(dettagli.calculateNumeroAvviso());
			info.setFormattedID(dettagli.getFormattedID());
			info.setImporto(dto.getImporto());
			info.setIuv(dto.getIuv());
			info.setCausali(causaleFinal);
			info.setOggettoPagamento(dettagli.getDeTrb()); 

			creditore.setGLN(dettagli.getGLN());
			creditore.setIbanCCP(dettagli.getIbanCCP());
			creditore.setUoaCompetente(dettagli.getUoaCompetente()); 
			creditore.setInfoTributo(dettagli.getInfoTributo()); 
			creditore.setIntestazioneCCP(dettagli.getIntestazioneCCP());
			if(StringUtils.isNotBlank(dettagli.getAutorizzStampaBP())) {
				creditore.setAutorizzStampaDDP(dettagli.getAutorizzStampaBP()); 
			}
			
			info.setScadenza(dto.getDtScadenzaDoc());

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			e.printStackTrace();
		}
		
		bollettino.setCreditore(creditore);
		bollettino.setDebitore(debitore);
		bollettino.setDettaglioPagamento(info);
		return bollettino;
	}
	*/
	@Override
	public byte[] creaPdfRicevuta(List<DocumentoDiPagamentoDTO> dtoList, Locale locale) {
		
		
		Map<String, Object> reportParametersMap = new HashMap<String, Object>();
		reportParametersMap.put(JRParameter.REPORT_LOCALE, locale); // DEFAULT ITALIANO	

		String reportName = CommonConstant.RICEVUTA_REPORT_NAME;
		if (dtoList.get(0).isNdp_1_7()) {
			reportName =CommonConstant.RICEVUTA_REPORT_NDP_NAME;
		}
		ReportManager reportGenerator = new ReportManager(
				reportName, 
				ReportExporterFactory.PDF_EXT, 
				reportParametersMap,
				CommonConstant.RECEIPT_FOLDER_NAME);

		byte[] reportFlow = reportGenerator.generateReport(dtoList);

		return reportFlow;
		
	}

	@Override
	public Pagamenti readPagamentoById(Long id) {
		Pagamenti retrieved = null;
		try {
			retrieved = pagamentiDao.loadById(Pagamenti.class, id);
		} catch (Exception e) {
			LOGGER.error("error on  readPagamentoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}

	@Override
	public List<CfgUtenteModalitaPagamento> readUtenteModalitaPagamentoUtilizzate(String codiceFiscale) {
		List<CfgUtenteModalitaPagamento> retrieved = null;
		try {
			retrieved = cfgUtenteModalitaPagamentoDao.getLstCfgUtenteModalitaPagamentoByCodiceFiscale(codiceFiscale);
		} catch (Exception e) {
			LOGGER.error("error on  readUtenteModalitaPagamentoUtilizzate, codiceFiscale = " + codiceFiscale, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	
	@Override
	public AllegatiPendenza getRicevutaAllegatoCondizione(String idPendenza, String idCondizione, Locale locale, EnumCodificaAllegato codifica) {
		
		AllegatiPendenza allegato = null;
		
		try {
			
			List<AllegatiPendenza> allegati = allegatiPendenzaDao.getRicevuteAllegatiCondizione(idPendenza, idCondizione);
		
			allegato = DocFragmentRenderer.buildAttachment(allegati, locale, codifica);
			
		} catch (Exception e) {
			
			LOGGER.error("error on  getRicevutaAllegatoCondizione, idPendenza: " + idPendenza + " - idCondizione: " + idCondizione, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return allegato;
	}
	
	@Override
	public List<CondizionePagamento> getListCondizioniByIdPagamento(String idPagamento, String codTributoEnte, String idEnte) {
		
		List<CondizionePagamento> condizioni = condizioniPagamentoDAO.getListCondizioniByIdPagamento(idPagamento, codTributoEnte, idEnte);
		
		return condizioni;
	}

	
	@Override
	public void ackRicevuta(Long idDocRepo) {
		
		Long version = docRepoDAO.readVersion(idDocRepo);
		
		docRepoDAO.updateAckDownload("1", idDocRepo, version);
		
	}

}
