package it.nch.utility;

import it.nch.fwk.fo.util.Tracer;

import java.util.Vector;

public class IbisConfigurationFromDB {

	private static long loadTime = 0;
	
	private static final long MINUTI_REFRESH_JLTAPARA_DEFAULT = 15;
	private static final long MINUTI_REFRESH_JLTAPARA_SE_ERRORE_DEFAULT = 3;

	private static int queryMaxRowsIncassi = 0;
	private static int queryMaxRowsPagamenti = 0;
	private static int queryMaxRowsDelegheF24 = 0;
	private static int queryMaxRowsFlussi = 0;
	private static int minutiRefreshCacheTabelleF24 = 15;
	private static String tribErarioNoSoglia = "";
	private static String tribRegioniNoSoglia = "";
	private static boolean checkSogliaCompensazioneF24 = true;
	private static boolean checkQuadraturaFlussiF24 = false;
	private static boolean auditWSLogEnable = false;
	private static String aziendeAuditWSLogEnabled = "NCH";  // se non specificato niente di diverso (o vuoto) in pratica non logga per nessuno.
	private static String serviziAuditLogEnabled = "";
	private static boolean optimizeImport = false;
	private static String optimizeImportValidChars = "";
	private static String optimizeImportNotValidChars = "";
	private static String optimizeImportNotPrintableChars = "";
	private static String optimizeImportNotValidCVSChars = "";
	private static String optimizeImportNotPrintableCVSChars = "";	
	private static int optimizeImportNMaxErrorNotValidChars = 0;
	private static int optimizeImportNMaxErrorNotPrintableChars = 0;
	
	private static boolean ottimizzazioneRtrimSuRidEnabled = false; 
	private static boolean ottimizzazioneRtrimSuRibaEnabled = false;
	private static boolean ottimizzazioneRtrimSuMavEnabled = false;
	private static boolean ottimizzazioneRtrimSuBonificiEnabled = false;
	private static boolean ottimizzazioneRtrimSuStipendiEnabled = false;
	private static boolean ottimizzazioneRtrimSuEffettiEnabled = false;
	private static String limiteOraDataEsecuzioneBonificoSct = "13";
	private static String checkRaggiungilitaSepaAcc = "0";
	private static String checkRaggiungilitaSepaAdd = "0";
	private static String checkMonobanca = "0";
	private static String protDelegaStategy = "IDENTITYBASED";
	private static String checkServiziPromemoriaOn = "";
	private static int minutiRefreshCachePromemoria = 15;
	private static String checkServiziCmsOn = "";
	private static int minutiRefreshCacheCms = 15;
	private static boolean cmsDebugEnable = false;
	private static int minutiRefreshSessionCache = 5;
	private static int millisTimeoutWsAudit = 5000;
	private static boolean errorImportEstesoEnable = false;
	private static String cmsPlaceHolderValues = "";
	private static boolean checkJsCbiNotValidCharsEnable = false;
	private static String overriddenAbiDirForCss = "";
	private static int numeroMassimoRecordPerElenco = 50;
	private static boolean recuperaUltimaQuietanzaEsportata = false;
	private static Integer giorniValiditaQuietanzaEsportata = new Integer(0);
	private static boolean enableMessaggiDinamiciFromDB = false;
	private static int minutiRefreshCacheMessaggiDinamiciFromDB = 15;
	private static int numMaxLogErrorImportazioni = 1000;
	private static String httpCacheControlMode = null;
	private static boolean checkEnableDiagnosi = false;
	private static boolean checkEnableErroreEstesoF24 = false;
	private static String listaAbiNoSped = "*";
	private static String listaAbiCtrDataF24 = "*";
	private static boolean enableTSUpdate = false;
	private static int auditWsMaxLengthDatiAggiuntivi = 128;
	private static int f24TransientListSize = 0;
	
	private static final String INDEX_MAXROWSINCASSI = "QUERYMAXROWSINCASSI";
	private static final String INDEX_MAXROWSPAGAMENTI = "QUERYMAXROWSPAGAMENTI";
	private static final String INDEX_MAXROWSDELEGHEF24 = "QUERYMAXROWSDELEGHEF24";
	private static final String INDEX_MAXROWSFLUSSI = "QUERYMAXROWSFLUSSI";
	private static final String INDEX_MINUTIREFRESHCACHETABELLEF24 = "MINUTIREFRESHCACHETABELLEF24";
	private static final String INDEX_TRIBERARIONOSOGLIA = "TRIBERARIONOSOGLIA%";
	private static final String INDEX_TRIBREGIONINOSOGLIA = "TRIBREGIONINOSOGLIA";
	private static final String INDEX_CHECKSOGLIACOMPENSAZIONEF24 = "CHECKSOGLIACOMPENSAZIONEF24";
	private static final String INDEX_CHECKQUADRATURAFLUSSIF24 = "CHECKQUADRATURAFLUSSIF24";
	private static final String INDEX_AUDITWSLOGENABLE = "AUDITWSLOGENABLE";
	private static final String INDEX_AZIENDEAUDITWSLOGENABLED = "AZIENDEAUDITWSLOGENABLED";
	private static final String INDEX_SERVIZIAUDITLOGENABLED = "SERVIZIAUDITLOGENABLED";
	private static final String INDEX_OPTIMIZEIMPORT = "OPTIMIZEIMPORT";
	private static final String INDEX_OPTIMIZEIMPORTVALIDCHARS = "OPTIMIZEIMPORTVALIDCHARS";
	private static final String INDEX_OPTIMIZEIMPORTNOTVALIDCHARS = "OPTIMIZEIMPORTNOTVALIDCHARS%";
	private static final String INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARS = "OPTIMIZEIMPORTNOTPRINTABLECHARS%";
	private static final String INDEX_OPTIMIZEIMPORTNOTVALIDCVSCHARS = "OPTIMIZEIMPORTNOTVALIDCVSCHARS%";
	private static final String INDEX_OPTIMIZEIMPORTNOTPRINTABLECVSCHARS = "OPTIMIZEIMPORTNOTPRINTABLECVSCHARS%";
	private static final String INDEX_OPTIMIZEIMPORTNMAXERRORNOTVALIDCHARS = "OPTIMIZEIMPORTNMAXERRORNOTVALIDCHARS";
	private static final String INDEX_OPTIMIZEIMPORTNMAXERRORNOTPRINTABLECHARS = "OPTIMIZEIMPORTNMAXERRORNOTPRINTABLECHARS";



