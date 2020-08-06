
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record68Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String nm;
		private String adrTp;
		private String adrLine_1;
		private String adrLine_2;
		private String adrLine_3;
		private String adrLine_4;
		private String adrLine_5;
		private String strtNm;
		private String bldgNb;
		private String pstCd;
		private String twnNm;
		private String ctrySubDvsn;
		private String ctry;
		private String bic;
		private String ibei;
		private String bei;
		private String eangln;
		private String uschu;
		private String duns;
		private String bkPtyId;
		private String taxIdNb;
		private String id;
		private String issr;
		private String ctryOfRes;
	
	private int	lineNumber;
	

			public String getFiller() {
				return filler;
			}

			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipo_record() {
				return tipo_record;
			}

			public void setTipo_record(String tipo_record) {
				this.tipo_record = tipo_record;
			}
			public String getRec_count() {
				return rec_count;
			}

			public void setRec_count(String rec_count) {
				this.rec_count = rec_count;
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
			public String getAdrLine_1() {
				return adrLine_1;
			}

			public void setAdrLine_1(String adrLine_1) {
				this.adrLine_1 = adrLine_1;
			}
			public String getAdrLine_2() {
				return adrLine_2;
			}

			public void setAdrLine_2(String adrLine_2) {
				this.adrLine_2 = adrLine_2;
			}
			public String getAdrLine_3() {
				return adrLine_3;
			}

			public void setAdrLine_3(String adrLine_3) {
				this.adrLine_3 = adrLine_3;
			}
			public String getAdrLine_4() {
				return adrLine_4;
			}

			public void setAdrLine_4(String adrLine_4) {
				this.adrLine_4 = adrLine_4;
			}
			public String getAdrLine_5() {
				return adrLine_5;
			}

			public void setAdrLine_5(String adrLine_5) {
				this.adrLine_5 = adrLine_5;
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
			public String getBic() {
				return bic;
			}

			public void setBic(String bic) {
				this.bic = bic;
			}
			public String getIbei() {
				return ibei;
			}

			public void setIbei(String ibei) {
				this.ibei = ibei;
			}
			public String getBei() {
				return bei;
			}

			public void setBei(String bei) {
				this.bei = bei;
			}
			public String getEangln() {
				return eangln;
			}

			public void setEangln(String eangln) {
				this.eangln = eangln;
			}
			public String getUschu() {
				return uschu;
			}

			public void setUschu(String uschu) {
				this.uschu = uschu;
			}
			public String getDuns() {
				return duns;
			}

			public void setDuns(String duns) {
				this.duns = duns;
			}
			public String getBkPtyId() {
				return bkPtyId;
			}

			public void setBkPtyId(String bkPtyId) {
				this.bkPtyId = bkPtyId;
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