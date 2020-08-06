package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * Bean che mappa la form con i filtri di ricerca degli accertamenti.
 * 
 * @author Marianna Memoli
 */
public class AccertamentiVisualizzaCommand{

	/**
	 * Codice tipo dovuto  		(equals)
	 */
	private String codTipoDovuto;
	/**
	 * Codice dello stato 		(equals)
	 */
	private String codStato;
	/**
	 * Testo digitato contenuto nella descrizione dell'accertamento 	(like, ignore case, match anywhere)
	 */
	private String nomeAccertamento;
	/**
	 * Individua se è attivo o meno il filtro sulla data ultimo aggiornamento. 
	 */
	private Boolean dataUltimoAggCheck;
	/**
	 * Data ultimo aggiornamento dal  (formato dd/MM/yyyy)		(>=)
	 */
	private String dataUltimoAggDa;
	/**
	 * Data ultimo aggiornamento al  (formato dd/MM/yyyy)		(<)
	 */
	private String dataUltimoAggA;
	/**
	 * Identificativo univoco versamento		(equals)
	 */
	private String codiceIuv;
	/**
	 * Numero pagina
	 */
	private int page;
	/**
	 * Elementi per pagina
	 */
	private int pageSize;
	
	
	 
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
	 * @return the codStato
	 */
	public String getCodStato() {
		return codStato;
	}
	/**
	 * @param codStato the codStato to set
	 */
	public void setCodStato(String codStato) {
		this.codStato = codStato;
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
	/**
	 * @return the dataUltimoAggDa
	 */
	public String getDataUltimoAggDa() {
		return dataUltimoAggDa;
	}
	/**
	 * @param dataUltimoAggDa the dataUltimoAggDa to set
	 */
	public void setDataUltimoAggDa(String dataUltimoAggDa) {
		this.dataUltimoAggDa = dataUltimoAggDa;
	}
	/**
	 * @return the dataUltimoAggA
	 */
	public String getDataUltimoAggA() {
		return dataUltimoAggA;
	}
	/**
	 * @param dataUltimoAggA the dataUltimoAggA to set
	 */
	public void setDataUltimoAggA(String dataUltimoAggA) {
		this.dataUltimoAggA = dataUltimoAggA;
	}
	/**
	 * @return the codiceIuv
	 */
	public String getCodiceIuv() {
		return codiceIuv;
	}
	/**
	 * @param codiceIud the codiceIuv to set
	 */
	public void setCodiceIuv(String codiceIuv) {
		this.codiceIuv = codiceIuv;
	}
	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	/**
	 * @return the dataUltimoAggCheck
	 */
	public Boolean getDataUltimoAggCheck() {
		return dataUltimoAggCheck;
	}
	/**
	 * @param dataUltimoAggCheck the dataUltimoAggCheck to set
	 */
	public void setDataUltimoAggCheck(Boolean dataUltimoAggCheck) {
		this.dataUltimoAggCheck = dataUltimoAggCheck;
	}
}
