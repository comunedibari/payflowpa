/**
 * Created on 27/feb/2009
 */
package it.nch.eb.flatx.flatmodel.objectconverters;

import it.nch.eb.flatx.flatmodel.IXmlRecord;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathToObjectConversionInfoEqualsHelper;
import it.nch.eb.flatx.flatmodel.conversioninfo.XmlRecordIXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;


/**
 * @author gdefacci
 */
public class RecordXPathConversionInfo implements XmlRecordIXPathToObjectConversionInfo{
	
	private static final long serialVersionUID = 3101566061858245782L;
	private final XmlRecord record;
	private final ObjectBuilder objectBuilder;
	private String name;
	private final int	position;

	public RecordXPathConversionInfo(int pos, XmlRecord record, ObjectBuilder objectBuilder) {
		this.record = record;
		this.position = pos;
		this.objectBuilder = objectBuilder;
	}

	public Class getTargetClass() {
		return objectBuilder.getProductClass();
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elem) {
		XPathPosition rpos; 
		
		XPathPosition recPos = record.getBaseXPathPosition();
		Object res;
		if (XPathUtils.sharedInstance.isRelativePosition( recPos )) {
			rpos = pos.concat(recPos);
			res = record.get(objectBuilder, rpos, elem);
		} else {
			XPathPosition npos = (XPathPosition) XPathUtils.sharedInstance.adaptCommonPrefixIndexes(pos, recPos);
			res = record.get(objectBuilder, npos, elem);
		} 
		return res;
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
	
	public XmlRecord getRecord() {
		return record;
	}
	
	public ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("RecordXPathConversionInfo(");
		sb.append("name:");
		sb.append(getName());
		sb.append(", position:");
		sb.append(getPosition());
		sb.append(", targetClass:");
		sb.append(getTargetClass());
		sb.append(")");
		return sb.toString();
	}

	/* @Override */
	public IXmlRecord getXmlRecord() {
		return record;
	}
	
	

}
