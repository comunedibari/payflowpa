package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

public class IncompleteRegistrationException extends BusinessConstraintException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336274347701749865L;

	
	public IncompleteRegistrationException(String nome, String valore) {
		
		Object[] i18nMessageParameters = {nome, valore};
		
		this.errorCode = EnumBusinessErrorCodes.APPEXC_BAD_REGISTRY;
		
		this.i18NMessageParameters = i18nMessageParameters;
	}
	
	public String getMessage(){
		
		return errorCode.getDescrizione()+ " CAMPO:" + this.i18NMessageParameters[0] + " VALORE:"+ this.i18NMessageParameters[1];
	}
}
