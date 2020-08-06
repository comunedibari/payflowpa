/**
 * 18/nov/2009
 */
package it.nch.eb.xsqlcmd.jms.tests;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import junit.framework.TestCase;
import it.nch.eb.xsqlcmd.jms.NamedQueueDestinationFactory;

/**
 * @author gdefacci
 */
public class QueueShareTest extends TestCase {
	
	ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
	
	public void test1() throws JMSException, IOException {
		Connection conn = connectionFactory.createConnection();
		
		NamedQueueDestinationFactory destFactory =
			new NamedQueueDestinationFactory("pippo");
		
		Destination dest1 = destFactory.create(conn);
		Destination dest2 = destFactory.create(conn);
		
		Session session1 = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Session session2 = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer prod1 = session1.createProducer(dest1);
		MessageProducer prod2 = session1.createProducer(dest1);
		
		Session consumerSession = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = consumerSession.createConsumer(dest2);
		consumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {
				TextMessage txt = (TextMessage) message;
				try {
					System.out.println("consumer received message " + txt.getText());
				} catch (JMSException e) {
					throw new RuntimeException(e);
				}
			}
			
		});
		
		conn.start();
		
		prod1.send(session1.createTextMessage("first 1"));
		prod2.send(session2.createTextMessage("sec 2"));
		
		System.in.read();
		
		
		
	}
}
