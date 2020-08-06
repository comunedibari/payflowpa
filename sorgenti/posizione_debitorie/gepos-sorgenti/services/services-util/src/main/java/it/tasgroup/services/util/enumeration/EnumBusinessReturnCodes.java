package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.EnumErrorMessage;
import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;


public enum EnumBusinessReturnCodes implements EnumErrorMessage {

	OK("OK000000", "OK", "", EnumSeverityLevel.INFO),
	
	OK_AUTORIZZATO("OK000001", "AUTORIZZATO", "", EnumSeverityLevel.INFO),
	OK_ANNULLATO("OK000002", "ANNULLATO", "", EnumSeverityLevel.INFO),
	OK_NOTIFICA_ACCETTATA("OK000003", "NOTIFICA_ACCETTATA", "", EnumSeverityLevel.INFO),
	OK_STORNO_ACCETTATO("OK000004", "STORNO_ACCETTATO", "", EnumSeverityLevel.INFO),
	
	KO_NON_AUTORIZZATO("KO000005", "NON_AUTORIZZATO", "", EnumSeverityLevel.ERROR),
	KO_NON_ANNULLATO("KO000006", "NON_ANNULLATO", "", EnumSeverityLevel.ERROR),
	KO_NOTIFICA_NON_ACCETTATA("KO000003", "NOTIFICA_NON_ACCETTATA", "", EnumSeverityLevel.ERROR),
	KO_STORNO_NON_ACCETTATO("KO000004", "STORNO_NON_ACCETTATO", "", EnumSeverityLevel.ERROR),
	
 	KO_GENERICO("KO000001", "Errore generico", "", EnumSeverityLevel.ERROR), 
 	KO_ERROREDB("KO000002", "Errore database", "", EnumSeverityLevel.ERROR), 
 	
