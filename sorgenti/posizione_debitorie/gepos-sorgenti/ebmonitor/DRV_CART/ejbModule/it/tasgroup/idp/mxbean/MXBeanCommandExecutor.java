/*******************************************************************************
 * Copyright (c) 2009 TasGroup.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TasGroup - initial API and implementation
 ******************************************************************************/
package it.tasgroup.idp.mxbean;

import it.tasgroup.idp.bean.SpazioCommandExecutor;
import it.tasgroup.idp.pojo.MonitoringData;
import it.tasgroup.idp.util.ServiceLocalMapper;
import it.tasgroup.idp.util.SystemPropertiesNames;

import javax.management.MXBean;

@MXBean
public interface MXBeanCommandExecutor {

	public MonitoringData executeCreazioneSpedizioneEsiti();

	public MonitoringData executeTimeout();

	public MonitoringData executeInvioEsiti();

	public MonitoringData executeRipartenza();
	
	public MonitoringData executeReinoltroEsiti();
	
	public MonitoringData executeReinoltroNotifiche(); 

	public String listaPendenzeSospese();

	public String listaPendenzeTimeout();

	public String listaPendenzeDaSpedire();

	public String listaEsitiDaSpedire();

	public String listaUltime25Pendenze();
	
	public String dettaglioFlussoPendenze(String e2emsgid);

	public String listaUltime25Pagamenti(String token);

	public String listaErroriCart();

	public String listaEsitiPendenza(String msg, String sendId, String sendSys);

	public String dettaglioEsitoCart(String msg, String sendId, String sendSys);

	public String dettaglioErroreIdp(String msg, String sendId, String sendSys);

	public MonitoringData executeEstrazionePagamentiPerCreazioneNotifiche();

	public MonitoringData executeCreazioneNotifichePagamento();

	public MonitoringData executeSpedizioneNotifichePagamento();

	public String listaNotificheDaSpedire();

	public String listaUltime25Notifiche();

//	public String listaProcessi();

//	public String listaStoricoFlussi();

	public String timerController();

	public String timerStatus();

	public String timerStarter(String timerName,String  intervallo,String  periodo,String  operazione);

//	public void executeApplicationTransaction(String cmdName);

//	public String cancellaLogicaPendenzeOriginali(String e2eMsgId , String senderId , String silMittente , String cdTrbEnte, String msgType);
//
//	public String controllerUpdateMassivo(String e2eMsgId , String senderId , String silMittente , String cdTrbEnte, String msgType);
//
//	public String cancellaFisicaPendenzeOriginali(String e2eMsgId , String senderId , String silMittente , String cdTrbEnte, String msgType);
//
//	public String deleteNuovePendenzeMassive(String e2eMsgId , String senderId , String silMittente , String cdTrbEnte, String msgType);
//
//	public String ripristinaPendenzeOriginali(String e2eMsgId , String senderId , String silMittente , String cdTrbEnte, String msgType);


	public String listaUltimi25CasellarioInfo();

	public String listaUltimi25EsitiRCT();

	public String listaUltime25BozzeBonifici();

	public String listaUltimi25DistinteRiaccredito();

	public String listaUltimi25CasellarioDispo();
	
	public String listaUltimi25EsitiBonificiRiaccredito();
	
	public String dettaglioDistintaRiaccredito(String id);
	
	public String cruscottoRiaccrediti();
	
	public String trackingPagamenti(String codTx);
	
	public String statistichePagamenti(String id);
	
	public String listaUltime25DistintePagamento();
	
	public String dettaglioDistintaPagamento(String codTx);	
	
	public String logSpazio(String regExp);

	public String checkQueue(String queueName);
	
	public String deleteMessage(String inputData);
	
	public String senderMessage(String inputData);
	
	public String reworkQueue(String inputData);
	
	public String elaboraRendicontazioniCBill();
	
	public String richiestaCatalogoPsp();
	
	public String richiestaElencoRendicontazioniNdp();
	
	public String richiestaDettaglioRendicontazioneNdp();
	
	public String elaboraDettagliRendicontazioneNdp();
	
	public String pendenzeCleaner(String dataStart, String dataEnd , String dimBlocco , String tipoOp, String todo);
	
	public String rfcCleaner(String dataStart, String dataEnd , String dimBlocco , String tipoOp, String todo);
	
	public String elencoEntiSilAttivi();
	
	public String elencoConfigNotifiche(String Sil);
	
	public String reportClearingData();
	
	public String reportRendicontazioni();
	
}
