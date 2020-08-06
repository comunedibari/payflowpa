/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public class AttributesReplacer implements AttributeDescriber, AttributesDescriber {
	
	private final AttributeDescriber defaultAttributeDescriber;
	private final AttributeReplacer[] replacers;
	
	public AttributesReplacer(AttributeReplacer[] replacers) {
		this(new DefaultAttributeDescriber(), replacers);
	}
	
	public AttributesReplacer(AttributeDescriber defaultAttributeDescriber, AttributeReplacer[] replacers) {
		this.defaultAttributeDescriber = defaultAttributeDescriber;
		this.replacers = replacers;
	}
	
	private AttributeDescriber getAttributesDescriber(Attributes attrs, int i) {
		for (int j = 0; j < this.replacers.length; j++) {
			AttributeReplacer replacer = this.replacers[j];
			if (replacer.match(attrs, i)) return replacer;
		}
		return defaultAttributeDescriber;
	}

	public String describeAttribute(Attributes attributes, int i) {
		return getAttributesDescriber(attributes, i).describeAttribute(attributes, i);
	}

	public String describeAttributes(Attributes attributes) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < attributes.getLength(); i++) {
			sb.append(describeAttribute(attributes, i));
		}
		return sb.toString();
	}

}
