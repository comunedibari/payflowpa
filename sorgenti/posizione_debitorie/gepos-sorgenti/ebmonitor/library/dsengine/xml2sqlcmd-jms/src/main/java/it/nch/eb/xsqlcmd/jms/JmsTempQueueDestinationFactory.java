/**
 * 23/giu/2009
 */
package it.nch.eb.xsqlcmd.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * @author gdefacci
 */
public class JmsTempQueueDestinationFactory implements DestinationFactory {
	
	public Destination create(Connection connection) throws JMSException {
		return connection.createSession(false, Session.AUTO_ACKNOWLEDGE).createTemporaryQueue();
	}

}
