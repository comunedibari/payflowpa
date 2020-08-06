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
public class ReinoltroNotifiche implements CommandExecutor, CommandExecutorLocal {

//	Introdotto dopo il passaggio a CMT/JTA
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	private EntityManager manager;
	@Resource 
	private EJBContext ejbCtx;
	
	@EJB(beanName = "DataManager")
	private DataStorageInterface dataTx;			

	private final Log logger = LogFactory.getLog(this.getClass());
	
	Timestamp to = null;		
	Timestamp from = null;
	
	public static final String REINOLTRO_NOTIFICHE_IN_SPEDIZIONE="REINOLTRO_NOTIFICHE_IN_SPEDIZIONE";
	public static final String REINOLTRO_NOTIFICHE_NON_INVIATE="REINOLTRO_NOTIFICHE_NON_INVIATE";


	/**
	 *
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction() {
		//monitoring data
		MonitoringData monData = new MonitoringData();

		Timestamp currentTs = new Timestamp(System.currentTimeMillis());
		int before = 28800000;
		//8hours
		setTo(new Timestamp(System.currentTimeMillis()-before));
		//36hours (40ore)
		int beforeAlot = 144000000;			
		setFrom(new Timestamp(System.currentTimeMillis()-beforeAlot));	
		
		execute();

		return monData;
	}

	
	/**
	 * 
	 * @param result
	 * @return
	 */
	private void execute() {
		
		try {			
	
			//calcolo la lista delle notifiche rifiutate e da reinviare
			List<ErroriIdp> lins = listaNotificheDaRispedire(getFrom(), getTo());			
			String result = " how many = "  + lins.size();			
			
			String e2emsgid = "";
			String senderId = "";
			String senderSys = "";
			String trtSenderId = "";
			String trtSenderSys = "";
				
			for (int i = 0; i < lins.size(); i++) {
				//recupero esito corrente
				ErroriIdp erroreIdp = (ErroriIdp)lins.get(i);
				
				try {
					//recupero la chiave
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
						if (erroreIdp.getServiceName().equals("IdpInformativaPagamento")) {
							//spedisce
							String nomeCoda = "JmsInformativaPagamentoPendenzeOutput";
							this.sendJmsMessage("JmsConnectionFactory",nomeCoda, esitoXml);
							logger.info(this.getClass().getSimpleName() 
									+ " Esito RISPEDITO verso "+nomeCoda+", idBustaEgov = " + e2emsgid + " - " + senderId + " - " + senderSys);												
						}					
						//update notifiche cart ed errori idp
						dataTx.updateReinoltroNotifiche(e2emsgid, senderId,
								senderSys, trtSenderId, trtSenderSys, erroreIdp);					
					} else {
						logger.info(this.getClass().getSimpleName() 
								+ " Reinoltro Notifica "+e2emsgid+" non eseguito, già in corso da "+errorExist.getTsAggiornamento());
					}
	
				} catch (Exception e) {
					e.printStackTrace();
					logger.info(this.getClass().getSimpleName() + " ERROR REINOLTRO NOTIFICHE SU FLUSSO = " + e2emsgid);
				}
	
			}			
			
			
			logger.info(result);
	
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(this.getClass().getSimpleName() + " ERROR REINOLTRO ESITI = " + e.getMessage());
		}
		finally {
		}
	}



	/**
	 * TODO FIXARE QUESTO METODO !!!
	 * @return
	 */
	public EntityManager getManager() {
		//quando siamo su JBOSS ci vuole questa riga
//		return emf.createEntityManager();
		//quando siamo su TEST JUNIT ci vuole questa riga
			return manager;
	}

	/*********************************
	 * NUOVA GESTIONE SMARTSIL
	 *********************************/
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Override
	public MonitoringData executeApplicationTransaction(String data) {
		if (REINOLTRO_NOTIFICHE_IN_SPEDIZIONE.equals(data)) {
			return  this.reinoltroNotificheInSpedizione();
		} else if (REINOLTRO_NOTIFICHE_NON_INVIATE.equals(data)) {
			return  this.reinoltroNotificheNonInviate();
		} else {
			logger.warn("parametro "+data+" non gestito. Chiamata ignorata");
			return null;
		}
	}

	/**
	 * Reinoltro notifiche non inviate
	 * @return
	 */
	public MonitoringData reinoltroNotificheInSpedizione() {


		//monitoring data
		MonitoringData monData = new MonitoringData();

		logger.info("Inizio Reinoltro notifiche SSIL ed internal");
		
		try {
		
		// Questo task serve per rimettere in stato "DA SPEDIRE" eventuali messaggi di notifica che possono essere rimasti in stato "IN_SPEDIZIONE" 
		// Parametri:
		// reinoltro.notifiche.in_spedizione.millis.retry = Delay rispetto all'ultimo aggiornamento per sbloccare notifiche in  stato  "IN_SPEDIZIONE".
		
		// Lettura parametri da properties
		Long millisRetryInSpedizione = IrisProperties.getLongProperty("reinoltro.notifiche.in_spedizione.millis.retry",60*60*1000L); // Default 1 ora

		// --------------------------------------
		// Recupero degli esiti "IN SPEDIZIONE"
		// --------------------------------------
		
		String queryRecuperoInSpedizione = 
				"UPDATE NotificheCart notificheCart " +
		        " SET notificheCart.stato = :stato_new " +
		        "   , notificheCart.opAggiornamento = :op_aggiornamento" +
		        "   , notificheCart.tsAggiornamento = :ts_aggiornamento  " +		        
		        " WHERE notificheCart.stato = :stato_old" +
		        " AND   notificheCart.tsAggiornamento <= :timestamp_max " ;
		
		Timestamp maxTimestamp = new Timestamp(System.currentTimeMillis()-millisRetryInSpedizione);
		logger.debug(this.getClass().getSimpleName() + " Recupero le notifiche rimaste in stato IN_SPEDIZIONE: query update notificheCart = " + queryRecuperoInSpedizione);
		logger.info(this.getClass().getSimpleName() + " Cerco notifiche in stato IN_SPEDIZIONE con timestamp aggiornamento <= " + maxTimestamp);
		
		Query queryUpdateNotificheCart = manager.createQuery (queryRecuperoInSpedizione);
		queryUpdateNotificheCart.setParameter("stato_new", StatoEnum.DA_SPEDIRE);			
		queryUpdateNotificheCart.setParameter("op_aggiornamento", "REINOLTRO_NOTIFICHE");
		queryUpdateNotificheCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdateNotificheCart.setParameter("stato_old", StatoEnum.IN_SPEDIZIONE);
		queryUpdateNotificheCart.setParameter("timestamp_max", maxTimestamp);

		int notificheUpdated = queryUpdateNotificheCart.executeUpdate();
							
		logger.info(this.getClass().getSimpleName() + " rimesse 'DA SPEDIRE' num. notifiche = " + notificheUpdated);
		
		// --------------------------------------
		// Retry delle notifiche "NON INVIATO" 
		// --------------------------------------
		
		} catch (Exception e) {
			logger.error("Errore in esecuzione Reinoltro Notifiche SSIL ed Internal ",e);
			ejbCtx.setRollbackOnly();
		} finally {
			logger.info(this.getClass().getSimpleName() + " fine Reinoltro Notifiche SSIL ed Internal");
		}
	
		return monData;
		
	}


	/**
	 * Reinoltro notifiche non inviate
	 * @return
	 */
	public MonitoringData reinoltroNotificheNonInviate() {
		
		//monitoring data
		MonitoringData monData = new MonitoringData();

		logger.info("Inizio Reinoltro notifiche SSIL ed internal (stato 'NON INVIATO')");
		
		try {
		
		// Questo task serve per rimettere in stato "DA SPEDIRE" eventuali messaggi di notifica che siano in stato "NON INVIATO" (ovvero abortiti per aver superato maxnum.tentativi di spedizione).
		// Parametri:
		// reinoltro.notifiche.non_inviate.millis.retry = Delay rispetto all'ultimo aggiornamento per riprovare la spedizione di un messaggio (NON INVIATO)
		// reinoltro.notifiche.non_inviate.millis.keep.retrying = Massimo intervallo di tempo (dalla creazione del messaggio) per il quale viene riprovata la spedizione di un messaggio (NON INVIATO)
		
		// Lettura parametri da properties
		Long millisRetryNonInviato = IrisProperties.getLongProperty("reinoltro.notifiche.non_inviate.millis.retry",60*60*1000L); // Default 1 ora
		Long millisKeepRetryNonInviato = IrisProperties.getLongProperty("reinoltro.notifiche.non_inviate.millis.keep.retrying",48*60*60*1000L); // Default 2 giorni (48 ore)

		
		// --------------------------------------
		// Recupero degli esiti "IN SPEDIZIONE"
		// --------------------------------------
		
		String queryRecuperoNonInviatiString = 
				"UPDATE NotificheCart notificheCart " +
		        " SET notificheCart.stato = :stato_new " +
		        "   , notificheCart.opAggiornamento = :op_aggiornamento" +
		        "   , notificheCart.tsAggiornamento = :ts_aggiornamento  " +	
		        "   , notificheCart.tentativi = 0 " +
		        " WHERE notificheCart.stato = :stato_old" +
		        " AND   notificheCart.tsAggiornamento <= :timestamp_max " +
		        " AND   notificheCart.tsInserimento >= :timestamp_min ";
		
		Timestamp maxTimestampNonInviato = new Timestamp(System.currentTimeMillis()-millisRetryNonInviato);
		Timestamp minTimestampNonInviato = new Timestamp(System.currentTimeMillis()-millisKeepRetryNonInviato);

		logger.debug(this.getClass().getSimpleName() + " Estraggo gli esiti rimasti in stato NON_INVIATO: query extract EsitiCart = " + queryRecuperoNonInviatiString);
		logger.info(this.getClass().getSimpleName() + " Cerco gli esiti in stato NON_INVIATO con timestamp aggiornamento <= " + maxTimestampNonInviato +" e ts_inserimento dopo il "+minTimestampNonInviato);
		
		Query queryUpdateNotificheCart = manager.createQuery (queryRecuperoNonInviatiString);
		queryUpdateNotificheCart.setParameter("stato_new", StatoEnum.DA_SPEDIRE);			
		queryUpdateNotificheCart.setParameter("op_aggiornamento", "REINOLTRO_NOTIFICHE NON INVIATO");
		queryUpdateNotificheCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		queryUpdateNotificheCart.setParameter("stato_old", StatoEnum.NON_INVIATO);
		queryUpdateNotificheCart.setParameter("timestamp_max", maxTimestampNonInviato);
		queryUpdateNotificheCart.setParameter("timestamp_min", minTimestampNonInviato);
		

		int notificheUpdated = queryUpdateNotificheCart.executeUpdate();
							
		logger.info(this.getClass().getSimpleName() + " rimesse 'DA SPEDIRE' num. notifiche = " + notificheUpdated);
		
		
		} catch (Exception e) {
			logger.error("Errore in esecuzione Reinoltro Notifiche SSIL ed Internal ",e);
			ejbCtx.setRollbackOnly();
		} finally {
			logger.info(this.getClass().getSimpleName() + " fine Reinoltro Notifiche SSIL ed Internal");
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
		List<ErroriIdp> lins = listaNotificheDaRispedire(getFrom(), getTo());

		logger.info(this.getClass().getSimpleName() + "executeHtml, found " + lins.size());
		table = "<br><br>";
		table += "<b>Lista notifiche RIFIUTATE e pronte per essere Rispedite</b>";
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
			logger.info(this.getClass().getSimpleName() + " ERROR EXECUTEHTML " +  e.getMessage());
		}
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


	public Timestamp getTo() {
		return to;
	}

	public void setTo(Timestamp to) {
		this.to = to;
	}

	public Timestamp getFrom() {
		return from;
	}

	public void setFrom(Timestamp from) {
		this.from = from;
	}


	/**
	 *
	 * @return
	 */
	private List<ErroriIdp> listaNotificheDaRispedire(Timestamp from , Timestamp to) {	

		//questa query legge i dati dopo che i messaggi sono stati depositati sulla
		//coda di errore
		Query queryErroriIdp = manager.createQuery(
				"SELECT erroriIdp FROM ErroriIdp erroriIdp " +
				" WHERE erroriIdp.pk.stato = :stato" +
	   			" AND erroriIdp.serviceName = :serviceName " +
				" AND erroriIdp.tsInserimento>= :from " +
				" AND erroriIdp.tsInserimento<= :to " 
				+ " AND (erroriIdp.opAggiornamento is null "
				+ " OR erroriIdp.opAggiornamento= :opAgg)"
				);			
		queryErroriIdp.setParameter("stato", StatoEnum.DA_REINVIARE);
		queryErroriIdp.setParameter("serviceName", StatoEnum.SERVICE_NAME_NOTIFICHE);
		queryErroriIdp.setParameter("from", from);
		queryErroriIdp.setParameter("to", to);
		queryErroriIdp.setParameter("opAgg", "ERROR_IDP_MANAGER");
		logger.info(" FROM = " + from);
		logger.info(" TO = " + to);
		
					
		List<ErroriIdp> lins = (List) queryErroriIdp.getResultList();
		return lins;
	}



}
