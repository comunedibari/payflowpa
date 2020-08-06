
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record01Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String orgnlMsgId;
		private String orgnlCreDtTm;
		private String filler1;
		private String filler2;
		private String filler3;
	
	private int	lineNumber;
	

			public String getFiller() {
				return filler;
			}

			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipo_record() {
				return tipo_record;
			}

			public void setTipo_record(String tipo_record) {
				this.tipo_record = tipo_record;
			}
			public String getRec_count() {
				return rec_count;
			}

			public void setRec_count(String rec_count) {
				this.rec_count = rec_count;
			}
			public String getOrgnlMsgId() {
				return orgnlMsgId;
			}

			public void setOrgnlMsgId(String orgnlMsgId) {
				this.orgnlMsgId = orgnlMsgId;
			}
			public String getOrgnlCreDtTm() {
				return orgnlCreDtTm;
			}

			public void setOrgnlCreDtTm(String orgnlCreDtTm) {
				this.orgnlCreDtTm = orgnlCreDtTm;
			}
			public String getFiller1() {
				return filler1;
			}

			public void setFiller1(String filler1) {
				this.filler1 = filler1;
			}
			public String getFiller2() {
				return filler2;
			}

			public void setFiller2(String filler2) {
				this.filler2 = filler2;
			}
			public String getFiller3() {
				return filler3;
			}

			public void setFiller3(String filler3) {
				this.filler3 = filler3;
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