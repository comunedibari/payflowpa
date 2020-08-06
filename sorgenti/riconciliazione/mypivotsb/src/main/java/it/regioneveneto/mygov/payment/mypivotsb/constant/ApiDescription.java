package it.regioneveneto.mygov.payment.mypivotsb.constant;

public class ApiDescription {
	private ApiDescription() {}

	//ENTE
	public static final String DESCR_GET_ALL_ENTE 		= "Lista di tutti gli Enti beneficiari";
	public static final String DESCR_GET_SINGLE_ENTE 	= "Visualizza Ente dettaglio";
	public static final String DESCR_CREATE_ENTE 		= "Creazione di un Ente";
	public static final String DESCR_UPDATE_ENTE 		= "Aggiorna dati di un Ente";
	public static final String DESCR_DELETE_ENTE 		= "Elimina un Ente";
	
	//ENTE_TIPO_DOVUTO
	public static final String DESCR_CREATE_ENTE_TIPO_DOVUTO 		= "Creazione di un Tipo Dovuto";
	public static final String DESCR_UPDATE_ENTE_TIPO_DOVUTO 		= "Aggiorna dati di un Tipo Dovuto";
	public static final String DESCR_DELETE_ENTE_TIPO_DOVUTO 		= "Elimina un Tipo Dovuto";
	public static final String DESCR_GET_ALL_ENTE_TIPO_DOVUTO 		= "Lista di tutti i Tipi Dovuto";
	public static final String DESCR_GET_SINGLE_ENTE_TIPO_DOVUTO 	= "Visualizza Tipo Dovuto dettaglio";
	
	//RICONCILIZAZIONI
	public static final String DESCR_GET_ALL_RICONCILIAZIONI 	= "Lista di tutte le Riconciliazioni";
	public static final String DESCR_EXPORT_RICONCILIAZIONI 	= "Export delle Riconciliazioni";
	
	//RICEVUTE TELEMATICHE
	public static final String DESCR_GET_ALL_RT 	= "Lista di tutte le Ricevute Telematiche";
	public static final String DESCR_GET_SINGLE_RT 	= "Visualizza RT dettaglio";
	
	//RENDICONTAZIONI PAGO PA
	public static final String DESCR_GET_ALL_RENDICONTAZIONE 	= "Lista di tutte le Rendicontazioni Pago PA";
	public static final String DESCR_GET_SINGLE_RENDICONTAZIONE 	= "Visualizza Renidicontazione dettaglio";
	
	//TESORERIA
	public static final String DESCR_GET_ALL_GIORNALE_DI_CASSA 			= "Lista di tutti i Giornali di Cassa ";
	public static final String DESCR_GET_SINGLE_GIORNALE_DI_CASSA 		= "Visualizza Giornale di Casas espanso";
	public static final String DESCR_GET_SINGLE_JSON_GIORNALE_DI_CASSA 	= "Visualizza Giornale di Casas espanso in formato json";
	
	//SEGNALZIONE
	public static final String DESCR_GET_ALL_SEGNALAZIONI 		= "Lista di tutti le Segnalazioni";
	public static final String DESCR_GET_SINGLE_SEGNALAZIONE 	= "Visualizza Segnalazione dettaglio";
	public static final String DESCR_CREATE_SEGNALAZIONE 		= "Creazione di un Segnalazione";
	public static final String DESCR_UPDATE_SEGNALAZIONE 		= "Aggiorna dati di un Segnalazione";
	
	//ANAGRAFICA UFFICIO-CAPITOLO-ACCERTAMENTO
	public static final String DESCR_CREATE_ANAGRAFICA 		= "Creazione di una Anagrafica";
	public static final String DESCR_UPDATE_ANAGRAFICA 		= "Aggiorna dati di una Anagrafica";
	public static final String DESCR_DELETE_ANAGRAFICA 		= "Elimina una Anagrafica";
	
	public static final String DESCR_GET_ALL_ANAGRAFICA 		= "Lista di tutte le Anagrafica";
	public static final String DESCR_GET_SINGLE_ANAGRAFICA	 	= "Visualizza Anagrafica dettaglio";
	public static final String DESCR_UPLOAD_ANAGRAFICA 			= "Upload massivo Anagrafica";
	
	//FLUSSO UPLOAD
	public static final String DESCR_UPLOAD_FILE 				= "File Upload";
	public static final String DESCR_UPLOAD_FLUSSO 				= "Flusso di Upload";
	public static final String DESCR_GET_ALL_UPLOAD_FLUSSO 		= "Lista di tutti i Flusso di Upload";
	public static final String DESCR_GET_ALL_EXPORT_FLUSSO 		= "Lista di tutti i Flusso di Export";
	public static final String DESCR_DOWNLOAD_FILE 				= "File Download";
	
	
	//ACCERTAMENTO
	public static final String DESCR_CREATE_ACCERTAMENTO 		= "Creazione di un Accertamento";
	public static final String DESCR_SET_STATUS_ACCERTAMENTO 	= "Aggiorna Stato di un Accertamento";
	
	public static final String DESCR_GET_ALL_ACCERTAMENTO 				= "Lista di tutti gli Accertamento";
	public static final String DESCR_GET_SINGLE_ACCERTAMENTO 			= "Visualizza Accertamento dettaglio";
	public static final String DESCR_GET_CAPITOLI_RT_ACCERTAMENTO 		= "Visualizza Accertamento dettaglio Capitoli di una RT";
	
	public static final String DESCR_GET_ALL_RT_TO_ADD_ACCERTAMENTO 	= "Lista tutti i Pagamenti che è possibile aggiungere ad un Accertamento";
	public static final String DESCR_ADD_RT_TO_ACCERTAMENTO 			= "Lista tutti i Pagamenti che è possibile aggiungere ad un Accertamento";
	public static final String DESCR_GET_ALL_RT_TO_DEL_ACCERTAMENTO 	= "Lista tutti i Pagamenti che è possibile aggiungere ad un Accertamento";
	public static final String DESCR_DEL_RT_FROM_ACCERTAMENTO 			= "Lista tutti i Pagamenti che è possibile aggiungere ad un Accertamento";
	
	//UTILITY
	public static final String DESCR_GET_ALL_CLASSIFICAZIONI			= "Lista di tutte le Classificazioni";
	public static final String DESCR_GET_ALL_STATO_ACCERTAMENTO			= "Lista di tutte gli Stati di un Accertamento";
	
	public static final String DESCR_GET_ALL_UFFICIO					= "Lista di tutte gli Uffci";
	public static final String DESCR_GET_ALL_CAPITOLO					= "Lista di tutte i Caitoli";
	public static final String DESCR_GET_ALL_ACCERTAMENTO_CAPITOLO		= "Lista di tutte gli Accertamenti per Caitoli";
	
	//STATISTICHE
	public static final String DESCR_GET_STATISTICHE_PER_ANNOMESEGIORNO			= "Dati statistici dei pagamenti aggregati per Anno/Mese/Giorno.";
	public static final String DESCR_GET_STATISTICHE_PER_UFFICI					= "Dati statistici dei pagamenti ripartiti in base ai diversi Uffici.";
	public static final String DESCR_GET_STATISTICHE_PER_DOVUTI					= "Dati statistici dei pagamenti ripartiti in base ai diversi Tipi Dovuto.";
	public static final String DESCR_GET_STATISTICHE_PER_CAPITOLO				= "Dati statistici dei pagamenti ripartiti in base ai diversi Capitolo.";
	public static final String DESCR_GET_STATISTICHE_PER_ACCERTAMENTO			= "Dati statistici dei pagamenti ripartiti in base ai diversi Accertamenti.";
	
}