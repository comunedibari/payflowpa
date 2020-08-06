
package it.nch.eb.common.converter.pmtreq.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtreqBodyItem  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.models.Record01Model record01;
		private java.util.List record11;
		private it.nch.eb.common.converter.pmtreq.models.Record20Model record20;
		private it.nch.eb.common.converter.pmtreq.models.Record30Model record30;
		private java.util.List record40;
		private java.util.List record50;
		private java.util.List record60;
		private it.nch.eb.common.converter.pmtreq.models.Record65Model record65;
		private it.nch.eb.common.converter.pmtreq.models.Record70Model record70;
		private it.nch.eb.common.converter.pmtreq.models.Record80Model record80;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.models.Record01Model getRecord01() {
				return record01;
			}
		
			public void setRecord01(it.nch.eb.common.converter.pmtreq.models.Record01Model record01) {
				this.record01 = record01;
			}
			public java.util.List getRecord11() {
				return record11;
			}
		
			public void setRecord11(java.util.List record11) {
				this.record11 = record11;
			}
			public it.nch.eb.common.converter.pmtreq.models.Record20Model getRecord20() {
				return record20;
			}
		
			public void setRecord20(it.nch.eb.common.converter.pmtreq.models.Record20Model record20) {
				this.record20 = record20;
			}
			public it.nch.eb.common.converter.pmtreq.models.Record30Model getRecord30() {
				return record30;
			}
		
			public void setRecord30(it.nch.eb.common.converter.pmtreq.models.Record30Model record30) {
				this.record30 = record30;
			}
			public java.util.List getRecord40() {
				return record40;
			}
		
			public void setRecord40(java.util.List record40) {
				this.record40 = record40;
			}
			public java.util.List getRecord50() {
				return record50;
			}
		
			public void setRecord50(java.util.List record50) {
				this.record50 = record50;
			}
			public java.util.List getRecord60() {
				return record60;
			}
		
			public void setRecord60(java.util.List record60) {
				this.record60 = record60;
			}
			public it.nch.eb.common.converter.pmtreq.models.Record65Model getRecord65() {
				return record65;
			}
		
			public void setRecord65(it.nch.eb.common.converter.pmtreq.models.Record65Model record65) {
				this.record65 = record65;
			}
			public it.nch.eb.common.converter.pmtreq.models.Record70Model getRecord70() {
				return record70;
			}
		
			public void setRecord70(it.nch.eb.common.converter.pmtreq.models.Record70Model record70) {
				this.record70 = record70;
			}
			public it.nch.eb.common.converter.pmtreq.models.Record80Model getRecord80() {
				return record80;
			}
		
			public void setRecord80(it.nch.eb.common.converter.pmtreq.models.Record80Model record80) {
				this.record80 = record80;
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