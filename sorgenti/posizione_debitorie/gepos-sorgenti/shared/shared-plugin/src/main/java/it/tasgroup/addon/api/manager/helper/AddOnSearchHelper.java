package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;
import it.tasgroup.iris.dto.SearchTributiRequest;

import javax.persistence.EntityManager;
import java.util.List;

public interface AddOnSearchHelper<T extends TributoStrutturato> {
	
	/**
	 * Recupera il tributo specifico da visualizzare
	 * @param request contiene i campi del tributo utili per la ricerca
	 * @param em
	 * @return il tributo specifico da visualizzare come primo elemento della lista
	 */
	List<T> find(SearchTributiRequest<T> request, EntityManager em);
}
