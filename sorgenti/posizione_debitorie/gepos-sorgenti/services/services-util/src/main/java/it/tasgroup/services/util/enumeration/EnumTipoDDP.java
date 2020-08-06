package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import java.util.ArrayList;
import java.util.List;


public enum EnumTipoDDP implements MessageDescription {
	
	BONIFICO ("1", "BONIFICO", "BLBON",1L),
	ATM 	 ("2", "ATM", "BLATM",2L),
	FRECCIA  ("3", "FRECCIA", "BLFRC",3L),
	GDO 	 ("4", "GDO", "BLEUP",4L),
	NDP 	 ("5", "NDP", "BLNDP",5L),
	ASL 	 ("6", "ASL", "BLASL",4L);
	

	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private Long idCfgDocumentoPagamento;

	private EnumTipoDDP(String chiave, String descrizione, String chiaveBundle, Long idCfgDocumentoPagamento) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.idCfgDocumentoPagamento = idCfgDocumentoPagamento;
	}
	
	private EnumTipoDDP(String chiave) {
		this.chiave = chiave;
	}
	
	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public Long getIdCfgDocumentoPagamento() {
		return idCfgDocumentoPagamento;
	}

	public void setIdCfgDocumentoPagamento(Long idCfgDocumentoPagamento) {
		this.idCfgDocumentoPagamento = idCfgDocumentoPagamento;
	}
	
	public static EnumTipoDDP getByKey(String chiave) {
		EnumTipoDDP desiredItem = null; // Default             
		for (EnumTipoDDP item : EnumTipoDDP.values()) {                 
			if (item.getChiave().equals(chiave)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		return desiredItem; 
	}
	

	public static List<String> getListaDescrizioni(){
		ArrayList<String> listaDescrizioni = new ArrayList<String>();
		
		for (EnumTipoDDP item : EnumTipoDDP.values()) 
			listaDescrizioni.add(item.descrizione);
		
		return listaDescrizioni;
	}

	public static List<String> getListaChiaviBundle(){
		ArrayList<String> listaChiaviBundle = new ArrayList<String>();
		
		for (EnumTipoDDP item : EnumTipoDDP.values()) 
			listaChiaviBundle.add(item.chiaveBundle);
		
		return listaChiaviBundle;
	}
	
	public static EnumTipoDDP getByDescrizione(String descrizione) {
		EnumTipoDDP desiredItem = null; // Default             
		for (EnumTipoDDP item : EnumTipoDDP.values()) {                 
			if (item.getDescrizione().equals(descrizione)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		return desiredItem; 
	}
	
	public static EnumTipoDDP getByChiaveBundle(String chiaveBundle) {
		EnumTipoDDP desiredItem = null; // Default             
		for (EnumTipoDDP item : EnumTipoDDP.values()) {                 
			if (item.getChiaveBundle().equals(chiaveBundle)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}             
		return desiredItem; 
	}
	
}
