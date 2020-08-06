
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class Record10Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String instrId;
		private String blank;
		private String reqdExctnDt;
		private String instrForDbtrAgt;
		private String prtry;
		private String instdAmt;
		private String filler1;
		private String prtry1;
		private String iban;
		private String iban1;
		private String or;
		private String or1;
		private String or2;
		private String id;
		private String blank1;
		private String blank2;
		private String pmtMtd;
		private String blank3;
		private String filler2;
	
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
			public String getBlank() {
				return blank;
			}
		
			public void setBlank(String blank) {
				this.blank = blank;
			}
			public String getReqdExctnDt() {
				return reqdExctnDt;
			}
		
			public void setReqdExctnDt(String reqdExctnDt) {
				this.reqdExctnDt = reqdExctnDt;
			}
			public String getInstrForDbtrAgt() {
				return instrForDbtrAgt;
			}
		
			public void setInstrForDbtrAgt(String instrForDbtrAgt) {
				this.instrForDbtrAgt = instrForDbtrAgt;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getInstdAmt() {
				return instdAmt;
			}
		
			public void setInstdAmt(String instdAmt) {
				this.instdAmt = instdAmt;
			}
			public String getFiller1() {
				return filler1;
			}
		
			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getPrtry1() {
				return prtry1;
			}
		
			public void setPrtry1(String prtry1) {
				this.prtry1 = prtry1;
			}
			public String getIban() {
				return iban;
			}
		
			public void setIban(String iban) {
				this.iban = iban;
			}
			public String getIban1() {
				return iban1;
			}
		
			public void setIban1(String iban1) {
				this.iban1 = iban1;
			}
			public String getOr() {
				return or;
			}
		
			public void setOr(String or) {
				this.or = or;
			}
			public String getOr1() {
				return or1;
			}
		
			public void setOr1(String or1) {
				this.or1 = or1;
			}
			public String getOr2() {
				return or2;
			}
		
			public void setOr2(String or2) {
				this.or2 = or2;
			}
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getBlank2() {
				return blank2;
			}
		
			public void setBlank2(String blank2) {
				this.blank2 = blank2;
			}
			public String getPmtMtd() {
				return pmtMtd;
			}
		
			public void setPmtMtd(String pmtMtd) {
				this.pmtMtd = pmtMtd;
			}
			public String getBlank3() {
				return blank3;
			}
		
			public void setBlank3(String blank3) {
				this.blank3 = blank3;
			}
			public String getFiller2() {
				return filler2;
			}
		
			public void setFiller2(String filler2) {
				this.filler2 = filler2;
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