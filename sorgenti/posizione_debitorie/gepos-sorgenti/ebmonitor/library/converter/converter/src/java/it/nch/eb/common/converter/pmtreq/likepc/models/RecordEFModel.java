
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordEFModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.BodyRecordsNumberProvider   {
	
		private String filler;
		private String tipoRecord;
		private String id;
		private String prtry;
		private String creDtTm;
		private String timestamp;
		private String blank;
		private String nbOfTxs;
		private String filler1;
		private String ctrlSum;
		private String bodyRowsNumber;
		private String blank1;
		private String filler2;
		private String blank2;
	
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
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getTimestamp() {
				return timestamp;
			}
		
			public void setTimestamp(String timestamp) {
				this.timestamp = timestamp;
			}
			public String getBlank() {
				return blank;
			}
		
			public void setBlank(String blank) {
				this.blank = blank;
			}
			public String getNbOfTxs() {
				return nbOfTxs;
			}
		
			public void setNbOfTxs(String nbOfTxs) {
				this.nbOfTxs = nbOfTxs;
			}
			public String getFiller1() {
				return filler1;
			}
		
			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getCtrlSum() {
				return ctrlSum;
			}
		
			public void setCtrlSum(String ctrlSum) {
				this.ctrlSum = ctrlSum;
			}
			public String getBodyRowsNumber() {
				return bodyRowsNumber;
			}
		
			public void setBodyRowsNumber(String bodyRowsNumber) {
				this.bodyRowsNumber = bodyRowsNumber;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getFiller2() {
				return filler2;
			}
		
			public void setFiller2(String filler2) {
				this.filler2 = filler2;
			}
			public String getBlank2() {
				return blank2;
			}
		
			public void setBlank2(String blank2) {
				this.blank2 = blank2;
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