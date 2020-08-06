package it.tasgroup.idp.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceFactory {
	
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEntityManagerFactory(String persistentUnit) {
		if (emf==null) {
			emf = Persistence.createEntityManagerFactory(persistentUnit);
		}
		return(emf);	
	}
}
