package it.tasgroup.iris.persistence.dao.interfaces;

import it.tasgroup.iris.domain.CfgIrisGatewayClient;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;

@Local
public interface CfgIrisGatewayClientDAO extends Dao<CfgIrisGatewayClient>{
	
	public CfgIrisGatewayClient retrieveGatewayClient(String applicationId, String systemId);
	
}
