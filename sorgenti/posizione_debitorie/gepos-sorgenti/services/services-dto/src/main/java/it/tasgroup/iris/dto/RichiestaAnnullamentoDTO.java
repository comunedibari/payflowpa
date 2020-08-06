package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.util.List;

public class RichiestaAnnullamentoDTO implements Serializable{
	private TestataMessaggioDTO testata;
	private List<String> condizioniDiPagamento;
	private MultaDTO multa;
	private String codiceTransazione;
	private String codAutorizzazione;
	private String canale;
	
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
	public String getCodAutorizzazione() {
		return codAutorizzazione;
	}
	public void setCodAutorizzazione(String codAutorizzazione) {
		this.codAutorizzazione = codAutorizzazione;
	}
	public List<String> getCondizioniDiPagamento() {
		return condizioniDiPagamento;
	}
	public void setCondizioniDiPagamento(List<String> condizioniDiPagamento) {
		this.condizioniDiPagamento = condizioniDiPagamento;
	}
	public MultaDTO getMulta() {
		return multa;
	}
	public void setMulta(MultaDTO multa) {
		this.multa = multa;
	}
}
