/**
 * 
 */
package it.nch.fwk.xml.schema.impl;

import it.nch.fwk.xml.schema.NameNotFoundException;
import it.nch.fwk.xml.schema.SchemaInfo;
import it.nch.fwk.xml.schema.SchemaLocator;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author gedfr
 * 
 */
public class DefaultSchemaLocator extends AbstractSchemaLocator implements SchemaLocator {

	private Map schemaLocationByNamespace;

	private Map schemaLocationBySystemId;

	/**
	 * 
	 */
	public DefaultSchemaLocator() {
		// TODO Auto-generated constructor stub
	}

	private synchronized void init(List schemaLocations) {
		this.schemaLocationByNamespace = new HashMap();
		this.schemaLocationBySystemId = new HashMap();
		for (Iterator it = schemaLocations.iterator(); it.hasNext();) {
			SchemaInfo schemaInfo = (SchemaInfo) it.next();
			if (null != schemaInfo.getNamespaceURI()) {
				this.schemaLocationByNamespace.put(schemaInfo.getNamespaceURI(), schemaInfo);
			}
			if (null != schemaInfo.getSystemId()) {
				this.schemaLocationBySystemId.put(getSchemaIdentifier(schemaInfo), schemaInfo);
			} else {
				throw new NullPointerException("SystemId property for schemaLocation cannot be null.");
			}
		}
	}

	public SchemaInfo resolveSystemId(String systemId) throws NameNotFoundException {
		String id = getSchemaIdentifier(systemId);

		if (this.schemaLocationBySystemId.containsKey(id)) {
			return (SchemaInfo) this.schemaLocationBySystemId.get(id);
		}
		return new SchemaInfo(null, systemId, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see it.nch.fwk.xml.resolv.SchemaLocator#resolveLocation(java.lang.String)
	 */
	public SchemaInfo resolveNamespace(String nameSpaceURI) throws NameNotFoundException {
		if (this.schemaLocationByNamespace.containsKey(nameSpaceURI)) {
			return (SchemaInfo) this.schemaLocationByNamespace.get(nameSpaceURI);
		}
		throw new NameNotFoundException();
	}

	/**
	 * @param schemaLocations
	 *            the schemaLocations to set
	 */
	public void setSchemaLocations(List schemaLocations) {
		if (null == schemaLocations) {
			throw new NullPointerException("Attribute schemaLocations cannot be null");
		}
		init(schemaLocations);
	}

	/* (non-Javadoc)
	 * @see it.nch.fwk.xml.schema.impl.AbstractSchemaLocator#openStream(java.net.URI)
	 */
	protected InputStream openStream(URI uri) throws IOException {
		if("classpath".equals(uri.getScheme())) {
			return getClass().getResourceAsStream(uri.getPath());
		}
		throw new IOException("Unexpected exception");
	}

}
