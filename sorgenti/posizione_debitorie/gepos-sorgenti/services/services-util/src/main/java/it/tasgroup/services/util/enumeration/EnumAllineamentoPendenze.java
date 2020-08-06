package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumAllineamentoPendenze implements MessageDescription{

	ALL_PEND_SSIL("ALL_PEND_SSIL", "Allineamento Pendenze Asincrono (nuovo lifecycle)", "common.label.AllineamentoPendenzeSSIL", EnumLifeCycleAllineaPendenze.ALL_PEND_CART_ASYNC, "common.label.AllineamentoPendenzeGroup"),
	ALL_PEND("ALL_PEND", "Allineamento Pendenze Asincrono", "common.label.AllineamentoPendenze", EnumLifeCycleAllineaPendenze.ALL_PEND_CART_ASYNC, "common.label.AllineamentoPendenzeGroup"),	
	ALL_PEND_OTF("ALL_PEND_OTF", "Allineamento Pendenze OTF", "common.label.AllineamentoOTF", EnumLifeCycleAllineaPendenze.ALL_PEND_CART_SYNC, "common.label.AllineamentoPendenzeGroup"),
	ALL_PEND_SYNC("ALL_PEND_SYNC", "Allineamento Pendenze Sincrono", "common.label.AllineamentoSync", EnumLifeCycleAllineaPendenze.ALL_PEND_CART_SYNC, "common.label.AllineamentoPendenzeGroup"),
	ALL_PEND_OTF_MX("ALL_PEND_OTF_MX", "Allineamento Pendenze Multi Ente", "common.label.AllineamentoOTFMX", EnumLifeCycleAllineaPendenze.ALL_PEND_OTF_MX, "common.label.AllineamentoPendenzeGroup"),
	CSV_BASIC_V3("CSV_BASIC_V3", "Allineamento Pendenze CSV Standard", "common.label.AllineamentoCSVBASICV3" , EnumLifeCycleAllineaPendenze.LOADER, "common.label.smartProxiesGroup"),
	CSV_BASIC_V2("CSV_BASIC_V2", "Allineamento Pendenze CSV Standard", "common.label.AllineamentoCSVBASICV2" , EnumLifeCycleAllineaPendenze.LOADER, "common.label.smartProxiesGroup");
	//CSV_BASIC_V2("CSV_BASIC_V2", "Allineamento Pendenze CSV Standard", "common.label.AllineamentoCSVBASICV2" , EnumLifeCycleAllineaPendenze.LOADER-REST);
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	private String descrizioneGruppo;
	private EnumLifeCycleAllineaPendenze lifeCycle;
	
	private EnumAllineamentoPendenze(String chiave, String descrizione, String chiaveBundle, EnumLifeCycleAllineaPendenze lifeCycle, String descrizioneGruppo) {
		
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
		this.lifeCycle = lifeCycle;
		this.descrizioneGruppo = descrizioneGruppo;
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
	
	public String getDescrizioneGruppo() {
		return descrizioneGruppo;
	}

	public void setDescrizioneGruppo(String descrizioneGruppo) {
		this.descrizioneGruppo = descrizioneGruppo;
	}

	public static EnumAllineamentoPendenze getByKey(String chiave) {
		
		for (EnumAllineamentoPendenze item : EnumAllineamentoPendenze.values()) {
            
			if (item.getChiave().equals(chiave))
				
                return item;
                
        }
		
        return null;
    }

	public EnumLifeCycleAllineaPendenze getLifeCycle() {
		return lifeCycle;
	}

	public void setLifeCycle(EnumLifeCycleAllineaPendenze lifeCycle) {
		this.lifeCycle = lifeCycle;
	}

}
