package it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.PageDto;

public class DettaglioRendicontazioneDto {
	private String identificativoFlussoRendicontazione;
	private String dataOraFlussoRendicontazione;
	private String identificativoUnivocoRegolamento;
	private String deDataRegolamento;
	private String importoTotalePagamenti;

	private PageDto<DettaglioRicevuteRendicontazioneDto> ricevute;

	public String getIdentificativoFlussoRendicontazione() {
		return identificativoFlussoRendicontazione;
	}

	public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
		this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
	}

	public String getDataOraFlussoRendicontazione() {
		return dataOraFlussoRendicontazione;
	}

	public void setDataOraFlussoRendicontazione(String dataOraFlussoRendicontazione) {
		this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
	}

	public String getIdentificativoUnivocoRegolamento() {
		return identificativoUnivocoRegolamento;
	}

	public void setIdentificativoUnivocoRegolamento(String identificativoUnivocoRegolamento) {
		this.identificativoUnivocoRegolamento = identificativoUnivocoRegolamento;
	}

	public String getDeDataRegolamento() {
		return deDataRegolamento;
	}

	public void setDeDataRegolamento(String deDataRegolamento) {
		this.deDataRegolamento = deDataRegolamento;
	}

	public String getImportoTotalePagamenti() {
		return importoTotalePagamenti;
	}

	public void setImportoTotalePagamenti(String importoTotalePagamenti) {
		this.importoTotalePagamenti = importoTotalePagamenti;
	}

	public PageDto<DettaglioRicevuteRendicontazioneDto> getRicevute() {
		return ricevute;
	}

	public void setRicevute(PageDto<DettaglioRicevuteRendicontazioneDto> ricevute) {
		this.ricevute = ricevute;
	}
}
