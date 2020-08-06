
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.EsitiBonificiRiaccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiBonificiRiaccreditoDao;
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

@Stateless(name="EsitiBonificiRiaccreditoDaoService")
public class EsitiBonificiRiaccreditoDaoImpl extends DaoImplJpaCmtJta<EsitiBonificiRiaccredito> implements EsitiBonificiRiaccreditoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(EsitiBonificiRiaccreditoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoByIdBonifico(Long idBonifico) {
		
		EsitiBonificiRiaccredito esitiBonificiRiaccredito = null;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idBonificiRiaccredito", idBonifico);
		
		try {			
			esitiBonificiRiaccredito = (EsitiBonificiRiaccredito)uniqueResultByQuery("getEsitiBonificiRiaccreditoByIdBonifico",params,em);
		} catch (Exception e) {
			LOGGER.error("error on getEsitiBonificiRiaccreditoByIdBonifico "+params, e);		
			throw new DAORuntimeException(e);
		}
		return esitiBonificiRiaccredito;
	}
	
	@Override
	public List<EsitiBonificiRiaccredito> listEsitiBonificiRiaccreditoByIdRend(ContainerDTO containerDTO) {
		
		List<EsitiBonificiRiaccredito> retList = null;

		Long id = (Long) containerDTO.getInputDTOList().get(0);
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select esiti.* from ESITI_BONIFICI_RIACCREDITO esiti " +
							"where ID_RENDICONTAZIONE=:idRendicontazione ";
			
                         String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query = query + " and FLAG_RICONCILIAZIONE in (:flags) ";
                        }

			retList = paginateByQuery(EsitiBonificiRiaccredito.class, query, pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listEsitiBonificiRiaccreditoByIdRend ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public EsitiBonificiRiaccredito getEsitiBonificiRiaccreditoById(Long id) {
		
		EsitiBonificiRiaccredito esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (EsitiBonificiRiaccredito)uniqueResultByQuery("getEsitiBonificiRiaccreditoById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getEsitiBonificiRiaccreditoById ", e);
			throw new DAORuntimeException(e);
		}
		return esito;
	}

}
