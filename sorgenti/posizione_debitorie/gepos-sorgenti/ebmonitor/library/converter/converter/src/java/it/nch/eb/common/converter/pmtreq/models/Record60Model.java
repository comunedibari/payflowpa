
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class Record60Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String cd;
		private String prtry;
		private String issr;
		private String rfrdDocNb;
		private String rfrdDocRltdDt;
		private String cdtrRefTp_Cd;
		private String cdtrRefTp_Prtry;
		private String cdtrRefTp_Issr;
		private String cdtrRef;
		private String addtlRmtInf;
	
	private int	lineNumber;
	
		
			public String getFiiller() {
				return fiiller;
			}
		
			public void setFiiller(String fiiller) {
				this.fiiller = fiiller;
			}
			public String getTipoRec() {
				return tipoRec;
			}
		
			public void setTipoRec(String tipoRec) {
				this.tipoRec = tipoRec;
			}
			public String getRecordCount() {
				return recordCount;
			}
		
			public void setRecordCount(String recordCount) {
				this.recordCount = recordCount;
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
			public String getCdtrRefTp_Cd() {
				return cdtrRefTp_Cd;
			}
		
			public void setCdtrRefTp_Cd(String cdtrRefTp_Cd) {
				this.cdtrRefTp_Cd = cdtrRefTp_Cd;
			}
			public String getCdtrRefTp_Prtry() {
				return cdtrRefTp_Prtry;
			}
		
			public void setCdtrRefTp_Prtry(String cdtrRefTp_Prtry) {
				this.cdtrRefTp_Prtry = cdtrRefTp_Prtry;
			}
			public String getCdtrRefTp_Issr() {
				return cdtrRefTp_Issr;
			}
		
			public void setCdtrRefTp_Issr(String cdtrRefTp_Issr) {
				this.cdtrRefTp_Issr = cdtrRefTp_Issr;
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