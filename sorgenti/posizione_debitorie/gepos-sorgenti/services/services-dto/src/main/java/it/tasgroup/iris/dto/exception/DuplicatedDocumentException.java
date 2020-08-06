/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

import java.util.List;

/**
 * @author PazziK
 *
 */
public class DuplicatedDocumentException extends BusinessConstraintException {
	

	public DuplicatedDocumentException(List<String> idCondizioniList) {
		
		this.errorCode = EnumBusinessErrorCodes.APPEXC_DOUBLE_DDP;
		
		this.i18NMessageParameters = idCondizioniList.toArray();
	}
	
	public String getMessage(){
		
		return "Esiste gia' un documento di pagamento non annullato per le medesime condizioni" + this.i18NMessageParameters;
	}

}
