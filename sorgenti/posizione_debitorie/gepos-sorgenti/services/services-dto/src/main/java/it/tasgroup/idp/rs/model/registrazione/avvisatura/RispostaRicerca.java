package it.tasgroup.idp.rs.model.registrazione.avvisatura;

import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RispostaRicerca {

	private String codiceFiscaleDebitore;
	private String nomeDebitore;
	private String cognomeDebitore;
	private List<HashMap<String, String>> canali; // TODO

	public RispostaRicerca() {

	}

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
