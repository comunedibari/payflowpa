
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class Record02Model  implements it.nch.eb.common.converter.RecordCountProvider, it.nch.eb.common.converter.RecordCountIncTrigger, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipo_record;
		private String recordCount;
		private String nmAndAdr;
		private String bei;
		private String ctry;
		private String acctNb;
	
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
			public String getCtry() {
				return ctry;
			}
		
			public void setCtry(String ctry) {
				this.ctry = ctry;
			}
			public String getAcctNb() {
				return acctNb;
			}
		
			public void setAcctNb(String acctNb) {
				this.acctNb = acctNb;
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