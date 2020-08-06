/**
 * 22/apr/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import jxl.Sheet;
import jxl.Workbook;

/**
 * @author gdefacci
 */
public abstract class IndexSheetMetaData {
	
	protected interface SheetColumnEnum {
		public String getColumnName();
		public int getColumnIndex();
	}
	protected class BaseSheetColumnEnum implements SheetColumnEnum {
		private final String columnName;
		private final int columnIndex;
		
		public BaseSheetColumnEnum(String columnName, int columnIndex) {
			this.columnName = columnName;
			this.columnIndex = columnIndex;
		}
		public String getColumnName() {
			return columnName;
		}
		public int getColumnIndex() {
			return columnIndex;
		}

	}
	
	public abstract IndexSheet create(Workbook wtkBook, Sheet sht);
	
	

}
