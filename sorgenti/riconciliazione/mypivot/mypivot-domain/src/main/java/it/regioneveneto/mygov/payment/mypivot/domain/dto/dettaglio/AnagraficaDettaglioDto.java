package it.regioneveneto.mygov.payment.mypivot.domain.dto.dettaglio;

public class AnagraficaDettaglioDto {
	private String codice;
	private String anagrafica;
	private String tipoIdentificativo;
	
	public AnagraficaDettaglioDto(String codice, String anagrafica, String tipoIdentificativo) {
		super();
		this.codice = codice;
		this.anagrafica = anagrafica;
		this.tipoIdentificativo = tipoIdentificativo;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getAnagrafica() {
		return anagrafica;
	}
	public void setAnagrafica(String anagrafica) {
		this.anagrafica = anagrafica;
	}
	public String getTipoIdentificativo() {
		return tipoIdentificativo;
	}
	public void setTipoIdentificativo(String tipoIdentificativo) {
		this.tipoIdentificativo = tipoIdentificativo;
	}
	
}
