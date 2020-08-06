package it.tasgroup.idp.bean;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.ErroriIdpPK;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.esiti.EsitoBuilder;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;

@Stateless
public class DataManager extends DataStorageAbstract   
 implements DataStorageInterface {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	public EntityManager manager;	
	@Resource 
	private EJBContext ejbCtx;	
	
	private final Log logger = LogFactory.getLog(this.getClass());

	public DataManager() {
	}


	/**
	 * Ad uso DataStorageManager
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public void persistPendenzeCart(PendenzeCartMessage cart) {
		
		try {			
			//persist blob
			manager.persist(cart);
			logger.info("PERSISTING PendenzeCart, E2EID " + cart.getPk().getE2emsgid() + " , Ente/SIL " + cart.getPk().getSenderid() + "/"  + cart.getPk().getSendersys() );
			
		} catch (RuntimeException e) {
			//e.printStackTrace();
			logger.info("Rollback inside persistPendenzeCart , E2EID " + cart.getPk().getE2emsgid() + " , Ente/SIL " + cart.getPk().getSenderid() + "/"  + cart.getPk().getSendersys()  );
			logger.info("Error inside persistPendenzeCart , message " + e.getMessage() );
			ejbCtx.setRollbackOnly();
			throw e;
			//si rilancia perch� siamo in caso di chiave duplicata
		}		
	}
	
	@Override
	public void persistSemplificationPendenzeCart(PendenzeCartMessage cart) {
		persistPendenzeCart(cart);	
	}
	
	/**
	 * Ad uso DataStorageManager
	 */	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)	
	public void persistEsitiCart(EsitiCart cart) {
		
		try {						
			//persist blob
			manager.persist(cart);
			logger.info("PERSISTING EsitiCart");
//			throw new PersistenceException();
			
		} catch (RuntimeException e) {
			logger.info("Rollback inside persistEsitoCart");
			ejbCtx.setRollbackOnly();
			//non si rilancia perch� � sufficiente il setRollBack per 
			//annullare tutta la transazione
			//il messaggio rimane in stato 'da elaborare'
		}		
	}		
	
	/**
	 * Ad uso DataStorageManager
	 */
	@Override	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateStatoPendenza(String E2EMsgId,
			String idMittente, String silMittente, String stato ) {
		try {
			Query queryUpdatePend = manager
					.createQuery("Update PendenzeCart pend "
							+ " set pend.stato = :stato  "
							+ " WHERE pend.pk.e2emsgid = :e2emsgid"
							+ " AND pend.pk.senderid= :senderId "
							+ " AND pend.pk.sendersys = :senderSys ");
			queryUpdatePend.setParameter("stato", stato);
			queryUpdatePend.setParameter("e2emsgid", E2EMsgId);
			queryUpdatePend.setParameter("senderId", idMittente);
			queryUpdatePend.setParameter("senderSys", silMittente);
			int esitiPend = queryUpdatePend.executeUpdate();
		} catch (Exception e) {
			logger.info("Rollback inside updateStatoPendenza");
			ejbCtx.setRollbackOnly();
		}
	}		
	
	/**
	 * Ad uso DSE
	 */	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public void callMe(DataStoreEngineService dataStoreEngineService,DataInput input) {
		
		try {
			//se la validazione va a posto allora inserisco
			dataStoreEngineService.store(input);
		} catch (Exception e) {
			e.printStackTrace();
			//in caso di eccezione gestire STATO per ripartenza
			logger.error(" ERRORE DATASTORAGE >>>>>",e);
			//non si rilancia perch� � considerato uno componente a se stante
		}	
	}
	
	/**
	 * Ad uso DSE (OtfSimulator, Hidden / visible)
	 */	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public void callMe(DataStoreEngineService dataStoreEngineService,
						DataInput input,
						boolean hidden) {
		
		try {
			//se la validazione va a posto allora inserisco
			dataStoreEngineService.store(input, hidden);
		} catch (Exception e) {
			e.printStackTrace();
			//in caso di eccezione gestire STATO per ripartenza
			logger.error(" ERRORE DATASTORAGE >>>>>",e);
			//non si rilancia perch� � considerato uno componente a se stante
		}	
	}	
	
	/**
	 * Ad uso DSE
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)	
	public void callMeToRestart(DataStoreEngineService dataStoreEngineService,DataInput input, String[] listPendenzaEnte) {
 		
		try {
			dataStoreEngineService.store(input,listPendenzaEnte);
		} catch (Exception e) {
			e.printStackTrace();
			//in caso di eccezione gestire STATO per ripartenza
			logger.error(" ERRORE DATASTORAGE (RIPARTENZA) >>>>>",e);
			//non si rilancia perch� � considerato uno componente a se stante
		}	
	}	

	/**
	 * Ad uso NotificaPagamento
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void persistNotificaCart(NotificheCart notificheCart) {
		try {			
			
			manager.persist(notificheCart);
			logger.info("PERSISTING notificheCart");
			
		} catch (RuntimeException e) {
			logger.error("Rollback inside persistNotificheCart"+e.getMessage());
			ejbCtx.setRollbackOnly();
			//non si rilancia perch� � sufficiente il setRollBack per 
			//annullare tutta la transazione (da verificare)
			//dovrebbe essere il caso speculare ad esiti_cart
		}	
	}
    /**
     * 
     * @param notificheCart
     * @param listaPagamentiModel
     * @param model
     * 
     * Inserisco le 2 operazioni di insert e update in una unica transazione REQUIRES_NEW 
     * per potere gestire in transazione e atomicamente la scrittura su notifiche_cart e 
     * aggiornamento su notifiche_pagamenti 
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistNotificheCartAndUpdateStatoNotPagamenti(NotificheCart notificheCart,List<PagamentoModel> listaPagamentiModel,
			NotifichePagamentoModel model) {
		
		logger.info(getClass().getName() + " persistNotificheCartAndUpdateStatoNotPagamenti() BEGIN ");
		this.persistNotificaCart(notificheCart);
		this.updateStatoNotifichePagamenti(listaPagamentiModel,model);
		if (ejbCtx.getRollbackOnly()) {
			logger.warn(getClass().getName() + " ROLLBACK !!!!! ");
			throw new RuntimeException();
		}		 
		logger.info(getClass().getName() + " persistNotificheCartAndUpdateStatoNotPagamenti() END ");
	}
	/**
     * 
     * @param notificheCart
     * @param listaPagamentiModel
     * @param model
     * 
     * Inserisco le 2 operazioni di insert e update in una unica transazione REQUIRES_NEW 
     * per potere gestire in transazione e atomicamente la scrittura su notifiche_cart e 
     * aggiornamento su notifiche_pagamenti 
     */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void persistNotificheCartAndUpdateStatoNotPagamenti(NotificheCart notificheCart,NotifichePagamenti notPagamento) {
		logger.info(getClass().getName() + " persistNotificheCartAndUpdateStatoNotPagamenti() BEGIN ");
		this.persistNotificaCart(notificheCart);
		this.updateStatoNotifichePagamenti(notPagamento);
		if (ejbCtx.getRollbackOnly()) {
			logger.warn(getClass().getName() + " ROLLBACK !!!!! ");
			throw new RuntimeException();
		}		 
		logger.info(getClass().getName() + " persistNotificheCartAndUpdateStatoNotPagamenti() END ");
	}
	
	
	/**
	 * Ad uso NotificaPagamento
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateStatoNotifichePagamenti(
			List<PagamentoModel> listaPagamentiModel,
			NotifichePagamentoModel model) {

		try {
			Iterator<PagamentoModel> enumPagModel = listaPagamentiModel.iterator();
			int numPagamInLista = 0;
			int numPagamentiTrovati = 0;
			while (enumPagModel.hasNext()) {
				PagamentoModel object = (PagamentoModel) enumPagModel.next();
				NotifichePagamenti notPag = new NotifichePagamenti();

				Query queryUpdatePend = manager.createQuery ("Update NotifichePagamenti notPag " +
						" set notPag.e2emsgid = :e2emsgid," +
						" notPag.receiverid= :receiverId, " +
						" notPag.receiversys = :receiverSys, "	+
						" notPag.version  = notPag.version + 1, " +
						" notPag.statoNotifica = :statoNotifica  " +
						" WHERE notPag.idNotifica = :idNotifica " + // primary key della tabella
						" and  notPag.version = :version"); 
				queryUpdatePend.setParameter("statoNotifica", StatoEnum.CREATO);
				queryUpdatePend.setParameter("idNotifica", object.getIdNotifica());
				queryUpdatePend.setParameter("e2emsgid", model.getE2emsgid());
				queryUpdatePend.setParameter("receiverId", model.getSenderId());
				queryUpdatePend.setParameter("receiverSys", model.getSenderSys());
				queryUpdatePend.setParameter("version"    , object.getVersionNotifichePagamento());
				
				numPagamInLista++;
				int esitiPend = queryUpdatePend.executeUpdate();
				numPagamentiTrovati=numPagamentiTrovati+esitiPend;
				if (esitiPend==0) {
					logger.warn(getClass().getSimpleName() + "::updateStatoNotifichePagamenti  attenzione!!! riga non trovata su DB!!!  ");
				}
				logger.info(" ho aggiornato lo stato delle notifiche pagamenti = " + model.getE2emsgid() + "-" + model.getSenderId() + "-" +  model.getSenderSys());
			}
			if (numPagamentiTrovati<numPagamInLista) {
				logger.warn(getClass().getSimpleName() + "::updateStatoNotifichePagamenti  numPagamentiTrovati = "+ numPagamentiTrovati + " < numPagamInLista = "+ numPagamInLista);
				// ATTENZIONE: possibile corsa critica o overlapping di 2 elaborazioni!!! dovrei effettuare un rollback!!!!
				ejbCtx.setRollbackOnly();
			}
		} catch (Exception e) {
			logger.error("Rollback inside updateStatoNotifichePagamenti"+e.getMessage());
			ejbCtx.setRollbackOnly();
			//non si rilancia perch� � sufficiente il setRollBack per 
			//annullare tutta la transazione (da verificare)
			//dovrebbe essere il caso speculare ad esiti_cart
		}		
	}	

	
	/**
	 * Ad uso NotificaPagamento
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateStatoNotifichePagamenti(NotifichePagamenti notPagamento) {

		try {
			
				// effettuo il detach dell'oggetto hibernate
			    //manager.detach(notPagamento);
			    
				Query queryUpdatePend = manager.createQuery ("Update NotifichePagamenti notPag " +
						" set notPag.e2emsgid = :e2emsgid," +
						" notPag.receiverid= :receiverId, " +
						" notPag.receiversys = :receiverSys, "	+
						" notPag.statoNotifica = :statoNotifica,  " +
						" notPag.version  = notPag.version + 1 " +
						" WHERE notPag.idNotifica = :idNotifica " +
						" and  notPag.version = :version");
				queryUpdatePend.setParameter("statoNotifica", StatoEnum.CREATO);
				queryUpdatePend.setParameter("idNotifica", notPagamento.getIdNotifica());
				queryUpdatePend.setParameter("e2emsgid", notPagamento.getE2emsgid());
				queryUpdatePend.setParameter("receiverId", notPagamento.getReceiverid());
				queryUpdatePend.setParameter("receiverSys", notPagamento.getReceiversys());
				queryUpdatePend.setParameter("version"            , notPagamento.getVersion());
				
				
				int esitiPend = queryUpdatePend.executeUpdate();
				if (esitiPend == 0) {
					logger.error("updateStatoNotifichePagamenti: numero di record aggiornati 0!!!! il record � stato aggiornato da un thread concorrente!! rollback!!!");
					ejbCtx.setRollbackOnly();
				} else {
				   notPagamento.setStatoNotifica(StatoEnum.CREATO);		// PATCH: l'update sopra non viene eseguito, ma sovrascritto da JPA...	
				   notPagamento.setVersion(notPagamento.getVersion()+1);
				   logger.info(" ho aggiornato lo stato delle notifiche pagamenti = " + notPagamento.getE2emsgid() + "-" + notPagamento.getReceiverid() + "-" +  notPagamento.getReceiversys());
				}
		} catch (Exception e) {
			logger.error("Rollback inside updateStatoNotifichePagamenti"+e.getMessage());
			ejbCtx.setRollbackOnly();
			//non si rilancia perch� � sufficiente il setRollBack per 
			//annullare tutta la transazione (da verificare)
			//dovrebbe essere il caso speculare ad esiti_cart
		}		
	}	

	
	/**
	 * Ad uso DataStorageManager
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public void updateEsitoAndPendenze(String e2emsgid, String senderId, String senderSys, String esitoDaSpedire) {
		
//		CreazioneEsitoProxy.executeApplicationTransaction(esitoModel);
		//eseguo EJB di Creazione Esito
		//connessione db
		//EntityManager em = null;
		//EntityTransaction tx = null;
		EsitoBuilder esitoBuilder = new EsitoBuilder();

		try {
			//em = getManager();
			//tx = em.getTransaction();
			//inizio transazione... in realt� non deve essere transazione DB ma distribuita su pi� risorse (jms + Db)
			//tx.begin();
			
			//devo spedire con l IDEGOV che corrisponde al contenuto della lista!
			PendenzeCart pendC = new PendenzeCart();
			PendenzeCartPK pendPK = new PendenzeCartPK();
			pendPK.setE2emsgid(e2emsgid);
			pendPK.setSenderid(senderId);
			pendPK.setSendersys(senderSys);
			pendC.setPk(pendPK);			

			//aggiorno lo stato della pendenza cart
			logger.info(this.getClass().getSimpleName() 
					+ " cambio stato della pendenza ricevuta, "
					+ "" + " idBusta = " + e2emsgid + " - " + senderId + " - " + senderSys );

			//lo stato che si imposta � RISPOSTA_INVIATA, considerazione:
			//l'esito XML non � stato ancora inviato alla rete (viene inviato da CartSenderBean)
			//l'esito XML � stato gi� prodotto e verr� inviato dal bean successivo CartSenderBean, in transazione.
			//LB 27/10: Lo stato viene impostato a IN SPEDIZIONE
			//LB 27/10: Lo stato della pendenze diventa RISPOSTA INVIATA con il bean successivo
			Query queryUpdatePend = manager.createQuery ("Update PendenzeCart pend " +
					" set pend.stato = :stato  " +
					" , pend.opAggiornamento = :opAggiornamento  " +
					" , pend.tsAggiornamento = :tsAggiornamento  " +
					" WHERE pend.pk.e2emsgid = :e2emsgid" +
					" AND pend.pk.senderid= :senderId " +
					" AND pend.pk.sendersys = :senderSys ");
//				queryUpdatePend.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
			queryUpdatePend.setParameter("stato", StatoEnum.RISPOSTA_INVIATA_WS);
			queryUpdatePend.setParameter("opAggiornamento", "DSE SYNC");
			queryUpdatePend.setParameter("tsAggiornamento", new Timestamp(System.currentTimeMillis()));
			queryUpdatePend.setParameter("e2emsgid", e2emsgid);
			queryUpdatePend.setParameter("senderId", senderId);
			queryUpdatePend.setParameter("senderSys", senderSys);

			int esitiPend = queryUpdatePend.executeUpdate();    
			logger.info(this.getClass().getSimpleName() 
					+ " ho cambiato lo stato della pendenza "
					+ ", num. pendenze = " + esitiPend );

			Query queryUpdateEsiti = manager.createQuery ("Update EsitiPendenza esiti " +
					" set esiti.stato = :stato  " +
					" WHERE esiti.pendenzeCart = :pendenzeCart");
//				queryUpdateEsiti.setParameter("stato", StatoEnum.IN_SPEDIZIONE);
			queryUpdateEsiti.setParameter("stato", StatoEnum.RISPOSTA_INVIATA_WS);				
			queryUpdateEsiti.setParameter("pendenzeCart", pendC);
			int esitiUpd = queryUpdateEsiti.executeUpdate();
			logger.info(this.getClass().getSimpleName() 
					+ " ho cambiato lo stato di tutti gli esiti associati "
					+ " alla pendenza , num. esiti = " + esitiUpd );

			//tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			ejbCtx.setRollbackOnly();
			logger.error(this.getClass().getSimpleName() + " ERROR CREAZIONE ESITO = " + e.getMessage());
		}
	}
	
	public void flush() {
		
		manager.flush();
		
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateReinoltroEsiti(String e2emsgid, String senderId,
			String senderSys, ErroriIdp erroreIdp, String trtSenderId, String trtSenderSys) {
		
		PendenzeCart pendC = new PendenzeCart();
		PendenzeCartPK pendPK = new PendenzeCartPK();
		pendPK.setE2emsgid(e2emsgid);
		pendPK.setSenderid(senderId);
		pendPK.setSendersys(senderSys);
		pendC.setPk(pendPK);
		
		logger.info(" update esitiCart (e2emsgid = " + e2emsgid + ", stato = " + StatoEnum.REINVIATO);

		String query = "Update EsitiCart esitiCart " +
		" set esitiCart.stato = :stato  " +
		" , esitiCart.opAggiornamento = :op_aggiornamento  " +
		" , esitiCart.tsAggiornamento = :ts_aggiornamento  " +
		" WHERE esitiCart.pk.e2emsgid = :e2emsgid" +
		" AND esitiCart.pk.senderid= :senderId " +
		" AND esitiCart.pk.sendersys = :senderSys ";
		logger.info(this.getClass().getSimpleName() + " query update esitiCart = " + query);
		//aggiorno esitoCart
		Query queryUpdateEsitiCart = manager.createQuery (query);
		queryUpdateEsitiCart.setParameter("stato", StatoEnum.REINVIATO);
		queryUpdateEsitiCart.setParameter("e2emsgid", e2emsgid);
		queryUpdateEsitiCart.setParameter("senderId", senderId);
		queryUpdateEsitiCart.setParameter("senderSys", senderSys);
		queryUpdateEsitiCart.setParameter("op_aggiornamento", "REINOLTRO");
		queryUpdateEsitiCart.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		int esitiUpd = queryUpdateEsitiCart.executeUpdate();

		logger.info(" remove erroreIdp ");
		//causa errore nella definizione della PK (e2emsgid+stato)
		//eseguo due operazioni
		//prima operazione: delete
	    ErroriIdp errorToBeRemoved = manager.getReference(ErroriIdp.class, erroreIdp.getPk());
	    manager.remove(errorToBeRemoved);
//		manager.merge(erroreIdp);
//		manager.remove(erroreIdp);
		//seconda operazione: insert					
		ErroriIdp newErroreIdp = new ErroriIdp();
		buildNewObject(e2emsgid, senderId, senderSys, erroreIdp,
				pendC, newErroreIdp, trtSenderId, trtSenderSys);
		manager.persist(newErroreIdp);
		
		logger.info(" update erroreIdp (e2emsgid = " + e2emsgid + ", stato = " + StatoEnum.REINVIATO);

		//non c'� bisogno di aggiornare questi stati perch� sono gi� corretti (a questo punto)
		//aggiorno lo stato di pendenze_cart
		//updateStatoPendenza(em, e2emsgid, senderId, senderSys, StatoEnum.RISPOSTA_INVIATA);
		//aggiorno lo stato di esiti_pendenzea
		//updateStatoEsitiPendenza(em, e2emsgid, senderId, senderSys,StatoEnum.INVIATO);

//					tx.commit();
		// se questo processo non gira gli ESITI_CART rimangono in stato DA_SPEDIRE
		// mentre le pendenze cart rimangono in stato RISPOSTA_INVIATA
		logger.info(this.getClass().getSimpleName() + 
				" ho cambiato (esito="+esitiUpd+") lo stato dell'esito cart "
						+ "" + e2emsgid + " - " + senderId + " -  " + senderSys 
						+ " , num. esiti = " + esitiUpd );
	}	
	




	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void updateReinoltroNotifiche(String e2emsgid, String senderId,
			String senderSys, String trtSenderId, String trtSenderSys, ErroriIdp erroreIdp) {

		String query = "Update NotificheCart notificheCart " +
		" set notificheCart.stato = :stato  " +
		" , notificheCart.opAggiornamento = :op_aggiornamento  " +
		" , notificheCart.tsAggiornamento = :ts_aggiornamento  " +
		" WHERE notificheCart.id.e2emsgid = :e2emsgid" +
		" AND notificheCart.id.receiverid= :receiverId " +
		" AND notificheCart.id.receiversys = :receiverSys ";
		logger.info(this.getClass().getSimpleName() + " query update notificheCart = " + query);
		//aggiorno notificaCart
		Query queryUpdateNotifiche = manager.createQuery (query);
		queryUpdateNotifiche.setParameter("stato", StatoEnum.REINVIATO);
		queryUpdateNotifiche.setParameter("e2emsgid", e2emsgid);
		queryUpdateNotifiche.setParameter("receiverId", senderId);
		queryUpdateNotifiche.setParameter("receiverSys", senderSys);
		queryUpdateNotifiche.setParameter("op_aggiornamento", "REINOLTRO_NOTIFICHE");
		queryUpdateNotifiche.setParameter("ts_aggiornamento", new Timestamp(System.currentTimeMillis()));
		int esitiUpd = queryUpdateNotifiche.executeUpdate(); 

		//causa errore nella definizione della PK (e2emsgid+stato)
		//eseguo due operazioni
		//prima operazione: delete
		//a causa del fatto che ci troviamo in una TX NEW
		//devo far s� che l'oggetto partecipi anche a questa transazione
		ErroriIdp errMerged = manager.getReference(ErroriIdp.class, erroreIdp.getPk());
		manager.remove(errMerged);
		
		//seconda operazione: insert
		//non c'� bisogno di reinserire il record nella table ERRORI_IDP
		//perch�
		//se il reinvio ha funzionato, allora avremo ricevuto l'esito della notifica
		//e la notifica sar� cambiata di stato da REINVIATO a ELABORATO OK/KO
		//se invece non ha funzionato allora avremo ricevuto nuovamente un messaggio 
		//in coda ERRORI ed un nuovo record in tabella ERRORI_IDP con stato DA REINVIARE
		//che consentir� nuovamente il reinvio. 
		//(sempre rimanendo nell'intervallo tra -40 e -8ore rispetto al momento corrente)
//		ErroriIdp newErroreIdp = new ErroriIdp();
//		buildNewObject(e2emsgid, senderId, senderSys, erroreIdp,newErroreIdp, trtSenderId, trtSenderSys);
//		manager.persist(newErroreIdp);
//		logger.info(" update erroreIdp (e2emsgid = " + e2emsgid + ", stato = " + StatoEnum.REINVIATO);
		
		//non c'� bisogno di aggiornare questi stati perch� sono gi� corretti (a questo punto)
		//aggiorno lo stato di pendenze_cart
		//updateStatoPendenza(em, e2emsgid, senderId, senderSys, StatoEnum.RISPOSTA_INVIATA);
		//aggiorno lo stato di esiti_pendenzea
		//updateStatoEsitiPendenza(em, e2emsgid, senderId, senderSys,StatoEnum.INVIATO);

		//tx.commit();
		// se questo processo non gira gli NOTIFICHE_CART rimangono in stato INVIATO
		// mentre nella tabella ERRORI_IDP i messaggi rimangono in stato DA_REINVIARE

		logger.info(this.getClass().getSimpleName() + " ho cambiato (esito="+esitiUpd+") lo stato della notifica cart " 
				+ e2emsgid + " - " + senderId + " -  " + senderSys 
				+ " , num. notifiche = " + esitiUpd );			

	}

	/**
	 * 
	 * @param e2emsgid
	 * @param senderId
	 * @param senderSys
	 * @param erroreIdp
	 * @param pendC
	 * @param newErroreIdp
	 */
	private void buildNewObject(String e2emsgid, String senderId,
			String senderSys, ErroriIdp erroreIdp, PendenzeCart pendC,
			ErroriIdp newErroreIdp, String trtSenderId, String trtSenderSys) {
		ErroriIdpPK newErroreIdpPk = new ErroriIdpPK();
		newErroreIdpPk.setE2emsgid(e2emsgid);
		newErroreIdpPk.setSenderid(senderId);
		newErroreIdpPk.setSendersys(senderSys);
		newErroreIdpPk.setStato(StatoEnum.REINVIATO);
		newErroreIdp.setPk(newErroreIdpPk);
		newErroreIdp.setOpAggiornamento("REINOLTRO_ESITI");
		newErroreIdp.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
		newErroreIdp.setOpInserimento(erroreIdp.getOpInserimento());
		newErroreIdp.setTsInserimento(erroreIdp.getTsInserimento());
		newErroreIdp.setEsitoXml(erroreIdp.getEsitoXml());
		newErroreIdp.setDescrizioneStato(erroreIdp.getDescrizioneStato());
		newErroreIdp.setServiceName(erroreIdp.getServiceName());
		newErroreIdp.setReceiverid(erroreIdp.getReceiverid());
		newErroreIdp.setReceiversys(erroreIdp.getReceiversys());
		newErroreIdp.setTrtSenderId(trtSenderId);
		newErroreIdp.setTrtSenderSys(trtSenderSys);		
		newErroreIdp.setPendenzeCart(pendC);
	}

	/**
		 * 
	 * @param e2emsgid
	 * @param senderId
	 * @param senderSys
	 * @param erroreIdp
	 * @param pendC
	 * @param newErroreIdp
	 * @param trtSenderSys 
	 * @param trtSenderId 
	 */
	private void buildNewObject(String e2emsgid, String senderId,
			String senderSys, ErroriIdp erroreIdp, 
			ErroriIdp newErroreIdp, String trtSenderId, String trtSenderSys) {
		ErroriIdpPK newErroreIdpPk = new ErroriIdpPK();
		newErroreIdpPk.setE2emsgid(e2emsgid);
		newErroreIdpPk.setSenderid(senderId);
		newErroreIdpPk.setSendersys(senderSys);
		newErroreIdpPk.setStato(StatoEnum.REINVIATO);
		newErroreIdp.setPk(newErroreIdpPk);
		newErroreIdp.setOpAggiornamento("REINOLTRO_NOTIFICHE");
		newErroreIdp.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
		newErroreIdp.setOpInserimento(erroreIdp.getOpInserimento());
		newErroreIdp.setTsInserimento(erroreIdp.getTsInserimento());
		newErroreIdp.setEsitoXml(erroreIdp.getEsitoXml());
		newErroreIdp.setDescrizioneStato(erroreIdp.getDescrizioneStato());
		newErroreIdp.setServiceName(erroreIdp.getServiceName());
		newErroreIdp.setReceiverid(erroreIdp.getReceiverid());
		newErroreIdp.setReceiversys(erroreIdp.getReceiversys());
		newErroreIdp.setTrtSenderId(trtSenderId);
		newErroreIdp.setTrtSenderSys(trtSenderSys);
//		newErroreIdp.setPendenzeCart(pendC);
	}	
	
}