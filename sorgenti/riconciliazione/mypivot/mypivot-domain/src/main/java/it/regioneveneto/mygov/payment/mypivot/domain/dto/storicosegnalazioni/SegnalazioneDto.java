package it.regioneveneto.mygov.payment.mypivot.domain.dto.storicosegnalazioni;

import java.util.Date;

import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

public class SegnalazioneDto {

	private Long id;
	private String deFirstname;
	private String deLastname;
	private String codIuv;
	private Date dtUltimaModifica;
	private Boolean flgAttivo;
	private String classificazioneCompletezza;
	private boolean flgNascosto;
	private String codIuf;
	private String codIud;
	private Date dtCreazione;
	private String deNota;

	public SegnalazioneDto() {
	}

	public SegnalazioneDto(String classificazioneCompletezza, String codIud, String codIuf, String codIuv,
			String deNota, Date dtCreazione, Date dtUltimaModifica, Boolean flgAttivo, boolean flgNascosto) {
		super();
		this.classificazioneCompletezza = classificazioneCompletezza;
		this.codIud = codIud;
		this.codIuf = codIuf;
		this.codIuv = codIuv;
		this.deNota = deNota;
		this.dtCreazione = dtCreazione;
		this.dtUltimaModifica = dtUltimaModifica;
		this.flgAttivo = flgAttivo;
		this.flgNascosto = flgNascosto;
	}

	public static SegnalazioneDto copyOf(SegnalazioneTO o) {
		return new SegnalazioneDto(
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).classificazioneCompletezza,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).codIud,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).codIuf,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).codIuv,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).deNota,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).dtCreazione,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).dtUltimaModifica,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).flgAttivo,
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).flgNascosto);
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
	public String getDeFirstname() {
		return deFirstname;
	}

	public void setDeFirstname(String deFirstname) {
		this.deFirstname = deFirstname;
	}

	public String getDeLastname() {
		return deLastname;
	}

	public void setDeLastname(String deLastname) {
		this.deLastname = deLastname;
	}

	public String getCodIuv() {
		return codIuv;
	}

	public void setCodIuv(String codIuv) {
		this.codIuv = codIuv;
	}

	public Date getDtUltimaModifica() {
		return dtUltimaModifica;
	}

	public void setDtUltimaModifica(Date dtUltimaModifica) {
		this.dtUltimaModifica = dtUltimaModifica;
	}

	public Boolean getFlgAttivo() {
		return flgAttivo;
	}

	public void setFlgAttivo(Boolean flgAttivo) {
		this.flgAttivo = flgAttivo;
	}

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
	}

	public boolean isFlgNascosto() {
		return flgNascosto;
	}

	public void setFlgNascosto(boolean flgNascosto) {
		this.flgNascosto = flgNascosto;
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

	public Date getDtCreazione() {
		return dtCreazione;
	}

	public void setDtCreazione(Date dtCreazione) {
		this.dtCreazione = dtCreazione;
	}

	public String getDeNota() {
		return deNota;
	}

	public void setDeNota(String deNota) {
		this.deNota = deNota;
	}
}