	// Ottimizzazioni RTRIM
	private static final String INDEX_OTTIMIZZAZIONERTRIMSURID = "OTTIMIZZAZIONERTRIMSURID";
	private static final String INDEX_OTTIMIZZAZIONERTRIMSURIBA = "OTTIMIZZAZIONERTRIMSURIBA";
	private static final String INDEX_OTTIMIZZAZIONERTRIMSUBONIFICI = "OTTIMIZZAZIONERTRIMSUBONIFICI";
	private static final String INDEX_OTTIMIZZAZIONERTRIMSUEFFETTI = "OTTIMIZZAZIONERTRIMSUEFFETTI";

	//PER USI FUTURI.
	private static final String INDEX_OTTIMIZZAZIONERTRIMSUSTIPENDI = "OTTIMIZZAZIONERTRIMSUSTIPENDI";
	private static final String INDEX_OTTIMIZZAZIONERTRIMSUMAV = "OTTIMIZZAZIONERTRIMSUMAV";

	//limite ora per calcolo data esecuzione +2 +3 gg lavorativi
	private static final String INDEX_LIMITEORADATAESECUZIONEBONIFICOSCT = "LIMITEORADATAESECUZIONESCT";
	
	//controlli raggiungibilita' banche - CBI2
	private static final String INDEX_CHECKRAGGIUNGIBILITASEPAACC = "CHECKRAGGIUNGIBILITASEPAACC";
	private static final String INDEX_CHECKRAGGIUNGIBILITASEPAADD = "CHECKRAGGIUNGIBILITASEPAADD";
	
	// controllo su monobanca
	private static final String INDEX_CHECKMONOBANCA = "CHECKMONOBANCA";
	
	// strategy protocollo
	private static final String PROTDELEGA_STRATEGY = "PROTDELEGASTRATEGY";	
//	private static final String PROTDELEGA_STRATEGY = "OBJBASED";	
//	private static final String PROTDELEGA_STRATEGY = "MAXBASED";		
//	private static final String PROTDELEGA_STRATEGY = "IDENTITYBASED";	


	private static final String INDEX_MINUTIREFRESHSESSIONCACHE = "MINUTIREFRESHSESSIONCACHE";
	private static final String INDEX_MILLISTIMEOUTWSAUDIT = "MILLISTIMEOUTWSAUDIT";
	private static final String INDEX_ERRORIMPORTESTESOENABLE = "ERRORIMPORTESTESOENABLE";	

	
	//	controllo servizi abilitati alla visualizzazione dei promemoria
	private static final String INDEX_CHECKSERVIZIPROMEMORIAON = "CHECKSERVIZIPROMEMORIAON";
	// minuti Refresh Cache 
	private static final String INDEX_MINUTIREFRESHCACHEPROMEMORIA = "MINUTIREFRESHCACHEPROMEMORIA";
	//	controllo servizi abilitati alla visulizzazione dei contenuti da cms
	private static final String INDEX_CHECKSERVIZICMSON = "CHECKSERVIZICMSON";
	// minuti Refresh contenuti Cms 
	private static final String INDEX_MINUTIREFRESHCACHECMS = "MINUTIREFRESHCACHECMS";
	// controllo modalita debug del cms 
	private static final String INDEX_CMSDEBUGENABLE = "CMSDEBUGENABLE";
	// configurazione placeholder per il cms ${chiave1}=valore1,${chiave2}=valore2,.. 
	public static final String INDEX_CMSPLACEHOLDERVALUES = "CMSPLACEHOLDERVALUES";

	// controllo se e' abilitato, nei js, il controllo dei caratteri CBI da database 
	public static final String INDEX_CHECKJSCBINOTVALIDCHARSENABLE = "CHECKJSCBINOTVALIDCHARSENABLE";
	
	public static final String INDEX_OVERRIDDENABIDIRFORCSS = "OVERRIDDENABIDIRFORCSS";

	// se abilitato recupero l'ultima quietanza esportata invece di effettuare una nuova esportazione
	public static final String INDEX_ENABLERESCUELASTEXP = "ENABLERESCUELASTEXP";

	// Giorni di validità della quietanza da recuperare
	public static final String INDEX_GIORNIVALIDITAQUIETANZAEXP = "GIORNIVALIDITAQUIETANZAEXP";

	// controllo se e' abilitato il servizio di messaggistica dinamica  
	public static final String INDEX_ENABLEMESSAGGIDINAMICIFROMDB = "ENABLEMESSAGGIDINAMICIFROMDB";
	
	// minuti Refresh Cache 
	private static final String INDEX_MINUTIREFRESHCACHEMESSAGGIDINAMICIFROMDB = "MINUTIREFRESHCACHEMESSAGGIDINAMICIFROMDB";
	
	// numero massimo record per elenchi
	public static final String NUMERO_MASSIMO_RECORD_PER_ELENCHI = "50";	
	
	//numero massimo errori per importazione NumMaxLogErrorImportazioni
	public static final String INDEX_NUMMAXLOGERRORIMPORTAZIONI = "NUMMAXLOGERRORIMPORTAZIONI";	
	
	// modalita' di Cache-Control da applicare alle response http dell'applicazione RBWebAdmin
	public static final String INDEX_HTTPCACHECONTROLMODE = "HTTPCACHECONTROLMODE";	
	
	//controllo se e' abilitata la modalità diagnosi
	public static final String INDEX_ENABLEDIAGNOSI = "ENABLEDIAGNOSI";

	//	controllo se e' abilitata la modalità di errore esteso (F24)
	public static final String INDEX_ENABLEERROREESTESO_F24 = "ENABLEERROREESTESO_F24";
	
