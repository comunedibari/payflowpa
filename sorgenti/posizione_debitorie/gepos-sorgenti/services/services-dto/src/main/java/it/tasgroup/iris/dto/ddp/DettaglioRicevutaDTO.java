package it.tasgroup.iris.dto.ddp;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DettaglioRicevutaDTO extends DettaglioDRPDTO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9047620569217069942L;
	private Timestamp dataPagamento;
	private String codiceTransazione;
	private String codiceAutorizzazione;
	private String causaleRPT;
	
	public Timestamp getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getCodiceTransazione() {
		return codiceTransazione;
	}
	public void setCodiceTransazione(String codiceTransazione) {
		this.codiceTransazione = codiceTransazione;
	}
	public String getCodiceAutorizzazione() {
		return codiceAutorizzazione;
	}
	public void setCodiceAutorizzazione(String codiceAutorizzazione) {
		this.codiceAutorizzazione = codiceAutorizzazione;
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		sb.append("\n"+this.getClass()+"\n");
		sb.append("statoTransazione="+this.getStato());
		sb.append(", tipoRicevuta=" + this.getTipo());
		sb.append(", codiceTransazione=" + this.getCodiceTransazione());
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		sb.append(", dataPagamento=");
		
		if(dataPagamento != null)
		    sb.append(sdf.format(dataPagamento));
		else
		    sb.append("nessuna");
		
		sb.append(", codiceAutorizzazione=" + this.getCodiceAutorizzazione());
	    sb.append("]\n");
	    return sb.toString();
	}
	public String getCausaleRPT() {
		return causaleRPT;
	}
	public void setCausaleRPT(String causaleRPT) {
		this.causaleRPT = causaleRPT;
	}
	
}
