/**
 * 26/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.BaseTablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.utils.ConnectionEffect;
import it.nch.eb.xsqlcmd.utils.DbHelper;
import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.eb.xsqlcmd.utils.SpringXml2SqlConfiguration;

import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.slf4j.Logger;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author gdefacci
 */
public class MysqlTestConfiguration extends SpringXml2SqlConfiguration implements PendenzeTestConfiguration {
	
	private Logger log = ResourcesUtil.createLogger(MysqlTestConfiguration.class);
	
	public MysqlTestConfiguration(Environment env) {
		super(env);
	}
	protected MysqlTestConfiguration(BeanFactory bf) {
		super(bf);
	}
	
	public DatabaseConnection create(Connection conn) {
		try {
			return new org.dbunit.ext.mysql.MySqlConnection(conn, this.getSchemaName());
		} catch (Exception e) {
			throw new RuntimeException("error wrapping dbunit connection", e);
		}
	}
	
	public String getSchemaName() {
		return "idp";
	}
	
	public static final String[] createDbScripts = {
		"sql/mysql/create_dbrpos.sql" ,
		"sql/mysql/create_ebm.sql"    ,
		"sql/mysql/create_enti.sql"   ,
	};
	
	public static final String[] dropDbScripts = {
		"sql/mysql/drop_dbrpos.sql"   ,
		"sql/mysql/drop_ebm.sql"      ,
		"sql/mysql/drop_enti.sql"      ,
	};
	
	public static final String[] populateDbScripts = {
		"sql/mysql/insert_enti.sql"   ,
	};
	
	private TablesUidProvider uidProvider = newUidProvider();
	
	public TablesUidProvider getTablesUidProvider() {
		return uidProvider;
	}
	
	protected TablesUidProvider newUidProvider() {
		return new BaseTablesUidProvider( 
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceTimestampUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider() {

					public synchronized long nextVal(Connection conn) {
						long res = super.nextVal(conn);
						System.out.println("jltpend_next " + res);
						return res;
					}
					
				},
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider(),
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider() {
					public synchronized long nextVal(Connection conn) {
						long res = super.nextVal(conn);
						System.out.println("esiti ** : " + res);
						return res;
					}
				},
				new it.nch.eb.xsqlcmd.dao.MemorySequenceUIdProvider() {

					public synchronized long nextVal(Connection conn) {
						long res = super.nextVal(conn);
						return res;
					}
					
				});
	}
	
	static String join(String[] str, String sep) {
		StringBuffer sb = new StringBuffer();
		if (str.length > 0) sb.append(str[0]);
		for (int i = 1; i < str.length; i++) {
			sb.append(sep);
			sb.append(str[i]);
		}
		return sb.toString();
	}
	
	public synchronized void initializeDB() {
		final DbHelper dbHelper = new DbHelper(this);
		this.uidProvider = newUidProvider();
		dbHelper.withConnection(new ConnectionEffect() {

			public void apply(Connection conn) throws Exception {
				DbHelper.executeClasspathScripts(conn, dropDbScripts, true);
				log.info("executed :\n" + join(dropDbScripts, ", "));
				DbHelper.executeClasspathScripts(conn, createDbScripts, true);
				log.info("executed :\n" + join(createDbScripts, ", "));
				DbHelper.executeClasspathScripts(conn, populateDbScripts, true);
				log.info("executed :\n" + join(populateDbScripts, ", "));
			}
			
		});
	}

}
