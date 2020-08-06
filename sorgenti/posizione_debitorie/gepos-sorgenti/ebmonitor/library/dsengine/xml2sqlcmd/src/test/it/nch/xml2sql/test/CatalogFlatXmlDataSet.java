/**
 * 04/ago/2009
 */
package it.nch.xml2sql.test;

import it.nch.fwk.xml.resolver.ExtendedXMLCatalogResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;

import javax.xml.XMLConstants;

import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.w3c.dom.ls.LSInput;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author gdefacci
 */
public class CatalogFlatXmlDataSet extends CachedDataSet {
	
	public CatalogFlatXmlDataSet(String catalog, Reader xmlReader, boolean dtdMetadata)
		throws IOException, DataSetException {
		super(new CatalogFlatXmlProducer(new String[] { catalog }, new InputSource(xmlReader), dtdMetadata));
	}
	
	public CatalogFlatXmlDataSet(String[] catalogs, Reader xmlReader, boolean dtdMetadata)
			throws IOException, DataSetException {
		super(new CatalogFlatXmlProducer(catalogs, new InputSource(xmlReader), dtdMetadata));
	}

	static class CatalogFlatXmlProducer extends FlatXmlProducer {

		private ExtendedXMLCatalogResolver resourceResolver;

		public CatalogFlatXmlProducer(String[] catalogs, InputSource xmlSource, boolean dtdMetadata) {
			super(xmlSource, dtdMetadata);
			resourceResolver = new ExtendedXMLCatalogResolver(catalogs);
		}

		public InputSource resolveEntity(String publicId, String systemId)
				throws SAXException {
			LSInput lsinp = resourceResolver.resolveResource(XMLConstants.DEFAULT_NS_PREFIX, systemId, null, null, null);
			try {
				InputStream is = lsinp.getByteStream() == null ? 
						new URL(lsinp.getSystemId()).openStream() :
						lsinp.getByteStream();
				InputSource res = new InputSource(is);
				res.setPublicId(lsinp.getPublicId());
				res.setPublicId(lsinp.getSystemId());
				return res;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	

}
