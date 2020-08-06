/**
 * 
 */
package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;


public class NazioneCittadinanzaDTO  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6456592486941023790L;

	private String codNazione;
	
	private String descrizione;
	
	private String cittadinanza;
	
	private String sigla;
	
	private String comunitario;
	
	private String sviluppo;
	
	private String codMinister;

	public String getCodNazione() {
		return codNazione;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public String getCittadinanza() {
		return cittadinanza;
	}

	public String getSigla() {
		return sigla;
	}

	public String getComunitario() {
		return comunitario;
	}

	public String getSviluppo() {
		return sviluppo;
	}

	public String getCodMinister() {
		return codMinister;
	}

	public void setCodNazione(String codNazione) {
		this.codNazione = codNazione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void setComunitario(String comunitario) {
		this.comunitario = comunitario;
	}

	public void setSviluppo(String sviluppo) {
		this.sviluppo = sviluppo;
	}

	public void setCodMinister(String codMinister) {
		this.codMinister = codMinister;
	}

}
