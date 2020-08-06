package it.nch.fwk.fo.exceptions;

import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoList;

public abstract class AbstractInfoException extends Exception {

	private DTOInfoList  dtoInfoList = null;
	private Throwable nestedException;

	public AbstractInfoException(DTOInfoList dtoInfoList) {
		super();
		this.dtoInfoList = dtoInfoList;
	}

	public AbstractInfoException(Throwable nestedException,DTOInfoList dtoInfoList) {
		super();
		this.nestedException = (Exception) nestedException;
		this.dtoInfoList = dtoInfoList;
	}

	public AbstractInfoException(DTOInfo dtoInfo) {
		super();
		if (this.dtoInfoList == null){
			this.dtoInfoList = new DTOInfoList();
		}
		this.dtoInfoList.addInfo(dtoInfo);

	}

	public AbstractInfoException(Throwable nestedException,DTOInfo dtoInfo) {
		super();
		this.nestedException = (Exception) nestedException;
		if (this.dtoInfoList == null){
			this.dtoInfoList = new DTOInfoList();
		}
		this.dtoInfoList.addInfo(dtoInfo);
	}

	public DTOInfoList getDtoInfoList() {
		return dtoInfoList;
	}

	public Throwable getNestedException() {
		return nestedException;
	}
	
}
