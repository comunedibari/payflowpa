package it.tasgroup.services.dao.util;

import it.tasgroup.services.dao.ejb.DaoImplJpaCmtJta;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public abstract class DaoPersistenceProvider {
		
	private static Map<String, EntityManagerFactory> factories = new HashMap<String, EntityManagerFactory>();
	
	public static final EntityManagerFactory getEntityManagerFactory(String persistenceUnitName) {
		if (!factories.containsKey(persistenceUnitName)) {
			factories.put(persistenceUnitName, Persistence.createEntityManagerFactory(persistenceUnitName));
		}
		return factories.get(persistenceUnitName);
	}
	
	@Deprecated
	public static final EntityManager getEntityManager(String persistenceUnitName) {
		return getEntityManagerFactory(persistenceUnitName).createEntityManager();
	}
	

}
