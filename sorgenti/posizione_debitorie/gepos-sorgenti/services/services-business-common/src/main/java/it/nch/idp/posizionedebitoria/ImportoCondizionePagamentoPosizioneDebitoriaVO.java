package it.nch.idp.posizionedebitoria;

import java.io.Serializable;
import java.math.BigDecimal;

public class ImportoCondizionePagamentoPosizioneDebitoriaVO implements Serializable
{
	private String deVoce;
	private BigDecimal imVoce;

	public BigDecimal getImVoce() {
		return imVoce;
	}

	public void setImVoce(BigDecimal decimal1) {
		imVoce = decimal1;
	}

	public String getDeVoce() {
		return deVoce;
	}

	public void setDeVoce(String string) {
		deVoce = string;
	}

}
