package it.tasgroup.addon.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.helper.AddOnManager;
import it.tasgroup.addon.api.manager.helper.ValidationMessage;
import it.tasgroup.iris.facade.ejb.client.anonymous.AnonymousPaymentFacade;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;

public class AddOnManagerFactory {
	
	private static final String ADDON_PROPERTIES_FILE_NAME = "addon.properties";
	private static final String ADDON_PROPERTIES_PREFIX = "it.tasgroup.addon.";

	/**
	 * Durata (in minuti) della cache - valore di default 15 minuti per sovrascrivere quasto valore e' sufficiente valorizzare la system property
	 * "addon.cache.duration.minutes"
	 */
	private static final long CACHE_DURATION_MINUTES_DEFAULT_VALUE = 15;
	private static final String CACHE_DURATION_SYSTEM_PROPERTY_NAME = "addon.cache.duration.minutes";

	private static Map<String, AddOnManager<?>> addonCache = new HashMap<String, AddOnManager<?>>();
	private static long initCacheTime = new Date().getTime();

	private static long getCacheDurationMillisec() {
		long cacheDurationMinutes = CACHE_DURATION_MINUTES_DEFAULT_VALUE;
		String cacheDurationMinutesProperty = System.getProperty(CACHE_DURATION_SYSTEM_PROPERTY_NAME);
		if (cacheDurationMinutesProperty != null) {
			try {
				cacheDurationMinutes = Long.parseLong(cacheDurationMinutesProperty);
			} catch (NumberFormatException e) {
				System.err.println("AddOnManagerFactory - Errore nella valorizzazione della system property " + CACHE_DURATION_SYSTEM_PROPERTY_NAME
						+ " - uso il default: " + CACHE_DURATION_MINUTES_DEFAULT_VALUE);
				e.printStackTrace();
			}
		}
		return cacheDurationMinutes * 6000;
	}

	
	public static String clearAddonCache() {
		StringBuilder report = new StringBuilder("clearAddonCache");
		report.append("\n\n").append("before clear - items: " + addonCache.size());
		for (String addonKey : addonCache.keySet()) {
			report.append("\n").append(addonKey).append(": \t").append(addonCache.get(addonKey).getClass().getName());
		}
		addonCache.clear();
		initCacheTime = new Date().getTime();
		report.append("\n\n").append("after  clear - items: " + addonCache.size());
		return report.toString();
	}

	
	private static String addOnKey(String idEnte, String cdTributo) {
		return idEnte + "-" + cdTributo;
	}

	
	static public boolean exists(String cdPlugin) {

		if (cdPlugin == null || cdPlugin.isEmpty()) {
			return false;
		}

		boolean exists = true;
		try {
			getAddonManagerClassName(cdPlugin); // TODO cache addOnManagers

		} catch (RuntimeException e) {
			Logger.getAnonymousLogger().info(e.getMessage());
			exists = false;
		} catch (Exception e) {
			e.printStackTrace();
			exists = false;
		}
		return exists;
	}

	
	public static synchronized <T extends TributoStrutturato> AddOnManager<T> getAddOnManager(String idEnte, String cdTributo, String cdPlugin) {

		long time = new Date().getTime();
		if (time - initCacheTime > getCacheDurationMillisec()) {
			System.out.println("getAddOnManager - cache expired");
			addonCache.clear();
			initCacheTime = time;
		}

		@SuppressWarnings("unchecked")
		AddOnManager<T> instance = (AddOnManager<T>) addonCache.get(addOnKey(idEnte, cdTributo));

		if (instance == null) {

			String concreteAddOnManagerClassName = getAddonManagerClassName(cdPlugin);
			System.out.println("getAddOnManager - loading: " + concreteAddOnManagerClassName);

			try {
				AnonymousPaymentFacade anonymousBean = (AnonymousPaymentFacade) ServiceLocator.getSLSBProxy("anonymousPaymentBeanRemote");

				byte[] dati = anonymousBean.getDatiCfgTributoPlugin(idEnte, cdTributo);

				// suppress unchecked warnings (...!!is not possible to avoid unsafe cast here!!)
				@SuppressWarnings("unchecked")
				Class<AddOnManager<T>> clazz = (Class<AddOnManager<T>>) Class.forName(concreteAddOnManagerClassName);

				instance = clazz.newInstance();
				instance.setDati(dati); // setDati costruisce la configurazione specifica del plugin (quando serve)

				// oppure uso il costruttore con parametro ...
				// Constructor<?> cons = clazz.getConstructor(byte[].class);
				// instance = (AddOnManager<?>)cons.newInstance(dati);

				addonCache.put(addOnKey(idEnte, cdTributo), instance);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return (AddOnManager<T>) instance;
	}

	
	private static String getAddonManagerClassName(String codPlugin) {

		if (codPlugin == null || codPlugin.isEmpty()) {
			throw new RuntimeException("Manca il parametro codPlugin nella richiesta");
		}
		Properties addOnConfiguration = getAddonPropertiesConfiguration();
		String concreteAddOnManagerClassName = addOnConfiguration.getProperty(ADDON_PROPERTIES_PREFIX + codPlugin.toLowerCase());
		if (concreteAddOnManagerClassName == null) {
			throw new RuntimeException(ADDON_PROPERTIES_FILE_NAME + " non contiene la configurazione per il plugin con codice: " + codPlugin
					+ ". Il file deve contenere una property che si chiama: " + ADDON_PROPERTIES_PREFIX + codPlugin.toLowerCase());
		}
		return concreteAddOnManagerClassName;
	}


	private static Properties addonPropertiesConfiguration = null;
	/*
	 * N.B. informazione valorizzata alla prima richiesta e tenuta in cache
	 */ 
	private static final Properties getAddonPropertiesConfiguration() {
		
		if (addonPropertiesConfiguration == null) {
			Properties props = new Properties();
			InputStream inputStream = AddOnManagerFactory.class.getClassLoader().getResourceAsStream(ADDON_PROPERTIES_FILE_NAME);
			if (inputStream == null) {
				throw new RuntimeException("property file '" + ADDON_PROPERTIES_FILE_NAME + "' not found in the classpath");
			}
			try {
				props.load(inputStream);
				addonPropertiesConfiguration = props;
			} catch (IOException e) {
				throw new RuntimeException("Error resolving loading property file: " + ADDON_PROPERTIES_FILE_NAME, e);
			}
		}
		return addonPropertiesConfiguration;
	}

	

	private static  Map<String, AddOnInfo> managedAddonInfo = null;
	/**
	 * Restituisce una lista di informazioni del addon gestiti dall'applicazione
	 * N.B. informazione valorizzata alla prima richiesta e tenuta in cache 
	 * @return
	 */
	public static Map<String, AddOnInfo> getManagedAddonInfo() {

		if (managedAddonInfo == null) {
			
			Map<String, AddOnInfo> infos = new HashMap<String, AddOnInfo>();
			Properties properties = getAddonPropertiesConfiguration();
	
			for (final String key : properties.stringPropertyNames()) {
				String shortKey = key.substring(ADDON_PROPERTIES_PREFIX.length()); // es: it.tasgroup.addon.raccolta_funghi ---> raccolta_funghi 
				String name = shortKey.replace('_', ' '); // es: it.tasgroup.addon.raccolta_funghi ---> "raccolta funghi"
				String codPlugin = shortKey.toUpperCase(); // es: it.tasgroup.addon.raccolta_funghi ---> "RACCOLTA_FUNGHI"
	
				boolean configurable;
				boolean predetermined;
				String concreteAddOnManagerClassName = properties.getProperty(key);
				try {
					@SuppressWarnings("unchecked")
					Class<AddOnManager<? extends TributoStrutturato>> clazz = (Class<AddOnManager<? extends TributoStrutturato>>) Class.forName(concreteAddOnManagerClassName);
					AddOnManager<? extends TributoStrutturato> instance = clazz.newInstance();
					configurable = instance.isConfigurable();
					predetermined = instance.isPredetermined();
				} catch (Exception e) {
					throw new RuntimeException("Error during getManagedAddonInfoList", e);
				}
				infos.put(codPlugin, new AddOnInfo(name, codPlugin, configurable, predetermined));
			}
			managedAddonInfo = infos;
		}
		return managedAddonInfo;
		
	}
	
	public static ValidationMessage validatePluginConfig(String cdPlugin, byte[] dati) {
		
		String concreteAddOnManagerClassName = getAddonManagerClassName(cdPlugin);
		try {
			@SuppressWarnings("unchecked")
			Class<AddOnManager<? extends TributoStrutturato>> clazz = (Class<AddOnManager<? extends TributoStrutturato>>) Class.forName(concreteAddOnManagerClassName);
			AddOnManager<? extends TributoStrutturato> instance = clazz.newInstance();
			return instance.validateDati(dati);

		} catch (Exception e) {
			// throw new RuntimeException("Error during getManagedAddonInfoList", e);
			return new ValidationMessage(ValidationMessage.Esit.KO, e.getMessage());
		}
		
	}
}
