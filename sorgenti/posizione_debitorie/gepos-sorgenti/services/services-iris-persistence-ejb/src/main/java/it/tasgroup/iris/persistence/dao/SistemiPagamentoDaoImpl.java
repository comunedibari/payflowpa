package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.SistemiPagamento;
import it.tasgroup.iris.persistence.dao.interfaces.SistemiPagamentoDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="SistemiPagamentoDaoService")
public class SistemiPagamentoDaoImpl extends DaoImplJpaCmtJta<SistemiPagamento> implements SistemiPagamentoDao{

	private static final Logger LOGGER = LogManager.getLogger(SistemiPagamentoDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public List<SistemiPagamento> getSystemsById(String systemId)  {
		List<SistemiPagamento> sistemi = null;
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put("idSistema", systemId);
		try {
			sistemi = (List<SistemiPagamento>)listByQuery("getSystemById", parameters);
		} catch (Exception e) {
			LOGGER.error("error on getSystemsById "+ parameters, e);
			throw new DAORuntimeException(e);
		}
		return sistemi;
	}

	@Override
	public List<SistemiPagamento> getSilsById(String systemSil) {
		List<SistemiPagamento> applicazioni = null;
		Map<String,String> parameters = new HashMap<String, String>();

		parameters.put("idApplicazione", systemSil);
		try {
			applicazioni = (List<SistemiPagamento>)listByQuery("getSilById", parameters);
		} catch (Exception e) {
			LOGGER.error("error on getSilsById "+ parameters, e);
			throw new DAORuntimeException(e);
		}
		return applicazioni;
	}

}
