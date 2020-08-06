
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class Record50Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String instrId;
		private String ustrd;
		private String filler1;
	
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
			public String getUstrd() {
				return ustrd;
			}
		
			public void setUstrd(String ustrd) {
				this.ustrd = ustrd;
			}
			public String getFiller1() {
				return filler1;
			}
		
			public void setFiller1(String filler1) {
				this.filler1 = filler1;
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