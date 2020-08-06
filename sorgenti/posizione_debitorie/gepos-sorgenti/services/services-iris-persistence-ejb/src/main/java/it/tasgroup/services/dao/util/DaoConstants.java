/**
 * 
 */
package it.tasgroup.services.dao.util;

/**
 * @author FolliA
 *
 */
public interface DaoConstants {
	
	/**
	 * Hibernate ScrollMode 
	 */
	String SCROLLMODE_FORWARD_ONLY = "FW";
	String SCROLLMODE_SCROLL_INSENSITIVE = "SI";
	String SCROLLMODE_SCROLL_SENSITIVE = "SS";
	
	/**
	 * Transaction Handling Actions
	 */
	String COMMIT = "C";
	String ROLLBACK = "R";
	String NO_ACTION = "NA";
	
	/**
	 * Marker Operations
	 */
	String CREATE = "C";
	String UPDATE = "U";
	String DELETE = "D";
		
}
