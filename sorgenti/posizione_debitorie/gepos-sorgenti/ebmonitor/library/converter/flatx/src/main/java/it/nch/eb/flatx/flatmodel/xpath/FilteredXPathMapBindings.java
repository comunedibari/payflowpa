/**
 * Created on 30/mar/2009
 */
package it.nch.eb.flatx.flatmodel.xpath;

import it.nch.eb.common.utils.StringUtils;

import java.util.Iterator;

public final class FilteredXPathMapBindings extends DelegateXPathMapScope {

	private final XPathPositionPredicate	pred;
	private IXPathMapScope	delegate;

	FilteredXPathMapBindings(IXPathMapScope delegate, final XPathPositionPredicate ppred) {
		this.pred = new XPathPositionPredicate() {

			public boolean match(BaseXPathPosition pos) {
				return ppred.match(pos) || FilteredXPathMapBindings.this.getDefitions().contains(pos.getUnindexed());
			}
			
		};
		this.delegate = delegate;
	}

	protected IXPathMapScope getDelegate() {
		return delegate;
	}
	
	public String get(BaseXPathPosition pos) {
		if (pred.match(pos))
			return super.get(pos);
		else 
			return null;
	}

	public Iterator entries() {
		final Iterator superIt = super.entries();
		return new FilteredXPathEntriesIterator(pred, superIt);
	}
	
	public int size() {
		Iterator entrs = entries();
		int count = 0;
		while (entrs.hasNext()) {
			count ++;
			entrs.next();
		}
		return count;
	}
	
	public boolean isEmpty() {
		return !entries().hasNext();
	}

	public String toString() {
		return StringUtils.toString(entries()); 
	}

	private static final class FilteredXPathEntriesIterator implements Iterator {

		private final Iterator	superIt;
		private final XPathPositionPredicate	pred;
		private Entry next;

		private FilteredXPathEntriesIterator(XPathPositionPredicate pred, Iterator superIt) {
			this.superIt = superIt;
			this.pred = pred;
			this.next = findNext();
		}

		public boolean hasNext() {
			return next != null;
		}

		public Object next() {
			Entry res = next;
			this.next = findNext();
			return res;
		}

		private Entry findNext() {
			if (!superIt.hasNext()) return null;
			else {
				Entry res = null;
				while (superIt.hasNext() && res == null) {
					Entry nextItem = (Entry) superIt.next();
					if (pred.match(nextItem.getPosition())) {
						res = nextItem;
					}
				}
				return res;
			}
		}

		public void remove() {
			throw new UnsupportedOperationException("remove not supported");
		}
	}
}