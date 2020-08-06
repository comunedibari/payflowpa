
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record11Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String dbtCdtRptgInd;
		private String cd;
		private String ccy;
		private String amt;
		private String inf;
	
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
			public String getDbtCdtRptgInd() {
				return dbtCdtRptgInd;
			}
		
			public void setDbtCdtRptgInd(String dbtCdtRptgInd) {
				this.dbtCdtRptgInd = dbtCdtRptgInd;
			}
			public String getCd() {
				return cd;
			}
		
			public void setCd(String cd) {
				this.cd = cd;
			}
			public String getCcy() {
				return ccy;
			}
		
			public void setCcy(String ccy) {
				this.ccy = ccy;
			}
			public String getAmt() {
				return amt;
			}
		
			public void setAmt(String amt) {
				this.amt = amt;
			}
			public String getInf() {
				return inf;
			}
		
			public void setInf(String inf) {
				this.inf = inf;
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