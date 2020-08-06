package it.tasgroup.iris.domain.helper;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe di ausilio nelle operazioni che riguardano le entità di persistenza
 * 
 * @author pazzik
 *
 * @param <T>
 */
public class EntityHelper<T extends Serializable> {
	
	public T getOneToOneObj(Set<T> setOfMany) {
		
		if (setOfMany==null || setOfMany.isEmpty())
			return null;
		
		if (setOfMany.size()>1)
			throw new IllegalStateException("Relazione OneToOne");
		
 		return setOfMany.iterator().next();
 	}
	
    public  Set<T> setOneToOneObj(T oneToOneObj, Set<T> setOfMany){
		
			if (setOfMany == null)
				setOfMany = new HashSet<T>();
						
			setOfMany.clear();
			
			if (oneToOneObj!=null) {	
				setOfMany.add(oneToOneObj);
			} 
			
 		return setOfMany;
    }

	
	
}
