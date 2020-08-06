package it.tasgroup.iris.persistence.dao.interfaces;

import java.util.List;

import it.tasgroup.iris.domain.CfgFornitoreGateway;
import it.tasgroup.services.dao.ejb.Dao;

import javax.ejb.Local;
@Local
public interface CfgFornitoreGatewayDao extends Dao<CfgFornitoreGateway>{

	public CfgFornitoreGateway getCfgByBundleKey(String key);

	public List<CfgFornitoreGateway> getCfgFornitoreGatewayAll();
	
	public List<CfgFornitoreGateway> getCfgFornitoreGatewayDistinctId();
		
	public  List<CfgFornitoreGateway> getCfgFornitoreGatewayBoe(String idEnteCreditore);
	public List<CfgFornitoreGateway> readCfgFornitoreGatewayCircuito(String idEnte);
	
}
