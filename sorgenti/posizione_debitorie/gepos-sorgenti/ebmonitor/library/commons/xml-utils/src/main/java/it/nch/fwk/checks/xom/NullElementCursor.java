/**
 * Created on 14/dic/07
 */
package it.nch.fwk.checks.xom;

import java.util.Iterator;

import it.nch.eb.common.utils.StringUtils;
import it.nch.fwk.checks.AbstractElementCursor;
import it.nch.fwk.checks.Attribute;
import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.core.NamespacesInfos;


/**
 * 
 * @author gdefacci
 */
public class NullElementCursor extends AbstractElementCursor {

	protected final class NullIterator implements Iterator {

		public boolean hasNext() {
			return false;
		}

		public Object next() {
			return null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private String	xpath;

	public NullElementCursor(NamespacesInfos namespaces, String xpath) {
		super(namespaces);
		this.xpath = xpath;
	}
	
	public NullElementCursor(NamespacesInfos namespaces, String[] basePaths, String xpath) {
		super(namespaces);
		this.xpath = StringUtils.concatPaths( StringUtils.concatPaths(basePaths) , xpath);
	}
	
	public boolean isNullElementCursor() {
		return true;
	}
	
	public static boolean isNullElementCursor(IElementCursor cursor) {
		return cursor==null || cursor instanceof NullElementCursor;
	}

	public Attribute attribute(String name) {
		return null;
	}

	public String value() {
		return null;
	}
	
	public String getValue() {
		return null;
	}

	public IElementCursor child(String name, int i) {
		return new NullElementCursor(getNamespaces(), xpath);
	}
	
	public IElementCursor[] chidren(String xquery) {
		return new IElementCursor[0];
	}

	public String getPath() {
		return xpath;
	}
	
//	FIXME: same as getPath() 
	public String getName() {
		return xpath;
	}
	
//	FIXME: null is not a nice prefix
	public String getPrefix() {
		return null;
	}

	public Iterator xpathIterator(String xpath) {
		return new NullIterator();
	}

	public IElementCursor getParent() {
		return null;
	}

	public Iterator iterator(String name) {
		return new NullIterator();
	}

	public IElementCursor optionalChild(String name) {
		return child(name);
	}

}
