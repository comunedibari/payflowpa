package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoRiversamento implements MessageDescription{
	
	INCORSO("IN CORSO","Riversamento In Corso",""),
	PENDING("PENDING","Riversamento Pendente",""),
	INERRORE("IN ERRORE","Riversamento In Errore",""),
	RIVERSATO("ESEGUITO","Riversamento Eseguito",""),
	GIA_RIVERSATO("GIA' RIVERSATO","Gia' riversato",""),
	DA_RIVERSARE("NON ESEGUITO","Riversamento in Attesa",""),
	NON_RIVERSATO("NON RIVERSATO","Non Riversato","");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoRiversamento(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static EnumStatoRiversamento getByKey(String chiave) {
		
		EnumStatoRiversamento desiredItem = null; // Default   
		
		for (EnumStatoRiversamento item : EnumStatoRiversamento.values()) {
			
			if (item.getChiave().equals(chiave)) {  
				
				desiredItem = item;   
				
				break;            
				
				}             
			}        
		
		return desiredItem; 
	}
	

}
