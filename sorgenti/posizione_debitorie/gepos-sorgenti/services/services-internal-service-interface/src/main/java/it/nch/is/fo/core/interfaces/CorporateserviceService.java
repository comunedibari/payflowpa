package it.nch.is.fo.core.interfaces;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.interfaces.FrontEndContext;

import java.rmi.RemoteException;

public interface CorporateserviceService {

	public DTO getCorporateByCode(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO updateCorporate(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTOCollection listCorporateByCriteria(FrontEndContext fec, DTO dto) throws RemoteException;

//	public DTOCollection getAllTipologieEnti(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTOCollection listEntiByCriteria(FrontEndContext fec, DTO dto) throws RemoteException;

//	public DTOCollection listAllEnti(FrontEndContext fec) throws RemoteException;

	public DTO getEnteByIntestCode(FrontEndContext fec, DTO dto) throws RemoteException;

	public DTO updateFlagComunicazioni(FrontEndContext fec, DTO dto) throws RemoteException;

}