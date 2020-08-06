package it.tasgroup.idp.billerservices.jobs;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumEsitoAllineamentoPendenza;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni.EnumStatoTrasmissione;
import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.billerservices.api.plugin.DatiPiazzaturaFlusso;
import it.tasgroup.idp.billerservices.api.plugin.GestorePlugin;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.DatiAllineamento;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.EsitoCaricamentoPendenza;
import it.tasgroup.idp.billerservices.api.plugin.ILoaderPlugin.EsitoValidazione;
import it.tasgroup.idp.billerservices.jobs.status.AllineamentoPendenzeJobStatus;
import it.tasgroup.idp.billerservices.jobs.status.AllineamentoPendenzeJobStatus.EnumEsito;
import it.tasgroup.idp.billerservices.jobs.status.AllineamentoPendenzeJobStatus.EnumFaultSeverity;
import it.tasgroup.idp.billerservices.jobs.status.AllineamentoPendenzeJobStatus.Fault;
import it.tasgroup.idp.billerservices.util.ObjectToXml;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.messaggi.ErroriEsitiPendenza;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AllineamentoPendenzeJobImpl implements AllineamentoPendenzeJob {

	private final Log logger = LogFactory.getLog(this.getClass());

//	"IdpJtaXA"
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta) 
	private EntityManager em;

	@Resource
	private EJBContext ctx;

    @Resource
    private TimerService timerService;
	
    @EJB(beanName = "AllineamentoPendenzeJobTransactionsImpl")
    private AllineamentoPendenzeJobTransactions transact; 
    
   
	@Override
	public DatiPiazzaturaFlusso piazzaturaFlusso(String inputFile, String tipoFile) throws LoaderException  {
		ByteArrayInputStream inputFileAsStream = new ByteArrayInputStream(inputFile.getBytes());
		return this.piazzaturaFlusso(inputFileAsStream, tipoFile);
	}
	
	@Override
	public DatiPiazzaturaFlusso piazzaturaFlusso(InputStream inputFile, String tipoFile) throws LoaderException  {
		
		logger.info("Inizio Piazzatura Flusso");
		// -----------------------------------------------------------
		// Pre analisi del flusso per estrarre i dati di piazzatura 
		// L'analisi � delegata al plugin specifico
		// -----------------------------------------------------------
		ILoaderPlugin plugin=GestorePlugin.creaPlugin(inputFile, tipoFile);
		DatiPiazzaturaFlusso datiPiazzaturaFlusso;
		try {
			datiPiazzaturaFlusso = plugin.piazzaturaFlusso();
		} catch (ValidationException e) {
			throw new LoaderException(e.getErrorCode(),e.getDescription(),e);
		}
		
		// -----------------------------------------------------------
		// Completamento e validazione  dei dati di piazzatura
		// -----------------------------------------------------------
		logger.info("Verifiche dati piazzatura");
		
		logger.info("Validazione del SenderId");
		if (datiPiazzaturaFlusso.senderId==null) {
			logger.info("Sender Id non trovato nel flusso");
			throw new LoaderException(EnumReturnValues.SENDER_ID_NON_PRESENTE,"Nel file non � stato trovato il parametro senderId");
		}
		
		logger.info("Trovato SenderId = "+datiPiazzaturaFlusso.senderId);
		Enti ente = GestoreVerificheAnagrafiche.getEnteByCodEnte(datiPiazzaturaFlusso.senderId,em);
		//Intestatari intestatarioEnte = GestoreVerificheAnagrafiche.getIntestatarioById(ente.getIntestatario(), em);  // Qui non mi aspetto errori, per costruzione del DB.
		logger.info("SenderId = "+datiPiazzaturaFlusso.senderId+" validato. Esiste ed e' attivo");
		
		datiPiazzaturaFlusso.descrizioneEnte = ente.getDenom();
		//datiPiazzaturaFlusso.abiEnte = intestatarioEnte.getAbi();  
		datiPiazzaturaFlusso.idEnte=ente.getIdEnte();
		
		logger.info("Validazione del TipoDebito");
		if ( datiPiazzaturaFlusso.tipoDebito==null) {
			logger.info("TipoDebito non trovato nel flusso");
			throw new LoaderException(EnumReturnValues.TIPO_DEBITO_NON_PRESENTE,"Nel file deve essere specificato il tipo debito");
		}
		logger.info("Trovato TipoDebito = "+datiPiazzaturaFlusso.tipoDebito);
		TributiEnti tributoEnte = GestoreVerificheAnagrafiche.getTributoEnteByIdEnteCodTributo(ente.getIdEnte(), datiPiazzaturaFlusso.tipoDebito, em);
		logger.info("TipoDebito = "+datiPiazzaturaFlusso.tipoDebito+" validato. Esiste ed e' attivo");
		
		datiPiazzaturaFlusso.senderSys = tributoEnte.getIdSystem();
		datiPiazzaturaFlusso.idTributo = tributoEnte.getJlttrpa().getIdTributo();
		
		logger.info("Fine Piazzatura Flusso");
		
		return datiPiazzaturaFlusso;
		
	}
	
	@Override
	public void registrazioneTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, String file) {
		transact.registrazioneTrasmissione(datiPiazzaturaFlusso, file);  
	}

	@Override
	public void registrazioneTrasmissione(DatiPiazzaturaFlusso datiPiazzaturaFlusso, InputStream file) {
		//TODO: implementare
		throw new UnsupportedOperationException("Metodo non ancora implementato");
		
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public AllineamentoPendenzeJobStatus doJob(DatiPiazzaturaFlusso datiPiazzaturaFlusso)  {

			// ------------------------------------------------------
			// Step 0: Recupero dati della trasmissione
			// ------------------------------------------------------
		
			PendenzeCartPK pk = new PendenzeCartPK();
			pk.setE2emsgid(datiPiazzaturaFlusso.e2eMsgId);
			pk.setSenderid(datiPiazzaturaFlusso.senderId);
			pk.setSendersys(datiPiazzaturaFlusso.senderSys);
			PendenzeCart trasmissione = em.find(PendenzeCart.class, pk);

			//TODO: se non c'� ?
		
			// ------------------------------------------------------
			// Step 1: Validazione del messaggio
			// ------------------------------------------------------
			
			EsitoValidazione esitoValidazione;
			ILoaderPlugin plugin=null;
			
			try {
			
				logger.info("Inizio Validazione messaggio");
				InputStream file = GestoreTrasmissioni.getTrasmissioneAsStream(datiPiazzaturaFlusso.senderId, datiPiazzaturaFlusso.senderSys, datiPiazzaturaFlusso.e2eMsgId, em);			
				
				logger.info("Instanziazione del plugin '"+datiPiazzaturaFlusso.tipoFile+"'");
				plugin = GestorePlugin.creaPlugin(file,datiPiazzaturaFlusso.tipoFile);
				
				esitoValidazione=plugin.validazioneFlusso(datiPiazzaturaFlusso, 100, em);  //TODO: prendere limite da configurazione
		
				logger.info("Esito validazione:");
				logger.info(esitoValidazione.msgEsito);

			} catch (LoaderException e) {
				//TODO: gestire
				throw new RuntimeException(e);
			}
			
			if (!esitoValidazione.ok) {
				logger.info("Validazione KO: Persistenza immediata dell'esito in ESITI_CART");
				
				transact.registrazioneEsitoTrasmissione(datiPiazzaturaFlusso, esitoValidazione.msgEsito, EnumStatoTrasmissione.VALIDATO_KO_LOADER);
				
				AllineamentoPendenzeJobStatus exit = new AllineamentoPendenzeJobStatus();
				exit.setEsito(EnumEsito.KO);
				exit.setTotalePagamenti(datiPiazzaturaFlusso.numeroPosizioni);
				exit.setTotalePagamentiCaricati(0);
				logger.info("Metodo 'doJob' concluso");
				return exit;
			}

			
			// Se va bene, evolvo lo stato della trasmissione in "Validato"
			transact.cambioStatoTrasmissione(datiPiazzaturaFlusso, EnumStatoTrasmissione.VALIDATO_OK_LOADER);
			
			// ------------------------------------------------------
			// Step 2: Elaborazione del messaggio
			// ------------------------------------------------------

			
			AllineamentoPendenzeJobStatus exitStatus = new AllineamentoPendenzeJobStatus();
			exitStatus.setEsito(EnumEsito.OK); // Esito OK a meno che non intervengano problemi che fanno abortire il processing

			
			logger.info("Inizio Elaborazione messaggio");
			
			// Processo le pendenze una alla volta
			
			plugin.moveFirst();
						
			int posizioniElaborateConSuccesso=0;
			long progressivo=0;
			
			while (true) {
				
				EsitiPendenza esito = null;
				ErroriEsitiPendenza errore = null;	

				try {

					progressivo++;
					
					DatiAllineamento datiAllineamento = plugin.elaboraNextPendenza(datiPiazzaturaFlusso,progressivo);

					if (datiAllineamento==null) {
						// raggiuntoEOL
						break;
					}
	
					esito = new EsitiPendenza();
					// Preparo esito da persistere
					esito.setPendenzeCart(trasmissione);
					esito.setIdEsitoPendenza(GeneratoreIdUnivoci.GetCurrent().generaId(null));
					esito.setIdPendenza(datiAllineamento.pendenzaDaAllineare.getIdPendenza());
					esito.setIdPendenzaEnte(datiAllineamento.pendenzaDaAllineare.getIdPendenzaente());
					esito.setStato("COMPLETO");
					esito.setPrVersione((int)progressivo);
					esito.setTsInserimento(new Timestamp(System.currentTimeMillis()));
					esito.setOpInserimento("LOADER");

					
					// Allineo la pendenza, in transazione separata
					EnumEsitoAllineamentoPendenza esitoAllineamento = transact.allineamentoPendenza(datiAllineamento);

					// Un'uscita senza eccezioni equivale ad un allineamento correttamente eseguito
					posizioniElaborateConSuccesso++;

					// Completamento dei dati esito
					if (EnumEsitoAllineamentoPendenza.SKIPPED.equals(esitoAllineamento)) {
						esito.setEsitoPendenza("WARN");
					} else {
						esito.setEsitoPendenza("OK");
					}
					
					esito.setDescrizioneEsito(esitoAllineamento.toString());
					

					
 				} catch (IOException e1) {
 					logger.error(e1);
 					exitStatus.setEsito(EnumEsito.KO);
 					Fault f = new Fault();
 					f.faultCode=EnumReturnValues.ERRORE_GENERICO;
 					f.faultDescription = e1.getMessage();
 					f.faultSeverity=EnumFaultSeverity.ERRORE;
 					exitStatus.getFaults().add(f);
 					
 					// esco dal ciclo:
 					break;
				}  
				catch (ValidationException e) {
					// Non dovrebbe succedere, il flusso e' stato validato interamente.
					logger.error(e.getMessage(),e);
					esito.setEsitoPendenza("KO");
					esito.setDescrizioneEsito(e.getDescription());
					errore = new ErroriEsitiPendenza();
					errore.setCodice(e.getErrorCode().toString());
					errore.setDescrizioneErrore(e.getDescription());
					errore.setIdErrore(GeneratoreIdUnivoci.GetCurrent().generaId(null));
					errore.setIdEsitoPendenza(esito);
					errore.setIdPendenza(esito.getIdPendenza());
					errore.setIdPendenzaEnte(esito.getIdPendenzaEnte());

				}
				catch (LoaderException e) {
					logger.error(e.getMessage(),e);
					esito.setEsitoPendenza("KO");
					esito.setDescrizioneEsito(e.getDescription());
					errore = new ErroriEsitiPendenza();
					errore.setCodice(e.getErrorCode().toString());
					errore.setDescrizioneErrore(e.getDescription());
					errore.setIdErrore(GeneratoreIdUnivoci.GetCurrent().generaId(null));
					errore.setIdEsitoPendenza(esito);
					errore.setIdPendenza(esito.getIdPendenza());
					errore.setIdPendenzaEnte(esito.getIdPendenzaEnte());

				}
				catch (Exception e1) {
					logger.error(e1.getMessage(),e1);
					esito.setEsitoPendenza("KO");
					esito.setDescrizioneEsito(e1.getMessage());
					errore = new ErroriEsitiPendenza();
					errore.setCodice(EnumReturnValues.ERRORE_GENERICO.toString());
					errore.setDescrizioneErrore(e1.getMessage());
					errore.setIdErrore(GeneratoreIdUnivoci.GetCurrent().generaId(null));
					errore.setIdEsitoPendenza(esito);
					errore.setIdPendenza(esito.getIdPendenza());
					errore.setIdPendenzaEnte(esito.getIdPendenzaEnte());
					
				} finally {

					if (esito!=null) {
						transact.persistEsitoPendenza(esito, errore);
					}
					 
				}
			}
			
			exitStatus.setTotalePagamenti((int)progressivo-1);
			exitStatus.setTotalePagamentiCaricati(posizioniElaborateConSuccesso);
			
			
			// -----------------------------------------------
			// Step3: Creazione dell'esito dell'elaborazione
			// -----------------------------------------------
			// A questo punto ho calcolato ed inserito in esiti_pendenza tutti gli esiti di ogni elaborazione
			// posso quindi procedere alla composizione dell'esito finale nel quale riporteremo solo gli errori o i
			// warning. (SKIP).
			
			String includeSuccess =IrisProperties.getProperty("includes.result.ok.esiti","false");
			
			List<EsitiPendenza> esitiTrasmissione = GestoreTrasmissioni.getEsitiTrasmissione(datiPiazzaturaFlusso.senderId, 
					                                                                         datiPiazzaturaFlusso.senderSys, 
					                                                                         datiPiazzaturaFlusso.e2eMsgId, 
					                                                                         true /*includeErrors*/, 
					                                                                         true /*includeWarnings*/, 
					                                                                         includeSuccess.equals("true") /*includeSuccess*/, 
					                                                                         em);
			
			List<EsitoCaricamentoPendenza> esitiCaricamento = new ArrayList<EsitoCaricamentoPendenza>();
			
			for (EsitiPendenza ep:esitiTrasmissione) {
				EsitoCaricamentoPendenza ec = new EsitoCaricamentoPendenza();
				ec.statoEsito=ep.getEsitoPendenza();
				ec.progressivo=ep.getPrVersione();
				ec.iuv = ep.getIuv();
				ec.idPagamento = ep.getIdPagamento();
				ec.idDebito=ep.getIdPendenzaEnte();
				ec.codiceErrore="";
				Set<ErroriEsitiPendenza> errori = ep.getErroriEsitiPendenzaCollection();
				if (errori!=null) {
					for (ErroriEsitiPendenza erroreEsitoPendenza: errori) {
						// Nell'esito metto solo il primo che trovo (anche perch� li ho costruiti con un solo errore)
						ec.codiceErrore=erroreEsitoPendenza.getCodice();
						ec.descrizioneEsito=erroreEsitoPendenza.getDescrizioneErrore();
					}
				}
				
				if ("".equals(ec.codiceErrore)) {
					ec.descrizioneEsito=ep.getDescrizioneEsito();
				}
				esitiCaricamento.add(ec);
				
			}
			

			// ----------------------------------------------------------------------------------
			// Step4: Marshalling dell'esito (al momento fatto in memoria e non in streaming su lob).
			// ----------------------------------------------------------------------------------
			ByteArrayOutputStream outstream = new ByteArrayOutputStream();
			plugin.marshallEsitoOnStream(esitiCaricamento, outstream);
			
			// Inserimento dell'esito sulla tabella esiti_cart
			byte[] blobData = outstream.toByteArray();
			String msgEsito;
			try {
				msgEsito = new String(blobData,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage(),e);
				throw new RuntimeException(e);
			}
			transact.registrazioneEsitoTrasmissione(datiPiazzaturaFlusso, msgEsito, EnumStatoTrasmissione.ESITATO_LOADER);
	
			return exitStatus;
			
	}

	
	// Metodi "tecnici" per il lancio asincrono del job in fork
	
	@Override
	public void fork(DatiPiazzaturaFlusso datiPiazzaturaFlusso, long sleepBefore) {
		Timer timer = timerService.createTimer(sleepBefore, datiPiazzaturaFlusso);
	}

	@Override
	@Timeout
	public void excecuteJobAsynch(Timer timer) {
		DatiPiazzaturaFlusso datiPiazzaturaFlusso= (DatiPiazzaturaFlusso)timer.getInfo();
		System.out.println("Inizio elaborazione E2EMsgId= "+datiPiazzaturaFlusso.e2eMsgId);
		
		AllineamentoPendenzeJobStatus esitoElaborazione = this.doJob(datiPiazzaturaFlusso);	
		
		System.out.println("Done "+datiPiazzaturaFlusso.e2eMsgId+ " With Status: ");
		System.out.println(ObjectToXml.toXml(esitoElaborazione));
		
	}
	
}
