/**
 * 
 */
package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;


public class AnagraficaCorsiDottoratoDTO  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5908046084508206669L;
	
	private String codCorso;
	private String codTassa;
	private Date dataScadenza;
	private BigDecimal importo;
	private String descrizione;
	
	private static final DecimalFormat importFormatter = new DecimalFormat("#0.00");
	
	
	public String getCodCorso() {
		return codCorso;
	}
	public String getCodTassa() {
		return codTassa;
	}
	public Date getDataScadenza() {
		return dataScadenza;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setCodCorso(String codCorso) {
		this.codCorso = codCorso;
	}
	public void setCodTassa(String codTassa) {
		this.codTassa = codTassa;
	}
	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getDescrizioneEstesa() {
		return "Euro " + importFormatter.format(this.importo) + " - " + descrizione;
	}
	
	public String getId() {
		return this.codCorso + "--" + this.codTassa;
	}
	

}
