package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoOperazione implements MessageDescription{
	
	BONIFICO("BONIFICO", "Bonifico", "", "048"),	
	GIROCONTO("GIROCONTO", "Giroconto", "", "436");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private String prefix;

	private EnumTipoOperazione(String chiave, String descrizione, String chiaveBundle, String prefix) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.prefix = prefix;
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

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public static EnumTipoOperazione getByCRO(String cro){
		
		if (cro != null) {
			
			if (cro.startsWith(BONIFICO.prefix))
				return BONIFICO;
			
			if (cro.startsWith(GIROCONTO.prefix))
				return GIROCONTO;
		}
		
		return null;
		
	}
	
	

}
