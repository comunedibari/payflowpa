package it.tasgroup.idp.rs.model.creditore;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Rappresenta un elemento che decompone l'importo totale di una condizione di pagamento
 * Ad esempio e' possibile decomporre il totale di una condizione di pagamento nei suoi elementi costitutivi:
 *   tributo base + sanzioni + interessi,  associando ad ognuno specifici codici, capitoli bilancio o codice atti accertamento.
 */
@XmlRootElement
public class Voce {
	private String tipo;
	private String codice;
	private String descrizione;
	private BigDecimal importo;
	private String capitoloBilancio;
	private String codiceAccertamento;

	public Voce() {
	}

	public Voce(String tipo, String codice, String descrizione, BigDecimal importo, String capitoloBilancio, String codiceAccertamento) {
		this.tipo = tipo;
		this.codice = codice;
		this.descrizione = descrizione;
		this.importo = importo;
		this.capitoloBilancio = capitoloBilancio;
		this.codiceAccertamento = codiceAccertamento;
	}

	/**
	 * Tipo della voce
	 */
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * Codice voce (e.g. SANZIONE)
	 */
	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 * Descrizione della voce
	 * @return
	 */
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	/**
	 * Importo della voce
	 * @return
	 */
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	/**
	 * Capitolo bilancio associato alla voce
	 * @return
	 */
	public String getCapitoloBilancio() {
		return capitoloBilancio;
	}
	public void setCapitoloBilancio(String capitoloBilancio) {
		this.capitoloBilancio = capitoloBilancio;
	}

	/**
	 * Codice accertamento associato alla voce
	 * @return
	 */
	public String getCodiceAccertamento() {
		return codiceAccertamento;
	}
	public void setCodiceAccertamento(String codiceAccertamento) {
		this.codiceAccertamento = codiceAccertamento;
	}

}
