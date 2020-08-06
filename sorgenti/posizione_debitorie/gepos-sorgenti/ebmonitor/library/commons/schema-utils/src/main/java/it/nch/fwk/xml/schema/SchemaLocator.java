/**
 * 
 */
package it.nch.fwk.xml.schema;


import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

/**
 * @author gedfr
 *
 */
public interface SchemaLocator {
	
	public static final URI DEFAULT_ROOT_URI = URI.create("/");
	public static final String SCHEMA_SCHEME = "schema";

	/**
	 * @param info
	 * @return
	 */
	public abstract String getSystemId(SchemaInfo info);

	/**
	 * @param info
	 * @return
	 */
	public abstract URI getSchemaLocationUri(SchemaInfo info);
	
	/**
	 * @param info
	 * @return
	 */
	public abstract String getSchemaIdentifier(SchemaInfo info);
	
	/**
	 * @param path
	 * @return
	 */
	public abstract String getSchemaIdentifier(String path);
	
	/**
	 * @param nameSpaceURI
	 * @return
	 */
	public abstract SchemaInfo resolveNamespace(String nameSpaceURI) throws NameNotFoundException;

	/**
	 * @param systemId
	 * @return
	 */
	public abstract SchemaInfo resolveSystemId(String systemId) throws NameNotFoundException;
	
	/**
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public abstract InputStream getInputStream(URI uri) throws IOException;

}
