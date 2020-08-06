/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.cart3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.bean.IVerificaStatoPagamento.VerificaPagamentoModelException;
import it.tasgroup.idp.bean.ObjectCommandExecutor;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.notifiche.VerificaPagamentoModel.EnumStatoPagamentoDettagliato;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;
import it.tasgroup.idp.util.GeneratoreIdUnivoci;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.JAXBContextProvider;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.iris.domain.SessioneGateway;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.DettaglioPagamentoInsertReplace;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.IdpBody;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.InfoPagamentoInsertReplace;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.Pendenza;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.PendenzaInsertReplace;
import it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze.PendenzaInsertReplace.InfoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Dettaglio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esiti;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.Esito;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpBodyType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpEsitoOTF;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.IdpOTFType;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoDettaglio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.InfoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoDettaglio;
import it.toscana.rete.cart.servizi.iris_1_1.idpesito.StatoMessaggio;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpHeader;
import it.toscana.rete.cart.servizi.iris_1_1.idpheader.IdpOTF;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.Allegato;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.ContentType;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.MIMETypeCode;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoOperazione;
import it.toscana.rete.cart.servizi.iris_1_1.idpinclude.TipoPagamento;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTF;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;

public class CartWsImpl extends CommonOTFWs {
		
	private final Log logger = LogFactory.getLog(this.getClass());
	
	private CommandExecutorLocal dataStorageManagerProxy;	
	private ObjectCommandExecutor gestioneOTFManagerProxy;
	private ObjectCommandExecutor gestioneOTFReplaceManagerProxy;
	private ObjectCommandExecutor DecodeEntiTributiManagerProxy;	
	
