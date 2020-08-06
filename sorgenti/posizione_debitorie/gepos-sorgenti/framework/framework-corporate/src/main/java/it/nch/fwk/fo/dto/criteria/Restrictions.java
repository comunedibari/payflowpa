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
public class Restrictions {

	public Restrictions()
    {
    }

	public static SimpleExpression eq(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, "=", "eq");
    }
	
    public static SimpleExpression ne(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, "<>", "ne");
    }

    public static SimpleExpression like(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, " like ", "like");
    }

    public static SimpleExpression gt(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, ">", "gt");
    }

    public static SimpleExpression lt(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, "<", "lt");
    }

    public static SimpleExpression le(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, "<=", "le");
    }

    public static SimpleExpression ge(String propertyName, Object value)
    {
        return new SimpleExpression(propertyName, value, ">=", "ge");
    }
    
}
