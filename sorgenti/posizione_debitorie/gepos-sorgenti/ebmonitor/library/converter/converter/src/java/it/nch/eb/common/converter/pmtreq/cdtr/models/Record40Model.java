
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record40Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String amt;
		private String ccy;
		private String reqdExctnDt;
		private String prtry;
		private String prtry1;
		private String ctgyPurp;
		private String pmtMtd;
		private String rmtId;
		private String rmtLctnMtd;
		private String rmtLctnElctrncAdr;
	
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
			public String getRec_count() {
				return rec_count;
			}

			public void setRec_count(String rec_count) {
				this.rec_count = rec_count;
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
			public String getPrtry() {
				return prtry;
			}

			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getPrtry1() {
				return prtry1;
			}

			public void setPrtry1(String prtry1) {
				this.prtry1 = prtry1;
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
			public String getRmtId() {
				return rmtId;
			}

			public void setRmtId(String rmtId) {
				this.rmtId = rmtId;
			}
			public String getRmtLctnMtd() {
				return rmtLctnMtd;
			}

			public void setRmtLctnMtd(String rmtLctnMtd) {
				this.rmtLctnMtd = rmtLctnMtd;
			}
			public String getRmtLctnElctrncAdr() {
				return rmtLctnElctrncAdr;
			}

			public void setRmtLctnElctrncAdr(String rmtLctnElctrncAdr) {
				this.rmtLctnElctrncAdr = rmtLctnElctrncAdr;
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