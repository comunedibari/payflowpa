package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

public interface AddOnInsertPhaseSupport<T extends TributoStrutturato> {
	/**
	 * Costruisce (a partire i campi del tributo) la causale che verrà utilizzata
	 * in fase di inserimento della pendenza (JLTPEND.DE_CAUSALE).
	 * @param tributoStrutturato
	 * @return la cusale
	 */
	String computeCausale(T tributoStrutturato);
}
