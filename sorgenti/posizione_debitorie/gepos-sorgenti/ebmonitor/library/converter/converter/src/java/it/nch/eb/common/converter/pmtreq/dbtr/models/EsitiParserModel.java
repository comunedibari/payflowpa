
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class EsitiParserModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.dbtr.models.RecorddiTestaModel recorddiTesta;
		private it.nch.eb.common.converter.pmtreq.dbtr.models.XmlRecordTestaBodyModel recordTestaBody;
		private java.util.List pmtReqDbtrEnvelope;
		private it.nch.eb.common.converter.pmtreq.dbtr.models.RecordCodaBodyModel recordCodaBody;
		private it.nch.eb.common.converter.common.models.RecordCodaModel recordCoda;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.dbtr.models.RecorddiTestaModel getRecorddiTesta() {
				return recorddiTesta;
			}
		
			public void setRecorddiTesta(it.nch.eb.common.converter.pmtreq.dbtr.models.RecorddiTestaModel recorddiTesta) {
				this.recorddiTesta = recorddiTesta;
			}
			public it.nch.eb.common.converter.pmtreq.dbtr.models.XmlRecordTestaBodyModel getRecordTestaBody() {
				return recordTestaBody;
			}
		
			public void setRecordTestaBody(it.nch.eb.common.converter.pmtreq.dbtr.models.XmlRecordTestaBodyModel recordTestaBody) {
				this.recordTestaBody = recordTestaBody;
			}
			public java.util.List getPmtReqDbtrEnvelope() {
				return pmtReqDbtrEnvelope;
			}
		
			public void setPmtReqDbtrEnvelope(java.util.List pmtReqDbtrEnvelope) {
				this.pmtReqDbtrEnvelope = pmtReqDbtrEnvelope;
			}
			public it.nch.eb.common.converter.pmtreq.dbtr.models.RecordCodaBodyModel getRecordCodaBody() {
				return recordCodaBody;
			}
		
			public void setRecordCodaBody(it.nch.eb.common.converter.pmtreq.dbtr.models.RecordCodaBodyModel recordCodaBody) {
				this.recordCodaBody = recordCodaBody;
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