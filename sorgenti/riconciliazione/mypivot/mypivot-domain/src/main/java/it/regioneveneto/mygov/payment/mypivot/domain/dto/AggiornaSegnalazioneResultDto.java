package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

public class AggiornaSegnalazioneResultDto {

	public static enum ESITO{
		AGGIORNATA, INSERITA, NO_NEED_TO_UPDATE, ERROR 
	}
	private ESITO esito;
	private String msg;
	
	private SegnalazioneTO segnalazioneAggiornata;
	
	public AggiornaSegnalazioneResultDto(ESITO esito) {
		super();
		this.esito = esito;
	}
	
	public AggiornaSegnalazioneResultDto(ESITO esito, SegnalazioneTO segnalazioneAggiornata) {
		super();
		this.esito = esito;
		this.segnalazioneAggiornata = segnalazioneAggiornata;
	}

	public AggiornaSegnalazioneResultDto(ESITO esito, String msg) {
		super();
		this.esito = esito;
		this.msg = msg;
	}

	public ESITO getEsito() {
		return esito;
	}
	public void setEsito(ESITO esito) {
		this.esito = esito;
	}
	public SegnalazioneTO getSegnalazioneAggiornata() {
		return segnalazioneAggiornata;
	}
	public void setSegnalazioneAggiornata(SegnalazioneTO segnalazioneAggiornata) {
		this.segnalazioneAggiornata = segnalazioneAggiornata;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
