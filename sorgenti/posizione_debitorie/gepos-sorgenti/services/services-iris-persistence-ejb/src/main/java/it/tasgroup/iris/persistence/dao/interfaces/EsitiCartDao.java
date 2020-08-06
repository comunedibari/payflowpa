package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface EsitiCartDao extends Dao<EsitiCart>{
	
	public int updateEsitiCartStato(List<EsitiCartPK> esistiCartListPK,String newState,String oldState);
	public int updateAllEsitiCartStato(FilterVO filter, String newState);
	

}