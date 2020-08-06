
package it.tasgroup.iris.persistence.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.is.fo.sistemienti.SistemaEnte;
import it.nch.is.fo.sistemienti.SistemaEnteId;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.SistemaEnteDTO;
import it.tasgroup.iris.persistence.dao.interfaces.SistemaEnteDAO;
import it.tasgroup.iris.persistence.dao.util.TributoEnteEntityBuilder;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="SistemaEnteDaoService")
public class SistemaEnteDaoImpl extends DaoImplJpaCmtJta<SistemaEnte> implements SistemaEnteDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(SistemaEnteDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		
		this.em=em;
		
	}
	
	
	/**
	 * @param context
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SistemaEnte> listSistemaEnteByFilterParams(ContainerDTO dtoIn) {
		
		try{

			SistemaEnteDTO filterDTO = (SistemaEnteDTO) dtoIn.getInputDTO();
	
			Map<String, Object> parameters = new HashMap<String, Object>();
	        
	        String queryString = buildQueryListSistemaEnte(filterDTO, parameters);
	
	        PagingCriteria pagingCriteria = dtoIn.getPagingCriteria();
	
	        PagingData pagingData = dtoIn.getPagingData();
	
			List<SistemaEnte> retList = null;
			
			if (pagingCriteria != null) {
				
				retList = paginateByQuery(SistemaEnte.class, queryString, pagingCriteria, pagingData, parameters);
				
			} else {
				Query query= em.createNativeQuery(queryString, SistemaEnte.class);
				for (Map.Entry<String, Object> entry : parameters.entrySet())
					query.setParameter(entry.getKey(), entry.getValue());
				retList = query.getResultList();
			}
	
			
			return retList;
	
		} catch (Exception e) {
			
			LOGGER.error("error on listSistemaEnteByFilterParams ", e);
			
			throw new DAORuntimeException(e);
		}

	}
	
	private String buildQueryListSistemaEnte(SistemaEnteDTO filterDTO, Map<String, Object> parameters){
		
			String selectFromWhere = "Select sil.* from JLTSIL sil "
					+ " where 1 = 1 ";
	
			StringBuffer whereConditions = new StringBuffer();
			
			if (!StringUtils.isEmpty(filterDTO.getIdEnte())) {
				
				whereConditions = whereConditions.append(" AND sil.ID_ENTE =:IDENTE ");
				parameters.put("IDENTE", filterDTO.getIdEnte());
				
			}
			
			if (!StringUtils.isEmpty(filterDTO.getIdSystem())) {
				
				whereConditions = whereConditions.append(" AND sil.ID_SYSTEM =:IDSYSTEM ");
				parameters.put("IDSYSTEM", filterDTO.getIdSystem());
				
			}
			
			return selectFromWhere + whereConditions;
			
		}
	
	@Override
	public SistemaEnte createSistemaEnte(SistemaEnteDTO silDTO) {
		
		SistemaEnte sil = null;
		
		try {
			
			sil = TributoEnteEntityBuilder.fillSistemaEnteEntity(silDTO);
			
			sil = this.create(sil);
			
		} catch(Exception e){
			
			LOGGER.error("error on  createSistemaEnte, " + silDTO, e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return sil;
	}


	@Override
	public void deleteSistemaEnteByKey(SistemaEnteId key) {
		
		try{
			
			this.deleteByKey(SistemaEnte.class,key);
			
		} catch(Exception e){
			
			LOGGER.error("error on  deleteSistemaEnteByKey, " + key, e);
			
			throw new DAORuntimeException(e);
			
		}
		
	}
	
	@Override
	public SistemaEnte updateSistemaEnte(SistemaEnteDTO silDTO) {
		
		SistemaEnte sil = null;
				
		try {
			
			SistemaEnteId pk = new SistemaEnteId(silDTO.getIdEnte(), silDTO.getIdSystem());
			
			SistemaEnte oldSIL = getById(SistemaEnte.class, pk);
			
			oldSIL.setDeSystem(silDTO.getDeSystem());
			oldSIL.setsSilEnabledAsString(silDTO.getIsSSilEnabled());
			oldSIL.setUserId(silDTO.getUserId());
			oldSIL.setAuthId(silDTO.getAuthId());
			oldSIL.setTrtId(silDTO.getTrtId());
			oldSIL.setTrtSystem(silDTO.getTrtSystem());
			oldSIL.setOpAggiornamento(silDTO.getOpAggiornamento());
			oldSIL.setTsAggiornamento(new Date());
			//SistemaEnteEntityBuilder.fillTributoEntity(silDTO, oldSIL);
			
			sil = this.update(oldSIL);
			
		} catch (Exception e) {
			
			LOGGER.error("error on updateSistemaEnte ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return sil;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SistemaEnte> selectActiveAndSemplSistemaEnte() {
		List<SistemaEnte> listResult = new ArrayList<SistemaEnte>();
		List<Object[]> list = null;
		String query = "Select sil,ente.codiceEnte from SistemaEnte sil, Enti ente  where sil.sSilEnabledAsString = :enabled AND sil.stato = :stato and sil.sisEntId.idEnte= ente.idEnte order by sil.trtId";
		Query selectQuery = em.createQuery(query);
		selectQuery.setParameter("enabled", "Y");
		selectQuery.setParameter("stato", "A");
		try {
			list = selectQuery.getResultList();
			for (int i= 0; i < list.size(); i++) {
				String cdEnte = (String) ((Object[])list.get(i))[1];
				SistemaEnte se = (SistemaEnte) ((Object[])list.get(i))[0];
				se.setCdEnte(cdEnte);
				listResult.add(se);
			}
		} catch(Exception e) {
			throw new DAORuntimeException(e);
		}
		return listResult;
	}


	
	@Override
	public SistemaEnte getSistemaEnteByCdEnteAndIdSystem(String cdEnte, String idSystem) {

		SistemaEnte result = null;
		
		try {
		
			Query query = null;
			
			String querySQL = 	"SELECT sil.* from JLTSIL sil " +				
								"WHERE sil.ID_ENTE = (SELECT ID_ENTE FROM JLTENTI WHERE CD_ENTE =:cdEnte) "  +
								"AND sil.ID_SYSTEM=:idSystem";

			query= em.createNativeQuery(querySQL, SistemaEnte.class);
			query.setParameter("cdEnte", cdEnte);
			query.setParameter("idSystem", idSystem);

			result = (SistemaEnte) query.getSingleResult();

			return result;
			
		} 
		catch (NoResultException nores) {
			
			LOGGER.warn("warn on getSistemaEnteByCdEnteAndIdSystem, cdEnte = "+ cdEnte +" idSystem = "+ idSystem +" no record found ", nores);
			
			return result;
		}
		catch (Exception e) {
			
			LOGGER.error("error on getSistemaEnteByCdEnteAndIdSystem, cdEnte = "+ cdEnte +" idSystem = "+ idSystem, e);
			
			throw new DAORuntimeException(e);
		}
	}

}
