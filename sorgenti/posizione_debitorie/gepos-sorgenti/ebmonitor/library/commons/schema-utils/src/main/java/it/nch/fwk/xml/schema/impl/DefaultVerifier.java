/**
 * 
 */
package it.nch.fwk.xml.schema.impl;

import java.io.File;
import java.io.IOException;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import it.nch.fwk.xml.schema.Verifier;

/**
 * @author gedfr
 * @version $Header$
 *
 */
public class DefaultVerifier implements Verifier {

	/**
	 * 
	 */
	public DefaultVerifier() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.Verifier#verify(java.lang.String)
	 */
	public boolean verify(String source) throws SAXException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.Verifier#verify(org.xml.sax.InputSource)
	 */
	public boolean verify(InputSource source) throws SAXException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.Verifier#verify(java.io.File)
	 */
	public boolean verify(File source) throws SAXException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.Verifier#verify(org.w3c.dom.Node)
	 */
	public boolean verify(Node source) throws SAXException, IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
