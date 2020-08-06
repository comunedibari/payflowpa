package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.ExportRendicontazioneTO;

public class VisualizzaDto {

	private ExportRendicontazioneTO exportRendicontazioneTO;

	//campi della chiave "ExportRendicontazioneId"
	private String codiceIpaEnte;
	private String codiceIuv;
	private String identificativoUnivocoRiscossione;

	private String esitoSingoloPagamento;

	public ExportRendicontazioneTO getExportRendicontazioneTO() {
		return exportRendicontazioneTO;
	}

	public void setExportRendicontazioneTO(ExportRendicontazioneTO exportRendicontazioneTO) {
		this.exportRendicontazioneTO = exportRendicontazioneTO;
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

	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}

	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}

	public String getEsitoSingoloPagamento() {
		return esitoSingoloPagamento;
	}

	public void setEsitoSingoloPagamento(String esitoSingoloPagamento) {
		this.esitoSingoloPagamento = esitoSingoloPagamento;
	}

}