	/**
	 * String e2emsgid,
			String senderId, String senderSys, List<EsitoPendenza> esitoDaSpedire)
	 */
	public IdpAllineamentoPendenzeEnteOTFEsito idpAllineamentoPendenzeEnteOTF(IdpAllineamentoPendenzeEnteOTF idpAllineamentoPendenzeOTF) {
		//monitoring data
		MonitoringData data = new MonitoringData();

		//business logic
		logger.info("Inside allineamento pendenze OTF (fase1)");
		logger.debug("OTF XML = \n " + idpAllineamentoPendenzeOTF.toString());
		
		boolean fase1 = true;
		boolean fase2 = true;
		boolean tipoOperazione = true;
		boolean condizNonUnica = true;
		boolean PagamNonUnic = true;			
		
		String e2emsgid = "";
		String id = "";
		String sil = "";

		IdpHeader header = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpHeader();
		IdpEsitoOTF esitoOtf = null;		
		List<Esito> listaErrori = new ArrayList<Esito>(); 
		
		//id eventuale per esito errato
		String idPendenza = "000";		
		List<BigDecimal> importi = new ArrayList<BigDecimal>();
		try {					
			//esiste il tag otf ?
			IdpOTF otf = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpOTF();
			 
			//leggo header
			e2emsgid = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpHeader().getE2E().getE2EMsgId();
			id = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpHeader().getE2E().getSender().getE2ESndrId();
			sil = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpHeader().getE2E().getSender().getE2ESndrSys();			
			
			/*
			 *  patch di assegnazione default a dataInizioValidita
			 *  la dataInizioValidita e' stata definita facoltativa nei wsdl/xsd
			 *  imponiamo la data di oggi come default #2978
			 */

			DatatypeFactory datatypeFactory = null;
			XMLGregorianCalendar xmlgc = null;
			try {
				datatypeFactory = DatatypeFactory.newInstance();
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(new Date());
				xmlgc = datatypeFactory.newXMLGregorianCalendar(gc);

				for (Pendenza pendenza : idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpBody().getPendenza()) {
					PendenzaInsertReplace insertReplace = pendenza.getInsert();
					if (insertReplace == null) {
						insertReplace = pendenza.getReplace();
					}
					if (insertReplace != null) {
						for (InfoPagamento infoPagamento : insertReplace.getInfoPagamento()) {
							for (DettaglioPagamentoInsertReplace dettaglioPagamento : infoPagamento.getDettaglioPagamento()) {
								if (dettaglioPagamento.getDataInizioValidita() == null) {
									dettaglioPagamento.setDataInizioValidita(xmlgc);
								}
							}
						}
					}
				}
			} catch (DatatypeConfigurationException dce) {
				logger.warn("Errore nell'assegnare il default alla DataInizioValidita", dce);
			}
			
			
			
			// ---------------------------------------------------------------
			// Verifiche di business su MBD,
			// ---------------------------------------------------------------
			boolean thereIsMarcaDaBollo = verificheEBollo(idpAllineamentoPendenzeOTF, listaErrori);
			
			
			//solo se c'� header OTF...
			//controllare che il tipo operazione sia INSERT
			//controllare che sia solo 'Pagamento Unico' 
			//controllare che esista una sola pendenza ed una sola condizione (solo quando non � cartella di pagamento)			
			
			if (otf!=null) {
				//controlli in caso di presenza OTF 
				IdpBody body = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpBody();
				List<Pendenza> listPend = body.getPendenza();			
				Iterator iterList = listPend.iterator();
				
				while (iterList.hasNext()) {
					Pendenza pendenza = (Pendenza) iterList.next();
					idPendenza = pendenza.getIdPendenza();
					if (!"Insert".equals(pendenza.getTipoOperazione().value())) {
						//se trovo una pendenza con tipo diverso da Insert
						//allora segnalo errore
						tipoOperazione = false;
						fase1 = false;
						addErrore(listaErrori, "A0000020","Sono consentite solo operazioni di inserimento (TipoOperazione = Insert)");
					} else {
						PendenzaInsertReplace insert =  pendenza.getInsert();			
						// ---------------------------------------------------------------------------------------------------
						// se esiste piu di una condizione di pagamento leggasi (InfoPagamento) allora segnalo errore
						// ---------------------------------------------------------------------------------------------------						
						if (insert.getInfoPagamento().size()>1) {
							condizNonUnica = false;
							fase1 = false;
//							addErrore(listaErrori, "A0000021","E' obbligatorio specificare una sola condizione di pagamento per ogni pendenza");
							addErrore(listaErrori, "A0000022","E' obbligatorio specificare un unico elemento 'InfoPagamento' con tipo di pagamento 'Pagamento Unico'");							
						} else  {
							//altrimenti controllo che sia Pagamento Unico
							if (!TipoPagamento.PAGAMENTO_UNICO.equals(insert.getInfoPagamento().get(0).getTipoPagamento())) {
								//se non � pagamento unico allora segnalo errore
								PagamNonUnic = false;
								fase1 = false;
//								addErrore(listaErrori, "A0000022","E' obbligatorio specificare la modalit� di pagamento 'Pagamento Unico'");
								addErrore(listaErrori, "A0000022","E' obbligatorio specificare un unico elemento 'InfoPagamento' con tipo di pagamento 'Pagamento Unico'");
							}
							//eliminato il controllo per poter gestire la cartella di pagamento
							else if ((pendenza.isCartellaDiPagamento()==null || !pendenza.isCartellaDiPagamento()) 
										&& insert.getInfoPagamento().get(0).getDettaglioPagamento().size()>1) {
								condizNonUnica = false;
								fase1 = false;
								//se non � una cartella di pagamento, ma � un OTF
								addErrore(listaErrori, "A0000021","E' obbligatorio specificare una sola condizione di pagamento per ogni pendenza");
								//in caso di cartella posso avere anche pi� condizioni
							} 
						}						
						
						// ---------------------------------------------------------------------------------------------------
						// devo controllare anche se ci sono una o piu DettaglioPagamento altrimenti segnalo errore come sopra !!
						// ---------------------------------------------------------------------------------------------------											
						List<InfoPagamento> listaInfoPag = insert.getInfoPagamento();
						Iterator iter = listaInfoPag.iterator();						
						while (iter.hasNext()) {
							InfoPagamento infoPag = (InfoPagamento) iter.next();		
							
							List<DettaglioPagamentoInsertReplace> listaDetta = infoPag.getDettaglioPagamento();
							Iterator iterDett = listaDetta.iterator();
							while (iterDett.hasNext()) {
								DettaglioPagamentoInsertReplace dettPag = (DettaglioPagamentoInsertReplace) iterDett.next();
								//controlla ste date adesso
								
								XMLGregorianCalendar inizio =  dettPag.getDataInizioValidita();
								XMLGregorianCalendar fine =  dettPag.getDataFineValidita();
								
								if (inizio !=null && fine!=null) {
									
									GregorianCalendar dataInizio =  inizio.toGregorianCalendar();
									GregorianCalendar dataFine =  fine.toGregorianCalendar();
									GregorianCalendar adesso = (GregorianCalendar) GregorianCalendar.getInstance();
									
									java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
									String dateString = formatter.format(dataInizio.getTime());
									String dateString1 = formatter.format(dataFine.getTime());
									String attuale = formatter.format(adesso.getTime());
									logger.info(" Data inizio " + dateString + " - data fine " + dateString1 + " Data attuale " + attuale);									
									
									if (dataInizio.after(adesso) || 
										dataFine.before(adesso)) {
										addErrore(listaErrori, "A0000036","Le date di validita' del pagamento devono comprendere la giornata corrente");
									}
								}
								
								// ---------------------------------------------------------------------------------------------------
								// Verifico che la condizione  non sia gi� pagata e che non ci siano pagamenti "IN CORSO" associati.
								// ---------------------------------------------------------------------------------------------------
								VerificaStatoPagamentoInput input = new VerificaStatoPagamentoInput();
								input.setIdPagamento(dettPag.getIdPagamento());
								input.setTipoPendenza(pendenza.getTipoPendenza());
								input.setCdEnte(id);
								input.setSil(sil);
								
								VerificaPagamentoModel pmodel=null;
								// ---------------------------------------------------------------------------------------------------
								// Verifico lo stato del pagamento!!!
								// ---------------------------------------------------------------------------------------------------							
								verificaStatoPagamento(listaErrori, input);	
																
								// ---------------------------------------------------------------------------------------------------
								// Verifico caso particolare Marcda da Bollo digitale, non possibile il pagamento con metodi offline
								// ---------------------------------------------------------------------------------------------------
								
								if (thereIsMarcaDaBollo) {
									if (otf.isOFFLINEPAYMENTMETHODS()!=null) {
										if (otf.isOFFLINEPAYMENTMETHODS().booleanValue()) {
											addErrore(listaErrori, "A0000042", "  IdpOTF.OFFLINE_PAYMENT_METHODS deve essere 'false' in presenza di pagamenti contenenti Marche da Bollo Digitali ");
										}
									}

								}
								
								// calcolo totale
								importi.add(dettPag.getImporto()); 
							}						
						}						
						//devo controllare che le date consentano il pagamento
						//altrimenti � inutile inserire la pendenza quando poi non si potr� mai pagare
						//in caso di cartella.... devo controllare le date di tutte le condizioni
						//in caso non sia una cartella... devo controllare la data dell'unica condizione						
					}					
				}								
			}
			
			
			
			// ---------------------------------------------------------------------------------------------------
			// se ho superato i controlli preliminari allora... 
			// verifico se si tratta di tributo con Insert/Replace da gestire
			// MAIN BUSINESS LOGIC
			// ---------------------------------------------------------------------------------------------------						
			if (tipoOperazione && condizNonUnica && PagamNonUnic && listaErrori.size()==0) {
				//verifico se devo fare la replace
				//e se la devo fare.. la faccio
				modificaTipoOperazioneOTF(idpAllineamentoPendenzeOTF,id);						
			}
			
			//adesso posso eseguire il marshall
			ByteArrayOutputStream output = marshallObjectToStream(idpAllineamentoPendenzeOTF);
			
			// ---------------------------------------------------------------------------------------------------
			// se ho superato i controlli preliminari allora... inserisco
			// MAIN BUSINESS LOGIC 
			// ---------------------------------------------------------------------------------------------------						
			if (tipoOperazione && condizNonUnica && PagamNonUnic && listaErrori.size()==0) {
				//se ho superato i controlli preliminari allora... inserisco
				String outputXml = new String(output.toByteArray());
				//calling datastorage as EJB 3			
				//MAIN BUSINESS LOGIC RESIDES HERE
				data = getDataStorageManagerProxy().executeApplicationTransaction(outputXml);
				
				//esito
				logger.debug(" OTF, XML di Esito = \n " + data.getEsito()  );
				logger.info("==============================================");				
			} else {
				//altrimenti preparo un messaggio di errore 
				//personalizzato con il dettaglio errori
				fase1 = false;
				esitoOtf = esitoErroreStandard(header,listaErrori, idPendenza); 
			}
			
		} catch (JAXBException e) {
			logger.info(" JAXBException = " + data.getEsito()  );
			fase1 = false;
			addErrore(listaErrori, "ABC","Errore Imprevisto (Fase di inserimento pendenza)");
			esitoOtf = esitoErroreStandard(header, listaErrori, idPendenza);
		}			
				
		
		// ---------------------------------------------------------------------------------------------------
		// se fase1 � stata superata... allora vado di fase2 
		// ---------------------------------------------------------------------------------------------------		
		if (fase1 && data.getEsito()!=null) {
			//se fase1 � stata superata... allora vado di fase2
			logger.info("Inside gestione allineamento pendenze OTF (fase2)");
			logger.debug(" ESITO XML da DSE = \n " + data.getEsito() );
			
			String esitoStandard = data.getEsito().replace("<IdpEsito", "<IdpEsitoOTF");
			String esitoBasePerOtf = esitoStandard.replace("</IdpEsito", "</IdpEsitoOTF");
									
			try {
				//unmarshall
				JAXBElement<IdpEsitoOTF> foo = unmarshallEsito(esitoBasePerOtf);			
				esitoOtf = foo.getValue();	
				
				//In caso di esito positivo...
				//devo inserire la gestione della tabella OTF
				//gestione tag otf
				String esito =  esitoOtf.getIdpBody().getInfoMessaggio().getStato().value();
				logger.info(" esito operazione = " + esito );
				Boolean pendInserita = esito.equals(StatoMessaggio.ELABORATO_CORRETTAMENTE.value());		
						
				IdpOTF otf = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpOTF();				    
				logger.info(" procedo con gestione OTF ? " + (otf!=null && pendInserita) );
				
				//se � arrivata una richiesta di tipo OTF 
				//e se i dati della pendenza sono stati inseriti
				//allora gestisco la sessione
				String token = GeneratoreIdUnivoci.GetCurrent().generaId(null);
				//e poi creo il tag OTF
				// ---------------------------------------------------------------------------------------------------
				// Creazione Sessione Gateway
				// MAIN BUSINESS LOGIC 
				// ---------------------------------------------------------------------------------------------------				
				if (otf!=null && pendInserita) {											
					logger.info(" E2EMSGID(originale) = " + e2emsgid);
					//in caso di FL_REPLACE_OTF valorizzato ad YES
					//se si verifica un messaggio di Insert in arrivo che contiene un IdPendenza gi� presente
					//allora la MAIN BUSINESS LOGIC eseguir� due operazioni (Insert+Replace)
					//di conseguenza.. la sessione OTF deve essere associata al nuovo E2EMSGID
					e2emsgid = esitoOtf.getIdpHeader().getE2E().getE2EMsgId();
					logger.info(" E2EMSGID(del doppione, se c'�) = " + e2emsgid);
					
					logger.info(" Gestisco anche il tag OTF !! " );
					String urlCancel = otf.getURLCANCEL();	
					String urlBack = 	otf.getURLBACK();
			 		
					Boolean pagOffline = false;
					if (otf.isOFFLINEPAYMENTMETHODS()!=null) {
						pagOffline = otf.isOFFLINEPAYMENTMETHODS();
					}
					
					SessioneGateway session = new SessioneGateway();			
					if (pagOffline==false) {
						session.setOfflinePaymentMethods(0);	
					} else {
						session.setOfflinePaymentMethods(1);
					}					  				
					session.setUrlBack(urlBack);
					session.setUrlCancel(urlCancel);
					
					BigDecimal importoTotale = BigDecimal.ZERO;
					for (BigDecimal importo : importi) {
						importoTotale = importoTotale.add(importo);
					}
					session.setImTotale(importoTotale);
																	
					//insert session  
					session.setToken(token);
					//TODO #2652
					if (otf.getDATIVERSANTE()!=null) {
						session.setTipoSoggettoVers(otf.getDATIVERSANTE().getTIPOSOGGETTO().value());
						session.setCodFiscaleVers(otf.getDATIVERSANTE().getIDFISCALE());
						session.setAnagraficaVers(otf.getDATIVERSANTE().getANAGRAFICA());
						session.setEmailVers(otf.getDATIVERSANTE().getEMAIL());
						session.setIndirizzoVers(otf.getDATIVERSANTE().getINDIRIZZO());
						session.setNumeroCivicoVers(otf.getDATIVERSANTE().getNUMEROCIVICO());
						session.setCapVers(otf.getDATIVERSANTE().getCAP());
						session.setLocalitaVers(otf.getDATIVERSANTE().getLOCALITA());
						session.setProvinciaVers(otf.getDATIVERSANTE().getPROVINCIA());
						session.setNazioneVers(otf.getDATIVERSANTE().getNAZIONE());
					}
					
					session.setE2eMsgId(e2emsgid);
					session.setSenderId(id);
					session.setSenderSys(sil);
					//gestisco la insert sessione+carrello
					getGestioneOTFManagerProxy().executeApplicationTransaction(session);
					
					//dopo aver inserito la sessione gateway etc etc
					//allora creo anche il tag OTF
					//creo il tag
					IdpOTFType otfType = new IdpOTFType();
					String generaId = token;			
					
					String urlGw = IrisProperties.getProperty("GATEWAY_URL");
												
					otfType.setUrlGW(urlGw + generaId);
					otfType.setIdSessioneGW(generaId);
					logger.info(this.getClass().getName() + " URL GATEWAY = " + urlGw + generaId);
					otfType.setVersione("01.03-02");
					esitoOtf.setIdpOTF(otfType);								
				} else {
					logger.info(" NON Gestisco il tag OTF !! " );
				}											
	
			} catch (ParserConfigurationException e) {
				logger.info(" ParserConfigurationException = " + e.getMessage() );
				fase2 = false;
			}	catch (SAXException e) {
				logger.info(" SAXException = " + e.getMessage() );
				fase2 = false;
			} catch (IOException e) {
				logger.info(" IOException = " + e.getMessage() );
				fase2 = false;
			} catch (JAXBException e) {
				logger.info(" JAXBException = " + e.getMessage() );
				fase2 = false;
			}
			
		} else if (fase1 && data.getEsito()==null) {
			//fase1 � stata superata... ma DSE non e' riuscito a creare l'esito
			//l'esito non viene creato a causa di errori imprevisti del DSE...
			//altrimenti ritorno un esito di errore
			addErrore(listaErrori, "DEF","Errore Imprevisto (Fase di creazione esito)");													
			esitoOtf = esitoErroreStandard(header, listaErrori, idPendenza);						
		} 
		
		
		//gestione esito standard
		if (fase2==false) {
			//altrimenti ritorno un esito di errore
			addErrore(listaErrori, "ABC","Errore Imprevisto (Fase di creazione esito e gestione sessione gateway)");													
			esitoOtf = esitoErroreStandard(header, listaErrori, idPendenza);			
		}			
		
		logger.info(" Controlli preliminari  ? tipoOperazione = " + tipoOperazione );
		logger.info(" Controlli preliminari  ? condizNonUnica = " + condizNonUnica );		
		logger.info(" Controlli preliminari  ? PagamNonUnic = " + PagamNonUnic );				
		logger.info(" Si sono verificati errori ? fase1 = " + fase1 + " fase2 = " + fase2);			
		logger.info(" Esito = " + esitoOtf.getIdpBody().getInfoMessaggio().getStato().value());
		
		if (fase2==false || fase1 == false) {
			logger.info(" Esito Errore = " + esitoOtf.getIdpBody().getInfoDettaglio().getDettaglio().size());
		}					
		
		IdpAllineamentoPendenzeEnteOTFEsito esito= new IdpAllineamentoPendenzeEnteOTFEsito();
		esito.setIdpEsitoOTF(esitoOtf);		

		//ritorno l'oggetto intero		
		return esito;
	}


