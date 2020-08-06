package it.tasgroup.iris.business.ejb.flussi;

import static it.tasgroup.services.util.enumeration.EnumUtils.byChiave;
import static it.tasgroup.services.util.enumeration.EnumUtils.findByChiave;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.backoffice.ente.CondizioniRicercaVO;
import it.nch.idp.backoffice.monitoraggioflussi.DistintePagamentoRicercaVO;
import it.nch.idp.posizionedebitoria.AvvisoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaForHomePageVO;
import it.nch.idp.posizionedebitoria.PagamentoPosizioneDebitoriaVO;
import it.nch.idp.posizionedebitoria.PreferenzaEsportazioneVO;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.profilo.Intestatari;
import it.nch.is.fo.stati.pagamenti.CheckRiconciliazioneCompleta;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.nch.is.fo.tributi.CfgTributoEntePlugin;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.pagamenti.creditcard.DisposizioneCartaCreditoVO;
import it.nch.pagamenti.creditcard.DistintaCartaCreditoVO;
import it.nch.pagamenti.nodopagamentispc.DataRichiestaRevocaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiRicevutaPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DatiSingolaRevocaVO;
import it.nch.pagamenti.nodopagamentispc.DatiSingoloPagamentoVO;
import it.nch.pagamenti.nodopagamentispc.DomainNotFoundException;
import it.nch.pagamenti.nodopagamentispc.DuplicatedRptException;
import it.nch.pagamenti.nodopagamentispc.RTNotFoundException;
import it.nch.pagamenti.nodopagamentispc.RptNotFoundException;
import it.nch.pagamenti.nodopagamentispc.SemanticException;
import it.nch.profile.IProfileManager;
import it.nch.utility.GeneratoreIdUnivoci;
import it.nch.utility.enumeration.FlagEnrollmentAvvisatura;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.AddOnViewHelper;
import it.tasgroup.addon.internal.AddOnManagerFactory;
import it.tasgroup.crypt.url.DownloadQuietanzaParametersEncrypter;
import it.tasgroup.crypt.url.DownloadRicevutaParametersEncrypter;
import it.tasgroup.idp.rs.enums.EnumStatoCondizione;
import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumTipoRateazione;
import it.tasgroup.idp.rs.enums.EnumTipoVersamento;
import it.tasgroup.idp.rs.model.creditore.Debitore;
import it.tasgroup.idp.rs.model.creditore.InformazioniPagamentoCondizione;
import it.tasgroup.idp.rs.model.creditore.Pagamento;
import it.tasgroup.idp.rs.model.creditore.StatistichePagamento;
import it.tasgroup.idp.rs.model.creditore.Versante;
import it.tasgroup.idp.rs.model.creditore.Voce;
import it.tasgroup.idp.rs.model.pagamenti.EsitoNDP;
import it.tasgroup.idp.rs.model.pagamenti.RichiestaNotificaPagamento;
import it.tasgroup.iris.business.ejb.client.anagrafica.IntestatariBusinessLocal;
import it.tasgroup.iris.business.ejb.client.esportazioni.ExportBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessLocal;
import it.tasgroup.iris.business.ejb.client.flussi.DistintePagamentoBusinessRemote;
import it.tasgroup.iris.business.ejb.pagamenti.dto.builder.ExportDTOBuilder;
import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentiRepository;
import it.tasgroup.iris.domain.Esportazioni;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.Prenotazioni;
import it.tasgroup.iris.domain.RevochePagamento;
import it.tasgroup.iris.domain.demo.VocePagamento;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.domain.helper.PaymentConditionStatusCalculator;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.ddp.DocumentoRepositoryDTO;
import it.tasgroup.iris.dto.exception.BusinessConstraintException;
import it.tasgroup.iris.dto.exception.UnavailableDDPException;
import it.tasgroup.iris.dto.flussi.DistintePagamentoDTOLight;
import it.tasgroup.iris.dto.flussi.DistintePagamentoRicercaDTO;
import it.tasgroup.iris.dto.flussi.DistinteRiaccreditoDTOLight;
import it.tasgroup.iris.dto.flussi.ExportCSV_CST_DTO;
import it.tasgroup.iris.dto.flussi.ExportCSV_STD_DTO;
import it.tasgroup.iris.dto.flussi.RevocaPagamentoDTO;
import it.tasgroup.iris.dto.rest.filters.StatistichePagamentoFilter;
import it.tasgroup.iris.gde.GiornaleEventiDocumentiNDP;
import it.tasgroup.iris.gev.CfgEventi;
import it.tasgroup.iris.gev.Eventi;
import it.tasgroup.iris.persistence.dao.interfaces.CfgEventiDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.DestinatariPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.DocumentiRepositoryDAO;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.EventiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GiornaleEventiDocumentiNDPDao;
import it.tasgroup.iris.persistence.dao.interfaces.IntestatariDAO;
import it.tasgroup.iris.persistence.dao.interfaces.PagamentiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.PrenotazioniDao;
import it.tasgroup.iris.persistence.dao.interfaces.RevochePagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoDao;
import it.tasgroup.iris.persistence.dao.interfaces.StoricoGestioneFlussiDao;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.iris.shared.util.shoppingcart.SessionShoppingCartItemDTO;
import it.tasgroup.services.util.enumeration.EnumDynaReportFormat;
import it.tasgroup.services.util.enumeration.EnumExportSTDFormat;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;
import it.tasgroup.services.util.enumeration.EnumStatoDistintePagamento;
import it.tasgroup.services.util.enumeration.EnumStatoEventi;
import it.tasgroup.services.util.enumeration.EnumStatoExport;
import it.tasgroup.services.util.enumeration.EnumTipoAllegato;
import it.tasgroup.services.util.enumeration.EnumTipoEventi;
import it.tasgroup.services.util.enumeration.EnumTipoExport;
import it.tasgroup.services.util.enumeration.EnumTypeExportMassivo;
import it.tasgroup.services.util.enumeration.EnumUtils;
import it.tasgroup.services.util.idgenerator.IDGenerator;

@Stateless(name = "DistintePagamentoBusiness")
public class DistintePagamentoBusinessBean implements DistintePagamentoBusinessLocal, DistintePagamentoBusinessRemote {

    private static final Logger LOGGER = LogManager.getLogger(DistintePagamentoBusinessBean.class);

	private static final ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("nodopagamenti.properties");

	private static final ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");

	private static final ConfigurationPropertyLoader gwPropsLoader = ConfigurationPropertyLoader.getInstance("gateway-ws-client.properties");
	

    @EJB(name = "EventiDaoService")
    private EventiDao eventiDAO;

    @EJB(name = "CfgEventiDaoService")
    private CfgEventiDao cfgEventiDAO;

    @EJB(name = "GestioneFlussiDaoImpl")
    private GestioneFlussiDao gestioneFlussiDAO;

    @EJB(name = "PrenotazioniDaoImpl")
    private PrenotazioniDao prenotazioniDAO;

    @EJB(name = "DocumentiRepositoryDAOImpl")
    private DocumentiRepositoryDAO documentiRepositoryDAO;

    @EJB(name = "GestioneFlussiDaoService")
    private GestioneFlussiDao distintaDao;
    
    @EJB(name = "StoricoGestioneFlussiDaoService")
    private StoricoGestioneFlussiDao storicoDistintaDao;

    @EJB
    private IntestatariBusinessLocal intestatariBusinessBean;

    @EJB(name = "PendenzaDaoService")
    private PendenzaDao pendenzaDao;

    @EJB(name = "CondizioniPagamentoDaoImpl")
    private CondizioniPagamentoDao condizioniPagamentoDao;

    @EJB(name = "PagamentiDaoService")
    protected PagamentiDao pagamentiDao;

	
	@EJB(name = "StoricoDaoService")
	protected StoricoDao pagamentiDaoStorico;

    @EJB(name = "ExportBusiness")
    private ExportBusinessLocal exportBusinessBean;

    @EJB(name = "DestinatariPendenzaDaoService")
    private DestinatariPendenzaDao destinatariPendenzaDao;

    @EJB(name = "CfgGatewayPagamentoDaoService")
	private CfgGatewayPagamentoDao cfgGatewayPagamentoDao;
    
    @EJB(name="GiornaleEventiDocumentiNDPDaoService")
    private GiornaleEventiDocumentiNDPDao gdeDao;
    
    @EJB(name = "RevochePagamentoDaoService")
    RevochePagamentoDao revocaPagamento;
 
    @EJB(name = "IntestatariDAOService")
    private IntestatariDAO intestatariDAO;

	@EJB(name = "EntiDaoImpl")
	EntiDao entiDao;

	protected static String ZIP_NAME_PREFIX = "export-quietanze-";
	protected static String OP_INSERT_NAME = "esportaQuietanzeGestioneFlussiFull";
	protected static String OP_UPDATE_NAME = "esportaGestioneFlussiFull";

    @Override
    public List<GestioneFlussi> getGestioneFlussiAll(ContainerDTO inputDTO) {

        return (List<GestioneFlussi>) gestioneFlussiDAO.getGestioneFlussiAll(inputDTO);

    }

    @Override
    public CfgGatewayPagamento getCfgGatewayPagamentoBySystemIdAndApplicationId(String systemId, String applicationId) {
    	return cfgGatewayPagamentoDao.getCfgBySystemIdAndApplicationId(systemId, applicationId);
    }


    @Override
    public List<GestioneFlussi> getDistintaByCodTransazione(String codTransazione) {

        return distintaDao.getDistintaByCodTrasazione(codTransazione);
    }

    @Override
    public List<GestioneFlussi> getDistinteNDP(ContainerDTO containerDTO) {

        return distintaDao.getDistinteNDP(containerDTO);
    }

    @Override
    public List<GestioneFlussi> getDistintaPagamento(ContainerDTO dtoIn) {

        return distintaDao.getDistintaPagamento(dtoIn);
    }

    @Override
    public List<GestioneFlussi> getDistinteByFilterParameters(ContainerDTO containerDTO) {

        return distintaDao.getDistinteByFilterParameters(containerDTO);
    }

    @Override
    public GestioneFlussi getDistintaById(Long idDistinta) {

        return distintaDao.getDistintaById(idDistinta);
    }

    @Override
    public GestioneFlussi getDistintaByIdStorico(Long idDistinta) {
        return storicoDistintaDao.getDistintaById(idDistinta);
    }

    @Override
    public GestioneFlussi getDistintaPagamentoForRicevuta(String codPagamento, String idFlusso, String codPagante) throws BusinessConstraintException {

    	if (codPagamento.length()==16)
    			IDGenerator.checkCRC(codPagamento);

    	GestioneFlussi distinta =  distintaDao.getDistintePagamento(codPagamento, idFlusso, codPagante);

    	// Controllo sullo stato della distinta!!!
		// Impossibile scaricare la ricevuta di un pagamento non eseguito
    	if (!EnumStatoDistintePagamento.ESEGUITO.getChiave().equals(distinta.getStato()))
    		throw new UnavailableDDPException();


    	//
    	// completo la distinta aggiungendo la lista dei debitori
    	//
    	for (Pagamenti pagamento : distinta.getPagamenti()) {

    		CondizionePagamento condPagamento = pagamento.getCondPagamento();
    		List<String> debitori = condizioniPagamentoDao.getDebitoriPendenza(condPagamento.getPendenza().getIdPendenza());
     		String cfAnonimo = ConfigurationPropertyLoader.getInstance( "iris-fe.properties").getProperty("iris.codice.fiscale.anonymous");
    		List<String> listaDebitoriView = new ArrayList<String>();
    		for (String debitore : debitori) {
    			if (debitore.equals(cfAnonimo)) {
    				listaDebitoriView.add("ANONIMO");
    			} else {
    				listaDebitoriView.add(debitore);
    			}		
    		}
    		condPagamento.setDebitori(listaDebitoriView);
    		
	   	}

    	return distinta;
    }



    @Override
    public List<Pagamenti> getPagamentiFull(ContainerDTO inputDTO) {

        DistintePagamentoRicercaDTO dto = copia(inputDTO);

        inputDTO.addInputDTO(dto);

        return pagamentiDao.getPagamentiFull(inputDTO);
    }

    @Override
    public List<Pagamenti> getPagamenti(ContainerDTO inputDTO) {

        DistintePagamentoRicercaDTO dto = copia(inputDTO);
        inputDTO.addInputDTO(dto);

        return (List<Pagamenti>) pagamentiDao.getPagamenti(inputDTO);
    }
    
    @Override
    public List<Pagamenti> getPagamentibyCodFisc(ContainerDTO inputDTO) {

        DistintePagamentoRicercaDTO dto = copia(inputDTO);
        inputDTO.addInputDTO(dto);

        return (List<Pagamenti>) pagamentiDao.getPagamentiByCodFiscale(inputDTO);
    }
    
    @Override
    public List<RevochePagamento> getRevocaPagamentoByIdPagamento(Long idPagamento) {
        return (List<RevochePagamento>) revocaPagamento.getRevocaPagamentoByIdPagamento(idPagamento);
    }
    
    @Override
    public RevochePagamento updateRevocaPagamento(RevocaPagamentoDTO revocaDTO ) {
    	try {
    		RevochePagamento revoca = revocaPagamento.getById(RevochePagamento.class, revocaDTO.getId());
	    	revoca.setCausaleEsitoRevoca(revocaDTO.getCausaleEsitoRevoca());
	    	revoca.setDatiAggiuntiviEsitoRevoca(revocaDTO.getDatiAggiuntiviEsitoRevoca());
	    	revoca.setStatoRevoca(revocaDTO.getStatoRevoca());
	    	revoca.setOpAggiornamento("PAYTAS");
	    	revoca.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
	    	// Automatic update
	    	//**** aggiorno  anche lo stato del pagamento e della distinta (nel caso di revoca accettata)
	    	if ("A".equals(revocaDTO.getStatoRevoca())){
	    		revoca.getPagamento().setStPagamento("ST");
	    		revoca.getPagamento().setOpAggiornamento("PAYTAS");
	    		revoca.getPagamento().setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
	    		GestioneFlussi dist =revoca.getPagamento().getFlussoDistinta();			
	    		boolean parzEseguito = false;
	    		for (Pagamenti p: dist.getPagamenti() ) {
	    			if (p.getStPagamento().equals("ES") || p.getStPagamento().equals("EF")){
	    				parzEseguito = true;
	    			}
	    		}
	    		//
	    		if (parzEseguito){
	    			LOGGER.warn(" updateRevocaPagamento, idRevoca = " + revocaDTO.getId()+ " parzialmente eseguito ");
	    		} else {
	    			dist.setStato("STORNATO");
	    			dist.setOpAggiornamento("PAYTAS");
	    			dist.setTsUpdate(new Timestamp(System.currentTimeMillis()));
	    		}
	    	}
	    	
	    	return revoca;
	    } catch (Exception e) {
			LOGGER.error("error on  updateRevocaPagamento, idRevoca = " + revocaDTO.getId()
					+ ", CausaleEsitoRevoca = " + revocaDTO.getCausaleEsitoRevoca()
					+ ", DatiAggiuntiviEsitoRevoca = " + revocaDTO.getDatiAggiuntiviEsitoRevoca()
					+ ", StatoRevoca = " + revocaDTO.getStatoRevoca()
					, e);
			throw new DAORuntimeException(e);
		}
    }

