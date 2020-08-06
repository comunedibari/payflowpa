package it.tasgroup.idp.rs.model.creditore;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Rappresenta un soggetto al quale e' stato iscritto un debito.
 *
 */
@XmlRootElement
public class Debitore {

	private String codFiscale;
	private String descrizione;
	private String tipoCodiceIdentificativoAlternativo;
	private String codiceIdentificativoAlternativo;

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

	/**
	 * Codice fiscale del soggetto debitore
	 */
	@XmlElement(required=true)
	public String getCodFiscale() {
		return codFiscale;
	}
	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	/**
	 * Descrizione del soggetto debitore (tipicamente nome cognome / ragione sociale)
	 */
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	/**
	 * Tipo codice identificativo alternativo (e.g. COD_TESSERA_SANITARIA)
	 */
	public String getTipoCodiceIdentificativoAlternativo() {
		return tipoCodiceIdentificativoAlternativo;
	}
	public void setTipoCodiceIdentificativoAlternativo(
			String tipoCodiceIdentificativoAlternativo) {
		this.tipoCodiceIdentificativoAlternativo = tipoCodiceIdentificativoAlternativo;
	}

	/**
	 * Tipo codice identificativo alternativo (e.g. SST1231231231)
	 */
	public String getCodiceIdentificativoAlternativo() {
		return codiceIdentificativoAlternativo;
	}
	public void setCodiceIdentificativoAlternativo(
			String codiceIdentificativoAlternativo) {
		this.codiceIdentificativoAlternativo = codiceIdentificativoAlternativo;
	}

	/**
	 * Nome Cognome, ragione sociale del debitore
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
	 * Data di nascita del debitore
	 */
	public Date getDataNascita() {
		return dataNascita;
	}
	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	/**
	 * eMail del debitore
	 */
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Indirizzo (via, piazza) del debitore
	 */
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	/**
	 * Localita', comune del debitore
	 */
	public String getLocalita() {
		return localita;
	}
	public void setLocalita(String localita) {
		this.localita = localita;
	}

	/**
	 * Luogo di nascita del debitore
	 */
	public String getLuogoNascita() {
		return luogoNascita;
	}
	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	/**
	 * Nazione del debitore
	 */
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	/**
	 * Numero civico del debitore
	 */
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	/**
	 * Codice provincia del debitore
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
