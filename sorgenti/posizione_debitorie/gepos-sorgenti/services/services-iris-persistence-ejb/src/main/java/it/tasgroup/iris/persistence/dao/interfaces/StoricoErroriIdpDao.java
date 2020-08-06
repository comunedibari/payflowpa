package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import javax.ejb.Local;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.services.dao.ejb.Dao;

@Local
public interface StoricoErroriIdpDao extends Dao<ErroriIdp>{
	public List<ErroriIdp> getByFilterParametersPaginated(FilterVO filter, String tipoMessaggio, PagingCriteria pagingCriteria, PagingData pagingData);
	
	public ErroriIdp getNotificaScartataByE2eMsgId(String e2emsgid);
}