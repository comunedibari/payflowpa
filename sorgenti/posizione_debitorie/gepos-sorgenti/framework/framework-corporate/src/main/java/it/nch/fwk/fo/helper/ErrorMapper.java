/*
 * Creato il 12-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.helper;

import java.util.HashMap;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class ErrorMapper {
	
	protected HashMap hm;
	
	public ErrorMapper()
	{
		hm=new HashMap();
		hm.put("framework.backend.error","Generic Framework Error");
		hm.put("framework.backend.info","Generic Framework Info");
		hm.put("framework.backend.warning","Generic Framework Warning");
	
	}
	
	public ErrorMapper(HashMap hm)
	{
		this.hm=hm;
		this.hm.put("framework.backend.error","Generic Framework Error");
		this.hm.put("framework.backend.info","Generic Framework Info");
		this.hm.put("framework.backend.warning","Generic Framework Warning");
	
	}
	
	public String mapCode(String code)
	{
		return ((String)hm.get(code)).intern();
	}
	

}
