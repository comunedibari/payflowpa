package it.tasgroup.idp.billerservices.jobs.status;

import it.tasgroup.idp.billerservices.api.EnumReturnValues;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AllineamentoPendenzeJobStatus {
	
	public static enum EnumEsito {
		OK,
		KO
	}
	
	public static enum EnumFaultSeverity {
		ERRORE,
		WARNING
	}
	
	public static class Fault {
		public String progressivo;
		public String idDebito;
		public String idPagamento;
		public EnumFaultSeverity faultSeverity;
		public EnumReturnValues faultCode;
		public String faultDescription;
	}
	
	@XmlElement
	private List<Fault> faults = new ArrayList<AllineamentoPendenzeJobStatus.Fault>();
	private EnumEsito esito;	
	private int totalePagamenti;
	private int totalePagamentiCaricati;
	
	public EnumEsito getEsito() {
		return esito;
	}
	public void setEsito(EnumEsito esito) {
		this.esito = esito;
	}
	public List<Fault> getFaults() {
		return faults;
	}
	public int getTotalePagamenti() {
		return totalePagamenti;
	}
	public void setTotalePagamenti(int totalePagamenti) {
		this.totalePagamenti = totalePagamenti;
	}
	public int getTotalePagamentiCaricati() {
		return totalePagamentiCaricati;
	}
	public void setTotalePagamentiCaricati(int totalePagamentiCaricati) {
		this.totalePagamentiCaricati = totalePagamentiCaricati;
	}

	
	
}
