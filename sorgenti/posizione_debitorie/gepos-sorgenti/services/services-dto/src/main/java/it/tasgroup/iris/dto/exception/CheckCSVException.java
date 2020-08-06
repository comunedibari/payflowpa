/**
 * 
 */
package it.tasgroup.iris.dto.exception;


/**
 * @author PazziK
 *
 */
public abstract class CheckCSVException extends BusinessConstraintException {
	

	public CheckCSVException() {		

	}
	
	public String getMessage(){
		
		return "Fallimento dei controlli di idoneita' eseguiti sul file CSV.";
	}

}
