package it.tasgroup.ge.enums;



public enum EnumStatoConfigurazioneEvento {
	ATTIVO("Y"), DISATTIVO("N");
	 
	private String chiave;
 
	private EnumStatoConfigurazioneEvento(String s) {
		chiave = s;
	}
 
	public String getChiave() {
		return chiave;
	}
}
