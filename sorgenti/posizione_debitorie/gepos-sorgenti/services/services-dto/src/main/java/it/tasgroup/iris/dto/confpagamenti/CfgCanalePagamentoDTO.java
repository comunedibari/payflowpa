package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;

public class CfgCanalePagamentoDTO implements Serializable{
	private String bundleKey;
	private String descrizione;
	
	public String getBundleKey() {
		return bundleKey;
	}
	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

}
