/**
 * 
 */
package it.nch.fwk.xml.resolver;

import it.nch.eb.common.utils.loaders.ResourceLoaders;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.dom.DOMInputImpl;
import org.apache.xerces.jaxp.SAXParserFactoryImpl;
import org.apache.xerces.xni.XMLResourceIdentifier;
import org.apache.xerces.xni.XNIException;
import org.apache.xerces.xni.parser.XMLEntityResolver;
import org.apache.xerces.xni.parser.XMLInputSource;
import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.readers.OASISXMLCatalogReader;
import org.apache.xml.resolver.readers.SAXCatalogReader;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

/**
 * @author gedfr
 * @version $Header$
 *
 */
public class ExtendedXMLCatalogResolver implements XMLEntityResolver, EntityResolver2, LSResourceResolver {

	/** Internal catalog manager for Apache catalogs. **/
	private CatalogManager fResolverCatalogManager = null;

	/** Internal catalog structure. **/
	private ExtendedCatalog fCatalog = null;

	/** An array of catalog URIs. **/
	private String[] fCatalogsList = null;

	/** 
	 * Indicates whether the list of catalogs has
	 * changed since it was processed.
	 */
	private boolean fCatalogsChanged = true;

	/** Application specified prefer public setting. **/
	private boolean fPreferPublic = true;

	/** 
	 * Indicates whether the application desires that 
	 * the parser or some other component performing catalog
	 * resolution should use the literal system identifier
	 * instead of the expanded system identifier.
	 */
	private boolean fUseLiteralSystemId = true;

	/**
	 * <p>Constructs a catalog resolver with a default configuration.</p>
	 */
	public ExtendedXMLCatalogResolver() {
		this(null, true);
	}

	/**
	 * <p>Constructs a catalog resolver with the given
	 * list of entry files.</p>
	 * 
	 * @param catalogs an ordered array list of absolute URIs
	 */
	public ExtendedXMLCatalogResolver(String[] catalogs) {
		this(catalogs, true);
	}

	/**
	 * <p>Constructs a catalog resolver with the given
	 * list of entry files and the preference for whether
	 * system or public matches are preferred.</p>
	 * 
	 * @param catalogs an ordered array list of absolute URIs
	 * @param preferPublic the prefer public setting
	 */
	public ExtendedXMLCatalogResolver(String[] catalogs, boolean preferPublic) {
		init(catalogs, preferPublic);
	}

	/**
	 * <p>Returns the initial list of catalog entry files.</p>
	 * 
	 * @return the initial list of catalog entry files
	 */
	public final synchronized String[] getCatalogList() {
		return (fCatalogsList != null) ? (String[]) fCatalogsList.clone() : null;
	}

	/**
	 * <p>Sets the initial list of catalog entry files.
	 * If there were any catalog mappings cached from 
	 * the previous list they will be replaced by catalog 
	 * mappings from the new list the next time the catalog
	 * is queried.</p>
	 * 
	 * @param catalogs an ordered array list of absolute URIs 
	 */
	public final synchronized void setCatalogList(String[] catalogs) {
		fCatalogsChanged = true;
		fCatalogsList = (catalogs != null) ? (String[]) catalogs.clone() : null;
	}

	/**
	 * <p>Forces the cache of catalog mappings to be cleared.</p>
	 */
	public final synchronized void clear() {
		fCatalog = null;
	}

	/**
	 * Initialization. Create a CatalogManager and set all 
	 * the properties upfront. This prevents JVM wide system properties 
	 * or a property file somewhere in the environment from affecting 
	 * the behaviour of this catalog resolver.
	 */
	private void init(String[] catalogs, boolean preferPublic) {
		fCatalogsList = (catalogs != null) ? (String[]) catalogs.clone() : null;
		fPreferPublic = preferPublic;
		fResolverCatalogManager = new CatalogManager();
		fResolverCatalogManager.setAllowOasisXMLCatalogPI(false);
		fResolverCatalogManager.setCatalogClassName("it.nch.fwk.xml.resolver.ExtendedCatalog");
		fResolverCatalogManager.setCatalogFiles("");
		fResolverCatalogManager.setIgnoreMissingProperties(true);
		fResolverCatalogManager.setPreferPublic(fPreferPublic);
		fResolverCatalogManager.setRelativeCatalogs(false);
		fResolverCatalogManager.setUseStaticCatalog(false);
		fResolverCatalogManager.setVerbosity(0);
	}

