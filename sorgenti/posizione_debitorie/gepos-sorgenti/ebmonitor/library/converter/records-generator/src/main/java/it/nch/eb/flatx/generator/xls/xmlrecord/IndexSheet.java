/**
 * 21/apr/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.generator.xls.xmlrecord.IndexSheetMetaData.SheetColumnEnum;


/**
 * @author gdefacci
 */
public abstract class IndexSheet {
	
	public abstract String get(SheetColumnEnum cloumn, String sheetName);

}
