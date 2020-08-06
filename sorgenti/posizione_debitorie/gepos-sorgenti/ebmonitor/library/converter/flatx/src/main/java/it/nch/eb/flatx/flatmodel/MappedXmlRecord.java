/**
 * 29/apr/2009
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;

/**
 * @author gdefacci
 */
public class MappedXmlRecord implements IMappedXMLRecord {
	
	private static final long serialVersionUID = 2336751739869682160L;
	
	private final ObjectBuilder objectBuilder;
	private final XmlRecord xmlRecord;
	
	public MappedXmlRecord(Class klass, XmlRecord xmlRecord) {
		this(new NewInstanceObjectBuilder(klass), xmlRecord);
	}
	public MappedXmlRecord(ObjectBuilder objectBuilder, XmlRecord xmlRecord) {
		this.objectBuilder = objectBuilder;
		this.xmlRecord = xmlRecord;
	}

	public String getBaseXPath() {
		return xmlRecord.getBaseXPath();
	}

	public IXPathToObjectConversionInfo[] getConversionInfos() {
		return xmlRecord.getConversionInfos();
	}

	public String getName() {
		return xmlRecord.getName();
	}

	public Object getValue(XPathPosition pos, IXPathMapScope elem) {
		return xmlRecord.get(objectBuilder, pos, elem);
	}

	public Class getTargetClass() {
		return objectBuilder.getProductClass();
	}

}
