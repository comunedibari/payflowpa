
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record01Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String bic;
		private String nm;
		private String sndrRef;
		private String relRef;
		private String bic1;
		private String desc;
	
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
			public String getBic() {
				return bic;
			}
		
			public void setBic(String bic) {
				this.bic = bic;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getSndrRef() {
				return sndrRef;
			}
		
			public void setSndrRef(String sndrRef) {
				this.sndrRef = sndrRef;
			}
			public String getRelRef() {
				return relRef;
			}
		
			public void setRelRef(String relRef) {
				this.relRef = relRef;
			}
			public String getBic1() {
				return bic1;
			}
		
			public void setBic1(String bic1) {
				this.bic1 = bic1;
			}
			public String getDesc() {
				return desc;
			}
		
			public void setDesc(String desc) {
				this.desc = desc;
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