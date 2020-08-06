/**
 * Created on 30/gen/09
 */
package it.nch.eb.flatx.flatmodel.sax;

import it.nch.fwk.core.NamespacesInfos;

import java.io.Reader;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author gdefacci
 */
public class SaxUtils {
	
	public static final SaxUtils instance = new SaxUtils();
	
	private SaxUtils() {}
	
	public void saxParse(Reader reader, NamespacesInfos nss, ModelLoader elementHandler) {
		saxParse(reader, new SaxElementHandler(elementHandler, nss));
	}
	public void saxParse(Reader reader, ModelLoader elementHandler) {
		saxParse(reader, new SaxElementHandler(elementHandler));
	}
	
	public void saxParse(Reader reader, DefaultHandler dsh) {
		try {
			XMLReader xmlReader = XMLReaderFactory.createXMLReader();
			xmlReader.setContentHandler(dsh);
			xmlReader.setErrorHandler(dsh);
			
			xmlReader.parse(new InputSource(reader));
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
}
