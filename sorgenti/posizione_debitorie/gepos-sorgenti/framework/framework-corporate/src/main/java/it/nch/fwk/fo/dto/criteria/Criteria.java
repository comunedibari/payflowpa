/*
 * Creato il 6-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto.criteria;

import java.io.Serializable;

/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public interface Criteria extends Serializable {
	
    //public abstract Criteria add(Criterion criterion);

    public abstract Criteria createCriteria(String s);
    
    public abstract Criteria setMaxResults(int i);

    public abstract Criteria setComment(String s);
    
}
