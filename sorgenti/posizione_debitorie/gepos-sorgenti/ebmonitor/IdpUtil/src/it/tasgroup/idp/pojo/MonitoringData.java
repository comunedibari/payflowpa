package it.tasgroup.idp.pojo;

import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpallineamentopendenze.IdpAllineamentoPendenzeEnteOTFEsito;
import it.toscana.rete.cart.servizi.iris_1_1.ws.proxy.idpinformativapagamento.IdpVerificaStatoPagamentiEsito;

import java.util.List;

public class MonitoringData implements MonitoringDataMXBean  {
	
	private static final long serialVersionUID = 1L;

//    POSIZIONE_NON_PRESENTE("Posizione non presente"),
//    POSIZIONE_NON_PAGABILE("Posizione non pagabile"),
//    PAGAMENTO_NON_ESEGUITO("Pagamento non eseguito"),
//    PAGAMENTO_ESEGUITO("Pagamento eseguito");
	
	public enum EsitoPagamento {
		POSIZIONE_NON_PRESENTE, PAGAMENTO_ESEGUITO, PAGAMENTO_NON_ESEGUITO, POSIZIONE_NON_PAGABILE, PLEASE_REFRESH_DATA
	}
	

	public String beanClassName = null;
	
	public String businessMethodName = null;
	
	public long timestamp_start;
	
	public long total_time;
	
	private String e2emsgid;

	private String senderid;

	private String sendersys;
	
	private String receiverid;
	
	//ad uso rendicontazione
	private List<String> filesList;
	//ad uso TimerController
	private List<TimerData> timerList;	
	
	//ad uso gestione flussi BT
	private String nomeFile;
	private String tipoOperazione;
	private String prodotto;
	
	//ad uso esclusivo RFC127Sincrono
	private String esito;
	//ad uso esclusivo RFC145Sincrono
	private EsitoPagamento esito145;
	
	//ad uso esclusivo RFC127Sincrono, per nuova interazione WS
	private IdpAllineamentoPendenzeEnteOTFEsito esitoAllineamentoPendenze;
	//ad uso esclusivo VerificaStatoPagamento, per nuova interazione WS
	private IdpVerificaStatoPagamentiEsito verificaStatoPagamenti;	
	
	private String messaggioErrore;
	
	//ad uso controlli di OTFManager
	private String idPendenzaEnte;
	private String cdTrbEnte;
	private String idEnte;	
	private boolean pendenzaTrovata;
	//ad uso controlli su tributo replaceable by OTF
	private boolean tributoReplaceable;



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return this.getE2emsgid() 
				+ "--" + this.senderid 
				+ "--" + this.sendersys 
				+ "--" + this.getBeanClassName() 
				+ "--" + this.numRecord 
				+ "--" + this.getTotal_time();
	}

	private String receiversys;	
	
	private int numRecord = 0;	
	
	public String getE2emsgid() {
		return e2emsgid;
	}

	public void setE2emsgid(String e2emsgid) {
		this.e2emsgid = e2emsgid;
	}

	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getSendersys() {
		return sendersys;
	}

	public void setSendersys(String sendersys) {
		this.sendersys = sendersys;
	}

	public String getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}

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

	public List<String> getFilesList() {
		return filesList;
	}

	public void setFilesList(List<String> filesList) {
		this.filesList = filesList;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public String getTipoOperazione() {
		return tipoOperazione;
	}

	public void setTipoOperazione(String tipoOperazione) {
		this.tipoOperazione = tipoOperazione;
	}
	
	public String getProdotto() {
		return prodotto;
	}

	public void setProdotto(String prodotto) {
		this.prodotto = prodotto;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public EsitoPagamento getEsito145() {
		return esito145;
	}

	public void setEsito145(EsitoPagamento esito145) {
		this.esito145 = esito145;
	}

	public String getMessaggioErrore() {
		return messaggioErrore;
	}

	public void setMessaggioErrore(String messaggioErrore) {
		this.messaggioErrore = messaggioErrore;
	}

	public List<TimerData> getTimerList() {
		return timerList;
	}

	public void setTimerList(List<TimerData> timerList) {
		this.timerList = timerList;
	}

	public IdpVerificaStatoPagamentiEsito getVerificaStatoPagamenti() {
		return verificaStatoPagamenti;
	}

	public void setVerificaStatoPagamenti(IdpVerificaStatoPagamentiEsito verificaStatoPagamenti) {
		this.verificaStatoPagamenti = verificaStatoPagamenti;
	}

	public IdpAllineamentoPendenzeEnteOTFEsito getEsitoAllineamentoPendenze() {
		return esitoAllineamentoPendenze;
	}

	public void setEsitoAllineamentoPendenze(
			IdpAllineamentoPendenzeEnteOTFEsito esitoAllineamentoPendenze) {
		this.esitoAllineamentoPendenze = esitoAllineamentoPendenze;
	}
	
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

	public String getCdTrbEnte() {
		return cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}	
	
	public boolean isPendenzaTrovata() {
		return pendenzaTrovata;
	}

	public void setPendenzaTrovata(boolean pendenzaTrovata) {
		this.pendenzaTrovata = pendenzaTrovata;
	}
	
	public boolean isTributoReplaceable() {
		return tributoReplaceable;
	}

	public void setTributoReplaceable(boolean tributoReplaceable) {
		this.tributoReplaceable = tributoReplaceable;
	}	
}
