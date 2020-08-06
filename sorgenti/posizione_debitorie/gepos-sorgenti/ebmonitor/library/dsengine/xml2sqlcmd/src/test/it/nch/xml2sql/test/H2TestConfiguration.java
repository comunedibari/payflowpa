/**
 * 26/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.ConnectionEffect;
import it.nch.eb.xsqlcmd.utils.DbHelper;
import it.nch.eb.xsqlcmd.utils.Environment;
import it.nch.eb.xsqlcmd.utils.SpringXml2SqlConfiguration;

import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;
import org.springframework.beans.factory.BeanFactory;

/**
 * @author gdefacci
 */
public class H2TestConfiguration extends SpringXml2SqlConfiguration implements PendenzeTestConfiguration {
	
	protected H2TestConfiguration(BeanFactory bf) {
		super(bf);
	}

	public H2TestConfiguration(Environment env) {
		super(env);
	}

	public DatabaseConnection create(Connection conn) {
		try {
			return new org.dbunit.ext.h2.H2Connection(conn, this.getSchemaName());
		} catch (Exception e) {
			throw new RuntimeException("error wrapping dbunit connection", e);
		}
	}
	
	public String getSchemaName() {
		return null;
	}
	
	public static final String[] createDbScripts = {
		"sql/h2/create_dbrpos.sql" ,
		"sql/h2/create_ebm.sql"    ,
		"sql/h2/create_enti.sql"   ,
		"sql/h2/create-seqs.sql"   ,
	};
	
	public static final String[] dropDbScripts = {
		"sql/h2/drop_dbrpos.sql"   ,
		"sql/h2/drop_ebm.sql"      ,
		"sql/h2/drop_enti.sql"      ,
		"sql/h2/drop_seqs.sql"     ,
	};
	
	public static final String[] populateDbScripts = {
		"sql/h2/insert_enti.sql"   ,
	};
	
	public void initializeDB() {
		final DbHelper dbHelper = new DbHelper(this);
		dbHelper.withConnection(new ConnectionEffect() {

			public void apply(Connection conn) throws Exception {
				DbHelper.executeClasspathScripts(conn, dropDbScripts, false);
				DbHelper.executeClasspathScripts(conn, createDbScripts, false);
				DbHelper.executeClasspathScripts(conn, populateDbScripts, false);
			}
			
		});
	}

}
