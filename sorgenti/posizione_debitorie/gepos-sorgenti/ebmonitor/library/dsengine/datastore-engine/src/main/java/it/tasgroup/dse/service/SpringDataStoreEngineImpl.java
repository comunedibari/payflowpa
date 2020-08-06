/**
 * 05/ago/2009
 */
package it.tasgroup.dse.service;

import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelExecutionDispatcherFactory;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.fwk.xml.validation.ValidationService;
import it.tasgroup.dse.dao.StorageDAO;

import org.springframework.beans.factory.BeanFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class SpringDataStoreEngineImpl extends DataStoreEngineBeanImpl {

	public SpringDataStoreEngineImpl() {
		this(DseEnviroments.DEFAULT_ENVIROMENT.apply());
	}
	
	public SpringDataStoreEngineImpl(Environment env) {
		this(env.getBeanFactory());
	}
	
	public SpringDataStoreEngineImpl(BeanFactory bf) {
		super( (ValidationService) bf.getBean(SpringNames.BeansNames.XSD_VALIDATION_SERVICE),  
		(StorageDAO) bf.getBean(SpringNames.BeansNames.STORAGE_DAO),		
		(SqlMapClient) bf.getBean(SpringNames.BeansNames.SQL_MAP_CLIENT),
		(PendenzeModelExecutionDispatcherFactory) bf.getBean(SpringNames.BeansNames.PENDENZE_MODEL_EXECUTION_DISPATCHER_FACTORY),
		(TablesUidProvider) bf.getBean(SpringNames.BeansNames.TABLES_UID_PROVIDER),
		((java.util.Properties)bf.getBean(SpringNames.BeansNames.CONFIGURATION_BEAN)).getProperty(SpringNames.DEFAULT_CHARSET_PROPERTY),
		((NamespacesInfos)bf.getBean(SpringNames.BeansNames.NAMESPACES)));
	}
	
}
