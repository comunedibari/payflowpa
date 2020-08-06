
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record08Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String iban;
		private String nm;
		private String assRate;
		private String assAmt;
		private String ccy;
	
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
			public String getIban() {
				return iban;
			}
		
			public void setIban(String iban) {
				this.iban = iban;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getAssRate() {
				return assRate;
			}
		
			public void setAssRate(String assRate) {
				this.assRate = assRate;
			}
			public String getAssAmt() {
				return assAmt;
			}
		
			public void setAssAmt(String assAmt) {
				this.assAmt = assAmt;
			}
			public String getCcy() {
				return ccy;
			}
		
			public void setCcy(String ccy) {
				this.ccy = ccy;
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