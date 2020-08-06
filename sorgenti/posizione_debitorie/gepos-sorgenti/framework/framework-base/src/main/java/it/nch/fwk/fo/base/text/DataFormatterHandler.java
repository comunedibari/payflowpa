/*
 * File: DataFormatterHandler.java
 * Package: com.etnoteam.service.text
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 19-lug-03 - 14.21.01
 * Created by: dcerri (Etnoteam)
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

import org.dom4j.Document;
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
public final class DataFormatterHandler extends DefaultHandler implements DataFormatterInterface  {
		
	private Map dataConf = null;		
	private StringBuffer factoryPrefix = null;
	private String lang = null;

	private static XMLFileCache fc = new XMLFileCache();
					
	/**
	 * Costruttore pubblico
	 * @param conf Hashtable dove memorizzare i dati di configurazione
	 */
	public DataFormatterHandler(Map conf) {
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
								
				} else if (qName.equalsIgnoreCase(TAG_PERC) || 
				            qName.equalsIgnoreCase(TAG_POS_PERC) || 
                            qName.equalsIgnoreCase(TAG_PERCUNIT) || 
                            qName.equalsIgnoreCase(TAG_DECIMAL) || 
                            qName.equalsIgnoreCase(TAG_DECIMAL3) || 
							qName.equalsIgnoreCase(TAG_PRICE) ||
							qName.equalsIgnoreCase(TAG_STOCKPRICE) ||
							qName.equalsIgnoreCase(TAG_FUNDPRICE) ||
					        qName.equalsIgnoreCase(TAG_CREDIT) || 
					        qName.equalsIgnoreCase(TAG_DEBIT) ||
					        qName.equalsIgnoreCase(TAG_EXCHANGE_RATE) ||
							qName.equalsIgnoreCase(TAG_QTY) ||
					        qName.equalsIgnoreCase(TAG_QTY_DEC) ||
					        qName.equalsIgnoreCase(TAG_QTY_INT) ||
					        qName.equalsIgnoreCase(TAG_TOTAL_SIGNED)    
					        ) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_GROUPING,Boolean.valueOf(attributes.getValue(ATTRIB_GROUPING)));
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_SEPARATOR,Boolean.valueOf(attributes.getValue(ATTRIB_SEPARATOR)));
				
				} else if(qName.equalsIgnoreCase(TAG_DATE)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));

				} else if ( qName.equalsIgnoreCase(TAG_TRUNC_PRICE) ){
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_GROUPING,Boolean.valueOf(attributes.getValue(ATTRIB_GROUPING)));
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_SEPARATOR,Boolean.valueOf(attributes.getValue(ATTRIB_SEPARATOR)));
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_ROUNDING,attributes.getValue(ATTRIB_ROUNDING));
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_DECIMALS,attributes.getValue(ATTRIB_DECIMALS));
				} else if(qName.equalsIgnoreCase(TAG_DATEYYYY)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));

                } else if(qName.equalsIgnoreCase(TAG_LITERALDATE)) {           
                    dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));
				
				} else if(qName.equalsIgnoreCase(TAG_TIME)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));
				
				} else if(qName.equalsIgnoreCase(TAG_DATETIME)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));				
					
				} else if(qName.equalsIgnoreCase(TAG_MONTHNAME)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));						
				} else if(qName.equalsIgnoreCase(TAG_DAYMONTH)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));						
				} else if(qName.equalsIgnoreCase(TAG_MONTHYEAR)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + ATTRIB_PATTERN,attributes.getValue(ATTRIB_PATTERN));						
				} else if(qName.equalsIgnoreCase(TAG_CCY)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + attributes.getValue(ATTRIB_CODE),attributes.getValue(ATTRIB_PATTERN));
					if(attributes.getValue(ATTRIB_ROUNDING) != null) dataConf.put(factoryPrefix.toString() + qName + SEP + attributes.getValue(ATTRIB_CODE) + SEP + ATTRIB_ROUNDING, attributes.getValue(ATTRIB_ROUNDING));
					if(attributes.getValue(ATTRIB_DECIMALS) != null) dataConf.put(factoryPrefix.toString() + qName + SEP + attributes.getValue(ATTRIB_CODE) + SEP + ATTRIB_DECIMALS, attributes.getValue(ATTRIB_DECIMALS));
				}  else if(qName.equalsIgnoreCase(TAG_PERC_DEC)) {			
					dataConf.put(factoryPrefix.toString() + qName + SEP + attributes.getValue(ATTRIB_CODE),attributes.getValue(ATTRIB_PATTERN));					
				} else if(qName.equalsIgnoreCase(TAG_FORMATTER)) {
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
		Map formatterConfiguration = new Hashtable();
		try {
			DataFormatterHandler hd = new DataFormatterHandler(formatterConfiguration);
			
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();

//			saxParser.parse(Configurations.getResourceAsStream(Configurations.RESOURCES_PATH + "DataFormatter.xml"), hd );
			Document doc = fc.get(ConfigConstants.RESOURCES_PATH + "DataFormatter.xml");
			String xmlString = doc.asXML();
			saxParser.parse(new ByteArrayInputStream(xmlString.getBytes()), hd);

			
		} catch (ParserConfigurationException e) {
			throw new Exception ("Parser configuration Exception: "+ e.getMessage());
		} catch (SAXException e) {
			throw new Exception ("Parsing error: "+e.getMessage());
		} catch (IOException e) {
			throw new Exception ("I/O Exception: "+e.getMessage());
		}
    	return formatterConfiguration;
	}
}
