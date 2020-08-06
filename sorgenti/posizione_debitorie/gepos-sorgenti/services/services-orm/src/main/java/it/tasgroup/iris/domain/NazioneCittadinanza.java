package it.tasgroup.iris.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the NAZIONE_CITTADINANZA database table.
 * 
 */
@Entity
@Table(name="NAZIONE_CITTADINANZA")
@NamedQueries(
{
	@NamedQuery(name="listaNazioniCittadinanza", 
			query="select p from NazioneCittadinanza p order by p.descrizione asc")
})

public class NazioneCittadinanza extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String codNazione;
	private String descrizione;
	private String cittadinanza;
	private String sigla;
	private String comunitario;
	private String sviluppo;
	private String codMinister;
	

	@Id
	@Column(name="COD_NAZIONE")
	public String getCodNazione() {
		return codNazione;
	}

	@Column(name="DESCRIZIONE")
	public String getDescrizione() {
		return descrizione;
	}

	@Column(name="CITTADINANZA")
	public String getCittadinanza() {
		return cittadinanza;
	}

	@Column(name="SIGLA")
	public String getSigla() {
		return sigla;
	}

	@Column(name="COMUNITARIO")
	public String getComunitario() {
		return comunitario;
	}

	@Column(name="SVILUPPO")
	public String getSviluppo() {
		return sviluppo;
	}

	@Column(name="COD_MINISTER")
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