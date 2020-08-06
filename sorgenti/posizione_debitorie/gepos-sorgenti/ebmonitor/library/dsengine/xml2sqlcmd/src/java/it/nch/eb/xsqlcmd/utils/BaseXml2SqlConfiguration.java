/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TableNames;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TableNamesDecoder;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.fwk.core.NamespacesInfos;

import java.sql.Connection;

import javax.sql.DataSource;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public class BaseXml2SqlConfiguration implements Xml2SqlAppConfiguration {
	
	private final SqlMapClient sqlMapClient;
	private final NamespacesInfos namespacesInfos;
	private final DataSource dataSource;
	private final TableNames tableNames;
	
	private TablesUidProvider tableUidProvider;
	
//	public BaseXml2SqlConfiguration(
//			TablesUidProvider tableUidProvider,
//			DataSource dataSource, 
//			SqlMapClient sqlMapClient,
//			String tableNamesMappingsClasspathLocation) {
//		this(NSS, tableUidProvider, dataSource, sqlMapClient, tableNamesMappingsClasspathLocation);
//	}

	public BaseXml2SqlConfiguration(
			NamespacesInfos namespacesInfos,
			TablesUidProvider tableUidProvider,
			DataSource dataSource, 
			SqlMapClient sqlMapClient,
			String tableNamesMappingsClasspathLocation) {
		this.tableUidProvider = tableUidProvider;
		this.sqlMapClient = sqlMapClient;
		this.namespacesInfos = namespacesInfos;
		this.dataSource = dataSource;
		this.tableNames = new TableNamesDecoder( ResourceLoaders.CLASSPATH.load( tableNamesMappingsClasspathLocation ) );
	}
	
	public BaseXml2SqlConfiguration(
			NamespacesInfos namespacesInfos,
			TablesUidProvider tableUidProvider,
			DataSource dataSource, 
			SqlMapClient sqlMapClient,
			TableNames tableNames) {
		this.tableUidProvider = tableUidProvider;
		this.sqlMapClient = sqlMapClient;
		this.namespacesInfos = namespacesInfos;
		this.dataSource = dataSource;
		this.tableNames = tableNames;
	}
	
	

	public final SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
	
	public final NamespacesInfos getNamespacesInfos() {
		return namespacesInfos;
	}
	
	public final DataSource getDataSource() {
		return dataSource;
	}
	
	public Connection getConnection() {
		try {
			DataSource ds = getDataSource();
			return ds.getConnection();
		} catch (Exception e) {
			throw new RuntimeException("errror retieving connection ", e);
		} 
	}

	public final TableNames getTableNames() {
		return tableNames;
	}

	public TablesUidProvider getTablesUidProvider() {
		return tableUidProvider;
	}

}
