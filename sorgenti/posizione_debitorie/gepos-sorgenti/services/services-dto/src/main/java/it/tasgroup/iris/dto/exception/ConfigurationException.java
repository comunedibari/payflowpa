package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumBusinessErrorCodes;

public class ConfigurationException extends BusinessConstraintException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4336274347701749865L;

	public ConfigurationException(EnumBusinessErrorCodes er,Object[] params,String message) {
		super(er,params,message);
	}
}
