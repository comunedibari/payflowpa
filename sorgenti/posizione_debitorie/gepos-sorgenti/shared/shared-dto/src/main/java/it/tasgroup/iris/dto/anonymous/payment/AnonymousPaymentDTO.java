/**
 * 
 */
package it.tasgroup.iris.dto.anonymous.payment;

import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author pazzik
 *
 */
public class AnonymousPaymentDTO implements Serializable {
	
	private Long idPagamento;
	private Long idDistinta;
	private String codPagamento;
	private String codTranzazione;
	private Timestamp dataesecuzione;
	private String modalitapagamento;
	private String modalitaPagamentoBundleKey;
	private String stato;
	private BigDecimal importoNetto;
	private BigDecimal importoTotale;
	private BigDecimal importocommissioni;
	private String codPagante;
	private boolean associatedDocAvailable=true;
	private boolean importoCommissioniNotNull=false;
	
	
	public Long getIdDistinta() {
		return idDistinta;
	}

	public void setIdDistinta(Long idDistinta) {
		this.idDistinta = idDistinta;
	}

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	public String getCodTranzazione() {
		return codTranzazione;
	}

	public void setCodTranzazione(String codTranzazione) {
		this.codTranzazione = codTranzazione;
	}

	public Timestamp getDataesecuzione() {
		return dataesecuzione;
	}

	public void setDataesecuzione(Timestamp dataesecuzione) {
		this.dataesecuzione = dataesecuzione;
	}

	public String getModalitapagamento() {
		return modalitapagamento;
	}

	public void setModalitapagamento(String modalitapagamento) {
		this.modalitapagamento = modalitapagamento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public BigDecimal getImportoNetto() {
		return importoNetto;
	}

	public void setImportoNetto(BigDecimal importoNetto) {
		this.importoNetto = importoNetto;
	}

	public BigDecimal getImportoTotale() {
		return importoTotale;
	}

	public void setImportoTotale(BigDecimal importoTotale) {
		this.importoTotale = importoTotale;
	}

	public BigDecimal getImportocommissioni() {
		return importocommissioni;
	}

	public void setImportocommissioni(BigDecimal importocommissioni) {
		this.importocommissioni = importocommissioni;
	}

	/**
	 * @return the codPagante
	 */
	public String getCodPagante() {
		return codPagante;
	}

	/**
	 * @param codPagante the codPagante to set
	 */
	public void setCodPagante(String codPagante) {
		this.codPagante = codPagante;
	}

	public String getModalitaPagamentoBundleKey() {
		return modalitaPagamentoBundleKey;
	}

	public void setModalitaPagamentoBundleKey(String modalitaPagamentoBundleKey) {
		this.modalitaPagamentoBundleKey = modalitaPagamentoBundleKey;
	}
    
	public boolean isPagamentoAnnullabile() {
		ConfigurationPropertyLoader props = ConfigurationPropertyLoader.getInstance("iris-fe.properties");
		
		if ("NDP".equals(modalitaPagamentoBundleKey) && 
			"IN CORSO".equals(stato) && 
			props.getBooleanProperty("posizionidebitorie.annulla.operaz.ndp"))
		   return true;
		else 
		   return false;
	}

	public boolean isAssociatedDocAvailable() {
		return associatedDocAvailable;
	}

	public void setAssociatedDocAvailable(boolean associatedDocAvailable) {
		this.associatedDocAvailable = associatedDocAvailable;
	}

	public boolean isImportoCommissioniNotNull() {
		if (importocommissioni == null)
			return false;
		else
			return importocommissioni.compareTo(BigDecimal.valueOf(0)) != 0;
	}

	public void setImportoCommissioniNotNull(boolean importoCommissioniNotNull) {
		this.importoCommissioniNotNull = importoCommissioniNotNull;
	}

	public Long getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}

}
