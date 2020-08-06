
package it.nch.eb.common.converter.pmtreq.likepc.models;

import it.nch.eb.common.utils.StringUtils;


public class LikePcModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private it.nch.eb.common.converter.pmtreq.likepc.models.RecordPCModel recordPC;
		private it.nch.eb.common.converter.pmtreq.likepc.models.LikePcBodyModel likePcBodyModel;
		private it.nch.eb.common.converter.pmtreq.likepc.models.RecordEFModel recordEF;
		private it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF1Model recordXF1;
		private it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF2Model recordXF2;
	
	private int	lineNumber;
	
		
			public it.nch.eb.common.converter.pmtreq.likepc.models.RecordPCModel getRecordPC() {
				return recordPC;
			}
		
			public void setRecordPC(it.nch.eb.common.converter.pmtreq.likepc.models.RecordPCModel recordPC) {
				this.recordPC = recordPC;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.LikePcBodyModel getLikePcBodyModel() {
				return likePcBodyModel;
			}
		
			public void setLikePcBodyModel(it.nch.eb.common.converter.pmtreq.likepc.models.LikePcBodyModel likePcBodyModel) {
				this.likePcBodyModel = likePcBodyModel;
			}
			public it.nch.eb.common.converter.pmtreq.likepc.models.RecordEFModel getRecordEF() {
				return recordEF;
			}
		
			public void setRecordEF(it.nch.eb.common.converter.pmtreq.likepc.models.RecordEFModel recordEF) {
				this.recordEF = recordEF;
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