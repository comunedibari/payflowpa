package it.tasgroup.idp.bean;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.pojo.PrevisitingData;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.StatoEnum;

@Stateless
public class DataStorageManagerSemplificationImpl extends DataStorageManager implements CommandExecutorSemplificationLocal {
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public MonitoringData executeApplicationTransaction(String message) {
		Destination dest = null;
		Connection conn = null;
		ConnectionFactory cf= null;
		Session session = null;
		Message objectMessage = null;
		MessageProducer producer = null;
		try {
			String serviceNameType = doPrevisiting(message); // Analizza msg e lo salva su PENDENZE_CART
			MessageInnerLight mil = new MessageInnerLight();
			mil.setPrevData(prevData);
			mil.setTrtSenderId(trtSenderId);
			mil.setTrtSenderSys(trtSenderSys);
			mil.setServiceNameType(serviceNameType);
			mil.setMonData(monData);
    		cf = (QueueConnectionFactory)IdpServiceLocator.getInstance().getServiceByName("JmsXA");
    		conn = cf.createConnection();
    		session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
    		objectMessage = session.createObjectMessage(mil);
    		dest = (Queue) IdpServiceLocator.getInstance().getServiceByName("JmsAllineamentoPendenzeInternalInput");
    		producer = session.createProducer(dest);	
    		producer.send(objectMessage);
        } catch(Exception e) {
        	if (e instanceof RuntimeException) {
        		throw (RuntimeException) e;        		
        	}
        	throw new RuntimeException("Error Sending message on queue" + e.getMessage());        		
        } finally { 
            try {
            	producer.close();
            	session.close();
            	conn.close(); 
           } catch (JMSException e) { 
        	   logger.info("Closing error jms connection: " + e.getMessage());
           }
        }
		return monData;
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public DataInput executeApplicationTransaction(Object milObject) {
		MessageInnerLight mil = (MessageInnerLight) milObject;
		E2EMsgId = mil.getPrevData().getE2EMsgId();
		idMittente = mil.getPrevData().getIdMittente();
		silMittente = mil.getPrevData().getSilMittente();
		idRicevente = mil.getPrevData().getIdRicevente();
		silRicevente = mil.getPrevData().getSilRicevente();
		trtSenderId = mil.getTrtSenderId();
		trtSenderSys = mil.getTrtSenderSys();
		monData = mil.getMonData();
		prevData = mil.getPrevData();

		DataInput input = getInput(mil.getXml());
		DataStoreEngineService dataStoreEngineService = getDataStoreEngineService();
		boolean elaborateOk = elaborateMessageParte1(mil.getServiceNameType(), mil.getXml(), input, dataStoreEngineService);
		if (elaborateOk)
			return input;
		else
			return null;
	}

	
	@Override
	protected void savingBlobOnDatabase(String message, String E2EMsgId, String idMittente, String silMittente,
			String idRicevente, String silRicevente, String otf, PrevisitingData prevData, String trtSenderId,
			String trtSenderSys) {
		super.savingSemplificationBlobOnDatabase(message, E2EMsgId, idMittente, silMittente, idRicevente, silRicevente, otf, prevData, trtSenderId, trtSenderSys);
	}

	@Override
	protected void setTipoMessaggio(String tipoUpd, PrevisitingData prevData, String otf, PendenzeCartMessage cart) {
		cart.setTipoMessaggio(StatoEnum.MSG_TYPE_ALL_PENDENZE_SSIL);
	}	
		
}

