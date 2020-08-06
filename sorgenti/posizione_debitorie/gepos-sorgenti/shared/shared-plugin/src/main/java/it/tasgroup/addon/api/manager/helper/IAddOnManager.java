package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.addon.api.manager.IAddOnReportRenderer;

public interface IAddOnManager {
	IAddOnPersistenceHelper<TributoStrutturato> getPersistenceHelper(TributoStrutturato t);
	IAddOnHtmlRenderer getHtmlRenderer(TributoStrutturato t);
	IAddOnReportRenderer getReportRenderer(TributoStrutturato t); 
	// etc....
	
}
 