package it.tasgroup.idp.billerservices.controller;

import it.tasgroup.idp.bean.IVerificaStatoPagamento;
import it.tasgroup.idp.bean.IVerificaStatoPagamento.VerificaPagamentoModelException;
import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestorePendenze;
import it.tasgroup.idp.billerservices.api.GestorePendenze.EnumTipoAllineamento;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni;
import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.billerservices.plugin.rfc.RfcToModelMapper;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.domain.posizioneDebitoria.DestinatariPendenze;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.iuvgenerator.IUVGeneratorLocal;
import it.tasgroup.idp.loader.TrasmissioneType;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel.EnumStatoPagamentoDettagliato;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris.domain.CarrelloGateway;
import it.tasgroup.iris.domain.CarrelloGatewayPK;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.domain.SessioneGateway;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.Destinatario;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.DettaglioPagamentoInsertReplace;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpAllineamentoPendenzeMultiOTFElementType;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpBody;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.Pendenza;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.PendenzaInsertReplace;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.PendenzaInsertReplace.InfoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esiti;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpBodyType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpMultiEsitoOTF;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpMultiEsitoOTFElement;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpOTFType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.E2EReceiver;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.E2ESender;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.HeaderE2E;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.HeaderTRT;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpOTF;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.TRTReceiver;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.TRTSender;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoOperazione;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeMultiEnteOTFEsito;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ComunicazionePosizioniDebitorieControllerImpl implements
		ComunicazionePosizioniDebitorieController {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	@EJB(beanName = "ComunicazionePosizioniDebitorieControllerTransactionsImpl")
	ComunicazionePosizioniDebitorieControllerTransactions tx;
	
	@EJB(beanName = "VerificaStatoPagamento")
	private IVerificaStatoPagamento verificaStatoPagamentoBean;
	
	@EJB(beanName = "IUVGenerator")
	private IUVGeneratorLocal iuvGenerator;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
		
	@Override
	public IdpAllineamentoPendenzeMultiEnteOTFEsito idpAllineamentoPendenzeMultiEnteOTF(
			IdpAllineamentoPendenzeMultiEnteOTF allineamentoPendenzeOTF) {

		ComunicazionePosizioniDebitorieControllerEsitoBuilder esitoBuilder = new ComunicazionePosizioniDebitorieControllerEsitoBuilder();
		
		// -------------------------------------		
		// Gestione parametri
		// -------------------------------------
		
		Boolean ignoraPagamentiInCorso = false;
		if (allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getParametri()!=null) {
			if (allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getParametri().isAnnullaPagamentiInCorso()!=null) {
				ignoraPagamentiInCorso=allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getParametri().isAnnullaPagamentiInCorso();
			}
		}
		
		// -------------------------------------		
		// Diagnosi messaggio in ingresso
		// -------------------------------------
		
		HashMap<String, EnumReturnValues> errorsMap = new HashMap<String, EnumReturnValues>(); 
		HashMap<String, ValidationException> validationExceptionMap = new HashMap<String, ValidationException>();
		
		// -------------------------------------		
		// Diagnosi header TRT
		// -------------------------------------
		
		String TRTMsgId =  allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getMsgId();
		String TRTSenderId =  allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getSender().getSenderId();
		String TRTSenderSil =  allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getSender().getSenderSys();
		
		PendenzeCart existingMsg= GestoreTrasmissioni.getTrasmissione(TRTSenderId, TRTSenderSil, TRTMsgId, em);
		
		// Se il messaggio è duplicato, esco subito.
		if (existingMsg!=null) { 
			IdpAllineamentoPendenzeMultiEnteOTFEsito esito=esitoBuilder.buildEsitoKOInteroMessaggio(allineamentoPendenzeOTF,EnumReturnValues.MESSAGGIO_DUPLICATO);
			// tx.traceCallKO(allineamentoPendenzeOTF,esito); Non posso tracciare..  andrei in errore di duplicazione
			return esito;
		}
		
		
		
		Set<String> e2emsgIdSet  = new HashSet<String>();
		
		// Id pendenza deve essere univoco nel messaggio a parità di ente+tipo debito.
		// Id condizione deve essere univoco nel messaggio a parità di ente.
		
		Set<String> pendenzeKeySet = new HashSet<String>();  // Per controllare l'univocità delle pendenze costruisco stringhe composte da: SenderID-TipoDebito-IdPendenzaEnte. Non devono esistere duplicati nel messaggio.
		Set<String> condizioniKeySet = new HashSet<String>(); // Per controllare l'univocità delle condizioni costruisco stringhe composte da: SenderID-IdPagamentoEnte. Non devono esistere duplicati nel messaggio.
		
			for(IdpAllineamentoPendenzeMultiOTFElementType msg:allineamentoPendenzeOTF.getIdpAllineamentoPendenzeMultiOTF().getIdpAllineamentoPendenzeOTF()) {

				// Univocità degli E2EMsgid nel messaggio
				String  e2emsgid = msg.getE2EMsgId();
				if (e2emsgIdSet.contains(e2emsgid)) {
					errorsMap.put(e2emsgid,EnumReturnValues.MESSAGGIO_DUPLICATO);
					break;
				} else {
					e2emsgIdSet.add(e2emsgid);
				}
				
				IdpBody body = msg.getIdpBody();
				
								
				RfcToModelMapper mapper = new RfcToModelMapper();
	
				for (Pendenza p:body.getPendenza()) {
					
					
					try {
						
						// Check anagrafiche
						EnumReturnValues esitoVerifiche = GestoreVerificheAnagrafiche.checkAnagrafica(msg.getE2ESender().getE2ESndrId(), msg.getE2ESender().getE2ESndrSys(), p.getTipoPendenza(), em);
						if (!(EnumReturnValues.OK==esitoVerifiche)) {
							errorsMap.put(e2emsgid,esitoVerifiche);
							continue;
						}
						Enti ente = GestoreVerificheAnagrafiche.getEnteByCodEnte(msg.getE2ESender().getE2ESndrId(), em);
						TributiEnti tributo = GestoreVerificheAnagrafiche.getTributoEnteByIdEnteCodTributo(ente.getIdEnte(), msg.getIdpBody().getPendenza().get(0).getTipoPendenza(), em);
						
						// Check specifici nel contesto OTF 
						controlliOTF( p, msg.getE2ESender().getE2ESndrId(), msg.getE2ESender().getE2ESndrSys(), ignoraPagamentiInCorso, pendenzeKeySet, condizioniKeySet);
						
						// Validazione semantica		
						Pendenze pendenzaModel = mapper.mapMessagePendenzaToModel(p, ente, tributo, msg.getE2ESender().getE2ESndrSys(), true);
						GestorePendenze.validaPendenza(pendenzaModel, true, em);
						
						
						// Controllo 
				

					} catch (LoaderException e) {
						errorsMap.put(e2emsgid,e.getErrorCode());
					} catch (ValidationException v) {
						validationExceptionMap.put(e2emsgid,v);
					}			
				}	
			}
		
		if (!errorsMap.isEmpty() || !validationExceptionMap.isEmpty()) {
			
			IdpAllineamentoPendenzeMultiEnteOTFEsito esito=esitoBuilder.buildEsitoKO(allineamentoPendenzeOTF,errorsMap, validationExceptionMap);
			tx.traceCallKO(allineamentoPendenzeOTF,esito);
			return esito;
		}
		
		
		IdpAllineamentoPendenzeMultiEnteOTFEsito idpEsito = null;
		
		try {
			
			// ----------------------------------------------------------------------
			// Allineamento delle pendenze (in transazione separata) 
			// ----------------------------------------------------------------------
				
			idpEsito =  tx.insertAndOpenSession(allineamentoPendenzeOTF);
					
		
		} 		
		catch (LoaderException e ) {
			logger.error(e);
			// Inserisco un errore "generico" per ogni messaggio contenuto nella chiamata
			for (String e2emsgid: e2emsgIdSet) {
				errorsMap.put(e2emsgid, e.getErrorCode());
			}
			idpEsito=esitoBuilder.buildEsitoKO(allineamentoPendenzeOTF,errorsMap, validationExceptionMap);
			tx.traceCallKO(allineamentoPendenzeOTF, idpEsito);
		} 
		catch (Exception e ) {
			logger.error(e);
			// Inserisco un errore "generico" per ogni messaggio contenuto nella chiamata
			for (String e2emsgid: e2emsgIdSet) {
				errorsMap.put(e2emsgid, EnumReturnValues.ERRORE_GENERICO);
			}
			idpEsito=esitoBuilder.buildEsitoKO(allineamentoPendenzeOTF,errorsMap, validationExceptionMap);
			tx.traceCallKO(allineamentoPendenzeOTF, idpEsito);
			
		} 
		
		return idpEsito; 
	}


	
	@Override
	public IdpAllineamentoPendenzeEnteOTFEsito idpAllineamentoPendenzeEnteOTF(
			IdpAllineamentoPendenzeEnteOTF allineamentoPendenzeOTF) {
		throw new UnsupportedOperationException("Metodo non implementato. Usare direttamente l'omomimo in ComunicazionePosizioneDebitoriaOTFImpl");
	}

	
	
	

	/**
	 * Controlli specifici da effettuarsi sul messaggio OTF.
	 * A differenza della validazione della pendenza che ha
	 * senso a prescindere dal metodo di allineamento.
	 * @param condizioniKeySet 
	 * @param pendenzeKeySet 
	 * @param p
	 */
	private void controlliOTF(Pendenza pendenza, String cdEnte, String sil, Boolean ignoraPagamentiInCorso, Set<String> pendenzeKeySet, Set<String> condizioniKeySet) throws ValidationException {
		
		
				// 0. Le pendenze devono essere univoche nel file.
		
				String pendenzaKey = cdEnte.trim()+"-"+pendenza.getTipoPendenza().trim()+"-"+pendenza.getIdPendenza().trim();
				
				if (pendenzeKeySet.contains(pendenzaKey)) {
					throw new ValidationException(EnumReturnValues.PENDENZA_DUPLICATA);
				} else {
					pendenzeKeySet.add(pendenzaKey);
				}
		

							
				// 1. Le pendenze devono avere tipoOperazione INSERT.
				if(pendenza.getTipoOperazione()!=TipoOperazione.INSERT || pendenza.getReplace()!=null ||
				   pendenza.getUpdateStatus()!=null || 
				   pendenza.getInsert()==null) {
					throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"Sono consentite solo operazioni di inserimento (TipoOperazione = Insert)"); //Era A0000020 
				} 
				
				
				//2. Le pendenze devono  avere esclusivamente tipo pagamento unico
				if (pendenza.getInsert().getInfoPagamento().size()>1 || 
					TipoPagamento.PAGAMENTO_UNICO!=pendenza.getInsert().getInfoPagamento().get(0).getTipoPagamento()) {
						throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"E' obbligatorio specificare un unico elemento 'InfoPagamento' con tipo di pagamento 'Pagamento Unico'"); //Era A0000022 
				} 

				PendenzaInsertReplace insert = pendenza.getInsert();
				InfoPagamento infoPagamento = insert.getInfoPagamento().get(0); 
				
				//3. [controllo eliminato rispetto a versione monoEnte] Le pendenze devono avere una sola condizione di pagamento (a meno che non siano cartelle di pagamento)
				