    protected DistintePagamentoRicercaDTO copia(ContainerDTO inputDTO) {

        DistintePagamentoRicercaVO vo = (DistintePagamentoRicercaVO) inputDTO.getInputDTOList().get(0);

        DistintePagamentoRicercaDTO dto = new DistintePagamentoRicercaDTO();
        dto.setCfUtenteCreatore(vo.getCfUtenteCreatore());
        dto.setEmailVersante(vo.getEmailVersante());
        dto.setDataCreazioneAGG(vo.getDataCreazioneAGG());
        dto.setDataCreazioneDaGG(vo.getDataCreazioneDaGG());
        dto.setDataCreazioneAMM(vo.getDataCreazioneAMM());
        dto.setDataCreazioneDaMM(vo.getDataCreazioneDaMM());
        dto.setDataCreazioneAYY(vo.getDataCreazioneAYY());
        dto.setDataCreazioneDaYY(vo.getDataCreazioneDaYY());
        dto.setDataScadenzaGG(vo.getDataScadenzaGG());
        dto.setDataScadenzaMM(vo.getDataScadenzaMM());
        dto.setDataScadenzaYY(vo.getDataScadenzaYY());
        dto.setFlagIncasso(vo.getFlagIncasso());
        dto.setRichiestaRevoca(vo.getRichiestaRevoca());
        dto.setOpInserimento(vo.getOpInserimento());
        dto.setIdEnte(vo.getIdEnte());
        dto.setIdIntestatarioEnte(vo.getIdIntestatarioEnte());
        dto.setIdPagamento(vo.getIdPagamento());
        dto.setCodPagamento(vo.getCodPagamento());
        dto.setIdPendenza(vo.getIdPendenza());
        dto.setImportoPagamentoA(vo.getImportoPagamentoA());
        dto.setImportoPagamentoDa(vo.getImportoPagamentoDa());
        dto.setModPagamento(vo.getModPagamento());
        dto.setModPagamentoAF(vo.getModPagamentoAF());
        dto.setModPagamentoPsp(vo.getModPagamentoPsp());
        dto.setTipoVersamento(vo.getTipoVersamento());
        dto.setModAnonima(vo.getModAnonima());
        dto.setTipoSpontaneo(vo.getTipoSpontaneo());
        dto.setStatoPagamento(vo.getStatoPagamento());
        dto.setCodFiscDebitore(vo.getCodFiscDebitore());
        dto.setIdTributo(vo.getIdTributo());
        dto.setIdTributoLista(vo.getIdTributoLista());
        dto.setWithQuietanza(vo.getWithQuietanza());
        dto.setWithRiaccredito(vo.getWithRiaccredito());
        dto.setAnnoRif(vo.getAnnoRif());
        dto.setIuv(vo.getIuv());
        dto.setIdFlusso(vo.getIdFlusso());
        dto.setTrn(vo.getTrn());
        dto.setIdPSP(vo.getIdPSP());
        dto.setDataRegolamentoAGG(vo.getDataRegolamentoAGG());
        dto.setDataRegolamentoDaGG(vo.getDataRegolamentoDaGG());
        dto.setDataRegolamentoAMM(vo.getDataRegolamentoAMM());
        dto.setDataRegolamentoDaMM(vo.getDataRegolamentoDaMM());
        dto.setDataRegolamentoAYY(vo.getDataRegolamentoAYY());
        dto.setDataRegolamentoDaYY(vo.getDataRegolamentoDaYY());
        dto.setCircuito(vo.getCircuito());
        if (vo.getModVersamento() != null && !vo.getModVersamento().isEmpty())
        	dto.setModelloVersamento(new ArrayList<String>(Arrays.asList(vo.getModVersamento().split(","))));
        dto.setIstitutoAttestante(vo.getIstitutoAttestante());

        return dto;
    }

