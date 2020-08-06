
package it.nch.flatfile.xls.generate.models;

import it.nch.eb.common.utils.StringUtils;


public class PmtreqBody  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.flatfile.xls.generate.models.Record00Model record00;
		private it.nch.flatfile.xls.generate.models.Record01Model record01;
		private it.nch.flatfile.xls.generate.models.Record20Model record20;
		private it.nch.flatfile.xls.generate.models.Record30Model record30;
		private java.util.List record40;
		private it.nch.flatfile.xls.generate.models.Record50Model record50;
		private it.nch.flatfile.xls.generate.models.Record65Model record65;
		private it.nch.flatfile.xls.generate.models.Record70Model record70;
		private it.nch.flatfile.xls.generate.models.Record80Model record80;
	
	private int	lineNumber;
	
		
			public it.nch.flatfile.xls.generate.models.Record00Model getRecord00() {
				return record00;
			}
		
			public void setRecord00(it.nch.flatfile.xls.generate.models.Record00Model record00) {
				this.record00 = record00;
			}
			public it.nch.flatfile.xls.generate.models.Record01Model getRecord01() {
				return record01;
			}
		
			public void setRecord01(it.nch.flatfile.xls.generate.models.Record01Model record01) {
				this.record01 = record01;
			}
			public it.nch.flatfile.xls.generate.models.Record20Model getRecord20() {
				return record20;
			}
		
			public void setRecord20(it.nch.flatfile.xls.generate.models.Record20Model record20) {
				this.record20 = record20;
			}
			public it.nch.flatfile.xls.generate.models.Record30Model getRecord30() {
				return record30;
			}
		
			public void setRecord30(it.nch.flatfile.xls.generate.models.Record30Model record30) {
				this.record30 = record30;
			}
			public java.util.List getRecord40() {
				return record40;
			}
		
			public void setRecord40(java.util.List record40) {
				this.record40 = record40;
			}
			public it.nch.flatfile.xls.generate.models.Record50Model getRecord50() {
				return record50;
			}
		
			public void setRecord50(it.nch.flatfile.xls.generate.models.Record50Model record50) {
				this.record50 = record50;
			}
			public it.nch.flatfile.xls.generate.models.Record65Model getRecord65() {
				return record65;
			}
		
			public void setRecord65(it.nch.flatfile.xls.generate.models.Record65Model record65) {
				this.record65 = record65;
			}
			public it.nch.flatfile.xls.generate.models.Record70Model getRecord70() {
				return record70;
			}
		
			public void setRecord70(it.nch.flatfile.xls.generate.models.Record70Model record70) {
				this.record70 = record70;
			}
			public it.nch.flatfile.xls.generate.models.Record80Model getRecord80() {
				return record80;
			}
		
			public void setRecord80(it.nch.flatfile.xls.generate.models.Record80Model record80) {
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