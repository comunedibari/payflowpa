/**
 * Created on 29/gen/09
 */
package it.nch.eb.flatx.flatmodel.sax.splitter;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public class TextCollectorEffect {
	private StringBuffer sb = new StringBuffer();
	private final XPathPosition position;
	private FragmentHandler[]	handlers;
	
	public TextCollectorEffect(final XPathPosition pos, FragmentHandler[] handlers) {
		this.position = pos;
		this.handlers = handlers;
	}
	
	public XPathPosition getPosition() {
		return position;
	}

	public void start(String prefix, String name, Attributes attrs) {
		sb.append("<");
		if (prefix!=null && prefix.trim().length() > 0) {
			sb.append(prefix);
			sb.append(":");
		}
		sb.append(name);
		
		int len = attrs.getLength();
		for (int i=0; i<len; i++) {
			String text = attrs.getValue(i);
			sb.append(" ");
			sb.append(attrs.getQName(i));
			sb.append("='");
			sb.append(text);
			sb.append("'");
		}
		
		sb.append(">");
	}
	
	public void characters(char[] ch, int start, int length) {
		sb.append(ch, start, length);
	}
	
	public void endElement(String prefix, String localName) {
		sb.append("</");
		if (prefix!=null && prefix.trim().length() > 0) {
			sb.append(prefix);
			sb.append(":");
		}
		sb.append(localName);
		sb.append(">");
	}

	public void executeCallbacks() {
		String content = sb.toString();
		for (int i = 0; i < handlers.length; i++) {
			FragmentHandler hndlr = handlers[i];
			hndlr.onFragment(content);
		}
	}
	
}