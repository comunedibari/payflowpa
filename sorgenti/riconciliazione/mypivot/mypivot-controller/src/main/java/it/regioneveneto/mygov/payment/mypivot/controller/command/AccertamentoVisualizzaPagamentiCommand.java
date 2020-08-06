package it.regioneveneto.mygov.payment.mypivot.controller.command;

import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoDettaglioDto;

/**
 * Bean che mappa la form per di ricerca ed edit dei pagamenti.
 * 
 * @author Marianna Memoli
 */
public class AccertamentoVisualizzaPagamentiCommand {
	
	/**
	 * Identificativo dell'accertamento 		(equals)
	 */
	private String accertamentoId;
	
	/**
	 * Codice tipo dovuto  			(equals)
	 */
	private String cod_tipo_dovuto;
	
	/**
	 * CF/PIVA Pagatore 			(equals)
	 */
	private String codice_identificativo_univoco_pagatore;
	
	/**
	 *  Identificativo univoco del dovuto 		(equals)
	 */
	private String codice_iud;
	
	/**
	 * Identificativo univoco del versamento 	(equals)
	 */
	private String codice_iuv;
	
	
	/**
	 * Individua se è attivo o meno il filtro sulla data ultimo aggiornamento. 
	 */
	private Boolean data_ultimo_aggiornamento_check;
	/**
	 * Data ultimo aggiornamento dal  (formato DD/MM/YYYY)		(>=)
	 */
	private String data_ultimo_aggiornamento_da;
	/**
	 * Data ultimo aggiornamento al  (formato DD/MM/YYYY)		(<)
	 */
	private String data_ultimo_aggiornamento_a;
	
	
	/**
	 * Individua se è attivo o meno il filtro sulla data esito pagamento
	 */
	private Boolean data_esito_singolo_pagamento_check;
	/**
	 * Data esito pagamento dal  (formato DD/MM/YYYY)		(>=)
	 */
	private String data_esito_singolo_pagamento_da;
	/**
	 * Data esito pagamento al  (formato DD/MM/YYYY)		(<)
	 */
	private String data_esito_singolo_pagamento_a;
	
	
	/**
	 * Numero pagina
	 */
	private int page;
	/**
	 * Elementi per pagina
	 */
	private int pageSize;
	
	/**
	 * Indica l'operazione che lato server devo applicare a seconda di quanto è stato selezionato nella View.
	 */
	private OPERATION operation;
	
/* ==================================================== */
	
	/** 
	 * Valori possibili: 
	 *  1. "DETAIL" : Mostra l'ufficio, capitolo ed accertamento contabili della RT selezionata
	 *  2. "SAVE" :   Salva le modifiche prima di avviare una nuova ricerca (per la view di edit).
	 *  3. "RC" :	  Ricerca pagamenti.
	 */
	public static enum OPERATION {DETAIL, SAVE, RC}
	
/* ============ PROPERTY RISULTATO RICERCA =============== */
	
	/**
	 * Lista dei pagamenti ottenuta dalla ricerca
	 */
	private List<AccertamentoDettaglioDto> resultList;
	
	/**
	 * 
	 */
	private boolean previousPage;
	/**
	 * 
	 */
	private boolean nextPage;
	/**
	 * Numero totale pagini
	 */
	private int totalPages;
	/**
	 * Numero record estratti
	 */
	private long totalRecords;
	
/* ==================================================== */
	
