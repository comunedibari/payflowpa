
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record30sModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord30Model record30;
		private java.util.List record32;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40sModel xmlRecord40Model;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord30Model getRecord30() {
				return record30;
			}

			public void setRecord30(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord30Model record30) {
				this.record30 = record30;
			}
			public java.util.List getRecord32() {
				return record32;
			}

			public void setRecord32(java.util.List record32) {
				this.record32 = record32;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40sModel getXmlRecord40Model() {
				return xmlRecord40Model;
			}

			public void setXmlRecord40Model(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40sModel xmlRecord40Model) {
				this.xmlRecord40Model = xmlRecord40Model;
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