package it.regioneveneto.mygov.payment.mypivot.domain.dto;



public class AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto {
	
	public static enum ESITO{
		AGGIORNATA, INSERITA, ERROR, EXIST 
	}
	private ESITO esito;
	private String msg;
	
	private AnagraficaUfficioCapitoloAccertamentoDto anagraficaAggiornata;
	
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO esito) {
		super();
		this.esito = esito;
	}
	
	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO esito, AnagraficaUfficioCapitoloAccertamentoDto anagraficaAggiornata) {
		super();
		this.esito = esito;
		this.anagraficaAggiornata = anagraficaAggiornata;
	}

	public AggiornaAnagraficaUfficioCapitoloAccertamentoResultDto(ESITO esito, String msg) {
		super();
		this.esito = esito;
		this.msg = msg;
	}

	

	/**
	 * @return the esito
	 */
	public ESITO getEsito() {
		return esito;
	}

	/**
	 * @param esito the esito to set
	 */
	public void setEsito(ESITO esito) {
		this.esito = esito;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the anagraficaAggiornata
	 */
	public AnagraficaUfficioCapitoloAccertamentoDto getAnagraficaAggiornata() {
		return anagraficaAggiornata;
	}

	/**
	 * @param anagraficaAggiornata the anagraficaAggiornata to set
	 */
	public void setAnagraficaAggiornata(AnagraficaUfficioCapitoloAccertamentoDto anagraficaAggiornata) {
		this.anagraficaAggiornata = anagraficaAggiornata;
	}
	
	

}
