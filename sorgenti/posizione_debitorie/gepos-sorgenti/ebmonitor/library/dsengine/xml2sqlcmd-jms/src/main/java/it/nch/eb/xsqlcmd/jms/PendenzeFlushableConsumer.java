/**
 * 03/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.utils.PendenzeUtils;

import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.slf4j.Logger;

/**
 * @author gdefacci
 */
public class PendenzeFlushableConsumer implements MessageListener {
	
	private static final Logger log = ResourcesUtil.createLogger(PendenzeFlushableConsumer.class);
	
	private final IPendenzeListDbPerformer dbPerformer;
	private final ResettableContainer<PendenzeModel> pendenze = new ResettableContainer<PendenzeModel>();
	private final int numberOfPendenze;

	private String name;
	
	public PendenzeFlushableConsumer(
			String name,
			int numberOfPendenze,
			IPendenzeListDbPerformer dbperf) {
		this.name = name;
		this.numberOfPendenze = numberOfPendenze;
		this.dbPerformer = dbperf;
	}

	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			PendenzeModel pend = getPendenza((ObjectMessage)message);
			pendenze.add(pend);
			log.info(name + " received pendenza " + description(pend));
		} else if (message instanceof TextMessage) {
			TextMessage tm = (TextMessage) message;
			if (getText(tm).equals("flush")) {
				log.debug(name + " received flush message" ); 
				flush();
			} else {
				throw new IllegalStateException(name + " cant interpret " + message);
			}
		} else {
			throw new IllegalStateException(name + "cant interpret " + message);
		}
		flush(false);
	}

	private String description(PendenzeModel pend) {
		return PendenzeUtils.pendenzaDescId(pend);
	}

	private PendenzeModel getPendenza(ObjectMessage message) {
		try {
			Object obj = message.getObject();
			if (obj instanceof PendenzeModel) {
				return (PendenzeModel) obj;
			} else {
				return null;
			}
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	private static String getText(TextMessage tm) {
		try {
			return tm.getText();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void flush() {
		flush(true);
	}
	
	private void flush(final boolean unconditionally) {
		List<PendenzeModel> pends = pendenze.getAndReset( new Function1<ResettableContainer<PendenzeModel>, Boolean>() {

			public Boolean apply(ResettableContainer<PendenzeModel> t) {
				int len = t.getList().size();
				return len > 0 && (unconditionally || len >= numberOfPendenze);
			}
			
		});
		if (pends != null) {
			process(pends);
		}
		dbPerformer.flush();
	}
	
	protected void process(List<PendenzeModel> rest) {
		dbPerformer.apply(rest);
	}

}
