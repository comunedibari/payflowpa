package it.tasgroup.iris.persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.SveLog;
import it.tasgroup.iris.domain.SveStato;
import it.tasgroup.iris.persistence.dao.interfaces.MonitoraggioPuliziaCaricamentiDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="MonitoraggioPuliziaCaricamentiDaoService")
public class MonitoraggioPuliziaCaricamentiDaoImpl extends DaoImplJpaCmtJta<SveLog> implements MonitoraggioPuliziaCaricamentiDao {
	
	private static final Logger log = LogManager.getLogger(MonitoraggioPuliziaCaricamentiDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SveStato> getListaStatoSvecchiamento() {
		String query = "findAllSveStato";
		Map<String, String> params = new HashMap<String, String>();
		try {
			List<SveStato> stati = (List<SveStato>) listByQuery(query, params);
			return stati;
		} catch (Exception e) {
			log.error("Error on getCanali", e);
			throw new DAORuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SveLog> getListaLogSvecchiamento(String nomeProcesso) {
		List<SveLog> retList = new ArrayList<SveLog>();
		try {
			Query query =  em.createNamedQuery("findSveLogByNomeProcesso", SveLog.class);
			query.setParameter("nomeProcesso", nomeProcesso);
			retList = (List<SveLog>) query.getResultList();
		} catch (Exception e) {
			log.error("error on getListaLogSvecchiamento ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}

}
