/**
 * 26/mag/2009
 */
package it.nch.eb.xsqlcmd.utils;

import java.sql.Connection;

import it.nch.eb.common.utils.ConnectionProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TableNames;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.fwk.core.NamespacesInfos;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public interface Xml2SqlAppConfiguration extends ConnectionProvider {

	NamespacesInfos getNamespacesInfos();
	SqlMapClient getSqlMapClient();

	Connection getConnection();
	TablesUidProvider getTablesUidProvider();

	TableNames getTableNames();

}