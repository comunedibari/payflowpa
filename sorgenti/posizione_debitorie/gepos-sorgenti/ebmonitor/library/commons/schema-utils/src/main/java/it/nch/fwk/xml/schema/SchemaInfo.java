/**
 * 
 */
package it.nch.fwk.xml.schema;

import java.net.URI;

/**
 * @author gedfr
 * 
 */
public class SchemaInfo {

	private String namespaceURI;
	private URI systemId;
	private URI baseUri;
	
	/**
	 * 
	 */
	public SchemaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param namespaceURI
	 * @param systemId
	 * @param baseUri
	 */
	public SchemaInfo(String namespaceURI, URI systemId) {
		this.namespaceURI = namespaceURI;
		this.systemId = systemId;
	}

	/**
	 * @param namespaceURI
	 * @param systemId
	 * @param baseUri
	 */
	public SchemaInfo(String namespaceURI, URI systemId, URI baseUri) {
		this.namespaceURI = namespaceURI;
		this.systemId = systemId;
		this.baseUri = baseUri;
	}

	/**
	 * @param namespaceURI
	 * @param systemId
	 * @param baseUri
	 */
	public SchemaInfo(String namespaceURI, String systemId, String baseUri) {
		this(namespaceURI, URI.create(systemId), baseUri!=null?URI.create(baseUri):null);
	}

	/**
	 * @return the baseUri
	 */
	public URI getBaseUri() {
		return baseUri;
	}

	/**
	 * @param baseUri the baseUri to set
	 */
	public void setBaseUri(URI baseUri) {
		this.baseUri = baseUri;
	}

	/**
	 * @return the namespaceURI
	 */
	public String getNamespaceURI() {
		return namespaceURI;
	}

	/**
	 * @param namespaceURI the namespaceURI to set
	 */
	public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	/**
	 * @return the systemId
	 */
	public URI getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(URI systemId) {
		this.systemId = systemId;
	}
	
	/**
	 * @param systemId the systemId to set
	 */
	public void setStringSystemId(String systemId) {
		this.systemId = URI.create(systemId);
	}
	
	/**
	 * @param baseUri the baseUri to set
	 */
	public void setStringBaseUri(String baseUri) {
		this.baseUri = URI.create(baseUri);
	}

}
