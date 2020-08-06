
package it.nch.eb.ubi.converter.sepa2000.models;

import it.nch.eb.common.utils.StringUtils;


public class Sepa2000Document  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordTestaModel sepa2000RecordTesta;
		private java.util.List sepa2000RecordDettaglio;
		private it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordCodaModel sepa2000RecordCoda;
		private it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF1Model recordXF1;
		private it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF2Model recordXF2;
	
	private int	lineNumber;
	
		
			public it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordTestaModel getSepa2000RecordTesta() {
				return sepa2000RecordTesta;
			}
		
			public void setSepa2000RecordTesta(it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordTestaModel sepa2000RecordTesta) {
				this.sepa2000RecordTesta = sepa2000RecordTesta;
			}
			public java.util.List getSepa2000RecordDettaglio() {
				return sepa2000RecordDettaglio;
			}
		
			public void setSepa2000RecordDettaglio(java.util.List sepa2000RecordDettaglio) {
				this.sepa2000RecordDettaglio = sepa2000RecordDettaglio;
			}
			public it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordCodaModel getSepa2000RecordCoda() {
				return sepa2000RecordCoda;
			}
		
			public void setSepa2000RecordCoda(it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordCodaModel sepa2000RecordCoda) {
				this.sepa2000RecordCoda = sepa2000RecordCoda;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF1Model getRecordXF1() {
				return recordXF1;
			}
		
			public void setRecordXF1(it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF1Model recordXF1) {
				this.recordXF1 = recordXF1;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF2Model getRecordXF2() {
				return recordXF2;
			}
		
			public void setRecordXF2(it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF2Model recordXF2) {
				this.recordXF2 = recordXF2;
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