	/**
	 * 
	 * @param idpAllineamentoPendenzeOTF
	 * @param id
	 * @return
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private void modificaTipoOperazioneOTF(
			IdpAllineamentoPendenzeEnteOTF idpAllineamentoPendenzeOTF, String id)
			throws JAXBException, PropertyException {
		
		List<Pendenza> listaPendenze = idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpBody().getPendenza();
		Iterator<Pendenza> it = listaPendenze.iterator();
		//ciclo su pendenza
		while (it.hasNext()) {
			Pendenza type = (Pendenza) it.next();					
			//verifico su struttura tributo se devo accettare una pendenza gi� presente
			//perch� in tal caso devo cambiare la struttura in replace e reinviarla
			boolean trbEnteAccettaPendDoppia = false;								
			String cdTrbEnte = type.getTipoPendenza();		
			
			MonitoringData dataCheckEnte = new MonitoringData();
			dataCheckEnte.setSenderid(id);
			dataCheckEnte.setCdTrbEnte(cdTrbEnte);
			//controllo ente tributo 
			dataCheckEnte = getDecodeEntiTributiManagerProxy().executeApplicationTransaction(dataCheckEnte);										
			logger.info("ente/tributo " + id +"/" + cdTrbEnte + " deve gestire la pend.doppia = " + dataCheckEnte.isTributoReplaceable() );					
			
			if (dataCheckEnte.isTributoReplaceable()) {
				
				//verifico se devo cambiare INSERT in REPLACE
				MonitoringData dataReplace = new MonitoringData();
				dataReplace.setIdPendenzaEnte(type.getIdPendenza());
				dataReplace.setCdTrbEnte(cdTrbEnte);
				dataReplace.setSenderid(id);
				//controllo se la pendenza di questo ente/tributo esiste gi� 
				dataReplace = getGestioneOTFReplaceManagerProxy().executeApplicationTransaction(dataReplace);
																
				//se devo cambiare allora lo faccio...
				if (dataReplace.isPendenzaTrovata()) {
					if (TipoOperazione.INSERT.equals(type.getTipoOperazione())) {
						TipoOperazione tipo = TipoOperazione.fromValue("Replace");
						type.setTipoOperazione(tipo);	
						PendenzaInsertReplace insRep = type.getInsert();
						//cancello la insert
						type.setInsert(null);
						//inserisco la replace
						type.setReplace(insRep);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param esitoBasePerOtf
	 * @return
	 * @throws JAXBException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private JAXBElement<IdpEsitoOTF> unmarshallEsito(String esitoBasePerOtf)
			throws JAXBException, ParserConfigurationException, SAXException,
			IOException {
		JAXBContext jaxbContext;
		jaxbContext = JAXBContextProvider.getInstance()
			.getJAXBContext (
				new String [] {
					  "it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpesito"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpheader"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpinclude"
				}
			);
		logger.debug("OTF, XML di Esito (vero) = \n " + esitoBasePerOtf);

		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();						
		ByteArrayInputStream input = new ByteArrayInputStream(esitoBasePerOtf.getBytes());
					
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		DocumentBuilder db;
		Document doc; 
		Element  fooSubtree = null;
		db = dbf.newDocumentBuilder();
		doc = db.parse(input);
		fooSubtree = doc.getDocumentElement();
										                          	
		//unmarshall
		JAXBElement<IdpEsitoOTF> foo = unmarshaller.unmarshal( fooSubtree, IdpEsitoOTF.class);
		return foo;
	}

	
	
	/**
	 * Scorre l'array di pendenze e fa scattare controlli specifici se trova E_BOLLO.
	 * @param idpAllineamentoPendenzeOTF
	 * @param listaErrori
	 * @return true se esiste almeno una pendenza E_BOLLO nel payload
	 */
	private boolean verificheEBollo(IdpAllineamentoPendenzeEnteOTF idpAllineamentoPendenzeOTF, List<Esito> listaErrori) {
		
		boolean returnValue = false;
		
		for (Pendenza pendenza: idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF().getIdpBody().getPendenza() ) {
			
			if ("E_BOLLO".equals(pendenza.getTipoPendenza())) {
				
				returnValue = true;
			
				PendenzaInsertReplace p=null;
				
				if (pendenza.getInsert()!=null) {
					p = pendenza.getInsert();
					
				}
				if (pendenza.getReplace()!=null) {
					p = pendenza.getReplace();
				}
				
				if (p!=null) {
					
					for (InfoPagamento infopag: p.getInfoPagamento()) {
						for (DettaglioPagamentoInsertReplace dettaglioPag: infopag.getDettaglioPagamento()) {
							verificaTipoPendenzaEBollo(listaErrori,pendenza,dettaglioPag);
						}
					}
					
				}
				
				
			}	
			
		}
		
		return returnValue;
		
	}


