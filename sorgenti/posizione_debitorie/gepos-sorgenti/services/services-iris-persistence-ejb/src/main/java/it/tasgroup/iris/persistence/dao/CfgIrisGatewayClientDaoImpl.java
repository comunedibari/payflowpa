/**
 * 
 */
package it.tasgroup.iris.persistence.dao;

import it.nch.fwk.fo.cross.exception.DAORuntimeException;
import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.iris.persistence.dao.interfaces.CfgIrisGatewayClientDAO;
import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PazziK
 * 
 */
@Stateless(name="CfgIrisGatewayClientDAO")
public class CfgIrisGatewayClientDaoImpl extends DaoImplJpaCmtJta<CfgIrisGatewayClient> implements CfgIrisGatewayClientDAO {

	private static final Logger LOGGER = LogManager.getLogger(CfgIrisGatewayClientDaoImpl.class);	
	
	@PersistenceContext(unitName="IrisPU")
	public void setEntityManager(EntityManager em){
		this.em=em;
	}
	
	@Override
	public CfgIrisGatewayClient retrieveGatewayClient(String applicationId, String systemId) {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("appID", applicationId);
			params.put("sysID", systemId);
			
			try {
				
				CfgIrisGatewayClient gtwClient = (CfgIrisGatewayClient)uniqueResultByQuery("getCfgIrisGtwClientByAppIDAndSysID",params,em);
				
				return gtwClient;
				
			} catch (Exception e) {
				LOGGER.error("error on retrieveGatewayClient "+params, e);		
				throw new DAORuntimeException(e);
			}
		
	}

}
