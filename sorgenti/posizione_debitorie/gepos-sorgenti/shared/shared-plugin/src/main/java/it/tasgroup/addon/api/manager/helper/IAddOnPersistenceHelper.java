package it.tasgroup.addon.api.manager.helper;

import it.tasgroup.addon.api.domain.TributoStrutturato;

import java.util.List;

import javax.persistence.EntityManager;

public interface IAddOnPersistenceHelper<T extends TributoStrutturato> {

	List<T> searchByExample(T tributoStrutturato, EntityManager em);

}
