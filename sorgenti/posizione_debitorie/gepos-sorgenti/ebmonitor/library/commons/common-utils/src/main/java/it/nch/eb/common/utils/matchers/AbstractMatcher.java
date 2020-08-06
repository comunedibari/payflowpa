/**
 * Created on 10/dic/07
 */
package it.nch.eb.common.utils.matchers;



/**
 * @author gdefacci
 */
public abstract class AbstractMatcher implements Matcher {
	
	private final Object expecetedValue;
	
	final static Object NULL_OBJECT = new Object();
	
	public AbstractMatcher() {
		expecetedValue = NULL_OBJECT;
	}
	
	public AbstractMatcher(Object expecetedValue) {
		this.expecetedValue = expecetedValue;
	}

	public Object getExpecetedValue() {
		if (expecetedValue==NULL_OBJECT) throw new IllegalStateException("cant invoke public Object getExpecetedValue() when using default constructor");
		return expecetedValue;
	}
	
	public String getDescription() {
		Description description = new StringBufferDescription();
		this.describeTo(description);
		return description.toString();
	}

}
