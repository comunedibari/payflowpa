package it.tasgroup.idp.cart.core.quadratura;

import it.tasgroup.idp.cart.core.model.ErroreComponenteModel;

public class Err {

	private ErroreComponenteModel tipoErrore;
	private long occorenze;
	public ErroreComponenteModel getTipoErrore() {
		return tipoErrore;
	}
	public void setTipoErrore(ErroreComponenteModel tipoErrore) {
		this.tipoErrore = tipoErrore;
	}
	public long getOccorenze() {
		return occorenze;
	}
	public void setOccorenze(long occorenze) {
		this.occorenze = occorenze;
	}
}
