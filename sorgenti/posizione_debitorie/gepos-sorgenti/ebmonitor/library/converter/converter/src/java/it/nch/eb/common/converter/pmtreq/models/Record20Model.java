
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class Record20Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String pstlAdrsNm;
		private String pstlAdrsAdrTp;
		private String pstlAdrsAdrLine1;
		private String pstlAdrsAdrLine2;
		private String pstlAdrsStrtNm;
		private String pstlAdrsBldgNb;
		private String pstlAdrsPstCd;
		private String pstlAdrsTwnNm;
		private String pstlAdrsCtrySubDvsn;
		private String pstlAdrsCtry;
		private String bei;
		private String taxIdNb;
		private String prtryId_Id;
		private String prtryId_Issr;
		private String sia;
		private String prtvid;
		private String othrId_Id;
		private String othrId_IdTp;
	
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
			public String getPstlAdrsNm() {
				return pstlAdrsNm;
			}
		
			public void setPstlAdrsNm(String pstlAdrsNm) {
				this.pstlAdrsNm = pstlAdrsNm;
			}
			public String getPstlAdrsAdrTp() {
				return pstlAdrsAdrTp;
			}
		
			public void setPstlAdrsAdrTp(String pstlAdrsAdrTp) {
				this.pstlAdrsAdrTp = pstlAdrsAdrTp;
			}
			public String getPstlAdrsAdrLine1() {
				return pstlAdrsAdrLine1;
			}
		
			public void setPstlAdrsAdrLine1(String pstlAdrsAdrLine1) {
				this.pstlAdrsAdrLine1 = pstlAdrsAdrLine1;
			}
			public String getPstlAdrsAdrLine2() {
				return pstlAdrsAdrLine2;
			}
		
			public void setPstlAdrsAdrLine2(String pstlAdrsAdrLine2) {
				this.pstlAdrsAdrLine2 = pstlAdrsAdrLine2;
			}
			public String getPstlAdrsStrtNm() {
				return pstlAdrsStrtNm;
			}
		
			public void setPstlAdrsStrtNm(String pstlAdrsStrtNm) {
				this.pstlAdrsStrtNm = pstlAdrsStrtNm;
			}
			public String getPstlAdrsBldgNb() {
				return pstlAdrsBldgNb;
			}
		
			public void setPstlAdrsBldgNb(String pstlAdrsBldgNb) {
				this.pstlAdrsBldgNb = pstlAdrsBldgNb;
			}
			public String getPstlAdrsPstCd() {
				return pstlAdrsPstCd;
			}
		
			public void setPstlAdrsPstCd(String pstlAdrsPstCd) {
				this.pstlAdrsPstCd = pstlAdrsPstCd;
			}
			public String getPstlAdrsTwnNm() {
				return pstlAdrsTwnNm;
			}
		
			public void setPstlAdrsTwnNm(String pstlAdrsTwnNm) {
				this.pstlAdrsTwnNm = pstlAdrsTwnNm;
			}
			public String getPstlAdrsCtrySubDvsn() {
				return pstlAdrsCtrySubDvsn;
			}
		
			public void setPstlAdrsCtrySubDvsn(String pstlAdrsCtrySubDvsn) {
				this.pstlAdrsCtrySubDvsn = pstlAdrsCtrySubDvsn;
			}
			public String getPstlAdrsCtry() {
				return pstlAdrsCtry;
			}
		
			public void setPstlAdrsCtry(String pstlAdrsCtry) {
				this.pstlAdrsCtry = pstlAdrsCtry;
			}
			public String getBei() {
				return bei;
			}
		
			public void setBei(String bei) {
				this.bei = bei;
			}
			public String getTaxIdNb() {
				return taxIdNb;
			}
		
			public void setTaxIdNb(String taxIdNb) {
				this.taxIdNb = taxIdNb;
			}
			public String getPrtryId_Id() {
				return prtryId_Id;
			}
		
			public void setPrtryId_Id(String prtryId_Id) {
				this.prtryId_Id = prtryId_Id;
			}
			public String getPrtryId_Issr() {
				return prtryId_Issr;
			}
		
			public void setPrtryId_Issr(String prtryId_Issr) {
				this.prtryId_Issr = prtryId_Issr;
			}
			public String getSia() {
				return sia;
			}
		
			public void setSia(String sia) {
				this.sia = sia;
			}
			public String getPrtvid() {
				return prtvid;
			}
		
			public void setPrtvid(String prtvid) {
				this.prtvid = prtvid;
			}
			public String getOthrId_Id() {
				return othrId_Id;
			}
		
			public void setOthrId_Id(String othrId_Id) {
				this.othrId_Id = othrId_Id;
			}
			public String getOthrId_IdTp() {
				return othrId_IdTp;
			}
		
			public void setOthrId_IdTp(String othrId_IdTp) {
				this.othrId_IdTp = othrId_IdTp;
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