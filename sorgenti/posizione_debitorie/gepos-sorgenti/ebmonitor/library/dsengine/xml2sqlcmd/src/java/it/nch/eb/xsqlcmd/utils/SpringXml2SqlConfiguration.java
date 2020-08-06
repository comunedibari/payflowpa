/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.fwk.core.NamespacesInfos;

import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class SpringXml2SqlConfiguration extends BaseXml2SqlConfiguration {
	
	protected static final String NSS_BEAN_NAME = "nss";
	protected static final String DATA_SOURCE_BEAN_NAME = "dataSource";
	protected static final String SQL_MAP_CLIENT_BEAN_NAME = "sqlMapClient";
	protected static final String TABLES_UID_PROVIDER_BEAN_NAME = "tablesUidProvider";
	
	protected static final String TABLE_MAPPINGS_CLASSPATH_LOCATION = "db/table-name-mappings.properties";
	private final BeanFactory beanFactory;
	
	public SpringXml2SqlConfiguration(Environment env) {
		this(env.getBeanFactory());
	}
	
	protected SpringXml2SqlConfiguration(BeanFactory bf) {
		super(((NamespacesInfos)bf.getBean(NSS_BEAN_NAME)), 
			  (TablesUidProvider)bf.getBean(TABLES_UID_PROVIDER_BEAN_NAME),
			  (DataSource)	bf.getBean(DATA_SOURCE_BEAN_NAME),
			  (SqlMapClient) bf.getBean(SQL_MAP_CLIENT_BEAN_NAME),
			  TABLE_MAPPINGS_CLASSPATH_LOCATION);
		this.beanFactory = bf;
	}

	protected BeanFactory getBeanFactory() {
		return beanFactory;
	}

}
