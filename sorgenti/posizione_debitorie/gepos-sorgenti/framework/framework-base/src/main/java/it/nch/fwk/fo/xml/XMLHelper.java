package it.nch.fwk.fo.xml;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.Node;

/**
 * <p>Utility class, facilita l'accesso al contenuto testuale dei nodi di
 * un documento XML fornendo nel contempo la conversione da e verso 
 * i piu' comuni tipi di dati.
 * </p>
 * <p>I tipi di dato gestiti/supportati sono:
 * <ul>
 *   <li>boolean</li>
 *   <li>short</li>
 *   <li>int</li>
 *   <li>long</li>
 *   <li>BigInteger</li>
 *   <li>float</li>
 *   <li>double</li>
 *   <li>BigDecimal</li>
 *   <li>Date</li>
 * <li>
 * </p>
 * <p>I metodi di set e get del valore di un nodo sono fornti in
 * diverse varanti a seconda che il nodo sia indicato esplicitamente,
 * o mediante una espressione XPath a partire dalla root del documento
 * o di un nodo intermedio.
 * </p>
 * <p>Sono poi definite varianti che consentono, sui metodi di get,
 * di specificare un valore di default qualora il nodo sia non valorizzato
 * </p>
 */
public class XMLHelper {
	private static final SimpleDateFormat defaultDateFormat;
	private static final Set ok;
	private static final Set ko;
	
