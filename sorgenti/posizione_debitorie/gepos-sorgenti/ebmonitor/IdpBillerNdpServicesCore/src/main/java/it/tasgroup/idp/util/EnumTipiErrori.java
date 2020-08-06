package it.tasgroup.idp.util;

public enum EnumTipiErrori {

	
	
	OK("OK","Elaborato correttamente","OK"),
	KO("KO","Elaborato con errori","KO"), 
	TIPO_TRIBUTO_SENZA_RANGE("01","Il tipo tributo non ha un range di generazione definito",""),
	SENDER_ID_INESISTENTE("02","EC non esiste",""),
	TIPO_DEBITO_SCONOSCIUTO("03","Il tipo tributo non esiste",""),
	ERRORE_GENERICO("04","Errore generico in fase di accesso al database",""),
	ERRORE_GENERICO_ELABORAZIONE("05","Errore generazione IUV",""),
	ERRORE_SCHEMA_FILE("06","Richiesta non valida",""),
	ERRORE_DIMENSIONE_LOTTO("07","Superato il numero di codici massimo consentito ",""),   
	ID_ENTE_SCONOSCIUTO("08","ID Ente non trovato",""),
	ERRORE_GENERAZIONE_BOLLETTINO("09","Errore webservice bollettino_ws generazione bollettini",""),
	ERRORE_CONDIZIONE("10","Richiesta non valida: condizione di pagamento non trovata",""),
	ERRORE_INTESTATARIO("11","Intestatario non trovato: identificativo dominio errato",""),
	AUX_DIGIT_NON_VALIDO("12","Il tipo tributo non ha un Aux Digit valido",""),
	COD_STAZ_PA_ASSENTE("13","Il tipo tributo non ha un Codice Stazione PA definito",""),
	IUV_NON_NUMERICO("14","Il codice IUV non è un valore numerico",""),
	IUV_NON_CONFORME_ERRORE_GENERICO("15","Il codice IUV non è conforme: errore generico",""),
	IUV_NON_CONFORME_NO_CONFIGURAZIONE("16","Il codice IUV non è conforme alla configurazione presente sul sistema: aux digit non configurato",""),
	IUV_NON_CONFORME_LUNGHEZZA_ERRATA_15("17","Il codice IUV non è conforme alla configurazione presente sul sistema: lunghezza errata. Lo IUV deve essere di 15 cifre",""),
	IUV_NON_CONFORME_LUNGHEZZA_ERRATA_17("18","Il codice IUV non è conforme alla configurazione presente sul sistema: lunghezza errata. Lo IUV deve essere di 17 cifre",""),
	IUV_NON_CONFORME_CHECK_DIGIT_KO("19","Il codice IUV non è conforme alla configurazione presente sul sistema: check digit errato","")
	;


	String key;
	String bundleKey;  // Bundle key per retroccompatibilitï¿½. Mappatura dei messaggi di errore con i codici stile RFC 
	String description;
	
	private EnumTipiErrori(String key, String description, String bundleKey) {
		this.key=key;
		this.bundleKey=bundleKey;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public String getBundleKey() {
		return bundleKey;
	}

	public String getDescription() {
		return description;
	}
	
	public static void main(String[] args) {
		// Genera documentazione errori su console.
		for (EnumTipiErrori value : EnumTipiErrori.values()) {
			System.out.println(value.getKey() + "," + value.getDescription() + "," + value.toString());
		}
	}
	
}
