package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;
import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoAllineaPendenze implements MessageDescription{
	
	DA_ELABORARE("DA_ELABORARE","DA ELABORARE","EnumStatoAllineaPendenze.DA_ELABORARE", EnumSeverityLevel.INFO),
	DA_ELABORARE_LOADER("DA_ELABORARE_LOADER","DA_ELABORARE_LOADER","EnumStatoAllineaPendenze.DA_ELABORARE_LOADER", EnumSeverityLevel.INFO),
	VALIDATO_OK_LOADER("VALIDATO_OK_LOADER","VALIDATO_OK_LOADER","EnumStatoAllineaPendenze.VALIDATO_OK_LOADER", EnumSeverityLevel.INFO),
	VALIDATO_KO_LOADER("VALIDATO_KO_LOADER","VALIDATO_KO_LOADER","EnumStatoAllineaPendenze.VALIDATO_KO_LOADER", EnumSeverityLevel.ERROR),
	ESITATO_LOADER("ESITATO_LOADER","ESITATO_LOADER","EnumStatoAllineaPendenze.ESITATO_LOADER", EnumSeverityLevel.INFO),
	COMPLETO("COMPLETO","COMPLETO","EnumStatoAllineaPendenze.COMPLETO", EnumSeverityLevel.ERROR),
	NON_VALIDO_XSDSCHEMA("NON_VALIDO_XSDSCHEMA","NON_VALIDO_XSDSCHEMA","EnumStatoAllineaPendenze.NON_VALIDO_XSDSCHEMA", EnumSeverityLevel.ERROR),
	DA_SPEDIRE("DA_SPEDIRE","DA SPEDIRE","EnumStatoAllineaPendenze.DA_SPEDIRE", EnumSeverityLevel.INFO),
	IN_SPEDIZIONE("IN_SPEDIZIONE","IN SPEDIZIONE","EnumStatoAllineaPendenze.IN_SPEDIZIONE", EnumSeverityLevel.INFO),
	RISPOSTA_INVIATA("RISPOSTA_INVIATA","RISPOSTA INVIATA","EnumStatoAllineaPendenze.RISPOSTA_INVIATA", EnumSeverityLevel.INFO),
	RISPOSTA_INVIATA_WS("RISPOSTA_INVIATA_WS","RISPOSTA INVIATA WS","EnumStatoAllineaPendenze.RISPOSTA_INVIATA_WS", EnumSeverityLevel.INFO),
	RISPOSTA_OK_WS("RISPOSTA_INVIATA_WS","RISPOSTA OK WS","EnumStatoAllineaPendenze.RISPOSTA_OK_WS", EnumSeverityLevel.INFO),	
	RISPOSTA_KO_WS("RISPOSTA_INVIATA_WS","RISPOSTA KO WS","EnumStatoAllineaPendenze.RISPOSTA_KO_WS", EnumSeverityLevel.ERROR),	
	RISPOSTA_NON_INVIATA("RISPOSTA_NON_INVIATA","RISPOSTA NON INVIATA","EnumStatoAllineaPendenze.RISPOSTA_NON_INVIATA", EnumSeverityLevel.ERROR);

	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private EnumSeverityLevel severity;

	private EnumStatoAllineaPendenze(String chiave, String descrizione, String chiaveBundle, EnumSeverityLevel severity) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.severity = severity;
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
	
	public static EnumStatoAllineaPendenze getByDescription(String descr) {
		
		for (EnumStatoAllineaPendenze item : EnumStatoAllineaPendenze.values()) {
            
			if (item.getDescrizione().equals(descr))
				
                return item;
                
        }
		
        return null;
    }

	public EnumSeverityLevel getSeverity() {
		return severity;
	}

	public void setSeverity(EnumSeverityLevel severity) {
		this.severity = severity;
	}
	

}
