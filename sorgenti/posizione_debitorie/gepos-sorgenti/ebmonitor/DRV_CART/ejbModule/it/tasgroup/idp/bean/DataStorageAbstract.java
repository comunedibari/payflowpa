package it.tasgroup.idp.bean;

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;

import java.util.List;

public abstract class DataStorageAbstract implements DataStorageInterface {
	
	@Override
	public void persistWithTx(ErroriCart cart) {
		// TODO Auto-generated method stub			
	}
	
		
	@Override
	public void persist(ErroriCart cart) {
		// TODO Auto-generated method stub
	}
	
	
	@Override
	public void persistNotificaCart(NotificheCart notificheCart) {
		// TODO Auto-generated method stub	
	}


	@Override
	public void updateStatoNotifichePagamenti(
			List<PagamentoModel> listaPagamentiModel,
			NotifichePagamentoModel model) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void callMeToRestart(DataStoreEngineService dataStoreEngineService,
			DataInput input, String[] array) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void persistPendenzeCart(PendenzeCartMessage cart) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void persistEsitiCart(EsitiCart cart) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateStatoPendenza(String E2EMsgId, String idMittente,
			String silMittente, String stato) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void callMe(DataStoreEngineService dataStoreEngineService,
			DataInput input) {
		// TODO Auto-generated method stub
	
	}	
	
	@Override
	public void callMe(DataStoreEngineService dataStoreEngineService,
			DataInput input, boolean hidden) {
		// TODO Auto-generated method stub
	
	}		
	
}