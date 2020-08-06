package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgCanalePagamento;
import it.tasgroup.iris.persistence.dao.interfaces.CfgCanalePagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CfgCanalePagamentoDaoService")
public  class CfgCanalePagamentoDaoImpl extends DaoImplJpaCmtJta<CfgCanalePagamento> implements CfgCanalePagamentoDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgFornitoreGatewayDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public CfgCanalePagamento getCfgByBundleKey(String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inBundleKey", key);
		CfgCanalePagamento cf= null;
		try {
			cf = (CfgCanalePagamento)uniqueResultByQuery("getcfcnByBundleKey",params,em);
		} catch (Exception e) {
			LOGGER.error("error on countByIdAndStatus "+params, e);
			throw new DAORuntimeException(e);
		}
		return cf;
	}

}
