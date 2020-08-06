/**
 * Created on 29/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import java.util.Iterator;
import java.util.Set;


/**
 * @author gdefacci
 */
public interface IXPathMapScope {
	
	boolean isRoot();

	String get(BaseXPathPosition pos);

	IXPathMapScope getParent();

	int size();
	
	Iterator/*<Entry>*/ entries();
	
	/**
	 * states that the key 'key' will be preserved in this scope, even if collected in a child scope
	 */
	void define(BaseXPathPosition key);
	void undefine(BaseXPathPosition key);
	
	Set/*<BaseXPathPosition>*/ getDefitions();
	IXPathMapScope view(final XPathPositionPredicate pred);
	
	boolean isEmpty();
	
	static interface Entry {
		BaseXPathPosition getPosition();
		String getValue();
	}
}