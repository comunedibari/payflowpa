package it.nch.is.fo.core.interfaces;

import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.interfaces.FrontEndContext;

public interface CorporateserviceServiceLocal{
  
	public DTO getCorporateByCode(FrontEndContext fec, DTO dto);

	public DTO updateCorporate(FrontEndContext fec, DTO dto);

	public DTOCollection listCorporateByCriteria(FrontEndContext fec, DTO dto);

//	public DTOCollection getAllTipologieEnti(FrontEndContext fec, DTO dto);

	public DTOCollection listEntiByCriteria(FrontEndContext fec, DTO dto);

//	public DTOCollection listAllEnti(FrontEndContext fec);

	public DTO getEnteByIntestCode(FrontEndContext fec, DTO dto);

	public DTO updateFlagComunicazioni(FrontEndContext fec, DTO dto);
  
}