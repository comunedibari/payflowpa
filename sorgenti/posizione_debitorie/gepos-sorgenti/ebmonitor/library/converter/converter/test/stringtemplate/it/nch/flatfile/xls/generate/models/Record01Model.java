
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.utils.StringUtils;


public class Record01Model  implements RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String instrId;
		private String endToEndId;
		private String prtry;
		private String lclInstrmPrtry;
		private String ctgyPurp;
		private String instdAmt;
		private String instdAmtCcy;
		private String chqInstr;
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
		private String srvInf;
		private String bIC;
		private String iban;
		private String bban;
		private String instrForDbtrAgt;
		private String nm;
		private String prtryId_Id;
		private String prtryId_Issr;
		private String sia;
		private String cd;
		private String purp_prtry;
	
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
			public String getInstrId() {
				return instrId;
			}
		
			public void setInstrId(String instrId) {
				this.instrId = instrId;
			}
			public String getEndToEndId() {
				return endToEndId;
			}
		
			public void setEndToEndId(String endToEndId) {
				this.endToEndId = endToEndId;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getLclInstrmPrtry() {
				return lclInstrmPrtry;
			}
		
			public void setLclInstrmPrtry(String lclInstrmPrtry) {
				this.lclInstrmPrtry = lclInstrmPrtry;
			}
			public String getCtgyPurp() {
				return ctgyPurp;
			}
		
			public void setCtgyPurp(String ctgyPurp) {
				this.ctgyPurp = ctgyPurp;
			}
			public String getInstdAmt() {
				return instdAmt;
			}
		
			public void setInstdAmt(String instdAmt) {
				this.instdAmt = instdAmt;
			}
			public String getInstdAmtCcy() {
				return instdAmtCcy;
			}
		
			public void setInstdAmtCcy(String instdAmtCcy) {
				this.instdAmtCcy = instdAmtCcy;
			}
			public String getChqInstr() {
				return chqInstr;
			}
		
			public void setChqInstr(String chqInstr) {
				this.chqInstr = chqInstr;
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
			public String getSrvInf() {
				return srvInf;
			}
		
			public void setSrvInf(String srvInf) {
				this.srvInf = srvInf;
			}
			public String getBIC() {
				return bIC;
			}
		
			public void setBIC(String bIC) {
				this.bIC = bIC;
			}
			public String getIban() {
				return iban;
			}
		
			public void setIban(String iban) {
				this.iban = iban;
			}
			public String getBban() {
				return bban;
			}
		
			public void setBban(String bban) {
				this.bban = bban;
			}
			public String getInstrForDbtrAgt() {
				return instrForDbtrAgt;
			}
		
			public void setInstrForDbtrAgt(String instrForDbtrAgt) {
				this.instrForDbtrAgt = instrForDbtrAgt;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
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
			public String getCd() {
				return cd;
			}
		
			public void setCd(String cd) {
				this.cd = cd;
			}
			public String getPurp_prtry() {
				return purp_prtry;
			}
		
			public void setPurp_prtry(String purp_prtry) {
				this.purp_prtry = purp_prtry;
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