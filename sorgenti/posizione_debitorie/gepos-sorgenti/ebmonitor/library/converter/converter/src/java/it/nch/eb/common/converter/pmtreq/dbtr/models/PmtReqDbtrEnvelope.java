
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtReqDbtrEnvelope  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.dbtr.models.Record01Model record01;
		private java.util.List pmtReqDbtrTrxInfo;
		private java.util.List pmtReqDbtrStatusInfo;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.dbtr.models.Record01Model getRecord01() {
				return record01;
			}
		
			public void setRecord01(it.nch.eb.common.converter.pmtreq.dbtr.models.Record01Model record01) {
				this.record01 = record01;
			}
			public java.util.List getPmtReqDbtrTrxInfo() {
				return pmtReqDbtrTrxInfo;
			}
		
			public void setPmtReqDbtrTrxInfo(java.util.List pmtReqDbtrTrxInfo) {
				this.pmtReqDbtrTrxInfo = pmtReqDbtrTrxInfo;
			}
			public java.util.List getPmtReqDbtrStatusInfo() {
				return pmtReqDbtrStatusInfo;
			}
		
			public void setPmtReqDbtrStatusInfo(java.util.List pmtReqDbtrStatusInfo) {
				this.pmtReqDbtrStatusInfo = pmtReqDbtrStatusInfo;
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