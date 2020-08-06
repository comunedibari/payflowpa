
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.utils.StringUtils;


public class Record00Model  implements RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String pmtMtd;
		private String instrPrty;
		private String cd;
		private String reqdExctnDt;
		private String nm;
		private String taxIdNb;
		private String id;
		private String issr;
		private String sia;
		private String iban;
		private String bic;
		private String prtry;
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
		private String chrgbr;
		private String chrgsAcctIban;
	
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
			public String getPmtMtd() {
				return pmtMtd;
			}
		
			public void setPmtMtd(String pmtMtd) {
				this.pmtMtd = pmtMtd;
			}
			public String getInstrPrty() {
				return instrPrty;
			}
		
			public void setInstrPrty(String instrPrty) {
				this.instrPrty = instrPrty;
			}
			public String getCd() {
				return cd;
			}
		
			public void setCd(String cd) {
				this.cd = cd;
			}
			public String getReqdExctnDt() {
				return reqdExctnDt;
			}
		
			public void setReqdExctnDt(String reqdExctnDt) {
				this.reqdExctnDt = reqdExctnDt;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getTaxIdNb() {
				return taxIdNb;
			}
		
			public void setTaxIdNb(String taxIdNb) {
				this.taxIdNb = taxIdNb;
			}
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
			}
			public String getIssr() {
				return issr;
			}
		
			public void setIssr(String issr) {
				this.issr = issr;
			}
			public String getSia() {
				return sia;
			}
		
			public void setSia(String sia) {
				this.sia = sia;
			}
			public String getIban() {
				return iban;
			}
		
			public void setIban(String iban) {
				this.iban = iban;
			}
			public String getBic() {
				return bic;
			}
		
			public void setBic(String bic) {
				this.bic = bic;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
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
			public String getChrgbr() {
				return chrgbr;
			}
		
			public void setChrgbr(String chrgbr) {
				this.chrgbr = chrgbr;
			}
			public String getChrgsAcctIban() {
				return chrgsAcctIban;
			}
		
			public void setChrgsAcctIban(String chrgsAcctIban) {
				this.chrgsAcctIban = chrgsAcctIban;
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