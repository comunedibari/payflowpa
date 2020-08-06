package it.tasgroup.services.util.enumeration;

public enum EnumTypeExportMassivo {
	
	RT("RT","RT","Ricevute Telematiche"),
	QT("QT", "QT", "Quietanze di pagamento"),
	FR("FR","RT","Flussi esiti NDP");

	private String chiave;
	private String behaviour;
	private String descrizione;

	private EnumTypeExportMassivo(String chiave, String behaviour, String descrizione) {
		this.chiave = chiave;
		this.behaviour = behaviour;
		this.descrizione = descrizione;
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

	public String getBehaviour() {
		return behaviour;
	}

	public void setBehaviour(String behaviour) {
		this.behaviour = behaviour;
	}
	
	public static EnumTypeExportMassivo getByChiave(String chiave) {
		
		EnumTypeExportMassivo ret = QT; // Default             
		
		for (EnumTypeExportMassivo item : EnumTypeExportMassivo.values()) {                 
			if (item.getChiave().equalsIgnoreCase(chiave)) {                     
				ret = item;                     
				break;                 
				}             
			}   
		
		return ret; 
	}
	
}
