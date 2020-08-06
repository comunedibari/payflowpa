package it.nch.fwk.fo.web.resources.messageresources;


import it.nch.fwk.fo.util.Tracer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.PropertyMessageResourcesFactory;

/**
 * Copyright 2005 Allen L. Fogleson
 * 
 * @author FOGLESONA
 * 
 * Note this is a fully implemented but hardly production ready version of
 * messageResources to grab the message from a DB. It relies on the sql being in
 * the form of SELECT value from TABLE where LOCALE_NAME_COLUMN = ? and
 * MESSAGE_KEY_COLUMN = ? you can change that "hard coded" order in the
 * getMessageFromDB() method.
 * 
 * You should use this by placing something like the following in the struts
 * config file
 * 
 * <message-resources
 * factory="org.homedns.afogleson.messageresources.DBMessageResourcesFactory"
 * parameter="java:comp/env/jdbc/myDS,SELECT value from Messages where locale=?
 * and messagekey=?,300000" null="false" />
 * 
 * Once again note that the ordering of a JNDI NAME to lookup a DataSource and
 * the SQL to get the message is "hard coded" in the parseConfig() method.
 * 
 * Sorry about no java doc comments. But it directly follows the
 * MessageResources class from org.apache.struts.util.MessageResources
 * 
 * This code is released under the GPL license version 2.
 * 
 * The GPL License can be viewed at: http://www.gnu.org/copyleft/gpl.html
 * 
 */
public class DBMessageResources extends BaseReloadableMessageResource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6354312211356186415L;

	private static Log log = LogFactory.getLog(DBMessageResources.class);

	private static String MSG_SQL = "SELECT messagekey, value FROM MESSAGES WHERE locale=?";

	private static String DS_JNDI_NAME ="jdbc/ioli2web"; // "java:/Messages";

	private static String DEFAULT_LOCALE = "";

	public DBMessageResources(PropertyMessageResourcesFactory factory,
			String config) throws MessageResourcesException {
		this(factory, config, false);
	}

	public DBMessageResources(PropertyMessageResourcesFactory factory,
			String config, boolean isNull) throws MessageResourcesException {
		super(factory, config, isNull);
		Tracer.info("DBMessageResources","costruttore","config=" + config,null);
		
		parseConfig(config);
	}

	protected Properties getMessagesAsProperties(Locale locale) {

		if (log.isTraceEnabled()) {
			log.trace("Getting properties from database");
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Properties props = new Properties();

		try {
			con = getConnection();
			String localeString = null;

			if (locale == null) {
				locale = Locale.getDefault();
			}

			localeString = locale.toString();
			ps = con.prepareStatement(MSG_SQL);
			ps.setString(1, localeString);
			rs = ps.executeQuery();

			if (rs.isBeforeFirst() ) {
				while (rs.next()) {
					String key = rs.getString(1);
					String val = rs.getString(2);
					props.setProperty(key, val);
				}
			} else {
				ps.setString(1, DEFAULT_LOCALE);
				rs = ps.executeQuery();

				while (rs.next()) {
					String key = rs.getString(1);
					String val = rs.getString(2);
					props.setProperty(key, val);
				}
			}
		} catch (Exception e) {
			props = new Properties();
		} finally {
			try {
				rs.close();
			} catch (Exception ignoreMe) {
			}

			try {
				ps.close();
			} catch (Exception ignoreMe) {
			}

			try {
				con.close();
			} catch (Exception ignoreMe) {
			}
		}

		return props;
	}

	protected void parseConfig(String config) throws MessageResourcesException {

		StringTokenizer st = new StringTokenizer(config, "|");

		if (st.countTokens() != 2) {
			throw new MessageResourcesException(
					"Cannot configure DBMessageResources config is not correct must be 2 comma separated values depicting the JNDI name and the timeout.");
		}

		DEFAULT_LOCALE = st.nextToken();
		timeout = Long.valueOf(st.nextToken()).longValue();
	}

	private Connection getConnection() throws Exception {
		InitialContext ctx = new InitialContext();
		Tracer.debug("DBMessageResources","getConnection","ctx=" + ctx,null);
		
		DataSource ds = (DataSource) PortableRemoteObject.narrow(ctx
				.lookup(DS_JNDI_NAME), DataSource.class);
		Tracer.debug("DBMessageResources","getConnection","ds=" + ds,null);
		
		return ds.getConnection();
	}
}