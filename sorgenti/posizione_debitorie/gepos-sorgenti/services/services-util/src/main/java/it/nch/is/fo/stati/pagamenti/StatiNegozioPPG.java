package it.nch.is.fo.stati.pagamenti;
/**
 * stati negozio (op_stato_op)
 */
public enum StatiNegozioPPG {
    ESITO_STATO_0(0),// descrizione : "Stato iniziale della transazione";
	ESITO_STATO_1(1), // descrizione :  "Transazione pronta per la conferma lato client";
	ESITO_STATO_2(2), // descrizione :  "Transazione pronta per l'esecuzione";
	ESITO_STATO_3(3), // descrizione :  "Transazione in attesa di richiesta esito";
	ESITO_STATO_4(4), // descrizione :  "Transazione di prenotazione confermata";
	ESITO_STATO_5(5), // descrizione :  "Transazione di addebito confermata";
	ESITO_STATO_6(6), // descrizione :  "Transazione di annullamento prenotazione  confermata";
	ESITO_STATO_7(7), // descrizione :  "Tansazione di annullamento prenotazione conclusa";
	ESITO_STATO_8(8), // descrizione :  "Transazione di addebito concluso";
	ESITO_STATO_9(9), // descrizione :  "Transazione di addebito annullata";
	ESITO_STATO_10(10),// descrizione :   "Transazione abortita per firma alterata";
	ESITO_STATO_11(11),// descrizione :  "Transazione abortita per stato incosistente";
	ESITO_STATO_12(12),// descrizione :  "Transazione abortita per IP-address non coincidente con quello comunicato dal negozio";
	ESITO_STATO_13(13),// descrizione :  "Transazione abortita per timeout scaduto";
	ESITO_STATO_14(14),// descrizione :  "Transazione abortita per troppi tentativi errati da parte del cliente";
	ESITO_STATO_15(15),// descrizione :  "Transazione abortita per errori rilevati nell'innesco della transazione ad host";
	ESITO_STATO_16(16),// descrizione :  "Transazione abortita per un errore in fase di notifica durante la funzione di autorizzazione o verifica presso l'ente emittente della carta";
	ESITO_STATO_17(17),// descrizione :  "Transazione abortita mancati ricevimento di risposta in fase di notifica durante la funzione di autorizzazione o verifica presso l'ente emittente della carta";
	ESITO_STATO_18(18),// descrizione :  "Transazione in attesa dell'esito della funzione di autorizzazione o verifica presso l'ente emittente della carta";
	ESITO_STATO_19(19),// descrizione :  "Transazione abortita per errori rilevati durante la schedulazione della funzione di autorizzazione o verifica";
	ESITO_STATO_20(20),// descrizione :  "Transazione dubbia";
	ESITO_STATO_21(21),// descrizione :  "Transazione annullata";
	ESITO_STATO_30(30),// descrizione :  "Chiusura batch per mancata conferma del cliente";
	ESITO_STATO_31(31),// descrizione :  "Chiusura batch per mancata richiesta esecuzione";
	ESITO_STATO_32(32),// descrizione :  "Chiusura batch per mancata richiesta esito";
	ESITO_STATO_33(33),// descrizione :  "Chiusura batch per mancata conferma prenotazione";
	ESITO_STATO_34(34), // descrizione : "Chiusura batch per mancata conferma addebito";
	ESITO_STATO_39(39),// descrizione :  "Eseguito annullamento addebito via batch";
	ESITO_STATO_40(40),// descrizione :  "Chiusura batch per errore generico";
	ESITO_STATO_50(50);// descrizione :  "Chiusura generica batch";
	
	private final int stato;
	
	StatiNegozioPPG(int stato) {
	    this.stato = new Integer(stato);
	}
	
	public Integer getStato() {
		return stato;
	}
}
