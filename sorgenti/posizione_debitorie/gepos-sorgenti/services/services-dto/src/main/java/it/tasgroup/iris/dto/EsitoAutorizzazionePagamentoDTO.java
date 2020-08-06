package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumBusinessReturnCodes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EsitoAutorizzazionePagamentoDTO implements Serializable{
	
	private EnumBusinessReturnCodes returnCode = EnumBusinessReturnCodes.OK;	
	private String logMessage = null;
	
	private String descrizione = null;
	private String msgId; 
	private String statoDocumento;
	private String idPendenzaEnte;
	private String idEnte;
	private String cdTrbEnte;
	private String idCondPagamento;
	private String idPagamentoEnte;
	private String importo;
	private String importoCommissioni;
	private Date dataScadenzaPagamento;
	private String descrizioneEnte;
	private String tipoTributo;
	private String descrizioneTributo;
	private String codiceFiscaleDebitore;
	private String causale;	
	private String ibanAccreditoTributo;
	
	private List<EsitoCondizioneDTO> esitiCondizioni;
	
	
	public List<EsitoCondizioneDTO> getEsitiCondizioni() {
		return esitiCondizioni;
	}
	
	public void setEsitiCondizioni(List<EsitoCondizioneDTO> condizioni) {
		this.esitiCondizioni = condizioni;
	}
	public String getIdEnte() {
		return idEnte;
	}
	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	
	public String getIdCondPagamento() {
		return idCondPagamento;
	}
	
	public void setIdCondPagamento(String idCondPagamento) {
		this.idCondPagamento = idCondPagamento;
	}
	
	public String getCodice() {
		return returnCode.getChiave();
	}
	
	public String getDescrizione() {
		
		if (descrizione!= null)
			return descrizione;
		
		return returnCode.getDescrizione();
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	public String getIdPagamentoEnte() {
		return idPagamentoEnte;
	}
	public void setIdPagamentoEnte(String idPagamentoEnte) {
		this.idPagamentoEnte = idPagamentoEnte;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	public String getImportoCommissioni() {
		return importoCommissioni;
	}
	public void setImportoCommissioni(String importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}
	public Date getDataScadenzaPagamento() {
		return dataScadenzaPagamento;
	}
	public void setDataScadenzaPagamento(Date dataScadenzaPagamento) {
		this.dataScadenzaPagamento = dataScadenzaPagamento;
	}
	public String getDescrizioneEnte() {
		return descrizioneEnte;
	}
	public void setDescrizioneEnte(String descrizioneEnte) {
		this.descrizioneEnte = descrizioneEnte;
	}
	public String getTipoTributo() {
		return tipoTributo;
	}
	public void setTipoTributo(String tipoTributo) {
		this.tipoTributo = tipoTributo;
	}
	public String getDescrizioneTributo() {
		return descrizioneTributo;
	}
	public void setDescrizioneTributo(String descrizioneTributo) {
		this.descrizioneTributo = descrizioneTributo;
	}
	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}
	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}
	public String getStatoDocumento() {
		return statoDocumento;
	}
	public void setStatoDocumento(String statoDocumento) {
		this.statoDocumento = statoDocumento;
	}
	
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	@Override
	public String toString() {
		return "EsitoOperazionePagamentoDTO [codice=" + getCodice() + ", descrizione=" + getDescrizione() + ", msgId=" + msgId + ", statoDocumento=" + statoDocumento + ", idPendenzaEnte="
				+ idPendenzaEnte + ", idEnte=" + idEnte + ", cdTrbEnte=" + cdTrbEnte + ", idCondPagamento=" + idCondPagamento + ", idPagamentoEnte=" + idPagamentoEnte
				+ ", importo=" + importo + ", importoCommissioni=" + importoCommissioni + ", dataScadenzaPagamento=" + dataScadenzaPagamento + ", descrizioneEnte="
				+ descrizioneEnte + ", tipoTributo=" + tipoTributo + ", descrizioneTributo=" + descrizioneTributo + ", codiceFiscaleDebitore=" + codiceFiscaleDebitore + "]";
	}
	
	public String getIbanAccreditoTributo() {		
		return ibanAccreditoTributo;
	}
	
	public void setIbanAccreditoTributo(String ibanAccreditoTributo) {
		this.ibanAccreditoTributo = ibanAccreditoTributo;
	}
	
	// TODO PAZZIK VERIFICARE DOVE VIENE USATO QUESTO METODO AUTO GENERATO
	
	public EnumBusinessReturnCodes getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(EnumBusinessReturnCodes returnCode) {
		this.returnCode = returnCode;
	}
	
	public boolean isError(){
		
		return getReturnCode().isError();
	}
	
	public String getLogMessage() {
		return logMessage;
	}
	
	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}
	

	
	
}	
