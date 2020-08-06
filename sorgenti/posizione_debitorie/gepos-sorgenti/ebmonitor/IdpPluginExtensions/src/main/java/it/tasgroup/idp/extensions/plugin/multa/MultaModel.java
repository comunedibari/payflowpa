package it.tasgroup.idp.extensions.plugin.multa;

import it.tasgroup.idp.plugin.api.DettaglioStrutturatoModel;

import java.sql.Timestamp;

public class MultaModel extends DettaglioStrutturatoModel {

	String targa;
	String numeroVerbale;
	Timestamp dataVerbale;
	String serieVerbale;
	
	
  


	public String getTarga() {
		return targa;
	}





	public String getNumeroVerbale() {
		return numeroVerbale;
	}





	public Timestamp getDataVerbale() {
		return dataVerbale;
	}





	public String getSerieVerbale() {
		return serieVerbale;
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





	public void setNumeroVerbale(String numeroVerbale) {		
		//eseguo uppercase
		//lo faccio qui anzichè nella DDL di Ibatis
		if (targa!=null) {
			this.numeroVerbale = numeroVerbale.toUpperCase();	
		} else {
			this.numeroVerbale = numeroVerbale;
		}				
	}





	public void setDataVerbale(Timestamp dataVerbale) {
		this.dataVerbale = dataVerbale;
	}





	public void setSerieVerbale(String serieVerbale) {		
		//eseguo uppercase
		//lo faccio qui anzichè nella DDL di Ibatis
		this.serieVerbale = (targa != null && serieVerbale != null) ? serieVerbale.toUpperCase() : serieVerbale;
	}





	@Override
	public String getQueryName() {
		return "insertMulta";
	}
	
	
	
	
}
