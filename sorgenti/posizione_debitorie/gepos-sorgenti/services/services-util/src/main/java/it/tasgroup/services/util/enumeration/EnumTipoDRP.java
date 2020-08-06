package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoDRP implements MessageDescription{

	RID 				("R", "Ricevute", "iris.drp.tipo.ricevuta.rid", "RICEVUTA_PAGAMENTO_RID","QUIETANZA_PAGAMENTO_BONIFICO", 3L),
	CARTA_DI_CREDITO 	("C", "Ricevute", "iris.drp.tipo.ricevuta.cartaDiCredito","RICEVUTA_PAGAMENTO_CREDIT_CARD","QUIETANZA_PAGAMENTO_CREDIT_CARD", 4L),
	BONIFICO 			("B", "Ricevute", "iris.drp.tipo.ricevuta.bonifico", "RICEVUTA_PAGAMENTO_BONIFICO","QUIETANZA_PAGAMENTO_BONIFICO", 5L),
	ATM 				("A", "Ricevute", "iris.drp.tipo.ricevuta.atm","RICEVUTA_PAGAMENTO_ATM","QUIETANZA_PAGAMENTO_ATM", 6L),
	BONIFICO_OFFLINE 	("BO", "Ricevute", "iris.drp.tipo.ricevuta.bonificoOffLine","","QUIETANZA_PAGAMENTO_BONIFICO_CODICE_PREDEF", 8L),
	EU_PAY 				("EP", "Ricevute", "iris.drp.tipo.ricevuta.euPay","RICEVUTA_PAGAMENTO_EU_PAY","QUIETANZA_PAGAMENTO_EU_PAY", 10L),
	T_SERVE 			("TS", "Ricevute", "iris.drp.tipo.ricevuta.tServe","RICEVUTA_PAGAMENTO_T_SERVE","QUIETANZA_PAGAMENTO_T_SERVE", 11L),
	CBILL 				("CB", "Ricevute", "iris.drp.tipo.ricevuta.cBill","RICEVUTA_PAGAMENTO_CBILL","QUIETANZA_PAGAMENTO_CBILL", 12L),
	BBT 				("BT", "Ricevute", "iris.drp.tipo.ricevuta.bbt","RICEVUTA_PAGAMENTO_BT","QUIETANZA_PAGAMENTO_BT", 14L),
	BPT 				("BPT", "Ricevute", "iris.drp.tipo.ricevuta.bpt","RICEVUTA_PAGAMENTO_BPT","QUIETANZA_PAGAMENTO_BPT", 15L),
	PSP 				("PSP", "Ricevute", "iris.drp.tipo.ricevuta.psp","RICEVUTA_PAGAMENTO_PSP","QUIETANZA_PAGAMENTO_PSP", 16L),
	PUNTO_SI 			("PS", "Ricevute", "iris.drp.tipo.ricevuta.puntoSi","RICEVUTA_PAGAMENTO_PUNTO_SI","QUIETANZA_PAGAMENTO_PUNTO_SI", 17L),
	FESP				("BP", "Ricevute", "iris.drp.tipo.ricevuta.fesp","RICEVUTA_PAGAMENTO_FESP","QUIETANZA_PAGAMENTO_FESP", 18L),
	PSTSPA				("SA", "Ricevute", "iris.drp.tipo.ricevuta.pstspa","RICEVUTA_PAGAMENTO_PSTSPA","QUIETANZA_PAGAMENTO_PSTSPA", 19L),
	OBEP				("OBEP", "Ricevute", "iris.drp.tipo.ricevuta.obep","RICEVUTA_PAGAMENTO_OBEP","QUIETANZA_PAGAMENTO_OBEP", 23L),	
	PAGO_PA             ("PPA", "Ricevute", "iris.drp.tipo.ricevuta.pagopa","RICEVUTA_PAGAMENTO_PAGOPA","QUIETANZA_PAGAMENTO_PAGOPA", 20L);
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
//	private String docTitle;
//	private String q_docTitle;
	private Long idCfgModalitaPagamento;

	private EnumTipoDRP(String chiave, String descrizione, String chiaveBundle, String docTitle, String q_docTitle, Long idCfgModalitaPagamento) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
//		this.docTitle = docTitle;
//		this.q_docTitle = q_docTitle;
		this.idCfgModalitaPagamento = idCfgModalitaPagamento;
	}

	public String getChiave() {
		return chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}
	
	public Long getIdCfgModalitaPagamento() {
		return idCfgModalitaPagamento;
	}

	public void setIdCfgModalitaPagamento(Long idCfgModalitaPagamento) {
		this.idCfgModalitaPagamento = idCfgModalitaPagamento;
	}
	
	public static EnumTipoDRP getByIdCfgModalitaPagamento(Long idCfgModalitaPagamento) {
		
		EnumTipoDRP desiredItem = null; // Default             
		
		for (EnumTipoDRP item : EnumTipoDRP.values()) {                 
			if (item.getIdCfgModalitaPagamento().equals(idCfgModalitaPagamento)) {                     
				desiredItem = item;                     
				break;                 
				}             
			}   
		
		return desiredItem; 
	}

//	/**
//	 * @return the docTitle
//	 */
//	public String getDocTitle() {
//		return docTitle;
//	}
//
//	/**
//	 * @param docTitle the docTitle to set
//	 */
//	public void setDocTitle(String docTitle) {
//		this.docTitle = docTitle;
//	}
//
//	
//	/**
//	 * @return the docTitle
//	 */
//	public String getQ_docTitle() {
//		return q_docTitle;
//	}
//
//	/**
//	 * @param docTitle the docTitle to set
//	 */
//	public void setQ_docTitle(String q_docTitle) {
//		this.q_docTitle = q_docTitle;
//	}

}
