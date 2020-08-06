package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoOperazioneMessaggi implements MessageDescription{
	
	UPDATE_STATUS("UpdateStatus", "Update Status", "common.label.aggiornamentoStato"),	
	UPDATE_MASSIVO("UpdateMassivo", "Massive Update", "common.label.aggiornamentoMassivo"),
	REPLACE("Replace", "Replace", "common.label.replace"),
	DELETE("Delete", "Delete", "common.label.delete"),
	INSERT("Insert", "Insert", "common.label.inserimento");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoOperazioneMessaggi(String chiave, String descrizione, String chiaveBundle) {
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}
	
	public static EnumTipoOperazioneMessaggi getByKey(String chiave) {
		
		for (EnumTipoOperazioneMessaggi item : EnumTipoOperazioneMessaggi.values()) {
            
			if (item.getChiave().equals(chiave))
				
                return item;
                
        }
		
        return null;
    }

}
