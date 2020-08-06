package it.tasgroup.idp.bean;

import it.tasgroup.idp.notifiche.VerificaPagamentoModel;
import it.tasgroup.idp.pojo.VerificaStatoPagamentoInput;

public interface IVerificaStatoPagamento {

	@SuppressWarnings("serial")
	public static class VerificaPagamentoModelException extends Exception { 
		public VerificaPagamentoModelException(String s) {super(s);} 
	};
	
	VerificaPagamentoModel verificaPagamentoModel(VerificaStatoPagamentoInput input, boolean includeDetails) throws VerificaPagamentoModelException;
	
}
