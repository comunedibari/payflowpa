/**
 * 
 */
package it.tasgroup.iris.dto.exception;

import java.util.List;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;


public class PaymentBusinessException extends BusinessConstraintException {
	
	
	public PaymentBusinessException(List<String> idCondizioniList) {
		
		this.errorCode = EnumBusinessErrorCodes.APPEXC_INVALID_DDP;
		
		this.i18NMessageParameters = idCondizioniList.toArray();
	}
	
	public String getMessage(){
		return "Si e' verificato un errore nel processo di pagamento.";
	}

}
