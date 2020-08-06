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
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.monitoring.MonitoringInterceptor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.interceptor.Interceptors;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Interceptors(MonitoringInterceptor.class)
@Stateless
@Remote(CommandExecutor.class)
public class ReinoltroEsiti implements CommandExecutor, CommandExecutorLocal {

//	Introdotto dopo il passaggio a CMT/JTA
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	
	@Resource 
	private EJBContext ejbCtx;
	
	@EJB(beanName = "DataManager")
	private DataStorageInterface dataTx;			
	
	private final Log logger = LogFactory.getLog(this.getClass());
	
	public static final String REINOLTRO_ESITI_IN_SPEDIZIONE="REINOLTRO_ESITI_IN_SPEDIZIONE";
	public static final String REINOLTRO_ESITI_NON_INVIATI="REINOLTRO_ESITI_NON_INVIATI";

	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();
		
		try {

			//calcolo la lista degli esiti rifiutati e da reinviare
			List<ErroriIdp> lins = listaEsitiDaRispedire();

			logger.info(this.getClass().getSimpleName() + " trovati esiti da rispedire a Cart, numtotale = " + lins.size()  );
			//monitoring data
			monData.setNumRecord(lins.size());

			//questo metodo è un pò da sistemare....
			//dic 2014... ormai sistemiamo dopo il passaggio a JTA/CMT

			String e2emsgid = "";
			String senderId = "";
			String senderSys = "";
			String trtSenderId = "";
			String trtSenderSys = "";			

			for (int i = 0; i < lins.size(); i++) {
				//recupero esito corrente
				ErroriIdp erroreIdp = (ErroriIdp)lins.get(i);

				//scandaloso questo ulteriore try/catch, sistemare con nuovo bean
				//se la spedizione di un flusso pendenza va in errore allora
				//procede comunque con il successivo
				try {

					e2emsgid = new String(erroreIdp.getPk().getE2emsgid().trim());
					senderId = new String(erroreIdp.getPk().getSenderid().trim());
					senderSys = new String(erroreIdp.getPk().getSendersys().trim());
					trtSenderId = new String(erroreIdp.getTrtSenderId()!=null ? erroreIdp.getTrtSenderId() : "");
					trtSenderSys = new String(erroreIdp.getTrtSenderSys()!=null ? erroreIdp.getTrtSenderSys() : "");					
					String esitoXml = new String(erroreIdp.getEsitoXml());
					
					//prima di spedire devo controllare però che non sia stata già spedita
					ErroriIdp error = new ErroriIdp();
					ErroriIdpPK errorPK = new ErroriIdpPK();
					errorPK.setStato("REINVIATO");
					errorPK.setE2emsgid(e2emsgid);
					errorPK.setSenderid(senderId);
					errorPK.setSendersys(senderSys);
					ErroriIdp errorExist = manager.find(ErroriIdp.class, errorPK);

					if (errorExist==null) {
						
						logger.info(this.getClass().getSimpleName() + " Reinoltro Notifica in corso "+e2emsgid);
					
						//spedisce
						this.sendJmsMessage("JmsConnectionFactory","JmsPosizioneDebitoriaOutput", esitoXml);
						logger.info(this.getClass().getSimpleName() + " esito RISPEDITO verso cart, idBustaEgov = " + e2emsgid + " - " + senderId + " - " + senderSys);

						//creo la chiave
						//int esitiUpd = updateReinoltroEsiti(e2emsgid, senderId,
						//		senderSys, erroreIdp);
						//logger.info(this.getClass().getSimpleName() + " ho cambiato (esito="+esitiUpd+") lo stato dell'esito cart " + e2emsgid + " - " + senderId + " -  " + senderSys + " , num. esiti = " + esitiUpd );
						dataTx.updateReinoltroEsiti(e2emsgid, senderId, senderSys, erroreIdp, trtSenderId, trtSenderSys);
						
					} else {
						logger.info(this.getClass().getSimpleName() 
								+ " Reinoltro Notifica "+e2emsgid+" non eseguito, già in corso da "+errorExist.getTsAggiornamento());
					}						

				} catch (Exception e) {
					e.printStackTrace();
					//ejbCtx.setRollbackOnly();
					logger.error(this.getClass().getSimpleName() + " ERROR REINOLTRO SU FLUSSO = " + e2emsgid);
				}

			}

			logger.info(this.getClass().getSimpleName() + " creazione/spedizione esiti terminata " );

		} catch (Exception e) {
			logger.error(this.getClass().getSimpleName() + " ERROR REINOLTRO ESITI = " + e.getMessage());
		}
		finally {
//			if (em!=null && em.isOpen()) em.close();
		}
		return monData;
	}




	/**
	 *
	 * @return
	 */
	private List<ErroriIdp> listaEsitiDaRispedire() {	
		Timestamp currentTs = new Timestamp(System.currentTimeMillis());
		int before = 28800000;
		//8hours
		Timestamp to = new Timestamp(System.currentTimeMillis()-before);		
		//36hours
		int beforeAlot = 144000000;			
		Timestamp from = new Timestamp(System.currentTimeMillis()-beforeAlot);
		//years before
//		BigInteger yearsBefore = new BigInteger("24400000000");
//		Timestamp from = new Timestamp(System.currentTimeMillis()-yearsBefore.longValue());

		Query queryErroriIdp = manager.createQuery(
				"SELECT erroriIdp FROM ErroriIdp erroriIdp " +
				" WHERE erroriIdp.pk.stato = :stato" +
				" AND erroriIdp.serviceName = :serviceName " +
				" AND erroriIdp.tsInserimento>= :from " +
				" AND erroriIdp.tsInserimento<= :to " +
				" AND (erroriIdp.opAggiornamento is null "
				+ " OR erroriIdp.opAggiornamento= :opAgg)");			
		queryErroriIdp.setParameter("stato", StatoEnum.DA_REINVIARE);
		queryErroriIdp.setParameter("serviceName", StatoEnum.SERVICE_NAME_TYPE_ALL_PENDENZE);
		queryErroriIdp.setParameter("from", from);
		queryErroriIdp.setParameter("to", to);
		queryErroriIdp.setParameter("opAgg", "ERROR_IDP_MANAGER");	
					
		List<ErroriIdp> lins = (List) queryErroriIdp.getResultList();
		return lins;
	}


	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
			return manager;
	}


	/*********************************
	 * NUOVA GESTIONE SMARTSIL
	 *********************************/
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		if (REINOLTRO_ESITI_IN_SPEDIZIONE.equals(data)) {
			return  this.reinoltroEsitiInSpedizione();
		} else if (REINOLTRO_ESITI_NON_INVIATI.equals(data)) {
			return  this.reinoltroEsitiNonInviati();
		} else {
			logger.warn("parametro "+data+" non gestito. Chiamata ignorata");
			return null;
		}
	}

	
	/**
	 * Reinoltro Esiti In stato IN_SPEDIZIONE dopo la scadenza di un  delay.
	 * @return
	 */
	private MonitoringData reinoltroEsitiInSpedizione() {


		//monitoring data
		MonitoringData monData = new MonitoringData();

		logger.info("Inizio Reinoltro esiti SSIL");
		
		try {
		
		// Questo task serve per  Rimettere in stato "DA SPEDIRE" eventuali messaggi di esito che possono essere rimasti in stato "IN_SPEDIZIONE" 
		// Parametri:
		// reinoltro.esiti.in_spedizione.millis.retry = Delay rispetto all'ultimo aggiornamento per riprovare un 
		
		// Lettura parametri da properties
		Long millisRetryInSpedizione = IrisProperties.getLongProperty("reinoltro.esiti.in_spedizione.millis.retry",60*60*1000L); // Default 1 ora
		
		// --------------------------------------
		// Recupero degli esiti "IN SPEDIZIONE"
		// --------------------------------------
		
		String queryRecuperoInSpedizione = 
				"UPDATE EsitiCart esitiCart " +
		        " SET esitiCart.stato = :stato_new " +
		        "   , esitiCart.opAggiornamento = :op_aggiornamento" +
		        "   , esitiCart.tsAggiornamento = :ts_aggiornamento  " +		        
		        " WHERE esitiCart.stato = :stato_old" +
		        " AND   esitiCart.tsAggiornamento <= :timestamp_max " ;
		
		Timestamp maxTimestamp = new Timestamp(System.currentTimeMillis()-millisRetryInSpedizione);
		logger.debug(this.getClass().getSimpleName() + " Recupero i rimasti in stato IN_SPEDIZIONE: query update EsitiCart = " + queryRecuperoInSpedizione);
		logger.info(this.getClass().getSimpleName() + " Cerco esiti in stato IN_SPEDIZIONE con timestamp aggiornamento <= " + maxTimestamp);
		
		Query queryUpdateEsitiCart = manager.createQuery (queryRecuperoInSpedizione);
		queryUpdateEsitiCart.setParameter("stato_new", StatoEnum.DA_SPEDIRE);			
		queryUpdateEsitiCart.setParameter("op_aggiornamento", "REINOLTRO_ESITI");
		queryUpdateEsitiCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdateEsitiCart.setParameter("stato_old", StatoEnum.IN_SPEDIZIONE);
		queryUpdateEsitiCart.setParameter("timestamp_max", maxTimestamp);

		int esitiUpdated = queryUpdateEsitiCart.executeUpdate();
							
		logger.info(this.getClass().getSimpleName() + " rimessi 'DA SPEDIRE' num. esiti = " + esitiUpdated);
		
		} catch (Exception e) {
			logger.error("Errore in esecuzione ReinoltroEsiti SSIL",e);
			ejbCtx.setRollbackOnly();
		} finally {
			logger.info(this.getClass().getSimpleName() + " fine Reinoltro esiti SSIL");
		}
	
		return monData;
	}
	
	
	
	/**
	 * Reinoltro Esiti In stato IN_SPEDIZIONE dopo la scadenza di un  delay.
	 * @return
	 */
	private MonitoringData reinoltroEsitiNonInviati() {
		
		//monitoring data
		MonitoringData monData = new MonitoringData();

		logger.info("Inizio Reinoltro esiti SSIL");
		
		try {

		
			// Questo task serve per rimettere in stato "DA SPEDIRE" eventuali messaggi di esito che siano in stato "NON INVIATO" (ovvero abortiti per aver superato maxnum.tentativi di spedizione).
			// Parametri:
			// reinoltro.esiti.non_inviati.millis.retry = Delay rispetto all'ultimo aggiornamento per riprovare la spedizione di un messaggio (NON INVIATO)
			// reinoltro.esisi.non_inviati.millis.keep.retrying = Massimo intervallo di tempo (dalla creazione del messaggio) per il quale viene riprovata la spedizione di un messaggio (NON INVIATO)
			
			// Lettura parametri da properties
			Long millisRetryNonInviato = IrisProperties.getLongProperty("reinoltro.esiti.non_inviati.millis.retry",60*60*1000L); // Default 1 ora
			Long millisKeepRetryNonInviato = IrisProperties.getLongProperty("reinoltro.esiti.non_inviati.millis.keep.retrying",48*60*60*1000L); // Default 2 giorni (48 ore)
			
	
			// --------------------------------------
			// Retry degli esiti "NON INVIATI" 
			// --------------------------------------
	
			
			String queryExtractEsitiNonInviatiString = 
					"SELECT esitiCart FROM EsitiCart esitiCart " +		        
				    " WHERE esitiCart.stato = :stato_old" +
			        " AND   esitiCart.tsAggiornamento <= :timestamp_max " +
					" AND   esitiCart.tsInserimento >= :timestamp_min " ;
	
					
			Timestamp maxTimestampNonInviato = new Timestamp(System.currentTimeMillis()-millisRetryNonInviato);
			Timestamp minTimestampNonInviato = new Timestamp(System.currentTimeMillis()-millisKeepRetryNonInviato);
	
			logger.debug(this.getClass().getSimpleName() + " Estraggo gli esiti rimasti in stato NON_INVIATO: query extract EsitiCart = " + queryExtractEsitiNonInviatiString);
			logger.info(this.getClass().getSimpleName() + " Cerco gli esiti in stato NON_INVIATO con timestamp aggiornamento <= " + maxTimestampNonInviato +" e ts_inserimento dopo il "+minTimestampNonInviato);
	
			Query queryExtractEsitiNonInviati = manager.createQuery (queryExtractEsitiNonInviatiString);
			queryExtractEsitiNonInviati.setParameter("timestamp_min", minTimestampNonInviato);
			queryExtractEsitiNonInviati.setParameter("stato_old", StatoEnum.NON_INVIATO);
			queryExtractEsitiNonInviati.setParameter("timestamp_max", maxTimestampNonInviato);
	
			List<EsitiCart> esitiDaAggiornare = (List<EsitiCart>)queryExtractEsitiNonInviati.getResultList();
			
			logger.info(this.getClass().getSimpleName() + " trovato num. esiti = " + esitiDaAggiornare.size() + " Da riproporre automaticamente " );
		
			
			int totaleEsitiUpdated = 0;
			int totalePendenzeCartUpdated = 0;
			
			
			for (EsitiCart esitoCart:esitiDaAggiornare) {
				
				//reinoltra esito:
			
				String queryUpdateReinoltroEsitoString = "UPDATE EsitiCart esitiCart set " +
												         " esitiCart.tsAggiornamento = :ts_aggiornamento"+
												         " ,esitiCart.opAggiornamento = 'REINOLTRO ESITI NON INVIATI'"+
												         " ,esitiCart.stato = :stato "+
												         " ,esitiCart.tentativi = 0 " + 
												         "WHERE esitiCart.pk = :pk  "+
												         " AND stato = :stato_old";
												   
				Query queryUpdateReinoltroEsito = manager.createQuery (queryUpdateReinoltroEsitoString);
				queryUpdateReinoltroEsito.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
				queryUpdateReinoltroEsito.setParameter("stato_old", StatoEnum.NON_INVIATO);
				queryUpdateReinoltroEsito.setParameter("stato", StatoEnum.DA_SPEDIRE);
				queryUpdateReinoltroEsito.setParameter("pk", esitoCart.getPk());
						
				
				// reinoltra pendenze cart:
												
				String queryUpdateReinoltroPendenzeCartString = "UPDATE PendenzeCart pendenzeCart set " +
						   								  "  pendenzeCart.tsAggiornamento = :ts_aggiornamento"+
						   								  " ,pendenzeCart.opAggiornamento = 'REINOLTRO ESITI NON INVIATI'"+
						   								  " ,pendenzeCart.stato = :stato "+ 
						   								  "WHERE pendenzeCart.pk = :pk  "+
						   								  " AND stato = :stato_old";

				PendenzeCartPK pendenzeCartPk=new PendenzeCartPK();
				pendenzeCartPk.setE2emsgid(esitoCart.getPk().getE2emsgid());
				pendenzeCartPk.setSenderid(esitoCart.getPk().getSenderid());
				pendenzeCartPk.setSendersys(esitoCart.getPk().getSendersys());
				
				Query queryUpdateReinoltroPendenzeCart = manager.createQuery (queryUpdateReinoltroPendenzeCartString);
				queryUpdateReinoltroPendenzeCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
				queryUpdateReinoltroPendenzeCart.setParameter("stato_old", StatoEnum.RISPOSTA_NON_INVIATA);
				queryUpdateReinoltroPendenzeCart.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
				queryUpdateReinoltroPendenzeCart.setParameter("pk", pendenzeCartPk);

				// eseguo le query:
				int esitiUpdated= queryUpdateReinoltroEsito.executeUpdate();
				int pendenzeCartUpdated = queryUpdateReinoltroPendenzeCart.executeUpdate();
				
				totaleEsitiUpdated+=esitiUpdated;
				totalePendenzeCartUpdated+=pendenzeCartUpdated;
				
				logger.info("Riproposizione esito: "+esitoCart.getPk().getSenderid() +", "+esitoCart.getPk().getSendersys()+" ,"+esitoCart.getPk().getE2emsgid()+"=> update: esiti_cart="+esitiUpdated+" , pendenze_cart="+pendenzeCartUpdated);
								
			}
		
			logger.info(this.getClass().getSimpleName() + " rimessi 'DA SPEDIRE' num. esiti = " + totaleEsitiUpdated + " e num. pendenze cart = "+totalePendenzeCartUpdated);

	
		} catch (Exception e) {
			logger.error("Errore in esecuzione ReinoltroEsiti SSIL (NON INVIATI)",e);
			ejbCtx.setRollbackOnly();
		} finally {
			logger.info(this.getClass().getSimpleName() + " fine Reinoltro esiti SSIL (NON INVIATI)");
		}
	
		return monData;
	}

	

	@Override
	public String executeHtml() throws Exception  {

	String table = "";
	//connessione db
//	EntityManager em = null;
	try {
//		em = getManager();
		//calcolo la lista delle pendenze per cui gli esiti sono pronti
		//per essere creati e spediti
		List<ErroriIdp> lins = listaEsitiDaRispedire();

		logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
		table = "<br><br>";
		table += "<b>Lista esiti RIFIUTATI e pronti per RIspediti</b>";
		table += "<br>";
		table+="<TABLE border=\"\1\">";
		table+="<TR>";
			table+="<TD>ID</TD>";
			table+="<TD>SENDERID</TD>";
			table+="<TD>SENDERSYS</TD>";
			table+="<TD>STATO</TD>";
			table+="<TD>DEX STATO</TD>";
			table+="<TD>TS INSERIMENTO</TD>";
		table+="</TR>";

		for (ErroriIdp object : lins) {
			table+="<TR>";
				table+="<TD>";
					table+=(object.getPk().getE2emsgid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSenderid());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getSendersys());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getPk().getStato());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getDescrizioneStato());
				table+="</TD>";
				table+="<TD>";
					table+=(object.getTsInserimento());
				table+="</TD>";
			table+="</TR>";
		}
		table+="</TABLE>";

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
//		table+="</PRE>";
		return table;
	}


    /**
     *
     * @param connectFact
     * @param jmsInput
     * @throws JMSException
     */
	private void sendJmsMessage(String connectFact, String jmsInput, String esit) throws JMSException {
		Destination dest = null;
		Connection conn = null;
		ConnectionFactory cf= null;
		Session session = null;
		Message textMessage = null;
		MessageProducer producer = null;

		cf = (QueueConnectionFactory)IdpServiceLocator.getInstance().getServiceByName(connectFact);
		conn = cf.createConnection();
		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

		textMessage = session.createTextMessage(esit);
//		objectMessage = session.createObjectMessage(new TestMessage("**** Object Message Content"));

		dest = (Queue) IdpServiceLocator.getInstance().getServiceByName(jmsInput);

		producer = session.createProducer(dest);
		
		// SET MESSAGE DIRECTION AND OWNER
		textMessage.setBooleanProperty("output", true);  
		textMessage.setStringProperty("owner", "IRIS");

		producer.send(textMessage);  
	}

	
	
	
	


}
