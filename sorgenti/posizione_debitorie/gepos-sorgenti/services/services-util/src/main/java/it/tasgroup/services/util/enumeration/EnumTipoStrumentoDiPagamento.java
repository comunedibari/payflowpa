package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoStrumentoDiPagamento implements MessageDescription{
	
	CREDITCARD("1","CARTA DI CREDITO",""),
	BONIFICO("2","BONIFICO BANCARIO",""),
	RID("3","RID ON LINE",""),
	BOLLETTINOFRECCIA("4","BOLLETTINO FRECCIA",""),
	DDP("5","DOCUMENTO DI PAGAMENTO",""),
	MULTA("MULTA","VERBALE MULTA",""),
	WEB("WEB","HOME BANKING","");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoStrumentoDiPagamento(String chiave, String descrizione, String chiaveBundle) {
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
	

}
