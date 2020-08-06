/**
 * Created on 10/dic/07
 */
package it.nch.eb.common.utils.matchers;



/**
 * @author gdefacci
 */
public class NullMatcher implements Matcher {

	public String getDescription() {
		return "null";
	}

	public boolean match(Object value) {
		return value == null;
	}

	public void describeTo(Description description) {
		description.append(" is null ");
	}

}
