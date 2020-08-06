package it.tasgroup.idp.pojo;

import java.util.List;

public class StoricoData implements MonitoringDataMXBean  {

	public String beanClassName = null;

	public String businessMethodName = null;

	public long timestamp_start;

	public long total_time;

	private String tipoOperazione;
	private String supporto;
	private String prodotto;
	private String processo;
	
	private String esito;

	//ad uso rendicontazione
	private List listNomiSupporto;

//	private String idRct;
//
//	private String idRiconciliazione;
//
//	private String idBonifico;
//
//	private String idDisposizione;


//	@Override
//	public String toString() {
//		return this.getIdRct()
//				+ "--" + this.getIdRiconciliazione()
//				+ "--" + this.getIdBonifico()
//				+ "--" + this.getBeanClassName()
//				+ "--" + this.numRecord
//				+ "--" + this.getTotal_time();
//		}

//	public String getIdRct() {
//		return idRct;
//	}

//	public void setIdRct(String idRct) {
//		this.idRct = idRct;
//	}
//
//	public String getIdRiconciliazione() {
//		return idRiconciliazione;
//	}
//
//	public void setIdRiconciliazione(String idRiconciliazione) {
//		this.idRiconciliazione = idRiconciliazione;
//	}
//
//	public String getIdBonifico() {
//		return idBonifico;
//	}
//
//	public void setIdBonifico(String idBonifico) {
//		this.idBonifico = idBonifico;
//	}
//
//	public String getIdDisposizione() {
//		return idDisposizione;
//	}
//
//	public void setIdDisposizione(String idDisposizione) {
//		this.idDisposizione = idDisposizione;
//	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public List getListNomiSupporto() {
		return listNomiSupporto;
	}

	public void setListNomiSupporto(List listNomiSupporto) {
		this.listNomiSupporto = listNomiSupporto;
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

	public String getProcesso() {
		return processo;
	}

	public void setProcesso(String processo) {
		this.processo = processo;
	}

	public String getSupporto() {
		return supporto;
	}

	public void setSupporto(String supporto) {
		this.supporto = supporto;
	}

}
