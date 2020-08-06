/**
 * Created on 10/dic/07
 */
package it.nch.eb.common.utils.matchers;



/**
 * @author gdefacci
 */
public class NumericMatcher extends AbstractMatcher implements Matcher{ 

	public boolean match(Object value) {
		if (!(value instanceof String)) return false;
		return  MatchersUtils.isDigits((String) value);
	}
	
	public void describeTo(Description description) {
		description.append(" is a number ");
	}

}
