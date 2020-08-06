package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

/**
 * 
 * Mappa sul campo TI_DEBITO della tabella PAGAMENTI
 * 
 * @author pazzik
 *
 */
public enum EnumTipoDebito implements MessageDescription {
	PENDENZA 			("P", "Pendenza", "iris.pagamenti.tipo.debito.pendenza"),
	SPONTANEO 			("S", "Spontaneo", "iris.pagamenti.tipo.debito.spontaneo");

	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoDebito(String chiave, String descrizione, String chiaveBundle) {
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
