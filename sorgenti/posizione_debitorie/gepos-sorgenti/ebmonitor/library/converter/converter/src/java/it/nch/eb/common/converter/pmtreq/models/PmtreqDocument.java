
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtreqDocument  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.common.models.RecordTestaModel recordTesta;
		private java.util.List pmtreqBody;
		private it.nch.eb.common.converter.common.models.RecordCodaModel recordCoda;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.common.models.RecordTestaModel getRecordTesta() {
				return recordTesta;
			}
		
			public void setRecordTesta(it.nch.eb.common.converter.common.models.RecordTestaModel recordTesta) {
				this.recordTesta = recordTesta;
			}
			public java.util.List getPmtreqBody() {
				return pmtreqBody;
			}
		
			public void setPmtreqBody(java.util.List pmtreqBody) {
				this.pmtreqBody = pmtreqBody;
			}
			public it.nch.eb.common.converter.common.models.RecordCodaModel getRecordCoda() {
				return recordCoda;
			}
		
			public void setRecordCoda(it.nch.eb.common.converter.common.models.RecordCodaModel recordCoda) {
				this.recordCoda = recordCoda;
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