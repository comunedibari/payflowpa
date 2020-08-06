package it.regioneveneto.mygov.payment.mypivot.domain.dto;

public class SegnalazioneDto  implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -595224785191162420L;
	/**
	 * 
	 */

	public String codIpa;
	
	public Long idSegnalazione;
	public String classificazioneCompletezza;
	public String codIud;
	public String codIuv;
	public String codIuf;
	public String deNota;
	public Boolean flgNascosto;
	public Boolean rendicontazione;
	public String tipoVisualizzazione;
	
    public SegnalazioneDto() {
    }

    public SegnalazioneDto(Long idSegnalazione, String classificazioneCompletezza, String codIud, String codIuv, String codIuf, String deNota,
    		Boolean flgNascosto, Boolean rendicontazione, String tipoVisualizzazione) {
        super();
        this.idSegnalazione = idSegnalazione;
        this.classificazioneCompletezza = classificazioneCompletezza;
        this.codIud = codIud;
        this.codIuv = codIuv;
        this.codIuf = codIuf;
        this.deNota = deNota;
        this.flgNascosto = flgNascosto;
        this.rendicontazione = rendicontazione;
        this.tipoVisualizzazione = tipoVisualizzazione;
        
    }


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

    
    public String getCodIpa() {
        return codIpa;
    }

    public void setCodIpa(String codIpa) {
        this.codIpa = codIpa;
    }

    public String getCodIud() {
        return codIud;
    }

    public void setCodIud(String codIud) {
        this.codIud = codIud;
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

    public void setCodIufd(String codIuf) {
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

    public Boolean getRendicontazione() {
        return rendicontazione;
    }

    public void setRendicontazione(Boolean rendicontazione) {
        this.rendicontazione = rendicontazione;
    }

    public String getTpoVisualizzazione() {
        return tipoVisualizzazione;
    }

    public void setTipoVisualizzazione(String tipoVisualizzazione) {
        this.tipoVisualizzazione = tipoVisualizzazione;
    }

	@Override
	public String toString() {
		return "SegnalazioneDto [idSegnalazione=" + idSegnalazione + ", classificazioneCompletezza="
				+ classificazioneCompletezza + ", codIud=" + codIud + ", codIuv=" + codIuv + ", codIuf=" + codIuf
				+ ", deNota=" + deNota + ", flgNascosto=" + flgNascosto + ", rendicontazione=" + rendicontazione
				+ ", tipoVisualizzazione=" + tipoVisualizzazione + "]";
	}

    
}
