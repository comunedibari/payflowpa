
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record66sModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.Record66Model record66;
		private java.util.List record67;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.Record66Model getRecord66() {
				return record66;
			}

			public void setRecord66(it.nch.eb.common.converter.pmtreq.cdtr.models.Record66Model record66) {
				this.record66 = record66;
			}
			public java.util.List getRecord67() {
				return record67;
			}

			public void setRecord67(java.util.List record67) {
				this.record67 = record67;
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