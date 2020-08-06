
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record82Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String bic;
		private String nm;
		private String adrTp;
		private String adrLine_1;
		private String adrLine_2;
		private String strtNm;
		private String bldgNb;
		private String pstCd;
		private String twnNm;
		private String ctrySubDvsn;
		private String ctry;
		private String bei;
		private String taxIdNb;
		private String id;
		private String issr;
		private String taxIdNb1;
		private String id1;
		private String idTp;
		private String iban;
		private String bban;
	
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
			public String getRecordCount() {
				return rec_count;
			}

			public void setRecordCount(String rec_count) {
				this.rec_count = rec_count;
			}
			public String getBic() {
				return bic;
			}

			public void setBic(String bic) {
				this.bic = bic;
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
				return adrLine_1;
			}

			public void setAdrLine1(String adrLine_1) {
				this.adrLine_1 = adrLine_1;
			}
			public String getAdrLine2() {
				return adrLine_2;
			}

			public void setAdrLine2(String adrLine_2) {
				this.adrLine_2 = adrLine_2;
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
			public String getTaxIdNb1() {
				return taxIdNb1;
			}

			public void setTaxIdNb1(String taxIdNb1) {
				this.taxIdNb1 = taxIdNb1;
			}
			public String getId1() {
				return id1;
			}

			public void setId1(String id1) {
				this.id1 = id1;
			}
			public String getIdTp() {
				return idTp;
			}

			public void setIdTp(String idTp) {
				this.idTp = idTp;
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