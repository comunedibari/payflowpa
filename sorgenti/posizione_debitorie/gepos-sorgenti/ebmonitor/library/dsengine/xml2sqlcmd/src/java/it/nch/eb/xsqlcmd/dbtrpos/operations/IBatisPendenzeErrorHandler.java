/**
 * 23/ott/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.operations;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public interface IBatisPendenzeErrorHandler extends DBErrorsHandler {

	public SqlMapClient getSqlMapClient();
}
