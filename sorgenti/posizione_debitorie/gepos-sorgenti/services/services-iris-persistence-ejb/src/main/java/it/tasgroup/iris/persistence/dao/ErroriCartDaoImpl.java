package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.tavolooperativo.FilterVO;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.iris.persistence.dao.interfaces.ErroriCartDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name = "ErroriCartDaoService")
public class ErroriCartDaoImpl extends DaoImplJpaCmtJta<ErroriCart> implements ErroriCartDao {

	private static final Logger LOGGER = LogManager.getLogger(ErroriCartDaoImpl.class);
	
	@PersistenceContext(unitName = "IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	public List<ErroriCart> getByFilterParametersPaginated(FilterVO filterVO, PagingCriteria pagingCriteria, PagingData pagingData) {

		
		String query = "select e.* from ERRORI_CART e where 1=1 ";
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		
		if(filterVO.getDataCaricamentoDa()!=null )
		{
			query+=" AND (e.ts_inserimento  >= :dataCaricamentoDa) ";
			parameters.put("dataCaricamentoDa",filterVO.getDataCaricamentoDa());		
		}

		if(filterVO.getDataCaricamentoA()!=null )
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(filterVO.getDataCaricamentoA());  
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);         
			cal.set(Calendar.MILLISECOND,999);  
			//int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
			query+=" AND (e.ts_inserimento  <= :dataCaricamentoA) ";
			parameters.put("dataCaricamentoA",cal.getTime());		
		}		
		
		query +=" order by e.ts_inserimento desc ";

		try {
			return paginateByQuery(ErroriCart.class, query, pagingCriteria, pagingData, parameters);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new DAORuntimeException(e);
		}
		
	}

	
}