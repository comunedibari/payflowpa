
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordTestaExpModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String srvNm;
		private String idCBISndrf;
		private String idCBIRcvrf;
		private String idMsgTrt;
		private String xmlCrtDt;
		private String idE2EMsg;
		private String srvInfoXmlCrtDt;
		private String idCBISend;
		private String idCBIRecv;
		private String usrBnk;
		private String diagVers;
		private String chkSbj;
		private String chkDt;
		private String srvBdyNb;
		private String version;
		private String rowsNumber;
	
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
			public String getXmlCrtDt() {
				return xmlCrtDt;
			}
		
			public void setXmlCrtDt(String xmlCrtDt) {
				this.xmlCrtDt = xmlCrtDt;
			}
			public String getIdE2EMsg() {
				return idE2EMsg;
			}
		
			public void setIdE2EMsg(String idE2EMsg) {
				this.idE2EMsg = idE2EMsg;
			}
			public String getSrvInfoXmlCrtDt() {
				return srvInfoXmlCrtDt;
			}
		
			public void setSrvInfoXmlCrtDt(String srvInfoXmlCrtDt) {
				this.srvInfoXmlCrtDt = srvInfoXmlCrtDt;
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
			public String getUsrBnk() {
				return usrBnk;
			}
		
			public void setUsrBnk(String usrBnk) {
				this.usrBnk = usrBnk;
			}
			public String getDiagVers() {
				return diagVers;
			}
		
			public void setDiagVers(String diagVers) {
				this.diagVers = diagVers;
			}
			public String getChkSbj() {
				return chkSbj;
			}
		
			public void setChkSbj(String chkSbj) {
				this.chkSbj = chkSbj;
			}
			public String getChkDt() {
				return chkDt;
			}
		
			public void setChkDt(String chkDt) {
				this.chkDt = chkDt;
			}
			public String getSrvBdyNb() {
				return srvBdyNb;
			}
		
			public void setSrvBdyNb(String srvBdyNb) {
				this.srvBdyNb = srvBdyNb;
			}
			public String getVersion() {
				return version;
			}
		
			public void setVersion(String version) {
				this.version = version;
			}
			public String getRowsNumber() {
				return rowsNumber;
			}
		
			public void setRowsNumber(String rowsNumber) {
				this.rowsNumber = rowsNumber;
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