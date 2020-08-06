package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCfgNotificaPagamento implements MessageDescription {

	NOTIFICATO 			("Y", "Notificato", "backoffice.tributi.searchCategoriaTributo.NOTIFICAPAGAMENTO.SI"),
	NON_NOTIFICATO		("N", "Non Notificato", "backoffice.tributi.searchCategoriaTributo.NOTIFICAPAGAMENTO.NO");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCfgNotificaPagamento(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumCfgNotificaPagamento getByKey(String chiave) {
		
		EnumCfgNotificaPagamento desiredItem = null; // Default   
		
		for (EnumCfgNotificaPagamento item : EnumCfgNotificaPagamento.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}  
		
		return desiredItem; 
	}

}
