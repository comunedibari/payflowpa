package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.TesoreriaSubsetTO;

public class TesoreriaSubsetDto {
	
	private TesoreriaSubsetTO tesoreriaSubsetTO;
	
	//campi della chiave "TesoreriaSubsetId"
	private String codiceIpaEnte;
	private String codiceIuv;
	private String identificativoFlussoRendicontazione;
	
	private String identificativoRiversamento;
	private SegnalazioneTO segnalazione;
	
	public TesoreriaSubsetTO getTesoreriaSubsetTO() {
		return tesoreriaSubsetTO;
	}
	public void setTesoreriaSubsetTO(TesoreriaSubsetTO tesoreriaSubsetTO) {
		this.tesoreriaSubsetTO = tesoreriaSubsetTO;
	}
	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}
	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}
	public String getCodiceIuv() {
		return codiceIuv;
	}
	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}
	public String getIdentificativoFlussoRendicontazione() {
		return identificativoFlussoRendicontazione;
	}
	public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
		this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
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
