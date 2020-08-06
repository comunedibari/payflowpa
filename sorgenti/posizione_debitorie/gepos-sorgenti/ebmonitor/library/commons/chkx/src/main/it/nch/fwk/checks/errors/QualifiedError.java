/**
 * Created on 05/apr/07
 */
package it.nch.fwk.checks.errors;



/**
 * error identifier
 * @author gdefacci
 */
public interface QualifiedError extends Comparable {
	
	int 		UNQUALIFIED_ERROR_TYPE = 0;	
	/**
	 * const used to query a ErrorCollectorQuery, whch will match any error type
	 */
	int 		ERROR_TYPE_ANY = -2;
	
	String 		NO_ERROR_MESSAGE = "NO ERROR";
	int 		NO_ERROR_TYPE = -1;
	
	static QualifiedError NO_ERROR = new QualifiedError()  {
		public String getErrorId() {
			return NO_ERROR_MESSAGE;
		}
		public int getErrorType() {
			return NO_ERROR_TYPE;
		}
		public int compareTo(Object o) {
			return -1;
		}
	};
	
	String getErrorId();
	int getErrorType();

}
