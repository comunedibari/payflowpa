
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.utils.StringUtils;


public class RecordCodaBodyModel  implements RecordCountProvider, it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String fiiller;
		private String tipoRec;
		private String recordCount;
		private String msgid;
		private String creDtTm;
		private String id;
		private String totalRow;
	
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
			public String getMsgid() {
				return msgid;
			}
		
			public void setMsgid(String msgid) {
				this.msgid = msgid;
			}
			public String getCreDtTm() {
				return creDtTm;
			}
		
			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getId() {
				return id;
			}
		
			public void setId(String id) {
				this.id = id;
			}
			public String getTotalRow() {
				return totalRow;
			}
		
			public void setTotalRow(String totalRow) {
				this.totalRow = totalRow;
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