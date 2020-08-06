/**
 * 
 */
package it.nch.fwk.xml.schema.impl;

import it.nch.fwk.xml.schema.SchemaInfo;
import it.nch.fwk.xml.schema.SchemaLocator;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author gedfr
 *
 */
public abstract class AbstractSchemaLocator implements SchemaLocator {
		
	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.SchemaLocator#getSystemId(it.nch.fwk.xml.schema.SchemaInfo)
	 */
	public String getSystemId(SchemaInfo info) {
		String systemId = info.getSystemId().toString();
		if(info.getSystemId().isAbsolute()) {
			if(info.getBaseUri() != null && info.getBaseUri().isAbsolute()) {
				systemId = info.getSystemId().getPath();
			}
		} else {
			systemId = DEFAULT_ROOT_URI.resolve(info.getSystemId().getPath()).toString();
		}
		return systemId;
	}
	
	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.SchemaLocator#getSchemaLocationUri(it.nch.fwk.xml.schema.SchemaInfo)
	 */
	public URI getSchemaLocationUri(SchemaInfo info) {
		URI location = info.getSystemId();
		if(info.getBaseUri() != null && info.getBaseUri().isAbsolute()) {
			URI relPath = DEFAULT_ROOT_URI.relativize(URI.create(info.getSystemId().getPath()));
			location = info.getBaseUri().resolve(relPath);
		}
		return location;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.SchemaLocator#getSchemaIdentifier(it.nch.fwk.xml.schema.SchemaInfo)
	 */
	public String getSchemaIdentifier(SchemaInfo info) {
		return getSchemaIdentifier(info.getSystemId().getPath());
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.SchemaLocator#getSchemaIdentifier(java.lang.String)
	 */
	public String getSchemaIdentifier(String path) {
		URI pathUri = DEFAULT_ROOT_URI.resolve(URI.create(path));
		
		String schemaId;
		try {
			schemaId = new URI(SCHEMA_SCHEME, null, pathUri.getPath(), null).toString();
		} catch (URISyntaxException e) {
			schemaId = SCHEMA_SCHEME+":"+pathUri.getPath();
		}
		return schemaId;
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.SchemaLocator#getInputStream(java.net.URI)
	 */
	public InputStream getInputStream(URI uri) throws IOException {
		try {
			return uri.toURL().openStream();
		} catch (IOException e) {
			if(e instanceof MalformedURLException) {
				return openStream(uri);
			}
		}
		throw new IOException("Unexpected exception");
	}
	
	protected abstract InputStream openStream(URI uri) throws IOException;
}
