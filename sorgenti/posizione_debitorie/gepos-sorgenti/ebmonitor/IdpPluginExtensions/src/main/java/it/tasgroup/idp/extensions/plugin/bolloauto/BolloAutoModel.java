package it.tasgroup.idp.extensions.plugin.bolloauto;

import java.util.Date;

import it.tasgroup.idp.plugin.api.DettaglioStrutturatoModel;

public class BolloAutoModel extends DettaglioStrutturatoModel {

	private static final long serialVersionUID = 1L;
	
	
	String targa;
	String tipoVeicolo;
	Date dataDecorrenza;
    int intervalloValidita;
    String pagFrazionato;
	
	public String getTarga() {
		return targa;
	}

	public String getTipoVeicolo() {
		return tipoVeicolo;
	}

    
	public void setTarga(String targa) {
		//eseguo uppercase
		//lo faccio qui anzichè nella DDL di Ibatis
		if (targa!=null) {
			this.targa = targa.toUpperCase();	
		} else {
			this.targa = targa;
		}		
	}


	public void setTipoVeicolo(String tipoVeicolo) {
		this.tipoVeicolo = tipoVeicolo;
	}



	@Override
	public String getQueryName() {
		return "insertBolloAuto";
	}

	public Date getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Date dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public int getIntervalloValidita() {
		return intervalloValidita;
	}

	public void setIntervalloValidita(int intervalloValidita) {
		this.intervalloValidita = intervalloValidita;
	}
	
	public String getPagFrazionato() {
		return pagFrazionato;
	}
	
	public void setPagFrazionato(String pagFrazionato) {
		this.pagFrazionato = pagFrazionato;
	}
	
	
	
	
}
