package it.tasgroup.iris.facade.ejb.operatori;

import it.nch.is.fo.profilo.OperatoriPojo;
import it.tasgroup.iris.business.ejb.client.anagrafica.OperatoriBusinessLocal;
import it.tasgroup.iris.facade.ejb.client.operatore.OperatoreFacadeLocal;
import it.tasgroup.iris.facade.ejb.client.operatore.OperatoreFacadeRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "OperatoriFacade")
public class OperatoriFacadeBean implements OperatoreFacadeLocal, OperatoreFacadeRemote {
	
	private static final Logger LOGGER = LogManager.getLogger(OperatoriFacadeBean.class);	
	
	
	@EJB(name = "OperatoriBusiness")
	private OperatoriBusinessLocal operatoriBusinessBean;
	
	@Override
	public void resetPasswdOperatore(String operatore, String newPasswd) {
		
		operatoriBusinessBean.resetPasswdOperatore(operatore, newPasswd);;
		return ;
	}
	
	@Override
	public OperatoriPojo getOperatoreByCodFiscale(String codFiscaleOperatore) {
		return operatoriBusinessBean.getOperatoreByCodFiscale(codFiscaleOperatore);
	}
	
	@Override
	public OperatoriPojo getOperatoreById(String idOperatore) throws Exception {
		return operatoriBusinessBean.getOperatoreById(idOperatore);
	}
	
	
}

