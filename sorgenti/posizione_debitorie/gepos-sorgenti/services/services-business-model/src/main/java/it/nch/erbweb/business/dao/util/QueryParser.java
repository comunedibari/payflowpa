package it.nch.erbweb.business.dao.util;

public interface QueryParser {
	String prepare(String target);
	PlaceHolder[] getInputs();
	
	public class PlaceHolder {
		private final String alias;
		private 	  Object value;
		
		PlaceHolder(String alias, Object value) {
			this.alias = alias;
			this.value = value;			
		}

		public String getAlias() {
			return alias;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(Object value) {
			this.value = value;
		}
		
		
	}

}
