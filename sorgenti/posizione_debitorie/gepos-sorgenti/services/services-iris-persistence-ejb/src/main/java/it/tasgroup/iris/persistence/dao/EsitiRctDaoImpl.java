
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.EsitiRct;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiRctDao;
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

@Stateless(name="EsitiRctDaoService")
public class EsitiRctDaoImpl extends DaoImplJpaCmtJta<EsitiRct> implements EsitiRctDao {
	
	private static final Logger LOGGER = LogManager.getLogger(EsitiRctDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<EsitiRct> listEsitiRctByIdRendicontazione(ContainerDTO containerDTO) {
		
		List<EsitiRct> retList = null;

		Long idrend = (Long) containerDTO.getInputDTOList().get(0);

		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", idrend);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select esiti.* from ESITI_RCT esiti " +
							"where ID_RENDICONTAZIONI=:idRendicontazione ";
                        
                         String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";
                        }
			
			retList = paginateByQuery(EsitiRct.class, query, pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listEsitiRctByIdRendicontazione ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	
	@Override
	public List<EsitiRct> listEsitiRctByIdRendicontazioneAndTipoEsito(ContainerDTO containerDTO) {
		
		List<EsitiRct> retList = null;

		Long idrend = (Long) containerDTO.getInputDTOList().get(0);
		String tipoesito = (String) containerDTO.getInputDTOList().get(1);
		
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", idrend);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select esiti.* from ESITI_RCT esiti " +
							"where ID_RENDICONTAZIONI=:idRendicontazione ";
			
			if(tipoesito != null && !tipoesito.equals("")) {
				
				params.put("tipoesito", tipoesito);
				
				query = query + "and TIPO_RECORD=:tipoesito";
			}
                        
                        String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";
                        }
			
			retList = paginateByQuery(EsitiRct.class, query, pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listEsitiRctByIdRendicontazione ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public EsitiRct getEsitiRctById(Long id) {
		
		EsitiRct esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (EsitiRct)uniqueResultByQuery("getEsitiRctById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getEsitiRctById ", e);
			throw new DAORuntimeException(e);
		}
		return esito;
	}

}
