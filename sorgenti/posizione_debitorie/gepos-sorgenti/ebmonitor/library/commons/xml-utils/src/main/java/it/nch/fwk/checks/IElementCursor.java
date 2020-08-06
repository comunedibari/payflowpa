/**
 * Created on 11/lug/07
 */
package it.nch.fwk.checks;

//import it.nch.fwk.core.NamespacesInfos;

import it.nch.fwk.core.NamespacesInfos;

import java.math.BigDecimal;
import java.util.Iterator;

/**
 * @author gdefacci
 */
public interface IElementCursor {

//	String value(); useless => use getValue()
	
	String value(IElementCursor element);

	Attribute attribute(IElementCursor cursor, String name);

	IElementCursor child(String name, int i);

	String getPath();
	
	String getName();
	
//	NamespacesInfos getNamespaces();
	
	String getValue();
	String getOrElse(String elseReturn);
	
	String getPrefix();
	
	Iterator xpathIterator(String xpath);
	Iterator iterator(String name);

	BigDecimal bigDecimalValue(String str);

	BigDecimal bigDecimalValue(IElementCursor elem);

	IElementCursor child(String name);
	IElementCursor child(String[] paths);
	
	IElementCursor[] chidren(final String xquery); 
	
	IElementCursor optionalChild(String name);
	IElementCursor optionalChild(String[] paths); 

	String childStringValue(String name, int idx);

	BigDecimal childBigDecimalValue(String name, int idx);

	String childStringValue(String name);

	BigDecimal childBigDecimalValue(String name);

	Attribute attribute(String name);

	String attributeStringValue(String name);

	BigDecimal attributeBigDecimalValue(String name);

	BigDecimal bigDecimalValue();

	IElementCursor getParent();
	
	NamespacesInfos getNamespaces();
	
	Integer intValue(String str);

	Integer intValue(IElementCursor element);
	
	Integer childIntValue(String name, int idx);
	
	Integer childIntValue(String name);
	
	Integer intValue();

	Integer attributeIntValue(String name);

	
}