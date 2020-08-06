/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class FileSizeException extends BusinessConstraintException {
	

	public FileSizeException() {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_FILE_SIZE;
	}
	
	public FileSizeException(Object[] params) {
		
		super(EnumBusinessErrorCodes.APPEXC_FILE_SIZE, params, "Superato il massimo numero di righe consentito: "+params[0]);
	}
	
	public String getMessage(){
		
		return "Superato il massimo numero di righe consentito:" +getI18NMessageParameters()[0];
	}

}
