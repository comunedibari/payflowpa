package it.nch.fwk.fo.cross;


import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.dto.DTOCollectionImpl;
import it.nch.fwk.fo.helper.ErrorManager;
import it.nch.fwk.fo.interfaces.FrontEndContext;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Vector;

/*
 * Creato il 2-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */

/**
 * @author EE10057
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public abstract class AbstractEjb {
	protected ErrorManager em;

	public void setErrorManager(ErrorManager em) {
		this.em = em;
	}

	
	protected DTO processException(DTO dto, RuntimeException re) {
		Tracer.info("AbstractEJB","method:processException"," ","");
		re.printStackTrace();
		
		em.menageException(dto, re);
		return dto;
	}
	
	protected DTOCollection processException(DTOCollection dtoCollection, RuntimeException re) {
		Tracer.info("AbstractEJB","method:processException"," ","");
		re.printStackTrace();
		
		em.menageException(dtoCollection, re);
		return dtoCollection;
	}
	
	protected DTO processException(FrontEndContext fec,DTO dto, RuntimeException re) {
		Tracer.info("AbstractEJB","method:processException"," ","");
		re.printStackTrace();
		
		
		/**
		 * 
		 * 
		 * DA RIVEDERE DOVE FARE il CLEAR del SeSSION MANAGER
		 */
		//this.extractSessionManager(fec).clear();
		em.menageException(dto, re);
		return dto;
	}
	
	protected DTOCollection processException(FrontEndContext fec,DTOCollection dtoCollection, RuntimeException re) {
		Tracer.info("AbstractEJB","method:processException"," ","");
		re.printStackTrace();
		
		
		/**
		 * 
		 * 
		 * DA RIVEDERE DOVE FARE il CLEAR del SeSSION MANAGER
		 */
		//this.extractSessionManager(fec).clear();
		em.menageException(dtoCollection, re);
		return dtoCollection;
	}

	protected void InizializeInfo(DTO dto) {
		
		/*
		 * 
		 * 
		 * DA RIVEDERE
		 * 
		 */
		//dto.setInfoList(new DTOInfo(DTOInfo.INFO));
	}
	
	protected DTOCollection dto2Collection(DTO dto){
		Collection c = new Vector();
		c.add(dto);
		return new DTOCollectionImpl(c);
		
		
		
		
	}
	
	
	
	protected DTO copyBusinessObject(DTO dto){
		
		if (dto !=null && dto.getBusinessObject()!=null){
		
			//dto.getBusinessObject().show();
			dto.setBusinessObject(dto.getBusinessObject().copy());		
				
		}
		return dto;
	}
	
	protected DTOCollection copyBusinessObject(DTOCollection dto_c){
		Iterator it = dto_c.getCollection().iterator();
		Set dtos = new LinkedHashSet();
		Tracer.info(this.getClass().getName(),"copyBusinessObject","numerosita': "+dto_c.getCollection().size(),null);
		
		boolean isExist=false;
		while(it.hasNext()){
			DTO dto = (DTO)it.next();
		
			if (dto.getBusinessObject()!=null){
				
					//dto.getBusinessObject().show();
					dto.setBusinessObject(dto.getBusinessObject().copy());			
					dtos.add(dto);
					isExist = true;
				}			
		
		}
		
		if (isExist)
			dto_c.setDTOs(dtos);
		
			
		return dto_c;
		
		
	}

	protected void clearBeanData(DTOCollection dto_c){
		if (dto_c != null){	
			Iterator it = dto_c.getCollection().iterator();
			Set dtos = new LinkedHashSet();
			while(it.hasNext()){
				DTO dto = (DTO)it.next();
				dto.setBusinessObject(null);
				dto.setPojo(null);
				dtos.add(dto);
			}
			dto_c.setDTOs(dtos);
		}
	}

	protected void clearBeanData(DTO dto){
		if (dto != null){
			dto.setBusinessObject(null);
			dto.setPojo(null);
		} 
	}


}