	// se abilitato aggiorna i campi TS_UPDATE,TS_FINE_UPDATE in JLTFLUD
	public static final String INDEX_ENABLETSUPDATE = "ENABLETSUPDATE";
	
	public static final String INDEX_LISTABINOSPED = "LISTABINOSPED";
	
	public static final String INDEX_LISTABICTRDATAF24 = "LISTABICTRDATAF24";
	
	// lunghezza massima dei dati aggiuntivi su WS Audit
	public static final String INDEX_AUDITWSMAXLENGTHDATIAGGIUNTIVI = "AUDITWSMAXLENGTHDATIAGGIUNTIVI";

	// gestione in sessione delle liste F24 "corte"
	public static final String INDEX_F24TRANSIENTLISTSIZE = "F24TRANSIENTLISTSIZE";

	private static final String TABELLA_PARAM = "JLTPARA";
	
	private IbisConfigurationFromDB() { }

	public static void loadParams() {
//		traceDebug("IbisConfigurationFromDB load");
		if (true) return;
		
		Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "");

		long MINUTI_REFRESH_JLTAPARA = MINUTI_REFRESH_JLTAPARA_DEFAULT;
		long MINUTI_REFRESH_JLTAPARA_SE_ERRORE = MINUTI_REFRESH_JLTAPARA_SE_ERRORE_DEFAULT; 
		
		try {
			MINUTI_REFRESH_JLTAPARA = Long.parseLong(IbisConfiguration.Get("Ibis", "minutiRefreshJLTPARA"));
			} catch (Exception e1) {
				// Se non lo trovo tengo il default.
		}

		try {
			MINUTI_REFRESH_JLTAPARA_SE_ERRORE = Long.parseLong(IbisConfiguration.Get("Ibis", "minutiRefreshJLTPARASeErrore"));
			} catch (Exception e1) {
				// Se non lo trovo tengo il default.
		}
	
		
		
		long startTime = System.currentTimeMillis();
		long elapsedTime = MINUTI_REFRESH_JLTAPARA* 60* 1000+1; // 15 min * 60 sec * 1000 + 1
		if (loadTime != 0) {
			elapsedTime = startTime - loadTime;
		}
		if (elapsedTime > MINUTI_REFRESH_JLTAPARA * 60* 1000) {
			synchronized (IbisConfigurationFromDB.class) {
				try {
					elapsedTime = System.currentTimeMillis() - loadTime;
					
					if (elapsedTime > MINUTI_REFRESH_JLTAPARA * 60* 1000) {
						
						boolean error=false;  // Diventa true se si ha un'eccezione in almeno una variabile da leggere.
						
						
						// non aggiorno i parametri se non sono passati
						// 15 minuti (900000 millisecondi) dall'ultimo caricamento
						Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "IbisConfigurationFromDB loading...");
						
						//QUERY GENERICA TABELLA PARAMETRI			
						String sSQL = "SELECT DATI FROM " + //DataAccess.getDBOwner() + 
										TABELLA_PARAM +	" WHERE INDICE = ? ";
						String sSQLLike = "SELECT DATI FROM " + //DataAccess.getDBOwner() + 
										TABELLA_PARAM +	" WHERE INDICE LIKE ? ";
						Vector vParams = new Vector();
						String[] dataContent = null;
						
						//------------- INDEX_MAXROWSINCASSI
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MAXROWSINCASSI);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: queryMaxRowsIncassi = 0;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								queryMaxRowsIncassi = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSINCASSI " + dataContent[0]); 
							} 
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSINCASSI " + e.getMessage()); 
						}
						
