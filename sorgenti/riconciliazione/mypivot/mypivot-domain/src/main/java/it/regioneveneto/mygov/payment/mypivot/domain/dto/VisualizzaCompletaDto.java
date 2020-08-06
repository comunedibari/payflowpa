package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.ImportExportRendicontazioneTesoreriaTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

public class VisualizzaCompletaDto {
	
	private ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO;
	
	//campi della chiave "ImportExportRendicontazioneTesoreriaId"
	private String codiceIpaEnte;
	private String codiceIuv;
	private String identificativoUnivocoRiscossione;
	private String identificativoFlussoRendicontazione;
	private String datiSpecificiRiscossione;

	private String esitoSingoloPagamento;
	private String rowClass;
	private String identificativoRiversamento;
	private SegnalazioneTO segnalazione;
	
	public String getRowClass() {
		return rowClass;
	}
	public void setRowClass(String rowClass) {
		this.rowClass = rowClass;
	}
	
	public ImportExportRendicontazioneTesoreriaTO getImportExportRendicontazioneTesoreriaTO() {
		return importExportRendicontazioneTesoreriaTO;
	}
	public void setImportExportRendicontazioneTesoreriaTO(ImportExportRendicontazioneTesoreriaTO importExportRendicontazioneTesoreriaTO) {
		this.importExportRendicontazioneTesoreriaTO = importExportRendicontazioneTesoreriaTO;
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
	public String getDatiSpecificiRiscossione() {
		return datiSpecificiRiscossione;
	}
	public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
		this.datiSpecificiRiscossione = datiSpecificiRiscossione;
	}
	public String getIdentificativoFlussoRendicontazione() {
		return identificativoFlussoRendicontazione;
	}
	public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
		this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
	}
	public String getEsitoSingoloPagamento() {
		return esitoSingoloPagamento;
	}
	public void setEsitoSingoloPagamento(String esitoSingoloPagamento) {
		this.esitoSingoloPagamento = esitoSingoloPagamento;
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
