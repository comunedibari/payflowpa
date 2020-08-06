/*
 * Created on 1-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.exceptions.handler;

import java.util.Map;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface ErrorDetailInterface {
    public static final String ERROR_SEVERITY_GENERIC = "G";

    public static final String ERROR_SEVERITY_ERROR = "E";

    public static final String ERROR_SEVERITY_WARNING = "W";

    public static final String ERROR_SEVERITY_INFO = "C";

    public static final String ERROR_SEVERITY_EMPTY = "V";

    public static final String ERROR_SEVERITY_NO_ERROR = "N";

    public static final String ERROR_SEVERITY_SHADY = "A";

    /**
     * Returns the code.
     * @return String
     */
    public abstract String getCode();

    /**
     * Returns the desc.
     * @return String
     */
    public abstract String getDesc();

    /**
     * Returns the severity.
     * @return String
     */
    public abstract String getSeverity();

    /**
     * Clonazione dell'oggetto.
     */
    public abstract Object clone();

    /**
     * Setta gli argomenti da sostituire al messaggio di errore
     */
    public abstract void setArgs(Map args);

    /**
     * Questo metodo deve essere utilizzato esclusivamente per la costruzione dell'errore
     * proveniente dalla negoziazione titoli.
     *  
     * @param desc The desc to set
     */
    public abstract void setDesc(String desc);
}