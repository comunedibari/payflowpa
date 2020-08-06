package it.tasgroup.iris.sso.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

import org.apache.log4j.Logger;

public class SSOManagerFactory {

	private static final Logger logger = Logger.getLogger(SSOManagerFactory.class);
	private static List<SSOManager> servicesList = null;

	private SSOManagerFactory() {
	}

	public static synchronized SSOManager getSSOManager() {

		if (servicesList == null) {
			initServiceList();
		}
		if (servicesList.size() != 1) {
			logger.error("ERRORE NELLA CREAZIONE DEL PACCHETTO DELL'APPLICAZIONE");
			logger.error("HO TROVATO " + servicesList.size() + " implementazioni dell'SSOManager");
			int i = 1;
			for (SSOManager service : servicesList) {
				logger.error("" + i++ + " - " + service.getClass());
			}
			throw new RuntimeException("ERRORE NELLA CREAZIONE DEL PACCHETTO DELL'APPLICAZIONE");
		}
		return servicesList.get(0);

	}

	private static void initServiceList() {

		servicesList = new ArrayList<SSOManager>();
		ServiceLoader<SSOManager> loader = ServiceLoader.load(SSOManager.class);
		logger.debug("SSOManagerLoader: " + loader.toString());

		try {
			Iterator<SSOManager> services = loader.iterator();
			while (services.hasNext()) {
				SSOManager item = services.next();
				logger.debug("carico implementazione: " + item.getClass());
				servicesList.add(item);
			}

		} catch (ServiceConfigurationError serviceError) {
			serviceError.printStackTrace();
		}

	}

}
