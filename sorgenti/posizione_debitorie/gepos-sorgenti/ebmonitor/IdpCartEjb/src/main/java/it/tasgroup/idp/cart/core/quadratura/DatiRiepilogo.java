package it.tasgroup.idp.cart.core.quadratura;

public class DatiRiepilogo {
	
	public enum StatoServizio {
		OK,WARN,FAIL
	}
	

	private String sil;
	private String soggetto;
	private String messaggioAP;
	private String messaggioAPE;
	private String messaggioIP;
	private StatoServizio statoServizioAP;
	private StatoServizio statoServizioAPE;
	private StatoServizio statoServizioIP;
 
	public String getSil() {
		return sil;
	}
	public void setSil(String sil) {
		this.sil = sil;
	}
	public String getSoggetto() {
		return soggetto;
	}
	public void setSoggetto(String soggetto) {
		this.soggetto = soggetto;
	}
	public String getMessaggioAP() {
		return messaggioAP;
	}
	public void setMessaggioAP(String messaggioAP) {
		this.messaggioAP = messaggioAP;
	}
	public String getMessaggioIP() {
		return messaggioIP;
	}
	public void setMessaggioIP(String messaggioIP) {
		this.messaggioIP = messaggioIP;
	}
	public StatoServizio getStatoServizioAP() {
		return statoServizioAP;
	}
	public void setStatoServizioAP(StatoServizio statoServizioAP) {
		this.statoServizioAP = statoServizioAP;
	}
	public StatoServizio getStatoServizioIP() {
		return statoServizioIP;
	}
	public void setStatoServizioIP(StatoServizio statoServizioIP) {
		this.statoServizioIP = statoServizioIP;
	}
	public String getMessaggioAPE() {
		return messaggioAPE;
	}
	public void setMessaggioAPE(String messaggioAPE) {
		this.messaggioAPE = messaggioAPE;
	}
	public StatoServizio getStatoServizioAPE() {
		return statoServizioAPE;
	}
	public void setStatoServizioAPE(StatoServizio statoServizioAPE) {
		this.statoServizioAPE = statoServizioAPE;
	}
	
}
