
package it.nch.eb.ubi.converter.sepa2000.models;

import it.nch.eb.common.utils.StringUtils;


public class Sepa2000RecordTestaModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String sia;
		private String abiRicevente;
		private String creDtTm;
		private String uid;
		private String blank1;
		private String msgId;
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
			public String getSia() {
				return sia;
			}
		
			public void setSia(String sia) {
				this.sia = sia;
			}
			public String getAbiRicevente() {
				return abiRicevente;
			}
		
			public void setAbiRicevente(String abiRicevente) {
				this.abiRicevente = abiRicevente;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getUid() {
				return uid;
			}
		
			public void setUid(String uid) {
				this.uid = uid;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getMsgId() {
				return msgId;
			}
		
			public void setMsgId(String msgId) {
				this.msgId = msgId;
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