    @Override
    @Asynchronous
    public void esportaPagamentiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale){

			List<Pagamenti> listaPagamenti = getPagamentiFull(inputDTO);

			List<DistintePagamentoDTOLight> outputDTO = ExportDTOBuilder.populateListPagamentoDTOLight(listaPagamenti, mapEnti, mapTipiTributo);

			PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(2);

			exportBusinessBean.esporta(prenotazione, vo.getMapListaCampi(), cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.PAGAMENTI.getChiave(), EnumDynaReportFormat.CSV_CUSTOM, null, DistintePagamentoDTOLight.class, locale);



    }
	
	
	@Override
	@Asynchronous
	public void esportaCondizioniCreditoreFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale){
		
		List<CondizionePagamento> listaCondizioni = getCondizioniCreditoreFull(inputDTO);
		
		List<DistintePagamentoDTOLight> outputDTO = ExportDTOBuilder.populateListPagamentoDTOLightFromCondizione(listaCondizioni, mapEnti, mapTipiTributo);
		
		PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(2);
		
		exportBusinessBean.esporta(prenotazione, vo.getMapListaCampi(), cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.PAGAMENTI.getChiave(), EnumDynaReportFormat.CSV_CUSTOM, null, DistintePagamentoDTOLight.class, locale);
		
		
	}
	
	private List<CondizionePagamento> getCondizioniCreditoreFull(ContainerDTO inputDTO) {
//		DistintePagamentoRicercaDTO dto = copia(inputDTO);
		CondizioniRicercaVO dto = (CondizioniRicercaVO) inputDTO.getInputDTOList().get(0);
		
		inputDTO.addInputDTO(dto);
		
		return pagamentiDao.getCondizioniCreditoreFull(inputDTO);
	}
	
	@Override
	public List<Pagamenti> getPagamentiFullRicevuteTelematiche(ContainerDTO inputDTO) {
    	return (List<Pagamenti>) pagamentiDao.getPagamentiFullRT(inputDTO);
    }
	
	
	@Override
	@Asynchronous
	public void esportaCondizioniCSVStandard(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, EnumExportSTDFormat stdFormat, Locale locale){
		
		List<CondizionePagamento> listaCondizioni = getCondizioniCreditoreFull(inputDTO);
		
		List<ExportCSV_STD_DTO> outputDTO = ExportDTOBuilder.fillEsportazioneCondizioniCSVStandardDTO(stdFormat, listaCondizioni, mapEnti, mapTipiTributo, locale);
		
		PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(2);
		
		exportBusinessBean.esporta(prenotazione, vo.getMapListaCampi(), cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.PAGAMENTI.getChiave(), EnumDynaReportFormat.CSV_STANDARD,vo.getExportStandardType(), ExportCSV_STD_DTO.class, locale);
		
	}
	
	
	@Override
    @Asynchronous
    public void esportaPagamentiCSVStandard(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, EnumExportSTDFormat stdFormat, Locale locale){

			List<Pagamenti> listaPagamenti = getPagamentiFull(inputDTO);

			List<ExportCSV_STD_DTO> outputDTO = ExportDTOBuilder.fillEsportazioneCSVStandardDTO(stdFormat, listaPagamenti, mapEnti, mapTipiTributo, locale);

			PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(2);

			exportBusinessBean.esporta(prenotazione, vo.getMapListaCampi(), cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.PAGAMENTI.getChiave(), EnumDynaReportFormat.CSV_STANDARD,vo.getExportStandardType(), ExportCSV_STD_DTO.class, locale);

    }

    @Override
    @Asynchronous
    public void esportaAvvisiCSVStandard(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale){

    	PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(0);

		List<AvvisoPosizioneDebitoriaVO> listaAvvisi = (List<AvvisoPosizioneDebitoriaVO>) inputDTO.getInputDTOList().get(2);

		List<ExportCSV_STD_DTO> outputDTO = ExportDTOBuilder.fillEsportazioneAvvisiCSVStandardDTO(vo.getExportStandardType(), listaAvvisi, mapEnti, mapTipiTributo, locale);



		exportBusinessBean.esporta(prenotazione, mapListaCampi, cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.POSIZIONI.getChiave(), EnumDynaReportFormat.CSV_STANDARD, vo.getExportStandardType(), ExportCSV_STD_DTO.class, locale);

    }

    @Override
    @Asynchronous
    public void esportaAvvisiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, Map<String, String> mapEnti, Map<String, String> mapTipiTributo, String cfOperatore, Locale locale) {
		List<AvvisoPosizioneDebitoriaVO> listaAvvisi = (List<AvvisoPosizioneDebitoriaVO>) inputDTO.getInputDTOList().get(2);
		PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(0);
		List<ExportCSV_CST_DTO> outputDTO = ExportDTOBuilder.fillEsportazioneAvvisiCSVCustomDTO(vo.getExportStandardType(), listaAvvisi, mapEnti, mapTipiTributo, locale);
		exportBusinessBean.esporta(prenotazione, mapListaCampi, cfOperatore,  vo.getRigaIntestazione(), vo.getMySelectSeparatore(), outputDTO, vo.getMyvaloreselezionato(),EnumTipoExport.POSIZIONI.getChiave(), EnumDynaReportFormat.CSV_CUSTOM, null, ExportCSV_CST_DTO.class, locale);
    }

    @Override
    @Asynchronous
    public void esportaRiaccreditiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, Map<String, String> mapListaCampi, String cfOperatore, Locale locale) {


    		List<DistinteRiaccreditoDTOLight> listaRiaccrediti = (List<DistinteRiaccreditoDTOLight>) inputDTO.getInputDTOList().get(2);

    		PreferenzaEsportazioneVO vo = (PreferenzaEsportazioneVO) inputDTO.getInputDTOList().get(0);
    		exportBusinessBean.esporta(
    				prenotazione,
    				mapListaCampi,
    				cfOperatore,
    				vo.getRigaIntestazione(),
    				vo.getMySelectSeparatore(),
    				listaRiaccrediti,
    				vo.getMyvaloreselezionato(),
    				EnumTipoExport.RIACCREDITI.getChiave(),
    				EnumDynaReportFormat.CSV_CUSTOM,null,
    				DistinteRiaccreditoDTOLight.class, locale);

    }

    public static void zipBytes(ZipOutputStream zos, String filename, byte[] input) throws IOException {
        ZipEntry entry = new ZipEntry(filename);
        entry.setSize(input.length);
        zos.putNextEntry(entry);
        zos.write(input);
        zos.closeEntry();
        // zos.close();

    }

    public static byte[] zipBytes(String filename, byte[] input) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        zipBytes(zos, filename, input);
        // ZipEntry entry = new ZipEntry(filename);
        // entry.setSize(input.length);
        // zos.putNextEntry(entry);
        // zos.write(input);
        // zos.closeEntry();
        zos.close();
        return baos.toByteArray();
    }
    

    @Override
    public void esportaQuietanzeGestioneFlussiFull(ContainerDTO inputDTO, Prenotazioni prenotazione, String cfOperatore,EnumTypeExportMassivo typo ) throws Exception {
    	List<DocumentoRepositoryDTO> lDoc = null;
    	if (typo.equals(EnumTypeExportMassivo.RT)) { 
    		lDoc=(List<DocumentoRepositoryDTO>) inputDTO.getInputDTOList().get(3);
    	}
    	else {
    		lDoc=(List<DocumentoRepositoryDTO>) inputDTO.getInputDTOList().get(4);
    	}
        String stato;
        try {
        	Esportazioni exp = new Esportazioni();
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		ZipOutputStream zos = new ZipOutputStream(baos);
    		for (DocumentoRepositoryDTO doc : lDoc) {
    			LOGGER.debug("id pagamento: " + doc.getId() + " - id doc repository: " + doc.getId());
    			zipBytes(zos, doc.getFileName() + "_" + doc.getId() + "." + doc.getFileExtension(), doc.getContent());
    		}
    		LOGGER.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%" );
    		LOGGER.info("n. documenti: " + lDoc.size());
    		zos.close();
    		Timestamp now = new Timestamp(System.currentTimeMillis());
    		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
    		StringBuilder filename = new StringBuilder(getZipFileName());
    		filename.append(dateFormatter.format(now));
            if(cfOperatore != null){
                filename.append("-")
                        .append(cfOperatore);

            }
            stato = EnumStatoExport.DISPONIBILE.getDescrizione();
            exp.setDati(baos.toByteArray());
            exp.setCompressione("S");
            exp.setFormato("zip");
            exp.setLenDati(new Long(baos.size()));
            exp.setNomeFile(filename.toString());
            exp.setOpInserimento("esportaQuietanzeGestioneFlussiFull");
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
        prenotazione.setTipoEsportazione(prenotazione.getTipoEsportazione());
        prenotazione.setOpAggiornamento("esportaGestioneFlussiFull");
        prenotazione.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
        prenotazioniDAO.updatePrenotazione(prenotazione);
        System.out.println("esportaGestioneFlussiFull - salvata la prenotazione");
    }

    @Override
    public Long contaPagamentiQuietanzati(ContainerDTO inputDTO) {

        DistintePagamentoRicercaDTO dto = copia(inputDTO);

        inputDTO.addInputDTO(dto);

        return pagamentiDao.contaPagamentiQuietanzati(inputDTO);
    }

    @Override
    public Long contaRicevuteTelematiche(ContainerDTO inputDTO) {
    	return pagamentiDao.contaRicevuteTelematiche(inputDTO);
    }
    
    @Override
    public Esportazioni getEsportazioni(Long idPren) {
        Esportazioni ret = null;
        try {
            Prenotazioni pren = prenotazioniDAO.getById(Prenotazioni.class, idPren);
            if (!pren.getEsportazioni().isEmpty())
                ret = pren.getEsportazioni().iterator().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     *
     * @param 
     * @param 
     * @return
     */
    @Override
    public GestioneFlussi aggiornaEsito(long idFlusso, StatiPagamentiIris stato, String tranId, String deOperazione, String tipoIdentifAttestante, String identifAttestante, String descrizAttestante ) {

        GestioneFlussi updatedDistinta = gestioneFlussiDAO.aggiornamentoStatoFlusso(idFlusso, stato, tranId, deOperazione,tipoIdentifAttestante, identifAttestante, descrizAttestante );

        if (stato.equals(StatiPagamentiIris.ESEGUITO)) {
            for (Pagamenti pagamento : updatedDistinta.getPagamenti())
                pendenzaDao.updateStRiga(pagamento.getCondPagamento());
        }

        return updatedDistinta;
    }


    @Override
	public GestioneFlussi aggiornaEsitoDaRT(DatiRicevutaPagamentoVO datiRicevutaPagamento) throws DomainNotFoundException, RptNotFoundException, DuplicatedRptException {

		LOGGER.debug("DistintePagamentoBusinessBean::aggiornamentoStatoFlussoDaRT - BEGIN");
		try {
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			GestioneFlussi distinta = gestioneFlussiDAO.getDistintaPagamentoByIdfiscCredIUVContpagamento(datiRicevutaPagamento.getIdentificativoFiscaleCreditore(), datiRicevutaPagamento.getIdentificativoUnivocoVersamento(), datiRicevutaPagamento.getCodiceContestoPagamento());

			//
			// non trovo la distinta perche sono arrivati dei codici identificativi che non matchano
			// TODO:MINO restituire un fault con codice
			//
			if (distinta == null) {




				LOGGER.error("[DistintePagamentoBusinessBean::aggiornamentoStatoFlussoDaRT] - ATTENZIONE!!! non trovo distinte relative a: "
						+ " codPagamento= " + datiRicevutaPagamento.getIdentificativoUnivocoVersamento()
						+ " - codTransazione= " + datiRicevutaPagamento.getRiferimentoMessaggioRichiesta()
						+ " - dataCreazione= " + datiRicevutaPagamento.getRiferimentoDataRichiesta());

				// verifico se idFiscaleCreditore ï¿½ presente nella configurazione della applicazione
				if (!intestatariBusinessBean.checkIdFiscaleEnte(datiRicevutaPagamento.getIdentificativoFiscaleCreditore())) {
					LOGGER.error("[DistintePagamentoBusinessBean::aggiornamentoStatoFlussoDaRT] - ATTENZIONE!!! non trovo distinte relative a: idFiscaleCreditore = "+ datiRicevutaPagamento.getIdentificativoFiscaleCreditore());
					throw new DomainNotFoundException();
				} else {
					throw new RptNotFoundException();
				}

			}

			//TODO effettuo il check dello stato attuale della distinta... se ï¿½ in uno stato finale RT duplicata!!!!
			if (false)
				throw new DuplicatedRptException();
			//
			// aggiornamento distinta
			//

			distinta.setStato(datiRicevutaPagamento.getStatoPagamentoIris().getFludMapping());
			distinta.setTsUpdate(now);
			distinta.setOpAggiornamento("RT_NODOPAGAMENTI");
			distinta.setCodTransazionePSP(datiRicevutaPagamento.getCodiceContestoPagamento());

            distinta.setTipoIdentificativoAttestante(datiRicevutaPagamento.getTipoIdentificativoPSP());
			distinta.setIdentificativoAttestante(datiRicevutaPagamento.getCodiceIdentificativoUnivocoPSP());
			distinta.setDescrizioneAttestante(datiRicevutaPagamento.getDescrizionePSP());

			//
			// aggiornamaento pagamenti
			//
			LOGGER.debug("### datiRicevutaPagamento.getDatiPagamento().size(): " + datiRicevutaPagamento.getDatiPagamento().size());
			LOGGER.debug("### datiRicevutaPagamento.getCodiceEsitoPagamento(): " + datiRicevutaPagamento.getCodiceEsitoPagamento());

			updatePagamenti(distinta, datiRicevutaPagamento, now);

			if (LOGGER.isDebugEnabled()) {
				for (Pagamenti pagamento : distinta.getPagamenti()) {
					LOGGER.debug("### aggiornamento pagamento id: " + pagamento.getId() + " allo stato: " + pagamento.getStPagamento());
				}
			}

			//
			// aggiornamento pagamenti on_line
			//
			PagamentiOnline pol = new PagamentiOnline();
			pol = new PagamentiOnline();
			pol.setCodAutorizzazione(distinta.getIuv());
			pol.setDeOperazione("NDP_INVIO_RT: ricevuta RT dal Nodo - update stato distinta a: " + distinta.getStato());
			pol.setFlussoDistintaOnline(distinta);
			pol.setIdOperazione(datiRicevutaPagamento.getCodiceContestoPagamento());
			pol.setNumOperazione("2");
			pol.setOpAggiornamento(null);
			pol.setOpInserimento("RT_NODOPAGAMENTI");
			pol.setSessionIdSistema("SYS");
			pol.setSessionIdTerminale("TERM");
			pol.setSessionIdTimbro("TIMBRO");
			//pol.setSessionIdToken("TOKEN");
			pol.setSessionIdToken(distinta.getCodTransazionePSP());
			pol.setApplicationId(distinta.getCfgGatewayPagamento().getApplicationId());
			pol.setSystemId(distinta.getCfgGatewayPagamento().getSystemId());
			pol.setTiOperazione(EnumOperazioniPagamento.NOTIFICA.getChiave());
			pol.setTsAggiornamento(null);
			pol.setTsInserimento(now);
			pol.setTsOperazione(now);

			distinta.getPagamentiOnline().add(pol);

						
			//----- INIZIO AGGIORNAMENTO ID PSP WISP 2.0 --- 
			String versioneInterfacciaWISP = gwPropsLoader.getProperty("versioneInterfacciaWISP");
			if ("2.0".equals(versioneInterfacciaWISP) && false) {
               CfgGatewayPagamento cfgGatewPag =  cfgGatewayPagamentoDao.getCfgGatewayPagamentoNdpByDatiRT(datiRicevutaPagamento.getTipoIdentificativoPSP(), datiRicevutaPagamento.getCodiceIdentificativoUnivocoPSP());
			   if (cfgGatewPag!=null){
				   distinta.setCfgGatewayPagamento(cfgGatewPag);
			   }
			}
			//----- FINE AGGIORNAMENTO ID PSP WISP 2.0 ----
			GestioneFlussi updated = gestioneFlussiDAO.update(distinta);

			//
			// inserisco evento di generazione mail solo se il documento e disponibile
			//
			boolean avvisaturaEsecuzionePagamento = "ESEGUITO".equals(distinta.getStato());

			if (avvisaturaEsecuzionePagamento && !distinta.getPagamenti().isEmpty()) {
				Pagamenti p=distinta.getPagamenti().iterator().next();
				avvisaturaEsecuzionePagamento=p.isAssociatedDocumentAvailable();
			}
			//
			// inserimento evento notifica da RT
			//

			//
			// check invio una sola mail per gruppo
			//
			boolean avvisaturaEsecuzionePagamentoRT = avvisaturaEsecuzionePagamento;
			if (distinta.getIdGruppo()!=null && !"".equals(distinta.getIdGruppo()) && avvisaturaEsecuzionePagamento) {
				avvisaturaEsecuzionePagamentoRT = false;
				LOGGER.info("### la gestione della mail nel caso  di un carrello multibenef. viene gestita da un Timer su backend... non genero evento mail ###");
//				List<GestioneFlussi> gList =gestioneFlussiDAO.getDistinteByIdGruppo(distinta.getIdGruppo());
//				for (GestioneFlussi f: gList) {
//					if (!"ESEGUITO".equals(f.getStato())) {
//						mailDaInviare=false;
//						LOGGER.info("### generazione evento invio mail: non genero la mail in quanto non e la ricezione dell ultima RT di un carrello multibenef. ###");
//					}
//				}
//				if (mailDaInviare) {
//					LOGGER.info("### generazione evento invio mail: genero la mail in quanto la ricezione dell ultima RT di un carrello multibenef. ###");
//				}
			}

			if (avvisaturaEsecuzionePagamentoRT) {
			   Eventi evento = new Eventi();
			   evento.setCodiceEvento(EnumTipoEventi.AVVISO_RICEZIONE_RT_NDP.getEventoCode());
			   evento.setDatiEvento("" + distinta.getId());
			   evento.setNumeroTentativi(0);
			   evento.setStato(EnumStatoEventi.INATTESA.getChiave());

			   eventiDAO.create(evento);
			   LOGGER.debug("### generazione evento invio mail ###");
			} else {
			   LOGGER.debug("### NON VIENE generato evento invio mail ###");
			}
			
			ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
			boolean avvisaturaOT = props.getBooleanProperty("avvisatura.pagamento.eseguito.opentoscana"); // Open Toscana
			if (avvisaturaEsecuzionePagamento && avvisaturaOT) {
				CfgEventi cfgEvento = cfgEventiDAO.getCfgEventiDao(EnumTipoEventi.AVVISO_DI_PAGAMENTO_ESEGUITO.getEventoCode());
				if (cfgEvento != null && "Y".equalsIgnoreCase(cfgEvento.getAttivo())) {
			    	for (Pagamenti pagamento : distinta.getPagamenti()) {
			    		CondizionePagamento condizionePagamento = pagamento.getCondPagamento();
			    		List<String> listaCfDebitori = condizioniPagamentoDao.getDebitoriPendenza(condizionePagamento.getPendenza().getIdPendenza());
			    		for (String cfDebitore : listaCfDebitori) {
			    			Intestatari intestatario = intestatariDAO.getIntestatarioByLapl(cfDebitore, false);
			    			if (intestatario != null && FlagEnrollmentAvvisatura.Y.getDescription().equals(intestatario.getFlagEnrollmentAvvisatura())) {
			    				Enti ente = entiDao.getById(Enti.class, pagamento.getIdEnte());
			    				Eventi evento = new Eventi();
			    				evento.setCodiceEvento(EnumTipoEventi.AVVISO_DI_PAGAMENTO_ESEGUITO.getEventoCode());
			    				evento.setDatiEvento(ente.getCodiceEnte() + "|" + pagamento.getCondPagamento().getIdPagamento() + "|" + cfDebitore);
			    				evento.setNumeroTentativi(0);
			    				evento.setStato(EnumStatoEventi.INATTESA.getChiave());
			    				evento.setOpInserimento("IRIS"); // TODO costante
			    				evento.setTsInserimento(new Timestamp(new Date().getTime()));
			    				eventiDAO.create(evento);
			    				LOGGER.debug("### generazione evento " + EnumTipoEventi.AVVISO_DI_PAGAMENTO_ESEGUITO.getEventoCode() + " (OT) per idCondizione " + pagamento.getCondPagamento().getIdCondizione() + "###");
			    			}
			    		}
					}
				} else {
					LOGGER.debug("### NON VIENE generato evento " + EnumTipoEventi.AVVISO_DI_PAGAMENTO_ESEGUITO.getEventoCode() + ": non attivo (OT) ###");
				}
			} else {
				LOGGER.debug("### NON VIENE generato evento " + EnumTipoEventi.AVVISO_DI_PAGAMENTO_ESEGUITO.getEventoCode() + ": non configurato (OT) ###");
			}
			
			return updated;

		} catch (Exception e) {

			if (e instanceof RptNotFoundException)
				throw (RptNotFoundException)e;

			if (e instanceof DomainNotFoundException)
				throw (DomainNotFoundException)e;

			if (e instanceof DuplicatedRptException)
				throw (DuplicatedRptException)e;

			e.printStackTrace();
			LOGGER.error("error on aggiornamentoStatoFlussoDaRT "+ datiRicevutaPagamento, e);
			throw new DAORuntimeException(e);

		}
		//LOGGER.debug("DistintePagamentoBusinessBean::aggiornamentoStatoFlussoDaRT - END");
	}

    
    @Override
	public void manageRichiestaRevoca(DataRichiestaRevocaPagamentoVO richiestaRevoca)
			throws SemanticException, RTNotFoundException {
    	
    	LOGGER.debug("DistintePagamentoBusinessBean::manageRichiestaRevoca - BEGIN");
		try {
			Timestamp now = new Timestamp(new java.util.Date().getTime());

			GestioneFlussi distinta = gestioneFlussiDAO.getDistintaPagamentoByIdfiscCredIUVContpagamento(richiestaRevoca.getIdentificativoDominio(), richiestaRevoca.getIdentificativoUnivocoVersamento(), richiestaRevoca.getCodiceContestoPagamento());

			//
			// non trovo la distinta perche sono arrivati dei codici identificativi che non matchano
			// TODO:MINO restituire un fault con codice
			//
			if (distinta == null) {

				LOGGER.error("[DistintePagamentoBusinessBean::manageRichiestaRevoca] - ATTENZIONE!!! non trovo distinte relative a: "
						+ " identificativoDominio= " + richiestaRevoca.getIdentificativoUnivocoVersamento()
						+ " - identificativoUnivocoVersamento = " + richiestaRevoca.getIdentificativoUnivocoVersamento()
						+ " - codiceContestoPagamento= " + richiestaRevoca.getCodiceContestoPagamento());

				
				throw new RTNotFoundException();			

			}
			if (!distinta.getStato().equals("ESEGUITO") && !distinta.getStato().equals("NON ESEGUITO")){
				LOGGER.error("[DistintePagamentoBusinessBean::manageRichiestaRevoca] - ATTENZIONE!!! la RT specificata e associata a una distinta in stato = " + distinta.getStato()
						+ " identificativoDominio= " + richiestaRevoca.getIdentificativoUnivocoVersamento()
						+ " - identificativoUnivocoVersamento = " + richiestaRevoca.getIdentificativoUnivocoVersamento()
						+ " - codiceContestoPagamento= " + richiestaRevoca.getCodiceContestoPagamento());

				throw new RTNotFoundException();
			}
			if (!richiestaRevoca.getTipoRevoca().equals("1") && !distinta.getStato().equals("ESEGUITO")) {
				LOGGER.error("[DistintePagamentoBusinessBean::manageRichiestaRevoca] - ATTENZIONE!!! La RT da revocare non risulta essere una RT positiva: stato = " + distinta.getStato() 
				+ " identificativoDominio= " + richiestaRevoca.getIdentificativoUnivocoVersamento()
				+ " - identificativoUnivocoVersamento = " + richiestaRevoca.getIdentificativoUnivocoVersamento()
				+ " - codiceContestoPagamento= " + richiestaRevoca.getCodiceContestoPagamento());

		        throw new SemanticException("La RT da revocare non risulta essere una RT positiva");
			}
			// controllo identificativoRiscossionePSP = IUR
			if (distinta.getPagamenti().size() != richiestaRevoca.getDatiRevoca().size()) {
				LOGGER.error("[DistintePagamentoBusinessBean::manageRichiestaRevoca] - ATTENZIONE!!! La RT da revocare e' associata a pagamenti incompatibili con i dati singole revoche"  
				+ " identificativoDominio= " + richiestaRevoca.getIdentificativoUnivocoVersamento()
				+ " - identificativoUnivocoVersamento = " + richiestaRevoca.getIdentificativoUnivocoVersamento()
				+ " - codiceContestoPagamento= " + richiestaRevoca.getCodiceContestoPagamento());
				
				throw new SemanticException("La RT da revocare e' associata a pagamenti incompatibili con i dati singole revoche");
			}
			for (Pagamenti p: distinta.getPagamenti()) {
				 boolean found= false;
				 for (DatiSingolaRevocaVO v: richiestaRevoca.getDatiRevoca()) {
					 if (v.getIdentificativoUnivocoRiscossione().equals(p.getIdRiscossionePSP())) {
						 found= true;
					 }
				 }
				 if (!found){
					 LOGGER.error("[DistintePagamentoBusinessBean::manageRichiestaRevoca] - ATTENZIONE!!! La RT contiene dati riscossore incompatibili con la richiesta revoca"  
								+ " identificativoDominio= " + richiestaRevoca.getIdentificativoUnivocoVersamento()
								+ " - identificativoUnivocoVersamento = " + richiestaRevoca.getIdentificativoUnivocoVersamento()
								+ " - codiceContestoPagamento= " + richiestaRevoca.getCodiceContestoPagamento());
					 
					 throw new SemanticException("La RT contiene dati riscossore incompatibili con la richiesta revoca");
								
				 }
				
			}
			
			Iterator<Pagamenti> iterPag=distinta.getPagamenti().iterator();
			for (DatiSingolaRevocaVO d: richiestaRevoca.getDatiRevoca()) {
			   //inserimento su data base
			   RevochePagamento r = new RevochePagamento();
			
			   r.setStatoRevoca("R");
			   r.setEsitoRevoca("0");
			   r.setTipoRevocaRichiesta(richiestaRevoca.getTipoRevoca());	
			   
			   r.setPagamento(iterPag.next());
			   r.setCausaleRevocaRichiesta(d.getCausaleRevoca());	
			   r.setDatiAggRevocaRichiesta(d.getDatiAggiuntiviRevoca());	
			   r.setIdMessaggioRevoca(richiestaRevoca.getIdentificativoMessaggioRevoca());		
			   r.setTsMessaggioRevoca(richiestaRevoca.getDataOraRichiestaRevoca());	

			   r.setOpInserimento("NDP");
			   r.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			
			   revocaPagamento.create(r);
			}
		} catch (Exception e) {

			if (e instanceof RTNotFoundException)
				throw (RTNotFoundException)e;

			if (e instanceof SemanticException)
				throw (SemanticException)e;

			
			e.printStackTrace();
			LOGGER.error("error on manageRichiestaRevoca "+ richiestaRevoca, e);
			throw new DAORuntimeException(e);

		}
		
	}
	/*
	 * N.B. viene creata una distinta con un unico pagamento di importo pari al totale dei pagamenti
	 */
	private void updatePagamenti(GestioneFlussi distinta, DatiRicevutaPagamentoVO datiRicevutaPagamento, Timestamp tsAggiornamento)
			throws Exception {

		// da Specifiche attuative Nodo 1.6.1 - Pag. 55
		// 0 Pagamento eseguito
		// 1 Pagamento non eseguito
		// 2 Pagamento parzialmente eseguito
		// 3 Decorrenza termini
		// 4 Decorrenza termini parziale

		Set<Pagamenti> pagamenti = distinta.getPagamenti();
		for (Pagamenti pagamento : pagamenti) {

			if (datiRicevutaPagamento.getDatiPagamento().isEmpty()) {
				pagamento.setStPagamento("1".equals(datiRicevutaPagamento.getCodiceEsitoPagamento()) ? StatiPagamentiIris.NON_ESEGUITO.getPagaMapping() : StatiPagamentiIris.ANNULLATO.getPagaMapping());
			} else {
				DatiSingoloPagamentoVO datiPagamento = datiRicevutaPagamento.getDatiPagamento().get(0);

				if ("1".equals(datiRicevutaPagamento.getCodiceEsitoPagamento()) || "3".equals(datiRicevutaPagamento.getCodiceEsitoPagamento())) {
					//
					// caso codiceEsitoPagamento = 1,3
					//
					pagamento.setStPagamento("1".equals(datiRicevutaPagamento.getCodiceEsitoPagamento()) ? StatiPagamentiIris.NON_ESEGUITO.getPagaMapping() : StatiPagamentiIris.ANNULLATO.getPagaMapping());

				} else {
					//
					// caso codiceEsitoPagamento = 0,2,4
					//
					pagamento.setStPagamento(datiPagamento.getStatoPagamentoIris().getPagaMapping());
					if(pagamento.getStPagamento().equals(StatiPagamentiIris.ESEGUITO.getPagaMapping())) {
						//BillItemInspector.makeVisible(pagamento.getCondPagamento());
						pendenzaDao.updateStRiga(pagamento.getCondPagamento());
						if (getExternalReceiptCreation(pagamento.getIdTributo()))
						   pagamento.setIdDocumentoExt(Pagamenti.DOC_NOT_AVAILABLE);
					}

				}
				pagamento.setTsDecorrenza(new Timestamp(datiPagamento.getDataEsitoSingoloPagamento().getTime()));
				pagamento.setIdRiscossionePSP(datiPagamento.getIdentificativoUnivocoRiscossione());
				pagamento.setNotePagamento(datiPagamento.getEsitoSingoloPagamento());
				pagamento.setCommissioniPsp(datiPagamento.getCommissioniApplicatePSP());
				if (datiPagamento.getTipoAllegatoRicevuta()!=null) {
					// inserisco qui documento repository
					ConfigurationPropertyLoader conf = ConfigurationPropertyLoader.getInstance("JasperReports/config.properties");
                    String applName = conf.getProperty("configuration.ddp.prefix");
            		
					DocumentiRepository docRep = new DocumentiRepository();
					docRep.setNomeFile(applName+"_MBD_"+distinta.getIuv());
					docRep.setDocumento(datiPagamento.getAllegatoRicevuta());
					docRep.setDimensione(datiPagamento.getAllegatoRicevuta().length);
					docRep.setVersion(1L);
					docRep.setOpInserimento("RT");
					docRep.setTsInserimento(tsAggiornamento);
					docRep=documentiRepositoryDAO.create(docRep);
					//
					pagamento.setTipoAllegatoRepository(datiPagamento.getTipoAllegatoRicevuta());
					pagamento.setIdAllegatoRepository(docRep.getId());
					//salvo i dati della marca da bollo digitale
					if ("BD".equals(datiPagamento.getTipoAllegatoRicevuta()) && "E_BOLLO".equals(pagamento.getCdTrbEnte())) {
						pagamento.setNotePagamento(datiPagamento.getDatiricevutaMBD());
					}
				}

			}

			pagamento.setTsAggiornamento(tsAggiornamento);
			pagamento.setOpAggiornamento("RT_NODOPAGAMENTI");

		}
	}


    // Metodi HP BO e Ente - START
	
	@Override
    public List<Object[]> riepilogoIncassi(String idEnte) {
    	return riepilogoIncassi(idEnte, null);
    }

    @Override
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento) {
    	return riepilogoIncassi(idEnte, annoRiferimento, null);
    }

    @Override
    public List<Object[]> riepilogoIncassi(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk) {

        List<Object[]> dtoReturn = new ArrayList<Object[]>();

        try {
            Tracer.debug(this.getClass().getName(), "riepilogoIncassi", "inizio");

            dtoReturn = condizioniPagamentoDao.riepilogoIncassi(idEnte, annoRiferimento, cdTrbEntePk);

            Tracer.debug(getClass().getName(), "id ente=" + idEnte, "riepilogo incassi nuovo HP - LISTA SIZE: " + dtoReturn.size());
            System.out.println(getClass().getName() + " - id ente=" + idEnte + "riepilogo incassi nuovo HP - LISTA SIZE: " + dtoReturn.size());

        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "errore riepilogoIncassi", e.getMessage(), e);
            e.printStackTrace();
            new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
        }

        Tracer.debug(this.getClass().getName(), "riepilogoIncassi", "fine");

        return dtoReturn;
    }
    
    @Override
    public List<Object[]> riepilogoPagamenti(String idEnte) {
    	return riepilogoPagamenti(idEnte, null, null);
    }


    @Override
    public List<Object[]> riepilogoPagamenti(String idEnte, String annoRiferimento) {
    	return riepilogoPagamenti(idEnte, annoRiferimento, null);
    }


    @Override
    public List<Object[]> riepilogoPagamenti(String idEnte, String annoRiferimento, Set<String> cdTrbEntePk) {

        List<Object[]> dtoReturn = new ArrayList<Object[]>();

        try {
            Tracer.debug(this.getClass().getName(), "riepilogoPagamenti", "inizio");

            dtoReturn = condizioniPagamentoDao.riepilogoPagamenti(idEnte, annoRiferimento, cdTrbEntePk);

            Tracer.debug(getClass().getName(), "id ente=" + idEnte, "riepilogoPagamenti nuovo HP - LISTA SIZE: " + dtoReturn.size());
            System.out.println(getClass().getName() + " - id ente=" + idEnte + "riepilogoPagamenti nuovo HP - LISTA SIZE: " + dtoReturn.size());
        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "errore riepilogoPagamenti", e.getMessage(), e);
            e.printStackTrace();
            new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
        }

        Tracer.debug(this.getClass().getName(), "riepilogoPagamenti", "fine");

        return dtoReturn;
    }

    @Override
    public List<Object[]> riepilogoPosizioniAttese(String idEnte, Integer nrGiorni) {

        List<Object[]> dtoReturn = new ArrayList<Object[]>();

        try {

            Tracer.debug(this.getClass().getName(), "riepilogoPosizioniAttese", "inizio");

            dtoReturn = condizioniPagamentoDao.riepilogoPosizioniAttese(idEnte, nrGiorni);

            Tracer.debug(getClass().getName(), "id ente=" + idEnte + ", nr_giorni=" + nrGiorni, "riepilogoPosizioniAttese nuovo HP - LISTA SIZE: " + dtoReturn.size());
            System.out.println(getClass().getName() + " - id ente=" + idEnte  + ", nr_giorni=" + nrGiorni + "riepilogoPosizioniAttese nuovo HP - LISTA SIZE: " + dtoReturn.size());

        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "errore riepilogoPosizioniAttese", e.getMessage(), e);
            e.printStackTrace();
            new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
        }

        Tracer.debug(this.getClass().getName(), "riepilogoPosizioniAttese", "fine");

        return dtoReturn;
    }

    @Override
    public List<Object[]> riepilogoPosizioniNonAttese(String idEnte, Integer nrGiorni) {

        List<Object[]> dtoReturn = new ArrayList<Object[]>();

        try {
            Tracer.debug(this.getClass().getName(), "riepilogoPosizioniNonAttese", "inizio");

            dtoReturn = condizioniPagamentoDao.riepilogoPosizioniNonAttese(idEnte, nrGiorni);

            Tracer.debug(getClass().getName(), "id ente=" + idEnte + ", nr_giorni=" + nrGiorni, "riepilogoPosizioniNonAttese nuovo HP - LISTA SIZE: " + dtoReturn.size());
            System.out.println(getClass().getName() + " - id ente=" + idEnte  + ", nr_giorni=" + nrGiorni + "riepilogoPosizioniNonAttese nuovo HP - LISTA SIZE: " + dtoReturn.size());
        } catch (Exception e) {
            Tracer.error(this.getClass().getName(), "errore riepilogoPosizioniNonAttese", e.getMessage(), e);
            e.printStackTrace();
            new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
        }

        Tracer.debug(this.getClass().getName(), "riepilogoPosizioniNonAttese", "fine");

        return dtoReturn;
    }

    // Metodi HP BO e Ente - END

    @Override
    public List<Object[]> pagamentiInScadenzaHP(IProfileManager profilo, String catTributo) {

        List<Object[]> dtoReturn = new ArrayList<Object[]>();

        try {

            Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", "inizio");

            dtoReturn = condizioniPagamentoDao.pagamentiInScadenzaHP(profilo.getCodiceFiscale(), catTributo);
            
            if(catTributo == null || catTributo.isEmpty()) {
            	Tracer.debug(getClass().getName(), "cod. fiscale utente=" + profilo.getCodiceFiscale(), "lista pagamenti in scadenza nuovo HP - LISTA SIZE: " + dtoReturn.size());
                System.out.println(getClass().getName() + " - cod. fiscale utente=" + profilo.getCodiceFiscale() + " - lista pagamenti in scadenza nuovo HP - LISTA SIZE: " + dtoReturn.size());
            }
            else {
            	Tracer.debug(getClass().getName(), "cod. fiscale utente=" + profilo.getCodiceFiscale() + " - cod. categoria tributo=" + catTributo, "lista pagamenti in scadenza nuovo HP - LISTA SIZE: " + dtoReturn.size());
                System.out.println(getClass().getName() + " - cod. fiscale utente=" + profilo.getCodiceFiscale() + " - cod. categoria tributo=" + catTributo + " - lista pagamenti in scadenza nuovo HP - LISTA SIZE: " + dtoReturn.size());
            }

        } catch (Exception e) {

            Tracer.error(this.getClass().getName(), "errore pagamentiInScadenzaHP", e.getMessage(), e);

            e.printStackTrace();

            new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
        }

        Tracer.debug(this.getClass().getName(), "pagamentiInScadenzaHP", "fine");

        return dtoReturn;
    }

    @Override
    public List<TributoEnte> listTributoEntePDB(String lapl){

    	List<TributoEnte> tributiEnte = pendenzaDao.listTributoEnteForUserPosizioneDebitoria(lapl);

		return tributiEnte;

    }

    @Override
    public BigDecimal importoTotalePagatoByPendenza(String idPendenza) {
        System.out.println("id_pendenza=" + idPendenza);

        BigDecimal importo = condizioniPagamentoDao.importoTotalePagatoByPendenza(idPendenza);

        System.out.println("PENDENZA ID=" + idPendenza + " TOTALE PAGATO = " + importo);
        Tracer.debug(getClass().getName(), "importoTotalePagatoByPendenza", "PENDENZA ID=" + idPendenza + " TOTALE PAGATO = " + importo);

        return importo;
    }

    @Override
    public List<PagamentoPosizioneDebitoriaForHomePageVO> ultimiPagamentiEffettuatiHP(IProfileManager profilo) {

        return pagamentiDao.ultimiPagamentiEffettuatiHP(profilo.getCodiceFiscale());

    }


    @Override
	public ContainerDTO storicoPagamenti(IProfileManager profilo, ContainerDTO containerDTO) {

        List<PagamentoPosizioneDebitoriaForHomePageVO> storicoPagamentiList = new ArrayList<PagamentoPosizioneDebitoriaForHomePageVO>();

        storicoPagamentiList = pagamentiDao.storicoPagamenti(profilo, containerDTO);

        containerDTO.setOutputDTO(storicoPagamentiList);

        return containerDTO;

	}

    public List<String> getDebitorePendenza(String idPendenza){

    	List<String> debitoriPendenza = destinatariPendenzaDao.listaDebitoriByIdPendenza(idPendenza);

    	return debitoriPendenza;
    }

	@Override
	public List<GestioneFlussi> getDistinteByIdCondizionePagamento(String idCondizionePagamento) {
		return distintaDao.getDistinteByIdCondizionePagamento(idCondizionePagamento);
	}

	@Override
	public List<GestioneFlussi> getDistinteByIdCondizionePagamentoStorico(String idCondizionePagamento) {
		return storicoDistintaDao.getDistinteByIdCondizionePagamento(idCondizionePagamento);
	}

	@Override
	public DistintaCartaCreditoVO preparaPagamento(IProfileManager profilo,DistintaCartaCreditoVO distinta) {

		//prima verifica della pagabilitï¿½
		for (DisposizioneCartaCreditoVO disposizione : distinta.getDisposizioni()) {
			SessionShoppingCartItemDTO pagamentoWIP = (SessionShoppingCartItemDTO) disposizione.getPagamentoVO();
			String idCondizione = pagamentoWIP.getIdCondizione();
			CondizionePagamento condizione = condizioniPagamentoDao.getSingleCondizioneById(idCondizione);
			if (condizione==null || !this.isCondizionePagabile(condizione)) {
				LOGGER.error( "Condizione non pagabile. stato attuale = "+condizione.getStatoPagamentoCalcolato(), null);
				new ManageBackEndException().processBusinessException(BackEndMessage.PAG_001);
			}
		}

		//recupero gateway
		CfgGatewayPagamento gateway = cfgGatewayPagamentoDao.getCfgGatewayPagamentoById(Long.parseLong(distinta.getIdCfgGateway()));


		GestioneFlussi gf = preparaDistinta(profilo, distinta, gateway);
		gf=gestioneFlussiDAO.insertFlusso(gf);

		distinta.setFlusso(gf.getId().toString());
		distinta.setIdFlusso(gf.getId());

		// carrello
		distinta.setIdDistinta(GeneratoreIdUnivoci.GetCurrent().generaId());
		distinta.setDataOrdine(gf.getTsInserimento());

		return distinta;
	}


	private GestioneFlussi preparaDistinta(IProfileManager profilo, DistintaCartaCreditoVO distinta, CfgGatewayPagamento gateway) {

		boolean overwriteCFanonymous = props.getBooleanProperty("gateway.prePagamento.overwriteCFanonymous");
		
		Timestamp now = new Timestamp(new Date().getTime());

		//pagamenti online
		Set<PagamentiOnline> pagaOnline = new LinkedHashSet<PagamentiOnline>();
		PagamentiOnline pol;
		//distinta
		GestioneFlussi flusso = new GestioneFlussi();
		//pagamenti
		Pagamenti pagamento = null;
		Set<Pagamenti> pagamenti = new LinkedHashSet<Pagamenti>();


		//distinta
		flusso.setCodTransazione(distinta.getCodTransazione());

		if (distinta.getCodTransazionePSP()!=null) {
			flusso.setCodTransazionePSP(distinta.getCodTransazionePSP()); // se c'ï¿½ giï¿½ appena creo la distinta
		} else {
			flusso.setCodTransazionePSP("0"); // se c'ï¿½ giï¿½ appena creo la distinta
		}
		if (distinta.getIUV()==null){
		   flusso.setIuv(distinta.getCodTransazione());
		} else {
		   flusso.setIuv(distinta.getIUV());
		}
		flusso.setIdentificativoFiscaleCreditore(distinta.getIdFiscaleCreditore());

		flusso.setDivisa("EUR");
		flusso.setImportoCommissioni(distinta.getImportoCommissioni());
		flusso.setOpAggiornamento(null);
		flusso.setOpInserimento(profilo.getLapl());
		flusso.setStato(StatiPagamentiIris.IN_CORSO.getFludMapping());
		flusso.setTmbcreazione(now);
		flusso.setTotimportipositivi(distinta.getTotImportiPositivi());
		flusso.setTsInserimento(now);
		flusso.setTsUpdate(null);
		
		if (overwriteCFanonymous) {
		   flusso.setUtentecreatore(profilo.getCodFiscalePagante());
	    }else {
		   flusso.setUtentecreatore(distinta.getCodFiscaleVers()); 
		   
	    }
		flusso.setCfgGatewayPagamento(gateway);
		flusso.setDataSpedizione(now);
		flusso.setCodPagamento(distinta.getCodPagamento());
		flusso.setEmailVersante(profilo.getEmailPagante());
        flusso.setIdGruppo(distinta.getIdGruppo());

        //
        flusso.setTipoSoggettoVers(distinta.getTipoSoggettoVers()); 
        flusso.setCodFiscaleVers(distinta.getCodFiscaleVers()); 
        flusso.setAnagraficaVers(distinta.getAnagraficaVers());
        flusso.setIndirizzoVers(distinta.getIndirizzoVers()); 
        flusso.setNumeroCivicoVers(distinta.getNumeroCivicoVers()); 
        flusso.setCapVers(distinta.getCapVers()); 
        flusso.setLocalitaVers(distinta.getLocalitaVers()); 
        flusso.setProvinciaVers(distinta.getProvinciaVers()); 
        flusso.setNazioneVers(distinta.getNazioneVers());
        //
        flusso.setLocale(distinta.getLocale());
        
		//pagamenti
		Iterator itDisp = distinta.getDisposizioni().iterator();
		while(itDisp.hasNext()){
			DisposizioneCartaCreditoVO cvo = (DisposizioneCartaCreditoVO)itDisp.next();
			SessionShoppingCartItemDTO posvo = (SessionShoppingCartItemDTO) cvo.getPagamentoVO();
			pagamento = new Pagamenti();

			//recupera condizione di pagamento
			CondizionePagamento condizione = condizioniPagamentoDao.getSingleCondizioneById(posvo.getIdCondizione());

			pagamento.setCdTrbEnte(posvo.getIdTributoEnte());
			pagamento.setCondPagamento(condizione);
			if (overwriteCFanonymous) {
			   pagamento.setCoPagante(profilo.getCodFiscalePagante());
			} else {
				pagamento.setCoPagante(distinta.getCodFiscaleVers()); 
			}
			pagamento.setDtScadenza(posvo.getScadenza());
			pagamento.setFlussoDistinta(flusso);
			pagamento.setIdEnte(posvo.getIdEnte());
			pagamento.setIdPendenza(posvo.getIdPendenza());
			pagamento.setIdPendenzaente(condizione.getPendenza().getIdPendenzaente());
			pagamento.setIdTributo(posvo.getIdTributo());
			pagamento.setImPagato(posvo.getImporto());
			pagamento.setOpAggiornamento(null);
			pagamento.setOpInserimento(profilo.getLapl());
			pagamento.setStatoNotifica(null);
			pagamento.setStPagamento(StatiPagamentiIris.IN_CORSO.getPagaMapping());
			pagamento.setStRiga("V");
			pagamento.setTiDebito(posvo.getTiDebito());
			pagamento.setTiPagamento(posvo.getTipoPagamento());

			pagamento.setFlagOpposizione730(posvo.getItemOpposizione730());

			if (posvo.isSpontaneo()) {
				pagamento.setTipospontaneo(posvo.getTipoSpontaneo());
			}
			else{
				pagamento.setTipospontaneo(PagamentoPosizioneDebitoriaVO.TIPO_ATTESO);
			}

			pagamento.setTsAggiornamento(null);
			pagamento.setTsDecorrenza(new Timestamp(posvo.getDecorrenza().getTime()));
			pagamento.setTsInserimento(now);
			pagamento.setTsOrdine(now);
		
			pagamento.setDistinta("DISTINTA");
			if ("E_BOLLO".equals(pagamento.getCdTrbEnte()) || (gateway.getFlagRendRiversamento().equals("0") && 
					CheckRiconciliazioneCompleta.isEnteRiconciliazioneCompleta(distinta.getIdFiscaleCreditore()))) {
			   pagamento.setFlagIncasso("N");	
			} else {
			   pagamento.setFlagIncasso("0");
			}
			pagamenti.add(pagamento);
		}

		//pagamenti online
		pol = new PagamentiOnline();
		pol.setCodAutorizzazione(flusso.getIuv());
		pol.setDeOperazione(EnumOperazioniPagamento.AUTORIZZAZIONE.getDescrizione());
		pol.setFlussoDistintaOnline(flusso);
		pol.setIdOperazione(distinta.getToken());
		pol.setNumOperazione("1");
		pol.setOpAggiornamento(null);
		pol.setOpInserimento(profilo.getLapl());
		pol.setSessionIdSistema("SYS");
		pol.setSessionIdTerminale("TERM");
		pol.setSessionIdTimbro("TIMBRO");
		pol.setSessionIdToken(flusso.getCodTransazionePSP());
		pol.setApplicationId(flusso.getCfgGatewayPagamento().getApplicationId());
		pol.setSystemId(flusso.getCfgGatewayPagamento().getSystemId());
		pol.setTiOperazione(EnumOperazioniPagamento.AUTORIZZAZIONE.getChiave());
		pol.setTsAggiornamento(null);
		pol.setTsInserimento(now);
		pol.setTsOperazione(now);
		pagaOnline.add(pol);

		//assegna pagamenti e paga on line
		flusso.setPagamenti(pagamenti);
		flusso.setPagamentiOnline(pagaOnline);
		flusso.setNumeroDisposizioni(pagamenti.size());

		return flusso;
			}
	
	@Override
    public List<Pagamento> getPagamentoEseguito(String idCondizione) {
    	List<Pagamenti> listCondizioniCreditore = pagamentiDao.getPagamentoEseguito(idCondizione);
        List<Pagamento> pagamentoList = new ArrayList<Pagamento>();

        for (Pagamenti pagamentoDomain : listCondizioniCreditore) {
            CondizionePagamento condizionePagamentoDomain = pagamentoDomain.getCondPagamento();
            Pagamento pagamento = new Pagamento();

            pagamento.setId(pagamentoDomain.getId());

            it.tasgroup.idp.rs.model.creditore.CondizionePagamento condizionePagamento = new it.tasgroup.idp.rs.model.creditore.CondizionePagamento();
            pagamento.setCondizionePagamento(condizionePagamento);

            condizionePagamento.setIdCondizione(condizionePagamentoDomain.getIdCondizione());
            condizionePagamento.setDataInizioValidita(condizionePagamentoDomain.getDtIniziovalidita());
            condizionePagamento.setDataScadenza(condizionePagamentoDomain.getDtScadenza());
            condizionePagamento.setDataFineValidita(condizionePagamentoDomain.getDtFinevalidita());
            condizionePagamento.setImporto(condizionePagamentoDomain.getImPagamento());
            condizionePagamento.setCausalePagamento(condizionePagamentoDomain.getCausalePagamento());
            condizionePagamento.setIdPagamento(condizionePagamentoDomain.getIdPagamento());
            condizionePagamento.setImportoTotaleDebito(condizionePagamentoDomain.getImPagamento());

            final Pendenza pendenzaDomain = condizionePagamentoDomain.getPendenza();

            final Set<DestinatariPendenza> destinatari = pendenzaDomain.getDestinatari();

            List<Debitore> debitori = new ArrayList<Debitore>();
            condizionePagamento.setDebitori(debitori);

            for (DestinatariPendenza destinatario : destinatari) {
                Debitore debitore = new Debitore();
                debitore.setAnagrafica(destinatario.getDeDestinatario());
                debitore.setCodFiscale(destinatario.getCoDestinatario());


                // debitore.setTipoCodiceIdentificativoAlternativo(); // non presente
//                debitore.setCodiceIdentificativoAlternativo(destinatario.getCodAlternativoDebitore());
//                debitore.setIndirizzo(destinatario.getIndirizzoDebitore());
//                debitore.setCap(destinatario.getCapDebitore());
//                debitore.setLocalita(destinatario.getLocalitaDebitore());
//                debitore.setLuogoNascita(destinatario.getLuogoNascitaDebitore());
//                debitore.setEmail(destinatario.getEmailDebitore());
//                debitore.setDataNascita(destinatario.getDataNascitaDebitore());
//                debitore.setNumeroCivico(destinatario.getNumeroCivicoDebitore());
//                debitore.setNazione(destinatario.getNazioneDebitore());
//                debitore.setProvincia(destinatario.getProvinciaDebitore());
//                debitore.setTipoSoggetto(destinatario.getTipoSoggettoDebitore());


                debitori.add(debitore);
            }

            condizionePagamento.setTipoDebito(pagamentoDomain.getTiDebito());
            final Enti enteDomain = condizionePagamentoDomain.getEnte();
            condizionePagamento.setCreditore(enteDomain.getCodiceEnte());
            condizionePagamento.setTipoDebito(pagamentoDomain.getCdTrbEnte());
            condizionePagamento.setDescrizioneCreditore(enteDomain.getDenominazione());

            final CategoriaTributo categoriaTributoDomain = pendenzaDomain.getCategoriaTributo();
            condizionePagamento.setCodCategoriaDebito(categoriaTributoDomain.getIdTributo());

            condizionePagamento.setDescrizioneCategoriaDebito(categoriaTributoDomain.getDeTrb());
            condizionePagamento.setNoteDebito(pendenzaDomain.getNote());
            condizionePagamento.setDataCreazioneDebito(pendenzaDomain.getTsCreazioneente());

            condizionePagamento.setCodFiscaleCreditore(enteDomain.getIntestatarioobj().getLaplIForm());

            condizionePagamento.setDataEmissioneDebito(pendenzaDomain.getTsEmissioneente());
            condizionePagamento.setImportoTotaleDebito(pendenzaDomain.getImTotale());

            condizionePagamento.setInformazioniPagamentoCondizione(condizionePagamento.getInformazioniPagamentoCondizione());


            EnumTipoRateazione tipoRateazione = findByChiave(condizionePagamentoDomain.getTiPagamento(), EnumTipoRateazione.class);
            condizionePagamento.setTipoRateazionePagamento(tipoRateazione);

            final GestioneFlussi distintaDomain = pagamentoDomain.getFlussoDistinta();
            pagamento.setCodPagamento(distintaDomain.getCodPagamento());

            Versante versante = new Versante();
            versante.setCodFiscale(distintaDomain.getUtentecreatore());
            versante.setEmail(distintaDomain.getEmailVersante());
            pagamento.setVersante(versante);

            condizionePagamento.setDivisa(distintaDomain.getDivisa());
            condizionePagamento.setDescrizioneTipoDebito(pendenzaDomain.getTributoEnte().getDeTrb());

            condizionePagamento.setCausaleDebito(pendenzaDomain.getDeCausale());
            condizionePagamento.setIdDebito(pendenzaDomain.getIdPendenzaente());

            condizionePagamento.setAnnoRiferimento(String.valueOf(pendenzaDomain.getAnnoRiferimento()));
            condizionePagamento.setIdMittente(pendenzaDomain.getIdMittente());
            condizionePagamento.setDescrizioneMittente(pendenzaDomain.getDeMittente());

            Pagamento.InformazioniTransazionePagamento informazioniTransazionePagamento = new Pagamento.InformazioniTransazionePagamento();

            informazioniTransazionePagamento.setDataOraTransazionePagamento(pagamentoDomain.getTsDecorrenza());
            informazioniTransazionePagamento.setIdentificativoUnivocoRiscossione(pagamentoDomain.getIdRiscossionePSP());

            List<GestioneFlussi> distinteStessoGruppo = getDistinteStessoGruppo(distintaDomain.getId());
            BigDecimal importoTransazionePagamento = BigDecimal.ZERO;
            BigDecimal importoCommissioniTransazionePagamento = BigDecimal.ZERO;
            Long numeroPagamentiTransazionePagamento = 0L;
            for (GestioneFlussi d : distinteStessoGruppo) {
                importoTransazionePagamento=importoTransazionePagamento.add(d.getTotimportipositivi());
                importoCommissioniTransazionePagamento=importoCommissioniTransazionePagamento.add(d.getImportoCommissioni());
                numeroPagamentiTransazionePagamento = numeroPagamentiTransazionePagamento + d.getNumeroDisposizioni();
            }
            CfgGatewayPagamento gatewayPagamento = distintaDomain.getCfgGatewayPagamento();
            informazioniTransazionePagamento.setDescrizionePsp(gatewayPagamento.getSystemName());
            informazioniTransazionePagamento.setIdentiticativoPsp(gatewayPagamento.getSystemId());
            informazioniTransazionePagamento.setImportoTransazionePagamento(importoTransazionePagamento);
            informazioniTransazionePagamento.setImportoCommissioniTransazionePagamento(importoCommissioniTransazionePagamento);
            informazioniTransazionePagamento.setNumeroPagamentiTransazionePagamento(numeroPagamentiTransazionePagamento);
            informazioniTransazionePagamento.setIdentificativoCanalePagamento(gatewayPagamento.getSystemId());
            informazioniTransazionePagamento.setIdentificativoCanalePagamento(gatewayPagamento.getApplicationId());
            informazioniTransazionePagamento.setTipoVersamento(findByChiave(gatewayPagamento.getTipoVersamento(), EnumTipoVersamento.class));
            informazioniTransazionePagamento.setModalitaPagamento(gatewayPagamento.getCfgModalitaPagamento().getDescrizione());

            // se e' incassato...
            if (byChiave(pagamentoDomain.getStPagamento(), EnumStatoPagamento.class) == EnumStatoPagamento.ESEGUITO && byChiave(pagamentoDomain.getFlagIncasso(), EnumStatoIncasso.class) == EnumStatoIncasso.RIVERSATO) {
                Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso = new Pagamento.InformazioniTransazioneIncasso(pagamentoDomain.getMittRendicontazioneIncasso(),
                        pagamentoDomain.getDataAccreditoEnte(),
                        pagamentoDomain.getTRN(),
                        pagamentoDomain.getTotaleRendicontazioneIncasso(),
                        pagamentoDomain.getCodRendicontazioneIncasso(),
                        pagamentoDomain.getIdentificativoFlusso(),
                        EnumUtils.findByChiave(pagamentoDomain.getFlagIncasso(), EnumStatoIncasso.class)
                );
                pagamento.setInformazioniTransazioneIncasso(informazioniTransazioneIncasso);
            }



            pagamento.setInformazioniTransazionePagamento(informazioniTransazionePagamento);

            List<VocePagamento> vociPagamento = condizionePagamentoDomain.getVociPagamento();
            List<Voce> voci = new ArrayList<Voce>();
            for (VocePagamento vocePagamento : vociPagamento) {
                Voce voce = new Voce(vocePagamento.getTiVoce(), vocePagamento.getCoVoce(), vocePagamento.getDeVoce(), vocePagamento.getImVoce(), vocePagamento.getCoCapBilancio(), vocePagamento.getCoAccertamento());
                voci.add(voce);
            }
            condizionePagamento.setVoci(voci);

            final String flagIncasso = pagamentoDomain.getFlagIncasso();
            final EnumStatoIncasso enumStatoIncasso = findByChiave(flagIncasso, EnumStatoIncasso.class);
            pagamento.setFlagIncasso(enumStatoIncasso);

            pagamento.setImportoPagato(pagamentoDomain.getImPagato());
            pagamento.setNotePagamento(pagamentoDomain.getNotePagamento());
            pagamento.setDataOraPagamento(pagamentoDomain.getTsInserimento());

            String stPagamento = pagamentoDomain.getStPagamento();
            EnumStatoPagamento enumStatoPagamento = findByChiave(stPagamento, EnumStatoPagamento.class);
            pagamento.setStatoPagamento(enumStatoPagamento);

            pagamento.setIdentificativoUnivocoVersamento(distintaDomain.getIuv());

            pagamento.setCodiceContestoPagamento(distintaDomain.getCodTransazionePSP());

            final String urlRicevuta = this.getUrlQuietanza(pagamentoDomain, distintaDomain.getCodPagamento(), pagamentoDomain.getCoPagante(), String.valueOf(pagamentoDomain.getId()));
            pagamento.setUrlDownloadRicevuta(urlRicevuta);

            pagamentoList.add(pagamento);

        }


        return pagamentoList;
    }
	
	@Override
    public List<Pagamento> getPagamentiEseguiti(ContainerDTO inputDTO, String flagInCorso, String idCondizione) {
    	List<Pagamenti> listCondizioniCreditore = pagamentiDao.getPagamentiEseguiti(inputDTO, flagInCorso, idCondizione);
        List<Pagamento> pagamentoList = new ArrayList<Pagamento>();

        for (Pagamenti pagamentoDomain : listCondizioniCreditore) {
            CondizionePagamento condizionePagamentoDomain = pagamentoDomain.getCondPagamento();
            Pagamento pagamento = new Pagamento();

            pagamento.setId(pagamentoDomain.getId());

            it.tasgroup.idp.rs.model.creditore.CondizionePagamento condizionePagamento = new it.tasgroup.idp.rs.model.creditore.CondizionePagamento();
            pagamento.setCondizionePagamento(condizionePagamento);

            condizionePagamento.setIdCondizione(condizionePagamentoDomain.getIdCondizione());
            condizionePagamento.setDataInizioValidita(condizionePagamentoDomain.getDtIniziovalidita());
            condizionePagamento.setDataScadenza(condizionePagamentoDomain.getDtScadenza());
            condizionePagamento.setDataFineValidita(condizionePagamentoDomain.getDtFinevalidita());
            condizionePagamento.setImporto(condizionePagamentoDomain.getImPagamento());
            condizionePagamento.setCausalePagamento(condizionePagamentoDomain.getCausalePagamento());
            condizionePagamento.setIdPagamento(condizionePagamentoDomain.getIdPagamento());
            condizionePagamento.setImportoTotaleDebito(condizionePagamentoDomain.getImPagamento());

            final Pendenza pendenzaDomain = condizionePagamentoDomain.getPendenza();

            final Set<DestinatariPendenza> destinatari = pendenzaDomain.getDestinatari();

            List<Debitore> debitori = new ArrayList<Debitore>();
            condizionePagamento.setDebitori(debitori);

            for (DestinatariPendenza destinatario : destinatari) {
                Debitore debitore = new Debitore();
                debitore.setAnagrafica(destinatario.getDeDestinatario());
                debitore.setCodFiscale(destinatario.getCoDestinatario());


                // debitore.setTipoCodiceIdentificativoAlternativo(); // non presente
//                debitore.setCodiceIdentificativoAlternativo(destinatario.getCodAlternativoDebitore());
//                debitore.setIndirizzo(destinatario.getIndirizzoDebitore());
//                debitore.setCap(destinatario.getCapDebitore());
//                debitore.setLocalita(destinatario.getLocalitaDebitore());
//                debitore.setLuogoNascita(destinatario.getLuogoNascitaDebitore());
//                debitore.setEmail(destinatario.getEmailDebitore());
//                debitore.setDataNascita(destinatario.getDataNascitaDebitore());
//                debitore.setNumeroCivico(destinatario.getNumeroCivicoDebitore());
//                debitore.setNazione(destinatario.getNazioneDebitore());
//                debitore.setProvincia(destinatario.getProvinciaDebitore());
//                debitore.setTipoSoggetto(destinatario.getTipoSoggettoDebitore());


                debitori.add(debitore);
            }

            condizionePagamento.setTipoDebito(pagamentoDomain.getTiDebito());
            final Enti enteDomain = condizionePagamentoDomain.getEnte();
            condizionePagamento.setCreditore(enteDomain.getCodiceEnte());
            condizionePagamento.setTipoDebito(pagamentoDomain.getCdTrbEnte());
            condizionePagamento.setDescrizioneCreditore(enteDomain.getDenominazione());

            final CategoriaTributo categoriaTributoDomain = pendenzaDomain.getCategoriaTributo();
            condizionePagamento.setCodCategoriaDebito(categoriaTributoDomain.getIdTributo());

            condizionePagamento.setDescrizioneCategoriaDebito(categoriaTributoDomain.getDeTrb());
            condizionePagamento.setNoteDebito(pendenzaDomain.getNote());
            condizionePagamento.setDataCreazioneDebito(pendenzaDomain.getTsCreazioneente());

            condizionePagamento.setCodFiscaleCreditore(enteDomain.getIntestatarioobj().getLaplIForm());

            condizionePagamento.setDataEmissioneDebito(pendenzaDomain.getTsEmissioneente());
            condizionePagamento.setImportoTotaleDebito(pendenzaDomain.getImTotale());

            condizionePagamento.setInformazioniPagamentoCondizione(condizionePagamento.getInformazioniPagamentoCondizione());


            EnumTipoRateazione tipoRateazione = findByChiave(condizionePagamentoDomain.getTiPagamento(), EnumTipoRateazione.class);
            condizionePagamento.setTipoRateazionePagamento(tipoRateazione);

            final GestioneFlussi distintaDomain = pagamentoDomain.getFlussoDistinta();
            pagamento.setCodPagamento(distintaDomain.getCodPagamento());

            Versante versante = new Versante();
            versante.setCodFiscale(distintaDomain.getUtentecreatore());
            versante.setEmail(distintaDomain.getEmailVersante());
            pagamento.setVersante(versante);

            condizionePagamento.setDivisa(distintaDomain.getDivisa());
            condizionePagamento.setDescrizioneTipoDebito(pendenzaDomain.getTributoEnte().getDeTrb());
            
            //TODO DECODIFICARE LA CASUALE
            
            condizionePagamento.setCausaleDebito(pendenzaDomain.getDeCausale());
            TributoStrutturato tributoStrutturato = condizionePagamentoDomain.getPendenza().getTributoStrutturato();
    		if (tributoStrutturato != null) {
    			String causaleDescr=getDescrCausaleByAddonManager(condizionePagamentoDomain.getPendenza());
    			condizionePagamento.setCausaleDebito(causaleDescr);
    		}
            
            
            condizionePagamento.setIdDebito(pendenzaDomain.getIdPendenzaente());

            condizionePagamento.setAnnoRiferimento(String.valueOf(pendenzaDomain.getAnnoRiferimento()));
            condizionePagamento.setIdMittente(pendenzaDomain.getIdMittente());
            condizionePagamento.setDescrizioneMittente(pendenzaDomain.getDeMittente());

            Pagamento.InformazioniTransazionePagamento informazioniTransazionePagamento = new Pagamento.InformazioniTransazionePagamento();

            informazioniTransazionePagamento.setDataOraTransazionePagamento(pagamentoDomain.getTsDecorrenza());
            informazioniTransazionePagamento.setIdentificativoUnivocoRiscossione(pagamentoDomain.getIdRiscossionePSP());

            List<GestioneFlussi> distinteStessoGruppo = getDistinteStessoGruppo(distintaDomain.getId());
            BigDecimal importoTransazionePagamento = BigDecimal.ZERO;
            BigDecimal importoCommissioniTransazionePagamento = BigDecimal.ZERO;
            Long numeroPagamentiTransazionePagamento = 0L;
            for (GestioneFlussi d : distinteStessoGruppo) {
                importoTransazionePagamento=importoTransazionePagamento.add(d.getTotimportipositivi());
                importoCommissioniTransazionePagamento=importoCommissioniTransazionePagamento.add(d.getImportoCommissioni());
                numeroPagamentiTransazionePagamento = numeroPagamentiTransazionePagamento + d.getNumeroDisposizioni();
            }
            CfgGatewayPagamento gatewayPagamento = distintaDomain.getCfgGatewayPagamento();
            informazioniTransazionePagamento.setDescrizionePsp(gatewayPagamento.getSystemName());
            informazioniTransazionePagamento.setIdentiticativoPsp(gatewayPagamento.getSystemId());
            informazioniTransazionePagamento.setImportoTransazionePagamento(importoTransazionePagamento);
            informazioniTransazionePagamento.setImportoCommissioniTransazionePagamento(importoCommissioniTransazionePagamento);
            informazioniTransazionePagamento.setNumeroPagamentiTransazionePagamento(numeroPagamentiTransazionePagamento);
            informazioniTransazionePagamento.setIdentificativoCanalePagamento(gatewayPagamento.getSystemId());
            informazioniTransazionePagamento.setIdentificativoCanalePagamento(gatewayPagamento.getApplicationId());
            informazioniTransazionePagamento.setTipoVersamento(findByChiave(gatewayPagamento.getTipoVersamento(), EnumTipoVersamento.class));
            informazioniTransazionePagamento.setModalitaPagamento(gatewayPagamento.getCfgModalitaPagamento().getDescrizione());

            // se e' incassato...
            if (byChiave(pagamentoDomain.getStPagamento(), EnumStatoPagamento.class) == EnumStatoPagamento.ESEGUITO && byChiave(pagamentoDomain.getFlagIncasso(), EnumStatoIncasso.class) == EnumStatoIncasso.RIVERSATO) {
                Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso = new Pagamento.InformazioniTransazioneIncasso(pagamentoDomain.getMittRendicontazioneIncasso(),
                        pagamentoDomain.getDataAccreditoEnte(),
                        pagamentoDomain.getTRN(),
                        pagamentoDomain.getTotaleRendicontazioneIncasso(),
                        pagamentoDomain.getCodRendicontazioneIncasso(),
                        pagamentoDomain.getIdentificativoFlusso(),
                        EnumUtils.findByChiave(pagamentoDomain.getFlagIncasso(), EnumStatoIncasso.class)
                );
                pagamento.setInformazioniTransazioneIncasso(informazioniTransazioneIncasso);
            }



            pagamento.setInformazioniTransazionePagamento(informazioniTransazionePagamento);

            List<VocePagamento> vociPagamento = condizionePagamentoDomain.getVociPagamento();
            List<Voce> voci = new ArrayList<Voce>();
            for (VocePagamento vocePagamento : vociPagamento) {
                Voce voce = new Voce(vocePagamento.getTiVoce(), vocePagamento.getCoVoce(), vocePagamento.getDeVoce(), vocePagamento.getImVoce(), vocePagamento.getCoCapBilancio(), vocePagamento.getCoAccertamento());
                voci.add(voce);
            }
            condizionePagamento.setVoci(voci);

            final String flagIncasso = pagamentoDomain.getFlagIncasso();
            final EnumStatoIncasso enumStatoIncasso = findByChiave(flagIncasso, EnumStatoIncasso.class);
            pagamento.setFlagIncasso(enumStatoIncasso);

            pagamento.setImportoPagato(pagamentoDomain.getImPagato());
            pagamento.setNotePagamento(pagamentoDomain.getNotePagamento());
            pagamento.setDataOraPagamento(pagamentoDomain.getTsInserimento());

            String stPagamento = pagamentoDomain.getStPagamento();
            EnumStatoPagamento enumStatoPagamento = findByChiave(stPagamento, EnumStatoPagamento.class);
            pagamento.setStatoPagamento(enumStatoPagamento);

            pagamento.setIdentificativoUnivocoVersamento(distintaDomain.getIuv());

            pagamento.setCodiceContestoPagamento(distintaDomain.getCodTransazionePSP());

            final String urlRicevuta = this.getUrlQuietanza(pagamentoDomain, distintaDomain.getCodPagamento(), pagamentoDomain.getCoPagante(), String.valueOf(pagamentoDomain.getId()));
            pagamento.setUrlDownloadRicevuta(urlRicevuta);

            pagamentoList.add(pagamento);

        }


        return pagamentoList;
    }
	
	private static String getDescrCausaleByAddonManager(Pendenza pendenza) {
		TributoEnte tributo = pendenza.getTributoEnte();
		String cdPlugin = tributo.getCfgTributoEntePlugin() != null ? tributo.getCfgTributoEntePlugin().getCdPlugin() : "";
		return getDescrCausaleByAddonManager(tributo.getIdEnte(), tributo.getCdTrbEnte(), cdPlugin, pendenza.getDeCausale());
	} 
    
    private static String getDescrCausaleByAddonManager(String idEnte, String cdTrbEnte, String cdPlugin, String deCausale) {

		String causaleDescr;
		if (AddOnManagerFactory.exists(cdPlugin)) {
			AddOnManager<TributoStrutturato> manager = AddOnManagerFactory.getAddOnManager(idEnte, cdTrbEnte, cdPlugin); // per es CONFERIMENTO_DISCARICA
			AddOnViewHelper<TributoStrutturato> viewHelper = manager.getViewHelper();
			causaleDescr = viewHelper.getCausale(deCausale);

		} else {
			causaleDescr = deCausale;
		}
		return causaleDescr;

	}
	
	@Override
    public String getUrlQuietanza(Pagamenti pagamento, String codPagamento, String codiceFiscale, String idFlusso) {

        // Ricevuta presente nel sistema quando eseguito e documento disponibile
        boolean ricevutaPresente = "ES".equals(pagamento.getStPagamento()) && pagamento.isAssociatedDocumentAvailable();

        String urlQuietanza = "";

        if (ricevutaPresente) {

            ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("gateway-ws-client.properties");
            String baseUrlGateway = cpl.getProperty("iris.gateway.webapp.baseUrl");
            DownloadQuietanzaParametersEncrypter dr = new DownloadQuietanzaParametersEncrypter(codPagamento, codiceFiscale, idFlusso);
            String crParam = dr.encrypt();
            urlQuietanza = baseUrlGateway + "/documentiPagamento.do?method=downloadQuietanza&" + SharedConstants.CRYPTEDPARAMS + "=" + crParam;
        }

        return urlQuietanza;
    }


	private boolean isCondizionePagabile(CondizionePagamento  cp) {
		cp.updateStatoPagamentoCalcolato();
		return EnumStatoPagamentoCondizione.DA_PAGARE.equals(cp.getStatoPagamentoCalcolato());
	}

	public Boolean getExternalReceiptCreation(String idTributo) {
		
		//FIXME
		//Premessa:
		//attualmente questa configurazione + gestita da file con i seguenti valori
		//false: gen. ricevuta
		//idTributo1,idTributo2 le categoria per le quali la ricevuta ï¿½ generata esternamente
		
		//questo valore deve essere configurato per ENTE/TRIBUTO
		//da mettere a piano la modifica di questa gestione per gestire questo valore da DB
		
		ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		String valueCfg= props.getProperty("generate.external.receipt");

		if ("false".equalsIgnoreCase(valueCfg))
			return false;
		
		//verifico in dettaglio seper questo ID_TRIBUTO (Categoria) la ricevuta viene generata esternamente
		String[] values= valueCfg.split(";");
		for (String val : values) {
			if (val.equalsIgnoreCase(idTributo))
				return true;
		}
		return false;
	}

    public List<GestioneFlussi> getByCodPagamentoCodiceFiscale(String codPagamento, String codFiscale) {
        return gestioneFlussiDAO.getByCodPagamentoCodiceFiscale(codPagamento, codFiscale);
    }

	@Override
	public List<GestioneFlussi> getDistinteByCodPagamento(String codPagamento) {
		return distintaDao.getByCodPagamento(codPagamento);
	}

	@Override
	public Pagamenti getPagamentoById(Long idPagamento) {
		try {
			return pagamentiDao.getById(Pagamenti.class, idPagamento);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException();
		}
	}

	@Override
	public List<Pagamenti> getPagamentiByIdDistinta(Long idDistinta) {
		return pagamentiDao.getPagamentiByIdDistinta(idDistinta);
	}

	@Override
	public List<GestioneFlussi> getDistinteStessoGruppo(Long idDistinta) {
		try {
		  GestioneFlussi d = distintaDao.getById(GestioneFlussi.class, idDistinta);
		  if (d.getIdGruppo()!=null && !"".equals(d.getIdGruppo())) {
			  return distintaDao.getDistinteByIdGruppo(d.getIdGruppo());
		  }else {
			  List<GestioneFlussi> l = new ArrayList<GestioneFlussi>();
			  l.add(d);
			  return l;
		  }
		} catch (Throwable t) {
		   // TODO Auto-generated method stub
		   return null;
		}
	}
		
	@Override
	public List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> getCondizioniCreditoreStorico(ContainerDTO inputDTO) {
		List<CondizionePagamento> listCondizioniCreditore = pagamentiDaoStorico.getCondizioniCreditore(inputDTO);
		return getCondizioniPagamento(listCondizioniCreditore);
	}
	
	@Override
	public List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> getCondizioniCreditore(ContainerDTO inputDTO) {
		List<CondizionePagamento> listCondizioniCreditore = pagamentiDao.getCondizioniCreditore(inputDTO);
		return getCondizioniPagamento(listCondizioniCreditore);
	}
		
		
		public List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> getCondizioniPagamento(	List<CondizionePagamento> listCondizioniCreditore) {
				List<it.tasgroup.idp.rs.model.creditore.CondizionePagamento> condizionePagamentoList = new ArrayList<it.tasgroup.idp.rs.model.creditore.CondizionePagamento>();
				
				for (CondizionePagamento condizionePagamentoDomain : listCondizioniCreditore) {
						it.tasgroup.idp.rs.model.creditore.CondizionePagamento condizionePagamento = new it.tasgroup.idp.rs.model.creditore.CondizionePagamento();
						
						// map pendenza to condizione
						final Pendenza pendenzaDomain = condizionePagamentoDomain.getPendenza();
						condizionePagamento.setIdPendenza(pendenzaDomain.getIdPendenza());
						//condizionePagamento.setIdLista(pendenza.getIdLista());
						// condizionePagamento.setRiferimentoIdDebito(pendenza.getRifIdDebito());
						
				
					
						
						condizionePagamento.setIdCondizione(condizionePagamentoDomain.getIdCondizione());
						condizionePagamento.setDataInizioValidita(condizionePagamentoDomain.getDtIniziovalidita());
						condizionePagamento.setDataScadenza(condizionePagamentoDomain.getDtScadenza());
						condizionePagamento.setDataFineValidita(condizionePagamentoDomain.getDtFinevalidita());
						condizionePagamento.setImporto(condizionePagamentoDomain.getImTotale());
						condizionePagamento.setImportoTotaleDebito(condizionePagamentoDomain.getImPagamento());
						
					
						
						// condizionePagamento.setCausaleBollettino(condizionePagamentoDomain.getCausaleBollettino());
						// condizionePagamento.setDatiRiscossione(condizionePagamentoDomain.getDatiRiscossione());
						// condizionePagamento.setRiferimentoIdDebito(condizionePagamentoDomain.getRifetimentoAllegatoAvviso());
					
						//condizionePagamento.setNomeAvviso(condizionePagamentoDomain.getNomeAvviso());
					
					condizionePagamento.setRiscossore(pendenzaDomain.getCoRiscossore());
					condizionePagamento.setRiferimento(pendenzaDomain.getRiferimento());
						
						condizionePagamento.setCausalePagamento(condizionePagamentoDomain.getCausalePagamento());
						condizionePagamento.setIdPagamento(condizionePagamentoDomain.getIdPagamento());
				
						
						final Set<DestinatariPendenza> destinatari = pendenzaDomain.getDestinatari();
						
						List<Debitore> debitori = new ArrayList<Debitore>();
						condizionePagamento.setDebitori(debitori);
						
						for (DestinatariPendenza destinatario : destinatari) {
								Debitore debitore = new Debitore();
								debitore.setAnagrafica(destinatario.getDeDestinatario());
								debitore.setCodFiscale(destinatario.getCoDestinatario());
								debitore.setDescrizione(destinatario.getDeDestinatario());
								
								//              debitore.setTipoCodiceIdentificativoAlternativo(destinatario.id); // non presente
								
							
//								debitore.setCodiceIdentificativoAlternativo(destinatario.getCodAlternativoDebitore());
								debitore.setIndirizzo(destinatario.getIndirizzoDestinatario());
								debitore.setCap(destinatario.getCapDestinatario());
								debitore.setLocalita(destinatario.getLocalitaDestinatario());
								debitore.setLuogoNascita(destinatario.getLuogoNascitaDestinatario());
								debitore.setEmail(destinatario.getEmailDestinatario());
//								debitore.setPec(destinatario.getPecDebitore());
//								debitore.setAltroRecapito(destinatario.getRecapitiAltDebitore());
								debitore.setDataNascita(destinatario.getDataNascitaDestinatario());
								debitore.setNumeroCivico(destinatario.getNumCivicoDestinatario());
								debitore.setNazione(destinatario.getNazioneDestinatario());
								debitore.setProvincia(destinatario.getProvinciaDestinatario());
								debitore.setTipoSoggetto(destinatario.getTipoSoggettoDestinatario());
								
								
								debitori.add(debitore);
						}
						
						final Enti enteDomain = condizionePagamentoDomain.getEnte();
						condizionePagamento.setCreditore(enteDomain.getCodiceEnte());
						
						condizionePagamento.setDescrizioneCreditore(enteDomain.getDenominazione());
						
						final CategoriaTributo categoriaTributoDomain = pendenzaDomain.getCategoriaTributo();
						condizionePagamento.setCodCategoriaDebito(categoriaTributoDomain.getIdTributo());
						
						condizionePagamento.setDescrizioneCategoriaDebito(categoriaTributoDomain.getDeTrb());
						condizionePagamento.setNoteDebito(pendenzaDomain.getNote());
						condizionePagamento.setDataCreazioneDebito(pendenzaDomain.getTsCreazioneente());
						
						condizionePagamento.setCodFiscaleCreditore(enteDomain.getIntestatarioobj().getLaplIForm());
						
						condizionePagamento.setDataEmissioneDebito(pendenzaDomain.getTsEmissioneente());
						condizionePagamento.setImportoTotaleDebito(pendenzaDomain.getImTotale());
						
//						condizionePagamento.setDataAnnullamento(condizionePagamentoDomain.getTsAnnullamento());
						
//						condizionePagamento.setCodiceCip(condizionePagamentoDomain.getCoCip());
//						condizionePagamento.setTipoSpedizioneAvviso(condizionePagamentoDomain.getTipoSpedizioneAvviso());
						
						EnumTipoRateazione tipoRateazione = findByChiave(condizionePagamentoDomain.getTiPagamento(), EnumTipoRateazione.class);
						condizionePagamento.setTipoRateazionePagamento(tipoRateazione);
						
						
						final ArrayList<Pagamenti> pagamentiEseguiti = new ArrayList<Pagamenti>(condizionePagamentoDomain.getPagamenti());
						CollectionUtils.filter(pagamentiEseguiti, new Predicate() {
								@Override
								public boolean evaluate(Object object) {
										Pagamenti pagamenti = (Pagamenti) object;
										return pagamenti.getStPagamento().equals(EnumStatoPagamento.ESEGUITO.getChiave());
								}
						});
						
						condizionePagamento.setStatoPendenza(pendenzaDomain.getStPendenza());
						condizionePagamento.setDescrizioneTipoDebito(pendenzaDomain.getTributoEnte().getDeTrb());
						
						condizionePagamento.setTipoDebito(condizionePagamentoDomain.getCdTrbEnte());
						condizionePagamento.setCausaleDebito(pendenzaDomain.getDeCausale());
						condizionePagamento.setIdDebito(pendenzaDomain.getIdPendenzaente());
						
						condizionePagamento.setAnnoRiferimento(pendenzaDomain.getAnnoRiferimento() == 0 ? "" : String.valueOf(pendenzaDomain.getAnnoRiferimento()));
						condizionePagamento.setIdMittente(pendenzaDomain.getIdMittente());
						condizionePagamento.setDescrizioneMittente(pendenzaDomain.getDeMittente());
					
						final CfgTributoEntePlugin cfgTributoEntePlugin = pendenzaDomain.getTributoEnte().getCfgTributoEntePlugin();
						if (cfgTributoEntePlugin != null ) {
							condizionePagamento.setCdPlugin(cfgTributoEntePlugin.getCdPlugin());
						}
						
						condizionePagamento.setCdTributoEnte(pendenzaDomain.getTributoEnte().getCdTrbEnte());
					
					
						
						
						InformazioniPagamentoCondizione informazioniPagamentoCondizione = new InformazioniPagamentoCondizione();
						condizionePagamento.setInformazioniPagamentoCondizione(informazioniPagamentoCondizione);
						// stato pagamento nuovo BOE
						informazioniPagamentoCondizione.setStatoCondizioneCP(condizionePagamentoDomain.getStPagamento());
						Pagamenti p = BillItemInspector.getMainPayment(condizionePagamentoDomain);
						if (p != null)
							informazioniPagamentoCondizione.setStatoPagamentoCP(p.getStPagamento());
						
						it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione enumStatoPagamentoCondizione = PaymentConditionStatusCalculator.calculateStatus(condizionePagamentoDomain);
						
						
						// FIXME  override comportamento di calculate status, da valutare se cambiare il comportamento di PaymentConditionStatusCalculator.calculateStatus in modo che a fronte di un ESEGUITO_SBF ritorni stato = IN CORSO
						final boolean inCorso = isInCorso(condizionePagamentoDomain);
						if (inCorso) {
							enumStatoPagamentoCondizione = it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione.IN_CORSO;
						}
					
					
						// (!!!DA RIVEDERE!!!) converto da enum a enum "DIVERSE" (ma su package differenti) assumendo  che i valori si mantengano uguali
						final String statoPagamentoCondizionePaytas = enumStatoPagamentoCondizione.name();
						final it.tasgroup.idp.rs.enums.EnumStatoPagamentoCondizione statoPagamentoCondizione = it.tasgroup.idp.rs.enums.EnumStatoPagamentoCondizione.valueOf(statoPagamentoCondizionePaytas);
						informazioniPagamentoCondizione.setStatoPagamentoCondizione(statoPagamentoCondizione);
						
						
						// remap EnumStatoPagamentoCondizione to EnumStatoCondizione
						final EnumStatoCondizione enumStatoCondizione = statoPagamentoCondizione.asStatoCondizione();
						
						if (EnumStatoCondizione.RIMBORSATA.equals(enumStatoCondizione)) {
						//imposto pagata in quanto se è rimborsata PER FORZA prima è stata pagata (non funzionerebbe l'UPDATE STATUS a "R")
							if  ("R".equals(informazioniPagamentoCondizione.getStatoCondizioneCP())) {
								informazioniPagamentoCondizione.setStatoCondizione(EnumStatoCondizione.PAGATA);	
							}
						}
						else {
							informazioniPagamentoCondizione.setStatoCondizione(enumStatoCondizione);
						}
						
						informazioniPagamentoCondizione.setPagatoIdp(!pagamentiEseguiti.isEmpty());
						
						if (!pagamentiEseguiti.isEmpty()) {
								Pagamenti pagamentoEseguito = pagamentiEseguiti.iterator().next(); // (se size > 1) prendo sempre quello effettuato per primo
								
								final GestioneFlussi distintaDomain = pagamentoEseguito.getFlussoDistinta();
								
								CfgFornitoreGateway fornitore = distintaDomain.getCfgGatewayPagamento().getCfgFornitoreGateway();
								String descrizioneCircuito = fornitore != null ? fornitore.getDescrizione() : "";
								informazioniPagamentoCondizione.setDescrizioneCircuito(descrizioneCircuito);
								informazioniPagamentoCondizione.setBundleKeyCanalePagamento(fornitore != null ? fornitore.getBundleKey() : "");
								
								condizionePagamento.setDivisa(distintaDomain.getDivisa());
							
								condizionePagamento.setCodPagamento(distintaDomain.getCodPagamento());
								condizionePagamento.setUtenteCreatore(distintaDomain.getUtentecreatore());
							
								condizionePagamento.getInformazioniPagamentoCondizione().setIdentificativoFiscaleCreditore(distintaDomain.getIdentificativoFiscaleCreditore()); 
								condizionePagamento.getInformazioniPagamentoCondizione().setCodTransazionePSP(distintaDomain.getCodTransazionePSP()); 
								informazioniPagamentoCondizione.setDescrizioneCanalePagamento(distintaDomain.getCfgGatewayPagamento().getCfgFornitoreGateway().getDescrizione());

								informazioniPagamentoCondizione.setFlagPagamentoMultiplo(pagamentiEseguiti.size() > 1); // se dovesse accadere l'eventualitÃ  di pagamenti multipli (in teoria non dovrebbe accadere)
								
								informazioniPagamentoCondizione.setDataOraPagamento(pagamentoEseguito.getTsDecorrenza());
							
								informazioniPagamentoCondizione.setIdPagamento(pagamentoEseguito.getId());
								
								
								informazioniPagamentoCondizione.setNotePagamento(pagamentoEseguito.getNotePagamento());
								informazioniPagamentoCondizione.setIdentificativoUnivocoVersamento(pagamentoEseguito.getIdentificativoFlusso()); 
								informazioniPagamentoCondizione.setDescrizioneCanalePagamento(condizionePagamentoDomain.getDeCanalepag());
								
								informazioniPagamentoCondizione.setImportoPagato(pagamentoEseguito.getImPagato());
								
								informazioniPagamentoCondizione.setModalitaPagamento(pagamentoEseguito.getFlussoDistinta().getCfgGatewayPagamento().getCfgModalitaPagamento().getDescrizione());
								
								informazioniPagamentoCondizione.setEmailVersante(distintaDomain.getEmailVersante());
								informazioniPagamentoCondizione.setCodFiscaleVersante(distintaDomain.getUtentecreatore());
								
																
							
							// se e' (anche) INCASSATO...
							final EnumStatoIncasso enumStatoIncasso = findByChiave(pagamentoEseguito.getFlagIncasso(), EnumStatoIncasso.class);
							Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso = new Pagamento.InformazioniTransazioneIncasso();
							if (byChiave(pagamentoEseguito.getStPagamento(), EnumStatoPagamento.class) == EnumStatoPagamento.ESEGUITO &&
							(enumStatoIncasso == EnumStatoIncasso.RIVERSATO  /*enumStatoIncasso == EnumStatoIncasso.DIFFERITO*/)) {
								informazioniTransazioneIncasso = new Pagamento.InformazioniTransazioneIncasso(pagamentoEseguito.getMittRendicontazioneIncasso(),
								pagamentoEseguito.getDataAccreditoEnte(),
								pagamentoEseguito.getTRN(),
								pagamentoEseguito.getTotaleRendicontazioneIncasso(),
								pagamentoEseguito.getCodRendicontazioneIncasso(),
								pagamentoEseguito.getIdentificativoFlusso());
					
							} else if (enumStatoIncasso == EnumStatoIncasso.ATTESO) {
								informazioniTransazioneIncasso = new Pagamento.InformazioniTransazioneIncasso();
							}
							informazioniTransazioneIncasso.setFlagIncasso(enumStatoIncasso);
							informazioniTransazioneIncasso.setDescrizioneAttestante(distintaDomain.getDescrizioneAttestante());
							informazioniTransazioneIncasso.setTipoIdentificativoAttestatante(distintaDomain.getTipoIdentificativoAttestante());
							informazioniTransazioneIncasso.setIdentificativoAttestante(distintaDomain.getIdentificativoAttestante());
								
							condizionePagamento.setInformazioniTransazioneIncasso(informazioniTransazioneIncasso);

						}
						
						
						if (enumStatoCondizione==EnumStatoCondizione.PAGATA && !informazioniPagamentoCondizione.getPagatoIdp()) {
							informazioniPagamentoCondizione.setDataOraPagamento(condizionePagamentoDomain.getDtPagamento());
							informazioniPagamentoCondizione.setNotePagamento(condizionePagamentoDomain.getDeNotePagamento());
							informazioniPagamentoCondizione.setDescrizioneCanalePagamento(condizionePagamentoDomain.getDeCanalepag());
						}
						
						//se la condizione è rimborsata aggiungo i dati della condizione ( note della condizione e imposto gli allegati NdC
						if ("R".equals(informazioniPagamentoCondizione.getStatoCondizioneCP()))
						{
							informazioniPagamentoCondizione.setDataOraPagamento(condizionePagamentoDomain.getDtPagamento());
							informazioniPagamentoCondizione.setDescrizioneCanalePagamento(condizionePagamentoDomain.getDeCanalepag());
							
							String noteP= informazioniPagamentoCondizione.getNotePagamento() != null ? " "+informazioniPagamentoCondizione.getNotePagamento() : "";
							String noteC= condizionePagamentoDomain.getDeNotePagamento() != null ? condizionePagamentoDomain.getDeNotePagamento() : "";
							String note=noteC+noteP;
							informazioniPagamentoCondizione.setNotePagamento((note != null && !"".equals(note)) ? note : null);
							
							Set<AllegatiPendenza> allegati = condizionePagamentoDomain.getAllegatiPendenza();
							List<String> idAllegati = new ArrayList<String>();
							for (AllegatiPendenza allegato : allegati) {
								if (EnumTipoAllegato.NDC.getDescrizione().equals(allegato.getTiAllegato())) {
									idAllegati.add(allegato.getIdAllegato());
								}
							}
							condizionePagamento.setIdAllegati(idAllegati);
						}

						
						
/*
						if (condizionePagamentoDomain.getTipoCausalePagamento() != null)  {
								condizionePagamento.setCausaleStrutturata(new CausaleStrutturata(EnumTipoCausalePagamento.valueOf(condizionePagamentoDomain.getTipoCausalePagamento()),condizionePagamentoDomain.getCausalePagamento()));
						}
*/
						
						
						List<VocePagamento> vociPagamento = condizionePagamentoDomain.getVociPagamento();
						List<Voce> voci = new ArrayList<Voce>();
						for (VocePagamento vocePagamento : vociPagamento) {
								Voce voce = new Voce(vocePagamento.getTiVoce(), vocePagamento.getCoVoce(), vocePagamento.getDeVoce(), vocePagamento.getImVoce(), vocePagamento.getCoCapBilancio(), vocePagamento.getCoAccertamento());
								voci.add(voce);
						}
						condizionePagamento.setVoci(voci);
						
						
						condizionePagamentoList.add(condizionePagamento);
						
				}
				
				
				return condizionePagamentoList;
				
		}
	
	private boolean isInCorso(CondizionePagamento condizionePagamento) {
		
		// Segnalata pagata dall'ente
		if (!condizionePagamento.getStPagamento().equals("N") )
			return false;
		
		Set<Pagamenti> p = condizionePagamento.getPagamenti();
		String statoPagamento = null;
		for (Pagamenti pagamenti:p) {
			statoPagamento = pagamenti.getStPagamento();
			if (statoPagamento!=null) {
				if (statoPagamento.equals(StatiPagamentiIris.IN_CORSO.getPagaMapping()) || statoPagamento.equals(StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping())  ) {
					condizionePagamento.setIdDistintaInCorso(pagamenti.getFlussoDistinta().getId().toString());
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	@Override
    public List<Pagamento> getPagamentiCreditore(ContainerDTO inputDTO) {
        List<Pagamenti> listCondizioniCreditore = pagamentiDao.getPagamentiCreditore(inputDTO);
        List<Pagamento> pagamentoList = new ArrayList<Pagamento>();

        for (Pagamenti pagamentoDomain : listCondizioniCreditore) {
            CondizionePagamento condizionePagamentoDomain = pagamentoDomain.getCondPagamento();
            Pagamento pagamento = new Pagamento();

            pagamento.setId(pagamentoDomain.getId());

            it.tasgroup.idp.rs.model.creditore.CondizionePagamento condizionePagamento = new it.tasgroup.idp.rs.model.creditore.CondizionePagamento();
            pagamento.setCondizionePagamento(condizionePagamento);

            condizionePagamento.setIdCondizione(condizionePagamentoDomain.getIdCondizione());
            condizionePagamento.setDataInizioValidita(condizionePagamentoDomain.getDtIniziovalidita());
            condizionePagamento.setDataScadenza(condizionePagamentoDomain.getDtScadenza());
            condizionePagamento.setDataFineValidita(condizionePagamentoDomain.getDtFinevalidita());
            condizionePagamento.setImporto(condizionePagamentoDomain.getImPagamento());
            condizionePagamento.setCausalePagamento(condizionePagamentoDomain.getCausalePagamento());
            condizionePagamento.setImporto(condizionePagamentoDomain.getImTotale());
            condizionePagamento.setIdPagamento(condizionePagamentoDomain.getIdPagamento());

            final Pendenza pendenzaDomain = condizionePagamentoDomain.getPendenza();

            final Set<DestinatariPendenza> destinatari = pendenzaDomain.getDestinatari();

            List<Debitore> debitori = new ArrayList<Debitore>();
            condizionePagamento.setDebitori(debitori);

            for (DestinatariPendenza destinatario : destinatari) {
                Debitore debitore = new Debitore();
                debitore.setAnagrafica(destinatario.getDeDestinatario());
                debitore.setCodFiscale(destinatario.getCoDestinatario());


                // debitore.setTipoCodiceIdentificativoAlternativo(); // non presente
                // debitore.setCodiceIdentificativoAlternativo(destinatario.getIdDestinatario()) // non presente
                
                debitore.setIndirizzo(destinatario.getIndirizzoDestinatario());
                debitore.setAnagrafica(destinatario.getAnagraficaDestinatario());
                debitore.setCap(destinatario.getCapDestinatario());
                debitore.setLocalita(destinatario.getLocalitaDestinatario());
                debitore.setLuogoNascita(destinatario.getLuogoNascitaDestinatario());
                debitore.setEmail(destinatario.getEmailDestinatario());
                debitore.setDataNascita(destinatario.getDataNascitaDestinatario());
                debitore.setNumeroCivico(destinatario.getNumCivicoDestinatario());
                debitore.setNazione(destinatario.getNazioneDestinatario());
                debitore.setProvincia(destinatario.getProvinciaDestinatario());
                debitore.setTipoSoggetto(destinatario.getTipoSoggettoDestinatario());

                debitori.add(debitore);
            }

            condizionePagamento.setTipoDebito(pagamentoDomain.getTiDebito());
            final Enti enteDomain = condizionePagamentoDomain.getEnte();
            condizionePagamento.setCreditore(enteDomain.getCodiceEnte());
            condizionePagamento.setTipoDebito(pagamentoDomain.getCdTrbEnte());
            condizionePagamento.setDescrizioneCreditore(enteDomain.getDenominazione());

            final CategoriaTributo categoriaTributoDomain = pendenzaDomain.getCategoriaTributo();
            condizionePagamento.setCodCategoriaDebito(categoriaTributoDomain.getIdTributo());

            condizionePagamento.setDescrizioneCategoriaDebito(categoriaTributoDomain.getDeTrb());
            condizionePagamento.setNoteDebito(pendenzaDomain.getNote());
            condizionePagamento.setDataCreazioneDebito(pendenzaDomain.getTsCreazioneente());

            condizionePagamento.setCodFiscaleCreditore(enteDomain.getIntestatarioobj().getLaplIForm());

            condizionePagamento.setDataEmissioneDebito(pendenzaDomain.getTsEmissioneente());
            condizionePagamento.setImportoTotaleDebito(pendenzaDomain.getImTotale());

            condizionePagamento.setInformazioniPagamentoCondizione(condizionePagamento.getInformazioniPagamentoCondizione());


            EnumTipoRateazione tipoRateazione = findByChiave(condizionePagamentoDomain.getTiPagamento(), EnumTipoRateazione.class);
            condizionePagamento.setTipoRateazionePagamento(tipoRateazione);

            final GestioneFlussi distintaDomain = pagamentoDomain.getFlussoDistinta();
            pagamento.setCodPagamento(distintaDomain.getCodPagamento());

            Versante versante = new Versante();
            versante.setCodFiscale(distintaDomain.getUtentecreatore());
            versante.setEmail(distintaDomain.getEmailVersante());
            pagamento.setVersante(versante);

            condizionePagamento.setDivisa(distintaDomain.getDivisa());
            condizionePagamento.setDescrizioneTipoDebito(pendenzaDomain.getTributoEnte().getDeTrb());

            condizionePagamento.setCausaleDebito(pendenzaDomain.getDeCausale());
            condizionePagamento.setIdDebito(pendenzaDomain.getIdPendenzaente());

            condizionePagamento.setAnnoRiferimento(String.valueOf(pendenzaDomain.getAnnoRiferimento()));
            condizionePagamento.setIdMittente(pendenzaDomain.getIdMittente());
            condizionePagamento.setDescrizioneMittente(pendenzaDomain.getDeMittente());

            Pagamento.InformazioniTransazionePagamento informazioniTransazionePagamento = new Pagamento.InformazioniTransazionePagamento();

            informazioniTransazionePagamento.setDataOraTransazionePagamento(pagamentoDomain.getTsDecorrenza());
            informazioniTransazionePagamento.setIdentificativoUnivocoRiscossione(pagamentoDomain.getIdRiscossionePSP());

            List<GestioneFlussi> distinteStessoGruppo = getDistinteStessoGruppo(distintaDomain.getId());
            BigDecimal importoTransazionePagamento = BigDecimal.ZERO;
            BigDecimal importoCommissioniTransazionePagamento = BigDecimal.ZERO;
            Long numeroPagamentiTransazionePagamento = 0L;
            for (GestioneFlussi d : distinteStessoGruppo) {
                importoTransazionePagamento=importoTransazionePagamento.add(d.getTotimportipositivi());
                importoCommissioniTransazionePagamento=importoCommissioniTransazionePagamento.add(d.getImportoCommissioni());
                numeroPagamentiTransazionePagamento = numeroPagamentiTransazionePagamento + d.getNumeroDisposizioni();
            }
            CfgGatewayPagamento gatewayPagamento = distintaDomain.getCfgGatewayPagamento();
            informazioniTransazionePagamento.setDescrizionePsp(gatewayPagamento.getSystemName());
            informazioniTransazionePagamento.setIdentiticativoPsp(gatewayPagamento.getSystemId());
            informazioniTransazionePagamento.setImportoTransazionePagamento(importoTransazionePagamento);
            informazioniTransazionePagamento.setImportoCommissioniTransazionePagamento(importoCommissioniTransazionePagamento);
            informazioniTransazionePagamento.setNumeroPagamentiTransazionePagamento(numeroPagamentiTransazionePagamento);
            informazioniTransazionePagamento.setIdentificativoCanalePagamento(gatewayPagamento.getSystemId());
            informazioniTransazionePagamento.setIdentificativoCanalePagamento(gatewayPagamento.getApplicationId());
            informazioniTransazionePagamento.setTipoVersamento(findByChiave(gatewayPagamento.getTipoVersamento(), EnumTipoVersamento.class));
            informazioniTransazionePagamento.setModalitaPagamento(gatewayPagamento.getCfgModalitaPagamento().getDescrizione());

            // se Ã¨ incassato...
            if (byChiave(pagamentoDomain.getStPagamento(), EnumStatoPagamento.class) == EnumStatoPagamento.ESEGUITO && byChiave(pagamentoDomain.getFlagIncasso(), EnumStatoIncasso.class) == EnumStatoIncasso.RIVERSATO) {
                Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso = new Pagamento.InformazioniTransazioneIncasso(pagamentoDomain.getMittRendicontazioneIncasso(),
                        pagamentoDomain.getDataRegolamento(),
                        pagamentoDomain.getTRN(),
                        pagamentoDomain.getTotaleRendicontazioneIncasso(),
                        pagamentoDomain.getCodRendicontazioneIncasso(),
                        pagamentoDomain.getIdentificativoFlusso()
                );
                pagamento.setInformazioniTransazioneIncasso(informazioniTransazioneIncasso);
            }



            pagamento.setInformazioniTransazionePagamento(informazioniTransazionePagamento);

            List<VocePagamento> vociPagamento = condizionePagamentoDomain.getVociPagamento();
            List<Voce> voci = new ArrayList<Voce>();
            for (VocePagamento vocePagamento : vociPagamento) {
                Voce voce = new Voce(vocePagamento.getTiVoce(), vocePagamento.getCoVoce(), vocePagamento.getDeVoce(), vocePagamento.getImVoce(), vocePagamento.getCoCapBilancio(), vocePagamento.getCoAccertamento());
                voci.add(voce);
            }
            condizionePagamento.setVoci(voci);

            final String flagIncasso = pagamentoDomain.getFlagIncasso();
            final EnumStatoIncasso enumStatoIncasso = findByChiave(flagIncasso, EnumStatoIncasso.class);
            pagamento.setFlagIncasso(enumStatoIncasso);

            pagamento.setImportoPagato(pagamentoDomain.getImPagato());
            pagamento.setNotePagamento(pagamentoDomain.getNotePagamento());
            pagamento.setDataOraPagamento(pagamentoDomain.getTsInserimento());

            String stPagamento = pagamentoDomain.getStPagamento();
            EnumStatoPagamento enumStatoPagamento = findByChiave(stPagamento, EnumStatoPagamento.class);
            pagamento.setStatoPagamento(enumStatoPagamento);

            pagamento.setIdentificativoUnivocoVersamento(distintaDomain.getIuv());

            pagamento.setCodiceContestoPagamento(distintaDomain.getCodTransazionePSP());

            final String urlRicevuta = this.getUrlRicevuta(pagamentoDomain, distintaDomain.getCodPagamento(), pagamentoDomain.getCoPagante(), String.valueOf(pagamentoDomain.getId()));
            pagamento.setUrlDownloadRicevuta(urlRicevuta);
 
            pagamentoList.add(pagamento);

        }


        return pagamentoList;
    }

    @Override
    public String getUrlRicevuta(Pagamenti pagamento, String codPagamento, String codiceFiscale, String idFlusso) {

        // Ricevuta presente nel sistema quando eseguito e documento disponibile
        boolean ricevutaPresente = "ES".equals(pagamento.getStPagamento()) && pagamento.isAssociatedDocumentAvailable();

        String urlRicevuta = "";

        if (ricevutaPresente) {

            ConfigurationPropertyLoader cpl = ConfigurationPropertyLoader.getInstance("gateway-ws-client.properties");
            String baseUrlGateway = cpl.getProperty("iris.gateway.webapp.baseUrl");
            DownloadRicevutaParametersEncrypter dr = new DownloadRicevutaParametersEncrypter(codPagamento, codiceFiscale, idFlusso);
            String crParam = dr.encrypt();
            urlRicevuta = baseUrlGateway + "/documentiPagamento.do?method=downloadRicevuta&" + SharedConstants.CRYPTEDPARAMS + "=" + crParam;
        }

        return urlRicevuta;
    }

    @Override
    public StatistichePagamento getStatisticheCreditore(StatistichePagamentoFilter filter) {
        final StatistichePagamento statisticheCreditore = pagamentiDao.getStatisticheCreditore(filter);

        return statisticheCreditore;
    }

    @Override
    public Long aggiornaInformazioniTransazioneIncasso(Long idFisico, String iuvPagamento, String codiceContestoPagamento, Pagamento.InformazioniTransazioneIncasso informazioniTransazioneIncasso) {
        return pagamentiDao.aggiornaInformazioniTransazioneIncasso(idFisico,iuvPagamento,codiceContestoPagamento, informazioniTransazioneIncasso);
    }
    
    
    
    @Override
	public boolean annullaOperatoreByIdDistinta(Long idDistinta) {
		try {
		  boolean exit = distintaDao.annullaOperatoreByIdDistinta(idDistinta);
		  return exit;
		} catch (Throwable t) {
			return false;
		}		
	}

    @Override
    public DocumentoRepositoryDTO getRicevuteTelematicheContent(String iuv, String codiceTransazionePSP, String idFiscaleCreditore) {
    	GiornaleEventiDocumentiNDP gde = gdeDao.getGiornaleEventiDocumenti(codiceTransazionePSP, idFiscaleCreditore, iuv, "RT",null);
    	DocumentoRepositoryDTO docRepoDTO = new DocumentoRepositoryDTO();
    	if (gde != null) {
    		docRepoDTO.setContent(gde.getDocumento());
    		docRepoDTO.setSize(gde.getDimensione());
    		// il nome file: preso da AgidRTNotificationBuilder ,stessa nomenclatura
    		// al posto di creare il nuovo timestamp come nome file utilizzo quello di inserimento della GDE_DOCUMENTI_NDP
    		String twelveTimestamp = new SimpleDateFormat("ddMMyyHHmmss").format(gde.getTsInserimento());
    		docRepoDTO.setFileName(iuv + twelveTimestamp);
    		docRepoDTO.setFileExtension("xml");
    		docRepoDTO.setId(gde.getId());
    	}
    	return docRepoDTO;
    }
    
    protected String getOpInserimento() {
    	return OP_INSERT_NAME;
    }
    
    protected String getOpAggiornamento() {
    	return OP_UPDATE_NAME;
    }
    
    protected String getZipFileName() {
    	return  ZIP_NAME_PREFIX;
    }
    
    protected String getTipoEsportazioni() {
    	return EnumTipoExport.RICEVUTE_TELEMATICHE.getChiave();
    }

	@Override
	public EsitoNDP notificaPagamento(RichiestaNotificaPagamento request) {
		
		return pagamentiDao.notificaPagamento(request);
	}

}
