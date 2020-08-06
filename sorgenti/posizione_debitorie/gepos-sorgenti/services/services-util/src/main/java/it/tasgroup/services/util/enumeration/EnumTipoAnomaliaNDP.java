package it.tasgroup.services.util.enumeration;

import it.tasgroup.iris.shared.util.enumeration.MessageDescription;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Tipo anomalia
 */
public enum EnumTipoAnomaliaNDP implements MessageDescription{

	/**
	 * Abbinamento Manuale
	 */
	I_ABBINAMENTO_MANUALE("I_ABBINAMENTO_MANUALE", "Abbinamento Manuale", ""),

	/**
	 * Riconciliazione fallita
	 */
	E_SUPERAMENTO_LIMITE_ELABORAZIONI("E_SUPERAMENTO_LIMITE_ELABORAZIONI", "Riconciliazione fallita", ""),

	/**
	 * Sintassi IUV errata
	 */
	E_IUV_RF_NON_VALIDO("E_IUV_RF_NON_VALIDO", "Sintassi IUV errata", ""),

	/**
	 * Importo causale incongruente
	 */
	W_CAUSALE_IMPORTO_INCONGRUENTE("W_CAUSALE_IMPORTO_INCONGRUENTE", "Importo causale incongruente", ""),

	/**
	 * Importo movimento maggiore importo posizione
	 */
	E_IMPORTO_MOV_MAGGIORE_IMPORTO_DIST("E_IMPORTO_MOV_MAGGIORE_IMPORTO_DIST", "Importo movimento maggiore importo posizione", ""),

	/**
	 * Importo movimento minore importo posizione
	 */
	E_IMPORTO_MOV_MINORE_IMPORTO_DIST("E_IMPORTO_MOV_MINORE_IMPORTO_DIST", "Importo movimento minore importo posizione", ""),

	/**
	 * Anomalia su flusso riversamento
	 */
	E_ANOMALIE_SU_ESITI("E_ANOMALIE_SU_ESITI", "Anomalia su flusso riversamento", ""),
	/**
	 * Importo movimento maggiore importo flusso riversamento
	 */
	E_IMPORTO_MOV_MAGGIORE_IMPORTO_REND("E_IMPORTO_MOV_MAGGIORE_IMPORTO_REND", "Importo movimento maggiore importo flusso riversamento", ""),
	/**
	 * Importo movimento minore importo flusso riversamento
	 */
	E_IMPORTO_MOV_MINORE_IMPORTO_REND("E_IMPORTO_MOV_MINORE_IMPORTO_REND", "Importo movimento minore importo flusso riversamento", ""),

	/**
	 * Importo rendicontazione riversamento maggiore importo posizione
	 */
	E_IMPORTO_ESI_MAGGIORE_IMPORTO_DIST("E_IMPORTO_ESI_MAGGIORE_IMPORTO_DIST", "Importo rendicontazione riversamento maggiore importo posizione", ""),
	/**
	 * Importo rendicontazione riversamento minore importo posizione
	 */
	E_IMPORTO_ESI_MINORE_IMPORTO_DIST("E_IMPORTO_ESI_MINORE_IMPORTO_DIST", "Importo rendicontazione riversamento minore importo posizione", ""),
	/**
	 * Posizione non trovata
	 */
	E_IUV_NON_TROVATO("E_IUV_NON_TROVATO", "Posizione non trovata", ""),
	/**
	 * Codice SIA non valorizzato
	 */
	E_SIA_NON_TROVATO("E_SIA_NON_TROVATO", "Codice SIA non valorizzato", ""),
	/**
	 * Distinta corrispondente non trovata
	 */
    E_DISTINTA_NON_TROVATA("E_DISTINTA_NON_TROVATA", "Distinta corrispondente non trovata", ""),
	/**
	 * Intestatario del codice SIA non trovato
	 */
    E_INTESTATARIO_NON_TROVATO("E_INTESTATARIO_NON_TROVATO", "Intestatario del codice SIA non trovato", ""),
	/**
	 * Flusso riversamento associato a movimento cumulativo non trovato
	 */
	E_CHIAVI_FR_INCONGRUENTI("E_CHIAVI_FR_INCONGRUENTI ", "Flusso riversamento associato a movimento cumulativo non trovato", ""),
	/**
	 * Errore inatteso
	 */
	E_ERRORE_GENERICO_DETTAGLI_NEL_LOG("E_ERRORE_GENERICO_DETTAGLI_NEL_LOG", "Errore inatteso", ""),
	/**
	 * Causale del movimento non rispetta le specifiche di riversamento.
	 */
	E_CAUSALE_SCONOSCIUTA("E_CAUSALE_SCONOSCIUTA", "Causale del movimento non rispetta le specifiche di riversamento.", "");


	private String chiave;
	private String descrizione;
	private String chiaveBundle;

	private EnumTipoAnomaliaNDP(String chiave, String descrizione, String chiaveBundle) {
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

	public static EnumTipoAnomaliaNDP getByKey(String chiave) {

		EnumTipoAnomaliaNDP desiredItem = null; // Default

		for (EnumTipoAnomaliaNDP item : EnumTipoAnomaliaNDP.values()) {

			if (item.getChiave().equals(chiave)) {

				desiredItem = item;

				break;

				}
			}

		return desiredItem;
	}

}
