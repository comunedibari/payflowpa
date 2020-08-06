/**
 * Created on 18/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import java.util.ArrayList;
import java.util.List;

import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SeqRecordStructure;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class SeqXmlRecordStructure implements XmlRecordStructure, SeqRecordStructure{

	private final static ObjectBuilder arraylistObjectBuilder = 
		new NewInstanceObjectBuilder(List.class, ArrayList.class);
	
	private String	name;
	private ObjectBuilder objBuilder;
	private XmlRecordStructure	itemStructure;
	private boolean	optional;
	private final XPathPosition xpathPosition;
	
	public SeqXmlRecordStructure(XmlRecordStructure itemStructure) {
		this(itemStructure.getName(), arraylistObjectBuilder, itemStructure, true);
	}
	public SeqXmlRecordStructure(String name, XmlRecordStructure itemStructure) {
		this(name, arraylistObjectBuilder, itemStructure, true);
	}
	
	public SeqXmlRecordStructure(String name, XmlRecordStructure itemStructure, boolean optional) {
		this(name, arraylistObjectBuilder, itemStructure, optional);
	}
			
	public SeqXmlRecordStructure(String name, ObjectBuilder objBuilder, XmlRecordStructure itemStructure,
			boolean optional) {
		this(name, objBuilder, itemStructure, optional, itemStructure.getBaseXPath());
	}
	
	public SeqXmlRecordStructure(String name, ObjectBuilder objBuilder, XmlRecordStructure itemStructure,
			boolean optional, XPathPosition xpathPosition) {
		super();
		this.name = name;
		this.objBuilder = objBuilder;
		this.itemStructure = itemStructure;
		this.optional = optional;
		this.xpathPosition = xpathPosition;
	}

	public String getName() {
		return name;
	}

	public ObjectBuilder getObjectBuilder() {
		return objBuilder;
	}

	public StructureKind getStructureKind() {
		return seq;
	}

	public boolean isOptional() {
		return optional;
	}

	public XPathPosition getBaseXPath() {
		return xpathPosition;
	}

	public IRecordStructure getItemStrucuture() {
		return itemStructure;
	}

}
