package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaNoMatchSubsetTO;

public class TesoreriaNoMatchSubsetDto {
	
	private TesoreriaNoMatchSubsetTO tesoreriaNoMatchSubsetTO;
	
	//campi della chiave "TesoreriaNoMatchSubsetId"
	private String codiceIpaEnte;
	private String deAnnoBolletta;
	private String codBolletta;
	
	private String identificativoRiversamento;
	private SegnalazioneTO segnalazione;
	
	
	public TesoreriaNoMatchSubsetTO getTesoreriaNoMatchSubsetTO() {
		return tesoreriaNoMatchSubsetTO;
	}
	public void setTesoreriaNoMatchSubsetTO(TesoreriaNoMatchSubsetTO tesoreriaNoMatchSubsetTO) {
		this.tesoreriaNoMatchSubsetTO = tesoreriaNoMatchSubsetTO;
	}
	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}
	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}
	public String getDeAnnoBolletta() {
		return deAnnoBolletta;
	}
	public void setDeAnnoBolletta(String deAnnoBolletta) {
		this.deAnnoBolletta = deAnnoBolletta;
	}
	public String getCodBolletta() {
		return codBolletta;
	}
	public void setCodBolletta(String codBolletta) {
		this.codBolletta = codBolletta;
	}
	public String getIdentificativoRiversamento() {
		return identificativoRiversamento;
	}
	public void setIdentificativoRiversamento(String identificativoRiversamento) {
		this.identificativoRiversamento = identificativoRiversamento;
	}
	public SegnalazioneTO getSegnalazione() {
		return segnalazione;
	}
	public void setSegnalazione(SegnalazioneTO segnalazione) {
		this.segnalazione = segnalazione;
	}
	
	
	
	
}
