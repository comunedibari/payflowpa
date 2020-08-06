
package it.nch.eb.common.converter.pmtreq.cdtr.models;

import it.nch.eb.common.utils.StringUtils;


public class Record65Model  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {

		private String fiiller;
		private String tipoRec;
		private String recCount;
		private String tipoImporto;
		private String importo;
		private String divisa;
	
	private int	lineNumber;
	

			public String getFiiller() {
				return fiiller;
			}

			public void setFiiller(String fiiller) {
				this.fiiller = fiiller;
			}
			public String getTipoRec() {
				return tipoRec;
			}

			public void setTipoRec(String tipoRec) {
				this.tipoRec = tipoRec;
			}
			public String getRecCount() {
				return recCount;
			}

			public void setRecCount(String recCount) {
				this.recCount = recCount;
			}
			public String getTipoImporto() {
				return tipoImporto;
			}

			public void setTipoImporto(String tipoImporto) {
				this.tipoImporto = tipoImporto;
			}
			public String getImporto() {
				return importo;
			}

			public void setImporto(String importo) {
				this.importo = importo;
			}
			public String getDivisa() {
				return divisa;
			}

			public void setDivisa(String divisa) {
				this.divisa = divisa;
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