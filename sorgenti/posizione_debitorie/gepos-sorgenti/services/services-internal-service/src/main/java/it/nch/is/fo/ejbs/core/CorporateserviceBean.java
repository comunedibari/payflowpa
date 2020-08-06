package it.nch.is.fo.ejbs.core;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Stateless;
import javax.persistence.PreRemove;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.cross.AbstractEjb;
import it.nch.fwk.fo.common.constants.FrameworkMessage;
import it.nch.is.fo.core.interfaces.CorporateserviceServiceLocal;
import it.nch.fwk.fo.util.Tracer;
import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.cross.exception.BusinessImplRuntimeException;
import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOInfoInterface;
@SuppressWarnings("rawtypes")
@Stateless(name="Corporateservice")
public class CorporateserviceBean extends AbstractEjb implements Corporateservice, CorporateserviceLocal {// javax.ejb.SessionBean {


   private BeanFactoryReference bfr;
   private BeanFactoryLocator bfl;
   private BeanFactory bf;
   private CorporateserviceServiceLocal corporateserviceServiceLocal;

   @Resource
   private javax.ejb.SessionContext mySessionCtx;

   /*
   Refactor da EJB 2.1 a EJB 3.0

   public javax.ejb.SessionContext getSessionContext() { 
       return mySessionCtx;
   }
   public void setSessionContext(javax.ejb.SessionContext ctx) { 
       	mySessionCtx = ctx;
   }
   */
   
   @PostConstruct
   @PostActivate
   public void ejbCreate() throws javax.ejb.CreateException {
   
     bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
     bfr = bfl.useBeanFactory("it.nch.business");
     Tracer.info(" CorporateserviceBean ", "ejbCreate","BeanFactory per oggetti business.........", null);
     bf = bfr.getFactory();
     corporateserviceServiceLocal = (CorporateserviceServiceLocal) bf.getBean("corporateserviceBusiness");
     Tracer.info(" CorporateserviceBean ", "ejbCreate","Instanziato OK!", null);
   
   }
   
   @PrePassivate
   @PreRemove
   public void ejbRemove() {
   
     bfr = null;
     bf = null;
     corporateserviceServiceLocal = null;
     Tracer.info(" CorporateserviceBean", "ejbRemove","Rimozione dal ciclo di vita del riferimento al Bean", null);
   
   }
   
   /*
   public void ejbActivate() {
   
     bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
     bfr = bfl.useBeanFactory("it.nch.business");
     Tracer.info(" CorporateserviceBean ", "ejbActivate","BeanFactory per oggetti business.........", null);
     bf = bfr.getFactory();
     corporateserviceServiceLocal = (CorporateserviceServiceLocal) bf.getBean("corporateserviceBusiness");
     Tracer.info(" CorporateserviceBean ", "ejbActivate","Instanziato OK!", null);
   
   }
   
   public void ejbPassivate() {
   
     bfr = null;
     bf = null;
     corporateserviceServiceLocal = null;
     Tracer.info(" CorporateserviceBean", "ejbPassivate","Rimozione dal ciclo di vita del riferimento al Bean", null);
   
   }
   */
   
