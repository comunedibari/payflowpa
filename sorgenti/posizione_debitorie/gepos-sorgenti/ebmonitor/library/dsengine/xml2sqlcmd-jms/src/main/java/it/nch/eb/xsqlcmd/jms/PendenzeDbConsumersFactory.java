/**
 * 19/nov/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;

import java.util.ArrayList;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class PendenzeDbConsumersFactory {
	
	private static final Logger log = 
		ResourcesUtil.createLogger(PendenzeDbConsumersFactory.class);
	
	private final OrchestratorPendenzeModelExecutionDispatcherFactory executionDispatcherFactory;
	private final TablesUidProvider tablesUidProvider;
	private final SqlMapClient sqlMapClient;
	private final int numberOfPendenzePerConsumers;
	private final int numberOfConsumers;
	
	private final EsitiErrorHandler esitiErrHandler;
	private final ConnectionFactory connectionFactory;

	public PendenzeDbConsumersFactory(
			final SqlMapClient sqlMapClient,
			final TablesUidProvider tablesUidProvider,
			int numberOfPendenzePerConsumers,
			int numberOfConsumers,
			ConnectionFactory connectionFactory,
			EsitiErrorHandler esitiErrHandler) {
		this.tablesUidProvider = tablesUidProvider;
		this.sqlMapClient = sqlMapClient;
		this.numberOfPendenzePerConsumers = numberOfPendenzePerConsumers;
		this.numberOfConsumers = numberOfConsumers;
		this.executionDispatcherFactory = new OrchestratorPendenzeModelExecutionDispatcherFactory();
		this.connectionFactory = connectionFactory;
		this.esitiErrHandler = esitiErrHandler;
	}

	public void createConsumers(DestinationFactory dispatchDbDestinationFactory) {
		try {
			Connection connection = connectionFactory.createConnection();
			Destination dest = dispatchDbDestinationFactory.create(connection);
			createConsumers(connection, dest);
			connection.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void createConsumers(Destination dispatchDbDestination) {
		try {
			Connection connection = connectionFactory.createConnection();
			createConsumers(connection, dispatchDbDestination);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	private IPendenzeListDbPerformer createPendenzeDbPerformer(String nm) {
		return new PendenzeListDbPerformerOnebyOne(nm, sqlMapClient, 
				executionDispatcherFactory.create(sqlMapClient, tablesUidProvider, esitiErrHandler));
	}
	
	private void createConsumers(Connection connection,
			Destination dispatchDbDestination) throws JMSException {
		List<Session> sessionsToClose = new ArrayList<Session>();
		Function1<String, IPendenzeListDbPerformer> dbPerformerFactory = new Function1<String, IPendenzeListDbPerformer>() {

			public IPendenzeListDbPerformer apply(String nm) {
				return createPendenzeDbPerformer(nm);
			}
			
		};
		
		for (int i=0; i < numberOfConsumers; i++) {
			Session consumerSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			sessionsToClose.add(consumerSession);
			String selector = createSelector(i, numberOfConsumers);
			MessageConsumer consumer = consumerSession.createConsumer(dispatchDbDestination, selector);
			final String consumerName = "consumer " + i;
			consumer.setMessageListener(createConsumer(consumerName, dbPerformerFactory));
			log.debug("Created Consumer" + consumerName);
		}
	}

	private MessageListener createConsumer(String string, Function1<String, IPendenzeListDbPerformer> dbPerformerFactory) {		
		return new PendenzeFlushableConsumer(string, numberOfPendenzePerConsumers, dbPerformerFactory.apply(string));
	}
	
	private String createSelector(int i, int consumersNumb) {
		return "fin is NULL or fin = " + i;
	}
	
//
//	public static final PendenzeDbConsumersFactory create(final SqlMapClient sqlMapClient,
//			final TablesUidProvider tablesUidProvider,
//			int numberOfPendenzePerConsumers,
//			int numberOfConsumers,
//			ConnectionFactory connectionFactory,
//			EsitiErrorHandler esitiErrHandler) {
//		PendenzeDbConsumersFactory res = new PendenzeDbConsumersFactory(sqlMapClient, tablesUidProvider, 
//						numberOfPendenzePerConsumers, numberOfConsumers, connectionFactory, esitiErrHandler);
//		res.createConsumers(null)
//		return res;
//	};
}
