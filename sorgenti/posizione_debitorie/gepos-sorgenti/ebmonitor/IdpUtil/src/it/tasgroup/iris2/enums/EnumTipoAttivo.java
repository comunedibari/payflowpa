package it.tasgroup.iris2.enums;

public enum EnumTipoAttivo {
	
	ATTIVO("A", "Attivo"),
	DISATTIVO("D", "Non Attivo");
	
	private String chiave;
	private String descrizione;

	private EnumTipoAttivo(String chiave, String descrizione) {
		this.chiave = chiave;
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
	
	public static String getKeyByDescription(String descrizione) {
		for(EnumTipoAttivo e : EnumTipoAttivo.values()){
            if(e.descrizione.equalsIgnoreCase(descrizione)) return e.chiave;
        }
        return null;
	}
}
