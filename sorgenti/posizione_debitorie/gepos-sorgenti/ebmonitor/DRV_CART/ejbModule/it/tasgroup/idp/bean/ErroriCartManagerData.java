package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;
import it.tasgroup.idp.util.ServiceLocalMapper;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ErroriCartManagerData extends DataStorageAbstract   
 implements DataStorageInterface {
	
	@PersistenceContext(unitName=ServiceLocalMapper.IdpCartJta)
	public EntityManager manager;	
	@Resource 
	private EJBContext ejbCtx;	
	
	private final Log logger = LogFactory.getLog(this.getClass());

	public ErroriCartManagerData() {
	}


	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public void persistWithTx(ErroriCart cart) {
		
		try {
			manager.persist(cart);
			logger.info("PERSISTING With Except");
			throw new NullPointerException();
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Set Rollback");
			ejbCtx.setRollbackOnly();
		}		
	}
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)	
	public void persist(ErroriCart cart) {
		
		try {
			
			manager.persist(cart);
			logger.info("PERSISTING ");
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("Set Rollback2");
			ejbCtx.setRollbackOnly();
		}		
	}


	@Override
	public void updateEsitoAndPendenze(String e2emsgid, String senderId,
			String senderSys, String esitoDaSpedire) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateReinoltroEsiti(String e2emsgid, String senderId,
			String senderSys, ErroriIdp erroreIdp, String trtSenderId,
			String trtSenderSys) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateReinoltroNotifiche(String e2emsgid, String senderId,
			String senderSys, String trtSenderId, String trtSenderSys,
			ErroriIdp erroreIdp) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateStatoNotifichePagamenti(NotifichePagamenti notPagamento) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void persistNotificheCartAndUpdateStatoNotPagamenti(
			NotificheCart notificheCart,
			List<PagamentoModel> listaPagamentiModel,
			NotifichePagamentoModel model) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void persistNotificheCartAndUpdateStatoNotPagamenti(
			NotificheCart notificheCart, NotifichePagamenti notPagamento) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void persistSemplificationPendenzeCart(PendenzeCartMessage cart) {
		// TODO Auto-generated method stub
		
	}












	
	
	
	
}