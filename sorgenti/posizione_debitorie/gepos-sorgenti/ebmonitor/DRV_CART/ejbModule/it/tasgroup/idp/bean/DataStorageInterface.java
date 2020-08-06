package it.tasgroup.idp.bean;

import java.util.List;

import javax.ejb.Local;

import it.tasgroup.dse.service.DataStoreEngineService;
import it.tasgroup.dse.view.DataInput;
import it.tasgroup.idp.domain.messaggi.ErroriCart;
import it.tasgroup.idp.domain.messaggi.ErroriIdp;
import it.tasgroup.idp.domain.messaggi.EsitiCart;
import it.tasgroup.idp.domain.messaggi.NotificheCart;
import it.tasgroup.idp.domain.messaggi.NotifichePagamenti;
import it.tasgroup.idp.domain.messaggi.PendenzeCart;
import it.tasgroup.idp.domain.messaggi.PendenzeCartMessage;
import it.tasgroup.idp.notifiche.NotifichePagamentoModel;
import it.tasgroup.idp.notifiche.PagamentoModel;

@Local
public interface DataStorageInterface {

	public  void persist(ErroriCart cart);

	public  void persistWithTx(ErroriCart cart);

	public  void persistPendenzeCart(PendenzeCartMessage cart);
	public  void persistSemplificationPendenzeCart(PendenzeCartMessage cart);

	public  void persistEsitiCart(EsitiCart cart);

	public  void updateStatoPendenza(String E2EMsgId,
			String idMittente, String silMittente, String stato);

	public  void callMe(DataStoreEngineService dataStoreEngineService, DataInput input);
	
	public  void callMe(DataStoreEngineService dataStoreEngineService, DataInput input, boolean hidden);

	public  void persistNotificaCart(NotificheCart notificheCart);

	public  void updateStatoNotifichePagamenti(
			List<PagamentoModel> listaPagamentiModel,
			NotifichePagamentoModel model);

	public  void callMeToRestart(DataStoreEngineService dataStoreEngineService,
			DataInput input, String[] array);
	
	public void updateEsitoAndPendenze(String e2emsgid, String senderId, String senderSys, String esitoDaSpedire);

	public void flush();
	
	public void updateReinoltroEsiti(String e2emsgid, String senderId, String senderSys, ErroriIdp erroreIdp, String trtSenderId, String trtSenderSys);
	
	public void updateReinoltroNotifiche(String e2emsgid, String senderId, String senderSys, String trtSenderId, String trtSenderSys, ErroriIdp erroreIdp);

	public void updateStatoNotifichePagamenti(NotifichePagamenti notPagamento);

	public void persistNotificheCartAndUpdateStatoNotPagamenti(
			NotificheCart notificheCart,
			List<PagamentoModel> listaPagamentiModel,
			NotifichePagamentoModel model);
	
	public void persistNotificheCartAndUpdateStatoNotPagamenti(
			NotificheCart notificheCart,
			NotifichePagamenti notPagamento);
	
		
}