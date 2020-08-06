package it.nch.fwk.fo.exceptions;

import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoList;

public class BusinessDelegateRuntimeException extends AbstractInfoRuntimeException {
	
	public BusinessDelegateRuntimeException(DTOInfoList dtoInfoList) {
		super(dtoInfoList);
	}

	public BusinessDelegateRuntimeException(Throwable nestedException,DTOInfoList dtoInfoList) {
		super(nestedException,dtoInfoList);
	}

	public BusinessDelegateRuntimeException(DTOInfo dtoInfo) {
		super(dtoInfo);
	}

	public BusinessDelegateRuntimeException(Throwable nestedException,DTOInfo dtoInfo) {
		super(nestedException,dtoInfo);
	}


}
