
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.EsitiCbill;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.EsitiCbillDao;
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

@Stateless(name="EsitiCbillDaoService")
public class EsitiCbillDaoImpl extends DaoImplJpaCmtJta<EsitiCbill> implements EsitiCbillDao {
	
	private static final Logger LOGGER = LogManager.getLogger(EsitiCbillDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<EsitiCbill> listEsitiCbillByIdRendicontazione(ContainerDTO containerDTO) {
		
		List<EsitiCbill> retList = null;

		Long id = (Long) containerDTO.getInputDTOList().get(0);
                // containerDTO.getInputDTOList().get(1) contiene tipoFlusso
                String canaleCbill = (String) containerDTO.getInputDTOList().get(2);
                String esitoanomalo = (String) containerDTO.getInputDTOList().get(3);
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("idRendicontazione", id);
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			StringBuilder query= new StringBuilder("select esiti.* from ESITI_CBILL esiti " +
							"where esiti.ID_RENDICONTAZIONE=:idRendicontazione ");
                        
                        if(canaleCbill != null && !canaleCbill.isEmpty()){
                            query.append("and esiti.CHANNEL = :canaleCbill ");
                            params.put("canaleCbill", canaleCbill);
                        }
                        
                        if(esitoanomalo != null && !esitoanomalo.isEmpty() && (esitoanomalo.equals("on") || esitoanomalo.equals("1"))){
                            List<String> flags = EnumFlagRiconciliazioneEsiti.getChiaveEsitAnomali();
                            params.put("flags", flags);
                            query.append(" and esiti.FLAG_RICONCILIAZIONE in (:flags)");
                        }
			
			retList = paginateByQuery(EsitiCbill.class, query.toString(), pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listEsitiCbillByIdRendicontazione ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public EsitiCbill getEsitiCbillById(Long id) {
		
		EsitiCbill esito = null;
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", id);
			
			esito = (EsitiCbill)uniqueResultByQuery("getEsitiCbillById",params,em);

		} catch (Exception e) {
			LOGGER.error("error on getEsitiCbillById ", e);
			throw new DAORuntimeException(e);
		}
		return esito;
	}
}
