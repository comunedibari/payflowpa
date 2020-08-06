package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.idp.backoffice.bacheca.BachecaNewsVO;
import it.tasgroup.iris.domain.BachecaNews;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.BachecaNewsDao;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="BachecaNewsDaoService")
public class BachecaNewsDaoImpl extends DaoImplJpaCmtJta<BachecaNews> implements BachecaNewsDao {
	
	private static final Logger log = LogManager.getLogger(BachecaNewsDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public List<BachecaNews> getBachecaNewsList(ContainerDTO containerDTO) {
		List<BachecaNews> retList = null;
		@SuppressWarnings("rawtypes")
		Map parameters = new HashMap();
		BachecaNewsVO inputVO = (BachecaNewsVO) containerDTO.getInputDTO();
		try {
			String filteringQuery = buildQueryAndParameters(inputVO, parameters);
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			PagingData pagingData = containerDTO.getPagingData();
			retList = paginateByQuery(BachecaNews.class, filteringQuery, pagingCriteria, pagingData, parameters);
		} catch (Exception e) {
			log.error("error on getBachecaNewsList ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<BachecaNews> getBachecaNewsListToDisplay (ContainerDTO containerDTO) {
		List<BachecaNews> retList = null;
		
		Map parameters = new HashMap();
		try {
			String filteringQuery = "Select bn.* from BACHECA_NEWS bn where " +
					":theDate BETWEEN bn.data_Decorrenza AND bn.data_Scadenza " + 
					"ORDER BY bn.priorita DESC, bn.data_Decorrenza";
			java.sql.Date theDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
			parameters.put("theDate", theDate);
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			PagingData pagingData = containerDTO.getPagingData();
			retList = paginateByQuery(BachecaNews.class, filteringQuery, pagingCriteria, pagingData, parameters);
		} catch (Exception e) {
			log.error("error on getBachecaNewsList ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public boolean deleteBachecaNews(Long idToDelete) {
        try {            
            BachecaNews notizia = em.find(BachecaNews.class, idToDelete);
            em.remove(notizia);
        } catch(Exception e){
            log.error("Errore durante la cancellazione della notizia: ", e);
            return false;
        }
        return true;
    }

	
	private String buildQueryAndParameters(BachecaNewsVO dto, Map parameters) {
		String query = "Select bn.* ";
        String from = " from BACHECA_NEWS bn ";
        String where = " where 1=1 ";
		String orderingCondition = "";
		StringBuffer whereConditions = new StringBuffer("");
		Date decorrenza = dto.getDecorrenza();
 		if (decorrenza != null ){
			whereConditions.append(" AND bn.DATA_DECORRENZA <=:decorrenza ");			
			parameters.put("decorrenza", decorrenza);
		}
		Date scadenza = dto.getScadenza();
		if (scadenza != null ){
			whereConditions.append(" AND bn.DATA_SCADENZA >=:scadenza ");
			parameters.put("scadenza", UtilDate.setOrarioEndOfDay(scadenza));
		}
		orderingCondition = " ORDER BY bn.DATA_DECORRENZA ";
		return query + from + where + whereConditions + orderingCondition;	
	}

	@Override
	public BachecaNews retrieveBachecaNewsById(Long id) {
		BachecaNews retrieved = null;
		try {
			retrieved = loadById(BachecaNews.class, id);
		} catch (Exception e) {
			log.error("error on  retrieveBachecaNewsById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}


	@Override
	public BachecaNews updateBachecaNews(BachecaNews bachecaNews) {
		try {
			BachecaNews bnDB = loadById(BachecaNews.class, bachecaNews.getId());
			bachecaNews.setTsAggiornamento(new Timestamp(System.currentTimeMillis()));
			bachecaNews.setOpInserimento(bnDB.getOpInserimento());
			bachecaNews.setTsInserimento(bnDB.getTsInserimento());
			bachecaNews.setVersion(bnDB.getVersion());
			bachecaNews = this.update(bachecaNews);
		} catch (Exception e) {
			log.error("error on updateBachecaNews", e);
			bachecaNews = null;
		}
		return bachecaNews;
	}


	@Override
	public BachecaNews insertBachecaNews(BachecaNews bachecaNews) {
		
		try {
			bachecaNews.setTsInserimento(new Timestamp(System.currentTimeMillis()));
			bachecaNews.setVersion(0l);
			bachecaNews = this.create(bachecaNews);
		} catch (Exception e) {
			log.error("error on updateBachecaNews", e);
			bachecaNews = null;
		}
		return bachecaNews;
	}

}
