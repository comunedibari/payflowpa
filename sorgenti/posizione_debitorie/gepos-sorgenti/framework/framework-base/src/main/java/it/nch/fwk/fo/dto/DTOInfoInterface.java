/*
 * Created on 5-dic-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.dto;

import java.util.Map;

/**
 * @author EE07869
 *
 * Interfaccia che designa un'oggetto informazione dotato di un livello di gravita',
 * un codice, un messaggio e degli argomenti.
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface DTOInfoInterface {


    public static final int SEVERITY_NO_ERROR = 0;

    public static final int SEVERITY_WARNING = 1;

    public static final int SEVERITY_INFO = 2;

    public static final int SEVERITY_ERROR = 3;

    public static final int SEVERITY_ROLLBACK = 4;

    public static final int SEVERITY_GENERIC = 5;

    public String getCode();
    public String getMessage();
    public int getSeverity();

    public abstract void setArgs(Map args);
    public abstract void setMessage(String msg);

}
