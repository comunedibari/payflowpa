/*
 * File: ConfigSourceFactory.java
 * Package: it.nch.fwk.fo.base.config
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 27-ott-2005 - 15.49.32
 * Created by: liacono (Etnoteam)
 */
package it.nch.fwk.fo.base.config;

import java.util.HashMap;
import java.util.List;


/**
 * @author liacono
 *
 * Factory per il recupero delle sorgenti della configurazione.
 * La factory carica di default le sorgenti per il framework. A queste
 * si possono aggiungere altre sorgenti tramite il caricamento di altri files.
 */
public class ConfigSourceFactory {
    
    //private static Logger logger = Logger.getLogger(ConfigSourceFactory.class);

    
    private static ConfigSourceFactory singleton = new ConfigSourceFactory();
    
    private HashMap sources = new HashMap();
    
    private ConfigSourceFactory() {
        //readConfig(FRAMEWORK_CONFIG_SOURCE_FILENAME);
    }
    
    /**
     * Legge le sorgenti dal file dato.
     * 
     * @param filename - file da cui leggere le sorgenti
     */
    private void readConfig(String filename) {
    	it.nch.fwk.fo.xml.XMLFileCached file;
        org.dom4j.Document doc;
        List nodeList;
        org.dom4j.Node n;
        ConfigSource cs;
        String name;
        String className;
        
        try {
            file = new it.nch.fwk.fo.xml.XMLFileCached(filename);
	        doc = file.document();
	        nodeList = it.nch.fwk.fo.xml.XMLHelper.getNodeList(doc, "//source");
	        int nodeListSize=nodeList.size();
	        for (int i = 0; i < nodeListSize; i++) {
	            n = (org.dom4j.Node) nodeList.get(i);
	            name = it.nch.fwk.fo.xml.XMLHelper.getNode(n, "@name").getText();
	            className = it.nch.fwk.fo.xml.XMLHelper.getNode(n, "@class-name").getText();
	            cs = (ConfigSource) Class.forName(className).newInstance();
	            cs.setConfig(n);
	            sources.put(name, cs);
	        }
        } catch (Throwable e) {
          //  logger.error("Errore durante la lettura del file di configurazione " + filename, e);
        e.printStackTrace();
        }
    }

    /**
     * Aggiunge le sorgenti contenute nel file a quelle esistenti
     * 
     * @param filename - il file da cui leggere le sorgenti
     */
    public static void readSourcesFile(String filename) {
        getInstance().readConfig(filename);
    }
    
    /**
     * Restituisce un'istanza della factory
     * 
     * @return - un'istanza della factory
     */
    public static ConfigSourceFactory getInstance() {
        return singleton;
    }
    
    /**
     * Restituisce la sorgente con il dato nome
     * 
     * @param name - il nome della sorgente
     * @return la sorgente di configurazione
     */
    public ConfigSource getConfigSourceInstance(String name) {
        return (ConfigSource) sources.get(name);
    }
    
    /**
     * Restituisce la sorgente di configuraione con il dato nome
     * 
     * @param name - il nome della sorgente
     * @return la sorgente di configurazione
     */
    public static ConfigSource getConfigSource(String name) {
    	ConfigSourceFactory thisOne = getInstance();
    	return thisOne.getConfigSourceInstance(name);
    }

}
