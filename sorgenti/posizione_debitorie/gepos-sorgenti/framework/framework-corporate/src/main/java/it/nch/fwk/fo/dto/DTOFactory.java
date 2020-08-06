/*
 * Creato il 30-nov-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.util.Tracer;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.beans.factory.access.BeanFactoryReference;
import org.springframework.beans.factory.access.SingletonBeanFactoryLocator;




/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class DTOFactory {
		
	public static DTOFactory _DTOFactory;
	private static BeanFactoryLocator bfl=SingletonBeanFactoryLocator.getInstance();	
	private static BeanFactoryReference bfr=bfl.useBeanFactory("it.nch.orm");
	private static BeanFactory bf=bfr.getFactory();
	
	
	
	/**
	 * @depreacted
	 * @param beanName
	 * @return
	 */
	public DTO getDTO(String beanName) {
		
		
		bf.getBean(beanName);
	
		Pojo pojo=null;
		DTOInfo dtoinfo=null;
		
		DTO mydto = new DTOImpl();
		pojo = (Pojo) bf.getBean(beanName);
		mydto.setPojo(pojo);
				
		return mydto;
	}
	public DTO getDTOCommon(String beanName) {
		
		
		bf.getBean(beanName);
	
		CommonBusinessObject pojo=null;
		DTOInfo dtoinfo=null;
		
		DTO mydto = new DTOImpl();
		Tracer.debug(getClass().getName(), "getDTOCommon", "************DTOFactory: "+bf.getBean(beanName).getClass().getName());
		Tracer.debug(getClass().getName(), "getDTOCommon", "************beanName: "+beanName);
		
		pojo = (CommonBusinessObject) bf.getBean(beanName);
		mydto.setBusinessObject(pojo);
				
		return mydto;
	}
	/**
	 * @param mydto
	 */
	private void fillDTO(DTO mydto) {
		DTOInfo dtoinfo;
		dtoinfo=new DTOInfo("code","",DTOInfo.ERROR);
		mydto.setInfoList(new DTOInfoList(dtoinfo));
	}

	public DTOCollection getDTOCollection() {		
		return new DTOCollectionImpl();
	}
	
	public static DTOFactory getInstance(){
		
		if (_DTOFactory !=null)
			return _DTOFactory;
		else {
			_DTOFactory = new DTOFactory();
			return _DTOFactory;
		}
	}
	
}
