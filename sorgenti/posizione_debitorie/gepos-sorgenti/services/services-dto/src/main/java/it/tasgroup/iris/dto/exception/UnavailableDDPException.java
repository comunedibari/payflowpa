package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

public class UnavailableDDPException extends BusinessConstraintException {

	public UnavailableDDPException() {
		this.errorCode = EnumBusinessErrorCodes.APPEXC_DDP_PAGAMENTO_NON_ESEGUITO;
	}

	public String getMessage() {
		return errorCode.getDescrizione();
	}

}
