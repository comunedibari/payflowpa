package it.tasgroup.idp.gateway.enums;


public enum EnumTipoEventiNDP implements MessageDescription{
	
	nodoInviaCarrelloRPT("nodoInviaCarrelloRPT", "Invia Carrello RPT",""), 
	nodoChiediQuadraturaPA("nodoChiediQuadraturaPA", "Chiedi Quadratura PA",""), 
	nodoInviaRPT("nodoInviaRPT", "Invia RPT",""), 
	nodoInviaRichiestaStorno("nodoInviaRichiestaStorno", "Invia Richiesta Storno",""), 
	nodoChiediElencoQuadraturePA("nodoChiediElencoQuadraturePA", "Chiedi Elenco Quadrature PA",""), 
	nodoChiediListaPendentiRPT("nodoChiediListaPendentiRPT", "Chiedi Lista Pendenti RPT",""), 
	nodoChiediStatoRPT("nodoChiediStatoRPT", "Chiedi Stato RPT",""), 
	nodoChiediFlussoRendicontazione("nodoChiediFlussoRendicontazione", "Chiedi Flusso Rendicontazione",""), 
	nodoChiediCopiaRT("nodoChiediCopiaRT", "Chiedi Copia RT",""), 
	nodoChiediElencoFlussiRendicontazione("nodoChiediElencoFlussiRendicontazione", "Chiedi Elenco Flussi Rendicontazione",""), 
	nodoChiediInformativaPSP("nodoChiediInformativaPSP", "Chiedi Informativa PSP",""), 
	paaInviaEsitoStorno("paaInviaEsitoStorno", "paa Invia Esito Storno",""), 
	paaInviaRT("paaInviaRT", "paa Invia RT",""),
	paaAttivaRPT("paaAttivaRPT", "paa Attiva RPT",""),
	nodoInviaRispostaRevoca("nodoInviaRispostaRevoca", "Invia Risposta Revoca ",""),
	paaVerificaRPT("paaVerificaRPT", "paa Verifica RPT ","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	private EnumTipoEventiNDP(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
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
	
	public static EnumTipoEventiNDP getByKey(String chiave) {
		
		EnumTipoEventiNDP desiredItem = null;  
		
		for (EnumTipoEventiNDP item : EnumTipoEventiNDP.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}
}
