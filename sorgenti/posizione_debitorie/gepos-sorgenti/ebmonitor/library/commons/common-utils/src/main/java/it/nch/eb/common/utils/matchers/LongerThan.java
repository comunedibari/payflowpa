package it.nch.eb.common.utils.matchers;


public class LongerThan extends AbstractMatcher implements Matcher {

	final int len;
	
	public LongerThan(int len) {
		this.len = len;
	}

	public boolean match(Object value) {
		String str;
		if (value==null) return false;
		if (value instanceof String) {
			str = (String) value;
		} else {
			str = value.toString();
		}
		return str.length() >= len;
	}

	public void describeTo(Description description) {
		description.append(" longer than [").append(String.valueOf(len)).append("]");
	}

}