						//------------- INDEX_MAXROWSPAGAMENTI
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MAXROWSPAGAMENTI);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: queryMaxRowsPagamenti = 0;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								queryMaxRowsPagamenti = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSPAGAMENTI " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSPAGAMENTI " + e.getMessage()); 
						}
						
						// INDEX_MAXROWSDELEGHEF24
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MAXROWSDELEGHEF24);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: queryMaxRowsDelegheF24 = 0;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								queryMaxRowsDelegheF24 = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSDELEGHEF24 " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSDELEGHEF24 " + e.getMessage()); 
						}
						
						// INDEX_MAXROWSFLUSSI
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MAXROWSFLUSSI);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: queryMaxRowsFlussi = 0;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								queryMaxRowsFlussi = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSFLUSSI " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MAXROWSFLUSSI " + e.getMessage()); 
						}
		
						// INDEX_MINUTIREFRESHCACHETABELLEF24
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MINUTIREFRESHCACHETABELLEF24);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: minutiRefreshCacheTabelleF24 = 15;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								minutiRefreshCacheTabelleF24 = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHETABELLEF24 " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHETABELLEF24 " + e.getMessage()); 
						}

						// INDEX_TRIBERARIONOSOGLIA
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_TRIBERARIONOSOGLIA);
						String tribErarioNoSogliaTemp = "";
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: tribErarioNoSoglia = "";
						try {
							dataContent = stringSelect(sSQLLike, vParams);
							for (int i = 0; i < dataContent.length; i++) {
								tribErarioNoSogliaTemp = tribErarioNoSogliaTemp + dataContent[i].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_TRIBERARIONOSOGLIA_TEMP " + tribErarioNoSogliaTemp);
							}
							tribErarioNoSoglia = tribErarioNoSogliaTemp;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_TRIBERARIONOSOGLIA " + tribErarioNoSoglia);
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_TRIBERARIONOSOGLIA " + e.getMessage()); 
						}

						// INDEX_TRIBREGIONINOSOGLIA
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_TRIBREGIONINOSOGLIA);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: tribRegioniNoSoglia = "";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								tribRegioniNoSoglia = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_TRIBREGIONINOSOGLIA " + tribRegioniNoSoglia); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_TRIBREGIONINOSOGLIA " + e.getMessage()); 
						}

						// INDEX_CHECKSOGLIACOMPENSAZIONEF24
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKSOGLIACOMPENSAZIONEF24);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkSogliaCompensazioneF24 = true;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkSogliaCompensazioneF24 = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKSOGLIACOMPENSAZIONEF24 " + checkSogliaCompensazioneF24); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKSOGLIACOMPENSAZIONEF24 " + e.getMessage()); 
						}


						// INDEX_CHECKQUADRATURAFLUSSIF24
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKQUADRATURAFLUSSIF24);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkQuadraturaFlussiF24 = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkQuadraturaFlussiF24 = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKQUADRATURAFLUSSIF24 " + checkQuadraturaFlussiF24); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKQUADRATURAFLUSSIF24 " + e.getMessage()); 
						}


						// INDEX_OTTIMIZZAZIONERTRIMSURID
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OTTIMIZZAZIONERTRIMSURID);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: ottimizzazioneRtrimSuRidEnabled = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								ottimizzazioneRtrimSuRidEnabled = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSURID " + ottimizzazioneRtrimSuRidEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSURID " + e.getMessage()); 
						}						

						// INDEX_OTTIMIZZAZIONERTRIMSURIBA 
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OTTIMIZZAZIONERTRIMSURIBA );
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: ottimizzazioneRtrimSuRibaEnabled = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								ottimizzazioneRtrimSuRibaEnabled = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSURIBA  " + ottimizzazioneRtrimSuRibaEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSURIBA  " + e.getMessage()); 
						}						
						
						// INDEX_OTTIMIZZAZIONERTRIMSUMAV 
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OTTIMIZZAZIONERTRIMSUMAV  );
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: ottimizzazioneRtrimSuMavEnabled = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								ottimizzazioneRtrimSuMavEnabled = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUMAV   " + ottimizzazioneRtrimSuMavEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUMAV   " + e.getMessage()); 
						}							
						// INDEX_OTTIMIZZAZIONERTRIMSUBONIFICI 
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OTTIMIZZAZIONERTRIMSUBONIFICI  );
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: ottimizzazioneRtrimSuBonificiEnabled = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								ottimizzazioneRtrimSuBonificiEnabled = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUBONIFICI   " + ottimizzazioneRtrimSuBonificiEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUBONIFICI   " + e.getMessage()); 
						}							
						
						
						// INDEX_OTTIMIZZAZIONERTRIMSUSTIPENDI 
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OTTIMIZZAZIONERTRIMSUSTIPENDI  );
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: ottimizzazioneRtrimSuStipendiEnabled = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								ottimizzazioneRtrimSuStipendiEnabled = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUSTIPENDI   " + ottimizzazioneRtrimSuStipendiEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUSTIPENDI   " + e.getMessage()); 
						}	
												
						// INDEX_OTTIMIZZAZIONERTRIMSUEFFETTI 
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OTTIMIZZAZIONERTRIMSUEFFETTI  );
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: ottimizzazioneRtrimSuEffettiEnabled = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								ottimizzazioneRtrimSuEffettiEnabled = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUEFFETTI   " + ottimizzazioneRtrimSuEffettiEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OTTIMIZZAZIONERTRIMSUEFFETTI   " + e.getMessage()); 
						}	

						// INDEX_CHECKRAGGIUNGIBILITAACC 
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKRAGGIUNGIBILITASEPAACC);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkRaggiungilitaSepaAcc = "0";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkRaggiungilitaSepaAcc = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKRAGGIUNGIBILITASEPAACC " + checkRaggiungilitaSepaAcc); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKRAGGIUNGIBILITAACC " + e.getMessage()); 
						}

						// INDEX_CHECKRAGGIUNGIBILITAADD
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKRAGGIUNGIBILITASEPAADD);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkRaggiungilitaSepaAdd = "0";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkRaggiungilitaSepaAdd = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKRAGGIUNGIBILITASEPAADD " + checkRaggiungilitaSepaAdd); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKRAGGIUNGIBILITASEPAADD " + e.getMessage()); 
						}
						
						//LIMITEORADATAESECUZIONESCT
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_LIMITEORADATAESECUZIONEBONIFICOSCT);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: limiteOraDataEsecuzioneBonificoSct = "13";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								limiteOraDataEsecuzioneBonificoSct = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_LIMITEORADATAESECUZIONEBONIFICOSCT " + limiteOraDataEsecuzioneBonificoSct); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_LIMITEORADATAESECUZIONEBONIFICOSCT " + e.getMessage()); 
						}						

						// INDEX_CHECKMONOBANCA
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKMONOBANCA);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkMonobanca = "0";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkMonobanca = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKMONOBANCA " + checkMonobanca); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKMONOBANCA " + e.getMessage()); 
						}
						
						// INDEX_AUDITWSLOGENABLE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_AUDITWSLOGENABLE);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: auditWSLogEnable = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								auditWSLogEnable = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_AUDITWSLOGENABLE " + auditWSLogEnable); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_AUDITWSLOGENABLE " + e.getMessage()); 
						}	
						
						// INDEX_AZIENDEAUDITWSLOGENABLED
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_AZIENDEAUDITWSLOGENABLED);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: aziendeAuditWSLogEnabled = "NCH";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								aziendeAuditWSLogEnabled = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_AZIENDEAUDITWSLOGENABLED " + aziendeAuditWSLogEnabled); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_AZIENDEAUDITWSLOGENABLED " + e.getMessage()); 
						}	
						
