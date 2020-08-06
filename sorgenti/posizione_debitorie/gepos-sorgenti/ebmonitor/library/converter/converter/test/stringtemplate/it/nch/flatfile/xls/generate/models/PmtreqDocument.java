
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtreqDocument  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.common.models.RecordTestaModel recordTestaExp;
		private it.nch.flatfile.xls.generate.models.RecordTestaBodyModel recordTestaBody;
		private java.util.List pmtreqBody;
		private it.nch.flatfile.xls.generate.models.RecordCodaBodyModel recordCodaBody;
		private it.nch.eb.common.converter.common.models.RecordCodaModel recordCodaExp;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.common.models.RecordTestaModel getRecordTestaExp() {
				return recordTestaExp;
			}
		
			public void setRecordTestaExp(it.nch.eb.common.converter.common.models.RecordTestaModel recordTestaExp) {
				this.recordTestaExp = recordTestaExp;
			}
			public it.nch.flatfile.xls.generate.models.RecordTestaBodyModel getRecordTestaBody() {
				return recordTestaBody;
			}
		
			public void setRecordTestaBody(it.nch.flatfile.xls.generate.models.RecordTestaBodyModel recordTestaBody) {
				this.recordTestaBody = recordTestaBody;
			}
			public java.util.List getPmtreqBody() {
				return pmtreqBody;
			}
		
			public void setPmtreqBody(java.util.List pmtreqBody) {
				this.pmtreqBody = pmtreqBody;
			}
			public it.nch.flatfile.xls.generate.models.RecordCodaBodyModel getRecordCodaBody() {
				return recordCodaBody;
			}
		
			public void setRecordCodaBody(it.nch.flatfile.xls.generate.models.RecordCodaBodyModel recordCodaBody) {
				this.recordCodaBody = recordCodaBody;
			}
			public it.nch.eb.common.converter.common.models.RecordCodaModel getRecordCodaExp() {
				return recordCodaExp;
			}
		
			public void setRecordCodaExp(it.nch.eb.common.converter.common.models.RecordCodaModel recordCodaExp) {
				this.recordCodaExp = recordCodaExp;
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