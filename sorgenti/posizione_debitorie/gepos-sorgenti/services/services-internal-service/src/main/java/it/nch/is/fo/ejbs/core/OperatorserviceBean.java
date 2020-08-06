package it.nch.is.fo.ejbs.core;


import it.nch.fwk.fo.common.constants.FrameworkMessage;
import it.nch.fwk.fo.core.exception.ManageBackEndException;
import it.nch.fwk.fo.cross.AbstractEjb;
import it.nch.fwk.fo.cross.exception.BusinessImplRuntimeException;
import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOInfoInterface;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;
import it.nch.is.fo.core.interfaces.OperatorserviceServiceLocal;

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

@Stateless(name="Operatorservice")
@SuppressWarnings("rawtypes")
public class OperatorserviceBean extends AbstractEjb implements Operatorservice, OperatorserviceLocal { //implements javax.ejb.SessionBean {


   private BeanFactoryReference bfr;
   private BeanFactoryLocator bfl;
   private BeanFactory bf;
   private OperatorserviceServiceLocal operatorserviceServiceLocal;

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
     Tracer.info(" OperatorserviceBean ", "ejbCreate","BeanFactory per oggetti business.........", null);
     bf = bfr.getFactory();
     operatorserviceServiceLocal = (OperatorserviceServiceLocal) bf.getBean("operatorserviceBusiness");
     Tracer.info(" OperatorserviceBean ", "ejbCreate","Instanziato OK!", null);
   
   }
   
   @PrePassivate
   @PreRemove
   public void ejbRemove() {
   
     bfr = null;
     bf = null;
     operatorserviceServiceLocal = null;
     Tracer.info(" OperatorserviceBean", "ejbRemove","Rimozione dal ciclo di vita del riferimento al Bean", null);
   
   }
   
   /*
   public void ejbActivate() {
   
     bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
     bfr = bfl.useBeanFactory("it.nch.business");
     Tracer.info(" OperatorserviceBean ", "ejbActivate","BeanFactory per oggetti business.........", null);
     bf = bfr.getFactory();
     operatorserviceServiceLocal = (OperatorserviceServiceLocal) bf.getBean("operatorserviceBusiness");
     Tracer.info(" OperatorserviceBean ", "ejbActivate","Instanziato OK!", null);
   
   }
   
   public void ejbPassivate() {
   
     bfr = null;
     bf = null;
     operatorserviceServiceLocal = null;
     Tracer.info(" OperatorserviceBean", "ejbPassivate","Rimozione dal ciclo di vita del riferimento al Bean", null);
   
   }
   */
   
