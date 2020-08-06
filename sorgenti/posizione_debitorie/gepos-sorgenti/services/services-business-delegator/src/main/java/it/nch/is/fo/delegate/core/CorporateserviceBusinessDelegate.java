package it.nch.is.fo.delegate.core;

import it.nch.erbweb.client.FrontEndMessage;
import it.nch.fwk.fo.cross.AbstractBusinessDelegate;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOInfoInterface;
import it.nch.fwk.fo.dto.DTOInfoList;
import it.nch.fwk.fo.exceptions.BusinessDelegateRuntimeException;
import it.nch.fwk.fo.exceptions.ManageFrontEndException;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.core.interfaces.CorporateserviceServiceLocal;
import it.tasgroup.iris.shared.util.locator.ServiceLocator;

@SuppressWarnings("rawtypes")
public class CorporateserviceBusinessDelegate extends AbstractBusinessDelegate implements CorporateserviceServiceLocal {

   private CorporateserviceServiceLocal corporateserviceService;
  
  public CorporateserviceBusinessDelegate() throws ServiceLocatorException{
  
      Tracer.info(this.getClass().toString(),"Costruttore","Inizio ",null);
  	try {
 
  		corporateserviceService = (CorporateserviceServiceLocal)ServiceLocator.getSLSBProxy("corporateserviceBean");
  	
  	} catch (it.tasgroup.iris.shared.util.locator.ServiceLocatorException e) {
		e.printStackTrace();
		throw new ServiceLocatorException("Nessun Servizio Corporateservice Configurato Correttamente",e);
	}

  
  }
  
  
  public DTO getCorporateByCode(FrontEndContext fec,DTO dto) {
	  
      DTO dtoReturn = null;
      
	    try {
	    	
		   Tracer.info(this.getClass().toString(),"getCorporateByCode","esecuzione CorporateserviceService",null);
		   
		   dtoReturn = corporateserviceService.getCorporateByCode(fec, dto);
		   
	    } catch (Throwable re) {
	    	
		       Tracer.error(this.getClass().toString(),"getCorporateByCode","errore durante l'esecuzione CorporateserviceService",re);
              dtoReturn =  ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
	    
	    }
	    
     DTOInfoList infoList = dtoReturn.getInfoList();
     
     if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
    	 
		               Tracer.error(this.getClass().toString(),"getCorporateByCode","errore durante l'esecuzione CorporateserviceService",null);
          throw new BusinessDelegateRuntimeException(infoList);
          
     }
     
     return dtoReturn;
}
  
  /**
   * 
   */
  public DTO getEnteByIntestCode(FrontEndContext fec,DTO dto) {
  
         DTO dtoReturn = null;
  	    try {
  		   Tracer.info(this.getClass().toString(),"getEnteByIntestCode","esecuzione CorporateserviceService",null);
  		   dtoReturn = corporateserviceService.getEnteByIntestCode(fec, dto);
  	    } catch (Throwable re) {
  		       Tracer.error(this.getClass().toString(),"getEnteByIntestCode","errore durante l'esecuzione CorporateserviceService",re);
                 dtoReturn =  ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
  	    }
        DTOInfoList infoList = dtoReturn.getInfoList();
        if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
  		               Tracer.error(this.getClass().toString(),"getEnteByIntestCode","errore durante l'esecuzione CorporateserviceService",null);
             throw new BusinessDelegateRuntimeException(infoList);
        }
        return dtoReturn;
  }
  
  
  public DTO updateCorporate(FrontEndContext fec,DTO dto) {
  
         DTO dtoReturn = null;
  	    try {
  		   Tracer.info(this.getClass().toString(),"updateCorporate","esecuzione CorporateserviceService",null);
  		   dtoReturn = corporateserviceService.updateCorporate(fec, dto);
  	    } catch (Throwable re) {
  		       Tracer.error(this.getClass().toString(),"updateCorporate","errore durante l'esecuzione CorporateserviceService",re);
                 dtoReturn =  ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
  	    }
        DTOInfoList infoList = dtoReturn.getInfoList();
        if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
  		               Tracer.error(this.getClass().toString(),"updateCorporate","errore durante l'esecuzione CorporateserviceService",null);
             throw new BusinessDelegateRuntimeException(infoList);
        }
        return dtoReturn;
  }
  
  
  public DTOCollection listCorporateByCriteria(FrontEndContext fec,DTO dto) {
  
         DTOCollection dtoCollectionReturn = null;
  	    try {
  		   Tracer.info(this.getClass().toString(),"listCorporateByCriteria","esecuzione CorporateserviceService",null);
  		   dtoCollectionReturn = corporateserviceService.listCorporateByCriteria(fec, dto);
  	    } catch (Throwable re) {
  		       Tracer.error(this.getClass().toString(),"listCorporateByCriteria","errore durante l'esecuzione CorporateserviceService",re);
                 dtoCollectionReturn =  ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
  	    }
        DTOInfoList infoList = dtoCollectionReturn.getInfoList();
        if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
  		               Tracer.error(this.getClass().toString(),"listCorporateByCriteria","errore durante l'esecuzione CorporateserviceService",null);
             throw new BusinessDelegateRuntimeException(infoList);
        }
        return dtoCollectionReturn;
  }
  
  
