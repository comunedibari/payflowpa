
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record30Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String stsId;
		private String orgnlInstrId;
		private String orgnlEndToEndId;
		private String prtry;
		private String filler1;
		private String benValDt;
		private String txSts;
		private String bic;
		private String filler2;
		private String filler3;
		private String filler4;
		private String cd;
		private String prtry1;
		private String accptncDtTm;
	
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
			public String getStsId() {
				return stsId;
			}

			public void setStsId(String stsId) {
				this.stsId = stsId;
			}
			public String getOrgnlInstrId() {
				return orgnlInstrId;
			}

			public void setOrgnlInstrId(String orgnlInstrId) {
				this.orgnlInstrId = orgnlInstrId;
			}
			public String getOrgnlEndToEndId() {
				return orgnlEndToEndId;
			}

			public void setOrgnlEndToEndId(String orgnlEndToEndId) {
				this.orgnlEndToEndId = orgnlEndToEndId;
			}
			public String getPrtry() {
				return prtry;
			}

			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getFiller1() {
				return filler1;
			}

			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getBenValDt() {
				return benValDt;
			}

			public void setBenValDt(String benValDt) {
				this.benValDt = benValDt;
			}
			public String getTxSts() {
				return txSts;
			}

			public void setTxSts(String txSts) {
				this.txSts = txSts;
			}
			public String getBic() {
				return bic;
			}

			public void setBic(String bic) {
				this.bic = bic;
			}
			public String getFiller2() {
				return filler2;
			}

			public void setFiller2(String filler2) {
				this.filler2 = filler2;
			}
			public String getFiller3() {
				return filler3;
			}

			public void setFiller3(String filler3) {
				this.filler3 = filler3;
			}
			public String getFiller4() {
				return filler4;
			}

			public void setFiller4(String filler4) {
				this.filler4 = filler4;
			}
			public String getCd() {
				return cd;
			}

			public void setCd(String cd) {
				this.cd = cd;
			}
			public String getPrtry1() {
				return prtry1;
			}

			public void setPrtry1(String prtry1) {
				this.prtry1 = prtry1;
			}
			public String getAccptncDtTm() {
				return accptncDtTm;
			}

			public void setAccptncDtTm(String accptncDtTm) {
				this.accptncDtTm = accptncDtTm;
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