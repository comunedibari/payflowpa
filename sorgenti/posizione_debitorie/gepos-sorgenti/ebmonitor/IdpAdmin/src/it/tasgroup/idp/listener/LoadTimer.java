package it.tasgroup.idp.listener;

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.idp.timer.TimerCommandExecutorLocal;
import it.tasgroup.idp.util.ConcreteFactory;
import it.tasgroup.idp.util.IrisProperties;
import it.tasgroup.idp.util.SystemPropertiesNames;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;


public class LoadTimer implements ServletContextListener {

	@EJB(beanName = "TimerController")
	private TimerCommandExecutorLocal timerController;

	private final Log logger = LogFactory.getLog(this.getClass());

	public void contextInitialized(ServletContextEvent servletcontextevent) {
		String failoverStrategy = System.getProperty("it.tasgroup.timers.failover.strategy");
		logger.info("Property it.tasgroup.timers.failover.strategy is " + failoverStrategy);

		if ("AUTO".equals(failoverStrategy)) {
			logger.info("Skipping TIMERS initialization");
			return;
		} else {
			logger.info("Failover Strategy is MAN, beginning TIMERS schedule process");
		}

		System.out.println("[LoadTimer::contextInitialized] INIT DSE");

		//using spring to initialize log4j e commons-logging
		String DseImpl = System.getProperty(SystemPropertiesNames.DSE_IMPL);
		ConcreteFactory factory = new ConcreteFactory();
		DataStoreEngineService dataStoreEngineService = factory.createDSE(DseImpl);


		System.out.println("[LoadTimer::contextInitialized] INIT TIMERS BEGIN");
		String currentNode = System.getProperty("it.tasgroup.timer.currentNode");
		timerController.executeApplicationTransaction();
		System.out.println("[LoadTimer::contextInitialized] INIT TIMERS END");
	}


	public void contextDestroyed(ServletContextEvent servletcontextevent) {

		System.out.println("[LoadTimer::contextDestroyed] BYE");

//		System.out.println("[LoadTimer::contextDestroyed] STOPPING TIMERS BEGIN");
//		timerController.executeApplicationTransaction("STOP");
//		System.out.println("[LoadTimer::contextDestroyed] STOPPING TIMERS END");
	}

}