	/**
	 * <p>Returns the preference for whether system or public
	 * matches are preferred. This is used in the absence
	 * of any occurence of the <code>prefer</code> attribute
	 * on the <code>catalog</code> entry of a catalog. If this
	 * property has not yet been explicitly set its value is
	 * <code>true</code>.</p>
	 * 
	 * @return the prefer public setting
	 */
	public final boolean getPreferPublic() {
		return fPreferPublic;
	}

	/**
	 * <p>Sets the preference for whether system or public
	 * matches are preferred. This is used in the absence
	 * of any occurence of the <code>prefer</code> attribute
	 * on the <code>catalog</code> entry of a catalog.</p>
	 * 
	 * @param preferPublic the prefer public setting
	 */
	public final void setPreferPublic(boolean preferPublic) {
		fPreferPublic = preferPublic;
		fResolverCatalogManager.setPreferPublic(preferPublic);
	}

	/**
	 * <p>Returns the preference for whether the literal system 
	 * identifier should be used when resolving system 
	 * identifiers when both it and the expanded system 
	 * identifier are available. If this property has not yet 
	 * been explicitly set its value is <code>true</code>.</p>
	 * 
	 * @return the preference for using literal system identifers
	 * for catalog resolution
	 * 
	 * @see #setUseLiteralSystemId
	 */
	public final boolean getUseLiteralSystemId() {
		return fUseLiteralSystemId;
	}

	/**
	 * <p>Sets the preference for whether the literal system 
	 * identifier should be used when resolving system 
	 * identifiers when both it and the expanded system 
	 * identifier are available.</p>
	 * 
	 * <p>The literal system identifier is the URI as it was
	 * provided before absolutization. It may be embedded within 
	 * an entity. It may be provided externally or it may be the 
	 * result of redirection. For example, redirection may 
	 * have come from the protocol level through HTTP or from 
	 * an application's entity resolver.</p>
	 * 
	 * <p>The expanded system identifier is an absolute URI 
	 * which is the result of resolving the literal system 
	 * identifier against a base URI.</p>
	 * 
	 * @param useLiteralSystemId the preference for using 
	 * literal system identifers for catalog resolution
	 */
	public final void setUseLiteralSystemId(boolean useLiteralSystemId) {
		fUseLiteralSystemId = useLiteralSystemId;
	}

	/**
	 * <p>Resolves an external entity. If the entity cannot be
	 * resolved, this method should return <code>null</code>. This
	 * method returns an input source if an entry was found in the
	 * catalog for the given external identifier. It should be
	 * overrided if other behaviour is required.</p>
	 * 
	 * @param publicId the public identifier, or <code>null</code> if none was supplied
	 * @param systemId the system identifier
	 * 
	 * @throws SAXException any SAX exception, possibly wrapping another exception
	 * @throws IOException thrown if some i/o error occurs
	 */
	public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
		String resolvedId = null;
		java.net.URI systemIdUri = URI.create(systemId);
		
		if(!systemIdUri.isAbsolute()) {
			systemId = systemId.replace('\\', '/');
			int pos = -1;
			if((pos = systemId.lastIndexOf('/')) > -1) {
				systemId = systemId.substring(pos+1);
			}
		}
		
		
		if (publicId != null && systemId != null) {
			resolvedId = resolvePublic(publicId, systemId);
		} else if (systemId != null) {
			resolvedId = resolveSystem(systemId);
		}
		
