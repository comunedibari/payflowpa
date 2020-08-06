package it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio;

public class DettaglioPagamentoDto {
	private String tipoDovuto;
	private String codiceIud;
	private String singoloImportoPagato;
	private String deDataEsecuzionePagamento;
	private String causaleVersamento;
	private AnagraficaDettaglioDto pagatore;
	private String datiSpecificiRiscossione;

	public String getTipoDovuto() {
		return tipoDovuto;
	}

	public void setTipoDovuto(String tipoDovuto) {
		this.tipoDovuto = tipoDovuto;
	}

	public String getCodiceIud() {
		return codiceIud;
	}

	public void setCodiceIud(String codiceIud) {
		this.codiceIud = codiceIud;
	}

	public String getSingoloImportoPagato() {
		return singoloImportoPagato;
	}

	public void setSingoloImportoPagato(String singoloImportoPagato) {
		this.singoloImportoPagato = singoloImportoPagato;
	}

	public String getDeDataEsecuzionePagamento() {
		return deDataEsecuzionePagamento;
	}

	public void setDeDataEsecuzionePagamento(String deDataEsecuzionePagamento) {
		this.deDataEsecuzionePagamento = deDataEsecuzionePagamento;
	}

	
	public String getCausaleVersamento() {
		return causaleVersamento;
	}

	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
	}

	public AnagraficaDettaglioDto getPagatore() {
		return pagatore;
	}

	public void setPagatore(AnagraficaDettaglioDto pagatore) {
		this.pagatore = pagatore;
	}

	public String getDatiSpecificiRiscossione() {
		return datiSpecificiRiscossione;
	}

	public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
		this.datiSpecificiRiscossione = datiSpecificiRiscossione;
	}

}
