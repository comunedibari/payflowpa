package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgUtenteModalitaPagamento;
import it.tasgroup.iris.persistence.dao.interfaces.CfgUtenteModalitaPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="CfgUtenteModalitaPagamentoService")
public  class CfgUtenteModalitaPagamentoDaoImpl extends DaoImplJpaCmtJta<CfgUtenteModalitaPagamento> implements CfgUtenteModalitaPagamentoDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgFornitoreGatewayDaoImpl.class);	

	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	public void setEntityMangerFactory(EntityManagerFactory emf){
		this.em = emf.createEntityManager();
	}

	@Override
	public CfgUtenteModalitaPagamento getCfgUtenteModalitaPagamentoByKey(
			String codiceFiscale, String applicationId, String systemId) {
		CfgUtenteModalitaPagamento cf = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codFiscale", codiceFiscale);		
		params.put("appID", applicationId);		
		params.put("sysID", systemId);		

		try {			
			cf= (CfgUtenteModalitaPagamento)uniqueResultByQuery("getCfgUtenteModalitaPagamentoByKey",params,em);		
		} catch (Exception e) {
			LOGGER.error("error on getLstCfgUtenteModalitaPagamentoByCodiceFiscale "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cf;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<CfgUtenteModalitaPagamento> getLstCfgUtenteModalitaPagamentoByCodiceFiscale(
			String codiceFiscale) {
		List<CfgUtenteModalitaPagamento> cf = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codFiscale", codiceFiscale);		

		try {			
			cf= (List<CfgUtenteModalitaPagamento>)listByQuery("listCfgUtenteModalitaPagamentoByCodiceFiscale",params,em);		
		} catch (Exception e) {
			LOGGER.error("error on getLstCfgUtenteModalitaPagamentoByCodiceFiscale "+params, e);		
			throw new DAORuntimeException(e);
		}
		return cf;
	}



}
