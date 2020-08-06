/**
 * 25/gen/2010
 */
package it.nch.fwk.xml.xsdcatalog;

import org.w3c.dom.ls.LSInput;

import java.io.Reader;
import java.io.InputStream;

public class LsInputImpl implements LSInput {

	protected String publicId = null;
	protected String systemId = null;
	protected String baseSystemId = null;

	protected String encoding = null;

	protected boolean certifiedText = false;
	private InputStream reader;

	public LsInputImpl() {
	}

	public LsInputImpl(String publicId, String systemId, String baseSystemId, InputStream rdr) {
		this.publicId = publicId;
		this.systemId = systemId;
		this.baseSystemId = baseSystemId;
		this.encoding = null;
		this.reader = rdr;
	}

	public InputStream getByteStream() {
		return reader;
	}

	public void setByteStream(InputStream byteStream) {
		throw new UnsupportedOperationException();
	}

	public Reader getCharacterStream() {
		return null;
	}

	public void setCharacterStream(Reader characterStream) {
		throw new UnsupportedOperationException();
	}

	public String getStringData() {
		return null;
	}

	public void setStringData(String stringData) {
		throw new UnsupportedOperationException();
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		throw new UnsupportedOperationException();
	}

	public String getPublicId() {
		return publicId;
	}

	public void setPublicId(String publicId) {
		throw new UnsupportedOperationException();
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		throw new UnsupportedOperationException();
	}

	public String getBaseURI() {
		return baseSystemId;
	}

	public void setBaseURI(String baseURI) {
		throw new UnsupportedOperationException();
	}

	public boolean getCertifiedText() {
		return false;
	}

	public void setCertifiedText(boolean certifiedText) {
		throw new UnsupportedOperationException();
	}

}
