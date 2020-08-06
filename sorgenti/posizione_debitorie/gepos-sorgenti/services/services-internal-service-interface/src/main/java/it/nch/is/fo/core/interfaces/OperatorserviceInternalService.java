package it.nch.is.fo.core.interfaces;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.interfaces.FrontEndContext;

public interface OperatorserviceInternalService {

//	public DTO getOperatorByLoginInfo(FrontEndContext fec, DTO dto);

//	public DTO getOperatorByCodiceFiscale(FrontEndContext fec, DTO dto);

	public DTO getOperatorByCode(FrontEndContext fec, DTO dto);

	public DTO insertOrUpdateOperatorSkipControl(FrontEndContext fec, DTO dto);

	public DTOCollection listFunctionsByCorporate(FrontEndContext fec, DTO dto);

	public DTOCollection listOperatorsByCorporate(FrontEndContext fec, DTO dto);

	public DTO updateOperator(FrontEndContext fec, DTO dto);

}