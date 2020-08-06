
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record62Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String cd;
		private String prtry;
		private String issr;
		private String rfrdDocNb;
		private String rfrdDocRltdDt;
		private String cd1;
		private String prtry1;
		private String issr1;
		private String cdtrRef;
		private String addtlRmtInf;
	
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
			public String getCd() {
				return cd;
			}

			public void setCd(String cd) {
				this.cd = cd;
			}
			public String getPrtry() {
				return prtry;
			}

			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getIssr() {
				return issr;
			}

			public void setIssr(String issr) {
				this.issr = issr;
			}
			public String getRfrdDocNb() {
				return rfrdDocNb;
			}

			public void setRfrdDocNb(String rfrdDocNb) {
				this.rfrdDocNb = rfrdDocNb;
			}
			public String getRfrdDocRltdDt() {
				return rfrdDocRltdDt;
			}

			public void setRfrdDocRltdDt(String rfrdDocRltdDt) {
				this.rfrdDocRltdDt = rfrdDocRltdDt;
			}
			public String getCd1() {
				return cd1;
			}

			public void setCd1(String cd1) {
				this.cd1 = cd1;
			}
			public String getPrtry1() {
				return prtry1;
			}

			public void setPrtry1(String prtry1) {
				this.prtry1 = prtry1;
			}
			public String getIssr1() {
				return issr1;
			}

			public void setIssr1(String issr1) {
				this.issr1 = issr1;
			}
			public String getCdtrRef() {
				return cdtrRef;
			}

			public void setCdtrRef(String cdtrRef) {
				this.cdtrRef = cdtrRef;
			}
			public String getAddtlRmtInf() {
				return addtlRmtInf;
			}

			public void setAddtlRmtInf(String addtlRmtInf) {
				this.addtlRmtInf = addtlRmtInf;
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