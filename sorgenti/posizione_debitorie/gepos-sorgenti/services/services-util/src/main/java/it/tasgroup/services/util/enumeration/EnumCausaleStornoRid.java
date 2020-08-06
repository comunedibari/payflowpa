package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCausaleStornoRid implements MessageDescription{
	
	S13("13","CAB errato",""),
	S16("16","Disposizione pervenuta oltre i termini",""),
	S18("18","Autorizzazione revocata",""),
	S19("19","Mancanza autorizzazione",""),
	S21("21","Numero incasso gia' comunicato",""),
	S31("31","Disposizione eccedente il numero massimo di disposizioni d'incasso",""),
	S32("32","Importo disposizione eccedente il limite stabilito nella delega",""),
	S33("33","Data di scadenza anteriore alla data del primo pagamento",""),
	S34("34","Data di scadenza posteriore alla data dell'ultimo pagamento",""),
	S35("35","Facolta' di storno per il debitore non conforme alla delega",""),
	S36("36","Autorizzazione attiva con flag di storno incongruente con il tipo incasso",""),
	S37("37","Posizione trasferita presso altra banca",""),
	S42("42","Importo disposizione diverso da importo prefissato",""),
	S17("17","Rimborso",""),
	S41("41","Revoca","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCausaleStornoRid(String chiave, String descrizione, String chiaveBundle) {
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
		
		for (EnumCausaleStornoRid item : EnumCausaleStornoRid.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredValue = item.getDescrizione();                     
				break;                 
				}             
			}             
		
		return desiredValue; 
	}

}
