
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordTestaBodyModel  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.BodyRecordsNumberProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String recordCount;
		private String msgId;
		private String creDtTm;
		private String nbOfTxs;
		private String ctrlSum;
		private String grpg;
		private String nm;
		private String taxIdNb;
		private String id;
		private String issr;
		private String sia;
		private String prtry;
		private String bodyRowsNumber;
	
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
			public String getRecordCount() {
				return recordCount;
			}
		
			public void setRecordCount(String recordCount) {
				this.recordCount = recordCount;
			}
			public String getMsgId() {
				return msgId;
			}
		
			public void setMsgId(String msgId) {
				this.msgId = msgId;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getNbOfTxs() {
				return nbOfTxs;
			}
		
			public void setNbOfTxs(String nbOfTxs) {
				this.nbOfTxs = nbOfTxs;
			}
			public String getCtrlSum() {
				return ctrlSum;
			}
		
			public void setCtrlSum(String ctrlSum) {
				this.ctrlSum = ctrlSum;
			}
			public String getGrpg() {
				return grpg;
			}
		
			public void setGrpg(String grpg) {
				this.grpg = grpg;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getTaxIdNb() {
				return taxIdNb;
			}
		
			public void setTaxIdNb(String taxIdNb) {
				this.taxIdNb = taxIdNb;
			}
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
			}
			public String getIssr() {
				return issr;
			}
		
			public void setIssr(String issr) {
				this.issr = issr;
			}
			public String getSia() {
				return sia;
			}
		
			public void setSia(String sia) {
				this.sia = sia;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getBodyRowsNumber() {
				return bodyRowsNumber;
			}
		
			public void setBodyRowsNumber(String bodyRowsNumber) {
				this.bodyRowsNumber = bodyRowsNumber;
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