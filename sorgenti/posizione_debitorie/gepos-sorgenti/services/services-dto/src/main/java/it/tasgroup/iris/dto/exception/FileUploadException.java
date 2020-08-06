/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class FileUploadException extends BusinessConstraintException {
	

	public FileUploadException() {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_FILE_UPLOAD;
	}
	
	public String getMessage(){
		
		return "Problemi durante l'upload di file.";
	}

}
