
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordCodaBodyModel  implements it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.common.converter.BodyRecordsNumberProvider, it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRecord;
		private String recordCount;
		private String msgId;
		private String idE2E;
		private String msgQual;
		private String creDtTm;
		private String id;
		private String bodyRowsNumber;
	
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
			public String getMsgId() {
				return msgId;
			}
		
			public void setMsgId(String msgId) {
				this.msgId = msgId;
			}
			public String getIdE2E() {
				return idE2E;
			}
		
			public void setIdE2E(String idE2E) {
				this.idE2E = idE2E;
			}
			public String getMsgQual() {
				return msgQual;
			}
		
			public void setMsgQual(String msgQual) {
				this.msgQual = msgQual;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
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