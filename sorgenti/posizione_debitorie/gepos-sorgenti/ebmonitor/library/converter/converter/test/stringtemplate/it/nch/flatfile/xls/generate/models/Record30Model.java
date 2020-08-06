
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.utils.StringUtils;


public class Record30Model  implements RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String pstlAdrsNm;
		private String pstlAdrsAdrTp;
		private String pstlAdrsAdrLine1;
		private String pstlAdrsAdrLine2;
		private String pstlAdrsAdrLine3;
		private String pstlAdrsAdrLine4;
		private String pstlAdrsAdrLine5;
		private String pstlAdrsStrtNm;
		private String pstlAdrsBldgNb;
		private String pstlAdrsPstCd;
		private String pstlAdrsTwnNm;
		private String pstlAdrsCtrySubDvsn;
		private String pstlAdrsCtry;
		private String bei;
		private String origidTaxIdNb;
		private String prtryId_Id;
		private String prtryId_Issr;
		private String sia;
		private String prvtIdTaxIdNb;
		private String othrId;
		private String othrIdTp;
	
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
			public String getPstlAdrsAdrLine3() {
				return pstlAdrsAdrLine3;
			}
		
			public void setPstlAdrsAdrLine3(String pstlAdrsAdrLine3) {
				this.pstlAdrsAdrLine3 = pstlAdrsAdrLine3;
			}
			public String getPstlAdrsAdrLine4() {
				return pstlAdrsAdrLine4;
			}
		
			public void setPstlAdrsAdrLine4(String pstlAdrsAdrLine4) {
				this.pstlAdrsAdrLine4 = pstlAdrsAdrLine4;
			}
			public String getPstlAdrsAdrLine5() {
				return pstlAdrsAdrLine5;
			}
		
			public void setPstlAdrsAdrLine5(String pstlAdrsAdrLine5) {
				this.pstlAdrsAdrLine5 = pstlAdrsAdrLine5;
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
			public String getOrigidTaxIdNb() {
				return origidTaxIdNb;
			}
		
			public void setOrigidTaxIdNb(String origidTaxIdNb) {
				this.origidTaxIdNb = origidTaxIdNb;
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
			public String getPrvtIdTaxIdNb() {
				return prvtIdTaxIdNb;
			}
		
			public void setPrvtIdTaxIdNb(String prvtIdTaxIdNb) {
				this.prvtIdTaxIdNb = prvtIdTaxIdNb;
			}
			public String getOthrId() {
				return othrId;
			}
		
			public void setOthrId(String othrId) {
				this.othrId = othrId;
			}
			public String getOthrIdTp() {
				return othrIdTp;
			}
		
			public void setOthrIdTp(String othrIdTp) {
				this.othrIdTp = othrIdTp;
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