//   public DTO getOperatorByLoginInfo(FrontEndContext fec,DTO dto) {
//   
//         DTO ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","getOperatorByLoginInfo","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.getOperatorByLoginInfo(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","getOperatorByLoginInfo","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","getOperatorByLoginInfo","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","getOperatorByLoginInfo","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
//public DTO getOperatorByCodiceFiscale(FrontEndContext fec,DTO dto) {
//	   
//       DTO ret = null;
//   try{
//       if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//          Tracer.debug(" OperatorserviceBean","getOperatorByCodiceFiscale","Guscio EJB", null);
//       ret = operatorserviceServiceLocal.getOperatorByCodiceFiscale(fec, this.copyBusinessObject(dto));
// 	  return this.copyBusinessObject(ret);
//   }catch(DAORuntimeException drte){
//       Tracer.error(" OperatorserviceBean","getOperatorByCodiceFiscale","", drte);
//       mySessionCtx.setRollbackOnly();
//       ret = drte.getDTO();
//       clearBeanData(ret);
//       return ret;
//   }catch(BusinessImplRuntimeException birte){
//       Tracer.error(" OperatorserviceBean","getOperatorByCodiceFiscale","", birte);
//       mySessionCtx.setRollbackOnly();
//       ret = birte.getDTO();
//       clearBeanData(ret);
//       return ret;
//   }catch(RuntimeException rte){
//       Tracer.error(" OperatorserviceBean","getOperatorByCodiceFiscale","", rte);
//       mySessionCtx.setRollbackOnly();
//       return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//   }
// }
   
   /**
    * 
    * @param fec
    * @param dto
    * @return
    */
   public DTO getOperatorByCodiceFiscaleInternal(FrontEndContext fec,DTO dto) {
	   
       DTO ret = null;
   try{
       if (Tracer.isDebugEnabled(" OperatorserviceBean"))
          Tracer.debug(" OperatorserviceBean","getOperatorByCodiceFiscale","Guscio EJB", null);
       ret = operatorserviceServiceLocal.getOperatorByCodiceFiscaleInternal(fec, this.copyBusinessObject(dto));
 	  return this.copyBusinessObject(ret);
   }catch(DAORuntimeException drte){
       Tracer.error(" OperatorserviceBean","getOperatorByCodiceFiscaleInternal","", drte);
       mySessionCtx.setRollbackOnly();
       ret = drte.getDTO();
       clearBeanData(ret);
       return ret;
   }catch(BusinessImplRuntimeException birte){
       Tracer.error(" OperatorserviceBean","getOperatorByCodiceFiscaleInternal","", birte);
       mySessionCtx.setRollbackOnly();
       ret = birte.getDTO();
       clearBeanData(ret);
       return ret;
   }catch(RuntimeException rte){
       Tracer.error(" OperatorserviceBean","getOperatorByCodiceFiscaleInternal","", rte);
       mySessionCtx.setRollbackOnly();
       return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
   }
 }

   /**
    * 
    * @param fec
    * @param dto
    * @return
    */
   public DTO assignNewOperator(FrontEndContext fec,DTO dto) {
	   
	   DTO ret = null;
	   try{
	       if (Tracer.isDebugEnabled(" OperatorserviceBean"))
	          Tracer.debug(" OperatorserviceBean","assignNewOperator","Guscio EJB", null);
	       ret = operatorserviceServiceLocal.assignNewOperator(fec, this.copyBusinessObject(dto));
	 	  return this.copyBusinessObject(ret);
	   }catch(DAORuntimeException drte){
	       Tracer.error(" OperatorserviceBean","assignNewOperator","", drte);
	       mySessionCtx.setRollbackOnly();
	       ret = drte.getDTO();
	       clearBeanData(ret);
	       return ret;
	   }catch(BusinessImplRuntimeException birte){
	       Tracer.error(" OperatorserviceBean","assignNewOperator","", birte);
	       mySessionCtx.setRollbackOnly();
	       ret = birte.getDTO();
	       clearBeanData(ret);
	       return ret;
	   }catch(RuntimeException rte){
	       Tracer.error(" OperatorserviceBean","assignNewOperator","", rte);
	       mySessionCtx.setRollbackOnly();
	       return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
	   	}
   }
   
   /**
    * 
    * @param fec
    * @param dto
    * @return
    */
   public DTO deAssignNewOperator(FrontEndContext fec,DTO dto) {
	   
	   DTO ret = null;
	   try{
	       if (Tracer.isDebugEnabled(" OperatorserviceBean"))
	          Tracer.debug(" OperatorserviceBean","deAssignNewOperator","Guscio EJB", null);
	       ret = operatorserviceServiceLocal.deAssignNewOperator(fec, this.copyBusinessObject(dto));
	 	  return this.copyBusinessObject(ret);
	   }catch(DAORuntimeException drte){
	       Tracer.error(" OperatorserviceBean","deAssignNewOperator","", drte);
	       mySessionCtx.setRollbackOnly();
	       ret = drte.getDTO();
	       clearBeanData(ret);
	       return ret;
	   }catch(BusinessImplRuntimeException birte){
	       Tracer.error(" OperatorserviceBean","deAssignNewOperator","", birte);
	       mySessionCtx.setRollbackOnly();
	       ret = birte.getDTO();
	       clearBeanData(ret);
	       return ret;
	   }catch(RuntimeException rte){
	       Tracer.error(" OperatorserviceBean","deAssignNewOperator","", rte);
	       mySessionCtx.setRollbackOnly();
	       return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
	   	}
   }
   
   
   public DTO checkOperatorByUsernameAndCorporate(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
     try{
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","checkOperatorByUsernameAndCorporate","Guscio EJB", null);
         ret = operatorserviceServiceLocal.checkOperatorByUsernameAndCorporate(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" OperatorserviceBean","checkOperatorByUsernameAndCorporate","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" OperatorserviceBean","checkOperatorByUsernameAndCorporate","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" OperatorserviceBean","checkOperatorByUsernameAndCorporate","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
   
   public DTO getOperatorByCode(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
     try{
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","getOperatorByCode","Guscio EJB", null);
         ret = operatorserviceServiceLocal.getOperatorByCode(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" OperatorserviceBean","getOperatorByCode","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" OperatorserviceBean","getOperatorByCode","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" OperatorserviceBean","getOperatorByCode","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
   public DTOCollection deleteOperator(FrontEndContext fec,DTO dto) {
   
         DTOCollection ret = null;
     try{
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","deleteOperator","Guscio EJB", null);
         ret = operatorserviceServiceLocal.deleteOperator(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" OperatorserviceBean","deleteOperator","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTOCollection();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" OperatorserviceBean","deleteOperator","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTOCollection();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" OperatorserviceBean","deleteOperator","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
   
//   public DTOCollection listOperatorsByCriteriaForACorporate(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listOperatorsByCriteriaForACorporate","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listOperatorsByCriteriaForACorporate(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listOperatorsByCriteriaForACorporate","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listOperatorsByCriteriaForACorporate","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listOperatorsByCriteriaForACorporate","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
//   public DTO getAdminOperator(FrontEndContext fec,DTO dto) {
//   
//         DTO ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","getAdminOperator","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.getAdminOperator(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","getAdminOperator","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","getAdminOperator","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","getAdminOperator","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
//   public DTO insertOrUpdateOperatorSkipControl(FrontEndContext fec,DTO dto) {
//   
//         DTO ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","insertOrUpdateOperatorSkipControl","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.insertOrUpdateOperatorSkipControl(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","insertOrUpdateOperatorSkipControl","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","insertOrUpdateOperatorSkipControl","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","insertOrUpdateOperatorSkipControl","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
   public DTO insertAndUpdateOperator(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
     try{
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","insertAndUpdateOperator","Guscio EJB", null);
         ret = operatorserviceServiceLocal.insertAndUpdateOperator(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" OperatorserviceBean","insertAndUpdateOperator","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" OperatorserviceBean","insertAndUpdateOperator","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" OperatorserviceBean","insertAndUpdateOperator","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
   public DTO updateOperator(FrontEndContext fec,DTO dto) {
	   
       DTO ret = null;
   try{
       if (Tracer.isDebugEnabled(" OperatorserviceBean"))
          Tracer.debug(" OperatorserviceBean","updateOperator","Guscio EJB", null);
       ret = operatorserviceServiceLocal.updateOperator(fec, this.copyBusinessObject(dto));
 	  return this.copyBusinessObject(ret);
   }catch(DAORuntimeException drte){
       Tracer.error(" OperatorserviceBean","updateOperator","", drte);
       mySessionCtx.setRollbackOnly();
       ret = drte.getDTO();
       clearBeanData(ret);
       return ret;
   }catch(BusinessImplRuntimeException birte){
       Tracer.error(" OperatorserviceBean","updateOperator","", birte);
       mySessionCtx.setRollbackOnly();
       ret = birte.getDTO();
       clearBeanData(ret);
       return ret;
   }catch(RuntimeException rte){
       Tracer.error(" OperatorserviceBean","updateOperator","", rte);
       mySessionCtx.setRollbackOnly();
       return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
   }
 }
   
   public DTO insertAndUpdateOperatorFunctions(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
     try{
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","insertAndUpdateOperatorFunctions","Guscio EJB", null);
         ret = operatorserviceServiceLocal.insertAndUpdateOperatorFunctions(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" OperatorserviceBean","insertAndUpdateOperatorFunctions","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" OperatorserviceBean","insertAndUpdateOperatorFunctions","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTO();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" OperatorserviceBean","insertAndUpdateOperatorFunctions","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
//   public DTOCollection listOperatorFunctionsByOperatorAndCorporate(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listOperatorFunctionsByOperatorAndCorporate","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listOperatorFunctionsByOperatorAndCorporate(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listOperatorFunctionsByOperatorAndCorporate","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listOperatorFunctionsByOperatorAndCorporate","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listOperatorFunctionsByOperatorAndCorporate","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
   public DTOCollection listFunctionsByCorporate(FrontEndContext fec,DTO dto) {
   
         DTOCollection ret = null;
     try{
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","listFunctionsByCorporate","Guscio EJB", null);
         ret = operatorserviceServiceLocal.listFunctionsByCorporate(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
     }catch(DAORuntimeException drte){
         Tracer.error(" OperatorserviceBean","listFunctionsByCorporate","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTOCollection();
         clearBeanData(ret);
         return ret;
     }catch(BusinessImplRuntimeException birte){
         Tracer.error(" OperatorserviceBean","listFunctionsByCorporate","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTOCollection();
         clearBeanData(ret);
         return ret;
     }catch(RuntimeException rte){
         Tracer.error(" OperatorserviceBean","listFunctionsByCorporate","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     }
   }
   
//   public DTOCollection listSignersByCorporateCode(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listSignersByCorporateCode","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listSignersByCorporateCode(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listSignersByCorporateCode","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listSignersByCorporateCode","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listSignersByCorporateCode","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
   public DTOCollection listOperatorsByCriteria(FrontEndContext fec,DTO dto) {
   
         DTOCollection ret = null;
         
     try{
    	 
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
            Tracer.debug(" OperatorserviceBean","listOperatorsByCriteria","Guscio EJB", null);
         ret = operatorserviceServiceLocal.listOperatorsByCriteria(fec, this.copyBusinessObject(dto));
   	  return this.copyBusinessObject(ret);
   	  
     }catch(DAORuntimeException drte){
    	 
         Tracer.error(" OperatorserviceBean","listOperatorsByCriteria","", drte);
         mySessionCtx.setRollbackOnly();
         ret = drte.getDTOCollection();
         clearBeanData(ret);
         return ret;
         
     }catch(BusinessImplRuntimeException birte){
    	 
         Tracer.error(" OperatorserviceBean","listOperatorsByCriteria","", birte);
         mySessionCtx.setRollbackOnly();
         ret = birte.getDTOCollection();
         clearBeanData(ret);
         return ret;
         
     }catch(RuntimeException rte){
    	 
         Tracer.error(" OperatorserviceBean","listOperatorsByCriteria","", rte);
         mySessionCtx.setRollbackOnly();
         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     
     }
     
   }
   
//   public DTO getOperatorKeyByUsernameAndCorporate(FrontEndContext fec,DTO dto) {
//   
//         DTO ret = null;
//         
//     try{
//    	 
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","getOperatorKeyByUsernameAndCorporate","Guscio EJB", null);
//         
//         ret = operatorserviceServiceLocal.getOperatorKeyByUsernameAndCorporate(fec, this.copyBusinessObject(dto));
//   	  
//         return this.copyBusinessObject(ret);
//   	  
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","getOperatorKeyByUsernameAndCorporate","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","getOperatorKeyByUsernameAndCorporate","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTO();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","getOperatorKeyByUsernameAndCorporate","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
   public DTO changePwd(FrontEndContext fec,DTO dto) {
   
         DTO ret = null;
         
     try{
    	 
         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
        	 
            Tracer.debug(" OperatorserviceBean","changePwd","Guscio EJB", null);
         
         ret = operatorserviceServiceLocal.changePwd(fec, this.copyBusinessObject(dto));
         
   	  return this.copyBusinessObject(ret);
   	  
     }catch(DAORuntimeException drte){
    	 
         Tracer.error(" OperatorserviceBean","changePwd","", drte);
         
         mySessionCtx.setRollbackOnly();
         
         ret = drte.getDTO();
         
         clearBeanData(ret);
         
         return ret;
         
     }catch(BusinessImplRuntimeException birte){
    	 
         Tracer.error(" OperatorserviceBean","changePwd","", birte);
         
         mySessionCtx.setRollbackOnly();
         
         ret = birte.getDTO();
         
         clearBeanData(ret);
         
         return ret;
         
     }catch(RuntimeException rte){
    	 
         Tracer.error(" OperatorserviceBean","changePwd","", rte);
         
         mySessionCtx.setRollbackOnly();
         
         return new ManageBackEndException().getBusinessDTOByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
     
     }
   }
   
//   public DTOCollection listEnabledFunctions(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listEnabledFunctions","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listEnabledFunctions(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctions","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctions","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctions","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
//   public DTOCollection listEnabledFunctionsWithoutPwdControl(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listEnabledFunctionsWithoutPwdControl","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listEnabledFunctionsWithoutPwdControl(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctionsWithoutPwdControl","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctionsWithoutPwdControl","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctionsWithoutPwdControl","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
//   public DTOCollection listFunctionsByBackOfficeOperator(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listFunctionsByBackOfficeOperator","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listFunctionsByBackOfficeOperator(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listFunctionsByBackOfficeOperator","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listFunctionsByBackOfficeOperator","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listFunctionsByBackOfficeOperator","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
//   
//   public DTOCollection listEnabledFunctionsButtonByBackOfficeOperator(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listEnabledFunctionsButtonByBackOfficeOperator","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listEnabledFunctionsButtonByBackOfficeOperator(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctionsButtonByBackOfficeOperator","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctionsButtonByBackOfficeOperator","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listEnabledFunctionsButtonByBackOfficeOperator","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
//   
//   public DTOCollection listEnableSubholdings(FrontEndContext fec,DTO dto) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","listEnableSubholdings","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.listEnableSubholdings(fec, this.copyBusinessObject(dto));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","listEnableSubholdings","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","listEnableSubholdings","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","listEnableSubholdings","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
//   
//   public DTOCollection enableSubholdings(FrontEndContext fec,DTOCollection dtocollection) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","enableSubholdings","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.enableSubholdings(fec, this.copyBusinessObject(dtocollection));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","enableSubholdings","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","enableSubholdings","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","enableSubholdings","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
//   
//   public DTOCollection disableSubholdings(FrontEndContext fec,DTOCollection dtocollection) {
//   
//         DTOCollection ret = null;
//     try{
//         if (Tracer.isDebugEnabled(" OperatorserviceBean"))
//            Tracer.debug(" OperatorserviceBean","disableSubholdings","Guscio EJB", null);
//         ret = operatorserviceServiceLocal.disableSubholdings(fec, this.copyBusinessObject(dtocollection));
//   	  return this.copyBusinessObject(ret);
//     }catch(DAORuntimeException drte){
//         Tracer.error(" OperatorserviceBean","disableSubholdings","", drte);
//         mySessionCtx.setRollbackOnly();
//         ret = drte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(BusinessImplRuntimeException birte){
//         Tracer.error(" OperatorserviceBean","disableSubholdings","", birte);
//         mySessionCtx.setRollbackOnly();
//         ret = birte.getDTOCollection();
//         clearBeanData(ret);
//         return ret;
//     }catch(RuntimeException rte){
//         Tracer.error(" OperatorserviceBean","disableSubholdings","", rte);
//         mySessionCtx.setRollbackOnly();
//         return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
//     }
//   }
   
   
   public DTOCollection listOperatorsByCorporate(FrontEndContext fec,DTO dto) {
	   
       DTOCollection ret = null;
   try{
       if (Tracer.isDebugEnabled(" OperatorserviceBean"))
          Tracer.debug(" OperatorserviceBean","listOperatorsByCorporate","Guscio EJB", null);
       ret = operatorserviceServiceLocal.listOperatorsByCorporate(fec, this.copyBusinessObject(dto));
 	  return this.copyBusinessObject(ret);
   }catch(DAORuntimeException drte){
       Tracer.error(" OperatorserviceBean","listOperatorsByCorporate","", drte);
       mySessionCtx.setRollbackOnly();
       ret = drte.getDTOCollection();
       clearBeanData(ret);
       return ret;
   }catch(BusinessImplRuntimeException birte){
       Tracer.error(" OperatorserviceBean","listOperatorsByCorporate","", birte);
       mySessionCtx.setRollbackOnly();
       ret = birte.getDTOCollection();
       clearBeanData(ret);
       return ret;
   }catch(RuntimeException rte){
       Tracer.error(" OperatorserviceBean","listOperatorsByCorporate","", rte);
       mySessionCtx.setRollbackOnly();
       return new ManageBackEndException().getBusinessDTOCollectionByError(FrameworkMessage.BO_0001,DTOInfoInterface.SEVERITY_GENERIC);
   }
 }       
   
//   public DTO test(FrontEndContext fec,DTO dto) {
//      try{
//         Tracer.debug("OperatorserviceBean","test","Guscio EJB", null);
//         return operatorserviceServiceLocal.test(fec,dto);
//      }catch(RuntimeException rte){
//         return this.processException(dto, rte);
//      }
//   }
}
