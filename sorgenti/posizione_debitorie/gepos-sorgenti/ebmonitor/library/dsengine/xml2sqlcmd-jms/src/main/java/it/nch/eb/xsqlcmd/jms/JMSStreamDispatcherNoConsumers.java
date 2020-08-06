/**
 * 03/set/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.FromXmlDispatcher;
import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FromXmlToDbPendenze;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzaXPathValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;
import it.nch.fwk.core.NamespacesInfos;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class JMSStreamDispatcherNoConsumers implements InputsFactoryDispatcher{
	
	private static final Logger log = 
		ResourcesUtil.createLogger(JMSStreamDispatcherNoConsumers.class);
	
	private final OrchestratorPendenzeModelExecutionDispatcherFactory executionDispatcherFactory;
	
	private final ConnectionFactory connectionFactory;
	private final DestinationFactory destinationFactory;
	private final FromXmlPendenzeDispatcherFactory dispatcherFactory;
	private final int numberOfConsumers;
	private final SqlMapClient sqlMapClient;

	private TablesUidProvider tablesUidProvider;

	public JMSStreamDispatcherNoConsumers(
			final NamespacesInfos nss,
			final SqlMapClient sqlMapClient,
			final TablesUidProvider tablesUidProvider,
			int numberOfConsumers,
			ConnectionFactory connectionFactory,
			DestinationFactory destinationFactory,
			final PendenzeInclusions pendenzeInclusions) {
		this.connectionFactory = connectionFactory;
		this.destinationFactory = destinationFactory;
		this.numberOfConsumers = numberOfConsumers;
		this.dispatcherFactory = new FromXmlPendenzeDispatcherFactory() {

			public FromXmlDispatcher create(PendenzeModelEffect effct, PendenzaXPathValidator pendenzaXPathValidator) {
				return new FromXmlToDbPendenze(null, null,effct, pendenzaXPathValidator, pendenzeInclusions, nss);
			}
			
		};
		this.sqlMapClient = sqlMapClient;
		this.tablesUidProvider = tablesUidProvider;
		this.executionDispatcherFactory = new OrchestratorPendenzeModelExecutionDispatcherFactory();
	}

	public void dispatch(InputsFactory rf) {
		PendenzeDispatcher producer = null;
		try {
			Connection connection = connectionFactory.createConnection();
			Destination dispatchDbDestination = destinationFactory.create(connection);

			Session prodSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			producer = createPendenzaProducer(prodSession, dispatchDbDestination);
			log.info("Created Producer" + producer.toString());

			
			final EsitiErrorHandler esitiErrHandler = new EsitiErrorHandler(sqlMapClient, tablesUidProvider);
			
			PendenzaXPathValidator pendenzaXPathValidator =
				new PendenzaXPathValidator( esitiErrHandler, executionDispatcherFactory.getErrorsFactory() );
			FromXmlDispatcher pushPendenze = dispatcherFactory.create(producer, pendenzaXPathValidator);
//			connection.start();
			pushPendenze.dispatch(rf);
			
			for (int i=0; i< numberOfConsumers; i++) {
				TextMessage msg = prodSession.createTextMessage("flush");
				msg.setIntProperty("fin", i);
				producer.send(msg);
				log.debug("Send Message to Consumer" + msg.getText());
			}
			

		} catch (Exception e) {
//			e.printStackTrace();
			log.error("Error dispatching " + e.getMessage() );
			throw new RuntimeException(e);
		} finally {
			if (producer!=null) {
				 producer.close();	
			} 			 
		}
	}

	private PendenzeDispatcher createPendenzaProducer( Session session, Destination destination) {
		return new PendenzeDispatcher(session, destination);
	}

	
}
