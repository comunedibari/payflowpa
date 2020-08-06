package it.tasgroup.iris.dto.exception;

import it.tasgroup.iris.shared.util.enumeration.EnumLayer;
import it.tasgroup.services.util.enumeration.EnumDAOErrorCodes;

public class DataConstraintException extends I18NException{

	public DataConstraintException(EnumDAOErrorCodes errorCode, Object[] parameters, String message) {
		
		super(errorCode, parameters, EnumLayer.DAO, message, null);
		
	}

}
