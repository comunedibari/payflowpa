/**
 * Created on 01/set/08
 */
package it.nch.eb.flatx.flatmodel.xpath;


import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * @author gdefacci
 */
public class RootXPathsMapScope implements IXPathMapScope, WXPathMapScope {
	
	public static final RootXPathsMapScope instance = new RootXPathsMapScope();
	
	private RootXPathsMapScope() {
	}
	
	public boolean isRoot() {
		return true;
	}
	
	public int size() {
		return 0;
	}

	public String absGet(BaseXPathPosition pos) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public String absGetOrElse(BaseXPathPosition key, String elseValue) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public Map asMap() {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public String get(BaseXPathPosition pos) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public String getOrElse(BaseXPathPosition key, String elseValue) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public IXPathMapScope getParent() {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public void put(BaseXPathPosition pos, String textValue) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public String remove(BaseXPathPosition pos) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}
	
	public Iterator entries() {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public String toString() {
		return "RootXPathsMapScope";
	}

	public Set getDefitions() {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}
	
	public void define(BaseXPathPosition key) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}
	
	public void undefine(BaseXPathPosition key) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public WXPathMapScope getWParent() {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public boolean isEmpty() {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}
	
	public IXPathMapScope view(XPathPositionPredicate pred) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}

	public IXPathMapScope view(XPathPositionPredicate pred,
			boolean keepDefiniton) {
		throw new UnsupportedOperationException("RootXPathsMapScope");
	}
	
}
