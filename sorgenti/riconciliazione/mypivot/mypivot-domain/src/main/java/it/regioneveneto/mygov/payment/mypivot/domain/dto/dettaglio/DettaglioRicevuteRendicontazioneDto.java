package it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio;

public class DettaglioRicevuteRendicontazioneDto {
	private String tipoDovuto;
	private String codiceIud;
	private String codiceIuv;
	private String codiceIur;
	private String singoloImportoPagato;
	private String deDataEsitoSingoloPagamento;
	private AnagraficaDettaglioDto attestante;
	private AnagraficaDettaglioDto pagatore;
	private AnagraficaDettaglioDto versante;
	private String causaleVersamento;
	private int indiceDatiSingoloPagamento;

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

	public String getCodiceIuv() {
		return codiceIuv;
	}

	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}

	public String getCodiceIur() {
		return codiceIur;
	}

	public void setCodiceIur(String codiceIur) {
		this.codiceIur = codiceIur;
	}

	public String getSingoloImportoPagato() {
		return singoloImportoPagato;
	}

	public void setSingoloImportoPagato(String singoloImportoPagato) {
		this.singoloImportoPagato = singoloImportoPagato;
	}

	public String getDeDataEsitoSingoloPagamento() {
		return deDataEsitoSingoloPagamento;
	}

	public void setDeDataEsitoSingoloPagamento(String deDataEsitoSingoloPagamento) {
		this.deDataEsitoSingoloPagamento = deDataEsitoSingoloPagamento;
	}

	public AnagraficaDettaglioDto getAttestante() {
		return attestante;
	}

	public void setAttestante(AnagraficaDettaglioDto attestante) {
		this.attestante = attestante;
	}

	public AnagraficaDettaglioDto getPagatore() {
		return pagatore;
	}

	public void setPagatore(AnagraficaDettaglioDto pagatore) {
		this.pagatore = pagatore;
	}

	public AnagraficaDettaglioDto getVersante() {
		return versante;
	}

	public void setVersante(AnagraficaDettaglioDto versante) {
		this.versante = versante;
	}

	public String getCausaleVersamento() {
		return causaleVersamento;
	}

	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
	}

	public int getIndiceDatiSingoloPagamento() {
		return indiceDatiSingoloPagamento;
	}

	public void setIndiceDatiSingoloPagamento(int indiceDatiSingoloPagamento) {
		this.indiceDatiSingoloPagamento = indiceDatiSingoloPagamento;
	}

}
