package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;
import it.tasgroup.iris.shared.util.enumeration.EnumLayer;

public class BusinessConstraintException extends I18NException{

	private static final long serialVersionUID = 1L;

	protected BusinessConstraintException() {
		
	}

	public BusinessConstraintException(EnumBusinessErrorCodes errorCode, Object[] parameters, String message) {
		
		super(errorCode, parameters, EnumLayer.EJB, message, null);
		
	}

}
