
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtReqDbtrTrxInfo  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.dbtr.models.Record20Model record20;
		private java.util.List record22;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.dbtr.models.Record20Model getRecord20() {
				return record20;
			}
		
			public void setRecord20(it.nch.eb.common.converter.pmtreq.dbtr.models.Record20Model record20) {
				this.record20 = record20;
			}
			public java.util.List getRecord22() {
				return record22;
			}
		
			public void setRecord22(java.util.List record22) {
				this.record22 = record22;
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