/**
 * 18/nov/2009
 */
package it.nch.eb.xsqlcmd.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;

/**
 * @author gdefacci
 */
public class NamedQueueDestinationFactory implements DestinationFactory{
	
	private final Queue queue;
	public NamedQueueDestinationFactory(String name) {
		this.queue = new ActiveMQQueue(name);
	}

	public Destination create(Connection connection) throws JMSException {
		return queue;
	}

}
