/**
 * 30/lug/2009
 */
package it.tasgroup.dse.dao;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;


/**
 * @author gdefacci
 */
public class StorageDAO {
	
	private static final Logger log = ResourcesUtil.createLogger(StorageDAO.class);
	
	private final DataSource datasource;

	public StorageDAO(DataSource datasource) {
		this.datasource = datasource;
	}
	
	public InputStream getInputStreamBlob(String _tableName, String _keyValue[], String _keyName[], String _fieldName) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		log.debug("StorageDAO.getInputStreamBlob[INIT]");
		InputStream out = null;
		try {
			con = datasource.getConnection();
			StringBuffer buffer_sql_fragment= new StringBuffer();
			StringBuffer buffer_log_fragment= new StringBuffer();
			for (int i=0;i<_keyValue.length;i++){
				buffer_sql_fragment.append(_keyName[i]);
				buffer_log_fragment.append(_keyName[i]);
				buffer_sql_fragment.append(" = ? AND ");
				buffer_log_fragment.append(" = ");
				buffer_log_fragment.append(_keyValue[i]);
				buffer_log_fragment.append(" AND ");
			}
			buffer_sql_fragment.append(" 1 = 1 ");
			buffer_log_fragment.append(" 1 = 1 ");
			
			ps = con.prepareStatement("SELECT " + _fieldName + " FROM " + _tableName + " WHERE " + buffer_sql_fragment);
		
			log.info("StorageDAO.getInputStreamBlob() - SELECT " + _fieldName + " FROM " + _tableName + " WHERE " + buffer_log_fragment);
			for (int i=0;i<_keyValue.length;i++){
				ps.setString(1+i, _keyValue[i]);
			}
			rs = ps.executeQuery();
			if (rs.next()) {
				out = rs.getBinaryStream(1);
			} else {
			    throw new Exception("Nessun risultato sulla tabella " + _tableName + " per l'id relativo alla condizione: " + buffer_log_fragment);
			}
		} finally {
			closeResultSet(rs);
			closeStatement(ps);
			closeConnection(con);
		}
		log.debug("StorageDAO.getInputStreamBlob[END]");
		return out;
	}

	private void closeConnection(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (Exception e) {
			}
		}
	}

	private void closeStatement(PreparedStatement stm) {
		if (stm!=null) {
			try {
				stm.close();
			} catch (Exception e) {
			}
		}
		
	}

	private void closeResultSet(ResultSet rs) {
		if (rs!=null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}
		
	}

}
