/**
 * 
 */
package it.tasgroup.iris.dto.anonymous.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author pazzik
 *
 */
@SuppressWarnings("serial")
public class AnonymousPaymentConditionDTO implements Serializable {
	private String idCondizione; 
	
	private String idPagamento;
	private String iuv;
	private String ente;
	private String tributo;
	
	private Integer annoRiferimento;
	private String stato;
	private boolean incassato;
	private String idPendenza;
	
	//TODO: sostituire con una tabella 
	private String causalePendenza;
	private String causalePendenzaDecorata;  //A cura del front end in post processing
	
	
	private BigDecimal totalePendenza;
	private BigDecimal importo;
	private Date dataScadenza;
	
	//TODO: da definire
	private String causaleCondizione;
	//
	
	private String notePendenza;
	
	
	private String idEnte;
	private String idTributoEnte;
	private String cdPlugin;

	private Date   dataPagamento;
	
	private boolean flRicevutaAnonimo;
	
	private List<String> cfDebitori;
	
	
	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String tributo) {
		this.tributo = tributo;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}

	public Integer getAnnoRiferimento() {
		return annoRiferimento;
	}

	public void setAnnoRiferimento(Integer annoRiferimento) {
		this.annoRiferimento = annoRiferimento;
	}

	public String getCausalePendenza() {
		return causalePendenza;
	}

	public void setCausalePendenza(String causalePendenza) {
		this.causalePendenza = causalePendenza;
	}

	public BigDecimal getTotalePendenza() {
		return totalePendenza;
	}

	public void setTotalePendenza(BigDecimal totalePendenza) {
		this.totalePendenza = totalePendenza;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getCausaleCondizione() {
		return causaleCondizione;
	}

	public void setCausaleCondizione(String causaleCondizione) {
		this.causaleCondizione = causaleCondizione;
	}

	public String getNotePendenza() {
		return notePendenza;
	}

	public void setNotePendenza(String notePendenza) {
		this.notePendenza = notePendenza;
	}


	/**
	 * @return the id
	 */
	public String getIdPagamento() {
		return idPagamento;
	}

	/**
	 * @param id the id to set
	 */
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getIuv() {
		return iuv;
	}

	public void setIuv(String iuv) {
		this.iuv = iuv;
	}

	/**
	 * @return the stato
	 */
	public String getStato() {
		return stato;
	}

	/**
	 * @param stato the stato to set
	 */
	public void setStato(String stato) {
		this.stato = stato;
	}

	/**
	 * @return the incassato
	 */
	public boolean isIncassato() {
		return incassato;
	}

	/**
	 * @param incassato the incassato to set
	 */
	public void setIncassato(boolean incassato) {
		this.incassato = incassato;
	}

	/**
	 * @return the idPendenza
	 */
	public String getIdPendenza() {
		return idPendenza;
	}

	/**
	 * @param idPendenza the idPendenza to set
	 */
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	public String getCausalePendenzaDecorata() {
		return causalePendenzaDecorata;
	}

	public void setCausalePendenzaDecorata(String casualePendenzaDecorata) {
		this.causalePendenzaDecorata = casualePendenzaDecorata;
	}

	public String getIdTributoEnte() {
		return idTributoEnte;
	}

	public void setIdTributoEnte(String idTributoEnte) {
		this.idTributoEnte = idTributoEnte;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	
	public String getCausaleFormattata() {
		
		StringBuilder sb = new StringBuilder();
		
		if (isFlRicevutaAnonimo()) {
			
			sb.append(": ");
			
			sb.append(this.causalePendenzaDecorata);
			
			if (this.causaleCondizione != null && this.causaleCondizione.trim().length() > 0) {
				
				sb.append(" - ");
				
				sb.append(this.causaleCondizione);
				
			}
		}
	
		return sb.toString();
	}

	public boolean isFlRicevutaAnonimo() {
		
		return flRicevutaAnonimo;
		
	}

	public void setFlRicevutaAnonimo(boolean flRicevutaAnonimo) {
		
		this.flRicevutaAnonimo = flRicevutaAnonimo;
		
	}

	public List<String> getCfDebitori() {
		return cfDebitori;
	}

	public void setCfDebitori(List<String> cfDebitori) {
		this.cfDebitori = cfDebitori;
	}

}
