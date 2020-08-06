
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordTestaBodyModel  implements it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.common.converter.BodyRecordsNumberProvider, it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String recordCount;
		private String advInstrTId;
		private String msgId;
		private String creDtTm;
		private String txNb;
		private String id;
		private String issr;
		private String filler1;
		private String nm;
		private String id1;
		private String issr1;
		private String bic;
		private String clrSysMmbId;
		private String bodyRowsNumber;
	
	private int	lineNumber;
	
		
			public String getFiller() {
				return filler;
			}
		
			public void setFiller(String filler) {
				this.filler = filler;
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
			public String getAdvInstrTId() {
				return advInstrTId;
			}
		
			public void setAdvInstrTId(String advInstrTId) {
				this.advInstrTId = advInstrTId;
			}
			public String getMsgId() {
				return msgId;
			}
		
			public void setMsgId(String msgId) {
				this.msgId = msgId;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getTxNb() {
				return txNb;
			}
		
			public void setTxNb(String txNb) {
				this.txNb = txNb;
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
			public String getFiller1() {
				return filler1;
			}
		
			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getNm() {
				return nm;
			}
		
			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getId1() {
				return id1;
			}
		
			public void setId1(String id1) {
				this.id1 = id1;
			}
			public String getIssr1() {
				return issr1;
			}
		
			public void setIssr1(String issr1) {
				this.issr1 = issr1;
			}
			public String getBic() {
				return bic;
			}
		
			public void setBic(String bic) {
				this.bic = bic;
			}
			public String getClrSysMmbId() {
				return clrSysMmbId;
			}
		
			public void setClrSysMmbId(String clrSysMmbId) {
				this.clrSysMmbId = clrSysMmbId;
			}
			public String getBodyRowsNumber() {
				return bodyRowsNumber;
			}
		
			public void setBodyRowsNumber(String bodyRowsNumber) {
				this.bodyRowsNumber = bodyRowsNumber;
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