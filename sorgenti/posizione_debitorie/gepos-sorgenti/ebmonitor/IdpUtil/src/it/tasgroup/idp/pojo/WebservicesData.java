package it.tasgroup.idp.pojo;

import java.util.Date;
import java.util.List;

public class WebservicesData implements MonitoringDataMXBean  {
		

	public String beanClassName = null;
	
	public String businessMethodName = null;
	
	public long timestamp_start;
	
	public long total_time;
	
	//da verificare
	private String mittente;
	private String ricevente;
	//altri campi chiave per insert nel casellario
	private String nomeSupporto;
	private String dataRiferimento;
	private Date dateRiferimento;
	private String tipoFlusso;
	//ad uso controllo abilitazioni mittente/sistemaMittente
	boolean enteFound = false;
	boolean silFound = false;	
	//ad uso gestione warning
	String warning;
	
	//ad uso rendicontazione PSP
	private byte[] rendicontazionePsp;	
	//ad uso esito blocco
	private boolean esitoBlocco = false;
	//ad uso info su messaggio completo
	private boolean messaggioCompleto = false;
	//ad uso info su memorizzazione su casellario info
	private boolean insertCasellario = false;
	//ad uso info su messaggio validato e memorizzato su casellario info
	private boolean messaggioValido = false;
	//ad uso info su doppia memorizzazione su casellario info
	private boolean casellarioGiaElaborato = false;	
	
	private String messaggioErrore;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return this.mittente 
				+ "--" + this.ricevente 
				+ "--" + this.getBeanClassName() 
				+ "--" + this.numRecord 
				+ "--" + this.getTotal_time();
	}

	private String receiversys;	
	
	private int numRecord = 0;	
	

	public String getReceiversys() {
		return receiversys;
	}

	public void setReceiversys(String receiversys) {
		this.receiversys = receiversys;
	}

	public int getNumRecord() {
		return numRecord;
	}

	public void setNumRecord(int numRecord) {
		this.numRecord = numRecord;
	}

	
	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
	}

	public String getBusinessMethodName() {
		return businessMethodName;
	}

	public void setBusinessMethodName(String businessMethodName) {
		this.businessMethodName = businessMethodName;
	}

	public long getTimestamp_start() {
		return timestamp_start;
	}

	public void setTimestamp_start(long timestampStart) {
		timestamp_start = timestampStart;
	}

	public long getTotal_time() {
		return total_time;
	}

	public void setTotal_time(long totalTime) {
		total_time = totalTime;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

	public String getMittente() {
		return mittente;
	}

	public void setMittente(String mittente) {
		this.mittente = mittente;
	}

	public String getRicevente() {
		return ricevente;
	}

	public void setRicevente(String ricevente) {
		this.ricevente = ricevente;
	}

	public byte[] getRendicontazionePsp() {
		return rendicontazionePsp;
	}

	public void setRendicontazionePsp(byte[] rendicontazionePsp) {
		this.rendicontazionePsp = rendicontazionePsp;
	}

	public String getNomeSupporto() {
		return nomeSupporto;
	}

	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}

	public String getDataRiferimento() {
		return dataRiferimento;
	}

	public void setDataRiferimento(String dataRiferimento) {
		this.dataRiferimento = dataRiferimento;
	}

	public String getTipoFlusso() {
		return tipoFlusso;
	}

	public void setTipoFlusso(String tipoFlusso) {
		this.tipoFlusso = tipoFlusso;
	}

	public boolean isEsitoBlocco() {
		return esitoBlocco;
	}

	public void setEsitoBlocco(boolean esitoBlocco) {
		this.esitoBlocco = esitoBlocco;
	}

	public boolean isMessaggioCompleto() {
		return messaggioCompleto;
	}

	public void setMessaggioCompleto(boolean messaggioCompleto) {
		this.messaggioCompleto = messaggioCompleto;
	}

	public boolean isMessaggioValido() {
		return messaggioValido;
	}

	public void setMessaggioValido(boolean messaggioValido) {
		this.messaggioValido = messaggioValido;
	}

	public boolean isInsertCasellario() {
		return insertCasellario;
	}

	public void setInsertCasellario(boolean insertCasellario) {
		this.insertCasellario = insertCasellario;
	}

	public boolean isEnteFound() {
		return enteFound;
	}

	public void setEnteFound(boolean enteFound) {
		this.enteFound = enteFound;
	}

	public boolean isSilFound() {
		return silFound;
	}

	public void setSilFound(boolean silFound) {
		this.silFound = silFound;
	}

	public String getWarning() {
		return warning;
	}

	public void setWarning(String warning) {
		this.warning = warning;
	}

	public Date getDateRiferimento() {
		return dateRiferimento;
	}

	public void setDateRiferimento(Date dateRiferimento) {
		this.dateRiferimento = dateRiferimento;
	}

	public boolean isCasellarioGiaElaborato() {
		return casellarioGiaElaborato;
	}

	public void setCasellarioGiaElaborato(boolean casellarioGiaElaborato) {
		this.casellarioGiaElaborato = casellarioGiaElaborato;
	}


	
	
		

}
