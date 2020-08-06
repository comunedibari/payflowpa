
package it.nch.eb.common.converter.pmtreq.advinf.models;

import it.nch.eb.common.utils.StringUtils;


public class AdvinfBodyModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.advinf.models.RecordTestaBodyModel recordTestaBody;
		private it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord01Model record01;
		private it.nch.eb.common.converter.pmtreq.advinf.models.Record02Model record02;
		private it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord03Model record03;
		private java.util.List record04;
		private it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord05Model record05;
		private it.nch.eb.common.converter.pmtreq.advinf.models.Record06Model record06;
		private it.nch.eb.common.converter.pmtreq.advinf.models.Record07Model record07;
		private java.util.List record08;
		private it.nch.eb.common.converter.pmtreq.advinf.models.Record09Model record09;
		private it.nch.eb.common.converter.pmtreq.advinf.models.Record10Model record10;
		private it.nch.eb.common.converter.pmtreq.advinf.models.Record11Model record11;
		private it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaBodyModel recordCodaBody;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.advinf.models.RecordTestaBodyModel getRecordTestaBody() {
				return recordTestaBody;
			}
		
			public void setRecordTestaBody(it.nch.eb.common.converter.pmtreq.advinf.models.RecordTestaBodyModel recordTestaBody) {
				this.recordTestaBody = recordTestaBody;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord01Model getRecord01() {
				return record01;
			}
		
			public void setRecord01(it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord01Model record01) {
				this.record01 = record01;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.Record02Model getRecord02() {
				return record02;
			}
		
			public void setRecord02(it.nch.eb.common.converter.pmtreq.advinf.models.Record02Model record02) {
				this.record02 = record02;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord03Model getRecord03() {
				return record03;
			}
		
			public void setRecord03(it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord03Model record03) {
				this.record03 = record03;
			}
			public java.util.List getRecord04() {
				return record04;
			}
		
			public void setRecord04(java.util.List record04) {
				this.record04 = record04;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord05Model getRecord05() {
				return record05;
			}
		
			public void setRecord05(it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord05Model record05) {
				this.record05 = record05;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.Record06Model getRecord06() {
				return record06;
			}
		
			public void setRecord06(it.nch.eb.common.converter.pmtreq.advinf.models.Record06Model record06) {
				this.record06 = record06;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.Record07Model getRecord07() {
				return record07;
			}
		
			public void setRecord07(it.nch.eb.common.converter.pmtreq.advinf.models.Record07Model record07) {
				this.record07 = record07;
			}
			public java.util.List getRecord08() {
				return record08;
			}
		
			public void setRecord08(java.util.List record08) {
				this.record08 = record08;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.Record09Model getRecord09() {
				return record09;
			}
		
			public void setRecord09(it.nch.eb.common.converter.pmtreq.advinf.models.Record09Model record09) {
				this.record09 = record09;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.Record10Model getRecord10() {
				return record10;
			}
		
			public void setRecord10(it.nch.eb.common.converter.pmtreq.advinf.models.Record10Model record10) {
				this.record10 = record10;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.Record11Model getRecord11() {
				return record11;
			}
		
			public void setRecord11(it.nch.eb.common.converter.pmtreq.advinf.models.Record11Model record11) {
				this.record11 = record11;
			}
			public it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaBodyModel getRecordCodaBody() {
				return recordCodaBody;
			}
		
			public void setRecordCodaBody(it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaBodyModel recordCodaBody) {
				this.recordCodaBody = recordCodaBody;
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