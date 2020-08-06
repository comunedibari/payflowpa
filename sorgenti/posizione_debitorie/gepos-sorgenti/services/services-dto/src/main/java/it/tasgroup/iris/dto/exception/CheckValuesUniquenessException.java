/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class CheckValuesUniquenessException extends CheckCSVException {
	

	public CheckValuesUniquenessException(Object[] parameters) {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_UNIQUE_COLUMN_VALUE_CSV;
		
		this.i18NMessageParameters = parameters;
	}

}
