
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class CdtrPaymentRequest  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.RecorddiTestaModel recorddiTesta;
		private java.util.List cdtrBody;
		private it.nch.eb.common.converter.common.models.RecordCodaModel recordCoda;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.RecorddiTestaModel getRecorddiTesta() {
				return recorddiTesta;
			}

			public void setRecorddiTesta(it.nch.eb.common.converter.pmtreq.cdtr.models.RecorddiTestaModel recorddiTesta) {
				this.recorddiTesta = recorddiTesta;
			}
			public java.util.List getCdtrBody() {
				return cdtrBody;
			}

			public void setCdtrBody(java.util.List cdtrBody) {
				this.cdtrBody = cdtrBody;
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