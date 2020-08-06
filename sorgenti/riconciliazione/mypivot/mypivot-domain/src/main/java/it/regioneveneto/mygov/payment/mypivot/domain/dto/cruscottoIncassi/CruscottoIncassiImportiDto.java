package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

/**
 * DTO per la presentazione lato Web degli importi statistici.
 * 
 * @author Marianna Memoli
 */
public class CruscottoIncassiImportiDto {
	
	/**
	 * Importo pagato (RT) 		(Formattato come "€ <importo>")
	 */
	private String importoPagato;
	
	/**
	 * Importo rendicontato 	(Formattato come "€ <importo>")
	 */
	private String importoRendicontato;
	
	/**
	 * Importo incassato 		(Formattato come "€ <importo>")
	 */
	private String importoIncassato;
	
	/**
	 * Individua se abilitare o meno l'opzione per cui è possibile richiedere il dettaglio dei singoli pagamenti.
	 */
	private boolean enableDttImportoPagato;
	
	/**
	 * Individua se abilitare o meno l'opzione per cui è possibile richiedere il dettaglio dei singoli pagamenti.
	 */
	private boolean enableDttImportoRend;
	
	/**
	 * Individua se abilitare o meno l'opzione per cui è possibile richiedere il dettaglio dei singoli pagamenti.
	 */
	private boolean enableDttImportoInc;
	
	
	
	/**
	 * @return the importoPagato
	 */
	public String getImportoPagato() {
		return importoPagato;
	}
	/**
	 * @param importoPagato the importoPagato to set
	 */
	public void setImportoPagato(String importoPagato) {
		this.importoPagato = importoPagato;
	}
	/**
	 * @return the importoRendicontato
	 */
	public String getImportoRendicontato() {
		return importoRendicontato;
	}
	/**
	 * @param importoRendicontato the importoRendicontato to set
	 */
	public void setImportoRendicontato(String importoRendicontato) {
		this.importoRendicontato = importoRendicontato;
	}
	/**
	 * @return the importoIncassato
	 */
	public String getImportoIncassato() {
		return importoIncassato;
	}
	/**
	 * @param importoIncassato the importoIncassato to set
	 */
	public void setImportoIncassato(String importoIncassato) {
		this.importoIncassato = importoIncassato;
	}
	/**
	 * @return the enableDttImportoPagato
	 */
	public boolean isEnableDttImportoPagato() {
		return enableDttImportoPagato;
	}
	/**
	 * @param enableDttImportoPagato the enableDttImportoPagato to set
	 */
	public void setEnableDttImportoPagato(boolean enableDttImportoPagato) {
		this.enableDttImportoPagato = enableDttImportoPagato;
	}
	/**
	 * @return the enableDttImportoRend
	 */
	public boolean isEnableDttImportoRend() {
		return enableDttImportoRend;
	}
	/**
	 * @param enableDttImportoRend the enableDttImportoRend to set
	 */
	public void setEnableDttImportoRend(boolean enableDttImportoRend) {
		this.enableDttImportoRend = enableDttImportoRend;
	}
	/**
	 * @return the enableDttImportoInc
	 */
	public boolean isEnableDttImportoInc() {
		return enableDttImportoInc;
	}
	/**
	 * @param enableDttImportoInc the enableDttImportoInc to set
	 */
	public void setEnableDttImportoInc(boolean enableDttImportoInc) {
		this.enableDttImportoInc = enableDttImportoInc;
	}
}