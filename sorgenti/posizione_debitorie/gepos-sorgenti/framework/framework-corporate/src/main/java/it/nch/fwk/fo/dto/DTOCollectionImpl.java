/*
 * Creato il 5-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import it.nch.fwk.fo.common.CommonBusinessObject;
import it.nch.fwk.fo.dto.business.Pojo;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.fwk.fo.util.Tracer;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class DTOCollectionImpl extends FrameworkDTO implements DTOCollection {
	
	/**
	 * variabili
	 */
	protected LinkedHashSet pojoColl = null;
	protected PagingData pagingData = null;

	/**
	 * costruttori
	 */
	public DTOCollectionImpl() {
		pojoColl = new LinkedHashSet(); 
	}
	
	public DTOCollectionImpl(Collection c) {
		pojoColl = new LinkedHashSet();
		try {
			if ((c != null) && !c.isEmpty()) {
				Iterator i = c.iterator();
				
				while (i.hasNext()) {
					try {
						Object obj = (Object) i.next();						
						DTOImpl mydto = new DTOImpl();
						Pojo po = (Pojo)obj;
						mydto.setPojo(po);
						pojoColl.add( mydto);
					
					} catch (Exception ex1) {
						Tracer.info("DTOCollectionImpl","construct" ," ", ex1.toString());
						ex1.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			Tracer.info("DTOCollectionImpl","construct"," ", ex.toString());
			throw new RuntimeException("error creating DTOCollectionImpl class");
		}	
	}
	
	public DTOCollectionImpl(Collection c,boolean isForm) {
		
		/*if (isForm)
		{
			this.isForm = isForm;*/
			pojoColl = new LinkedHashSet();
		
			try {
				if ((c != null) && !c.isEmpty()) {
					Iterator i = c.iterator();				
					while (i.hasNext()) {
						try {
							Object obj = (Object) i.next();						
							DTOImpl mydto = new DTOImpl();
							CommonBusinessObject po = (CommonBusinessObject)obj;
							mydto.setBusinessObject(po);						
							pojoColl.add( mydto);
							
						} catch (Exception ex1) {
							ex1.printStackTrace();
							//Tracer.info("DTOCollectionImpl","construct" ," ", ex1.toString());						
						}
					}
				}
			} catch (Exception ex) {
				Tracer.info("DTOCollectionImpl","construct"," ", ex.toString());
				throw new RuntimeException("error creating DTOCollectionImpl class");
			}
		/*}
		else {
			new DTOCollectionImpl(c);
		}*/
	}	
	
	/*
	 * TODO: da rivedere
	 * Costruisce la DTOCollection in cui l'Hashtable ha lo stesso ordine del vettore
	 */
	public DTOCollectionImpl(List vec) {
		pojoColl = new LinkedHashSet();
		try {
				if ((vec != null) && !vec.isEmpty()) {
					for(int iw = 0 ; iw < vec.size(); iw++) {
						try {
							Object obj = (Object) vec.get(iw);
							DTOImpl mydto = new DTOImpl();
							Pojo po = (Pojo)obj;
							mydto.setPojo(po);
							pojoColl.add( mydto);
							
						} catch (Exception ex1) {
							Tracer.info("DTOCollectionImpl","construct" ,"vector", ex1.toString());
							break;
						}
					}
				}				
			} catch (Exception ex) {
				Tracer.info("DTOCollectionImpl","construct"," ", ex.toString());
				throw new RuntimeException("error creating DTOCollectionImpl class");
			}
	}
	
	
	public DTOCollectionImpl(Iterator it) {
		pojoColl = new LinkedHashSet();
		try {
			if ((it != null)) {			
				while (it.hasNext()) {
					try {
						//Object obj = (Object) it.next();						
						DTOImpl mydto = new DTOImpl();
						Pojo po = (Pojo)it.next();
						mydto.setPojo(po);
						pojoColl.add( mydto);
						
					} catch (Exception ex1) {
						Tracer.info("DTOCollectionImpl","construct" ," ciclo annidato ", ex1.toString());
						ex1.printStackTrace();
					}
				}
			}
		} catch (Exception ex) {
			Tracer.info("DTOCollectionImpl","construct"," ciclo esterno", ex.toString());
			throw new RuntimeException("error creating DTOCollectionImpl class");
		}
	}
	
	/**
	 * 
	 * @deprecated
	 * use getCollection() instead
	 */
	public Set getSetDTO() {
		return pojoColl;
	}
	
	
	
	public Set getCollection() {		
		return pojoColl;	
	}
	
	public void setDTOs(Set dtoCol) {
		pojoColl = (LinkedHashSet)dtoCol;
	}

	
	
	public boolean addDTO(DTO dto) {
		return this.pojoColl.add(dto);
	}
	
	/**
	 * @deprecated In alternativa verrà fornito un metodo searchSeverity
	 * @param infoType
	 * @return
	 */
	private boolean searchInfoType(short infoType) {
		if (infoList.size() == 0)
			return false;
		else 
		{
			for (int i = 0; i < infoList.size(); i++) {
				DTOInfo di = infoList.getInfo(i);
				if (di.getInfoType() == infoType)
					return true;
			}
			return false;
		}
	} 
	
	public boolean hasInfo() {
		return searchInfoType(DTOInfo.INFO);
	}
	
	public boolean hasWarning() {
		return searchInfoType(DTOInfo.WARNING);
	}
	
	public boolean hasError() {
		return searchInfoType(DTOInfo.ERROR);
	}

	/* (non Javadoc)
	 * @see it.nch.fwk.fo.dto.FrameworkDTOInterface#getDTOInfo()
	 */
	public DTOInfoInterface getDTOInfo() {
		// TODO Stub di metodo generato automaticamente
		return null;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.fo.dto.DTOCollection#isExpired()
	 */
	public boolean isExpired() 
	{
		// TODO
		return false;
	}
	
	public PagingData getPagingData() 
	{
		return pagingData; 
	}
	
	public void setPagingData(PagingData pagingData) 
	{
		this.pagingData = pagingData;
	}

	public Collection getPojoCollection() 
	{
		//if (!isForm)
		//{	
			Collection retColl = new LinkedHashSet();
			//if (pojoColl != null) {
				Iterator iter = pojoColl.iterator();
				while (iter.hasNext()) {
					Pojo p = ((DTOImpl)iter.next()).getPojo();
					if (p != null)
						retColl.add(p);
				}
				return retColl;
			//}
		//}	
		//return null;
	}
	
}
