
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class AdvinfModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.advinf.models.RecorddiTestaModel recorddiTesta;
		private java.util.List advinfBodyModel;
		private it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaModel recordCoda;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.advinf.models.RecorddiTestaModel getRecorddiTesta() {
				return recorddiTesta;
			}
		
			public void setRecorddiTesta(it.nch.eb.common.converter.pmtreq.advinf.models.RecorddiTestaModel recorddiTesta) {
				this.recorddiTesta = recorddiTesta;
			}
			public java.util.List getAdvinfBodyModel() {
				return advinfBodyModel;
			}
		
			public void setAdvinfBodyModel(java.util.List advinfBodyModel) {
				this.advinfBodyModel = advinfBodyModel;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaModel getRecordCoda() {
				return recordCoda;
			}
		
			public void setRecordCoda(it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaModel recordCoda) {
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