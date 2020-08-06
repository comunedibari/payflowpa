package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;

public class FlussoRicevutaDto {

	private String codiceIpaEnte;
	private String deImportoTotale;
	private String deImportoSingolo;
	private String codESoggPagIdUnivPagTipoIdUnivoco;
	private String codESoggVersIdUnivVersTipoIdUnivoco;
	private String deTipoDovuto;

	private FlussoExportTO flussoExportTO;

	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}

	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}

	public String getDeImportoTotale() {
		return deImportoTotale;
	}

	public void setDeImportoTotale(String deImportoTotale) {
		this.deImportoTotale = deImportoTotale;
	}

	public String getDeImportoSingolo() {
		return deImportoSingolo;
	}

	public void setDeImportoSingolo(String deImportoSingolo) {
		this.deImportoSingolo = deImportoSingolo;
	}

	public String getCodESoggPagIdUnivPagTipoIdUnivoco() {
		return codESoggPagIdUnivPagTipoIdUnivoco;
	}

	public void setCodESoggPagIdUnivPagTipoIdUnivoco(String codESoggPagIdUnivPagTipoIdUnivoco) {
		this.codESoggPagIdUnivPagTipoIdUnivoco = codESoggPagIdUnivPagTipoIdUnivoco;
	}

	public String getCodESoggVersIdUnivVersTipoIdUnivoco() {
		return codESoggVersIdUnivVersTipoIdUnivoco;
	}

	public void setCodESoggVersIdUnivVersTipoIdUnivoco(String codESoggVersIdUnivVersTipoIdUnivoco) {
		this.codESoggVersIdUnivVersTipoIdUnivoco = codESoggVersIdUnivVersTipoIdUnivoco;
	}

	public String getDeTipoDovuto() {
		return deTipoDovuto;
	}

	public void setDeTipoDovuto(String deTipoDovuto) {
		this.deTipoDovuto = deTipoDovuto;
	}

	public FlussoExportTO getFlussoExportTO() {
		return flussoExportTO;
	}

	public void setFlussoExportTO(FlussoExportTO flussoExportTO) {
		this.flussoExportTO = flussoExportTO;
	}

}