	/**
	 * @return the accertamentoId
	 */
	public String getAccertamentoId() {
		return accertamentoId;
	}
	/**
	 * @param accertamentoId the accertamentoId to set
	 */
	public void setAccertamentoId(String accertamentoId) {
		this.accertamentoId = accertamentoId;
	}
	/**
	 * @return the cod_tipo_dovuto
	 */
	public String getCod_tipo_dovuto() {
		return cod_tipo_dovuto;
	}
	/**
	 * @param cod_tipo_dovuto the cod_tipo_dovuto to set
	 */
	public void setCod_tipo_dovuto(String cod_tipo_dovuto) {
		this.cod_tipo_dovuto = cod_tipo_dovuto;
	}
	/**
	 * @return the codice_identificativo_univoco_pagatore
	 */
	public String getCodice_identificativo_univoco_pagatore() {
		return codice_identificativo_univoco_pagatore;
	}
	/**
	 * @param codice_identificativo_univoco_pagatore the codice_identificativo_univoco_pagatore to set
	 */
	public void setCodice_identificativo_univoco_pagatore(String codice_identificativo_univoco_pagatore) {
		this.codice_identificativo_univoco_pagatore = codice_identificativo_univoco_pagatore;
	}
	/**
	 * @return the codice_iud
	 */
	public String getCodice_iud() {
		return codice_iud;
	}
	/**
	 * @param codice_iud the codice_iud to set
	 */
	public void setCodice_iud(String codice_iud) {
		this.codice_iud = codice_iud;
	}
	/**
	 * @return the codice_iuv
	 */
	public String getCodice_iuv() {
		return codice_iuv;
	}
	/**
	 * @param codice_iuv the codice_iuv to set
	 */
	public void setCodice_iuv(String codice_iuv) {
		this.codice_iuv = codice_iuv;
	}
	/**
	 * @return the data_ultimo_aggiornamento_check
	 */
	public Boolean getData_ultimo_aggiornamento_check() {
		return data_ultimo_aggiornamento_check;
	}
	/**
	 * @param data_ultimo_aggiornamento_check the data_ultimo_aggiornamento_check to set
	 */
	public void setData_ultimo_aggiornamento_check(Boolean data_ultimo_aggiornamento_check) {
		this.data_ultimo_aggiornamento_check = data_ultimo_aggiornamento_check;
	}
	/**
	 * @return the data_ultimo_aggiornamento_da
	 */
	public String getData_ultimo_aggiornamento_da() {
		return data_ultimo_aggiornamento_da;
	}
	/**
	 * @param data_ultimo_aggiornamento_da the data_ultimo_aggiornamento_da to set
	 */
	public void setData_ultimo_aggiornamento_da(String data_ultimo_aggiornamento_da) {
		this.data_ultimo_aggiornamento_da = data_ultimo_aggiornamento_da;
	}
	/**
	 * @return the data_ultimo_aggiornamento_a
	 */
	public String getData_ultimo_aggiornamento_a() {
		return data_ultimo_aggiornamento_a;
	}
	/**
	 * @param data_ultimo_aggiornamento_a the data_ultimo_aggiornamento_a to set
	 */
	public void setData_ultimo_aggiornamento_a(String data_ultimo_aggiornamento_a) {
		this.data_ultimo_aggiornamento_a = data_ultimo_aggiornamento_a;
	}
	/**
	 * @return the data_esito_singolo_pagamento_check
	 */
	public Boolean getData_esito_singolo_pagamento_check() {
		return data_esito_singolo_pagamento_check;
	}
	/**
	 * @param data_esito_singolo_pagamento_check the data_esito_singolo_pagamento_check to set
	 */
	public void setData_esito_singolo_pagamento_check(Boolean data_esito_singolo_pagamento_check) {
		this.data_esito_singolo_pagamento_check = data_esito_singolo_pagamento_check;
	}
	/**
	 * @return the data_esito_singolo_pagamento_da
	 */
	public String getData_esito_singolo_pagamento_da() {
		return data_esito_singolo_pagamento_da;
	}
	/**
	 * @param data_esito_singolo_pagamento_da the data_esito_singolo_pagamento_da to set
	 */
	public void setData_esito_singolo_pagamento_da(String data_esito_singolo_pagamento_da) {
		this.data_esito_singolo_pagamento_da = data_esito_singolo_pagamento_da;
	}
	/**
	 * @return the data_esito_singolo_pagamento_a
	 */
	public String getData_esito_singolo_pagamento_a() {
		return data_esito_singolo_pagamento_a;
	}
	/**
	 * @param data_esito_singolo_pagamento_a the data_esito_singolo_pagamento_a to set
	 */
	public void setData_esito_singolo_pagamento_a(String data_esito_singolo_pagamento_a) {
		this.data_esito_singolo_pagamento_a = data_esito_singolo_pagamento_a;
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
	 * @return the operation
	 */
	public OPERATION getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(OPERATION operation) {
		this.operation = operation;
	}
	/**
	 * @return the resultList
	 */
	public List<AccertamentoDettaglioDto> getResultList() {
		return resultList;
	}
	/**
	 * @param resultList the resultList to set
	 */
	public void setResultList(List<AccertamentoDettaglioDto> resultList) {
		this.resultList = resultList;
	}
	/**
	 * @return the previousPage
	 */
	public boolean isPreviousPage() {
		return previousPage;
	}
	/**
	 * @param previousPage the previousPage to set
	 */
	public void setPreviousPage(boolean previousPage) {
		this.previousPage = previousPage;
	}
	/**
	 * @return the nextPage
	 */
	public boolean isNextPage() {
		return nextPage;
	}
	/**
	 * @param nextPage the nextPage to set
	 */
	public void setNextPage(boolean nextPage) {
		this.nextPage = nextPage;
	}
	/**
	 * @return the totalPages
	 */
	public int getTotalPages() {
		return totalPages;
	}
	/**
	 * @param totalPages the totalPages to set
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * @return the totalRecords
	 */
	public long getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}
}