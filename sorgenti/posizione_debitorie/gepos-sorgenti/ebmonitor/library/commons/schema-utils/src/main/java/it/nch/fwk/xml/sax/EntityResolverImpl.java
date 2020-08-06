/**
 * 
 */
package it.nch.fwk.xml.sax;

import it.nch.fwk.xml.schema.NameNotFoundException;
import it.nch.fwk.xml.schema.SchemaInfo;
import it.nch.fwk.xml.schema.SchemaLocator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author gedfr
 *
 */
public class EntityResolverImpl implements EntityResolver {

	private SchemaLocator schemaLocator;
	
	private URI baseURI;
	
	/**
	 * @param schemaLocator
	 * @param baseURI
	 */
	public EntityResolverImpl(SchemaLocator schemaLocator, URI baseURI) {
		this.schemaLocator = schemaLocator;
		this.baseURI = baseURI;
	}

	/* (non-Javadoc)
	 * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
	 */
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		SchemaInfo schemaInfo;
		try {
			schemaInfo = getSchemaLocator().resolveSystemId(systemId);
		} catch (NameNotFoundException e) {
			SAXException se = new SAXException("Cannot resolve "+systemId+".", e);
			throw se;
		}
		if(this.baseURI == null && schemaInfo.getBaseUri()!=null) {
			this.baseURI = schemaInfo.getBaseUri();
		}
		if(schemaInfo.getBaseUri() == null) {
			schemaInfo.setBaseUri(this.baseURI);
		}
		URI locationUri = getSchemaLocator().getSchemaLocationUri(schemaInfo);
		if(null == locationUri) {
			throw new SAXException("Cannot resolve "+systemId+".");
		}
		
		InputStream inputStream = null;
		if("classpath".equals(locationUri.getScheme())) {
			inputStream = getClass().getResourceAsStream(locationUri.getPath());
		} else if("http".equals(locationUri.getScheme())) {
			inputStream = locationUri.toURL().openStream();
		} else {
			throw new RuntimeException("Scheme not supported ["+locationUri.getScheme()+"]");
		}
		
		InputSource source = new InputSource(inputStream);
		source.setPublicId(publicId);
		source.setSystemId(getSchemaLocator().getSystemId(schemaInfo));
		
		return source;

	}

	/**
	 * @return the schemaLocator
	 */
	public SchemaLocator getSchemaLocator() {
		return schemaLocator;
	}

	/**
	 * @param schemaLocator the schemaLocator to set
	 */
	public void setSchemaLocator(SchemaLocator schemaLocator) {
		this.schemaLocator = schemaLocator;
	}

	/**
	 * @return the baseURI
	 */
	public URI getBaseURI() {
		return baseURI;
	}

	/**
	 * @param baseURI the baseURI to set
	 */
	public void setBaseURI(URI baseURI) {
		this.baseURI = baseURI;
	}

}
