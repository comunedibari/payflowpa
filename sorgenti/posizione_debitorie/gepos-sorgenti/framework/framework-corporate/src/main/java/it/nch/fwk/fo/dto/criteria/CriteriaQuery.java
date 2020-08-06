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
public interface CriteriaQuery extends Serializable {
	
    public abstract String getColumn(Criteria criteria, String s);
    
    public abstract Object getType(Criteria criteria, String s);

    public abstract String getEntityName(Criteria criteria);

    public abstract String getEntityName(Criteria criteria, String s);
    
    public abstract String[] getIdentifierColumns(Criteria criteria);
    
}
