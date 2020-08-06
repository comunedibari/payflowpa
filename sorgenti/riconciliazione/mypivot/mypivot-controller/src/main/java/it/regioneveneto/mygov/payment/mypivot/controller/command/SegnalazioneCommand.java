/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * @author Riccardo Cannas
 * 
 */
public class SegnalazioneCommand {
	private Long idSegnalazione;
	private String classificazioneCompletezza;
	private String codIud;
	private String codIuv;
	private String codIuf;
	private String deNota;
	private Boolean flgNascosto;
	private Boolean rendicontazione;
	private String tipoVisualizzazione;

	public Long getIdSegnalazione() {
		return idSegnalazione;
	}

	public void setIdSegnalazione(Long idSegnalazione) {
		this.idSegnalazione = idSegnalazione;
	}

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
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

	public String getDeNota() {
		return deNota;
	}

	public void setDeNota(String deNota) {
		this.deNota = deNota;
	}

	public Boolean getFlgNascosto() {
		return flgNascosto;
	}

	public void setFlgNascosto(Boolean flgNascosto) {
		this.flgNascosto = flgNascosto;
	}

	public String getCodIud() {
		return codIud;
	}

	public void setCodIud(String codIud) {
		this.codIud = codIud;
	}

	public Boolean getRendicontazione() {
		return rendicontazione;
	}

	public void setRendicontazione(Boolean rendicontazione) {
		this.rendicontazione = rendicontazione;
	}

	public String getTipoVisualizzazione() {
		return tipoVisualizzazione;
	}

	public void setTipoVisualizzazione(String tipoVisualizzazione) {
		this.tipoVisualizzazione = tipoVisualizzazione;
	}

}