//				if ((pendenza.isCartellaDiPagamento()==null || !pendenza.isCartellaDiPagamento()) 
//						&& infoPagamento.getDettaglioPagamento().size()>1) {
//					throw new ValidationException(EnumReturnValues.ERRORE_SCHEMA_FILE,"E' obbligatorio specificare una sola condizione di pagamento per ogni pendenza"); //Era A0000021						} 
//				}						
					

				for (DettaglioPagamentoInsertReplace condizione:infoPagamento.getDettaglioPagamento()) {
					
					// 4. IdPagamento (condizione) univoca nel file a parità di ente
					String condizioneKey = cdEnte.trim()+"-"+condizione.getIdPagamento().trim();
					
					if (condizioniKeySet.contains(condizioneKey)) {
						throw new ValidationException(EnumReturnValues.CONDIZIONE_PAGAMENTO_DUPLICATA);
					} else {
						condizioniKeySet.add(condizioneKey);
					}

					
					// 5. Si accettano solo condizioni di pagamento pagabili alla data odierna (a differenza dei caricamenti massivi dove si
					//    possono caricare pendenze pagabili anche  in futuro).
					
					XMLGregorianCalendar inizio =  condizione.getDataInizioValidita();
					XMLGregorianCalendar fine =  condizione.getDataFineValidita(); 
					
					if (inizio !=null && fine!=null) {
						
						GregorianCalendar dataInizio =  inizio.toGregorianCalendar();
						GregorianCalendar dataFine =  fine.toGregorianCalendar();
						GregorianCalendar adesso = (GregorianCalendar) GregorianCalendar.getInstance();

						if (dataInizio.after(adesso) || 
							dataFine.before(adesso)) {
							throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"Le date di validita' del pagamento devono comprendere la giornata corrente"); //Era A0000036							
						}
					}	


					// 6. Verifico che la condizione  non sia già pagata e che non ci siano pagamenti "IN CORSO" associati.

					
					VerificaStatoPagamentoInput input = new VerificaStatoPagamentoInput();
					input.setIdPagamento(condizione.getIdPagamento());
					input.setTipoPendenza(pendenza.getTipoPendenza());
					input.setCdEnte(cdEnte);
					input.setSil(sil);

					VerificaPagamentoModel pmodel=null;
					try {
						pmodel = verificaStatoPagamentoBean.verificaPagamentoModel(input, true);

						if (pmodel.isRefreshData()) {
							logger.info("Richiesto aggiornamento stato posizione (in corso) -----------------");
							logger.info("Ricalcolo lo stato  -----------------");
							pmodel = verificaStatoPagamentoBean.verificaPagamentoModel(input, true);
						}
	
						logger.info("Esito Verifica -----------------");
						logger.info(pmodel.getStatoPagamento());
						logger.info(pmodel.getPagamento());
						logger.info(pmodel.getTipoPendenza());
						logger.info(pmodel.getDescrizioneStato());
						logger.info("Fine Esito Verifica -----------------");

					} catch (VerificaPagamentoModelException e) {
						throw new ValidationException(EnumReturnValues.ERRORE_GENERICO," Impossibile verificare se la posizione risulta gia' pagata ");
					}	

									
					if (pmodel.getStatoPagamento()==EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA ) {
						throw new ValidationException(EnumReturnValues.CONDIZIONE_PAG_ESEGUITO,"La condizione (dettaglioPagamento) con idPagamento='"+condizione.getIdPagamento()+"' risulta gia' pagata");
					}
					
					if (pmodel.getStatoPagamento()==EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA_SBF) {
						throw new ValidationException(EnumReturnValues.CONDIZIONE_PAG_ESEGUITO_SBF,"La condizione (dettaglioPagamento) con idPagamento='"+condizione.getIdPagamento()+"' ha gia' associato un pagamento eseguito in corso di perfezionamento ");					
					}

					if (pmodel.getStatoPagamento()==EnumStatoPagamentoDettagliato.POSIZIONE_CON_PAG_IN_CORSO && !ignoraPagamentiInCorso) {
						throw new ValidationException(EnumReturnValues.CONDIZIONE_PAG_IN_CORSO,"La condizione (dettaglioPagamento) con idPagamento='"+condizione.getIdPagamento()+"' ha gia' associato un pagamento in corso ");					
					}	
					
				}	
			}

	
}
