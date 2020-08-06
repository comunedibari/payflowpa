
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtreqBody  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.models.RecordTestaBodyModel recordTestaBody;
		private it.nch.eb.common.converter.pmtreq.models.Record00Model record00;
		private java.util.List pmtreqBodyItem;
		private it.nch.eb.common.converter.pmtreq.models.RecordCodaBodyModel recordCodaBody;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.models.RecordTestaBodyModel getRecordTestaBody() {
				return recordTestaBody;
			}
		
			public void setRecordTestaBody(it.nch.eb.common.converter.pmtreq.models.RecordTestaBodyModel recordTestaBody) {
				this.recordTestaBody = recordTestaBody;
			}
			public it.nch.eb.common.converter.pmtreq.models.Record00Model getRecord00() {
				return record00;
			}
		
			public void setRecord00(it.nch.eb.common.converter.pmtreq.models.Record00Model record00) {
				this.record00 = record00;
			}
			public java.util.List getPmtreqBodyItem() {
				return pmtreqBodyItem;
			}
		
			public void setPmtreqBodyItem(java.util.List pmtreqBodyItem) {
				this.pmtreqBodyItem = pmtreqBodyItem;
			}
			public it.nch.eb.common.converter.pmtreq.models.RecordCodaBodyModel getRecordCodaBody() {
				return recordCodaBody;
			}
		
			public void setRecordCodaBody(it.nch.eb.common.converter.pmtreq.models.RecordCodaBodyModel recordCodaBody) {
				this.recordCodaBody = recordCodaBody;
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