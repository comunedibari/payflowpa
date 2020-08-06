/**
 * 22/apr/2009
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import it.nch.eb.flatx.generator.xls.XlsUtil;
import it.nch.eb.flatx.generator.xls.xmlrecord.IndexSheetMetaData.SheetColumnEnum;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * @author gdefacci
 */
public class BaseIndexSheet extends IndexSheet {

	private final Sheet indexSheet;
	private final Set/*<String>*/ sheetNames;
	
	public BaseIndexSheet(Workbook wrkBk, Sheet indexSheet) {
		this(indexSheet, new HashSet( Arrays.asList( XlsUtil.getSheetsName(wrkBk)) ));
	}
	
	public BaseIndexSheet(Sheet indexSheet, Set sheetNames) {
		this.indexSheet = indexSheet;
		this.sheetNames = sheetNames;
	}

	protected int getStartRow() { return 1; }
	
	public int getRowFor(String sheetName) {
		if (!sheetNames.contains(sheetName)) {
			throw new IllegalArgumentException("the workbook does not include any sheet with name " + sheetName);
		}
		
		Cell[] firstCol = indexSheet.getColumn(0);
		for (int i = getStartRow(); i < firstCol.length; i++) {
			Cell cell = firstCol[i];
			String shtNm = XlsUtil.getContent(cell);
			if (shtNm!=null && shtNm.equals(sheetName)) {
				return i;
			}
		}
		return -1;
	}

	public String get(SheetColumnEnum col, String sheetName) {
		Cell cell = getCell(col, sheetName);
		return XlsUtil.getContent(cell);
	}

	public Cell getCell(SheetColumnEnum col, String sheetName) {
		int ridx = getRowFor(sheetName);
		if (ridx < 0) return null;
		Cell cell = XlsUtil.getCell(indexSheet, col.getColumnIndex(), ridx);
		return cell;
	}
}
