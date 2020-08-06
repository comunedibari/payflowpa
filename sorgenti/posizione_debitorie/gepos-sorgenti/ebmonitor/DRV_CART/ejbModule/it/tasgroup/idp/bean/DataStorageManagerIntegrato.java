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
package it.tasgroup.idp.bean;

import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;
import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DBReference;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.dse.view.DataInputByte;
import it.tasgroup.dse.view.DataInputDb;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.esiti.EsitoBuilder;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.ConcreteFactory;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.idp.util.SystemPropertiesNames;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
public class DataStorageManagerIntegrato extends DataStorageManagerCommon implements CommandExecutor, CommandExecutorLocal {
	
	@EJB(beanName = "SpedizioneEsiti")
	private ObjectCommandExecutorLocal SpedizioneEsitiProxy;	

	final Log logger = LogFactory.getLog(this.getClass());
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	public EntityManager manager;	
	@Resource 
	private EJBContext ejbCtx;	
	
	@EJB(beanName = "DataManager")
	private DataStorageInterface dataTx;		

	/**
	 *
	 */
	public MonitoringData executeApplicationTransaction(String message) {
		
		MonitoringData monData = new MonitoringData();
        
		// valido il messaggio proveniente da web service
		//-------------------------------------------------------------------------
		//------ INIZIO ELABORAZIONE
		//-------------------------------------------------------------------------
		//elaborazione messaggio
		// ATTENZIONE: valido il messaggio originario prima di "muccarlo"
		ConcreteFactory factory = new ConcreteFactory();
		String DseImpl = System.getProperty(SystemPropertiesNames.DSE_IMPL_SINCRONO);
		DataStoreEngineService dataStoreEngineService = factory.createDSE(DseImpl);									
								
		boolean valido = true;	
		StringBuilder buf = new StringBuilder();
		Set set = null;
		DataInput valInput = null;
		try {
			//eseguo la validazione
			String validateXsd = System.getProperty(SystemPropertiesNames.ENV_VALIDATE_XSD);
			if ("true".equals(validateXsd)) {
				//eseguo la validazione
				valInput = new DataInputByte(message.getBytes(Charset.defaultCharset())); // ATTENZIONE: LO GENERO SEMPRE IN-MEMORY!!!
				set = dataStoreEngineService.validate(valInput);
				Iterator iter = set.iterator();

				while (iter.hasNext()) {
					ExtendedErrorInfoImpl type = (ExtendedErrorInfoImpl) iter.next();
					logger.info(" ERRORE VALIDAZIONE XSD SCHEMA DATASTORAGE >>>>>" + type.getErrorDetail());
					buf.append(type.getErrorDetail()+";");
					//se trovo eccezioni gestite allora non è valido
					valido = false;
				}
			}
		} catch (Exception e) {
			//in caso di eccezione gestire STATO per ripartenza
			logger.error(" ERRORE VALIDAZIONE XSD SCHEMA DATASTORAGE >>>>>",e);
			//se si verifica eccezione allora non è valido
			valido = false;
		} finally {
			valInput = null;
		}
		//-------------------------------------------------------------------------
        //---- FINE ELABORAZIONE
		//-------------------------------------------------------------------------
		// Effettuo la "muccatura" del messaggio per fare si che possa essere elaborato dal flowmanager
		message = message.replace("IdpAllineamentoPendenzeOTF", "IdpAllineamentoPendenze");
		logger.debug( "OTF XML DA INSERIRE = \n" + message); 
			
		//------- 

		//esito
		EsitoBuilder esitoB = new EsitoBuilder();

		//estraggo l'id busta egov perchè mi serve come chiave
		String E2EMsgId = null;
		String idMittente = null;
		String silMittente = null;
		String idRicevente = null;
		String silRicevente = null;
		String trtSenderId = null;
		String trtSenderSys = null;
		String idEnte = null;		
		String cdTrbEnte = null;
		String tipoOperazione = null;	
		String otf = null;				 

		//previsita xml
		PrevisitingData prevData = new PrevisitingData();
		//estrazione dati significativi
		try {
			//leggo i tag testata 
			String[] testata = readXml("E2EMsgId","E2ESndrId","E2ESndrSys","E2ERcvrId","E2ERcvrSys","URL_BACK","SenderId","SenderSys","TipoPendenza","TipoOperazione",message);
			E2EMsgId = testata[0];
			idMittente = testata[1];
			silMittente = testata[2];
			idRicevente = testata[3];
			silRicevente = testata[4];
			otf = testata[5];
			trtSenderId = testata[6];
			trtSenderSys = testata[7];			
			cdTrbEnte = testata[8];
			tipoOperazione = testata[9];

		} catch (FactoryConfigurationError e1) {
			logger.error(" : Error reading XML file " + e1.getMessage() );
			throw new RuntimeException("Error Reading HEADER" + e1.getMessage());
		} catch (XMLStreamException e1) {
			logger.error(" : Error reading XML file " + e1.getMessage() );
			throw new RuntimeException("Error Reading HEADER" + e1.getMessage());
		} catch (FileNotFoundException e) {
			logger.error(" : Error reading XML file " + e.getMessage() );
			throw new RuntimeException("Error Reading HEADER" + e.getMessage());
		}
		logger.info("DataStorageManager , Ricevuta busta :  " + E2EMsgId + "/"+ idMittente + "/" + silMittente + " dal mittente " + idMittente);

		//monitoring data
		monData.setE2emsgid(E2EMsgId);
		monData.setSenderid(idMittente);
		monData.setSendersys(silMittente);
		monData.setReceiverid(idRicevente);
		monData.setReceiversys(silRicevente);

		//salvo il blobbone anche su file system
		String fileName = saveBlobOnFile(message, E2EMsgId, idMittente,	silMittente, trtSenderId, trtSenderSys);

		//conteggio pendenze e duplicati
		prevData = this.previsitingXml("Pendenza", fileName);
		//salvo su prevData anche le info sul mittente
		prevData.setIdMittente(idMittente);
		prevData.setSilMittente(silMittente);
		logger.info( " Num_Pend_XPath " + prevData.getNumPendXPath() );
		//salvo su tabella monitoring anche il numero di pendenze
		monData.setNumRecord(prevData.getNumPendXPath());

		//recupero il serviceNameType
		String serviceNameType = "UpdateMassivo".equals(prevData.getTipoOperazione()) ? StatoEnum.SERVICE_NAME_TYPE_ALL_MASSIVO_PENDENZE : StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE;
		boolean insertable = true;
		
		try {

			//salvo su Pendenze_cart
			savingBlobOnDatabase(message, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, otf, prevData, 
					trtSenderId, trtSenderSys);

			logger.info("Salvata busta con blob :  " + E2EMsgId + ", numPendenze = " + prevData.getNumPendXPath());

		} catch (Exception e) {
			logger.error(" : Error Saving BLOB " + e.getMessage() );
			//IN CASO DI OTF... si gestisce anche l'errore derivato dall'utilizzo di una chiave duplicata
			//in caso di e2emsgid/id/sil non si scrive più su tabella ERRORI_CART
			//ma si risponde con un esito 'comprensibile'
			//throw new RuntimeException("Error saving Blob");
			insertable = false;
		} 
		
		//rimuovo il file da filesystem, adesso si trova su tabella
		removeBlobFromFS(fileName);		

		//qui si deve controllare il SIL
		//ed in caso non sia valido si deve creare e gestire l'errore
		//come nel caso di pendenzeNonUnivoche e IdPagamentoNonUnivoci
		//ATTENZIONE --- SCOMMENTARE DA QUI ..... IN CASO DI ESECUZIONE DI JUNIT CON EJB3UNIT
		boolean silPresente = false;
		try {
			//controllo SIL
			silPresente = findSilEnte(manager, idMittente, silMittente, trtSenderId, trtSenderSys);
			logger.info(" : SIL presente = " + silPresente );
		} catch (Exception e) {
			logger.error(" : Error checking SIL " + e.getMessage() );
		} 
		//ATTENZIONE --- SCOMMENTARE FINO A QUI .....
		//ATTENZIONE --- ELIMINARE LA RIGA QUI SOTTO .....
		//boolean silPresente = true;
		
		
		//qui si deve controllare se gli IBAN sono trusted
		//ed in caso non lo siano si deve creare e gestire l'errore
		//come nel caso di pendenzeNonUnivoche e IdPagamentoNonUnivoci etc etc
		//ATTENZIONE --- SCOMMENTARE DA QUI ..... IN CASO DI ESECUZIONE DI JUNIT CON EJB3UNIT
		boolean ibanTrusted = true;
		if (prevData.getIban().size()>0) {						
			try {	
				//controllo ibanTrusted
				ibanTrusted = checkIbanTrusted(manager, prevData.getIban(), silMittente, prevData.getTipoPendenza());
				logger.info(" : ibanTrusted = " + ibanTrusted );

	
			} catch (Exception e) { 
				logger.error(" : Error checking ibanTrusted " + e.getMessage() );
			} 
		}
		//ATTENZIONE --- SCOMMENTARE FINO A QUI .....
		//ATTENZIONE --- ELIMINARE LA RIGA QUI SOTTO .....
		//boolean ibanTrusted = true;
					
		String esitoCreatoDaDSManager = ""; 

		if (!insertable) {
			//gestione idPendenza non univoco
			esitoCreatoDaDSManager = manageChiaveMessaggioDuplicata(esitoB, serviceNameType, E2EMsgId, idMittente, silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);				
		} else if (prevData.isPendenzeUnivoche()) {					
		//if (false) {
			//se idPendenza non è univoco segnalo l'errore e non processo il messaggio.			
			//gestione idPendenza non univoco
			esitoCreatoDaDSManager = managePendenzeNonUnivoche(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		} //se idPagamento non è univoco segnalo l'errore e non processo il messaggio.
		else if (prevData.isIdPagamentoUnivoci()) {
			//gestione idPagamento non univoco
			esitoCreatoDaDSManager = managePagamentoNonUnivoche(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		}  else if (silPresente==false) {
			//gestione SIL NON PRESENTE
			esitoCreatoDaDSManager = manageSilNonPresente(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		}  else if (prevData.isConsistenzaTipoOperazione()==false) {
			//gestione TIPO OPERAZIONE NON CONSISTENTE
			esitoCreatoDaDSManager = manageTipoOperazioneNonConsistente(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		} else if (ibanTrusted==false) {
			//gestione IBAN NOT TRUSTED
			esitoCreatoDaDSManager = manageIbanNotTrusted(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		} else {
			//se idPendenza e idPagamento è univoco allora procedo con l'inserimento
			DataInput input = null;
			//preparo il dataInput con i dati in memoria
			//DataInput input = new DataInputByte(mess age.getBytes(Charset.defaultCharset()));
			//preparo il dataInput con l'indicazione dei dati su Db
			//String[][] keys= new String[][]{{E2EMsgId,"E2EMSGID"},{idMittente,"SenderId"},{silMittente,"SenderSys"}};
			//DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
			//DataInput inputDb = new DataInputDb(reference);

			String transferMode = System.getProperty(SystemPropertiesNames.ENV_TRANSFER_MODE);
			if ("Memory".equals(transferMode)) {
				input = new DataInputByte(message.getBytes(Charset.defaultCharset()));
				logger.info("trasmetto il messaggio al DSE in modalità INMEMORY ");
			} else if ("Database".equals(transferMode)) {
				String[][] keys= new String[][]{{E2EMsgId,"E2EMSGID"},{idMittente,"SenderId"},{silMittente,"SenderSys"}};
				DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
				input = new DataInputDb(reference);
				logger.info("Invio i riferimenti al DSE per lettura BLOB da tabella :  " + E2EMsgId );
			}		
			
			//se lo schema è valido allora eseguo l'inserimento
			//se si verifica eccezione in inserimento allora lascio perdere, non rilancio eccezione
			//il flusso sarà fatto ripartire dalla 'ripartenza'
			if (valido) {
				try {
					//se la validazione va a posto allora inserisco
					if (OTF_NOT_FOUND.equals(otf)) {
						logger.info("CALL DATASTORAGE >>>>> PENDENZA VISIBLE (no OTF) ",null);
						dataTx.callMe(dataStoreEngineService, input, false);
					} else if ("Replace".equals(tipoOperazione)) {
						logger.info("CALL DATASTORAGE >>>>> PENDENZA VISIBLE (OTF in modalità Replace) ",null);
						dataTx.callMe(dataStoreEngineService, input, false);
					} else if ("Insert".equals(tipoOperazione)) {
						logger.info("CALL DATASTORAGE >>>>> PENDENZA HIDDEN (OTF in modalità Insert) ",null);
						dataTx.callMe(dataStoreEngineService, input, true);
					} else {
						logger.info("CALL DATASTORAGE >>>>> PENDENZA HIDDEN ",null);		
						dataTx.callMe(dataStoreEngineService, input, true);
					}
					
					logger.info(" ESCO DA DATASTORAGE >>>>>",null);
				} catch (Exception e) {
					//in caso di eccezione gestire STATO per ripartenza
					logger.error(" ERRORE DATASTORAGE >>>>>",e);
				}
			} else if (!valido) {
				//gestione flusso non valido XSD
				esitoCreatoDaDSManager = manageXsdNonValido(esitoB, serviceNameType, E2EMsgId, idMittente,silMittente, idRicevente, silRicevente, set, trtSenderId, trtSenderSys);
			} 
		}
				
		//ATTENZIONE QUI::::::
		//SE l'ESITO E' COSTRUITO DAL DataStorage(EJB)
		//allora il codice sotto (listaEsitiDaSpedire) non avrà effetto...
		//questo accade quando il messaggio non viene inserito perchè falliscono i controlli preliminari
		//in questo caso particolare solo della gestione sincrona 
		//si deve gestire manualmente come caso particolare
		logger.info("WS Sincrono , devo rispondere subito con l'esito di errore");
		if (prevData.isPendenzeUnivoche() || prevData.isIdPagamentoUnivoci() 
				|| silPresente==false || !insertable || ibanTrusted==false 
				|| (!valido)) {
			
			//spedisco l'esito XML alla rete CART
			dataTx.updateEsitoAndPendenze(E2EMsgId, idMittente, silMittente, esitoCreatoDaDSManager);
			//updateEsitoAndPendenze(E2EMsgId, idMittente , silMittente , esitoCreatoDaDSManager);

			logger.info("WS Sincrono , messaggio di risposta (controllo errori su Monitor)");   
			logger.info(esitoCreatoDaDSManager);
			
			//aggiungo l'esito xml per rfc127 sincrono
			monData.setEsito(esitoCreatoDaDSManager);
		}  		 			
		//FINE ATTENZIONE
		else {
			//CI COLLEGHIAMO CON EJB SPEDIZIONEESITI
			//CHE GESTISCE SIA IL SINCRONO CHE L'ASINCRONO
			//creo un pojo qualsiasi per comunicare con il prossimo ejb
			EsitiModel esitoModel = new EsitiModel();
			esitoModel.setE2emsgid(E2EMsgId);
			esitoModel.setSenderId(idMittente);
			esitoModel.setSenderSys(silMittente);
			
			logger.info(", calling SpedizioneEsitiProxy");
			
			//-------------------------------------------------------------------------
	        //---- MAIN BUSINESS LOGIC - SPEDIZIONE E CREAZIONE ESITO
			//-------------------------------------------------------------------------			
			MonitoringData esitoContainer = (MonitoringData)SpedizioneEsitiProxy.executeApplicationTransaction(esitoModel);		
			
			//esito
			String esitoXml = esitoContainer.getEsito();
			
			logger.info("WS Sincrono , messaggio di risposta");   
			logger.debug(esitoXml);
			
			monData.setEsito(esitoXml);			
		}
		
		//verifico se la risposta al primo messaggio di Insert è stata pendenza duplicata
		boolean alreadyPresent = pendenzaAlreadyPresent(monData.getEsito());			
		if (alreadyPresent) {			
		//se la pendenza è gia presente ... allora verifico se deve usare REPLACE
		//ed eventualmente inserisco
			try {
				//verifico su struttura tributo se deve accettare una pendenza già presente
				//perchè in tal caso devo cambiare la struttura in replace e reinviarla
				boolean trbEnteAccettaPendDoppia = false;			
				//controllo ente tributo 
				trbEnteAccettaPendDoppia = findEnteTributo(manager, idMittente, cdTrbEnte);
				logger.info("tributo deve gestire la pend.doppia = " + trbEnteAccettaPendDoppia );
				//controllo se devo modificare e rimandare il messaggio
				if (trbEnteAccettaPendDoppia && alreadyPresent) {
					//utilizzo metodo esterno per separazione biz logic
					//-------------------------------------------------------------------------
			        //---- MAIN BUSINESS LOGIC - GESTIONE REPLACE OTF
					//-------------------------------------------------------------------------						
					String esitoXml = gestioneReplaceOTF(message, monData, dataStoreEngineService,
							E2EMsgId, idMittente, silMittente, idRicevente,
							silRicevente, trtSenderId, trtSenderSys, otf, prevData);
					//restituisco l'esito XML (che contiene anche le URL per eseguire il pagamento)
					monData.setEsito(esitoXml);				
				}
			} catch (Exception e) {
				logger.error(" Error gestione controlloFlReplaceOTF/DataStorage/SpedizioneEsiti " + e.getMessage() );
			} 			
		}
		
					
		return monData;
	}

	/**
	 * 
	 * @param message
	 * @param monData
	 * @param dataStoreEngineService
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param idRicevente
	 * @param silRicevente
	 * @param trtSenderId
	 * @param trtSenderSys
	 * @param otf
	 * @param prevData
	 */
	private String gestioneReplaceOTF(String message, MonitoringData monData,
			DataStoreEngineService dataStoreEngineService, String E2EMsgId,
			String idMittente, String silMittente, String idRicevente,
			String silRicevente, String trtSenderId, String trtSenderSys,
			String otf, PrevisitingData prevData) {
		
		//cambio l'operazione da Insert a Replace
		//e cambio anche il E2EMsgId 			
		String e2eMsgIdNew = new StringBuffer(E2EMsgId).reverse().toString();
		String xmlMorphed = morphMessage(message,E2EMsgId, e2eMsgIdNew);
		logger.info(" XML MORPHED (Insert transformed in Replace) = " + xmlMorphed);
		//eseguo l'operazione
		//devo purtroppo fare try/catch per poter inviare sempre una risposta, anche in caso di errore
		try {				
			//creo l'object da inserire
			DataInput input2 = null;
			String transferMode = System.getProperty(SystemPropertiesNames.ENV_TRANSFER_MODE);
			
			//ri-salvo su Pendenze_cart
			savingBlobOnDatabase(message, e2eMsgIdNew, idMittente,
					silMittente, idRicevente, silRicevente, otf, prevData, 
					trtSenderId, trtSenderSys);
			logger.info("Salvata busta(doppione) con blob :  " + e2eMsgIdNew + ", numPendenze = " + prevData.getNumPendXPath());				
			
			//-------------------------------------------------------------------------
	        //Risottometto per l'inserimento con nuovo E2EMSGID
			//-------------------------------------------------------------------------					
			if ("Memory".equals(transferMode)) {
				input2 = new DataInputByte(xmlMorphed.getBytes(Charset.defaultCharset()));
				//annullo gli oggetti per questioni di memoria
				message = null;
				xmlMorphed = null;
				logger.info("trasmetto il messaggio al DSE in modalità INMEMORY (doppione) ");
			} else if ("Database".equals(transferMode)) {
				String[][] keys= new String[][]{{e2eMsgIdNew,"E2EMSGID"},{idMittente,"SenderId"},{silMittente,"SenderSys"}};
				DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
				input2 = new DataInputDb(reference);
				logger.info("Invio i riferimenti al DSE per lettura BLOB da tabella :  " + e2eMsgIdNew );
			}
			logger.info(" CHIAMO DATASTORAGE (doppione) >>>>> PENDENZA HIDDEN ",null);
			dataTx.callMe(dataStoreEngineService, input2, false);
			
		} catch (Exception e) {
			//in caso di eccezione gestire STATO per ripartenza
			logger.error(" ERRORE DATASTORAGE (doppione) >>>>>",e);
		}
			
		//purtroppo devo ricreare nuovamente l'esito
		//creo un pojo qualsiasi per comunicare con il prossimo ejb
		EsitiModel esitoModel = new EsitiModel();
		esitoModel.setE2emsgid(e2eMsgIdNew);
		esitoModel.setSenderId(idMittente);
		esitoModel.setSenderSys(silMittente);			
		logger.info(" calling SpedizioneEsitiProxy (doppione)");
		//-------------------------------------------------------------------------
        //Rieseguo nouvamente la creazione dell'esito
		//-------------------------------------------------------------------------				
		MonitoringData esitoContainer = (MonitoringData)SpedizioneEsitiProxy.executeApplicationTransaction(esitoModel);					
		//esito
		String esitoXml = esitoContainer.getEsito();			
		logger.info("WS Sincrono , messaggio di risposta (doppione) \n " + esitoXml);   
		
		return esitoXml;		
	}
	
	/**
	 * 
	 * @param message
	 * @param e2eMsgId 
	 * @return
	 */
	private String morphMessage(String message, String e2eMsgId, String e2emsgidNew) {
		//Sostituisco in malo modo il tag Insert con Replace
		String messageMorphed = message.replace("Insert", "Replace");		
		String messageMorphed2 = messageMorphed.replace(e2eMsgId, e2emsgidNew);		
		return messageMorphed2;
	}

	/**
	 * 
	 * @param esito 
	 * @return
	 */
	private boolean pendenzaAlreadyPresent(String esito) {	
		//andrebbe riletto diversamente...
		if (esito!=null && esito.indexOf("A0000003")!=-1) {
			//la pendenza era già presente
			return true;
		} else {
			return false;
		}		
	}

	/**
	 * 
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param esitoXML
	 * @param stato
	 */
	protected void insertEsitoCartBridge(String E2EMsgId, String idMittente,
			String silMittente, String esitoXML, String trtSenderId, String trtSenderSys) {
		
		insertEsitoCart(E2EMsgId, idMittente, silMittente, esitoXML, StatoEnum.DA_SPEDIRE_WS, trtSenderId, trtSenderSys);		
	}		
	
	
	/**
	 * 
	 * @param tipoUpd
	 * @param tipoOperazione
	 */
	protected void setTipoMessaggio(String tipoUpd, PrevisitingData prevData, String otf, PendenzeCartMessage cart) {

		if (OTF_NOT_FOUND.equals(otf)) {
			//tipo Messaggio lo metto fisso perchè siamo in Sincrono			
			cart.setTipoMessaggio(StatoEnum.MSG_TYPE_ALL_PENDENZE_SYNC);			
		} else {
			//tipo Messaggio lo metto fisso perchè siamo in Sincrono ed in caso OTF
			cart.setTipoMessaggio(StatoEnum.MSG_TYPE_ALL_PENDENZE_OTF);
		}		
	}		


	@Override
	public MonitoringData executeApplicationTransaction() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String executeHtml() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


	

}
