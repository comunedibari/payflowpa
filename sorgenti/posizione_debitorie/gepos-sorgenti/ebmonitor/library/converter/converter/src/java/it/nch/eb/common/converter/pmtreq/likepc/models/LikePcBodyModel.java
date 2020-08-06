
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class LikePcBodyModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record10Model record10;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record16Model record16;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record17Model record17;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record20Model record20;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record30Model record30;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record40Model record40;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record50Model record50;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record60Model record60;
		private it.nch.eb.common.converter.pmtreq.likepc.models.Record70Model record70;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record10Model getRecord10() {
				return record10;
			}
		
			public void setRecord10(it.nch.eb.common.converter.pmtreq.likepc.models.Record10Model record10) {
				this.record10 = record10;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record16Model getRecord16() {
				return record16;
			}
		
			public void setRecord16(it.nch.eb.common.converter.pmtreq.likepc.models.Record16Model record16) {
				this.record16 = record16;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record17Model getRecord17() {
				return record17;
			}
		
			public void setRecord17(it.nch.eb.common.converter.pmtreq.likepc.models.Record17Model record17) {
				this.record17 = record17;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record20Model getRecord20() {
				return record20;
			}
		
			public void setRecord20(it.nch.eb.common.converter.pmtreq.likepc.models.Record20Model record20) {
				this.record20 = record20;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record30Model getRecord30() {
				return record30;
			}
		
			public void setRecord30(it.nch.eb.common.converter.pmtreq.likepc.models.Record30Model record30) {
				this.record30 = record30;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record40Model getRecord40() {
				return record40;
			}
		
			public void setRecord40(it.nch.eb.common.converter.pmtreq.likepc.models.Record40Model record40) {
				this.record40 = record40;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record50Model getRecord50() {
				return record50;
			}
		
			public void setRecord50(it.nch.eb.common.converter.pmtreq.likepc.models.Record50Model record50) {
				this.record50 = record50;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record60Model getRecord60() {
				return record60;
			}
		
			public void setRecord60(it.nch.eb.common.converter.pmtreq.likepc.models.Record60Model record60) {
				this.record60 = record60;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.Record70Model getRecord70() {
				return record70;
			}
		
			public void setRecord70(it.nch.eb.common.converter.pmtreq.likepc.models.Record70Model record70) {
				this.record70 = record70;
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