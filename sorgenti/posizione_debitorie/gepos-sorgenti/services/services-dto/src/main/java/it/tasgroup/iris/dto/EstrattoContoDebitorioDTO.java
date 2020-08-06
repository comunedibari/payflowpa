package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EstrattoContoDebitorioDTO implements Serializable{
	
	private TestataMessaggioDTO testata;
	
	private List<String> lsStato;
	
	private Date dataIni;
	private Date dataFin;
	private String codiceFiscaleDebitore;
	private String codiceFiscaleCreditore;
	private Integer annoRiferimento;	
	private String codiceCategoriaDebito;
	private String codiceDebitoCreditore;
	private String codiceSIACreditore;	
	private String deOperazione;	
	private boolean soloPosizioniPagabili = false;
	private boolean extractInProgressPayments = false;
	
	public String getWsCallerType() {
		return wsCallerType;
	}

	public void setWsCallerType(String wsCallerType) {
		this.wsCallerType = wsCallerType;
	}

	private String wsCallerType; //ws cbill oppure ws pai	
	

	public TestataMessaggioDTO getTestata() {
		return testata;
	}

	public void setTestata(TestataMessaggioDTO testata) {
		this.testata = testata;
	}


	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}

	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}
	
	public List<String> getLsStato() {
		return lsStato;
	}

	public void setLsStato(List<String> lsStato) {
		this.lsStato = lsStato;
	}

	public Date getDataIni() {
		return dataIni;
	}

	public void setDataIni(Date dataIni) {
		this.dataIni = dataIni;
	}

	public Date getDataFin() {
		return dataFin;
	}

	public void setDataFin(Date dataFin) {
		this.dataFin = dataFin;
	}

	public String getCodiceSIACreditore() {
		return codiceSIACreditore;
	}

	public void setCodiceSIACreditore(String codiceSIACreditore) {
		this.codiceSIACreditore = codiceSIACreditore;
	}

	public boolean isExtractInProgressPayments() {
		return extractInProgressPayments;
	}

	public void setExtractInProgressPayments(boolean extractInProgressPayments) {
		this.extractInProgressPayments = extractInProgressPayments;
	}
	
	public String getCodiceFiscaleCreditore() {
		return codiceFiscaleCreditore;
	}

	public void setCodiceFiscaleCreditore(String codiceFiscaleCreditore) {
		this.codiceFiscaleCreditore = codiceFiscaleCreditore;
	}
	
	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public String getCodiceCategoriaDebito() {
		return codiceCategoriaDebito;
	}

	public void setCodiceCategoriaDebito(String codiceCategoriaDebito) {
		this.codiceCategoriaDebito = codiceCategoriaDebito;
	}

	public String getCodiceDebitoCreditore() {
		return codiceDebitoCreditore;
	}

	public void setCodiceDebitoCreditore(String codiceDebitoCreditore) {
		this.codiceDebitoCreditore = codiceDebitoCreditore;
	}

	public boolean isSoloPosizioniPagabili() {
		return soloPosizioniPagabili;
	}

	public void setSoloPosizioniPagabili(boolean soloPosizioniPagabili) {
		this.soloPosizioniPagabili = soloPosizioniPagabili;
	}
	
	public boolean isCBILL(){
		
//		return getTestata().getSenderSil().equals("CBILL");
		return this.getWsCallerType().equals("CBILL");
	}
	
	public boolean isPAI(){
		
//		return getTestata().getSenderSil().equals("PUNTOSI") || getTestata().getSenderSil().equals("PUNTOGI");
		return this.getWsCallerType().equals("PAI");
	}
	
	@Override
	public String toString() {
		return "EstrattoContoDebitorioDTO [testata=" + testata + ", lsStato="
				+ lsStato + ", dataIni=" + dataIni + ", dataFin=" + dataFin
				+ ", codiceFiscaleDebitore=" + codiceFiscaleDebitore
				+ ", codiceFiscaleCreditore=" + codiceFiscaleCreditore
				+ ", annoRiferimento=" + annoRiferimento
				+ ", codiceCategoriaDebito=" + codiceCategoriaDebito
				+ ", codiceDebitoCreditore=" + codiceDebitoCreditore
				+ ", codiceSIACreditore=" + codiceSIACreditore
				+ ", soloPosizioniPagabili=" + soloPosizioniPagabili
				+ ", extractInProgressPayments=" + extractInProgressPayments
				+ "]";
	}

	public String getDeOperazione() {
		return deOperazione;
	}

	public void setDeOperazione(String deOperazione) {
		this.deOperazione = deOperazione;
	}

}