//  public DTOCollection getAllTipologieEnti(FrontEndContext fec,DTO dto) {
//	  
//	  return null;
//	  
//	  DTOCollection dtoCollectionReturn = null;
//	    try {
//		   Tracer.info(this.getClass().toString(),"getAllTipologieEnti","esecuzione CorporateserviceService",null);
//		   dtoCollectionReturn = corporateserviceService.getAllTipologieEnti(fec, dto);
//	    } catch (Throwable re) {
//		       Tracer.error(this.getClass().toString(),"getAllTipologieEnti","errore durante l'esecuzione CorporateserviceService",re);
//               dtoCollectionReturn =  ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//	    }
//      DTOInfoList infoList = dtoCollectionReturn.getInfoList();
//      if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
//		               Tracer.error(this.getClass().toString(),"getAllTipologieEnti","errore durante l'esecuzione CorporateserviceService",null);
//           throw new BusinessDelegateRuntimeException(infoList);
//      }
//      return dtoCollectionReturn;
//}
  
  
  
//  public DTO test(FrontEndContext fec,DTO dto) {
//  
//         DTO dtoReturn = null;
//  	    try {
//  		   Tracer.info(this.getClass().toString(),"test","esecuzione CorporateserviceService",null);
//  		   dtoReturn = corporateserviceService.test(fec, dto);
//  	    } catch (Throwable re) {
//  		       Tracer.error(this.getClass().toString(),"test","errore durante l'esecuzione CorporateserviceService",re);
//                 dtoReturn =  ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//  	    }
//        DTOInfoList infoList = dtoReturn.getInfoList();
//        if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
//  		               Tracer.error(this.getClass().toString(),"test","errore durante l'esecuzione CorporateserviceService",null);
//             throw new BusinessDelegateRuntimeException(infoList);
//        }
//        return dtoReturn;
//  }
  
  public DTOCollection listEntiByCriteria(FrontEndContext fec,DTO dto) {
	  
      DTOCollection dtoCollectionReturn = null;
	    try {
		   Tracer.info(this.getClass().toString(),"listEntiByCriteria","esecuzione CorporateserviceService",null);
		   dtoCollectionReturn = corporateserviceService.listEntiByCriteria(fec, dto);
	    } catch (Throwable re) {
		       Tracer.error(this.getClass().toString(),"listEntiByCriteria","errore durante l'esecuzione CorporateserviceService",re);
              dtoCollectionReturn =  ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
	    }
     DTOInfoList infoList = dtoCollectionReturn.getInfoList();
     if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
		               Tracer.error(this.getClass().toString(),"listEntiByCriteria","errore durante l'esecuzione CorporateserviceService",null);
          throw new BusinessDelegateRuntimeException(infoList);
     }
     return dtoCollectionReturn;
}
  
