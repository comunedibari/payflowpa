package it.nch.pagamenti.creditcard;

import java.util.Date;

public class DisposizioneRidVO extends DisposizioneCartaCreditoVO {

	private Date scadenza;

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
}
