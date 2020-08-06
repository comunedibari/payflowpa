/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

/**
 * @author PazziK
 *
 */
public class UnavailableDDPBonException extends BusinessConstraintException {
	
	private static final long serialVersionUID = 1L;

	public UnavailableDDPBonException() {		

		this.errorCode = EnumBusinessErrorCodes.APPEXC_DDP_BON_UNIFORM;
	}
	
	public String getMessage(){
		
		return "Il pagamento mediante Bonifico con Codice Predefinito e' possibile solo per un carrello di condizioni uniformi per ente e tributo";
	}

}
