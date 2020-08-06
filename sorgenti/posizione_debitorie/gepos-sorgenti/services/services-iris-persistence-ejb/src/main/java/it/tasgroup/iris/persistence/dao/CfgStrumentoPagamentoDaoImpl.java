package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgStrumentoPagamento;
import it.tasgroup.iris.persistence.dao.interfaces.CfgStrumentoPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CfgStrumentoPagamentoDao")
public  class CfgStrumentoPagamentoDaoImpl extends DaoImplJpaCmtJta<CfgStrumentoPagamento> implements CfgStrumentoPagamentoDao {
    
	private static final Logger LOGGER = LogManager.getLogger(CfgFornitoreGatewayDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public List<CfgStrumentoPagamento> getCfgStrumentoPagamentoAll(){
		List<CfgStrumentoPagamento> cfg = null;
		
		try {
			cfg = (List<CfgStrumentoPagamento>) listByQuery("getCfgStrumentoPagamentoAll");
		} catch (Exception e) {
			LOGGER.error("error on getCfgStrumentoPagamentoAll ", e);
			throw new DAORuntimeException(e);
		}
		
		return cfg;
	}
	
	@Override
	public CfgStrumentoPagamento getCfgStrumentoPagamentoById(Long id) {
		CfgStrumentoPagamento cf = new CfgStrumentoPagamento();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);		
		
		try {			
	     cf= (CfgStrumentoPagamento)uniqueResultByQuery("getCfgStrumentoPagamentoById",params,em);		
		} catch (Exception e) {
			LOGGER.error("error on getCfgStrumentoPagamentoById "+params, e);		
			throw new DAORuntimeException(e);
		}
	     return cf;
	}
}
