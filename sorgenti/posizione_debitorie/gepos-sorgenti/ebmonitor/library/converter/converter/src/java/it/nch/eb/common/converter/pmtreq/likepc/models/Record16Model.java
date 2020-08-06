
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class Record16Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String instrId;
		private String iban;
		private String filler1;
		private String iban1;
		private String filler2;
		private String iban2;
		private String iban3;
		private String blank;
		private String endToEndId;
		private String blank1;
	
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
			public String getInstrId() {
				return instrId;
			}
		
			public void setInstrId(String instrId) {
				this.instrId = instrId;
			}
			public String getIban() {
				return iban;
			}
		
			public void setIban(String iban) {
				this.iban = iban;
			}
			public String getFiller1() {
				return filler1;
			}
		
			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getIban1() {
				return iban1;
			}
		
			public void setIban1(String iban1) {
				this.iban1 = iban1;
			}
			public String getFiller2() {
				return filler2;
			}
		
			public void setFiller2(String filler2) {
				this.filler2 = filler2;
			}
			public String getIban2() {
				return iban2;
			}
		
			public void setIban2(String iban2) {
				this.iban2 = iban2;
			}
			public String getIban3() {
				return iban3;
			}
		
			public void setIban3(String iban3) {
				this.iban3 = iban3;
			}
			public String getBlank() {
				return blank;
			}
		
			public void setBlank(String blank) {
				this.blank = blank;
			}
			public String getEndToEndId() {
				return endToEndId;
			}
		
			public void setEndToEndId(String endToEndId) {
				this.endToEndId = endToEndId;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
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