
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record62sModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord62Model record62;
		private java.util.List record65;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord66sModel xmlRecord66Model;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord68sModel xmlRecord68Model;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord62Model getRecord62() {
				return record62;
			}

			public void setRecord62(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord62Model record62) {
				this.record62 = record62;
			}
			public java.util.List getRecord65() {
				return record65;
			}

			public void setRecord65(java.util.List record65) {
				this.record65 = record65;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord66sModel getXmlRecord66Model() {
				return xmlRecord66Model;
			}

			public void setXmlRecord66Model(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord66sModel xmlRecord66Model) {
				this.xmlRecord66Model = xmlRecord66Model;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord68sModel getXmlRecord68Model() {
				return xmlRecord68Model;
			}

			public void setXmlRecord68Model(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord68sModel xmlRecord68Model) {
				this.xmlRecord68Model = xmlRecord68Model;
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