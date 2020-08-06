
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class Record11Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String dbtCdtRptgInd;
		private String cd;
		private String amtCcy;
		private String rgltryDtlsAmt;
		private String rgltryDtlsInf;
	
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
			public String getAmtCcy() {
				return amtCcy;
			}
		
			public void setAmtCcy(String amtCcy) {
				this.amtCcy = amtCcy;
			}
			public String getRgltryDtlsAmt() {
				return rgltryDtlsAmt;
			}
		
			public void setRgltryDtlsAmt(String rgltryDtlsAmt) {
				this.rgltryDtlsAmt = rgltryDtlsAmt;
			}
			public String getRgltryDtlsInf() {
				return rgltryDtlsInf;
			}
		
			public void setRgltryDtlsInf(String rgltryDtlsInf) {
				this.rgltryDtlsInf = rgltryDtlsInf;
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