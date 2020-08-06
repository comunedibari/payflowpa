package it.tasgroup.iris.dto.flussi;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class RispostaRicercaAvvisaturaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	String codiceFiscaleDebitore;
	String nomeDebitore;
	String cognomeDebitore;
	List<HashMap<String, String>> canali;
	
	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}
	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}
	public String getNomeDebitore() {
		return nomeDebitore;
	}
	public void setNomeDebitore(String nomeDebitore) {
		this.nomeDebitore = nomeDebitore;
	}
	public String getCognomeDebitore() {
		return cognomeDebitore;
	}
	public void setCognomeDebitore(String cognomeDebitore) {
		this.cognomeDebitore = cognomeDebitore;
	}
	public List<HashMap<String, String>> getCanali() {
		return canali;
	}
	public void setCanali(List<HashMap<String, String>> canali) {
		this.canali = canali;
	}
	
}
