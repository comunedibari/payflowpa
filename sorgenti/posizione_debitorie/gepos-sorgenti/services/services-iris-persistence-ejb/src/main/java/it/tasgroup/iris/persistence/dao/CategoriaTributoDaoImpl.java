
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.nch.is.fo.tributi.CategoriaTributo;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.anagrafica.CategoriaTributoDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CategoriaTributoDAO;
import it.tasgroup.iris.persistence.dao.interfaces.TributoEnteDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CategoriaTributoDaoService")
public class CategoriaTributoDaoImpl extends DaoImplJpaCmtJta<CategoriaTributo> implements CategoriaTributoDAO {
	
	private static final Logger LOGGER = LogManager.getLogger(CategoriaTributoDaoImpl.class);
	
	@EJB(name="TributoEnteDaoService")
	TributoEnteDao tributoDao;
	
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
	public List<CategoriaTributo> listCategoriaTributoByFilterParams(ContainerDTO dtoIn) {
		
		try{

			CategoriaTributoDTO filterDTO = (CategoriaTributoDTO) dtoIn.getInputDTO();
	
			Map<String, Object> parameters = new HashMap<String, Object>();
	        
	        String queryString = buildQueryListCategoriaTributo(filterDTO, parameters);
	
	        PagingCriteria pagingCriteria = dtoIn.getPagingCriteria();
	
	        PagingData pagingData = dtoIn.getPagingData();
	
			List<CategoriaTributo> retList = null;
			
			if (pagingCriteria != null) {
				
				retList = paginateByQuery(CategoriaTributo.class, queryString, pagingCriteria, pagingData, parameters);
				
			} else {
				
				Query query= em.createNativeQuery(queryString);
				
				retList = query.getResultList();
			}
	
			
			return retList;
	
		} catch (Exception e) {
			
			LOGGER.error("error on listCategoriaTributoByFilterParams ", e);
			
			throw new DAORuntimeException(e);
		}

	}
	
	private String buildQueryListCategoriaTributo(CategoriaTributoDTO filterDTO, Map<String, Object> parameters){
		
		String selectFromWhere = "Select cat.* from JLTTRPA cat "
				+ " where 1 = 1 ";

		StringBuffer whereConditions = new StringBuffer();
		
		if (!StringUtils.isEmpty(filterDTO.getIdTributo())) {
			
			whereConditions = whereConditions.append(" AND cat.ID_TRIBUTO =:IDTRIBUTO ");
			parameters.put("IDTRIBUTO", filterDTO.getIdTributo());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getDeTrb())) {
			
			whereConditions = whereConditions.append(" AND cat.DE_TRB =:DETRB ");
			parameters.put("DETRB", filterDTO.getDeTrb());
			
		}
		
		if (!StringUtils.isEmpty(filterDTO.getCdAde())) {
			
			whereConditions = whereConditions.append(" AND cat.CD_ADE =:CDADE ");
			parameters.put("CDADE", filterDTO.getCdAde());
			
		}

		if (!StringUtils.isEmpty(filterDTO.getTpEntrata())) {
			
			whereConditions = whereConditions.append(" AND cat.TP_ENTRATA =:TPENTRATA ");
			parameters.put("TPENTRATA", filterDTO.getTpEntrata());
			
		}

		if (!StringUtils.isEmpty(filterDTO.getFlPredeterm())) {
			
			whereConditions = whereConditions.append(" AND trb.FL_PREDETERM =:FLPREDETERM ");
			parameters.put("FLPREDETERM", filterDTO.getFlPredeterm());
			
		}

		if (!StringUtils.isEmpty(filterDTO.getFlIniziativa())) {
			
			whereConditions = whereConditions.append(" AND cat.FL_INIZIATIVA =:FLINIZIATIVA ");
			parameters.put("FLINIZIATIVA", filterDTO.getFlIniziativa());
			
		}

		if (!StringUtils.isEmpty(filterDTO.getStato())) {
			
			whereConditions = whereConditions.append(" AND cat.STATO =:STATO ");
			parameters.put("STATO", filterDTO.getStato());
			
		}

		// ordinamento per denominazione		
		whereConditions = whereConditions.append(" ORDER BY ID_TRIBUTO ASC");
		