//						INDEX_AZIENDEAUDITWSLOGENABLED
						 vParams.removeAllElements();
						 AttributeValueType.addStringElement(vParams,INDEX_SERVIZIAUDITLOGENABLED);
						 //ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: aziendeAuditWSLogEnabled = "NCH";
						 try {
							 dataContent = stringSelect(sSQL, vParams);
							 if (dataContent.length == 1) {
								 serviziAuditLogEnabled = dataContent[0].trim();
								 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_SERVIZIAUDITLOGENABLED" + serviziAuditLogEnabled); 
							 }
						 } catch (Exception e) {
							 error=true;	
							 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_SERVIZIAUDITLOGENABLED" + e.getMessage()); 
						 }	
						
						// INDEX_OPTIMIZEIMPORT
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORT);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImport = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								optimizeImport = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORT " + optimizeImport); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORT " + e.getMessage()); 
						}	

						// INDEX_OPTIMIZEIMPORTVALIDCHARS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTVALIDCHARS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportValidChars = "";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								optimizeImportValidChars = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTVALIDCAHRS " + optimizeImportValidChars); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTVALIDCHARS " + e.getMessage()); 
						}	

						// INDEX_OPTIMIZEIMPORTNOTVALIDCVSCHARS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTNOTVALIDCVSCHARS);
						String optimizeImportNotValidCVSCharsTemp = "";
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportNotValidCVSChars = "";
						try {
							dataContent = stringSelect(sSQLLike+" ORDER BY INDICE ASC ", vParams);
							for (int i = 0; i < dataContent.length; i++) {
								optimizeImportNotValidCVSCharsTemp = optimizeImportNotValidCVSCharsTemp + dataContent[i].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTVALIDCVSCHARS_TEMP " + optimizeImportNotValidCVSCharsTemp); 
							}
							optimizeImportNotValidCVSChars = optimizeImportNotValidCVSCharsTemp;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTVALIDCVSCHARS " + optimizeImportNotValidCVSChars); 
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTVALIDCHARSCVS " + e.getMessage()); 
						}	

						// INDEX_OPTIMIZEIMPORTNOTVALIDCHARS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTNOTVALIDCHARS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportNotValidChars = "";
						String optimizeImportNotValidCharsTemp = "";
						try {
							dataContent = stringSelect(sSQLLike+" ORDER BY INDICE ASC ", vParams);
							for (int i = 0; i < dataContent.length; i++) {
								optimizeImportNotValidCharsTemp = optimizeImportNotValidCharsTemp + dataContent[i].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTVALIDCHARS_TEMP " + optimizeImportNotValidCharsTemp); 
							}
							optimizeImportNotValidChars = optimizeImportNotValidCharsTemp;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTVALIDCHARS " + optimizeImportNotValidChars); 
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTVALIDCHARS " + e.getMessage()); 
						}	

						// INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportNotPrintableChars = "";
						String optimizeImportNotPrintableCharsTemp = "";
						try {
							dataContent = stringSelect(sSQLLike+" ORDER BY INDICE ASC ", vParams);
							for (int i = 0; i < dataContent.length; i++) {
								optimizeImportNotPrintableCharsTemp = optimizeImportNotPrintableCharsTemp + dataContent[i].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARS_TEMP " + optimizeImportNotPrintableCharsTemp); 
							}
							optimizeImportNotPrintableChars = optimizeImportNotPrintableCharsTemp;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARS " + optimizeImportNotPrintableChars); 
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARS " + e.getMessage()); 
						}	

						// INDEX_OPTIMIZEIMPORTNOTPRINTABLECVSCHARS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTNOTPRINTABLECVSCHARS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportNotPrintableCVSChars = "";
						String optimizeImportNotPrintableCVSCharsTemp = "";
						try {
							dataContent = stringSelect(sSQLLike+" ORDER BY INDICE ASC ", vParams);
							for (int i = 0; i < dataContent.length; i++) {
								optimizeImportNotPrintableCVSCharsTemp = optimizeImportNotPrintableCVSCharsTemp + dataContent[i].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTPRINTABLECVSCHARS_TEMP " + optimizeImportNotPrintableCVSCharsTemp); 
							}
							optimizeImportNotPrintableCVSChars = optimizeImportNotPrintableCVSCharsTemp;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTPRINTABLECVSCHARS " + optimizeImportNotPrintableCVSChars); 
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNOTPRINTABLECHARSCVS " + e.getMessage()); 
						}	

						// INDEX_OPTIMIZEIMPORTNMAXERRORNOTVALID
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTNMAXERRORNOTVALIDCHARS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportNMaxErrorNotValidChars = 0;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								optimizeImportNMaxErrorNotValidChars = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNMAXERRORNOTVALIDCHARS " + optimizeImportNMaxErrorNotValidChars); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNMAXERRORNOTVALIDCHARS " + e.getMessage()); 
						}												

						// INDEX_OPTIMIZEIMPORTNMAXERRORNOTPRINTABLECHARS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_OPTIMIZEIMPORTNMAXERRORNOTPRINTABLECHARS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: optimizeImportNMaxErrorNotPrintableChars = 0;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								optimizeImportNMaxErrorNotPrintableChars = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNMAXERRORNOTPRINTABLECHARS " + optimizeImportNMaxErrorNotPrintableChars); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OPTIMIZEIMPORTNMAXERRORNOTPRINTABLECHARS " + e.getMessage()); 
						}	
						
						// PROTDELEGA_STRATEGY
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,PROTDELEGA_STRATEGY);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								protDelegaStategy = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - PROTDELEGA_STRATEGY " + protDelegaStategy); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - PROTDELEGA_STRATEGY " + e.getMessage()); 
						}
						
						// INDEX_CHECKSERVIZIPROMEMORIAON
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKSERVIZIPROMEMORIAON);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkServiziPromemoriaOn = "";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkServiziPromemoriaOn = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKSERVIZIPROMEMORIAON " + checkServiziPromemoriaOn); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKSERVIZIPROMEMORIAON " + e.getMessage()); 
						}							
						
						//INDEX_MINUTIREFRESHCACHEPROMEMORIA
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MINUTIREFRESHCACHEPROMEMORIA);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: minutiRefreshCachePromemoria = 15;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								minutiRefreshCachePromemoria = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHEPROMEMORIA " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHEPROMEMORIA " + e.getMessage()); 
						}
						
						// INDEX_CHECKSERVIZICMSON
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CHECKSERVIZICMSON);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: checkServiziCmsOn = "";
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkServiziCmsOn = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKSERVIZICMSON " + checkServiziCmsOn); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKSERVIZICMSON " + e.getMessage()); 
						}	
						
						//INDEX_MINUTIREFRESHCACHECMS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MINUTIREFRESHCACHECMS);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: minutiRefreshCacheCms = 15;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								minutiRefreshCacheCms = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHECMS " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHECMS " + e.getMessage()); 
						}
						
						//INDEX_CMSDEBUGENABLE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CMSDEBUGENABLE);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: cmsDebugEnable = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								cmsDebugEnable = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CMSDEBUGENABLE " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CMSDEBUGENABLE " + e.getMessage()); 
						}
												
						//INDEX_MINUTIREFRESHSESSIONCACHE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MINUTIREFRESHSESSIONCACHE);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: minutiRefreshSessionCache = 15;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								minutiRefreshSessionCache = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHSESSIONCACHE " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHSESSIONCACHE " + e.getMessage()); 
						}


						//INDEX_MILLISTIMEOUTWSAUDIT
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MILLISTIMEOUTWSAUDIT);

						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								millisTimeoutWsAudit = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MILLISTIMEOUTWSAUDIT " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MILLISTIMEOUTWSAUDIT " + e.getMessage()); 
						}

						// INDEX_ERRORIMPORTESTESOENABLE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_ERRORIMPORTESTESOENABLE);
						
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								errorImportEstesoEnable = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ERRORIMPORTESTESOENABLE " + errorImportEstesoEnable); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ERRORIMPORTESTESOENABLE " + e.getMessage()); 
						}	
						
						// INDEX_CMSPLACEHOLDERVALUES
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_CMSPLACEHOLDERVALUES);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								cmsPlaceHolderValues = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CMSPLACEHOLDERVALUES " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CMSPLACEHOLDERVALUES " + e.getMessage()); 
						}

						
						// INDEX_CHECKJSCBINOTVALIDCHARSENABLE


						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_CHECKJSCBINOTVALIDCHARSENABLE);		
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkJsCbiNotValidCharsEnable = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKJSCBINOTVALIDCHARSENABLE " + checkJsCbiNotValidCharsEnable); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_CHECKJSCBINOTVALIDCHARSENABLE " + e.getMessage()); 
						}



						// INDEX_OVERRIDDENABIDIRFORCSS
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_OVERRIDDENABIDIRFORCSS);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								overriddenAbiDirForCss = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OVERRIDDENABIDIRFORCSS " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_OVERRIDDENABIDIRFORCSS " + e.getMessage()); 
						}
						
						
						// NUMERO_MASSIMO_RECORD_PER_ELENCHI
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,NUMERO_MASSIMO_RECORD_PER_ELENCHI);
						try { 
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								numeroMassimoRecordPerElenco = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - NUMERO_MASSIMO_RECORD_PER_ELENCHI " + numeroMassimoRecordPerElenco); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - NUMERO_MASSIMO_RECORD_PER_ELENCHI " + e.getMessage()); 
						}	
						
						// ABIlITAZIONE DEL RECUPERO DELL'ULTIMA QUIETANZA ESPORTATA
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_ENABLERESCUELASTEXP);
						try { 
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								recuperaUltimaQuietanzaEsportata = Boolean.TRUE.toString().equals(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - ABILITAZIONE_RECUPERO_ULTIMA_QUIET_EXP " + recuperaUltimaQuietanzaEsportata); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - ABILITAZIONE_RECUPERO_ULTIMA_QUIET_EXP " + e.getMessage()); 
						}

						// GIORNI DI VALIDITA' DELLE QUIETANZE DA RECUPERARE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_GIORNIVALIDITAQUIETANZAEXP);
						try { 
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								giorniValiditaQuietanzaEsportata = new Integer(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - VALIDITA_QUIETANZE_DA_RECUPERARE " + giorniValiditaQuietanzaEsportata); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - VALIDITA_QUIETANZE_DA_RECUPERARE " + e.getMessage()); 
						}
						
						// INDEX_ENABLEMESSAGGIDINAMICIFROMDB
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_ENABLEMESSAGGIDINAMICIFROMDB);
						try { 
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								enableMessaggiDinamiciFromDB = Boolean.TRUE.toString().equalsIgnoreCase(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLEMESSAGGIDINAMICIFROMDB " + enableMessaggiDinamiciFromDB); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLEMESSAGGIDINAMICIFROMDB " + e.getMessage()); 
						}
						
						//INDEX_MINUTIREFRESHCACHEMESSAGGIDINAMICIFROMDB
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_MINUTIREFRESHCACHEMESSAGGIDINAMICIFROMDB);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								minutiRefreshCacheMessaggiDinamiciFromDB = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHEMESSAGGIDINAMICIFROMDB " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_MINUTIREFRESHCACHEMESSAGGIDINAMICIFROMDB " + e.getMessage()); 
						}
						
						//INDEX_NUMMAXLOGERRORIMPORTAZIONI
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_NUMMAXLOGERRORIMPORTAZIONI);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								numMaxLogErrorImportazioni = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_NUMMAXLOGERRORIMPORTAZIONI " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_NUMMAXLOGERRORIMPORTAZIONI " + e.getMessage()); 
						}	
						
						//INDEX_HTTPCACHECONTROLMODE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams, INDEX_HTTPCACHECONTROLMODE);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								httpCacheControlMode = dataContent[0].trim();
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_HTTPCACHECONTROLMODE " + dataContent[0]); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_HTTPCACHECONTROLMODE " + e.getMessage()); 
						}
						
						// INDEX_ENABLEDIAGNOSI
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_ENABLEDIAGNOSI);
						try { 
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								checkEnableDiagnosi = Boolean.TRUE.toString().equalsIgnoreCase(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLEDIAGNOSI " + checkEnableDiagnosi); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLEDIAGNOSI " + e.getMessage()); 
						}	

