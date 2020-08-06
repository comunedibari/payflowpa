/**
 * 23/giu/2009
 */
package it.nch.eb.xsqlcmd.jms;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;

/**
 * @author gdefacci
 */
public interface DestinationFactory {

	Destination create(Connection connection) throws JMSException;
	
}
