package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.iris.persistence.dao.interfaces.CfgFornitoreGatewayDao;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless(name="CfgFornitoreGatewayService")
public  class CfgFornitoreGatewayDaoImpl extends DaoImplJpaCmtJta<CfgFornitoreGateway> implements CfgFornitoreGatewayDao {

	private static final Logger LOGGER = LogManager.getLogger(CfgFornitoreGatewayDaoImpl.class);
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}

	@Override
	public CfgFornitoreGateway getCfgByBundleKey(String key) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("inBundleKey", key);
		CfgFornitoreGateway cf= null;
		try {
			cf = (CfgFornitoreGateway)uniqueResultByQuery("getcfByBundleKey",params,em);
		} catch (Exception e) {
			LOGGER.error("error on countByIdAndStatus "+params, e);
			throw new DAORuntimeException(e);
		}
		return cf;
	}

	@Override
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayAll() {
		List<CfgFornitoreGateway> cfg = null;
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			cfg = (List<CfgFornitoreGateway>) listByQuery("getCfgFornitoreGatewayAll", params);
		} catch (Exception e) {
			LOGGER.error("error on getCfgFornitoreGatewayAll ", e);
			throw new DAORuntimeException(e);
		}

		return cfg;
	}
	
	@Override
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayDistinctId() {
		List<CfgFornitoreGateway> retList = new ArrayList<CfgFornitoreGateway>();
		try {
			String sqlQuery = "select F.ID, F.DESCRIZIONE FROM CFG_FORNITORE_GATEWAY F WHERE F.ID IN " +
					"(SELECT DISTINCT ID_CFG_FORNITORE_GATEWAY " +
					"FROM CFG_GATEWAY_PAGAMENTO " +
					"WHERE ST_RIGA='V')";
			Query query= em.createNativeQuery(sqlQuery);
	        List<Object[]> res = query.getResultList();
	        for (Object[] row : res) {
	        	CfgFornitoreGateway item = new CfgFornitoreGateway();
	            item.setId(((Integer)row[0]).longValue());
	            item.setDescrizione((String)row[1]);
	            retList.add(item);
	        }
		} catch (Exception e) {
			LOGGER.error("error on getCfgFornitoreGatewayDistinctId ", e);
			throw new DAORuntimeException(e);
		}
		 return retList;
	}
	
	@Override
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayBoe(String idEnteCreditore) {
		//language=HTML
		String queryString = "SELECT DISTINCT\n" +
		"  F.*\n" +
		"FROM CFG_FORNITORE_GATEWAY F\n" +
		"WHERE\n" +
		"  F.ID IN (SELECT DISTINCT ID_CFG_FORNITORE_GATEWAY\n" +
		"           FROM CFG_GATEWAY_PAGAMENTO)\n" +
		"  AND\n" +
		"  (\n" +
		"    (F.ID = '1' AND 1 = (SELECT COUNT(*)\n" +
		"                         FROM ENTI E\n" +
		"                         WHERE E.FL_BANCA_TESORIERA = 'S' AND E.ID_ENTE = :idEnte))\n" +
		"    OR\n" +
		"    (F.ID = '4' AND 1 = (SELECT COUNT(*)\n" +
		"                         FROM ENTI E\n" +
		"                           \n" +
		"                         WHERE E.FL_NDP = 'S' AND E.ID_ENTE = :idEnte))\n" +
		"    OR\n" +
		"    F.ID IN (SELECT ID_CFG_FORNITORE_GATEWAY\n" +
		"             FROM CFG_GATEWAY_PAGAMENTO, ENTI E\n" +
		"             WHERE SYSTEM_ID = E.CD_AZIENDA_SANITARIA AND E.ID_ENTE = :idEnte)\n" +
		"    OR\n" +
		"    F.ID = '3' AND 1 = (SELECT COUNT(*)\n" +
		"                        FROM ENTI E\n" +
		"                        WHERE E.CD_AZIENDA_SANITARIA = '090108' AND E.ID_ENTE = :idEnte)\n" +
		"  )";
		
		Query query = em.createNativeQuery(queryString, CfgFornitoreGateway.class);
		query.setParameter("idEnte", idEnteCreditore);
		List retList = query.getResultList();
		return retList;
	}
		
	@Override
	public List<CfgFornitoreGateway> readCfgFornitoreGatewayCircuito(String idEnte) {
		List<CfgFornitoreGateway> retList = new ArrayList<CfgFornitoreGateway>();
		String sqlQuery = "SELECT DISTINCT F.ID, F.DESCRIZIONE FROM CFG_FORNITORE_GATEWAY F" + 
				" WHERE F.ID IN (SELECT DISTINCT ID_CFG_FORNITORE_GATEWAY FROM CFG_GATEWAY_PAGAMENTO)" + 
				" AND (F.ID = '1' AND 1 = (SELECT COUNT(*) FROM ENTI E WHERE E.FL_BANCA_TESORIERA = 'S' AND E.ID_ENTE=:idEnte))" + 
				" OR (F.ID = '4' AND 1 = (SELECT COUNT(*) FROM ENTI E WHERE E.FL_NDP = 'S' AND E.ID_ENTE=:idEnte))" +
				" OR F.ID IN (SELECT ID_CFG_FORNITORE_GATEWAY FROM CFG_GATEWAY_PAGAMENTO, ENTI E WHERE SYSTEM_ID=E.CD_AZIENDA_SANITARIA AND E.ID_ENTE=:idEnte)" + 
				" OR F.ID = '3' AND 1 = (SELECT COUNT(*) FROM ENTI E WHERE E.CD_AZIENDA_SANITARIA = '090108' AND E.ID_ENTE=:idEnte)";

        Query query= em.createNativeQuery(sqlQuery);
        query.setParameter("idEnte", idEnte);
        
        List<Object[]> res = query.getResultList();
        
        for (Object[] row : res) {
        	CfgFornitoreGateway item = new CfgFornitoreGateway();
            item.setId(((Integer)row[0]).longValue());
            item.setDescrizione((String)row[1]);
            retList.add(item);
        }
        return retList;
	}

}