 	E0000001("E0000001", "Identificativo Mittente sconosciuto", "", EnumSeverityLevel.ERROR), 
 	E0000002("E0000002", "Identificativo Sistema Mittente sconociuto", "", EnumSeverityLevel.ERROR), 
 	E0000003("E0000003", "Identificativo Destinatario sconosciuto", "",	EnumSeverityLevel.ERROR), 
 	E0000004("E0000004", "Identificativo Sistema Destinatario sconosciuto", "", EnumSeverityLevel.ERROR), 
 	E0000005("E0000005", "Canale di pagamento sconociuto", "", EnumSeverityLevel.ERROR), 
 	E0000006("E0000006", "Nessun oggetto legato ad una transazione di pagamento", "",EnumSeverityLevel.ERROR), 
 	E0000007("E0000007", "PSP non trovato", "",EnumSeverityLevel.ERROR),
 	N0000001("N0000001", "Stato non congruente", "", EnumSeverityLevel.ERROR), 
 	A0000001("A0000001", "Posizione non presente", "", EnumSeverityLevel.ERROR), 
 	A0000002("A0000002", "Pendenza gia' pagata (stato = eseguito)", "",EnumSeverityLevel.ERROR), 
 	A0000003("A0000003", "Pendenza non pagabile", "", EnumSeverityLevel.ERROR), 
 	A0000004("A0000004", "Pendenza scaduta", "",EnumSeverityLevel.ERROR), 
 	A0000005("A0000005", "Pendenza con pagamento in corso", "", EnumSeverityLevel.ERROR), 
 	A0000006("A0000006", "Pendenza con pagamento disposto / non eseguito (stato = pagato sbf)", "", EnumSeverityLevel.ERROR), 
 	A0000007("A0000007", "Pendenza gia' pagata", "",	EnumSeverityLevel.ERROR), 
 	A0000008("A0000008", "Identificativo associato ad un documento annullato", "", EnumSeverityLevel.ERROR), 
 	A0000009("A0000009", "Condizioni di pagamento miste non ammesse per la stessa pendenza", "", EnumSeverityLevel.ERROR), 
 	A0000010("A0000010", "Transazione non trovata", "",	EnumSeverityLevel.ERROR), 
 	A0000011("A0000011", "Transazione in stato annullato", "", EnumSeverityLevel.ERROR), 
 	A0000012("A0000012", "Transazione in stato eseguito", "", EnumSeverityLevel.ERROR), 
 	A0000013("A0000013", "Transazione in errore", "", EnumSeverityLevel.ERROR), 
 	A0000014("A0000014", "Codice Sessione errato", "", EnumSeverityLevel.ERROR), 
 	A0000015("A0000015", "Codice Autorizzazione errato", "", EnumSeverityLevel.ERROR), 
 	A0000016("A0000016", "Transazione in stato eseguito sbf", "", EnumSeverityLevel.ERROR), 
 	A0000017("A0000017", "Transazione in stato in corso", "",EnumSeverityLevel.ERROR), 
 	A0000018("A0000018", "Stato transazione non coerente con la richiesta", "", EnumSeverityLevel.ERROR), 
 	A0000019("A0000019", "Pendenze non coerenti con la condizione passata", "", EnumSeverityLevel.ERROR), 
 	A0000020("A0000020", "Richiesta contenente importi non coerenti con quelli presenti su DB", "", EnumSeverityLevel.ERROR), 
 	A0000021("A0000021", "Risultato non univoco", "", EnumSeverityLevel.ERROR), 
 	A0000022("A0000022", "Transazione duplicata", "", EnumSeverityLevel.ERROR),
 	A0000023("A0000023", "Identificativo della transazione non valorizzato", "", EnumSeverityLevel.ERROR),
 	A0000024("A0000024", "Trovate piu' condizioni pagabili contemporaneamente per la stessa posizione", "", EnumSeverityLevel.ERROR),
 	A0000025("A0000025", "Il tipo allegato in notifica pagamento puo' assumere solo il valore RICEVUTA", "", EnumSeverityLevel.ERROR),
 	A0000026("A0000026", "Il contenuto dell' allegato in notifica pagamento deve essere un PDF.", "", EnumSeverityLevel.ERROR),
 	A0000027("A0000027", "Richiesta non coerente con i pagamenti trovati", "", EnumSeverityLevel.ERROR),
 	A0000028("A0000028", "Nessun ente censito corrisponde al codice fiscale presente nella richiesta", "", EnumSeverityLevel.ERROR),
 	A0000029("A0000029", "Codice Pagatore non corrispondente al destinatario del debito", "", EnumSeverityLevel.ERROR),
 	A0000030("A0000030", "Ricevuta non valida ai fini fiscali", "", EnumSeverityLevel.ERROR),
 	B0000001("B0000001", "Multa non presente", "",	EnumSeverityLevel.ERROR), 
 	B0000002("B0000002", "Multa non pagabile", "", EnumSeverityLevel.ERROR), 
 	B0000003("B0000003", "Multa scaduta", "",EnumSeverityLevel.ERROR), 
 	B0000004("B0000004", "Importo multa errato", "", EnumSeverityLevel.ERROR), 
 	B0000005("B0000005", "Data multa errata", "",EnumSeverityLevel.ERROR), 
 	W0000001("W0000001", "Dati della pendenza diversi da quelli noti al contribuente", "", EnumSeverityLevel.ERROR), 
 	W0000002("W0000002", "Multa gia' pagata", "", EnumSeverityLevel.ERROR),
 	W0000003("W0000003", "Valore di input non supportato", "", EnumSeverityLevel.ERROR);
	

	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private EnumSeverityLevel severityLevel;

	private EnumBusinessReturnCodes(String chiave, String descrizione, String chiaveBundle, EnumSeverityLevel severityLevel) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.severityLevel = severityLevel;
	}

	@Override
	public String getChiave() {
		return chiave;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}

	@Override
	public EnumSeverityLevel getSeverityLevel() {
		return severityLevel;
	}

	@Override
	public void setSeverityLevel(EnumSeverityLevel severityLevel) {
		this.severityLevel = severityLevel;
	}

	public static EnumBusinessReturnCodes getByKey(String chiave) {
		EnumBusinessReturnCodes desiredItem = null; // Default
		for (EnumBusinessReturnCodes item : EnumBusinessReturnCodes.values()) {
			if (item.getChiave().equals(chiave)) {
				desiredItem = item;
				break;
			}
		}
		return desiredItem;
	}
	
	public boolean isError(){
		
		return getSeverityLevel().equals(EnumSeverityLevel.ERROR);
	}
}
