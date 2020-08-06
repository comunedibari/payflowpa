/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gdefacci
 *
 */
public class XmlRecordRecordSheetModel {
	
	private final String name;
	private final String basePath;
	private final String childPath;
	private final List/*<Xml2SqlConversionInfoDecl>*/ rows = new ArrayList();
	private final String[] interfaces;
	private final String xmlRecordClass;
	
	public XmlRecordRecordSheetModel(String name, String xmlRecordClass, String basePath,
			String childPath, String[] interfaces) {
		this.name = name;
		this.basePath = basePath;
		this.interfaces = interfaces;
		this.xmlRecordClass = xmlRecordClass;
		this.childPath = childPath;
	}

	public XmlRecordRecordSheetModel(String name, String basePath, String[] interfaces) {
		this(name, "it.nch.eb.flatx.flatmodel.XmlRecord", basePath, null, interfaces);
	}

	public String getName() {
		return name;
	}

	public String getBasePath() {
		return basePath;
	}
	
	public String[] getInterfaces() {
		return interfaces;
	}

	public Xml2SqlConversionInfoDecl[] getRows() {
		return (Xml2SqlConversionInfoDecl[]) rows.toArray(new Xml2SqlConversionInfoDecl[0]);
	}
	
	public String getXmlRecordClass() {
		return xmlRecordClass;
	}
	
	public String getChildPath() {
		return childPath;
	}

	public void add(Xml2SqlConversionInfoDecl decl) {
		this.rows.add(decl);
	}
	
}
