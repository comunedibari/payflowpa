package it.tasgroup.services.util.enumeration;

public enum EnumStatoEventi {
	
	INATTESA("01.IN_ATTESA"), 
	INESECUZIONE("02.IN_ESECUZIONE"), 
	NOTIFICATO("03.NOTIFICATO"), 
	INERRORE("04.IN_ERRORE"), 
	ABORTITO("05.ABORTITO");

	private String chiave;

	private EnumStatoEventi(String s) {
		chiave = s;
	}

	public String getChiave() {
		return chiave;
	}
}
