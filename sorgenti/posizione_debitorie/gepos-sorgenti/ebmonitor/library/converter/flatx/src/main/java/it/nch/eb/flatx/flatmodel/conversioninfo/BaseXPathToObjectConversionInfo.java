/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class BaseXPathToObjectConversionInfo implements IXPathToObjectConversionInfo {

	private static final long	serialVersionUID	= 5124085637907440675L;
	
	private final int position;

	private String	name;
	private final XPathMapFunction xpathMapFunction ;

	private final Class	targetClass;
	
	public BaseXPathToObjectConversionInfo(int position, Class trgtClass, XPathMapFunction xpathMapFunction) {
		this(position, null, trgtClass, xpathMapFunction);
	}
	
	public BaseXPathToObjectConversionInfo(int position, XPathMapFunction xpathMapFunction) {
		this(position, null, null, xpathMapFunction );
	}
	
	protected BaseXPathToObjectConversionInfo(int position, String name, Class targetClass,
			XPathMapFunction xpathMapFunction) {
		this.position = position;
		this.name = name;
		this.xpathMapFunction = xpathMapFunction;
		if (targetClass != null) 
			this.targetClass = targetClass;
		else
			this.targetClass = getTargetClass(xpathMapFunction);
	}
	
	static Class getTargetClass(XPathMapFunction xpathMapFunction) {
		if (xpathMapFunction instanceof TypedXPathMapFunction) {
			return ((TypedXPathMapFunction)xpathMapFunction).getTargetClass();
		} else {
			return Object.class;
		}
	}

	public Object getValue(XPathPosition pos, IXPathMapScope/*<String,Object>*/ elem) {
		return xpathMapFunction.getValue(pos, elem);
	}
	
	public XPathMapFunction getXpathMapFunction() {
		return xpathMapFunction;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public String getName() {
		return name;
	}

	public int getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int compareTo(Object o) {
		return XPathToObjectConversionInfoEqualsHelper.instance.compare(this, o);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer("BaseXPathToObjectConversionInfo(");
		sb.append("name:");
		sb.append(getName());
		sb.append(", position:");
		sb.append(getPosition());
		sb.append(", XPathMapFunction:");
		sb.append(getXpathMapFunction());
		sb.append(", targetClass:");
		sb.append(getTargetClass());
		sb.append(")");
		return sb.toString();
	}
}
