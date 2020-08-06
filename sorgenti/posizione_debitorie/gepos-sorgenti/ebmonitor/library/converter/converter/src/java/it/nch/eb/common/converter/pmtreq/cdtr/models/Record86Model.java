
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record86Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String filler;
		private String tipo_record;
		private String rec_count;
		private String nm;
		private String id;
		private String issr;
	
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
			public String getNm() {
				return nm;
			}

			public void setNm(String nm) {
				this.nm = nm;
			}
			public String getId() {
				return id;
			}

			public void setId(String id) {
				this.id = id;
			}
			public String getIssr() {
				return issr;
			}

			public void setIssr(String issr) {
				this.issr = issr;
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