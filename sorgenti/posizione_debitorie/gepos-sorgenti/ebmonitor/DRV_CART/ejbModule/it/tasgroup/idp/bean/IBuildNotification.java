package it.tasgroup.idp.bean;

import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;

import java.util.List;

import javax.persistence.EntityManager;

public interface IBuildNotification {
    
	public void build(List<NotifichePagamenti> notifList, String statoNotificaPagamento,DataStorageInterface dataTx, EntityManager em) throws Exception;
}
