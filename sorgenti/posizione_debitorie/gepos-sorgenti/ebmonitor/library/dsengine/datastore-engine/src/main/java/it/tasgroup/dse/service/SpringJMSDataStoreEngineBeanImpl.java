/**
 * 07/set/2009
 */
package it.tasgroup.dse.service;

import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.jms.IJMSStreamDispatcherFactory;
import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.fwk.xml.validation.ValidationService;
import it.tasgroup.dse.dao.StorageDAO;

import org.springframework.beans.factory.BeanFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class SpringJMSDataStoreEngineBeanImpl extends JMSDataStoreEngineBeanImpl {
	
	public SpringJMSDataStoreEngineBeanImpl() {
		this(DseEnviroments.DEFAULT_ENVIROMENT.apply());
	}
	
	public SpringJMSDataStoreEngineBeanImpl(Environment env) {
		this(env.getBeanFactory());
	}
	
	public SpringJMSDataStoreEngineBeanImpl(BeanFactory bf) {
		super((ValidationService) bf.getBean(SpringNames.BeansNames.XSD_VALIDATION_SERVICE),
				(StorageDAO)bf.getBean(SpringNames.BeansNames.STORAGE_DAO),
				(SqlMapClient) bf.getBean(SpringNames.BeansNames.SQL_MAP_CLIENT),
				(IJMSStreamDispatcherFactory) bf.getBean(SpringNames.BeansNames.JMS_PENDENZE_MODEL_STREAM_DISPATCHER_FACTORY),
				(TablesUidProvider) bf.getBean(SpringNames.BeansNames.TABLES_UID_PROVIDER),
				((java.util.Properties)bf.getBean(SpringNames.BeansNames.CONFIGURATION_BEAN)).getProperty(SpringNames.DEFAULT_CHARSET_PROPERTY)
				);
//		StartablePendenzeDbConsumersFactory dbConsumersStarter = (StartablePendenzeDbConsumersFactory) bf.getBean(SpringNames.BeansNames.JMS_DB_CONSUMERS);
//		dbConsumersStarter.startDbConsumers();
	}

}
