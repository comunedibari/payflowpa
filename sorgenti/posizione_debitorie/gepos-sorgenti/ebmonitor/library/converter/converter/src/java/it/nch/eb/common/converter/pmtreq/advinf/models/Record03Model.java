
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record03Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String ccy;
		private String instdAmt;
		private String ccy1;
		private String amt;
		private String xcghRate;
		private String chrgBr;
		private String vlDt;
		private String rmtInf;
		private String cd;
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
			public String getCcy() {
				return ccy;
			}
		
			public void setCcy(String ccy) {
				this.ccy = ccy;
			}
			public String getInstdAmt() {
				return instdAmt;
			}
		
			public void setInstdAmt(String instdAmt) {
				this.instdAmt = instdAmt;
			}
			public String getCcy1() {
				return ccy1;
			}
		
			public void setCcy1(String ccy1) {
				this.ccy1 = ccy1;
			}
			public String getAmt() {
				return amt;
			}
		
			public void setAmt(String amt) {
				this.amt = amt;
			}
			public String getXcghRate() {
				return xcghRate;
			}
		
			public void setXcghRate(String xcghRate) {
				this.xcghRate = xcghRate;
			}
			public String getChrgBr() {
				return chrgBr;
			}
		
			public void setChrgBr(String chrgBr) {
				this.chrgBr = chrgBr;
			}
			public String getVlDt() {
				return vlDt;
			}
		
			public void setVlDt(String vlDt) {
				this.vlDt = vlDt;
			}
			public String getRmtInf() {
				return rmtInf;
			}
		
			public void setRmtInf(String rmtInf) {
				this.rmtInf = rmtInf;
			}
			public String getCd() {
				return cd;
			}
		
			public void setCd(String cd) {
				this.cd = cd;
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