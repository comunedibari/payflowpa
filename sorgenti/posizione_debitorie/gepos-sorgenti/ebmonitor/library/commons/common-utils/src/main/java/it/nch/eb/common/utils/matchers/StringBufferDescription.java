/**
 * Created on 22/feb/08
 */
package it.nch.eb.common.utils.matchers;


/**
 * @author gdefacci
 */
public class StringBufferDescription implements Description {

	final StringBuffer sb = new StringBuffer();
	
	public Description append(SelfDescribing sf) {
		sf.describeTo(this);
		return this;
	}

	public Description append(String str) {
		sb.append(str);
		return this;
	}

	public Description append(Object obj) {
		sb.append(obj);
		return this;
	}

	public String toString() {
		return sb.toString();
	}
	
}
