/*
 * File: ConfigSource.java
 * Package: it.nch.fwk.fo.base.config
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 26-ott-2005 - 10.48.49
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.base.config;

import java.util.Iterator;

import org.dom4j.Node;


/**
 * @author liacono
 *
 * Interfaccia per una sorgente da cui leggere la configurazione
 */
public interface ConfigSource {

    /**
     * Imposta la configurazione per la sorgente utilizzando un nodo XML.
     * 
     * @param configNode - il nodo XML per la configurazione della sorgente
     */
    public void setConfig(Node configNode);
    
    /**
     * Legge la propriet&agrave; specificata e la restituisce
     * @param name - la propriet&agrave; da leggere
     * @return - la propriet&agrave; letta
     */
    public String readProperty(String name);
    
    public Iterator propertyNamesIterator();

    /**
     * Una descrizione della sorgente di configurazione a fini di
     * logging. Tipicamente sara' il nome della classe
     * @return la descrizione
     */
    public String getDescription();
    
}
