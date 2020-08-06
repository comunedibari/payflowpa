/**
 * Created on 23/nov/07
 */
package it.nch.fwk.checks.jaxen;

import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.IteratorsFactory;
import it.nch.fwk.checks.xom.AbstractReadOnlyLookaheadIterator;
import it.nch.fwk.core.NamespacesInfos;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;


/**
 * @author gdefacci
 */
public abstract class JaxenIteratorFactory implements IteratorsFactory {
	
	private NamespacesInfos nss;
	
	public JaxenIteratorFactory(NamespacesInfos nss) {
		this.nss = nss;
	}
	
	/**
	 * the info about prefixes used in xpath xpressions
	 */
	public NamespacesInfos getNamespaces() {
		return nss;
	}

	protected abstract Navigator getNavigator();
	protected abstract Object getElement(IElementCursor root);
	public abstract IElementCursor createElementCursor(Object object);

	public Iterator getIterator(String name, IElementCursor root) {
		Iterator iterator = null;
		try {
			if (name.equals(descendantOrSelf)) {
				Object element = getElement(root);
				Iterator jaxenIterator = getNavigator().getDescendantOrSelfAxisIterator(element);
				return new InternalElementCursorIterator(this, jaxenIterator);
			}
		} catch (UnsupportedAxisException e) {
			throw new RuntimeException(e);
		}
		return iterator;
	}
	
	public Iterator xpathIterator(IElementCursor root, String xpath) {
		try {
			XPath path = createXPath(xpath);
			if (this.nss!=null) {
				Map map = nss.getPrefixesMap();
				path.setNamespaceContext( new SimpleNamespaceContext(map));
			}
			Object elem = getElement(root);
			List nodes = path.selectNodes(elem);
			return new InternalElementCursorIterator(this, nodes.iterator());
		} catch (JaxenException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected abstract XPath createXPath(String xpath) throws JaxenException;

	protected abstract boolean isElement(Object obj);

	private static final class InternalElementCursorIterator extends AbstractReadOnlyLookaheadIterator {
		
		final JaxenIteratorFactory iteratorFactory;
		final Iterator jaxenIterator;

		public InternalElementCursorIterator(JaxenIteratorFactory iteratorFactory, Iterator jaxenIterator) {
			this.iteratorFactory = iteratorFactory;
			this.jaxenIterator = jaxenIterator;
			findFirst();
		}

		protected Object findNext() {
			if (jaxenIterator.hasNext()) {
				Object object = jaxenIterator.next();
				if (object!=null) { 
					if (iteratorFactory.isElement(object)) return iteratorFactory.createElementCursor(object);
					else return findNext();
				}
			}
			return null;
		}
		
	}	

}
