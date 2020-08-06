package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

public enum EnumTipoAnomaliaIncasso implements MessageDescription{
	
	PAG_DUPLICATO("PAG_DUPLICATO", "Pagamento Duplicato", ""),
	SOSPETTO_GR("SOSPETTO_GR", "Sospetto Gia' Riversato", ""),
	DOC_DUPLICATO("DOC_DUPLICATO", "Documento Duplicato", ""),
	IMP_MINORE("IMP_MINORE", "Importo Minore", ""),
	IMP_MAGGIORE("IMP_MAGGIORE", "Importo Maggiore", ""),
	DOC_NON_PRESENTE("DOC_NON_PRESENTE", "Documento Non Presente", "");
	
	
	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoAnomaliaIncasso(String chiave, String descrizione, String chiaveBundle) {
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