   public DTO getCorporateByCode(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
     try{
         if (Tracer.isDebugEnabled(" CorporateserviceBean"))
            Tracer.debug(" CorporateserviceBean","getCorporateByCode","Guscio EJB", null);
         ret = corporateserviceServiceLocal.getCorporateByCode(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" CorporateserviceBean","getCorporateByCode","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" CorporateserviceBean","getCorporateByCode","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" CorporateserviceBean","getCorporateByCode","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
 
   
//   public DTO getCorporateByID(FrontEndContext fec,DTO dto) {
//   
//         DTO ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" CorporateserviceBean"))
//            Tracer.debug(" CorporateserviceBean","getCorporateByID","Guscio EJB", null);
//         ret = corporateserviceServiceLocal.getCorporateByID(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" CorporateserviceBean","getCorporateByID","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" CorporateserviceBean","getCorporateByID","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" CorporateserviceBean","getCorporateByID","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
   
   
   /**
    * 
    * @param fec
    * @param dto
    * @return
    */
   public DTO getEnteByIntestCode(FrontEndContext fec,DTO dto) {
	   
       DTO ret = null;
   try{
       if (Tracer.isDebugEnabled(" CorporateserviceBean"))
          Tracer.debug(" CorporateserviceBean","getEnteByIntestCode","Guscio EJB", null);
       ret = corporateserviceServiceLocal.getEnteByIntestCode(fec, this.copyBusinessObject(dto));
 	  return this.copyBusinessObject(ret);
   }catch(DAORuntimeException drte){
       Tracer.error(" CorporateserviceBean","getEnteByIntestCode","", drte);
       mySessionCtx.setRollbackOnly();
       ret = drte.getDTO();
       clearBeanData(ret);
       return ret;
   }catch(BusinessImplRuntimeException birte){
       Tracer.error(" CorporateserviceBean","getEnteByIntestCode","", birte);
       mySessionCtx.setRollbackOnly();
       ret = birte.getDTO();
       clearBeanData(ret);
       return ret;
   }catch(RuntimeException rte){
       Tracer.error(" CorporateserviceBean","getEnteByIntestCode","", rte);
       mySessionCtx.setRollbackOnly();
       return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
   }
 }   
   
   public DTO updateCorporate(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
     try{
         if (Tracer.isDebugEnabled(" CorporateserviceBean"))
            Tracer.debug(" CorporateserviceBean","updateCorporate","Guscio EJB", null);
         ret = corporateserviceServiceLocal.updateCorporate(fec, this.copyBusinessObject(dto));
         
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" CorporateserviceBean","updateCorporate","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" CorporateserviceBean","updateCorporate","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTO();
         //clearBeanData(ret);
         return this.copyBusinessObject(ret);
     }catch(RuntimeException rte){
         Tracer.error(" CorporateserviceBean","updateCorporate","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
   public DTOCollection listCorporateByCriteria(FrontEndContext fec,DTO dto) {
   
         DTOCollection ret = null;
     try{
         if (Tracer.isDebugEnabled(" CorporateserviceBean"))
            Tracer.debug(" CorporateserviceBean","listCorporateByCriteria","Guscio EJB", null);
         ret = corporateserviceServiceLocal.listCorporateByCriteria(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" CorporateserviceBean","listCorporateByCriteria","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTOCollection();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" CorporateserviceBean","listCorporateByCriteria","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTOCollection();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
    	 rte.printStackTrace();
         Tracer.error(" CorporateserviceBean","listCorporateByCriteria","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
   
//   public DTOCollection getAllTipologieEnti(FrontEndContext fec,DTO dto) {
//	   
//	   DTOCollection ret = null;
//	     try{
//	         if (Tracer.isDebugEnabled(" CorporateserviceBean"))
//	            Tracer.debug(" CorporateserviceBean","getAllTipologieEnti","Guscio EJB", null);
//	         ret = corporateserviceServiceLocal.getAllTipologieEnti(fec, this.copyBusinessObject(dto));
//	   	  return this.copyBusinessObject(ret);
//	     }catch(DAORuntimeException drte){
//	         Tracer.error(" CorporateserviceBean","listCorporateByCriteria","", drte);
//	         mySessionCtx.setRollbackOnly();
//	         ret = drte.getDTOCollection();
//	         clearBeanData(ret);
//	         return ret;
//	     }catch(BusinessImplRuntimeException birte){
//	         Tracer.error(" CorporateserviceBean","listCorporateByCriteria","", birte);
//	         mySessionCtx.setRollbackOnly();
//	         ret = birte.getDTOCollection();
//	         clearBeanData(ret);
//	         return ret;
//	     }catch(RuntimeException rte){
//	         Tracer.error(" CorporateserviceBean","listCorporateByCriteria","", rte);
//	         mySessionCtx.setRollbackOnly();
//	         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//	     }
// }
   
   
//   public DTO test(FrontEndContext fec,DTO dto) {
//      try{
//         Tracer.debug("CorporateserviceBean","test","Guscio EJB", null);
//         return corporateserviceServiceLocal.test(fec,dto);
//      }catch(RuntimeException rte){
//         return this.processException(dto, rte);
//      }
//   }
   
   public DTOCollection listEntiByCriteria(FrontEndContext fec,DTO dto) {
	   
       DTOCollection ret = null;
   try{
       if (Tracer.isDebugEnabled(" CorporateserviceBean"))
          Tracer.debug(" CorporateserviceBean","listEntiByCriteria","Guscio EJB", null);
       ret = corporateserviceServiceLocal.listEntiByCriteria(fec, this.copyBusinessObject(dto));
 	  return this.copyBusinessObject(ret);
   }catch(DAORuntimeException drte){
       Tracer.error(" CorporateserviceBean","listEntiByCriteria","", drte);
       mySessionCtx.setRollbackOnly();
       ret = drte.getDTOCollection();
       clearBeanData(ret);
       return ret;
   }catch(BusinessImplRuntimeException birte){
       Tracer.error(" CorporateserviceBean","listEntiByCriteria","", birte);
       mySessionCtx.setRollbackOnly();
       ret = birte.getDTOCollection();
       clearBeanData(ret);
       return ret;
   }catch(RuntimeException rte){
       Tracer.error(" CorporateserviceBean","listEntiByCriteria","", rte);
       mySessionCtx.setRollbackOnly();
       return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
   }
 }
   
   
//   /**
//    * 
//    * @param fec
//    * @return
//    */
//   public DTOCollection listAllEnti(FrontEndContext fec) {
//	   
//       DTOCollection ret = null;
//   try{
//       if (Tracer.isDebugEnabled(" CorporateserviceBean"))
//          Tracer.debug(" CorporateserviceBean","listAllEnti","Guscio EJB", null);
//       ret = corporateserviceServiceLocal.listAllEnti(fec);
// 	  return this.copyBusinessObject(ret);
//   }catch(DAORuntimeException drte){
//       Tracer.error(" CorporateserviceBean","listAllEnti","", drte);
//       mySessionCtx.setRollbackOnly();
//       ret = drte.getDTOCollection();
//       clearBeanData(ret);
//       return ret;
//   }catch(BusinessImplRuntimeException birte){
//       Tracer.error(" CorporateserviceBean","listAllEnti","", birte);
//       mySessionCtx.setRollbackOnly();
//       ret = birte.getDTOCollection();
//       clearBeanData(ret);
//       return ret;
//   }catch(RuntimeException rte){
//       Tracer.error(" CorporateserviceBean","listAllEnti","", rte);
//       mySessionCtx.setRollbackOnly();
//       return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//   }
// }
   
//   /**
//    * 
//    * @param fec
//    * @return
//    */
//   public DTOCollection listAllSubjectsByOperator(FrontEndContext fec, DTO dto) {
//	   
//       DTOCollection ret = null;
//   try{
//       if (Tracer.isDebugEnabled(" CorporateserviceBean"))
//          Tracer.debug(" CorporateserviceBean","listAllSubjectsByOperator","Guscio EJB", null);
//       ret = corporateserviceServiceLocal.listAllSubjectsByOperator(fec, dto);
// 	  return this.copyBusinessObject(ret);
//   }catch(DAORuntimeException drte){
//       Tracer.error(" CorporateserviceBean","listAllSubjectsByOperator","", drte);
//       mySessionCtx.setRollbackOnly();
//       ret = drte.getDTOCollection();
//       clearBeanData(ret);
//       return ret;
//   }catch(BusinessImplRuntimeException birte){
//       Tracer.error(" CorporateserviceBean","listAllSubjectsByOperator","", birte);
//       mySessionCtx.setRollbackOnly();
//       ret = birte.getDTOCollection();
//       clearBeanData(ret);
//       return ret;
//   }catch(RuntimeException rte){
//       Tracer.error(" CorporateserviceBean","listAllSubjectsByOperator","", rte);
//       mySessionCtx.setRollbackOnly();
//       return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//   }
// }

	@Override
	public DTO updateFlagComunicazioni(FrontEndContext fec, DTO dto) {

		DTO ret = null;
		try {
			if (Tracer.isDebugEnabled(" CorporateserviceBean"))
				Tracer.debug(" CorporateserviceBean", "updateCorporate", "Guscio EJB", null);
			ret = corporateserviceServiceLocal.updateFlagComunicazioni(fec, this.copyBusinessObject(dto));
			return this.copyBusinessObject(ret);
		} catch (DAORuntimeException drte) {
			Tracer.error(" CorporateserviceBean", "updateFlagComunicazioni", "", drte);
			mySessionCtx.setRollbackOnly();
			ret = drte.getDTO();
			clearBeanData(ret);
			return ret;
		} catch (BusinessImplRuntimeException birte) {
			Tracer.error(" CorporateserviceBean", "updateFlagComunicazioni", "", birte);
			mySessionCtx.setRollbackOnly();
			ret = birte.getDTO();
			clearBeanData(ret);
			return ret;
		} catch (RuntimeException rte) {
			Tracer.error(" CorporateserviceBean", "updateFlagComunicazioni", "", rte);
			mySessionCtx.setRollbackOnly();
			return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001, DTOInfoInterface.SEVERITY_GENERIC);
		}
	}   
   
}
