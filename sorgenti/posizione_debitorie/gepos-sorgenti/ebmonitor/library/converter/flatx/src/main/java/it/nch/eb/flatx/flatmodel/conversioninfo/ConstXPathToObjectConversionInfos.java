/**
 * 
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;


/**
 * @author gdefacci
 *
 */
public interface ConstXPathToObjectConversionInfos {

	class I extends ConstTypedIXPathToObjectConversionInfo {
		
		public I(String value) {
			this(Integer.valueOf(value));
		}
		
		public I(int value) {
			this(new Integer(value));
		}
		
		public I(Integer value) {
			super(Integer.class, value);
		}

		public String toString() {
			return "Int const(" + getValue() + ")";
		}
		
	}
	
	class L extends ConstTypedIXPathToObjectConversionInfo {
		
		public L(String value) {
			this(Long.valueOf(value));
		}
		
		public L(long value) {
			this(new Long(value));
		}
		
		public L(Long value) {
			super(Long.class, value);
		}
		
		public String toString() {
			return "Long const(" + getValue() + ")";
		}

	}
	
	class B extends ConstTypedIXPathToObjectConversionInfo {
		
		public B(String value) {
			this(Boolean.valueOf(value));
		}
		
		public B(boolean value) {
			this(Boolean.valueOf(value));
		}
		
		public B(Boolean value) {
			super(Boolean.class, value);
		}
		
		public String toString() {
			return "Boolean const(" + getValue() + ")";
		}

	}
	
	class S extends ConstTypedIXPathToObjectConversionInfo {
		
		public S(String value) {
			super(String.class, value);
		}
		
		public String toString() {
			return "String const(\"" + getValue() + "\")";
		}

	}
	
}
