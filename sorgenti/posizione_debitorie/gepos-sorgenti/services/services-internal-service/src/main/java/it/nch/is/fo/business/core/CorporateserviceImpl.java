package it.nch.is.fo.business.core;

import it.nch.fwk.fo.core.BackEndContext;
import it.nch.fwk.fo.cross.AbstractJavaBean;
import it.nch.fwk.fo.cross.exception.BusinessImplRuntimeException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.BackEndConstant;
import it.nch.is.fo.business.core.dao.CorporateserviceDaoDB;
import it.nch.is.fo.core.interfaces.CorporateserviceInternalService;
import it.nch.is.fo.core.interfaces.CorporateserviceServiceLocal;

public class CorporateserviceImpl extends AbstractJavaBean implements
		CorporateserviceServiceLocal, CorporateserviceInternalService {

	CorporateserviceDaoDB daoDB = null;

	public CorporateserviceImpl(CorporateserviceDaoDB daoDB) {
		this.daoDB = daoDB;
	}

	public DTO getCorporateByCode(FrontEndContext fec, DTO dto) {
		try {
			return daoDB.getCorporateByCode((BackEndContext) fec, dto);
		} catch (Exception e) {
			Tracer.debug(this.getClass().getName(), "getCorporateByCode", e
					.getMessage(), e);
			processException(dto, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
			return dto;
		}
	}
	
	/**
	 * 
	 */
	public DTO getEnteByIntestCode(FrontEndContext fec, DTO dto) {
		try {
			return daoDB.getEnteByIntestCode((BackEndContext) fec, dto);
		} catch (Exception e) {
			Tracer.debug(this.getClass().getName(), "getEnteByIntestCode", e
					.getMessage(), e);
			processException(dto, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
			return dto;
		}
	}	

	public DTO updateCorporate(FrontEndContext fec, DTO dto) {

		try {
			return daoDB.updateCorporate((BackEndContext) fec, dto);
		} catch (Exception e) {
			Tracer.debug(this.getClass().getName(), "updateCorporate", e.getMessage(), e);
			processException(dto, e, BackEndConstant.UPDATE_ERROR_MESSAGE);
			throw new BusinessImplRuntimeException(dto.getInfoList());
			//return dto;
		}
	}

	public DTO test(FrontEndContext fec, DTO dto) {
		return null;
	}

	public DTOCollection listCorporateByCriteria(FrontEndContext fec, DTO dto) {
		DTOCollection coll = new DTOCollectionImpl(null, false);
		try {
			coll = daoDB.listCorporateByCriteria((BackEndContext) fec, dto);
			return coll;
		} catch (Exception e) {
			e.printStackTrace();
			processException(coll, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
			return coll;
		}
	}

//	public DTOCollection getAllTipologieEnti(FrontEndContext fec, DTO dto) {
//		DTOCollection coll = new DTOCollectionImpl(null, false);
//		try {
//			coll = daoDB.getAllTipologieEnti((BackEndContext) fec, dto);
//			return coll;
//		} catch (Exception e) {
//			Tracer.debug(this.getClass().getName(), "getAllTipologieEnti", e
//					.getMessage(), e);
//			processException(dto, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//			return coll;
//		}
//	}
	
	public DTOCollection listEntiByCriteria(FrontEndContext fec, DTO dto) {
		DTOCollection coll = new DTOCollectionImpl(null, false);
		try {
			coll = daoDB.listEntiByCriteria((BackEndContext) fec, dto);
			return coll;
		} catch (Exception e) {
			processException(coll, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
			return coll;
		}
	}
	
//	/**
//	 * 
//	 * @param fec
//	 * @return
//	 */
//	public DTOCollection listAllEnti(FrontEndContext fec) {
//		DTOCollection coll = new DTOCollectionImpl(null, false);
//		try {
//			coll = daoDB.listAllEnti((BackEndContext) fec);
//			return coll;
//		} catch (Exception e) {
//			processException(coll, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//			return coll;
//		}
//	}
	
//	/**
//	 * listAllSubjectsByOperator.
//	 * @param FrontEndContext fec, DTO dto.
//	 * @return DTOCollection of Subjects.
//	 */
//	public DTOCollection listAllSubjectsByOperator(FrontEndContext fec, DTO dto) {
//		DTOCollection coll = new DTOCollectionImpl(null, false);
//		try {
//			coll = daoDB.listAllSubjectsByOperator((BackEndContext) fec, dto);
//			return coll;
//		} catch (Exception e) {
//			processException(coll, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//			return coll;
//		}
//	}

//	@Override
//	public DTO getCorporateByID(FrontEndContext fec, DTO dto) {
//		try {
//			return daoDB.getCorporateByID((BackEndContext) fec, dto);
//		} catch (Exception e) {
//			Tracer.debug(this.getClass().getName(), "getCorporateByID", e
//					.getMessage(), e);
//			processException(dto, e, BackEndConstant.SEARCH_ERROR_MESSAGE);
//			return dto;
//		}
//	}

	@Override
	public DTO updateFlagComunicazioni(FrontEndContext fec, DTO dto) {
		try {
			daoDB.updateFlagComunicazioni((BackEndContext) fec, dto);
			return dto;
		} catch (Exception e) {
			Tracer.debug(this.getClass().getName(), "updateCorporate", e.getMessage(), e);
			processException(dto, e, BackEndConstant.UPDATE_ERROR_MESSAGE);
			return dto;
		}
	}	

}