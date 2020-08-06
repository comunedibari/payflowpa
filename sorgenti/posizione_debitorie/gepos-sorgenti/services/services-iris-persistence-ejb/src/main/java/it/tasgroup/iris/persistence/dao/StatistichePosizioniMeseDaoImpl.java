package it.tasgroup.iris.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.tasgroup.iris.domain.StatistichePosizioniMese;
import it.tasgroup.iris.persistence.dao.interfaces.StatistichePosizioniMeseDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="StatistichePosizioniMeseDaoService")
public class StatistichePosizioniMeseDaoImpl extends DaoImplJpaCmtJta<StatistichePosizioniMese> implements StatistichePosizioniMeseDao {
	private static final Logger log = LogManager.getLogger(StatistichePosizioniMeseDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> riepilogoPosizioniEseguiteMese(String idEnte, int anno) {
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			String theQuery = "SELECT month(spm.id.meseEstrazione), "
					+ " SUM(spm.numeroPosizioni), SUM(spm.importo)"
				 	+ " FROM StatistichePosizioniMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ " GROUP BY spm.id.meseEstrazione"
					 	+ " ORDER BY spm.id.meseEstrazione DESC";
			log.debug("riepilogoPosizioniEseguiteMese [query:" + theQuery + " - idEnte: " + idEnte + " - anno: " + anno + "]");
			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			result = query.getResultList(); 
		} catch (Exception e) {
			log.error("riepilogoPosizioniEseguiteMese [" + e.getMessage() + "]");
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> riepilogoPosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat){
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			String theQuery = "SELECT "
					+ "sil.deSystem, "
					+ "tr.deTrb, "
					+ "SUM(spm.numeroPosizioni), "
					+ "SUM(spm.importo) "
					
				 	+ "FROM StatistichePosizioniMese spm, " 
				 	+ "TributoEnte tr, "
				 	+ "SistemaEnte sil "
				 	
				 	+ "WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ ((stat.getMese() != null) ? 
					 			" AND month(spm.id.meseEstrazione)=:mese" : "")
					 	+ (StringUtils.isNotEmpty(stat.getCdTrbEnte()) ?
					 			" AND spm.id.cdTributoEnte=:cdTributoEnte" : "")
					 	+ (StringUtils.isNotEmpty(stat.getSil()) ?
					 			" AND spm.id.idSystem=:sil" : "")
					 	// 
					 	+ " AND tr.tribEnId.idEntePk = spm.id.idEnte"
					 	+ " AND tr.tribEnId.cdTrbEntePk = spm.id.cdTributoEnte"
					 	
						+ " AND sil.sisEntId.idEnte = spm.id.idEnte"
						+ " AND sil.sisEntId.idSystem = spm.id.idSystem"
					 	
						+ " group by spm.id.idSystem, spm.id.cdTributoEnte"
						+ " order by spm.id.idSystem, spm.id.cdTributoEnte";
	
			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			if (stat.getMese() != null) 
				query.setParameter("mese", stat.getMese());
			if (StringUtils.isNotEmpty(stat.getCdTrbEnte()))
				query.setParameter("cdTributoEnte", stat.getCdTrbEnte());
			if (StringUtils.isNotEmpty(stat.getSil()))
				query.setParameter("sil", stat.getSil());
			
			result = query.getResultList();
		} catch (Exception e) {
			log.error("totalePosizioniCaricate [" + e.getMessage() + "]");
		}
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] totalePosizioniCaricate(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		Object[] result = new Object[2];
		try {
			String theQuery = "SELECT"
					+ " SUM(spm.numeroPosizioni), "
					+ " SUM(spm.importo)"
				 	+ " FROM StatistichePosizioniMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ ((stat.getMese() != null) ? 
					 			" AND month(spm.id.meseEstrazione)=:mese" : "")
					 	+ (StringUtils.isNotEmpty(stat.getCdTrbEnte()) ?
					 			" AND spm.id.cdTributoEnte=:cdTributoEnte" : "")
					 	+ (StringUtils.isNotEmpty(stat.getSil()) ?
					 			" AND spm.id.idSystem=:sil" : "")
					 	+ " GROUP BY year(spm.id.meseEstrazione)";
	
			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			if (stat.getMese() != null) 
				query.setParameter("mese", stat.getMese());
			if (StringUtils.isNotEmpty(stat.getCdTrbEnte()))
				query.setParameter("cdTributoEnte", stat.getCdTrbEnte());
			if (StringUtils.isNotEmpty(stat.getSil()))
				query.setParameter("sil", stat.getSil());
			List<Object[]> resultList = query.getResultList(); 
			Object[] vect = resultList.get(0); 
			if (vect[0] != null) {
				result[0] = vect[0];
				result[1] = vect[1];
			}
		} catch (Exception e) {
			log.error("totalePosizioniEseguiteAnno [" + e.getMessage() + "]");
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object[] totalePosizioniEseguiteAnno(String idEnte, int anno) {
		Object[] result = new Object[2];
		try {
			String theQuery = "SELECT SUM(spm.numeroPosizioni), "
					+ " SUM(spm.importo)"
				 	+ " FROM StatistichePosizioniMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno";
	
			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			List<Object[]> resultList = query.getResultList(); 
			Object[] vect = resultList.get(0); 
			if (vect[0] != null) {
				result[0] = vect[0];
				result[1] = vect[1];
			}
		} catch (Exception e) {
			log.error("totalePosizioniEseguiteAnno [" + e.getMessage() + "]");
		}
		return result;
	}
}
