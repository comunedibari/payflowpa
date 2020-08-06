
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class RecordCodaBodyModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String msgId;
		private String idE2E;
		private String msgQual;
		private String creDtTm;
		private String id;
	
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
			public String getMsgId() {
				return msgId;
			}

			public void setMsgId(String msgId) {
				this.msgId = msgId;
			}
			public String getIdE2E() {
				return idE2E;
			}

			public void setIdE2E(String idE2E) {
				this.idE2E = idE2E;
			}
			public String getMsgQual() {
				return msgQual;
			}

			public void setMsgQual(String msgQual) {
				this.msgQual = msgQual;
			}
			public String getCreDtTm() {
				return creDtTm;
			}

			public void setCreDtTm(String creDtTm) {
				this.creDtTm = creDtTm;
			}
			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
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