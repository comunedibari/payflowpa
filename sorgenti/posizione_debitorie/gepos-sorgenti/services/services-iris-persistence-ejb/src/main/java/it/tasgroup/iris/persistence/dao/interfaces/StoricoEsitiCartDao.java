package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.EsitiCartPK;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoEsitiCartDao extends Dao<EsitiCart>{
	public int updateEsitiCartStato(List<EsitiCartPK> esistiCartListPK,String newState,String oldState);
	public int updateAllEsitiCartStato(FilterVO filter, String newState);
}