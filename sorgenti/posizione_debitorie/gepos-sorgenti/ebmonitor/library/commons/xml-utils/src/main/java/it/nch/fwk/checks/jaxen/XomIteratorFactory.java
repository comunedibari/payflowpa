/**
 * Created on 23/nov/07
 */
package it.nch.fwk.checks.jaxen;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.xom.ElementCursor;
import it.nch.fwk.core.NamespacesInfos;
import nu.xom.Element;

import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.XPath;
import org.jaxen.xom.DocumentNavigator;
import org.jaxen.xom.XOMXPath;


/**
 * @author gdefacci
 */
public class XomIteratorFactory extends JaxenIteratorFactory {
	
	public XomIteratorFactory(NamespacesInfos nss) {
		super(nss);
	}

	protected Navigator getNavigator() {
		return new DocumentNavigator();
	}
	
	protected boolean isElement(Object obj) {
		return obj instanceof Element;
	}
	
	protected XPath createXPath(String xpath) throws JaxenException {
		return new XOMXPath(xpath);
	}

	public IElementCursor createElementCursor(Object object) {
		if (!(object instanceof Element)) throw new ClassCastException();
		Element node = (Element)object;
//		FIXME: should also provide the index and the nss
		return new ElementCursor(node, getNamespaces());
	}

	protected Object getElement(IElementCursor root) {
		if (root==null) return null;
		ElementCursor xomElemCursor = (ElementCursor) root;
		return xomElemCursor.currentElement();
	}

	

}
