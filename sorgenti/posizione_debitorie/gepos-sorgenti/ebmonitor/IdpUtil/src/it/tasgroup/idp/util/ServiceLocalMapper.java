package it.tasgroup.idp.util;

import java.io.IOException;
import java.util.Properties;

public class ServiceLocalMapper {

	
	private static final Properties jndiNames = new Properties();
	
	static {
		try {
			String isPortable = IrisProperties.getProperty("portableJndiNames");
//			String isUnitTest = System.getProperty("JndiNamesForUnitTest");
			if("true".equals(isPortable)) {
				jndiNames.load(ServiceLocalMapper.class.getClassLoader().getResourceAsStream("jndiNamePortable.properties"));
//			} else if("true".equals(isUnitTest)) {
//				jndiNames.load(ServiceLocalMapper.class.getClassLoader().getResourceAsStream("jndiNameUnitTest.properties"));
			} else {
				jndiNames.load(ServiceLocalMapper.class.getClassLoader().getResourceAsStream("jndiName.properties"));
			}
			System.out.println("isPortable: " + isPortable );
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static String getJndiName(String serviceName) {

//		return jndiNames.getProperty(serviceName);

		String jndiName = null;
		try {
			jndiName = (String) ServiceLocalMapper.class.getDeclaredField(serviceName).get(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return jndiName;
	}

	
	//
	// TIMERS - sempre interfaccia LOCAL
	//
	
	// questo � il nome logico del timer (equivalente a quello presente nella tabella timeriris)
	public static final String ControllerUpdateMassivoTimer_NAME = "ControllerUpdateMassivoTimer";

	// questi sono jndiName
	public static final String RendicontazionePosteSFTPInputTimer = jndiNames.getProperty("RendicontazionePosteSFTPInputTimer");
	public static final String RendicontazionePosteManagerTimer = jndiNames.getProperty("RendicontazionePosteManagerTimer");
	public static final String ControllerUpdateMassivoTimer = jndiNames.getProperty("ControllerUpdateMassivoTimer");
	public static final String ParseAndMatchTimer = jndiNames.getProperty("ParseAndMatchTimer");
	public static final String AccettazioneRichiestaRevocaTimer = jndiNames.getProperty("AccettazioneRichiestaRevocaTimer");
	public static final String IncassoDisposizioniRidTimer = jndiNames.getProperty("IncassoDisposizioniRidTimer");
	public static final String DisposizioneRidTimer = jndiNames.getProperty("DisposizioneRidTimer");
	public static final String RichiestaAutorizzazioneTimer = jndiNames.getProperty("RichiestaAutorizzazioneTimer");
	public static final String RichiestaRevocaTimer = jndiNames.getProperty("RichiestaRevocaTimer");	
	public static final String SpedizioneEsitiTimer = jndiNames.getProperty("SpedizioneEsitiTimer");
	public static final String ReinoltroEsitiTimer = jndiNames.getProperty("ReinoltroEsitiTimer");
	public static final String ReinoltroNotificheTimer = jndiNames.getProperty("ReinoltroNotificheTimer");
	public static final String TimeoutTimer = jndiNames.getProperty("TimeoutTimer");
	public static final String RipartenzaDataStorageTimer = jndiNames.getProperty("RipartenzaDataStorageTimer");
	public static final String NotificaPagamentoTimer = jndiNames.getProperty("NotificaPagamentoTimer");
	public static final String DailyNotificationTimer = jndiNames.getProperty("DailyNotificationTimer");
	public static final String SixHoursNotificationTimer = jndiNames.getProperty("SixHoursNotificationTimer");
	public static final String TwelveHoursNotificationTimer = jndiNames.getProperty("TwelveHoursNotificationTimer");
	public static final String EventNotificationTimer = jndiNames.getProperty("EventNotificationTimer");	
	public static final String StatistichePagamentiTimer = jndiNames.getProperty("StatistichePagamentiTimer");	
	public static final String StatistichePosizioniTimer = jndiNames.getProperty("StatistichePosizioniTimer");	
	public static final String AggregazioneBozzeBonificiTimer = jndiNames.getProperty("AggregazioneBozzeBonificiTimer");
	public static final String BonificiRiaccreditoCbiTimer = jndiNames.getProperty("BonificiRiaccreditoCbiTimer");
	public static final String BozzeBonificiManagerTimer = jndiNames.getProperty("BozzeBonificiManagerTimer");
	public static final String CasellarioInfoManagerTimer = jndiNames.getProperty("CasellarioInfoManagerTimer");
	public static final String SpazioOutputManagerTimer = jndiNames.getProperty("SpazioOutputManagerTimer");	
	public static final String RiconciliaBonificiRiaccreditoCbiTimer = jndiNames.getProperty("RiconciliaBonificiRiaccreditoCbiTimer");
	public static final String RiconciliazioneMovimentiNdpTimer = jndiNames.getProperty("RiconciliazioneMovimentiNdpTimer");
	public static final String SpazioIROutputManagerTimer = jndiNames.getProperty("SpazioIROutputManagerTimer");
	public static final String SpazioALOutputManagerTimer = jndiNames.getProperty("SpazioALOutputManagerTimer");		
	public static final String PromozionePagamentiTimer = jndiNames.getProperty("PromozionePagamentiTimer");
	public static final String SprenotazionePagamentiTimer = jndiNames.getProperty("SprenotazionePagamentiTimer");
	public static final String NotificaEventoTimer = jndiNames.getProperty("NotificaEventoTimer");
	public static final String RichiestaCatalogoPSPTimer = jndiNames.getProperty("RichiestaCatalogoPSPTimer");
	public static final String ElencoFlussiRendicontazioneNdpTimer = jndiNames.getProperty("ElencoFlussiRendicontazioneNdpTimer");
	public static final String TestTimer = jndiNames.getProperty("TestTimer");
	public static final String TimerLoaderTimer = jndiNames.getProperty("TimerLoaderTimer");
	public static final String DettaglioFlussoRendicontazioneNdpTimer = jndiNames.getProperty("DettaglioFlussoRendicontazioneNdpTimer");
	public static final String GiornaleDiCassaSiopeTimer = jndiNames.getProperty("GiornaleDiCassaSiopeTimer");
	public static final String ElaboraDettaglioRendicontazioneNdpTimer = jndiNames.getProperty("ElaboraDettaglioRendicontazioneNdpTimer");
	public static final String AvanzamentoRiversamentiTimer = jndiNames.getProperty("AvanzamentoRiversamentiTimer");

	public static final String ReloaderTimer = jndiNames.getProperty("ReloaderTimer");
	public static final String CasellarioInfoRctManagerTimer = jndiNames.getProperty("CasellarioInfoRctManagerTimer");
	
	public static final String InvioInConservazioneTimer = jndiNames.getProperty("InvioInConservazioneTimer");
    public static final String AllineamentoAnagraficoTimer = jndiNames.getProperty("AllineamentoAnagraficoTimer");
    public static final String AllineamentoAnagraficheAsur = jndiNames.getProperty("AllineamentoAnagraficheAsur");

    public static final String CreateEmailEventMultiPayeeTimer = jndiNames.getProperty("CreateEmailEventMultiPayeeTimer");
    public static final String EsitiRevocaRTTimer = jndiNames.getProperty("EsitiRevocaRTTimer");
    public static final String AvvisaturaManagerRealTimeTimer = jndiNames.getProperty("AvvisaturaManagerRealTimeTimer"); 
	//
	// Usati negli MXBean
	//
	public static final String CasellarioInfoCbill = jndiNames.getProperty("CasellarioInfoCbill");	
	public static final String SpedizioneEsitiRemote = jndiNames.getProperty("SpedizioneEsitiRemote");
	public static final String RipartenzaDataStorageRemote = jndiNames.getProperty("RipartenzaDataStorageRemote");
	public static final String CartSenderRemote = jndiNames.getProperty("CartSenderRemote");
	public static final String NotificaSenderRemote = jndiNames.getProperty("NotificaSenderRemote");
	public static final String TimeoutRemote = jndiNames.getProperty("TimeoutRemote");
	public static final String NotificaPagamentoRemote = jndiNames.getProperty("NotificaPagamentoRemote");
	public static final String EstrazionePagamentiNotificaRemote = jndiNames.getProperty("EstrazionePagamentiNotificaRemote");
	public static final String ReinoltroEsitiRemote = jndiNames.getProperty("ReinoltroEsitiRemote");
	public static final String ReinoltroNotificheRemote = jndiNames.getProperty("ReinoltroNotificheRemote");	
	public static final String MonitoraggioPendenze = jndiNames.getProperty("MonitoraggioPendenze");
	public static final String MonitoraggioEsitiPendenza = jndiNames.getProperty("MonitoraggioEsitiPendenza");
	public static final String MonitoraggioEsitiCart = jndiNames.getProperty("MonitoraggioEsitiCart");
	public static final String MonitoraggioErroriIdp = jndiNames.getProperty("MonitoraggioErroriIdp");
	public static final String MonitoraggioErroriCart = jndiNames.getProperty("MonitoraggioErroriCart");
	public static final String MonitoraggioNotifiche = jndiNames.getProperty("MonitoraggioNotifiche");
	public static final String MonitoraggioPagamenti = jndiNames.getProperty("MonitoraggioPagamenti");
	public static final String MonitoraggioEntiSil = jndiNames.getProperty("MonitoraggioEntiSil");
	public static final String RfcCleaner = jndiNames.getProperty("RfcCleaner");
	public static final String PendenzeCleaner = jndiNames.getProperty("PendenzeCleaner");
	public static final String TimerController = jndiNames.getProperty("TimerController");
	public static final String TimerStatus = jndiNames.getProperty("TimerStatus");
	public static final String BonificiRiaccreditoSpazioSenderBeanRemote = jndiNames.getProperty("BonificiRiaccreditoSpazioSenderBeanRemote");
	public static final String CasellarioInfoDelegatorRemote = jndiNames.getProperty("CasellarioInfoDelegatorRemote");
	public static final String EsitiRctBlobInputManagerRemote = jndiNames.getProperty("EsitiRctBlobInputManagerRemote");
	public static final String IncassiBonificiRhBlobInputManagerRemote = jndiNames.getProperty("IncassiBonificiRhBlobInputManagerRemote");
	public static final String RiconciliaBonificiRiaccreditoManagerRemote = jndiNames.getProperty("RiconciliaBonificiRiaccreditoManagerRemote");
	public static final String BozzeBonificiRiaccreditoManagerRemote = jndiNames.getProperty("BozzeBonificiRiaccreditoManagerRemote");
	public static final String TestJobRemote = jndiNames.getProperty("TestJobRemote");
	public static final String DistintaCbiManagerRemote = jndiNames.getProperty("DistintaCbiManagerRemote");
	public static final String FileSearchRemote = jndiNames.getProperty("FileSearchRemote");
	public static final String JmsQueueBrowserRemote = jndiNames.getProperty("JmsQueueBrowserRemote");
	public static final String JmsDeleteManagerRemote = jndiNames.getProperty("JmsDeleteManagerRemote");
	public static final String JmsSenderManagerRemote = jndiNames.getProperty("JmsSenderManagerRemote");
	public static final String JmsQueueReworkerRemote = jndiNames.getProperty("JmsQueueReworkerRemote");
	public static final String RichiestaCatalogoPspNdp = jndiNames.getProperty("RichiestaCatalogoPspNdp"); 
	public static final String RichiestaElencoRendicontazioneNdp = jndiNames.getProperty("RichiestaElencoRendicontazioneNdp"); 
	public static final String RichiestaDettaglioRendicontazioneNdp = jndiNames.getProperty("RichiestaDettaglioRendicontazioneNdp"); 
	public static final String ElaboraDettaglioRendicontazioneNdp = jndiNames.getProperty("ElaboraDettaglioRendicontazioneNdp");
	
	public static final String ReportRendicontazioneCCDManager = jndiNames.getProperty("ReportRendicontazioneCCDManager");
	public static final String ReportRendicontazioneRctManager = jndiNames.getProperty("ReportRendicontazioneRctManager");
	
	public static final String ComunicazionePosizioniDebitorieControllerImpl = jndiNames.getProperty("ComunicazionePosizioniDebitorieControllerImpl");
	public static final String ConfigurazioneEnteControllerImpl = jndiNames.getProperty("ConfigurazioneEnteControllerImpl");
	public static final String VerificaStatoPagamento = jndiNames.getProperty("VerificaStatoPagamento");
	public static final String DataStorageManagerIntegrato = jndiNames.getProperty("DataStorageManagerIntegrato");
	public static final String GestioneOTFManager = jndiNames.getProperty("GestioneOTFManager");
	public static final String GestioneOTFReplaceManager = jndiNames.getProperty("GestioneOTFReplaceManager");
	public static final String DecodeEntiTributiManager = jndiNames.getProperty("DecodeEntiTributiManager");	
	
    // code su cui siamo in ascolto
	//
	// referenziate dai MDB (del CART) 
	//
	public static final String InformativaPagamentoPendenzeErrore = "queue/InformativaPagamentoPendenzeErrore";
	public static final String InformativaPagamentoPendenzeEsito = "queue/InformativaPagamentoPendenzeEsito";
	public static final String PosizioneDebitoriaInput = "queue/PosizioneDebitoriaInput";
	public static final String PosizioneDebitoriaErrore = "queue/PosizioneDebitoriaErrore";
	public static final String InformativaPagamentoPendenzeOutputInternal = "queue/InformativaPagamentoPendenzeOutputInternal"; // 2 volte, leggo e scrivo
	public static final String PosizioneDebitoriaOutputInternalSSil = "queue/PosizioneDebitoriaOutputInternalSSil";
	public static final String InformativaPagamentoPendenzeOutputInternalSSil = "queue/InformativaPagamentoPendenzeOutputInternalSSil";

	//
	// referenziate dai MDB (di SPAZIO)
	//
	public static final String DLQ = "queue/DLQ";
	public static final String EsitiAllineamentiRidBT = "queue/EsitiAllineamentiRidInput";
	public static final String RendicontazioniBollettiniInput = "queue/RendicontazioniBollettiniInput";
	public static final String EsitiBonificiRiaccreditoBT = "queue/EsitiBonificiRiaccreditoInput";
	public static final String EsitiBonificiRiaccreditoXmlBT = "queue/EsitiBonificiRiaccreditoXmlInput";
	public static final String RendicontazioniRctInput = "queue/RendicontazioniRctInput";
	public static final String EsitiRidBT = "queue/EsitiRidInput";
	public static final String RendicontazioniMovimentiCCInput = "queue/RendicontazioniMovimentiCCInput";

	// code su cui SCRIVIAMO MESSAGGI

	
	// ------------------------------------------------------------------------------------- 
	//
	// Quelle del business-service.xml
	//

	// 
	// Quelle del business-service.xml SICURAMENTE USATE
	//
//	public static final String JmsXA = "";
//	public static final String JmsConnectionFactory = "";
//	public static final String JmsDLQInput = "";
//	public static final String JmsDLQOutput = "";
//	public static final String JmsAllineamentiRidBT = "";
//	public static final String JmsBonificiRiaccreditoBT = "";
//	public static final String JmsInformativaPagamentoPendenzeOutput = "";
//	public static final String JmsInformativaPagamentoPendenzeOutputInternal = "";
//	public static final String JmsPagamentiRidBT = "";
//	public static final String JmsPosizioneDebitoriaOutput = "";
	public static final String JmsConnectionFactory = "ConnectionFactory";
	public static final String JmsXA = jndiNames.getProperty("JmsXA"); //"java:JmsXA";
	public static final String JmsXAConnFactory = "JmsXA";
	public static final String ClusteredXAConnectionFactory = jndiNames.getProperty("ClusteredXAConnectionFactory");

	public static final String JmsDLQInput = "queue/DLQInput";
	public static final String JmsDLQOutput = "queue/DLQOutput";

	public static final String JmsInformativaPagamentoPendenzeOutput = "queue/InformativaPagamentoPendenzeOutput";
	public static final String JmsInformativaPagamentoPendenzeOutputInternal = "queue/InformativaPagamentoPendenzeOutputInternal"; // 2volte, scrivo e leggo
	public static final String JmsInformativaPagamentoPendenzeOutputInternalSSil = "queue/InformativaPagamentoPendenzeOutputInternalSSil"; // 2volte, scrivo e leggo
	public static final String JmsPosizioneDebitoriaOutput = "queue/PosizioneDebitoriaOutput";
	public static final String JmsPosizioneDebitoriaOutputInternalSSil = "queue/PosizioneDebitoriaOutputInternalSSil";

	//Riaggiunte perche' usate dal DLQBrowserManager per le spedizioni
	public static final String JmsEsitiBonificiRiaccreditoInput = "queue/EsitiBonificiRiaccreditoInput";
	public static final String JmsInformativaPagamentoPendenzeErrore = "queue/InformativaPagamentoPendenzeErrore";
	public static final String JmsInformativaPagamentoPendenzeEsito = "queue/InformativaPagamentoPendenzeEsito";
	public static final String JmsPosizioneDebitoriaErrore = "queue/PosizioneDebitoriaErrore";
	public static final String JmsPosizioneDebitoriaInput = "queue/PosizioneDebitoriaInput";
	public static final String JmsRendicontazioniMovimentiCCInput = "queue/RendicontazioniMovimentiCCInput";
	public static final String JmsRendicontazioniRctInput = "queue/RendicontazioniRctInput";	
	
	// vere
	public static final String JmsAllineamentiRidBT = "queue/AllineamentiRidBT";
	public static final String JmsBonificiRiaccreditoBT = "queue/BonificiRiaccreditoBT";
	public static final String JmsBonificiRiaccreditoXmlBT = "queue/BonificiRiaccreditoXmlBT";
	public static final String JmsPagamentiRidBT = "queue/PagamentiRidBT";

	// del simulatore - NON PIU USATE
    // public static final String JmsDLQ = "queue/DLQ";
	// ------------------------------------------------------------------------------------- 
	
	//nuova coda semplificazione
	public static final String AllineamentoPendenzeInternalInput = "AllineamentoPendenzeInternalInput";
	public static final String JmsAllineamentoPendenzeInternalInput = "queue/AllineamentoPendenzeInternalInput";	
	
	//
	// DataSources
	//
	public static final String IdpAppJpa = "dscmt";
	public static final String IdpCartJta = "dscmt";
//	public static final String IdpCartJta = "dscmtCart";
	public static final String IdpBTJta = "dscmt";
	public static final String IdpBTJtaXA = "dscmtXa";
	
	

}
