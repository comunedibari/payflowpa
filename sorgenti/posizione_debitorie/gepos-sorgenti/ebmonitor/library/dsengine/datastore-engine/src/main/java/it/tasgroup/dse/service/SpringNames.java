/**
 * 07/set/2009
 */
package it.tasgroup.dse.service;

/**
 * @author gdefacci
 */
public class SpringNames {

	public static class BeansNames {
		public static final String SQL_MAP_CLIENT = "sqlMapClient";
		public static final String PENDENZE_MODEL_EXECUTION_DISPATCHER_FACTORY = "pendenzeModelExecutionDispatcherFactory";
		public static final String JMS_PENDENZE_MODEL_EXECUTION_DISPATCHER_FACTORY = "jmsDispatcherFactory";
		public static final String JMS_PENDENZE_MODEL_STREAM_DISPATCHER_FACTORY = "jmsStreamDispatcherFactory";
		public static final String TABLES_UID_PROVIDER = "tablesUidProvider";
		public static final String STORAGE_DAO = "storageDao";
		public static final String JMS_DB_CONSUMERS = "jmsDbConsumers";
		public static final String XSD_VALIDATION_SERVICE = "idpXsdValidator";
		public static final String CONFIGURATION_BEAN = "configurationBean";
		public static final String DATASOURCE = "dataSource";
		public static final String NAMESPACES = "nss";
	
	}

	public static final String DEFAULT_CHARSET_PROPERTY  = "DefaultCharset";
	
	public static class SystemProperties {

		public static final String ENV_CONFIG_LOCATION = "it.tasgroup.dse.conf.location";
		public final static String ENV_CONFIG_PROPERTY_NAME = "it.tasgroup.dse.enviroment";
		
		public static final String DEFAULT_CONFIG_LOCATION = "classpath:";
		
	}

}
