package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

public interface AddOnPersistenceHelper<T extends TributoStrutturato> {
	
	AddOnInsertPhaseSupport<T> getAddonInsertPhaseSupport();

}
