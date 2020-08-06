package it.tasgroup.iris.dto.confpagamenti;

import java.io.Serializable;
import java.math.BigDecimal;

public class CfgTipoCommissioneDTO implements Serializable {
	private String id;
	private String bundleKey;
	private String descrizione;
	private String flStato;
	
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

	public String getFlStato() {
		return flStato;
	}
	public void setFlStato(String flStato) {
		this.flStato = flStato;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


}
