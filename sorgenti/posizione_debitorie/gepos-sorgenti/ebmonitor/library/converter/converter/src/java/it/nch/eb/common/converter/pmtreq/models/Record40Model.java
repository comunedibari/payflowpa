
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class Record40Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String rmtId;
		private String rmtLctnMtd;
		private String rmtLctnElctrncAdr;
	
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