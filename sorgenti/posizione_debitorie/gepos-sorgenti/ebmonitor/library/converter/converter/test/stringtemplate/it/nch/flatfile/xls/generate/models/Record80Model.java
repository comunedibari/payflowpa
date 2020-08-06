
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.utils.StringUtils;


public class Record80Model  implements RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String pstlAdrsNm;
		private String pstlAdrsAdrTp;
		private String pstlAdrsAdrLine;
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
		private String ctryOfRes;
	
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
			public String getPstlAdrsAdrLine() {
				return pstlAdrsAdrLine;
			}
		
			public void setPstlAdrsAdrLine(String pstlAdrsAdrLine) {
				this.pstlAdrsAdrLine = pstlAdrsAdrLine;
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
			public String getCtryOfRes() {
				return ctryOfRes;
			}
		
			public void setCtryOfRes(String ctryOfRes) {
				this.ctryOfRes = ctryOfRes;
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