package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneTesoreriaSubsetTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

public class RendicontazioneTesoreriaSubsetDto {

	public String codiceIpaEnte;
	public String identificativoFlussoRendicontazione;

	private SegnalazioneTO segnalazione;
	private RendicontazioneTesoreriaSubsetTO rendicontazioneTesoreriaSubsetTO;

	public String getCodiceIpaEnte() {
		return codiceIpaEnte;
	}

	public void setCodiceIpaEnte(String codiceIpaEnte) {
		this.codiceIpaEnte = codiceIpaEnte;
	}

	public String getIdentificativoFlussoRendicontazione() {
		return identificativoFlussoRendicontazione;
	}

	public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
		this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
	}

	public SegnalazioneTO getSegnalazione() {
		return segnalazione;
	}

	public void setSegnalazione(SegnalazioneTO segnalazione) {
		this.segnalazione = segnalazione;
	}

	public RendicontazioneTesoreriaSubsetTO getRendicontazioneTesoreriaSubsetTO() {
		return rendicontazioneTesoreriaSubsetTO;
	}

	public void setRendicontazioneTesoreriaSubsetTO(RendicontazioneTesoreriaSubsetTO rendicontazioneTesoreriaSubsetTO) {
		this.rendicontazioneTesoreriaSubsetTO = rendicontazioneTesoreriaSubsetTO;
	}

}
