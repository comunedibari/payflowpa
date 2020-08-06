package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoCodiceRiferimentoEBR implements MessageDescription{
	
	CRO("1","C.R.O. (Codice Riferimento Originario)", ""),
	CIB("2","Codice Interno Banca","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoCodiceRiferimentoEBR(String chiave, String descrizione, String chiaveBundle) {
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
