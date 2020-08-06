package it.tasgroup.idp.generazioneiuv;

public enum EnumTipiErroriGenerazioneIUV {

	
	
	OK("OK","Elaborato correttamente","OK"),
	KO("KO","Elaborato con errori","KO"), 
	TIPO_TRIBUTO_SENZA_RANGE("01","Il tipo tributo non ha un range di generazione definito",""),
	SENDER_ID_INESISTENTE("02","EC non esiste",""),
	TIPO_DEBITO_SCONOSCIUTO("03","Il tipo tributo non esiste",""),
	ERRORE_GENERICO("04","Errore generico in fase di accesso al database",""),
	ERRORE_GENERICO_ELABORAZIONE("05","Errore generazione IUV",""),
	ERRORE_SCHEMA_FILE("06","Richiesta non valida",""),
	ERRORE_DIMENSIONE_LOTTO("07","Superato il numero di codici massimo consentito ",""),   
	
	;


	String key;
	String bundleKey;  // Bundle key per retroccompatibilità. Mappatura dei messaggi di errore con i codici stile RFC 
	String description;
	
	private EnumTipiErroriGenerazioneIUV(String key, String description, String bundleKey) {
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
		for( EnumTipiErroriGenerazioneIUV value:EnumTipiErroriGenerazioneIUV.values()) {
			System.out.println(value.getKey()+","+value.getDescription()+","+value.toString() );
		}
	}
	
}
