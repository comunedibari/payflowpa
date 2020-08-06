package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

public class InvalidInputException extends BusinessConstraintException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336274347701749865L;

	public InvalidInputException(EnumBusinessErrorCodes er,Object[] params,String message) {
		super(er,params,message);
	}
}
