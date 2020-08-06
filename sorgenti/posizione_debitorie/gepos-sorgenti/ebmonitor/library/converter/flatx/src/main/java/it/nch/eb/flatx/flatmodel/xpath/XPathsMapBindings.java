/**
 * Created on 29/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;


import java.util.Iterator;
import java.util.Set;




/**
 * @author gdefacci
 */
public class XPathsMapBindings extends DelegateXPathMapScope implements WXPathMapScope {
	
	private WXPathMapScope delegate;

	public XPathsMapBindings() {
		this(new XPathsMapScope());
	}
	
	public XPathsMapBindings(Set/*<BaseXPathPosition>*/ topLevelDefinitions) {
		this(new XPathsMapScope());
		if (topLevelDefinitions!=null && !topLevelDefinitions.isEmpty()) {
			for (Iterator it = topLevelDefinitions.iterator(); it.hasNext();) {
				BaseXPathPosition pos = (BaseXPathPosition) it.next();
				define(pos);
			}
		}
	}
	
	public WXPathMapScope getWParent() {
		return (WXPathMapScope) getParent();
	}

	public void put(BaseXPathPosition pos, String textValue) {
		getWDelegate().put(pos, textValue);
	}
	
	protected XPathsMapBindings(WXPathMapScope delegate) {
		if (delegate==null) throw new NullPointerException("the delegate is null");
		this.delegate = delegate;
	}
	
	protected IXPathMapScope getDelegate() {
		return delegate;
	}
	
	protected WXPathMapScope getWDelegate() {
		return delegate;
	}
	
	public void enterScope() {
		this.delegate = new XPathsMapScope(delegate);
	}
	
	public void exitScope() {
		this.delegate = delegate.getWParent();
	}
	
}
