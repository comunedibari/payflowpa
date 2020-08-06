package it.tasgroup.iris.business.ejb.pagamenti;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrTokenizer;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.idp.posizionedebitoria.PosizioneDebitoriaHelper;
import it.nch.is.fo.BackEndMessage;
import it.nch.is.fo.profilo.Enti;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.is.fo.stati.pagamenti.data.AutorizzaPagamentoResponse;
import it.nch.is.fo.stati.pagamenti.data.FaultType;
import it.nch.is.fo.tributi.CfgTributoEntePlugin;
import it.nch.is.fo.tributi.TributoEnte;
import it.nch.is.fo.util.plugin.PluginReceiptUtil;
import it.nch.utility.GeneratoreIdUnivoci;
import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.business.ejb.client.confpagamenti.ConfPagamentiBusinessLocal;
import it.tasgroup.iris.business.ejb.client.ddp.DDPBusinessLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AnnullamentoPagamentoServiceLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneLocal;
import it.tasgroup.iris.business.ejb.client.pagamenti.AutorizzazioneRemote;
import it.tasgroup.iris.business.ejb.client.pagamenti.CommonPaymentBusinessLocal;
import it.tasgroup.iris.business.ejb.pagamenti.dto.builder.PAIBusinessDTOBuilder;
import it.tasgroup.iris.domain.AllegatiPendenza;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CondizioneDocumento;
import it.tasgroup.iris.domain.CondizionePagamento;
import it.tasgroup.iris.domain.DestinatariPendenza;
import it.tasgroup.iris.domain.DocumentoDiPagamento;
import it.tasgroup.iris.domain.GestioneFlussi;
import it.tasgroup.iris.domain.Pagamenti;
import it.tasgroup.iris.domain.PagamentiOnline;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.domain.helper.BillInspector;
import it.tasgroup.iris.domain.helper.BillItemInspector;
import it.tasgroup.iris.domain.helper.CommissioniCalculator;
import it.tasgroup.iris.domain.helper.PaymentConditionStatusCalculator;
import it.tasgroup.iris.dto.CondizionePagamentoDTO;
import it.tasgroup.iris.dto.EsitoCondizioneDTO;
import it.tasgroup.iris.dto.EsitoOperazionePagamentoDTO;
import it.tasgroup.iris.dto.IdentificativoUnivocoVersamentoDTO;
import it.tasgroup.iris.dto.PendenzaDTO;
import it.tasgroup.iris.dto.RichiestaAUPPerListaCodiciDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneCBILLDTO;
import it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO;
import it.tasgroup.iris.persistence.dao.interfaces.AllegatiPendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.CondizioniPagamentoDao;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.iris.persistence.dao.interfaces.GestioneFlussiDao;
import it.tasgroup.iris.persistence.dao.interfaces.PendenzaDao;
import it.tasgroup.iris.persistence.dao.interfaces.TributoEnteDao;
import it.tasgroup.iris.persistence.dao.util.DDP_IDGenerator;
import it.tasgroup.iris.shared.util.UtilBigDecimal;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;
import it.tasgroup.services.util.enumeration.EnumAutorizzazionePagamentoReturnCode;
import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;
import it.tasgroup.services.util.enumeration.EnumOperazioniPagamento;
import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoStrumentoDiPagamento;
import it.tasgroup.services.util.idgenerator.IDGenerator;

@Stateless(name = "AutorizzazioneBusiness")
public class AutorizzazioneBean implements AutorizzazioneLocal, AutorizzazioneRemote {

	private static final long serialVersionUID = 1L;

	final static String VERBALE_IDX = "VERBALE";
	final static String ANNO_IDX = "ANNO";
	final static String SERIE_IDX = "SERIE";
	final static String DATA_IDX = "DATA";
	final static String TARGA_IDX = "TARGA";

	final static String IDENTIFICATORE_MULTIPLO="*";
	final static String ENTI_MULTIPLI = "PAGAMENTO MULTI-ENTE";
	final static String TRIBUTI_MULTIPLI = "PAGAMENTO MULTI-TRIBUTO";
	final static String CAUSALI_MULTIPLE = "" ;
	final static String AUTORIZZATO = "Autorizzato" ;
	final static String NON_AUTORIZZATO = "Non Autorizzato" ;
	
	final static String CF_CREDITORE_COMUNE_PRATO="84006890481";
	
	protected static final String DEFAULT_OPERATOR = "IRIS";
	
	@EJB(name = "CondizioniPagamentoDaoService")
	private CondizioniPagamentoDao condPagDao;

	@EJB(name = "GestioneFlussiDaoService")
	private GestioneFlussiDao gestioneFlussiDao;

	@EJB(name = "TributoEnteDaoService")
	TributoEnteDao tributoEnteDao;

	@EJB(name = "ConfPagamentiBusiness")
	private ConfPagamentiBusinessLocal confPagamentoBusinessBean;
	
	@EJB(name = "EntiDaoService")
	private EntiDao entiDao;
	
	@EJB(name = "AnnullamentoBusiness")
	private AnnullamentoPagamentoServiceLocal annullaPagamentoBean;
	
	@EJB(name = "CommonPaymentBusiness")
	private CommonPaymentBusinessLocal commonPaymentBusinessBean;

	@EJB(name = "PendenzaDaoService")
	private PendenzaDao pendenzaDao;
	
	@EJB(name = "AllegatiPendenzaDao")
	private AllegatiPendenzaDao allegatiPendenzaDao;

	@EJB(name = "CfgGatewayPagamentoDaoService")
	private CfgGatewayPagamentoDao cfgGatewayPagamentoDao;
	
	@EJB
	private DDPBusinessLocal ddpBusinessBean;

