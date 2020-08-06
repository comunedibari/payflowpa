/*
 * File: DataSorterHandler.java
 * Package: com.etnoteam.service.text
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 26-ott-03 - 1.06.33
 * Created by: finsaccanebbia (Etnoteam)
 */
package it.nch.fwk.fo.base.text;
import it.nch.fwk.fo.base.config.ConfigConstants;
import it.nch.fwk.fo.xml.XMLFileCache;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author dcerri
 *
 * <br>
 * Sax Handler del file XML di configurazione della classe DataFormatter
 * Valorizza un Hashtable (passato per riferimento nel costruttore) con
 * i dati letti dal file di configurazione.
 * Utilizza il contenuto degli attributi bank","channel","lang" come prefisso
 * delle chiavi di memorizzazione nell'hashtable (nell'ordine specificato).
 * Utilizza i nomi dei tag figli del tag padre "FACTORY" per la composizione
 * della chiave stessa (es. il contenuto del tag "DECIMAL_PATTERN" della factory di 
 * nome "FACTORY1" verra' memorizzato nell'hashtable utilizzando come chiave
 * INTESA_INWEB_IT_DECIMAL_PATTERN)
 * 
**/
public final class DataSorterHandler extends DefaultHandler implements DataSorterInterface  {
		
	private Map dataConf = null;		
	private StringBuffer factoryPrefix = null;
	private String lang = null;
	
	private static XMLFileCache fc = new XMLFileCache();
	
	private String sorterName=null;
	private String columnName=null;
					
	/**
	 * Costruttore pubblico
	 * @param conf Hashtable dove memorizzare i dati di configurazione
	 */
	public DataSorterHandler(Map conf) {
		this.dataConf = conf;	
	}
	
	/**
	 * @see org.xml.sax.ContentHandler#startElement(String, String, String, Attributes)
	 */
	public void startElement(java.lang.String uri, java.lang.String localName, java.lang.String qName, Attributes attributes) throws SAXException {
						
		
			try {
				if (qName.equalsIgnoreCase(TAG_FACTORY)) {
					
					this.lang = attributes.getValue("lang");
					
					/* reset string buffer */
					factoryPrefix = new StringBuffer();

					/* prefisso composizione base per factory corrente */
					factoryPrefix.append(attributes.getValue("bank").toUpperCase());
					factoryPrefix.append(SEP);
					factoryPrefix.append(attributes.getValue("channel").toUpperCase());
					factoryPrefix.append(SEP);
					factoryPrefix.append(attributes.getValue("lang").toUpperCase());			
					factoryPrefix.append(SEP);			
					/* flag esistenza factory per i parametri specificati */
					dataConf.put(factoryPrefix.toString(),"1");
				
				} else if (qName.equalsIgnoreCase(TAG_CLASS)) {	
					
					Class cf = null;				
					Locale loc = null;
					
					/* mantengo riferimento ad oggetto Class */		
					cf = Class.forName(attributes.getValue(ATTRIB_TYPE));			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_TYPE,cf);	
					
					/* creo istanza di oggetto Locale e memorizzo in hashtable 
					 * uso il contenuto dell'attributo lang per come ISO code della lingua (lower-case) e 
					 * del paese (upper-case)
					 */				
					loc = new Locale(this.lang.toLowerCase(),this.lang.toUpperCase());
					dataConf.put(factoryPrefix.toString() + qName + SEP + KEY_LOCALE,loc);
								
				} else if (qName.equalsIgnoreCase(TAG_SORTER)) {			
					sorterName=attributes.getValue(ATTRIB_NAME);			
					columnName=attributes.getValue(ATTRIB_COLUMN);
					String alternateColumnName=attributes.getValue(ATTRIB_ALTERNATE);
					if (alternateColumnName!=null) {
						dataConf.put(factoryPrefix.toString() +  sorterName + SEP +columnName + SEP + ATTRIB_ALTERNATE,alternateColumnName);
					}	
				} else if (qName.equalsIgnoreCase(TAG_VALUE)) {			
					dataConf.put(factoryPrefix.toString() +  sorterName + SEP + columnName +SEP +attributes.getValue(ATTRIB_NAME) + SEP + ATTRIB_ORDER,attributes.getValue(ATTRIB_ORDER));
				
				} else if(qName.equalsIgnoreCase(TAG_SORTER)) {
					sorterName=attributes.getValue(ATTRIB_NAME);			
				} else if(qName.equalsIgnoreCase(TAG_DATASORTER)) {
                    /* ignore */
				} else 				
					/* parse error */ 
					throw new SAXException ("Unrecognized TAG: "+qName);
			} catch (ClassNotFoundException e) {
				throw new SAXException ("Parsing error:" +e.getMessage());
			} catch (SAXException e) {
				throw new SAXException ("Parsing error:" +e.getMessage());
			}						
	}

	/**
	 * Caricamento configurazione
	 */
	public static Map reload() throws Exception {
		Map sorterConfiguration = new Hashtable();
		try {
			DataSorterHandler hd = new DataSorterHandler(sorterConfiguration);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

			//saxParser.parse(Configurations.getResourceAsStream(Configurations.RESOURCES_PATH + "DataSorter.xml"), hd );

			String xmlString = fc.get(ConfigConstants.RESOURCES_PATH + "DataSorter.xml").asXML();
			saxParser.parse(new ByteArrayInputStream(xmlString.getBytes()), hd);

		} catch (ParserConfigurationException e) {
			throw new Exception ("Parser configuration Exception: "+ e.getMessage());
		} catch (SAXException e) {
			throw new Exception ("Parsing error: "+e.getMessage());
		} catch (IOException e) {
			throw new Exception ("I/O Exception: "+e.getMessage());
		}
    	return sorterConfiguration;
	}
}
