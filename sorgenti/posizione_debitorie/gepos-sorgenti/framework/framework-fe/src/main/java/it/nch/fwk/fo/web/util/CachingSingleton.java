/*
 * Creato il 24-gen-2006
 */
package it.nch.fwk.fo.web.util;
/*
import it.nch.fwk.fo.actionworker.ActionWorker;
import it.nch.fwk.fo.core.interfaces.ActionworkerService;
import it.nch.fwk.fo.core.interfaces.ActionworkerServiceLocal;
import it.nch.fwk.fo.cross.AbstractBusinessDelegate;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.locator.ServiceLocatorException;
import it.nch.fwk.fo.pojo.ActionwrkPojo;
import it.nch.fwk.fo.util.Tracer;
import java.rmi.RemoteException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;
*/
/**
 * @author EE10802
 */

public class CachingSingleton {
	/*
	private ActionworkerServiceLocal actionWorkerServiceLocal;
	private ActionworkerService actionWorkerService;	
	
	private BeanFactoryLocator bfl;
	private BeanFactoryReference bfr;
	private BeanFactory bf;
	
	private static CachingSingleton caching;
	
	private CachingSingleton() throws ServiceLocatorException
	{		
		Tracer.info(this.getClass().toString(),"Costruttore","Inizio ",null);
		bfl = SingletonBeanFactoryLocator.getInstance();		
		bfr = bfl.useBeanFactory("businessDelegate");				
		Tracer.info(this.getClass().toString(), "costruttore", "Retrieve della bean factory", null);
		
		bf=bfr.getFactory();		
		Tracer.info(this.getClass().toString(), "costruttore", "Retrieve del bean", null);
		
		try {			
			if (bf.containsBean("actionworkerLocal")) 
				actionWorkerServiceLocal= (ActionworkerServiceLocal) bf.getBean("actionworkerLocal");
			else if (bf.containsBean("actionworker")) 
				actionWorkerService= (ActionworkerService) bf.getBean("actionworker");
			else
				throw new ServiceLocatorException("Nessun Servizio Action Worker Trovato nella configurazione");			
		} catch (BeansException e) {
			throw new ServiceLocatorException("Nessun Servizio Prototype Configurato Correttamente",e);
		}		
	}
	
	public static CachingSingleton getInstance() throws ServiceLocatorException
	{
		if (caching == null)
			caching = new CachingSingleton();
		return caching;
	}	
	*/	
	/*
	public ActionWorker getActionWorker(FrontEndContext fec, DTO dto, DTOCriteria criteria)
	{		
	    Tracer.info(this.getClass().toString(), "getActionWorker", "", null);
	    Tracer.debug(this.getClass().getName(), "getActionWorker", "fec ["+fec.toString()+"] dto ["+dto+"] criteria ["+criteria+"]", null);
	    
		if (actionWorkerServiceLocal != null) {
				Tracer.info(this.getClass().toString(), "getActionWorker", "utilizzo actionWorkerService LOCAL", null);				
				DTO dtoRet = actionWorkerServiceLocal.getActionWorker(fec, dto);
				Tracer.debug(this.getClass().getName(), "getActionWorker", "dtoRet ["+dtoRet+"]", null);				
			    ActionwrkPojo actwrk = (ActionwrkPojo)dtoRet.getPojo();			    
			    if (actwrk != null)
			    	return populateBD(dto.getActionWorker(), actwrk);
			    return null;
		} else {			
			try {				
			    Tracer.info(this.getClass().toString(), "getActionWorker", " utilizzo actionWorkerService REMOTE", null);			    
			    DTO dtoRet = actionWorkerService.getActionWorker(fec, dto);
			    Tracer.debug(this.getClass().getName(), "getActionWorker", "dtoRet ["+dtoRet+"]", null);
			    //Tracer.debug(this.getClass().getName(), "getActionWorker", "dtoRet.getPojo() ["+dtoRet.getPojo()+"]", null);
			    ActionwrkPojo actwrk = (ActionwrkPojo)dtoRet.getPojo();
			    //Tracer.debug(this.getClass().getName(), "getActionWorker", "actwrk ["+actwrk+"]", null);
			    if (actwrk != null)
			    	return populateBD(dto.getActionWorker(), actwrk);			    
				 return null;
			} catch (RemoteException e) {
				Tracer.error(this.getClass().toString(), "getActionWorker", "REMOTE EXCEPTION", null);
				e.printStackTrace();
				return null;
			} 
		}
	}
	*/
	
