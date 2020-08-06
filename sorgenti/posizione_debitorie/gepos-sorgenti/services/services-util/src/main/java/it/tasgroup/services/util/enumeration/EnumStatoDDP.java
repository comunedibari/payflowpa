package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.util.ArrayList;
import java.util.List;


public enum EnumStatoDDP implements MessageDescription {
	
	PAGATO 			("P", "Pagato", "iris.ddp.stato.pagato"),
	INCASSATO 			("?", "Incassato", "iris.ddp.stato.incassato"),
	EMESSO 			("E", "Emesso", "iris.ddp.stato.emesso"),
	ANNULLATO_UTENTE 			("A", "Annullato da utente", "iris.ddp.stato.annullato.utente"),
	ANNULLATO_SISTEMA 			("S", "Annullato da sistema", "iris.ddp.stato.annullato.sistema");

	private String chiave;
	private String descrizione;

	private String chiaveBundle;

	private EnumStatoDDP(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return this.chiave;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public String getChiaveBundle() {
		return this.chiaveBundle;
	}

	
	public static EnumStatoDDP getByKey(String chiave) {
		EnumStatoDDP desiredItem = null; // Default             
		for (EnumStatoDDP item : EnumStatoDDP.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		return desiredItem; 
	}


	
	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();
		
		for (EnumStatoDDP item : EnumStatoDDP.values()) 
			listaDescrizioni.add(item.descrizione);
		
		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();
		
		for (EnumStatoDDP item : EnumStatoDDP.values()) 
			listaChiaviBundle.add(item.chiaveBundle);
		
		return listaChiaviBundle;
	}
	
}
