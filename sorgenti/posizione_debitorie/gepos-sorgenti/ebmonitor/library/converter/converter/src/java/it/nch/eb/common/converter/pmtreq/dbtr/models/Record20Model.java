
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record20Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRecord;
		private String recordCount;
		private String bic;
		private String cd;
		private String prtry;
		private String elmRfc;
	
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
			public String getBic() {
				return bic;
			}
		
			public void setBic(String bic) {
				this.bic = bic;
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
			public String getElmRfc() {
				return elmRfc;
			}
		
			public void setElmRfc(String elmRfc) {
				this.elmRfc = elmRfc;
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