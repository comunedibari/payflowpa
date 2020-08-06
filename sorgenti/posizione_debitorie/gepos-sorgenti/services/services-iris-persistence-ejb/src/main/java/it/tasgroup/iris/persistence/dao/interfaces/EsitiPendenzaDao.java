package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.EsitiPendenza;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface EsitiPendenzaDao extends Dao<EsitiPendenza>{

	public List<Object[]> groupByDescrizioneEsito(ContainerDTO dtoIn);
	
	public Long countPendenzeCaricate(String e2emsgid, String senderid, String sendersys);
	
	public Long countPendenze(String e2emsgid, String senderid, String sendersys);
	
	public List<EsitiPendenza> listPendenzeErrate(String e2emsgid, String senderid, String sendersys);
	
	public int updateEsitiPendenzaStato(List<PendenzeCart> pkList,String oldState,String newState);

	public int updateAllEsitiPendenzaStato(FilterVO filter, String newState);
}