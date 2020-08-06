package it.tasgroup.idp.autorizzazionepagamento;

import javax.ejb.Local;

@Local
public interface AutorizzazionePagamentoController{
	public void  attivaPagamento(AttivaPagamentoRequestType request,AttivaPagamentoResponseType response) throws Exception;

}
