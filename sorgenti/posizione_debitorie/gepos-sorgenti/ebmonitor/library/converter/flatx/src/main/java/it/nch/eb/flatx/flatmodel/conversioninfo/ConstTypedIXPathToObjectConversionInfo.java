/**
 * 
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 *
 */
public class ConstTypedIXPathToObjectConversionInfo implements TypedXPathMapFunction, StableXPathMapFunction{

	private final Class targetClass;
	private final Object value;
	
	public ConstTypedIXPathToObjectConversionInfo(Class targetClass, Object value) {
		this.targetClass = targetClass;
		this.value = value;
	}

	public final Class getTargetClass() {
		return targetClass;
	}

	public final Object getValue(XPathPosition pos, IXPathMapScope elem) {
		return value;
	}

	protected Object getValue() {
		return value;
	}
	
}
