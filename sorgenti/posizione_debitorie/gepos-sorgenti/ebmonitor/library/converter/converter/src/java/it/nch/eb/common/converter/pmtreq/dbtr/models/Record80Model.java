
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record80Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRecord;
		private String recordCount;
		private String nm;
		private String adrTp;
		private String adrLine1;
		private String adrLine2;
		private String strtNm;
		private String bldgNb;
		private String pstCd;
		private String twnNm;
		private String ctrySubDvsn;
		private String ctry;
		private String orgnlTxRef_Nm;
		private String orgnlTxRef_Id;
		private String orgnlTxRef_Issr;
		private String orgnlTxRef_TaxIdNb;
		private String orgnlTxRef_Iban;
	
	private int	lineNumber;
	
		
			public String getFiiller() {
				return fiiller;
			}
		
			public void setFiiller(String fiiller) {
				this.fiiller = fiiller;
			}
			public String getTipoRecord() {
				return tipoRecord;
			}
		
			public void setTipoRecord(String tipoRecord) {
				this.tipoRecord = tipoRecord;
			}
			public String getRecordCount() {
				return recordCount;
			}
		
			public void setRecordCount(String recordCount) {
				this.recordCount = recordCount;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getAdrTp() {
				return adrTp;
			}
		
			public void setAdrTp(String adrTp) {
				this.adrTp = adrTp;
			}
			public String getAdrLine1() {
				return adrLine1;
			}
		
			public void setAdrLine1(String adrLine1) {
				this.adrLine1 = adrLine1;
			}
			public String getAdrLine2() {
				return adrLine2;
			}
		
			public void setAdrLine2(String adrLine2) {
				this.adrLine2 = adrLine2;
			}
			public String getStrtNm() {
				return strtNm;
			}
		
			public void setStrtNm(String strtNm) {
				this.strtNm = strtNm;
			}
			public String getBldgNb() {
				return bldgNb;
			}
		
			public void setBldgNb(String bldgNb) {
				this.bldgNb = bldgNb;
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
			public String getCtrySubDvsn() {
				return ctrySubDvsn;
			}
		
			public void setCtrySubDvsn(String ctrySubDvsn) {
				this.ctrySubDvsn = ctrySubDvsn;
			}
			public String getCtry() {
				return ctry;
			}
		
			public void setCtry(String ctry) {
				this.ctry = ctry;
			}
			public String getOrgnlTxRef_Nm() {
				return orgnlTxRef_Nm;
			}
		
			public void setOrgnlTxRef_Nm(String orgnlTxRef_Nm) {
				this.orgnlTxRef_Nm = orgnlTxRef_Nm;
			}
			public String getOrgnlTxRef_Id() {
				return orgnlTxRef_Id;
			}
		
			public void setOrgnlTxRef_Id(String orgnlTxRef_Id) {
				this.orgnlTxRef_Id = orgnlTxRef_Id;
			}
			public String getOrgnlTxRef_Issr() {
				return orgnlTxRef_Issr;
			}
		
			public void setOrgnlTxRef_Issr(String orgnlTxRef_Issr) {
				this.orgnlTxRef_Issr = orgnlTxRef_Issr;
			}
			public String getOrgnlTxRef_TaxIdNb() {
				return orgnlTxRef_TaxIdNb;
			}
		
			public void setOrgnlTxRef_TaxIdNb(String orgnlTxRef_TaxIdNb) {
				this.orgnlTxRef_TaxIdNb = orgnlTxRef_TaxIdNb;
			}
			public String getOrgnlTxRef_Iban() {
				return orgnlTxRef_Iban;
			}
		
			public void setOrgnlTxRef_Iban(String orgnlTxRef_Iban) {
				this.orgnlTxRef_Iban = orgnlTxRef_Iban;
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