package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;

public class FlussoRendicontazioneDto {

	private String codiceIpaEnte;
	private String deImporto;

	private FlussoRendicontazioneTO flussoRendicontazioneTO;

	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}

	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}

	public String getDeImporto() {
		return deImporto;
	}

	public void setDeImporto(String deImporto) {
		this.deImporto = deImporto;
	}

	public FlussoRendicontazioneTO getFlussoRendicontazioneTO() {
		return flussoRendicontazioneTO;
	}

	public void setFlussoRendicontazioneTO(FlussoRendicontazioneTO flussoRendicontazioneTO) {
		this.flussoRendicontazioneTO = flussoRendicontazioneTO;
	}

}