		return selectFromWhere + whereConditions;
		
	}


	@Override
	public void changeStatusCategoriaTributoList(List<CategoriaTributoDTO> inputList) {
		
		try {
			
			for (CategoriaTributoDTO catDTO : inputList) {
				
				CategoriaTributo cat = getById(CategoriaTributo.class, catDTO.getIdTributo());

				cat.setStato(catDTO.getStato());

				cat = update(cat);		
			
			}

		} catch (Exception e) {
			
			LOGGER.error("Error on changeStatusCategoriaTributoList", e);
			
			throw new DAORuntimeException(e);
		}
		
	}
	
	@Override
	public CategoriaTributo createCategoriaTributo(CategoriaTributo cat) {
		
		try {
			
			String nextId = tributoDao.getNextCategoria();
			
			cat.setIdTributo(nextId);
			
			cat.setTsInserimento(new Date());
			
			cat = this.create(cat);
			
		} catch (Exception e) {
			
			LOGGER.error("Error on createCategoriaTributo ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return cat;
	}
	
	@Override
	public CategoriaTributo getCategoriaTributoById(String idTributo) {
		
		CategoriaTributo cat;
		
		try {
			
			cat = getById(CategoriaTributo.class, idTributo);
			
		} catch (Exception e) {
			
			LOGGER.error("Error on getCategoriaTributoById ", e);
			
			throw new DAORuntimeException(e);
		}
		
		return cat;
		
	}


	@Override
	public CategoriaTributo updateCategoriaTributo(CategoriaTributoDTO cat) {
		
		CategoriaTributo updatedCat = null;
		
		try {
			
			CategoriaTributo oldCat = getById(CategoriaTributo.class, cat.getIdTributo());
			
			oldCat.setDeTrb(cat.getDeTrb());
			
			oldCat.setTpEntrata(cat.getTpEntrata());
			
			oldCat.setTassonomia(cat.getTassonomia()); 
			
			oldCat.setOpAggiornamento(cat.getOpAggiornamento());
			
			oldCat.setTsAggiornamento(new Date());
			
			updatedCat = this.update(oldCat);
			
		} catch (Exception e) {
			
			LOGGER.error("Error on updateCategoriaTributo ", e);
			
			throw new DAORuntimeException(e);
			
		}
		
		return updatedCat;
	}


	@Override
	public void deleteCategorieTributi(List<String> selectedIds) {
		
		try {
			
			Query deleteQuery = buildQueryDeleteCategorie(selectedIds);
			
			deleteQuery.executeUpdate();
			
		} catch(Exception e){
			
			LOGGER.error("error on deleteCategorieTributi, " + selectedIds, e);
			
			throw new DAORuntimeException(e);
			
		}
	}
	
	private Query buildQueryDeleteCategorie(List<String> selectedIds) {

		String selectFromWhere = "delete from JLTTRPA ";
		
		StringBuffer inCondition = new StringBuffer("where ID_TRIBUTO IN (");

		appendInCondition(inCondition, selectedIds);

		Query query = em.createNativeQuery(selectFromWhere + inCondition);

		addInParameters(query, selectedIds, 0);

		return query;
	}
	
	@Override
	public List<CategoriaTributo> listCategorieTributiEnte(ContainerDTO dtoIn, String idEnte){
		
		List<CategoriaTributo> listaCategorie = null;
		
		LOGGER.debug( "getListCategoriaTributoByEnte, ID_ENTE = " + idEnte);
		
		try{
			
				String queryString = "select cat.* from JLTTRPA cat where cat.ID_TRIBUTO in (select distinct trbent.ID_TRIBUTO from JLTENTR trbent where trbent.ID_ENTE = :idEnte) order by ID_TRIBUTO";
		
				HashMap<String, Object> params = new HashMap<String, Object>();
				
				params.put("idEnte", idEnte);
		
				if (dtoIn.getPagingCriteria()!=null){
					
					listaCategorie = paginateByQuery(CategoriaTributo.class, queryString, dtoIn.getPagingCriteria(), dtoIn.getPagingData(), params);
				
				} else {
					
					Query q = em.createNativeQuery(queryString);
					
					listaCategorie = q.getResultList();
					
				}
				
			 } catch (Exception e) {
				 
		         LOGGER.error("error on getListCategoriaTributoByEnte ", e);
		         
		         throw new DAORuntimeException(e);
		     } 

		return listaCategorie;

	}

}
