
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record01Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRecord;
		private String recordCount;
		private String orgnlMsgId;
		private String orgnlCreDtTm;
		private String grpSts;
		private String dtldNbOfTxs;
		private String dtldCtrlSum;
	
	private int	lineNumber;
	
		
			public String getFiiller() {
				return fiiller;
			}
		
			public void setFiiller(String fiiller) {
				this.fiiller = fiiller;
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
			public String getOrgnlMsgId() {
				return orgnlMsgId;
			}
		
			public void setOrgnlMsgId(String orgnlMsgId) {
				this.orgnlMsgId = orgnlMsgId;
			}
			public String getOrgnlCreDtTm() {
				return orgnlCreDtTm;
			}
		
			public void setOrgnlCreDtTm(String orgnlCreDtTm) {
				this.orgnlCreDtTm = orgnlCreDtTm;
			}
			public String getGrpSts() {
				return grpSts;
			}
		
			public void setGrpSts(String grpSts) {
				this.grpSts = grpSts;
			}
			public String getDtldNbOfTxs() {
				return dtldNbOfTxs;
			}
		
			public void setDtldNbOfTxs(String dtldNbOfTxs) {
				this.dtldNbOfTxs = dtldNbOfTxs;
			}
			public String getDtldCtrlSum() {
				return dtldCtrlSum;
			}
		
			public void setDtldCtrlSum(String dtldCtrlSum) {
				this.dtldCtrlSum = dtldCtrlSum;
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