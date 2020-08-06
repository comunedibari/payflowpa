package it.tasgroup.idp.autorizzazionepagamento.utils;

import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.soap.SOAPElement;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

/**
 * Classe di utility per i nodi DOM.
 * 
 * @author Lorenzo Nardi (nardi@link.it)
 * @version $Id: DOMUtils.java 358 2013-05-22 15:32:32Z nardi $
 */
public final class XmlUtils {
	/**
	 * Costruttore privato.
	 */
	private XmlUtils() { }
	/**
	 * Trasforma un nodo in una stringa XML.
	 * @param node Nodo da trasformare
	 * @return la rappresentazione xml
	 * @throws TransformerException se ci sono errori durante la conversione
	 */
	public static String nodeToString(final Node node) throws TransformerException {
        Source source = new DOMSource(node);
        StringWriter stringWriter = new StringWriter();
        Result result = new StreamResult(stringWriter);
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.transform(source, result);
        return stringWriter.getBuffer().toString();
    }

	/**
	 * Cerca e ritorna il primo figlio che rispetta i criteri di ricerca.
	 * @param s Elemento da cui cominciare la ricerca
	 * @param localName Nome locale dell'elemento da ricercare
	 * @return L'elemento cercato se esiste, null altrimenti
	 */
	public static SOAPElement getChildElement(final SOAPElement s, final String localName) {
		Iterator<?> i = s.getChildElements();
		while (i.hasNext()) {
			try {
				SOAPElement n = (SOAPElement) i.next();
				if (n.getLocalName().compareTo(localName) == 0) {
					return n;
				} else {
					SOAPElement e = getChildElement(n, localName);
					if (e != null) {
						return e;
					} else {
						continue;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return null;
	}
	
	/**
	 * Cerca e ritorna il primo figlio che rispetta i criteri di ricerca.
	 * @param s Elemento da cui cominciare la ricerca
	 * @param localName Nome locale dell'elemento da ricercare
	 * @param namespace 
	 * @return L'elemento cercato se esiste, null altrimenti
	 */
	public static SOAPElement getChildElement(final SOAPElement s, final String localName, final String namespace) {
		Iterator<?> i = s.getChildElements();
		while (i.hasNext()) {
			try {
				SOAPElement n = (SOAPElement) i.next();
				if (n.getLocalName().compareTo(localName) == 0 && n.getNamespaceURI().contains(namespace)) {
					return n;
				} else {
					SOAPElement e = getChildElement(n, localName);
					if (e != null) {
						return e;
					} else {
						continue;
					}
				}
			} catch (Exception e) {
				continue;
			}
		}
		return null;
	}
}
