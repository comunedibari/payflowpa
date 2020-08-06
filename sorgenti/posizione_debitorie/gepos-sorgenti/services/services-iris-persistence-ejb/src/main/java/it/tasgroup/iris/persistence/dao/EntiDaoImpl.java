
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.nch.is.fo.profilo.Enti;
import it.tasgroup.iris.persistence.dao.interfaces.EntiDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

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

@Stateless(name="EntiDaoService")
public class EntiDaoImpl extends DaoImplJpaCmtJta<Enti> implements EntiDao {
	
	private static final Logger LOGGER = LogManager.getLogger(EntiDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public List<Enti> listEntiAbleToAnonymousPayment(){
		return listEntiAbleToAnonymousPayment(null);
	}

	@Override
	public List<Enti> listEntiAbleToAnonymousPayment(String predeterminatoValue){
		List<Enti> retList = null;

		try {
			
			Query query = null;
			
			String querysql = "Select e.* from JLTENTI e " +
							"where e.STATO = 'A' "  +
							"and e.ID_ENTE in (Select distinct (t.ID_ENTE) from JLTENTR t where t.FL_INIZIATIVA = 'Y' and t.STATO = 'A' " +
							((predeterminatoValue != null) ? "and t.FL_PREDETERM = '" + predeterminatoValue + "' " : "") +
							 ") ORDER BY e.DENOM ";
			
			query= em.createNativeQuery(querysql, Enti.class);
			retList = (List<Enti>) query.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("error on listEntiAbleToAnonimousPayment ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public List<Enti> listEntiAbleForTributo(String idTributo){
		List<Enti> retList = null;
		try {
			Query query = null;
			org.hibernate.Session session = (org.hibernate.Session) em.getDelegate();
			Object dialect = org.apache.commons.beanutils.PropertyUtils.getProperty(session.getSessionFactory(), "dialect");
			String restrictionTributoEnte = "";
			if (dialect.toString().equals("org.hibernate.dialect.Oracle10gDialect")) {
				restrictionTributoEnte = "(RTRIM(t.ISTRUZIONI_PAGAMENTO) is not null )";
			} else {
				restrictionTributoEnte = "(RTRIM(t.ISTRUZIONI_PAGAMENTO) is not null and RTRIM(t.ISTRUZIONI_PAGAMENTO) <> '' )";
			}
			String querysql = "Select e.* from ENTI e " +
							"where e.STATO = 'A' "  +
							"and e.ID_ENTE in (Select distinct (t.ID_ENTE) from TRIBUTIENTI t where t.STATO = 'A' "
							+ "and t.ID_TRIBUTO = :idTributo and t.FL_INIZIATIVA='Y' and t.FL_NASCOSTO_FE='N' "
							+ "and " + restrictionTributoEnte + ")";
			query= em.createNativeQuery(querysql, Enti.class);
			query.setParameter("idTributo", idTributo);
			retList = (List<Enti>) query.getResultList();
		} catch (Exception e) {
			LOGGER.error("error on listEntiAbleToAnonimousPayment ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	@Override
	public List<Enti> listEntiAbleForCdTrbEnte(String cdTrbEnte){
		
		List<Enti> retList = null;

		try {
			
			Query query = null;
			
			String querysql = "Select e.* from ENTI e " +
							"where e.STATO = 'A' "  +
							"and e.ID_ENTE in (Select distinct (t.ID_ENTE) from TRIBUTIENTI t where t.STATO = 'A' and t.CD_TRB_ENTE = :cdTrbEnte) " +
							" ORDER BY e.DENOM ";
			
			query= em.createNativeQuery(querysql, Enti.class);
			
			query.setParameter("cdTrbEnte", cdTrbEnte);
			
			retList = (List<Enti>) query.getResultList();
			
		} catch (Exception e) {
			LOGGER.error("error on listEntiAbleToAnonimousPayment ", e);
			throw new DAORuntimeException(e);
		}
		return retList;
	}
	
	
	@Override
	public Enti getEntiAbleToAnonymousPaymentByIdEnte(String idEnte) {
		
		Enti ret = null;

		try {
			
			Query query = null;
			
			String querysql = "Select e.* from ENTI e " +
							"where e.ID_ENTE = :idEnte  ";
			
			query= em.createNativeQuery(querysql, Enti.class);
			query.setParameter("idEnte", idEnte);
			
			ret = (Enti) query.getSingleResult();
			
		} catch (Exception e) {
			LOGGER.error("error on getEntiAbleToAnonymousPaymentByIdEnte ", e);
			throw new DAORuntimeException(e);
		}
		return ret;
		
	}

	@Override
	public Enti getEntiAbleToAnonymousPaymentByLapl(String lapl) {
		
		Enti ret = null;

		try {
			
			Query query = null;
			
			String querysql = "Select e.* from ENTI e " +
							"where e.INTESTATARIO = " +
							"(select intestatario from INTESTATARI where LAPL = :lapl)";
			
			query= em.createNativeQuery(querysql, Enti.class);
			query.setParameter("lapl", lapl);
			
			ret = (Enti) query.getSingleResult();
			
		} catch (Exception e) {
			LOGGER.error("error on getEntiAbleToAnonymousPaymentByLapl ", e);
			throw new DAORuntimeException(e);
		}
		return ret;
		
	}

	@Override
	public Enti getEntiAbleToAnonymousPaymentByCdEnte(String cdEnte) {
		Enti ret = null;

		try {

			Query query = null;

			String querysql = "Select e.* from ENTI e where e.CD_ENTE = :cdEnte";

			query= em.createNativeQuery(querysql, Enti.class);
			query.setParameter("cdEnte", cdEnte);

			ret = (Enti) query.getSingleResult();

		} catch (Exception e) {
			LOGGER.error("error on getEntiAbleToAnonymousPaymentByIdEnte ", e);
			throw new DAORuntimeException(e);
		}
		return ret;
	}
	
	@Override
    public String getIdEnteFromIntestatario(String intestatario) {
        
        String ret = null;

        try {
            
            Query query = null;
            
            String querysql = "Select e.ID_ENTE from ENTI e " +
                            "where e.INTESTATARIO = :intestatario  ";
            
            query= em.createNativeQuery(querysql);
            
            query.setParameter("intestatario", intestatario);
            
            ret = (String) query.getSingleResult();
            
        } catch (Exception e) {
        	
            e.printStackTrace();
            
            LOGGER.error("error on getIdEnteFromIntestatario ", e);
            
            throw new DAORuntimeException(e);
        }
        return ret;
        
    }
	
	@Override
    public String getIdEnte(String siaEnte, String laplEnte) {
        
        String ret = "NOTFOUND";

        try {
            
            Query query = null;
            
            String querysql = "select id_ente from JLTENTI en, INTESTATARI inte " +
            		"where en.intestatario = inte.intestatario and inte.categoria='EN' ";
            
            if (!StringUtils.isEmpty(siaEnte))
            	querysql += "and inte.SIA=:SIA ";
            
            if (!StringUtils.isEmpty(laplEnte))
            	querysql += "and inte.LAPL=:LAPL ";
            
            query= em.createNativeQuery(querysql);
            
            if (!StringUtils.isEmpty(siaEnte))
            	query.setParameter("SIA", siaEnte);
            
            if (!StringUtils.isEmpty(laplEnte))
            	 query.setParameter("LAPL", laplEnte);
            
            List<String> enti = query.getResultList();
            
            if ( enti!= null && enti.size()>1)
            	throw new IllegalStateException("ID ENTE non univoco");
            
            if ( enti!= null && enti.size()==1)
            	ret = enti.get(0);
                        
        } catch (Exception e) {
        	
            e.printStackTrace();
            
            LOGGER.error("error on getIdEnte ", e);
            
            throw new DAORuntimeException(e);
            
        }
        
        return ret;
        
    }

	@Override
	public Enti readEnteByIntestatario(String intestatario) {
		
		String qs = "select e from Enti e where intestatario = :intestatario";
		
		Query q = em.createQuery(qs,Enti.class);
		
		q.setParameter("intestatario", intestatario);
		
		Enti e = (Enti)q.getSingleResult();
	    
		return e;
	}
	
	@Override
	public Enti readEnte(String codEnte) {
		
		Enti ente = null;
				
		try {
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			
			parameters.put("codEnte", codEnte);
			
			ente = (Enti) uniqueResultByQuery("enteByCodEnte", parameters, em);
			
		} catch (Exception e) {
	        
	        LOGGER.error("error on readEnte ", e);
	        
	        throw new DAORuntimeException(e);
	        
	    }
	    
		return ente;
	}

	@Override
	public Enti readEnteByCodFiscale(String codFiscaleEnte) {
		String queryEnteByCodFiscale = " from Enti where intestatarioobj.lapl=:codFiscaleEnte";
		Query q = em.createQuery(queryEnteByCodFiscale);
		q.setParameter("codFiscaleEnte", codFiscaleEnte);
		return (Enti)q.getSingleResult();
	}

	@Override
	public List<Enti> listTuttiEnti() {
		String queryEnti = "from Enti e order by e.denominazione";
		Query q = em.createQuery(queryEnti);
		return (List<Enti>)q.getResultList();
	}

	@Override
    public List<Enti> listaEnti() {
		
        List<Enti> l = null;
        
        try {
        	
            l = (List<Enti>) listByQuery("entiAttivi");
            
        } catch (Exception e) {
        	
        	LOGGER.error("error on listaEnti ", e);
        	
            throw new DAORuntimeException();
            
        }
        
        return l;
    }

}
