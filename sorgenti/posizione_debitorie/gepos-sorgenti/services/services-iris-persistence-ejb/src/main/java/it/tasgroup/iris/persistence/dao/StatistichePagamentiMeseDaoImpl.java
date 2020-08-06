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

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.idp.backoffice.statistiche.RiepilogoStatisticheVO;
import it.tasgroup.iris.domain.StatistichePagamentiMese;
import it.tasgroup.iris.persistence.dao.interfaces.StatistichePagamentiMeseDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

@Stateless(name="StatistichePagamentiMeseDaoService")
public class StatistichePagamentiMeseDaoImpl extends DaoImplJpaCmtJta<StatistichePagamentiMese> implements StatistichePagamentiMeseDao {
	
	private static final Logger log = LogManager.getLogger(StatistichePagamentiMeseDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> riepilogoPagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat){
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			String theQuery = "SELECT "
					+ "sil.deSystem, "
					+ "tr.deTrb, "
					+ "SUM(spm.numeroPagamenti), "
					+ "SUM(spm.importo) "
					
				 	+ "FROM StatistichePagamentiMese spm, " 
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
					 	+ (StringUtils.isNotEmpty(stat.getTipo()) ?
					 			" AND spm.id.atteso=:tipoPagamento" : "")

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
			if (StringUtils.isNotEmpty(stat.getTipo()))
				query.setParameter("tipoPagamento", stat.getTipo());
			result = query.getResultList();
		} catch (Exception e) {
			log.error("riepilogoPagamentiEseguitiPerTipoDebito [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> riepilogoPagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat){
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			String theQuery = "SELECT "
					+ "f.descrizione, "
					+ "m.descrizione, "
					+ "SUM(spm.numeroPagamenti), "
					+ "SUM(spm.importo) "
				 	+ "FROM StatistichePagamentiMese spm, " 
					+ "CfgModalitaPagamento m, "
					+ "CfgFornitoreGateway f "
				 	+ "WHERE " 
					 	+ "spm.id.circuito=f.id " 
					 	+ "AND spm.id.modalita=m.id "
					 	+ "AND spm.id.idEnte=:idEnte "
					 	+ "AND year(spm.id.meseEstrazione)=:anno "
					 	+ ((stat.getMese() != null) ? 
					 			" AND month(spm.id.meseEstrazione)=:mese" : "")
					 	+ (StringUtils.isNotEmpty(stat.getCdTrbEnte()) ?
					 			" AND spm.id.cdTributoEnte=:cdTributoEnte" : "")
					 	+ (StringUtils.isNotEmpty(stat.getSil()) ?
					 			" AND spm.id.idSystem=:sil" : "")
					 	+ (StringUtils.isNotEmpty(stat.getTipo()) ?
					 			" AND spm.id.atteso=:tipoPagamento" : "")
					 	
						+ " GROUP BY spm.id.circuito, spm.id.modalita";

			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			if (stat.getMese() != null) 
				query.setParameter("mese", stat.getMese());
			if (StringUtils.isNotEmpty(stat.getCdTrbEnte()))
				query.setParameter("cdTributoEnte", stat.getCdTrbEnte());
			if (StringUtils.isNotEmpty(stat.getSil()))
				query.setParameter("sil", stat.getSil());
			if (StringUtils.isNotEmpty(stat.getTipo()))
				query.setParameter("tipoPagamento", stat.getTipo());
			result = query.getResultList();
		} catch (Exception e) {
			log.error("riepilogoPagamentiEseguitiPerCircuito [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] totalePagamentiEseguitiPerCircuito(String idEnte, int anno, RiepilogoStatisticheVO stat){
		Object[] result = new Object[3];
		try {
			String theQuery = "SELECT "
					+ "SUM(spm.numeroPagamenti), "
					+ "SUM(spm.importo) "
				 	+ "FROM StatistichePagamentiMese spm, " 
					+ "CfgModalitaPagamento m, "
					+ "CfgFornitoreGateway f "
				 	+ "WHERE " 
					 	+ "spm.id.circuito=f.id " 
					 	+ "AND spm.id.modalita=m.id "
					 	+ "AND spm.id.idEnte=:idEnte "
					 	+ "AND year(spm.id.meseEstrazione)=:anno "
					 	+ ((stat.getMese() != null) ? 
					 			" AND month(spm.id.meseEstrazione)=:mese" : "")
					 	+ (StringUtils.isNotEmpty(stat.getCdTrbEnte()) ?
					 			" AND spm.id.cdTributoEnte=:cdTributoEnte" : "")
					 	+ (StringUtils.isNotEmpty(stat.getSil()) ?
					 			" AND spm.id.idSystem=:sil" : "")
					 	+ (StringUtils.isNotEmpty(stat.getTipo()) ?
					 			" AND spm.id.atteso=:tipoPagamento" : "")
						+ " GROUP BY spm.id.circuito, spm.id.modalita";

			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			if (stat.getMese() != null) 
				query.setParameter("mese", stat.getMese());
			if (StringUtils.isNotEmpty(stat.getCdTrbEnte()))
				query.setParameter("cdTributoEnte", stat.getCdTrbEnte());
			if (StringUtils.isNotEmpty(stat.getSil()))
				query.setParameter("sil", stat.getSil());
			if (StringUtils.isNotEmpty(stat.getTipo()))
				query.setParameter("tipoPagamento", stat.getTipo());
			List<Object[]> resultList = query.getResultList(); 
			Object[] vect = resultList.get(0); 
			if (vect[0] != null) {
				result[0] = vect[0];
				result[1] = vect[1];
				result[1] = vect[2];
			}
		} catch (Exception e) {
			log.error("riepilogoPagamentiEseguitiPerCircuito [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> riepilogoPagamentiEseguitiMese(String idEnte, int anno) {
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			String theQuery = "SELECT month(spm.id.meseEstrazione), "
					+ " SUM(spm.numeroPagamenti), SUM(spm.importo)"
				 	+ " FROM StatistichePagamentiMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ " GROUP BY spm.id.meseEstrazione"
					 	+ " ORDER BY spm.id.meseEstrazione DESC";
			
			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			result = query.getResultList(); 
		} catch (Exception e) {
			log.error("riepilogoPagamentiEseguitiMese [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		
		return result;
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> riepilogoPagamentiSpontaneiEseguitiMese(String idEnte, int anno) {
		List<Object[]> result = new ArrayList<Object[]>();
		try {
			String theQuery = "SELECT month(spm.id.meseEstrazione), "
					+ " SUM(spm.numeroPagamenti), SUM(spm.importo)"
				 	+ " FROM StatistichePagamentiMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ " AND spm.id.atteso = 'N'"
					 	+ " GROUP BY spm.id.meseEstrazione"
					 	+ " ORDER BY spm.id.meseEstrazione DESC";
			

			Query query = em.createQuery(theQuery); 
			query.setParameter("idEnte", idEnte);
			query.setParameter("anno", anno);
			result = query.getResultList(); 
		} catch (Exception e) {
			log.error("riepilogoPagamentiSpontaneiEseguitiMese [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] totalePagamentiEseguitiAnno(String idEnte, int anno) {
		Object[] result = new Object[2];
		try {
			String theQuery = "SELECT SUM(spm.numeroPagamenti), "
					+ " SUM(spm.importo)"
				 	+ " FROM StatistichePagamentiMese spm" 
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
			log.error("totalePagamentiEseguitiAnno [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] totalePagamentiSpontaneiEseguitiAnno(String idEnte, int anno) {
		Object[] result = new Object[2];
		try {
			String theQuery = "SELECT SUM(spm.numeroPagamenti), "
					+ " SUM(spm.importo)"
				 	+ " FROM StatistichePagamentiMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ " AND spm.id.atteso = 'N'";
	
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
			log.error("totalePagamentiSpontaneiEseguitiAnno [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object[] totalePagamentiEseguitiPerTipoDebito(String idEnte, int anno, RiepilogoStatisticheVO stat) {
		Object[] result = new Object[2];
		try {
			String theQuery = "SELECT"
					+ " SUM(spm.numeroPagamenti), "
					+ " SUM(spm.importo)"
				 	+ " FROM StatistichePagamentiMese spm" 
				 	+ " WHERE" 
					 	+ " spm.id.idEnte=:idEnte"
					 	+ " AND year(spm.id.meseEstrazione)=:anno"
					 	+ ((stat.getMese() != null) ? 
					 			" AND month(spm.id.meseEstrazione)=:mese" : "")
					 	+ (StringUtils.isNotEmpty(stat.getCdTrbEnte()) ?
					 			" AND spm.id.cdTributoEnte=:cdTributoEnte" : "")
					 	+ (StringUtils.isNotEmpty(stat.getSil()) ?
					 			" AND spm.id.idSystem=:sil" : "")
					 	+ (StringUtils.isNotEmpty(stat.getTipo()) ?
					 			" AND spm.id.atteso=:tipoPagamento" : "")
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
			if (StringUtils.isNotEmpty(stat.getTipo()))
				query.setParameter("tipoPagamento", stat.getTipo());
			List<Object[]> resultList = query.getResultList(); 
			Object[] vect = resultList.get(0); 
			if (vect[0] != null) {
				result[0] = vect[0];
				result[1] = vect[1];
			}
		} catch (Exception e) {
			log.error("totalePagamentiEseguitiPerTipoDebito [" + e.getMessage() + "]");
			throw new DAORuntimeException(e);
		}
		return result;
	}



}
