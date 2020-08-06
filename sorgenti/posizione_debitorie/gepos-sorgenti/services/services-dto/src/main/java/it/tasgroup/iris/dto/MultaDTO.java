package it.tasgroup.iris.dto;

public class MultaDTO {
	private String targa;
	private String data;
	private String numVerbale;
	private String serie;
	private String importo;
	
	public String getTarga() {
		return targa;
	}
	public void setTarga(String targa) {
		this.targa = targa;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNumVerbale() {
		return numVerbale;
	}
	public void setNumVerbale(String numVerbale) {
		this.numVerbale = numVerbale;
	}
	public String getSerie() {
		return serie;
	}
	public void setSerie(String serie) {
		this.serie = serie;
	}
	public String getImporto() {
		return importo;
	}
	public void setImporto(String importo) {
		this.importo = importo;
	}
	@Override
	public String toString() {
		return "MultaDTO [targa=" + targa + ", data=" + data + ", numVerbale=" + numVerbale + ", serie=" + serie + ", importo=" + importo + "]";
	}

}
