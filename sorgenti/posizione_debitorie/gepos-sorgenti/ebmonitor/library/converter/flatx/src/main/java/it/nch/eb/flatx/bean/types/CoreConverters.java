/**
 * Created on 14/lug/2008
 */
package it.nch.eb.flatx.bean.types;



/**
 * @author gdefacci
 */
public class CoreConverters {

	public static class StringIdentity implements SizedConverter {
		
		private static final long	serialVersionUID	= 4572381523631122187L;
		protected final String	value;
		
		/**
		 * construct a SizedConverter which always convert to value, ignoring provided to convert parameter
		 */
		public StringIdentity(String value) {
			this.value = value;
		}
	
		public String encode(String stringModel) {
			return value;
		}
	
		public final Integer getLength() {
			return new Integer(value.length());
		}
	
		public final String toString() {
			return "stringIdentityConverter(\'" + value  + "\')";
		}
		
	}

	public static final Converter IDENTITY = new Converter() {
	
		private static final long	serialVersionUID	= -6581155981946076291L;

		public String encode(String stringModel) {
			return stringModel;
		}
	
		public String toString() {
			return "identityConverter()";
		}
		
	};

}
