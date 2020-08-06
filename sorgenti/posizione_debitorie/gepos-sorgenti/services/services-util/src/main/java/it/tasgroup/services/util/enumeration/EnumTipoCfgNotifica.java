package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoCfgNotifica implements MessageDescription {

	ESEGUITO 		("ESEGUITO", "Eseguito", "backoffice.tributi.NOTIFICA.ESEGUITO"),
	INCASSO		    ("INCASSO", "Incasso", "backoffice.tributi.NOTIFICA.INCASSATO"),
	REGOLATO		("REGOLATO", "Regolato", "backoffice.tributi.NOTIFICA.REGOLATO");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoCfgNotifica(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumTipoCfgNotifica getByKey(String chiave) {
		
		EnumTipoCfgNotifica desiredItem = null; // Default   
		
		for (EnumTipoCfgNotifica item : EnumTipoCfgNotifica.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}

}
