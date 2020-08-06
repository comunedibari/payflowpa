
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class CdtrBody  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.RecordTestaBodyModel recordTestaBody;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.Record01Model record01;
		private java.util.List xmlRecord30Model;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.RecordCodaBodyModel recordCodaBody;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.RecordTestaBodyModel getRecordTestaBody() {
				return recordTestaBody;
			}

			public void setRecordTestaBody(it.nch.eb.common.converter.pmtreq.cdtr.models.RecordTestaBodyModel recordTestaBody) {
				this.recordTestaBody = recordTestaBody;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.Record01Model getRecord01() {
				return record01;
			}

			public void setRecord01(it.nch.eb.common.converter.pmtreq.cdtr.models.Record01Model record01) {
				this.record01 = record01;
			}
			public java.util.List getXmlRecord30Model() {
				return xmlRecord30Model;
			}

			public void setXmlRecord30Model(java.util.List xmlRecord30Model) {
				this.xmlRecord30Model = xmlRecord30Model;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.RecordCodaBodyModel getRecordCodaBody() {
				return recordCodaBody;
			}

			public void setRecordCodaBody(it.nch.eb.common.converter.pmtreq.cdtr.models.RecordCodaBodyModel recordCodaBody) {
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