		if (resolvedId != null) {
			InputSource source = null;
			java.net.URI resolvedIdUri = URI.create(resolvedId);
			if(resolvedIdUri.isAbsolute()) {
				if("classpath".equals(resolvedIdUri.getScheme())) {
					source = new InputSource(getClass().getResourceAsStream(resolvedIdUri.getPath()));
				} else {
					source = new InputSource(resolvedId);
				}
			} else {
				source = new InputSource(resolvedId);
			}
			
			source.setPublicId(publicId);
			source.setSystemId('/'+systemId);
			return source;
		}
		return null;
	}

	/**
	 * <p>Resolves an external entity. If the entity cannot be
	 * resolved, this method should return <code>null</code>. This
	 * method returns an input source if an entry was found in the
	 * catalog for the given external identifier. It should be
	 * overrided if other behaviour is required.</p>
	 * 
	 * @param name the identifier of the external entity 
	 * @param publicId the public identifier, or <code>null</code> if none was supplied
	 * @param baseURI the URI with respect to which relative systemIDs are interpreted.
	 * @param systemId the system identifier
	 * 
	 * @throws SAXException any SAX exception, possibly wrapping another exception
	 * @throws IOException thrown if some i/o error occurs
	 */
	public InputSource resolveEntity(String name, String publicId, String baseURI, String systemId) throws SAXException,
			IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>Locates an external subset for documents which do not explicitly
	 * provide one. This method always returns <code>null</code>. It
	 * should be overrided if other behaviour is required.</p>
	 * 
	 * @param name the identifier of the document root element 
	 * @param baseURI the document's base URI
	 * 
	 * @throws SAXException any SAX exception, possibly wrapping another exception
	 * @throws IOException thrown if some i/o error occurs
	 */
	public InputSource getExternalSubset(String name, String baseURI) throws SAXException, IOException {
		return null;
	}

	/** 
	 * <p>Resolves a resource using the catalog. This method interprets that 
	 * the namespace URI corresponds to uri entries in the catalog.
	 * Where both a namespace and an external identifier exist, the namespace
	 * takes precedence.</p>
	 * 
	 * @param type the type of the resource being resolved
	 * @param namespaceURI the namespace of the resource being resolved, 
	 * or <code>null</code> if none was supplied
	 * @param publicId the public identifier of the resource being resolved,
	 * or <code>null</code> if none was supplied
	 * @param systemId the system identifier of the resource being resolved,
	 * or <code>null</code> if none was supplied
	 * @param baseURI the absolute base URI of the resource being parsed, 
	 * or <code>null</code> if there is no base URI
	 */
	public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {
		String resolvedId = null;

		try {
			// The namespace is useful for resolving namespace aware
			// grammars such as XML schema. Let it take precedence over
			// the external identifier if one exists.
			if (namespaceURI != null) {
				resolvedId = resolveURI(namespaceURI);
			}

			if (!getUseLiteralSystemId() && baseURI != null) {
				// Attempt to resolve the system identifier against the base URI.
				try {
					org.apache.xerces.util.URI uri = new org.apache.xerces.util.URI(new org.apache.xerces.util.URI(baseURI),
							systemId);
					systemId = uri.toString();
				}
				// Ignore the exception. Fallback to the literal system identifier.
				catch (org.apache.xerces.util.URI.MalformedURIException ex) {
				}
			}

			// Resolve against an external identifier if one exists. This
			// is useful for resolving DTD external subsets and other 
			// external entities. For XML schemas if there was no namespace 
			// mapping we might be able to resolve a system identifier 
			// specified as a location hint.
			if (resolvedId == null) {
				if (publicId != null && systemId != null) {
					resolvedId = resolvePublic(publicId, systemId);
				} else if (systemId != null) {
					resolvedId = resolveSystem(systemId);
				}
			}
		}
		// Ignore IOException. It cannot be thrown from this method.
		catch (IOException ex) {
		}

		// Needed to manage classpath scheme
		if (resolvedId != null) {
			java.net.URI resolvedIdUri = URI.create(resolvedId);
			try {
				if (resolvedIdUri.isAbsolute()) {
					if ("classpath".equals(resolvedIdUri.getScheme())) {
//						resolvedId = getClass().getResource(resolvedIdUri.getPath()).toString();
						String str = resolvedIdUri.toString();
						String resNm = resolvedIdUri.toString().substring("classpath:".length());
						InputStream is = ResourceLoaders.CLASSPATH.loadInputStream(resNm);
						if (is==null) {
							throw new NullPointerException("cant find a stream for " + resNm);
						}
						return new DOMInputImpl(publicId, publicId, resolvedIdUri.toString(), is, "UTF-8");
//						resolvedId = resolvedIdUri.toString().substring("classpath:".length());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("error resoving namespace uri " + namespaceURI
						+ " with path " + resolvedIdUri.getPath());
			}
		}

		if (resolvedId != null) {
			return new DOMInputImpl(publicId, resolvedId, baseURI);
		}
		return null;
	}

	/**
	 * <p>Resolves an external entity. If the entity cannot be
	 * resolved, this method should return <code>null</code>. This
	 * method only calls <code>resolveIdentifier</code> and returns
	 * an input source if an entry was found in the catalog. It
	 * should be overrided if other behaviour is required.</p>
	 *
	 * @param resourceIdentifier location of the XML resource to resolve
	 *
	 * @throws XNIException thrown on general error
	 * @throws IOException thrown if some i/o error occurs 
	 */
	public XMLInputSource resolveEntity(XMLResourceIdentifier resourceIdentifier) throws XNIException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * <p>Returns the URI mapping in the catalog for the given
	 * external identifier or <code>null</code> if no mapping
	 * exists. Public identifiers are normalized before
	 * comparison.</p>
	 * 
	 * @param publicId the public identifier to locate in the catalog
	 * @param systemId the system identifier to locate in the catalog
	 * 
	 * @return the mapped URI or <code>null</code> if no mapping
	 * was found in the catalog
	 * 
	 * @throws IOException if an i/o error occurred while reading
	 * the catalog
	 */
	public final synchronized String resolvePublic(String publicId, String systemId) throws IOException {

		if (fCatalogsChanged) {
			parseCatalogs();
			fCatalogsChanged = false;
		}
		return (fCatalog != null) ? fCatalog.resolvePublic(publicId, systemId) : null;
	}

	/**
	 * <p>Returns the URI mapping in the catalog for the given URI
	 * reference or <code>null</code> if no mapping exists. 
	 * URI comparison is case sensitive. If the URI reference 
	 * is an URN in the <code>publicid</code> namespace 
	 * it is converted into a public identifier by URN "unwrapping" 
	 * as specified in the XML Catalogs specification and then
	 * resolution is performed following the semantics of 
	 * external identifier resolution.</p>
	 * 
	 * @param uri the URI to locate in the catalog
	 * 
	 * @return the mapped URI or <code>null</code> if no mapping
	 * was found in the catalog
	 * 
	 * @throws IOException if an i/o error occurred while reading
	 * the catalog
	 */
	public final synchronized String resolveURI(String uri) throws IOException {

		if (fCatalogsChanged) {
			parseCatalogs();
			fCatalogsChanged = false;
		}
		return (fCatalog != null) ? fCatalog.resolveURI(uri) : null;
	}

	/**
	 * <p>Returns the URI mapping in the catalog for the given
	 * external identifier or <code>null</code> if no mapping
	 * exists. If the system identifier is an URN in the
	 * <code>publicid</code> namespace it is converted into
	 * a public identifier by URN "unwrapping" as specified
	 * in the XML Catalogs specification.</p>
	 * 
	 * @param systemId the system identifier to locate in the catalog
	 * 
	 * @return the mapped URI or <code>null</code> if no mapping
	 * was found in the catalog
	 * 
	 * @throws IOException if an i/o error occurred while reading
	 * the catalog
	 */
	public final synchronized String resolveSystem(String systemId) throws IOException {

		if (fCatalogsChanged) {
			parseCatalogs();
			fCatalogsChanged = false;
		}
		return (fCatalog != null) ? fCatalog.resolveSystem(systemId) : null;
	}

	/**
	 * Instruct the <code>ExtendedCatalog</code> to parse each of the  
	 * catalogs in the list. Only the first catalog will actually be 
	 * parsed immediately. The others will be queued and read if 
	 * they are needed later.
	 */
	private void parseCatalogs() throws IOException {
		if (fCatalogsList != null) {
			fCatalog = new ExtendedCatalog(fResolverCatalogManager, getClass());
			attachReaderToCatalog(fCatalog);
			for (int i = 0; i < fCatalogsList.length; ++i) {
				String catalog = fCatalogsList[i];
				if (catalog != null && catalog.length() > 0) {
					fCatalog.parseCatalog(URI.create(catalog));
				}
			}
		} else {
			fCatalog = null;
		}
	}

	/**
	 * Attaches the reader to the catalog.
	 */
	private void attachReaderToCatalog(Catalog catalog) {
		SAXParserFactory spf = new SAXParserFactoryImpl();
		spf.setNamespaceAware(true);
		spf.setValidating(false);

		SAXCatalogReader saxReader = new SAXCatalogReader(spf);
		saxReader.setCatalogParser(OASISXMLCatalogReader.namespaceName, "catalog",
				"org.apache.xml.resolver.readers.OASISXMLCatalogReader");
		catalog.addReader("application/xml", saxReader);
	}

}
