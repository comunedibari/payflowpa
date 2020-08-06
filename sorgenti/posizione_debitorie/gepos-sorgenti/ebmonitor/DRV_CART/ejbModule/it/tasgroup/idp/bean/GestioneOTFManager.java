package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.idp.domain.posizioneDebitoria.CondizioniPagamento;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.StatoEnum;
import it.tasgroup.iris.domain.CarrelloGateway;
import it.tasgroup.iris.domain.CarrelloGatewayPK;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.domain.SessioneGateway;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class GestioneOTFManager implements ObjectCommandExecutor {

	@Resource
	private EJBContext ctx;
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpBTJta)
	private EntityManager manager;
	
	private final Log logger = LogFactory.getLog(this.getClass());


	@Override
//    @Interceptors(StoricoFlussiInterceptor.class)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(Object otf) {
		// Monitoring data
    	MonitoringData monitoringData = new MonitoringData();    	   
    	
    	SessioneGateway session = (SessioneGateway)otf;
    	    	
		// Fill object for interceptor
    	monitoringData.setTipoOperazione("SAVE TOKEN PER OTF!");
		try {
			
			//completo la table session
			session.completeForInsert();	
			session.setOperatore("ANONYMOUS");
			session.setIntestatario("ANONYMOUS");
			session.setUsata(0);
			session.setImTotale(BigDecimal.ZERO);			
			//session, altri campi
			session.setSessionId("sessionId");					
			
			//completo la table session con la config
			CfgIrisGatewayClient cfgGw2 = manager.find(CfgIrisGatewayClient.class, new Long(2));
			session.setCfgIrisGatewayClient(cfgGw2);
			
			logger.info(" INSERT TOKEN " + session.getToken() );							
			
			//aggiungo carrello
			Set<CarrelloGateway> setG = new HashSet<CarrelloGateway>();

			//condiz. inserite	
			List<CondizioniPagamento> listaEsitiPendenza = this.findCondInserite(session.getE2eMsgId(), session.getSenderId(), session.getSenderSys());
			Iterator<CondizioniPagamento> iterEsitiP = listaEsitiPendenza.iterator();
			while (iterEsitiP.hasNext()) {
				CondizioniPagamento condizioni = (CondizioniPagamento) iterEsitiP.next();
				//cambiamento ciclo, si usa while Iter anzichè for
				logger.info(" IdCondizione =  " + condizioni.getIdCondizione());
				logger.info(" IdCondizione, Importo =  " + condizioni.getImTotale());
				String idCond = condizioni.getIdCondizione();
				
				//creo e completo la table carrello
				CarrelloGatewayPK sessPk = new CarrelloGatewayPK();
				sessPk.setToken(session.getToken());
				sessPk.setIdCondizione(idCond);				

				CarrelloGateway sessId = new CarrelloGateway();
				sessId.completeForInsert();
				sessId.setImTotale(BigDecimal.ZERO);
				sessId.setId(sessPk);
				//aggiungo al set
				setG.add(sessId);
				//aggiungo relazione con table sessioni
				sessId.setSessioneGateway(session);
				System.out.println(" CarrelloInserito " );
			}			

			//aggiungo su table sessioni
			session.setCarrelloGateways(setG);		
			
			//SAVE
			manager.persist(session);	 			
			
											 

		} catch (PersistenceException e) {
			logger.error(" PERSISTENT EXCEPTION SAVE TOKEN PER OTF " + e.getMessage());
			logger.error(e);
			throw e;
		} catch (RuntimeException e) {
			logger.error(" GENERIC ECCEZIONE SAVE TOKEN PER OTF " + e.getMessage());
			logger.error(e);
			ctx.setRollbackOnly();
			throw e;
		}			
		
		return monitoringData;

	}


	@Override
	public String executeHtml(String msg, String sendId, String sendSys) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 *
	 * @param message
	 * @param E2EMsgId
	 * @param idMittente
	 * @param silMittente
	 * @return
	 */
	private List<CondizioniPagamento> findCondInserite(String e2emsgid, String id, String sil) {
	
		//creo criteria
		PendenzeCart cart = new PendenzeCart();
		PendenzeCartPK cartPk = new PendenzeCartPK();
		cartPk.setE2emsgid(e2emsgid);
		cartPk.setSenderid(id);
		cartPk.setSendersys(sil);
		cart.setPk(cartPk);		
		
		//OLD STYLE QUERY...
		//SOSTITUITA DA NAMED QUERY!!
//		Query queryCondizioni = this.manager.createQuery("SELECT cond FROM EsitiPendenza esitiPendenze" +
//				" , CondizioniPagamento cond  " +
//				" WHERE esitiPendenze.stato = :stato " +
//				" AND cond.idPendenza = esitiPendenze.idPendenza " +
//				" AND esitiPendenze.pendenzeCart = :cart "
//				);					
		Query queryCondizioni = manager.createNamedQuery("listaEsitiPendenza");				
		queryCondizioni.setParameter("stato", StatoEnum.RISPOSTA_INVIATA_WS);
		queryCondizioni.setParameter("cart", cart);

		List<CondizioniPagamento> listaEsitiPendenza = (List) queryCondizioni.getResultList();

		logger.info(" Costruisco esito del messaggio " + e2emsgid + "/" + id + "/" + sil
					+ " contenente n° esitiPendenza = " + listaEsitiPendenza.size());
		Iterator<CondizioniPagamento> iterEsitiP = listaEsitiPendenza.iterator();
		while (iterEsitiP.hasNext()) {
			CondizioniPagamento condizioni = (CondizioniPagamento) iterEsitiP.next();
			//cambiamento ciclo, si usa while Iter anzichè for
			logger.info(" IdCondizione =  " + condizioni.getIdCondizione());
			logger.info(" IdCondizione, Importo =  " + condizioni.getImTotale());
		}
			
//		select * from ESITI_PENDENZA 
//		left outer join JLTPEND on (ESITI_PENDENZA.ID_PENDENZA = JLTPEND.ID_PENDENZA)
//		left outer join JLTCOPD on (JLTCOPD.ID_PENDENZA = JLTPEND.ID_PENDENZA)
//		where E2EMSGID='SOAPUI-092'
	
	
		return listaEsitiPendenza;
		
	}


}
