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

import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.ErroriIdpPK;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.namespace.QName;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
public class ErrorIdpManager implements ErrorTao {

	private static final String ERROR_IDP_MANAGER = "ERROR_IDP_MANAGER";

	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	@Resource 
	private EJBContext ejbCtx;		

	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 *
	 */
	public MonitoringData executeApplicationTransaction(Object data, Object dataAddOn, Object actor) {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		//messaggioXml
		String message = (String)data;
		StringBuilder contents = new StringBuilder(message);
		//stato
		String stato = (String)dataAddOn;
		String actorS = (String)actor;

		//estraggo l'id busta egov perchè mi serve come chiave
		String E2EMsgId = null;
		String idMittente = null;
		String silMittente = null;
		String idRicevente = null;
		String silRicevente = null;
		String serviceName = null;

		//ATTENZIONE; CONTROLLO IL SERVICE_NAME E SCARTO IL MESSAGGIO FAKE
		//ALTRIMENTI PROCEDO E SCRIVO SU DB COME AL SOLITO
		try {
			serviceName = readXml("ServiceName",contents);
		} catch (FileNotFoundException e1) {
			logger.info(this.getClass().getSimpleName() + e1.getMessage());
		} catch (FactoryConfigurationError e1) {
			logger.info(this.getClass().getSimpleName() + e1.getMessage());
		} catch (XMLStreamException e1) {
			logger.info(this.getClass().getSimpleName() + e1.getMessage());
		}
		logger.info(this.getClass().getSimpleName() + " Ricevuto messaggio dal servizio " + serviceName);
		//ATTENZIONE; FINE CONTROLLO MESSAGGIO FAKE

		if (serviceName!=null && serviceName.endsWith("Fake")) {
			//ATTENZIONE; INIZIO CONTROLLO MESSAGGIO FAKE
			logger.info(this.getClass().getSimpleName() + " Ricevuto messaggio FAKE dal servizio " + serviceName + " (Nessun salvataggio in ERRORI_IDP)");
			//ATTENZIONE; FINE CONTROLLO MESSAGGIO FAKE
		} else {

			//int numPend = 0;
			try {
				E2EMsgId = readXml("E2EMsgId",contents);
				idMittente = readXml("SenderId",contents);
				silMittente = readXml("SenderSys",contents);
				idRicevente = readXml("ReceiverId",contents);
				silRicevente = readXml("ReceiverSys",contents);
//				serviceName = readXml("ServiceName",contents);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
	//			e1.printStackTrace();
				logger.info(this.getClass().getSimpleName() + e1.getMessage());
			} catch (FactoryConfigurationError e1) {
				// TODO Auto-generated catch block
	//			e1.printStackTrace();
				logger.info(this.getClass().getSimpleName() + e1.getMessage());
			} catch (XMLStreamException e1) {
				// TODO Auto-generated catch block
	//			e1.printStackTrace();
				logger.info(this.getClass().getSimpleName() + e1.getMessage());
			}
			logger.info(this.getClass().getSimpleName() + " Esito in Errore (stato =" + stato + ", servizio " + serviceName+ ") su busta :  " + E2EMsgId + " del mittente " + idMittente + " sil " + silMittente);

			//monitoring data
			monData.setE2emsgid(E2EMsgId);
			monData.setSenderid(idMittente);
			monData.setSendersys(silMittente);
			monData.setReceiverid(idRicevente);
			monData.setReceiversys(silRicevente);
			monData.setNumRecord(1);

			try {
//				em = getManager();
//				tx = em.getTransaction();
//				tx.begin();

				ErroriIdp errorIdp = new ErroriIdp();
				ErroriIdpPK errorIdpPK = new ErroriIdpPK();

				errorIdpPK.setE2emsgid(E2EMsgId);
				errorIdpPK.setSenderid(idRicevente);
				errorIdpPK.setSendersys(silRicevente);
				if (!"ProxyIRISCentrale".equals(actor)) {
					errorIdpPK.setStato(StatoEnum.DA_REINVIARE);
				} else {
					errorIdpPK.setStato(StatoEnum.RIFIUTATO);
				}
				errorIdp.setPk(errorIdpPK);

				// non eseguo più l'inserimento secco..
				//perchè adesso gli esiti 'DA REINVIARE' vengono reinviati
				//e dunque possono essere scartati nuovamente...
				//cioè significa che se l'errore è già presente si deve procedere ad un aggiornamento
				//altrimenti lo si deve inserire
				//em.persist(errorIdp);
				ErroriIdp errorIdpDb = manager.find(ErroriIdp.class, errorIdpPK);
				manager.flush();

				if (errorIdpDb != null){ // mobile object may or may not be null.
					errorIdpDb.setOpAggiornamento(ERROR_IDP_MANAGER);
					errorIdpDb.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
					//aggiorno lo stato dell'errore
					manager.merge(errorIdpDb);
					logger.info(this.getClass().getSimpleName() + " eseguito aggiornamento errore , statoAttuale = " + errorIdpDb.getPk().getStato() + " , TS inserimento  " + errorIdpDb.getTsInserimento() );
				} else {
					errorIdp.setEsitoXml(message.getBytes(Charset.forName("UTF-8")));
					errorIdp.setDescrizioneStato(stato);
					errorIdp.setReceiverid(idMittente);
					errorIdp.setReceiversys(silMittente);
					errorIdp.setOpInserimento(ERROR_IDP_MANAGER);
					errorIdp.setTsInserimento(new Timestamp(System.currentTimeMillis()));
					errorIdp.setServiceName(serviceName);
					//inserisco
					manager.persist(errorIdp);
					logger.info(this.getClass().getSimpleName() + " inserimento errore, statoAttuale = " + errorIdp.getPk().getStato() + " , E2EMSGID = " + E2EMsgId + " del mittente " + idMittente + " sil " + silMittente);
				}

				if (StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE.equals(serviceName)) {
					//aggiorno anche lo stato della ESITI_CART in uscita
					//da valutare se questa operazione è necessaria o meno....
					if (!"ProxyIRISCentrale".equals(actor)) {
						//se non è colpa di IRIS IDP allora posso pure decidere di reinviarlo
						//in questo caso non c'è bisogno di ricreare l'esito
						//occorre solamente reinviarlo prima che scada il timeout
						updateStatoEsito(manager, E2EMsgId, idRicevente, silRicevente, StatoEnum.DA_REINVIARE);
					} else {
						//se invece è colpa di IRIS IDP allora potrei mettere la pendenza_Cart
						//in stato DA ELABORARE e cancellare l'ESITO CART
						//in modo tale che, prima del timeout e dopo un nuovo rilascio,
						//l'esito possa essere rigenerato e rispedito
						//non implementato, da approfondire
						updateStatoEsito(manager, E2EMsgId, idRicevente, silRicevente, StatoEnum.RIFIUTATO);
					}
				}
				//in caso serviceName = IdpInformativaPagamentoPendenze occorre aggiornare lo stato della NOTIFICHE_CART
				//da valutare...
	//			if ("IdpInformativaPagamentoPendenze".equals(serviceName))

				//in caso serviceName = IdpRendicontazioneEnti occorre aggiornare lo stato della RENDICONTAZIONE_CART
				//da valutare...
	//			if ("IdpRendicontazioneEnti".equals(serviceName))


				logger.info(this.getClass().getSimpleName() + " salvato esito in errore " + E2EMsgId + " del mittente " + idMittente + " sil " + silMittente);

			} catch (Exception e) {
				ejbCtx.setRollbackOnly();
				logger.error(" ERROR saving Blob Esito in ErroriIDP >>>>>" + e.getMessage());
				throw new RuntimeException("Error saving Blob Esito in ErroriIDP");
			} finally {
			}

		}

		return monData;
	}


