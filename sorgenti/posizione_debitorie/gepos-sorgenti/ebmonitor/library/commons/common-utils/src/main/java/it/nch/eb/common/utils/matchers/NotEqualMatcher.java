/**
 * Created on 10/dic/07
 */
package it.nch.eb.common.utils.matchers;


/**
 * @author gdefacci
 */
public class NotEqualMatcher extends AbstractMatcher{ 

	public NotEqualMatcher(Object expecetedValue) {
		super( expecetedValue );
	}

	public boolean match(Object value) {
		if (value instanceof Matcher) {
			Matcher matcher = (Matcher) value;
			return !matcher.match(getExpecetedValue());
		}
		return !getExpecetedValue().equals(value);
	}
	
	public void describeTo(Description description) {
		description.append("not(").append(getExpecetedValue()).append(")");
	}

}
