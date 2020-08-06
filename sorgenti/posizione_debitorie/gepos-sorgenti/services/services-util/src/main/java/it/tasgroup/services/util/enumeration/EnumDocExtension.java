package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;


public enum EnumDocExtension implements MessageDescription {
	
	PDF ("PDF", "PDF", "PDF", (short) 0),
	TXT ("TXT", "TXT", "TXT", (short) 1),
	XLS ("XLS", "XLS", "XLS", (short) 2),
	DOCX ("DOCX", "DOCX", "DOCX", (short) 3);
	

	private String chiave;
	
	private String descrizione;
	
	private String chiaveBundle;
	
	private short id;

	EnumDocExtension(String chiave, String descrizione, String chiaveBundle, short id) {
		
		this.chiave = chiave;
		
		this.descrizione = descrizione;
		
		this.chiaveBundle = chiaveBundle;
		
		this.id = id;
		
	}
	
	private EnumDocExtension(String chiave) {
		this.chiave = chiave;
	}
	
	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public short getId() {
		return id;
	}

	public void setId(short id) {
		this.id = id;
	}
	
	public static EnumDocExtension getById(short id) {
		
		for (EnumDocExtension item : EnumDocExtension.values()) {   
			
			if (item.getId() == id)        
				
				return item;              				
		}
		
		return null; 
	}
	
}
