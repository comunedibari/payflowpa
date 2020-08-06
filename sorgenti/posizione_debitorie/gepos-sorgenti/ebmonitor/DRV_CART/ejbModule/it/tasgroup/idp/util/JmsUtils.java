package it.tasgroup.idp.util;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;

public class JmsUtils {

	private JmsUtils() {}
	
	public static void sendJmsMessage(String cfString, String jmsDestinationString, Serializable serializable) throws JMSException {
		ConnectionFactory connectionFactory = (QueueConnectionFactory)IdpServiceLocator.getInstance().getServiceByName(cfString);
		sendJmsMessage(connectionFactory, jmsDestinationString, serializable);	
	}
	
	public static void sendJmsMessage(String cfString, String cfStringClusterAware, String jmsDestinationString, Serializable serializable) throws JMSException {
		ConnectionFactory connectionFactory = (QueueConnectionFactory)IdpServiceLocator.getInstance().getServiceByName(cfString, cfStringClusterAware);
		sendJmsMessage(connectionFactory, jmsDestinationString, serializable);	
	}
	
	private static void sendJmsMessage(ConnectionFactory connectionFactory, String jmsDestinationString, Serializable serializable) throws JMSException {
		Connection connection = null;
		Session session = null;
		MessageProducer producer = null;
		Message message;
		Destination destination;
		
		try {
			connection = connectionFactory.createConnection();
				connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			message = serializable instanceof String ? session.createTextMessage((String)serializable) : session.createObjectMessage(serializable);
				message.setBooleanProperty("output", true);
				message.setStringProperty("owner", "IRIS");
			destination = (Queue) IdpServiceLocator.getInstance().getServiceByName(jmsDestinationString);
			producer = session.createProducer(destination);
				producer.send(message);
		} finally {
			if (producer != null) try {producer.close();} catch (Exception e) {/* non interessa */}
			if (session != null) try {session.close();} catch (Exception e) {/* non interessa */}
			if (connection != null) {
				try {connection.stop();} catch (Exception e) {/* Non interessa */}
				try {connection.close();} catch (Exception e) {/* Non interessa */}
			}
		}
	}
}
