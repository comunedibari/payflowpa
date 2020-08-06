package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;
import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumSeverityLevelTO implements MessageDescription{
	
	FATAL(0,"FATAL","Fatal","EnumSeverityLevelTO.FATAL", EnumSeverityLevel.FATAL),
	ERROR_PROTOCOL(1,"ERROR_PROTOCOL","errorProtocol","EnumSeverityLevelTO.ERROR_PROTOCOL", EnumSeverityLevel.ERROR),
	ERROR_INTEGRATION(2,"ERROR_INTEGRATION","errorIntegration","EnumSeverityLevelTO.ERROR_INTEGRATION", EnumSeverityLevel.ERROR),
	INFO_PROTOCOL(3,"INFO_PROTOCOL","infoProtocol","EnumSeverityLevelTO.INFO_PROTOCOL", EnumSeverityLevel.INFO),
	INFO_INTEGRATION(4,"INFO_INTEGRATION","infoIntegration","EnumSeverityLevelTO.INFO_INTEGRATION", EnumSeverityLevel.INFO),
	DEBUG_LOW(5,"DEBUG_LOW","debugLow","EnumSeverityLevelTO.DEBUG_LOW", EnumSeverityLevel.WARN),
	DEBUG_MEDIUM(6,"DEBUG_MEDIUM","debugMedium","EnumSeverityLevelTO.DEBUG_MEDIUM", EnumSeverityLevel.WARN),
	DEBUG_HIGH(7,"DEBUG_HIGH","debugHigh","EnumSeverityLevelTO.DEBUG_HIGH", EnumSeverityLevel.WARN);
	
	private int code;
	private String chiave;
	private String descrizione;
	private String chiaveBundle;	
	private EnumSeverityLevel severity;

	private EnumSeverityLevelTO(int code, String chiave, String descrizione, String chiaveBundle, EnumSeverityLevel severity) {
		this.code = code;
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
	
	public static EnumSeverityLevelTO getByDescription(String descr) {
		
		for (EnumSeverityLevelTO item : EnumSeverityLevelTO.values()) {
            
			if (item.getDescrizione().equals(descr))
				
                return item;
                
        }
		
        return null;
    }
	
	public static EnumSeverityLevelTO getByCode(int code) {
		for (EnumSeverityLevelTO item : EnumSeverityLevelTO.values()) {
            if (item.getCode() == code)
				return item;
        }
        return null;
    }

	public EnumSeverityLevel getSeverity() {
		return severity;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}	

}
