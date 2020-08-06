package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import java.math.BigDecimal;

/**
 * DTO per la presentazione lato Web delle informazioni caratterizzanti la RT in accertamento
 * (Tabelle coinvolte: mygov_flusso_export).
 * 
 * @author Marianna Memoli
 */
public class AccertamentoFlussoExportDto {
	
	private String codTipoDovuto;
	private String deTipoDovuto; 
	private String codiceIud;
	private String codiceIuv; 
	private String identificativoUnivocoRiscossione;
	private String denominazioneAttestante;
	private String codiceIdentificativoUnivocoAttestante;
	private String tipoIdentificativoUnivocoAttestante;
	private String anagraficaVersante;
	private String codiceIdentificativoUnivocoVersante;
	private String tipoIdentificativoUnivocoVersante;
	private String anagraficaPagatore;
	private String codiceIdentificativoUnivocoPagatore;
	private String tipoIdentificativoUnivocoPagatore;
	private String deDataUltimoAggiornamento;
	private String dataEsitoSingoloPagamento;
	private String causaleVersamento;
	private BigDecimal singoloImportoPagato;
	/**
	 * Singolo importo pagato formattato come "€ <importo>"
	 */
	private String formatSingoloImportoPagato;
	
	
	/**
	 * @return the codTipoDovuto
	 */
	public String getCodTipoDovuto() {
		return codTipoDovuto;
	}
	/**
	 * @param codTipoDovuto the codTipoDovuto to set
	 */
	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}
	/**
	 * @return the deTipoDovuto
	 */
	public String getDeTipoDovuto() {
		return deTipoDovuto;
	}
	/**
	 * @param deTipoDovuto the deTipoDovuto to set
	 */
	public void setDeTipoDovuto(String deTipoDovuto) {
		this.deTipoDovuto = deTipoDovuto;
	}
	/**
	 * @return the codiceIud
	 */
	public String getCodiceIud() {
		return codiceIud;
	}
	/**
	 * @param codiceIud the codiceIud to set
	 */
	public void setCodiceIud(String codiceIud) {
		this.codiceIud = codiceIud;
	}
	/**
	 * @return the codiceIuv
	 */
	public String getCodiceIuv() {
		return codiceIuv;
	}
	/**
	 * @param codiceIuv the codiceIuv to set
	 */
	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}
	/**
	 * @return the identificativoUnivocoRiscossione
	 */
	public String getIdentificativoUnivocoRiscossione() {
		return identificativoUnivocoRiscossione;
	}
	/**
	 * @param identificativoUnivocoRiscossione the identificativoUnivocoRiscossione to set
	 */
	public void setIdentificativoUnivocoRiscossione(String identificativoUnivocoRiscossione) {
		this.identificativoUnivocoRiscossione = identificativoUnivocoRiscossione;
	}
	/**
	 * @return the denominazioneAttestante
	 */
	public String getDenominazioneAttestante() {
		return denominazioneAttestante;
	}
	/**
	 * @param denominazioneAttestante the denominazioneAttestante to set
	 */
	public void setDenominazioneAttestante(String denominazioneAttestante) {
		this.denominazioneAttestante = denominazioneAttestante;
	}
	/**
	 * @return the codiceIdentificativoUnivocoAttestante
	 */
	public String getCodiceIdentificativoUnivocoAttestante() {
		return codiceIdentificativoUnivocoAttestante;
	}
	/**
	 * @param codiceIdentificativoUnivocoAttestante the codiceIdentificativoUnivocoAttestante to set
	 */
	public void setCodiceIdentificativoUnivocoAttestante(String codiceIdentificativoUnivocoAttestante) {
		this.codiceIdentificativoUnivocoAttestante = codiceIdentificativoUnivocoAttestante;
	}
	/**
	 * @return the tipoIdentificativoUnivocoAttestante
	 */
	public String getTipoIdentificativoUnivocoAttestante() {
		return tipoIdentificativoUnivocoAttestante;
	}
	/**
	 * @param tipoIdentificativoUnivocoAttestante the tipoIdentificativoUnivocoAttestante to set
	 */
	public void setTipoIdentificativoUnivocoAttestante(String tipoIdentificativoUnivocoAttestante) {
		this.tipoIdentificativoUnivocoAttestante = tipoIdentificativoUnivocoAttestante;
	}
	/**
	 * @return the anagraficaVersante
	 */
	public String getAnagraficaVersante() {
		return anagraficaVersante;
	}
	/**
	 * @param anagraficaVersante the anagraficaVersante to set
	 */
	public void setAnagraficaVersante(String anagraficaVersante) {
		this.anagraficaVersante = anagraficaVersante;
	}
	/**
	 * @return the codiceIdentificativoUnivocoVersante
	 */
	public String getCodiceIdentificativoUnivocoVersante() {
		return codiceIdentificativoUnivocoVersante;
	}
	/**
	 * @param codiceIdentificativoUnivocoVersante the codiceIdentificativoUnivocoVersante to set
	 */
	public void setCodiceIdentificativoUnivocoVersante(String codiceIdentificativoUnivocoVersante) {
		this.codiceIdentificativoUnivocoVersante = codiceIdentificativoUnivocoVersante;
	}
	/**
	 * @return the tipoIdentificativoUnivocoVersante
	 */
	public String getTipoIdentificativoUnivocoVersante() {
		return tipoIdentificativoUnivocoVersante;
	}
	/**
	 * @param tipoIdentificativoUnivocoVersante the tipoIdentificativoUnivocoVersante to set
	 */
	public void setTipoIdentificativoUnivocoVersante(String tipoIdentificativoUnivocoVersante) {
		this.tipoIdentificativoUnivocoVersante = tipoIdentificativoUnivocoVersante;
	}
	/**
	 * @return the anagraficaPagatore
	 */
	public String getAnagraficaPagatore() {
		return anagraficaPagatore;
	}
	/**
	 * @param anagraficaPagatore the anagraficaPagatore to set
	 */
	public void setAnagraficaPagatore(String anagraficaPagatore) {
		this.anagraficaPagatore = anagraficaPagatore;
	}
	/**
	 * @return the codiceIdentificativoUnivocoPagatore
	 */
	public String getCodiceIdentificativoUnivocoPagatore() {
		return codiceIdentificativoUnivocoPagatore;
	}
	/**
	 * @param codiceIdentificativoUnivocoPagatore the codiceIdentificativoUnivocoPagatore to set
	 */
	public void setCodiceIdentificativoUnivocoPagatore(String codiceIdentificativoUnivocoPagatore) {
		this.codiceIdentificativoUnivocoPagatore = codiceIdentificativoUnivocoPagatore;
	}
	/**
	 * @return the tipoIdentificativoUnivocoPagatore
	 */
	public String getTipoIdentificativoUnivocoPagatore() {
		return tipoIdentificativoUnivocoPagatore;
	}
	/**
	 * @param tipoIdentificativoUnivocoPagatore the tipoIdentificativoUnivocoPagatore to set
	 */
	public void setTipoIdentificativoUnivocoPagatore(String tipoIdentificativoUnivocoPagatore) {
		this.tipoIdentificativoUnivocoPagatore = tipoIdentificativoUnivocoPagatore;
	}
	/**
	 * @return the deDataUltimoAggiornamento
	 */
	public String getDeDataUltimoAggiornamento() {
		return deDataUltimoAggiornamento;
	}
	/**
	 * @param deDataUltimoAggiornamento the deDataUltimoAggiornamento to set
	 */
	public void setDeDataUltimoAggiornamento(String deDataUltimoAggiornamento) {
		this.deDataUltimoAggiornamento = deDataUltimoAggiornamento;
	}
	 
	/**
	 * @return the dataEsitoSingoloPagamento
	 */
	public String getDataEsitoSingoloPagamento() {
		return dataEsitoSingoloPagamento;
	}
	/**
	 * @param dataEsitoSingoloPagamento the dataEsitoSingoloPagamento to set
	 */
	public void setDataEsitoSingoloPagamento(String dataEsitoSingoloPagamento) {
		this.dataEsitoSingoloPagamento = dataEsitoSingoloPagamento;
	}
	/**
	 * @return the causaleVersamento
	 */
	public String getCausaleVersamento() {
		return causaleVersamento;
	}
	/**
	 * @param causaleVersamento the causaleVersamento to set
	 */
	public void setCausaleVersamento(String causaleVersamento) {
		this.causaleVersamento = causaleVersamento;
	}
	/**
	 * @return the formatSingoloImportoPagato
	 */
	public String getFormatSingoloImportoPagato() {
		return formatSingoloImportoPagato;
	}
	/**
	 * @param formatSingoloImportoPagato the formatSingoloImportoPagato to set
	 */
	public void setFormatSingoloImportoPagato(String formatSingoloImportoPagato) {
		this.formatSingoloImportoPagato = formatSingoloImportoPagato;
	}
	/**
	 * @return the singoloImportoPagato
	 */
	public BigDecimal getSingoloImportoPagato() {
		return singoloImportoPagato;
	}
	/**
	 * @param singoloImportoPagato the singoloImportoPagato to set
	 */
	public void setSingoloImportoPagato(BigDecimal singoloImportoPagato) {
		this.singoloImportoPagato = singoloImportoPagato;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AccertamentoFlussoExportDto [codTipoDovuto=");
		builder.append(codTipoDovuto);
		builder.append(", deTipoDovuto=");
		builder.append(deTipoDovuto);
		builder.append(", codiceIud=");
		builder.append(codiceIud);
		builder.append(", codiceIuv=");
		builder.append(codiceIuv);
		builder.append(", identificativoUnivocoRiscossione=");
		builder.append(identificativoUnivocoRiscossione);
		builder.append(", denominazioneAttestante=");
		builder.append(denominazioneAttestante);
		builder.append(", codiceIdentificativoUnivocoAttestante=");
		builder.append(codiceIdentificativoUnivocoAttestante);
		builder.append(", tipoIdentificativoUnivocoAttestante=");
		builder.append(tipoIdentificativoUnivocoAttestante);
		builder.append(", anagraficaVersante=");
		builder.append(anagraficaVersante);
		builder.append(", codiceIdentificativoUnivocoVersante=");
		builder.append(codiceIdentificativoUnivocoVersante);
		builder.append(", tipoIdentificativoUnivocoVersante=");
		builder.append(tipoIdentificativoUnivocoVersante);
		builder.append(", anagraficaPagatore=");
		builder.append(anagraficaPagatore);
		builder.append(", codiceIdentificativoUnivocoPagatore=");
		builder.append(codiceIdentificativoUnivocoPagatore);
		builder.append(", tipoIdentificativoUnivocoPagatore=");
		builder.append(tipoIdentificativoUnivocoPagatore);
		builder.append(", deDataUltimoAggiornamento=");
		builder.append(deDataUltimoAggiornamento);
		builder.append(", dataEsitoSingoloPagamento=");
		builder.append(dataEsitoSingoloPagamento);
		builder.append(", causaleVersamento=");
		builder.append(causaleVersamento);
		builder.append(", singoloImportoPagato=");
		builder.append(singoloImportoPagato);
		builder.append(", formatSingoloImportoPagato=");
		builder.append(formatSingoloImportoPagato);
		builder.append("]");
		return builder.toString();
	}
}
