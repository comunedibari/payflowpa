package it.tasgroup.services.util.enumeration;

/*
 * Nel caso di interazioni SOAP sincrone assume i valori req/rsp per indicare rispettivamente SOAP Request e SOAP Response.
 */
public enum EnumSottoTipoEvento {
	
    req("Richiesta","Richiesta",""),
    rsp("Risposta","Risposta","");
    
    private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	private EnumSottoTipoEvento(String chiave, String descrizione, String chiaveBundle) {
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

}
