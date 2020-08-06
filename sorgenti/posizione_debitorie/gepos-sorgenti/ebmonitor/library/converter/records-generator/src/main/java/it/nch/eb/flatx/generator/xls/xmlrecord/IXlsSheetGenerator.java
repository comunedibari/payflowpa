/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import jxl.Sheet;

/**
 * @author gdefacci
 *
 */
public interface IXlsSheetGenerator {
	
	void generate(String recordName, Sheet sheet);

}
