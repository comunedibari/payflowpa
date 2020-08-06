/**
 * Created on 10/dic/07
 */
package it.nch.eb.common.utils.matchers;


/**
 * @author gdefacci
 */
public class EqualMatcher extends AbstractMatcher{ 

	public EqualMatcher(Object expecetedValue) {
		super( expecetedValue );
	}

	public boolean match(Object value) {
		return getExpecetedValue().equals(value);
	}

	public void describeTo(Description description) {
		description.append("equlas to [").append(this.getExpecetedValue()).append("]");
	}
	
}
