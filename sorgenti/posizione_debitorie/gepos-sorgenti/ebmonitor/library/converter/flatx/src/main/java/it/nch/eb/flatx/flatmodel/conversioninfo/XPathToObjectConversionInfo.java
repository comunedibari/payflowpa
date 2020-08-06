/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.ReflectionUtils.Setter;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConverter;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.MissingXPathHandler;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class XPathToObjectConversionInfo implements XPathHolderIXPathToObjectConversionInfo {

	private static final long	serialVersionUID	= 5124085637907440675L;
	
	private final int position;
	private final BaseXPathPosition xpath;
	private final boolean optional;
	private final Converter converter;
	private final ToObjectConverter	toObjectConverter;

	private String	name;

	public XPathToObjectConversionInfo(int pos, Converter delegate, BaseXPathPosition xpath, boolean optional) {
		this(pos, delegate, null, null, xpath, optional);
	}
	
	public XPathToObjectConversionInfo(int pos, Converter delegate, ToObjectConverter cnv, BaseXPathPosition xpath,
			boolean optional) {
		this(pos, delegate, cnv, null, xpath, optional);
	}

	public XPathToObjectConversionInfo(int pos, Converter delegate, ToObjectConverter cnv, String name, BaseXPathPosition xpath,
			boolean optional) {
		if (delegate==null) throw new NullPointerException("converter");
		this.position = pos;
		this.converter = delegate;
		this.toObjectConverter = cnv;
		this.name = name;
		this.xpath = xpath;
		this.optional = optional;
	}
	
	public MissingXPathHandler getMissingXPathHandler() {
		return new MissingXPathHandler() {

			public String missingXPath(BaseXPathPosition pos, IXPathMapScope elem) {
				throw new RuntimeException("pos)" + getPosition() + " element)" + getName() + "\ncant find xpath " + xpath + " on elements map " + elem);
			}
			
		};
	}
	
	private ToObjectConverter getToObjectConverter() {
		return toObjectConverter;
	}
	
	private Converter getConverter() {
		return converter;
	}

	public void setProperty(Object target, String propValue) {
		String c1 = getConverter().encode(propValue);
		Object realObjectvalue = getToObjectConverter() != null ? getToObjectConverter().convert(c1) : c1;
		Setter setter = ReflectionUtils.getSetterForProperty(target, getName(), getToObjectConverter().getConversionTargetClass());
		setter.setValue(realObjectvalue);
	}
	
	public Object getValue(XPathPosition pos, IXPathMapScope/*<String,Object>*/ elem) {
		String stringValue2 = getStringValue(pos, elem);
		if (stringValue2!=null) {
			try {
				String stringValue = getConverter().encode(stringValue2);
				return getToObjectConverter() != null ? getToObjectConverter().convert(stringValue) : stringValue;
			} catch (Exception e) {
				throw new RuntimeException("error converting " + stringValue2 + " using " + this, e);
			}
		} else {
			if (!optional) throw new IllegalStateException("missing mandatory field " + this);
			else return null;
		}
	}
	
	public Class getTargetClass() {
		return getToObjectConverter() !=null ? getToObjectConverter().getConversionTargetClass() : String.class;
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
	
	protected String getStringValue(XPathPosition basePos, IXPathMapScope elem) {
		String res = null;
		BaseXPathPosition rpos = null;
		try {
			rpos = basePos.concat(this.xpath);
			res = elem.get(rpos);
		} catch (Exception e) {
			throw new RuntimeException("error retrieving xpath " +  this.xpath + " from elem " + elem + "\n current position " + basePos , e);
		}
		if (res==null && !optional) {
			return getMissingXPathHandler().missingXPath(rpos, elem);
		}
		else return res;
	}
	
	public int compareTo(Object o) {
		return XPathToObjectConversionInfoEqualsHelper.instance.compare(this, o);
	}
	
	public BaseXPathPosition getXPath() {
		return xpath;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("XPathToObjectConversionInfo(");
		sb.append("name:");
		sb.append(getName());
		sb.append(", xpath:");
		sb.append(this.xpath);
		sb.append(", position:");
		sb.append(getPosition());
		sb.append(", converter:");
		sb.append(getConverter());
		sb.append(", toObjectConverter:");
		sb.append(getToObjectConverter());
		sb.append(", targetClass:");
		sb.append(getTargetClass());
		sb.append(")");
		return sb.toString();
	}
}
