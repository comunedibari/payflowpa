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

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;
import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DBReference;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.dse.view.DataInputByte;
import it.tasgroup.dse.view.DataInputDb;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.esiti.EsitoBuilder;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.pojo.TimerData;
import it.tasgroup.idp.timer.TimerCommandExecutorLocal;
import it.tasgroup.idp.util.ConcreteFactory;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.idp.util.SystemPropertiesNames;;

@Interceptors(MonitoringInterceptor.class)
@Stateless
public class DataStorageManager extends DataStorageManagerCommon implements CommandExecutor, CommandExecutorLocal, IDataStorageManager {

	@PersistenceContext(unitName=ServiceLocalMapper.IdpAppJpa)
	public EntityManager manager;	
	@Resource 
	private EJBContext ejbCtx;		
	
	@EJB(beanName = "DataManager")
	protected DataStorageInterface dataTx;	

	@EJB(beanName = "TimerController")
	TimerCommandExecutorLocal timerController;
	
	final Log logger = LogFactory.getLog(this.getClass());

	String E2EMsgId = null;
	String idMittente = null;
	String silMittente = null;
	String idRicevente = null;
	String silRicevente = null;
	String trtSenderId = null;
	String trtSenderSys = null;
	String cdTrbEnte = null;		
	String otf = null;
	MonitoringData monData = new MonitoringData();
	PrevisitingData prevData = new PrevisitingData();
	
