/**
 * 23/nov/2009
 */
package it.nch.eb.xsqlcmd.jms;

import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;

import javax.jms.ConnectionFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 

/**
 * @author gdefacci
 */
public class StartablePendenzeDbConsumersFactory extends PendenzeDbConsumersFactory  {

	private static final Logger log = LoggerFactory.getLogger(StartablePendenzeDbConsumersFactory.class); 

	private final DestinationFactory destinationFactory;
	private boolean alreadyStarted = false;

	public StartablePendenzeDbConsumersFactory(SqlMapClient sqlMapClient,
			TablesUidProvider tablesUidProvider,
			int numberOfPendenzePerConsumers, int numberOfConsumers,
			ConnectionFactory connectionFactory,
			DestinationFactory destinationFactory) {
		super(sqlMapClient, tablesUidProvider, numberOfPendenzePerConsumers,
				numberOfConsumers, connectionFactory, new EsitiErrorHandler(sqlMapClient, tablesUidProvider));
		this.destinationFactory = destinationFactory;
	}
	
	public synchronized void startDbConsumers() {
		if (!alreadyStarted) {
			log.info("starting db consumers"); 
			this.createConsumers(this.destinationFactory);
			alreadyStarted = true;
		} 
	}

}
