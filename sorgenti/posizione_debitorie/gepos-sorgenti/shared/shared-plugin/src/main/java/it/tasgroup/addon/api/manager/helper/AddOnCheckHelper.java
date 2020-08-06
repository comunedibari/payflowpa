package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

import java.util.List;

import javax.persistence.EntityManager;

public interface AddOnCheckHelper<T extends TributoStrutturato> {
	
	List<T> checkExistingPayments(T tributo, EntityManager em);
	
}