	private static final Logger LOGGER = LogManager.getLogger(AutorizzazioneBean.class);
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.pagamenti.Autorizzazione#autorizzaPagamento(it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO)
	 * 
	 * 
	 */
	public EsitoOperazionePagamentoDTO autorizzaPagamento(RichiestaAutorizzazioneDTO autorizzazioneDto) {	
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		
		try{
			
			dtoOut = autorizzaCarrello(autorizzazioneDto, dtoOut);	
			
			dtoOut = autorizzaMulta(autorizzazioneDto, dtoOut);
			
			dtoOut = autorizzaCodicePagamentoIris(autorizzazioneDto, dtoOut);
			
			return dtoOut;
		
		} catch(EJBTransactionRolledbackException dex){
				
			Throwable cause = dex.getCause();
							
			LOGGER.error("AutorizzazioneBean - autorizzaPagamento EJBTransactionRolledbackException ", dex);
				
			LOGGER.error("AutorizzazioneBean - autorizzaPagamento EJBTransactionRolledbackException.cause ", dex.getCause());
						
			if (cause instanceof DAORuntimeException)							
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			else 				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} catch (Exception e) {
						
			LOGGER.error("AutorizzazioneBean - autorizzaPagamento exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} finally {
			
			commonPaymentBusinessBean.manageTermination(autorizzazioneDto.getTestata(), dtoOut, null, EnumOperazioniPagamento.AUTORIZZAZIONE, DEFAULT_OPERATOR, autorizzazioneDto.getCodicePagamentoIris());		
			
		}
			
	}
	
	public EsitoOperazionePagamentoDTO autorizzaPagamentoCBILL(RichiestaAutorizzazioneDTO autorizzazioneDto) {	
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		
		try{

			dtoOut = autorizzaCBILL(autorizzazioneDto, dtoOut);
			
			return dtoOut;
		
		} catch(EJBTransactionRolledbackException dex){
				
			Throwable cause = dex.getCause();
							
			LOGGER.error("AutorizzazioneBean - autorizzaPagamentoCBILL EJBTransactionRolledbackException ", dex);
				
			LOGGER.error("AutorizzazioneBean - autorizzaPagamentoCBILL EJBTransactionRolledbackException.cause ", dex.getCause());
						
			if (cause instanceof DAORuntimeException)							
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			else 				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} catch (Exception e) {
						
			LOGGER.error("AutorizzazioneBean - autorizzaPagamentoCBILL exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} finally {
			
			commonPaymentBusinessBean.manageTermination(autorizzazioneDto.getTestata(), dtoOut, null, EnumOperazioniPagamento.AUTORIZZAZIONE, DEFAULT_OPERATOR, autorizzazioneDto.getCodicePagamentoIris());		
			
		}
			
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.pagamenti.Autorizzazione#autorizzaPagamentoPUNTOSI(it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO)
	 */
	public EsitoOperazionePagamentoDTO autorizzaPagamentoPAI(RichiestaAutorizzazioneDTO autorizzazioneDto) {	
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		
		try{
				 	
			dtoOut = autorizzaPerListaCodici(autorizzazioneDto, dtoOut);
			
			dtoOut = autorizzaCodicePagamentoIris(autorizzazioneDto, dtoOut);
			
			return dtoOut;
		
		} catch(EJBTransactionRolledbackException dex){
				
			Throwable cause = dex.getCause();
							
			LOGGER.error("AutorizzazioneBean - autorizzaPagamento EJBTransactionRolledbackException ", dex);
				
			LOGGER.error("AutorizzazioneBean - autorizzaPagamento EJBTransactionRolledbackException.cause ", dex.getCause());
						
			if (cause instanceof DAORuntimeException)							
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			else 				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} catch (Exception e) {
						
			LOGGER.error("AutorizzazioneBean - autorizzaPagamento exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} finally {

          if (LOGGER.isInfoEnabled())
				LOGGER.info(" calling: commonPaymentBusinessBean.manageTermination - alle" + new Timestamp(new Date().getTime()).toString());

			if (dtoOut.isError()) {
				
				// In questo punto vengono caricati i dati da loggare nei campi:'cod_autorizzazione' , 'de_operazione'  in modo da facilitare 
				// la diagnosi del problema avvenuto.
				
				String deOperazione = "";
				int idPagamentiTrovati = 0;
				String codAut = "";
				
				if (autorizzazioneDto.getListaCodici()!=null) {
					
					// Autorizzazione per lista codici
								
				    
					
					for (RichiestaAUPPerListaCodiciDTO codiceAutorizzazione: autorizzazioneDto.getListaCodici())   {
						for ( IdentificativoUnivocoVersamentoDTO idIUV : codiceAutorizzazione.getIdVersamenti()) {
							deOperazione+="[ CREDITORE="+codiceAutorizzazione.getCodiceFiscaleCreditore()+";";
							deOperazione+=" TIPO ID="+idIUV.getTipoIdDebitoCreditore()+";";
							deOperazione+=" ID PAGAMENTO="+idIUV.getIdPagamentoCreditore()+";";
							deOperazione+= " ]";
							idPagamentiTrovati=idPagamentiTrovati+1;
						}
					}
	
					if (dtoOut.getEsitiCondizioni()!=null) {
						deOperazione+=" ESITO = [";
						for (EsitoCondizioneDTO esitoCondizioneDTO: dtoOut.getEsitiCondizioni()) {
							deOperazione+=esitoCondizioneDTO.getReturnCode()+";";
						}
						deOperazione+=" ]";
					}
					
					// Comunque vada, su codAut riporto il codice del PRIMO idPagamento della lista.
					codAut = autorizzazioneDto.getListaCodici().get(0).getIdVersamenti().get(0).getIdPagamentoCreditore();
					
					
				} else if (autorizzazioneDto.getCodicePagamentoIris()!=null) {
					
					codAut = "DOC ID: "+autorizzazioneDto.getCodicePagamentoIris();
					
				}
					
				if (deOperazione.length()>1000) {
					deOperazione=deOperazione.substring(0, 995)+"...";
				}
				
				commonPaymentBusinessBean.manageTermination(autorizzazioneDto.getTestata(), dtoOut, deOperazione, EnumOperazioniPagamento.AUTORIZZAZIONE, DEFAULT_OPERATOR, codAut);		

			}
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" called: commonPaymentBusinessBean.manageTermination - alle" + new Timestamp(new Date().getTime()).toString());
			
			}
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.pagamenti.Autorizzazione#verificaPagamentoPSP(it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO)
	 */
	public EsitoOperazionePagamentoDTO verificaPagamentoPSP(RichiestaAutorizzazioneDTO autorizzazioneDto) {	
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		dtoOut.setIdDominio(autorizzazioneDto.getTestata().getIdFiscaleCreditore());
		try{
			
			dtoOut = verificaPSP(autorizzazioneDto, dtoOut);
			
			return dtoOut;
		
		} catch(EJBTransactionRolledbackException dex){
				
			Throwable cause = dex.getCause();
							
			LOGGER.error("AutorizzazioneBean - verificaPagamentoPSP EJBTransactionRolledbackException ", dex);
				
			LOGGER.error("AutorizzazioneBean - verificaPagamentoPSP EJBTransactionRolledbackException.cause ", dex.getCause());
						
			if (cause instanceof DAORuntimeException)							
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			else 				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} catch (Exception e) {
						
			LOGGER.error("AutorizzazioneBean - verificaPagamentoPSP exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} finally {
			
			commonPaymentBusinessBean.manageTermination(autorizzazioneDto.getTestata(), dtoOut, null, EnumOperazioniPagamento.AUTORIZZAZIONE, DEFAULT_OPERATOR, autorizzazioneDto.getCodicePagamentoIris());		
			
		}
			
	}
	
	/* (non-Javadoc)
	 * @see it.tasgroup.iris.business.ejb.client.pagamenti.Autorizzazione#attivaPagamentoPSP(it.tasgroup.iris.dto.RichiestaAutorizzazioneDTO)
	 */
	public EsitoOperazionePagamentoDTO attivaPagamentoPSP(RichiestaAutorizzazioneDTO autorizzazioneDto) {	
		
		EsitoOperazionePagamentoDTO dtoOut = new EsitoOperazionePagamentoDTO();
		
		dtoOut.setIdDominio(autorizzazioneDto.getTestata().getIdFiscaleCreditore());
		
		try{
			
			dtoOut = attivaPSP(autorizzazioneDto, dtoOut);
			
			return dtoOut;
		
		} catch(EJBTransactionRolledbackException dex){
				
			Throwable cause = dex.getCause();
							
			LOGGER.error("AutorizzazioneBean - attivaPagamentoPSP EJBTransactionRolledbackException ", dex);
				
			LOGGER.error("AutorizzazioneBean - attivaPagamentoPSP EJBTransactionRolledbackException.cause ", dex.getCause());
						
			if (cause instanceof DAORuntimeException)							
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			else 				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} catch (Exception e) {
						
			LOGGER.error("AutorizzazioneBean - attivaPagamentoPSP exception", e);
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
			
			return dtoOut;
			
		} finally {
			
			commonPaymentBusinessBean.manageTermination(autorizzazioneDto.getTestata(), dtoOut, null, EnumOperazioniPagamento.AUTORIZZAZIONE, DEFAULT_OPERATOR, autorizzazioneDto.getCodicePagamentoIris());		
			
		}
			
	}
	
	/**
	 * @param autorizzazioneDto
	 * @param dtoOut
	 * @return
	 * @throws Exception
	 */
	private EsitoOperazionePagamentoDTO attivaPSP(RichiestaAutorizzazioneDTO autorizzazioneDto, EsitoOperazionePagamentoDTO dtoOut) throws Exception {
		
		GestioneFlussi gestioneFlussi = new GestioneFlussi();
		
		Timestamp now = new Timestamp(new Date().getTime());	
		
		//autorizzazioneDto.setExtCodPagamento(autorizzazioneDto.getIuv());// forzo il valore del pagamento uguale allo IUV
		//autorizzazioneDto.setExtTransactionId(autorizzazioneDto.getIuv()); // forzo il valore del pagamento uguale allo IUV
		dtoOut.setMsgId(autorizzazioneDto.getCodTransazionePSP());
		// controllo se mi viene passato il carrello
		if (!autorizzazioneDto.getListaCodici().isEmpty()) {
			
			String logMsg = "Servizio attiva pagamento PSP - richiesta autorizzazione per i codici : "+ autorizzazioneDto.getListaCodici();
			
			dtoOut.setLogMessage(logMsg);
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(logMsg);
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
						
			dtoOut = getAndCheckListaCondizioni(autorizzazioneDto, dtoOut, //
					true, // refs #3752 ri-attivato controllo di pagabilita' (stesso della verifica)
					"A");			
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			// se le condizioni sono tutte pagabili le autorizzo
			dtoOut = autorizzaPagamentoCondizioniNDP(autorizzazioneDto, dtoOut, gestioneFlussi, now);
			
			if (dtoOut.getReturnCode().equals(EnumBusinessReturnCodes.OK)) 
			
					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK_AUTORIZZATO);
			
			return dtoOut;		
			
		} // FINE CARRELLO
		
		return dtoOut;
		
	}

	/**
	 * @param autorizzazioneDto
	 * @param dtoOut
	 * @return
	 * @throws Exception
	 */
	private EsitoOperazionePagamentoDTO verificaPSP(RichiestaAutorizzazioneDTO autorizzazioneDto,
			EsitoOperazionePagamentoDTO dtoOut) throws Exception {
		
		dtoOut.setMsgId(autorizzazioneDto.getCodTransazionePSP());
		// controllo se mi viene passato il carrello
		if (!autorizzazioneDto.getListaCodici().isEmpty()) {
			
			String logMsg = "Servizio verifica pagamento PSP - verifica autorizzazione per i codici : "+ autorizzazioneDto.getListaCodici();
			
			dtoOut.setLogMessage(logMsg);
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(logMsg);
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			dtoOut = getAndCheckListaCondizioni(autorizzazioneDto, dtoOut, true,"V");
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			
			formatCausalePSP(dtoOut); 
			
			return dtoOut;		
			
		} // FINE CARRELLO
		
		return dtoOut;
		
	}

	/**
	 * @param autorizzazioneDto
	 * @param dtoOut
	 * @param modPagamento 
	 * @return
	 * @throws Exception 
	 */
	private EsitoOperazionePagamentoDTO autorizzaCBILL(RichiestaAutorizzazioneDTO autorizzazioneDto, EsitoOperazionePagamentoDTO dtoOut) throws Exception {
		
		// controllo se mi viene passata una richiesta CBILL
		if (autorizzazioneDto.getRichiestaCBILLDTO() != null) {
			
			
			
			String codRicPag = autorizzazioneDto.getRichiestaCBILLDTO().getCodiceRicercaPagamento();
			//BEGIN 
			//  ANDREA LIGUORO: check della lunghezza del codice codiceRicercaPagamento. se > 35 skippo ricerca e
			// ritorno esito = NOT_FOUND
			if (codRicPag!=null && codRicPag.length()> 35) {
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000001);
				return dtoOut;
			}
			//END 
			autorizzazioneDto.setCodicePagamentoIris(codRicPag);
			
			dtoOut.setLogMessage("Servizio autorizzazione pagamento - richiesta autorizzazione per la condizione: " + codRicPag);
			
			GestioneFlussi gestioneFlussi = new GestioneFlussi();
			
			Timestamp now = new Timestamp(new Date().getTime());	
			
			List<CondizionePagamento> listaCondizioni = null;
						
			try{								
					if (LOGGER.isInfoEnabled())
						LOGGER.info("Servizio autorizzazione pagamento - richiesta autorizzazione per la condizione : "+ codRicPag);
					
					dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
					
					if (dtoOut.getReturnCode().isError())
														return dtoOut;
					
					dtoOut = preProcessingCBILL(autorizzazioneDto,dtoOut);
					
					if (dtoOut.getReturnCode().isError())
						return dtoOut;
					
					do{
		
						listaCondizioni = estraiListaCondizioni(autorizzazioneDto);
						
						dtoOut.setCondizioni(listaCondizioni);
						
						dtoOut = checkListaCondizioniCBILL(dtoOut,autorizzazioneDto);
										
						// Controllo che le pendenze/condizioni passate siano coerenti
						// TODO PAZZIK Verificare se serve anche per CBILL
						//dtoOut = isPendenzeCoerenti(dtoOut,autorizzazioneDto);
						if (dtoOut.getReturnCode().isError())
														return dtoOut;
						
						dtoOut = checkPaymentInProgress(dtoOut, listaCondizioni, autorizzazioneDto);
					
					} while (dtoOut.getReturnCode().equals(EnumBusinessReturnCodes.A0000011));
					
					if (!dtoOut.getReturnCode().equals(EnumBusinessReturnCodes.A0000005)){
					
						if (dtoOut.getReturnCode().isError())
														return dtoOut;
						
						dtoOut = checkCondizioniPagabili(dtoOut, listaCondizioni);
											
						if (dtoOut.getReturnCode().isError())
														return dtoOut;
						
						// se le condizioni sono tutte pagabili le autorizzo
						dtoOut = autorizzaPagamentoCondizioni(autorizzazioneDto, dtoOut, gestioneFlussi, now);
						
						if (dtoOut.getCodice() != null)
														return dtoOut;
					}
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
					
					return dtoOut;
				
			
			} catch(Exception t){
				
				// Tanto per fare qualcosa, l'importante ï¿½ eseguire il finally
				t.printStackTrace();
				
				throw t;
			
			} finally {

				dtoOut = getEsitoOperazione(dtoOut, gestioneFlussi, listaCondizioni, codRicPag);
				
			}
			
		} // FINE AUTORIZZAZIONE CBILL
		
		return dtoOut;
		
	}

	private EsitoOperazionePagamentoDTO autorizzaCodicePagamentoIris(RichiestaAutorizzazioneDTO autorizzazioneDto, EsitoOperazionePagamentoDTO dtoOut) throws Exception {
		
		PagamentiOnline pagamentoOnline;
		
		Set<Pagamenti> setPagamenti;
		
		String codiceFiscaleDestinatario = "non trovato";
		
		GestioneFlussi gestioneFlussi = new GestioneFlussi();
		
		Timestamp now = new Timestamp(new Date().getTime());
		
		// GIRO CLASSICO CON Codice Pagamento Iris
		
		String codPagamentoIris = autorizzazioneDto.getCodicePagamentoIris();
		
		if (!StringUtils.isEmpty(codPagamentoIris)) {
		
			dtoOut.setLogMessage("Servizio autorizzazione pagamento - richiesta autorizzazione per il doc: " + codPagamentoIris);
			
			if (LOGGER.isInfoEnabled()) 
				LOGGER.info("Servizio autorizzazione pagamento - richiesta autorizzazione per il doc: " + codPagamentoIris);
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" calling: commonPaymentBusinessBean.controlliTestata - alle" + new Timestamp(new Date().getTime()).toString());
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" called: commonPaymentBusinessBean.controlliTestata - alle" + new Timestamp(new Date().getTime()).toString());
			
			if (dtoOut.getReturnCode().isError()) 
											return dtoOut;
			
			DocumentoDiPagamento dp = null;
			
			try {
				if (LOGGER.isInfoEnabled())
					LOGGER.info(" calling: readSingleDDP_BKOF - alle" + new Timestamp(new Date().getTime()).toString());
				
				dp = ddpBusinessBean.readSingleDDP_BKOF(codPagamentoIris);
				
				if (LOGGER.isInfoEnabled())
					LOGGER.info(" called: readSingleDDP_BKOF - alle" + new Timestamp(new Date().getTime()).toString());
				
				
			} catch(Exception e){
				
				LOGGER.error(e);
				
				// in caso di eccezione si prosegue con dp = null
			}
			
			if (dp != null) {
				
				// controllo se il documento ï¿½ stato annullato
				
				// //////////////////////////
				// Verifica importo distinta
				// //////////////////////////
				if (autorizzazioneDto.getTotalAmount() != null) {
				
					if (autorizzazioneDto.getTotalAmount().compareTo(dp.getImporto()) != 0){
						
						dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000020);
						
						return dtoOut;
					}
						
				}
				
				if (dp.getStato().equals(EnumStatoDDP.ANNULLATO_SISTEMA.getChiave())) {
					// TODO: Recuperare la descrizione dal bundle
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000008);
					
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione per il doc:" + codPagamentoIris);
						LOGGER.info("CODICE: " + dtoOut.getCodice());
						LOGGER.info("CODICE: " + dtoOut.getDescrizione());
					}
					return dtoOut;
				}
				if (dp.getStato().equals(EnumStatoDDP.ANNULLATO_UTENTE.getChiave())) {
					// TODO: Recuperare la descrizione dal bundle
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000008);
					
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione per il doc: " + codPagamentoIris);
						LOGGER.info("CODICE: " + dtoOut.getCodice());
						LOGGER.info("CODICE: " + dtoOut.getDescrizione());
					}
					return dtoOut;
				}

				Set<CondizioneDocumento> setCondizioniDelDocumento = dp.getCondizioni();
				// se non ci sono condizioni associate al documento di pagamento
				// ritorno un errore
				if (setCondizioniDelDocumento.size() == 0) {
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000015);
					
					// DATI INCONSISTENTI NON CI SONO CONDIZIONI ASSOCIATE AL DOC
					// DI PAGAMENTO
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione per il doc: " + codPagamentoIris);
						LOGGER.info("CODICE: " + dtoOut.getCodice());
						LOGGER.info("CODICE: " + dtoOut.getDescrizione() + dp.getId());
					}
					return dtoOut;
				}
				
				List<CondizionePagamento> listaCondizioni = new ArrayList<CondizionePagamento>();
				// controllo che le condizioni associate al pagamento siano
				// pagabili
				String idCondizionePagamento = "";
				String idPagamentoEnte = "";
				
				// Pagamento per conto terzi: utilizzo sempre il codice fiscale del pagante
				// impostato nel documento di pagamento
				//----------------------------------------------------------------------------
				codiceFiscaleDestinatario = dp.getCodFiscalePagante();
				
				for (CondizioneDocumento condizioneDocumento : setCondizioniDelDocumento) {

					listaCondizioni.add(condizioneDocumento.getCondizionePagamento());
					CondizionePagamento cond = condizioneDocumento.getCondizionePagamento();
					idCondizionePagamento = cond.getIdCondizione();
					idPagamentoEnte= cond.getIdPagamento();
					Set<DestinatariPendenza> destPend = cond.getPendenza().getDestinatari();
										
					if (codiceFiscaleDestinatario==null || codiceFiscaleDestinatario.trim().equals("")) {
						// Per retrocompatibilitï¿½ su eventuali documenti vecchi senza co_pagante ma ancora validi al momento dell'installazione.
						for (DestinatariPendenza destinatariPendenza : destPend) 
							
							codiceFiscaleDestinatario = destinatariPendenza.getCoDestinatario();
						
					}	
					
				}
				
				String opInserimento=codiceFiscaleDestinatario;
				if ("ANONYMOUS".equalsIgnoreCase(dp.getIntestatario())) 
					opInserimento="ANONYMOUS";
				
									
				dtoOut = this.checkCondizioniPagabili(dtoOut, listaCondizioni);
				
				if (dtoOut.getReturnCode().isError()) 
												return dtoOut;
				else {
					// 1. inserimento distinta di pagamento nella tabella
					// GESTIONEFLUSSI
					gestioneFlussi = new GestioneFlussi();
					BigDecimal totImportiPostitivi = getTotImporti(listaCondizioni);

					gestioneFlussi = popolaFlusso(autorizzazioneDto, now, codiceFiscaleDestinatario, opInserimento, totImportiPostitivi,EnumTipoStrumentoDiPagamento.DDP);
					
					if (EnumTipoDDP.GDO.equals(dp.getTipoDocumentoEnum())) {
						gestioneFlussi.setCodPagamento(DDP_IDGenerator.extractCodPagamentoFromGDOIdentifier(codPagamentoIris));
					} 
					else if (EnumTipoDDP.ATM.equals(dp.getTipoDocumentoEnum())) {
							gestioneFlussi.setCodPagamento(codPagamentoIris);
						}

					// vado a ciclare le condizioni
					Set<PagamentiOnline> setPagamentiOnline = new HashSet<PagamentiOnline>();
					setPagamenti = new HashSet<Pagamenti>();
					pagamentoOnline = new PagamentiOnline();
					pagamentoOnline = PaymentEntityBuilder.popolaPagamentoOnline(autorizzazioneDto.getTestata(), autorizzazioneDto.getCodicePagamentoIris(), gestioneFlussi, null, EnumOperazioniPagamento.AUTORIZZAZIONE, dtoOut.getReturnCode(), codiceFiscaleDestinatario);
					Set<Pagamenti> pagamenti = PaymentEntityBuilder.popolaPagamenti(listaCondizioni, gestioneFlussi);

					dtoOut.setMsgId(gestioneFlussi.getCodTransazione());
					dtoOut.setCodPagamentoFlusso(gestioneFlussi.getCodPagamento());
					
					CondizionePagamento primaCondizionePagamento=listaCondizioni.get(0);
					String idPendenzaEnte=primaCondizionePagamento.getPendenza().getIdPendenzaente();
					String idEnte=primaCondizionePagamento.getEnte().getIdEnte();
					String cdTrbEnte=primaCondizionePagamento.getCdTrbEnte();
					Date dataScadenzaPagamento=primaCondizionePagamento.getDtScadenza();
					String descrizioneEnte=primaCondizionePagamento.getEnte().getIntestatarioobj().getRagionesocialeIForm();
					String tipoTributo=primaCondizionePagamento.getPendenza().getCategoriaTributo().getDeTrb();
					String descrizioneTributo=primaCondizionePagamento.getPendenza().getTributoEnte().getDeTrb();
					idCondizionePagamento = primaCondizionePagamento.getIdCondizione();
					idPagamentoEnte = primaCondizionePagamento.getIdPagamento();

					boolean isPagamentoMultiCondizione=listaCondizioni.size()>1;
					
					if (isPagamentoMultiCondizione) {
						
						for( int i=1;i<listaCondizioni.size();i++ ) {
							
							CondizionePagamento condizionePagamento = listaCondizioni.get(i);
							String nIdPendenzaEnte=condizionePagamento.getPendenza().getIdPendenzaente();
							String nIdEnte=condizionePagamento.getEnte().getIdEnte();
							String nCdTrbEnte=condizionePagamento.getCdTrbEnte();
							Date nDataScadenzaPagamento=condizionePagamento.getDtScadenza();
							String nDescrizioneEnte=condizionePagamento.getEnte().getIntestatarioobj().getRagionesocialeIForm();
							String nTipoTributo=condizionePagamento.getPendenza().getCategoriaTributo().getDeTrb();
							String nDescrizioneTributo=condizionePagamento.getPendenza().getTributoEnte().getDeTrb();
							String nIdCondizionePagamento = condizionePagamento.getIdCondizione();
							String nIdPagamentoEnte = condizionePagamento.getIdPagamento();

							
							// Prendo la data scadenza minore (assert = la data scadenza c'ï¿½ sempre sulle condizioni)
							if (dataScadenzaPagamento.after(nDataScadenzaPagamento))
								dataScadenzaPagamento = nDataScadenzaPagamento;
							
							// In caso di disomogeneitï¿½ dei seguenti campi assumo dei valori di default.
							
							if (!idPendenzaEnte.equals(nIdPendenzaEnte))
								idPendenzaEnte = IDENTIFICATORE_MULTIPLO;
							
							if (!idEnte.equals(nIdEnte))
								idEnte = IDENTIFICATORE_MULTIPLO;
							
							if (!cdTrbEnte.equals(nCdTrbEnte)) 
								cdTrbEnte = IDENTIFICATORE_MULTIPLO;
							
							if (!idCondizionePagamento.equals(nIdCondizionePagamento))
								idCondizionePagamento = IDENTIFICATORE_MULTIPLO;
							
							if (!idPagamentoEnte.equals(nIdPagamentoEnte))
								idPagamentoEnte = IDENTIFICATORE_MULTIPLO;
													
							if (!descrizioneEnte.equals(nDescrizioneEnte))
								descrizioneEnte = ENTI_MULTIPLI;
		
							if (!tipoTributo.equals(nTipoTributo))
								tipoTributo = TRIBUTI_MULTIPLI;
						
							if (!descrizioneTributo.equals(nDescrizioneTributo))
								descrizioneTributo = TRIBUTI_MULTIPLI;
			
						}

						// Se i tributi sono omogenei comunque torniamo l'informazione
						// Che appartengono a posizioni diverse.
						
						if (!TRIBUTI_MULTIPLI.equals(tipoTributo))
							tipoTributo=tipoTributo+" "+CAUSALI_MULTIPLE;
				

						if (!TRIBUTI_MULTIPLI.equals(descrizioneTributo))
							descrizioneTributo = descrizioneTributo+" "+CAUSALI_MULTIPLE;
					
						
					}	
										
					dtoOut.setIdPendenzaEnte(idPendenzaEnte);
					dtoOut.setIdEnte(idEnte);
					dtoOut.setCdTrbEnte(cdTrbEnte);
					dtoOut.setDataScadenzaPagamento(dataScadenzaPagamento);
					dtoOut.setDescrizioneEnte(descrizioneEnte);
					dtoOut.setTipoTributo(tipoTributo);
					dtoOut.setDescrizioneTributo(descrizioneTributo);
					dtoOut.setIdCondPagamento(idCondizionePagamento);
					dtoOut.setIdPagamentoEnte(idPagamentoEnte);
					
				
					dtoOut.setImporto(UtilBigDecimal.getStringFromBigDecimal(totImportiPostitivi));
					dtoOut.setImportoCommissioni(UtilBigDecimal.getStringFromBigDecimal(gestioneFlussi.getImportoCommissioni()));
					
					setPagamentiOnline.add(pagamentoOnline);
					gestioneFlussi.setPagamentiOnline(setPagamentiOnline);
					gestioneFlussi.setPagamenti(pagamenti);
                    gestioneFlussi.setNumeroDisposizioni(listaCondizioni.size());
                   
                    //FIX-561: aggiunto id della distinta al documento di pagamento
                    dp.setDistinta(gestioneFlussi);
                    gestioneFlussi.setDocPagamento(dp);
                    
                    if (LOGGER.isInfoEnabled())
        				LOGGER.info(" calling: gestioneFlussiDao.create - alle" + new Timestamp(new Date().getTime()).toString());
        			
					GestioneFlussi flusso = gestioneFlussiDao.create(gestioneFlussi); 
					if (LOGGER.isInfoEnabled())
        				LOGGER.info(" called: gestioneFlussiDao.create - alle" + new Timestamp(new Date().getTime()).toString());
        			
					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione per il doc: " + codPagamentoIris);
						LOGGER.info("CODICE: " + dtoOut.getCodice());
						LOGGER.info("CODICE: " + dtoOut.getDescrizione());
						LOGGER.info("PAGAMENTI INSERITI: " + getPagamentiInseriti(setPagamenti));
					}

					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
					return dtoOut;
				}
				
			} else {
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000001);
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione per il doc: " + codPagamentoIris);
					LOGGER.info("CODICE: " + dtoOut.getCodice());
					LOGGER.info("CODICE: " + dtoOut.getDescrizione());
				}
				return dtoOut;
			}
		}
		
		return dtoOut;
		
	}

	private EsitoOperazionePagamentoDTO autorizzaMulta(RichiestaAutorizzazioneDTO autorizzazioneDto, EsitoOperazionePagamentoDTO dtoOut) {
		
		Timestamp now = new Timestamp(new Date().getTime());
		
		// controllo se mi viene passata una multa
		if (autorizzazioneDto.getMulta() != null) {
			
			if (LOGGER.isInfoEnabled()) 
				LOGGER.info("Autorizzazione pagamento - Multa : " + autorizzazioneDto.toString());
			
			try {
				
				dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
				if (dtoOut.getReturnCode().isError()) 
					return dtoOut;
				

				// data multa valida
				if (StringUtils.isNotBlank(autorizzazioneDto.getMulta().getData())) {
					if (!isValidDataMulta(autorizzazioneDto.getMulta().getData())) {
						if (LOGGER.isDebugEnabled())
							LOGGER.debug("Data multa errato - multa : " + autorizzazioneDto.getMulta().toString());

						dtoOut.setReturnCode(EnumBusinessReturnCodes.B0000005);
						
						return dtoOut;

					}
				}
				// importo multa valido
				if (StringUtils.isNotBlank(autorizzazioneDto.getMulta().getImporto())) {
					if (!isValidImportoMulta(autorizzazioneDto.getMulta().getImporto())) {

						if (LOGGER.isDebugEnabled())
							LOGGER.debug("Importo multa errato - multa : " + autorizzazioneDto.getMulta().toString());

						dtoOut.setReturnCode(EnumBusinessReturnCodes.B0000002);
						
						return dtoOut;
					}
				}

				//////////////////
				// verificare la multa sulla tabella condizioni
				// RECUPERO MULTA
				///////////////////

				Date dataVerbale = getDateFromString(autorizzazioneDto.getMulta().getData());
				
				List<Pendenza> pendenze = pendenzaDao.getMultaByBusinessKeys(autorizzazioneDto.getMulta().getTarga(), dataVerbale, autorizzazioneDto.getMulta().getNumVerbale(), autorizzazioneDto.getMulta().getSerie());
				
				//TODO: controllo sull'importo? Non ci starebbe male.. visto che non c'ï¿½ l'ente in chiave di ricerca
				
				if (pendenze == null || pendenze.size()==0) {
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Multa non trovata - multa : " + autorizzazioneDto.getMulta().toString());

					dtoOut.setReturnCode(EnumBusinessReturnCodes.B0000001);
					
					return dtoOut;					
				}
				
				if (pendenze.size()>1) {
					
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Risultato non univoco : " + autorizzazioneDto.getMulta().toString());

					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000021);
					
					return dtoOut;					
				}
				
				
				Pendenza multa = pendenze.get(0);
				
				int condizioniPagabili = 0;
				
				int condizioniPagate = 0;
				
				CondizionePagamento condizioneMulta = null;
				
				for (CondizionePagamento c:multa.getCondizioni()) {
					
					c.updateStatoPagamentoCalcolato();
					
					System.out.println("C="+c.getStatoPagamentoCalcolato());
					
					if (EnumStatoPagamentoCondizione.DA_PAGARE.equals(c.getStatoPagamentoCalcolato())) {
						condizioniPagabili++;
						condizioneMulta=c;
					}
					
					if (EnumStatoPagamentoCondizione.PAGATA.equals(c.getStatoPagamentoCalcolato()))
						condizioniPagate++;
					
				}
				
				if (condizioniPagate > 0) {
					
					if (LOGGER.isDebugEnabled())
						
						LOGGER.debug("Multa gia'pagata - multa : " + autorizzazioneDto.getMulta().toString());
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.W0000002);
					
					return dtoOut;
					
				}
				
				if (condizioniPagabili == 0) {
					
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Multa non pagabile - multa : " + autorizzazioneDto.getMulta().toString());
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.B0000002);
					
					return dtoOut;
					
				}
				
				if (condizioniPagabili > 1) {
					
					if (LOGGER.isDebugEnabled())
						LOGGER.debug("Trovate piu' condizioni pagabili per la stessa posizione  : " + autorizzazioneDto.getMulta().toString());

					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000024);
					
					return dtoOut;
					
				}

				GestioneFlussi flussoOut =  aggiornaMulta(condizioneMulta, autorizzazioneDto, now, dtoOut.getReturnCode());
				
				if (flussoOut.getTotimportipositivi()!=null) {
					
                    dtoOut.setImporto(UtilBigDecimal.getStringFromBigDecimal(flussoOut.getTotimportipositivi()));
                    dtoOut.setImportoCommissioni(UtilBigDecimal.getStringFromBigDecimal(flussoOut.getImportoCommissioni()));
				 
				} else  
					dtoOut.setImporto("0");
				
				dtoOut.setCodiceFiscaleDebitore(flussoOut.getOpInserimento());
				Set<Pagamenti> setPOut =  flussoOut.getPagamenti();
				for (Pagamenti pagamenti: setPOut) {
					
					dtoOut.setDataScadenzaPagamento(pagamenti.getDtScadenza());
					dtoOut.setDescrizioneEnte(condizioneMulta.getEnte().getIntestatarioobj().getRagionesocialeIForm());
//		                    dtoOut.setDescrizioneTributo(condizioneMulta.getPendenza().getCategoriaTributo().getDeTrb());
					dtoOut.setDescrizioneTributo(condizioneMulta.getPendenza().getTributoEnte().getDeTrb());
                    dtoOut.setIdCondPagamento(Long.toString(pagamenti.getId()));
                    dtoOut.setIdEnte(pagamenti.getIdEnte());
                    dtoOut.setIdPagamentoEnte(pagamenti.getIdPendenzaente());  
//		                    dtoOut.setTipoTributo(condizioneMulta.getPendenza().getCategoriaTributo().getIdTributo());
                    dtoOut.setTipoTributo(condizioneMulta.getPendenza().getCategoriaTributo().getDeTrb());
                    dtoOut.setIdPendenzaEnte(condizioneMulta.getPendenza().getIdPendenzaente());
                    dtoOut.setCdTrbEnte(pagamenti.getCdTrbEnte());
				}
                dtoOut.setMsgId(flussoOut.getCodTransazione());
                
				if (LOGGER.isInfoEnabled()){
					LOGGER.debug("Multa AUTORIZZATA : " + autorizzazioneDto.getMulta().toString());
				    LOGGER.debug("CODICE TRANSAZIONE: " + dtoOut.getMsgId());
				}
                
			} catch (Exception e) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(e.toString());
				}
				dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
				
			}


			return dtoOut;
		} //FINE MULTA
				
		
		return dtoOut;
		
	}


	private EsitoOperazionePagamentoDTO autorizzaCarrello(RichiestaAutorizzazioneDTO autorizzazioneDto, EsitoOperazionePagamentoDTO dtoOut) throws Exception {
		
		GestioneFlussi gestioneFlussi = new GestioneFlussi();
		
		String docPagamento = autorizzazioneDto.getCodicePagamentoIris();
		
		Timestamp now = new Timestamp(new Date().getTime());	
		
		
		// controllo se mi viene passato il carrello
		if (autorizzazioneDto.getPendenze() != null && autorizzazioneDto.getPendenze().size() > 0) {
			
			dtoOut.setLogMessage("Servizio autorizzazione pagamento - richiesta autorizzazione per il doc: " + docPagamento);
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info("Servizio autorizzazione pagamento - richiesta autorizzazione per il carrello: ");
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			List<CondizionePagamento> listaCondizioni = estraiListaCondizioni(autorizzazioneDto);
			
			if (listaCondizioni.size() == 0) {
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000001);
				
				return dtoOut;
			}
			
			// Controllo che le pendenze/condizioni passate siano coerenti
			dtoOut = isPendenzeCoerenti(dtoOut,autorizzazioneDto);
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			//
			dtoOut = checkCondizioniPagabili(dtoOut,listaCondizioni);
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			
			dtoOut.setCondizioni(listaCondizioni);
	
			// se le condizioni sono tutte pagabili le autorizzo
			dtoOut = autorizzaPagamentoCondizioni(autorizzazioneDto, dtoOut, gestioneFlussi, now);
			
			if (dtoOut.getCodice() != null) 
										return dtoOut;
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
			
			return dtoOut;		
			
		} // FINE CARRELLO
		
		return dtoOut;
		
	}
	
	private EsitoOperazionePagamentoDTO autorizzaPerListaCodici(RichiestaAutorizzazioneDTO autorizzazioneDto, EsitoOperazionePagamentoDTO dtoOut) throws Exception {
		
		GestioneFlussi gestioneFlussi = new GestioneFlussi();
		
		Timestamp now = new Timestamp(new Date().getTime());	
		
		
		// controllo se mi viene passato il carrello
		if (!autorizzazioneDto.getListaCodici().isEmpty()) {
			
			String logMsg = "Servizio autorizzazione pagamento - richiesta autorizzazione per i codici : "+ autorizzazioneDto.getListaCodici();
			
			dtoOut.setLogMessage(logMsg);
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" calling: commonPaymentBusinessBean.controlliTestata - alle" + new Timestamp(new Date().getTime()).toString());
			
			dtoOut = (EsitoOperazionePagamentoDTO) commonPaymentBusinessBean.controlliTestata(dtoOut,autorizzazioneDto.getTestata(),autorizzazioneDto.getIdentificativoCanalePSP(),autorizzazioneDto.getIdentificativoIntermediarioPSP());
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" called: commonPaymentBusinessBean.controlliTestata - alle" + new Timestamp(new Date().getTime()).toString());
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			if (LOGGER.isInfoEnabled())
			LOGGER.info(" calling: getAndCheckListaCondizioni - alle" + new Timestamp(new Date().getTime()).toString());
			
			dtoOut = getAndCheckListaCondizioni(autorizzazioneDto, dtoOut, true,"V");
			
			if (LOGGER.isInfoEnabled())
			LOGGER.info(" called: getAndCheckListaCondizioni - alle" + new Timestamp(new Date().getTime()).toString());
			
			if (dtoOut.getReturnCode().isError())
										return dtoOut;
			
			
			
			// Controllo che le pendenze/condizioni passate siano coerenti
			// TODO PAZZIK verificare se serve ancora questa serie di verifiche 
			// dopo aver giï¿½ estratto le condizioni
			//dtoOut = isPendenzeCoerenti(dtoOut,autorizzazioneDto);
			
			//if (dtoOut.getReturnCode().isError())
										//return dtoOut;
			//
			//dtoOut = checkCondizioniPagabiliPUNTOSI(dtoOut,listaCondizioni);
			
			//if (dtoOut.getReturnCode().isError())
										//return dtoOut;
	
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" calling: autorizzaPagamentoCondizioni - alle" + new Timestamp(new Date().getTime()).toString());
			
			// se le condizioni sono tutte pagabili le autorizzo
			dtoOut = autorizzaPagamentoCondizioni(autorizzazioneDto, dtoOut, gestioneFlussi, now);
			
			if (LOGGER.isInfoEnabled())
				LOGGER.info(" called: autorizzaPagamentoCondizioni - alle" + new Timestamp(new Date().getTime()).toString());
			
			
			if (dtoOut.getReturnCode().equals(EnumBusinessReturnCodes.OK)) 
			
					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK_AUTORIZZATO);
			
			return dtoOut;		
			
		} // FINE CARRELLO
		
		return dtoOut;
		
	}

	private String getPagamentiInseriti(Set<Pagamenti> pagamenti) {
		String out = "";
		for (Pagamenti pagamento : pagamenti) {
			out = out + pagamento.getId()+ " ";
		}
		return out;
	}
	
	private List<CondizionePagamento> estraiListaCondizioni(RichiestaAutorizzazioneDTO dto) {
		
		List<CondizionePagamento> condOut = new ArrayList<CondizionePagamento>();
		
		List<PendenzaDTO> lista = dto.getPendenze();
		
		for (PendenzaDTO pendenzaDTO : lista) {
			
			List<String> listaIdCond = pendenzaDTO.getCondizioni();
								
			List<CondizionePagamento> condizioniPagamento = condPagDao.getCondizioniByIdList(listaIdCond);
			
			for (CondizionePagamento condizionePagamento: condizioniPagamento) {
				
					condizionePagamento.updateStatoPagamentoCalcolato();
					
					condOut.add(condizionePagamento);	
					
					// TODO PAZZIK solo temporaneamente per evitare che getEsitoPagamento sbombi quando si trova ad eseguire senza una transazione aperta
					// sistemare in PaymentApplicationIntegration con CondizionePagamentoDTO
					condizionePagamento.getEnte().getIntestatarioobj().getRagionesocialeIForm();
					
					// TO AVOID LAZY INIT EXCEPTIONS
					BillItemInspector.preLoadPagamenti(condizionePagamento);
			}
			
		}
		
		return condOut;
	}

	/**
	 * @param autorizzazioneDTO
	 * @param dtoOut
	 * @return
	 */
	private EsitoOperazionePagamentoDTO getAndCheckListaCondizioni(RichiestaAutorizzazioneDTO autorizzazioneDTO, EsitoOperazionePagamentoDTO dtoOut, boolean checkSePagabile, String tipoOperazione) {
		
		List<CondizionePagamento> condizioniAutorizzabili = new ArrayList<CondizionePagamento>();
		
		if (LOGGER.isInfoEnabled())
			LOGGER.info(" getAndCheckListaCondizioni - inizio - alle" + new Timestamp(new Date().getTime()).toString());
		boolean isAutorizzazionePerComunePrato = false;
		
		int contaIdVersamenti = 0;
		
		List<RichiestaAUPPerListaCodiciDTO> listaCodici = autorizzazioneDTO.getListaCodici();
		BigDecimal totaleCalcolato = new BigDecimal(0);
		for (RichiestaAUPPerListaCodiciDTO requestDTO : listaCodici) {
			
			String codiceTributoEnte = requestDTO.getCodiceDebitoCreditore(); // null.... è corretto?
			
			String cfCreditore = requestDTO.getCodiceFiscaleCreditore();
			
			if (LOGGER.isInfoEnabled())				
				LOGGER.info(" getAndCheckListaCondizioni - prima di invocare  getIdEnte  - alle" + new Timestamp(new Date().getTime()).toString());
			
			String idEnte = entiDao.getIdEnte(null, cfCreditore);
			
			if (LOGGER.isInfoEnabled())				
				LOGGER.info(" getAndCheckListaCondizioni - dopo avere invocato  getIdEnte  - alle" + new Timestamp(new Date().getTime()).toString());
			
			if (cfCreditore!=null) {
			   try {
				  if (LOGGER.isInfoEnabled())				
						LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  readEnteByCodFiscale  - alle" + new Timestamp(new Date().getTime()).toString());
					
			      Enti ente = entiDao.readEnteByCodFiscale(cfCreditore);
			      if (LOGGER.isInfoEnabled())				
						LOGGER.info(" getAndCheckListaCondizioni - dopo avere invocato  readEnteByCodFiscale  - alle" + new Timestamp(new Date().getTime()).toString());
					
			      dtoOut.setDescrizioneEnte(ente.getDenominazione());
			   } catch (Throwable t){
				   // errore... non andra a settare il campo opzionale entiBeneficiario in risposta
			   }
			}
			List<IdentificativoUnivocoVersamentoDTO> listaId = requestDTO.getIdVersamenti(); 
			
			contaIdVersamenti += listaId.size();
			
			if (cfCreditore!=null && cfCreditore.equals(CF_CREDITORE_COMUNE_PRATO)){
				isAutorizzazionePerComunePrato = true;
			}
			
			for (IdentificativoUnivocoVersamentoDTO idDTO : listaId) {
				
				CondizionePagamentoDTO condDTO = new CondizionePagamentoDTO();				
				EsitoCondizioneDTO esitoCondDTO = new EsitoCondizioneDTO();
						
				List<CondizionePagamento> condizioni = null;	
				
				
				/**
				 * NALDI: 
				 * I codici IUV saranno composti da 17 cifre numeriche così definite: 
				 * 		2  cifre numeriche per il codice di segregazione 
				 * 		13 cifre numeriche 
				 * 		2  cifre di check-digit
				 * Il numero avviso sarà composto da 18 cifre numeriche così definite: 
				 *     '3' (aux_digit) + IUV 
				 */
				
				condDTO.setIdPagamento(idDTO.getIdPagamentoCreditore()); // serve?
				if (isAutorizzazionePerComunePrato){
				
					String codiceDiSegregazione = idDTO.getIdPagamentoCreditore().substring(0, 2);
					
					List<String> listaUrl = tributoEnteDao.getAllTributiEntibyEnteAndNdpCdStaz(idEnte, codiceDiSegregazione);
					
					if (LOGGER.isInfoEnabled())			
						LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  AutorizzaPagamentoRequest  - alle" + new Timestamp(new Date().getTime()).toString());
					if (!listaUrl.isEmpty()) {
						String urlUpdService = listaUrl.get(0);
						if (urlUpdService== null || urlUpdService.equals("")){
							if (LOGGER.isInfoEnabled())			
								LOGGER.info("Impossibile eseguire la chiamata al WS di Autorizzazione Pagamento per Verifica Pagamento - URL NON VALORIZZATA!! " );
							dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
							return dtoOut;
							
						}
						ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
						String urlServletBackend = props.getProperty("ws.autorizzazione.pagamento.url");
						
						if (urlServletBackend == null || urlServletBackend.equals("")){
							if (LOGGER.isInfoEnabled())			
								LOGGER.info("Impossibile eseguire la chiamata al WS di Autorizzazione Pagamento - URL SERVLET DI BACKEND NON VALORIZZATA!! " );
							dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
							return dtoOut;
						}
						
						// chiamo il BE!!!
						//
						// chiama il webservice con:
						//		IdentificativoDominio 
						//		IdentificativoUnivocoVersamento 
						//id dominio, url, iuv , codice contesto pagamento, tipo richiesta (v, a)
						
						
						//ottengo: esito AutorizzaPagamentoResponse
					
						String url = null;
						String importoStr = null;

						AutorizzaPagamentoResponse apv = null;
						if (idDTO.getImporto() !=null)
							importoStr = idDTO.getImporto().toString();
						
						if (tipoOperazione.equals("V")){
							// chiamo la verifica pagamento
							if (LOGGER.isInfoEnabled())			
								LOGGER.info("Chiamata alla servlet di Autorizzazione Pagamento per Verifica Pagamento - URL: " + urlServletBackend);
							
							apv=AutorizzazioneNdpHelper.chiamaAutorizzazionePagamentoWs(urlUpdService, autorizzazioneDTO.getTestata().getCodiceContestoPagamento(),  cfCreditore,idDTO.getIdPagamentoCreditore(),"V",importoStr,urlServletBackend);
						}else{
							// chiamo l'attiva pagamento
							if (LOGGER.isInfoEnabled())			
								LOGGER.info("Chiamata al WS di Autorizzazione Pagamento per Attiva Pagamento- URL: " + url);
							apv=AutorizzazioneNdpHelper.chiamaAutorizzazionePagamentoWs(urlUpdService, autorizzazioneDTO.getTestata().getCodiceContestoPagamento(),  cfCreditore,idDTO.getIdPagamentoCreditore(),"A",importoStr,urlServletBackend);
							
						}
					
					
						if (apv.getFault() == null) {
							esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.OK);
							dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
							condDTO.setCausalePendenza(apv.getCausaleVersamento());
							condDTO.setCdTrbEnte("");
							condDTO.setImTotale(apv.getImportoPagamento());
							
							if (LOGGER.isInfoEnabled())				
								LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  getRicevutaAllegatoCondizione  - alle" + new Timestamp(new Date().getTime()).toString());
						}else{
							if (LOGGER.isInfoEnabled())			
								LOGGER.info("Chiamata al WS di Autorizzazione Pagamento in ERRORE: - ERROR CODE: " + apv.getFault().getFaultCode() + " ERROR DESCRIPTION: " + apv.getFault().getFaultDescription());
							esitoCondDTO.setReturnCode(remappedFaultFromAutorizzazionePagamentoWS(apv.getFault()));
							dtoOut.setReturnCode(remappedFaultFromAutorizzazionePagamentoWS(apv.getFault()));
							return dtoOut;
						}
					
						
						if (tipoOperazione.equals("A")){
						
							condDTO.setIdPagamento(idDTO.getIdPagamentoCreditore());
							if (LOGGER.isInfoEnabled())				
								LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  getListCondizioniByIdPagamento  - alle" + new Timestamp(new Date().getTime()).toString());
							condizioni = condPagDao.getListCondizioniByIdPagamento(idDTO.getIdPagamentoCreditore(), apv.getTipoDebito(), idEnte);
							
							if (condizioni == null || condizioni.isEmpty())					
								esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000001);
								
							
							if (condizioni.size() > 1)					
								esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000021);
								
							
							if (condizioni.size() == 1) {					
								CondizionePagamento condizione = condizioni.get(0);
								String systemId = autorizzazioneDTO.getTestata().getSenderSys();
								String applicationId = autorizzazioneDTO.getTestata().getSenderSil();
								
								CfgGatewayPagamento cfgGatewayPagamento = cfgGatewayPagamentoDao.getCfgBySystemIdAndApplicationId(systemId, applicationId);
								
								String ibanAccredito= calcolaIbanBeneficiario(condizione, cfgGatewayPagamento);
								dtoOut.setIbanAccredito(ibanAccredito);
								
								condDTO = PAIBusinessDTOBuilder.fillCondizionePagamentoDTO(condizione, null);
								
								// CONTROLLO CONGRUENZA IMPORTO TOTALE
								if (idDTO.getImporto() != null && condizione.getImTotale().compareTo(idDTO.getImporto()) != 0){						
									esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000020);						
									dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_NON_AUTORIZZATO);						
									esitoCondDTO.setCondizione(condDTO);						
									dtoOut.getEsitiCondizioni().add(esitoCondDTO);						
									return dtoOut;
								}
								
								totaleCalcolato = totaleCalcolato.add(condizione.getImTotale());
								
								String codDestinatarioPendenzaDB = BillInspector.getDestinatario(condizione.getPendenza()).getCoDestinatario();					
								String codDebitoreFromRequest = idDTO.getCodiceDebitore();					
								String codVersanteFromRequest = idDTO.getCodiceVersante();
								
								// CONTROLLO IDENTITA' DEL DEBITORE
								
								if (codDebitoreFromRequest != null && !"ANONIMO".equals(codDebitoreFromRequest) && !codDestinatarioPendenzaDB.equals(codDebitoreFromRequest)){						
									esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000029);						
									dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_NON_AUTORIZZATO);						
									esitoCondDTO.setCondizione(condDTO);						
									dtoOut.getEsitiCondizioni().add(esitoCondDTO);						
									return dtoOut;
								}
								
								// IL PAGANTE, SE SPECIFICATO, DEVE DIVENTARE L'UTENTE CREATORE DELLA DISTINTA
								
								if (codVersanteFromRequest != null)						
										autorizzazioneDTO.setUtenteCreatore(codVersanteFromRequest);
								
								
								condizioniAutorizzabili.add(condizione);
								dtoOut.setCondizioni(condizioniAutorizzabili);
							}
						}
					
						
						
						
						if (LOGGER.isInfoEnabled())			
							LOGGER.info(" getAndCheckListaCondizioni - dopo avere invocato  AutorizzaPagamentoRequest  - alle" + new Timestamp(new Date().getTime()).toString());
					}else{
						if (LOGGER.isInfoEnabled())			
							LOGGER.info("Impossibile eseguire la chiamata al WS di Autorizzazione Pagamento per Verifica Pagamento - url  di invocazione al servizio non censita!! " );
						dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_GENERICO);
						return dtoOut;
					}
				} else {
				
				
					switch( idDTO.getTipoIdDebitoCreditore() ) {
					
						case ID_PAGAMENTO_CREDITORE: 
							
							String idPagamentoCreditore = idDTO.getIdPagamentoCreditore();	// IUV					
							condDTO.setIdPagamento(idPagamentoCreditore);
							if (LOGGER.isInfoEnabled())				
								LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  getListCondizioniByIdPagamento  - alle" + new Timestamp(new Date().getTime()).toString());
							
							List<CondizionePagamento> condizioniFromQuery = condPagDao.getListCondizioniByIdPagamento(idPagamentoCreditore, codiceTributoEnte, idEnte);
							
							if (LOGGER.isInfoEnabled())				
								LOGGER.info(" getAndCheckListaCondizioni - dopo avere invocato  getListCondizioniByIdPagamento  - alle" + new Timestamp(new Date().getTime()).toString());
							
							
							// Lista condizioni che verra' usata nelle verifiche successive. 
							condizioni = new ArrayList<CondizionePagamento>(); 
							 
							// Eseguo un preProcessing sulla lista condizioni per intercettare e correggere situazioni particolari 
							 
							if (condizioniFromQuery==null || condizioniFromQuery.size()==0) { 
							        // Non faccio nulla. La lista condizioni passata oltre nel metodo e' gia' una lista vuota. 
							} else  
							if (condizioniFromQuery.size()==1) { 
							        // Normal flow. Trovo una ed una sola condizione 
							        condizioni.addAll(condizioniFromQuery); 
							} else if  (condizioniFromQuery.size()>1) { 
							         
							        //----------------------------------------------------------------------------------------------------------------------- 
							        // Ciclo sulle condizioni per individuare se ne esiste una che si puo' autorizzare 
							        // Il fatto che qui se ne possa trovare piu' di una valida e' legato al fatto che ci possono essere piu' condizioni in 
							        // st_riga='H' figlie di tentativi successivi. 
							        // Siccome la lista e' ordinata per ts_inserimento desc, la condizione piu' recente e' ciclata per prima. 
							        //----------------------------------------------------------------------------------------------------------------------- 
							         
							        CondizionePagamento candidata = null; 
							         
							        for(CondizionePagamento  c:condizioniFromQuery) { 
							                if ("V".equals(c.getStRiga())) { 
							                        // Se trovo quella in stato "V" la candidata e' sicuramente lei. 
							                        candidata=c; 
							                        break; 
							                }  
							                if ("H".equals(c.getStRiga())) { 
							                         
							                        // Verifico se la condizione ha pagamenti in corso (in tal caso infatti st_riga='H') 
							                        // In tal caso la condizione da considerare e' sicuramente quella (se ce ne fossero di piu' passa la piu' recente) 
							                        // Nota (l'OR con lo stato Pagata e' una salvaguardia.. in realta' una condizione pagata dovrebbe sempre avere st_riga='V') 
							                        c.updateStatoPagamentoCalcolato(); 
							                        if (c.getStatoPagamentoCalcolato()==EnumStatoPagamentoCondizione.IN_CORSO ||  
							                                c.getStatoPagamentoCalcolato()==EnumStatoPagamentoCondizione.PAGATA) { 
							                                candidata=c; 
							                                break; 
							                        } 
							                         
							                        // Se non ha pagamenti in corso (e non ce ne e' una piu' recente.. la candidata e' temporaneamente lei. 
							                        if (candidata==null) {  
							                                candidata=c; 
							                                // si  noti che qui non c'e' break, perche' ce ne potrebbero essere di meno recenti con condizioni di selezione prioritarie. 
							                        } 
							                } 
							        } 
							         
							         
							        // Alla fine se ho trovato una candidata, la aggiungo alla collection: 
							        if (candidata!=null) { 
							                condizioni.add(candidata); 
							        } 
							 
							} 

							
							
							
							break;
							
						case CIP: 
							
							String coCIP = idDTO.getIdPagamentoCreditore();						
							condDTO.setCoCip(coCIP);
							if (LOGGER.isInfoEnabled())				
								LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  getListCondizioniByCIPCode  - alle" + new Timestamp(new Date().getTime()).toString());
							
							condizioni = condPagDao.getListCondizioniByCIPCode(coCIP, codiceTributoEnte, idEnte);
							if (LOGGER.isInfoEnabled())				
								LOGGER.info(" getAndCheckListaCondizioni - dopo avere invocato  getListCondizioniByCIPCode  - alle" + new Timestamp(new Date().getTime()).toString());
							
							break;
					
					}
					
					if (condizioni == null || condizioni.isEmpty())					
						esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000001);
						
					
					if (condizioni.size() > 1)					
						esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000021);
						
					
					if (condizioni.size() == 1) {					
						CondizionePagamento condizione = condizioni.get(0);
						String systemId = autorizzazioneDTO.getTestata().getSenderSys();
						String applicationId = autorizzazioneDTO.getTestata().getSenderSil();
						
						CfgGatewayPagamento cfgGatewayPagamento = cfgGatewayPagamentoDao.getCfgBySystemIdAndApplicationId(systemId, applicationId);
						
						String ibanAccredito= calcolaIbanBeneficiario(condizione, cfgGatewayPagamento);
						dtoOut.setIbanAccredito(ibanAccredito);
						if (LOGGER.isInfoEnabled())				
							LOGGER.info(" getAndCheckListaCondizioni - prima di avere invocato  getRicevutaAllegatoCondizione  - alle" + new Timestamp(new Date().getTime()).toString());
						
						AllegatiPendenza allegato = ddpBusinessBean.getRicevutaAllegatoCondizione(condizione.getPendenza().getIdPendenza(), condizione.getIdCondizione(), new Locale("it","IT","tuscany"), autorizzazioneDTO.getCodificaRicevuta());
						
						if (LOGGER.isInfoEnabled())				
							LOGGER.info(" getAndCheckListaCondizioni - dopo avere invocato  getRicevutaAllegatoCondizione  - alle" + new Timestamp(new Date().getTime()).toString());
						
						condDTO = PAIBusinessDTOBuilder.fillCondizionePagamentoDTO(condizione, allegato);
						
						// CONTROLLO CONGRUENZA IMPORTO TOTALE
						if (idDTO.getImporto() != null && condizione.getImTotale().compareTo(idDTO.getImporto()) != 0){						
							esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000020);						
							dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_NON_AUTORIZZATO);						
							esitoCondDTO.setCondizione(condDTO);						
							dtoOut.getEsitiCondizioni().add(esitoCondDTO);						
							return dtoOut;
						}
						
						totaleCalcolato = totaleCalcolato.add(condizione.getImTotale());
						
						String codDestinatarioPendenzaDB = BillInspector.getDestinatario(condizione.getPendenza()).getCoDestinatario();					
						String codDebitoreFromRequest = idDTO.getCodiceDebitore();					
						String codVersanteFromRequest = idDTO.getCodiceVersante();
						
						// CONTROLLO IDENTITA' DEL DEBITORE
						
						if (codDebitoreFromRequest != null && !"ANONIMO".equals(codDebitoreFromRequest) && !codDestinatarioPendenzaDB.equals(codDebitoreFromRequest)){						
							esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000029);						
							dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_NON_AUTORIZZATO);						
							esitoCondDTO.setCondizione(condDTO);						
							dtoOut.getEsitiCondizioni().add(esitoCondDTO);						
							return dtoOut;
						}
						
						// IL PAGANTE, SE SPECIFICATO, DEVE DIVENTARE L'UTENTE CREATORE DELLA DISTINTA
						
						if (codVersanteFromRequest != null)						
								autorizzazioneDTO.setUtenteCreatore(codVersanteFromRequest);
						
						
						if (checkSePagabile) {
							EsitoOperazionePagamentoDTO esitoOp=checkCondizionePagabile(condizione, esitoCondDTO, dtoOut);						
							if (EnumBusinessReturnCodes.A0000005==esitoCondDTO.getReturnCode()) {
								
								// Intercetto il pagamento in corso.
								// Chiamo funzione che annulla il pagamento precedente (se il chiamante lo vuole)
								List<CondizionePagamento> lps = new ArrayList<CondizionePagamento>();
								lps.add(condizione);
								dtoOut=checkPaymentInProgress(dtoOut, lps, autorizzazioneDTO );
								
								if (EnumBusinessReturnCodes.A0000011==dtoOut.getReturnCode()) {
									// La posizione torna pagabile
									esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.OK);
									dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
								}
								
								
							}
						}	
						
						if (!esitoCondDTO.getReturnCode().isError())						
							condizioniAutorizzabili.add(condizione);
						
					}
				}
								
				esitoCondDTO.setCondizione(condDTO);
				
				dtoOut.getEsitiCondizioni().add(esitoCondDTO);
			}
			
		
			if(!isAutorizzazionePerComunePrato) { // comune di prato --> TODO rivedi ctrl
	       
				if (autorizzazioneDTO.getTotalAmount() != null && totaleCalcolato.compareTo(autorizzazioneDTO.getTotalAmount()) != 0){
					int lastPos = dtoOut.getEsitiCondizioni().size() -1;			
					EsitoCondizioneDTO  esitoCondDTO = dtoOut.getEsitiCondizioni().get(lastPos);
					esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000020);						
					dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_NON_AUTORIZZATO);	
					return dtoOut;
				}
		
		
				if (!condizioniAutorizzabili.isEmpty())
					dtoOut.setCondizioni(condizioniAutorizzabili);
				
				// se una sola condizione della richiesta non ï¿½ autorizzabile, fallisce l'autorizzazione di tutta la lista
				if (condizioniAutorizzabili.size() != contaIdVersamenti)		
					dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_NON_AUTORIZZATO);
			}
		} // fine else, da rivedere
		
		if (LOGGER.isInfoEnabled())
			LOGGER.info(" getAndCheckListaCondizioni - fine - alle" + new Timestamp(new Date().getTime()).toString());
		
		return dtoOut;
	}
	
	
	
	
	

	private EsitoOperazionePagamentoDTO preProcessingCBILL(RichiestaAutorizzazioneDTO dto, EsitoOperazionePagamentoDTO dtoOut) {
		
		boolean isCBILLRequest = dto.getTestata().getSenderSil().equals(CfgGatewayPagamento.CBILL);
		
		if (isCBILLRequest){
			
			RichiestaAutorizzazioneCBILLDTO richCBILLDTO = dto.getRichiestaCBILLDTO();
			
			String tipoCodPag = richCBILLDTO.getTipoCodicePagamento();
			
			String codRicPag = richCBILLDTO.getCodiceRicercaPagamento();
			
			String codTribEnte = richCBILLDTO.getCodiceTributoEnte();
			
			String siaEnte = richCBILLDTO.getSiaEnte();
			
			String idEnte = null;
			
			PendenzaDTO pendDto = null;
			
			if (!StringUtils.isEmpty(siaEnte))
				idEnte = entiDao.getIdEnte(siaEnte, null);
			
			ArrayList<PendenzaDTO> pendenze = new ArrayList<PendenzaDTO>();
			
			if (StringUtils.isEmpty(tipoCodPag))
				pendDto = searchByIdPagamento(codRicPag, codTribEnte, idEnte);
			
			if (pendDto.getCondizioni().isEmpty())
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000001);
			
			pendenze.add(pendDto);
			
			dto.setPendenze(pendenze);
			
		}
		
		return dtoOut;
	}

	/**
	 * @param codiceRicercaPagamento
	 * @param codiceTributoEnte
	 * @param idEnte
	 * @param importoPagam
	 * @return
	 */
	private PendenzaDTO searchByIdPagamento(String codiceRicercaPagamento, String codiceTributoEnte, String idEnte) {
		
		List<CondizionePagamento> condizioni = condPagDao.getListCondizioniByIdPagamento(codiceRicercaPagamento, codiceTributoEnte, idEnte);
		
		PendenzaDTO pendDTO = new PendenzaDTO();
		
		for (CondizionePagamento cond  : condizioni)
			
			//pendDTO.setIdPendenzaEnte(cond.getPendenza().getIdPendenzaente());
			
			//pendDTO.setIdEnte(cond.getEnte().getIdEnte());
			
			pendDTO.getCondizioni().add(cond.getIdCondizione());
		
		
		return pendDTO;
	}

	
	private EsitoOperazionePagamentoDTO autorizzaPagamentoCondizioni(RichiestaAutorizzazioneDTO dtoIn, EsitoOperazionePagamentoDTO dtoOut, GestioneFlussi gestioneFlussi, Timestamp now) throws Exception {
		
		List<CondizionePagamento> listaCondizioni = dtoOut.getCondizioni();
		
		// *******
		// TODO: Recuperare la pendenza
		// 1. inserimento distinta di pagamento nella tabella
		String codiceFiscaleDestinatario = "non trovato";
		
		CondizionePagamento cp = listaCondizioni.get(0);
		
		codiceFiscaleDestinatario = dtoIn.getUtenteCreatore() != null ? dtoIn.getUtenteCreatore() : BillInspector.getDestinatario(cp.getPendenza()).getCoDestinatario();
					
		BigDecimal totImportiPositivi = getTotImporti(listaCondizioni);
		
		gestioneFlussi = popolaFlusso(dtoIn, now, codiceFiscaleDestinatario, codiceFiscaleDestinatario, totImportiPositivi,EnumTipoStrumentoDiPagamento.WEB);
		
		// vado a ciclare le condizioni
		Set<PagamentiOnline> setPagamentiOnline = new HashSet<PagamentiOnline>();
		
		PagamentiOnline pagamentoOnline = PaymentEntityBuilder.popolaPagamentoOnline(dtoIn.getTestata(), dtoIn.getCodicePagamentoIris(), gestioneFlussi, null, EnumOperazioniPagamento.AUTORIZZAZIONE, dtoOut.getReturnCode(), codiceFiscaleDestinatario);
		
		setPagamentiOnline.add(pagamentoOnline);
		
		gestioneFlussi.setPagamentiOnline(setPagamentiOnline);

		Set<Pagamenti> pagamenti = PaymentEntityBuilder.popolaPagamenti(listaCondizioni, gestioneFlussi);
		
		gestioneFlussi.setPagamenti(pagamenti);
		
		gestioneFlussi.setNumeroDisposizioni(pagamenti.size());
		// PAGAMENTI
		
		// Check di univocita' del codice transazione che vado ad inserire. (Ci metteremo un indice prima o poi)
		GestioneFlussi old = gestioneFlussiDao.getDistintaByMsgId(gestioneFlussi.getCodTransazione());
		
		if (old != null) {
			
			// Se prima di inserire ne trovo un'altra con lo stesso cod_transazione, mi arrabbio parecchio.
			dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000022);
			
			return dtoOut;
		}
		
		GestioneFlussi flusso = gestioneFlussiDao.create(gestioneFlussi);
		
		cp.setCodPagamentoFlusso(flusso.getCodPagamento());
		cp.setCodPaganteFlusso(flusso.getUtentecreatore());
		cp.setIdPagamentoFlusso(flusso.getId().toString());
		
		
		if (LOGGER.isInfoEnabled()) {
			
			LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione");
			LOGGER.info("CODICE: " + dtoOut.getCodice());
			LOGGER.info("CODICE: " + dtoOut.getDescrizione());
			LOGGER.info("PAGAMENTI INSERITI: " + getPagamentiInseriti(pagamenti));
		
		}
		
		dtoOut.setCodiceFiscaleDebitore(flusso.getOpInserimento());
		dtoOut.setCodPagamentoFlusso(gestioneFlussi.getCodPagamento());
		dtoOut.setImporto(totImportiPositivi.toString());
		dtoOut.setMsgId(gestioneFlussi.getCodTransazione());
		dtoOut.setDataOrdine(gestioneFlussi.getDataSpedizione());
		dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
		
		return dtoOut;
		
	}	
	
	
	/*
	 * Autorizzazione, in caso di pagamenti proventienti da Nodo dei pagamenti.
	 */
	private EsitoOperazionePagamentoDTO autorizzaPagamentoCondizioniNDP(RichiestaAutorizzazioneDTO dtoIn, EsitoOperazionePagamentoDTO dtoOut, GestioneFlussi gestioneFlussi, Timestamp now) throws Exception {
		
		List<CondizionePagamento> listaCondizioni = dtoOut.getCondizioni();
		
		// *******
		// TODO: Recuperare la pendenza
		// 1. inserimento distinta di pagamento nella tabella
		String codiceFiscaleDestinatario = "non trovato";
		
		CondizionePagamento cp = listaCondizioni.get(0);
		
		codiceFiscaleDestinatario = dtoIn.getUtenteCreatore() != null ? dtoIn.getUtenteCreatore() : BillInspector.getDestinatario(cp.getPendenza()).getCoDestinatario();
					
		BigDecimal totImportiPositivi = getTotImporti(listaCondizioni);
		
		gestioneFlussi = popolaFlusso(dtoIn, now, codiceFiscaleDestinatario, codiceFiscaleDestinatario, totImportiPositivi,EnumTipoStrumentoDiPagamento.WEB);
				
		// vado a ciclare le condizioni
		Set<PagamentiOnline> setPagamentiOnline = new HashSet<PagamentiOnline>();
		
		PagamentiOnline pagamentoOnline = PaymentEntityBuilder.popolaPagamentoOnline(dtoIn.getTestata(), dtoIn.getCodicePagamentoIris(), gestioneFlussi, null, EnumOperazioniPagamento.AUTORIZZAZIONE, dtoOut.getReturnCode(), codiceFiscaleDestinatario);
		
		setPagamentiOnline.add(pagamentoOnline);
		
		gestioneFlussi.setPagamentiOnline(setPagamentiOnline);

		Set<Pagamenti> pagamenti = PaymentEntityBuilder.popolaPagamenti(listaCondizioni, gestioneFlussi);
		
		gestioneFlussi.setPagamenti(pagamenti);
		
		//inserisco idPsp su ogni pagamento
		for (Pagamenti p: pagamenti) {
			p.setIdRiscossionePSP(dtoIn.getIdentificativoPSP());
		}
		
		gestioneFlussi.setNumeroDisposizioni(pagamenti.size());
		
		// recupero l indirizzo email dal documento di pagamento
		if (!cp.getCondizioniDocumento().isEmpty()) {
			for (CondizioneDocumento cd :cp.getCondizioniDocumento()) {
				gestioneFlussi.setEmailVersante(cd.getDocumento().getEmailVersante());
			}			
		}
				
		// PAGAMENTI
		// Check di univocitï¿½ del codice transazione che vado ad inserire. (Ci metteremo un indice prima o poi)
		// recupero la lista dei flussi con stesso IUV e ID_FISCALE_CREDITORE ordinati in base al ts_modifica o ts_inserimento desc
		List<GestioneFlussi>  l = gestioneFlussiDao.getDistintaByIUVIdFiscCred(gestioneFlussi.getIdentificativoFiscaleCreditore(), gestioneFlussi.getIuv());
		GestioneFlussi old = null;
		GestioneFlussi flusso = null;
		if (!l.isEmpty()) {
			// recupero il primo che e il piu recente
			old = l.get(0);
			String idPsp = null;
			//for (Pagamenti p :old.getPagamenti()) {
			//	idPsp = p.getIdRiscossionePSP();
			//}
			
			if (old != null) {
				idPsp = old.getCfgGatewayPagamento().getSystemId();
				if ("DEFAULT3XXX".equals(old.getCfgGatewayPagamento().getSystemId())){
					idPsp=old.getIdPspModello3();
				}
			}
			
			if (old!=null && StatiPagamentiIris.ESEGUITO.getFludMapping().equals(old.getStato())) {
            	dtoOut.setIdFlusso(old.getId());
            	dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000022); // DUPLICATO
			}
            else if (old!=null && StatiPagamentiIris.IN_CORSO.getFludMapping().equals(old.getStato())) {
				if (old.getCodTransazionePSP().equals(gestioneFlussi.getCodTransazionePSP())  && dtoIn.getIdentificativoPSP().equals(idPsp)) {
					dtoOut.setIdFlusso(old.getId());
					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
				} else {
					dtoOut.setIdFlusso(old.getId());
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000017); // IN CORSO
				}
			}
            else if (old!=null && StatiPagamentiIris.ESEGUITO_SBF.getFludMapping().equals(old.getStato()) ) {
				if (old.getCodTransazionePSP().equals(gestioneFlussi.getCodTransazionePSP())&& dtoIn.getIdentificativoPSP().equals(idPsp)) {
					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
					//metto distinta e pagamento associato IN CORSO
					dtoOut.setIdFlusso(old.getId());
					gestioneFlussiDao.aggiornamentoStatoFlusso(old.getId(), StatiPagamentiIris.IN_CORSO, null, " da ", null, null, null);
				} else {
					dtoOut.setIdFlusso(old.getId());
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000017); // IN CORSO
				}
			} 
            else if (old!=null && StatiPagamentiIris.IN_ERRORE.getFludMapping().equals(old.getStato()) ) {
				if (old.getCodTransazionePSP().equals(gestioneFlussi.getCodTransazionePSP())&& dtoIn.getIdentificativoPSP().equals(idPsp)) {
					dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
					//metto distinta e pagamento associato IN CORSO
					dtoOut.setIdFlusso(old.getId());
					gestioneFlussiDao.aggiornamentoStatoFlusso(old.getId(), StatiPagamentiIris.IN_CORSO, null, " da ",null, null, null);
				} else {
					old = null; // creo una nuova distinta
				}
			}     
            else if (old!=null /*Tutti gli altri stati di annullamento e non eseguito ANNULLATO, ANNULLATO OPE, NON_ESEGUITO, STORNATO */) {
				if (old.getCodTransazionePSP().equals(gestioneFlussi.getCodTransazionePSP()) && dtoIn.getIdentificativoPSP().equals(idPsp)) {
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000011);
					dtoOut.setIdFlusso(old.getId());
				} else {
					old = null; // creo una nuova distinta
				}
			}
		}
		
		formatCausalePSP(dtoOut); 
		
		if (old != null) {
			flusso = old;
			// Se prima di inserire ne trovo un'altra con lo stesso cod_transazione, mi arrabbio parecchio.
			//dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000022);	
			dtoOut.setIdFlusso(old.getId());
			return dtoOut;
		} else {		
		   flusso = gestioneFlussiDao.create(gestioneFlussi);
		}
		
		cp.setCodPagamentoFlusso(flusso.getCodPagamento());
		cp.setCodPaganteFlusso(flusso.getUtentecreatore());
		cp.setIdPagamentoFlusso(flusso.getId().toString());		
		
		if (LOGGER.isInfoEnabled()) {
			
			LOGGER.info("Servizio autorizzazione pagamento - esito richiesta autorizzazione");
			LOGGER.info("CODICE: " + dtoOut.getCodice());
			LOGGER.info("CODICE: " + dtoOut.getDescrizione());
			LOGGER.info("PAGAMENTI INSERITI: " + getPagamentiInseriti(pagamenti));
		
		}
		
		dtoOut.setIdFlusso(flusso.getId());
		dtoOut.setCodiceFiscaleDebitore(flusso.getOpInserimento());
		dtoOut.setCodPagamentoFlusso(gestioneFlussi.getCodPagamento());
		dtoOut.setImporto(totImportiPositivi.toString());
		dtoOut.setMsgId(gestioneFlussi.getCodTransazione());
		dtoOut.setDataOrdine(gestioneFlussi.getDataSpedizione());
		dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
		
		return dtoOut;
		
	}

	/**
	 * 
	 * @param autorizzazioneDto
	 * @param now
	 * @param codiceFiscaleDestinatario
	 * @param totImportiPostitivi
	 * @param tipoStrumento
	 * @return
	 * @throws Exception
	 */
	private GestioneFlussi popolaFlusso(RichiestaAutorizzazioneDTO autorizzazioneDto, Timestamp now, String codiceFiscaleDestinatario, String opInserimento, BigDecimal totImportiPostitivi, EnumTipoStrumentoDiPagamento tipoStrumento) throws Exception {
		
		GestioneFlussi gestioneFlussi = new GestioneFlussi();
		
		String extIdTransazione = autorizzazioneDto.getExtTransactionId(); 
		
		if (!StringUtils.isEmpty(extIdTransazione))
			
			gestioneFlussi.setCodTransazione(extIdTransazione);
		
		else {
			
			String msgIdCorrente = GeneratoreIdUnivoci.GetCurrent().generaCodiceTransazione();
			gestioneFlussi.setCodTransazione(msgIdCorrente);
			
		}
		
		String extCodPagamento = autorizzazioneDto.getExtCodPagamento();
		
		if (!StringUtils.isEmpty(extCodPagamento))
			gestioneFlussi.setCodPagamento(extCodPagamento);
		else 
			gestioneFlussi.setCodPagamento(IDGenerator.Generate_TRANSACTION_ID());
		
		gestioneFlussi.setStato(StatiPagamentiIris.IN_CORSO.getFludMapping());
		gestioneFlussi.setTotimportipositivi(totImportiPostitivi);

		// Gestione campi tipici nodo dei pagamenti. 
		// Per gli altri canali (dove questi dati non sono passati nel DTO) occorre inserire dei valori
		// che non mandino in dupkey l'indice.
		
		if (autorizzazioneDto.getIuv()==null) {
			gestioneFlussi.setIuv(gestioneFlussi.getCodTransazione());
		} else {
			gestioneFlussi.setIuv(autorizzazioneDto.getIuv());
		} 
		
		if (autorizzazioneDto.getTestata().getIdFiscaleCreditore()!=null) {
			gestioneFlussi.setIdentificativoFiscaleCreditore(autorizzazioneDto.getTestata().getIdFiscaleCreditore());
		} else {
			gestioneFlussi.setIdentificativoFiscaleCreditore("0");
		}
		
		if (autorizzazioneDto.getCodTransazionePSP()!=null) {
			gestioneFlussi.setCodTransazionePSP(autorizzazioneDto.getCodTransazionePSP());
		} else {
			gestioneFlussi.setCodTransazionePSP("0");
		}

		//
		// recupero gateway e  calcolo commissioni
		//
		String systemId = autorizzazioneDto.getTestata().getSenderSys();
		String applicationId = autorizzazioneDto.getTestata().getSenderSil();
		CfgGatewayPagamento gateway = null;
		// N.B. vedi PSPDTOBuilder.SENDER_SIL_PSP
		if("SENDER_SIL_PSP".equals(applicationId)) {
			gateway = cfgGatewayPagamentoDao.getCfgBySystemIdModelloVersamentoCanaleIntermediario(systemId, "4", autorizzazioneDto.getIdentificativoCanalePSP(), autorizzazioneDto.getIdentificativoIntermediarioPSP());
		} else {
			gateway = cfgGatewayPagamentoDao.getCfgBySystemIdAndApplicationId(systemId, applicationId);
		}
		if (gateway == null) {
		    gateway = cfgGatewayPagamentoDao.getCfgBySystemId("DEFAULT3XXX");
		    gestioneFlussi.setIdCanaleModello3(autorizzazioneDto.getIdentificativoCanalePSP());
		    gestioneFlussi.setIdIntermediarioModello3(autorizzazioneDto.getIdentificativoIntermediarioPSP());
		} else {
            gestioneFlussi.setIdCanaleModello3(gateway.getApplicationId());
		    gestioneFlussi.setIdIntermediarioModello3(gateway.getSubsystemId());		    
		}
		
		//**** setto comunque il valore del psp indipendentemente sia censito o no
		gestioneFlussi.setIdPspModello3(systemId);
		
		//**** 
		BigDecimal commissioniDistinta = CommissioniCalculator.calcolaTotaleCommissioni(gateway.getCfgCommissionePagamenti(), totImportiPostitivi);
		gestioneFlussi.setImportoCommissioni(commissioniDistinta);
		gestioneFlussi.setCfgGatewayPagamento(gateway);
		gestioneFlussi.setTmbcreazione(now);

		gestioneFlussi.setTsInserimento(now);
		gestioneFlussi.setTsUpdate(now);
		gestioneFlussi.setDivisa("EUR");
		gestioneFlussi.setUtentecreatore(codiceFiscaleDestinatario.trim());
		gestioneFlussi.setOpInserimento(opInserimento.trim());
//		gestioneFlussi.setVersion(new Long("1"));
		gestioneFlussi.setDataSpedizione(now);
		
		return gestioneFlussi;
	}

	private EsitoOperazionePagamentoDTO getEsitoOperazione(EsitoOperazionePagamentoDTO out, GestioneFlussi gf, List<CondizionePagamento> condizioniPagamento, String idPagamentoCorrente) {
		
		EsitoOperazionePagamentoDTO dtoOut = null;
		
		if (out != null && out.getCodice() != null)
			
			dtoOut = out;
		
		else
			dtoOut = new EsitoOperazionePagamentoDTO();
		
		if (condizioniPagamento != null)
			
			for(CondizionePagamento condizionePagamento : condizioniPagamento){
				
				Pendenza pend = condizionePagamento.getPendenza();
				
				dtoOut.setCondizioni(condizioniPagamento);
		
				dtoOut.setMsgId(gf.getCodTransazione());
				dtoOut.setIdPendenzaEnte(pend.getIdPendenzaente());
				dtoOut.setIdEnte(condizionePagamento.getEnte().getIdEnte());
				dtoOut.setCdTrbEnte(condizionePagamento.getCdTrbEnte());
				dtoOut.setIdCondPagamento(idPagamentoCorrente);
				// dtoOut.setIdPagamentoEnte(salvaIdPagamentoEnte);
		
				dtoOut.setImporto(UtilBigDecimal.getStringFromBigDecimal(condizionePagamento.getImTotale()));
		
				// dtoOut.setImportoCommissioni(salvaImportoCommissioni);
				dtoOut.setDataScadenzaPagamento(condizionePagamento.getDtScadenza());
				dtoOut.setDescrizioneEnte(condizionePagamento.getEnte().getIntestatarioobj().getRagionesocialeIForm());
		//		dtoOut.setTipoTributo(condizionePagamento.getPendenza().getCategoriaTributo().getIdTributo());
				dtoOut.setTipoTributo(pend.getCategoriaTributo().getDeTrb());
		//		dtoOut.setDescrizioneTributo(condizionePagamento.getPendenza().getCategoriaTributo().getDeTrb());
				dtoOut.setDescrizioneTributo(pend.getTributoEnte().getDeTrb());
				
			}
		
		return dtoOut;

	}


	private GestioneFlussi aggiornaMulta(CondizionePagamento condizioneMulta, RichiestaAutorizzazioneDTO richiesta, Timestamp now, EnumBusinessReturnCodes returnCode) throws Exception {

		// crea identificativo univoco
		String id = GeneratoreIdUnivoci.GetCurrent().generaId();

		// recupero il codice fiscale
		String codiceFiscaleDestinatario = "non trovato";
		Set<DestinatariPendenza> destinatari = condizioneMulta.getPendenza().getDestinatari();

		if (!destinatari.isEmpty()) {
			codiceFiscaleDestinatario = destinatari.iterator().next().getCoDestinatario();
		}

		// crea flusso
		List<CondizionePagamento> listaCondizioni = new ArrayList<CondizionePagamento>();
		
		listaCondizioni.add(condizioneMulta);
		
		BigDecimal totImportiPositivi = getTotImporti(listaCondizioni);
		
		GestioneFlussi flusso = popolaFlusso(richiesta, now, codiceFiscaleDestinatario, codiceFiscaleDestinatario, totImportiPositivi,EnumTipoStrumentoDiPagamento.MULTA);
        
		flusso.setTotimportipositivi(condizioneMulta.getImTotale());
		// crea pagamento
		Pagamenti pagamento = PaymentEntityBuilder.popolaPagamento(condizioneMulta, flusso, getBigDecimalFromString(richiesta.getMulta().getImporto()));

		// pagamentionline
		PagamentiOnline pagaOnline = PaymentEntityBuilder.popolaPagamentoOnline(richiesta.getTestata(), richiesta.getCodicePagamentoIris(), flusso, null, EnumOperazioniPagamento.AUTORIZZAZIONE, returnCode, codiceFiscaleDestinatario);

		Set<Pagamenti> pagamenti = new HashSet<Pagamenti>();
		pagamenti.add(pagamento);
		flusso.setPagamenti(pagamenti);

		Set<PagamentiOnline> pagamentiOnLine = new HashSet<PagamentiOnline>();
		pagamentiOnLine.add(pagaOnline);
		flusso.setPagamentiOnline(pagamentiOnLine);
        
		gestioneFlussiDao.insertFlusso(flusso);

        return flusso;
	}

	private boolean isValidImportoMulta(String importo) {

		if (StringUtils.isBlank(importo))
			return false;

		if (!StringUtils.isNumeric(importo))
			return false;

		if (StringUtils.length(importo) > 13)
			return false;

		return true;
	}

	private BigDecimal getBigDecimalFromString(String importo) {
		BigDecimal retVal = null;

		if (isValidImportoMulta(importo)) {
			BigDecimal tmp = new BigDecimal(importo);
			retVal = tmp.divide(new BigDecimal("100"));
		}

		return retVal;
	}

	private boolean isValidDataMulta(String dataVerbale) {
		Date parsed = getDateFromString(dataVerbale);
		return (parsed != null);
	}

	private Date getDateFromString(String dataVerbale) {

		String[] patterns = { "ddMMyy", "dd/MM/yy", "dd-MM-yy" };
		Date parsedDate = null;
		SimpleDateFormat sdf;

		for (String pattern : patterns) {
			sdf = new SimpleDateFormat(pattern);
			sdf.setLenient(false);
			try {
				parsedDate = sdf.parse(dataVerbale);
			} catch (ParseException e) {
				
				if (LOGGER.isTraceEnabled())
					LOGGER.trace("errore silenziato: ", e);
			}
			if (parsedDate != null)
				break;
		}

		return parsedDate;
	}
	
	
	
	private EsitoOperazionePagamentoDTO checkCondizionePagabile(CondizionePagamento condizione, EsitoCondizioneDTO esitoCondDTO, EsitoOperazionePagamentoDTO dtoOut) {
		
		String statoPagamento = null;
			
		boolean isCondizionePagabile = false;

		try {			
				if (condizione != null) {
					
					String statoCondizione = condizione.getStPagamento();
										
					Pagamenti mainPayment = BillItemInspector.getMainPayment(condizione);
					
					if (mainPayment != null)
						statoPagamento = mainPayment.getStPagamento();
										
					isCondizionePagabile = PosizioneDebitoriaHelper.isCondizionePagabile(statoCondizione, statoPagamento);
					
					if (LOGGER.isDebugEnabled()) 
						LOGGER.debug("verifico la condizione: " + condizione.getIdCondizione() + "stato condizione: " + statoCondizione + " stato pagamento: " + statoPagamento);
												
				}
				
			} catch (Exception e) {
				
				Tracer.error(this.getClass().getName(), "checkCondizionePagabile", e.getMessage(), e);
				
				e.printStackTrace();
				
				new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
			
			}
		
		    // se ne trovo anche una non pagabile ritorno false
			if (!isCondizionePagabile) {
				
				esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000003);
				
				if (statoPagamento != null) {
					
					if (statoPagamento.equals(StatiPagamentiIris.IN_CORSO.getPagaMapping())) {
												
						esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000005);
					
					}
					
					if (statoPagamento.equals(StatiPagamentiIris.ESEGUITO.getPagaMapping())||statoPagamento.equals(StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping()))
						
						esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000007);					
				
				}
				
			} else {
				
				boolean pagabilePerTermini = PaymentConditionStatusCalculator.checkValidita(condizione.getDtFinevalidita(), condizione.getDtIniziovalidita());
				
				if (!pagabilePerTermini)

					esitoCondDTO.setReturnCode(EnumBusinessReturnCodes.A0000004);
				
			}
		
		return dtoOut;
		
	}
	
	@Override
	public EsitoOperazionePagamentoDTO checkCondizioniPagabili(EsitoOperazionePagamentoDTO dtoOut,List<CondizionePagamento> listaCondizioni) {
		
		String statoPagamento = null;
		
		for (CondizionePagamento condizione : listaCondizioni) {
			
			boolean isCondizionePagabile = false;

			try {
				
					if (condizione != null){
						
						String statoCondizione = condizione.getStPagamento();
						
						boolean ratapagabile = PaymentConditionStatusCalculator.checkValidita(condizione.getDtFinevalidita(), condizione.getDtIniziovalidita());
						
						if (!ratapagabile)
							dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000004);
						
						Pagamenti mainPayment = BillItemInspector.getMainPayment(condizione);
						
						if (mainPayment != null)
							statoPagamento = mainPayment.getStPagamento();
											
						isCondizionePagabile = PosizioneDebitoriaHelper.isCondizionePagabile(statoCondizione, statoPagamento);
						
						if (LOGGER.isDebugEnabled()) 
							LOGGER.debug("verifico la condizione: " + condizione.getIdCondizione() + "stato condizione: " + statoCondizione + " stato pagamento: " + statoPagamento);
													
					}
					
				} catch (Exception e) {
					Tracer.error(this.getClass().getName(), "checkCondizioniPagabili", e.getMessage(), e);
					e.printStackTrace();
					new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
				}
			
			// se ne trovo anche una non pagabile ritorno false
			if (!isCondizionePagabile) {
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000003);
				 
				if (statoPagamento!=null){
					if (statoPagamento.equals(StatiPagamentiIris.ESEGUITO.getPagaMapping())
					  ||statoPagamento.equals(StatiPagamentiIris.ESEGUITO_SBF.getPagaMapping())){
						dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000007);
					}
				}
			}
		}
		
		return dtoOut;
		
	}
	
	
	/**
	 * @param dtoOut
	 * @param listaCondizioni
	 * @param richDTO
	 * @return
	 */
	private EsitoOperazionePagamentoDTO checkPaymentInProgress(EsitoOperazionePagamentoDTO dtoOut, List<CondizionePagamento> listaCondizioni, RichiestaAutorizzazioneDTO richDTO ) {
				
		Pagamenti mainPayment = null;		
		for (CondizionePagamento condizione : listaCondizioni) {
			try {				
					if (condizione != null) {						
						mainPayment = BillItemInspector.getMainPayment(condizione);						
						if (mainPayment != null && mainPayment.getFlussoDistinta() != null) {							
							condizione.setCodPagamentoFlusso(mainPayment.getFlussoDistinta().getCodPagamento());
							condizione.setCodPaganteFlusso(mainPayment.getFlussoDistinta().getUtentecreatore());
							condizione.setIdPagamentoFlusso(mainPayment.getFlussoDistinta().getId().toString());						
						}	
									
						String statoPagamento = mainPayment != null? mainPayment.getStPagamento() : null;
						
						if (StatiPagamentiIris.IN_CORSO.getPagaMapping().equals(statoPagamento)){							
							GestioneFlussi mainPaymentFlow = mainPayment.getFlussoDistinta();							
							if (!mainPaymentFlow.getCodTransazione().equals(richDTO.getExtTransactionId())){							
								CfgGatewayPagamento canale = mainPaymentFlow.getCfgGatewayPagamento();
									
								boolean annullaPagamentiInCorsoStessoCanale = richDTO.isAnnullaPagamentiInCorsoStessoCanale();									
								if (annullaPagamentiInCorsoStessoCanale && 
										                                richDTO.getTestata().getSenderSys().equals(canale.getSystemId()) 
										                                &&
										                                richDTO.getTestata().getSenderSil().equals(canale.getApplicationId())
										                                ){
									try {																	
									   Long idDistinta=mainPayment.getFlussoDistinta().getId();							
									   annullaPagamentoBean.annullaPagamentoPrecedente(idDistinta);
									} catch (RuntimeException r) {
                                       dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000017);											
			    						return dtoOut;
									}
									dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000011);
									return dtoOut;									
								} else {
									dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000017);											
									return dtoOut;
								}
							
							} else {
								
								dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000005);
								
								if (LOGGER.isDebugEnabled())
									LOGGER.debug("condizione: " + condizione.getIdCondizione() + "stato condizione: " + condizione.getStPagamento() + " stato pagamento: " + statoPagamento);
								
								return dtoOut;
							}
							
						} 
			            	
						if (LOGGER.isDebugEnabled())
							LOGGER.debug("verificata la condizione: " + condizione.getIdCondizione() + "stato condizione: " + condizione.getStPagamento() + " stato pagamento: " + statoPagamento);
							
					}
					
				} catch (Exception e) {
					Tracer.error(this.getClass().getName(), "checkCondizionePagabile", e.getMessage(), e);
					e.printStackTrace();
					new ManageBackEndException().processBusinessException(e, BackEndMessage.BI_0001);
				}
			
		}
		
		return dtoOut;
		

	}

	private EsitoOperazionePagamentoDTO isPendenzeCoerenti(EsitoOperazionePagamentoDTO dtoOut, RichiestaAutorizzazioneDTO dto) {
		
		List<PendenzaDTO> listPendenze = dto.getPendenze();
		
		for (PendenzaDTO pendenzaDTO : listPendenze) {
			
			if (pendenzaDTO.getIdPendenzaEnte() == null || pendenzaDTO.getIdPendenzaEnte().equals("")) {
				
				if (LOGGER.isInfoEnabled()) 
					LOGGER.info("id pendenza in input nullo o vuoto");
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000019);

				return dtoOut;
			}
			
			if (pendenzaDTO.getIdEnte() == null || pendenzaDTO.getIdEnte().equals("")) {
				
				if (LOGGER.isInfoEnabled()) 
					LOGGER.info("id ente in input nullo o vuoto");
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000019);

				return dtoOut;
			}
			
			if (pendenzaDTO.getCdTrbEnte() == null || pendenzaDTO.equals("")) {
				
				if (LOGGER.isInfoEnabled()) 
					LOGGER.info("codice tributo ente in input nullo o vuoto");
				
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000019);

				return dtoOut;
			}
			
			List<String> listaCondizioni = pendenzaDTO.getCondizioni();
			
			for (String condizione : listaCondizioni) {
				
				if (condizione == null || condizione.equals("")) {
					
					if (LOGGER.isInfoEnabled()) 
						LOGGER.info("condizione in input nulla o vuoto");
					
					dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000019);
					
					return dtoOut;
				}
				
				dtoOut = checkPendenzaCondizioneCoerente(dtoOut,condizione,pendenzaDTO.getCdTrbEnte(),pendenzaDTO.getIdEnte(),pendenzaDTO.getIdPendenzaEnte());
			    
				if (!dtoOut.getCodice().equals(EnumBusinessReturnCodes.OK.getChiave())) 
					return dtoOut;
				
			}
		}
		return dtoOut;
	}

	private EsitoOperazionePagamentoDTO checkPendenzaCondizioneCoerente(EsitoOperazionePagamentoDTO dtoOut,String condizione, String codiceTributoEnte, String idEnte, String idPendenzaEnte) {
	
		try {
			CondizionePagamento condizionePagamento = condPagDao.getById(CondizionePagamento.class, condizione);
			if (condizionePagamento==null){
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000001);
				
				if (LOGGER.isDebugEnabled())
					LOGGER.debug("CONDIZIONE NON TROVATA ID: "+condizione);
				
				return dtoOut;
			}
			if (!(condizionePagamento.getCdTrbEnte().equals(codiceTributoEnte) && condizionePagamento.getEnte().getIdEnte().equals(idEnte) && condizionePagamento.getPendenza()
					.getIdPendenzaente().equals(idPendenzaEnte))) {
				dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000019);
			}
			
		} catch (Exception e) {
			
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("errore mentre recuperavo la condizione" + condizione);
				LOGGER.info(e.toString());
			}
			
			dtoOut.setReturnCode(EnumBusinessReturnCodes.KO_ERROREDB);
			
			return dtoOut;
		}
		return dtoOut;
	}
	
	/**
	 * 
	 * Controlli di molteplicitï¿½ e congruenza importi
	 *  
	 * @param dtoOut
	 * @param dtoIn
	 * @return
	 */
	private EsitoOperazionePagamentoDTO checkListaCondizioniCBILL(EsitoOperazionePagamentoDTO dtoOut, RichiestaAutorizzazioneDTO dtoIn) {
		
    	List<CondizionePagamento> filteredList = new ArrayList<CondizionePagamento>();
    	
    	List<CondizionePagamento> retrieved = dtoOut.getCondizioni();
    	
    	if (retrieved == null || retrieved.isEmpty()){
    		
			dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000001);
			
			if (LOGGER.isDebugEnabled())
				LOGGER.debug("NESSUNA CONDIZIONE CORRISPONDE AI CRITERI DI RICERCA: " + dtoIn.getRichiestaCBILLDTO());
			
			return dtoOut;
		}
    	
    	BigDecimal importoPagam = dtoIn.getRichiestaCBILLDTO().getImportoPagamento();
    	
    	for(CondizionePagamento cond : retrieved){
    		
    		if (!cond.isCBILLIdsCompatible()){
    			
    			dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000003);
    			
    			if (LOGGER.isDebugEnabled()) 
    				LOGGER.debug("CONDIZIONI TROVATE MA CON ID NON IDONEI A CBILL : " + dtoIn.getRichiestaCBILLDTO());
    			
    			return dtoOut;
    		}
    			
    		BigDecimal importoCondizione=cond.getImTotale();
    			
    		if (importoCondizione.compareTo(importoPagam)==0)
            	filteredList.add(cond);
    	}
    	
        dtoOut.setCondizioni(filteredList);
    	
    	if (filteredList.isEmpty()){
			dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000020);
			
			if (LOGGER.isDebugEnabled()) 
				LOGGER.debug("CONDIZIONI TROVATE MA NON CORRISPONDENTI AI CRITERI DI RICERCA : " + dtoIn.getRichiestaCBILLDTO());
			
			return dtoOut;
		}
    	
        if (filteredList.size() > 1){
        	
        	dtoOut.setReturnCode(EnumBusinessReturnCodes.A0000021);
			
			if (LOGGER.isDebugEnabled()) 
				LOGGER.debug("PIU' CONDIZIONI CORRISPONDENTI AI CRITERI DI RICERCA: "+ dtoIn.getRichiestaCBILLDTO());
			
			return dtoOut;
        	
        }
        
        // Se arrivo qui ho esattamente una condizione
        dtoOut.setCausale(filteredList.get(0).getPendenza().getDeCausale());
        dtoOut.setNote(filteredList.get(0).getPendenza().getNote());
        
        dtoOut.setReturnCode(EnumBusinessReturnCodes.OK);
        
		return dtoOut;
	}

	/**
	 * @param strMulta
	 * @return
	 */
	public Map<String, String> getEstremiMulta(String strMulta) {
		Map<String, String> elemMulta = new HashMap<String, String>();

		String verbaleStrip = "Verbale ";
		String annoStrip = "Anno";
		String serieStrip = "Serie";
		String dataVerbaleStrip = "Data Verbale";
		String TargaStrip = "Targa Veicolo";
		StrTokenizer st;
		String[] elementiMulta;

		try {
			st = new StrTokenizer(strMulta, ";");

			elementiMulta = st.getTokenArray();
			String tmp;

			for (int i = 0; i < elementiMulta.length; i++) {

				switch (i) {
				case 0:
					tmp = StringUtils.substringAfter(elementiMulta[i], verbaleStrip);
					elemMulta.put(VERBALE_IDX, tmp.trim());
					break;
				case 1:
					tmp = StringUtils.substringAfter(elementiMulta[i], annoStrip);
					elemMulta.put(ANNO_IDX, tmp.trim());
					break;
				case 2:
					tmp = StringUtils.substringAfter(elementiMulta[i], serieStrip);
					elemMulta.put(SERIE_IDX, tmp.trim());
					break;
				case 3:
					tmp = StringUtils.substringAfter(elementiMulta[i], dataVerbaleStrip);
					elemMulta.put(DATA_IDX, tmp.trim());
					break;
				case 4:
					tmp = StringUtils.substringAfter(elementiMulta[i], TargaStrip);
					elemMulta.put(TARGA_IDX, tmp.trim());
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Formato campo DE_CAUSALE errato : " + strMulta, e);
			elemMulta = null;
		}

		return elemMulta;
	}

	private BigDecimal getTotImporti(List<CondizionePagamento> listacondizioni) {
		
		BigDecimal totImporti = BigDecimal.ZERO;
		
		for (CondizionePagamento condizionePagamento : listacondizioni) {
			
			totImporti = totImporti.add(condizionePagamento.getImTotale());
		}
		
		return totImporti;
	}
	

	private void formatCausale(EsitoOperazionePagamentoDTO dtoOut) {

		CondizionePagamentoDTO condDTO = dtoOut.getEsitiCondizioni().get(0).getCondizione();

		String causalePendenza = condDTO.getCausalePendenza();
		String idPendenza = condDTO.getIdPendenza();
		String cdTrbEnte = condDTO.getCdTrbEnte();

		TributoEnte tributoEnte = tributoEnteDao.getTributiEntiByKey(condDTO.getEnte().getId(), cdTrbEnte);
		CfgTributoEntePlugin cfgPlugin = tributoEnte.getCfgTributoEntePlugin();

		if (cfgPlugin != null && PluginReceiptUtil.exists_plugin(cfgPlugin.getCdPlugin())) {

			TributoStrutturato tributo = (TributoStrutturato) pendenzaDao.getTributoStrutturatoByIdPendenza(idPendenza);
			if (tributo != null) {
				Map<String, String> map = PluginReceiptUtil.getDetails(tributo, condDTO.getEnte().getId(), cdTrbEnte, Locale.ITALY);
				if (map != null) {
					condDTO.setCausalePendenza(map.toString().replace("=", ": ").replace("}", "").replace("{", ""));
				}
			}

		} else {
			condDTO.setCausalePendenza(PluginReceiptUtil.getCausaleFormatted(cdTrbEnte, causalePendenza));
		}
	}
	
	private void formatCausalePSP(EsitoOperazionePagamentoDTO dtoOut) {
		int maxLen = 140;
		
		if (dtoOut.getEsitiCondizioni()!=null && !dtoOut.getEsitiCondizioni().isEmpty()){
			CondizionePagamentoDTO condDTO = dtoOut.getEsitiCondizioni().get(0).getCondizione();
			
			if (condDTO != null) {
				String causalePendenza = condDTO.getCausalePendenza();
				String idPendenza = condDTO.getIdPendenza();
				String cdTrbEnte = condDTO.getCdTrbEnte();
				String idEnte = condDTO.getEnte().getId();
			
				String causale = null;
				if (condDTO.getDescrizioneDebito()!=null)
					causale = condDTO.getDescrizioneDebito() + " ";
				else
					causale = new String();
				
				TributoEnte tributoEnte = tributoEnteDao.getTributiEntiByKey(idEnte, cdTrbEnte);
				CfgTributoEntePlugin cfgPlugin = tributoEnte.getCfgTributoEntePlugin();

				if (cfgPlugin != null && PluginReceiptUtil.exists_plugin(cfgPlugin.getCdPlugin())) {
					
					TributoStrutturato tributo = (TributoStrutturato) pendenzaDao.getTributoStrutturatoByIdPendenza(idPendenza);
					if( tributo != null ){
						
						Map<String, String> map = PluginReceiptUtil.getDetails(tributo, idEnte , cdTrbEnte, Locale.ITALY);
						if (map != null) {
							causale += map.toString().replace("=", ": ").replace("}", "").replace("{", "");
						}
					
					}					
					
				}   else 
					causale += (PluginReceiptUtil.getCausaleFormatted(cdTrbEnte, causalePendenza));
					// 140 caratteri
				condDTO.setCausalePendenza(causale.length() > maxLen ? causale.substring(0, maxLen) : causale);
			}
		}
	}
	
	/*
     * 
     */
	protected String calcolaIbanBeneficiario(CondizionePagamento condPag, CfgGatewayPagamento cfgPagamento) {
		TributoEnte tribEnte = condPag.getPendenza().getTributoEnte();
		String ibanBeneficiario = condPag.getIbanBeneficiario();
		return 	calcolaIbanBeneficiario(tribEnte, ibanBeneficiario, cfgPagamento);
	}
	
	/*
     * 
     */
	protected String calcolaIbanBeneficiario(TributoEnte tribEnte, String ibanBeneficiario, CfgGatewayPagamento cfgPagamento) {
		
		try {
		
			if (tribEnte.getIBANContoTecnico() != null && !tribEnte.getIBANContoTecnico().trim().equals(""))
				return tribEnte.getIBANContoTecnico();

			return tribEnte.getIBAN();
		} catch (Throwable t) {
			t.printStackTrace();
		}

		return null;
	}
	
	private EnumBusinessReturnCodes remappedFaultFromAutorizzazionePagamentoWS(FaultType fault){
		
		String errorCode = fault.getFaultCode();
		if (errorCode==null)
			return EnumBusinessReturnCodes.KO_GENERICO;
		
		EnumAutorizzazionePagamentoReturnCode returnErrorCodeKey = EnumAutorizzazionePagamentoReturnCode.getByKey(errorCode);
		
		switch (returnErrorCodeKey){
			
				case PAA_PAGAMENTO_SCONOSCIUTO:  return EnumBusinessReturnCodes.A0000001;
				
				case PAA_PAGAMENTO_DUPLICATO:  return EnumBusinessReturnCodes.A0000022;
				
				case PAA_PAGAMENTO_ANNULLATO:  return EnumBusinessReturnCodes.A0000011;
				
				case PAA_PAGAMENTO_IN_CORSO :  return EnumBusinessReturnCodes.A0000005;
				
				case PAA_PAGAMENTO_SCADUTO: return EnumBusinessReturnCodes.A0000004;
				
				case PAA_ATTIVA_RPT_IMPORTO_NON_VALIDO:  return EnumBusinessReturnCodes.A0000020;
				
				default: return EnumBusinessReturnCodes.KO_GENERICO;
			}
		
	}

}