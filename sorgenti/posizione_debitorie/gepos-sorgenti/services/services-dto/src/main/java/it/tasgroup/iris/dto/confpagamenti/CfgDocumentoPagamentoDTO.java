package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;

public class CfgDocumentoPagamentoDTO implements Serializable {

	private String id;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

}
