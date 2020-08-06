package it.tasgroup.iris.persistence.dao.interfaces;

import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.services.dao.ejb.Dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ErroriCartDao extends Dao<ErroriCart>{
	
	public List<ErroriCart> getByFilterParametersPaginated(FilterVO filter, PagingCriteria pagingCriteria, PagingData pagingData);


	
}