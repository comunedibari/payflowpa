package it.tasgroup.services.util.enumeration;

import java.util.Arrays;
import java.util.List;

import it.tasgroup.iris.shared.util.enumeration.EnumSeverityLevel;
import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumStatoNotifichePagamento implements MessageDescription{
	
	DA_SPEDIRE("DA_SPEDIRE","DA SPEDIRE","EnumStatoNotifichePagamento.DA_SPEDIRE", EnumSeverityLevel.INFO),
	INVIATO("INVIATO","INVIATO","EnumStatoNotifichePagamento.INVIATO", EnumSeverityLevel.INFO),
	REINVIATO("REINVIATO","REINVIATO","EnumStatoNotifichePagamento.REINVIATO", EnumSeverityLevel.INFO),
	NON_INVIATO("NON_INVIATO","NON_INVIATO","EnumStatoNotifichePagamento.NON_INVIATO",EnumSeverityLevel.ERROR),
	ELABORATO_OK("ELABORATO OK","ELABORATO OK","EnumStatoNotifichePagamento.ELABORATO_OK", EnumSeverityLevel.INFO),
	ELABORATO_KO("ELABORATO_KO","ELABORATO_KO","EnumStatoNotifichePagamento.ELABORATO_KO", EnumSeverityLevel.ERROR),
	IN_SPEDIZIONE("IN_SPEDIZIONE","IN_SPEDIZIONE","EnumStatoNotifichePagamento.IN_SPEDIZIONE", EnumSeverityLevel.WARN)
	;
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private EnumSeverityLevel severity;

	private EnumStatoNotifichePagamento(String chiave, String descrizione, String chiaveBundle, EnumSeverityLevel severity) {
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
	
	public static EnumStatoNotifichePagamento getByDescription(String descr) {
		
		for (EnumStatoNotifichePagamento item : EnumStatoNotifichePagamento.values()) {
            
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
