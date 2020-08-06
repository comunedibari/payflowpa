package it.regioneveneto.mygov.payment.mypivot.domain.dto.visualcompleta;

import java.util.Date;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.SegnalazioneKeyDto;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

public class SegnalazioneDto {
	private String deFirstname;
	private String deLastname;
	private Date dtUltimaModifica;
	private Boolean flgAttivo;
	private String classificazioneCompletezza;
	private boolean flgNascosto;
	private Date dtCreazione;
	private String deNota;
	private SegnalazioneKeyDto key;

	public SegnalazioneDto() {
	}

	public SegnalazioneDto(String classificazioneCompletezza, String codIud, String codIuf, String codIuv,
			String deNota, Date dtCreazione, Date dtUltimaModifica, Boolean flgAttivo, boolean flgNascosto,
			String codIpaEnte) {
		super();
		this.classificazioneCompletezza = classificazioneCompletezza;
		this.deNota = deNota;
		this.dtCreazione = dtCreazione;
		this.dtUltimaModifica = dtUltimaModifica;
		this.flgAttivo = flgAttivo;
		this.flgNascosto = flgNascosto;
		this.key = new SegnalazioneKeyDto();
		this.key.setClassificazioneCompletezza(classificazioneCompletezza);
		this.key.setCodIuv(codIuv);
		this.key.setCodIud(codIud);
		this.key.setCodIuf(codIuf);
		this.key.setCodIpaEnte(codIpaEnte);
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
				((it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO) o).flgNascosto,
				(o.ente != null ? o.ente.codIpaEnte : null));
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

	public SegnalazioneKeyDto getKey() {
		return key;
	}

	public void setKey(SegnalazioneKeyDto key) {
		this.key = key;
	}

}
