package it.tasgroup.ge.helpers;

import it.tasgroup.ge.CfgDestinatari;
import it.tasgroup.ge.CfgEventi;
import it.tasgroup.ge.enums.EnumTipiEventi;
import it.tasgroup.ge.pojo.CommunicationEvent;
import it.tasgroup.ge.pojo.CommunicationEventDetail;
import it.tasgroup.ge.pojo.MessaggioLogico;
import it.tasgroup.ge.util.EmailUtil;
import it.tasgroup.idp.pojo.UtenteIris;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.webservices.helper.gestorecanali.GestoreCanaliWebServiceHelper;
import it.tasgroup.idp.webservices.helper.gestorecanali.GestoreCanaliWebServiceHelperImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public abstract class GestoreEventiMailHelper implements GestoreEventiHelper {

	public static final String TIPO_INVIO_TO = "TO";
	public static final String TIPO_INVIO_CC = "CC";
	public static final String TIPO_DESTINATARIO_CITTADINO = "1";
	public static final String TIPO_DESTINATARIO_OPERATORE_ENTE = "2";
	public static final String TIPO_DESTINATARIO_OPERATORE_BACKOFFICE = "3";
	public static final String TIPO_DESTINATARIO_AMMINISTRATORE_ENTE = "4";
	public static final String TIPO_DESTINATARIO_AMMINISTRATORE_BACKOFFICE = "5";
	public static final String AMMINISTRATORE_BACKOFFICE = "ADMINCORPORATE";
	public static final String GESTORE_EVENTI_URL_HOME_PAGE = "GESTORE_EVENTI_URL_HOME_PAGE";
	public static final String UTENTE_TIPO_AMMINISTRATORE = "AC";
	public static final String UTENTE_TIPO_OPERATORE = "OP";
	
	public abstract MessaggioLogico getMessaggioLogico(String datiEvento,CfgEventi confEvento) throws Exception;


	protected final Log logger = LogFactory.getLog(this.getClass());
	protected EntityManager manager;
	
	/**
	 * Metodo che crea il messaggio logico
	 * @param confEvento contiene la configurazione dell'evento letta da db
	 * @param eventData contiene i dati necessari per completare il template del messaggio
	 * @param destinatariTo contiene la lista dei destinatari a cui è destinato il messaggio
	 * @param destinatariCc contiene la lista dei destinatari in cc
	 * @return L'oggetto il messaggio logico valorizzato
	 * @throws Exception Eccezione sollevata nel caso in cui non si riesca a valorizzare correttamente il messaggio logico
	 */
	protected MessaggioLogico creaMessaggioLogico(CfgEventi confEvento,CommunicationEventDetail eventData,ArrayList<String> destinatariTo, ArrayList<String> destinatariCc) throws Exception{
		EmailUtil em = new EmailUtil();
		MessaggioLogico ml = null;
		ml = em.createEmail(eventData, confEvento.getTemplate());
		ml.setDataInvio(new Date(System.currentTimeMillis()));
		String mittenteEmail = IrisProperties.getProperty("GESTORE_EVENTI_MITTENTE_MAIL");
		if (mittenteEmail !=null) {
			ml.setFrom(mittenteEmail);
		} else {
			ml.setFrom("iris@regione.it");
		}
		ml.setTo(destinatariTo);
		ml.setCc(destinatariCc);
		return ml;
	}
	
	/**
	 * Metodo che crea Id_utente_Iris come concatenazione delle stringhe INTESTATARIO-OPERATORE-TIPO_OPERATORE
	 * @param idUtente identificativo dell'utente
	 * @return idUtenteIris valorizzato
	 */
	private String creaIdUtenteIrisCittadino(String idUtente) throws Exception {
		String idUtenteIris = null;
		if (idUtente != null) {
			logger.info(this.getClass().getSimpleName() + " recupero ID_UTENTE_IRIS dell'utente " + idUtente);
			
			Query utenteQuery =  manager.createNativeQuery(
					"select IO.INTESTATARIO, IO.OPERATORE, IO.TP_OPERATORE " +
					"  from OPERATORI OP, INTEST_OPER IO, INTESTATARI INTE " +
					" where OP.INTESTATARIO = IO.INTESTATARIO " +
					"   and OP.OPERATORE = IO.OPERATORE " +
					"   and OP.USERNAME = :utente " + 
					"   and INTE.INTESTATARIO = IO.INTESTATARIO " +
					"   and INTE.FLAG_COMUNICAZIONI = 'S' ");
			
			utenteQuery.setParameter("utente", idUtente.toUpperCase());
			Object intestOper = utenteQuery.getSingleResult();
			if (intestOper != null) {
				Object[] result = (Object[]) intestOper;
				String intestatario = (String) result[0];
				String operatore = (String) result[1];
				String tipoOperatore = (String) result[2];
				idUtenteIris = intestatario + "-" + operatore + "-" + tipoOperatore;
				logger.debug(this.getClass().getSimpleName() + " idUtenteIris: " + idUtenteIris);
			}
		} else {
			logger.error(" Non è stato possibile recuperare ID_UTENTE_IRIS !!!");
			throw new Exception("Non è stato possibile recuperare ID_UTENTE_IRIS !!!");

		}
		return idUtenteIris;
	}
	
	/**
	 * Metodo che crea Id_utente_Iris_Amministratore_Ente come concatenazione delle stringhe INTESTATARIO-OPERATORE-TIPO_OPERATORE
	 * @param ente identificativo dell'ente (ID) 
	 * @param tipoOperatore identificativo tipo operatore dell'ente (operatore, amministratore (AC-OP)
	 * @return idUtenteIris valorizzato
	 */
	private ArrayList<String> creaIdUtenteIrisEnte(String ente, String tipoOperatore) throws Exception {
		String idUtenteIris = null;
		ArrayList<String> destinatari = new ArrayList<String>();
		
		if (ente != null) {

			logger.info(this.getClass().getSimpleName() + " recupero ID_UTENTE_IRIS AMMINISTRATORE ENTE ");
			
	
			Query utenteQuery = manager.createNativeQuery(
					"select IO.INTESTATARIO, IO.OPERATORE " +
					"  from OPERATORI OP, INTEST_OPER IO, INTESTATARI INTE " +
					"   where OP.INTESTATARIO=IO.INTESTATARIO " +
					"   and OP.INTESTATARIO = :utente " +
					"   and IO.TP_OPERATORE = :tpOperatore " +
					"   and INTE.INTESTATARIO = IO.INTESTATARIO " +
					"   and INTE.FLAG_COMUNICAZIONI = 'S' ");
				
			utenteQuery.setParameter("utente", ente);
			utenteQuery.setParameter("tpOperatore", tipoOperatore);
	
			List<?> intestOperList = utenteQuery.getResultList();
			Iterator<?> intestOperListIterator = intestOperList.iterator();
			while (intestOperListIterator.hasNext()) {
				Object[] result = (Object[]) intestOperListIterator.next();
				String intestatario = (String) result[0];
				String operatore = (String) result[1];
				idUtenteIris = intestatario + "-" + operatore + "-" + tipoOperatore;
				logger.debug(this.getClass().getSimpleName() + " idUtenteIris: " + idUtenteIris);
				destinatari.add(idUtenteIris);
			}
			
			//SE l'intestario relativo all'ente ha il FLAG_COMUNICAZIONI = 'S' allora
			//devo aggiungere anche un destinatario così formato:
			//INTESTATARIO-INTESTATARIO-AC
			//che rappresenta la sottoscrizione dell'ente stesso
			Query enteQuery =  manager.createNativeQuery(
					"select INT.INTESTATARIO " +
					"  from INTESTATARI INTE, ENTI EN " +
					" where EN.ID_ENTE = :utente " + 
					"   and INTE.INTESTATARIO = EN.INTESTATARIO " + 			
					"   and INTE.FLAG_COMUNICAZIONI = 'S' ");
			
			enteQuery.setParameter("utente", ente);
			Object intEnte = enteQuery.getSingleResult();
			
			if (intEnte != null) {
				Object[] result = (Object[]) intEnte;
				String intestatario = (String) result[0];				
				idUtenteIris = intestatario + "-" + intestatario + "-" + UTENTE_TIPO_AMMINISTRATORE;
				logger.debug(this.getClass().getSimpleName() + " idUtenteIris: " + idUtenteIris);
				destinatari.add(idUtenteIris);
		}
		} else {
			logger.error(" Ente non valorizzato! Non è stato possibile recuperare ID_UTENTE_IRIS !!!");
			throw new Exception("Ente non valorizzato! Non è stato possibile recuperare ID_UTENTE_IRIS !!!");

		}

		return destinatari;
	}

	
	/**
	 * Metodo che crea Id_utente_Iris_Amministratore_BackOffice come concatenazione delle stringhe INTESTATARIO-OPERATORE-TIPO_OPERATORE
	 * @param idUtente identificativo dell'utente di backOffice --- ADMINCORPORATE
	 * @return idUtenteIris valorizzato
	 */
	private ArrayList<String> creaIdUtenteIrisPiattaformaBackOffice(String tipoOperatore) throws Exception {
		String idUtenteIris = null;

		logger.info(this.getClass().getSimpleName() + " recupero ID_UTENTE_IRIS AMMINISTRATORE BACKOFFICE ");
		ArrayList<String> destinatari = new ArrayList<String>();

		Query utenteQuery = manager.createNativeQuery(
				"select IO.INTESTATARIO, IO.OPERATORE " +
				"  from OPERATORI OP, INTEST_OPER IO, INTESTATARI INTE " +
				" where OP.INTESTATARIO=IO.INTESTATARIO " +
				"   and OP.INTESTATARIO = :utente " +
				"   and IO.TP_OPERATORE = :tpOperatore " +
				"   and INTE.INTESTATARIO = IO.INTESTATARIO " +
				"   and INTE.FLAG_COMUNICAZIONI = 'S' ");
		
		utenteQuery.setParameter("utente", AMMINISTRATORE_BACKOFFICE);
		utenteQuery.setParameter("tpOperatore", tipoOperatore);

		List<?> intestOperList = utenteQuery.getResultList();
		Iterator<?> intestOperListIterator = intestOperList.iterator();
		while (intestOperListIterator.hasNext()) {
			Object[] result = (Object[]) intestOperListIterator.next();
			String intestatario = (String) result[0];
			String operatore = (String) result[1];
			idUtenteIris = intestatario + "-" + operatore + "-" + tipoOperatore;
			logger.debug(this.getClass().getSimpleName() + " idUtenteIris: " + idUtenteIris);
			destinatari.add(idUtenteIris);
		}

		return destinatari;
	}

	/**
	 * Metodo che crea la lista dei destinatari in TO o CC a seconda del valore tipoDestinatario
	 * @param confEvento contiene la configurazione dell'evento letta da db
	 * @param utente contiene identificativo utente necessario per creare idUtenteIris
	 * @return L'oggetto è  la lista dei destinatari valorizzati
	 * @throws Exception Eccezione sollevata nel caso in cui non si riesca a valorizzare correttamente la lista dei destinatari
	 */
	protected ArrayList<String> creaListaDestinatari(CfgEventi confEvento, String utente, String tipoInvio)	throws Exception {
		
		logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari Start : " + utente);
		
		ArrayList<String> destinatari = new ArrayList<String>();
		if (confEvento != null) {

			if (confEvento.getCfgDestinatari() != null) {
			
				Iterator<CfgDestinatari> cfgDestIter = confEvento.getCfgDestinatari().iterator();
				while (cfgDestIter.hasNext()) {
					CfgDestinatari currentCfg = cfgDestIter.next();
					
					if (currentCfg.getTipoInvio().equals(tipoInvio)) {
						if (currentCfg.getTipoDestinatario().equals(TIPO_DESTINATARIO_CITTADINO)) {
							logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari per TIPO_DESTINATARIO_CITTADINO");
							if(utente != null) {
								String tmpDestinatari = creaIdUtenteIrisCittadino(utente);
								destinatari.add(tmpDestinatari);
								logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari added destinarari: "+ tmpDestinatari);
							}

						} else if (currentCfg.getTipoDestinatario().equals(TIPO_DESTINATARIO_OPERATORE_ENTE)) {
							
							logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari per TIPO_DESTINATARIO_OPERATORE_ENTE");

						} else if (currentCfg.getTipoDestinatario().equals(TIPO_DESTINATARIO_AMMINISTRATORE_ENTE)) {
							
							logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari per TIPO_DESTINATARIO_AMMINISTRATORE_ENTE");
							 ArrayList<String> destinatariTmp = creaIdUtenteIrisEnte(utente, UTENTE_TIPO_AMMINISTRATORE);
							 destinatari.addAll(destinatariTmp);
							 logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari added destinarari: "+ destinatariTmp);

						} else if (currentCfg.getTipoDestinatario().equals(TIPO_DESTINATARIO_OPERATORE_BACKOFFICE)) {
							
							logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari per TIPO_DESTINATARIO_OPERATORE_BACKOFFICE");
							 ArrayList<String> destinatariTmp = creaIdUtenteIrisPiattaformaBackOffice(UTENTE_TIPO_OPERATORE);
							 destinatari.addAll(destinatariTmp);
							 logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari added destinarari: "+ destinatariTmp);

						} else if (currentCfg.getTipoDestinatario().equals(TIPO_DESTINATARIO_AMMINISTRATORE_BACKOFFICE)) {
							
							logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari per TIPO_DESTINATARIO_AMMINISTRATORE_BACKOFFICE");
							 ArrayList<String> destinatariTmp = creaIdUtenteIrisPiattaformaBackOffice(UTENTE_TIPO_AMMINISTRATORE);
							 destinatari.addAll(destinatariTmp);
							 logger.info(this.getClass().getSimpleName() + " :creaListaDestinatari added destinarari: "+ destinatariTmp);
						}
					}
				}
			}
		}
		return destinatari;
	}

	/**
	 * Metodo che recupera l'indirizzo email dell'utente
	 * @param idUtente identificativo dell'utente
	 * @return email utente valorizzato
	 */
	public String getEmailUtente(String idUtente) throws Exception{
		String email=null;
//		if (idUtente!=null){
//			// utente iris è formato da intestatario-operatore-tipooperatore
//			// a me serve solo operatore per fare la query
//			String [] splits = idUtente.split("-");
//			if (splits.length==3)
//				idUtente = splits[1];
//			logger.debug(this.getClass().getSimpleName() + " recupero email dell'utente " + idUtente);
//			Query utenteQuery =  manager.createNativeQuery(
//					"select EMAIL " +
//					"from OPERATORI "+
//					"WHERE OPERATORE=:utente");
//			utenteQuery.setParameter("utente", idUtente.toUpperCase());
//			try{
//				Object oper = utenteQuery.getSingleResult();
//				if (oper!=null ){
//					Object[] result = (Object[]) oper;
//					email = (String) result[1];
//					logger.debug(this.getClass().getSimpleName() + " email utente recuperata: " + email );
//				}
//			}catch (NoResultException e) {
//				logger.error(" Non è stato possibile recuperare EMAIL DELL'UTENTE:" + idUtente);
//				
//			}
//		}else{
//			logger.error(" Non è stato possibile recuperare EMAIL DELL'UTENTE:" + idUtente);
//			throw new Exception("Non è stato possibile recuperare ID_UTENTE_IRIS"+ idUtente);
//			
//		}
		return email;
	}
	
	/**
	 * Metodo che crea Id_utente_Iris_Amministratore_BackOffice come concatenazione delle stringhe INTESTATARIO-OPERATORE-TIPO_OPERATORE
	 * @param idUtente identificativo dell'utente di backOffice --- ADMINCORPORATE
	 * @return idUtenteIris valorizzato
	 */
//	private String creaIdUtenteIrisAmministratoreBackOffice() throws Exception {
//		String idUtenteIris = null;
//
//		logger.debug(this.getClass().getSimpleName() + " recupero ID_UTENTE_IRIS AMMINISTRATORE BACKOFFICE ");
//		Query utenteQuery = manager.createNativeQuery("select IO.INTESTATARIO, IO.OPERATORE, IO.TP_OPERATORE "
//				+ "from OPERATORI OP, INTEST_OPER IO "
//				+ "WHERE OP.INTESTATARIO=IO.INTESTATARIO AND OP.OPERATORE=IO.OPERATORE AND OP.INTESTATARIO=:utente");
//		utenteQuery.setParameter("utente", AMMINISTRATORE_BACKOFFICE);
//		Object intestOper = utenteQuery.getSingleResult();
//		if (intestOper != null) {
//			Object[] result = (Object[]) intestOper;
//			String intestatario = (String) result[1];
//			String operatore = (String) result[2];
//			String tipoOperatore = (String) result[3];
//
//			idUtenteIris = intestatario + "-" + operatore + "-" + tipoOperatore;
//
//			logger.debug(this.getClass().getSimpleName() + " idUtenteIris: " + idUtenteIris);
//		}
//
//		return idUtenteIris;
//	}

	@Override
	public String fireCommunicationEvt(CommunicationEvent e, CfgEventi cfgEvento) throws Exception {

		// creazione messaggio logico
		MessaggioLogico cDetail = getMessaggioLogico(e.getObjectId(), cfgEvento);

		if (cDetail != null) {

			logger.info(this.getClass().getSimpleName() + " getMessaggioLogico ottenuto ");

			// recupero la mail utente per la sottoscrizione del canale email
			if (cDetail.getTo() != null) {

				ArrayList<UtenteIris> irisUsers = new ArrayList<UtenteIris>();

				UtenteIris newUser = null;

				for (String idUtente : cDetail.getTo()) {
					newUser = new UtenteIris();
					newUser.setIdUtente(idUtente);
					newUser.setScopoMessaggio(GestoreEventiMailHelper.TIPO_INVIO_TO);
					if (e.getCodiceEvento().equals(EnumTipiEventi.INVIOQUIETANZA.getEventoCode()) || e.getCodiceEvento().equals(EnumTipiEventi.AVVISO_RICEZIONE_RT_NDP.getEventoCode())) {
						newUser.setSubscribe(true);
						newUser.setEmail(getEmailUtente(e.getObjectId()));
					}
					irisUsers.add(newUser);
				}

				if (cDetail.getCc() != null) {
					for (String idUtente : cDetail.getCc()) {
						newUser = new UtenteIris();
						newUser.setIdUtente(idUtente);
						newUser.setScopoMessaggio(GestoreEventiMailHelper.TIPO_INVIO_CC);
						if (e.getCodiceEvento().equals(EnumTipiEventi.INVIOQUIETANZA.getEventoCode()) || e.getCodiceEvento().equals(EnumTipiEventi.AVVISO_RICEZIONE_RT_NDP.getEventoCode())) {
							newUser.setSubscribe(true);
							newUser.setEmail(getEmailUtente(e.getObjectId()));
						}
						irisUsers.add(newUser);
					}
				}

				// chiamata al Gestore Canali
				try {

					GestoreCanaliWebServiceHelper webServicehelper = new GestoreCanaliWebServiceHelperImpl();
					webServicehelper.sendMessage(irisUsers, cDetail.getSubject(), cDetail.getContent(), e.getCodiceEvento());

				} catch (Exception e1) {
					logger.error(" Errore durante la spedizione di un messaggio al gestore canali", e1);
					throw e1;
				}

			} else {
				logger.info(" Invio email fallito!!! Mancano i destinatari delle email!!");
				throw new Exception("Invio email fallito!!! Mancano i destinatari delle email!!");
			}

		} else {
			logger.info(this.getClass().getSimpleName() + " getMessaggioLogico null ");
		}
		
		return "OK";
	}
	
}
