
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordPCModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String id;
		private String prtry;
		private String creDtTm;
		private String timestamp;
		private String blank;
		private String msgId;
		private String creDtTm1;
		private String blank1;
		private String filler1;
		private String blank2;
	
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
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getTimestamp() {
				return timestamp;
			}
		
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}
			public String getBlank() {
				return blank;
			}
		
			public void setBlank(String blank) {
				this.blank = blank;
			}
			public String getMsgId() {
				return msgId;
			}
		
			public void setMsgId(String msgId) {
				this.msgId = msgId;
			}
			public String getCreDtTm1() {
				return creDtTm1;
			}
		
			public void setCreDtTm1(String creDtTm1) {
				this.creDtTm1 = creDtTm1;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getFiller1() {
				return filler1;
			}
		
			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getBlank2() {
				return blank2;
			}
		
			public void setBlank2(String blank2) {
				this.blank2 = blank2;
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