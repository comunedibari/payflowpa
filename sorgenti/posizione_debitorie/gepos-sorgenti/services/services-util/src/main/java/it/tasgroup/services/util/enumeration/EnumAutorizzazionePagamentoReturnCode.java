package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumAutorizzazionePagamentoReturnCode implements MessageDescription {


	
	PAA_PAGAMENTO_SCONOSCIUTO 			("PAA_PAGAMENTO_SCONOSCIUTO", "Pagamento in attesa risulta sconosciuto all Ente Creditore.", ""),
	PAA_PAGAMENTO_DUPLICATO		("PAA_PAGAMENTO_DUPLICATO", "Non Pagamento in attesa risulta duplicato all Ente Creditore.", ""),
	PAA_PAGAMENTO_ANNULLATO		("PAA_PAGAMENTO_ANNULLATO", "Pagamento in attesa risulta non notificato all Ente Creditore.", ""),
	PAA_PAGAMENTO_IN_CORSO		("PAA_PAGAMENTO_IN_CORSO", "Pagamento in attesa risulta in corso all Ente Creditore.", ""),
	PAA_PAGAMENTO_SCADUTO		("PAA_PAGAMENTO_SCADUTO", "Non Notificato", "Pagamento in attesa risulta scaduto all Ente Creditore."),
	PAA_ATTIVA_RPT_IMPORTO_NON_VALIDO		("PAA_ATTIVA_RPT_IMPORTO_NON_VALIDO", "L importo del pagamento in attesa non e congruente con il dato indicato dal PSP.", "");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumAutorizzazionePagamentoReturnCode(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public static EnumAutorizzazionePagamentoReturnCode getByKey(String chiave) {
		
		EnumAutorizzazionePagamentoReturnCode desiredItem = null; // Default   
		
		for (EnumAutorizzazionePagamentoReturnCode item : EnumAutorizzazionePagamentoReturnCode.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}

}
