
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.EsitiBb;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiBbDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="EsitiBbDaoService")
public class EsitiBbDaoImpl extends DaoImplJpaCmtJta<EsitiBb> implements EsitiBbDao {
	
	private static final Logger LOGGER = LogManager.getLogger(EsitiBbDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<EsitiBb> listEsitiBbByIdRendicontazione(ContainerDTO containerDTO) {
		
		List<EsitiBb> retList = null;

		Long id = (Long) containerDTO.getInputDTOList().get(0);
                
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select esiti.* from ESITI_BB esiti " +
							"where ID_RENDICONTAZIONE=:idRendicontazione ";
                        
                         String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";
                        }
			
			retList = paginateByQuery(EsitiBb.class, query, pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listEsitiBbByIdRendicontazione ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public EsitiBb getEsitiBbById(Long id) {
		
		EsitiBb esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (EsitiBb)uniqueResultByQuery("getEsitiBbById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getEsitiBbById ", e);
			throw new DAORuntimeException(e);
		}
		return esito;
	}

}
