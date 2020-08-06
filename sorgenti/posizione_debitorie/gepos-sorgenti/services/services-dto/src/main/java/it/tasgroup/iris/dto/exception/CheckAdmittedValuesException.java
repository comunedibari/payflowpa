/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class CheckAdmittedValuesException extends CheckCSVException {
	

	public CheckAdmittedValuesException(Object[] parameters) {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_INVALID_CSV;
		
		this.i18NMessageParameters = parameters;
	}

}
