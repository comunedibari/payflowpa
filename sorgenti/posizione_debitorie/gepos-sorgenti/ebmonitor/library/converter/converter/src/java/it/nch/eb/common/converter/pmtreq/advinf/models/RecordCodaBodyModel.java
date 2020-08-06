
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordCodaBodyModel  implements it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.common.converter.BodyRecordsNumberProvider, it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String recordCount;
		private String advInstrTId;
		private String msgId;
		private String creDtTm;
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
			public String getAdvInstrTId() {
				return advInstrTId;
			}
		
			public void setAdvInstrTId(String advInstrTId) {
				this.advInstrTId = advInstrTId;
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