package it.nch.fwk.fo.cross.exception;

import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.exceptions.AbstractInfoRuntimeException;

public class DAORuntimeException extends AbstractInfoRuntimeException {

	
	public DAORuntimeException() {
		super();
	}
	public DAORuntimeException(Throwable nestedException) {
		super(nestedException);
	}
	
	public DAORuntimeException(DTOInfoList dtoInfoList) {
		super(dtoInfoList);
	}

	public DAORuntimeException(Throwable nestedException,DTOInfoList dtoInfoList) {
		super(nestedException,dtoInfoList);
	}

	public DAORuntimeException(DTOInfo dtoInfo) {
		super(dtoInfo);
	}

	public DAORuntimeException(Throwable nestedException,DTOInfo dtoInfo) {
		super(nestedException,dtoInfo);
	}

}
