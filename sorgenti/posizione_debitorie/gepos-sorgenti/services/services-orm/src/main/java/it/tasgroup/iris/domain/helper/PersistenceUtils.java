package it.tasgroup.iris.domain.helper;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class PersistenceUtils   {
	
	public static <T> T materialize(T var) {
	    if (var == null) {
	        throw new IllegalArgumentException("passed argument is null");
	    }

	    Hibernate.initialize(var);
	    if (var instanceof HibernateProxy) {
	        var = (T) ((HibernateProxy) var).getHibernateLazyInitializer().getImplementation();
	    }
	    return var;
	}
}
