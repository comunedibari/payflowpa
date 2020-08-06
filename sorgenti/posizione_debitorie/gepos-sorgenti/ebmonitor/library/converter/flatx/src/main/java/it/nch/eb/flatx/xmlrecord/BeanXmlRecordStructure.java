/**
 * Created on 18/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;


/**
 * @author gdefacci
 */
public class BeanXmlRecordStructure implements XmlRecordStructure, BeanStructure {

	private String	name;
	private ObjectBuilder	objectBuilder;
	private XPathPosition	xpathPosition;
	private boolean	optional;
	private XmlRecordStructure[]	items;
	
	public BeanXmlRecordStructure(String name, ObjectBuilder objectBuilder, XmlRecordStructure[] items) {
		this(name, objectBuilder, getBaseXPath(items), true, items);
	}
	
	public BeanXmlRecordStructure(String name, ObjectBuilder objectBuilder, boolean optional, XmlRecordStructure[] items) {
		this(name, objectBuilder, getBaseXPath(items), optional, items);
	}
	
	public static XPathPosition getBaseXPath(XmlRecordStructure[] items2) {
		XPathPosition pos = null;
		for (int i = 0; i < items2.length; i++) {
			XmlRecordStructure item = items2[i];
			if (pos==null) {
				pos = item.getBaseXPath();
			} else {
				pos = XPathUtils.sharedInstance.commonPrefix(pos, item.getBaseXPath());
			}
		}
		return pos;
	}

	public BeanXmlRecordStructure(String name, ObjectBuilder objectBuilder, XPathPosition xpathPosition,
			boolean optional, XmlRecordStructure[] items) {
		this.name = name;
		this.objectBuilder = objectBuilder;
		this.xpathPosition = xpathPosition;
		this.optional = optional;
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public ObjectBuilder getObjectBuilder() {
		return objectBuilder;
	}

	public StructureKind getStructureKind() {
		return bean;
	}

	public boolean isOptional() {
		return optional;
	}

	public XPathPosition getBaseXPath() {
		return xpathPosition;
	}

	public IRecordStructure[] getItemStructures() {
		return items;
	}

}
