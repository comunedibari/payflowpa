/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;


/**
 * @author PazziK
 *
 */
public class CheckSospettoGRException extends BusinessConstraintException {
	
	
	public CheckSospettoGRException(Object[] parameters) {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_SOSPETTO_GR;
		
		this.i18NMessageParameters = parameters;
	}
	
	public String getMessage(){
		
		return "Selezione multipla contenente un movimento con anomalia SOSPETTO_GR.";
	}

}
