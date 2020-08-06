/**
 * 24/nov/2009
 */
package it.nch.eb.xsqlcmd.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;

/**
 * @author gdefacci
 */
public class SingletonDestinationFactory implements DestinationFactory {
	
	private final Destination destination;
	public SingletonDestinationFactory(Destination destination) {
		this.destination = destination;
	}

	public Destination create(Connection connection) throws JMSException {
		return destination;
	}

}
