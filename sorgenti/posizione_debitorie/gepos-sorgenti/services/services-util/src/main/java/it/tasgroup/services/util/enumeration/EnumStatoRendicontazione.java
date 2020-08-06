package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;
import java.util.ArrayList;
import java.util.List;

public enum EnumStatoRendicontazione implements MessageDescription{
	
	RICEVUTO("RICEVUTO","Flusso Ricevuto",""),
	PARZIALMENTE_RICONCILIATO("PARZ RICONCILIATO","Flusso Parzialmente Riconciliato",""),
	RICONCILIATO("RICONCILIATO","Flusso Riconciliato",""),
	RIFIUTATO("IN ERRORE","Flusso In Errore",""),
	DA_ELABORARE("DA ELABORARE","Flusso Da Elaborare",""),
	NON_RICONCILIATO("NON RICONCILIATO","Flusso non Riconciliato","");

    private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumStatoRendicontazione(String chiave, String descrizione, String chiaveBundle) {
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
	
        public static List<String> getChiaveEsitAnomali() {
            List<String> flags = new ArrayList<String>();
            
            //TODO: aggiungere i codici riconciliazione esiti anomali
            flags.add(EnumStatoRendicontazione.NON_RICONCILIATO.chiave);
            flags.add(EnumStatoRendicontazione.PARZIALMENTE_RICONCILIATO.chiave);
            flags.add(EnumStatoRendicontazione.RICONCILIATO.chiave);
            
            return flags;
        }
        
}
