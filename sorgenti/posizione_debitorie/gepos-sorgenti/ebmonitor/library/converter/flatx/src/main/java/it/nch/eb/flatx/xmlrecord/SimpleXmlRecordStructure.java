/**
 * Created on 11/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordStructureEqualsHelper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SimpleRecordStructure;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;


/**
 * @author gdefacci
 */
public class SimpleXmlRecordStructure implements SimpleRecordStructure, XmlRecordStructure {
	
	private final XmlRecord xmlRecord;
	private final ObjectBuilder objectBuilder;
	private final boolean optional;
	private final XPathPosition	baseXpath;
	private final String	name;
	
	public SimpleXmlRecordStructure(XmlRecord xmlRecord, ObjectBuilder objectBuilder) {
		this(xmlRecord, objectBuilder, false);
	}
	
	public SimpleXmlRecordStructure(XmlRecord xmlRecord, ObjectBuilder objectBuilder, boolean optional) {
		this(xmlRecord, xmlRecord.getName(), objectBuilder, optional, 
				getBaseXPath(xmlRecord) );
	}

	private static XPathPosition getBaseXPath(XmlRecord xmlRecord) {
		return XPathsParser.instance.parseXPathPosition(xmlRecord.getBaseXPath());
	}
	
	public SimpleXmlRecordStructure(XmlRecord xmlRecord, String name, ObjectBuilder objectBuilder, boolean optional) {
		this(xmlRecord, name, objectBuilder, optional, 
				getBaseXPath(xmlRecord) );
	}
	
	public SimpleXmlRecordStructure(XmlRecord xmlRecord, String name, ObjectBuilder objectBuilder) {
		this(xmlRecord, name, objectBuilder, true, 
				getBaseXPath(xmlRecord) );
	}
	
	public SimpleXmlRecordStructure(XmlRecord xmlRecord, String name, ObjectBuilder objectBuilder, boolean optional,
			XPathPosition baseXpath) {
		this.xmlRecord = xmlRecord;
		this.objectBuilder = objectBuilder;
		this.optional = optional;
		this.baseXpath = baseXpath;
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}

	public StructureKind getStructureKind() {
		return simple;
	}

	public boolean isOptional() {
		return optional;
	}

	public XPathPosition getBaseXPath() {
		return baseXpath;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SimpleXmlRecordStructure)) return false;
		return RecordStructureEqualsHelper.instance.areEquals(this, obj);
	}

	public int hashCode() {
		return RecordStructureEqualsHelper.instance.hashCode(this);
	}
	
	public String toString() {
		return RecordStructureEqualsHelper.instance.toString(this);
	}
	
	public XmlRecord getXmlRecord() {
		return xmlRecord;
	}
	
}
