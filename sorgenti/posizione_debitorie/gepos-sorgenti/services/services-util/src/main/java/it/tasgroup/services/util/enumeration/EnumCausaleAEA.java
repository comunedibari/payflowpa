package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumCausaleAEA implements MessageDescription{
	
	C90211("90211","RICHIESTA DI AUTORIZZAZIONE ALL'ADDEBITO IN CONTO ACQUISITA DALL'AZIENDA CLIENTE",""),
	C90218("90218","REVOCA DELL'AUTORIZZAZIONE ALL'ADDEBITO SU INIZIATIVA DEL SOTTOSCRITTORE",""),
	C90440("90440"," - ",""),
	
	C90600("90600","DINIEGO PER MANCATA RISPOSTA DA PARTE DELLA BANCA DOMICILIATARIA",""),
	C90311("90311","DINIEGO",""),
	C90312("90312","DINIEGO PER CAB ERRATO",""),
	C90313("90313","DINIEGO PER NUMERO DI CONTO CORRENTE ERRATO",""),
	C90314("90314","DINIEGO PERCHE' IL SOTTOSCRITTORE NON PUO' TRARRE SUL CONTO CORRENTE INDICATO",""),
	C90315("90315","DINIEGO PERCHE' FLAG DI STORNO NON ACCETTABILE",""),
	C90316("90316","STORNO IN QUANTO GIA' PRESENTE IN ARCHIVIO",""),
	C90318("90318","STORNO DELLA REVOCA",""),
	C90560("90560","STORNO DELLA REVOCA PER TRASFERIMENTO DELLE DELEGHE IN CORSO",""),

	C90212("90212", "ACCETTAZIONE DELLA RICHIESTA DI AUTORIZZAZIONE ALL'ADDEBITO IN CONTO ACQUISITA DALL'AZIENDA CLIENTE", "");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumCausaleAEA(String chiave, String descrizione, String chiaveBundle) {
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
		
		for (EnumCausaleAEA item : EnumCausaleAEA.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredValue = item.getDescrizione();                     
				break;                 
				}             
			}             
		
		return desiredValue; 
	}

}
