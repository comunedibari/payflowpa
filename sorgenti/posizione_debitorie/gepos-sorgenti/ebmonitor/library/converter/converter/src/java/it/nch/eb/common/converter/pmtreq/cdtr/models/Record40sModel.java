
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record40sModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40Model record40;
		private java.util.List record60;
		private java.util.List xmlRecord62Model;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord80Model record80;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord82Model record82;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord84Model record84;
		private it.nch.eb.common.converter.pmtreq.cdtr.models.Record86Model record86;
	
	private int	lineNumber;
	

			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40Model getRecord40() {
				return record40;
			}

			public void setRecord40(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40Model record40) {
				this.record40 = record40;
			}
			public java.util.List getRecord60() {
				return record60;
			}

			public void setRecord60(java.util.List record60) {
				this.record60 = record60;
			}
			public java.util.List getXmlRecord62Model() {
				return xmlRecord62Model;
			}

			public void setXmlRecord62Model(java.util.List xmlRecord62Model) {
				this.xmlRecord62Model = xmlRecord62Model;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord80Model getRecord80() {
				return record80;
			}

			public void setRecord80(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord80Model record80) {
				this.record80 = record80;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord82Model getRecord82() {
				return record82;
			}

			public void setRecord82(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord82Model record82) {
				this.record82 = record82;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord84Model getRecord84() {
				return record84;
			}

			public void setRecord84(it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord84Model record84) {
				this.record84 = record84;
			}
			public it.nch.eb.common.converter.pmtreq.cdtr.models.Record86Model getRecord86() {
				return record86;
			}

			public void setRecord86(it.nch.eb.common.converter.pmtreq.cdtr.models.Record86Model record86) {
				this.record86 = record86;
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