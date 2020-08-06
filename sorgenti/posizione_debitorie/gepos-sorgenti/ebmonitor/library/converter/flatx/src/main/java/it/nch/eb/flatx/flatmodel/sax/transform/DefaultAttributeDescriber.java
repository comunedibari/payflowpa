/**
 * 25/set/2009
 */
package it.nch.eb.flatx.flatmodel.sax.transform;

import org.xml.sax.Attributes;

/**
 * @author gdefacci
 */
public class DefaultAttributeDescriber implements AttributeDescriber {

	public String describeAttribute(Attributes attributes, int i) {
		return describeAttributeName(attributes, i) + describeAttributeValue(attributeValue(attributes, i));
	}
	
	public AttributeDescriber and(final String rawString) {
		final DefaultAttributeDescriber outer = this;
		return new AttributeDescriber() {
			
			public String describeAttribute(Attributes attributes, int i) {
				return outer.describeAttribute(attributes, i) + " " + rawString + " ";
			}
		};
	}

	protected String attributeValue(Attributes attributes, int i) {
		return attributes.getValue(i);
	}

	public String describeAttributeName(Attributes attributes, int i) {
		return " " + attributeName(attributes, i) + "=";
	}

	protected String attributeName(Attributes attributes, int i) {
		return attributes.getQName(i);
	}

	public String describeAttributeValue(String value) {
		String qte = value.indexOf('"') < 0 ? "\"" : "'";
		return qte + value + qte;
	}
	
	public static class WithName extends DefaultAttributeDescriber {

		private String name;
		
		public WithName(String name) {
			this.name = name;
		}

		protected String attributeName(Attributes attributes, int i) {
			return name;
		}
	
	};
	
	public static class WithValue extends DefaultAttributeDescriber {

		private String value;
		
		public WithValue(String value) {
			this.value = value;
		}

		protected String attributeValue(Attributes attributes, int i) {
			return value;
		}

	};
	
	public static class With extends DefaultAttributeDescriber {
		
		private String name;
		private String value;
		
		public With(String name, String value) {
			this.name = name;
			this.value = value;
		}

		protected String attributeName(Attributes attributes, int i) {
			return name;
		}

		protected String attributeValue(Attributes attributes, int i) {
			return value;
		}
		
	}

}
