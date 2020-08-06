package it.tasgroup.iris.persistence.dao.interfaces;

import javax.ejb.Local;

import it.nch.is.fo.profilo.Operatori;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface OperatoriDAO extends Dao<Operatori> {

	public Operatori getOperatoreByUsername(String username);

	public Operatori getOperatoreByOperatoreAndCorporate(String operatore, String corporate);

	public Operatori getOperatoreByCodiceFiscale(String codiceFiscale);
	
	public void resetPasswdOperatore(String operatore, String newPasswd);
	
	public Operatori createOperatore(Operatori operatore);

}
