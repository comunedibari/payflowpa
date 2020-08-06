package it.tasgroup.idp.rs.model.creditore;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Questa classe rappresenta un soggetto al quale e' stato iscritto un debito.
 *
 */
@XmlRootElement
public class Versante {

	private String codFiscale;

	// Dati anagrafici
	private String anagrafica;
	private String cap;
	private Date dataNascita;
	private String email;
	private String indirizzo;
	private String localita;
	private String luogoNascita;
	private String nazione;
	private String numeroCivico;
	private String provincia;
	private String tipoSoggetto;  //TODO: enum

	public Versante() {
	}

	public Versante(String codFiscale, String anagrafica, String cap, Date dataNascita, String email, String indirizzo, String localita, String luogoNascita, String nazione, String numeroCivico, String provincia, String tipoSoggetto) {
		this.codFiscale = codFiscale;
		this.anagrafica = anagrafica;
		this.cap = cap;
		this.dataNascita = dataNascita;
		this.email = email;
		this.indirizzo = indirizzo;
		this.localita = localita;
		this.luogoNascita = luogoNascita;
		this.nazione = nazione;
		this.numeroCivico = numeroCivico;
		this.provincia = provincia;
		this.tipoSoggetto = tipoSoggetto;
	}

	/**
	 * Codice fiscale del soggetto versante
	 */
	@XmlElement(required=true)
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	/**
	 * Nome Cognome, ragione sociale del versante
	 */
	public String getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(String anagrafica) {
		this.anagrafica = anagrafica;
	}

	/**
	 * Codice avviamento postale del creditore
	 */
	public String getCap() {
		return cap;
	}
	public void setCap(String cap) {
		this.cap = cap;
	}

	/**
	 * Data di nascita del versante
	 */
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	/**
	 * eMail del versante
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Indirizzo (via, piazza) del versante
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * Localita', comune del versante
	 */
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}

	/**
	 * Luogo di nascita del versante
	 */
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	/**
	 * Nazione del versante
	 */
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	/**
	 * Numero civico del versante
	 */
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	/**
	 * Codice provincia del versante
	 */
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * Tipo soggetto (F=Persona fisica, G=Persona giuridica)
	 */
	public String getTipoSoggetto() {
		return tipoSoggetto;
	}
	public void setTipoSoggetto(String tipoSoggetto) {
		this.tipoSoggetto = tipoSoggetto;
	}



}
