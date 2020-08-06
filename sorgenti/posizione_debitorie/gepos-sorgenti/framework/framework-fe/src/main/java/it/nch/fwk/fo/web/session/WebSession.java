package it.nch.fwk.fo.web.session;

import it.nch.fwk.fo.dto.DTOCollection;
import it.nch.fwk.fo.interfaces.FrontEndContext;

import java.io.Serializable;
import java.util.HashMap;

public class WebSession implements Serializable {


	private static final long serialVersionUID = -2831466523250311487L;

	private DTOCollection collectionDTO = null;

	private HashMap mapCollection = new HashMap();

	private FrontEndContext context = null;
	
	private HashMap mapSession =new HashMap();

	/**
	 * @return Returns the collectionDTO.
	 */
	public DTOCollection getCollectionDTO() 
	{ return collectionDTO; }

	public DTOCollection getCollectionDTO(String key) 
	{ return (DTOCollection)mapCollection.get (key) ; }

	/**
	 * @param collectionDTO
	 *            The collectionDTO to set.
	 */

	public void setCollectionDTO(String key, DTOCollection collectionDTO) 
	{
		mapCollection.put(key, collectionDTO);
		this.setCollectionDTO(collectionDTO);
	}

	public void resetCollectionDTO(String key) 
	{
		mapCollection.put(key, null);
		this.setCollectionDTO(null);
	}

	public void resetAllCollectionsDTO() 
	{ mapCollection.clear(); }

	private void setCollectionDTO(DTOCollection collectionDTO) 
	{ this.collectionDTO = collectionDTO; }

	/**
	 * @return Returns the context.
	 */
	public FrontEndContext getContext() 
	{ return this.context; }

	/**
	 * @param context
	 *            The context to set.
	 */
	public void setContext(FrontEndContext context) 
	{ this.context = context; }

	
	
	public Object getSessionElement(String key) 
	{ return mapSession.get (key) ; }

	

	public void setSessionElement(String key, Object object) 
	{
		mapSession.put(key, object);		
	}
	
	
	public void resetMapSession() 
	{ mapSession.clear(); }

		
		
	
}