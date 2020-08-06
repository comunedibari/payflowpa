
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordFIModel  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String sgnt1;
		private String sgnt2;
		private String sgnt3;
		private String sgnt4;
		private String sgnt5;
		private String sgnt6;
		private String sgnt7;
		private String sgnt8;
		private String sgnt9;
		private String sgnt10;
	
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