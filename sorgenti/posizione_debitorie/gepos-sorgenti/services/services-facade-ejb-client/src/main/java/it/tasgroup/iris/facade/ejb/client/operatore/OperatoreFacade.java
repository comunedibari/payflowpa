/**
 * 
 */
package it.tasgroup.iris.facade.ejb.client.operatore;

import it.nch.is.fo.profilo.OperatoriPojo;

/**
 * @author pazzik
 *
 */
public interface OperatoreFacade {
	
		
	public void resetPasswdOperatore(String operatore, String newPasswd);
	
	public OperatoriPojo getOperatoreByCodFiscale(String codFiscaleOperatore);
	
	public OperatoriPojo getOperatoreById(String idOperatore) throws Exception;
	
}
