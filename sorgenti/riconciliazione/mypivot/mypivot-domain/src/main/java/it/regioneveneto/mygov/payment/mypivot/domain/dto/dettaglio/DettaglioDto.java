package it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio;

public class DettaglioDto {
	private String codIpaEnte;
	private String classificazioneCompletezza;
	private String codiceIuv;
	private String codiceIuf;
	private String codiceIud;

	private DettaglioPagamentoDto pagamento;
	private DettaglioRendicontazioneDto rendicontazione;
	private DettaglioTesoreriaDto tesoreria;
	private DettaglioRicevutaTelematicaDto ricevuta;

	public String getCodIpaEnte() {
		return codIpaEnte;
	}

	public void setCodIpaEnte(String codIpaEnte) {
		this.codIpaEnte = codIpaEnte;
	}

	public String getClassificazioneCompletezza() {
		return classificazioneCompletezza;
	}

	public void setClassificazioneCompletezza(String classificazioneCompletezza) {
		this.classificazioneCompletezza = classificazioneCompletezza;
	}

	public DettaglioPagamentoDto getPagamento() {
		return pagamento;
	}

	public void setPagamento(DettaglioPagamentoDto pagamento) {
		this.pagamento = pagamento;
	}

	public DettaglioRendicontazioneDto getRendicontazione() {
		return rendicontazione;
	}

	public void setRendicontazione(DettaglioRendicontazioneDto rendicontazione) {
		this.rendicontazione = rendicontazione;
	}

	public DettaglioTesoreriaDto getTesoreria() {
		return tesoreria;
	}

	public void setTesoreria(DettaglioTesoreriaDto tesoreria) {
		this.tesoreria = tesoreria;
	}

	public DettaglioRicevutaTelematicaDto getRicevuta() {
		return ricevuta;
	}

	public void setRicevuta(DettaglioRicevutaTelematicaDto ricevuta) {
		this.ricevuta = ricevuta;
	}
	
	public String getCodiceIuv() {
		return codiceIuv;
	}

	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}

	public String getCodiceIuf() {
		return codiceIuf;
	}

	public void setCodiceIuf(String codiceIuf) {
		this.codiceIuf = codiceIuf;
	}

	public String getCodiceIud() {
		return codiceIud;
	}

	public void setCodiceIud(String codiceIud) {
		this.codiceIud = codiceIud;
	}

}
