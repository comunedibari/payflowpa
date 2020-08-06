package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class StornoDTO implements Serializable{
	
	private TestataMessaggioDTO testata;
	private String codiceTransazione;
	private Timestamp tsStorno;
	private String statoNotifica;
	private String canale;
	private String causaleStorno;
	
	public TestataMessaggioDTO getTestata() {
		return testata;
	}
	
	public void setTestata(TestataMessaggioDTO testata) {
		this.testata = testata;
	}
	
	public String getCodiceTransazione() {
		return codiceTransazione;
	}
	
	public void setCodiceTransazione(String codiceTransazione) {
		this.codiceTransazione = codiceTransazione;
	}
	
	public String getCanale() {
		return canale;
	}
	
	public void setCanale(String canale) {
		this.canale = canale;
	}
	
	public String getStatoNotifica() {
		return statoNotifica;
	}
	
	public void setStatoNotifica(String statoNotifica) {
		this.statoNotifica = statoNotifica;
	}
	
	public String getCausaleStorno() {
		return causaleStorno;
	}
	
	public void setCausaleStorno(String causaleStorno) {
		this.causaleStorno = causaleStorno;
	}

	public Timestamp getTsStorno() {
		return tsStorno;
	}

	public void setTsStorno(Timestamp tsStorno) {
		this.tsStorno = tsStorno;
	}

}
