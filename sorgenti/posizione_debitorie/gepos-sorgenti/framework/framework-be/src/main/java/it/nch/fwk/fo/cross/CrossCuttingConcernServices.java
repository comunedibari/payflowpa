/*
 * Creato il 13-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.cross;

import it.nch.fwk.fo.core.AbstractCorporateContext;
import it.nch.fwk.fo.util.Tracer;

/**
 * @author EE10057
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CrossCuttingConcernServices {
	
	public void doAudit(AbstractCorporateContext bec,Class clazz,String method,String message)
	{
		Tracer.info(clazz.toString(),method,message.concat(bec.toString()),null);
	}
	
	public void doSign(AbstractCorporateContext bec,Class clazz,byte[] ba)
	{
		Tracer.info(this.getClass().toString(),"doSign",new String(ba).toString(),null);
	}
}
