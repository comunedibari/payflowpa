/**
 * Created on 10/dic/07
 */
package it.nch.eb.common.utils.matchers;


/**
 * concept stolen from hamcrest 
 * @author gdefacci
 */
public interface Matcher extends SelfDescribing {
	boolean match(Object value);
	public String getDescription(); // providing only describeTo is a little silly 
}
