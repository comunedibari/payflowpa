/**
 * 
 */
package it.nch.fwk.xml.resolver;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.xml.resolver.Catalog;
import org.apache.xml.resolver.CatalogException;
import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.readers.CatalogReader;
import org.springframework.util.ResourceUtils;

/**
 * @author gedfr
 * @version $Header$
 * 
 */
public class ExtendedCatalog extends Catalog {

	private static org.slf4j.Logger log = ResourcesUtil.createLogger(ExtendedCatalog.class);
	
	private ClassLoader classLoader;

	private Class clazz;

	/**
	 * 
	 */
	public ExtendedCatalog() {
	}

	/**
	 * @param manager
	 */
	public ExtendedCatalog(CatalogManager manager) {
		super(manager);
	}

	/**
	 * @param manager
	 * @param classLoader
	 */
	public ExtendedCatalog(CatalogManager manager, ClassLoader classLoader) {
		super(manager);
		this.classLoader = classLoader;
	}

	/**
	 * @param manager
	 * @param clazz
	 */
	public ExtendedCatalog(CatalogManager manager, Class clazz) {
		super(manager);
		this.clazz = clazz;
	}

	/**
	 * Parse a catalog file, augmenting internal data structures.
	 * 
	 * @param resourceUri
	 *            The filename of the catalog file to process
	 * 
	 * @throws MalformedURLException
	 *             The fileName cannot be turned into a valid URL.
	 * @throws IOException
	 *             Error reading catalog file.
	 */
	public synchronized void parseCatalog(java.net.URI resourceUri) throws MalformedURLException, IOException {

		default_override = catalogManager.getPreferPublic();
		catalogManager.debug.message(4, "Parse catalog: " + resourceUri);

		// Put the file into the list of catalogs to process...
		// In all cases except the case when initCatalog() is the
		// caller, this will be the only catalog initially in the list...
		catalogFiles.addElement(resourceUri);

		// Now process all the pending catalogs...
		parsePendingCatalogs();
	}

	protected synchronized void parseCatalogFile(String fileName) throws MalformedURLException, IOException, CatalogException {
		super.parseCatalogFile(fileName);
	}

//	protected synchronized void parseCatalogFile(java.net.URI resourceUri) throws Exception {
//		File file = ResourceUtils.getFile(resourceUri.toString());
//		if (file != null && file.exists()) {
//			parseCatalogFile(new FileInputStream(file));
//		} else {
//			throw new CatalogException(CatalogException.PARSE_FAILED);
//		}
//	}
	
	protected synchronized void parseCatalogFile(java.net.URI resourceUri) throws MalformedURLException, IOException,
			CatalogException {
		if (!resourceUri.isAbsolute()) {
			parseCatalog(resourceUri.getPath());
		} else {
			InputStream in = null;
			try {
				URL url = resourceUri.toURL();
				base = url;
				in = url.openStream();

				parseCatalogFile(in);
			} catch (MalformedURLException mue) {
				if ("classpath".equals(resourceUri.getScheme())) {
					try {

						String pth = resourceUri.toString();

						if (base == null) {
							base = getBaseUrl(pth);
							if (base != null) {
								in = getResource(pth);
							}
						}

					} catch (Exception e) {
						throw new RuntimeException(e);
					}
					if (in != null) {
						parseCatalogFile(in);
					} else {
						throw new CatalogException(CatalogException.PARSE_FAILED);
					}
				} else {
					throw mue;
				}
			}
		}
	}

