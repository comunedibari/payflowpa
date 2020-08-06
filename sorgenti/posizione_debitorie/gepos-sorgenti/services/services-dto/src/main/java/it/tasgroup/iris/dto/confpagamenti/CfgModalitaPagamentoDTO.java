/**
 * 
 */
package it.tasgroup.iris.dto.confpagamenti;

import java.math.BigDecimal;

/**
 * @author PazziK
 *
 */
public class CfgModalitaPagamentoDTO {
	
	private String id;
	private String bundleKey;
	private String descrizione;
	private BigDecimal importoTotale;
	private BigDecimal importoCommissioni;
	private Boolean enabled;
	private int paymentMethod;
	
	public String getBundleKey() {
		return bundleKey;
	}
	public void setBundleKey(String bundleKey) {
		this.bundleKey = bundleKey;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public BigDecimal getImportoTotale() {
		return importoTotale;
	}
	public void setImportoTotale(BigDecimal importoTotale) {
		this.importoTotale = importoTotale;
	}
	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}
	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(int paymentMethod) {
		this.paymentMethod = paymentMethod;
	}	
	
	public static CfgModalitaPagamentoDTO getCfgModalitaPagamentoDTO(String id, String bundleKey, String descrizione) {
		CfgModalitaPagamentoDTO res = new CfgModalitaPagamentoDTO();
		res.setId(id);
		res.setBundleKey(bundleKey);
		res.setDescrizione(descrizione);
		return res;
	}
	

}
