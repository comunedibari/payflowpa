/**
 * Created on 07/ago/2008
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.sax.SaxTextCollector;
import it.nch.eb.flatx.flatmodel.xpath.ChildAwareXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathBuilder;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import java.io.PrintStream;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


/**
 * @author gdefacci
 */
public class WriterSaxElementHandler extends DefaultHandler {
	
	private final PrintStream printer;
	private final IXPathBuilder  xpathbBuilder;
	private SaxTextCollector textCollector = null;
	private final ElementPrinterEffectsContainer elmentPrinterEffects;
	
	private final Stack/*<XPathPosition>*/ activationStack = new Stack();
	private boolean active = true;
	
	public WriterSaxElementHandler(ElementPrinterEffectsContainer efcts, PrintStream printer) {
		this(efcts, printer, new ChildAwareXPathBuilder());
	}
	
	public WriterSaxElementHandler(ElementPrinterEffectsContainer efcts, PrintStream printer, 
			IXPathBuilder  xpp) {
		this.printer = printer;
		this.xpathbBuilder = xpp;
		this.elmentPrinterEffects = efcts;
	}


	public void startElement(String uri, String localName, String qname, Attributes attributes) throws SAXException {
		String prfx = getPrefix(qname);
		xpathbBuilder.pathSegment(prfx, localName);
		
		XPathPosition pos = xpathbBuilder.getPosition();

		if (active) {
			StartElementResult starElemResult = elmentPrinterEffects.start(pos, attributes);
			printer.print( starElemResult.getContent() );
			if (!starElemResult.isVisitChildren()) {
				this.activationStack.push(pos);
				this.active = false;
			} else {
				this.textCollector = new SaxTextCollector(this.textCollector);
			}
		}
	}

	private String getPrefix(String qname) {
		int colonIdx = qname.indexOf(":");
		if (colonIdx > 0) {
			return qname.substring(0, colonIdx);
		}
		return null;
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		if (this.active) {
			textCollector.append(ch, start, length);
		}
	}

	public void endElement(String uri, String localName, String qname) throws SAXException {
		XPathPosition pos = xpathbBuilder.getPosition();
		
		if (active) {
			String buffereString = textCollector.getBuffer().toString().trim();
			
			printer.print( elmentPrinterEffects.end(pos, buffereString) );
			textCollector = textCollector.getParent();
			
		} else {
			XPathPosition actvPos = (XPathPosition) this.activationStack.peek();
			if (pos.equals(actvPos)) {
				printer.print( elmentPrinterEffects.end(pos, "") );
				active = true;
			}
		}
		
		String prfx = getPrefix(qname);
		xpathbBuilder.pathSegmentEnd(prfx, localName);
						
	}

	protected IXPathBuilder getXpathbBuilder() {
		return xpathbBuilder;
	}

	protected SaxTextCollector getTextCollector() {
		return textCollector;
	}

}
