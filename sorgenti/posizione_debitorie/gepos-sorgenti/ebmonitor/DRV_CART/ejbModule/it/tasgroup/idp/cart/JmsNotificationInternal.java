package it.tasgroup.idp.cart;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.bean.ObjectMessageContainer;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.JmsUtils;
import it.tasgroup.idp.util.StatoEnum;

public class JmsNotificationInternal implements ISenderNotification {
	private final Log logger = LogFactory.getLog(this.getClass());	
	
	@Override 
	public Object delivery(NotificheCart nc,EntityManager manager) throws Exception { 
		//TODO: costanti che dovrebbero essere gestite in maniera differente 
		String connectionFactory = "JmsXA";
		String jmsDestination = "JmsInformativaPagamentoPendenzeOutputInternal";
		
		ObjectMessageContainer omc = new ObjectMessageContainer();
			omc.setReceiverId(nc.getId().getReceiverid());
			omc.setReceiverSys(nc.getId().getReceiversys());
			omc.setSenderId(nc.getSenderid());
			omc.setSenderSys(nc.getSendersys());
			omc.setE2eMsgId(nc.getId().getE2emsgid());
			omc.setMsgContent(new String(nc.getNotificaXml()));
			omc.setIdEnte(nc.getIdEnte());
			omc.setCdTrbEnte(nc.getCdTrbEnte());
			omc.setTipoNotifica(nc.getTipoNotifica());
			
		JmsUtils.sendJmsMessage(connectionFactory, jmsDestination, omc);	
		
		logger.info(this.getClass().getSimpleName() + " messaggio inviato sulla coda =" +jmsDestination+ " , identificativo con = " 
				+ nc.getId().getE2emsgid() + " - " + nc.getId().getReceiverid() + " - " + nc.getId().getReceiversys());
		
		logger.info(this.getClass().getSimpleName() + " contenuto = \n" + nc.getNotificaXml().toString() );
		
		return null;
	}
	
	@Override
	public String getFinalState() {
		return null; //Lo stato finale sara' settato dal listener
	}
}