	public MonitoringData executeApplicationTransaction(String message) {
		String serviceNameType = doPrevisiting(message);
		elaborateMessage(serviceNameType,message);
		return monData;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public DataInput executeApplicationTransactionTX1(String message) {
		// faccio la doPrevisiting e la prima meta' della elaborateMessage() originaria
		String serviceNameType = doPrevisiting(message);
		DataInput input = getInput(message); // attenzione, l'input corretto si ha solo DOPO aver fatto il previsiting
		DataStoreEngineService dataStoreEngineService = getDataStoreEngineService();
		boolean elaborateOk = elaborateMessageParte1(serviceNameType, message, input, dataStoreEngineService);
		if (elaborateOk)
			return input;
		else
			return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public MonitoringData executeApplicationTransactionTX2(DataInput input) {
		// faccio la seconda meta' della elaborateMessage() originaria
		if (input != null)
			dataTx.callMe(getDataStoreEngineService(), input);
		return monData;
	}

	/**
	 * 
	 * @param message
	 * @return
	 */
	protected String doPrevisiting(String message) {
		//previsita xml
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

		} catch (FactoryConfigurationError e1) {
			logger.error(this.getClass().getSimpleName() + " : Error reading XML file " + e1.getMessage() );
			throw new RuntimeException("Error Reading HEADER" + e1.getMessage());
		} catch (XMLStreamException e1) {
			logger.error(this.getClass().getSimpleName() + " : Error reading XML file " + e1.getMessage() );
			throw new RuntimeException("Error Reading HEADER" + e1.getMessage());
		} catch (FileNotFoundException e) {
			logger.error(this.getClass().getSimpleName() + " : Error reading XML file " + e.getMessage() );
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
		prevData.setE2EMsgId(E2EMsgId);
		prevData.setIdRicevente(idRicevente);
		prevData.setSilRicevente(silRicevente);
		logger.info( " Num_Pend_XPath " + prevData.getNumPendXPath() );
		//salvo su tabella monitoring anche il numero di pendenze
		monData.setNumRecord(prevData.getNumPendXPath());

		//recupero il serviceNameType
		String serviceNameType = "UpdateMassivo".equals(prevData.getTipoOperazione()) ? StatoEnum.SERVICE_NAME_TYPE_ALL_MASSIVO_PENDENZE : StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE;

		try {
			//salvo su Pendenze_cart
			savingBlobOnDatabase(message, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, null, prevData,
					trtSenderId, trtSenderSys);

			logger.info("Salvata busta con blob :  " + E2EMsgId + ", numPendenze = " + prevData.getNumPendXPath());

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + " : Error Saving BLOB " + e.getMessage() );
			throw new RuntimeException("Error saving Blob");
		} 
		
		//rimuovo il file da filesystem, adesso si trova su tabella
		removeBlobFromFS(fileName);
		return serviceNameType;
	}


	/**
	 * 
	 * @param idMittente
	 * @param silMittente
	 * @param prevData
	 * @return
	 */
	protected boolean controlliUpdateMassivo(String idMittente,
			String silMittente, PrevisitingData prevData) {
		boolean validoMassivo;
		try {
			//se il tipoPendenza � univoco, allora proseguo e controllo le validit� di ente e tributo
			validoMassivo = checkEnteTributo(manager,idMittente,silMittente,prevData.getTipoPendenza());
			//select ID_ENTE from JLTENTI where CD_ENTE = JLTPEND.E2ESndrId and STATO = "A"
			//select ID_TRIBUTO from JLTENTR where ID_ENTE = JLTPEND.ID_ENTE and CD_TRB_ENTE = JLTPEND.CD_TRB_ENTE and ID_SISTEMA = JLTPEND.ID_SYSTEM and STATO = "A"

			if (validoMassivo) {
				//se i controlli preliminari sono andati a buon fine
//				CancellaLogicaPendenzeOriginaliProxy.executeApplicationTransaction(prevData);
//							deleteLogicaMassivi(prevData);
				//cio�, pulisco e poi .. inserir� con il DSE
				//faccio partire anche il timer
//				String Url1 =  (String)ServiceLocator.getServiceName("jndiUrl1");
//				String Url2 =  (String)ServiceLocator.getServiceName("jndiUrl2");
//				startControllerUpdateMassivoTimer(ServiceLocalMapper.ControllerUpdateMassivoTimer, 150000, Url1, Url2);
				startControllerUpdateMassivoTimer();
			}
		} catch (Exception e) {						
			//gestire questo caso!!!
			validoMassivo = false;
			logger.error(" ERRORE VALIDAZIONE DI BUSINESS e/o PULIZIA MASSIVA >>>>>",e);
		}
		return validoMassivo;
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
			String silMittente, String esitoXML, String trtIdMittente, String trtSilMittente) {
		
		insertEsitoCart(E2EMsgId, idMittente, silMittente, esitoXML, StatoEnum.DA_SPEDIRE, trtIdMittente, trtSilMittente);
		
	}	



	/**
	 * 
	 * @param tipoUpd
	 * @param tipoOperazione
	 */  
	@Override
	protected void setTipoMessaggio(String tipoUpd, PrevisitingData prevData, String otf, PendenzeCartMessage cart) {

		if (tipoUpd.equals(prevData.getTipoOperazione())) {
			cart.setTipoMessaggio(StatoEnum.MSG_TYPE_ALL_MASSIVO_PENDENZE);
			cart.setTipoTributo(prevData.getTipoPendenza());
		} else {
			cart.setTipoMessaggio(StatoEnum.MSG_TYPE_ALL_PENDENZE);
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


	protected String startControllerUpdateMassivoTimer() {
		String status;
		try {
			String timerName = ServiceLocalMapper.ControllerUpdateMassivoTimer_NAME;
			int period = 150000;
			//
			// START
			//
			System.out.println("starting timer " + timerName + "(period=" + period + ") on this node");
			TimerData timerData = new TimerData(timerName, TimerData.Action.START, TimerData.DEFAULT_INTERVAL, period, "node1");
			timerController.executeApplicationTransaction(timerData);
			//
			// CHECK
			//
			System.out.println("checking timer " + timerName);
			timerData.setAction(TimerData.Action.CHECK);
			MonitoringData mData = timerController.executeApplicationTransaction(timerData);
			status = mData.getEsito();
			System.out.println("	checkstatus = " + status);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERRORE in startControllerUpdateMassivoTimer", e);
			status = "ERRORE: " + e.getMessage();
		}
		return status;
	}
	
	
	protected void elaborateMessage(String serviceNameType, String message) {
		
		DataInput input = getInput(message);
		DataStoreEngineService dataStoreEngineService = getDataStoreEngineService();
		
		boolean elaborateOk = elaborateMessageParte1(serviceNameType, message, input, dataStoreEngineService);
		
		if (elaborateOk)
			dataTx.callMe(dataStoreEngineService, input);

	}

	protected boolean elaborateMessageParte1(String serviceNameType, String message, DataInput input, DataStoreEngineService dataStoreEngineService) {
		EsitoBuilder esitoB = new EsitoBuilder();
		boolean silPresente = false;
		try {
			silPresente = findSilEnte(manager, idMittente, silMittente, trtSenderId, trtSenderSys);
			logger.info(this.getClass().getSimpleName() + " : SIL presente = " + silPresente );

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + " : Error checking SIL " + e.getMessage() );
		} 
		
		boolean ibanTrusted = true;
		if (prevData.getIban().size()>0) {						
			try {
				ibanTrusted = checkIbanTrusted(manager, prevData.getIban(), silMittente, prevData.getTipoPendenza());
				logger.info(this.getClass().getSimpleName() + " : ibanTrusted = " + ibanTrusted );
	
			} catch (Exception e) {
				logger.error(this.getClass().getSimpleName() + " : Error checking ibanTrusted " + e.getMessage() );
			} 
		}
		//se idPendenza non � univoco segnalo l'errore e non processo il messaggio.
		if (prevData.isPendenzeUnivoche()) {
//		if (false) {
			//gestione idPendenza non univoco
			managePendenzeNonUnivoche(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);

		} //se idPagamento non � univoco segnalo l'errore e non processo il messaggio.
		else if (prevData.isIdPagamentoUnivoci()) {
			//gestione idPagamento non univoco
			managePagamentoNonUnivoche(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);

		}  else if (silPresente==false) {
			//gestione SIL NON PRESENTE
			manageSilNonPresente(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);

		} else if (prevData.isConsistenzaTipoOperazione()==false) {
			//gestione TIPO OPERAZIONE NON CONSISTENTE
			manageTipoOperazioneNonConsistente(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		} else if (ibanTrusted==false) {
			//gestione IBAN NOT TRUSTED
			manageIbanNotTrusted(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		} else {
			
			boolean valido = true;
			StringBuilder buf = new StringBuilder();
			Set set = null;
			try {
				String validateXsd = System.getProperty(SystemPropertiesNames.ENV_VALIDATE_XSD);
				if ("true".equals(validateXsd)) {
					set = dataStoreEngineService.validate(input);
					Iterator iter = set.iterator();
					while (iter.hasNext()) {
						ExtendedErrorInfoImpl type = (ExtendedErrorInfoImpl) iter.next();
						logger.info(this.getClass().getSimpleName() + " ERRORE VALIDAZIONE XSD SCHEMA DATASTORAGE >>>>>" + type.getErrorDetail());
						buf.append(type.getErrorDetail()+";");
						valido = false;
					}
				}
			} catch (Exception e) {
				logger.error(" ERRORE VALIDAZIONE XSD SCHEMA DATASTORAGE >>>>>",e);
				valido = false;
			}

			boolean validoMassivo = true;
			if (valido && "UpdateMassivo".equals(prevData.getTipoOperazione())) {
				if (prevData.isTipoPendenzaUnivoco()) {
					validoMassivo = controlliUpdateMassivo(idMittente,
							silMittente, prevData); 
				} else {
					validoMassivo = false;
				}
			}

			if (valido && validoMassivo) {
				return true;
			} else if (!valido) { 
				manageXsdNonValido(esitoB, serviceNameType, E2EMsgId, idMittente,silMittente, idRicevente, silRicevente, set, trtSenderId, trtSenderSys);
			} else if (!validoMassivo) {
				manageXmlMassivoNonValido(esitoB, serviceNameType, E2EMsgId, idMittente,silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
			}

		}
		return false;
	}

	protected DataInput getInput(String message) {
		
		DataInput input = null;
		
		String transferMode = System.getProperty(SystemPropertiesNames.ENV_TRANSFER_MODE);
		if ("Memory".equals(transferMode)) {
			input = new DataInputByte(message.getBytes(Charset.defaultCharset()));
			message = null;
			logger.info("trasmetto il messaggio al DSE in modalita' INMEMORY ");
		} else if ("Database".equals(transferMode)) {
			String[][] keys= new String[][]{{E2EMsgId,"E2EMSGID"},{idMittente,"SenderId"},{silMittente,"SenderSys"}};
			DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
			input = new DataInputDb(reference);
			logger.info("Invio i riferimenti al DSE per lettura BLOB da tabella :  " + E2EMsgId );
		}
		
		return input;
		
	}
	
	protected DataStoreEngineService getDataStoreEngineService() {
		
		ConcreteFactory factory = new ConcreteFactory();
		String DseImpl = System.getProperty(SystemPropertiesNames.DSE_IMPL);
		logger.info("RRREPE:: DseImpl="+DseImpl);
		return factory.createDSE(DseImpl);
		
	}
	
	
}
