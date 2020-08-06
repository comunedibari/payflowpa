/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.generator;

import java.io.InputStream;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/**
 * @author gdefacci
 */
public class XlsLoader {
	
	public XlsModel[] load(InputStream is) {
		Workbook workbook = loadFile(is);
		Sheet sheet = workbook.getSheet(0);
		int rowsNumber = sheet.getRows();
		XlsModel[] res = new XlsModel[rowsNumber];
		for (int y=0; y<rowsNumber; y++) {
			String modifier = sheet.getCell(0,y).getContents();
			String[] infos = getInfos(sheet, y);
			res[y] = new XlsModel(modifier, infos);
		}
		return res;
	}

	private String[] getInfos(Sheet sheet, int y) {
		Cell[] cells = sheet.getRow(y);
		String[] res = new String[cells.length];
		for (int i = 1; i < cells.length; i++) 	
			res[i-1] = cells[i].getContents();
		return res;
	}

	private Workbook loadFile(InputStream is) {
		try {
			return Workbook.getWorkbook(is);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
}
