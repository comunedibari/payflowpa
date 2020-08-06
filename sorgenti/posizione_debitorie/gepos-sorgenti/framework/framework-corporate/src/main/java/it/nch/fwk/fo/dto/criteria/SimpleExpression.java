/*
 * Creato il 6-dic-2005
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.dto.criteria;

/**
 * @author sberisso
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class SimpleExpression implements Criterion {
	
	/**
	 * variabili
	 */
    private final String propertyName;
    private final Object value;
    private boolean ignoreCase;
    private final String op;
    private final String opName;

    /**
     * costruttori
     */
	protected SimpleExpression(String propertyName, Object value, String op, String opName)
    {
        this.propertyName = propertyName;
        this.value = value;
        this.op = op;
        this.opName = opName;
    }

    protected SimpleExpression(String propertyName, Object value, String op, String opName, boolean ignoreCase)
    {
        this.propertyName = propertyName;
        this.value = value;
        this.ignoreCase = ignoreCase;
        this.op = op;
        this.opName = opName;
    }

    /**
     * metodi
     */
    public SimpleExpression ignoreCase()
    {
        ignoreCase = true;
        return this;
    }

    public String toString()
    {
        return propertyName + getOp() + value;
    }

    protected final String getOp()
    {
        return op;
    }
    
    public final String getOpName()
    {
    	return opName;
    }
    public  String GetpropertyName()
    {
    	return propertyName;
    }
    public  Object Getvalue()
    {
    	return value;
    }    
    
}
