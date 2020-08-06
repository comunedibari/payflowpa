package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;

public class CfgFornitoreGatewayDTO implements Serializable {

	private Long id;
	private String bundleKey;
	private String descrizione;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
