package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotificheCartPK;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface NotificheCartDao extends Dao<NotificheCart>{
	
	public List<NotificheCart> getByFilterParametersPaginated(FilterVO filter, PagingCriteria pagingCriteria, PagingData pagingData);
	public int updateNotificheCartStato(List<NotificheCartPK> listNotifichePK, String oldState, String newState);
	public int updateMoreNotificheStato(List<NotificheCartPK> listNotifichePK, List<String> oldStateList, String newState);
	public int updateAllNotificheStato(FilterVO filter,String newState);


	
}