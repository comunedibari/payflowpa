/*
 * Creato il 5-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto;

import it.nch.fwk.fo.pager.PagingData;

import java.util.Collection;
import java.util.Set;

/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface DTOCollection extends FrameworkDTOInterface {
	
	public Set getSetDTO();
	
	public Set getCollection();	
	
	public void setDTOs(Set dtoCol);
	
	public boolean addDTO(DTO dto);
	
	public boolean hasInfo();
	
	public boolean hasWarning();
	
	public boolean hasError();
	
	public boolean isExpired();
	
	public PagingData getPagingData();
	
	public void setPagingData(PagingData pagingData);
	
	public Collection getPojoCollection();
	
}