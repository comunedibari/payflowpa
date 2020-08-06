package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.pagamenti.Multe;
import it.tasgroup.iris.persistence.dao.interfaces.MulteDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@Stateless(name="MulteDaoService")
public class MulteDaoImpl extends DaoImplJpaCmtJta<Multe> implements MulteDao{

	private static final Logger LOGGER = LogManager.getLogger(MulteDaoImpl.class);

	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}	

	@Override
	public Multe getMultaByTargaAndNumVerbale(String targa, String numeroVerbale) {
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("targa", targa);
		parameters.put("numeroVerbale", numeroVerbale);
		Multe multa = null;
		
		try {
			multa = (Multe) uniqueResultByQuery("getMultaByTargaAndNumeroVerbale",parameters);
		} catch (Exception e) {
			LOGGER.error("error on  getMultaByTargaAndNumVerbale, targa = " + targa + " , numeroVerbale = " + numeroVerbale, e);
			throw new DAORuntimeException(e);
		}
		
		return multa;
	}

	@Override
	public Multe saveMulta(Multe multa) {
		Multe multaManged = null;
        try {
        	multaManged = update(multa);
		} catch (Exception e) {
			LOGGER.error("error on saveMulta ", e);
			throw new DAORuntimeException(e);
		}
		return multaManged;
		
	}


}

