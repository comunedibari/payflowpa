package it.nch.eb.flatx.flatmodel.sax.transform;

import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public class DefaultElementPrinterEffect implements ElementPrinterEffect {
	
	private int lastDepth;
	private AttributesDescriber attributesDescriber;
	
	public DefaultElementPrinterEffect() {
		this(new DefaultAttributesDescriber());
	}
	
	public DefaultElementPrinterEffect(AttributesDescriber attributesDescriber) {
		this.attributesDescriber = attributesDescriber;
	}

	public StartElementResult start(XPathPosition pos, Attributes attrs) {
		String attrStr = attributesDescriber.describeAttributes(attrs);
		return new StartElementResult( "\n" + ElementPrinterEffectsContainer.indentation(pos.getDepth()) + "<" + tagName(pos)  + attrStr + ">" );
	}

	public String end(XPathPosition pos, String content) {
		int newDepth = pos.getDepth();
		String prfx = ((newDepth < lastDepth) || (content.indexOf('\n') > -1) )? 
				("\n" + ElementPrinterEffectsContainer.indentation(pos.getDepth()) ): 
				""; 
		lastDepth = newDepth;
		StringBuffer sb = new StringBuffer(getContent(content));
		sb.append(prfx + "</" +  tagName(pos) + ">");
		return sb.toString();
	}

	protected String getContent(String content) {
		return content;
	}

	protected String tagName(XPathPosition pos) {
		String prfx = pos.getPrefix();
		return (prfx != null ? prfx + ":" : "" ) + pos.getName(); 
	}
}