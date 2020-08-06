package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

public abstract class AddOnManager<T extends TributoStrutturato> {


	/**
	 * Indica se il plugin gestisce tributi di tipo predeterminato (ricerca pendenza) o liberi (inserimento pendenza)
	 * Sovrascrivere nei manager dei tributi predetereminati. 
	 * @return
	 */
	public boolean isPredetermined() {
		return false;
	}

	/**
	 * Indica se il manager del plugin necessita di dati di configurazione.
	 * Default false per tutti i plugin "storici" che non hanno configurazione.
	 * Sovrascrivere questo metodo nelle classi Manager dei plugin che devono
	 * effettivamente utilizzare dei dati di configurazione.
	 * @return
	 */
	public boolean isConfigurable() {
		return false;
	}

	/**
	 * sovrascrivere questo metodo nei manager che devono passare i dati di configurazione
	 * del plugin ai singoli helper (per ora l'unico caso è il BbeAddOnManager)
	 * @param dati
	 */
	public void setDati(byte[] dati) {
		// nella classe abstact non fa niente.
	}

	/**
	 * Validazione dei dati di configurazione. Default ERRORE per tutti i plugin "storici" che non 
	 * hanno configurazione.
	 * Sovrascrivere questo metodo nelle classi Manager dei plugin che devono
	 * hanno a disposizione le logiche per interpretare e validate i dati di configurazione.
	 * @param dati
	 * @return ValidationMessage
	 */
	public ValidationMessage validateDati(byte[] dati) {
		return new ValidationMessage(ValidationMessage.Esit.KO, "Configurazione non prevista");
	}

	
	public abstract AddOnPersistenceHelper<T> getPersistenceHelper();

	public abstract AddOnSearchHelper<T> getSearchHelper();

	public abstract AddOnViewHelper<T> getViewHelper();

	public abstract AddOnReceiptHelper<T> getReceiptHelper();

	public abstract AddOnCheckHelper<T> getCheckHelper();
	
}
