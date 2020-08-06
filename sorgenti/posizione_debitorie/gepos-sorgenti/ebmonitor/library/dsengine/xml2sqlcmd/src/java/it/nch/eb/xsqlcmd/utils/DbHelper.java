/**
 * 15/mag/2009
 */
package it.nch.eb.xsqlcmd.utils;

import it.nch.eb.common.utils.ConnectionProvider;
import it.nch.eb.common.utils.loaders.ReaderLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * @author gdefacci
 */
public class DbHelper {
	
//	private static final Logger log = ResourcesUtil.createLogger(DbHelper.class);

	private ConnectionProvider connectionProvider;
	
	public DbHelper(ConnectionProvider connectionProvider ) {
		this.connectionProvider = connectionProvider;
	}
	
	public DbHelper(final DataSource ds) {
		this.connectionProvider = new ConnectionProvider() {

			public Connection getConnection() {
				try {
					return ds.getConnection();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
			
		};
	}
	
	public void withConnection(ConnectionEffect efct) {
		Connection conn =  null;
		try {
			conn = connectionProvider.getConnection();
			efct.apply(conn);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("error getting connection " , e); 
		} finally {
			closeConnection(conn);
		}
	}
	
	public void closeConnection(Connection conn) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new Error("error relasing connection ", e);
			}
		}
	}

	public static void executeClasspathScripts(Connection conn, String[] loc, boolean splitScript) {
		executeScripts(conn, loc, splitScript, ResourceLoaders.CLASSPATH);
	}
	
	public static void executeFilesystemScripts(Connection conn, String[] loc, boolean splitScript) {
		executeScripts(conn, loc, splitScript, ResourceLoaders.FILESYSTEM);
	}
	
	public static void executeScripts(Connection conn, String[] loc, boolean splitScript, ReaderLoader readerLoader) {
		final String[] content = loadScripts(loc, splitScript, readerLoader);
		executeSql(conn, content);
	}

	public static String[] loadScripts(String[] loc, boolean splitScript, ReaderLoader readerLoader) {
		
		if (loc == null) return null;
		if (loc.length == 0) return new String[0];
		
		final String[] content;
		if (!splitScript) {
			content = new String[loc.length];
			for (int i = 0; i < loc.length; i++) {
				String location = loc[i];
				content[i] = ResourcesUtil.asString(readerLoader.loadReader(location));
			}
		} else {
			final List/*<String>*/ lst = new ArrayList();
			for (int i = 0; i < loc.length; i++) {
				String location = loc[i];
				String fullScript = ResourcesUtil.asString(readerLoader.loadReader(location));;
 				String[] instrs = fullScript.split(";");
 				for (int j = 0; j < instrs.length; j++) {
					String instr = instrs[j].trim();
					if (instr!=null && instr.length()>0) lst.add(instr);
				}
			}
			content = ((String[]) lst.toArray(new String[0]));
			
		}
		return content;
	}

	public static void executeSql(Connection conn, final String[] content) {
		for (int i = 0; i < content.length; i++) {
			try {
				conn.createStatement().execute(content[i]);
				//log.debug("executed \n" + content[i]);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("error executing " + content[i], e);
			}
		}
	}

}
