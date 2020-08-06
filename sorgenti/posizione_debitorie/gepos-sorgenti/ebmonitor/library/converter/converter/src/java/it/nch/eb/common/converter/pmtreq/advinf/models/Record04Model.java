
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record04Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String ccy;
		private String sndrChg;
	
	private int	lineNumber;
	
		
			public String getFiller() {
				return filler;
			}
		
			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipo_record() {
				return tipo_record;
			}
		
			public void setTipo_record(String tipo_record) {
				this.tipo_record = tipo_record;
			}
			public String getRecordCount() {
				return recordCount;
			}
		
			public void setRecordCount(String recordCount) {
				this.recordCount = recordCount;
			}
			public String getCcy() {
				return ccy;
			}
		
			public void setCcy(String ccy) {
				this.ccy = ccy;
			}
			public String getSndrChg() {
				return sndrChg;
			}
		
			public void setSndrChg(String sndrChg) {
				this.sndrChg = sndrChg;
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