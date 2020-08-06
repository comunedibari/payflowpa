
package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;


public enum EnumTipoAllegato implements MessageDescription{

	// Warning: il campo descrizione di questo enum è usato come chiave per il DB.
	
    DOCUMENTO("DOCUMENTO", "Documento", ""),
    RICEVUTA("RICEVUTA", "Ricevuta", ""),
    QUIETANZA("QUIETANZA", "Quietanza", ""),
	NDC("NDC", "NdC", "");
	
    private String chiave;
	private String descrizione;
	private String chiaveBundle;
    
    private EnumTipoAllegato(String chiave, String descrizione, String chiaveBundle) {
    	
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

}