//						INDEX_ENABLEErroreEsteso_F24
						 vParams.removeAllElements();
						 AttributeValueType.addStringElement(vParams,INDEX_ENABLEERROREESTESO_F24);
						 try { 
							 dataContent = stringSelect(sSQL, vParams);
							 if (dataContent.length == 1) {
								 checkEnableErroreEstesoF24 = Boolean.TRUE.toString().equalsIgnoreCase(dataContent[0]);
								 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLEERROREESTESO_F24 " + checkEnableErroreEstesoF24); 
							 }
						 } catch (Exception e) {
							 error=true;
							 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLEERROREESTESO_F24 " + e.getMessage()); 
						 }	


						vParams.removeAllElements();
						 AttributeValueType.addStringElement(vParams,INDEX_LISTABINOSPED);
						 try { 
							 dataContent = stringSelect(sSQL, vParams);
							 if (dataContent.length == 1) {
								 listaAbiNoSped = dataContent[0].trim();
								 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_LISTABINOSPED " + listaAbiNoSped); 
							 }
						 } catch (Exception e) {
							 error=true;
							 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_LISTABINOSPED " + e.getMessage()); 
						 }	
						vParams.removeAllElements();
						 AttributeValueType.addStringElement(vParams,INDEX_LISTABICTRDATAF24);
						 try { 
							 dataContent = stringSelect(sSQL, vParams);
							 if (dataContent.length == 1) {
								listaAbiCtrDataF24 = dataContent[0].trim();
								 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_LISTABICTRDATAF24 " + listaAbiCtrDataF24 ); 
							 }
						 } catch (Exception e) {
							 error=true;
							 Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_LISTABICTRDATAF24 " + e.getMessage()); 
						 }	

						// ENABLE_TSUPDATE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_ENABLETSUPDATE);
						//ELIMINATO PER MANTENERE ULTIMO VALORE LETTO: enableTSUpdate = false;
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								enableTSUpdate = dataContent[0].trim().equalsIgnoreCase("true");
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLETSUPDATE " + auditWSLogEnable); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_ENABLETSUPDATE " + e.getMessage()); 
						}	


						// ENABLE_TSUPDATE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_AUDITWSMAXLENGTHDATIAGGIUNTIVI);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								auditWsMaxLengthDatiAggiuntivi = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_AUDITWSMAXLENGTHDATIAGGIUNTIVI " + auditWSLogEnable); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_AUDITWSMAXLENGTHDATIAGGIUNTIVI " + e.getMessage()); 
						}	


						// F24TRANSIENTLISTSIZE
						vParams.removeAllElements();
						AttributeValueType.addStringElement(vParams,INDEX_F24TRANSIENTLISTSIZE);
						try {
							dataContent = stringSelect(sSQL, vParams);
							if (dataContent.length == 1) {
								f24TransientListSize = Integer.parseInt(dataContent[0]);
								Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_F24TRANSIENTLISTSIZE " + auditWSLogEnable); 
							}
						} catch (Exception e) {
							error=true;
							Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "loadParams - INDEX_F24TRANSIENTLISTSIZE " + e.getMessage()); 
						}	

						/* Ricalcolo il valore di loadTime in funzione della strategia scelta in caso di errore
						 * 
						 * Se è la prima volta che viene tentato il caricamento, un qualsiasi errore nel leggere una qualsiasi variabile deve 
						 * essere considerato come un errore tale da far ripetere il caricamento. Non è bello infatti che il sistema giri a lungo con i valori
						 * di default
						 * 
						 * Se non è la prima volta, allora è opportuno che il caricamento venga rifatto prima dei 15 minuti canonici. Quindi il loadTime
						 * viene fissato in modo da scattare dopo: MINUTI_REFRESH_JLTAPARA_SE_ERRORE minuti.
						 */
						
						if (error) {
							if (loadTime==0) {
								loadTime=0; 
							} else {
								loadTime=System.currentTimeMillis()-(MINUTI_REFRESH_JLTAPARA-MINUTI_REFRESH_JLTAPARA_SE_ERRORE)*60*1000;
							}							
						} else {
							loadTime=System.currentTimeMillis();	
						}
						
						Tracer.debug(IbisConfigurationFromDB.class.getName(), "loadParams", "IbisConfigurationFromDB loaded (" + ((System.currentTimeMillis() - startTime) / 1000) + " sec)");						
						
					}	
				} catch (Exception e) {
					Tracer.error(IbisConfigurationFromDB.class.getName(), "loadParams", "IbisConfigurationFromDB Errore generico, mantenuti gli ultimi valori in memoria : Errore = "+ e.getMessage());
					
				}
			}
		}
	}
	
	
	public static String[] stringSelect(String sSQL, Vector vParams) {
		return new String[1];
	}

	public static int getQueryMaxRowsIncassi() {
		loadParams();
		return queryMaxRowsIncassi;
	}

	public static int getQueryMaxRowsPagamenti() {
		loadParams();
		return queryMaxRowsPagamenti;
	}

	public static int getQueryMaxRowsDelegheF24() {
		loadParams();
		return queryMaxRowsDelegheF24;
	}

	public static int getQueryMaxRowsFlussi() {
		loadParams();
		return queryMaxRowsFlussi;
	}

	public static int getMinutiRefreshCacheTabelleF24() {
		loadParams();
		return minutiRefreshCacheTabelleF24;
	}

	public static String getTribErarioNoSoglia() {
		loadParams();
		return tribErarioNoSoglia;
	}

	public static String getTribRegioniNoSoglia() {
		loadParams();
		return tribRegioniNoSoglia;
	}

	public static boolean getCheckSogliaCompensazioneF24() {
		loadParams();
		return checkSogliaCompensazioneF24;
	}

	public static boolean getCheckQuadraturaFlussiF24() {
		loadParams();
		return checkQuadraturaFlussiF24;
	}

	public static boolean getOttimizzazioneRtrimSuRidEnabled() {
		loadParams();
		return ottimizzazioneRtrimSuRidEnabled;
	}
	
	public static boolean getOttimizzazioneRtrimSuRibaEnabled() {
		loadParams();
		return ottimizzazioneRtrimSuRibaEnabled;
	}

	public static boolean getOttimizzazioneRtrimSuMavEnabled() {
		loadParams();
		return ottimizzazioneRtrimSuMavEnabled;
	}
	
	public static boolean getOttimizzazioneRtrimSuBonificiEnabled() {
		loadParams();
		return ottimizzazioneRtrimSuBonificiEnabled;
	}
	
	public static boolean getOttimizzazioneRtrimSuEffettiEnabled() {
		loadParams();
		return ottimizzazioneRtrimSuEffettiEnabled;
	}
	
	public static boolean getOttimizzazioneRtrimSuStipendiEnabled() {
		loadParams();
		return ottimizzazioneRtrimSuStipendiEnabled;
	}
	
	public static String getLimiteOraDataEsecuzioneBonificoSct() {
		return limiteOraDataEsecuzioneBonificoSct;
	}

	public static String getCheckRaggiungilitaSepaAcc() {
		loadParams();
		return checkRaggiungilitaSepaAcc;
	}

	public static String getCheckRaggiungilitaSepaAdd() {
		loadParams();
		return checkRaggiungilitaSepaAdd;
	}

	public static String getCheckMonobanca() {
		loadParams();
		return checkMonobanca;
	}
	
	public static String getprotDelegaStrategy() {
		loadParams();
		return protDelegaStategy;
	}	
	
	public static boolean getAuditWSLogEnable() {
		loadParams();
		return auditWSLogEnable;
	}	

	public static String getAziendeAuditWSLogEnabled() {
		loadParams();
		return aziendeAuditWSLogEnabled;
	}	

	public static boolean getOptimizeImport() {
		loadParams();
		return optimizeImport;
	}	
	public static String getListaAbiNosped() {
		loadParams();
		return listaAbiNoSped;
	}
	public static String getListaAbiCtrDataF24() {
		loadParams();
		return listaAbiCtrDataF24;
	}
	/**
	 * @return
	 */
	public static int getOptimizeImportNMaxErrorNotPrintableChars() {
		return optimizeImportNMaxErrorNotPrintableChars;
	}

	/**
	 * @return
	 */
	public static int getOptimizeImportNMaxErrorNotValidChars() {
		return optimizeImportNMaxErrorNotValidChars;
	}

	/**
	 * @return
	 */
	public static String getOptimizeImportNotPrintableChars() {
		return optimizeImportNotPrintableChars;
	}

	/**
	 * @return
	 */
	public static String getOptimizeImportNotValidChars() {
		return optimizeImportNotValidChars;
	}

	/**
	 * @return
	 */
	public static String getOptimizeImportNotPrintableCVSChars() {
		return optimizeImportNotPrintableCVSChars;
	}

	/**
	 * @return
	 */
	public static String getOptimizeImportNotValidCVSChars() {
		return optimizeImportNotValidCVSChars;
	}

	/**
	 * @return
	 */
	public static String getOptimizeImportValidChars() {
		return optimizeImportValidChars;
	}
	
	/**
	 * @return
	 */
	public static String getCheckServiziPromemoriaOn() {
		loadParams();
		return checkServiziPromemoriaOn;
	}
	
	public static int getMinutiRefreshCachePromemoria() {
		loadParams();
		return minutiRefreshCachePromemoria;
	}
	
	public static String getCheckServiziCmsOn() {
		loadParams();
		return checkServiziCmsOn;
	}
	
	public static int getMinutiRefreshCacheCms() {
		loadParams();
		return minutiRefreshCacheCms;
	}
	
	public static boolean getCmsDebugEnable() {
		loadParams();
		return cmsDebugEnable;
	}
	

	public static int getMinutiRefreshSessionCache() {
		loadParams();
		return minutiRefreshSessionCache;
	}

	
	public static int getMillisTimeoutWsAudit() {
		loadParams();
		return millisTimeoutWsAudit;
	}

	public static boolean getErrorImportEstesoEnable() {
		loadParams();
		return errorImportEstesoEnable;
	}
	
	public static String getCmsPlaceHolderValues() {
		loadParams();
		return cmsPlaceHolderValues;
	}
	
	public static int getNumeroMassimoRecordPerElenco() {
		loadParams();
		return numeroMassimoRecordPerElenco;
	}
	
	
	public static boolean getCheckJsCbiNotValidCharsEnable() {
		//loadParams();  Commentata volutamente: potrebbe scattare da WebApp prima cche sia stato inizializzato il DataSource
		return checkJsCbiNotValidCharsEnable;

	}	
	
	
	public static String getOverriddenAbiDirForCss() {
		//loadParams(); Commentata volutamente: potrebbe scattare da WebApp prima cche sia stato inizializzato il DataSource
		return overriddenAbiDirForCss;
	}

	public static boolean getRecuperaUltimaQuietanzaEsportata() {
		loadParams(); 
		return recuperaUltimaQuietanzaEsportata;
	}
			
	public static Integer getGiorniValiditaQuietanzaEsportata() {
		loadParams(); 
		return giorniValiditaQuietanzaEsportata;
	}
			
	public static boolean isEnableMessaggiDinamiciFromDB() {
		loadParams();
		return enableMessaggiDinamiciFromDB;
	}

	public static int getMinutiRefreshCacheMessaggiDinamiciFromDB() {
		loadParams();
		return minutiRefreshCacheMessaggiDinamiciFromDB;
	}
	public static String getServiziAuditLogEnabled() {
		loadParams();
		return serviziAuditLogEnabled;
	}
	public static int getNumMaxLogErrorImportazioni() {
		loadParams();
		return numMaxLogErrorImportazioni;
	}		
	public static String getHttpCacheControlMode() {
		//loadParams();  Commentata volutamente: potrebbe scattare da WebApp prima cche sia stato inizializzato il DataSource
		return httpCacheControlMode;
	}	
	public static boolean getCheckEnableDiagnosi(){
		return checkEnableDiagnosi;		
	}
	
	public static boolean getCheckEnableErroreEstesoF24(){
		return checkEnableErroreEstesoF24;		
	}
	
	public static boolean getEnableTSUpdate(){
		return enableTSUpdate;		
	}
	
	public static int getAuditWsMaxLengthDatiAggiuntivi(){
		return auditWsMaxLengthDatiAggiuntivi;		
	}
	
	public static int getF24TransientListSize(){
		return f24TransientListSize;		
	}
	
}