package it.nch.fwk.fo.util;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.DTO;
import it.nch.fwk.fo.dto.DTOCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * 
 * DTO Utility
 * 
 */
public class DTOUtil {
	
	/**
	 * Returns the BusinessObject for the input DTOCollection
	 * 
	 * @param iter
	 * @return
	 */
	public static CommonBusinessObject getBusinessObject(Iterator i) {
		DTO ret = (DTO)i.next();
		return ret.getBusinessObject();
	}
	
	public static Collection getList(DTOCollection col) {
		
		ArrayList list = new ArrayList();
		if(col != null && col.getCollection()!=null){
			Iterator i = col.getCollection().iterator();
			while(i.hasNext()){
				DTO ret = (DTO)i.next();
				list.add(ret.getBusinessObject());
			}
			return list;
		}
		return null;
	}
	
	/**
	 * 
	 * Restituisce l'iterator dato il DTOCollection
	 * 
	 * @param c
	 * @return
	 */
	public static Iterator getIterator(DTOCollection c) {
		return c.getCollection().iterator();
	}
	
}
