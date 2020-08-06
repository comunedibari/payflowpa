/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.eb.flatx.generator.JavaGenerator;
import it.nch.eb.flatx.generator.xls.XlsUtil;
import it.nch.eb.flatx.generator.xls.recordimpl.AliasedGenTypeModel;

import java.io.File;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;

/**
 * @author gdefacci
 *
 */
public class XmlRecordGenerator implements IXlsSheetGenerator {
	
	private static final XPathUtils xpthUtils = XPathUtils.sharedInstance;
	
	private final Xml2SqlCmdFullRecordGenerator mainGenerator;

	public XmlRecordGenerator(Xml2SqlCmdFullRecordGenerator mainGenerator) {
		this.mainGenerator = mainGenerator;
	}

	public void generate(String recordName , Sheet sheet) {
		int startRow = mainGenerator.getStartRow();
		List rcis = XRowInfoUtils.instance.getRecordCellsInfos(sheet, startRow, mainGenerator.getColumnMappings());
		String baseName = XRowInfoUtils.instance.toJavaClassName( recordName );
		String javaClassName = mainGenerator.getRecordNamingStrategy().apply( baseName );

		String chldXPth = null;
		String recordBaseXPath = mainGenerator.getIndexSheet().get(mainGenerator.getIndexSheetMetaData().baseXPath, sheet.getName());
		if (rcis.size()>0) {
			if (recordBaseXPath==null) {
				recordBaseXPath = XRowInfoUtils.instance.findBaseXpath(rcis);
			} else {
				chldXPth = mainGenerator.getIndexSheet().get(mainGenerator.getIndexSheetMetaData().childXPath, sheet.getName());
			}
		}
		
		XmlRecordRecordSheetModel recSheet = chldXPth == null ? 
			new XmlRecordRecordSheetModel(javaClassName, recordBaseXPath, mainGenerator.getRecordInterfaces()) :
			new XmlRecordRecordSheetModel(javaClassName, 
					"it.nch.eb.xsqlcmd.record.ChildRelativeXmlRecord", 
					recordBaseXPath, chldXPth,
					mainGenerator.getRecordInterfaces()) ;
		
		XPathPosition recordBaseXPathPosition = recordBaseXPath != null ? 
			XPathsParser.instance.parseXPathPosition(recordBaseXPath) : null;
		
		for (Iterator it = rcis.iterator(); it.hasNext();) {
			XRowInfo xri = (XRowInfo) it.next();
			try {
				if (xri.isXPath()) {
					AliasedGenTypeModel theType = mainGenerator.getTypesMap().get(xri.getTypeName());
					String rowInfoXPath = xri.getXPath();
					BaseXPathPosition xriPath = XPathsParser.instance.parseXPath(rowInfoXPath);
					BaseXPathPosition relPos = xpthUtils.relative(recordBaseXPathPosition, xriPath);
					if (chldXPth!=null && xpthUtils.containSegment(relPos, chldXPth)) {
						String[] parts = XPathUtils.sharedInstance.splitOnSegment(relPos, chldXPth);
						recSheet.add(
							new ChildRelativeXPathConversionInfoDecl(
								xri.getJavaName(), 
								parts[0],
								parts[1],
								theType.getConverterName(), 
								theType.getToObjectConverterName(), 
								xri.isOptional()));
					} else {
						recSheet.add(
							new XPathConversionInfoDecl(
								xri.getJavaName(), 
								removingLeadingSlash( relPos.toString() ), 
								theType.getConverterName(), 
								theType.getToObjectConverterName(), 
								xri.isOptional()));
					}
				} else {
					String javaType = mainGenerator.getTypesMap().getJavaType(xri.getTypeName());
					recSheet.add( new GetConstConversionInfoDecl(xri.getJavaName(), javaType, xri.getConstValue()) );
				}
			} catch (Exception e) {
				throw new RuntimeException("eror generating for " + xri, e);
				
			}
		}
		
		File pkgFolder = JavaGenerator.createPackageFolder(mainGenerator.getBaseFolder(), mainGenerator.getRecordPackageName());
		
		XmlRecordRenderer generator = new XmlRecordRenderer(mainGenerator.getRecordPackageName());
		File f = new File(pkgFolder, javaClassName + ".java");
		OutputStream fos = null;
		try {
			fos = XlsUtil.fileStream(f);
			generator.generate(fos, recSheet);
			System.out.println("created " + f);
		} finally {
			ResourcesUtil.close(fos);
		}
	}
	
	public static String removingLeadingSlash(String str) {
		if (str.startsWith("/")) return removingLeadingSlash(str.substring(1));
		else return str;
	}


}
