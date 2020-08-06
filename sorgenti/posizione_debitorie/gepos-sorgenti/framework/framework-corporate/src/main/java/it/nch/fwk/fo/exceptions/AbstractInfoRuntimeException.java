package it.nch.fwk.fo.exceptions;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.dto.DTOImpl;
import it.nch.fwk.fo.dto.DTOInfo;
import it.nch.fwk.fo.dto.DTOInfoList;

public abstract class AbstractInfoRuntimeException extends RuntimeException {
	
	private DTOInfoList  dtoInfoList = null;
	private Throwable nestedException = null;


	public AbstractInfoRuntimeException() {
	}

	public AbstractInfoRuntimeException(DTOInfoList dtoInfoList) {
		super();
		this.dtoInfoList = dtoInfoList;
	}

	public AbstractInfoRuntimeException( Throwable nestedException) {
		super();
		this.nestedException = (Exception) nestedException;
	}

	
	public AbstractInfoRuntimeException( Throwable nestedException,DTOInfoList dtoInfoList) {
		super();
		this.nestedException = (Exception) nestedException;
		this.dtoInfoList = dtoInfoList;
	}

	public AbstractInfoRuntimeException(DTOInfo dtoInfo) {
		super();
		if (this.dtoInfoList == null){
			this.dtoInfoList = new DTOInfoList();
		}
		this.dtoInfoList.addInfo(dtoInfo);

	}

	public AbstractInfoRuntimeException(Throwable nestedException,DTOInfo dtoInfo) {
		super();
		this.nestedException = (Exception) nestedException;
		if (this.dtoInfoList == null){
			this.dtoInfoList = new DTOInfoList();
		}
		this.dtoInfoList.addInfo(dtoInfo);
	}

	public DTOInfoList getDtoInfoList() {
		return this.dtoInfoList;
	}

	public DTO getDTO() {
		DTO dtoInfo = new DTOImpl();
		dtoInfo.setInfoList(this.dtoInfoList);
		return dtoInfo;
	}

	public DTOCollection getDTOCollection() {
		DTOCollection dtoCollection = new DTOCollectionImpl();
		dtoCollection.setInfoList(this.dtoInfoList);
		return dtoCollection;
	}

	public Throwable getNestedException() {
		return nestedException;
	}

}
