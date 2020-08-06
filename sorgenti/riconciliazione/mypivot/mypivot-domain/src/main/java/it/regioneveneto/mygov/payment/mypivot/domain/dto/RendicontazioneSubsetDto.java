package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.RendicontazioneSubsetTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.SegnalazioneTO;

public class RendicontazioneSubsetDto {
	
	private RendicontazioneSubsetTO rendicontazioneSubsetTO;
	
	//campi della chiave "RendicontazioneSubsetId"
//	private String dataOraFlussoRendicontazione;
	private String identificativoFlussoRendicontazione;
	private SegnalazioneTO segnalazione;
	
	public String getIdentificativoFlussoRendicontazione() {
		return identificativoFlussoRendicontazione;
	}
	public void setIdentificativoFlussoRendicontazione(String identificativoFlussoRendicontazione) {
		this.identificativoFlussoRendicontazione = identificativoFlussoRendicontazione;
	}
	public RendicontazioneSubsetTO getRendicontazioneSubsetTO() {
		return rendicontazioneSubsetTO;
	}
	public void setRendicontazioneSubsetTO(RendicontazioneSubsetTO rendicontazioneSubsetTO) {
		this.rendicontazioneSubsetTO = rendicontazioneSubsetTO;
	}
//	public String getDataOraFlussoRendicontazione() {
//		return dataOraFlussoRendicontazione;
//	}
//	public void setDataOraFlussoRendicontazione(String dataOraFlussoRendicontazione) {
//		this.dataOraFlussoRendicontazione = dataOraFlussoRendicontazione;
//	}
	public SegnalazioneTO getSegnalazione() {
		return segnalazione;
	}
	public void setSegnalazione(SegnalazioneTO segnalazione) {
		this.segnalazione = segnalazione;
	}
}
