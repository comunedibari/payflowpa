
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.EsitiNdp;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiNdpDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import it.tasgroup.services.util.enumeration.EnumFlagRiconciliazioneEsiti;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="EsitiNdpDaoService")
public class EsitiNdpDaoImpl extends DaoImplJpaCmtJta<EsitiNdp> implements EsitiNdpDao {
	
	private static final Logger LOGGER = LogManager.getLogger(EsitiNdpDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<EsitiNdp> listEsitiNdpByIdRendicontazione(ContainerDTO containerDTO) {
		
		List<EsitiNdp> retList = null;

		Long id = (Long) containerDTO.getInputDTOList().get(0);
                // containerDTO.getInputDTOList().get(1) contiene tipoFlusso
                String esitoanomalo = (String) containerDTO.getInputDTOList().get(2);
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			StringBuilder query= new StringBuilder("select esiti.* from ESITI_NDP esiti " +
							"where esiti.ID_RENDICONTAZIONI=:idRendicontazione ");
                        
            if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                params.put("flags", flags);
                query.append(" and esiti.FLAG_RICONCILIAZIONE in (:flags)");
            }
			
			retList = paginateByQuery(EsitiNdp.class, query.toString(), pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listEsitiNdpByIdRendicontazione ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public EsitiNdp getEsitiNdpById(Long id) {
		
		EsitiNdp esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (EsitiNdp)uniqueResultByQuery("getEsitiNdpById",params,em);

		} catch (Exception e) {
			
			LOGGER.error("error on getEsitiNdpById ", e);
			
			throw new DAORuntimeException(e);
			
		}
		return esito;
	}
	
	@Override
	public List<EsitiNdp> getEsitiNdpByIdFlusso(String idFlusso) {
		
		List<EsitiNdp> esiti = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idFlusso", idFlusso);
			
			esiti = (List<EsitiNdp>) listByQuery("getEsitiNdpByIdFlusso",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getEsitiNdpByIdFlusso ", e);
			throw new DAORuntimeException(e);
		}
		
		return esiti;
	}
	
	@Override
	public EsitiNdp retrieveEsitiNdpById(Long id) {
		
		EsitiNdp retrieved = null;
		
		try {
			
			retrieved = loadById(EsitiNdp.class, id);
			
		} catch (Exception e) {
			
			LOGGER.error("error on  retrieveEsitiNdpById, ID = " + id, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return retrieved;
	}
	
	@Override
	public EsitiNdp updateEsitiNdp(EsitiNdp esito) {
		
		try {
			
			esito = this.update(esito);
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateEsitiNdp ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return esito;
	}
	
	
}
