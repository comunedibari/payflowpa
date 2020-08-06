package it.tasgroup.idp.bean;

import it.tasgroup.dse.errorInfo.ExtendedErrorInfoImpl;
import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DBReference;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.dse.view.DataInputByte;
import it.tasgroup.dse.view.DataInputDb;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.esiti.EsitoBuilder;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.ConcreteFactory;
import it.tasgroup.idp.util.SystemPropertiesNames;
import it.tasgroup.idp.util.StatoEnum;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class DataStorageManagerSmart extends DataStorageManagerCommon implements CommandExecutor, CommandExecutorLocal { 
	
	final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 
	 */
	protected String manageTipoOperazioneNonConsistente(EntityManager em,
			EntityTransaction tx, EsitoBuilder esitoB, String serviceNameType,
			String E2EMsgId, String idMittente, String silMittente,
			String idRicevente, String silRicevente) {		
		
		String esitoXML = "true";

		return esitoXML;		
		
	}		
	
	

	/**
	 *
	 */
	public MonitoringData executeApplicationTransaction(String message) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		EntityManager em = null;
		EntityTransaction tx = null;

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
		logger.info( " Num_Pend_XPath " + prevData.getNumPendXPath() ); 
		//salvo su tabella monitoring anche il numero di pendenze
		monData.setNumRecord(prevData.getNumPendXPath());
		
		//se non si riesce ad eseguire alcun mapping.. allora salviamo il mex
		//nella tabella errori
		//non mi fido di questo controllo
		//questa e' di competenza del proxyIris
//		if (prevData.getE2EMsgId()==null) {
//			throw new RuntimeException("Ricevuto messaggio non conforme");
//		}		

		//recupero il serviceNameType
		String serviceNameType = "UpdateMassivo".equals(prevData.getTipoOperazione()) ? StatoEnum.SERVICE_NAME_TYPE_ALL_MASSIVO_PENDENZE : StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE;

		try {

			em = getManager();
			tx = em.getTransaction();
			tx.begin();

			//salvo su Pendenze_cart
			savingBlobOnDatabase(message, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, null, prevData,
					trtSenderId, trtSenderSys);

			//salvo e poi sono a posto
			tx.commit();

			logger.info("Salvata busta con blob :  " + E2EMsgId + ", numPendenze = " + prevData.getNumPendXPath());

		} catch (Exception e) {
			if (tx!=null && tx.isActive()) tx.rollback();
			logger.error(this.getClass().getSimpleName() + " : Error Saving BLOB " + e.getMessage() );
			e.printStackTrace();
			throw new RuntimeException("Error saving Blob");
		} finally {
			if (em!=null && em.isOpen()) em.close();
		}
		
		//rimuovo il file da filesystem, adesso si trova su tabella
		removeBlobFromFS(fileName);

		//qui si deve controllare il SIL
		//ed in caso non sia valido si deve creare e gestire l'errore
		//come nel caso di pendenzeNonUnivoche e IdPagamentoNonUnivoci
		//ATTENZIONE --- SCOMMENTARE DA QUI .....
		boolean silPresente = false;
		try {
			em = getManager();
			tx = em.getTransaction();
			tx.begin();

			//controllo SIL
			silPresente = findSilEnte(em, idMittente, silMittente, trtSenderId, trtSenderSys);
			logger.info(this.getClass().getSimpleName() + " : SIL presente = " + silPresente );

			tx.commit();

		} catch (Exception e) {
			if (tx!=null && tx.isActive()) tx.rollback();
			logger.error(this.getClass().getSimpleName() + " : Error checking SIL " + e.getMessage() );
		} finally {
			if (em!=null && em.isOpen()) em.close();
		}
//		ATTENZIONE --- SCOMMENTARE FINO A QUI .....
//		ATTENZIONE --- ELIMINARE LA RIGA QUI SOTTO .....
//		boolean silPresente = true;

		//se idPendenza non è univoco segnalo l'errore e non processo il messaggio.
		if (prevData.isPendenzeUnivoche()) {
//		if (false) {
			//gestione idPendenza non univoco
			managePendenzeNonUnivoche(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);

		} //se idPagamento non è univoco segnalo l'errore e non processo il messaggio.
		else if (prevData.isIdPagamentoUnivoci()) {
			//gestione idPagamento non univoco
			managePagamentoNonUnivoche( esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);

		}  else if (silPresente==false) {
			//gestione SIL NON PRESENTE
			manageSilNonPresente( esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);

		} else if (prevData.isConsistenzaTipoOperazione()==false) {
			//gestione TIPO OPERAZIONE NON CONSISTENTE
			manageTipoOperazioneNonConsistente(esitoB, serviceNameType, E2EMsgId, idMittente,
					silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
		} else {
			//se idPendenza e idPagamento è univoco allora procedo con l'inserimento
			DataInput input = null;
			//preparo il dataInput con i dati in memoria
			//DataInput input = new DataInputByte(message.getBytes(Charset.defaultCharset()));
			//preparo il dataInput con l'indicazione dei dati su Db
			//String[][] keys= new String[][]{{E2EMsgId,"E2EMSGID"},{idMittente,"SenderId"},{silMittente,"SenderSys"}};
			//DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
			//DataInput inputDb = new DataInputDb(reference);

			String transferMode = System.getProperty(SystemPropertiesNames.ENV_TRANSFER_MODE);
			if ("Memory".equals(transferMode)) {
				input = new DataInputByte(message.getBytes(Charset.defaultCharset()));
				message = null;
				logger.info("trasmetto il messaggio al DSE in modalità INMEMORY ");
			} else if ("Database".equals(transferMode)) {
				String[][] keys= new String[][]{{E2EMsgId,"E2EMSGID"},{idMittente,"SenderId"},{silMittente,"SenderSys"}};
				DBReference reference= new DBReference("PENDENZE_CART",keys,"Messaggio_Xml");
				input = new DataInputDb(reference);
				logger.info("Invio i riferimenti al DSE per lettura BLOB da tabella :  " + E2EMsgId );
			}
 
			//elaborazione messaggio
			ConcreteFactory factory = new ConcreteFactory();
			String DseImpl = System.getProperty(SystemPropertiesNames.DSE_IMPL);
			logger.info("RRREPE:: DseImpl="+DseImpl);
			DataStoreEngineService dataStoreEngineService = factory.createDSE(DseImpl);

			boolean valido = true;
			StringBuilder buf = new StringBuilder();
			Set set = null;
			try {
				//eseguo la validazione
				String validateXsd = System.getProperty(SystemPropertiesNames.ENV_VALIDATE_XSD);
				if ("true".equals(validateXsd)) {
					//eseguo la validazione
					set = dataStoreEngineService.validate(input);
					Iterator iter = set.iterator();

					while (iter.hasNext()) {
						ExtendedErrorInfoImpl type = (ExtendedErrorInfoImpl) iter.next();
						logger.info(this.getClass().getSimpleName() + " ERRORE VALIDAZIONE XSD SCHEMA DATASTORAGE >>>>>" + type.getErrorDetail());
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
			}

			//controllo se è update massivo
			boolean validoMassivo = true;
			//eseguo solo se però è ALMENO valido rispetto al XSD
			if (valido && "UpdateMassivo".equals(prevData.getTipoOperazione())) {

				em = getManager();
				//controlli preliminari, da valutare se possono stare qui dentro
				//o, appunto, spostati nel DSE
//				boolean validoBizMassivo = validateXml(idMittente, silMittente, prevData.getTipoPendenza(),fileName);

				//scrivo su tabella che si tratta di UPDATE MASSIVO

				if (prevData.isTipoPendenzaUnivoco()) {
					try {
						//se il tipoPendenza è univoco, allora proseguo e controllo le validità di ente e tributo
//						LB, 02052011
						validoMassivo = checkEnteTributo(em,idMittente,silMittente,prevData.getTipoPendenza());
						//select ID_ENTE from JLTENTI where CD_ENTE = JLTPEND.E2ESndrId and STATO = "A"
						//select ID_TRIBUTO from JLTENTR where ID_ENTE = JLTPEND.ID_ENTE and CD_TRB_ENTE = JLTPEND.CD_TRB_ENTE and ID_SISTEMA = JLTPEND.ID_SYSTEM and STATO = "A"

						if (validoMassivo) {
							logger.info("Massivo not configured");
						}
						
					} catch (Exception e) {
						e.printStackTrace();
						//gestire questo caso!!!
						validoMassivo = false;
						logger.error(" ERRORE VALIDAZIONE DI BUSINESS e/o PULIZIA MASSIVA >>>>>",e);
					} finally  {
						if (em!=null && em.isOpen()) em.close();
					}

				} else {
					//altrimenti il file è considerato non valido
					validoMassivo = false;
				}
			}

			//se lo schema è valido allora eseguo l'inserimento
			//se si verifica eccezione in inserimento allora lascio perdere, non rilancio eccezione
			//il flusso sarà fatto ripartire dalla 'ripartenza'
			if (valido && validoMassivo) {
				try {
					//se la validazione va a posto allora inserisco
					dataStoreEngineService.store(input);
				} catch (Exception e) {
					//in caso di eccezione gestire STATO per ripartenza
					logger.error(" ERRORE DATASTORAGE >>>>>",e);
				}
			} else if (!valido) {
				//gestione flusso non valido XSD
				manageXsdNonValido(esitoB, serviceNameType, E2EMsgId, idMittente,silMittente, idRicevente, silRicevente, set, trtSenderId, trtSenderSys);
			} else if (!validoMassivo) {
				//gestione flusso valido XSD ma NON VALIDO PER IL MASSIVO
				manageXmlMassivoNonValido( esitoB, serviceNameType, E2EMsgId, idMittente,silMittente, idRicevente, silRicevente, trtSenderId, trtSenderSys);
			}

		}
		return monData;
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
	protected void insertEsitoCartBridge(EntityManager em, String E2EMsgId, String idMittente,
			String silMittente, String esitoXML, String trtSenderId, String trtSenderSys) {
		
		insertEsitoCart(E2EMsgId, idMittente, silMittente, esitoXML, StatoEnum.DA_SPEDIRE, trtSenderId, trtSenderSys);
		
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



	@Override
	protected void insertEsitoCartBridge(String E2EMsgId, String idMittente,
			String silMittente, String esitoXML, String trtSenderId, String trtSenderSys) {
		// TODO Auto-generated method stub
		
	}


	
	
	
}
