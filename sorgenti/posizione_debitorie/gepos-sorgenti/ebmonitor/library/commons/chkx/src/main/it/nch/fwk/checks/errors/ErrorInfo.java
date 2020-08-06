/**
 * Created on 05/apr/07
 */
package it.nch.fwk.checks.errors;



/**
 * generic error info: not element coupled
 * @author gdefacci
 */
public interface ErrorInfo extends QualifiedError {
	
	public static final String	NO_EXPECETED_VALUE	= "NO_EXPECTED_VALUE";
//	public static final String	NO_PATH_SPECIFIED	= "NO_PATH_SPECIFIED";

	public static class Severity {
		public static String FATAL 			= "FATAL";
		public static String ERROR 			= "ERROR";
		public static String WARNING 		= "WARNING";
		public static String INFO 			= "INFO";
		public static String DEFAULT 		= ERROR;
	}

	String getMessage();
	
	String getExpectedValue();
	String getSeverity();		
	
	void setMessage(String msg);
	
	ErrorInfo create(QualifiedError qError);

}
