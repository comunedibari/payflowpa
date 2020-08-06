/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public class DefaultAttributesDescriber implements AttributesDescriber {
	
	private final AttributeDescriber defaultAttributeDescriber ;
	
	public DefaultAttributesDescriber() {
		this(new DefaultAttributeDescriber());
	}

	public DefaultAttributesDescriber(
			AttributeDescriber defaultAttributeDescriber) {
		this.defaultAttributeDescriber = defaultAttributeDescriber;
	}


	public String describeAttributes(Attributes attributes) {
		StringBuffer sb = new StringBuffer();
		for (int i=0; i < attributes.getLength(); i++) {
			sb.append(defaultAttributeDescriber.describeAttribute(attributes, i));
		}
		return sb.toString();
	}

}
