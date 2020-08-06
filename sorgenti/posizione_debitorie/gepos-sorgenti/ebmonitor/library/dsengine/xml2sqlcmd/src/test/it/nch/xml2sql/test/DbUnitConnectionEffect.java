/**
 * 27/mag/2009
 */
package it.nch.xml2sql.test;

import org.dbunit.database.IDatabaseConnection;

/**
 * @author gdefacci
 */
public interface DbUnitConnectionEffect {
	void apply(IDatabaseConnection conn) throws Exception;
}
