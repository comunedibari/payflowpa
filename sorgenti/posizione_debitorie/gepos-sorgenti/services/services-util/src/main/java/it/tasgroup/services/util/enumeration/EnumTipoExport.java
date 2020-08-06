package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoExport implements MessageDescription{
	
	POSIZIONI("Posizioni","avvisi",""),
	PAGAMENTI("Pagamenti", "pagam", ""),
	CONDIZIONI("Condizioni", "condizioni", ""),
	EVENTINDP("EventiNDP","evNDP",""),
	RICEVUTE_TELEMATICHE("Ricevute_Telematiche","rt",""),
	RIACCREDITI("Riaccrediti", "riac", ""),
	STATISTICHE_CRUSCOTTO("Statistiche", "statistiche", ""),
	STATISTICHE_POSIZIONI("Stat_Posizioni", "Statistiche_Posizioni_Attese", ""),
	STATISTICHE_PAGAMENTI("Stat_Pagamenti", "Statistiche_Pagamenti_TipoDebito", ""),
	STATISTICHE_PAGAMENTI_CIRCUITO("Stat_Pagamenti_Circ", "Statistiche_Pagamenti_Circuito", "");

	private String chiave;
	private String propertyPrefix;
	private String chiaveBundle;

	private EnumTipoExport(String chiave, String propertyPrefix, String chiaveBundle) {
		this.chiave = chiave;
		this.setPropertyPrefix(propertyPrefix);
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}

	public String getPropertyPrefix() {
		return propertyPrefix;
	}

	public void setPropertyPrefix(String propertyPrefix) {
		this.propertyPrefix = propertyPrefix;
	}

	@Override
	public String getDescrizione() {
		return getChiave();
	}
	
	public static EnumTipoExport getByChiave(String chiave) {
		
		EnumTipoExport ret = null; // Default             
		
		for (EnumTipoExport item : EnumTipoExport.values()) {                 
			if (item.getChiave().equalsIgnoreCase(chiave)) {                     
				ret = item;                     
				break;                 
				}             
			}   
		
		return ret; 
	}
	
	public static String getPropertyPrefix(String tipoEsportazione){
    	
    	String propertyPrefix;
    	
    	if (tipoEsportazione == null || tipoEsportazione.equals(""))
    		propertyPrefix= PAGAMENTI.getPropertyPrefix();
    	else
    		propertyPrefix = getByChiave(tipoEsportazione).getPropertyPrefix();
    	
    	return propertyPrefix;
    }
	

}
