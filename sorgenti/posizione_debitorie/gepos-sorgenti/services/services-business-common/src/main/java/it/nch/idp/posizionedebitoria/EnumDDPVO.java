package it.nch.idp.posizionedebitoria;

public class EnumDDPVO {
	
	private String descrizione;
	private String chiave;

	public EnumDDPVO(String chiave, String descrizione){
		this.descrizione = descrizione;
		this.chiave = chiave;
	}
	
	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}
	
}
