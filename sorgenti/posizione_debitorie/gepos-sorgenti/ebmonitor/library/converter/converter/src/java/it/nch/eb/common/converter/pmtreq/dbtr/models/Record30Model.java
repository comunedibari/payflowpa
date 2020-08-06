
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record30Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRecord;
		private String recordCount;
		private String stsId;
		private String orgnlInstrId;
		private String orgnlEndToEndId;
		private String lclInstrm_Prtry;
		private String ordValDt;
		private String benValDt;
		private String txSts;
		private String bic;
		private String stsRsn_Cd;
		private String prtry;
		private String elmRfc;
		private String purp_CD;
		private String purp_Prtry;
		private String accptncDtTm;
	
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
			public String getLclInstrm_Prtry() {
				return lclInstrm_Prtry;
			}
		
			public void setLclInstrm_Prtry(String lclInstrm_Prtry) {
				this.lclInstrm_Prtry = lclInstrm_Prtry;
			}
			public String getOrdValDt() {
				return ordValDt;
			}
		
			public void setOrdValDt(String ordValDt) {
				this.ordValDt = ordValDt;
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
			public String getStsRsn_Cd() {
				return stsRsn_Cd;
			}
		
			public void setStsRsn_Cd(String stsRsn_Cd) {
				this.stsRsn_Cd = stsRsn_Cd;
			}
			public String getPrtry() {
				return prtry;
			}
		
			public void setPrtry(String prtry) {
				this.prtry = prtry;
			}
			public String getElmRfc() {
				return elmRfc;
			}
		
			public void setElmRfc(String elmRfc) {
				this.elmRfc = elmRfc;
			}
			public String getPurp_CD() {
				return purp_CD;
			}
		
			public void setPurp_CD(String purp_CD) {
				this.purp_CD = purp_CD;
			}
			public String getPurp_Prtry() {
				return purp_Prtry;
			}
		
			public void setPurp_Prtry(String purp_Prtry) {
				this.purp_Prtry = purp_Prtry;
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