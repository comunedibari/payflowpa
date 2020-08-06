package it.regioneveneto.mygov.payment.mypivot.domain.dto.flussorendicontazione;

import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoExportTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.FlussoRendicontazioneTO;

public class DettaglioFlussoRendicontazioneDto {

	private FlussoRendicontazioneTO flussoRendicontazioneTO;
	private FlussoExportTO flussoExportTO;
	private String deTipoDovuto;

	public FlussoRendicontazioneTO getFlussoRendicontazioneTO() {
		return flussoRendicontazioneTO;
	}

	public void setFlussoRendicontazioneTO(FlussoRendicontazioneTO flussoRendicontazioneTO) {
		this.flussoRendicontazioneTO = flussoRendicontazioneTO;
	}

	public FlussoExportTO getFlussoExportTO() {
		return flussoExportTO;
	}

	public void setFlussoExportTO(FlussoExportTO flussoExportTO) {
		this.flussoExportTO = flussoExportTO;
	}

	public String getDeTipoDovuto() {
		return deTipoDovuto;
	}

	public void setDeTipoDovuto(String deTipoDovuto) {
		this.deTipoDovuto = deTipoDovuto;
	}

}
