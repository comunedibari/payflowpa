
package it.nch.eb.ubi.converter.sepa2000.models;

import it.nch.eb.common.utils.StringUtils;


public class Sepa2000RecordCodaModel  implements it.nch.eb.flatx.flatmodel.FilePositionProvider   {
	
		private String filler;
		private String tipoRecord;
		private String sia;
		private String ricevente;
		private String dataFlusso;
		private String blank1;
		private String numeroDisposizioni;
		private String blank2;
		private String totaleImporti;
		private String blank3;
	
	private int	lineNumber;
	
		
			public String getFiller() {
				return filler;
			}
		
			public void setFiller(String filler) {
				this.filler = filler;
			}
			public String getTipoRecord() {
				return tipoRecord;
			}
		
			public void setTipoRecord(String tipoRecord) {
				this.tipoRecord = tipoRecord;
			}
			public String getSia() {
				return sia;
			}
		
			public void setSia(String sia) {
				this.sia = sia;
			}
			public String getRicevente() {
				return ricevente;
			}
		
			public void setRicevente(String ricevente) {
				this.ricevente = ricevente;
			}
			public String getDataFlusso() {
				return dataFlusso;
			}
		
			public void setDataFlusso(String dataFlusso) {
				this.dataFlusso = dataFlusso;
			}
			public String getBlank1() {
				return blank1;
			}
		
			public void setBlank1(String blank1) {
				this.blank1 = blank1;
			}
			public String getNumeroDisposizioni() {
				return numeroDisposizioni;
			}
		
			public void setNumeroDisposizioni(String numeroDisposizioni) {
				this.numeroDisposizioni = numeroDisposizioni;
			}
			public String getBlank2() {
				return blank2;
			}
		
			public void setBlank2(String blank2) {
				this.blank2 = blank2;
			}
			public String getTotaleImporti() {
				return totaleImporti;
			}
		
			public void setTotaleImporti(String totaleImporti) {
				this.totaleImporti = totaleImporti;
			}
			public String getBlank3() {
				return blank3;
			}
		
			public void setBlank3(String blank3) {
				this.blank3 = blank3;
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