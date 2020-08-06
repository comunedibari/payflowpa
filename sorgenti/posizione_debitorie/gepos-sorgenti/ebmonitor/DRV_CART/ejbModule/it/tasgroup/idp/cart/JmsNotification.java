package it.tasgroup.idp.cart;

import java.nio.charset.Charset;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import it.tasgroup.idp.domain.enti.Sil;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.util.JmsUtils;
import it.tasgroup.idp.util.StatoEnum;

/**
 * Gestisce la spedizione delle notifiche verso Cart tramite Proxy Cart o SmartSil
 * 
 * @author tasgroup
 *
 */
public class JmsNotification implements ISenderNotification {

	private final Log logger = LogFactory.getLog(this.getClass());	
	
	private boolean isSmartSil = false;
	/**
	 * La spedizione delle notifiche verso Cart avviene, in alternativa: 
	 * - su coda esterna su cui e' in ascolto Proxy Cart (SmartSil assente)
	 * - su coda interna su cui e' in ascolto un MDB che colloquia con SmartSil (SmartSil presente)
	 * 
	 * Nel caso di presenza di SmartSil, l'aggiornamento dello stato di NOTIFICHE_CART non deve avvenire sul chiamante
	 * (NotificationItemSenderBean). Questa operazione viene gestita 
	 */
	@Override 
	public Object delivery(NotificheCart notifica, EntityManager em) throws Exception { 
		final String connectionFact = "JmsXA";
		final String connectionFactClusterAware = "ClusteredXAConnectionFactory";
		
		if (!isSSilActive(notifica.getId().getReceiversys(),notifica.getId().getReceiverid(),em)) {
			// Passaggio attraverso Proxy Cart
			logger.info(this.getClass().getSimpleName() + " notifica da indirizzare a a Proxy Cart");
			JmsUtils.sendJmsMessage(connectionFact, "JmsInformativaPagamentoPendenzeOutput", new String(notifica.getNotificaXml(), Charset.forName("UTF-8")));

			logger.info(this.getClass().getSimpleName() + " notifica spedita verso cart, idBustaEgov = " 
					+ notifica.getId().getE2emsgid() + " - " + notifica.getId().getReceiverid() + " - " + notifica.getId().getReceiversys());
			logger.info(this.getClass().getSimpleName() + " esito idBustaEgov = \n" + notifica.getNotificaXml().toString() );
			isSmartSil=false;
			return null;
		} else {
			// Passaggio attraverso Smart Sil
			logger.info(this.getClass().getSimpleName() + " notifica da indirizzare a SmartSil");
			JmsUtils.sendJmsMessage(connectionFact, connectionFactClusterAware, "JmsInformativaPagamentoPendenzeOutputInternalSSil", notifica.getId());
			// Escamotage per comunicare al chiamante che non deve procedere all'update dello stato di NOTIFICHE_CART
			// return new Boolean(false);
			isSmartSil=true;
			return null;
		}
	}
	
	/**
	 * Individua lo stato finale di NotificaCart se la spedizione (put nella coda) e' avvenuta correttamente
	 */
	@Override
	public String getFinalState() {
		
		if(isSmartSil) {
			return null; // Lo stato finale sara' settato dal listener
		} else {
			return StatoEnum.INVIATO;
		}
	}
	
	private boolean isSSilActive(String receiverSys,String receiverId, EntityManager em) {
		try {
			Sil sil = (Sil) em.createNamedQuery("checkSIL")
                               .setParameter("idSystem", receiverSys)
                               .setParameter("idMittente", receiverId)
                                   .getSingleResult();
			return sil.isSSilEnabled();
		} catch (NoResultException e) {
			return false;
		}
	}
}
