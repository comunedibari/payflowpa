package it.nch.is.fo.core.interfaces;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import java.rmi.RemoteException;

public interface OperatorserviceService {

	public DTO checkOperatorByUsernameAndCorporate(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO getOperatorByCode(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTOCollection deleteOperator(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO insertAndUpdateOperator(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO insertAndUpdateOperatorFunctions(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTOCollection listFunctionsByCorporate(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTOCollection listOperatorsByCriteria(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO changePwd(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTOCollection listOperatorsByCorporate(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO updateOperator(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO getOperatorByCodiceFiscaleInternal(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO assignNewOperator(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO deAssignNewOperator(FrontEndContext fec, DTO dto) throws RemoteException;

}