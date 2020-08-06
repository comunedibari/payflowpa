/**
 * 04/ago/2009
 */
package it.nch.xml2sql.test;

/**
 * @author gdefacci
 */
public interface  DBTestsRetriever {
	
	public static final String ERRORS_PROPERTIES = "errors.properties";		
	public static final String INITIAL_DATASET = "initial-dataset.xml";
	public static final String EXPECTED_DATASET = "expected-dataset.xml";

	FullDbTest[] retrieveTests(String folder);
}
