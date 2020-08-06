package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.Pendenza;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.flussi.ListaEventiNDPFilterDTO;
import it.tasgroup.iris.gde.GiornaleEventi;
import it.tasgroup.iris.persistence.dao.interfaces.GiornaleEventiDao;
import it.tasgroup.iris.shared.util.UtilDate;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="GiornaleEventiDaoService")
public  class GiornaleEventiDaoImpl extends DaoImplJpaCmtJta<GiornaleEventi> implements GiornaleEventiDao {

	private static final Logger LOGGER = LogManager.getLogger(GiornaleEventiDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<GiornaleEventi> listEventiNDPByFilterParams(ContainerDTO input) {
		
		List<GiornaleEventi> retList = null;
			
		Map params = new HashMap();
		
		ListaEventiNDPFilterDTO inputDTO = (ListaEventiNDPFilterDTO) input.getInputDTOList().get(0);
		
		try {
			
			String filteringQuery= buildQueryAndParameters(inputDTO, params);
			
			PagingCriteria pagingCriteria = input.getPagingCriteria();
			
			PagingData pagingData = input.getPagingData();
			
			if (pagingCriteria != null) {
				// Se specificato, Pagina
				retList = paginateByQuery(GiornaleEventi.class, filteringQuery, pagingCriteria, pagingData, params);
			
			} else {
				
				// Altrimenti non Pagina
				Query query = em.createNativeQuery(filteringQuery, GiornaleEventi.class); 
				
				this.querySetup(query,params);
				
				retList=query.getResultList();
			}
			
			
		} catch (Exception e) {
			LOGGER.error("error on listEventiNDPByFilterParams ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	private String buildQueryAndParameters(ListaEventiNDPFilterDTO dto, Map params) {
		
		String selectFromWhere = "Select ev.* from GDE_EVENTI ev where 1 = 1 ";
		
		StringBuffer whereConditions = new StringBuffer("");
		
		String orderingCondition = "";
		
		Long idEvento = dto.getIdEvento();
		
		if (idEvento != null) {
			
			whereConditions.append(" AND ev.ID =:ID");
			
			params.put("ID", idEvento);
			
		}
		
		String tipoEvento = dto.getTipoEvento();
		
		if (!StringUtils.isEmpty(tipoEvento)) {
			
			whereConditions.append(" AND ev.TIPO_EVENTO =:TIPO_EVENTO");
			
			params.put("TIPO_EVENTO", tipoEvento);																					
		}
				
		String idDominio = dto.getIdDominio();
		
		if (!StringUtils.isEmpty(idDominio)) {
			
			whereConditions.append(" AND ev.ID_DOMINIO =:ID_DOMINIO");
			
			params.put("ID_DOMINIO", idDominio);
			
			}
		
		String iuv = dto.getIuv();
		
		if (!StringUtils.isEmpty(iuv)) {
			
			whereConditions.append(" AND ev.ID_UNIVOCO_VERSAMENTO =:ID_UNIVOCO_VERSAMENTO");
			
			params.put("ID_UNIVOCO_VERSAMENTO", iuv);
			
			}
		
		String idPSP = dto.getIdPSP();
		
		if (!StringUtils.isEmpty(idPSP)) {
			
			whereConditions.append(" AND ev.ID_PRESTATORESERVIZI_PAGAMENTO =:ID_PSP");
			
			params.put("ID_PSP", idPSP);
			
			}


		Timestamp dataDa = dto.getDataDa();
		
		if (dataDa != null ){
			
			whereConditions.append(" AND ev.DT_EVENTO >=:DT_EVENTO_DA");	
			
			params.put("DT_EVENTO_DA", dataDa);
			
		}
		
		Date dataA = dto.getDataA();
		
		if (dataA != null ){
			
			// TODO PAZZIK CENTRALIZZARE
			dataA.setTime(dataA.getTime() + 24*60*60*1000 - 1);
			
			whereConditions.append(" AND ev.DT_EVENTO <=:DT_EVENTO_A");
			
			params.put("DT_EVENTO_A", UtilDate.setOrarioEndOfDay(dataA));
			
		}
		
		String codContesto = dto.getCodContesto();
		
		if (!StringUtils.isEmpty(codContesto)){
			
			whereConditions.append(" AND ev.codice_contesto_pagamento =:COD_CONTESTO");
			
			params.put("COD_CONTESTO", codContesto);
			
		}
		
		orderingCondition = " ORDER BY ev.TS_INSERIMENTO DESC, ev.SOTTO_TIPO_EVENTO DESC ";
		
		return selectFromWhere + whereConditions + orderingCondition;	
	}

}
