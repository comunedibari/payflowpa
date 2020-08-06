/**
 * Created on 10/mar/2009
 */
package it.nch.eb.xsqlcmd.commands.db;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;


/**
 * @author gdefacci
 */
public class BaseIbatisDao {

	private static final Logger log = ResourcesUtil.createLogger(BaseIbatisDao.class);

	private SqlMapClient sqlMapClient;

	public BaseIbatisDao(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

	public SqlMapClient getSqlMapClient() {
		return this.sqlMapClient;
	}

	public Object queryForObject(String stmName, Object obj) {
		logStatement(stmName, obj);
		try {
			return getSqlMapClient().queryForObject(stmName, obj);
		} catch (SQLException e) {
			throw new RuntimeException("error performing queryForObject :" + stmName + "\n object" + obj, e);
		}
	}

	private void logStatement(String stmName, Object obj) {
		if (stmName.equals(""))
		log.debug("perfoming [" + stmName + "]");
	}

	public List queryForList(String stmName, Object obj) {
		logStatement(stmName, obj);
		try {
			return getSqlMapClient().queryForList(stmName, obj);
		} catch (SQLException e) {
			throw new RuntimeException("error performing queryForObject :" + stmName + "\n object" + obj, e);
		}
	}

	public void insert(String stmName, Object obj) {
		logStatement(stmName, obj);
		try {
			getSqlMapClient().insert(stmName, obj);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error performing insert :" + stmName + "\n object" + obj, e);
		}
	}

	public void update(String stmName, Object obj) {
		logStatement(stmName, obj);
		try {
			getSqlMapClient().update(stmName, obj);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("errr performing update :" + stmName + "\n object" + obj, e);
		}
	}

	public void delete(String stmName, Object obj) {
		logStatement(stmName, obj);
		try {
			getSqlMapClient().delete(stmName, obj);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error performing delete :" + stmName + "\n object" + obj, e);
		}
	}

	public void commit() {
		try {
			sqlMapClient.getCurrentConnection().commit();
			sqlMapClient.commitTransaction();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
