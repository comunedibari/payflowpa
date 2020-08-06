package it.tasgroup.iris.business.ejb.client.anagrafica;

import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.profilo.OperatoriPojo;


public interface OperatoriBusiness {

	public Operatori getOperatoreByCodFiscale(String codFiscaleOperatore);
	
	public void resetPasswdOperatore(String operatore, String newPasswd);
	
	public OperatoriPojo getOperatoreById(String idOperatore) throws Exception;

}
