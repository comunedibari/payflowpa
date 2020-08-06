/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class MalformedCSVException extends CheckCSVException {
	

	public MalformedCSVException(Object[] parameters) {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_MALFORMED_CSV;
		
		this.i18NMessageParameters = parameters;
	}

}