//  /**
//   * 
//   * @param fec
//   * @return
//   */
//  public DTOCollection listAllEnti(FrontEndContext fec) {
//	  
//      DTOCollection dtoCollectionReturn = null;
//	    try {
//		   Tracer.info(this.getClass().toString(),"listAllEnti","esecuzione CorporateserviceService",null);
//		   dtoCollectionReturn = corporateserviceService.listAllEnti(fec);
//	    } catch (Throwable re) {
//		       Tracer.error(this.getClass().toString(),"listAllEnti","errore durante l'esecuzione CorporateserviceService",re);
//              dtoCollectionReturn =  ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//	    }
//     DTOInfoList infoList = dtoCollectionReturn.getInfoList();
//     if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
//		               Tracer.error(this.getClass().toString(),"listAllEnti","errore durante l'esecuzione CorporateserviceService",null);
//          throw new BusinessDelegateRuntimeException(infoList);
//     }
//     return dtoCollectionReturn;
//  }

  /**
   * 
   * @param fec
   * @return
   */
////TODO: TASEGO
//  public DTOCollection listAllSubjectsByOperator(FrontEndContext fec, DTO dto) {
//	  
//      DTOCollection dtoCollectionReturn = null;
//	    try {
//		   Tracer.info(this.getClass().toString(),"listAllSubjectsByOperator","esecuzione CorporateserviceService",null);
//		   dtoCollectionReturn = corporateserviceService.listAllSubjectsByOperator(fec, dto);
//		   
//	    } catch (Throwable re) {
//		       Tracer.error(this.getClass().toString(),"listAllSubjectsByOperator","errore durante l'esecuzione CorporateserviceService",re);
//              dtoCollectionReturn =  ManageFrontEndException.getBusinessDTOCollectionByError(FrontEndMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//	    }
//     DTOInfoList infoList = dtoCollectionReturn.getInfoList();
//     if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)){
//		               Tracer.error(this.getClass().toString(),"listAllSubjectsByOperator","errore durante l'esecuzione CorporateserviceService",null);
//          throw new BusinessDelegateRuntimeException(infoList);
//     }
//     return dtoCollectionReturn;
//  }


//	// TODO: TASEGO
//	@Override
//	public DTO getCorporateByID(FrontEndContext fec, DTO dto) {
//		DTO dtoReturn = null;
//		try {
//			Tracer.info(this.getClass().toString(), "getCorporateByID",
//					"esecuzione CorporateserviceService", null);
//			dtoReturn = corporateserviceService.getCorporateByID(fec, dto);
//		} catch (Throwable re) {
//			Tracer.error(this.getClass().toString(), "getCorporateByID",
//					"errore durante l'esecuzione CorporateserviceService", re);
//			dtoReturn = ManageFrontEndException.getBusinessDTOByError(
//					FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
//		}
//		DTOInfoList infoList = dtoReturn.getInfoList();
//		if (infoList != null
//				&& infoList
//						.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
//			Tracer
//					.error(
//							this.getClass().toString(),
//							"getCorporateByID",
//							"errore durante l'esecuzione CorporateserviceService",
//							null);
//			throw new BusinessDelegateRuntimeException(infoList);
//		}
//		return dtoReturn;
//	}

	@Override
	public DTO updateFlagComunicazioni(FrontEndContext fec, DTO dto) {

		DTO dtoReturn = null;
		try {
			Tracer.info(this.getClass().toString(), "updateFlagComunicazione", "esecuzione CorporateserviceService", null);
			dtoReturn = corporateserviceService.updateFlagComunicazioni(fec, dto);
		} catch (Throwable re) {
			Tracer.error(this.getClass().toString(), "updateFlagComunicazione", "errore durante l'esecuzione CorporateserviceService", re);
			dtoReturn = ManageFrontEndException.getBusinessDTOByError(FrontEndMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
		DTOInfoList infoList = dtoReturn.getInfoList();
		if (infoList != null && infoList.hasErrorGESeverity(DTOInfoInterface.SEVERITY_GENERIC)) {
			Tracer.error(this.getClass().toString(), "updateFlagComunicazione", "errore durante l'esecuzione CorporateserviceService", null);
			throw new BusinessDelegateRuntimeException(infoList);
		}
		return dtoReturn;
	}
}
