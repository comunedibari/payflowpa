/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public class AttributeReplacer implements AttributeMatcher, AttributeDescriber {

	private final AttributeMatcher matcher;
	private final AttributeDescriber describer;
	public AttributeReplacer(AttributeMatcher matcher,
			AttributeDescriber describer) {
		this.matcher = matcher;
		this.describer = describer;
	}
	public boolean match(Attributes attrs, int i) {
		return matcher.match(attrs, i);
	}
	public String describeAttribute(Attributes attributes, int i) {
		return describer.describeAttribute(attributes, i);
	}
	
}