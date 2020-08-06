/**
 * Created on 11/lug/07
 */
package it.nch.fwk.checks;

import it.nch.fwk.checks.xom.NullElementCursor;
import it.nch.fwk.core.NamespacesInfos;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * FIXME: replace java.lang.Integer with java.lang.Long, renaming related methods
 * @author gdefacci
 */
public abstract class AbstractElementCursor implements IElementCursor {
	
	private NamespacesInfos namespaces;
	
	public AbstractElementCursor(NamespacesInfos namespaces) {
		this.namespaces = namespaces;
	}
	
	public abstract Attribute attribute(String name);
	
	public abstract IElementCursor child(String name, int i);
	
	public abstract String getPath();
	
	public String value(IElementCursor element) {
		if (element==null) return null;
		return element.getValue();
	}
	
	public BigDecimal bigDecimalValue(String str) {
		if (str==null) return null;
		BigDecimal bigDecimal = new BigDecimal(str);
		return bigDecimal;
	}
	
	public Integer intValue(String str) {
		if (str==null) return null;
		return Integer.valueOf( str );
	}

	public Integer intValue(IElementCursor element) {
		String str = value(element);
		return intValue(str);
	}
	
	public Integer childIntValue(String name) {
		return intValue( child(name) );
	}
	
	public Integer childIntValue(String name, int idx) {
		return this.intValue( child(name, idx) );
	}
	
	public Integer intValue() {
		return intValue(this);
	}

	public Integer attributeIntValue(String name) {
		return intValue(attributeStringValue(name));
	}

	public BigDecimal bigDecimalValue(IElementCursor elem) {
		if (elem==null) return null;
		String str = value(elem).trim();
		return bigDecimalValue(str);
	}

	public IElementCursor child(String name) {
		return child(name,0);
	}

	public IElementCursor child(String[] paths) {
		IElementCursor res = this;
		if (paths!=null && paths.length>0) {
			for (int i = 0; i < paths.length && !NullElementCursor.isNullElementCursor(res); i++) {
				res = res.child(paths[i]);
			}
		}
		return res;
	}
	
	public IElementCursor optionalChild(String[] paths) {
		IElementCursor res = this;
		if (paths!=null && paths.length>0) {
			for (int i = 0; i < paths.length && !NullElementCursor.isNullElementCursor(res); i++) {
				res = res.optionalChild(paths[i]);
			}
		}
		return res;
	}

	public String childStringValue(String name, int idx) {
		return value( child(name, idx) );
	}

	public BigDecimal childBigDecimalValue(String name, int idx) {
		return bigDecimalValue( child(name, idx) );
	}

	public String childStringValue(String name) {
		return value(child(name)) ;
	}


	public BigDecimal childBigDecimalValue(String name) {
		return bigDecimalValue( child(name) );
	}
	
	public Attribute attribute(IElementCursor cursor, String name) {
		return cursor.attribute(name);
	}

	public String attributeStringValue(String name) {
		Attribute attribute = attribute(name);
		if (attribute==null) return null;
		return attribute.getValue();
	}

	public BigDecimal attributeBigDecimalValue(String name) {
		return bigDecimalValue(attributeStringValue(name));
	}

	public BigDecimal bigDecimalValue() {
		return bigDecimalValue(this);
	}

	public NamespacesInfos getNamespaces() {
		return namespaces;
	}
	
	public String getOrElse(String elseReturn) {
		String value = getValue();
		if (value==null) return elseReturn;
		return value;
	}

	public abstract Iterator xpathIterator(String xpath);
	
}