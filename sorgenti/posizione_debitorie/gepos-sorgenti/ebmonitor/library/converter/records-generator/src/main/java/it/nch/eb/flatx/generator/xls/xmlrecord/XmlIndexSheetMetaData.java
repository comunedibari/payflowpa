/**
 * 22/apr/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @author gdefacci
 */
public class XmlIndexSheetMetaData extends IndexSheetMetaData {
	
	public final SheetColumnEnum baseXPath = new BaseSheetColumnEnum("baseXPath", 1);
	public final SheetColumnEnum childXPath = new BaseSheetColumnEnum("childXPath", 2);
	
	/* @Override */
	public IndexSheet create(Workbook wtkBook, Sheet sht) {
		return new BaseIndexSheet(wtkBook, sht);
	}

}
