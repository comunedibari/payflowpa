/**
 * 24/lug/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.utils.InputsFactoryDispatcher;
import it.nch.fwk.core.NamespacesInfos;

import javax.jms.ConnectionFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class JMSStreamDispatcherFactory implements IJMSStreamDispatcherFactory {
	
	private final int numberOfPendenzePerConsumer;
	private final int numberOfConsumers;
	private final ConnectionFactory connectionFactory;
	private final DestinationFactory destinationFactory;
	private final NamespacesInfos nss;
	private final SqlMapClient sqlMapClient;
	private final TablesUidProvider tablesUidProvider;
	
	public JMSStreamDispatcherFactory(
			NamespacesInfos nss,
			SqlMapClient sqlMapClient,
			TablesUidProvider tablesUidProvider,
			int numberOfPendenzePerConsumer,
			int numberOfConsumers,
			ConnectionFactory connectionFactory,
			DestinationFactory destinationFactory) {
		this.nss = nss;
		this.sqlMapClient = sqlMapClient;
		this.tablesUidProvider = tablesUidProvider;
		this.numberOfPendenzePerConsumer = numberOfPendenzePerConsumer;
		this.numberOfConsumers = numberOfConsumers;
		this.connectionFactory = connectionFactory;
		this.destinationFactory = destinationFactory;
	}

	public InputsFactoryDispatcher create(PendenzeInclusions pendenzeInclusions) {
		return new JMSStreamDispatcher( nss, sqlMapClient, tablesUidProvider, numberOfPendenzePerConsumer, numberOfConsumers,connectionFactory,destinationFactory, pendenzeInclusions );
	}
}
