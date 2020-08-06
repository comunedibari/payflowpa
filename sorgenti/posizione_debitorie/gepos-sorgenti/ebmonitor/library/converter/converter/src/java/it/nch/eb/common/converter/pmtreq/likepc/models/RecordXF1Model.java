
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordXF1Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String sgnt1;
		private String sgnt2;
		private String sgnt3;
		private String sgnt4;
		private String sgnt5;
	
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
			public String getSgnt1() {
				return sgnt1;
			}
		
			public void setSgnt1(String sgnt1) {
				this.sgnt1 = sgnt1;
			}
			public String getSgnt2() {
				return sgnt2;
			}
		
			public void setSgnt2(String sgnt2) {
				this.sgnt2 = sgnt2;
			}
			public String getSgnt3() {
				return sgnt3;
			}
		
			public void setSgnt3(String sgnt3) {
				this.sgnt3 = sgnt3;
			}
			public String getSgnt4() {
				return sgnt4;
			}
		
			public void setSgnt4(String sgnt4) {
				this.sgnt4 = sgnt4;
			}
			public String getSgnt5() {
				return sgnt5;
			}
		
			public void setSgnt5(String sgnt5) {
				this.sgnt5 = sgnt5;
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