package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * Bean che mappa la form per la creazione di un'accertamento (@Accertamento).
 * 
 * @author Marianna Memoli
 */
public class AccertamentoNuovoCommand {
	
	/**
	 * Codice della tipologia di dovuto 
	 */
	private String codTipoDovuto;
	/**
	 * Descrizione accertamento
	 */
	private String nomeAccertamento;
	
	
	/**
	 * @return the codTipoDovuto
	 */
	public String getCodTipoDovuto() {
		return codTipoDovuto;
	}
	/**
	 * @param codTipoDovuto the codTipoDovuto to set
	 */
	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}
	/**
	 * @return the nomeAccertamento
	 */
	public String getNomeAccertamento() {
		return nomeAccertamento;
	}
	/**
	 * @param nomeAccertamento the nomeAccertamento to set
	 */
	public void setNomeAccertamento(String nomeAccertamento) {
		this.nomeAccertamento = nomeAccertamento;
	}
}
