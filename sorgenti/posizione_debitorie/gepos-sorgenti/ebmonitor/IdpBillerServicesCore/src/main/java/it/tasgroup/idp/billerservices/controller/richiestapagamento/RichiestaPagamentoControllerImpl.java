package it.tasgroup.idp.billerservices.controller.richiestapagamento;

import it.tasgroup.idp.bean.IVerificaStatoPagamento;
import it.tasgroup.idp.bean.IVerificaStatoPagamento.VerificaPagamentoModelException;
import it.tasgroup.idp.billerservices.api.EnumReturnValues;
import it.tasgroup.idp.billerservices.api.GestorePendenze;
import it.tasgroup.idp.billerservices.api.GestoreTrasmissioni;
import it.tasgroup.idp.billerservices.api.GestoreVerificheAnagrafiche;
import it.tasgroup.idp.billerservices.api.LoaderException;
import it.tasgroup.idp.billerservices.api.ValidationException;
import it.tasgroup.idp.billerservices.plugin.csv.CSVRowPagamento.Debitore;
import it.tasgroup.idp.billerservices.plugin.xmlbillerservices.XmlBillerServicesToModelMapper;
import it.tasgroup.idp.domain.enti.Enti;
import it.tasgroup.idp.domain.enti.TributiEnti;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.posizioneDebitoria.Pendenze;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel.EnumStatoPagamentoDettagliato;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.xmlbillerservices.common.TipoOperazione;
import it.tasgroup.idp.xmlbillerservices.header.IdpOTF;
import it.tasgroup.idp.xmlbillerservices.pendenze.CondizionePagamento;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpAllineamentoPendenzeMultiOTFElementType;
import it.tasgroup.idp.xmlbillerservices.pendenze.IdpBody;
import it.tasgroup.idp.xmlbillerservices.pendenze.Pendenza;
import it.tasgroup.idp.xmlbillerservices.pendenze.Pendenza.InfoPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamento;
import it.tasgroup.idp.xmlbillerservices.ws.richiestapagamento.RichiestaPagamentoResponse;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class RichiestaPagamentoControllerImpl implements RichiestaPagamentoController {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)  
	private EntityManager em;
	
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@EJB(beanName = "RichiestaPagamentoControllerTransactionsImpl")
	RichiestaPagamentoControllerTransactions tx;
	
	@EJB(beanName = "VerificaStatoPagamento")
	private IVerificaStatoPagamento verificaStatoPagamentoBean;
	
	@Override
	public RichiestaPagamentoResponse richiestaPagamento(
			RichiestaPagamento request) {
		
			
			RichiestaPagamentoControllerEsitoBuilder esitoBuilder = new RichiestaPagamentoControllerEsitoBuilder();
		
			// -------------------------------------		
			// Gestione parametri
			// -------------------------------------
		
			Boolean ignoraPagamentiInCorso = false;
			if (request.getIdpAllineamentoPendenzeMultiOTF().getParametri()!=null) {
				if (request.getIdpAllineamentoPendenzeMultiOTF().getParametri().isAnnullaPagamentiInCorso()!=null) {
					ignoraPagamentiInCorso=request.getIdpAllineamentoPendenzeMultiOTF().getParametri().isAnnullaPagamentiInCorso();
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
			
			String TRTMsgId =  request.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getMsgId();
			String TRTSenderId =  request.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getSender().getSenderId();
			String TRTSenderSil =  request.getIdpAllineamentoPendenzeMultiOTF().getHeaderTRT().getSender().getSenderSys();
			
			PendenzeCart existingMsg= GestoreTrasmissioni.getTrasmissione(TRTSenderId, TRTSenderSil, TRTMsgId, em);
			
			// Se il messaggio è duplicato, esco subito.
			if (existingMsg!=null) { 
				RichiestaPagamentoResponse esito=esitoBuilder.buildEsitoKOInteroMessaggio(request,EnumReturnValues.MESSAGGIO_DUPLICATO);
				// tx.traceCallKO(allineamentoPendenzeOTF,esito); Non posso tracciare..  andrei in errore di duplicazione
				return esito;
			}

			
			Set<String> e2emsgIdSet  = new HashSet<String>();
			
			// Id pendenza deve essere univoco nel messaggio a parità di ente+tipo debito.
			// Id condizione deve essere univoco nel messaggio a parità di ente.
			
			Set<String> pendenzeKeySet = new HashSet<String>();  // Per controllare l'univocità delle pendenze costruisco stringhe composte da: SenderID-TipoDebito-IdPendenzaEnte. Non devono esistere duplicati nel messaggio.
			Set<String> condizioniKeySet = new HashSet<String>(); // Per controllare l'univocità delle condizioni costruisco stringhe composte da: SenderID-IdPagamentoEnte. Non devono esistere duplicati nel messaggio.
			
			
			XmlBillerServicesToModelMapper mapper  = new XmlBillerServicesToModelMapper();
			
			for(IdpAllineamentoPendenzeMultiOTFElementType msg:request.getIdpAllineamentoPendenzeMultiOTF().getIdpAllineamentoPendenzeOTF()) {

				// Univocità degli E2EMsgid nel messaggio
				String  e2emsgid = msg.getE2EMsgId();
				if (e2emsgIdSet.contains(e2emsgid)) {
					errorsMap.put(e2emsgid,EnumReturnValues.MESSAGGIO_DUPLICATO);
					break;
				} else {
					e2emsgIdSet.add(e2emsgid);
				}
				
				IdpBody body = msg.getIdpBody();
	
				for (Pendenza p:body.getPendenza()) {
									
					try {
						
						// Check anagrafiche
						EnumReturnValues esitoVerifiche = GestoreVerificheAnagrafiche.checkAnagrafica(msg.getE2ESender().getE2ESndrId(), msg.getE2ESender().getE2ESndrSys(), p.getTipoDebito(), em);
						if (!(EnumReturnValues.OK==esitoVerifiche)) {
							errorsMap.put(e2emsgid,esitoVerifiche);
							continue;
						}
						Enti ente = GestoreVerificheAnagrafiche.getEnteByCodEnte(msg.getE2ESender().getE2ESndrId(), em);
						TributiEnti tributo = GestoreVerificheAnagrafiche.getTributoEnteByIdEnteCodTributo(ente.getIdEnte(), msg.getIdpBody().getPendenza().get(0).getTipoDebito(), em);
						
						// Check specifici nel contesto OTF 
						controlliOTF( p, msg.getE2ESender().getE2ESndrId(), msg.getE2ESender().getE2ESndrSys(), ignoraPagamentiInCorso, pendenzeKeySet, condizioniKeySet,
								      request.getIdpAllineamentoPendenzeMultiOTF().getIdpOTF());
						
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
				
				RichiestaPagamentoResponse esito=esitoBuilder.buildEsitoKO(request,errorsMap, validationExceptionMap);
				tx.traceCallKO(request,esito);
				return esito;
			}
			
			
			RichiestaPagamentoResponse idpEsito = null;
			
			try {
				
				// ----------------------------------------------------------------------
				// Allineamento delle pendenze (in transazione separata) 
				// ----------------------------------------------------------------------
					
				idpEsito =  tx.insertAndOpenSession(request);
						
			
			} 		
			catch (LoaderException e ) {
				logger.error("Errore Generico",e);
				// Inserisco un errore "generico" per ogni messaggio contenuto nella chiamata
				for (String e2emsgid: e2emsgIdSet) {
					errorsMap.put(e2emsgid, e.getErrorCode());
				}
				idpEsito=esitoBuilder.buildEsitoKO(request,errorsMap, validationExceptionMap);
				tx.traceCallKO(request, idpEsito);
			} 
			catch (Exception e ) {
				logger.error("Errore Generico",e);
				// Inserisco un errore "generico" per ogni messaggio contenuto nella chiamata
				for (String e2emsgid: e2emsgIdSet) {
					errorsMap.put(e2emsgid, EnumReturnValues.ERRORE_GENERICO);
				}
				idpEsito=esitoBuilder.buildEsitoKO(request,errorsMap, validationExceptionMap);
				tx.traceCallKO(request, idpEsito);
				
			} 
			
			return idpEsito; 		
		
	}

	
	/**
	 * Controlli specifici da effettuarsi sul messaggio OTF.
	 * A differenza della validazione della pendenza che ha
	 * senso a prescindere dal metodo di allineamento.
	 * @param condizioniKeySet 
	 * @param pendenzeKeySet 
	 * @param p
	 */
	private void controlliOTF(Pendenza pendenza, String cdEnte, String sil, Boolean ignoraPagamentiInCorso, Set<String> pendenzeKeySet, Set<String> condizioniKeySet,IdpOTF idpOTF) throws ValidationException {
		
		
				// 0. Le pendenze devono essere univoche nel file.
		
				String pendenzaKey = cdEnte.trim()+"-"+pendenza.getTipoDebito().trim()+"-"+pendenza.getIdDebito().trim();
				
				if (pendenzeKeySet.contains(pendenzaKey)) {
					throw new ValidationException(EnumReturnValues.PENDENZA_DUPLICATA);
				} else {
					pendenzeKeySet.add(pendenzaKey);
				}
		

							
				// 1. Le pendenze devono avere tipoOperazione PAGAMENTO OTF.
				if(pendenza.getTipoOperazione()!=TipoOperazione.PAGAMENTO_OTF ) {
					throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"Sono consentite solo operazioni di inserimento (TipoOperazione = PagamentoOTF)"); //Era A0000020 
				} 
				
				
				//2. Le pendenze devono  avere esclusivamente tipo pagamento unico
//				if (pendenza.getInsert().getInfoPagamento().size()>1 || 
//					TipoPagamento.PAGAMENTO_UNICO!=pendenza.getInsert().getInfoPagamento().get(0).getTipoPagamento()) {
//						throw new ValidationException(EnumReturnValues.ERRORE_VALIDAZIONE_SEMANTICA,"E' obbligatorio specificare un unico elemento 'InfoPagamento' con tipo di pagamento 'Pagamento Unico'"); //Era A0000022 
//				} 

//				PendenzaInsertReplace insert = pendenza.getInsert();
//				InfoPagamento infoPagamento = insert.getInfoPagamento().get(0); 
				
				InfoPagamento infoPagamento = pendenza.getInfoPagamento();
				
					
				for (CondizionePagamento condizione:infoPagamento.getCondizionePagamento()) {
					
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
					input.setTipoPendenza(pendenza.getTipoDebito());
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
				//check E_BOLLO
				if ("E_BOLLO".equals(pendenza.getTipoDebito())) {
					if (pendenza.getDatiMBD()==null) {
						throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," DatiMBD non specificato in pendenza con tipoDebito='E_BOLLO' ");
					}
					if (pendenza.getDatiMBD().getHashDocumento()==null || pendenza.getDatiMBD().getHashDocumento().length==0) {
						throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," DatiMBD HashDocumento non specificato o non corretto ");
					}
					if (!"01".equals(pendenza.getDatiMBD().getTipoBollo())) {
						throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," DatiMBD TipoBollo deve essere  '01' ");
					}
					if ((pendenza.getImportoTotale().compareTo(new BigDecimal("16.00"))!=0)) {
						throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," Pendenza Importo Totale deve essere = 16.00 ");
					}
					//for (Debitore d: pendenza.getDebitori() ) {
					//	if (d.getProvincia()!=null && !pendenza.getDatiMBD().getProvinciaResidenzaDebitore().equals(d.getProvincia())) {
					//		throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," DatiMBD.ProvinciaResidenzaDebitore deve coincidere con Debitore.Provincia ");
					//	}
					//s}
					for (CondizionePagamento c: pendenza.getInfoPagamento().getCondizionePagamento()){
						if (c.getImportoPagamento().compareTo(new BigDecimal("16.00"))!=0) {
						throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," Condizione Importo Pagamento deve essere = 16.00 ");
					}
					}
					if (idpOTF!=null && idpOTF.isOFFLINEPAYMENTMETHODS()!=null) {
						if (idpOTF.isOFFLINEPAYMENTMETHODS().booleanValue()) {
							throw new ValidationException(EnumReturnValues.MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE," IdpOTF.OFFLINE_PAYMENT_METHODS deve essere 'false' in presenza di pagamenti contenenti Marche da Bollo Digitali ");
						}
					}
				}
			}

}
