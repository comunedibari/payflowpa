/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class UnreachableBollettinoFrecciaException extends I18NException {
	

	public UnreachableBollettinoFrecciaException(String providerURL) {
		
		this.errorCode = EnumBusinessErrorCodes.APPEXC_FRECCIA_KO;
	}
	
	public String getMessage(){
		
		return "Servizio al momento non disponibile, riprovare piu' tardi";
	}

}
