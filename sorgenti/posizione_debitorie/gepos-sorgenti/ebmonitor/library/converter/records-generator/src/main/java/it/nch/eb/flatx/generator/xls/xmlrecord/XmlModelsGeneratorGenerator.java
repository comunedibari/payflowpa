/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.generator.ConversionGenerationModel;
import it.nch.eb.flatx.generator.OutputStreamGenerator;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;
import it.nch.eb.flatx.generator.xls.recordimpl.ModelsGeneratorGenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @author gdefacci
 *
 */
public class XmlModelsGeneratorGenerator extends ModelsGeneratorGenerator implements IXlsGenerator, ConversionGenerationModel {
	
	private final Xml2SqlCmdConvertersMap typesMap;
	
	public XmlModelsGeneratorGenerator(
			ConversionGenerationModel mainGenerator, Xml2SqlCmdConvertersMap typesMap) {
		super(mainGenerator);
		this.typesMap = typesMap;
	}
	
	protected OutputStreamGenerator createRenderer(Workbook workbook, String[] processedSheets) {
		XmlRecordWithExtraFieldsInfo[] recWthFlds = new XmlRecordWithExtraFieldsInfo[processedSheets.length];
		for (int i = 0; i < processedSheets.length; i++) {
			String sheetName = processedSheets[i];
			Sheet sheet = workbook.getSheet(sheetName);
			if (sheet==null) throw new NullPointerException();
			FieldDecl[] extraFields = getExtraFields( sheet ); 
			recWthFlds[i] = new XmlRecordWithExtraFieldsInfo( getRercordClassFullName(sheetName), extraFields);
		}
		return new XmlRecord2GenerateModelsRenderer(this, getGeneratedClassName(), recWthFlds);
	}

	protected FieldDecl[] getExtraFields(Sheet sheet) {
		int startRow = getStartRow();
		List/*<XRowInfo>*/ xrs = XRowInfoUtils.instance.getAllCellsInfos(sheet, startRow, getColumnMappings());
		List/*<FieldDecl>*/ res = new ArrayList();
		for (Iterator it = xrs.iterator(); it.hasNext();) {
			XRowInfo xr = (XRowInfo) it.next();
			if (((xr.getXPath() == null) || (xr.getXPath().trim().length() == 0))
					&& ((xr.getConstValue() == null) || (xr.getConstValue().trim().length() == 0))) {
				String javType = typesMap.getJavaType(xr.getTypeName());
				res.add(new FieldDecl(xr.getJavaName(), javType));
			}
		}
		
		if (res.isEmpty()) return null;
		else return (FieldDecl[]) res.toArray(new FieldDecl[0]);
	}

}