	/*
	
	public DTO getActionWorker(FrontEndContext fec, DTO dto)
	{		
	    Tracer.info(this.getClass().toString(), "getActionWorker", "", null);
	    Tracer.debug(this.getClass().getName(), "getActionWorker", "fec ["+fec.toString()+"] dto ["+dto+"] criteria ["+dto.getCriteria()+"]", null);
	    
		if (actionWorkerServiceLocal != null) {
				Tracer.info(this.getClass().toString(), "getActionWorker", "utilizzo actionWorkerService LOCAL", null);				
				DTO dtoRet = actionWorkerServiceLocal.getActionWorker(fec, dto);
				Tracer.debug(this.getClass().getName(), "getActionWorker", "dtoRet ["+dtoRet+"]", null);				
			    ActionwrkPojo actwrk = (ActionwrkPojo)dtoRet.getActionWorker().getActwrkPojo();			    
			    if (actwrk != null)
			    	return populateBD(dto.getActionWorker(), dto);
			    return null;
		} else {			
			try {				
			    Tracer.info(this.getClass().toString(), "getActionWorker", " utilizzo actionWorkerService REMOTE", null);			    
			    dto = actionWorkerService.getActionWorker(fec, dto);
			    Tracer.debug(this.getClass().getName(), "getActionWorker", "dtoRet ["+dto+"]", null);
			    //Tracer.debug(this.getClass().getName(), "getActionWorker", "dtoRet.getPojo() ["+dtoRet.getPojo()+"]", null);
			    ActionwrkPojo actwrk = (ActionwrkPojo)dto.getActionWorker().getActwrkPojo();
			   
			    //Tracer.debug(this.getClass().getName(), "getActionWorker", "actwrk ["+actwrk+"]", null);
			    if (actwrk != null)
			    	return populateBD(dto.getActionWorker(), dto);			    
				 return null;
			} catch (RemoteException e) {
				
				Tracer.error(this.getClass().toString(), "getActionWorker", "REMOTE EXCEPTION", null);
				e.printStackTrace();
				return null;
			} 
		}
	}
	
	private DTO populateBD(ActionWorker aw, DTO dto){
		
		try {
			
			ActionwrkPojo pojo  = (ActionwrkPojo)dto.getActionWorker().getActwrkPojo();
			
			AbstractBusinessDelegate abd = (AbstractBusinessDelegate) bf.getBean(pojo.getBusinessDelegate());
			aw.setAbstractBD(abd);
		} catch (Exception e) {
		    Tracer.error(this.getClass().toString(), "populateBD", "EXCEPTION", null);
			e.printStackTrace();
			return null;
		}
		dto.setActionWorker(aw);
		return dto;
	}
	*/
	/*
	private ActionWorker populateActionWorker(ActionwrkPojo actwrk, DTOCriteria criteria){
		Tracer.info(this.getClass().getName(), "populateActionWorker", "", null);			
		Tracer.debug(this.getClass().getName(), "populateActionWorker", "actwrk ["+actwrk+"] criteria ["+criteria+"]", null);
		ActionWorker aw = new ActionWorker();
		String strCriteria = "";		
		if (criteria != null) {
			HibernateCriteriaAdapter adapter = new HibernateCriteriaAdapter();
			adapter.setDtoCriteria(criteria);
			strCriteria = adapter.dtoPreConditionToString();			
			Tracer.debug(this.getClass().getName(), "populateActionWorker", "strCriteria ["+strCriteria+"]", null);
		}
		
		//costruisco l'HQL per fare la query
		String hqlTemplate = actwrk.getHqlTemplate();
		String hqlQuery = "";
		String hqlComplete = "";
		//Tracer.debug(this.getClass().getName(), "populateActionWorker", "hqlTemplate ["+hqlTemplate+"]", null);
		if (strCriteria.trim().length() > 0)
			hqlQuery = hqlTemplate + " " + strCriteria;
		else
			hqlQuery = hqlTemplate;
		//Tracer.debug(this.getClass().getName(), "populateActionWorker", "hqlQuery ["+hqlQuery+"]", null);
		if (actwrk.getPostCondition() != null)
			hqlComplete = actwrk.getPostCondition().trim() + " " + hqlQuery;
		else
			hqlComplete = hqlQuery;
		if (actwrk.getFinalCondition() != null && actwrk.getFinalCondition().trim().length() > 0 )
			hqlComplete += " " +actwrk.getFinalCondition();
		Tracer.debug(this.getClass().getName(), "populateActionWorker", "hqlComplete ["+hqlComplete+"]", null);
		aw.setActwrkPojo(actwrk);
		aw.setHQLQuery(hqlComplete);
		
		//costruisco l'HQL per fare la count dei risultati estratti con la precedente query
		String hqlCountQuery = hqlComplete = "select count(*) " + hqlQuery;
		//Tracer.debug(this.getClass().getName(), "populateActionWorker", "hqlCountQuery ["+hqlCountQuery+"]", null);
		aw.setHQLCountQuery(hqlCountQuery);
		
		
		try {
			AbstractBusinessDelegate abd = (AbstractBusinessDelegate) bf.getBean(actwrk.getBusinessDelegate());
			aw.setAbstractBD(abd);
		} catch (Exception e) {
		    Tracer.error(this.getClass().toString(), "populateActionWorker", "EXCEPTION", null);
			e.printStackTrace();
			return null;
		}
		return aw;		
	}
	*/
}
