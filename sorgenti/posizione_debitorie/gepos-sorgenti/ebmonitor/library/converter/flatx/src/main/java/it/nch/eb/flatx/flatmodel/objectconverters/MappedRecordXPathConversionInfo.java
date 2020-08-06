/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.flatmodel.IXmlRecord;
import it.nch.eb.flatx.flatmodel.MappedXmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathToObjectConversionInfoEqualsHelper;
import it.nch.eb.flatx.flatmodel.conversioninfo.XmlRecordIXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class MappedRecordXPathConversionInfo implements XmlRecordIXPathToObjectConversionInfo {
	
	private static final long serialVersionUID = 6402096333051805342L;
	private final MappedXmlRecord xmlRecord;
	private String name;
	private final int	position;

	public MappedRecordXPathConversionInfo(int pos, MappedXmlRecord record) {
		this.xmlRecord = record;
		this.position = pos;
	}

	public Class getTargetClass() {
		return xmlRecord.getTargetClass();
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elem) {
		return xmlRecord.getValue(pos, elem);
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
	
	public IXmlRecord getXmlRecord() {
		return xmlRecord;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("MappedRecordXPathConversionInfo(");
		sb.append("name:");
		sb.append(getName());
		sb.append(", position:");
		sb.append(getPosition());
		sb.append(", targetClass:");
		sb.append(getTargetClass());
		sb.append(")");
		return sb.toString();
	}

}
