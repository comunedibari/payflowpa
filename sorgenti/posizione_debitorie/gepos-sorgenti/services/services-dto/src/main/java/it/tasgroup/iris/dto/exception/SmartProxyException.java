/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class SmartProxyException extends BusinessConstraintException {
	

	public SmartProxyException() {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_SMART_PROXY;
	}
	
	public String getMessage(){
		
		return "Problemi durante l'interrogazione dello Smart Proxy.";
	}

}