	/**
	 * 
	 * @param listaErrori
	 * @param otf
	 * @param pendenza
	 * @param dettPag
	 */
	private void verificaTipoPendenzaEBollo(List<Esito> listaErrori,  Pendenza pendenza, DettaglioPagamentoInsertReplace dettPag) {
			
			if (dettPag.getAllegato()==null || dettPag.getAllegato().isEmpty()) {
				addErrore(listaErrori, "A0000040", " Allegato non specificato in pendenza con tipoPendenza='E_BOLLO' ");
			}
			if (dettPag.getAllegato()!=null && !dettPag.getAllegato().isEmpty()) {
				Allegato all= dettPag.getAllegato().get(0);
				if (!ContentType.DOCUMENTO.equals(all.getTipo())|| 
					 !"DatiMBD.txt".equals(all.getTitolo())||
					 !MIMETypeCode.TEXT.equals(all.getCodifica())) {
				   addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
				}
				try {
					String s = new String(all.getContenuto());
					String tipo = null;
					String prov = null;
					String hash = null;
					String[] xx=s.split(";");
					int i=0;
					while (i<xx.length){
						String cc=xx[i];
						String dd[]=cc.split("=");
						if ("TIPO".equals(dd[0])){
							tipo=(dd[1]);
						}
						if ("PROV".equals(dd[0])){
							prov=(dd[1]);
						}
						if ("HASH".equals(dd[0])){
							hash=(dd[1]);
						}
						i++;
					}
					if (!"01".equals(tipo)) {
						addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
					}
					if (prov!=null) {
						String pattern = "[A-Z]{2,2}";
					    boolean isMatch = Pattern.matches(pattern, prov);
					    if (!isMatch) {
					    	addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
					    }
					} else {
						addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
					}
					if (hash!=null ) {
						// #3760 - controlli sul formato dell'HASH Documento
						// if (!Base64.isBase64(hash)) {
						if (!Pattern.matches("[a-fA-F0-9]{64}", hash)) {
							addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
						}
					} else {
						addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
					}
				} catch(Exception t) {
					addErrore(listaErrori, "A0000040", " Allegato non specificato correttamente in pendenza con tipoPendenza='E_BOLLO' ");
				}
			}
			
			if ((dettPag.getImporto().compareTo(new BigDecimal("16.00"))!=0)) {
				addErrore(listaErrori, "A0000041", " Pendenza Importo  deve essere = 16.00 ");
			}
			

	}


