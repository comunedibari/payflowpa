package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCausaleRid implements MessageDescription{
	
	D50000("50000"," - ",""),
	D50002("50002","DISPOSIZIONE OLTRE I TERMINI",""),
	D50006("50006","DISPOSIZIONE IRREGOLARE O NON IDONEA AL TRATTAMENTO",""),
	D50008("50008","DISPOSIZIONE RICHIAMATA",""),
	D50010("50010","DISPOSIZIONE PAGATA",""),
	D50001("50001","DISPOSIZIONE STORNATA PER CONTO ESTINTO",""),
	D50003("50003","DISPOSIZIONE STORNATA PER INSUFFICIENZA FONDI",""),
	D50004("50004","DISPOSIZIONE STORNATA A SEGUITO DI OPPOSIZIONE DEL DEBITORE",""),
	D50007("50007","DISPOSIZIONE STORNATA PER MOTIVI TECNICI/MANCATO ALLINEAMENTO ARCHIVI",""),
	D50009("50009","DISPOSIZIONE STORNATA PER MOTIVI GENERICI","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCausaleRid(String chiave, String descrizione, String chiaveBundle) {
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
	
	public static String getValoreByChiave(String chiave) {
		
		String desiredValue = "";    
		
		for (EnumCausaleRid item : EnumCausaleRid.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredValue = item.getDescrizione();                     
				break;                 
				}             
			}             
		
		return desiredValue; 
	}

}
