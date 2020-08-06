package it.tasgroup.idp.bean;

import javax.ejb.Local;

import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.pojo.MonitoringData;

@Local
public interface IDataStorageManager {

	public DataInput executeApplicationTransactionTX1(String message);
	
	public MonitoringData executeApplicationTransactionTX2(DataInput input);

}
