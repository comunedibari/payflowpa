package it.tasgroup.idp.cart;

import javax.ejb.Local;

import it.tasgroup.idp.bean.CommandExecutorLocal;
import it.tasgroup.idp.esiti.EsitiModel;
import it.tasgroup.idp.pojo.MonitoringData;

@Local
public interface ICartSenderBean {

	public MonitoringData executeApplicationTransaction(EsitiModel esitiModel);

}
