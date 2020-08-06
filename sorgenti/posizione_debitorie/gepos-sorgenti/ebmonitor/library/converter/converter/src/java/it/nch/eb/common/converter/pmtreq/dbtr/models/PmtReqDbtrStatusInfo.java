
package it.nch.eb.common.converter.pmtreq.dbtr.models;

import it.nch.eb.common.utils.StringUtils;

public class PmtReqDbtrStatusInfo  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord30Model record30;
		private java.util.List record32;
		private java.util.List record34;
		private java.util.List record36;
		private it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord40Model record40;
		private java.util.List record80;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord30Model getRecord30() {
				return record30;
			}
		
			public void setRecord30(it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord30Model record30) {
				this.record30 = record30;
			}
			public java.util.List getRecord32() {
				return record32;
			}
		
			public void setRecord32(java.util.List record32) {
				this.record32 = record32;
			}
			public java.util.List getRecord34() {
				return record34;
			}
		
			public void setRecord34(java.util.List record34) {
				this.record34 = record34;
			}
			public java.util.List getRecord36() {
				return record36;
			}
		
			public void setRecord36(java.util.List record36) {
				this.record36 = record36;
			}
			public it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord40Model getRecord40() {
				return record40;
			}
		
			public void setRecord40(it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord40Model record40) {
				this.record40 = record40;
			}
			
	
	public int getLineNumber() {
		return lineNumber;
	}
	
	public java.util.List getRecord80() {
		return record80;
	}
	
	public void setRecord80(java.util.List record80) {
		this.record80 = record80;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) + "\n" + StringUtils.toString(this);
	}
	
}