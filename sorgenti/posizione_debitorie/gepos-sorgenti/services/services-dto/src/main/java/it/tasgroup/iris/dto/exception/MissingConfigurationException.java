/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class MissingConfigurationException extends BusinessConstraintException {
	

	public MissingConfigurationException(Object[] parameters) {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_MISSING_CONFIGURATION;
		
		this.i18NMessageParameters = parameters;
	}

}
