/*
 * Creato il 2-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.cross;

import it.nch.fwk.fo.core.AbstractCorporateContext;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.helper.ErrorManager;
import it.nch.fwk.fo.helper.ErrorManagerImpl;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;
import java.util.Vector;

import net.sf.dozer.util.mapping.MapperIF;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class AbstractJavaBean {
	
	protected ErrorManager em=new ErrorManagerImpl();
	protected CrossCuttingConcernServices cccs;

	public void setErrorManager(ErrorManager em) {
		this.em = em;
	}

	protected DTO processException(DTO dto, String code) {
		Tracer.error("AbstractjavaBean","method:processException",code,null);
		em.menageException(dto, code);
		return dto;
	}
	
	protected DTOCollection processException(DTOCollection dtoCollection, String code) {
		Tracer.error("AbstractjavaBean","method:processException",code,null);
		em.menageException(dtoCollection,code);
		return dtoCollection;
	}
	
	protected DTO processException(DTO dto, Exception e,String code) {
		Tracer.error("AbstractjavaBean","method:processException",e.getMessage(),null);
		em.menageException(dto,e, code);
		return dto;
	}
	
	protected DTOCollection processException(DTOCollection dtoCollection,Exception e, String code) {
		Tracer.error("AbstractjavaBean","method:processException",e.getMessage(),null);
		em.menageException(dtoCollection,e, code);
		return dtoCollection;
	}

	/**
	 * @return Restituisce il valore cccs.
	 */
	public CrossCuttingConcernServices getCccs() {
		return cccs;
	}
	/**
	 * @param cccs Il valore cccs da impostare.
	 */
	public void setCccs(CrossCuttingConcernServices cccs) {
		this.cccs = cccs;
	}
	
	protected void doAudit(AbstractCorporateContext bec,String method,String message)
	{
		cccs.doAudit(bec,this.getClass(),method,message);
	}
	
	protected void doSign(AbstractCorporateContext bec,byte[] sign)
	{
		cccs.doSign(bec,this.getClass(),sign);
	}
	
	protected DTOCollection dto2Collection(DTO dto){
		Collection c = new Vector();
		c.add(dto);
		return new DTOCollectionImpl(c);
		
	}
	
	protected MapperIF getBeanMapper(){
		BeanFactoryLocator bfl = SingletonBeanFactoryLocator.getInstance("server-beanRefFactory.xml");
		BeanFactoryReference bfr = bfl.useBeanFactory("it.nch.custom.server");
		BeanFactory bf = bfr.getFactory();
		MapperIF mapper =(MapperIF)bf.getBean("BEDozerBeanMapping");
		return mapper;
		
	}
	
	
}
