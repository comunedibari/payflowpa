/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class SecurityException extends BusinessConstraintException {
	

	public SecurityException(String messageParameter) {
		
		Object[] i18nMessageParameters = {messageParameter};
		
		this.errorCode = EnumBusinessErrorCodes.APPEXC_SECURITY;
		this.i18NMessageParameters = i18nMessageParameters;
	}
	
	public String getMessage(){
		
		return errorCode.getDescrizione() + this.i18NMessageParameters[0];
	}

}
