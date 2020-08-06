/**
 * Created on 29/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;


import java.util.Iterator;
import java.util.Set;



/**
 * @author gdefacci
 */
public abstract class DelegateXPathMapScope implements IXPathMapScope {
	
	protected abstract IXPathMapScope getDelegate();
	
	public String get(BaseXPathPosition pos) {
		return getDelegate().get(pos);
	}

	public IXPathMapScope getParent() {
		return getDelegate().getParent();
	}

	public int size() {
		return getDelegate().size();
	}
	
	public boolean isRoot() {
		return getDelegate().isRoot();
	}
	
	public Iterator entries() {
		return getDelegate().entries();
	}

	public String toString() {
		return getDelegate().toString();
	}

	public void define(BaseXPathPosition key) {
		getDelegate().define(key);
	}
	
	public void undefine(BaseXPathPosition key) {
		getDelegate().undefine(key);
	}

	public Set getDefitions() {
		return getDelegate().getDefitions();
	}

	public boolean isEmpty() {
		return getDelegate().isEmpty();
	}

	public IXPathMapScope view(XPathPositionPredicate pred) {
		return getDelegate().view(pred);
	}
	
}
