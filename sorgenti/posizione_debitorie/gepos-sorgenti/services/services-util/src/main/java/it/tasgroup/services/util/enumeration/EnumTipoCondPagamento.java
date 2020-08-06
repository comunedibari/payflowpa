package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoCondPagamento implements MessageDescription {
	
	SINGOLO 	("S", "Singolo", "iris.condpag.tipo.singolo"),
	RATEIZZATO 	("R", "Rateizzato", "iris.condpag.tipo.rateizzato");
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoCondPagamento(String chiave, String descrizione, String chiaveBundle) {
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