	/**
	 * 
	 * @param listaErrori
	 * @param input
	 */
	private void verificaStatoPagamento(List<Esito> listaErrori,
			VerificaStatoPagamentoInput input) {
		VerificaPagamentoModel pmodel;
		try {
			//MAIN BUSINESS 
			pmodel = getVerificaStatoPagamentoBean().verificaPagamentoModel(input, true);

			if (pmodel.isRefreshData()) {
				logger.info("Richiesto aggiornamento stato posizione (in corso) -----------------");
				logger.info("Ricalcolo lo stato  -----------------");
				pmodel = getVerificaStatoPagamentoBean().verificaPagamentoModel(input, true);
			}

			logger.info("Esito Verifica -----------------");
			logger.info(" Stato/TipoPend/DexStato " + pmodel.getPagamento() + pmodel.getTipoPendenza() + pmodel.getDescrizioneStato());
			logger.info("Fine Esito Verifica -----------------");
			
			
			if (pmodel.getStatoPagamento()==EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA ) {
				addErrore(listaErrori, "A0000016", "La posizione risulta pagata");
			}
			
			if (pmodel.getStatoPagamento()==EnumStatoPagamentoDettagliato.POSIZIONE_CON_PAG_IN_CORSO ||
				pmodel.getStatoPagamento()==EnumStatoPagamentoDettagliato.POSIZIONE_PAGATA_SBF) {
			
				addErrore(listaErrori, "A0000016", "Esiste un pagamento in corso associato alla posizione");
			}
		
		} catch (VerificaPagamentoModelException e) {
			addErrore(listaErrori, "A0000010"," Impossibile verificare se la posizione risulta gia' pagata ");
		}
	}


