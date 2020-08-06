package it.tasgroup.idp.billerservices.api;

public enum EnumReturnValues {
	
	OK("OK000000","Elaborato correttamente","OK000000"),
	KO("KO000000","Elaborato con errori","KO000000"), 
	SENDER_ID_INESISTENTE("BS000001","Creditore non censito in anagrafica","errors.pendenza.decode.idente"),
	SENDER_ID_NON_UNIVOCO("BS000002","Creditore non univoco in anagrafica",""),
	SENDER_SYS_INESISTENTE("BS000003","Sistema Informatico Locale del creditore (SenderSys) non censito","errors.messaggio.silNonAvailable"),
	SENDER_ID_DISABILITATO("BS000004","Creditore non abilitato","errors.pendenza.decode.idente"),
	SENDER_SYS_DISABILITATO("BS000005","Sistema Informatico Locale del creditore (SenderSys) non abilitato","errors.messaggio.silNonAvailable"),
	TIPO_DEBITO_SCONOSCIUTO("BS000006","Tipo Debito non censito in anagrafica","errors.pendenza.decode.tributoid"),
	TIPO_DEBITO_DISABILITATO("BS00007","Tipo Debito non abilitato","errors.pendenza.decode.tributoid"),
	TIPO_DEBITO_NON_COERENTE_SENDER_SYS("BS00008","Tipo Debito non censito per il Sistema Informatico Locale del creditore (SenderSys) ","messaggio.silNonAvailable"),
	MESSAGGIO_DUPLICATO("BS000009","Messaggio duplicato","errors.messaggio.chiaveDuplicata"),
	PLUGIN_SCONOSCIUTO("BS000010","Tipo Messaggio / Formato non gestito",""),
	ERRORE_SCHEMA_FILE("BS000011","Messaggio non formato correttamente rispetto allo schema previsto, vedere descrizione specifica",""),
	ERRORE_GENERICO("BS000012","Errore generico in fase di accesso al database","db.exception"),
	SENDER_ID_NON_PRESENTE("BS000013","Identificativo Creditore non presente nel messaggio","errors.pendenza.decode.idente"),
	TIPO_DEBITO_NON_PRESENTE("BS000014","Tipo Debito non presente nel messaggio","errors.pendenza.decode.tributoid"),
	SENDER_ID_NON_VALIDO("BS000015","Identificativo del Creditore non valido","errors.pendenza.decode.idente"),
	SENDER_SYS_NON_VALIDO("BS000016","Sistema Informatico Locale del creditore (SenderSys) non valido","errors.messaggio.silNonAvailable"), 
	SENDER_ID_NON_OMOGENEO("BS000017","Il Creditore deve essere omogeneo nel messaggio",""),
	TIPO_DEBITO_NON_OMOGENEO("BS000018","Il Tipo Debito deve essere omogeneo nel messaggio",""),
	RAGGIUNTO_MAX_NUM_ERRORI_DIAGNOSI("BS000019","Raggiunto il massimo numero di errori di diagnosi",""),
	ERRORE_VALIDAZIONE_SEMANTICA("BS000020","Errore di validazione semantica, vedere descrizione specifica",""),   
	PENDENZA_DUPLICATA("BS000021","Pendenza duplicata","errors.pendenza.duplicated"),
	CONDIZIONE_PAGAMENTO_DUPLICATA("BS000022","Condizione Pagamento Duplicata","errors.condizionepagamento.idpagamento.alredyexists"),
	PENDENZA_NON_TROVATA("BS000023","Pendenza non trovata","errors.pendenza.update.doesntexists"),
	CONDIZIONE_NON_TROVATA("BS000024","Condizione Pagamento non trovata","errors.condizionepagamento.update.doesntexists"),
	PENDENZA_NON_CANCELLABILE("BS000025","Pendenza non cancellabile","errors.xml.condizionePagamento.unmodifiable"),
	CONDIZIONE_NON_CANCELLABILE("BS000026","Condizione pagamento non cancellabile","errors.xml.condizionePagamento.unmodifiable"),
	PENDENZA_NON_MODIFICABILE("BS000027","Pendenza non modificabile","errors.xml.condizionePagamento.unmodifiable"),
	CONDIZIONE_NON_MODIFICABILE("BS000028","Condizione Pagamento non modificabile","errors.xml.condizionePagamento.unmodifiable"),
	NOTIFICA_NON_PRESENTE("BS000029","Messaggio di notifica non presente",""),
	NOTIFICA_NON_UNIVOCA("BS000030","Messaggio di notifica non univoco",""),
	CONDIZIONE_PAG_ESEGUITO("BS000031","La Condizione Pagamento risulta pagata",""),
	CONDIZIONE_PAG_IN_CORSO("BS000032","La Condizione Pagamento ha un pagamento precedente in corso",""),
	CONDIZIONE_PAG_ESEGUITO_SBF("BS000033","La Condizione Pagamento ha un pagamento precedente in stato Eseguito Salvo Buon Fine (da cconfermare)",""),
	ID_DEBITO_INCOMPATIBILE_CBILL("BS000034","Id Debito non compatibile con il sistema CBILL, deve essere di lunghezza massima 18 caratteri",""),
	ERRORE_IN_COSTRUZIONE_PLUGIN("BS000035","Errore durante la costruzione del plugin",""),
	MARCA_DA_BOLLO_DIGITALE_NON_SPECIFICATA_CORRETTAMENTE("BS000040","Marca da bollo non specificata correttamente",""),
	INTESTATARIO_DUPLICATO("BS000045","Intestatario gia' censito",""),
	ENTE_DUPLICATO("BS000050","Ente gia' censito",""),
	PARAM_GEN_IUV_ERRATI("BS000055","Parametri di configurazione IUV errati",""),
	TIPO_OPERAZIONE_NON_VALIDA("BS000060","Tipo Operazione non Valida",""),
	INTESTATARIO_INESISTENTE("BS000065","Intestatario inesistente",""),
	ENTE_INESISTENTE("BS000070","Ente inesistente",""),
	SIL_INESISTENTE("BS000075","Sil inesistente",""),
	TRIBUTO_INESISTENTE("BS000080","Tributo inesistente",""),
	TRIBUTO_DUPLICATO("BS000080","Tributo duplicato",""),
	PARAMETRI_ERRATI("BS000085","Parametri errati","")
	
	;


	String key;
	String bundleKey;  // Bundle key per retroccompatibilità. Mappatura dei messaggi di errore con i codici stile RFC 
	String description;
	
	private EnumReturnValues(String key, String description, String bundleKey) {
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
		for( EnumReturnValues value:EnumReturnValues.values()) {
			System.out.println(value.getKey()+","+value.getDescription()+","+value.toString() );
		}
	}
	
	
}
