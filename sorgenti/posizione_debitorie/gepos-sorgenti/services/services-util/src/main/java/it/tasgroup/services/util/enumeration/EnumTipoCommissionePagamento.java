package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoCommissionePagamento implements MessageDescription {
	IMPORTO		("1", "Importo", ""),
	PERCENTUALE	("2", "Percentuale", ""),
	ESTERNA		("3", "Esterna","");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoCommissionePagamento(String chiave, String descrizione, String chiaveBundle) {
		this.chiave = chiave;
		this.descrizione = descrizione;
		this.chiaveBundle = chiaveBundle;
	}
	
	@Override
	public String getChiave() {
		return chiave;
	}

	@Override
	public String getDescrizione() {
		return descrizione;
	}

	@Override
	public String getChiaveBundle() {
		return chiaveBundle;
	}


}
