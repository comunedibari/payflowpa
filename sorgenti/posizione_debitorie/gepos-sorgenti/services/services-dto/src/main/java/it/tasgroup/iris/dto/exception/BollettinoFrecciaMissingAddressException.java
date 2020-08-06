/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class BollettinoFrecciaMissingAddressException extends
		BusinessConstraintException {
	

	public BollettinoFrecciaMissingAddressException() {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_FRECCIA_INDIRIZZO_NOTFOUND;
	}
	
	public String getMessage(){
		
		return "Per poter utilizzare il servizio di gestione dei Bollettini Freccia e' necessario impostare l'indirizzo nei dati anagrafici! ";
	}

}
