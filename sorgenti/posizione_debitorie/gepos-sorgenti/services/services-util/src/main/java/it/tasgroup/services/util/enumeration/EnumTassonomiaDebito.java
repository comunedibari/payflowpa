package it.tasgroup.services.util.enumeration;

public enum EnumTassonomiaDebito {
	
	CARTELLA_ESATTORIALE("0", "Cartelle esattoriali"),
	DIRITTI("1","Diritti e concessioni"),
	IMPOSTE("2", "Imposte e tasse"),
	TASSE_COMUNALI("3"," IMU, TASI e altre tasse comunali"),
	MOSTRE_MUSEI("4", "Ingressi a mostre e musei"),
	MULTE("5", "Multe e sanzioni amministrative"),
	PREVIDENZA("6", "Previdenza e infortuni"),
	SERVIZI("7", "Servizi erogati dal comune"),
	ALTRI_SERV("8", "Servizi erogati da altri enti"),
	SERVIZI_SCOLASTICI("9", "Servizi scolastici"),
	TASSA_AUTOMOBILISTICA("10", "Tassa automobilistica"),
	TICKET_SANITARIO("11", "Ticket   prestazioni sanitarie"),
	TRASPORTI("12", "Trasporti, mobilità e parcheggi");
	
	private String chiave;
	private String descrizione;
	
	private EnumTassonomiaDebito(String chiave,String descrizione){
		this.chiave = chiave;
		this.descrizione = descrizione;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

   public static EnumTassonomiaDebito getByKey(String chiave) {
		
	   EnumTassonomiaDebito desiredItem = null; // Default
		
		for (EnumTassonomiaDebito item : EnumTassonomiaDebito.values()) {
			
			if (item.getChiave().equals(chiave)) {
				
				desiredItem = item;
				
				break;
				
				}
			}
		
		return desiredItem;
	}
	
}
