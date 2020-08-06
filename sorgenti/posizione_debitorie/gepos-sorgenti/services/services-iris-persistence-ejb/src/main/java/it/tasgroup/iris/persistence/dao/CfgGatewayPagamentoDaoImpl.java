package it.tasgroup.iris.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.fwk.fo.pager.PagingCriteria;
import it.nch.fwk.fo.pager.PagingData;
import it.tasgroup.iris.domain.CfgGatewayPagamento;
import it.tasgroup.iris.domain.CfgTributiEntiGateway;
import it.tasgroup.iris.dto.ContainerDTO;
import it.tasgroup.iris.dto.confpagamenti.FilterGatewayPagamentoDTO;
import it.tasgroup.iris.persistence.dao.interfaces.CfgGatewayPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="CfgGatewayPagamentoDaoService")
public  class CfgGatewayPagamentoDaoImpl extends DaoImplJpaCmtJta<CfgGatewayPagamento> implements CfgGatewayPagamentoDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgGatewayPagamentoDaoImpl.class);	

	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	public void setEntityMangerFactory(EntityManagerFactory emf){
		this.em = emf.createEntityManager();
	}

	@Override
	public CfgGatewayPagamento getCfgByBundleKey(String key) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("inBundleKey", key);		
		
		CfgGatewayPagamento cf= null;
		
		try {			
			
			cf = (CfgGatewayPagamento)uniqueResultByQuery("getcfByBundleKey",params,em);
			
		} catch (Exception e) {
			
			LOGGER.error("error on getCfgByBundleKey "+params, e);	
			
			throw new DAORuntimeException(e);
			
		}
		return cf;
	}

	@Override
	public CfgGatewayPagamento getCfgBySystemIdAndApplicationId(
			String systemId, String applicationId) {
		CfgGatewayPagamento cf = new CfgGatewayPagamento();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inApplicationId", applicationId);		
		params.put("inSystemId", systemId);
		try {			
			cf= (CfgGatewayPagamento)uniqueResultByQuery("getCfgBySystemIdAndApplicationId",params,em);		
		} catch (Exception e) {
			LOGGER.error("error on getCfgBySystemIdAndApplicationId "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cf;
	}
    
	@Override
	public CfgGatewayPagamento getCfgBySystemIdModelloVersamentoCanaleIntermediario(
			String systemId, 
			String modelloVersamento,
			String identificativoCanalePSP,
			String identificativoIntermediarioPSP) {
		
		CfgGatewayPagamento cf = new CfgGatewayPagamento();
		
		String query="select cf from CfgGatewayPagamento cf "+
				  "where cf.systemId =:inSystemId "+
				  "and cf.modelloVersamento =:inModelloVersamento "+
				  "and cf.stato = 'ATTIVO' ";
		
	
		if (identificativoCanalePSP!=null) {
			query=query + " and cf.applicationId =:inApplicationId ";			
		}
		
		if (identificativoIntermediarioPSP!=null){
			query=query + " and cf.subsystemId =:inSubsystemId ";			
		}
		
		try {			
			Query q = em.createQuery(query);
			q.setParameter("inModelloVersamento", modelloVersamento);
			q.setParameter("inSystemId", systemId);
			if (identificativoCanalePSP!=null) {				
				q.setParameter("inApplicationId", identificativoCanalePSP+"-PO");
			}
			
			if (identificativoIntermediarioPSP!=null){				
				q.setParameter("inSubsystemId", identificativoIntermediarioPSP);
			}
			
			List<CfgGatewayPagamento> listaOut = (List<CfgGatewayPagamento>)q.getResultList();	
			if (listaOut!= null && listaOut.size() >0) {
			   cf= listaOut.get(0);
			} else {
			   cf = null;	
			}
		} catch (Exception e) {
			LOGGER.error("error on getCfgBySystemIdAndModelloVersamento inModelloVersamento="+modelloVersamento+
					        " , inSystemId="+ systemId +
					        " , inApplicationId="+ identificativoCanalePSP+"-PO" +
					        " , inSubsystemId = " +identificativoIntermediarioPSP  , e);
			 
			throw new DAORuntimeException(e);
		}
		return cf;
	}
	
	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoAll(){
		List<CfgGatewayPagamento> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stRiga", "V");		

		try {
			cfg = (List<CfgGatewayPagamento>) listByQuery("getCfgGatewayPagamentoAll", params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoAll ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}

	@Override
	public CfgGatewayPagamento getCfgGatewayPagamentoById(Long id) {
		CfgGatewayPagamento cf = new CfgGatewayPagamento();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);		

		try {			
			cf= (CfgGatewayPagamento)uniqueResultByQuery("getCfgGatewayPagamentoById",params,em);		
		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoById "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cf;
	}


	private Query createQueryStatoCfgGateway(String[] idCfgGateway, String stato) {

		String selectFromWhere = "update CFG_GATEWAY_PAGAMENTO set STATO = ?";
		StringBuffer inCondition = new StringBuffer("where ID IN (");

		for (String id : idCfgGateway)
			inCondition.append("?,");

		inCondition.deleteCharAt(inCondition.length()-1).append(")");

		Query query= em.createNativeQuery(
				selectFromWhere+inCondition);

		query.setParameter(1, stato);
		int i = 1;

		for (String id : idCfgGateway){

			query.setParameter(1+i, id);

			i++;
		}

		return query;
	}

	@Override
	public void updateStatoCfgGatewayPagamento(String[] idcfgGatewayPagamento, String stato) {
		try {

			Query updateStatoCfgGatewayPagamento = createQueryStatoCfgGateway(idcfgGatewayPagamento, stato);

			int result = updateStatoCfgGatewayPagamento.executeUpdate();

		} catch (Exception e) {
			LOGGER.error("error on updateStatoCfgGatewayPagamento " + idcfgGatewayPagamento, e);
			throw new DAORuntimeException(e);
		}
	}

	@Override
	public List<CfgGatewayPagamento> getListCfgByStato(String stato) {
		Map<String, Object> params = new HashMap<String, Object>();

		Timestamp now = new Timestamp(new Date().getTime()); 

		params.put("stato", stato);		
		params.put("dtInizioValidita", now);
		params.put("dtFineValidita",now);

		List<CfgGatewayPagamento> cfs = null;
		try {			
			cfs = (List<CfgGatewayPagamento>)listByQuery("getCfGatewayPagamentoListByStato",params);
		} catch (Exception e) {
			LOGGER.error("error on getListCfgByStato "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	}

	@Override
	public List<CfgGatewayPagamento> getListCfgByStatoAndSysIdAndAppId(String stato, String sysId, String appId) {
		Map<String, Object> params = new HashMap<String, Object>();

		Timestamp now = new Timestamp(new Date().getTime()); 

		params.put("stato", stato);		
		params.put("dtInizioValidita", now);
		params.put("dtFineValidita",now);
		params.put("sysId",sysId);
		params.put("appId",appId);

		List<CfgGatewayPagamento> cfs = null;
		try {			
			cfs = (List<CfgGatewayPagamento>)listByQuery("getCfGatewayPagamentoListByStatoAndSysIdAndAppId",params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgByStatoAndSysIdAndAppId "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	}
	
	private String buildQueryAndParameters(FilterGatewayPagamentoDTO dto, Map<String, Object> parameters) {

		String selectFromWhere = "Select gtw.* from CFG_GATEWAY_PAGAMENTO gtw, CFG_FORNITORE_GATEWAY fornit, CFG_STRUMENTO_PAGAMENTO strum "
				+ "where gtw.ID_CFG_FORNITORE_GATEWAY = fornit.ID and strum.ID = gtw.ID_CFG_STRUMENTO_PAGAMENTO and gtw.ST_RIGA='V' and strum.ST_RIGA='V' and fornit.ST_RIGA='V' ";

		StringBuffer whereConditions = new StringBuffer();
		
		if (!StringUtils.isEmpty(dto.getFornitoreGateway())) {
			
			whereConditions.append("AND fornit.BUNDLE_KEY =:FORNITORE ");
			parameters.put("FORNITORE", dto.getFornitoreGateway());
			
		}
		
		if (!StringUtils.isEmpty(dto.getStatoPagamento())) {
			
			whereConditions.append("AND gtw.STATO =:STATOGTW ");
			parameters.put("STATOGTW", dto.getStatoPagamento());
			
		}
		
		if (!StringUtils.isEmpty(dto.getSystemName())) {
			
			String systemName = dto.getSystemName();
			
			if (systemName.contains("*")) {
				
				whereConditions.append(" AND gtw.SYSTEM_NAME like :SYSTEMNAME");
				
				parameters.put("SYSTEMNAME", systemName.replaceAll("[*]", "%"));
				
	        } else {
	        	
	        	whereConditions.append(" AND gtw.SYSTEM_NAME = :SYSTEMNAME");
	        	
				parameters.put("SYSTEMNAME", systemName);
	        }
			
		}
		
		if (!StringUtils.isEmpty(dto.getStrumentoPagamento())) {
			
			whereConditions.append(" AND strum.BUNDLE_KEY = :STRUMENTO");
        	
			parameters.put("STRUMENTO", dto.getStrumentoPagamento());

		}
		
		StringBuffer orderingCondition = new StringBuffer(" ORDER BY gtw.ID_CFG_FORNITORE_GATEWAY DESC");

		String queryString = selectFromWhere + whereConditions + orderingCondition;

		return queryString;
	}
	
	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoByFilter(ContainerDTO containerDTO) {

		FilterGatewayPagamentoDTO inputDTO = (FilterGatewayPagamentoDTO) containerDTO.getInputDTO();
		
		List<CfgGatewayPagamento> retList = null;
		
		try {

			Map<String, Object> parameters = new HashMap<String, Object>();

			String filteringQuery = buildQueryAndParameters(inputDTO, parameters);

			PagingCriteria pagingCriteria = containerDTO.getPagingCriteria();

			PagingData pagingData = containerDTO.getPagingData();

			retList = paginateByQuery(CfgGatewayPagamento.class, filteringQuery, pagingCriteria, pagingData, parameters);

		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoByFilter " + containerDTO, e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}


	
	
	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoDistinctSystemName() {
		List<CfgGatewayPagamento> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("stRiga", "V");		

		try {
			cfg = (List<CfgGatewayPagamento>) listByQuery("getCfgGatewayPagamentoDistinctSystemName", params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoAll ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}

	@Override
	public List<String> getCfGatewayPagamentoDistinctSysId() {
		Map<String, Object> params = new HashMap<String, Object>();

		
		List<String> cfs = null;
		try {			
			cfs = (List<String>)listByQuery("getCfGatewayPagamentoDistinctSysId",params);
		} catch (Exception e) {
			LOGGER.error("error on getCfGatewayPagamentoDistinctSysId "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	}
     
	@Override
	public List<String> getCfGatewayPagamentoDistinctApplicationId() {
		Map<String, Object> params = new HashMap<String, Object>();

		
		List<String> cfs = null;
		try {			
			cfs = (List<String>)listByQuery("getCfGatewayPagamentoDistinctApplicationId",params);
		} catch (Exception e) {
			LOGGER.error("error on getCfGatewayPagamentoDistinctApplicationId "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cfs;
	} 
	
	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoTributiEnteAllFornitore(String idEnte, String cdTrbEnte) {
		List<CfgGatewayPagamento> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEnte", idEnte);		
		params.put("cdTrbEnte", cdTrbEnte);		

		try {
			cfg = (List<CfgGatewayPagamento>) listByQuery("getCfgGatewayPagamentoTributiEnte", params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoTributiEnte ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}
	
	@Override
	public List<Long> getIdCfgGatewayPagamentoTributiEnteAllFornitore(String idEnte, String cdTrbEnte) {
		List<Long> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEnte", idEnte);		
		params.put("cdTrbEnte", cdTrbEnte);		
		params.put("tipoVersamento", new String());	
		params.put("modelloVersamento", new String());	
		try {                               
			cfg = (List<Long>) listByQuery("getIdCfgGatewayPagamentoTributiEnte", params);
		} catch (Exception e) {
			LOGGER.error("error on getIdCfgGatewayPagamentoTributiEnteAllFornitore ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}
	
	@Override
	public List<CfgGatewayPagamento> getCfgGatewayPagamentoTributiEnteSpecificGtw(String idEnte, String cdTrbEnte) {
		List<CfgGatewayPagamento> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEnte", idEnte);		
		params.put("cdTrbEnte", cdTrbEnte);		
		
		try {
			cfg = (List<CfgGatewayPagamento>) listByQuery("getCfgGatewayTributiEnte", params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoTributiEnte ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}
	
	@Override
	public List<Long> getIdCfgGatewayPagamentoTributiEnteSpecificGtw(String idEnte, String cdTrbEnte) {
		List<Long> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEnte", idEnte);		
		params.put("cdTrbEnte", cdTrbEnte);	
		params.put("tipoVersamento", new String());	
		params.put("modelloVersamento", new String());	

		try {                              
			cfg = (List<Long>) listByQuery("getIdCfgGatewayTributiEnte", params);
		} catch (Exception e) {
			LOGGER.error("error on getIdCfgGatewayPagamentoTributiEnteSpecificGtw ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}
	
	@Override
	public List<CfgTributiEntiGateway> getListaCfgGatewayPagamentoTributiEnteFornitore(String idEnte, String cdTrbEnte) {
		List<CfgTributiEntiGateway> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEnte", idEnte);		
		params.put("cdTrbEnte", cdTrbEnte);	
		

		try {
			cfg = (List<CfgTributiEntiGateway>) listByQuery("getModelloETipoVersamentoCfgGatewayTributiEnte", params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgGatewayPagamentoTributiEnte ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}
	
	@Override
	public List<Object[]> readModalitaPagamentoPsp(String idEnte) {
		List<Object[]> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idEnte", idEnte);
		try {
			cfg = (List<Object[]>) listByQuery("getIdCfgGatewayPagamentoPSP", params);
		} catch (Exception e) {
			LOGGER.error("error on getIdCfgGatewayPagamentoPSP ", e);
			throw new DAORuntimeException(e);
		}
		return cfg;
	}
	
	@Override
	public List<Object[]> readModalitaPagamentoTipoVersamento(String sys,String sub) {
		List<Object[]> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("sysId", sys);
		params.put("subSysId", sub);
		try {
			cfg = (List<Object[]>) listByQuery("getIdCfgGatewayPagamentoTipoVersamento", params);
		} catch (Exception e) {
			LOGGER.error("error on getIdCfgGatewayPagamentoTipoVersamento ", e);
			throw new DAORuntimeException(e);
		}
		return cfg;
	}

	
	@Override
	public CfgGatewayPagamento getCfgGatewayPagamentoNdpByDatiRT(String tipoIdentificativoPSP,
			String codiceIdentificativoUnivocoPSP) {
		
		List<CfgGatewayPagamento> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		
		
		if ("B".equals(tipoIdentificativoPSP)){
		    params.put("inSystemId", codiceIdentificativoUnivocoPSP);

		    try {
				cfg = (List<CfgGatewayPagamento>) listByQuery("getCfgNdpByBic", params);
				if (!cfg.isEmpty()){
					return cfg.get(0);
				} else {
					return null;
				}
			} catch (Exception e) {
				LOGGER.error("error on getCfgGatewayPagamentoNdpByDatiRT ", e);
				throw new DAORuntimeException(e);
			}
		}
		
		if ("G".equals(tipoIdentificativoPSP)){
		    params.put("subsystemId", codiceIdentificativoUnivocoPSP);

		    try {
				cfg = (List<CfgGatewayPagamento>) listByQuery("getCfgNdpByCodFiscale", params);
				if (!cfg.isEmpty()){
					return cfg.get(0);
				} else {
					return null;
				}
			} catch (Exception e) {
				LOGGER.error("error on getCfgGatewayPagamentoNdpByDatiRT ", e);
				throw new DAORuntimeException(e);
			}
		}
		if ("A".equals(tipoIdentificativoPSP)){
			
			
		}
		
       
      
		return null;
	}

	@Override
	public CfgGatewayPagamento getCfgBySystemId(String systemId) {
		
		CfgGatewayPagamento cf = new CfgGatewayPagamento();
		
		String query="select cf from CfgGatewayPagamento cf "+
				  "where cf.systemId =:inSystemId ";
		
		try {
			Query q = em.createQuery(query);			
			q.setParameter("inSystemId", systemId);
			
			
			List<CfgGatewayPagamento> listaOut = (List<CfgGatewayPagamento>)q.getResultList();
			if (listaOut!= null && listaOut.size() >0) {
			   cf= listaOut.get(0);
			} else {
			   cf = null;
			}
		} catch (Exception e) {
			LOGGER.error("error on getCfgBySystemIdAndModelloVersamento "+
					        "  inSystemId="+ systemId , e);
			 
			throw new DAORuntimeException(e);
		}
		return cf;
	}
	
	
	
}
