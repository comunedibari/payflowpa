/**
 * Created on 07/ago/2008
 */
package it.nch.eb.flatx.flatmodel.sax;

import java.io.Reader;

import it.nch.eb.flatx.flatmodel.xpath.AttributeXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.XPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.fwk.core.NamespacesInfos;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * @author gdefacci
 */
public class SaxElementHandler extends PrefixProviderSaxHandler {
	
//	private NamespacesInfos nss;
	private final ActivableLeafElementHandler modelLoader;
	
	private IXPathBuilder  xpathbBuilder;
	private SaxTextCollector textCollector = null;
	
	public SaxElementHandler(ActivableLeafElementHandler dh) {
		this( dh, null, new XPathBuilder());
	}
	
	public SaxElementHandler(ActivableLeafElementHandler dh, IXPathBuilder  xpp) {
		this( dh, null, xpp);
	}
	
	public SaxElementHandler(ActivableLeafElementHandler dh, NamespacesInfos nss) {
		this(dh, nss, new ChildAwareXPathBuilder());
	}
	
	public SaxElementHandler(ActivableLeafElementHandler delegateElementHandler, 
			NamespacesInfos nss, 
			IXPathBuilder  xpp) {
		super(nss);
		this.xpathbBuilder = xpp;
		this.modelLoader = delegateElementHandler;
	}


	public void startElement(String uri, String localName, String qname, Attributes attributes) throws SAXException {
		String prfx = getPrefix(uri);
		xpathbBuilder.pathSegment(prfx, localName);
		
		XPathPosition pos = xpathbBuilder.getPosition();
		
		doStartElement(pos, attributes);
	}

	protected void doStartElement(XPathPosition pos, Attributes attributes) {
		modelLoader.startElement(pos);	
		if (modelLoader.isActive()) {
			attributes(modelLoader, pos, attributes);
			this.textCollector = new SaxTextCollector(this.textCollector);
		}
	}

	protected void attributes(ElementHandler h, XPathPosition elemPos, Attributes attrs) {
		if (h instanceof LeafElementHandler) {
			LeafElementHandler hndlr = (LeafElementHandler) h;
			int len = attrs.getLength();
			for (int i=0; i<len; i++) {
				String text = attrs.getValue(i);
				AttributeXPathPosition pos = attributeXPathPosition(elemPos, attrs, i);
				hndlr.text(pos, text);		
			}
		}
	}

	protected AttributeXPathPosition attributeXPathPosition(XPathPosition elemPos,
			Attributes attrs, int i) {
		return new AttributeXPathPosition(elemPos, attrs.getQName(i));
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (modelLoader.isActive()) {
			textCollector.append(ch, start, length);
		}
	}

	public void endElement(String uri, String localName, String qname) throws SAXException {
		XPathPosition pos = xpathbBuilder.getPosition();
		
		if (modelLoader.isActive()) {
			String buffereString = textCollector.getBuffer().toString().trim();
			if (buffereString.length()>0) {
				modelLoader.text(pos, buffereString);
			} else {
				modelLoader.text(pos, "");
			}
			textCollector = textCollector.getParent();
		}
		
		String prfx = getPrefix(uri);
		xpathbBuilder.pathSegmentEnd(prfx, localName);

		modelLoader.endElement(pos);				
	}

	protected ActivableLeafElementHandler getModelLoader() {
		return modelLoader;
	}

	protected IXPathBuilder getXpathbBuilder() {
		return xpathbBuilder;
	}

	protected SaxTextCollector getTextCollector() {
		return textCollector;
	}
}
