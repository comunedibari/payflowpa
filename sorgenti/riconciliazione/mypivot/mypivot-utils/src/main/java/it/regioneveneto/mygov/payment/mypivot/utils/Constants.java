package it.regioneveneto.mygov.payment.mypivot.utils;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Constants {

	public final static String DDMMYYYY_HHMMSS_PATTERN = "dd/MM/yyyy hh:mm:ss";
	public final static DateFormat DDMMYYYY_HHMMSS = new SimpleDateFormat(DDMMYYYY_HHMMSS_PATTERN);

	public final static String DDMMYYYY_HHMM_PATTERN = "dd/MM/yyyy HH:mm";
	public final static DateFormat DDMMYYYY_HHMM = new SimpleDateFormat(DDMMYYYY_HHMM_PATTERN);

	public final static String DDMMYYYY_PATTERN = "dd/MM/yyyy";
	public final static DateFormat DDMMYYYY = new SimpleDateFormat(DDMMYYYY_PATTERN);

	public final static String YYYYMMDD_PATTERN = "yyyy-MM-dd";
	public final static DateFormat YYYYMMDD = new SimpleDateFormat(YYYYMMDD_PATTERN);

	public final static String YYYY_MM_PATTERN = "yyyy_MM";
	public final static DateFormat YYYY_MM = new SimpleDateFormat(YYYY_MM_PATTERN);
	
	public final static String YYYY_PATTERN = "yyyy";
	public final static DateFormat YYYY = new SimpleDateFormat(YYYY_PATTERN);
	
	public final static String MMYYYY_PATTERN = "MM/yyyy";
	public final static DateFormat MMYYYY = new SimpleDateFormat(MMYYYY_PATTERN);

	public final static String DDMMYY_PATTERN = "dd/MM/yy";
	public final static DateFormat DDMMYY = new SimpleDateFormat(DDMMYY_PATTERN);

	public final static String yyyyMMddHHmmss_PATTERN = "yyyyMMddHHmmss";
	public final static DateFormat yyyyMMddHHmmss = new SimpleDateFormat(yyyyMMddHHmmss_PATTERN);

	public final static Date MINIMUM_DATE = new Date(Long.MIN_VALUE);
	public final static Date MAXIMUM_DATE = new Date(Long.MAX_VALUE);

	public final static NumberFormat NUMBER_FORMAT_US = NumberFormat.getNumberInstance(Locale.US);
	
	public final static NumberFormat NUMBER_FORMAT_IT = NumberFormat.getNumberInstance(Locale.ITALIAN);
	
	public static final String COD_PROVENIENZA_FILE_WEB = "WEB";

	public static String RUOLO_AMMINISTRATORE = "ROLE_ADMIN";
	public static String RUOLO_VISUALIZZATORE = "ROLE_VISUAL";
	public static String RUOLO_ACCERTAMENTO = "ROLE_ACC";
	public static String RUOLO_STATISTICHE = "ROLE_STATS";

	/*
	 * TIPO FLUSSO
	 */
	public static Character COD_TIPO_FLUSSO_EXPORT_PAGATI = 'E';
	public static Character COD_TIPO_FLUSSO_RENDICONTAZIONE_STANDARD = 'R';
	public static Character COD_TIPO_FLUSSO_RENDICONTAZIONE_POSTE = 'P';
	public static Character COD_TIPO_FLUSSO_QUADRATURA = 'Q';
	public static Character COD_TIPO_FLUSSO_TESORERIA = 'T';
	public static Character COD_TIPO_FLUSSO_DOVUTI = 'D';
	public static Character COD_TIPO_FLUSSO_GIORNALE_DI_CASSA = 'C';
	public static Character COD_TIPO_FLUSSO_GIORNALE_DI_CASSA_OPI = 'O';

	/*
	 * DE TIPI STATO
	 */
	public static String DE_TIPO_STATO_MANAGE = "MANAGE";
	public static final String DE_TIPO_STATO_PRENOTA_FLUSSO_RICONCILIAZIONE = "EXPORT_FLUSSO_RICONCILIAZIONE";
	public static final String DE_TIPO_STATO_ALL = "ALL";
	public static final String DE_TIPO_STATO_ACCERTAMENTO = "ACCERTAMENTO";

	/*
	 * COD STATO
	 */
	public static String COD_TIPO_STATO_MANAGE_FLUSSO_OBSOLETO = "FLUSSO_OBSOLETO";
	public static String COD_TIPO_STATO_MANAGE_FILE_IN_DOWNLOAD = "FILE_IN_DOWNLOAD";
	public static String COD_TIPO_STATO_MANAGE_FILE_SCARICATO = "FILE_SCARICATO";
	public static String COD_TIPO_STATO_MANAGE_FILE_IN_CARICAMENTO = "FILE_IN_CARICAMENTO";
	public static String COD_TIPO_STATO_MANAGE_FILE_CARICATO = "FILE_CARICATO";
	public static String COD_TIPO_STATO_MANAGE_ERROR_DOWNLOAD = "ERROR_DOWNLOAD";
	public static String COD_TIPO_STATO_MANAGE_ERROR_LOAD = "ERROR_LOAD";

	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_PRENOTATO = "PRENOTATO";
	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_ERRORE_EXPORT_FLUSSO_RICONCILIAZIONE = "ERRORE_EXPORT_FLUSSO_RICONCILIAZIONE";
	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_NUMERO_MASSIMO_EXPORT_RIGHE_CONSENTITO_SUPERATO = "NUMERO_MASSIMO_EXPORT_RIGHE_CONSENTITO_SUPERATO";
	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO = "EXPORT_ESEGUITO";
	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_EXPORT_ESEGUITO_NESSUN_RECORD_TROVATO = "EXPORT_ESEGUITO_NESSUN_RECORD_TROVATO";
	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_VERSIONE_TRACCIATO_ERRATA = "VERSIONE_TRACCIATO_ERRATA";
	public static final String COD_TIPO_STATO_EXPORT_FLUSSO_RICONCILIAZIONE_CLASSIFICAZIONE_COMPLETEZZA_NON_VALIDA = "CLASSIFICAZIONE_COMPLETEZZA_NON_VALIDA";

	public static final String COD_TIPO_STATO_IN_CARICO = "IN_CARICO";
	
	
	/* 
	 * Stati che descrivono l'evoluzione dell'Accertamento. 
	 * @author Marianna Memoli
	 */
	public static final String COD_TIPO_STATO_ACCERTAMENTO_INSERITO = 	"INSERITO";
	public static final String COD_TIPO_STATO_ACCERTAMENTO_CHIUSO = 	"CHIUSO";
	public static final String COD_TIPO_STATO_ACCERTAMENTO_ANNULLATO = 	"ANNULLATO";
	
	
	/*
	 * COD ERRORE
	 */
	public static final String COD_ERRORE_RT_NO_IUF = "RT_NO_IUF";// Pagamenti
																	// non
																	// correttamente
																	// rendicontati
	public static final String COD_ERRORE_IUF_NO_TES = "IUF_NO_TES";// Rendicontazioni
																	// non
																	// correttamente
																	// riversate
	public static final String COD_ERRORE_TES_NO_IUF_OR_IUV = "TES_NO_IUF_OR_IUV";// Riversamenti
																					// non
																					// rendicontati
																					// o
																					// di
																					// pagamenti
																					// non
																					// eseguiti
	public static final String COD_ERRORE_IUV_NO_RT = "IUV_NO_RT";// Rendicontazioni
																	// di
																	// pagamenti
																	// non
																	// eseguiti
	public static final String COD_ERRORE_IUD_NO_RT = "IUD_NO_RT";// Notifiche
																	// di pagamenti
																	// non eseguiti
	public static final String COD_ERRORE_RT_NO_IUD = "RT_NO_IUD";// Pagamenti
																	// non
																	// correttamente
																	// notificati
	public static final String COD_ERRORE_IUD_RT_IUF_TES = "IUD_RT_IUF_TES";// Pagamenti
																			// Notificati
	public static final String COD_ERRORE_RT_IUF = "RT_IUF";// Pagamenti
															// Rendicontati
	public static final String COD_ERRORE_RT_IUF_TES = "RT_IUF_TES";// Pagamenti
																	// riversati
																	// cumulativamente
	public static final String COD_ERRORE_IUD_RT_IUF = "IUD_RT_IUF";// Pagamenti
																	// Notificati
																	// e
																	// Riconciliati
	public static final String COD_ERRORE_TES_NO_MATCH = "TES_NO_MATCH"; // Riversamenti
																			// di
																			// tesoreria
																			// sconosciuti
	public static final String COD_ERRORE_IUF_TES_DIV_IMP = "IUF_TES_DIV_IMP"; // Rendicontazioni
																				// con
																				// riversamento
																				// ma
																				// con
																				// importo
																				// differente
	public static final String COD_ERRORE_RT_TES = "RT_TES"; // Pagamenti con
																// riversamento
																// puntuale

	
	/*
	 * Endpoint alle pagini dell'accertamento
	 * 
	 * @author Marianna Memoli
	 */
	public static final String PATH_PAGE_SCELTA_ENTE = 			  "/protected/sceltaEnte.html";
	public static final String PATH_PAGE_RICERCA_ACCERTAMENTI =   "/protected/accertamenti/ricerca.html";
	public static final String PATH_PAGE_DETTAGLIO_ACCERTAMENTO = "/protected/accertamenti/dettaglio.html";
	public static final String PATH_PAGE_ADD_PAG_ACCERTAMENTO =   "/protected/accertamenti/addPagamenti.html";
	public static final String PATH_PAGE_DEL_PAG_ACCERTAMENTO =   "/protected/accertamenti/deletePagamenti.html";
	

	
	/*
	 * CLASSIFICAZIONI
	 * 
	 */
	
	public static String	CLASSIFICAZIONI								= "Classificazione";
	public static String	CLASSIFICAZIONI_IUD_NO_RT                   = "Notifiche di pagamenti non eseguiti";
	public static String	CLASSIFICAZIONI_IUF_NO_TES                  = "Rendicontazioni non correttamente riversate";
	public static String	CLASSIFICAZIONI_IUF_TES_DIV_IMP				= "Rendicontazioni riversate con importo scorretto";
	public static String	CLASSIFICAZIONI_IUV_NO_RT                   = "Rendicontazioni di pagamenti non eseguiti";
	public static String	CLASSIFICAZIONI_IUD_RT_IUF_TES              = "Pagamenti Notificati";
	public static String	CLASSIFICAZIONI_RT_IUF                		= "Pagamenti Rendicontati";
	public static String	CLASSIFICAZIONI_RT_IUF_TES                	= "Pagamenti riversati cumulativamente";
	public static String	CLASSIFICAZIONI_RT_NO_IUD                   = "Pagamenti non correttamente notificati";
	public static String	CLASSIFICAZIONI_RT_NO_IUF                   = "Pagamenti non correttamente rendicontati";
	public static String	CLASSIFICAZIONI_RT_TES						= "Pagamenti riversati puntualmente";
	public static String	CLASSIFICAZIONI_TES_NO_IUF_OR_IUV           = "Riversamenti non rendicontati o di pagamenti non eseguiti";
	public static String	CLASSIFICAZIONI_IUD_RT_IUF           		= "Pagamenti Notificati e Riconciliati";
	public static String	CLASSIFICAZIONI_TES_NO_MATCH				= "Riversamenti di tesoreria sconosciuti";




	public enum VERSIONE_TRACCIATO_EXPORT {
		VERSIONE_1_0("1.0"), VERSIONE_1_1("1.1"), VERSIONE_1_2("1.2"), VERSIONE_1_3("1.3");
		String value;

		private VERSIONE_TRACCIATO_EXPORT(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	public static final int CAUSALE_MAX_LENGTH_EXPORT_RICONCILIAZIONE_VERSIONE = 1024;

	public static final String CODICE_NOT_AVAILABLE = "n/a";

	public enum VISUALIZZA_NASCOSTI {
		TRUE("true"), FALSE("false"), ALL("all");
		String value;

		private VISUALIZZA_NASCOSTI(String value) {
			this.value = value;
		}

		public static VISUALIZZA_NASCOSTI byValue(String value) {
			if (value == null) {
				return null;
			} else {
				for (VISUALIZZA_NASCOSTI currVal : VISUALIZZA_NASCOSTI.values()) {
					if (currVal.value.equals(value)) {
						return currVal;
					}
				}
				throw new RuntimeException("valore non previsto[" + value + "]");
			}
		}

		public String getValue() {
			return value;
		}
	}

	public enum RESPONSE_STATUS {
		OK("OK"), KO("KO");
		String value;

		private RESPONSE_STATUS(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
	
	public enum TIPO_VISUALIZZAZIONE {
		RICONCILIAZIONE("R"), ANOMALIE("A");
		String value;

		private TIPO_VISUALIZZAZIONE(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	
}