	static {
		 defaultDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 ok = new HashSet(10);

		 ok.add("true");
		 ok.add("yes");
		 ok.add("ok");
		 ok.add("y");
		 ok.add("si");
		 ok.add("s");
		 ok.add("t");
		 ok.add("1");


		 ko = new HashSet(10);
		 
		 ko.add("false");
		 ko.add("no");
		 ko.add("ko");
		 ko.add("n");
		 ko.add("f");
		 ko.add("0");
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>boolean</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static boolean getBoolean(Document document, String xpathExpression)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{

		return getBoolean(getNode(document, xpathExpression));
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>boolean</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static boolean getBoolean(Document document, String xpathExpression, boolean defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getBoolean(getNode(document, xpathExpression), defaultValue);
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>boolean</code>.
	 * </p>
	 * <p>Questa variante consente di specificare una diversa
	 * rappresentazione testuale dei valori true e false.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param trueRep	   la rappresentazione del valore "true"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @param trueRep	   la rappresentazione del valore "false"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static boolean getBoolean(Document document, String xpathExpression, String trueRep, String falseRep)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{

		return getBoolean(getNode(document, xpathExpression), trueRep, falseRep);
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>boolean</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 * <p>Questa variante consente di specificare una diversa
	 * rappresentazione testuale dei valori true e false.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 * @param trueRep	   la rappresentazione del valore "true"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @param trueRep	   la rappresentazione del valore "false"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static boolean getBoolean(Document document, String xpathExpression, boolean defaultValue, String trueRep, String falseRep)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getBoolean(getNode(document, xpathExpression), defaultValue, trueRep, falseRep);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>boolean</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static boolean getBoolean(Node n)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText().toLowerCase();
		if( ok.contains(val) ) {
			return true;
		}
		if( ko.contains(val) ) {
			return false;
		}
		throw new XMLHelperParseValueException(n.getName(), "boolean");
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>boolean</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static boolean getBoolean(Node n, boolean defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText().toLowerCase();
		if( "".equals(val) ) {
			return defaultValue;
		}
		if( ok.contains(val) ) {
			return true;
		}
		if( ko.contains(val) ) {
			return false;
		}
		throw new XMLHelperParseValueException(n.getName(), "boolean");
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>boolean</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Questa variante consente di specificare una diversa
	 * rappresentazione testuale dei valori true e false.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param trueRep	   la rappresentazione del valore "true"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @param trueRep	   la rappresentazione del valore "false"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static boolean getBoolean(Node n, String trueRep, String falseRep)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if(val.equals(trueRep)) {
			return true;
		}
		if(val.equals(falseRep)) {
			return false;
		}
		throw new XMLHelperParseValueException(n.getName(), "boolean");
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>boolean</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 * <p>Questa variante consente di specificare una diversa
	 * rappresentazione testuale dei valori true e false.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 * @param trueRep	   la rappresentazione del valore "true"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @param trueRep	   la rappresentazione del valore "false"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 *
	 * @return <code>boolean</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         boolean
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static boolean getBoolean(Node n, boolean defaultValue, String trueRep, String falseRep)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if(val.equals(trueRep)) {
			return true;
		}
		if(val.equals(falseRep)) {
			return false;
		}
		
		if( "".equals(val) ) {
			return defaultValue;
		}

		throw new XMLHelperParseValueException(n.getName(), "boolean");
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>short</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>short</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         short
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static short getShort(Document document, String xpathExpression)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getShort(getNode(document, xpathExpression));
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>short</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>short</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         short
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static short getShort(Document document, String xpathExpression, short defaultValue)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getShort(getNode(document, xpathExpression), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>short</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>short</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         short
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static short getShort(Node n)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		try {
			return Short.parseShort(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "short integer", e);
		} 
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>short</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>short</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         short
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static short getShort(Node n, short defaultValue)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) ) {
			return defaultValue;
		}
		try { 
			return Short.parseShort(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "short integer", e);
		} 
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>int</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>int</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         int
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static int getInt(Document document, String xpathExpression)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getInt(getNode(document, xpathExpression));
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>int</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>int</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         int
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static int getInt(Document document, String xpathExpression, int defaultValue)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getInt(getNode(document, xpathExpression), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>int</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>int</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         int
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static int getInt(Node n)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		try {
			return Integer.parseInt(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "integer", e);
		} 
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>int</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>int</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         int
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static int getInt(Node n, int defaultValue)
		throws
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) ) {
			return defaultValue;
		}
		try { 
			return Integer.parseInt(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "integer", e);
		} 
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>long</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>long</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         long
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static long getLong(Document document, String xpathExpression)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getLong(getNode(document, xpathExpression));
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>long</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>long</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         long
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static long getLong(Document document, String xpathExpression, long defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getLong(getNode(document, xpathExpression), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>long</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>long</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         long
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static long getLong(Node n)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		try { 
			return Long.parseLong(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "long integer", e);
		} 
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>long</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>long</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         long
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static long getLong(Node n, long defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) ) {
			return defaultValue;
		}
		try { 
			return Long.parseLong(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "long integer", e);
		} 
	}
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>BigInteger</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>BigInteger</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigInteger
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static BigInteger getBigInteger(Document document, String xpathExpression)
		throws 
			XMLHelperParseValueException,
			XMLHelperNodeNotFoundException
	{
		return getBigInteger(getNode(document, xpathExpression), null);
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>BigInteger</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>BigInteger</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigInteger
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static BigInteger getBigInteger(Document document, String xpathExpression, BigInteger defaultValue)
		throws 
			XMLHelperParseValueException,
			XMLHelperNodeNotFoundException
	{
		return getBigInteger(getNode(document, xpathExpression), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>BigInteger</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>BigInteger</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigInteger
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static BigInteger getBigInteger(Node n)
		throws XMLHelperParseValueException
	{
		return getBigInteger(n, null);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>BigInteger</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>BigInteger</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigInteger
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static BigInteger getBigInteger(Node n, BigInteger defaultValue)
		throws XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) && defaultValue != null ) {
			return defaultValue;
		}
		try { 
			return new BigInteger(val);
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "BigInteger", e);
		} 
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>float</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>float</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         float
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static float getFloat(Document document, String xpathExpression)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getFloat(getNode(document, xpathExpression));
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>float</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>float</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         float
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static float getFloat(Document document, String xpathExpression, float defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getFloat(getNode(document, xpathExpression), defaultValue);
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>float</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>float</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         float
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static float getFloat(Node n)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		try { 
			return Float.parseFloat(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "floating point", e);
		} 
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>float</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>float</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         float
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static float getFloat(Node n, float defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) ) {
			return defaultValue;
		}
		try { 
			return Float.parseFloat(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "floating point", e);
		} 
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>double</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>double</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         double
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static double getDouble(Document document, String xpathExpression)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDouble(getNode(document, xpathExpression));
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>double</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>double</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         double
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static double getDouble(Document document, String xpathExpression, double defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDouble(getNode(document, xpathExpression), defaultValue);
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>double</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>double</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         double
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static double getDouble(Node n)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		try { 
			return Double.parseDouble(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "double precision floating point", e);
		} 
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>double</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>double</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         double
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static double getDouble(Node n, double defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) ) {
			return defaultValue;
		}
		try { 
			return Double.parseDouble(val); 
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "double precision floating point", e);
		} 
	}
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>BigDecimal</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>BigDecimal</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigDecimal
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static BigDecimal getBigDecimal(Document document, String xpathExpression)
		throws 
			XMLHelperParseValueException,
			XMLHelperNodeNotFoundException
	{
		return getBigDecimal(getNode(document, xpathExpression), null);
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>BigDecimal</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>BigDecimal</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigDecimal
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static BigDecimal getBigDecimal(Document document, String xpathExpression, BigDecimal defaultValue)
		throws 
			XMLHelperParseValueException,
			XMLHelperNodeNotFoundException
	{
		return getBigDecimal(getNode(document, xpathExpression), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>BigDecimal</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>BigDecimal</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigDecimal
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static BigDecimal getBigDecimal(Node n)
		throws XMLHelperParseValueException
	{
		return getBigDecimal(n, null);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>BigDecimal</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>BigDecimal</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         BigDecimal
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static BigDecimal getBigDecimal(Node n, BigDecimal defaultValue)
		throws 
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) && defaultValue != null ) {
			return defaultValue;
		}
		try { 
			return new BigDecimal(val);
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "BigDecimal", e);
		} 
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>Date</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Date getDate(Document document, String xpathExpression)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(document, xpathExpression, defaultDateFormat, null);
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>Date</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Date getDate(Document document, String xpathExpression, Date defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(document, xpathExpression, defaultDateFormat, defaultValue);
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>Date</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Date getDate(Document document, String xpathExpression, String format)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(document, xpathExpression, new SimpleDateFormat(format), null);
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>Date</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Date getDate(Document document, String xpathExpression, DateFormat format)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(document, xpathExpression, format, null);
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>Date</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Date getDate(Document document, String xpathExpression, String format, Date defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(document, xpathExpression, new SimpleDateFormat(format), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath effettuando contestualmente la conversione da
	 * da <code>String</code> a <code>Date</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Date getDate(Document document, String xpathExpression, DateFormat format, Date defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(getNode(document, xpathExpression), format, defaultValue);
	}

	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 *
	 * @return <code>String</code> - 
	 *                         il contenuto testuale del nodo
	 *
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static String getText(Document document, String xpathExpression)
		throws 
			XMLHelperNodeNotFoundException
	{
		return getText(getNode(document, xpathExpression));
	}
	
	/**
	 * <p>Estrae il valore del nodo spedificato dall'espressione 
	 * XPath.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>String</code> - 
	 *                         il contenuto testuale del nodo
	 *
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static String getText(Document document, String xpathExpression, String defaultValue)
		throws 
			XMLHelperNodeNotFoundException
	{
		return getText(getNode(document, xpathExpression), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>Date</code> a
	 * <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static Date getDate(Node n)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(n, defaultDateFormat, null);
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>Date</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static Date getDate(Node n, Date defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(n, defaultDateFormat, defaultValue);
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>Date</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static Date getDate(Node n, String format)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(n, new SimpleDateFormat(format), null);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>Date</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static Date getDate(Node n, DateFormat format)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(n, format, null);
	}

	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>Date</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static Date getDate(Node n, String format, Date defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		return getDate(n, new SimpleDateFormat(format), defaultValue);
	}
	
	/**
	 * <p>Estrae il valore del nodo specificato effettuando 
	 * contestualmente la conversione da <code>Date</code> a
	 * <code>String</code>.
	 * </p>
	 * <p>Il metodo, in questa variante, consente anche di specificare 
	 * il formato da utilizzare per interpretare come data il contenuto 
	 * testuale del nodo.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param format           il formato da utilizzare per interpretare
	 *                         il contenuto testuale del nodo come
	 *                         data in luogo della rappresentazione 
	 *                         canonica (dd/MM/yyyy)
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>Date</code> - 
	 *                         il valore ottenuto dalla conversione
	 *                         del contenuto testuale del nodo a
	 *                         Date
	 *
	 *
	 * @throws XMLHelperParseValueException  
	 *                         quando l'elemento indirizzato non
	 *                         contiene la rapresentazione testuale
	 *                         del tipo/formato atteso
	 */
	public static Date getDate(Node n, DateFormat format, Date defaultValue)
		throws 
			XMLHelperNodeNotFoundException,
			XMLHelperParseValueException
	{
		String val = n.getText();
		if( "".equals(val) ) {
			return defaultValue;
		}
		try {
			return format.parse(val);
		}
		catch(Exception e) {
			throw new XMLHelperParseValueException(n.getName(), "date", e);
		}
	}

	/**
	 * <p>Estrae il valore del nodo specificato.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 *
	 * @return <code>String</code> - 
	 *                         il contenuto testuale del nodo
	 *
	 */
	public static String getText(Node n)
	{
		return n.getText();
	}

	/**
	 * <p>Estrae il valore del nodo specificato.
	 * </p>
	 * <p>Il metodo consente inoltre di specificare un valore di
	 * default che viene restituito quando l'elemento indirizzato
	 * non e' valorizzato (i.e. <code>Node.getText()</code> 
	 * ritorna una stringa vuota).
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param defaultValue     il valore che viene restituito nel caso
	 *                         che l'elemento specificato non sia
	 *                         valorizzato.
	 *
	 *
	 * @return <code>String</code> - 
	 *                         il contenuto testuale del nodo
	 *
	 */
	public static String getText(Node n, String defaultValue)
	{
		String val = n.getText();
		if( "".equals(val) && defaultValue != null ) {
			return defaultValue;
		}
		return val;
	}

	/**
	 * <p>Individua all'interno di un documento XML il nodo indirizzato 
	 * da una data espressione XPath.
	 * </p>
	 * 
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 * 
	 * 
	 * @return <code>Node</code>
	 *                         il nodo selezionato.
	 *
	 *  
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Node getNode(Document document, String xpathExpression) 
		throws 
			XMLHelperNodeNotFoundException
	{
		try {
			Node n;
			if( (n = document.selectSingleNode(xpathExpression)) == null ) {
				throw new XMLHelperNodeNotFoundException(xpathExpression);
			} 
			return n;
		}
		catch(Exception e) {
			throw new XMLHelperNodeNotFoundException(xpathExpression, e);
		}
	}
	
	/**
	 * <p>Individua all'interno di un documento XML il nodo indirizzato 
	 * da una data espressione XPath relativamente al nodo specificato.
	 * </p>
	 * 
	 * @param node             il nodo a partire dal quale viene
	 *                         individuato il nodo desiderato
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 * 
	 * 
	 * @return <code>Node</code>
	 *                         il nodo selezionato.
	 *
	 *  
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Node getNode(Node n, String xpathExpression) 
		throws 
			XMLHelperNodeNotFoundException
	{
		try {
			Node n1;
			if( (n1 = n.selectSingleNode(xpathExpression)) == null ) {
				throw new XMLHelperNodeNotFoundException(xpathExpression);
			} 
			return n1;
		}
		catch(Exception e) {
			throw new XMLHelperNodeNotFoundException(xpathExpression, e);
		}
	}
	
	/**
	 * <p>Individua all'interno di un documento XML la collezione di nodi
	 * individuati da una data espressione XPath.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 * 
	 * 
	 * @return <code>List</code>
	 *                         la lista, eventualmente vuota, dei nodi 
	 *                         selezionati.
	 *
	 *  
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static List getNodeList(Document document, String xpathExpression) 
		throws XMLHelperNodeNotFoundException
	{
		try {
			return document.selectNodes(xpathExpression);
		}
		catch(Exception e) {
			throw new XMLHelperNodeNotFoundException(xpathExpression, e);
		}
	}
	
	/**
	 * <p>Individua all'interno di un documento XML la lista di nodi
	 * indirizzati da una data espressione XPath relativamente al nodo 
	 * specificato.
	 * </p>
	 * 
	 * @param node             il nodo a partire dal quale viene
	 *                         individuato il nodo desiderato
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 * 
	 * 
	 * @return <code>Node</code>
	 *                         il nodo selezionato.
	 *
	 *  
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static List getNodeList(Node node, String xpathExpression) 
		throws XMLHelperNodeNotFoundException
	{
		try {
			return node.selectNodes(xpathExpression);
		}
		catch(Exception e) {
			throw new XMLHelperNodeNotFoundException(xpathExpression, e);
		}
	}

	/**
	 * <p>Individua all'interno di un documento XML la collezione di nodi
	 * individuati da una data espressione XPath di cui ritorna un
	 * Iterator.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 * 
	 * 
	 * @return <code>Iterator</code>
	 *                         l'iterator della la lista, eventualmente 
	 *                         vuota, dei nodi  selezionati.
	 *
	 *  
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Iterator getNodeIterator(Document document, String xpathExpression) 
		throws XMLHelperNodeNotFoundException
	{
		try {
			return document.selectNodes(xpathExpression).iterator();
		}
		catch(Exception e) {
			throw new XMLHelperNodeNotFoundException(xpathExpression, e);
		}
	}
	
	/**
	 * <p>Individua all'interno di un documento XML la lista di nodi
	 * indirizzati da una data espressione XPath, relativamente al nodo 
	 * specificato, di cui ritorna un Iterator.
	 * </p>
	 * 
	 * @param node             il nodo a partire dal quale viene
	 *                         individuato il nodo desiderato
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 * 
	 * 
	 * @return <code>Iterator</code>
	 *                         l'iterator della la lista, eventualmente 
	 *                         vuota, dei nodi  selezionati.
	 *
	 *  
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static Iterator getNodeIterator(Node node, String xpathExpression) 
		throws XMLHelperNodeNotFoundException
	{
		try {
			return node.selectNodes(xpathExpression).iterator();
		}
		catch(Exception e) {
			throw new XMLHelperNodeNotFoundException(xpathExpression, e);
		}
	}

	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>boolean</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, boolean value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}
	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>boolean</code> a <code>String</code>.
	 * </p>
	 * <p>In questa variante, la funzione consente di specificare una
	 * rappresentazione testuale alternativa dei valori true e false.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @param trueRep	   la rappresentazione del valore "true"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @param trueRep	   la rappresentazione del valore "false"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, boolean value, String trueRep, String falseRep)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value, trueRep, falseRep);
	}
	
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>boolean</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, boolean value)
	{
		n.setText(value? "true" : "false");
	}
	
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>boolean</code>
	 * a <code>String</code>.
	 * </p>
	 * <p>In questa variante, la funzione consente di specificare una
	 * rappresentazione testuale alternativa dei valori true e false.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @param trueRep	   la rappresentazione del valore "true"
	 *                         da utilizzare in luogo di quella canonica
	 *
	 * @param trueRep	   la rappresentazione del valore "false"
	 *                         da utilizzare in luogo di quella canonica
	 */
	public static void set(Node n, boolean value, String trueRep, String falseRep)
	{
		n.setText(value? trueRep : falseRep);
	}

	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>short</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, short value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}

	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>short</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, short value)
	{
		n.setText(""+value);
	}
	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>int</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, int value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}

	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>int</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, int value)
	{
		n.setText(""+value);
	}

	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>long</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, long value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}
	
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>long</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, long value)
	{
		n.setText(""+value);
	}

	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>BigInteger</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, BigInteger value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}

	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>BigInteger</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, BigInteger value)
	{
		n.setText(value.toString());
	}
	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>float</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, float value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}
	
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>float</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, float value)
	{
		DecimalFormat df = new DecimalFormat("00*.00*");
		n.setText(df.format(value));
	}
	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>double</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, double value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}

	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>double</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, double value)
	{
		DecimalFormat df = new DecimalFormat("00*.00*");
		n.setText(df.format(value));
	}

	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>BigDecimal</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, BigDecimal value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}
	
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>BigDecimal</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, BigDecimal value)
	{
		n.setText(value.toString());
	}
	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>Date</code> a <code>String</code>.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, Date value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}
	
	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato effettuando contestualmente la 
	 * conversione da <code>Date</code> a <code>String</code>.
	 * </p>
	 * <p>In questa variante, la funzione consente di specificare un
	 * formato di rappresentazine alternativo alla rappresentazione
	 * canonica (dd/MM/yyyy)
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @param format           il formato da utilizzare in luogo della
	 *                         rappresentazione canonica (dd/MM/yyyy)
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, Date value, DateFormat format)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value, format);
	}
	
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>Date</code>
	 * a <code>String</code>.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, Date value)
	{
		set(n, value, null);
	}

	/**
	 * <p>Setta il valore del nodo specificato al valore indicato 
	 * effettuando contestualmente la conversione da <code>Date</code>
	 * a <code>String</code>.
	 * </p>
	 * <p>In questa variante, la funzione consente di specificare un
	 * formato di rappresentazine alternativo alla rappresentazione
	 * canonica (dd/MM/yyyy)
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @param format           il formato da utilizzare in luogo della
	 *                         rappresentazione canonica (dd/MM/yyyy)
	 */
	public static void set(Node n, Date value, DateFormat format)
	{
		String formatted;
		
		DateFormat dateFormat=format;
		if( dateFormat == null ) {
		    dateFormat = defaultDateFormat;
		}
		
		synchronized(dateFormat) {
			formatted = dateFormat.format(value);
		}
		n.setText(formatted);
	}

	/**
	 * <p>Setta il valore del nodo spedificato dall'espressione 
	 * XPath al valore indicato.
	 * </p>
	 *
	 * @param document         il documento nel quale individuare il nodo
	 *
	 * @param xpathExpression  l'espressione XPath che individua il
	 *                         nodo su cui operare
	 *
	 * @param value            il nuovo valore
	 *
	 * @throws XMLHelperNodeNotFoundException  
	 *                         quando l'elemento indirizzato non esiste
	 */
	public static void set(Document document, String xpathExpression, String value)
		throws 
			XMLHelperNodeNotFoundException
	{
		set(getNode(document, xpathExpression), value);
	}
		
	/**
	 * <p>Setta il valore del nodo specificato al valore indicato.
	 * </p>
	 *
	 * @param n                il nodo (i.e. Element/Attribute) su cui 
	 *                         operare
	 *
	 * @param value            il nuovo valore
	 */
	public static void set(Node n, String value)
	{
		n.setText(value);
	}
}





