
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record68sModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.Record68Model record68;
		private java.util.List record69;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.Record68Model getRecord68() {
				return record68;
			}

			public void setRecord68(it.nch.eb.common.converter.pmtreq.cdtr.models.Record68Model record68) {
				this.record68 = record68;
			}
			public java.util.List getRecord69() {
				return record69;
			}

			public void setRecord69(java.util.List record69) {
				this.record69 = record69;
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