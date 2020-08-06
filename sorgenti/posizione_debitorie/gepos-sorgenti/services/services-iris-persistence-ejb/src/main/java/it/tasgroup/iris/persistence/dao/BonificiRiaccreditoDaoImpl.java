
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.BonificiRiaccredito;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.persistence.dao.interfaces.BonificiRiaccreditoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="BonificiRiaccreditoDaoService")
public class BonificiRiaccreditoDaoImpl extends DaoImplJpaCmtJta<BonificiRiaccredito> implements BonificiRiaccreditoDao {
	
	private static final Logger LOGGER = LogManager.getLogger(BonificiRiaccreditoDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public BonificiRiaccredito retrieveBonificiRiaccreditoById(Long id) {
		BonificiRiaccredito retrieved = null;
		try {
			retrieved = loadById(BonificiRiaccredito.class,id);
		} catch (Exception e) {
			LOGGER.error("error on  retrieveBonificiRiaccreditoById, ID = " + id, e);
			throw new DAORuntimeException(e);
		}
		return retrieved;
	}
	
	@Override
	public List<BonificiRiaccredito> listBonificiRiaccreditoByIdDistinta(ContainerDTO containerDTO) {
		
		List<BonificiRiaccredito> retList = null;
		Long idDistinta = (Long) containerDTO.getInputDTOList().get(1);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idDistintaRiaccredito", idDistinta);
		
		try {			
			
			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();
			
			PagingData pagingData = containerDTO.getPagingData();
			
			String query="select bon.* from BONIFICI_RIACCREDITO bon " +
						 "where id_distinte_riaccredito=:idDistintaRiaccredito "+
						 "order by data_esecuzione, RAGIONE_SOCIALE_BENEFICIARIO, iban_beneficiario desc";
			
			retList = paginateByQuery(BonificiRiaccredito.class, query, pagingCriteria, pagingData, params);
			
		} catch (Exception e) {
			LOGGER.error("error on listBonificiRiaccreditoByIdDistinta "+params, e);		
			throw new DAORuntimeException(e);
		}
		return retList;
	}

}
