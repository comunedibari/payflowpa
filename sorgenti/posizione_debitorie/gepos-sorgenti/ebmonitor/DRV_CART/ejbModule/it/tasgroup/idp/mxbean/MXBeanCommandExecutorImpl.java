package it.tasgroup.idp.mxbean;

import it.tasgroup.idp.bean.CommandExecutor;
import it.tasgroup.idp.bean.ObjectCommandExecutor;
import it.tasgroup.idp.bean.SpazioCommandExecutor;
import it.tasgroup.idp.bean.StressTestCommandExecutor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.timer.TimerCommandExecutor;
import it.tasgroup.idp.util.IdpServiceLocator;
import it.tasgroup.idp.util.ServiceLocalMapper;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Stateless
@Remote(MXBeanCommandExecutor.class)
public class MXBeanCommandExecutorImpl implements MXBeanCommandExecutor {

	private final Log logger = LogFactory.getLog(this.getClass());
 
	@Override
	public MonitoringData executeTimeout() {

		try {
			logger.info("executing timeout" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.TimeoutRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = timeout , Eccezione = " + e.getMessage());
		}
		return null;
	}


	
	@Override
	public String listaPendenzeDaSpedire() {
		String html = null;
		try {
			logger.info("executing listaPendenzeDaSpedire" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.SpedizioneEsitiRemote);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaPendenzeDaSpedire , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaPendenzeSospese() {
		String html = null;
		try {
			logger.info("NOT IMPLEMENTED listaPendenzeSospese" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RipartenzaDataStorageRemote);
//			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaPendenzeSospese , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	@Override
	public MonitoringData executeCreazioneSpedizioneEsiti() {

		try {
			logger.info("executing CreazioneSpedizioneEsiti" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.SpedizioneEsitiRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			logger.info("CmdName = CreazioneSpedizioneEsiti , Eccezione = " + e.getMessage());
		}
		return null;
	}	
	
	@Override
	public MonitoringData executeInvioEsiti() {

		try {
			logger.info("executing InvioEsiti" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CartSenderRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			logger.info("CmdName = InvioEsiti , Eccezione = " + e.getMessage());
		}
		return null;
	}	
	
	
	
	
	@Override
	public String listaPendenzeTimeout() {
		String html = null;
		try {
			logger.info("executing listaPendenzeTimeout" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.TimeoutRemote);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaPendenzeTimeout , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	@Override
	public MonitoringData executeRipartenza() {

		try {
			logger.info("executing Ripartenza" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RipartenzaDataStorageRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			logger.info("CmdName = Ripartenza , Eccezione = " + e.getMessage());
		}
		return null;
	}
	
	
	@Override
	public MonitoringData executeReinoltroEsiti() {

		try {
			logger.info("executing ReinoltroEsiti" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.ReinoltroEsitiRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			logger.info("CmdName = ReinoltroEsiti , Eccezione = " + e.getMessage());
		}
		return null;
	}	
	
	@Override
	public MonitoringData executeReinoltroNotifiche() {

		try { 
			logger.info("executing ReinoltroNotifiche" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.ReinoltroNotificheRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			logger.info("CmdName = ReinoltroNotifiche , Eccezione = " + e.getMessage());
		}
		return null;
	}		



	@Override
	public String listaErroriCart() {
		String html = null;
		try {
			logger.info("executing listaErroriCart" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioErroriCart);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaErroriCart , Eccezione = " + e.getMessage());
		}
		return html;
	}


	

	@Override
	public String listaEsitiDaSpedire() {
		String html = null;
		try {
			logger.info("executing listaEsitiDaSpedire" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CartSenderRemote);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaEsitiDaSpedire , Eccezione = " + e.getMessage());
		}
		return html;
	}


	@Override
	public String listaUltime25Pendenze() {
		String html = null;
		try {
			logger.info("executing listaUltime25Pendenze" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioPendenze);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaUltime25Pendenze , Eccezione = " + e.getMessage());
		}
		return html;
	}
	
	@Override
	public String listaEsitiPendenza(String msg, String sendId, String sendSys) {
		String html = null;
		try {
			logger.info("executing listaEsitiPendenza" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioEsitiPendenza);
			html = ((ObjectCommandExecutor) objectProxy).executeHtml(msg, sendId, sendSys);
		} catch (Exception e) {
			logger.info("CmdName = listaEsitiPendenza , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	@Override
	public String dettaglioFlussoPendenze(String e2emsgid) {
		String html = null;
		try {
			logger.info("executing dettaglioPendenza" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioPendenze);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml(e2emsgid);
		} catch (Exception e) {
			logger.info("CmdName = dettaglioPendenza , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	

	@Override
	public String dettaglioEsitoCart(String msg, String sendId, String sendSys) {
		String html = null;
		try {
			logger.info("executing dettaglioEsitoCart" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioEsitiCart);
			html = ((ObjectCommandExecutor) objectProxy).executeHtml(msg, sendId, sendSys);
		} catch (Exception e) {
			logger.info("CmdName = dettaglioEsitoCart , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String dettaglioErroreIdp(String msg, String sendId, String sendSys) {
		String html = null;
		try {
			logger.info("executing dettaglioErroreIdp" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioErroriIdp);
			html = ((ObjectCommandExecutor) objectProxy).executeHtml(msg, sendId, sendSys);
		} catch (Exception e) {
			logger.info("CmdName = dettaglioErroreIdp , Eccezione = " + e.getMessage());
		}
		return html;
	}

	
	
	
	
	@Override
	public MonitoringData executeEstrazionePagamentiPerCreazioneNotifiche() {
		try {
			logger.info("executing executeEstrazionePagamentiPerCreazioneNotifiche" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.EstrazionePagamentiNotificaRemote);
			((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = executeEstrazionePagamentiPerCreazioneNotifiche , Eccezione = " + e.getMessage());
		}
		return null;
	}

	@Override
	public MonitoringData executeCreazioneNotifichePagamento() {
		try {
			logger.info("executing executeCreazioneNotifichePagamento" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.NotificaPagamentoRemote);
			((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = executeCreazioneNotifichePagamento , Eccezione = " + e.getMessage());
		}
		return null;
	}

	@Override
	public MonitoringData executeSpedizioneNotifichePagamento() {
		try {
			logger.info("executing executeSpedizioneNotifichePagamento" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.NotificaSenderRemote);
			((CommandExecutor) objectProxy).executeApplicationTransaction();
		} catch (Exception e) {
			logger.info("CmdName = executeSpedizioneNotifichePagamento , Eccezione = " + e.getMessage());
		}
		return null;
	}

	@Override
	public String listaNotificheDaSpedire() {
		String html = null;
		try {
			logger.info("executing listaNotificheDaSpedire" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.NotificaSenderRemote);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaNotificheDaSpedire , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltime25Notifiche() {
		String html = null;
		try {
			logger.info("executing listaUltime25Notifiche" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioNotifiche);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = listaUltime25Notifiche , Eccezione = " + e.getMessage());
		}
		return html;
	}


//	@Override
//	public String listaProcessi() {
//		String html = null; 
//		try {
//			logger.info("executing listaProcessi" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoringProcessi);
//			html = ((ObjectCommandExecutor) objectProxy).executeHtml(null,null,null);
//		} catch (Exception e) {
//			logger.info("CmdName = listaProcessi , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}

//	@Override
//	public String listaStoricoFlussi() {
//		String html = null;
//		try {
//			logger.info("executing listaStoricoFlussi" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.StoricoFlussi);
//			html = ((ObjectCommandExecutor) objectProxy).executeHtml(null,null,null);
//		} catch (Exception e) {
//			logger.info("CmdName = listaStoricoFlussi , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}

	@Override
	public String timerController() {
		String html = null;
		try {
			logger.info("executing timerController" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.TimerController);
			html = ((TimerCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = timerController , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String timerStatus() {
		String html = null;
		try {
			logger.info("executing timerStatus" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.TimerStatus);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = timerStatus , Eccezione = " + e.getMessage());
		}
		return html;
	}


	@Override
	public String timerStarter(String timerName,String  intervallo,String  periodo,String  operazione) {
		String html = null;
		try {
			logger.info("executing timerStarterStopper" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.TimerController);
			html = ((TimerCommandExecutor) objectProxy).executeHtml(timerName, intervallo, periodo, operazione);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = timerController , Eccezione = " + e.getMessage());
		}
		return html;
	}


//	@Override
//	public MonitoringData executeApplicationTransaction(String cmdName) {
//
//		try {
//			logger.info("executing " + cmdName);
//			if ("TIMEOUT".equals(cmdName)) {
//				Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.TimeoutRemote);
//				((CommandExecutor) objectProxy).executeApplicationTransaction();
//			} else if ("SPEDIZIONE".equals(cmdName)) {
//				Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.SpedizioneEsitiRemote);
//				((CommandExecutor) objectProxy).executeApplicationTransaction();
//			} else if ("RIPARTENZA".equals(cmdName)) {
//				Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RipartenzaDataStorageRemote);
//				((CommandExecutor) objectProxy).executeApplicationTransaction();
//			} else if ("INVIOFLUSSIESITO".equals(cmdName)) {
//				Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CartSenderRemote);
//				((CommandExecutor) objectProxy).executeApplicationTransaction();
//			}
//
//		} catch (Exception e) {
//			logger.info("CmdName = " + cmdName + " Eccezione = " + e.getMessage());
//		}
//
//		return null;
//	}


//	@Override
//	public String cancellaLogicaPendenzeOriginali(String e2eMsgId,
//			String senderId, String silMittente, String cdTrbEnte, String msgType) {
//		String html = null;
//		try {
//			logger.info("executing cancellaLogicaPendenzeOriginali" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CancellaLogicaPendenzeOriginali);
//			html = ((MassiveCommandExecutor) objectProxy).executeHtml(e2eMsgId, senderId, silMittente, cdTrbEnte, "MASSIVO");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("CmdName = cancellaLogicaPendenzeOriginali , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}
//
//	@Override
//	public String controllerUpdateMassivo(String e2eMsgId,
//			String senderId, String silMittente, String cdTrbEnte, String msgType) {
//		String html = null;
//		try {
//			logger.info("executing controllerUpdateMassivo" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.ControllerUpdateMassivo);
//			html = ((MassiveCommandExecutor) objectProxy).executeHtml(e2eMsgId, senderId, silMittente, cdTrbEnte, "MASSIVO");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("CmdName = controllerUpdateMassivo , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}
//
//	@Override
//	public String cancellaFisicaPendenzeOriginali(String e2eMsgId,
//			String senderId, String silMittente, String cdTrbEnte, String msgType) {
//		String html = null;
//		try {
//			logger.info("executing cancellaFisicaPendenzeOriginali" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CancellaFisicaPendenzeOriginali);
//			html = ((MassiveCommandExecutor) objectProxy).executeHtml(e2eMsgId, senderId, silMittente, cdTrbEnte, "MASSIVO");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("CmdName = cancellaFisicaPendenzeOriginali , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}
//
//	@Override
//	public String deleteNuovePendenzeMassive(String e2eMsgId, String senderId,
//			String silMittente, String cdTrbEnte, String msgType) {
//		String html = null;
//		try {
//			logger.info("executing deleteNuovePendenzeMassive" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.DeleteNuovePendenzeMassive);
//			html = ((MassiveCommandExecutor) objectProxy).executeHtml(e2eMsgId, senderId, silMittente, cdTrbEnte, "MASSIVO");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("CmdName = deleteNuovePendenzeMassive , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}
//
//	@Override
//	public String ripristinaPendenzeOriginali(String e2eMsgId, String senderId,
//			String silMittente, String cdTrbEnte, String msgType) {
//		String html = null;
//		try {
//			logger.info("executing ripristinaPendenzeOriginali" );
//			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RipristinaPendenzeOriginali);
//			html = ((MassiveCommandExecutor) objectProxy).executeHtml(e2eMsgId, senderId, silMittente, cdTrbEnte, "MASSIVO");
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info("CmdName = ripristinaPendenzeOriginali , Eccezione = " + e.getMessage());
//		}
//		return html;
//	}

	@Override
	public String listaUltime25Pagamenti(String token) {
		String html = null;
		try {
			logger.info("executing listaUltime25Pagamenti" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioPagamenti);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml(token);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltime25Pagamenti , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltime25BozzeBonifici() {
		String html = null;
		try {
			logger.info("executing listaUltime25 BozzeBonifici" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.BozzeBonificiRiaccreditoManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltime25 BozzeBonifici , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltimi25CasellarioInfo() {
		String html = null;
		try {
			logger.info("executing listaUltime25 Casell. Info" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CasellarioInfoDelegatorRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltime25 Casell. Info, Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltimi25EsitiRCT() {
		String html = null;
		try {
			logger.info("executing listaUltime25 Esiti Rct" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.EsitiRctBlobInputManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltime25 Esiti RCT , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltimi25DistinteRiaccredito() {
		String html = null;
		try {
			logger.info("executing listaUltimi25 DistinteRiaccredito" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.DistintaCbiManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltimi25 DistinteRiaccredito , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltimi25CasellarioDispo() {
		String html = null;
		try {
			logger.info("executing listaUltimi25 FlussiPcCBI" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.BonificiRiaccreditoSpazioSenderBeanRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltimi25 FlussiPcCBI , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String listaUltimi25EsitiBonificiRiaccredito() {
		String html = null;
		try {
			logger.info("executing listaUltimi25 EsitiBonificiRiaccredito" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RiconciliaBonificiRiaccreditoManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltimi25 EsitiBonificiRiaccredito , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String dettaglioDistintaRiaccredito(String id) {
		String html = null;
		try {
			logger.info("executing dettaglio distinta riaccredito" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.DistintaCbiManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(id);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltimi25 EsitiBonificiRiaccredito , Eccezione = " + e.getMessage());
		}
		return html;
	}
	
	@Override
	public String cruscottoRiaccrediti() {
		String html = null;
		try {
			logger.info("executing cruscotto" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioPagamenti);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltimi25 cruscotto , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	@Override
	public String listaUltime25DistintePagamento() {
		String html = null;
		try {
			logger.info("executing listaUltime25DistintePagamento" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.IncassiBonificiRhBlobInputManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = listaUltime25DistintePagamento , Eccezione = " + e.getMessage());
		}
		return html;
	}		
	
	@Override
	public String dettaglioDistintaPagamento(String codTx) {
		String html = null;
		try {
			logger.info("executing dettaglioDistintaPagamento" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.IncassiBonificiRhBlobInputManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(codTx);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = dettaglioDistintaPagamento , Eccezione = " + e.getMessage());
		}
		return html;
	}			
	
	@Override
	public String trackingPagamenti(String codTx) {
		String html = null;
		try {
			logger.info("executing trackingPagamenti" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioNotifiche);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml(codTx);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = trackingPagamenti , Eccezione = " + e.getMessage());
		}
		return html;
	}		
	
	@Override
	public String statistichePagamenti(String id) {
		String html = null;   
		try {
			logger.info("executing statistichePagamenti" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioPagamenti);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml(id);
		} catch (Exception e) { 
			e.printStackTrace();
			logger.info("CmdName = statistichePagamenti , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	
	
	@Override
	public String logSpazio(String regExp) {
		String html = null;
		try {
			logger.info("executing logSpazio" );
			
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.FileSearchRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(regExp);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = logSpazio, Eccezione = " + e.getMessage());
		}
		return html;
	}		
	
	@Override
	public String checkQueue(String queueName) {
		String html = null;
		try {
			logger.info("executing checkQueue with parameter: " + queueName );
			
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.JmsQueueBrowserRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(queueName);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = checkQueue, Eccezione = " + e.getMessage());
		}
		return html;
	}		
	
	@Override
	public String reworkQueue(String queueName) {
		String html = null;
		try {
			logger.info("executing reworkQueue with parameter: " + queueName );
			
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.JmsQueueReworkerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(queueName);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = reworkQueue, Eccezione = " + e.getMessage());
		}
		return html;
	}			
	
	@Override
	public String deleteMessage(String inputData) {
		String html = null;
		try {
			logger.info("executing deleteMessage" );
			
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.JmsDeleteManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(inputData);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = deleteMessage, Eccezione = " + e.getMessage());
		}
		return html;
	}		
	
	@Override
	public String senderMessage(String inputData) { 
		String html = null;
		try {
			logger.info("executing senderMessage" );
			
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.JmsSenderManagerRemote);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml(inputData);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("CmdName = senderMessage, Eccezione = " + e.getMessage());
		}
		return html;
	}
	
	
	
	
	@Override
	public String elaboraRendicontazioniCBill() {
		String html = null;   
		try {
			logger.info("executing CasellarioInfoCbill" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.CasellarioInfoCbill);
			html = ((SpazioCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) { 
			e.printStackTrace();
			logger.info("CmdName = CasellarioInfoCbill , Eccezione = " + e.getMessage());
		}
		return html;
	}	
	
	@Override
	public String richiestaCatalogoPsp() {
		String html = null;   
		try {
			logger.info("executing RichiestaCatalogoPSP" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RichiestaCatalogoPspNdp);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) { 
			e.printStackTrace();
			logger.info("CmdName = RichiestaCatalogoPSP , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String richiestaElencoRendicontazioniNdp() {
		String html = null;   
		try {
			logger.info("executing RichiestaElencoRendicontazioneNdp" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RichiestaElencoRendicontazioneNdp);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) { 
			e.printStackTrace();
			logger.info("CmdName = RichiestaElencoRendicontazioneNdp , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String richiestaDettaglioRendicontazioneNdp() {
		String html = null;   
		try {
			logger.info("executing RichiestaDettaglioRendicontazioneNdp" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RichiestaDettaglioRendicontazioneNdp);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) { 
			e.printStackTrace();
			logger.info("CmdName = RichiestaDettaglioRendicontazioneNdp , Eccezione = " + e.getMessage());
		}
		return html;
	}		
	
	@Override
	public String elaboraDettagliRendicontazioneNdp() {
		String html = null;   
		try {
			logger.info("executing ElaboraDettaglioRendicontazioneNdp" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.ElaboraDettaglioRendicontazioneNdp);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) { 
			e.printStackTrace();
			logger.info("CmdName = ElaboraDettaglioRendicontazioneNdp , Eccezione = " + e.getMessage());
		}
		return html;
	}



	@Override
	public String rfcCleaner(String dataStart, String dataEnd,
			String dimBlocco, String tipoOp, String todo) {
		String html = null;
		try {
			logger.info("executing lista Rfc127 " );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.RfcCleaner);
			html = ((ObjectCommandExecutor) objectProxy).executeHtml(dataStart,dataEnd,tipoOp);
		} catch (Exception e) {
			logger.info("CmdName = lista Rfc127 , Eccezione = " + e.getMessage());
		}
		return html;
	}			
	
	@Override
	public String pendenzeCleaner(String dataStart, String dataEnd,
			String dimBlocco, String tipoOp, String todo) {
		String html = null;
		try {
			logger.info("executing pendenzeCleaner" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.PendenzeCleaner);
			html = ((ObjectCommandExecutor) objectProxy).executeHtml(dataStart,dataEnd,dimBlocco);
		} catch (Exception e) {
			logger.info("CmdName = pendenzeCleaner , Eccezione = " + e.getMessage());
		}
		return html;
	}

	@Override
	public String elencoEntiSilAttivi() {
		String html = null;
		try {
			logger.info("executing MonitoraggioEntiSil" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioEntiSil);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = MonitoraggioEntiSil , Eccezione = " + e.getMessage());
		}
		return html;
	}
	
	@Override
	public String elencoConfigNotifiche(String Sil) {
		String html = null;
		try {
			logger.info("executing MonitoraggioEntiSil" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.MonitoraggioEntiSil);
			html = ((StressTestCommandExecutor) objectProxy).executeHtml(Sil);
		} catch (Exception e) {
			logger.info("CmdName = MonitoraggioEntiSil , Eccezione = " + e.getMessage());
		}
		return html;	
	}			

	
	@Override
	public String reportClearingData() {
		String html = null;
		try {
			logger.info("executing ReportRendicontazioneCCDManager" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.ReportRendicontazioneCCDManager);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = ReportRendicontazioneCCDManager , Eccezione = " + e.getMessage());
		}
		return html;	
	}		
	
	
	@Override
	public String reportRendicontazioni() {
		String html = null;
		try {
			logger.info("executing ReportRendicontazioneRctManager" );
			Object objectProxy = IdpServiceLocator.getInstance().getServiceByJndiName(ServiceLocalMapper.ReportRendicontazioneRctManager);
			html = ((CommandExecutor) objectProxy).executeHtml();
		} catch (Exception e) {
			logger.info("CmdName = ReportRendicontazioneRctManager , Eccezione = " + e.getMessage());
		}
		return html;	
	}		
	
}
