package it.tasgroup.idp.cart.core.quadratura;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatiQuadratura {

	
	private String soggetto;
	private String sil;
	private Date dataInizio;
	private Date dataFine;
	private Report reportAP;
	private Report reportIP;
	
	public DatiQuadratura(String soggetto, String sil, Date dataInizio){
		this(soggetto, sil, dataInizio, null);
	}
	
	public DatiQuadratura(String soggetto, String sil, Date dataInizio, Date dataFine){
		this.dataFine= dataFine;
		this.dataInizio = dataInizio;
		this.soggetto = soggetto;
		this.sil = sil;
		
		// caso Pdf data fine = datainizio + 7 giorni
		if(this.dataFine == null){
			try{
				GregorianCalendar cal = new GregorianCalendar();
				cal.setTime(this.dataInizio);
				cal.add(Calendar.DAY_OF_MONTH, 8);
				cal.add(Calendar.MILLISECOND, -1); 
				this.dataFine = cal.getTime();
			}catch(Exception e){
				
			}
		}
	}

	public String getSoggetto() {
		return soggetto;
	}

	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}

	public String getSil() {
		return sil;
	}

	public void setSil(String sil) {
		this.sil = sil;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Date dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public void setDataFine(Date dataFine) {
		this.dataFine = dataFine;
	}

	public Report getReportAP() {
		return reportAP;
	}

	public void setReportAP(Report reportAP) {
		this.reportAP = reportAP;
	}

	public Report getReportIP() {
		return reportIP;
	}

	public void setReportIP(Report reportIP) {
		this.reportIP = reportIP;
	}
	
	public  String getElencoParametriAsString(){
		StringBuilder sb = new StringBuilder();
		
		sb.append("SIL: [").append(this.sil).append("]");
		sb.append(" Soggetto: [").append(this.soggetto).append("]");
		sb.append(" DataInizio: [").append(this.dataInizio).append("]");
		sb.append(" DataFine: [").append(this.dataFine).append("]");
		
		return sb.toString();
	}
	
}
