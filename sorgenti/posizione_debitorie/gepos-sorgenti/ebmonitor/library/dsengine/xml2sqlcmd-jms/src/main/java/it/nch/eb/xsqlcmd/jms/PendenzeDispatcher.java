/**
 * 03/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.utils.PendenzeUtils;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.slf4j.Logger;

/**
 * @author gdefacci
 */
public class PendenzeDispatcher implements PendenzeModelEffect {
	
	private static final Logger log = ResourcesUtil.createLogger(PendenzeDispatcher.class);
	
	private static final String JMS_GROUP_ID_PROPERTY = "JMSXGroupID";
	private final Session session;
	private final MessageProducer producer;
	
	public PendenzeDispatcher(Session session, Destination destination) {
		this.session = session;
		this.producer = createProducer(session, destination);
	}

	protected MessageProducer createProducer(Session session, Destination destination) {
		try {
			return session.createProducer(destination);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public void apply(PendenzeModel model) {
		send(model);
	}
	
	public void send(Message msg) {
		try {
			producer.send(msg);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void send(PendenzeModel pm) {
		try {
			ObjectMessage objMsg = session.createObjectMessage(pm);
			objMsg.setStringProperty(JMS_GROUP_ID_PROPERTY, PendenzeUtils.pendenzaId(pm));
			producer.send(objMsg);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	public void close() {
		try {
			this.producer.close();
			this.session.close();
		} catch (JMSException e) {
			log.error("error closing jms producer/session", e);
		}
	}

}
