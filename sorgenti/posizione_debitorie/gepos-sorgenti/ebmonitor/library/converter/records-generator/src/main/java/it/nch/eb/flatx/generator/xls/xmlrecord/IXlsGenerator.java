/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import jxl.Workbook;

/**
 * @author gdefacci
 *
 */
public interface IXlsGenerator {
	
	void generate(Workbook workbook, String[] processedSheets);

}
