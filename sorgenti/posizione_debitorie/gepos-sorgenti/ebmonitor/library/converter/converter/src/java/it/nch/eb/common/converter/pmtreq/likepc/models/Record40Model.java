
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class Record40Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider, it.nch.eb.common.converter.RecordCountIncTrigger   {
	
		private String filler;
		private String tipoRecord;
		private String instrId;
		private String or;
		private String pstCd;
		private String twnNm;
		private String blank;
	
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
			public String getOr() {
				return or;
			}
		
			public void setOr(String or) {
				this.or = or;
			}
			public String getPstCd() {
				return pstCd;
			}
		
			public void setPstCd(String pstCd) {
				this.pstCd = pstCd;
			}
			public String getTwnNm() {
				return twnNm;
			}
		
			public void setTwnNm(String twnNm) {
				this.twnNm = twnNm;
			}
			public String getBlank() {
				return blank;
			}
		
			public void setBlank(String blank) {
				this.blank = blank;
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