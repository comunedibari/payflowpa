package it.tasgroup.services.util.enumeration;


public enum EnumOperazioniPagamento {
	
	AUTORIZZAZIONE("AUP","Autorizzazione",""),
	ANNULLAMENTO("ANP","Annullamento",""),
	ESTRATTOCONTO("ECD","Estratto Conto Debitorio",""),
	NOTIFICA_SBF("NOS","Notifica sbf",""),
	NOTIFICA("NOP","Notifica",""),
	STORNO("STO","Storno",""), 
	VERIFICA("VER","Verifica Stato","");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;
	
	EnumOperazioniPagamento(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getChiaveBundle() {
		return chiaveBundle;
	}

	public void setChiaveBundle(String chiaveBundle) {
		this.chiaveBundle = chiaveBundle;
	}
	
	public static EnumOperazioniPagamento getOperationByStatusPagamento(String stato){

		if ("ES".equals(stato)){
			return EnumOperazioniPagamento.NOTIFICA;
		}
		
		if ("EF".equals(stato)){
			return EnumOperazioniPagamento.NOTIFICA_SBF;
		}
		
		if ("IC".equals(stato)){
			return EnumOperazioniPagamento.AUTORIZZAZIONE;
		}
		
		if ("IE".equals(stato)){
			return EnumOperazioniPagamento.AUTORIZZAZIONE;
		}
		
		if ("AN".equals(stato)){
			return EnumOperazioniPagamento.ANNULLAMENTO;
		}
		
		if ("NE".equals(stato)){
			return EnumOperazioniPagamento.AUTORIZZAZIONE;
		}
		
		if ("IA".equals(stato)){
			return EnumOperazioniPagamento.AUTORIZZAZIONE;
		}
		
		if ("AO".equals(stato)){
			return EnumOperazioniPagamento.ANNULLAMENTO;
		}
		
		if ("I".equals(stato)){
			return EnumOperazioniPagamento.AUTORIZZAZIONE;
		}

		throw new IllegalStateException("Pagamento status not found!");
	}
	
	public static EnumOperazioniPagamento getOperationByStatusDescription(String stato){

		if ("ESEGUITO".equals(stato))
			return EnumOperazioniPagamento.NOTIFICA;
		
		
		if ("ESEGUITO SBF".equals(stato) || "ESEGUITO_SBF".equals(stato))
			return EnumOperazioniPagamento.NOTIFICA_SBF;

		throw new IllegalStateException("Pagamento status not found!");
	}
	
}
