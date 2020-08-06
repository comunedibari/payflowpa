package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoEsitiPendenzaDao extends Dao<EsitiPendenza> {
	public Long countPendenzeCaricate(String e2emsgid, String senderid, String sendersys);
	public Long countPendenze(String e2emsgid, String senderid, String sendersys);
	public int updateEsitiPendenzaStato(List<PendenzeCart> pkList,String oldState,String newState);
	public int updateAllEsitiPendenzaStato(FilterVO filter, String newState);
	public List<EsitiPendenza> listPendenzeErrate(String e2emsgid, String senderid, String sendersys);

}
