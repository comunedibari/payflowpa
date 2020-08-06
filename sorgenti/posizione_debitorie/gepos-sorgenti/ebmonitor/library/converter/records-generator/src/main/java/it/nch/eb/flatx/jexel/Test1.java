/**
 * Created on 15/gen/08
 */
package it.nch.eb.flatx.jexel;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.InputStream;

import junit.framework.TestCase;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


/**
 * @author gdefacci
 */
public class Test1 extends TestCase {
	
	public void testSysOut() throws Exception {
		InputStream is = ResourcesUtil.CLASSPATH.loadInputStream("xls/Test1.xls");
		Workbook workbook = Workbook.getWorkbook(is);
		Sheet sheet = workbook.getSheet(0);
		for (int x=0; x<3 ; x++) {
			for (int y=0; y<sheet.getRows(); y++) {
				 Cell cell = sheet.getCell(x,y); 
				 String stringa1 = cell.getContents(); 
				System.out.println(stringa1);
			}
		}
	}

}
