package it.tasgroup.services.util.enumeration;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;

public enum EnumStatoRPT {

	RPT_RICEVUTA_NODO("RPT ricevuta dal Nodo", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.IN_CORSO),
			
	RPT_RIFIUTATA_NODO("RPT rifiutata dal Nodo per sintassi o semantica errata", 
			StatiPagamentiIris.IN_ERRORE, StatiPagamentiIris.IN_ERRORE),
			
	RPT_ACCETTATA_NODO("RPT accettata dal Nodo come valida", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.IN_CORSO),
			
	RPT_RIFIUTATA_PSP("	RPT rifiutata dall'Intermediario PSP per sintassi o semantica errata", 
			StatiPagamentiIris.IN_ERRORE, StatiPagamentiIris.IN_ERRORE),
			
	RPT_ERRORE_INVIO_A_PSP("RPT inviata all'Intermediario PSP - indisponibilita' del ricevente", 
			StatiPagamentiIris.IN_ERRORE, StatiPagamentiIris.IN_CORSO),
			
	RPT_INVIATA_A_PSP("RPT inviata all'Intermediario PSP - azione in attesa di risposta", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.IN_CORSO),
			
	RPT_ACCETTATA_PSP("RPT ricevuta ed accettata dall'Intermediario PSP come valida", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF),
			
	RPT_DECORSI_TERMINI("RPT ha superato il periodo di decorrenza termini nel Nodo", 
			StatiPagamentiIris.ANNULLATO, StatiPagamentiIris.ANNULLATO), 
	
	RPT_ANNULLATA("RPT annullata dall'utente", 
			StatiPagamentiIris.ANNULLATO_DA_OPERATORE, StatiPagamentiIris.ANNULLATO_DA_OPERATORE), 
	
	RPT_PARCHEGGIATA_NODO("RPT parcheggiata sul Nodo", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.IN_CORSO),

	RT_RICEVUTA_NODO("RT ricevuta dal Nodo", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF),
			
	RT_RIFIUTATA_NODO("RT rifiutata dal Nodo per sintassi o semantica errata", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF),
			
	RT_ACCETTATA_NODO("RT accettata dal Nodo come valida ed in corso di invio all'Intermediario PA", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF),
			
	RT_ACCETTATA_PA("RT ricevuta dall'Intermediario PA ed accettata", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF),
			
	RT_RIFIUTATA_PA("RT ricevuta dall'Intermediario PA e rifiutata", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF),
			
	RT_ESITO_SCONOSCIUTO_PA("Esito dell'accettazione RT dell'Intermediario PA non interpretabile", 
			StatiPagamentiIris.IN_CORSO, StatiPagamentiIris.ESEGUITO_SBF);

	private String descrizione;
	private StatiPagamentiIris statoPagamentoDiretto;
	private StatiPagamentiIris statoPagamentoDifferito;

	private EnumStatoRPT(String descrizione, StatiPagamentiIris statoPagamentoDiretto, StatiPagamentiIris statoPagamentoDifferito) {
		this.descrizione = descrizione;
		this.statoPagamentoDiretto = statoPagamentoDiretto;
		this.statoPagamentoDifferito = statoPagamentoDifferito;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public StatiPagamentiIris getStatoPagamentoDiretto() {
		return statoPagamentoDiretto;
	}

	public StatiPagamentiIris getStatoPagamentoDifferito() {
		return statoPagamentoDifferito;
	}
	
	public StatiPagamentiIris getStatoPagamentoIris(boolean isDiretto) {
		return isDiretto ? statoPagamentoDiretto : statoPagamentoDifferito;
	}
	

}
