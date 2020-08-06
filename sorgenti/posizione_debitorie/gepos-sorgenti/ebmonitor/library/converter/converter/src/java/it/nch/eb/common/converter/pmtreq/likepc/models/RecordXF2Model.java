
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordXF2Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String sgnt6;
		private String sgnt7;
		private String sgnt8;
		private String sgnt9;
		private String sgnt10;
	
	private int	lineNumber;
	
		
			public String getFiller() {
				return filler;
			}
		
			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipoRecord() {
				return tipoRecord;
			}
		
			public void setTipoRecord(String tipoRecord) {
				this.tipoRecord = tipoRecord;
			}
			public String getSgnt6() {
				return sgnt6;
			}
		
			public void setSgnt6(String sgnt6) {
				this.sgnt6 = sgnt6;
			}
			public String getSgnt7() {
				return sgnt7;
			}
		
			public void setSgnt7(String sgnt7) {
				this.sgnt7 = sgnt7;
			}
			public String getSgnt8() {
				return sgnt8;
			}
		
			public void setSgnt8(String sgnt8) {
				this.sgnt8 = sgnt8;
			}
			public String getSgnt9() {
				return sgnt9;
			}
		
			public void setSgnt9(String sgnt9) {
				this.sgnt9 = sgnt9;
			}
			public String getSgnt10() {
				return sgnt10;
			}
		
			public void setSgnt10(String sgnt10) {
				this.sgnt10 = sgnt10;
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