
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class Record70Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String instrId;
		private String blank;
		private String pmtMtd;
		private String endToEndId;
		private String blank1;
	
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
			public String getInstrId() {
				return instrId;
			}
		
			public void setInstrId(String instrId) {
				this.instrId = instrId;
			}
			public String getBlank() {
				return blank;
			}
		
			public void setBlank(String blank) {
				this.blank = blank;
			}
			public String getPmtMtd() {
				return pmtMtd;
			}
		
			public void setPmtMtd(String pmtMtd) {
				this.pmtMtd = pmtMtd;
			}
			public String getEndToEndId() {
				return endToEndId;
			}
		
			public void setEndToEndId(String endToEndId) {
				this.endToEndId = endToEndId;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
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