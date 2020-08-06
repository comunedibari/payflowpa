package it.tasgroup.iris.business.ejb.anagrafica;

import it.nch.is.fo.profilo.Operatori;
import it.nch.is.fo.profilo.OperatoriPojo;
import it.tasgroup.iris.business.ejb.client.anagrafica.OperatoriBusinessLocal;
import it.tasgroup.iris.business.ejb.client.anagrafica.OperatoriBusinessRemote;
import it.tasgroup.iris.persistence.dao.interfaces.OperatoriDAO;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "OperatoriBusiness")
public class OperatoriBusinessBean implements OperatoriBusinessLocal, OperatoriBusinessRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(OperatoriBusinessBean.class);
	
	@EJB(name = "OperatoriDAO")
	OperatoriDAO operatoriDAO;
	
	@Override
	public Operatori getOperatoreByCodFiscale(String codFiscaleOperatore) {
		return operatoriDAO.getOperatoreByUsername(codFiscaleOperatore);
	}

	@Override
	public void resetPasswdOperatore(String operatore, String newPasswd) {
		operatoriDAO.resetPasswdOperatore(operatore, newPasswd);
		
	}
	
	@Override
	public OperatoriPojo getOperatoreById(String idOperatore) throws Exception {
		return operatoriDAO.getById(Operatori.class, idOperatore);
	}
}
