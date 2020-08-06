
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record40Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRecord;
		private String recordCount;
		private String amt;
		private String ccy;
		private String reqdExctnDt;
		private String svcLvl_prtry;
		private String lclInstrm_prtry;
		private String ctgyPurp;
		private String pmtMtd;
		private String blank1;
		private String blank2;
		private String blank3;
	
	private int	lineNumber;
	
		
			public String getFiiller() {
				return fiiller;
			}
		
			public void setFiiller(String fiiller) {
				this.fiiller = fiiller;
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
			public String getAmt() {
				return amt;
			}
		
			public void setAmt(String amt) {
				this.amt = amt;
			}
			public String getCcy() {
				return ccy;
			}
		
			public void setCcy(String ccy) {
				this.ccy = ccy;
			}
			public String getReqdExctnDt() {
				return reqdExctnDt;
			}
		
			public void setReqdExctnDt(String reqdExctnDt) {
				this.reqdExctnDt = reqdExctnDt;
			}
			public String getSvcLvl_prtry() {
				return svcLvl_prtry;
			}
		
			public void setSvcLvl_prtry(String svcLvl_prtry) {
				this.svcLvl_prtry = svcLvl_prtry;
			}
			public String getLclInstrm_prtry() {
				return lclInstrm_prtry;
			}
		
			public void setLclInstrm_prtry(String lclInstrm_prtry) {
				this.lclInstrm_prtry = lclInstrm_prtry;
			}
			public String getCtgyPurp() {
				return ctgyPurp;
			}
		
			public void setCtgyPurp(String ctgyPurp) {
				this.ctgyPurp = ctgyPurp;
			}
			public String getPmtMtd() {
				return pmtMtd;
			}
		
			public void setPmtMtd(String pmtMtd) {
				this.pmtMtd = pmtMtd;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getBlank2() {
				return blank2;
			}
		
			public void setBlank2(String blank2) {
				this.blank2 = blank2;
			}
			public String getBlank3() {
				return blank3;
			}
		
			public void setBlank3(String blank3) {
				this.blank3 = blank3;
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