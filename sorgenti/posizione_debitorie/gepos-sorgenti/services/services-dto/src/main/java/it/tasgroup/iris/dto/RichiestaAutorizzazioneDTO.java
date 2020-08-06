package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumCodificaAllegato;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RichiestaAutorizzazioneDTO implements Serializable{
	
	private TestataMessaggioDTO testata; 
	private RichiestaAutorizzazioneCBILLDTO richiestaCBILLDTO;
	private String canale;
	private String codicePagamentoIris;
	private String codTransazionePSP;
	private String esito;
	private String errore;
	private MultaDTO multa;
	private List<PendenzaDTO> pendenze = new ArrayList<PendenzaDTO>();
	private BigDecimal totalAmount;	
	private EnumCodificaAllegato codificaRicevuta = EnumCodificaAllegato.PDF;
	private String identificativoPSP;
	private String identificativoCanalePSP;
	private String identificativoIntermediarioPSP;
	private String iuv;
	
	private String flagOpposizione730;
	
	private boolean annullaPagamentiInCorsoStessoCanale;
	
	// CODICI PROVENIENTI DA CLIENT ESTERNI AL WS
	private String extTransactionId;	
	private String extCodPagamento;
	private String utenteCreatore;
	
	private List<RichiestaAUPPerListaCodiciDTO> listaCodici = new ArrayList<RichiestaAUPPerListaCodiciDTO>();

	
	public String getCodicePagamentoIris() {
		return codicePagamentoIris;
	}
	public void setCodicePagamentoIris(String idDocumento) {
		this.codicePagamentoIris = idDocumento;
	}
	
	public String getEsito() {
		return esito;
	}
	public void setEsito(String esito) {
		this.esito = esito;
	}
	public String getErrore() {
		return errore;
	}
	public void setErrore(String errore) {
		this.errore = errore;
	}
	public TestataMessaggioDTO getTestata() {
		return testata;
	}
	public void setTestata(TestataMessaggioDTO testata) {
		this.testata = testata;
	}
	
	public String getCanale() {
		return canale;
	}
	public void setCanale(String canale) {
		this.canale = canale;
	}
	public MultaDTO getMulta() {
		return multa;
	}
	public void setMulta(MultaDTO multa) {
		this.multa = multa;
	}
	public List<PendenzaDTO> getPendenze() {
		return pendenze;
	}
	public void setPendenze(List<PendenzaDTO> pendenze) {
		this.pendenze = pendenze;
	}
	
	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	
	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the flagOpposizione730
	 */
	public String getFlagOpposizione730() {
		return flagOpposizione730;
	}
	
	/**
	 * @param flagOpposizione730 the totalAmount to set
	 */
	public void setFlagOpposizione730(String flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}
	
	public RichiestaAutorizzazioneCBILLDTO getRichiestaCBILLDTO() {
		return richiestaCBILLDTO;
	}
	public void setRichiestaCBILLDTO(
			RichiestaAutorizzazioneCBILLDTO richiestaCBILLDTO) {
		this.richiestaCBILLDTO = richiestaCBILLDTO;
	}
	
	public String getExtTransactionId() {
		return extTransactionId;
	}

	public void setExtTransactionId(String extTransactionId) {
		this.extTransactionId = extTransactionId;
	}

	public String getExtCodPagamento() {
		return extCodPagamento;
	}

	public void setExtCodPagamento(String extCodPagamento) {
		this.extCodPagamento = extCodPagamento;
	}
	
	public List<RichiestaAUPPerListaCodiciDTO> getListaCodici() {
		return listaCodici;
	}
	public void setListaCodici(List<RichiestaAUPPerListaCodiciDTO> listaCodici) {
		this.listaCodici = listaCodici;
	}
	
	@Override
	public String toString() {
		return "RichiestaAutorizzazioneDTO [testata=" + testata
				+ ", richiestaCBILLDTO=" + richiestaCBILLDTO + ", canale="
				+ canale + ", codicePagamentoIris=" + codicePagamentoIris
				+ ", esito=" + esito + ", errore=" + errore + ", multa="
				+ multa + ", pendenze=" + pendenze + ", totalAmount="
				+ totalAmount + ", extTransactionId=" + extTransactionId
				+ ", extCodPagamento=" + extCodPagamento + ", listaCodici="
				+ listaCodici + "]";
	}
	public String getCodTransazionePSP() {
		return codTransazionePSP;
	}
	public void setCodTransazionePSP(String codTransazionePSP) {
		this.codTransazionePSP = codTransazionePSP;
	}
	public String getUtenteCreatore() {
		return utenteCreatore;
	}
	public void setUtenteCreatore(String utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}
	
	public EnumCodificaAllegato getCodificaRicevuta() {
		return codificaRicevuta;
	}
	public void setCodificaRicevuta(EnumCodificaAllegato codificaRicevuta) {
		this.codificaRicevuta = codificaRicevuta;
	}
	public String getIdentificativoPSP() {
		return identificativoPSP;
	}
	public void setIdentificativoPSP(String identificativoPSP) {
		this.identificativoPSP = identificativoPSP;
	}
	public String getIuv() {
		return iuv;
	}
	public void setIuv(String iuv) {
		this.iuv = iuv;
	}
	public boolean isAnnullaPagamentiInCorsoStessoCanale() {
		return annullaPagamentiInCorsoStessoCanale;
	}
	public void setAnnullaPagamentiInCorsoStessoCanale(
			boolean annullaPagamentiInCorsoStessoCanale) {
		this.annullaPagamentiInCorsoStessoCanale = annullaPagamentiInCorsoStessoCanale;
	}
	public String getIdentificativoCanalePSP() {
		return identificativoCanalePSP;
	}
	public void setIdentificativoCanalePSP(String identificativoCanalePSP) {
		this.identificativoCanalePSP = identificativoCanalePSP;
	}
	public String getIdentificativoIntermediarioPSP() {
		return identificativoIntermediarioPSP;
	}
	public void setIdentificativoIntermediarioPSP(
			String identificativoIntermediarioPSP) {
		this.identificativoIntermediarioPSP = identificativoIntermediarioPSP;
	}
		
}