	/**
	 * 
	 * @param idpAllineamentoPendenzeOTF
	 * @return
	 * @throws JAXBException
	 * @throws PropertyException
	 */
	private ByteArrayOutputStream marshallObjectToStream(
			IdpAllineamentoPendenzeEnteOTF idpAllineamentoPendenzeOTF)
			throws JAXBException, PropertyException {
		JAXBContext jaxbContext = JAXBContextProvider.getInstance()
			.getJAXBContext (
				new String [] {
					  "it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpallineamentopendenze"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpheader"
					, "it.toscana.rete.cart.servizi.iris_1_1.idpinclude"
				}
			);
		
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,new Boolean(true));
					
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		//marshall
		marshaller.marshal(idpAllineamentoPendenzeOTF.getIdpAllineamentoPendenzeOTF(), output);
		return output;
	}



	/**
	 * 
	 */
	private void addErrore(List<Esito> listaErrori, String codice, String descrizione) {
		Esito esito = new Esito();
		esito.setCodice(codice);
		esito.setDescrizione(descrizione);
		listaErrori.add(esito);
	}

	/**
	 * @param header 
	 * @param listaErrori 
	 * @param idPendenza 
	 * 
	 */
	private IdpEsitoOTF esitoErroreStandard(IdpHeader header, List<Esito> listaErrori, String idPendenza) {
		IdpEsitoOTF esitoOtf = new IdpEsitoOTF();			
		
		buildHeaderForResponse(header);
		esitoOtf.setIdpHeader(header);
		esitoOtf.setVersione("01.03-02");
		
		IdpBodyType body = new IdpBodyType();
				
		InfoMessaggio infoM = new InfoMessaggio();  
		infoM.setStato(StatoMessaggio.ELABORATO_CON_ERRORI);		
		body.setInfoMessaggio(infoM);
		
		InfoDettaglio infoD = new InfoDettaglio();		
		
		Iterator erroriList = listaErrori.iterator();
		while (erroriList.hasNext()) {
			Esito esito = (Esito) erroriList.next();	
			Dettaglio dett = new Dettaglio();
			
			dett.setId(idPendenza);
			dett.setStato(StatoDettaglio.SCARTATO);
			
			Esiti esiti = new Esiti();
			esiti.getEsito().add(esito);
			dett.setEsiti(esiti);			
			infoD.getDettaglio().add(dett);
			logger.info(" Dettaglio errori " + infoD.getDettaglio().size());
		}
		body.setInfoDettaglio(infoD);
		
		esitoOtf.setIdpBody(body);   
		
		return esitoOtf;
	}
	
	/**
	 * 
	 * @return
	 */
	protected CommandExecutorLocal getDataStorageManagerProxy() {
		if (dataStorageManagerProxy == null) { 
			return (CommandExecutorLocal) IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.DataStorageManagerIntegrato);
		}
		return dataStorageManagerProxy;
	}
	
	/**
	 * 
	 * @return
	 */
	protected ObjectCommandExecutor getGestioneOTFManagerProxy() {
		if (gestioneOTFManagerProxy == null) { 
			return (ObjectCommandExecutor) IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.GestioneOTFManager);
		}
		return gestioneOTFManagerProxy;
	}
	
	/**
	 * 
	 * @return
	 */
	protected ObjectCommandExecutor getGestioneOTFReplaceManagerProxy() {
		if (gestioneOTFReplaceManagerProxy == null) { 
			return (ObjectCommandExecutor) IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.GestioneOTFReplaceManager);
		}
		return gestioneOTFReplaceManagerProxy;
	}	
	
	/**
	 * 
	 * @return
	 */
	protected ObjectCommandExecutor getDecodeEntiTributiManagerProxy() {
		if (DecodeEntiTributiManagerProxy == null) { 
			return (ObjectCommandExecutor) IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.DecodeEntiTributiManager);
		}
		return DecodeEntiTributiManagerProxy;
	}		

}
