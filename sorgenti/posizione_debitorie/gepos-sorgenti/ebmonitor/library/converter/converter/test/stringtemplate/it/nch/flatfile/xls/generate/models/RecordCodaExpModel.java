
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordCodaExpModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String srvNm;
		private String idCBISndrf;
		private String idCBIRcvrf;
		private String idMsgTrt;
		private String hdrTrtXmlCrtDt;
		private String idE2EMsg;
		private String hdrSrvXmlCrtDt;
		private String idCBISend;
		private String cbiRefrRecv;
		private String totRec;
	
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
			public String getHdrTrtXmlCrtDt() {
				return hdrTrtXmlCrtDt;
			}
		
			public void setHdrTrtXmlCrtDt(String hdrTrtXmlCrtDt) {
				this.hdrTrtXmlCrtDt = hdrTrtXmlCrtDt;
			}
			public String getIdE2EMsg() {
				return idE2EMsg;
			}
		
			public void setIdE2EMsg(String idE2EMsg) {
				this.idE2EMsg = idE2EMsg;
			}
			public String getHdrSrvXmlCrtDt() {
				return hdrSrvXmlCrtDt;
			}
		
			public void setHdrSrvXmlCrtDt(String hdrSrvXmlCrtDt) {
				this.hdrSrvXmlCrtDt = hdrSrvXmlCrtDt;
			}
			public String getIdCBISend() {
				return idCBISend;
			}
		
			public void setIdCBISend(String idCBISend) {
				this.idCBISend = idCBISend;
			}
			public String getCbiRefrRecv() {
				return cbiRefrRecv;
			}
		
			public void setCbiRefrRecv(String cbiRefrRecv) {
				this.cbiRefrRecv = cbiRefrRecv;
			}
			public String getTotRec() {
				return totRec;
			}
		
			public void setTotRec(String totRec) {
				this.totRec = totRec;
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