/**
 * 26/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.Xml2SqlAppConfiguration;

import java.sql.Connection;

import org.dbunit.database.DatabaseConnection;


/**
 * @author gdefacci
 */
public interface PendenzeTestConfiguration extends Xml2SqlAppConfiguration {

	DatabaseConnection create(Connection conn) throws Exception;
	/** drop db content, create tables, indexes, seqs, populate anagraphic tables */
	void initializeDB();
	String getSchemaName();
	
}