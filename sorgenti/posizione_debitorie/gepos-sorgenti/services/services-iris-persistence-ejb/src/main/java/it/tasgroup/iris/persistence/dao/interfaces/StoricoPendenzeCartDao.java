package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartPK;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoPendenzeCartDao extends Dao<PendenzeCart> {
	
	public List<PendenzeCart> getByFilterParametersPaginated(FilterVO filterVO, PagingCriteria pagingCriteria, PagingData pagingData);
	int updatePendenzeCartStato(List<PendenzeCartPK> pkList,String oldState,String newState);
	int updateAllPendenzeCartStato(FilterVO filter,String newState);
	
}
