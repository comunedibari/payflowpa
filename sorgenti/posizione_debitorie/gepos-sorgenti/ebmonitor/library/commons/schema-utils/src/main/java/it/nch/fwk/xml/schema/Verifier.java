package it.nch.fwk.xml.schema;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author gedfr
 * @version $Header$
 *
 */
public interface Verifier {
	
	/**
	 * @param source
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract boolean verify(String source) throws SAXException, IOException;
	
	/**
	 * @param source
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract boolean verify(InputSource source) throws SAXException, IOException;
	
	/**
	 * @param source
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract boolean verify(File source) throws SAXException, IOException;

	/**
	 * @param source
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract boolean verify(Node source) throws SAXException, IOException;

}