	private InputStream getResource(String pth) {
		try {
			return ResourceUtils.getURL(pth).openStream();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private URL getBaseUrl(String pth) {
		int bpthIdx = pth.lastIndexOf("/");
		try {
			return ResourceUtils.getURL(pth.substring(0, bpthIdx));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected synchronized void parseCatalogFile(InputStream source) throws IOException {
		DataInputStream inStream = null;
		boolean parsed = false;

		for (int count = 0; !parsed && count < readerArr.size(); count++) {
			CatalogReader reader = (CatalogReader) readerArr.get(count);
//			CatalogReader catReader = 
//				new XCatalogReader(SAXParserFactory.newInstance());
			
			inStream = new DataInputStream((source instanceof BufferedInputStream) ? source : new BufferedInputStream(source));
			try {
				reader.readCatalog(this, inStream);
				parsed = true;
			} catch (CatalogException ce) {
				ce.printStackTrace();
				if (ce.getExceptionType() == CatalogException.PARSE_FAILED) {
					// give up!
					break;
				} else {
					// try again!
				}
			}

			try {
				inStream.close();
			} catch (IOException e) {
				// nop
			}
		}

		if (!parsed) {
			catalogManager.debug.message(1, "Failed to parse catalog");
		}
	}

	/**
	 * Parse all of the pending catalogs.
	 * 
	 * <p>
	 * Catalogs may refer to other catalogs, this method parses all of the
	 * currently pending catalog files.
	 * </p>
	 */
	protected synchronized void parsePendingCatalogs() throws MalformedURLException, IOException {

		if (!localCatalogFiles.isEmpty()) {
			// Move all the localCatalogFiles into the front of
			// the catalogFiles queue
			Vector newQueue = new Vector();
			Enumeration q = localCatalogFiles.elements();
			while (q.hasMoreElements()) {
				newQueue.addElement(q.nextElement());
			}

			// Put the rest of the catalogs on the end of the new list
			for (int curCat = 0; curCat < catalogFiles.size(); curCat++) {
				Object element = catalogFiles.elementAt(curCat);
				newQueue.addElement(element);
			}

			catalogFiles = newQueue;
			localCatalogFiles.clear();
		}

		// Suppose there are no catalog files to process, but the
		// single catalog already parsed included some delegate
		// entries? Make sure they don't get lost.
		if (catalogFiles.isEmpty() && !localDelegate.isEmpty()) {
			Enumeration e = localDelegate.elements();
			while (e.hasMoreElements()) {
				catalogEntries.addElement(e.nextElement());
			}
			localDelegate.clear();
		}

		// Now process all the files on the catalogFiles vector. This
		// vector can grow during processing if CATALOG entries are
		// encountered in the catalog
		while (!catalogFiles.isEmpty()) {
			Object element = catalogFiles.elementAt(0);
			try {
				catalogFiles.remove(0);
			} catch (ArrayIndexOutOfBoundsException e) {
				// can't happen
			}

			if (catalogEntries.size() == 0 && catalogs.size() == 0) {
				// We haven't parsed any catalogs yet, let this
				// catalog be the first...
				try {
					if (element instanceof java.net.URI) {
						parseCatalogFile((java.net.URI) element);
					} else if (element instanceof String) {
						parseCatalogFile((String) element);
					}
				} catch (Exception ce) {
					ce.printStackTrace();
					log.error("FIXME: " + ce.toString());
				}
			} else {
				// This is a subordinate catalog. We save its name,
				// but don't bother to load it unless it's necessary.
				catalogs.addElement(element);
			}

			if (!localCatalogFiles.isEmpty()) {
				// Move all the localCatalogFiles into the front of
				// the catalogFiles queue
				Vector newQueue = new Vector();
				Enumeration q = localCatalogFiles.elements();
				while (q.hasMoreElements()) {
					newQueue.addElement(q.nextElement());
				}

				// Put the rest of the catalogs on the end of the new list
				for (int curCat = 0; curCat < catalogFiles.size(); curCat++) {
					element = catalogFiles.elementAt(curCat);
					newQueue.addElement(element);
				}

				catalogFiles = newQueue;
				localCatalogFiles.clear();
			}

			if (!localDelegate.isEmpty()) {
				Enumeration e = localDelegate.elements();
				while (e.hasMoreElements()) {
					catalogEntries.addElement(e.nextElement());
				}
				localDelegate.clear();
			}
		}

		// We've parsed them all, reinit the vector...
		catalogFiles.clear();
	}

	/**
	 * Search the subordinate catalogs, in order, looking for a match.
	 * 
	 * <p>
	 * This method searches the Catalog and returns the system identifier
	 * specified for the given entity type with the given name, public, and
	 * system identifiers. In some contexts, these may be null.
	 * </p>
	 * 
	 * @param entityType
	 *            The CatalogEntry type for which this query is being conducted.
	 *            This is necessary in order to do the approprate query on a
	 *            subordinate catalog.
	 * @param entityName
	 *            The name of the entity being searched for, if appropriate.
	 * @param publicId
	 *            The public identifier of the entity in question (as provided
	 *            in the source document).
	 * @param systemId
	 *            The nominal system identifier for the entity in question (as
	 *            provided in the source document). This parameter is overloaded
	 *            for the URI entry type.
	 * 
	 * @throws MalformedURLException
	 *             The formal system identifier of a delegated catalog cannot be
	 *             turned into a valid URL.
	 * @throws IOException
	 *             Error reading delegated catalog file.
	 * 
	 * @return The system identifier to use. Note that the nominal system
	 *         identifier is not returned if a match is not found in the
	 *         catalog, instead null is returned to indicate that no match was
	 *         found.
	 */
	protected synchronized String resolveSubordinateCatalogs(int entityType, String entityName, String publicId, String systemId)
			throws MalformedURLException, IOException {

		for (int catPos = 0; catPos < catalogs.size(); catPos++) {
			Catalog c = null;

			try {
				c = (Catalog) catalogs.elementAt(catPos);
			} catch (ClassCastException e) {
				Object element = catalogs.elementAt(catPos);
				c = newCatalog();

				try {
					if (element instanceof java.net.URI && c instanceof ExtendedCatalog) {
						((ExtendedCatalog) c).parseCatalog((java.net.URI) element);
					} else if (element instanceof String) {
						c.parseCatalog((String) element);
					}
				} catch (MalformedURLException mue) {
					catalogManager.debug.message(1, "Malformed Catalog URL", element.toString());
				} catch (FileNotFoundException fnfe) {
					catalogManager.debug.message(1, "Failed to load catalog, file not found", element.toString());
				} catch (IOException ioe) {
					catalogManager.debug.message(1, "Failed to load catalog, I/O error", element.toString());
				}

				catalogs.setElementAt(c, catPos);
			}

			String resolved = null;

			// Ok, now what are we supposed to call here?
			if (entityType == DOCTYPE) {
				resolved = c.resolveDoctype(entityName, publicId, systemId);
			} else if (entityType == DOCUMENT) {
				resolved = c.resolveDocument();
			} else if (entityType == ENTITY) {
				resolved = c.resolveEntity(entityName, publicId, systemId);
			} else if (entityType == NOTATION) {
				resolved = c.resolveNotation(entityName, publicId, systemId);
			} else if (entityType == PUBLIC) {
				resolved = c.resolvePublic(publicId, systemId);
			} else if (entityType == SYSTEM) {
				resolved = c.resolveSystem(systemId);
			} else if (entityType == URI) {
				resolved = c.resolveURI(systemId);
			}

			if (resolved != null) {
				return resolved;
			}
		}

		return null;
	}

	/**
	 * Return the thread context class loader if available. Otherwise return
	 * null.
	 * 
	 * The thread context class loader is available for JDK 1.2 or later, if
	 * certain security conditions are met.
	 * 
	 * @exception RuntimeException
	 *                if a suitable class loader cannot be identified.
	 */
	protected static ClassLoader getContextClassLoader() throws RuntimeException {
		ClassLoader classLoader = null;

		try {
			// Are we running on a JDK 1.2 or later system?
			Method method = Thread.class.getMethod("getContextClassLoader", null);

			// Get the thread context class loader (if there is one)
			try {
				classLoader = (ClassLoader) method.invoke(Thread.currentThread(), null);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Unexpected IllegalAccessException", e);
			} catch (InvocationTargetException e) {
				/**
				 * InvocationTargetException is thrown by 'invoke' when the
				 * method being invoked (getContextClassLoader) throws an
				 * exception.
				 * 
				 * getContextClassLoader() throws SecurityException when the
				 * context class loader isn't an ancestor of the calling class's
				 * class loader, or if security permissions are restricted.
				 * 
				 * In the first case (not related), we want to ignore and keep
				 * going. We cannot help but also ignore the second with the
				 * logic below, but other calls elsewhere (to obtain a class
				 * loader) will trigger this exception where we can make a
				 * distinction.
				 */
				if (e.getTargetException() instanceof SecurityException) {
					; // ignore
				} else {
					// Capture 'e.getTargetException()' exception for details
					// alternate: log 'e.getTargetException()', and pass back
					// 'e'.
					throw new RuntimeException("Unexpected InvocationTargetException", e.getTargetException());
				}
			}
		} catch (NoSuchMethodException e) {
			// Assume we are running on JDK 1.1
			classLoader = ExtendedCatalog.class.getClassLoader();
		}

		// Return the selected class loader
		return classLoader;
	}

	/**
	 * Create a new Catalog object.
	 *
	 * <p>This method constructs a new instance of the running Catalog
	 * class (which might be a subtype of org.apache.xml.resolver.Catalog).
	 * All new catalogs are managed by the same CatalogManager.
	 * </p>
	 *
	 * <p>N.B. All Catalog subtypes should call newCatalog() to construct
	 * a new Catalog. Do not simply use "new Subclass()" since that will
	 * confuse future subclasses.</p>
	 */
	protected Catalog newCatalog() {
		Catalog catalog = super.newCatalog();
		if(catalog instanceof ExtendedCatalog) {
			if(this.classLoader != null) {
				((ExtendedCatalog)catalog).setClassLoader(this.classLoader);
			}
			if(this.clazz != null) {
				((ExtendedCatalog)catalog).setClazz(this.clazz);
			}
		}
		
		return catalog;
	}

	/**
	 * @return the classLoader
	 */
	protected ClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * @param classLoader the classLoader to set
	 */
	protected void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	/**
	 * @return the clazz
	 */
	protected Class getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	protected void setClazz(Class clazz) {
		this.clazz = clazz;
	}

}
