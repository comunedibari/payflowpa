/**
 * 04/ago/2009
 */
package it.nch.xml2sql.test;

/**
 * @author gdefacci
 */
public interface DBTestsFactory {

	public FullDbTest newDbTest(String initialDataSetLoc, 
			final String inputFileLoc, 
			String expctFileLoc);
}
