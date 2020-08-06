package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface NotifichePagamentoDao extends Dao<NotifichePagamenti>{
	
	public NotifichePagamenti getNotifichePagamentiByIdPagamentiAndStato(Long idPagamento, String statoPagamento);


	
}