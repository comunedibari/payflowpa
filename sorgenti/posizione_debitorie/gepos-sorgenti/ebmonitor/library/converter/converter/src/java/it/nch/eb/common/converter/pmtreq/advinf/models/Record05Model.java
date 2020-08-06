
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record05Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String nmAndAdr;
		private String bei;
		private String id;
		private String issr;
		private String iban;
		private String bban;
		private String id1;
		private String tp;
	
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
				return recordCount;
			}
		
			public void setRecordCount(String recordCount) {
				this.recordCount = recordCount;
			}
			public String getNmAndAdr() {
				return nmAndAdr;
			}
		
			public void setNmAndAdr(String nmAndAdr) {
				this.nmAndAdr = nmAndAdr;
			}
			public String getBei() {
				return bei;
			}
		
			public void setBei(String bei) {
				this.bei = bei;
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
			public String getId1() {
				return id1;
			}
		
			public void setId1(String id1) {
				this.id1 = id1;
			}
			public String getTp() {
				return tp;
			}
		
			public void setTp(String tp) {
				this.tp = tp;
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