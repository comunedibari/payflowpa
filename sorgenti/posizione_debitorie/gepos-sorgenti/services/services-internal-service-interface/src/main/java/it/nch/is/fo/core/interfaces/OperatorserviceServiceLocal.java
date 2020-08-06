package it.nch.is.fo.core.interfaces;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.interfaces.FrontEndContext;

public interface OperatorserviceServiceLocal {

	public DTO checkOperatorByUsernameAndCorporate(FrontEndContext fec, DTO dto);

	public DTO getOperatorByCode(FrontEndContext fec, DTO dto);

	public DTOCollection deleteOperator(FrontEndContext fec, DTO dto);

	public DTO insertAndUpdateOperator(FrontEndContext fec, DTO dto);

	public DTO insertAndUpdateOperatorFunctions(FrontEndContext fec, DTO dto);

	public DTOCollection listFunctionsByCorporate(FrontEndContext fec, DTO dto);

	public DTOCollection listOperatorsByCriteria(FrontEndContext fec, DTO dto);

	public DTO changePwd(FrontEndContext fec, DTO dto);

	public DTOCollection listOperatorsByCorporate(FrontEndContext fec, DTO dto);

	public DTO updateOperator(FrontEndContext fec, DTO dto);

	public DTO getOperatorByCodiceFiscaleInternal(FrontEndContext fec, DTO dto);

	public DTO assignNewOperator(FrontEndContext fec, DTO dto);

	public DTO deAssignNewOperator(FrontEndContext fec, DTO dto);

}