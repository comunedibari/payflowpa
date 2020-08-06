
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordCodaModel  implements it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.common.converter.TotalRecordsNumberProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String srvNm;
		private String idCBISndrf;
		private String idCBIRcvrf;
		private String idMsgTrt;
		private String xMLCrtDt;
		private String idE2EMsg;
		private String xMLCrtDt1;
		private String idCBISend;
		private String idCBIRecv;
		private String totalRowsNumber;
	
	private int	lineNumber;
	
		
			public String getFiller() {
				return filler;
			}
		
			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipoRecord() {
				return tipoRecord;
			}
		
			public void setTipoRecord(String tipoRecord) {
				this.tipoRecord = tipoRecord;
			}
			public String getSrvNm() {
				return srvNm;
			}
		
			public void setSrvNm(String srvNm) {
				this.srvNm = srvNm;
			}
			public String getIdCBISndrf() {
				return idCBISndrf;
			}
		
			public void setIdCBISndrf(String idCBISndrf) {
				this.idCBISndrf = idCBISndrf;
			}
			public String getIdCBIRcvrf() {
				return idCBIRcvrf;
			}
		
			public void setIdCBIRcvrf(String idCBIRcvrf) {
				this.idCBIRcvrf = idCBIRcvrf;
			}
			public String getIdMsgTrt() {
				return idMsgTrt;
			}
		
			public void setIdMsgTrt(String idMsgTrt) {
				this.idMsgTrt = idMsgTrt;
			}
			public String getXMLCrtDt() {
				return xMLCrtDt;
			}
		
			public void setXMLCrtDt(String xMLCrtDt) {
				this.xMLCrtDt = xMLCrtDt;
			}
			public String getIdE2EMsg() {
				return idE2EMsg;
			}
		
			public void setIdE2EMsg(String idE2EMsg) {
				this.idE2EMsg = idE2EMsg;
			}
			public String getXMLCrtDt1() {
				return xMLCrtDt1;
			}
		
			public void setXMLCrtDt1(String xMLCrtDt1) {
				this.xMLCrtDt1 = xMLCrtDt1;
			}
			public String getIdCBISend() {
				return idCBISend;
			}
		
			public void setIdCBISend(String idCBISend) {
				this.idCBISend = idCBISend;
			}
			public String getIdCBIRecv() {
				return idCBIRecv;
			}
		
			public void setIdCBIRecv(String idCBIRecv) {
				this.idCBIRecv = idCBIRecv;
			}
			public String getTotalRowsNumber() {
				return totalRowsNumber;
			}
		
			public void setTotalRowsNumber(String totalRowsNumber) {
				this.totalRowsNumber = totalRowsNumber;
			}
	
	public int getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
	}
	
}