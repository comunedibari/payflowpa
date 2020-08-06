package it.nch.fwk.fo.cross.exception;

import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.exceptions.AbstractInfoRuntimeException;

public class BusinessImplRuntimeException extends AbstractInfoRuntimeException {

	
	public BusinessImplRuntimeException(DTOInfoList dtoInfoList) {
		super(dtoInfoList);
	}

	public BusinessImplRuntimeException(Throwable nestedException,DTOInfoList dtoInfoList) {
		super(nestedException,dtoInfoList);
	}

	public BusinessImplRuntimeException(DTOInfo dtoInfo) {
		super(dtoInfo);
	}

	public BusinessImplRuntimeException(Throwable nestedException,DTOInfo dtoInfo) {
		super(nestedException,dtoInfo);
	}
}
