package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;

public class AggiornaSegnalazioneDto {

	private boolean flgNascosto;
	private String classificazioneCompletezza;
	private EnteTO ente;
	private String tesIuv;
	private String deNota;
	private Long idSegnalazione;
	private UtenteTO utente;
	private String codIuv;
	private String codIuf;
	private String codIud;

	public boolean isFlgNascosto() {
		return flgNascosto;
	}

	public void setFlgNascosto(boolean flgNascosto) {
		this.flgNascosto = flgNascosto;
	}

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
	}

	public EnteTO getEnte() {
		return ente;
	}

	public void setEnte(EnteTO ente) {
		this.ente = ente;
	}

	public String getTesIuv() {
		return tesIuv;
	}

	public void setTesIuv(String tesIuv) {
		this.tesIuv = tesIuv;
	}

	public String getDeNota() {
		return deNota;
	}

	public void setDeNota(String deNota) {
		this.deNota = deNota;
	}

	public UtenteTO getUtente() {
		return utente;
	}

	public void setUtente(UtenteTO utente) {
		this.utente = utente;
	}

	public String getCodIuv() {
		return codIuv;
	}

	public void setCodIuv(String codIuv) {
		this.codIuv = codIuv;
	}

	public String getCodIuf() {
		return codIuf;
	}

	public void setCodIuf(String codIuf) {
		this.codIuf = codIuf;
	}

	public String getCodIud() {
		return codIud;
	}

	public void setCodIud(String codIud) {
		this.codIud = codIud;
	}

	public Long getIdSegnalazione() {
		return idSegnalazione;
	}

	public void setIdSegnalazione(Long idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}

}
