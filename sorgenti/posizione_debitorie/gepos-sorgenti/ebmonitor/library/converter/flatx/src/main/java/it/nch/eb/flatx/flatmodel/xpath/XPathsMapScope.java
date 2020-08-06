/**
 * Created on 29/ago/2008
 */
package it.nch.eb.flatx.flatmodel.xpath;

import it.nch.eb.common.utils.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


/**
 * @author gdefacci
 */
public class XPathsMapScope implements IXPathMapScope, WXPathMapScope {
	
	private final WXPathMapScope parent;
	private Map/*<BaseXPathPosition,String>*/ pathsMap;
	private Set/*<BaseXPathPosition>*/ definedKeys;
	
	public XPathsMapScope() {
		this(null, new TreeMap());
	}
	
	public XPathsMapScope(WXPathMapScope parent) {
		this(parent, new TreeMap());
	}
	
	public XPathsMapScope(WXPathMapScope parent, Map pathsMap) {
		if (parent!=null) this.parent = parent;
		else this.parent = RootXPathsMapScope.instance;
		this.pathsMap = pathsMap;
		this.definedKeys = new TreeSet();
	}
	
	public boolean isRoot() {
		return false;
	}

	public String get(BaseXPathPosition pos) {
		String res = (String) this.pathsMap.get(pos);
		if (res==null && !parent.isRoot()) res = this.parent.get(pos);
		return res;
	}

	public IXPathMapScope getParent() {
		return parent;
	}
	
	public WXPathMapScope getWParent() {
		return parent;
	}
	
	public boolean isEmpty() {
		return !entries().hasNext();
	}

	public WXPathMapScope getDefinition(BaseXPathPosition pos) {
		if (pos==null) throw new NullPointerException();
		boolean found = false;
		WXPathMapScope scope = this;
		while (!found && scope!=null && !scope.isRoot()) {
			if (scope.getDefitions().contains(pos)) {
				found = true;
			} else {
				scope = scope.getWParent();
			}
		}
		if (found) return scope;
		else return null;
	}
	
	public void define(BaseXPathPosition key) {
		if (definedKeys.contains(key)) throw new IllegalStateException("duplicate key "+ key);
		this.definedKeys.add(key);
	}
	
	public void undefine(BaseXPathPosition key) {
		if (!definedKeys.contains(key)) throw new IllegalStateException("undefining a non existent key "+ key);
		this.definedKeys.remove(key);
	}

	public Set getDefitions() {
		Set res = new TreeSet();
		if (!getParent().isRoot()) {
			Set parentDefs = getParent().getDefitions();
			res.addAll(parentDefs);
		}
		res.addAll(definedKeys);
		return res;
	}

	public void put(BaseXPathPosition pos, String textValue) {
		if (pos==null || textValue==null) throw new NullPointerException();
		WXPathMapScope scope = getDefinition(pos);
		if (scope!=null && !scope.isRoot() && scope!=this) {
			scope.put(pos, textValue);
		} else if (scope==this || scope==null) {
			this.pathsMap.put(pos, textValue);
		} else {
			throw new IllegalStateException("root");
		}
	}

	public int size() {
		return pathsMap.size() + (parent.isRoot() ? 0 : parent.size());
	}
	
	public Iterator entries() {
		return new XPathsMapIterator();
	}
	
	public IXPathMapScope view(final XPathPositionPredicate pred) {
		return new FilteredXPathMapBindings(this, pred);
	}
	
	public String toString() {
		return StringUtils.toString(this.pathsMap) + (parent.isRoot() ? "" : parent.toString()); 
	}

	private final class XPathsMapIterator implements Iterator {

		private Iterator parentIterator = XPathsMapScope.this.parent.isRoot() ?
				new TreeSet().iterator() :
				XPathsMapScope.this.parent.entries();
				
		private Iterator thisIterator = XPathsMapScope.this.pathsMap.entrySet().iterator();

		public boolean hasNext() {
			return (thisIterator.hasNext() || parentIterator.hasNext());
		}

		private ConcreteEntry create(java.util.Map.Entry/*<BaseXPathPosition,String>*/ mapEntry) {
			return new ConcreteEntry((BaseXPathPosition)mapEntry.getKey(), (String)mapEntry.getValue());
		}

		public Object next() {
			if (thisIterator.hasNext()) return create( (java.util.Map.Entry) thisIterator.next() );
			else return parentIterator.next();
		}

		public void remove() {
			throw new UnsupportedOperationException("remove aint supported by this iterator");
		}
	}

	static class ConcreteEntry implements Entry {

		private String	value;
		private BaseXPathPosition	pos;
		
		public ConcreteEntry(BaseXPathPosition pos, String value) {
			this.pos = pos;
			this.value = value;
		}

		public BaseXPathPosition getPosition() {
			return pos;
		}

		public String getValue() {
			return value;
		}

		public String toString() {
			return "Entry(" + pos + ", " + value + ")"; 
		}
		
	}
}