	/**
	 *
	 * @param contents
	 * @throws FactoryConfigurationError
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
		private  String readXml(String idMsgTrtTag, StringBuilder contents) throws FactoryConfigurationError,
				FileNotFoundException, XMLStreamException {

			//Creo input factory
			XMLInputFactory factory = XMLInputFactory.newInstance();
		    Reader fileReader = new StringReader(contents.toString());
		    XMLEventReader reader = factory.createXMLEventReader(fileReader);

		    //lista tag necessari
		    boolean idMsgTrtFound = false;

		    //bean di uscita
		    String dati = "";

		    //leggo dal reader
		    while (reader.hasNext()) {
		      //evento
		      XMLEvent event = reader.nextEvent();
		      //entro se è l'apertura di un elemento
		      if (event.isStartElement()) {
		        StartElement element = (StartElement) event;
	//	        logger.info("Start Element: " + element.getSimpleName().getLocalPart());

		        //cerco il tag che mi interessa
		        if (element.getName().getLocalPart().equals(idMsgTrtTag)) {
		        	idMsgTrtFound = true;
				}
		        //stampo gli attributi del tag
		        Iterator iterator = element.getAttributes();
		        while (iterator.hasNext()) {
		          Attribute attribute = (Attribute) iterator.next();
		          QName name = attribute.getName();
		          String value = attribute.getValue();
		        }
		      }
		      //entro se è la chiusura di un elemento
		      if (event.isEndElement()) {
		        EndElement element = (EndElement) event;
		      }
		      //entro e stampo il contenuto di un tag
		      if (event.isCharacters()) {
		        Characters characters = (Characters) event;
		        if (idMsgTrtFound) {
	//	        	dati.setXmlData(characters.getData().getBytes());
		        	dati = characters.getData();
		        	idMsgTrtFound = false;
				}
		      }
		    }

		    logger.info(this.getClass().getSimpleName() + " trovato tag " + idMsgTrtTag + " = " + dati);
		    return dati;
		}


	/**
	 *
	 * @param em
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @param stato
	 */
	private void updateStatoEsito(EntityManager em, String E2EMsgId,
			String idMittente, String silMittente, String stato ) {
		Query queryUpdateEsiti = em.createQuery ("Update EsitiCart esiti " +
				" set esiti.stato = :stato , " +
				" esiti.tsAggiornamento = :ts_aggiornamento , " +
				" esiti.opAggiornamento = :op_aggiornamento  " +
				" WHERE esiti.pk.e2emsgid = :e2emsgid" +
				" AND esiti.pk.senderid= :senderId " +
				" AND esiti.pk.sendersys = :senderSys ");

		queryUpdateEsiti.setParameter("stato", stato);
		queryUpdateEsiti.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdateEsiti.setParameter("op_aggiornamento", ERROR_IDP_MANAGER);


		queryUpdateEsiti.setParameter("e2emsgid", E2EMsgId);
		queryUpdateEsiti.setParameter("senderId", idMittente);
		queryUpdateEsiti.setParameter("senderSys", silMittente);

		int esitiPend = queryUpdateEsiti.executeUpdate();
	}


	/**
	 *
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//			return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
//			return manager;
		//In caso di persistence di tipo JTA
		return manager;			
	}
}
